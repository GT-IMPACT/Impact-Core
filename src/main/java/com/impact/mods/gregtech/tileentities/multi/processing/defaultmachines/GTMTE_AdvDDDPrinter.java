package com.impact.mods.gregtech.tileentities.multi.processing.defaultmachines;

import com.impact.mods.gregtech.blocks.HatchCounts;
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
import static space.impact.structures.structure.StructureUtility.withHint;

public class GTMTE_AdvDDDPrinter extends GTMTE_Impact_BlockBase<GTMTE_AdvDDDPrinter> {

    private static final ICasing CASING = ImpactCasings.DDD_PRINTER_CASING;
    private int casingCount;

    private static final StructureRequirements SHELL = StructureRequirements.builder(CASING, 30)
            .atLeast(HatchElement.Maintenance).build();
    private static IStructureDefinition<GTMTE_AdvDDDPrinter> definition;

    public GTMTE_AdvDDDPrinter(int aID, String aNameRegional) {
        super(aID, "impact.multimachine.advdddprinter", aNameRegional);
    }

    public GTMTE_AdvDDDPrinter(String aName) {
        super(aName);
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
        return new GTMTE_AdvDDDPrinter(this.mName);
    }

    @Override
    public IStructureDefinition<GTMTE_AdvDDDPrinter> getStructureDefinition() {
        if (definition == null) {
            definition = StructureDefinition.<GTMTE_AdvDDDPrinter>builder()
                    .addShape("main", new String[][] {
                            { "DGGGGD", "~GGGGD", "DDDDDD" },
                            { "DGGGGD", "DCCCCD", "DDDDDD" },
                            { "DGGGGD", "DCCCCD", "DDDDDD" },
                            { "DGGGGD", "DCCCCD", "DDDDDD" },
                            { "DGGGGD", "DCCCCD", "DDDDDD" },
                            { "DGGGGD", "DGGGGD", "DDDDDD" }
                    })
                    .addChannel(GTStructureUtility.HATCH_CHANNEL, 1, 1)
                    .addElement('G', ofBlockAnyMeta(IGlassBlock))
                    .addElement('C', ImpactCasings.DDD_PRINTER_CASING4X4.asElement())
                    .addElement('D', withHint(GTStructureUtility.buildHatchAdder(GTMTE_AdvDDDPrinter.class)
                            .allow(HatchElement.Input, ImpactHatchElement.Muffler, HatchElement.Energy,
                                    ImpactHatchElement.LaserInput, HatchElement.Output)
                            .autoPlace(ImpactHatchElement.PowerInput, HatchElement.Input, ImpactHatchElement.Muffler, HatchElement.Output)
                            .buildAndChain(SHELL, machine -> machine.casingCount++), HintColor.GREEN))
                    .build();
        }
        return definition;
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
    protected MultiblockTooltipBuilder createTooltip() {
        return new MultiblockTooltipBuilder(mName)
                .addInfo("Assembler machines")
                .addMachineType("4x4 Crafting")
                .addInfo("Configure input bus separation in the machine GUI")
                .addSeparator()
                .addController("See the structure hologram")
                .addOtherStructurePart("Energy Hatch", "Any casing; maximum 4")
                .addOtherStructurePart("Maintenance Hatch", "Any casing position; exactly 1")
                .addOtherStructurePart("Input Bus", "Any casing; maximum 30")
                .addOtherStructurePart("Output Bus", "Any casing; maximum 3")
                .addCasingInfoMin(SHELL.getCasing(), SHELL.getMinimumCasings(), false)
                .addOtherStructurePart("I-Glass", "it is glass")
                .addOtherStructurePart(ImpactCasings.DDD_PRINTER_CASING4X4, "core structure", 16)
                .addStructureHint("Casing or compatible hatch", HintColor.GREEN)
                .addStructureInfo("Hatches replacing casings: at most 29")
                .addSubChannel(GTStructureUtility.HATCH_CHANNEL, "Installs maintenance, energy, input, output and muffler hatches from your inventory in the marked casing positions.")
                .toolTipFinisher();
    }

    @Override
    public GT_Recipe.GT_Recipe_Map getRecipeMap() {
        return GT_Recipe.GT_Recipe_Map.sBasicline;
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
