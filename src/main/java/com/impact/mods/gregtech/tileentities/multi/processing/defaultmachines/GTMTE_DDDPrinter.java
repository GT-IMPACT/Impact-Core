package com.impact.mods.gregtech.tileentities.multi.processing.defaultmachines;

import com.impact.mods.gregtech.blocks.HatchCounts;
import com.impact.util.ImpactMultiblockTooltipBuilder;
import com.impact.mods.gregtech.blocks.ImpactCasings;
import com.impact.mods.gregtech.blocks.ImpactHatchElement;
import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_Impact_BlockBase;
import gregtech.api.enums.HatchElement;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ICasing;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTStructureUtility;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.StructureRequirements;
import net.minecraft.item.ItemStack;
import space.impact.structures.hint.HintColor;
import space.impact.structures.structure.IStructureDefinition;
import space.impact.structures.structure.ISurvivalBuildEnvironment;
import space.impact.structures.structure.StructureDefinition;

import static com.impact.loader.ItemRegistery.IGlassBlock;
import static space.impact.structures.structure.StructureUtility.ofBlockAnyMeta;

public class GTMTE_DDDPrinter extends GTMTE_Impact_BlockBase<GTMTE_DDDPrinter> {

    private static final ICasing CASING = ImpactCasings.DDD_PRINTER_CASING;
    private int casingCount;

    private static final StructureRequirements SHELL = StructureRequirements.builder(CASING, 22)
            .atLeast(HatchElement.Maintenance, ImpactHatchElement.PowerInput).build();
    private static IStructureDefinition<GTMTE_DDDPrinter> definition;

    public GTMTE_DDDPrinter(int aID, String aNameRegional) {
        super(aID, "impact.multimachine.dddprinter", aNameRegional);
    }

    public GTMTE_DDDPrinter(String aName) {
        super(aName);
    }

    @Override
    public void construct(ItemStack itemStack, boolean b) {
        buildPiece(itemStack, b, 0, 1, 0);
    }

    @Override
    public int survivalConstruct(ItemStack trigger, int budget,
            ISurvivalBuildEnvironment environment) {
        return survivalBuildPiece("main", trigger, 0, 1, 0, budget, environment);
    }

    @Override
    public IStructureDefinition<GTMTE_DDDPrinter> getStructureDefinition() {
        if (definition == null) {
            definition = StructureDefinition.<GTMTE_DDDPrinter>builder()
                    .addShape("main", new String[][] {
                            { "DGGGD", "~GGGD", "DDDDD" },
                            { "DGGGD", "DCCCD", "DDDDD" },
                            { "DGGGD", "DCCCD", "DDDDD" },
                            { "DGGGD", "DCCCD", "DDDDD" },
                            { "DGGGD", "DGGGD", "DDDDD" }
                    })
                    .addChannel(GTStructureUtility.HATCH_CHANNEL, 1, 1)
                    .addElement('G', ofBlockAnyMeta(IGlassBlock))
                    .addElement('C', ImpactCasings.DDD_PRINTER_CASING3X3.asElement())
                    .addElement('D', GTStructureUtility.buildHatchAdder(GTMTE_DDDPrinter.class)
                            .allow(HatchElement.Input, ImpactHatchElement.Muffler, HatchElement.Energy,
                                    ImpactHatchElement.LaserInput, HatchElement.Output)
                            .autoPlace(ImpactHatchElement.PowerInput, HatchElement.Input, ImpactHatchElement.Muffler, HatchElement.Output)
                            .buildAndChain(SHELL, machine -> machine.casingCount++).withHint(HintColor.GREEN))
                    .build();
        }
        return definition;
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
        return new GTMTE_DDDPrinter(this.mName);
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        return new ImpactMultiblockTooltipBuilder(mName)
                .useCompactStructure()
                .addMachineType("3D Printer 3x3")
                .beginStructureBlock(5, 3, 5, false)
                .addController("front left edge, middle layer")
                .addStructureSection("Components")
                .addStructurePartInfo(SHELL.getCasing(), "%s+; fill unused casing positions", SHELL.getMinimumCasings())
                .addOtherStructurePart(ImpactCasings.DDD_PRINTER_CASING3X3, "exactly 9; center 3x3 of the middle layer")
                .addOtherStructurePart("I-Glass", "exactly 21; roof and front/back walls of the middle layer; any color")
                .addStructureSection("Hatches and buses")
                .addHatchInfo("Maintenance Hatch", "exactly 1; casing positions")
                .addHatchInfo("Energy Hatches", "1-4 total; casing positions")
                .addHatchInfo("Input Buses", "optional; casing positions")
                .addHatchInfo("Input Hatches", "optional; casing positions")
                .addHatchInfo("Output Buses", "optional, up to 3; casing positions")
                .addStructureHint("Casings or hatches/buses", HintColor.GREEN)
                .addHatchChannelInfo()
                .toolTipFinisher();
    }

    @Override
    public GT_Recipe.GT_Recipe_Map getRecipeMap() {
        return GT_Recipe.GT_Recipe_Map.sPrimitiveLine;
    }

    @Override
    public GT_Recipe.GT_Recipe_Map getRecipeMapForNEI() {
        return GT_Recipe.GT_Recipe_Map.sPrimitiveLine;
    }

    @Override
    public boolean machineStructure(IGregTechTileEntity thisController) {
        casingCount = 0;
        boolean formationChecklist = checkPiece(0, 1, 0) && SHELL.isSatisfied(this, casingCount);

        if (HatchCounts.inputBuses(this) > 30) {
            formationChecklist = false;
        }
        if (this.mOutputBusses.size() > 3) {
            formationChecklist = false;
        }
        if (HatchCounts.energyHatches(this) > 4) {
            formationChecklist = false;
        }
        if (this.mMaintenanceHatches.size() != 1) {
            formationChecklist = false;
        }

        return formationChecklist;
    }
}
