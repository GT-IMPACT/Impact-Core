package com.impact.mods.gregtech.tileentities.basic;

import com.impact.util.string.Language;
import gregtech.api.GregTech_API;
import gregtech.api.enums.ConfigCategories;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicGenerator;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GT_Recipe;

public class GTMTE_SemifluidGenerator
    extends GT_MetaTileEntity_BasicGenerator {

  public static final int BASE_POLLUTION = 1;

  public int mEfficiency;

  public GTMTE_SemifluidGenerator(int aID, String aName, String aNameRegional, int aTier) {
    super(aID, aName, aNameRegional, aTier, new String[]{
            Language.transDesc("basic.generator.semifluid.0", "Requires Creosote"),
            String.format(
                    Language.transDesc("basic.generator.semifluid.1", "Causes %s Pollution per second"),
                    (int) (20 * BASE_POLLUTION * Math.pow(2, aTier - 1))
            )});
    onConfigLoad();
  }

  public GTMTE_SemifluidGenerator(String aName, int aTier, String aDescription,
      ITexture[][][] aTextures) {
    super(aName, aTier, aDescription, aTextures);
    onConfigLoad();
  }

  public GTMTE_SemifluidGenerator(String aName, int aTier, String[] aDescription,
      ITexture[][][] aTextures) {
    super(aName, aTier, aDescription, aTextures);
    onConfigLoad();
  }

  public boolean isOutputFacing(byte aSide) {
    return aSide == getBaseMetaTileEntity().getFrontFacing();
  }

  public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
    return new GTMTE_SemifluidGenerator(this.mName, this.mTier, this.mDescriptionArray,
        this.mTextures);
  }

  public GT_Recipe.GT_Recipe_Map getRecipes() {
    return GT_Recipe.GT_Recipe_Map.sSemifluidFuels;
  }

  public int getCapacity() {
    return 8000 * (this.mTier + 1);
  }

  public void onConfigLoad() {
    this.mEfficiency = GregTech_API.sMachineFile
        .get(ConfigCategories.machineconfig, "SemifluidGenerator.efficiency.tier." + this.mTier,
            (100 - this.mTier * 5));
  }


  public int getEfficiency() {
    return this.mEfficiency;
  }

  public ITexture[] getFront(byte aColor) {
    return new ITexture[]{super.getFront(aColor)[0],
        TextureFactory.of(Textures.BlockIcons.DIESEL_GENERATOR_FRONT),
        Textures.BlockIcons.OVERLAYS_ENERGY_OUT[this.mTier]};
  }

  public ITexture[] getBack(byte aColor) {
    return new ITexture[]{super.getBack(aColor)[0],
        TextureFactory.of(Textures.BlockIcons.DIESEL_GENERATOR_BACK)};
  }

  public ITexture[] getBottom(byte aColor) {
    return new ITexture[]{super.getBottom(aColor)[0],
        TextureFactory.of(Textures.BlockIcons.DIESEL_GENERATOR_BOTTOM)};
  }

  public ITexture[] getTop(byte aColor) {
    return new ITexture[]{super.getTop(aColor)[0],
        TextureFactory.of(Textures.BlockIcons.DIESEL_GENERATOR_TOP)};
  }

  public ITexture[] getSides(byte aColor) {
    return new ITexture[]{super.getSides(aColor)[0],
        TextureFactory.of(Textures.BlockIcons.DIESEL_GENERATOR_SIDE)};
  }

  public ITexture[] getFrontActive(byte aColor) {
    return new ITexture[]{super.getFrontActive(aColor)[0],
        TextureFactory.of(Textures.BlockIcons.DIESEL_GENERATOR_FRONT_ACTIVE),
        Textures.BlockIcons.OVERLAYS_ENERGY_OUT[this.mTier]};
  }

  public ITexture[] getBackActive(byte aColor) {
    return new ITexture[]{super.getBackActive(aColor)[0],
        TextureFactory.of(Textures.BlockIcons.DIESEL_GENERATOR_BACK_ACTIVE)};
  }

  public ITexture[] getBottomActive(byte aColor) {
    return new ITexture[]{super.getBottomActive(aColor)[0],
        TextureFactory.of(Textures.BlockIcons.DIESEL_GENERATOR_BOTTOM_ACTIVE)};
  }

  public ITexture[] getTopActive(byte aColor) {
    return new ITexture[]{super.getTopActive(aColor)[0],
        TextureFactory.of(Textures.BlockIcons.DIESEL_GENERATOR_TOP_ACTIVE)};
  }

  public ITexture[] getSidesActive(byte aColor) {
    return new ITexture[]{super.getSidesActive(aColor)[0],
        TextureFactory.of(Textures.BlockIcons.DIESEL_GENERATOR_SIDE_ACTIVE)};
  }

  @Override
  public int getPollution() {
    return (int) (GTMTE_SemifluidGenerator.BASE_POLLUTION * Math.pow(2, mTier - 1));
  }
}
