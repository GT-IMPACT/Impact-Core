package com.impact.recipes;

import cpw.mods.fml.common.Loader;
import gregtech.GT_Mod;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gtPlusPlus.core.material.ALLOY;
import gtPlusPlus.core.material.ELEMENT;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class GTplusplusRecipe implements Runnable {

    private static final String aTextWire1 = "wire."; private static final String aTextCable1 = "cable."; private static final String aTextWire2 = " Wire"; private static final String aTextCable2 = " Cable";
    private final static String aTextPlate = "PPP"; private final static String aTextPlateWrench = "PwP"; private final static String aTextPlateMotor = "PMP"; private final static String aTextCableHull = "CMC";
    private final static String aTextWireHull = "WMW"; private final static String aTextWireChest = "WTW"; private final static String aTextWireCoil = "WCW"; private final static String aTextMotorWire = "EWE";
    private final static String aTextWirePump = "WPW";
    public final static String imagination= EnumChatFormatting.RESET + "You just need " + EnumChatFormatting.DARK_PURPLE + "I" + EnumChatFormatting.LIGHT_PURPLE + "m" + EnumChatFormatting.DARK_RED + "a" + EnumChatFormatting.RED + "g" + EnumChatFormatting.YELLOW + "i" + EnumChatFormatting.GREEN + "n" + EnumChatFormatting.AQUA + "a" + EnumChatFormatting.DARK_AQUA + "t" + EnumChatFormatting.BLUE + "i" + EnumChatFormatting.DARK_BLUE + "o" + EnumChatFormatting.DARK_PURPLE + "n" + EnumChatFormatting.RESET + " to use this.";
    private final static long bits = GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE | GT_ModHandler.RecipeBits.BUFFERED;
    private final static long bitsd = GT_ModHandler.RecipeBits.DISMANTLEABLE | bits;

    private final static boolean aBoolConst_0 = false;
    private final static Boolean isNEILoaded = Loader.isModLoaded("NotEnoughItems");

    public static String plateHastelloyC276= "plateHastelloyC276";
    public static String plateHastelloyN= "plateHastelloyN";
    public static String plateLafiumCompound= "plateLafiumCompound";
    public static String plateCinobiteA243= "plateCinobiteA243";

    @Override
    public void run() {

        GT_Values.RA.addFluidSolidifierRecipe(ItemList.Shape_Mold_Ball.get(0L), new FluidStack(FluidRegistry.getFluid("ender"), 250), new ItemStack(Items.ender_pearl, 1, 0), 100, 30);

        //MK4
        GT_Values.RA.addFusionReactorRecipe(Materials.Plutonium241.getMolten(144), Materials.Helium.getGas(1000), ELEMENT.getInstance().CURIUM.getFluid(144), 96, 98304, 500000000);
        GT_Values.RA.addFusionReactorRecipe(ELEMENT.getInstance().CURIUM.getFluid(144), Materials.Helium.getPlasma(144), ELEMENT.getInstance().CALIFORNIUM.getFluid(144), 128, 196608, 750000000);
        //GT_Values.RA.addFusionReactorRecipe(Materials.Plutonium241.getMolten(144), Materials.Calcium.getPlasma(144), Materials.Flerovium.getMolten(144), 160, 196608, 1000000000);
    }
}
