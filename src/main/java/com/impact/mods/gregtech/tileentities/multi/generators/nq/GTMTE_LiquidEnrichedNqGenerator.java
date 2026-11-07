package com.impact.mods.gregtech.tileentities.multi.generators.nq;

import com.impact.common.block.blocks.Block_NqTether;
import com.impact.common.block.blocks.Block_QuantumStuff;
import com.impact.mods.gregtech.blocks.HatchCounts;
import com.impact.mods.gregtech.blocks.ImpactCasings;
import com.impact.mods.gregtech.blocks.ImpactHatchElement;
import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_Impact_FuelGeneratorBase;
import com.impact.util.string.Language;
import com.impact.util.vector.Structure;
import com.impact.util.vector.Vector3ic;
import gregtech.api.enums.HatchElement;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ICasing;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTStructureUtility;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.StructureRequirements;
import java.text.NumberFormat;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import space.impact.structures.hint.HintColor;
import space.impact.structures.structure.IStructureDefinition;
import space.impact.structures.structure.ISurvivalBuildEnvironment;
import space.impact.structures.structure.StructureDefinition;

import static com.impact.loader.ItemRegistery.IGlassBlock;
import static com.impact.loader.ItemRegistery.InsideBlock;
import static gregtech.api.enums.GT_Values.RA;
import static java.text.NumberFormat.getNumberInstance;
import static space.impact.structures.structure.StructureUtility.ofBlock;
import static space.impact.structures.structure.StructureUtility.ofBlockAnyMeta;
import static space.impact.structures.structure.StructureUtility.withHint;

public class GTMTE_LiquidEnrichedNqGenerator extends GTMTE_Impact_FuelGeneratorBase<GTMTE_LiquidEnrichedNqGenerator> {

    private static final ICasing CASING = ImpactCasings.NQ_CASING;

    private static final StructureRequirements HATCHES_A = StructureRequirements.builder(ImpactCasings.NQ_CASING, 94)
            .build();

    private static IStructureDefinition<GTMTE_LiquidEnrichedNqGenerator> STRUCTURE_DEFINITION;
    public boolean Stuff;
    public int EU_PER_TICK = 8388608;
    private int casingCount = 0;

    public GTMTE_LiquidEnrichedNqGenerator(int aID, String aNameRegional) {
        super(aID, "impact.multimachine.liquidenrichednqgenerator", aNameRegional);
        new Runner().run();
    }

    public GTMTE_LiquidEnrichedNqGenerator(String aName) {
        super(aName);
    }

    @Override
    public IStructureDefinition<GTMTE_LiquidEnrichedNqGenerator> getStructureDefinition() {
        if (STRUCTURE_DEFINITION == null) {
            STRUCTURE_DEFINITION = StructureDefinition
                    .<GTMTE_LiquidEnrichedNqGenerator>builder()
                    .addChannel(GTStructureUtility.HATCH_CHANNEL, 1, 1)
                    .addShape("main", new String[][] {
                            { "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "   AA~AA   ", "   AAAAA   " },
                            { "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "   AAAAA   ", "  A     A  ", "  AAAAAAA  " },
                            { "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "   ABABA   ", "  AA   AA  ", " A       A ", " AAAAAAAAA " },
                            { "     A     ", "     A     ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "  ABBABBA  ", " AA     AA ", "A         A", "AAAAAAAAAAA" },
                            { "           ", "     A     ", "     B     ", "     B     ", "     B     ", "     B     ", "     B     ", "     B     ", "     B     ", "     B     ", "  BBABABB  ", " A  DBD  A ", "A   DDD   A", "AAAAAAAAAAA" },
                            { "   A   A   ", "   AACAA   ", "    B B    ", "    B B    ", "    B B    ", "    B B    ", "    B B    ", "    B B    ", "    B B    ", "    B B    ", "  AAB BAA  ", " A  B B  A ", "A   D D   A", "AAAAADAAAAA" },
                            { "           ", "     A     ", "     B     ", "     B     ", "     B     ", "     B     ", "     B     ", "     B     ", "     B     ", "     B     ", "  BBABABB  ", " A  DBD  A ", "A   DDD   A", "AAAAAAAAAAA" },
                            { "     A     ", "     A     ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "  ABBABBA  ", " AA     AA ", "A         A", "AAAAAAAAAAA" },
                            { "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "   ABABA   ", "  AA   AA  ", " A       A ", " AAAAAAAAA " },
                            { "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "   AAAAA   ", "  A     A  ", "  AAAAAAA  " },
                            { "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "   AAAAA   ", "   AAAAA   " }
                    })
                    .addElement('A', withHint(GTStructureUtility.buildHatchAdder(GTMTE_LiquidEnrichedNqGenerator.class)
                            .allow(HatchElement.Input, HatchElement.Output, HatchElement.Maintenance,
                                    HatchElement.Energy, ImpactHatchElement.Muffler, ImpactHatchElement.Dynamo,
                                    ImpactHatchElement.LaserInput, ImpactHatchElement.LaserOutput)
                            .autoPlace(ImpactHatchElement.PowerOutput, ImpactHatchElement.PowerInput, HatchElement.Input, HatchElement.Output, HatchElement.Maintenance, ImpactHatchElement.Muffler)
                            .buildAndChain(HATCHES_A, t -> t.casingCount++), HintColor.GREEN))
                    .addElement('B', ofBlockAnyMeta(IGlassBlock))
                    .addElement('C', ofBlock(Block_NqTether.INSTANCE, 0))
                    .addElement('D', ofBlock(InsideBlock, 0))
                    .build();
        }
        return STRUCTURE_DEFINITION;
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GTMTE_LiquidEnrichedNqGenerator(mName);
    }

    @Override
    public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide, final byte aFacing,
            final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
        return aSide == aFacing
                ? new ITexture[] { CASING.getTexture(),
                        TextureFactory.of(aActive ? Textures.BlockIcons.MP1a : Textures.BlockIcons.MP1) }
                : new ITexture[] { CASING.getTexture() };
    }


    @Override
    public boolean machineStructure(IGregTechTileEntity iGregTechTileEntity) {
        casingCount = 0;
        boolean check = checkPiece(5, 12, 0)
                && mMaintenanceHatches.size() <= 1 && HatchCounts.inputHatches(this) <= 3;
        if (!HATCHES_A.isSatisfied(this, casingCount))
            check = false;
        return check;
    }

    @Override
    protected long getFuelCycleGeneration() {
        return mEfficiency > 9000 ? EU_PER_TICK : 0;
    }

    @Override
    public GT_Recipe.GT_Recipe_Map getRecipeMap() {
        return GT_Recipe.GT_Recipe_Map.sLiquidENqGenerator;
    }

    @Override
    public GT_Recipe.GT_Recipe_Map getRecipeMapForNEI() {
        return GT_Recipe.GT_Recipe_Map.sLiquidENqGenerator;
    }

    @Override
    public void onPostTick(IGregTechTileEntity base, long tick) {
        super.onPostTick(base, tick);
        if (base.isServerSide() && mStartUpCheck < 0) {
            quantumStuff(base.isActive());
        }
    }

    @Override
    public void stopMachine() {
        super.stopMachine();
        quantumStuff(false);
    }

    @Override
    public void onRemoval() {
        quantumStuff(false, false);
        super.onRemoval();
    }

    private void quantumStuff(boolean active) {
        quantumStuff(mMachine, active);
    }

    private void quantumStuff(boolean shouldExist, boolean active) {
        IGregTechTileEntity base = getBaseMetaTileEntity();
        if (base != null && base.isServerSide() && base.getWorld() != null) {
            Vector3ic vec = Structure.goBuild(base, 0, 13, -5);
            Block current = Structure.getBlock(base, vec);
            if (shouldExist) {
                int meta = active ? Block_QuantumStuff.ACTIVE_META : Block_QuantumStuff.INACTIVE_META;
                if (current == Block_QuantumStuff.INSTANCE) {
                    if (base.getMetaIDOffset(vec.x(), vec.y(), vec.z()) == meta)
                        return;
                } else if (!base.getWorld().isAirBlock(base.getXCoord() + vec.x(), base.getYCoord() + vec.y(),
                        base.getZCoord() + vec.z())) {
                    return;
                }
                Structure.setBlock(base, vec, Block_QuantumStuff.INSTANCE, meta);
            } else {
                if (current != Block_QuantumStuff.INSTANCE)
                    return;
                Structure.setAir(base, vec);
            }
            int x = base.getXCoord() + vec.x();
            int y = base.getYCoord() + vec.y();
            int z = base.getZCoord() + vec.z();
            base.getWorld().markBlockRangeForRenderUpdate(x - 2, y - 2, z - 2, x + 2, y + 2, z + 2);
        }
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        return new MultiblockTooltipBuilder(mName)
                .addInfo("Multi-Amperes generator")
                .addMachineType("Generator")
                .addInfo(Language.langGetEUTick(getNumberInstance().format(EU_PER_TICK)))
                .addSeparator()
                .addController("See the structure hologram")
                .addOtherStructurePart("Dynamo Hatch", "Any casing")
                .addOtherStructurePart("Maintenance Hatch", "Any casing; maximum 1")
                .addOtherStructurePart("Input Hatch", "Any casing; maximum 3")
                .addCasingInfoMin(ImpactCasings.NQ_CASING, HATCHES_A.getMinimumCasings(), false)
                .addOtherStructurePart(new ItemStack(InsideBlock, 1, 0), "inside structure", 13)
                .addOtherStructurePart("Tether Core", "1x, see the structure hologram")
                .addStructureHint("Casing or compatible hatch", HintColor.GREEN)
                .addStructureInfo("Hatches replacing casings: at most 93")
                .addOtherStructurePart(60, "I-Glass", "Any color; see the structure hologram")
                .addSubChannel(GTStructureUtility.HATCH_CHANNEL, "Installs available fuel inputs, energy inputs, dynamos, maintenance, muffler and output hatches in the generator casing.")
                .toolTipFinisher();
    }

    @Override
    public String[] getInfoData() {
        return new String[] {
                "Total Output: " + EnumChatFormatting.GREEN + NumberFormat.getNumberInstance().format(super.mEUt)
                        + EnumChatFormatting.RESET + " EU/t",
                "Efficiency: " + EnumChatFormatting.YELLOW + (float) this.mEfficiency / 100.0F
                        + EnumChatFormatting.YELLOW + " %",
                "Maintenance: " + ((super.getRepairStatus() == super.getIdealStatus())
                        ? EnumChatFormatting.GREEN + "No Problems" + EnumChatFormatting.RESET
                        : EnumChatFormatting.RED + "Has Problems" + EnumChatFormatting.RESET),
                "Fuel supply: " + EnumChatFormatting.RED + "" + fuelConsumption + EnumChatFormatting.RESET + " L/s"
        };
    }

    @Override
    public void construct(ItemStack stackSize, boolean hintsOnly) {
        buildPiece(stackSize, hintsOnly, 5, 12, 0);
    }

    @Override
    public int survivalConstruct(ItemStack trigger, int budget,
            ISurvivalBuildEnvironment environment) {
        return survivalBuildPiece("main", trigger, 5, 12, 0, budget, environment);
    }

    @Override
    public int getSupportedProcessingModes() {
        return 0;
    }

    public static class Runner implements Runnable {

        public int[] FUEL_PER_SECOND = new int[] { // for 1A
                10,
                5,
                2,
        };

        public ItemStack[] FUEL_NAME = new ItemStack[] {
                GT_OreDictUnificator.get(OrePrefixes.cell, Materials.NaquadahEHeavyFuel, 1),
                GT_OreDictUnificator.get(OrePrefixes.cell, Materials.NaquadahEMediumFuel, 1),
                GT_OreDictUnificator.get(OrePrefixes.cell, Materials.NaquadahELightFuel, 1),
        };

        @Override
        public void run() {
            for (int i = 0; i < FUEL_NAME.length; i++) {
                RA.addFuel(FUEL_NAME[i],
                        GT_Utility.getFluidForFilledItem(FUEL_NAME[i], true) == null
                                ? GT_Utility.getContainerItem(FUEL_NAME[i], true)
                                : null,
                        FUEL_PER_SECOND[i] * 64, 9);
            }
        }
    }
}
