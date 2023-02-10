package com.impact.mods.gregtech.tileentities.multi.ores.hatches;

import gregtech.api.enums.Textures;
import gregtech.api.gui.*;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.render.TextureFactory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class GTMTE_EnrichmentUnit extends GT_MetaTileEntity_Hatch {
	
	public GTMTE_EnrichmentUnit(int aID, String name, int tier) {
		super(aID, "impact.hatch.miner_enrichment_hatch." + tier, name, tier, 3, new String[]{
				"Enrichment unit for miners"
		});
	}
	
	public GTMTE_EnrichmentUnit(String aName, int tier, String[] aDescription, ITexture[][][] aTextures) {
		super(aName, tier, 3, aDescription, aTextures);
	}
	
	@Override
	public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		return new GT_Container_BasicTank(aPlayerInventory, aBaseMetaTileEntity);
	}
	
	@Override
	public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		return new GT_GUIContainer_BasicTank(aPlayerInventory, aBaseMetaTileEntity, "Enrichment Unit");
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
	public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GTMTE_EnrichmentUnit(mName, mTier, mDescriptionArray, mTextures);
	}
	
	@Override
	public ITexture[] getTexturesActive(ITexture aBaseTexture) {
		return new ITexture[]{aBaseTexture, TextureFactory.of(Textures.BlockIcons.OVERLAY_PUMP)};
	}
	
	@Override
	public ITexture[] getTexturesInactive(ITexture aBaseTexture) {
		return new ITexture[]{aBaseTexture, TextureFactory.of(Textures.BlockIcons.OVERLAY_PUMP)};
	}
	
	public boolean onRightclick(IGregTechTileEntity te, EntityPlayer aPlayer) {
		if (te.isServerSide()) {
			te.openGUI(aPlayer);
		}
		return true;
	}
	
	public boolean isFacingValid(byte aFacing) {
		return true;
	}
	
	public boolean doesFillContainers() {
		return true;
	}
	
	public boolean doesEmptyContainers() {
		return true;
	}
	
	public boolean canTankBeFilled() {
		return true;
	}
	
	public boolean canTankBeEmptied() {
		return true;
	}
	
	public boolean displaysItemStack() {
		return true;
	}
	
	public boolean displaysStackSize() {
		return false;
	}
	
	public void updateSlots() {
		if (this.mInventory[this.getInputSlot()] != null && this.mInventory[this.getInputSlot()].stackSize <= 0) {
			this.mInventory[this.getInputSlot()] = null;
		}
	}
	
	public boolean isFluidInputAllowed(FluidStack aFluid) {
		return true;
	}
	
	public boolean allowPullStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack) {
		return aIndex == 1;
	}
	
	public boolean allowPutStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack) {
		return aIndex == 0;
	}
	
	public int getCapacity() {
		return 256_000;
	}
	
	public int getTankPressure() {
		return -100;
	}
}