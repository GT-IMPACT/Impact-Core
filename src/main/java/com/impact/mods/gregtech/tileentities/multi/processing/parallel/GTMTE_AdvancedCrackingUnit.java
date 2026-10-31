package com.impact.mods.gregtech.tileentities.multi.processing.parallel;

import com.impact.mods.gregtech.blocks.ImpactHatchElement;
import com.impact.mods.gregtech.gui.base.GUI_BASE;
import com.impact.mods.gregtech.tileentities.multi.implement.GT_MetaTileEntity_MultiParallelBlockBase;
import gregtech.api.enums.Casings;
import gregtech.api.enums.HatchElement;
import gregtech.api.enums.HeatingCoil;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ICasing;
import gregtech.api.interfaces.IHatchElement;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Muffler;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase;
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

import static space.impact.structures.structure.StructureUtility.withHint;

public class GTMTE_AdvancedCrackingUnit extends GT_MetaTileEntity_MultiParallelBlockBase<GTMTE_AdvancedCrackingUnit> {

    private static final ICasing CASING = Casings.CLEAN_STAINLESS_STEEL;
    private static final StructureRequirements MUFFLER_POSITION = StructureRequirements.builder(CASING, 0)
            .atLeast(TopMuffler.INSTANCE).build();
    private boolean topMufflerPresent;

    private static final StructureRequirements HATCHES_A = StructureRequirements.builder(CASING, 0)
            .atLeast(HatchElement.Maintenance).build();

    private static IStructureDefinition<GTMTE_AdvancedCrackingUnit> definition;

    public GTMTE_AdvancedCrackingUnit(int aID, String aNameRegional) {
        super(aID, "impact.multis.cracking_unit", aNameRegional);
    }

    public GTMTE_AdvancedCrackingUnit(String aName) {
        super(aName);
    }

    @Override
    public IStructureDefinition<GTMTE_AdvancedCrackingUnit> getStructureDefinition() {
        if (definition == null) {
            definition = StructureDefinition
                    .<GTMTE_AdvancedCrackingUnit>builder()
                    .addChannel(GTStructureUtility.HATCH_CHANNEL, 1, 1)
                    .addShape("main", new String[][] {
                            { "     ", " AAA ", "  A  ", "  A  ", "  A  ", " AAA ", " A~A " },
                            { " AAA ", "ABBBA", " BBB ", " BBB ", " BBB ", "ABBBA", "AAAAA" },
                            { " ADA ", "ABCBA", "ABCBA", "ABCBA", "ABCBA", "ABCBA", "AAAAA" },
                            { " AAA ", "ABBBA", " BBB ", " BBB ", " BBB ", "ABBBA", "AAAAA" },
                            { "     ", " AAA ", "  A  ", "  A  ", "  A  ", " AAA ", " AAA " }
                    })
                    .addElement('A', withHint(GTStructureUtility.buildHatchAdder(GTMTE_AdvancedCrackingUnit.class)
                            .allow(ImpactHatchElement.Parallel, HatchElement.Input, HatchElement.Output,
                                    HatchElement.Energy, ImpactHatchElement.Muffler, ImpactHatchElement.Dynamo,
                                    ImpactHatchElement.LaserInput, ImpactHatchElement.LaserOutput)
                            .buildAndChain(HATCHES_A), HintColor.GREEN))
                    .addElement('B', HeatingCoil.NAQUADAH.asElement())
                    .addElement('C', Casings.PTFE_PIPE.asElement())
                    .addElement('D',
                            GTStructureUtility.buildHatchAdder(GTMTE_AdvancedCrackingUnit.class).hint(HintColor.RED)
                                    .build(MUFFLER_POSITION))
                    .build();
        }
        return definition;
    }

    @Override
    public void construct(ItemStack itemStack, boolean b) {
        buildPiece(itemStack, b, 2, 6, 0);
    }

    @Override
    public int survivalConstruct(ItemStack trigger, int budget,
            ISurvivalBuildEnvironment environment) {
        return survivalBuildPiece("main", trigger, 2, 6, 0, budget, environment);
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex,
            boolean aActive, boolean aRedstone) {
        return aSide == aFacing ? new ITexture[] { CASING.getTexture(),
                TextureFactory.of(aActive ? Textures.BlockIcons.OVERLAY_FRONT_VACUUM_FREEZER_ACTIVE
                        : Textures.BlockIcons.OVERLAY_FRONT_VACUUM_FREEZER) }
                : new ITexture[] { CASING.getTexture() };

    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GTMTE_AdvancedCrackingUnit(this.mName);
    }

    protected IAlignmentLimits getInitialAlignmentLimits() {
        return (direction, rotation, flip) -> (direction.ordinal() > 1) && rotation.isNotRotated()
                && flip.isNotFlipped();
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        return new MultiblockTooltipBuilder(mName)
                .addInfo("Parallel points: base 1, maximum 256")
                .addMachineType("Cracking Unit")
                .addInfo("Shift-click the controller with a screwdriver to switch separated buses")
                .addSeparator()
                .addController("See the structure hologram")
                .addOtherStructurePart("Energy Hatch", "Any casing; maximum 4")
                .addOtherStructurePart("Maintenance Hatch", "Any casing; maximum 1")
                .addOtherStructurePart("Input Hatch", "Any casing; maximum 16")
                .addOtherStructurePart("Output Hatch", "Any casing; maximum 8")
                .addOtherStructurePart("Input Bus", "Any casing; maximum 8")
                .addOtherStructurePart("Output Bus", "Any casing; maximum 3")
                .addOtherStructurePart("Parallel Hatch", "Any casing; maximum 1")
                .addOtherStructurePart("Muffler Hatch", "Any casing; maximum 1")
                .addOtherStructurePart("Naquadah Coil", "around Pipe Machine Casing")
                .addOtherStructurePart("PTFE Pipe Machine Casing", "inside the hollow")
                .addCasingInfoMin(CASING, 10, false)
                .addStructureHint("Muffler Hatch", HintColor.RED)
                .addStructureHint("Casing or compatible hatch", HintColor.GREEN)
                .toolTipFinisher();
    }

    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GUI_BASE(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "MultiParallelBlockGUI.png");
    }

    @Override
    public GT_Recipe_Map getRecipeMap() {
        return GT_Recipe_Map.sCrakingRecipes;
    }

    @Override
    public boolean machineStructure(IGregTechTileEntity thisController) {

        topMufflerPresent = false;
        boolean formationChecklist = checkPiece(2, 6, 0);

        if (this.mInputBusses.size() > 8) {
            formationChecklist = false;
        }
        if (this.mInputHatches.size() > 16) {
            formationChecklist = false;
        }
        if (this.mOutputBusses.size() > 3) {
            formationChecklist = false;
        }
        if (this.mOutputHatches.size() > 8) {
            formationChecklist = false;
        }
        if (this.mEnergyHatches.size() > 4) {
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

    private enum TopMuffler implements IHatchElement {
        INSTANCE;

        @Override
        public boolean matches(IMetaTileEntity tile) {
            return tile instanceof GT_MetaTileEntity_Hatch_Muffler;
        }

        @Override
        public int count(GT_MetaTileEntity_MultiBlockBase machine) {
            return ((GTMTE_AdvancedCrackingUnit) machine).topMufflerPresent ? 1 : 0;
        }

        @Override
        public boolean add(GT_MetaTileEntity_MultiBlockBase machine, IGregTechTileEntity tile, int texture) {
            boolean added = machine.addMufflerToMachineList(tile, texture);
            ((GTMTE_AdvancedCrackingUnit) machine).topMufflerPresent = added;
            return added;
        }

        @Override
        public String getDescription() {
            return ImpactHatchElement.Muffler.getDescription();
        }
    }
}
