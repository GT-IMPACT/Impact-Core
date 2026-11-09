package com.impact.mods.gregtech.tileentities.multi.units;

import gregtech.api.logic.ProcessingLogic;
import gregtech.api.logic.WorldProcessingLogic;
import gregtech.api.logic.IProcessingOutputSink;
import com.impact.addon.gt.api.recipe.PrimitiveProcessingOutputs;
import java.util.Collections;
import com.impact.mods.gregtech.blocks.ImpactCasings;
import com.impact.mods.gregtech.tileentities.hatches.GT_MetaTileEntity_Primitive_Hatch_Output;
import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_Impact_BlockBase;
import com.impact.util.ImpactMultiblockTooltipBuilder;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ICasing;
import gregtech.api.interfaces.IHatchElement;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Output;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTStructureUtility;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.StructureRequirements;
import java.util.ArrayList;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fluids.FluidStack;
import space.impact.structures.hint.HintColor;
import space.impact.structures.structure.IStructureDefinition;
import space.impact.structures.structure.ISurvivalBuildEnvironment;
import space.impact.structures.structure.StructureDefinition;

import static gregtech.api.util.GTStructureUtility.ofFrame;
import static net.minecraftforge.common.BiomeDictionary.Type.BEACH;
import static net.minecraftforge.common.BiomeDictionary.Type.CONIFEROUS;
import static net.minecraftforge.common.BiomeDictionary.Type.FOREST;
import static net.minecraftforge.common.BiomeDictionary.Type.HILLS;
import static net.minecraftforge.common.BiomeDictionary.Type.JUNGLE;
import static net.minecraftforge.common.BiomeDictionary.Type.MESA;
import static net.minecraftforge.common.BiomeDictionary.Type.MOUNTAIN;
import static net.minecraftforge.common.BiomeDictionary.Type.PLAINS;
import static net.minecraftforge.common.BiomeDictionary.Type.SANDY;
import static net.minecraftforge.common.BiomeDictionary.Type.SAVANNA;
import static net.minecraftforge.common.BiomeDictionary.Type.SNOWY;
import static net.minecraftforge.common.BiomeDictionary.Type.SWAMP;
import static net.minecraftforge.common.BiomeDictionary.Type.WATER;
import static space.impact.structures.structure.StructureUtility.withHint;

public class GTMTE_BasicWaterPump extends GTMTE_Impact_BlockBase<GTMTE_BasicWaterPump> {

    public ArrayList<GT_MetaTileEntity_Primitive_Hatch_Output> mOutputHatches1 = new ArrayList<>();

    protected int boimeWater;

    private static final ICasing CASING = ImpactCasings.PRIMITIVE_WATER_PUMP_CASING;

    private static IStructureDefinition<GTMTE_BasicWaterPump> definition;

    public GTMTE_BasicWaterPump(int aID, String aNameRegional) {
        super(aID, "impact.multimachine.basicwaterpump", aNameRegional);
        enabledMaintenance = false;
    }

    public GTMTE_BasicWaterPump(String aName) {
        super(aName);
        enabledMaintenance = false;
    }

    @Override
    public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide, final byte aFacing,
            final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
        return aSide == aFacing
                ? new ITexture[] { CASING.getTexture(), TextureFactory.of(Textures.BlockIcons.OVERLAY_PUMP) }
                : new ITexture[] { CASING.getTexture() };
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GTMTE_BasicWaterPump(this.mName);
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        return new ImpactMultiblockTooltipBuilder(mName)
                .useCompactStructure()
                .addMachineType("Water Pump")
                .addInfo("Pumps water from the ground; base output depends on the biome.")
                .addInfo("Output multiplier: Pump Output Hatch 1x; ULV Output Hatch 2x; LV Output Hatch 3x.")
                .addControlInfo("Biome base output (L/s; later matches take priority):")
                .addControlInfo("Water: 1000")
                .addControlInfo("Coniferous: 175")
                .addControlInfo("Jungle: 350")
                .addControlInfo("Swamp: 800")
                .addControlInfo("Snowy: 300")
                .addControlInfo("Beach: 170")
                .addControlInfo("Plains or Forest: 250")
                .addControlInfo("Hills, Mountains, Savanna, Sandy or Mesa: 100")
                .beginStructureBlock(4, 3, 3, false)
                .addController("front right corner of the bottom layer")
                .addStructureSection("Components")
                .addOtherStructurePart(CASING, "10 fixed; fill the output position if unused")
                .addOtherStructurePart(GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Wood, 1), "exactly 10; support frame above the pump deck")
                .addStructureSection("Hatches and buses")
                .addHatchInfo("Pump Output Hatch or Output Hatch", "optional, up to 1 total; center-left of the bottom layer; Output Hatch: ULV or LV")
                .addStructureHint("Output position: casing or output hatch", HintColor.ORANGE)
                .addHatchChannelInfo()
                .toolTipFinisher();
    }

    @Override
    public IStructureDefinition<GTMTE_BasicWaterPump> getStructureDefinition() {
        if (definition == null) {
            definition = StructureDefinition.<GTMTE_BasicWaterPump>builder()
                    .addShape("main", new String[][] {
                            { " F  ", " F  ", "CCC~" },
                            { "FFFF", "F  F", "CHCC" },
                            { " F  ", " F  ", "CCCC" }
                    })
                    .addChannel(GTStructureUtility.HATCH_CHANNEL, 1, 1)
                    .addElement('C', CASING.asElement())
                    .addElement('F', ofFrame(Materials.Wood))
                    .addElement('H', withHint(GTStructureUtility.buildHatchAdder(GTMTE_BasicWaterPump.class)
                            .allow(PumpOutput.INSTANCE)
                            .autoPlace(PumpOutput.INSTANCE)
                            .buildAndChain(StructureRequirements.builder(CASING, 0).build()), HintColor.ORANGE))
                    .build();
        }
        return definition;
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
        return new WorldProcessingLogic(request -> new WorldProcessingLogic.Operation(0, 20,
                new ItemStack[0], new FluidStack[] {
                        GT_ModHandler.getWater(Math.multiplyExact(getWaterInBiomes(), getTierFluidHatch() + 1))
                }, () -> true));
    }

    @Override
    protected IProcessingOutputSink createProcessingOutputs() {
        return PrimitiveProcessingOutputs.create(this, Collections.emptyList(), mOutputHatches1);
    }

    public boolean machineStructure(IGregTechTileEntity thisController) {
        mOutputHatches1.clear();
        if (BiomeDictionary.isBiomeOfType(thisController.getBiome(), WATER)) {
            this.boimeWater = 1000;
        }

        if (BiomeDictionary.isBiomeOfType(thisController.getBiome(), CONIFEROUS)) {
            this.boimeWater = 175;
        }

        if (BiomeDictionary.isBiomeOfType(thisController.getBiome(), JUNGLE)) {
            this.boimeWater = 350;
        }

        if (BiomeDictionary.isBiomeOfType(thisController.getBiome(), SWAMP)) {
            this.boimeWater = 800;
        }

        if (BiomeDictionary.isBiomeOfType(thisController.getBiome(), SNOWY)) {
            this.boimeWater = 300;
        }

        if (BiomeDictionary.isBiomeOfType(thisController.getBiome(), BEACH)) {
            this.boimeWater = 170;
        }

        if (BiomeDictionary.isBiomeOfType(thisController.getBiome(), PLAINS)
                || BiomeDictionary.isBiomeOfType(thisController.getBiome(), FOREST)) {
            this.boimeWater = 250;
        }

        if (BiomeDictionary.isBiomeOfType(thisController.getBiome(), HILLS)
                || BiomeDictionary.isBiomeOfType(thisController.getBiome(), MOUNTAIN)
                || BiomeDictionary.isBiomeOfType(thisController.getBiome(), SAVANNA)
                || BiomeDictionary.isBiomeOfType(thisController.getBiome(), SANDY)
                || BiomeDictionary.isBiomeOfType(thisController.getBiome(), MESA)) {
            this.boimeWater = 100;
        }

        return checkPiece(3, 2, 0) && getTierFluidHatch() <= 2;
    }

    public int getTierFluidHatch() {
        int Tier = 0;
        for (GT_MetaTileEntity_Hatch_Output tHatch : mOutputHatches) {
            if (isValidMetaTileEntity(tHatch)) {
                Tier = tHatch.mTier + 1;
            }
        }
        for (GT_MetaTileEntity_Primitive_Hatch_Output tHatch : mOutputHatches1) {
            if (isValidMetaTileEntity(tHatch)) {
                Tier = tHatch.mTier;
            }
        }
        return Tier;
    }

    public int getWaterInBiomes() {
        return this.boimeWater;
    }

    public int Parallel() {
        return 0;
    }

    @Override
    public int getPollutionPerTick(ItemStack aStack) {
        return 0;
    }

    private boolean dumpFluid(FluidStack copiedFluidStack, boolean restrictiveHatchesOnly) {
        for (GT_MetaTileEntity_Primitive_Hatch_Output tHatch : mOutputHatches1) {
            if (!isValidMetaTileEntity(tHatch) || (restrictiveHatchesOnly && tHatch.mMode == 0)) {
                continue;
            }
            if (GT_ModHandler.isSteam(copiedFluidStack)) {
                if (!tHatch.outputsSteam()) {
                    continue;
                }
            } else {
                if (!tHatch.outputsLiquids()) {
                    continue;
                }
                if (tHatch.isFluidLocked() && tHatch.getLockedFluidName() != null && !tHatch
                        .getLockedFluidName().equals(copiedFluidStack.getUnlocalizedName())) {
                    continue;
                }
            }
            int tAmount = tHatch.fill(copiedFluidStack, false);
            if (tAmount >= copiedFluidStack.amount) {
                boolean filled = tHatch.fill(copiedFluidStack, true) >= copiedFluidStack.amount;
                tHatch.onEmptyingContainerWhenEmpty();
                return filled;
            } else if (tAmount > 0) {
                copiedFluidStack.amount = copiedFluidStack.amount - tHatch.fill(copiedFluidStack, true);
                tHatch.onEmptyingContainerWhenEmpty();
            }
        }
        return false;
    }

    public boolean addOutput1(FluidStack aLiquid) {
        if (aLiquid == null) {
            return false;
        } else {
            FluidStack copiedFluidStack = aLiquid.copy();
            if (!this.dumpFluid(copiedFluidStack, true)) {
                this.dumpFluid(copiedFluidStack, false);
            }

            return false;
        }
    }

    public boolean addPrimOutputToMachineList(IGregTechTileEntity aTileEntity, int aBaseCasingIndex) {
        if (aTileEntity == null) {
            return false;
        }
        IMetaTileEntity aMetaTileEntity = aTileEntity.getMetaTileEntity();
        if (aMetaTileEntity == null) {
            return false;
        }
        if (aMetaTileEntity instanceof GT_MetaTileEntity_Primitive_Hatch_Output) {
            ((GT_MetaTileEntity_Hatch) aMetaTileEntity).updateTexture(aBaseCasingIndex);
            return mOutputHatches1.add((GT_MetaTileEntity_Primitive_Hatch_Output) aMetaTileEntity);
        }
        return false;
    }

    @Override
    public void construct(ItemStack itemStack, boolean b) {
        buildPiece(itemStack, b, 3, 2, 0);
    }

    @Override
    public int survivalConstruct(ItemStack trigger, int budget,
            ISurvivalBuildEnvironment environment) {
        return survivalBuildPiece("main", trigger, 3, 2, 0, budget, environment);
    }

    @Override
    public int getSupportedProcessingModes() {
        return super.getSupportedProcessingModes() & ~ProcessingLogic.SEPARATE_INPUTS;
    }

    private enum PumpOutput implements IHatchElement {
        INSTANCE;

        @Override
        public boolean matches(IMetaTileEntity tile) {
            return tile instanceof GT_MetaTileEntity_Primitive_Hatch_Output
                    || tile instanceof GT_MetaTileEntity_Hatch_Output
                            && ((GT_MetaTileEntity_Hatch_Output) tile).mTier <= 1;
        }

        @Override
        public int count(GT_MetaTileEntity_MultiBlockBase machine) {
            return machine.mOutputHatches.size() + ((GTMTE_BasicWaterPump) machine).mOutputHatches1.size();
        }

        @Override
        public boolean add(GT_MetaTileEntity_MultiBlockBase machine, IGregTechTileEntity tile, int texture) {
            return machine.addOutputToMachineList(tile, texture)
                    || ((GTMTE_BasicWaterPump) machine).addPrimOutputToMachineList(tile, texture);
        }

        @Override
        public String getDescription() {
            return StatCollector.translateToLocal("impact.structure.hatch.pump_output");
        }
    }
}
