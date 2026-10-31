package com.impact.mods.gregtech.tileentities.multi.processing.parallel;

import com.impact.mods.gregtech.blocks.ImpactHatchElement;
import com.impact.mods.gregtech.gui.base.GUI_BASE;
import com.impact.mods.gregtech.tileentities.multi.implement.GT_MetaTileEntity_MultiParallelBlockBase;
import gregtech.api.enums.Casings;
import gregtech.api.enums.HatchElement;
import gregtech.api.enums.Textures.BlockIcons;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ICasing;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTStructureUtility;
import gregtech.api.util.GT_Recipe.GT_Recipe_Map;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.StructureRequirements;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import space.impact.structures.hint.HintColor;
import space.impact.structures.structure.IStructureDefinition;
import space.impact.structures.structure.ISurvivalBuildEnvironment;
import space.impact.structures.structure.StructureDefinition;

import static com.impact.loader.ItemRegistery.InsideBlock;
import static space.impact.structures.structure.StructureUtility.ofBlock;
import static space.impact.structures.structure.StructureUtility.withHint;

public class GTMTE_ElectromagneticInductionFurnace
        extends GT_MetaTileEntity_MultiParallelBlockBase<GTMTE_ElectromagneticInductionFurnace> {

    private static final ICasing CASING = Casings.HEAT_PROOF;

    private static final StructureRequirements SHELL = StructureRequirements.builder(CASING, 0)
            .atLeast(HatchElement.Maintenance, ImpactHatchElement.Muffler).build();
    private static IStructureDefinition<GTMTE_ElectromagneticInductionFurnace> definition;

    public GTMTE_ElectromagneticInductionFurnace(int aID, String aNameRegional) {
        super(aID, "impact.multis.eif", aNameRegional);
    }

    public GTMTE_ElectromagneticInductionFurnace(String aName) {
        super(aName);
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex,
            boolean aActive, boolean aRedstone) {
        return aSide == aFacing
                ? new ITexture[] { CASING.getTexture(),
                        TextureFactory.of(aActive ? BlockIcons.OVERLAY_FRONT_ELECTRIC_BLAST_FURNACE_ACTIVE
                                : Textures.BlockIcons.OVERLAY_FRONT_ELECTRIC_BLAST_FURNACE) }
                : new ITexture[] { CASING.getTexture() };
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GTMTE_ElectromagneticInductionFurnace(this.mName);
    }

    @Override
    public void construct(ItemStack itemStack, boolean b) {
        buildPiece(itemStack, b, 2, 4, 0);
    }

    @Override
    public int survivalConstruct(ItemStack trigger, int budget,
            ISurvivalBuildEnvironment environment) {
        return survivalBuildPiece("main", trigger, 2, 4, 0, budget, environment);
    }

    @Override
    public IStructureDefinition<GTMTE_ElectromagneticInductionFurnace> getStructureDefinition() {
        if (definition == null) {
            definition = StructureDefinition.<GTMTE_ElectromagneticInductionFurnace>builder()
                    .addShape("main", new String[][] {
                            { " BBB ", "  B  ", "  B  ", "  B  ", " B~B " },
                            { "BBBBB", " CCC ", " DDD ", " CCC ", "BBBBB" },
                            { "BBBBB", "BCCCB", "BDDDB", "BCCCB", "BBBBB" },
                            { "BBBBB", " CCC ", " DDD ", " CCC ", "BBBBB" },
                            { " BBB ", "  B  ", "  B  ", "  B  ", " BBB " }
                    })
                    .addChannel(GTStructureUtility.HATCH_CHANNEL, 1, 1)
                    .addElement('C', Casings.MAGNETIC_COIL.asElement())
                    .addElement('D', ofBlock(InsideBlock, 5))
                    .addElement('B',
                            withHint(GTStructureUtility.buildHatchAdder(GTMTE_ElectromagneticInductionFurnace.class)
                                    .allow(HatchElement.Input, HatchElement.Energy, ImpactHatchElement.LaserInput,
                                            ImpactHatchElement.Parallel, HatchElement.Output)
                                    .buildAndChain(SHELL), HintColor.GREEN))
                    .build();
        }
        return definition;
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        return new MultiblockTooltipBuilder(mName)
                .addInfo("Hey, its not EBF ha-ha!")
                .addInfo("Parallel points: base 1, maximum 256")
                .addMachineType("Electric Blast Furnace")
                .addInfo("Shift-click the controller with a screwdriver to switch separated buses")
                .addSeparator()
                .addController("See the structure hologram")
                .addOtherStructurePart("Energy Hatch", "Any casing; maximum 4")
                .addOtherStructurePart("Maintenance Hatch", "Any casing; maximum 1")
                .addOtherStructurePart("Muffler Hatch", "Any casing; maximum 1")
                .addOtherStructurePart("Input Hatch", "Any casing; maximum 8")
                .addOtherStructurePart("Output Hatch", "Any casing; maximum 3")
                .addOtherStructurePart("Input Bus", "Any casing; maximum 8")
                .addOtherStructurePart("Output Bus", "Any casing; maximum 3")
                .addOtherStructurePart("Parallel Hatch", "Any casing; maximum 1")
                .addOtherStructurePart("Electromagnetic Chamber", "middle center")
                .addOtherStructurePart("Magnetic Coil", "middle outside")
                .addRequirements(SHELL, "Any casing position")
                .addStructureHint("Casing or compatible hatch", HintColor.GREEN)
                .toolTipFinisher();
    }

    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GUI_BASE(aPlayerInventory, aBaseMetaTileEntity, "Electrom. Induction Furn.",
                "MultiParallelBlockGUI.png");
    }

    @Override
    public GT_Recipe.GT_Recipe_Map getRecipeMap() {
        return GT_Recipe_Map.sBlastRecipes;
    }

    @Override
    public boolean machineStructure(IGregTechTileEntity thisController) {
        boolean formationChecklist = checkPiece(2, 4, 0) && SHELL.isSatisfied(this, 0);

        if (this.mMufflerHatches.size() != 1) {
            formationChecklist = false;
        }
        if (this.mInputBusses.size() > 8) {
            formationChecklist = false;
        }
        if (this.mInputHatches.size() > 8) {
            formationChecklist = false;
        }
        if (this.mOutputBusses.size() > 3) {
            formationChecklist = false;
        }
        if (this.mOutputHatches.size() > 3) {
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

    @Override
    public int getPollutionPerTick(ItemStack aStack) {
        return 50;
    }
}
