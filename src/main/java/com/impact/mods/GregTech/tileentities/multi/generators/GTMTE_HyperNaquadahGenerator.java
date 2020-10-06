package com.impact.mods.GregTech.tileentities.multi.generators;

import com.github.technus.tectech.mechanics.constructable.IConstructable;
import com.github.technus.tectech.mechanics.structure.IStructureDefinition;
import com.github.technus.tectech.mechanics.structure.StructureDefinition;
import com.github.technus.tectech.thing.block.QuantumStuffBlock;
import com.github.technus.tectech.thing.metaTileEntity.multi.base.GT_MetaTileEntity_MultiblockBase_EM;
import com.impact.block.blocks.Block_NqTether;
import com.impact.block.blocks.Block_QuantumStuff;
import com.impact.mods.GregTech.gui.GT_Container_MultiParallelMachine;
import com.impact.mods.GregTech.gui.GUI_BASE;
import com.impact.util.MultiBlockTooltipBuilder;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.input.Keyboard;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;

import static com.github.technus.tectech.mechanics.structure.StructureUtility.*;
import static com.impact.mods.GregTech.blocks.Casing_Helper.sCaseCore2;
import static gregtech.api.enums.GT_Values.RA;

public class GTMTE_HyperNaquadahGenerator extends GT_MetaTileEntity_MultiblockBase_EM implements IConstructable {

    private static final String[] description = new String[]{
            EnumChatFormatting.RED + "Impact Details:",
            "- Naquadah Base Casing",
            "- I-Glass (any glass)",
            "- Tether Core",
            "- Hatches (any Casing)",
            "- Dynamo (any Casing)",
    };

    private static final IStructureDefinition<GTMTE_HyperNaquadahGenerator> STRUCTURE_DEFINITION =
            StructureDefinition.<GTMTE_HyperNaquadahGenerator>builder()
                    .addShape("main", new String[][]{
                            {"               ", "      CCC      ", "    CC   CC    ", "   C       C   ", "  C         C  ", "  C         C  ", " C           C ", " C           C ", " C           C ", "  C         C  ", "  C         C  ", "   C       C   ", "    CC   CC    ", "      C~C      ", "               "},
                            {"      CCC      ", "    CCBBBCC    ", "   CBBAAABBC   ", "  CBAA   AABC  ", " CBA       ABC ", " CBA       ABC ", "CBA         ABC", "CBA         ABC", "CBA         ABC", " CBA       ABC ", " CBA       ABC ", "  CBAA   AABC  ", "   CBBAAABBC   ", "    CCBBBCC    ", "      CCC      "},
                            {"               ", "      CCC      ", "    CC   CC    ", "   C       C   ", "  C         C  ", "  C         C  ", " C           C ", " C           C ", " C           C ", "  C         C  ", "  C         C  ", "   C       C   ", "    CC   CC    ", "      CCC      ", "               "}
                    })
                    .addElement('A', ofBlock(sCaseCore2, 10))
                    .addElement('B', ofBlock(Block_NqTether.INSTANCE, 0))
                    .addElement('C', ofChain(
                            ofHatchAdder(GTMTE_HyperNaquadahGenerator::addClassicToMachineList, 410, sCaseCore2, 10),
                            onElementPass(t -> t.casingCount++, ofBlock(sCaseCore2, 10))
                    ))
                    .build();


    private static Block CASING = sCaseCore2;
    private static byte CASING_META = 10;
    private static ITexture INDEX_CASE = Textures.BlockIcons.casingTexturePages[3][CASING_META + 16];
    private static int CASING_TEXTURE_ID = CASING_META + 128 * 3;
    public boolean Stuff;
    private int casingCount = 0;
    public int EU_PER_TICK = 134217728;
    protected int fuelConsumption = 0;

    public GTMTE_HyperNaquadahGenerator(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
        new Runner().run();
    }

    public GTMTE_HyperNaquadahGenerator(String aName) {
        super(aName);
    }

    @Override
    public IStructureDefinition<GTMTE_HyperNaquadahGenerator> getStructure_EM() {
        return STRUCTURE_DEFINITION;
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GTMTE_HyperNaquadahGenerator(mName);
    }

    @Override
    public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide, final byte aFacing,
                                 final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
        return aSide == aFacing
                ? new ITexture[]{INDEX_CASE, new GT_RenderedTexture(
                aActive
                        ? Textures.BlockIcons.MP1a
                        : Textures.BlockIcons.MP1)}
                : new ITexture[]{INDEX_CASE};
    }

    @Override
    public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_Container_MultiParallelMachine(aPlayerInventory, aBaseMetaTileEntity);
    }

    @Override
    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GUI_BASE(aPlayerInventory, aBaseMetaTileEntity, "Hyper Generator", "MultiParallelBlockGUI.png");
    }

    @Override
    public boolean checkMachine_EM(IGregTechTileEntity iGregTechTileEntity, ItemStack itemStack) {
        casingCount = 0;
        return structureCheck_EM("main", 7, 13, 0) && casingCount >= 5;
    }

    @Override
    public boolean checkRecipe_EM(ItemStack itemStack) {

        final ArrayList<FluidStack> storedFluids = super.getStoredFluids();

        Collection<GT_Recipe> recipeList = GT_Recipe.GT_Recipe_Map.sHyperGenerator.mRecipeList;

        if ((storedFluids.size() > 0 && recipeList != null)) {

            for (FluidStack hatchFluid : storedFluids) {

                for (GT_Recipe aFuel : recipeList) {

                    FluidStack liquid; // Register FluidStack (name fluid from oredict cell materials, amount)

                    if ((liquid = GT_Utility.getFluidForFilledItem(aFuel.getRepresentativeInput(0), true)) != null
                            && hatchFluid.isFluidEqual(liquid)) { // check: fluid cell and fluid without cell

                        fuelConsumption = liquid.amount = aFuel.mSpecialValue; // set Amount: FUEL_PER_SECOND

                        if (super.depleteInput(liquid)) {


                            super.mMaxProgresstime = 20; // 1 Second
                            super.mEfficiencyIncrease = 50; // 50 - 0.5% per cycle
                            if (mEfficiency > 9000) {
                                super.eAmpereFlow = 256;
                                super.mEUt = EU_PER_TICK/256;
                            } else {
                                super.mEUt = 0;
                                super.eAmpereFlow = 0;
                            }// UV * 256A
                            quantumStuff(true);
                            return true;
                        }
                    }
                }
            }
        }
        super.mEUt = 0;
        super.eAmpereFlow = 0;
        super.mEfficiency = 0;
        quantumStuff(false);
        return false;
    }

//    @Override
//    public boolean onRunningTick(ItemStack aStack) {
//       // if (mEfficiency > 9000)
//            addEnergyOutput_EM(EU_PER_TICK/256, 256);
//        return true;
//    }

    private void quantumStuff(boolean shouldExist) {
        IGregTechTileEntity base = getBaseMetaTileEntity();
        if (base != null && base.getWorld() != null) {
            int xDir = ForgeDirection.getOrientation(base.getBackFacing()).offsetX + base.getXCoord();
            int yDir = ForgeDirection.getOrientation(base.getBackFacing()).offsetY * 6 + base.getYCoord();
            int zDir = ForgeDirection.getOrientation(base.getBackFacing()).offsetZ + base.getZCoord();
            Block block = base.getWorld().getBlock(xDir, yDir, zDir);
            if (shouldExist) {
                if (block != null) {
                    base.getWorld().setBlock(xDir, yDir + 6, zDir, Block_QuantumStuff.INSTANCE, 0, 2);
                }
            } else {
                base.getWorld().setBlock(xDir, yDir + 6, zDir, QuantumStuffBlock.INSTANCE, 0, 2);
            }
        }
    }

    @Override
    public String[] getDescription() {
        final MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder();
        b
                .addInfo("Multi-Amperes generator")
                .addInfo("Outputs " + NumberFormat.getNumberInstance().format(EU_PER_TICK) + "EU/t including 256A")
                .addSeparator()
                .addController()
                .addDynamoHatch("Any casing")
                .addMaintenanceHatch("Any casing")
                .addInputHatch("Any casing (max x3)")
                .addCasingInfo("Naquadah Base Casing")
                .addOtherStructurePart("Naquadah Chamber Casing", "inside structure")
                .addOtherStructurePart("Tether Core", "for contain core naqahdah")
                .signAndFinalize(": " + EnumChatFormatting.RED + "IMPACT");
        if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            return b.getInformation();
        } else {
            return b.getStructureInformation();
        }
    }

    @Override
    public String[] getInfoData() {

        return new String[]{
                "Total Output: " + EnumChatFormatting.GREEN + NumberFormat.getNumberInstance().format(super.mEUt*256) + EnumChatFormatting.RESET + " EU/t",
                "Output: " + EnumChatFormatting.GREEN + NumberFormat.getNumberInstance().format(super.mEUt) + EnumChatFormatting.RESET + " EU/t | Amperes: " + EnumChatFormatting.GREEN + "256" + EnumChatFormatting.RESET + " A",
                "Efficiency: " + EnumChatFormatting.YELLOW + (float) this.mEfficiency / 100.0F + EnumChatFormatting.YELLOW + " %",
                "Maintenance: " + ((super.getRepairStatus() == super.getIdealStatus())
                        ? EnumChatFormatting.GREEN + "No Problems" + EnumChatFormatting.RESET
                        : EnumChatFormatting.RED + "Has Problems" + EnumChatFormatting.RESET),
                "Fuel supply: " + EnumChatFormatting.RED + "" + fuelConsumption + EnumChatFormatting.RESET + " L/s"
        };
    }

    @Override
    public void construct(ItemStack stackSize, boolean hintsOnly) {
        structureBuild_EM("main", 7, 13, 0, hintsOnly, stackSize);
    }

    @Override
    public String[] getStructureDescription(ItemStack stackSize) {
        return description;
    }

    public static class Runner implements Runnable {
        public int[] FUEL_PER_SECOND = new int[]{ //for 1A
                20,
                10,
                5,
        };

        public ItemStack[] FUEL_NAME = new ItemStack[]{
                GT_OreDictUnificator.get(OrePrefixes.cell, Materials.HyperFuelI, 1),
                GT_OreDictUnificator.get(OrePrefixes.cell, Materials.HyperFuelII, 1),
                GT_OreDictUnificator.get(OrePrefixes.cell, Materials.HyperFuelIII, 1),
        };

        @Override
        public void run() {

            for (int i = 0; i < FUEL_NAME.length; i++)
                RA.addFuel(FUEL_NAME[i], GT_Utility.getFluidForFilledItem(FUEL_NAME[i], true) == null ? GT_Utility.getContainerItem(FUEL_NAME[i], true) : null, FUEL_PER_SECOND[i] * 256, 10);

        }
    }

}
