package com.impact.mods.gregtech.tileentities.multi.processing.parallel;

import com.google.common.collect.Lists;
import com.impact.mods.gregtech.GT_RecipeMaps;
import com.impact.mods.gregtech.blocks.ImpactCasings;
import com.impact.mods.gregtech.blocks.ImpactHatchElement;
import com.impact.mods.gregtech.gui.base.GUI_BASE;
import com.impact.mods.gregtech.tileentities.multi.implement.GT_MetaTileEntity_MultiParallelBlockBase;
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
import java.util.List;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import space.impact.structures.hint.HintColor;
import space.impact.structures.structure.IStructureDefinition;
import space.impact.structures.structure.ISurvivalBuildEnvironment;
import space.impact.structures.structure.StructureDefinition;

import static space.impact.structures.structure.StructureUtility.withHint;

public class GTMTE_ArcFurnace extends GT_MetaTileEntity_MultiParallelBlockBase<GTMTE_ArcFurnace> {

    private static final ICasing CASING = ImpactCasings.ARC_CASING;

    private static final StructureRequirements SHELL = StructureRequirements.builder(CASING, 0)
            .atLeast(HatchElement.Maintenance, ImpactHatchElement.Muffler).build();
    private static IStructureDefinition<GTMTE_ArcFurnace> definition;

    public GTMTE_ArcFurnace(int aID, String aNameRegional) {
        super(aID, "impact.multimachine.arcfurnace", aNameRegional);
    }

    public GTMTE_ArcFurnace(String aName) {
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
        return new GTMTE_ArcFurnace(this.mName);
    }

    @Override
    public void construct(ItemStack itemStack, boolean b) {
        buildPiece(itemStack, b, 2, 3, 0);
    }

    @Override
    public int survivalConstruct(ItemStack trigger, int budget,
            ISurvivalBuildEnvironment environment) {
        return survivalBuildPiece("main", trigger, 2, 3, 0, budget, environment);
    }

    @Override
    public IStructureDefinition<GTMTE_ArcFurnace> getStructureDefinition() {
        if (definition == null) {
            definition = StructureDefinition.<GTMTE_ArcFurnace>builder()
                    .addShape("main", new String[][] {
                            { "     ", " CCC ", " CCC ", "CC~CC" },
                            { " CCC ", "C   C", "C   C", "CCCCC" },
                            { " CCC ", "C   C", "C   C", "CCCCC" },
                            { " CCC ", "C   C", "C   C", "CCCCC" },
                            { "     ", " CCC ", " CCC ", "CCCCC" }
                    })
                    .addChannel(GTStructureUtility.HATCH_CHANNEL, 1, 1)
                    .addElement('C', withHint(GTStructureUtility.buildHatchAdder(GTMTE_ArcFurnace.class)
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
                .addInfo("Single-block machine equivalent")
                .addInfo("Parallel points: base 1, maximum 256")
                .addInfo("Pollution: 200 to 12800")
                .addMachineType("Arc Furnace, Alloy Smelter, Drying Oven")
                .addInfo("Select machine type by clicking the controller with a screwdriver")
                .addInfo("Shift-click the controller with a screwdriver to switch separated buses")
                .addSeparator()
                .addController("See the structure hologram")
                .addOtherStructurePart("Energy Hatch", "Any casing; maximum 4")
                .addOtherStructurePart("Maintenance Hatch", "Any casing; maximum 1")
                .addOtherStructurePart("Input Bus", "Any casing; maximum 6")
                .addOtherStructurePart("Output Bus", "Any casing; maximum 1")
                .addOtherStructurePart("Input Hatch", "Any casing; maximum 3")
                .addOtherStructurePart("Output Hatch", "Any casing; maximum 1")
                .addOtherStructurePart("Muffler Hatch", "Any casing; maximum 1")
                .addOtherStructurePart("Parallel Hatch", "Any casing; maximum 1")
                .addRequirements(SHELL, "Any casing position")
                .addStructureHint("Casing or compatible hatch", HintColor.GREEN)
                .toolTipFinisher();
    }

    @Override
    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GUI_BASE(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "MultiParallelBlockGUI.png");
    }

    @NotNull
    @Override
    public List<GT_Recipe.GT_Recipe_Map> getRecipesMap() {
        return Lists.newArrayList(
                GT_Recipe.GT_Recipe_Map.sArcFurnaceRecipes,
                GT_Recipe.GT_Recipe_Map.sAlloySmelterRecipes,
                GT_RecipeMaps.sDryingOven);
    }

    @Override
    public boolean hasSwitchMap() {
        return true;
    }

    @Override
    public boolean machineStructure(IGregTechTileEntity thisController) {
        boolean formationChecklist = checkPiece(2, 3, 0) && SHELL.isSatisfied(this, 0);

        if (this.mInputBusses.size() > 6) {
            formationChecklist = false;
        }
        if (this.mInputHatches.size() > 3) {
            formationChecklist = false;
        }
        if (this.mOutputBusses.size() > 1) {
            formationChecklist = false;
        }
        if (this.mOutputHatches.size() > 3) {
            formationChecklist = false;
        }
        if (this.mMufflerHatches.size() != 1) {
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
        return 200;
    }
}
