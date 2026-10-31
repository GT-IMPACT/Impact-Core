package com.impact.mods.gregtech.tileentities.multi.processing.defaultmachines;

import com.impact.addon.gt.api.recipe.MultiBlockRecipeBuilder;
import com.impact.mods.gregtech.blocks.ImpactCasings;
import com.impact.mods.gregtech.blocks.ImpactHatchElement;
import com.impact.mods.gregtech.enums.Texture;
import com.impact.mods.gregtech.gui.base.GUI_BASE;
import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_Impact_BlockBase;
import gregtech.api.enums.HatchElement;
import gregtech.api.interfaces.ICasing;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTStructureUtility;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.StructureRequirements;
import java.util.HashSet;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import space.impact.structures.hint.HintColor;
import space.impact.structures.structure.IStructureDefinition;
import space.impact.structures.structure.ISurvivalBuildEnvironment;
import space.impact.structures.structure.StructureDefinition;

import static gregtech.api.enums.GT_Values.E;
import static gregtech.api.enums.GT_Values.RES_PATH_GUI;
import static space.impact.structures.structure.StructureUtility.withHint;

public class GTMTE_RailAssembler extends GTMTE_Impact_BlockBase<GTMTE_RailAssembler> {

    public static final GT_Recipe.GT_Recipe_Map sTrackAssemblerRecipes = new GT_Recipe.GT_Recipe_Map(
            new HashSet<GT_Recipe>(1000),
            "impact.recipe.railassembler",
            "Rail Assembler",
            null,
            RES_PATH_GUI + "basic/RailAssembler",
            6, 1, 0, 0,
            1, E, 1, E, true, false);

    private static final ICasing CASING = ImpactCasings.RAILASSEMBLER_CASING;

    private static final StructureRequirements SHELL = StructureRequirements.builder(CASING, 0)
            .build();
    private static IStructureDefinition<GTMTE_RailAssembler> definition;

    public GTMTE_RailAssembler(int aID, String aNameRegional) {
        super(aID, "impact.multimachine.railassembler", aNameRegional);
    }

    public GTMTE_RailAssembler(String aName) {
        super(aName);
    }

    @Override
    public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide, final byte aFacing,
            final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
        return aSide == aFacing
                ? new ITexture[] { CASING.getTexture(), TextureFactory.of(
                        aActive ? Texture.Icons.OVERLAY_RAIL_ASSEMBLER_ACTIVE : Texture.Icons.OVERLAY_RAIL_ASSEMBLER) }
                : new ITexture[] { CASING.getTexture() };
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GTMTE_RailAssembler(this.mName);
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        return new MultiblockTooltipBuilder(mName)
                .addMachineType("Rail Assembler")
                .addController("See the structure hologram")
                .addOtherStructurePart("Energy Hatch", "Any casing")
                .addOtherStructurePart("Maintenance Hatch", "Any casing; maximum 1")
                .addOtherStructurePart("Input Bus", "Any casing; maximum 5")
                .addOtherStructurePart("Output Bus", "Any casing; maximum 1")
                .addOtherStructurePart("Energy Hatch", "Any casing; maximum 1")
                .addRequirements(SHELL, "Any casing position")
                .addStructureHint("Casing or compatible hatch", HintColor.GREEN)
                .toolTipFinisher();
    }

    @Override
    public GT_Recipe.GT_Recipe_Map getRecipeMap() {
        return sTrackAssemblerRecipes;
    }

    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GUI_BASE(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "MultiParallelBlockGUI.png");
    }

    @Override
    public boolean machineStructure(IGregTechTileEntity thisController) {
        boolean formationChecklist = checkPiece(0, 0, 3) && SHELL.isSatisfied(this, 0);

        if (this.mInputBusses.size() > 5) {
            formationChecklist = false;
        }
        if (this.mOutputBusses.size() > 1) {
            formationChecklist = false;
        }
        if (this.mEnergyHatches.size() > 1) {
            formationChecklist = false;
        }
        if (this.mMaintenanceHatches.size() > 1) {
            formationChecklist = false;
        }

        return formationChecklist;
    }

    @Override
    public void construct(ItemStack itemStack, boolean b) {
        buildPiece(itemStack, b, 0, 0, 3);
    }

    @Override
    public int survivalConstruct(ItemStack trigger, int budget,
            ISurvivalBuildEnvironment environment) {
        return survivalBuildPiece("main", trigger, 0, 0, 3, budget, environment);
    }

    @Override
    public IStructureDefinition<GTMTE_RailAssembler> getStructureDefinition() {
        if (definition == null) {
            definition = StructureDefinition.<GTMTE_RailAssembler>builder()
                    .addShape("main", new String[][] {
                            { " CC", " CC" },
                            { " CC", " CC" },
                            { " CC", " CC" },
                            { "~CC", "CCC" }
                    })
                    .addChannel(GTStructureUtility.HATCH_CHANNEL, 1, 1)
                    .addElement('C', withHint(GTStructureUtility.buildHatchAdder(GTMTE_RailAssembler.class)
                            .allow(HatchElement.Maintenance, HatchElement.Input, ImpactHatchElement.Muffler,
                                    HatchElement.Energy, ImpactHatchElement.LaserInput, HatchElement.Output)
                            .buildAndChain(SHELL), HintColor.GREEN))
                    .build();
        }
        return definition;
    }

    @Override
    public boolean checkRecipe(MultiBlockRecipeBuilder<?> recipeBuilder, int indexBus) {
        return recipeBuilder
                .checkSizeHatches(false, true, indexBus)
                .checkVoltage()
                .checkRecipeMap(indexBus)
                .checkInputEquals(indexBus, false)
                .checkEfficiency()
                .checkConsumption()
                .checkOutputs(true)
                .build();
    }
}
