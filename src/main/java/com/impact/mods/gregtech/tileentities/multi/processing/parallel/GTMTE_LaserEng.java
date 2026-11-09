package com.impact.mods.gregtech.tileentities.multi.processing.parallel;

import com.impact.mods.gregtech.blocks.HatchCounts;
import com.impact.util.ImpactMultiblockTooltipBuilder;
import com.impact.mods.gregtech.blocks.ImpactCasings;
import com.impact.mods.gregtech.blocks.ImpactHatchElement;
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
import net.minecraft.item.ItemStack;
import space.impact.structures.hint.HintColor;
import space.impact.structures.structure.IStructureDefinition;
import space.impact.structures.structure.ISurvivalBuildEnvironment;
import space.impact.structures.structure.StructureDefinition;

import static com.impact.loader.ItemRegistery.IGlassBlock;
import static space.impact.structures.structure.StructureUtility.ofBlock;
import static space.impact.structures.structure.StructureUtility.onElementPass;

public class GTMTE_LaserEng extends GT_MetaTileEntity_MultiParallelBlockBase<GTMTE_LaserEng> {

    private static final ICasing CASING = ImpactCasings.ENGRAVER_CASING;

    private int casingCount;

    private static final StructureRequirements SHELL = StructureRequirements.builder(CASING, 19)
            .atLeast(HatchElement.Maintenance, ImpactHatchElement.PowerInput).build();
    private static IStructureDefinition<GTMTE_LaserEng> definition;

    public GTMTE_LaserEng(int aID, String aNameRegional) {
        super(aID, "impact.multimachine.laserengraver", aNameRegional);
    }

    public GTMTE_LaserEng(String aName) {
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
    public void construct(ItemStack itemStack, boolean b) {
        buildPiece(itemStack, b, 1, 3, 0);
    }

    @Override
    public int survivalConstruct(ItemStack trigger, int budget,
            ISurvivalBuildEnvironment environment) {
        return survivalBuildPiece("main", trigger, 1, 3, 0, budget, environment);
    }

    @Override
    public IStructureDefinition<GTMTE_LaserEng> getStructureDefinition() {
        if (definition == null) {
            definition = StructureDefinition.<GTMTE_LaserEng>builder()
                    .addShape("main", new String[][] {
                            { "   ", "   ", "   ", "H~H", "HHH" },
                            { "HHH", "   ", "   ", "FEB", "HHH" },
                            { "HCH", " D ", "   ", "FEB", "HHH" },
                            { "HCH", "   ", "   ", "FEB", "HHH" },
                            { "HHH", "HHH", "HHH", "HHH", "HHH" }
                    })
                    .addChannel(GTStructureUtility.HATCH_CHANNEL, 1, 1)
                    .addElement('C', onElementPass(machine -> machine.casingCount++, CASING.asElement()))
                    .addElement('D', ofBlock(IGlassBlock, 0))
                    .addElement('B', ofBlock(IGlassBlock, 11))
                    .addElement('E', ofBlock(IGlassBlock, 13))
                    .addElement('F', ofBlock(IGlassBlock, 14))
                    .addElement('H', GTStructureUtility.buildHatchAdder(GTMTE_LaserEng.class)
                            .allow(HatchElement.Input, ImpactHatchElement.Muffler, HatchElement.Energy,
                                    ImpactHatchElement.LaserInput, ImpactHatchElement.Parallel, HatchElement.Output)
                            .autoPlace(ImpactHatchElement.PowerInput, HatchElement.Input, ImpactHatchElement.Muffler, ImpactHatchElement.Parallel, HatchElement.Output)
                            .buildAndChain(SHELL, machine -> machine.casingCount++).withHint(HintColor.GREEN))
                    .build();
        }
        return definition;
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GTMTE_LaserEng(this.mName);
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        return new ImpactMultiblockTooltipBuilder(mName)
                .useCompactStructure()
                .addMachineType("Precision Laser Engraver")
                .addParallelInfo()
                .beginStructureBlock(3, 5, 5, false)
                .addController("front center, layer 2 from the bottom")
                .addStructureSection("Components")
                .addStructurePartInfo(SHELL.getCasing(), "%s+ total; roof center and unused hatch positions", SHELL.getMinimumCasings())
                .addOtherStructurePart(new ItemStack(IGlassBlock, 1, 0), "exactly 1; center, layer 4 from the bottom")
                .addOtherStructurePart(new ItemStack(IGlassBlock, 1, 11), "exactly 3; right row, layer 2 from the bottom")
                .addOtherStructurePart(new ItemStack(IGlassBlock, 1, 13), "exactly 3; center row, layer 2 from the bottom")
                .addOtherStructurePart(new ItemStack(IGlassBlock, 1, 14), "exactly 3; left row, layer 2 from the bottom")
                .addStructureSection("Hatches and buses")
                .addStructureInfo("The 2 fixed roof casings cannot be replaced by hatches.")
                .addHatchInfo("Maintenance Hatch", "exactly 1; casing positions")
                .addHatchInfo("Energy Hatches", "1-4 total; casing positions")
                .addHatchInfo("Input Buses", "optional, up to 16; casing positions")
                .addHatchInfo("Output Buses", "optional, up to 3; casing positions")
                .addHatchInfo("Parallel Receiver", "optional, up to 1; casing positions")
                .addStructureHint("Casings or hatches/buses", HintColor.GREEN)
                .addHatchChannelInfo()
                .toolTipFinisher();
    }


    @Override
    public GT_Recipe.GT_Recipe_Map getRecipeMap() {
        return GT_Recipe.GT_Recipe_Map.sLaserEngraverRecipes;
    }

    @Override
    public boolean machineStructure(IGregTechTileEntity thisController) {
        casingCount = 0;
        boolean formationChecklist = checkPiece(1, 3, 0) && SHELL.isSatisfied(this, casingCount);

        if (HatchCounts.inputBuses(this) > 16) {
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
        return 0;
    }
}
