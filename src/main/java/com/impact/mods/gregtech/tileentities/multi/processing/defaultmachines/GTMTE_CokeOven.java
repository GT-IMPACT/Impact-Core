package com.impact.mods.gregtech.tileentities.multi.processing.defaultmachines;

import com.impact.mods.gregtech.blocks.ImpactHatchElement;
import com.impact.util.ImpactMultiblockTooltipBuilder;
import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_PrimitiveBlockBase;
import gregtech.api.logic.ProcessingLogic;
import gregtech.api.enums.Casings;
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
import gregtech.common.GT_Pollution;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.ChunkPosition;
import space.impact.structures.alignment.IAlignmentLimits;
import space.impact.structures.hint.HintColor;
import space.impact.structures.structure.IStructureDefinition;
import space.impact.structures.structure.ISurvivalBuildEnvironment;
import space.impact.structures.structure.StructureDefinition;


public class GTMTE_CokeOven extends GTMTE_PrimitiveBlockBase<GTMTE_CokeOven> {
    private static final ICasing CASING = Casings.COKE_OVEN_BRICKS;
    private static final StructureRequirements SHELL = StructureRequirements.builder(CASING, 22).build();
    private int casingCount;

    private static IStructureDefinition<GTMTE_CokeOven> STRUCTURE;

    @Override
    public IStructureDefinition<GTMTE_CokeOven> getStructureDefinition() {
        if (STRUCTURE == null) {
            STRUCTURE = StructureDefinition.<GTMTE_CokeOven>builder()
                    .addShape("main", new String[][] {
                            { "CCC", "C~C", "CCC" },
                            { "CCC", "C-C", "CCC" },
                            { "CCC", "CCC", "CCC" },
                    })
                    .addChannel(GTStructureUtility.HATCH_CHANNEL, 1, 1)
                    .addElement('C', GTStructureUtility.buildHatchAdder(GTMTE_CokeOven.class)
                            .allow(ImpactHatchElement.PrimitiveInput, ImpactHatchElement.PrimitiveOutput)
                            .autoPlace(ImpactHatchElement.PrimitiveInput, ImpactHatchElement.PrimitiveOutput)
                            .buildAndChain(SHELL, machine -> machine.casingCount++).withHint(HintColor.GREEN))
                    .build();
        }
        return STRUCTURE;
    }

    @Override
    public void construct(ItemStack trigger, boolean hintsOnly) {
        buildPiece("main", trigger, hintsOnly, 1, 1, 0);
    }

    @Override
    public int survivalConstruct(ItemStack trigger, int budget,
            ISurvivalBuildEnvironment environment) {
        return survivalBuildPiece("main", trigger, 1, 1, 0, budget, environment);
    }


    public GTMTE_CokeOven(int aID, String aNameRegional) {
        super(aID, "impact.multimachine.cokeoven", aNameRegional);
    }

    public GTMTE_CokeOven(String aName) {
        super(aName);
    }

    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GTMTE_CokeOven(this.mName);
    }

    protected MultiblockTooltipBuilder createTooltip() {
        return new ImpactMultiblockTooltipBuilder(mName)
                .useCompactStructure()
                .addMachineType("Coke Oven")
                .addInfo("Pollution: 25 per second.")
                .beginStructureBlock(3, 3, 3, false)
                .addController("front center, middle layer")
                .addStructureSection("Components")
                .addStructurePartInfo(SHELL.getCasing(), "%s+; fill unused casing positions", SHELL.getMinimumCasings())
                .addAir(1, "center of the structure")
                .addStructureSection("Hatches and buses")
                .addHatchInfo("Primitive Input Bus", "optional, up to 1; casing positions")
                .addHatchInfo("Primitive Output Bus", "optional, up to 1; casing positions")
                .addHatchInfo("Primitive Output Hatch", "optional, up to 1; casing positions")
                .addStructureHint("Casings or hatches/buses", HintColor.GREEN)
                .addHatchChannelInfo()
                .toolTipFinisher();
    }

    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing,
            byte aColorIndex, boolean aActive, boolean aRedstone) {
        if (aSide == aFacing) {
            return new ITexture[] { CASING.getTexture(), TextureFactory.of(
                    aActive ? Textures.BlockIcons.OVERLAY_COKE_OVEN_BRICK_ACTIVE
                            : Textures.BlockIcons.OVERLAY_COKE_OVEN_BRICK) };
        }
        return new ITexture[] { CASING.getTexture() };
    }

    public GT_Recipe.GT_Recipe_Map getRecipeMap() {
        return GT_Recipe.GT_Recipe_Map.sCokeOvenRecipes;
    }

    public boolean isCorrectMachinePart(ItemStack aStack) {
        return true;
    }

    protected IAlignmentLimits getInitialAlignmentLimits() {
        return (direction, rotation, flip) -> direction.offsetY == 0 && rotation.isNotRotated() && flip.isNotFlipped();
    }

    @Override
    public boolean useMui2() {
        return false;
    }

    @Override
    public boolean onRightclick(IGregTechTileEntity tile, EntityPlayer player) {
        return false;
    }

    @Override
    protected ProcessingLogic createProcessingLogic() {
        return new ProcessingLogic().setRecipeMapSupplier(this::getRecipeMap).setPowerRequired(false);
    }

    @Override
    public boolean hasControllerSlotInGui() {
        return true;
    }

    @Override
    public boolean usesControllerSlotAsRecipeInput() {
        return true;
    }

    @SuppressWarnings({ "deprecation" })
    @Override
    public boolean onRunningTick(ItemStack aStack) {
        if (this.mMaxProgresstime > 0 && (getBaseMetaTileEntity().getTimer() % 20L == 0L)) {
            GT_Pollution.addPollution(
                    this.getBaseMetaTileEntity().getWorld(),
                    new ChunkPosition(this.getBaseMetaTileEntity().getXCoord(),
                            this.getBaseMetaTileEntity().getYCoord(), this.getBaseMetaTileEntity().getZCoord()),
                    25);
        }
        return super.onRunningTick(aStack);
    }

    public boolean checkMachine(IGregTechTileEntity base, ItemStack trigger) {
        mInputBusses1.clear();
        mOutputBusses1.clear();
        mOutputHatches1.clear();
        casingCount = 0;
        boolean formed = checkPiece("main", 1, 1, 0) && SHELL.isSatisfied(this, casingCount)
                && mInputBusses1.size() <= 1 && mOutputBusses1.size() <= 1 && mOutputHatches1.size() <= 1;
        if (formed) {
            mWrench = mScrewdriver = mSoftHammer = mHardHammer = mSolderingTool = mCrowbar = true;
        }
        return formed;
    }

    public int getMaxEfficiency(ItemStack aStack) {
        return 10000;
    }

    public int getPollutionPerTick(ItemStack aStack) {
        return 0;
    }

    public int getDamageToComponent(ItemStack aStack) {
        return 0;
    }

    public boolean explodesOnComponentBreak(ItemStack aStack) {
        return false;
    }
}
