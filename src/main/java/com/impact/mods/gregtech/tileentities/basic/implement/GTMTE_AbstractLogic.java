package com.impact.mods.gregtech.tileentities.basic.implement;

import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicMachine;
import net.minecraft.entity.player.EntityPlayer;

public abstract class GTMTE_AbstractLogic extends GT_MetaTileEntity_BasicMachine {
	
	public GTMTE_AbstractLogic(int aID, String aName, String aNameRegional, int tier, int amperage, int inSlots, int outSlots, String guiName) {
		super(aID, aName, aNameRegional, tier, amperage, "", inSlots, outSlots, guiName, "");
	}
	
	public GTMTE_AbstractLogic(int aID, String aName, String aNameRegional, int tier, String guiName) {
		super(aID, aName, aNameRegional, tier, 1, "", 1, 1, guiName, "");
	}
	
	protected GTMTE_AbstractLogic(String aName, int aTier, String aDescription, ITexture[][][] aTextures) {
		super(aName, aTier, 1, aDescription, aTextures, 1, 1, "dataReader.png", "");
	}
	
	protected GTMTE_AbstractLogic(String aName, int aTier, String aDescription, ITexture[][][] aTextures, int amperage, int inSlots, int outSlots, String guiName) {
		super(aName, aTier, amperage, aDescription, aTextures, inSlots, outSlots, guiName, "");
	}
	
	@Override
	public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
		return new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[mTier][aColorIndex + 1]};
	}
	
	@Override
	public ITexture[][][] getTextureSet(ITexture[] aTextures) {
		return null;
	}
	
	@Override
	public int checkRecipe() {
		return DID_NOT_FIND_RECIPE;
	}
	
	@Override
	public boolean isAccessAllowed(EntityPlayer aPlayer) {
		return true;
	}
	
	@Override
	public boolean isSimpleMachine() {
		return false;
	}
	
	@Override
	public boolean isElectric() {
		return false;
	}
	
	@Override
	public boolean isEnetInput() {
		return false;
	}
}
