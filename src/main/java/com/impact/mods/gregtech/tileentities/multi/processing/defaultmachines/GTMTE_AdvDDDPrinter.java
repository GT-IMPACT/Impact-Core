package com.impact.mods.gregtech.tileentities.multi.processing.defaultmachines;

import com.impact.addon.gt.api.recipe.MultiBlockRecipeBuilder;
import com.impact.mods.gregtech.blocks.ImpactCasings;
import com.impact.mods.gregtech.blocks.ImpactHatchElement;
import com.impact.mods.gregtech.gui.base.GUI_BASE;
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
import net.minecraft.entity.player.InventoryPlayer;
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
    private static final StructureRequirements SHELL = StructureRequirements.builder(CASING, 0)
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
                            .buildAndChain(SHELL), HintColor.GREEN))
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
                .addInfo("Shift-click the controller with a screwdriver to switch separated buses")
                .addSeparator()
                .addController("See the structure hologram")
                .addOtherStructurePart("Energy Hatch", "Any casing; maximum 4")
                .addOtherStructurePart("Maintenance Hatch", "Any casing; maximum 1")
                .addOtherStructurePart("Input Bus", "Any casing; maximum 30")
                .addOtherStructurePart("Output Bus", "Any casing; maximum 3")
                .addRequirements(SHELL, "Any casing position")
                .addOtherStructurePart("I-Glass", "it is glass")
                .addOtherStructurePart("Configuration Casing 4x4", "core structure")
                .addStructureHint("Casing or compatible hatch", HintColor.GREEN)
                .toolTipFinisher();
    }

    @Override
    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GUI_BASE(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "MultiParallelBlockGUI.png");
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

    @Override
    public GT_Recipe.GT_Recipe_Map getRecipeMap() {
        return GT_Recipe.GT_Recipe_Map.sBasicline;
    }

    @Override
    public boolean machineStructure(IGregTechTileEntity thisController) {
        boolean formationChecklist = checkPiece(0, 1, 0) && SHELL.isSatisfied(this, 0);

        if (this.mInputBusses.size() > 30) {
            formationChecklist = false;
        }
        if (this.mOutputBusses.size() > 3) {
            formationChecklist = false;
        }
        if (this.mEnergyHatches.size() > 4) {
            formationChecklist = false;
        }
        if (this.mMaintenanceHatches.size() != 1) {
            formationChecklist = false;
        }

        return formationChecklist;
    }
}
