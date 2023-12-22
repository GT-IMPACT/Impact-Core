package com.impact.mods.gregtech.tileentities.multi.storage.hatch;

import appeng.api.AEApi;
import appeng.api.config.AccessRestriction;
import appeng.api.config.Actionable;
import appeng.api.networking.GridFlags;
import appeng.api.networking.IGrid;
import appeng.api.networking.IGridNode;
import appeng.api.networking.events.*;
import appeng.api.networking.security.BaseActionSource;
import appeng.api.networking.security.IActionHost;
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
import com.impact.mods.gregtech.tileentities.multi.storage.GTMTE_MultiTank;
import com.impact.mods.gregtech.tileentities.multi.storage.GTMTE_SingleTank;
import com.impact.util.fluid.MultiFluidHandler;
import com.impact.util.fluid.IMultiFluidWatcher;
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
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.impact.mods.gregtech.enums.Texture.Icons.OVERLAY_MULTIHATCH;

@Optional.InterfaceList(value = {
		@Optional.Interface(iface = "appeng.api.networking.security.IActionHost", modid = "appliedenergistics2", striprefs = true),
		@Optional.Interface(iface = "appeng.me.helpers.IGridProxyable", modid = "appliedenergistics2", striprefs = true),
		@Optional.Interface(iface = "appeng.api.storage.IMEInventory", modid = "appliedenergistics2", striprefs = true),
		@Optional.Interface(iface = "appeng.api.storage.IMEInventoryHandler", modid = "appliedenergistics2", striprefs = true),
		@Optional.Interface(iface = "appeng.api.storage.ICellContainer", modid = "appliedenergistics2", striprefs = true),
})
public class GTMTE_TankHatch extends GT_MetaTileEntity_Hatch implements IGridProxyable, IActionHost, ICellContainer,
		IMEInventory<IAEFluidStack>, IMEInventoryHandler<IAEFluidStack>, IMultiFluidWatcher {
	
	private static final HashMap<Integer, Integer> vals = new HashMap<>();
	private static final int INV_SLOT_COUNT = 2;
	
	static {
		vals.put(5, 200000);
	}
	
	public MultiFluidHandler mfh;
	private Set<FluidStack> updatedFluids = new HashSet<>();
	
	private AENetworkProxy gridProxy = null;
	private int priority;
	
	public boolean modeOut = false;
	public boolean lastLocked = false;
	
	public static IAEFluidStack createAEFluidStack(Fluid fluid, long amount) {
		return createAEFluidStack(fluid.getID(), amount);
	}
	
	public static IAEFluidStack createAEFluidStack(FluidStack fluid) {
		return AEApi.instance().storage().createFluidStack(fluid);
	}
	
	public static IAEFluidStack createAEFluidStack(int fluidId, long amount) {
		return createAEFluidStack(new FluidStack(FluidRegistry.getFluid(fluidId), 1)).setStackSize(amount);
	}
	
	public GTMTE_TankHatch(int aID, String aName, String aNameRegional, int aTier) {
		super(aID, aName, aNameRegional, aTier, INV_SLOT_COUNT, new String[]{
				"All-in-one access for the High-Tech fluid tank",
				"Right-click with a screwdriver to toggle auto-output"}
		);
	}
	
	public GTMTE_TankHatch(String aName, int aTier, String aDescription, ITexture[][][] aTextures) {
		super(aName, aTier, INV_SLOT_COUNT, aDescription, aTextures);
	}
	
	public GTMTE_TankHatch(String aName, int aTier, String[] aDescription, ITexture[][][] aTextures) {
		super(aName, aTier, INV_SLOT_COUNT, aDescription, aTextures);
	}
	
	public void setMultiFluidHandler(MultiFluidHandler mfh) {
		if (this.mfh != null) {
			this.mfh.removeFluidWatcher(this);
		}
		this.mfh = mfh;
		if (this.mfh != null) {
			this.lastLocked = mfh.locked;
			this.mfh.addFluidWatcher(this);
		}
		notifyAENetwork(true);
	}

	@Override
	public void saveNBTData(NBTTagCompound aNBT) {
		super.saveNBTData(aNBT);
		aNBT.setBoolean("outputting", modeOut);
		aNBT.setInteger("mAEPriority", this.priority);
		aNBT.setBoolean("lastLocked", lastLocked);
	}
	
	@Override
	public void loadNBTData(NBTTagCompound aNBT) {
		super.loadNBTData(aNBT);
		modeOut = aNBT.getBoolean("outputting");
		this.priority = aNBT.getInteger("mAEPriority");
		lastLocked = aNBT.getBoolean("lastLocked");
	}
	
	@Override
	public ITexture[] getTexturesActive(ITexture aBaseTexture) {
		return new ITexture[]{aBaseTexture, TextureFactory.of(OVERLAY_MULTIHATCH)};
	}
	
	@Override
	public ITexture[] getTexturesInactive(ITexture aBaseTexture) {
		return new ITexture[]{aBaseTexture, TextureFactory.of(OVERLAY_MULTIHATCH)};
	}
	
	@Override
	public boolean isSimpleMachine() {
		return true;
	}
	
	@Override
	public boolean isFacingValid(byte aFacing) {
		return true;
	}
	
	@Override
	public boolean isAccessAllowed(EntityPlayer aPlayer) {
		return true;
	}
	
	@Override
	public IMetaTileEntity newMetaEntity(IGregTechTileEntity iGregTechTileEntity) {
		return new GTMTE_TankHatch(mName, mTier, mDescriptionArray, mTextures);
	}
	
	@Override
	public void onScrewdriverRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY,
										float aZ) {
		modeOut = !modeOut;
		
		GT_Utility.sendChatToPlayer(aPlayer, modeOut ? "Auto-output enabled" : "Auto-output disabled");
	}
	
	@Override
	public boolean onSolderingToolRightClick(byte aSide, byte aWrenchingSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
		if (aPlayer.isSneaking())
			this.priority -= 100;
		else
			this.priority += 100;
		GT_Utility.sendChatToPlayer(aPlayer, String.format("Priority set: %s", this.priority));
		return true;
	}
	
	@Optional.Method(modid = "appliedenergistics2")
	public IGridNode getGridNode(ForgeDirection forgeDirection) {
		AENetworkProxy gp = getProxy();
		return gp != null ? gp.getNode() : null;
	}
	
	@Override
	@Optional.Method(modid = "appliedenergistics2")
	public AECableType getCableConnectionType(ForgeDirection forgeDirection) {
		return AECableType.SMART;
	}
	
	@Optional.Method(modid = "appliedenergistics2")
	public void securityBreak() {}
	
	@Override
	@Optional.Method(modid = "appliedenergistics2")
	public AENetworkProxy getProxy() {
		if (gridProxy == null) {
			gridProxy = new AENetworkProxy(this, "proxy", getStackForm(1), true);
			gridProxy.onReady();
			gridProxy.setFlags(GridFlags.REQUIRE_CHANNEL);
		}
		return this.gridProxy;
	}
	
	@Override
	@Optional.Method(modid = "appliedenergistics2")
	public DimensionalCoord getLocation() {
		IGregTechTileEntity gtm = this.getBaseMetaTileEntity();
		return new DimensionalCoord(gtm.getWorld(), gtm.getXCoord(), gtm.getYCoord(), gtm.getZCoord());
	}
	
	@Override
	@Optional.Method(modid = "appliedenergistics2")
	public IItemList<IAEFluidStack> getAvailableItems(IItemList<IAEFluidStack> out) {
		if (mfh != null) {
			if (mfh.getFluids().isEmpty()) return out;
			mfh.getFluids().forEach(fluidStack -> {
				if (fluidStack != null)
					out.add(createAEFluidStack(fluidStack));
			});
			return out;
		}
		return out;
	}
	
	@Override
	@Optional.Method(modid = "appliedenergistics2")
	public IAEFluidStack injectItems(IAEFluidStack input, Actionable type, BaseActionSource src) {
		FluidStack rInput = input.getFluidStack();
		int amt = fill(null, rInput, false);
		if (amt == rInput.amount) {
			if (type.equals(Actionable.MODULATE)) fill(null, rInput, true);
			return null;
		}
		return input;
	}
	
	@Override
	@Optional.Method(modid = "appliedenergistics2")
	public IAEFluidStack extractItems(IAEFluidStack request, Actionable mode, BaseActionSource src) {
		FluidStack ready = drain(null, request.getFluidStack(), false);
		if (ready != null) {
			if (mode.equals(Actionable.MODULATE)) drain(null, request.getFluidStack(), true);
			return createAEFluidStack(ready.getFluid(), ready.amount);
		}
		else return null;
	}
	
	@Override
	@Optional.Method(modid = "appliedenergistics2")
	public StorageChannel getChannel() {
		return StorageChannel.FLUIDS;
	}

	@Optional.Method(modid = "appliedenergistics2")
	private void notifyAENetwork(boolean allFluids) {
		IGridNode node = getGridNode(null);
		if (node == null) {
			return;
		}

		IGrid grid = node.getGrid();
		if (grid == null) {
			return;
		}

		grid.postEvent(new MENetworkCellArrayUpdate());
		IStorageGrid storageGrid = grid.getCache(IStorageGrid.class);
		if (storageGrid == null) {
			node.getGrid().postEvent(new MENetworkStorageEvent(null, StorageChannel.FLUIDS));
		} else if (mfh != null) {
			List<IAEFluidStack> aeFluids = new ArrayList<>(mfh.getFluids().size());
			if (allFluids) {
				mfh.getFluids().forEach(fluidStack -> {
					aeFluids.add(AEApi.instance().storage().createFluidStack(fluidStack));
				});
			}
			if (!mfh.locked) {
				updatedFluids.forEach(fluidStack -> {
					aeFluids.add(AEApi.instance().storage().createFluidStack(fluidStack));
				});
			}
			storageGrid.postAlterationOfStoredItems(StorageChannel.FLUIDS, aeFluids, new BaseActionSource());
			if (allFluids) {
				node.getGrid().postEvent(new MENetworkStorageEvent(storageGrid.getFluidInventory(), StorageChannel.FLUIDS));
			}
		} else {
			node.getGrid().postEvent(new MENetworkStorageEvent(storageGrid.getFluidInventory(), StorageChannel.FLUIDS));
		}
		node.getGrid().postEvent(new MENetworkCellArrayUpdate());
	}

	@MENetworkEventSubscribe
	@Optional.Method(modid = "appliedenergistics2")
	public void updateChannels(MENetworkChannelsChanged channel) {
		notifyAENetwork(true);
	}
	
	@MENetworkEventSubscribe
	@Optional.Method(modid = "appliedenergistics2")
	public void powerChange(MENetworkPowerStatusChange event) {
		notifyAENetwork(true);
	}

	@Override
	public void onMultiFluidChange(FluidStack fs, int newAmount) {
        updatedFluids.add(new FluidStack(fs.getFluid(), 1));
	}

	@Override
	public void onFirstTick(IGregTechTileEntity aBaseMetaTileEntity) {
		super.onFirstTick(aBaseMetaTileEntity);
		getProxy();
	}
	
	@Override
	public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
		if (mfh != null) {
			boolean fullUpdate = !mfh.locked && (lastLocked != mfh.locked);
			lastLocked = mfh.locked;
			if (fullUpdate || !updatedFluids.isEmpty()) {
				notifyAENetwork(fullUpdate);
				updatedFluids.clear();
			}
		}
		super.onPostTick(aBaseMetaTileEntity, aTick);
	}
	
	@Override
	public int getCapacity() {
		if (getBaseMetaTileEntity() instanceof GTMTE_MultiTank) {
			return (mfh != null) ? mTier * 20000 : 0;
		}
		
		if (getBaseMetaTileEntity() instanceof GTMTE_SingleTank) {
			return (mfh != null) ? mTier * 20000 : 0;
		}
		
		return mTier * 20000;
	}
	
	public void onPreTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
		super.onPreTick(aBaseMetaTileEntity, aTick);
	}
	
	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		FluidTankInfo[] tankInfo = new FluidTankInfo[0];
		List<FluidStack> fluids;
		if (mfh != null) {
			fluids   = mfh.getFluids();
			tankInfo = new FluidTankInfo[fluids.size()];
			for (int i = 0; i < tankInfo.length; i++) {
				tankInfo[i] = new FluidTankInfo(fluids.get(i), mfh.getCapacity());
			}
		}
		return tankInfo;
	}
	
	@Override
	public boolean isFluidInputAllowed(FluidStack aFluid) {
		return true;
	}
	
	@Override
	public boolean doesEmptyContainers() {
		return true;
	}
	
	@Override
	public boolean displaysItemStack() {
		return true;
	}
	
	@Override
	public boolean displaysStackSize() {
		return false;
	}
	
	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		if (mfh != null) {
			return mfh.pushFluid(resource, doFill);
		}
		return 0;
	}
	
	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		if (mfh != null) {
			return new FluidStack(resource.getFluid(), mfh.pullFluid(resource, doDrain));
		}
		return null;
	}
	
	/**
	 * Drains fluid out of 0th internal tank. If the TFFT Controller contains an Integrated Circuit,
	 * drain fluid from the slot equal to the circuit configuration.
	 *
	 * @param from     Orientation the fluid is drained to.
	 * @param maxDrain Maximum amount of fluid to drain.
	 * @param doDrain  If false, drain will only be simulated.
	 * @return FluidStack representing the Fluid and amount that was (or would have been, if
	 * simulated) drained.
	 */
	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		if (mfh != null) {
			final FluidStack drain = mfh.getFluid(0);
			if (drain != null) {
				return new FluidStack(
						drain.getFluid(),
						mfh.pullFluid(new FluidStack(drain.getFluid(), maxDrain), 0, doDrain)
				);
			}
		}
		return null;
	}
	
	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		if (mfh != null) {
			return mfh.couldPush(new FluidStack(fluid, 1));
		}
		return false;
	}
	
	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		if (mfh != null) {
			return mfh.contains(new FluidStack(fluid, 1));
		}
		return false;
	}
	
	@Override
	public boolean allowPullStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide,
								  ItemStack aStack) {
		return true;
	}
	
	@Override
	public boolean allowPutStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide,
								 ItemStack aStack) {
		return true;
	}
	
	@Override
	public boolean canTankBeFilled() {
		return true;
	}
	
	@Override
	public boolean canTankBeEmptied() {
		return true;
	}
	
	@Override
	public boolean doesFillContainers() {
		return true;
	}
	
	@Override
	@Optional.Method(modid = "appliedenergistics2")
	public IGridNode getActionableNode() {
		AENetworkProxy gp = getProxy();
		return gp != null ? gp.getNode() : null;
	}
	
	@Override
	@Optional.Method(modid = "appliedenergistics2")
	public AccessRestriction getAccess() {
		return AccessRestriction.READ_WRITE;
	}
	
	@Override
	@Optional.Method(modid = "appliedenergistics2")
	public boolean isPrioritized(IAEFluidStack input) {
		return true;
	}
	
	@Override
	@Optional.Method(modid = "appliedenergistics2")
	public boolean canAccept(IAEFluidStack input) {
		FluidStack rInput = input.getFluidStack();
		return fill(null, rInput, false) > 0;
	}
	
	@Override
	@Optional.Method(modid = "appliedenergistics2")
	public List<IMEInventoryHandler> getCellArray(StorageChannel channel) {
		List<IMEInventoryHandler> list = new ArrayList<>();
		if (channel == StorageChannel.FLUIDS) {
			list.add(this);
		}
		return list;
	}
	
	@Override
	@Optional.Method(modid = "appliedenergistics2")
	public int getPriority() {
		return this.priority;
	}
	
	@Override
	@Optional.Method(modid = "appliedenergistics2")
	public int getSlot() {
		return 0;
	}
	
	@Override
	@Optional.Method(modid = "appliedenergistics2")
	public boolean validForPass(int i) {
		return true;
	}
	
	@Override
	public void blinkCell(int slot) { }
	
	@Override
	public void saveChanges(IMEInventory cellInventory) {
		//This is handled by host itself.
	}
}


