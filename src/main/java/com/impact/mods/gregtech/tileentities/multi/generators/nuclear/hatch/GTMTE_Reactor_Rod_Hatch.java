package com.impact.mods.gregtech.tileentities.multi.generators.nuclear.hatch;

import com.impact.mods.gregtech.enums.Texture;
import com.impact.mods.gregtech.gui.nuclear.GT_Container_Reactor_Rod;
import com.impact.mods.gregtech.gui.nuclear.GT_GUIContainer_Reactor_Rod;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.items.GT_RadioactiveCellIC_Item;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GT_Utility;
import ic2.core.Ic2Items;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import static gregtech.api.items.GT_RadioactiveCell_Item.getDurabilityOfStack;

public class GTMTE_Reactor_Rod_Hatch extends GT_MetaTileEntity_Hatch {
	
	public int mDownRod = 0;
	public float mCoefficientFuelRod = 0;
	public boolean mStartReactor = false;
	public int mSpeedDecay = 1;
	public int mIDhatch = 0;
	public int mDurability = 0;
	public boolean mIsMox = false;
	
	public GTMTE_Reactor_Rod_Hatch(int aID, String aName, String aNameRegional) {
		super(aID, aName, aNameRegional, 5, 1, new String[]{
				"Hatch is used for feeding fuel rods into the reactor",
				"Adjusted the state of the fuel rods by raising or lowering them,",
				"this dependence affects the final operating time",
				"and the output amount of supercritical steam"});
	}
	
	public GTMTE_Reactor_Rod_Hatch(String aName, String aDescription, ITexture[][][] aTextures) {
		super(aName, 5, 1, aDescription, aTextures);
	}
	
	public GTMTE_Reactor_Rod_Hatch(String aName, String[] aDescription, ITexture[][][] aTextures) {
		super(aName, 5, 1, aDescription, aTextures);
	}
	
	@Override
	public ITexture[] getTexturesActive(ITexture aBaseTexture) {
		return new ITexture[]{aBaseTexture,
				TextureFactory.of(Texture.Icons.OVERLAY_REACTOR_HATCH_ACTIVE)};
	}
	
	@Override
	public ITexture[] getTexturesInactive(ITexture aBaseTexture) {
		return new ITexture[]{aBaseTexture,
				TextureFactory.of(Texture.Icons.OVERLAY_REACTOR_HATCH_INACTIVE)};
	}
	
	@Override
	public boolean isSimpleMachine() {
		return true;
	}
	
	@Override
	public boolean isFacingValid(byte aFacing) {
		return aFacing == 1;
	}
	
	@Override
	public boolean isAccessAllowed(EntityPlayer aPlayer) {
		return true;
	}
	
	@Override
	public boolean isLiquidInput(byte aSide) {
		return false;
	}
	
	@Override
	public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GTMTE_Reactor_Rod_Hatch(mName, mDescriptionArray, mTextures);
	}
	
	@Override
	public boolean onRightclick(IGregTechTileEntity aBaseMetaTileEntity, EntityPlayer aPlayer) {
		if (aBaseMetaTileEntity.isClientSide()) {
			return true;
		}
		aBaseMetaTileEntity.openGUI(aPlayer);
		return true;
	}
	
	@Override
	public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory,
							   IGregTechTileEntity aBaseMetaTileEntity) {
		return new GT_Container_Reactor_Rod(aPlayerInventory, aBaseMetaTileEntity);
	}
	
	@Override
	public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory,
							   IGregTechTileEntity aBaseMetaTileEntity) {
		return new GT_GUIContainer_Reactor_Rod(aPlayerInventory, aBaseMetaTileEntity, "Reactor Rod");
	}
	
	@Override
	public void saveNBTData(NBTTagCompound aNBT) {
		super.saveNBTData(aNBT);
		aNBT.setInteger("mDownRod", mDownRod);
		aNBT.setInteger("mIDhatch", mIDhatch);
		aNBT.setInteger("mDurability", mDurability);
		aNBT.setFloat("mCoefficientFuelRod", mCoefficientFuelRod);
	}
	
	@Override
	public void loadNBTData(NBTTagCompound aNBT) {
		super.loadNBTData(aNBT);
		mDownRod = aNBT.getInteger("mDownRod");
		mIDhatch = aNBT.getInteger("mIDhatch");
		mDurability = aNBT.getInteger("mDurability");
		mCoefficientFuelRod = aNBT.getFloat("mCoefficientFuelRod");
	}
	
	@Override
	public void onScrewdriverRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY,
										float aZ) {
		GT_Utility.sendChatToPlayer(aPlayer, "mDownRod: " + mDownRod);
		GT_Utility.sendChatToPlayer(aPlayer, "mCoefficientFuelRod: " + mCoefficientFuelRod);
		super.onScrewdriverRightClick(aSide, aPlayer, aX, aY, aZ);
	}
	
	@Override
	public boolean allowPullStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide,
								  ItemStack aStack) {
		return checkItemStack(aStack);
	}
	
	private boolean checkItemStack(ItemStack aStack) {
		if (aStack != null) {
			if (aStack.getItem() instanceof GT_RadioactiveCellIC_Item) {
				return ((GT_RadioactiveCellIC_Item) aStack.getItem()).sHeat == 0;
			}
			return aStack.getItem().equals(Ic2Items.reactorDepletedUraniumSimple.getItem()) ||
					aStack.getItem().equals(Ic2Items.reactorDepletedUraniumDual.getItem()) ||
					aStack.getItem().equals(Ic2Items.reactorDepletedUraniumQuad.getItem()) ||
					aStack.getItem().equals(Ic2Items.reactorDepletedMOXSimple.getItem()) ||
					aStack.getItem().equals(Ic2Items.reactorDepletedMOXDual.getItem()) ||
					aStack.getItem().equals(Ic2Items.reactorDepletedMOXQuad.getItem());
		}
		return false;
	}
	
	@Override
	public boolean allowPutStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide,
								 ItemStack aStack) {
		if (aStack.getItem() instanceof GT_RadioactiveCellIC_Item) {
			return ((GT_RadioactiveCellIC_Item) aStack.getItem()).sHeat > 0;
		}
		return false;
	}
	
	@Override
	public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTimer) {
		if (aTimer < 10) {
			getBaseMetaTileEntity().setFrontFacing((byte) 1);
		}
		ItemStack is = mInventory[0];
		
		if (aBaseMetaTileEntity.isServerSide() && aTimer % 20 == 0) {
			mCoefficientFuelRod = 0;
			mDurability = 0;
			if (is != null && is.getItem() instanceof GT_RadioactiveCellIC_Item) {
				GT_RadioactiveCellIC_Item rod = (GT_RadioactiveCellIC_Item) is.getItem();
				aBaseMetaTileEntity.setActive(true);
				mDurability = rod.getMaxDamageEx();
				if (mStartReactor && mDownRod > 0) {
					if (rod.sHeat > 0) {
						mCoefficientFuelRod = ((rod.sHeat + 1) * (rod.sMox ? 4 : 1) * rod.numberOfCells) * mDownRod;
						setMoxFuel(rod.sMox);
						rod.setDamageForStack(is, getDurabilityOfStack(is) + (mDownRod * mSpeedDecay));
						if (getDurabilityOfStack(is) >= rod.getMaxDamageEx()) {
							mInventory[0] = rod.sDepleted.copy();
						}
					}
					mStartReactor = false;
				}
			} else
			aBaseMetaTileEntity.setActive(false);
		}
	}
	
	public int getMaxDurability() {
		return mDurability;
	}
	
	public void setID(int ID) {
		mIDhatch = ID;
	}
	
	public void setMoxFuel(boolean isMox) {
		mIsMox = isMox;
	}
	
	public void setFastDecay(int speedDecay) {
		mSpeedDecay = speedDecay;
	}
	
	public void setRodPosition(int position) {
		mDownRod = position <= 0 ? 0 : Math.min(position, 10);
	}
	
	public void setStartReactor(boolean start) {
		this.mStartReactor = start;
	}
	
	public void updateSlots() {
		for (int i = 0; i < mInventory.length; i++) {
			if (mInventory[i] != null && mInventory[i].stackSize <= 0) {
				mInventory[i] = null;
			}
		}
		fillStacksIntoFirstSlots();
	}
	
	protected void fillStacksIntoFirstSlots() {
		for (int i = 0; i < mInventory.length; i++) {
			for (int j = i + 1; j < mInventory.length; j++) {
				if (mInventory[j] != null && (mInventory[i] == null || GT_Utility
						.areStacksEqual(mInventory[i], mInventory[j]))) {
					GT_Utility
							.moveStackFromSlotAToSlotB(getBaseMetaTileEntity(), getBaseMetaTileEntity(), j, i,
									(byte) 64, (byte) 1, (byte) 64, (byte) 1);
				}
			}
		}
	}
	
	@Override
	public int getInventoryStackLimit() {
		return 1;
	}
	
	@Override
	public boolean isValidSlot(int aIndex) {
		return true;
	}
}