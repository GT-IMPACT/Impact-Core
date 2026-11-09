package com.impact.mods.gregtech.tileentities.multi.units;

import gregtech.api.logic.ProcessingLogic;
import com.impact.mods.gregtech.blocks.HatchCounts;
import com.impact.mods.gregtech.blocks.ImpactHatchElement;
import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_Impact_BlockBase;
import com.impact.util.ImpactMultiblockTooltipBuilder;
import gregtech.api.enums.Casings;
import gregtech.api.enums.Dyes;
import gregtech.api.enums.HatchElement;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Energy;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Input;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_InputBus;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Output;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_OutputBus;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTStructureUtility;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_TieredMachineBlock;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.StructureRequirements;
import ic2.core.Ic2Items;
import ic2.core.util.StackUtil;
import net.minecraft.item.ItemStack;
import space.impact.structures.alignment.IAlignmentLimits;
import space.impact.structures.hint.HintColor;
import space.impact.structures.structure.IStructureDefinition;
import space.impact.structures.structure.ISurvivalBuildEnvironment;
import space.impact.structures.structure.StructureDefinition;

import static gregtech.api.util.GT_Recipe.GT_Recipe_Map.sTinyWormHoleRecipes;
import static space.impact.structures.structure.StructureUtility.ofBlockAnyMeta;
import static space.impact.structures.structure.StructureUtility.onElementPass;
import static space.impact.structures.structure.StructureUtility.withHint;

public class GTMTE_TinyWormHole extends GTMTE_Impact_BlockBase<GTMTE_TinyWormHole> {

    private int casingCount;
    private int intermixCount;

    private static final StructureRequirements HATCHES_G = StructureRequirements
            .builder(Casings.ROBUST_NAQUADAH_ALLOY, 45).build();
    private static final StructureRequirements HATCHES_J = StructureRequirements.builder(Casings.INTERMIX_CHAMBER, 8)
            .build();

    public GTMTE_TinyWormHole(int aID, String aNameRegional) {
        super(aID, "impact.multis.tinywormhole", aNameRegional);
    }

    public GTMTE_TinyWormHole(String aName) {
        super(aName);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GTMTE_TinyWormHole(mName);
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex,
            boolean aActive, boolean aRedstone) {
        ITexture[] sTexture;
        if (aSide == aFacing) {
            sTexture = new ITexture[] {
                    TextureFactory.of(Textures.BlockIcons.MACHINE_CASING_FUSION_GLASS,
                            Dyes.getModulation(-1, Dyes._NULL.mRGBa)),
                    TextureFactory.of(Textures.BlockIcons.OVERLAY_FUSION1) };
        } else {
            if (!aActive) {
                sTexture = new ITexture[] { TextureFactory.of(Textures.BlockIcons.MACHINE_CASING_FUSION_GLASS,
                        Dyes.getModulation(-1, Dyes._NULL.mRGBa)) };
            } else {
                sTexture = new ITexture[] { TextureFactory.of(Textures.BlockIcons.MACHINE_CASING_FUSION_GLASS_YELLOW,
                        Dyes.getModulation(-1, Dyes._NULL.mRGBa)) };
            }
        }
        return sTexture;
    }


    @Override
    public boolean isCorrectMachinePart(ItemStack aStack) {
        return true;
    }

    @Override
    protected IAlignmentLimits getInitialAlignmentLimits() {
        return (direction, rotation, flip) -> (direction.ordinal() > 1) && rotation.isNotRotated()
                && flip.isNotFlipped();
    }

    @Override
    public GT_Recipe.GT_Recipe_Map getRecipeMap() {
        return sTinyWormHoleRecipes;
    }

    private static IStructureDefinition<GTMTE_TinyWormHole> structureDefinition;

    @Override
    public IStructureDefinition<GTMTE_TinyWormHole> getStructureDefinition() {
        if (structureDefinition == null) {

            structureDefinition = StructureDefinition.<GTMTE_TinyWormHole>builder()
                    .addChannel(GTStructureUtility.HATCH_CHANNEL, 1, 1)
                    .addShape("main", new String[][] {
                            { "FFFFF", "FAAAF", "FAAAF", "FAJAF", "FAAAF", "FAAAF", "GG~GG" },
                            { "FFFFF", "ABBBA", "ADDDA", "ADCDA", "ADDDA", "ABBBA", "GGGGG" },
                            { "FFFFF", "ABBBA", "ADCDA", "JCECJ", "ADCDA", "ABBBA", "GGGGG" },
                            { "FFFFF", "ABBBA", "ADDDA", "ADCDA", "ADDDA", "ABBBA", "GGGGG" },
                            { "FFFFF", "FAAAF", "FAAAF", "FAJAF", "FAAAF", "FAAAF", "GGGGG" }
                    })
                    .addElement('A',
                            ofBlockAnyMeta(StackUtil.getBlock(Ic2Items.reinforcedGlass)))
                    .addElement('B', Casings.FUSION_MK_II.asElement())
                    .addElement('C', onElementPass(machine -> machine.intermixCount++, Casings.INTERMIX_CHAMBER.asElement()))
                    .addElement('D', Casings.DYSON_RING.asElement())
                    .addElement('E', Casings.CORE_CHAMBER.asElement())
                    .addElement('F', onElementPass(machine -> machine.casingCount++, Casings.ROBUST_NAQUADAH_ALLOY.asElement()))
                    .addElement('G', withHint(GTStructureUtility.buildHatchAdder(GTMTE_TinyWormHole.class)
                            .allow(HatchElement.Input, HatchElement.Output, HatchElement.Maintenance,
                                    HatchElement.Energy,
                                    ImpactHatchElement.Muffler, ImpactHatchElement.Dynamo,
                                    ImpactHatchElement.LaserInput,
                                    ImpactHatchElement.LaserOutput)
                            .autoPlace(ImpactHatchElement.PowerOutput, ImpactHatchElement.PowerInput, HatchElement.Input, HatchElement.Output, HatchElement.Maintenance, ImpactHatchElement.Muffler)
                            .onlyAcceptIf(hatch -> !(hatch instanceof GT_MetaTileEntity_TieredMachineBlock)
                                    || HatchElement.Maintenance.matches(hatch) || ImpactHatchElement.Muffler.matches(hatch)
                                    || ((GT_MetaTileEntity_TieredMachineBlock) hatch).mTier >= 8)
                            .buildAndChain(HATCHES_G, machine -> machine.casingCount++), HintColor.GREEN))
                    .addElement('J', withHint(GTStructureUtility.buildHatchAdder(GTMTE_TinyWormHole.class)
                            .allow(HatchElement.Input)
                            .autoPlace(HatchElement.Input)
                            .onlyAcceptIf(hatch -> !(hatch instanceof GT_MetaTileEntity_TieredMachineBlock)
                                    || HatchElement.Maintenance.matches(hatch) || ImpactHatchElement.Muffler.matches(hatch)
                                    || ((GT_MetaTileEntity_TieredMachineBlock) hatch).mTier >= 8)
                            .buildAndChain(HATCHES_J, machine -> machine.intermixCount++), HintColor.BLUE))
                    .build();

        }
        return structureDefinition;
    }

    @Override
    public boolean machineStructure(IGregTechTileEntity thisController) {
        casingCount = 0;
        intermixCount = 0;
        boolean check = checkPiece(2, 6, 0)
                && HATCHES_G.isSatisfied(this, casingCount)
                && HATCHES_J.isSatisfied(this, intermixCount)
                && mMaintenanceHatches.size() <= 1;

        for (GT_MetaTileEntity_Hatch_Energy mEnergyHatch : this.mEnergyHatches) {
            if (mEnergyHatch.mTier < 8) {
                check = false;
                break;
            }
        }

        for (GT_MetaTileEntity_Hatch_InputBus mInputBuss : this.mInputBusses) {
            if (mInputBuss.mTier < 8) {
                check = false;
                break;
            }
        }

        for (GT_MetaTileEntity_Hatch_OutputBus mOutputBuss : this.mOutputBusses) {
            if (mOutputBuss.mTier < 8) {
                check = false;
                break;
            }
        }

        for (GT_MetaTileEntity_Hatch_Input mInputHatch : this.mInputHatches) {
            if (mInputHatch.mTier < 8) {
                check = false;
                break;
            }
        }

        for (GT_MetaTileEntity_Hatch_Output mOutputHatch : this.mOutputHatches) {
            if (mOutputHatch.mTier < 8) {
                check = false;
                break;
            }
        }

        if (HatchCounts.inputHatches(this) > 1)
            check = false;
        if (mOutputHatches.size() > 1)
            check = false;
        if (HatchCounts.inputBuses(this) > 1)
            check = false;
        if (mOutputBusses.size() > 1)
            check = false;
        if (HatchCounts.energyHatches(this) > 2)
            check = false;

        check &= HatchCounts.inputsAtLeastTier(this, 8);

        return check;
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        return new ImpactMultiblockTooltipBuilder(mName)
                .useCompactStructure()
                .addInfo("Tiny WORMHOLE on The Earth!!!")
                .addMachineType("Wormhole")
                .beginStructureBlock(5, 7, 5, false)
                .addController("front center of the bottom layer")
                .addStructureSection("Components")
                .addStructurePartInfo(Casings.ROBUST_NAQUADAH_ALLOY, "%s fixed in the roof and corner columns; fill unused bottom positions", HATCHES_G.getMinimumCasings())
                .addStructurePartInfo(Casings.INTERMIX_CHAMBER, "%s+; around the core and unused side-center input positions", HATCHES_J.getMinimumCasings())
                .addOtherStructurePart(Casings.DYSON_RING, "exactly 20; inner cage around the core")
                .addOtherStructurePart(Casings.FUSION_MK_II, "exactly 18; inner 3x3 plates in layers 2 and 6")
                .addOtherStructurePart(Casings.CORE_CHAMBER, "exactly 1; center of layer 4")
                .addOtherStructurePart(Ic2Items.reinforcedGlass, "exactly 56; side walls, except corners and side-center inputs")
                .addStructureSection("Hatches and buses")
                .addHatchInfo("Input Hatch", "optional, up to 1; UV+; bottom layer or side centers in layer 4")
                .addHatchInfo("Input Bus", "optional, up to 1; UV+; bottom layer or side centers in layer 4")
                .addHatchInfo("Output Hatch", "optional, up to 1; UV+; bottom layer")
                .addHatchInfo("Output Bus", "optional, up to 1; UV+; bottom layer")
                .addHatchInfo("Energy Hatches", "optional, up to 2 total; UV+; bottom layer")
                .addHatchInfo("Maintenance Hatch", "optional, up to 1; bottom layer")
                .addStructureHint("Bottom: casings or hatches/buses", HintColor.GREEN)
                .addStructureHint("Side centers: casings or input hatches/buses", HintColor.BLUE)
                .addHatchChannelInfo()
                .toolTipFinisher();
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
    public int getSupportedProcessingModes() {
        return super.getSupportedProcessingModes() & ~ProcessingLogic.SEPARATE_INPUTS;
    }
}
