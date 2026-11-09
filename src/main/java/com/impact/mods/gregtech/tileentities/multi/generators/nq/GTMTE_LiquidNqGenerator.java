package com.impact.mods.gregtech.tileentities.multi.generators.nq;

import com.impact.common.block.blocks.Block_NqTether;
import com.impact.common.block.blocks.Block_QuantumStuff;
import com.impact.mods.gregtech.blocks.HatchCounts;
import com.impact.mods.gregtech.blocks.ImpactCasings;
import com.impact.mods.gregtech.blocks.ImpactHatchElement;
import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_Impact_FuelGeneratorBase;
import com.impact.util.ImpactMultiblockTooltipBuilder;
import com.impact.util.vector.Structure;
import com.impact.util.vector.Vector3i;
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
import space.impact.structures.alignment.IAlignmentLimits;
import space.impact.structures.hint.HintColor;
import space.impact.structures.structure.IStructureDefinition;
import space.impact.structures.structure.ISurvivalBuildEnvironment;
import space.impact.structures.structure.StructureDefinition;

import static com.impact.loader.ItemRegistery.IGlassBlock;
import static com.impact.loader.ItemRegistery.InsideBlock;
import static gregtech.api.enums.GT_Values.RA;
import static space.impact.structures.structure.StructureUtility.ofBlock;
import static space.impact.structures.structure.StructureUtility.ofBlockAnyMeta;

public class GTMTE_LiquidNqGenerator extends GTMTE_Impact_FuelGeneratorBase<GTMTE_LiquidNqGenerator> {

    private static final ICasing CASING = ImpactCasings.NQ_CASING;
    private int casingCount;

    private static final StructureRequirements SHELL = StructureRequirements.builder(CASING, 121)
            .atLeast(HatchElement.Maintenance).build();
    private static IStructureDefinition<GTMTE_LiquidNqGenerator> definition;
    public int EU_PER_TICK = 524288;

    public GTMTE_LiquidNqGenerator(int aID, String aNameRegional) {
        super(aID, "impact.multimachine.liquidnaqgen", aNameRegional);
        new FuelNqGenerator().run();
    }

    public GTMTE_LiquidNqGenerator(String aName) {
        super(aName);
    }

    @Override
    public void construct(ItemStack itemStack, boolean b) {
        buildPiece(itemStack, b, 3, 3, 1);
    }

    @Override
    public int survivalConstruct(ItemStack trigger, int budget,
            ISurvivalBuildEnvironment environment) {
        return survivalBuildPiece("main", trigger, 3, 3, 1, budget, environment);
    }

    @Override
    public IStructureDefinition<GTMTE_LiquidNqGenerator> getStructureDefinition() {
        if (definition == null) {
            definition = StructureDefinition.<GTMTE_LiquidNqGenerator>builder()
                    .addShape("main", new String[][] {
                            { "       ", " EE EE ", " E   E ", "       ", " E   E ", " EE EE ", "       " },
                            { "  EEE  ", " EEEEE ", "EEEEEEE", "EEE~EEE", "EEEEEEE", " EEEEE ", "  EEE  " },
                            { "  GGG  ", " E   E ", "G D D G", "G DCD G", "G D D G", " E   E ", "  GGG  " },
                            { "  GGG  ", " E   E ", "G     G", "G     G", "G     G", " E   E ", "  GGG  " },
                            { "  GGG  ", " E   E ", "G     G", "G     G", "G     G", " E   E ", "  GGG  " },
                            { "  GGG  ", " E   E ", "G     G", "G     G", "G     G", " E   E ", "  GGG  " },
                            { "  GGG  ", " E   E ", "G D D G", "G DCD G", "G D D G", " E   E ", "  GGG  " },
                            { "  EEE  ", " EEEEE ", "EEEEEEE", "EEEEEEE", "EEEEEEE", " EEEEE ", "  EEE  " },
                            { "       ", " BB BB ", " BBBBB ", "  BBB  ", " BBBBB ", " BB BB ", "       " }
                    })
                    .addChannel(GTStructureUtility.HATCH_CHANNEL, 1, 1)
                    .addElement('G', ofBlockAnyMeta(IGlassBlock))
                    .addElement('C', ofBlock(Block_NqTether.INSTANCE, 0))
                    .addElement('D', ofBlock(InsideBlock, 0))
                    .addElement('B', GTStructureUtility.buildHatchAdder(GTMTE_LiquidNqGenerator.class)
                            .allow(ImpactHatchElement.FluidInput, ImpactHatchElement.Dynamo,
                                    ImpactHatchElement.LaserOutput)
                            .autoPlace(ImpactHatchElement.PowerOutput, ImpactHatchElement.FluidInput)
                            .buildAndChain(SHELL, machine -> machine.casingCount++).withHint(HintColor.GREEN))
                    .addElement('E', GTStructureUtility.buildHatchAdder(GTMTE_LiquidNqGenerator.class)
                            .allow(ImpactHatchElement.FluidInput)
                            .autoPlace(ImpactHatchElement.FluidInput)
                            .buildAndChain(SHELL, machine -> machine.casingCount++).withHint(HintColor.LIME))
                    .build();
        }
        return definition;
    }

    protected IAlignmentLimits getInitialAlignmentLimits() {
        return (direction, rotation, flip) -> (true) && rotation.isNotRotated() && flip.isNotFlipped();
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
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GTMTE_LiquidNqGenerator(this.mName);
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        return new ImpactMultiblockTooltipBuilder(mName)
                .useCompactStructure()
                .addMachineType("Generator")
                .addInfo("Maximum output after warmup: %s EU/t.", EU_PER_TICK)
                .beginStructureBlock(7, 7, 9, false)
                .addController("front center of layer 4")
                .addStructureSection("Components")
                .addStructurePartInfo(CASING, "%s+; fill unused casing positions", SHELL.getMinimumCasings())
                .addOtherStructurePart(new ItemStack(InsideBlock, 1, 0), "exactly 12; inner rings next to the front and rear walls")
                .addOtherStructurePart(new ItemStack(Block_NqTether.INSTANCE, 1, 0), "exactly 2; centers of the inner rings")
                .addOtherStructurePart("I-Glass", "exactly 60; side walls; any color")
                .addStructureSection("Hatches and buses")
                .addHatchInfo("Maintenance Hatch", "exactly 1; casing positions")
                .addHatchInfo("Input Hatches", "optional, up to 3; casing positions")
                .addHatchInfo("Dynamo Hatch", "optional, up to 1; rear end layer")
                .addStructureHint("Rear end: casings or input/maintenance/dynamo hatches", HintColor.GREEN)
                .addStructureHint("Other casing positions: casings or input/maintenance hatches", HintColor.LIME)
                .addHatchChannelInfo()
                .toolTipFinisher();
    }


    public Vector3ic rotateOffsetVector(Vector3ic forgeDirection, int x, int y, int z) {
        final Vector3i offset = new Vector3i();

        // either direction on z-axis
        if (forgeDirection.x() == 0 && forgeDirection.z() == -1) {
            offset.x = x;
            offset.y = y;
            offset.z = z;
        }
        if (forgeDirection.x() == 0 && forgeDirection.z() == 1) {
            offset.x = -x;
            offset.y = y;
            offset.z = -z;
        }
        // either direction on x-axis
        if (forgeDirection.x() == -1 && forgeDirection.z() == 0) {
            offset.x = z;
            offset.y = y;
            offset.z = -x;
        }
        if (forgeDirection.x() == 1 && forgeDirection.z() == 0) {
            offset.x = -z;
            offset.y = y;
            offset.z = x;
        }
        // either direction on y-axis
        if (forgeDirection.y() == -1) {
            offset.x = x;
            offset.y = z;
            offset.z = y;
        }

        return offset;
    }

    @Override
    public boolean machineStructure(IGregTechTileEntity thisController) {
        casingCount = 0;

        boolean formationChecklist = checkPiece(3, 3, 1) && SHELL.isSatisfied(this, casingCount);

        if (HatchCounts.inputBuses(this) != 0) {
            formationChecklist = false;
        }
        if (HatchCounts.inputHatches(this) > 3) {
            formationChecklist = false;
        }
        if (this.mOutputBusses.size() != 0) {
            formationChecklist = false;
        }
        if (this.mOutputHatches.size() != 0) {
            formationChecklist = false;
        }
        if (this.mMufflerHatches.size() != 0) {
            formationChecklist = false;
        }
        if (HatchCounts.energyHatches(this) != 0) {
            formationChecklist = false;
        }
        if (HatchCounts.dynamoHatches(this) > 1) {
            formationChecklist = false;
        }
        if (this.mMaintenanceHatches.size() != 1) {
            formationChecklist = false;
        }
        return formationChecklist;
    }

    @Override
    protected long getFuelCycleGeneration() {
        return mEfficiency > 9000 ? EU_PER_TICK : Math.max(0, lEUt);
    }

    @Override
    public GT_Recipe.GT_Recipe_Map getRecipeMap() {
        return GT_Recipe.GT_Recipe_Map.sLiquidNqGenerator;
    }

    @Override
    public GT_Recipe.GT_Recipe_Map getRecipeMapForNEI() {
        return GT_Recipe.GT_Recipe_Map.sLiquidNqGenerator;
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
            Vector3ic vec = Structure.goBuild(base, 0, 0, -3);
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
    public int getSupportedProcessingModes() {
        return 0;
    }

    static class FuelNqGenerator implements Runnable {

        public int[] FUEL_PER_SECOND = new int[] { // for 1A
                10,
                5,
                2,
        };

        public ItemStack[] FUEL_NAME = new ItemStack[] {
                GT_OreDictUnificator.get(OrePrefixes.cell, Materials.NaquadahHeavyFuel, 1),
                GT_OreDictUnificator.get(OrePrefixes.cell, Materials.NaquadahMediumFuel, 1),
                GT_OreDictUnificator.get(OrePrefixes.cell, Materials.NaquadahLightFuel, 1),
        };

        @Override
        public void run() {
            for (int i = 0; i < FUEL_NAME.length; i++) {
                RA.addFuel(FUEL_NAME[i],
                        GT_Utility.getFluidForFilledItem(FUEL_NAME[i], true) == null
                                ? GT_Utility.getContainerItem(FUEL_NAME[i], true)
                                : null,
                        FUEL_PER_SECOND[i] * 16, 8);
            }
        }
    }
}
