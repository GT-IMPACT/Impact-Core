package com.impact.mods.gregtech.tileentities.multi.processing.parallel;

import com.impact.mods.gregtech.blocks.HatchCounts;
import com.impact.mods.gregtech.blocks.ImpactHatchElement;
import com.impact.mods.gregtech.gui.base.GUI_BASE;
import com.impact.mods.gregtech.tileentities.multi.implement.GT_MetaTileEntity_MultiParallelBlockBase;
import gregtech.api.enums.Casings;
import gregtech.api.enums.HatchElement;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ICasing;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTStructureUtility;
import gregtech.api.util.GT_Recipe.GT_Recipe_Map;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.StructureRequirements;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import space.impact.structures.alignment.IAlignmentLimits;
import space.impact.structures.hint.HintColor;
import space.impact.structures.structure.IStructureDefinition;
import space.impact.structures.structure.ISurvivalBuildEnvironment;
import space.impact.structures.structure.StructureDefinition;

import static com.impact.loader.ItemRegistery.IGlassBlock;
import static space.impact.structures.structure.StructureUtility.ofBlockAnyMeta;
import static space.impact.structures.structure.StructureUtility.withHint;

public class GTMTE_MultiChemicalReactor extends GT_MetaTileEntity_MultiParallelBlockBase<GTMTE_MultiChemicalReactor> {

    private static final ICasing CASING = Casings.CHEMICALLY_INERT;

    private int casingCount;

    private static final StructureRequirements SHELL = StructureRequirements.builder(CASING, 33)
            .atLeast(HatchElement.Maintenance).build();
    private static IStructureDefinition<GTMTE_MultiChemicalReactor> definition;

    public GTMTE_MultiChemicalReactor(int aID, String aNameRegional) {
        super(aID, "impact.multis.chemicalreactor", aNameRegional);
    }

    public GTMTE_MultiChemicalReactor(String aName) {
        super(aName);
    }

    @Override
    public IStructureDefinition<GTMTE_MultiChemicalReactor> getStructureDefinition() {
        if (definition == null) {
            definition = StructureDefinition.<GTMTE_MultiChemicalReactor>builder()
                    .addShape("main", new String[][] {
                            { " DDD ", " DGD ", " DGD ", " DGD ", " DDD " },
                            { "DDDDD", "DCCCD", "DCCCD", "DCCCD", "DDDDD" },
                            { "DD~DD", "GC CG", "GC CG", "GC CG", "DDDDD" },
                            { "DDDDD", "DCCCD", "DCCCD", "DCCCD", "DDDDD" },
                            { " DDD ", " DGD ", " DGD ", " DGD ", " DDD " }
                    })
                    .addChannel(GTStructureUtility.HATCH_CHANNEL, 1, 1)
                    .addElement('G', ofBlockAnyMeta(IGlassBlock))
                    .addElement('C', Casings.PTFE_PIPE.asElement())
                    .addElement('D', withHint(GTStructureUtility.buildHatchAdder(GTMTE_MultiChemicalReactor.class)
                            .allow(HatchElement.Input, ImpactHatchElement.Muffler, HatchElement.Energy,
                                    ImpactHatchElement.LaserInput, ImpactHatchElement.Parallel, HatchElement.Output)
                            .buildAndChain(SHELL, machine -> machine.casingCount++), HintColor.GREEN))
                    .build();
        }
        return definition;
    }

    @Override
    public void construct(ItemStack itemStack, boolean b) {
        buildPiece(itemStack, b, 2, 0, 2);
    }

    @Override
    public int survivalConstruct(ItemStack trigger, int budget,
            ISurvivalBuildEnvironment environment) {
        return survivalBuildPiece("main", trigger, 2, 0, 2, budget, environment);
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex,
            boolean aActive, boolean aRedstone) {
        if (aSide == 1) {
            return new ITexture[] { CASING.getTexture(),
                    TextureFactory.of(aActive ? Textures.BlockIcons.OVERLAY_FRONT_LARGE_CHEMICAL_REACTOR_ACTIVE
                            : Textures.BlockIcons.OVERLAY_FRONT_LARGE_CHEMICAL_REACTOR) };
        }
        return new ITexture[] { CASING.getTexture() };
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GTMTE_MultiChemicalReactor(this.mName);
    }

    protected IAlignmentLimits getInitialAlignmentLimits() {
        return (direction, rotation, flip) -> (direction.ordinal() > 1) && rotation.isNotRotated()
                && flip.isNotFlipped();
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        return new MultiblockTooltipBuilder(mName)
                .addInfo("Single-block machine equivalent")
                .addInfo("Parallel points: base 1, maximum 256")
                .addMachineType("Chemical Reactor")
                .addSeparator()
                .addController("See the structure hologram")
                .addOtherStructurePart("Energy Hatch", "Any casing; maximum 4")
                .addOtherStructurePart("Maintenance Hatch", "Any casing position; exactly 1")
                .addOtherStructurePart("Input Hatch", "Any casing; maximum 16")
                .addOtherStructurePart("Output Hatch", "Any casing; maximum 8")
                .addOtherStructurePart("Input Bus", "Any casing; maximum 8")
                .addOtherStructurePart("Output Bus", "Any casing; maximum 3")
                .addOtherStructurePart("Parallel Hatch", "Any casing; maximum 1")
                .addOtherStructurePart(Casings.PTFE_PIPE, "inside the hollow", 24)
                .addCasingInfoMin(SHELL.getCasing(), SHELL.getMinimumCasings(), false)
                .addStructureHint("Casing or compatible hatch", HintColor.GREEN)
                .addStructureInfo("Hatches replacing casings: at most 32")
                .addOtherStructurePart(new ItemStack(IGlassBlock, 1, 0), "See the structure hologram", 12)
                .toolTipFinisher();
    }

    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GUI_BASE(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "MultiParallelBlockGUI.png");
    }

    @Override
    public GT_Recipe_Map getRecipeMap() {
        return GT_Recipe_Map.sMultiblockChemicalRecipes;
    }

    @Override
    public boolean machineStructure(IGregTechTileEntity thisController) {
        casingCount = 0;
        boolean formationChecklist = checkPiece(2, 0, 2) && SHELL.isSatisfied(this, casingCount);

        if (HatchCounts.inputBuses(this) > 8) {
            formationChecklist = false;
        }
        if (HatchCounts.inputHatches(this) > 16) {
            formationChecklist = false;
        }
        if (this.mOutputBusses.size() > 3) {
            formationChecklist = false;
        }
        if (this.mOutputHatches.size() > 8) {
            formationChecklist = false;
        }
        if (HatchCounts.energyHatches(this) > 4) {
            formationChecklist = false;
        }
        if (this.mMaintenanceHatches.size() != 1) {
            formationChecklist = false;
        }
        if (this.sParallHatchesIn.size() > 1) {
            formationChecklist = false;
        }
        if (this.sParallHatchesOut.size() != 0) {
            formationChecklist = false;
        }
        return formationChecklist;
    }
}
