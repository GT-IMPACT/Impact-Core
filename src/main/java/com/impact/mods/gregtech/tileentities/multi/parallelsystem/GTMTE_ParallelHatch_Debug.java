package com.impact.mods.gregtech.tileentities.multi.parallelsystem;

import gregtech.api.enums.Dyes;
import gregtech.api.enums.ItemList;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;

import static com.impact.mods.gregtech.enums.Texture.Icons.PRL_HATCH_RED;
import static com.impact.mods.gregtech.enums.Texture.Icons.PRL_HATCH_YELLOW;
import static gregtech.api.enums.Dyes.MACHINE_METAL;


public class GTMTE_ParallelHatch_Debug extends GTMTE_ParallelHatch_Input {
	
	private int debugParallel;
	
	
	public GTMTE_ParallelHatch_Debug(int aID, String aName, String aNameRegional, int aTier, int aMaxParallel) {
		super(aID, aName, aNameRegional, aTier, 256);
		isDebug = true;
	}
	
	public GTMTE_ParallelHatch_Debug(String aName, int aTier, String[] aDescription, ITexture[][][] aTextures, int aMaxParallel) {
		super(aName, aTier, aDescription, aTextures, 256);
		isDebug = true;
	}
	
	@Override
	public ITexture[] getTexturesActive(ITexture aBaseTexture) {
		return new ITexture[]{aBaseTexture, TextureFactory.of(PRL_HATCH_YELLOW, Dyes.getModulation(getBaseMetaTileEntity().getColorization(), MACHINE_METAL.getRGBA())),};
	}
	
	@Override
	public ITexture[] getTexturesInactive(ITexture aBaseTexture) {
		return new ITexture[]{aBaseTexture, TextureFactory.of(PRL_HATCH_RED, Dyes.getModulation(getBaseMetaTileEntity().getColorization(), MACHINE_METAL.getRGBA())),};
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
		isDebug = true;
		return new GTMTE_ParallelHatch_Debug(mName, mTier, mDescriptionArray, mTextures, mMaxParallel);
	}
	
	@Override
	public boolean isOutputFacing(byte aSide) {
		return false;
	}
	
	@Override
	public boolean isInputFacing(byte aSide) {
		return aSide == getBaseMetaTileEntity().getFrontFacing();
	}
	
	@Override
	public void onFirstTick(IGregTechTileEntity te) {
		super.onFirstTick(te);
	}
	
	@Override
	public void onNotePadRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
		super.onNotePadRightClick(aSide, aPlayer, aX, aY, aZ);
		if (getBaseMetaTileEntity().isServerSide()) {
			ItemStack tCurrentItem = aPlayer.inventory.getCurrentItem();
			if (ItemList.Tool_NoteBook.getItem() == tCurrentItem.getItem()) {
				GT_ModHandler.damageOrDechargeItem(tCurrentItem, 1, 100, aPlayer);
				NBTTagCompound nbt = tCurrentItem.getTagCompound();
				nbt.setInteger("mXCoordIn", getBaseMetaTileEntity().getXCoord());
				nbt.setInteger("mYCoordIn", getBaseMetaTileEntity().getYCoord());
				nbt.setInteger("mZCoordIn", getBaseMetaTileEntity().getZCoord());
				nbt.setInteger("mDCoordIn", getBaseMetaTileEntity().getWorld().provider.dimensionId);
				GT_Utility.sendChatToPlayer(aPlayer, EnumChatFormatting.YELLOW + "Connection start");
				GT_Utility.sendChatToPlayer(aPlayer, "X: " + getBaseMetaTileEntity().getXCoord() + " Y: " +
						getBaseMetaTileEntity().getYCoord() + " Z: " + getBaseMetaTileEntity().getZCoord());
			}
		}
	}
	
	@Override
	public void saveNBTData(NBTTagCompound aNBT) {
		super.saveNBTData(aNBT);
		aNBT.setInteger("debugParallel", debugParallel);
	}
	
	@Override
	public void loadNBTData(NBTTagCompound aNBT) {
		super.loadNBTData(aNBT);
		debugParallel = aNBT.getInteger("debugParallel");
	}
	
	@Override
	public void onScrewdriverRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
		if (getBaseMetaTileEntity().isServerSide()) {
			if (!aPlayer.isSneaking()) {
				debugParallel++;
				if (debugParallel > 4) {
					debugParallel = 0;
				}
				GT_Utility.sendChatToPlayer(aPlayer, EnumChatFormatting.YELLOW + "Parallel: " + (int) Math.pow(4, debugParallel));
			}
		}
		super.onScrewdriverRightClick(aSide, aPlayer, aX, aY, aZ);
	}
	
	@Override
	public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
		super.onPostTick(aBaseMetaTileEntity, aTick);
		if (aBaseMetaTileEntity.isServerSide()) {
//			setTrueRecipe(true);
			mMaxParallel = (int) Math.pow(4, debugParallel);
			aBaseMetaTileEntity.setActive(true);
		}
	}
}
