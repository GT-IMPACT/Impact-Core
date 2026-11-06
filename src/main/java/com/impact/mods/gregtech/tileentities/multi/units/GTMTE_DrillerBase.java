package com.impact.mods.gregtech.tileentities.multi.units;

import gregtech.api.logic.ProcessingInputs;
import gregtech.api.logic.ProcessingLogic;
import gregtech.api.logic.ProcessingWorldChanges;
import gregtech.api.logic.WorldProcessingLogic;
import gregtech.api.recipe.check.CheckRecipeResult;
import net.minecraftforge.fluids.FluidStack;
import java.util.function.BooleanSupplier;
import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ICasing;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_ProcessingMultiBlockBase;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Energy;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_EnergyMulti;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Output;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.ForgeDirection;
import space.impact.structures.alignment.constructable.ISurvivalConstructable;

import static gregtech.api.enums.GT_Values.W;
import static net.minecraftforge.common.BiomeDictionary.Type.*;

public abstract class GTMTE_DrillerBase<T extends GTMTE_DrillerBase<T>>
        extends GT_MetaTileEntity_ProcessingMultiBlockBase<T>
        implements ISurvivalConstructable {

    protected static final int STATE_DOWNWARD = 0, STATE_AT_BOTTOM = 1, STATE_UPWARD = 2;
    private static final ItemStack miningPipe = GT_ModHandler.getIC2Item("miningPipe", 0);
    private static final ItemStack miningPipeTip = GT_ModHandler.getIC2Item("miningPipeTip", 0);
    private static final Block miningPipeBlock = GT_Utility.getBlockFromStack(miningPipe);
    private static final Block miningPipeTipBlock = GT_Utility.getBlockFromStack(miningPipeTip);
    protected int boimeWater;
    protected boolean isPickingPipes;
    protected int workState;
    private ForgeDirection back;
    private int xDrill, yDrill, zDrill, xPipe, zPipe, yHead;
    private FakePlayer mFakePlayer = null;

    public GTMTE_DrillerBase(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public GTMTE_DrillerBase(String aName) {
        super(aName);
    }

    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex,
            boolean aActive, boolean aRedstone) {
        if (aSide == aFacing) {
            return new ITexture[] { getCasing().getTexture(),
                    TextureFactory.of(aActive ? Textures.BlockIcons.OVERLAY_FRONT_ORE_DRILL_ACTIVE
                            : Textures.BlockIcons.OVERLAY_FRONT_ORE_DRILL) };
        }
        return new ITexture[] { getCasing().getTexture() };
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
        aNBT.setInteger("workState", workState);
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
        workState = aNBT.getInteger("workState");
        if (aNBT.hasKey("isPickingPipes")) {
            workState = aNBT.getBoolean("isPickingPipes") ? STATE_UPWARD : STATE_DOWNWARD;
        }
    }

    @Override
    public boolean hasControllerSlotInGui() {
        return true;
    }

    @Override
    public int getSupportedProcessingModes() {
        return ProcessingLogic.PROTECT_ITEMS | ProcessingLogic.PROTECT_FLUIDS;
    }

    @Override
    protected ProcessingLogic createProcessingLogic() {
        return new WorldProcessingLogic(this::prepareOperation);
    }

    protected abstract long getDrillingEUt();

    protected abstract int getDrillingDuration();

    /** Retained for subclasses that inspect the drilling cycle's electricity statistics. */
    protected void setElectricityStats() {
        lEUt = -getDrillingEUt();
        mEUt = (int) Math.max(Integer.MIN_VALUE, lEUt);
        mMaxProgresstime = getDrillingDuration();
        mEfficiency = getCurrentEfficiency(null);
        mEfficiencyIncrease = 10000;
    }

    protected int getDrillingTier() {
        return Math.max(0, GT_Utility.getTier(getTotalInputVoltage()));
    }

    private boolean isEnergyEnough() {
        long requiredEnergy = Math.addExact(512L, Math.multiplyExact(getTotalInputVoltage(), 4L));
        for (GT_MetaTileEntity_Hatch_Energy hatch : mEnergyHatches) {
            requiredEnergy -= Math.min(requiredEnergy, Math.max(0, hatch.getEUVar()));
        }
        for (GT_MetaTileEntity_Hatch_EnergyMulti hatch : mEnergyHatchesMulti) {
            requiredEnergy -= Math.min(requiredEnergy, Math.max(0, hatch.getEUVar()));
        }
        return requiredEnergy == 0;
    }

    private WorldProcessingLogic.Operation prepareOperation(ProcessingLogic.Request request) {
        if (!isEnergyEnough()) {
            return WorldProcessingLogic.Operation.failure(CheckRecipeResult.INSUFFICIENT_STARTUP_ENERGY);
        }
        updateCoordinates();
        DrillingPlan plan = new DrillingPlan(request.inputs.get(0).copy());
        checkPipesAndSetYHead(plan);
        plan.loadPipes();
        CheckRecipeResult status;
        switch (workState) {
            case STATE_DOWNWARD:
                status = prepareDownward(plan);
                break;
            case STATE_AT_BOTTOM:
                status = prepareAtBottom(plan);
                break;
            case STATE_UPWARD:
                status = prepareUpward(plan);
                break;
            default:
                status = CheckRecipeResult.MACHINE_CONDITION;
        }
        if (!status.wasSuccessful()) {
            return WorldProcessingLogic.Operation.failure(status);
        }
        return new WorldProcessingLogic.Operation(getDrillingEUt(), getDrillingDuration(),
                plan.items, plan.fluids, plan::commit);
    }

    protected CheckRecipeResult prepareDownward(DrillingPlan plan) {
        switch (plan.lowerPipe(false)) {
            case 2:
                return CheckRecipeResult.NO_RECIPE;
            case 3:
                plan.nextState = STATE_UPWARD;
                break;
            case 1:
                plan.nextState = STATE_AT_BOTTOM;
                break;
            default:
                break;
        }
        return CheckRecipeResult.SUCCESS;
    }

    protected CheckRecipeResult prepareAtBottom(DrillingPlan plan) {
        plan.nextState = plan.lowerPipe(true) == 0 ? STATE_DOWNWARD : STATE_UPWARD;
        return CheckRecipeResult.SUCCESS;
    }

    protected CheckRecipeResult prepareUpward(DrillingPlan plan) {
        if (plan.pickPipe()) {
            return CheckRecipeResult.SUCCESS;
        }
        workState = STATE_DOWNWARD;
        stopMachine();
        return CheckRecipeResult.NO_RECIPE;
    }

    protected boolean reachingVoidOrBedrock() {
        return yHead <= 0 || checkBlockAndMeta(xPipe, yHead - 1, zPipe, Blocks.bedrock, W);
    }

    protected boolean mayBreakBlock(int x, int y, int z) {
        return GT_Utility.eraseBlockByFakePlayer(getFakePlayer(getBaseMetaTileEntity()), x, y, z, true);
    }

    protected boolean mayPlaceBlock(int x, int y, int z, Block block, int meta) {
        return GT_Utility.setBlockByFakePlayer(getFakePlayer(getBaseMetaTileEntity()), x, y, z, block, meta, true);
    }

    /** Inventory and world changes stay provisional until power and output checks have passed. */
    protected final class DrillingPlan {
        public final ProcessingInputs inputs;
        public final ProcessingWorldChanges world = new ProcessingWorldChanges(getBaseMetaTileEntity().getWorld());
        public final int pipeX = xPipe;
        public final int pipeZ = zPipe;
        public final int drillY = yDrill;
        public int headY;
        public int nextState = workState;
        public ItemStack[] items = new ItemStack[0];
        public FluidStack[] fluids = new FluidStack[0];
        public BooleanSupplier commitResource = () -> true;
        public Runnable afterCommit = () -> {
        };
        private final ItemStack originalController = getStackInSlot(1);
        private final ItemStack beforeController = originalController == null ? null : originalController.copy();
        private ItemStack controller = beforeController == null ? null : beforeController.copy();

        private DrillingPlan(ProcessingInputs inputs) {
            this.inputs = inputs;
        }

        private boolean controllerUnchanged() {
            return originalController == getStackInSlot(1)
                    && ItemStack.areItemStacksEqual(beforeController, getStackInSlot(1));
        }

        private void loadPipes() {
            if (controller != null && (!controller.isItemEqual(miningPipe)
                    || controller.stackSize >= controller.getMaxStackSize())) {
                return;
            }
            for (ItemStack item : inputs.items) {
                if (!item.isItemEqual(miningPipe)) {
                    continue;
                }
                if (controller == null) {
                    controller = GT_Utility.copyAmount(0, miningPipe);
                }
                int count = Math.min(Math.max(0, item.stackSize), controller.getMaxStackSize() - controller.stackSize);
                controller.stackSize += count;
                item.stackSize -= count;
            }
        }

        /** 0: ready, 1: bottom, 2: missing pipes, 3: protected block. */
        public int lowerPipe(boolean simulate) {
            if (controller == null || controller.stackSize <= 0 || !controller.isItemEqual(miningPipe)) {
                return 2;
            }
            if (headY <= 0 || GT_Utility.getBlockHardnessAt(getBaseMetaTileEntity().getWorld(),
                    pipeX, headY - 1, pipeZ) < 0) {
                return 1;
            }
            Block below = getBaseMetaTileEntity().getBlock(pipeX, headY - 1, pipeZ);
            world.observe(pipeX, headY - 1, pipeZ);
            if (!mayBreakBlock(pipeX, headY - 1, pipeZ)
                    || below != miningPipeTipBlock && !mayPlaceBlock(pipeX, headY - 1, pipeZ, miningPipeTipBlock, 0)) {
                return 3;
            }
            if (!simulate) {
                if (below != miningPipeTipBlock) {
                    world.setBlock(pipeX, headY - 1, pipeZ, miningPipeTipBlock, 0);
                }
                if (headY != drillY) {
                    world.setBlock(pipeX, headY, pipeZ, miningPipeBlock, 0);
                }
                controller.stackSize--;
            }
            return 0;
        }

        public boolean pickPipe() {
            if (headY == drillY) {
                return false;
            }
            if (controller == null) {
                controller = GT_Utility.copyAmount(1, miningPipe);
            } else if (controller.isItemEqual(miningPipe) && controller.stackSize < controller.getMaxStackSize()) {
                controller.stackSize++;
            } else {
                items = new ItemStack[]{GT_Utility.copyAmount(1, miningPipe)};
            }
            if (checkBlockAndMeta(pipeX, headY + 1, pipeZ, miningPipeBlock, W)) {
                world.setBlock(pipeX, headY + 1, pipeZ, miningPipeTipBlock, 0);
            }
            world.setBlock(pipeX, headY, pipeZ, Blocks.air, 0);
            return true;
        }

        private boolean commit() {
            if (!isEnergyEnough() || !controllerUnchanged() || !inputs.isUnchanged() || !world.commit()) {
                return false;
            }
            // Block callbacks may have changed an inventory. Roll back before spending anything.
            if (!controllerUnchanged() || !inputs.isUnchanged() || !commitResource.getAsBoolean()) {
                world.rollback();
                return false;
            }
            if (!inputs.commit()) {
                world.rollback();
                return false;
            }
            setInventorySlotContents(1, controller == null || controller.stackSize <= 0 ? null : controller);
            workState = nextState;
            isPickingPipes = workState == STATE_UPWARD;
            afterCommit.run();
            return true;
        }
    }

    public boolean checkMachine(IGregTechTileEntity thisController, ItemStack guiSlotItem) {
        updateCoordinates();
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

        boolean formationChecklist = checkPiece("main", 2, 6, 0);

        if (!checkHatches()) {
            formationChecklist = false;
        }
        if (getTierEnergyHatch() < getMinTier()) {
            formationChecklist = false;
        }
        if (getTierEnergyHatch() != getTierFluidHatch()) {
            formationChecklist = false;
        }
        if (this.mMaintenanceHatches.size() != 1) {
            formationChecklist = false;
        }
        if (this.mEnergyHatches.size() > 4) {
            formationChecklist = false;
        }
        if (this.mOutputHatches.size() > 4) {
            formationChecklist = false;
        }
        if (this.mInputBusses.size() > 1) {
            formationChecklist = false;
        }

        return formationChecklist;
    }

    public int getTierEnergyHatch() {
        int Tier = 0;
        for (GT_MetaTileEntity_Hatch_Energy tHatch : mEnergyHatches) {
            if (isValidMetaTileEntity(tHatch)) {
                if (Tier == 0) {
                    Tier = tHatch.mTier;
                }
                Tier = Tier > tHatch.mTier ? tHatch.mTier : Tier;
            }
        }
        for (GT_MetaTileEntity_Hatch_EnergyMulti tHatch : mEnergyHatchesMulti) {
            if (isValidMetaTileEntity(tHatch)) {
                if (Tier == 0) {
                    Tier = tHatch.mTier;
                }
                Tier = Tier > tHatch.mTier ? tHatch.mTier : Tier;
            }
        }
        return Tier;
    }

    public int getTierFluidHatch() {
        int Tier = 0;
        for (GT_MetaTileEntity_Hatch_Output tHatch : mOutputHatches) {
            if (isValidMetaTileEntity(tHatch)) {
                if (Tier == 0) {
                    Tier = tHatch.mTier;
                }
                Tier = Tier > tHatch.mTier ? tHatch.mTier : Tier;
            }
        }
        return Tier;
    }

    protected int getWaterInBiomes() {
        return this.boimeWater;
    }

    private void updateCoordinates() {
        xDrill = getBaseMetaTileEntity().getXCoord();
        yDrill = getBaseMetaTileEntity().getYCoord() + 3;
        zDrill = getBaseMetaTileEntity().getZCoord();
        back = ForgeDirection.getOrientation(getBaseMetaTileEntity().getBackFacing());
        if (back == ForgeDirection.WEST) {
            xPipe = xDrill - 1 + back.offsetX;
            zPipe = zDrill + back.offsetZ;
        }
        if (back == ForgeDirection.EAST) {
            xPipe = xDrill + 1 + back.offsetX;
            zPipe = zDrill + back.offsetZ;
        }
        if (back == ForgeDirection.NORTH) {
            xPipe = xDrill + back.offsetX;
            zPipe = zDrill - 1 + back.offsetZ;
        }
        if (back == ForgeDirection.SOUTH) {
            xPipe = xDrill + back.offsetX;
            zPipe = zDrill + 1 + back.offsetZ;
        }
    }

    private void checkPipesAndSetYHead(DrillingPlan plan) {
        yHead = yDrill - 1;
        while (yHead >= 0 && checkBlockAndMeta(xPipe, yHead, zPipe, miningPipeBlock, W)) {
            plan.world.observe(xPipe, yHead, zPipe);
            yHead--;
        }
        if (yHead >= 0) {
            plan.world.observe(xPipe, yHead, zPipe);
        }
        if (!checkBlockAndMeta(xPipe, yHead, zPipe, miningPipeTipBlock, W) && ++yHead != yDrill) {
            plan.world.setBlock(xPipe, yHead, zPipe, miningPipeTipBlock, 0);
        }
        if (yHead > 0) {
            plan.world.observe(xPipe, yHead - 1, zPipe);
        }
        plan.headY = yHead;
    }

    private boolean checkBlockAndMeta(int x, int y, int z, Block block, int meta) {
        return (meta == W || getBaseMetaTileEntity().getMetaID(x, y, z) == meta)
                && getBaseMetaTileEntity().getBlock(x, y, z) == block;
    }

    protected FakePlayer getFakePlayer(IGregTechTileEntity aBaseTile) {
        if (mFakePlayer == null) {
            mFakePlayer = GT_Utility.getFakePlayer(aBaseTile);
        }
        mFakePlayer.setWorld(aBaseTile.getWorld());
        mFakePlayer.setPosition(aBaseTile.getXCoord(), aBaseTile.getYCoord(), aBaseTile.getZCoord());
        return mFakePlayer;
    }

    @Override
    public boolean isCorrectMachinePart(ItemStack aStack) {
        return true;
    }

    @Override
    public int getMaxEfficiency(ItemStack aStack) {
        return 10000;
    }

    @Override
    public int getPollutionPerTick(ItemStack aStack) {
        return 0;
    }

    @Override
    public int getDamageToComponent(ItemStack aStack) {
        return 0;
    }

    @Override
    public boolean explodesOnComponentBreak(ItemStack aStack) {
        return false;
    }

    protected abstract ICasing getCasing();

    protected abstract Materials getFrameMaterial();

    protected abstract int getMinTier();

    protected abstract boolean checkHatches();

}
