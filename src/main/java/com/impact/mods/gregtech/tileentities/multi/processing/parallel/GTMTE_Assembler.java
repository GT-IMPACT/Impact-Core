package com.impact.mods.gregtech.tileentities.multi.processing.parallel;

import com.google.common.collect.Lists;
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

import static com.impact.loader.ItemRegistery.IGlassBlock;
import static space.impact.structures.structure.StructureUtility.ofBlockAnyMeta;
import static space.impact.structures.structure.StructureUtility.withHint;

public class GTMTE_Assembler extends GT_MetaTileEntity_MultiParallelBlockBase<GTMTE_Assembler> {

    public int mLevel = 0;

    private static final ICasing CASING = ImpactCasings.ASSEMBLER_CASING;

    private static final StructureRequirements SHELL = StructureRequirements.builder(CASING, 0)
            .atLeast(HatchElement.Maintenance).build();
    private static IStructureDefinition<GTMTE_Assembler> definition;

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        return new MultiblockTooltipBuilder(mName)
                .addInfo("Single-block machine equivalent")
                .addInfo("Parallel points: base 1, maximum 256")
                .addMachineType("Assembler, Circuit Assembler, Component Assembler")
                .addInfo("Select machine type by clicking the controller with a screwdriver")
                .addInfo("Shift-click the controller with a screwdriver to switch separated buses")
                .addSeparator()
                .addController("See the structure hologram")
                .addOtherStructurePart("Energy Hatch", "Any casing; maximum 4")
                .addOtherStructurePart("Maintenance Hatch", "Any casing; maximum 1")
                .addOtherStructurePart("Input Bus", "Any casing; maximum 20")
                .addOtherStructurePart("Input Hatch", "Any casing; maximum 6")
                .addOtherStructurePart("Output Bus", "Any casing; maximum 3")
                .addOtherStructurePart("Parallel Hatch", "Any casing; maximum 1")
                .addRequirements(SHELL, "Any casing position")
                .addStructureHint("Casing or compatible hatch", HintColor.GREEN)
                .toolTipFinisher();
    }

    @Override
    public IStructureDefinition<GTMTE_Assembler> getStructureDefinition() {
        if (definition == null) {
            definition = StructureDefinition.<GTMTE_Assembler>builder()
                    .addShape("main", new String[][] {
                            { "CGGGC   C", "CGGGCC~CC", "CCCCCCCCC" },
                            { "CGGGCCCCC", "C       C", "CCCCCCCCC" },
                            { "CCCCCCCCC", "CCCCCCCCC", "CCCCCCCCC" }
                    })
                    .addChannel(GTStructureUtility.HATCH_CHANNEL, 1, 1)
                    .addElement('G', ofBlockAnyMeta(IGlassBlock))
                    .addElement('C', withHint(GTStructureUtility.buildHatchAdder(GTMTE_Assembler.class)
                            .allow(HatchElement.Input, ImpactHatchElement.Parallel, HatchElement.Energy,
                                    ImpactHatchElement.LaserInput, HatchElement.Output)
                            .buildAndChain(SHELL), HintColor.GREEN))
                    .build();
        }
        return definition;
    }

    @Override
    public void construct(ItemStack itemStack, boolean b) {
        buildPiece(itemStack, b, 6, 1, 0);
    }

    @Override
    public int survivalConstruct(ItemStack trigger, int budget,
            ISurvivalBuildEnvironment environment) {
        return survivalBuildPiece("main", trigger, 6, 1, 0, budget, environment);
    }

    public GTMTE_Assembler(int aID, String aNameRegional) {
        super(aID, "impact.multimachine.assembler", aNameRegional);
    }

    public GTMTE_Assembler(String aName) {
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
        return new GTMTE_Assembler(this.mName);
    }

    @Override
    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GUI_BASE(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "MultiParallelBlockGUI.png");
    }

    @NotNull
    @Override
    public List<GT_Recipe.GT_Recipe_Map> getRecipesMap() {
        return Lists.newArrayList(
                GT_Recipe.GT_Recipe_Map.sCircuitAssemblerRecipes,
                GT_Recipe.GT_Recipe_Map.sComponentAssemblerRecipes,
                GT_Recipe.GT_Recipe_Map.sAssemblerRecipes);
    }

    @Override
    public boolean hasSwitchMap() {
        return true;
    }

    @Override
    public boolean machineStructure(IGregTechTileEntity thisController) {
        boolean formationChecklist = checkPiece(6, 1, 0) && SHELL.isSatisfied(this, 0);

        setParallel(this.mLevel);

        if (this.mInputBusses.size() > 20) {
            formationChecklist = false;
        }
        if (this.mInputHatches.size() > 6) {
            formationChecklist = false;
        }
        if (this.mOutputBusses.size() > 3) {
            formationChecklist = false;
        }
        if (this.mMufflerHatches.size() != 0) {
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
}
