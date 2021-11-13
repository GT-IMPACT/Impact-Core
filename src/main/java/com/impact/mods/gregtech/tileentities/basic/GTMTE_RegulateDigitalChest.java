package com.impact.mods.gregtech.tileentities.basic;

import com.impact.util.Utilits;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.common.tileentities.storage.GT_MetaTileEntity_DigitalChestBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class GTMTE_RegulateDigitalChest extends GT_MetaTileEntity_DigitalChestBase {
	
	public static final int MAX_COUNT = 4_000_000;
	public int mItemCount = 0;
	public int mMaxItemCount = 64;
	public ItemStack mItemStack = null;
	
	public GTMTE_RegulateDigitalChest(int aID, String aName, String aNameRegional, int aTier) {
		super(aID, aName, aNameRegional, aTier);
	}
	
	public GTMTE_RegulateDigitalChest(String aName, int aTier, String[] aDescription, ITexture[][][] aTextures) {
		super(aName, aTier, aDescription, aTextures);
	}
	
	@Override
	public boolean isValidSlot(int aIndex) {
		return true;
	}
	
	@Override
	public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GTMTE_RegulateDigitalChest(mName, mTier, mDescriptionArray, mTextures);
	}
	
	@Override
	protected String chestName() {
		return "Regulate Digital Chest";
	}
	
	@Override
	public int getItemCount() {
		return mItemCount;
	}
	
	@Override
	public void setItemCount(int aCount) {
		mItemCount = aCount;
	}
	
	@Override
	protected ItemStack getItemStack() {
		return mItemStack;
	}
	
	@Override
	protected void setItemStack(ItemStack s) {
		mItemStack = s;
	}
	
	@Override
	public int getMaxItemCount() {
		return mMaxItemCount;
	}
	
	@Override
	public void onScrewdriverRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
		super.onScrewdriverRightClick(aSide, aPlayer, aX, aY, aZ);
		Utilits.openGui(aPlayer, 6, getBaseMetaTileEntity());
	}
	
	@Override
	public void saveNBTData(NBTTagCompound aNBT) {
		super.saveNBTData(aNBT);
		aNBT.setInteger("mMaxItemCount", mMaxItemCount);
	}
	
	@Override
	public void loadNBTData(NBTTagCompound aNBT) {
		super.loadNBTData(aNBT);
		mMaxItemCount = aNBT.getInteger("mMaxItemCount");
	}
}