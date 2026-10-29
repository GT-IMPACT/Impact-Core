package com.impact.mods.gregtech.tileentities.multi.storage.hatch;

import appeng.api.AEApi;
import appeng.api.config.AccessRestriction;
import appeng.api.config.Actionable;
import appeng.api.networking.GridFlags;
import appeng.api.networking.IGrid;
import appeng.api.networking.IGridNode;
import appeng.api.networking.events.MENetworkCellArrayUpdate;
import appeng.api.networking.events.MENetworkChannelsChanged;
import appeng.api.networking.events.MENetworkEventSubscribe;
import appeng.api.networking.events.MENetworkPowerStatusChange;
import appeng.api.networking.security.BaseActionSource;
import appeng.api.networking.security.IActionHost;
import appeng.api.networking.security.MachineSource;
import appeng.api.networking.storage.IStorageGrid;
import appeng.api.storage.ICellContainer;
import appeng.api.storage.IMEInventory;
import appeng.api.storage.IMEInventoryHandler;
import appeng.api.storage.StorageChannel;
import appeng.api.storage.data.IAEFluidStack;
import appeng.api.storage.data.IItemList;
import appeng.api.util.AECableType;
import appeng.api.util.DimensionalCoord;
import appeng.me.helpers.AENetworkProxy;
import appeng.me.helpers.IGridProxyable;
import com.impact.util.fluid.IMultiFluidTank;
import com.impact.util.fluid.IMultiFluidWatcher;
import com.impact.util.fluid.MultiFluidHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.impact.mods.gregtech.enums.Texture.Icons.OVERLAY_MULTIHATCH;

/** Доступ к общему хранилищу бака только через AE2. Локального буфера и предметных слотов нет. */
@Optional.InterfaceList(value = {
        @Optional.Interface(iface = "appeng.api.networking.security.IActionHost", modid = "appliedenergistics2", striprefs = true),
        @Optional.Interface(iface = "appeng.me.helpers.IGridProxyable", modid = "appliedenergistics2", striprefs = true),
        @Optional.Interface(iface = "appeng.api.storage.IMEInventory", modid = "appliedenergistics2", striprefs = true),
        @Optional.Interface(iface = "appeng.api.storage.IMEInventoryHandler", modid = "appliedenergistics2", striprefs = true),
        @Optional.Interface(iface = "appeng.api.storage.ICellContainer", modid = "appliedenergistics2", striprefs = true),
})
public class GTMTE_TankHatch extends GT_MetaTileEntity_Hatch implements IGridProxyable, IActionHost, ICellContainer,
        IMEInventory<IAEFluidStack>, IMEInventoryHandler<IAEFluidStack>, IMultiFluidWatcher {

    private MultiFluidHandler mfh;
    private IGregTechTileEntity controller;
    private AENetworkProxy gridProxy;
    private int priority;
    private boolean contentDirty = true;
    private boolean topologyDirty = true;
    private IGrid publishedGrid;
    private boolean publishedExport;
    private Map<FluidStack, Long> publishedContents = new HashMap<>();

    public GTMTE_TankHatch(int id, String name, String regionalName, int tier) {
        super(id, name, regionalName, tier, 0, new String[]{"ME access for the High-Tech fluid tank"});
    }

    public GTMTE_TankHatch(String name, int tier, String description, ITexture[][][] textures) {
        super(name, tier, 0, description, textures);
    }

    public GTMTE_TankHatch(String name, int tier, String[] description, ITexture[][][] textures) {
        super(name, tier, 0, description, textures);
    }

    @Optional.Method(modid = "appliedenergistics2")
    public static IAEFluidStack createAEFluidStack(Fluid fluid, long amount) {
        return createAEFluidStack(fluid.getID(), amount);
    }

    @Optional.Method(modid = "appliedenergistics2")
    public static IAEFluidStack createAEFluidStack(FluidStack fluid) {
        return AEApi.instance().storage().createFluidStack(fluid);
    }

    @Optional.Method(modid = "appliedenergistics2")
    public static IAEFluidStack createAEFluidStack(int fluidId, long amount) {
        return createAEFluidStack(new FluidStack(FluidRegistry.getFluid(fluidId), 1)).setStackSize(amount);
    }

    public void setMultiFluidHandler(MultiFluidHandler storage, IGregTechTileEntity owner) {
        if (mfh == storage && controller == owner) return;
        detachStorage();
        mfh = storage;
        controller = owner;
        if (mfh != null) {
            mfh.addFluidWatcher(this);
            markSharedTopologyDirty(mfh);
        }
        contentDirty = true;
        topologyDirty = true;
    }

    public void detachFromController(IGregTechTileEntity owner) {
        if (controller == owner) detachStorage();
    }

    private void detachStorage() {
        if (mfh != null) {
            mfh.removeFluidWatcher(this);
            markSharedTopologyDirty(mfh);
        }
        mfh = null;
        controller = null;
        contentDirty = true;
        topologyDirty = true;
    }

    private static void markSharedTopologyDirty(MultiFluidHandler storage) {
        for (IMultiFluidWatcher watcher : storage.getFluidWatchers()) {
            if (watcher instanceof GTMTE_TankHatch) ((GTMTE_TankHatch) watcher).topologyDirty = true;
        }
    }

    /** Проверка не загружает чанк контроллера и отсекает обработчики от предыдущего loadNBTData. */
    protected MultiFluidHandler getStorage() {
        IGregTechTileEntity base = getBaseMetaTileEntity();
        if (mfh == null || base == null || controller == null) return null;
        World world = base.getWorld();
        if (world == null || world.isRemote || controller.getWorld() != world) return null;
        if (!isLoadedTile(world, base) || !isLoadedTile(world, controller)) return null;
        IMetaTileEntity owner = controller.getMetaTileEntity();
        if (!(owner instanceof IMultiFluidTank)) return null;
        return controller.isActive() && ((IMultiFluidTank) owner).getMultiFluidHandler() == mfh ? mfh : null;
    }

    private static boolean isLoadedTile(World world, IGregTechTileEntity tile) {
        return !tile.isDead() && world.blockExists(tile.getXCoord(), tile.getYCoord(), tile.getZCoord())
                && world.getTileEntity(tile.getXCoord(), tile.getYCoord(), tile.getZCoord()) == tile;
    }

    @Optional.Method(modid = "appliedenergistics2")
    private IGrid activeGrid() {
        IGridNode node = getGridNode(ForgeDirection.UNKNOWN);
        return node != null && node.isActive() ? node.getGrid() : null;
    }

    /** Один экспорт на пару (обработчик, ME-сеть); независимые сети могут пользоваться тем же баком. */
    protected boolean isExportingStorage() {
        MultiFluidHandler storage = getStorage();
        IGrid grid = activeGrid();
        if (storage == null || grid == null) return false;
        GTMTE_TankHatch selected = null;
        for (IMultiFluidWatcher watcher : storage.getFluidWatchers()) {
            if (!(watcher instanceof GTMTE_TankHatch)) continue;
            GTMTE_TankHatch hatch = (GTMTE_TankHatch) watcher;
            if (hatch.getStorage() != storage || hatch.activeGrid() != grid) continue;
            if (selected == null || hatch.priority > selected.priority) selected = hatch;
        }
        return selected == this;
    }

    private Map<FluidStack, Long> contents() {
        Map<FluidStack, Long> contents = new HashMap<>();
        MultiFluidHandler storage = getStorage();
        if (storage != null && isExportingStorage()) {
            for (FluidStack fluid : storage.getFluids()) {
                if (fluid != null && fluid.amount > 0 && (fluid.tag == null || exportStack(fluid) != null)) {
                    contents.put(fluidKey(fluid), (long) fluid.amount);
                }
            }
        }
        return contents;
    }

    private static FluidStack fluidKey(FluidStack fluid) {
        FluidStack key = fluid.copy();
        key.amount = 1;
        return key;
    }

    @Optional.Method(modid = "appliedenergistics2")
    private static IAEFluidStack exportStack(FluidStack fluid) {
        IAEFluidStack stack = createAEFluidStack(fluid);
        // Эта версия AE2 не сохраняет NBT жидкости. Не объединяем несовместимые варианты в ME-учёте.
        return stack != null && fluid.isFluidEqual(stack.getFluidStack()) ? stack : null;
    }

    private void prepareNetworkOperation() {
        IGrid grid = activeGrid();
        if (!publishedExport || publishedGrid != grid) {
            publishedGrid = grid;
            publishedExport = true;
            publishedContents = contents();
        }
    }

    private void acknowledgeNetworkChange(FluidStack fluid, long amount) {
        // NetworkMonitor сам публикует результат ME-операции. Другие сети должны получить изменение от нас.
        FluidStack key = fluidKey(fluid);
        publishedContents.put(key, publishedContents.getOrDefault(key, 0L) + amount);
        contentDirty = true;
    }

    @Override
    @Optional.Method(modid = "appliedenergistics2")
    public IItemList<IAEFluidStack> getAvailableItems(IItemList<IAEFluidStack> out) {
        MultiFluidHandler storage = getStorage();
        if (storage != null && isExportingStorage()) {
            for (FluidStack fluid : storage.getFluids()) {
                if (fluid == null || fluid.amount <= 0) continue;
                IAEFluidStack stack = exportStack(fluid);
                if (stack != null) out.add(stack);
            }
        }
        return out;
    }

    @Override
    @Optional.Method(modid = "appliedenergistics2")
    public IAEFluidStack injectItems(IAEFluidStack input, Actionable mode, BaseActionSource source) {
        MultiFluidHandler storage = getStorage();
        if (input == null || input.getStackSize() <= 0 || storage == null || !isExportingStorage()) return input;
        FluidStack fluid = input.getFluidStack().copy();
        if (fluid.tag != null && exportStack(fluid) == null) return input;
        fluid.amount = (int) Math.min(Integer.MAX_VALUE, input.getStackSize());
        if (mode == Actionable.MODULATE) prepareNetworkOperation();
        int accepted = storage.pushFluid(fluid, mode == Actionable.MODULATE);
        if (accepted <= 0) return input;
        if (mode == Actionable.MODULATE) acknowledgeNetworkChange(fluid, accepted);
        return accepted == input.getStackSize() ? null : input.copy().setStackSize(input.getStackSize() - accepted);
    }

    @Override
    @Optional.Method(modid = "appliedenergistics2")
    public IAEFluidStack extractItems(IAEFluidStack request, Actionable mode, BaseActionSource source) {
        MultiFluidHandler storage = getStorage();
        if (request == null || request.getStackSize() <= 0 || storage == null || !isExportingStorage()) return null;
        FluidStack fluid = request.getFluidStack().copy();
        if (fluid.tag != null && exportStack(fluid) == null) return null;
        fluid.amount = (int) Math.min(Integer.MAX_VALUE, request.getStackSize());
        if (mode == Actionable.MODULATE) prepareNetworkOperation();
        int extracted = storage.pullFluid(fluid, mode == Actionable.MODULATE);
        if (extracted <= 0) return null;
        if (mode == Actionable.MODULATE) acknowledgeNetworkChange(fluid, -extracted);
        return request.copy().setStackSize(extracted);
    }

    private void synchronizeNetwork() {
        IGrid grid = activeGrid();
        boolean exporting = isExportingStorage();
        Map<FluidStack, Long> current = contents();
        if (topologyDirty || grid != publishedGrid || exporting != publishedExport) {
            IGrid previous = publishedGrid;
            publishedGrid = grid;
            publishedExport = exporting;
            publishedContents = current;
            topologyDirty = false;
            contentDirty = false;
            if (previous != null && previous != grid) previous.postEvent(new MENetworkCellArrayUpdate());
            if (grid != null) grid.postEvent(new MENetworkCellArrayUpdate());
            return;
        }
        if (!contentDirty || grid == null || !exporting) return;
        IStorageGrid storage = grid.getCache(IStorageGrid.class);
        if (storage == null) return;
        Map<FluidStack, Long> changes = new HashMap<>(current);
        for (Map.Entry<FluidStack, Long> entry : publishedContents.entrySet()) {
            changes.merge(entry.getKey(), -entry.getValue(), Long::sum);
        }
        List<IAEFluidStack> deltas = new ArrayList<>();
        for (Map.Entry<FluidStack, Long> entry : changes.entrySet()) {
            if (entry.getValue() != 0) deltas.add(createAEFluidStack(entry.getKey()).setStackSize(entry.getValue()));
        }
        publishedContents = current;
        contentDirty = false;
        if (!deltas.isEmpty()) {
            storage.postAlterationOfStoredItems(StorageChannel.FLUIDS, deltas,
                    new MachineSource(this));
        }
    }

    @Override
    public void onMultiFluidChange(FluidStack fluid, int newAmount) {
        contentDirty = true;
    }

    @Override
    public void onMultiFluidStateChange() {
        contentDirty = true;
    }

    @MENetworkEventSubscribe
    @Optional.Method(modid = "appliedenergistics2")
    public void updateChannels(MENetworkChannelsChanged event) {
        topologyDirty = true;
        if (mfh != null) markSharedTopologyDirty(mfh);
    }

    @MENetworkEventSubscribe
    @Optional.Method(modid = "appliedenergistics2")
    public void powerChange(MENetworkPowerStatusChange event) {
        topologyDirty = true;
        if (mfh != null) markSharedTopologyDirty(mfh);
    }

    @Override
    public void gridChanged() {
        topologyDirty = true;
        if (mfh != null) markSharedTopologyDirty(mfh);
    }

    @Override
    @Optional.Method(modid = "appliedenergistics2")
    public AENetworkProxy getProxy() {
        if (gridProxy == null) {
            gridProxy = new AENetworkProxy(this, "proxy", getStackForm(1), true) {
                private NBTTagCompound savedData = new NBTTagCompound();

                @Override
                public void readFromNBT(NBTTagCompound data) {
                    if (data != null) savedData = (NBTTagCompound) data.copy();
                    super.readFromNBT(data);
                }

                @Override
                public void writeToNBT(NBTTagCompound data) {
                    if (savedData.hasKey("proxy", 10)) data.setTag("proxy", savedData.getCompoundTag("proxy").copy());
                    super.writeToNBT(data);
                }

                @Override
                public void invalidate() {
                    NBTTagCompound saved = new NBTTagCompound();
                    writeToNBT(saved);
                    detachStorage();
                    super.invalidate();
                    readFromNBT(saved);
                }
            };
            gridProxy.setFlags(GridFlags.REQUIRE_CHANNEL);
        }
        return gridProxy;
    }

    @Override
    public void onFirstTick(IGregTechTileEntity base) {
        super.onFirstTick(base);
        if (base.isServerSide() && Loader.isModLoaded("appliedenergistics2")) {
            AENetworkProxy proxy = getProxy();
            proxy.onReady();
            // После загрузки узла применяем владельца, которого GT задаёт при размещении блока.
            proxy.readFromNBT(null);
        }
    }

    @Override
    public void onPostTick(IGregTechTileEntity base, long tick) {
        super.onPostTick(base, tick);
        if (!base.isServerSide()) return;
        // При остановке связь сохраняется: содержимое снова появится после запуска контроллера.
        if (mfh != null && controller != null && (!isLoadedTile(base.getWorld(), controller)
                || !(controller.getMetaTileEntity() instanceof IMultiFluidTank)
                || ((IMultiFluidTank) controller.getMetaTileEntity()).getMultiFluidHandler() != mfh)) {
            detachStorage();
        }
        if (Loader.isModLoaded("appliedenergistics2") && (contentDirty || topologyDirty || tick % 20 == 0)) {
            synchronizeNetwork();
        }
    }

    @Override
    public void onRemoval() {
        detachStorage();
        super.onRemoval();
    }

    @Override
    public void inValidate() {
        detachStorage();
        super.inValidate();
    }

    @Override
    public void saveNBTData(NBTTagCompound nbt) {
        super.saveNBTData(nbt);
        nbt.setInteger("mAEPriority", priority);
        if (Loader.isModLoaded("appliedenergistics2") && gridProxy != null) gridProxy.writeToNBT(nbt);
    }

    @Override
    public void loadNBTData(NBTTagCompound nbt) {
        super.loadNBTData(nbt);
        priority = nbt.getInteger("mAEPriority");
        if (Loader.isModLoaded("appliedenergistics2")) getProxy().readFromNBT(nbt);
        topologyDirty = true;
    }

    @Override
    public boolean onSolderingToolRightClick(byte side, byte wrenchSide, EntityPlayer player, float x, float y, float z) {
        if (getBaseMetaTileEntity().isServerSide()) {
            long next = (long) priority + (player.isSneaking() ? -100 : 100);
            setPriority((int) Math.max(Integer.MIN_VALUE, Math.min(Integer.MAX_VALUE, next)));
            GT_Utility.sendChatToPlayer(player, String.format("Priority set: %s", priority));
        }
        return true;
    }

    public void setPriority(int value) {
        if (priority == value) return;
        priority = value;
        IGregTechTileEntity base = getBaseMetaTileEntity();
        if (base != null && base.isServerSide()) markStorageDirty();
        topologyDirty = true;
        if (mfh != null) markSharedTopologyDirty(mfh);
    }

    @Optional.Method(modid = "appliedenergistics2")
    @Override public void saveChanges(IMEInventory inventory) {
        MultiFluidHandler storage = getStorage();
        if (storage != null) storage.markDirty();
    }

    @Optional.Method(modid = "appliedenergistics2")
    @Override public IGridNode getGridNode(ForgeDirection side) { return getProxy().getNode(); }
    @Optional.Method(modid = "appliedenergistics2")
    @Override public IGridNode getActionableNode() { return getGridNode(ForgeDirection.UNKNOWN); }
    @Optional.Method(modid = "appliedenergistics2")
    @Override public AECableType getCableConnectionType(ForgeDirection side) { return AECableType.SMART; }
    @Optional.Method(modid = "appliedenergistics2")
    @Override public DimensionalCoord getLocation() {
        IGregTechTileEntity base = getBaseMetaTileEntity();
        return new DimensionalCoord(base.getWorld(), base.getXCoord(), base.getYCoord(), base.getZCoord());
    }
    @Optional.Method(modid = "appliedenergistics2")
    @Override public void securityBreak() {}
    @Optional.Method(modid = "appliedenergistics2")
    @Override public StorageChannel getChannel() { return StorageChannel.FLUIDS; }
    @Optional.Method(modid = "appliedenergistics2")
    @Override public AccessRestriction getAccess() { return AccessRestriction.READ_WRITE; }
    @Optional.Method(modid = "appliedenergistics2")
    @Override public boolean isPrioritized(IAEFluidStack input) { return true; }
    @Optional.Method(modid = "appliedenergistics2")
    @Override public boolean canAccept(IAEFluidStack input) {
        return input != null && input.getStackSize() > 0
                && injectItems(input, Actionable.SIMULATE, null) != input;
    }
    @Optional.Method(modid = "appliedenergistics2")
    @Override public List<IMEInventoryHandler> getCellArray(StorageChannel channel) {
        return channel == StorageChannel.FLUIDS && isExportingStorage()
                ? Collections.singletonList(this) : Collections.emptyList();
    }
    @Optional.Method(modid = "appliedenergistics2")
    @Override public int getPriority() { return priority; }
    @Optional.Method(modid = "appliedenergistics2")
    @Override public int getSlot() { return 0; }
    @Optional.Method(modid = "appliedenergistics2")
    @Override public boolean validForPass(int pass) { return true; }
    @Optional.Method(modid = "appliedenergistics2")
    @Override public void blinkCell(int slot) {}

    // Все Forge/GT-пути закрыты, в том числе прямые вызовы без стороны и доступ через covers.
    @Override public int getCapacity() { return 0; }
    @Override public FluidTankInfo[] getTankInfo(ForgeDirection side) { return new FluidTankInfo[0]; }
    @Override public int fill(FluidStack fluid, boolean fill) { return 0; }
    @Override public int fill(ForgeDirection side, FluidStack fluid, boolean fill) { return 0; }
    @Override public FluidStack drain(int amount, boolean drain) { return null; }
    @Override public FluidStack drain(ForgeDirection side, int amount, boolean drain) { return null; }
    @Override public FluidStack drain(ForgeDirection side, FluidStack fluid, boolean drain) { return null; }
    @Override public boolean canFill(ForgeDirection side, Fluid fluid) { return false; }
    @Override public boolean canDrain(ForgeDirection side, Fluid fluid) { return false; }
    @Override public boolean isLiquidInput(byte side) { return false; }
    @Override public boolean isLiquidOutput(byte side) { return false; }
    @Override public boolean isFluidInputAllowed(FluidStack fluid) { return false; }
    @Override public boolean canTankBeFilled() { return false; }
    @Override public boolean canTankBeEmptied() { return false; }
    @Override public boolean doesEmptyContainers() { return false; }
    @Override public boolean doesFillContainers() { return false; }
    @Override public boolean displaysItemStack() { return false; }
    @Override public boolean displaysStackSize() { return false; }
    @Override public boolean allowPullStack(IGregTechTileEntity base, int index, byte side, ItemStack stack) { return false; }
    @Override public boolean allowPutStack(IGregTechTileEntity base, int index, byte side, ItemStack stack) { return false; }
    @Override public ITexture[] getTexturesActive(ITexture base) {
        return new ITexture[]{base, TextureFactory.of(OVERLAY_MULTIHATCH)};
    }
    @Override public ITexture[] getTexturesInactive(ITexture base) {
        return new ITexture[]{base, TextureFactory.of(OVERLAY_MULTIHATCH)};
    }
    @Override public boolean isSimpleMachine() { return true; }
    @Override public boolean isFacingValid(byte facing) { return true; }
    @Override public boolean isAccessAllowed(EntityPlayer player) { return true; }
    @Override public IMetaTileEntity newMetaEntity(IGregTechTileEntity base) {
        return new GTMTE_TankHatch(mName, mTier, mDescriptionArray, mTextures);
    }
}
