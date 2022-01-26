package com.impact.mods.gregtech.tileentities.multi.storage.hatch;

import appeng.api.config.AccessRestriction;
import appeng.api.config.Actionable;
import appeng.api.networking.GridFlags;
import appeng.api.networking.IGrid;
import appeng.api.networking.IGridNode;
import appeng.api.networking.events.MENetworkCellArrayUpdate;
import appeng.api.networking.events.MENetworkStorageEvent;
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
import com.impact.util.fluid.SingleFluidHandler;
import cpw.mods.fml.common.Optional;
import extracells.util.FluidUtil;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.impact.mods.gregtech.enums.Texture.Icons.OVERLAY_MULTIHATCH;

@Optional.InterfaceList(value = {
		@Optional.Interface(iface = "appeng.api.networking.security.IActionHost", modid = "appliedenergistics2", striprefs = true),
		@Optional.Interface(iface = "appeng.me.helpers.IGridProxyable", modid = "appliedenergistics2", striprefs = true),
		@Optional.Interface(iface = "appeng.api.storage.IMEInventory", modid = "appliedenergistics2", striprefs = true),
		@Optional.Interface(iface = "appeng.api.storage.IMEInventoryHandler", modid = "appliedenergistics2", striprefs = true),
		@Optional.Interface(iface = "appeng.api.storage.ICellContainer", modid = "appliedenergistics2", striprefs = true),
})
public class GTMTE_TankHatch extends GT_MetaTileEntity_Hatch implements IGridProxyable, IActionHost, ICellContainer,
		IMEInventory<IAEFluidStack>, IMEInventoryHandler<IAEFluidStack> {
	
	private static final HashMap<Integer, Integer> vals = new HashMap<>();
	private static final int INV_SLOT_COUNT = 2;
	
	static {
		vals.put(5, 200000);
	}
	
	public MultiFluidHandler mfhMulti;
	public SingleFluidHandler mfhSingle;
	
	private AENetworkProxy gridProxy = null;
	private int priority;
	
	public boolean modeOut = false;
	
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
	
	public void setMultiFluidHandler(MultiFluidHandler mfhMulti) {
		this.mfhMulti = mfhMulti;
	}
	
	public void setSingleFluidHandler(SingleFluidHandler mfhSingle) {
		this.mfhSingle = mfhSingle;
	}
	
	@Override
	public void saveNBTData(NBTTagCompound aNBT) {
		super.saveNBTData(aNBT);
		aNBT.setBoolean("outputting", modeOut);
		aNBT.setInteger("mAEPriority", this.priority);
	}
	
	@Override
	public void loadNBTData(NBTTagCompound aNBT) {
		super.loadNBTData(aNBT);
		modeOut = aNBT.getBoolean("outputting");
		this.priority = aNBT.getInteger("mAEPriority");
	}
	
	@Override
	public ITexture[] getTexturesActive(ITexture aBaseTexture) {
		return new ITexture[]{aBaseTexture, new GT_RenderedTexture(OVERLAY_MULTIHATCH)};
	}
	
	@Override
	public ITexture[] getTexturesInactive(ITexture aBaseTexture) {
		return new ITexture[]{aBaseTexture, new GT_RenderedTexture(OVERLAY_MULTIHATCH)};
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
		if (mfhMulti != null) {
			if (mfhMulti.getFluids().isEmpty()) return out;
			mfhMulti.getFluids().forEach(fluidStack -> {
				if (fluidStack != null)
					out.add(FluidUtil.createAEFluidStack(fluidStack));
			});
			return out;
		}
		
		if (mfhSingle != null) {
			if (mfhSingle.getFluids().isEmpty()) return out;
			mfhSingle.getFluids().forEach(fluidStack -> {
				if (fluidStack != null)
					out.add(FluidUtil.createAEFluidStack(fluidStack));
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
			return FluidUtil.createAEFluidStack(ready.getFluid(), ready.amount);
		}
		else return null;
	}
	
	@Override
	@Optional.Method(modid = "appliedenergistics2")
	public StorageChannel getChannel() {
		return StorageChannel.FLUIDS;
	}
	
	@Override
	public void onFirstTick(IGregTechTileEntity aBaseMetaTileEntity) {
		super.onFirstTick(aBaseMetaTileEntity);
		getProxy();
	}
	
	@Override
	public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
		IGridNode node = getGridNode(null);
		if (node != null) {
			IGrid grid = node.getGrid();
			if (grid != null) {
				grid.postEvent(new MENetworkCellArrayUpdate());
				IStorageGrid storageGrid = grid.getCache(IStorageGrid.class);
				if (storageGrid == null) {
					node.getGrid().postEvent(new MENetworkStorageEvent(null, StorageChannel.FLUIDS));
				}
				else {
					node.getGrid().postEvent(new MENetworkStorageEvent(storageGrid.getFluidInventory(), StorageChannel.FLUIDS));
				}
				node.getGrid().postEvent(new MENetworkCellArrayUpdate());
			}
		}
		super.onPostTick(aBaseMetaTileEntity, aTick);
	}
	
	
	
	@Override
	public int getCapacity() {
		if (getBaseMetaTileEntity() instanceof GTMTE_MultiTank) {
			return (mfhMulti != null) ? mTier * 20000 : 0;
		}
		
		if (getBaseMetaTileEntity() instanceof GTMTE_SingleTank) {
			return (mfhSingle != null) ? mTier * 20000 : 0;
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
		if (mfhMulti != null) {
			fluids   = mfhMulti.getFluids();
			tankInfo = new FluidTankInfo[fluids.size()];
			for (int i = 0; i < tankInfo.length; i++) {
				tankInfo[i] = new FluidTankInfo(fluids.get(i), mfhMulti.getCapacity());
			}
		}
		if (mfhSingle != null) {
			fluids   = mfhSingle.getFluids();
			tankInfo = new FluidTankInfo[fluids.size()];
			for (int i = 0; i < tankInfo.length; i++) {
				tankInfo[i] = new FluidTankInfo(fluids.get(i), mfhSingle.getCapacity());
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
		if (mfhMulti != null) {
			return mfhMulti.pushFluid(resource, doFill);
		}
		if (mfhSingle != null) {
			return mfhSingle.pushFluid(resource, doFill);
		}
		return 0;
	}
	
	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		if (mfhMulti != null) {
			return new FluidStack(resource.getFluid(), mfhMulti.pullFluid(resource, doDrain));
		}
		if (mfhSingle != null) {
			return new FluidStack(resource.getFluid(), mfhSingle.pullFluid(resource, doDrain));
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
		if (mfhMulti != null) {
			final FluidStack drain = mfhMulti.getFluid(0);
			if (drain != null) {
				return new FluidStack(
						drain.getFluid(),
						mfhMulti.pullFluid(new FluidStack(drain.getFluid(), maxDrain), 0, doDrain)
				);
			}
		}
		if (mfhSingle != null) {
			final FluidStack drain = mfhSingle.getFluid(0);
			if (drain != null) {
				return new FluidStack(
						drain.getFluid(),
						mfhSingle.pullFluid(new FluidStack(drain.getFluid(), maxDrain), 0, doDrain)
				);
			}
		}
		return null;
	}
	
	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		if (mfhMulti != null) {
			return mfhMulti.couldPush(new FluidStack(fluid, 1));
		}
		if (mfhSingle != null) {
			return mfhSingle.couldPush(new FluidStack(fluid, 1));
		}
		return false;
	}
	
	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		if (mfhMulti != null) {
			return mfhMulti.contains(new FluidStack(fluid, 1));
		}
		if (mfhSingle != null) {
			return mfhSingle.contains(new FluidStack(fluid, 1));
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


