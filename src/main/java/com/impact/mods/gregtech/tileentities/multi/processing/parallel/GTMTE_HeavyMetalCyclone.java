package com.impact.mods.gregtech.tileentities.multi.processing.parallel;

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
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import space.impact.structures.hint.HintColor;
import space.impact.structures.structure.IStructureDefinition;
import space.impact.structures.structure.ISurvivalBuildEnvironment;
import space.impact.structures.structure.StructureDefinition;

import static com.impact.loader.ItemRegistery.IGlassBlock;
import static com.impact.loader.ItemRegistery.InsideBlock;
import static space.impact.structures.structure.StructureUtility.ofBlock;
import static space.impact.structures.structure.StructureUtility.ofBlockAnyMeta;
import static space.impact.structures.structure.StructureUtility.withHint;

public class GTMTE_HeavyMetalCyclone extends GT_MetaTileEntity_MultiParallelBlockBase<GTMTE_HeavyMetalCyclone> {

    private static final ICasing CASING = ImpactCasings.CYCLONE_CASING;
    private static final StructureRequirements SHELL = StructureRequirements.builder(CASING, 0)
            .atLeast(HatchElement.Maintenance).build();
    private static IStructureDefinition<GTMTE_HeavyMetalCyclone> definition;

    public GTMTE_HeavyMetalCyclone(int aID, String aNameRegional) {
        super(aID, "impact.multimachine.heavymetalcyclone", aNameRegional);
    }

    public GTMTE_HeavyMetalCyclone(String aName) {
        super(aName);
    }

    @Override
    public void construct(ItemStack itemStack, boolean b) {
        buildPiece(itemStack, b, 3, 4, 1);
    }

    @Override
    public int survivalConstruct(ItemStack trigger, int budget,
            ISurvivalBuildEnvironment environment) {
        return survivalBuildPiece("main", trigger, 3, 4, 1, budget, environment);
    }

    @Override
    public IStructureDefinition<GTMTE_HeavyMetalCyclone> getStructureDefinition() {
        if (definition == null) {
            definition = StructureDefinition.<GTMTE_HeavyMetalCyclone>builder()
                    .addShape("main", new String[][] {
                            { "       ", "       ", "       ", "       ", "       ", "       ", " DDDDD " },
                            { "  BBB  ", " BBBBB ", "  BBB  ", "  BBB  ", "  B~B  ", " EEEEE ", "DDDDDDD" },
                            { " BBBBB ", " B C B ", " B C B ", " B C B ", " B C B ", " E C E ", "DDDDDDD" },
                            { " BBBBB ", " GC CG ", " GC CG ", " GC CG ", " GC CG ", " EC CE ", "DDDDDDD" },
                            { " BBBBB ", " B C B ", " B C B ", " B C B ", " B C B ", " E C E ", "DDDDDDD" },
                            { "  BBB  ", " BBGBB ", "  BGB  ", "  BGB  ", "  BGB  ", " EEEEE ", "DDDDDDD" },
                            { "       ", "       ", "       ", "       ", "       ", "       ", " DDDDD " }
                    })
                    .addChannel(GTStructureUtility.HATCH_CHANNEL, 1, 1)
                    .addElement('G', ofBlockAnyMeta(IGlassBlock))
                    .addElement('C', ofBlock(InsideBlock, 1))
                    .addElement('D', withHint(GTStructureUtility.buildHatchAdder(GTMTE_HeavyMetalCyclone.class)
                            .allow(HatchElement.Input, ImpactHatchElement.Muffler, HatchElement.Energy,
                                    ImpactHatchElement.LaserInput, ImpactHatchElement.Parallel, HatchElement.Output)
                            .buildAndChain(SHELL), HintColor.GREEN))
                    .addElement('B', withHint(GTStructureUtility.buildHatchAdder(GTMTE_HeavyMetalCyclone.class)
                            .allow(HatchElement.Input, ImpactHatchElement.Muffler, ImpactHatchElement.Parallel,
                                    HatchElement.Energy, ImpactHatchElement.LaserInput, HatchElement.Output)
                            .buildAndChain(SHELL), HintColor.GREEN))
                    .addElement('E', withHint(GTStructureUtility.buildHatchAdder(GTMTE_HeavyMetalCyclone.class)
                            .allow(HatchElement.Input, ImpactHatchElement.Parallel, ImpactHatchElement.Muffler,
                                    HatchElement.Energy, ImpactHatchElement.LaserInput, HatchElement.Output)
                            .buildAndChain(SHELL), HintColor.GREEN))
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
        return new GTMTE_HeavyMetalCyclone(this.mName);
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        return new MultiblockTooltipBuilder(mName)
                .addInfo("Centrifuge for heavy materials")
                .addInfo("Parallel points: base 1, maximum 256")
                .addMachineType("Heavy Metal Cyclone")
                .addSeparator()
                .addController("See the structure hologram")
                .addOtherStructurePart("Energy Hatch", "Any casing; maximum 3")
                .addOtherStructurePart("Maintenance Hatch", "Any casing; maximum 1")
                .addOtherStructurePart("Input Bus", "Any casing; maximum 4")
                .addOtherStructurePart("Input Hatch", "Any casing; maximum 6")
                .addOtherStructurePart("Output Bus", "Any casing; maximum 4")
                .addOtherStructurePart("Output Hatch", "Any casing; maximum 4")
                .addOtherStructurePart("Parallel Hatch", "Any casing; maximum 1")
                .addRequirements(SHELL, "Any casing position")
                .addOtherStructurePart("Cyclone Chamber Casing", "inside structure")
                .addOtherStructurePart("I-Glass", "glass for structure")
                .addStructureHint("Casing or compatible hatch", HintColor.GREEN)

                .toolTipFinisher();
    }

    @Override
    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GUI_BASE(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "MultiParallelBlockGUI.png");
    }

    @Override
    public GT_Recipe.GT_Recipe_Map getRecipeMap() {
        return GT_Recipe.GT_Recipe_Map.sCyclonRecipes;
    }

    @Override
    public boolean machineStructure(IGregTechTileEntity thisController) {
        boolean formationChecklist = checkPiece(3, 4, 1) && SHELL.isSatisfied(this, 0);

        if (this.mInputBusses.size() > 4) {
            formationChecklist = false;
        }
        if (this.mInputHatches.size() > 6) {
            formationChecklist = false;
        }
        if (this.mOutputBusses.size() > 4) {
            formationChecklist = false;
        }
        if (this.mOutputHatches.size() > 4) {
            formationChecklist = false;
        }
        if (this.mEnergyHatches.size() > 3) {
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
