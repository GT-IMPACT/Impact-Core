package com.impact.mods.GregTech.tileentities.hatches;

import com.impact.util.Utilits;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_InputBus;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Recipe.GT_Recipe_Map;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

public class GTMTE_BoxinatorInputBus extends GT_MetaTileEntity_Hatch_InputBus {

  private int mAmountSlots;
  public GT_Recipe_Map mRecipeMap = GT_Recipe.GT_Recipe_Map.sBoxinatorRecipes;

  public GTMTE_BoxinatorInputBus(int aID, String aName, String aNameRegional, int aTier,
      int aAmountSlots) {
    super(aID, aName, aNameRegional, aTier, aAmountSlots, new String[]{
        "Large Item Input for Utility Machine (Packager)",
        "Shift + right click with screwdriver to turn Sort mode on/off",
        "Capacity: " + aAmountSlots + " stacks",
        Utilits.impactTag()});
    this.mAmountSlots = aAmountSlots;
  }

  public GTMTE_BoxinatorInputBus(String aName, int aTier, int aAmountSlots, String[] aDescription, ITexture[][][] aTextures) {
    super(aName, aTier, aAmountSlots, aDescription, aTextures);
  }

  @Override
  public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
    return new GTMTE_BoxinatorInputBus(mName, mTier, mAmountSlots, mDescriptionArray,
        mTextures);
  }

  @Override
  public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory,
      IGregTechTileEntity aBaseMetaTileEntity) {
    return false;
  }

  @Override
  public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory,
      IGregTechTileEntity aBaseMetaTileEntity) {
    return false;
  }

  @Override
  public boolean allowPutStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide,
      ItemStack aStack) {
    return aSide == getBaseMetaTileEntity().getFrontFacing()
        && mRecipeMap == GT_Recipe.GT_Recipe_Map.sBoxinatorRecipes && mRecipeMap
        .containsInput(aStack);
  }
}
