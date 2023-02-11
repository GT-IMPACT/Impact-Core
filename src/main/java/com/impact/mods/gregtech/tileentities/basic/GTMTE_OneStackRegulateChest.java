package com.impact.mods.gregtech.tileentities.basic;

import com.impact.mods.gregtech.gui.regulatechest.Container_OneStackRegulateChest;
import com.impact.mods.gregtech.gui.regulatechest.GUI_OneStackRegulateChest;
import com.impact.util.string.Language;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_TieredMachineBlock;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class GTMTE_OneStackRegulateChest extends GT_MetaTileEntity_TieredMachineBlock {

	public GTMTE_OneStackRegulateChest(int aID, String aName, String aNameRegional, int aTier) {
		super(aID, aName, aNameRegional, aTier, 27, Language.transDesc("regulate.oneStackChest", "This Chest stores 27 stacks by 1 type"));
	}
	
	public GTMTE_OneStackRegulateChest(String aName, int aTier, String[] aDescription, ITexture[][][] aTextures) {
		super(aName, aTier, 27, aDescription, aTextures);
	}
	
	@Override
	public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GTMTE_OneStackRegulateChest(mName, mTier, mDescriptionArray, mTextures);
	}
	
	@Override
	public boolean isSimpleMachine() {
		return true;
	}
	
	@Override
	public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		return new Container_OneStackRegulateChest(aPlayerInventory, aBaseMetaTileEntity);
	}
	
	@Override
	public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		return new GUI_OneStackRegulateChest(aPlayerInventory, aBaseMetaTileEntity, getLocalName());
	}
	
	@Override
	public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTimer) {
		super.onPostTick(aBaseMetaTileEntity, aTimer);
	}
	
	@Override
	public boolean allowPullStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack) {
		return true;
	}
	
	@Override
	public boolean allowPutStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack) {
		int stackCount = 0;
		int stack = 0;
		boolean is = false;
		if (aBaseMetaTileEntity.getTimer() % 5 == 0) {
			for (ItemStack itemStack : mInventory) {
				if (GT_Utility.areStacksEqual(aStack, itemStack)) {
					stackCount++;
					stack += itemStack.stackSize;
				}
			}
			is = true;
		}
		return is && stackCount <= 1 && stack < 64;
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
	public boolean isValidSlot(int aIndex) {
		return true;
	}
	
	@Override
	public void saveNBTData(NBTTagCompound aNBT) {
	}
	
	@Override
	public void loadNBTData(NBTTagCompound aNBT) {
	}
	
	@Override
	public boolean onRightclick(IGregTechTileEntity aBaseMetaTileEntity, EntityPlayer aPlayer) {
		if (aBaseMetaTileEntity.isClientSide()) return true;
		aBaseMetaTileEntity.openGUI(aPlayer);
		return true;
	}
	
	@Override
	public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
		return aSide == 1 ? new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[mTier][aColorIndex + 1], TextureFactory.of(Textures.BlockIcons.OVERLAY_QCHEST)} : new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[mTier][aColorIndex + 1]};
	}
	
	@Override
	public ITexture[][][] getTextureSet(ITexture[] aTextures) {
		return new ITexture[0][0][0];
	}
}