package com.impact.mods.gregtech.tileentities.multi.matrixsystem;

import com.impact.mods.gregtech.enums.Texture;
import com.impact.mods.gregtech.gui.matrixsystem.GT_Container_MP_Chamber;
import com.impact.mods.gregtech.gui.matrixsystem.GT_GUIContainer_MP_Chamber;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import static com.impact.common.item.Core_List_Items.MPBufferEmpty;
import static com.impact.common.item.Core_List_Items.MPBufferFull;

public class GTMTE_Hatch_MESystemMPChamber extends GT_MetaTileEntity_Hatch {

	private int mMPSummary;
	private final static int MAX_MATRIX_PARTICLES = 10_000;

	public GTMTE_Hatch_MESystemMPChamber(int aID) {
		super(aID, "matrix.system.mp_chamber", "Matrix Particles Chamber", 5, 2, new String[]{});
	}

	public GTMTE_Hatch_MESystemMPChamber(String aName, String aDescription, ITexture[][][] aTextures) {
		super(aName, 5, 2, aDescription, aTextures);
	}

	public GTMTE_Hatch_MESystemMPChamber(String aName, String[] aDescription, ITexture[][][] aTextures) {
		super(aName, 5, 2, aDescription, aTextures);
	}

	@Override
	public ITexture[] getTexturesActive(ITexture aBaseTexture) {
		return new ITexture[]{aBaseTexture,
				TextureFactory.of(Texture.Icons.OVERLAY_MCHAMBER_ITEM_OVERLAY)};
	}

	@Override
	public ITexture[] getTexturesInactive(ITexture aBaseTexture) {
		return new ITexture[]{aBaseTexture,
				TextureFactory.of(Texture.Icons.OVERLAY_MCHAMBER_ITEM_OVERLAY)};
	}

	@Override
	public boolean isFacingValid(byte aFacing) {
		return true;
	}

	@Override
	public boolean isSimpleMachine() {
		return true;
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
		return new GTMTE_Hatch_MESystemMPChamber(mName, mDescriptionArray, mTextures);
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
	public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		return new GT_Container_MP_Chamber(aPlayerInventory, aBaseMetaTileEntity);
	}

	@Override
	public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		return new GT_GUIContainer_MP_Chamber(aPlayerInventory, aBaseMetaTileEntity, "MP Chamber");
	}

	@Override
	public void saveNBTData(NBTTagCompound aNBT) {
		super.saveNBTData(aNBT);
		aNBT.setInteger("mMPSummary", mMPSummary);
	}

	@Override
	public void loadNBTData(NBTTagCompound aNBT) {
		super.loadNBTData(aNBT);
		mMPSummary = aNBT.getInteger("mMPSummary");
	}

	@Override
	public void onScrewdriverRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
		super.onScrewdriverRightClick(aSide, aPlayer, aX, aY, aZ);
	}

	@Override
	public boolean allowPullStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack) {
		return GT_Utility.areStacksEqual(aStack, MPBufferEmpty.get(1));
	}

	@Override
	public boolean allowPutStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack) {
		return aIndex == 0 && !GT_Utility.areStacksEqual(aStack, MPBufferEmpty.get(1));
	}

	@Override
	public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTimer) {
		if (aBaseMetaTileEntity.isServerSide()) {
			ItemStack is = mInventory[0];
			if (aTimer % 20 == 0) {
				aBaseMetaTileEntity.setActive(mMPSummary > 0);
			}
			if (is != null && GT_Utility.areStacksEqual(is, MPBufferFull.get(1))) {
				if (mMPSummary + 1000 <= MAX_MATRIX_PARTICLES) {
					mMPSummary += 1000;
					mInventory[0].stackSize--;
					if (mInventory[0].stackSize <= 0) mInventory[0] = null;
					if (mInventory[1] == null) {
						mInventory[1] = MPBufferEmpty.get(1);
					} else if (GT_Utility.areStacksEqual(mInventory[1], MPBufferEmpty.get(1))) {
						mInventory[1].stackSize++;
					}
				}
			}
		}
	}

	public int getMPSummary() {
		return mMPSummary;
	}

	public void subMPSummary(int out) {
		mMPSummary -= out;
	}

	@Override
	public int getInventoryStackLimit() {
		return 8;
	}

	@Override
	public boolean isValidSlot(int aIndex) {
		return true;
	}
}