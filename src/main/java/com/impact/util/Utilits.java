package com.impact.util;

import com.impact.core.Refstrings;
import com.impact.register.SSBodies;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.ReflectionHelper;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.interfaces.tileentity.IHasWorldObjectAndCoords;
import gregtech.api.util.*;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.Iterator;
import java.util.List;

import static net.minecraft.util.StatCollector.translateToLocal;

public class Utilits {

	public static Color getColorTier(int aTier) {
		switch (aTier) {
			case 0:
				return new Color(180, 180, 180);
			case 1:
				return new Color(220, 220, 220);
			case 2:
				return new Color(255, 100, 0);
			case 3:
				return new Color(255, 255, 30);
			case 4:
				return new Color(128, 128, 128);
			case 5:
				return new Color(240, 240, 245);
			case 6:
				return new Color(220, 220, 245);
			case 7:
				return new Color(200, 200, 245);
			case 8:
				return new Color(180, 180, 245);
			case 9:
				return new Color(160, 160, 245);
			case 10:
				return new Color(140, 140, 245);
			case 11:
				return new Color(120, 120, 245);
			case 12:
				return new Color(100, 100, 245);
			case 13:
				return new Color(80, 80, 245);
			case 14:
				return new Color(60, 60, 245);
			case 15:
				return new Color(40, 40, 245);
		}
		return Color.BLACK;
	}

	public static short[] getColorToRGBA(Color c) {
		return new short[]{(short) c.getRed(), (short) c.getGreen(), (short) c.getBlue(), 0};
	}

	public static void bindTexture(String texture) {
		Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("impact", texture));
	}

	public static void bindTexture(ResourceLocation resource) {
		Minecraft.getMinecraft().renderEngine.bindTexture(resource);
	}

	public static ResourceLocation getParticleTexture() {
		try {
			return ReflectionHelper.getPrivateValue(EffectRenderer.class, null, new String[]{"particleTextures", "b", "field_110737_b"});
		} catch (Exception var1) {
			return null;
		}
	}

	public static String translate(String text) {
		return translateToLocal("tooltip." + text);
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

	private static Block getBlock(String modID, String name, int meta) {
		if (Loader.isModLoaded(modID)) {
			return Block.getBlockFromItem(GT_ModHandler.getModItem(modID, name, 1, meta).getItem());
		}
		return null;
	}

	public static Block getBlock(String modID, String name) {
		if (Loader.isModLoaded(modID)) {
			return Block.getBlockFromItem(GT_ModHandler.getModItem(modID, name, 1).getItem());
		}
		return null;
	}

	public static ItemStack is(OrePrefixes prefixes, Materials materials) {
		return GT_OreDictUnificator.get(prefixes, materials, 1);
	}

	public static boolean isB(int A, int B) {
		return (A == B);
	}

	public static boolean isB(int A, int B, int C) {
		return (A >= B && A <= C);
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

	public static boolean isLowGravity(int idDim) {
		return idDim == SSBodies.dimensionIDOrbitEarth;
	}

	public static boolean isLowGravity(IGregTechTileEntity iAm) {
		int dimId = iAm.getWorld().provider.dimensionId;
		return dimId == SSBodies.dimensionIDOrbitEarth || isValidDim(dimId, "Orbit") || isValidDim(dimId, "Space") || isValidDim(dimId, "SS") || isValidDim(dimId, "SpaceStation");
	}

	public static AxisAlignedBB setBoxAABB(IGregTechTileEntity igt, double radius) {
		return AxisAlignedBB.getBoundingBox(
				(igt.getXCoord() - radius), (igt.getYCoord() - radius),
				(igt.getZCoord() - radius), (igt.getXCoord() + radius),
				(igt.getYCoord() + radius), (igt.getZCoord() + radius)
		);
	}

	public static GT_Recipe findRecipe(GT_Recipe.GT_Recipe_Map map, IHasWorldObjectAndCoords te, GT_Recipe cashRecipe,
									   boolean aNotUnificated, boolean aDontCheckStackSizes,
									   long aVoltage, FluidStack[] aFluids, ItemStack[] aItems) {
		return map.findRecipe(te, cashRecipe, aNotUnificated, aDontCheckStackSizes, aVoltage, aFluids, aItems);
	}

	public static boolean checkInputs(GT_Recipe recipe, boolean aDecreaseStackSizeBySuccess,
									  boolean aDontCheckStackSizes, FluidStack[] aFluids, ItemStack[] aItems) {
		return recipe.isRecipeInputEqual(aDecreaseStackSizeBySuccess, aDontCheckStackSizes, aFluids, aItems);
	}

	public static void sendChatByTE(IGregTechTileEntity te, String chat) {
		try {
			EntityPlayer player = te.getWorld().getPlayerEntityByName(te.getOwnerName());
			GT_Utility.sendChatToPlayer(player, chat);
		} catch (Exception ignored) {
		}
	}

	public static MovingObjectPosition raytraceFromEntity(World world, Entity player, double range) {
		float f = 1.0F;
		float f1 = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * f;
		float f2 = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * f;
		double d0 = player.prevPosX + (player.posX - player.prevPosX) * (double) f;
		double d1 = player.prevPosY + (player.posY - player.prevPosY) * (double) f;
		if (!world.isRemote && player instanceof EntityPlayer)
			d1 += 1.62D;
		double d2 = player.prevPosZ + (player.posZ - player.prevPosZ) * (double) f;
		Vec3 vec3 = Vec3.createVectorHelper(d0, d1, d2);
		float f3 = MathHelper.cos(-f2 * 0.017453292F - (float) Math.PI);
		float f4 = MathHelper.sin(-f2 * 0.017453292F - (float) Math.PI);
		float f5 = -MathHelper.cos(-f1 * 0.017453292F);
		float f6 = MathHelper.sin(-f1 * 0.017453292F);
		float f7 = f4 * f5;
		float f8 = f3 * f5;
		double d3 = range;
		if (player instanceof EntityPlayerMP) {
			d3 = ((EntityPlayerMP) player).theItemInWorldManager.getBlockReachDistance();
		}
		Vec3 vec31 = vec3.addVector((double) f7 * d3, (double) f6 * d3, (double) f8 * d3);
		return world.rayTraceBlocks(vec3, vec31);
	}

	public static void openGui(EntityPlayer aPlayer, int idGui, World w) {
		aPlayer.openGui(Refstrings.MODID, idGui, w, 0, 0, 0);
	}

	public static void openGui(EntityPlayer aPlayer, int idGui, IGregTechTileEntity igt) {
		aPlayer.openGui(Refstrings.MODID, idGui, igt.getWorld(), igt.getXCoord(), igt.getYCoord(), igt.getZCoord());
	}

	public static boolean equalsIntArray(int[] int1, int[] int2) {
		if (int1.length == int2.length) {
			boolean check = true;
			for (int i = 0; i < int1.length; i++) {
				if (int1[i] != int2[i]) {
					check = false;
					break;
				}
			}
			return check;
		}
		return false;
	}

	public static ItemStack getFluidDisplay(FluidStack fs) {
		return GT_Utility.getFluidDisplayStack(fs, true);
	}

	@Nonnull
	public static String getTimeString(long millis) {
		return getTimeString(millis, true);
	}

	@Nonnull
	public static String getTimeString(long millis, boolean days) {
		int DAY24 = 24 * 60 * 60;
		long secs = millis / 1000L;
		StringBuilder sb = new StringBuilder();

		long h = (secs / 3600L) % 24;
		long m = (secs / 60L) % 60L;
		long s = secs % 60L;

		if (days && secs >= DAY24) {
			sb.append(secs / DAY24);
			//sb.append("d ");
			sb.append(':');
		}

		if (h < 10) {
			sb.append('0');
		}
		sb.append(h);
		//sb.append("h ");
		sb.append(':');
		if (m < 10) {
			sb.append('0');
		}
		sb.append(m);
		//sb.append("m ");
		sb.append(':');
		if (s < 10) {
			sb.append('0');
		}
		sb.append(s);
		//sb.append('s');

		return sb.toString();
	}
}