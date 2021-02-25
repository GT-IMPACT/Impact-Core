package com.impact.util;

import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.util.GT_LanguageManager;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class Utilits {

  public static boolean invertBoolean(final boolean booleans) {
    if (booleans) {
      return false;
    }
    return true;
  }

  public static String translateGTItemStack(ItemStack itemStack) {
    if (!GT_Utility.isStackValid(itemStack)) {
      return "Not a Valid ItemStack:" + itemStack;
    }
    String ret = GT_LanguageManager
        .getTranslation(GT_LanguageManager.getTranslateableItemStackName(itemStack));
    if (!ret.contains("%material")) {
      return ret;
    }
    String matname = "";
    if (Utilits.checkStackAndPrefix(itemStack)) {
      matname = GT_OreDictUnificator
          .getAssociation(itemStack).mMaterial.mMaterial.mDefaultLocalName;
    }
    return ret.replace("%material", matname);
  }

  public static boolean checkStackAndPrefix(ItemStack itemStack) {
    return itemStack != null && GT_OreDictUnificator.getAssociation(itemStack) != null
        && GT_OreDictUnificator.getAssociation(itemStack).mPrefix != null
        && GT_OreDictUnificator.getAssociation(itemStack).mMaterial != null
        && GT_OreDictUnificator.getAssociation(itemStack).mMaterial.mMaterial != null;
  }

  public static NBTTagCompound getNBT(ItemStack aStack) {
    NBTTagCompound rNBT = aStack.getTagCompound();
    return ((rNBT == null) ? new NBTTagCompound() : rNBT);
  }

  public static void setBoolean(ItemStack aStack, String aTag, boolean aBoolean) {
    NBTTagCompound tNBT = getNBT(aStack);
    tNBT.setBoolean(aTag, aBoolean);
    GT_Utility.ItemNBT.setNBT(aStack, tNBT);
  }

  public static boolean getBoolean(ItemStack aStack, String aTag) {
    NBTTagCompound tNBT = getNBT(aStack);
    return tNBT.getBoolean(aTag);
  }

  public static void setInteger(ItemStack aStack, String aTag, int aInt) {
    NBTTagCompound tNBT = getNBT(aStack);
    tNBT.setInteger(aTag, aInt);
    GT_Utility.ItemNBT.setNBT(aStack, tNBT);
  }

  public static int getInteger(ItemStack aStack, String aTag) {
    NBTTagCompound tNBT = getNBT(aStack);
    return tNBT.getInteger(aTag);
  }

  public static void setLong(ItemStack aStack, String aTag, long aInt) {
    NBTTagCompound tNBT = getNBT(aStack);
    tNBT.setLong(aTag, aInt);
    GT_Utility.ItemNBT.setNBT(aStack, tNBT);
  }

  public static long getLong(ItemStack aStack, String aTag) {
    NBTTagCompound tNBT = getNBT(aStack);
    return tNBT.getLong(aTag);
  }

  public static void setFloat(ItemStack aStack, String aTag, float aInt) {
    NBTTagCompound tNBT = getNBT(aStack);
    tNBT.setFloat(aTag, aInt);
    GT_Utility.ItemNBT.setNBT(aStack, tNBT);
  }

  public static float getFloat(ItemStack aStack, String aTag) {
    NBTTagCompound tNBT = getNBT(aStack);
    return tNBT.getFloat(aTag);
  }

  public static void setString(ItemStack aStack, String aTag, String aString) {
    NBTTagCompound tNBT = getNBT(aStack);
    tNBT.setString(aTag, aString);
    GT_Utility.ItemNBT.setNBT(aStack, tNBT);
  }

  public static String getString(ItemStack aStack, String aTag) {
    NBTTagCompound tNBT = getNBT(aStack);
    return tNBT.getString(aTag);
  }

  public static FluidStack getFluidStack(final String fluidName, final int amount) {
    try {
      FluidStack x = FluidRegistry.getFluidStack(fluidName, amount);
      return x != null ? x.copy() : null;
    } catch (final Throwable e) {
      return null;
    }
  }

  public static FluidStack getFluidStack(final FluidStack vmoltenFluid, final int fluidAmount) {
    try {
      FluidStack x = FluidRegistry.getFluidStack(vmoltenFluid.getFluid().getName(), fluidAmount);
      return x != null ? x.copy() : null;
    } catch (final Throwable e) {
      return null;
    }
  }

  public static ItemStack[] toItemStackArray(List<ItemStack> stacksList) {
    if (stacksList.size() == 0) {
      return null;
    }

    ItemStack[] ret = new ItemStack[stacksList.size()];
    Iterator<ItemStack> iterator = stacksList.iterator();
    for (int i = 0; i < ret.length; i++) {
      ret[i] = iterator.next();
    }
    return ret;
  }

  public static FluidStack[] toFluidStackArray(List<FluidStack> stacksList) {
    if (stacksList.size() == 0) {
      return null;
    }

    FluidStack[] ret = new FluidStack[stacksList.size()];
    Iterator<FluidStack> iterator = stacksList.iterator();
    for (int i = 0; i < ret.length; i++) {
      ret[i] = iterator.next();
    }
    return ret;
  }

  public static ItemStack BlockstackMeta(Block aBlock, int aMeta) {
    return new ItemStack(aBlock, 1, aMeta);
  }

  public static ItemStack Blockstack(Block aBlock, int aAmount, int aMeta) {
    return new ItemStack(aBlock, aAmount, aMeta);
  }

  public static ItemStack Blockstack(Block aBlock, int aAmount) {
    return new ItemStack(aBlock, aAmount, 0);
  }

  public static ItemStack ItemstackMeta(Item aItem, int aMeta) {
    return new ItemStack(aItem, 1, aMeta);
  }

  public static ItemStack Itemstack(Item aItem, int aAmount, int aMeta) {
    return new ItemStack(aItem, aAmount, aMeta);
  }

  public static ItemStack Itemstack(Item aItem, int aAmount) {
    return new ItemStack(aItem, aAmount, 0);
  }

  public static boolean isB(int A, int B) {
    return (A == B);
  }

  public static boolean isB(int A, int B, int C) {
    return (A >= B && A <= C);
  }

  public static float calculateGravity(float Si) {
    return (9.81F - Si) * 0.008664628F;
  }

  public static void registerEntity(Class<? extends Entity> entityClass, String name,
      Object instance) {
    int ID = EntityRegistry.findGlobalUniqueEntityId();
    EntityRegistry.registerGlobalEntityID(entityClass, name, ID);
    EntityRegistry.registerModEntity(entityClass, name, ID, instance, 128, 1, true);
  }

  public static ItemStack[] arrayIS(ItemStack... is) {
    return is.clone();
  }

  public static FluidStack[] arrayFS(FluidStack... fs) {
    return fs.clone();
  }

  public static ItemStack simpleMetaStack(final Item item, int meta, int size) {
    if (item == null) {
      return null;
    }
    if (meta < 0 || meta > Short.MAX_VALUE) {
      meta = 0;
    }
    if (size < 0 || size > 64) {
      size = 1;
    }
    final ItemStack metaStack = new ItemStack(item, size, meta);
    return metaStack;
  }

  public static String getUniqueIdentifier(ItemStack is) {
    return GameRegistry.findUniqueIdentifierFor(is.getItem()).modId + ':' + is.getUnlocalizedName();
  }

  public static int getRandom(int min, int max) {
    return (int) (Math.random() * ((max - min) + 1)) + min;
  }

  public static String impactTag() {
    return "" + EnumChatFormatting.DARK_GRAY + "Impact: " + EnumChatFormatting.DARK_GRAY
        + "GregTech Module";
  }

  public static int square(int a) {
    return a * a;
  }

  public static int distanceBetween2D(int x1, int x2, int y1, int y2) {
    int x = square(x1 - x2);
    int y = square(y1 - y2);
    return (int) Math.sqrt(x + y);
  }

  public static int distanceBetween3D(int x1, int x2, int y1, int y2, int z1, int z2) {
    int x = square(x1 - x2);
    int y = square(y1 - y2);
    int z = square(z1 - z2);
    return (int) Math.sqrt(x + y + z);
  }

  public static int[] getCoordsBaseMTE(IGregTechTileEntity iGregTechTileEntity) {
    return new int[]{
        iGregTechTileEntity.getXCoord(),
        iGregTechTileEntity.getYCoord(),
        iGregTechTileEntity.getZCoord(),
        iGregTechTileEntity.getWorld().provider.dimensionId,
    };
  }

  public static String inToStringUUID(int integer, EntityPlayer player) {
    return Integer.toString(integer) + player.getUniqueID();
  }

  public static boolean isValidDim(int idDim, String nameDim) {
    return DimensionManager.getProvider(idDim).getClass().getName().contains(nameDim);
  }

  public static AxisAlignedBB setBoxAABB(IGregTechTileEntity igt, double radius){
    return AxisAlignedBB.getBoundingBox(
        (igt.getXCoord() - radius), (igt.getYCoord() - radius),
        (igt.getZCoord() - radius), (igt.getXCoord() + radius),
        (igt.getYCoord() + radius), (igt.getZCoord() + radius));
  }
}
