package com.impact.mods.gregtech.tileentities.multi.units;

import com.impact.util.vector.Vector3i;
import com.impact.util.vector.Vector3ic;
import gregtech.api.GregTech_API;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Energy;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_EnergyMulti;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Output;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase;
import gregtech.api.objects.GT_RenderedTexture;
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

import static gregtech.api.enums.GT_Values.W;
import static net.minecraftforge.common.BiomeDictionary.Type.*;

public abstract class GTMTE_DrillerBase extends GT_MetaTileEntity_MultiBlockBase {
	
	protected static final int STATE_DOWNWARD = 0, STATE_AT_BOTTOM = 1, STATE_UPWARD = 2;
	private static final ItemStack miningPipe = GT_ModHandler.getIC2Item("miningPipe", 0);
	private static final ItemStack miningPipeTip = GT_ModHandler.getIC2Item("miningPipeTip", 0);
	private static final Block miningPipeBlock = GT_Utility.getBlockFromStack(miningPipe);
	private static final Block miningPipeTipBlock = GT_Utility.getBlockFromStack(miningPipeTip);
	protected int boimeWater;
	protected boolean isPickingPipes;
	protected int workState;
	private Block casingBlock;
	private int casingMeta;
	private int frameMeta;
	private int casingTextureIndex;
	private ForgeDirection back;
	private int xDrill, yDrill, zDrill, xPipe, zPipe, yHead;
	private FakePlayer mFakePlayer = null;
	
	public GTMTE_DrillerBase(int aID, String aName, String aNameRegional) {
		super(aID, aName, aNameRegional);
		initFields();
	}
	
	public GTMTE_DrillerBase(String aName) {
		super(aName);
		initFields();
	}
	
	private void initFields() {
		casingBlock = getCasingBlockItem().getBlock();
		casingMeta  = getCasingBlockItem().get(0).getItemDamage();
		int frameId = 4096 + getFrameMaterial().mMetaItemSubID;
		frameMeta = GregTech_API.METATILEENTITIES[frameId] != null ? GregTech_API.METATILEENTITIES[frameId].getTileEntityBaseType() : W;
		casingTextureIndex = getCasingTextureIndex();
		workState = STATE_DOWNWARD;
	}
	
	public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
		if (aSide == aFacing) {
			return new ITexture[]{Textures.BlockIcons.CASING_BLOCKS[casingTextureIndex],
                    TextureFactory.of(aActive ? Textures.BlockIcons.OVERLAY_FRONT_ORE_DRILL_ACTIVE : Textures.BlockIcons.OVERLAY_FRONT_ORE_DRILL)};
		}
		return new ITexture[]{Textures.BlockIcons.CASING_BLOCKS[casingTextureIndex]};
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
	
	protected boolean tryPickPipe() {
		if (yHead == yDrill) {
			return isPickingPipes = false;
		}
		if (tryOutputPipe()) {
			if (checkBlockAndMeta(xPipe, yHead + 1, zPipe, miningPipeBlock, W)) {
				getBaseMetaTileEntity().getWorld().setBlock(xPipe, yHead + 1, zPipe, miningPipeTipBlock);
			}
			getBaseMetaTileEntity().getWorld().setBlockToAir(xPipe, yHead, zPipe);
			return isPickingPipes = true;
		}
		return isPickingPipes = false;
	}
	
	protected boolean tryLowerPipe() {
		return tryLowerPipeState(false) == 0;
	}
	
	protected int tryLowerPipeState() {
		return tryLowerPipeState(false);
	}
	
	protected int tryLowerPipeState(boolean isSimulating) {
		if (!isHasMiningPipes()) {
			return 2;
		}
		switch (canLowerPipe()) {
			case 1:
				return 1;
			case 2:
				return 3;
		}
		
		if (!GT_Utility.setBlockByFakePlayer(getFakePlayer(getBaseMetaTileEntity()), xPipe, yHead - 1, zPipe, miningPipeTipBlock, 0, isSimulating)) {
			return 3;
		}
		if (!isSimulating) {
			if (yHead != yDrill) {
				getBaseMetaTileEntity().getWorld().setBlock(xPipe, yHead, zPipe, miningPipeBlock);
			}
			getBaseMetaTileEntity().decrStackSize(1, 1);
		}
		
		return 0;
	}
	
	private void putMiningPipesFromInputsInController() {
		int maxPipes = miningPipe.getMaxStackSize();
		if (isHasMiningPipes(maxPipes)) {
			return;
		}
		
		ItemStack pipes = getStackInSlot(1);
		if (pipes != null && !pipes.isItemEqual(miningPipe)) {
			return;
		}
		for (ItemStack storedItem : getStoredInputs()) {
			if (!storedItem.isItemEqual(miningPipe)) {
				continue;
			}
			
			if (pipes == null) {
				setInventorySlotContents(1, GT_Utility.copy(miningPipe));
				pipes = getStackInSlot(1);
			}
			
			if (pipes.stackSize == maxPipes) {
				break;
			}
			
			int needPipes = maxPipes - pipes.stackSize;
			int transferPipes = storedItem.stackSize < needPipes ? storedItem.stackSize : needPipes;
			
			pipes.stackSize += transferPipes;
			storedItem.stackSize -= transferPipes;
		}
		updateSlots();
	}
	
	private boolean tryOutputPipe() {
		if (!getBaseMetaTileEntity().addStackToSlot(1, GT_Utility.copyAmount(1, miningPipe))) {
			mOutputItems = new ItemStack[]{GT_Utility.copyAmount(1, miningPipe)};
		}
		return true;
	}
	
	protected int canLowerPipe() {
		IGregTechTileEntity aBaseTile = getBaseMetaTileEntity();
		if (yHead > 0 && GT_Utility.getBlockHardnessAt(aBaseTile.getWorld(), xPipe, yHead - 1, zPipe) >= 0) {
			return GT_Utility.eraseBlockByFakePlayer(getFakePlayer(aBaseTile), xPipe, yHead - 1, zPipe, true) ? 0 : 2;
		}
		return 1;
	}
	
	protected boolean reachingVoidOrBedrock() {
		return yHead <= 0 || checkBlockAndMeta(xPipe, yHead - 1, zPipe, Blocks.bedrock, W);
	}
	
	private boolean isHasMiningPipes() {
		return isHasMiningPipes(1);
	}
	
	private boolean isHasMiningPipes(int minCount) {
		ItemStack pipe = getStackInSlot(1);
		return pipe != null && pipe.stackSize > minCount - 1 && pipe.isItemEqual(miningPipe);
	}
	
	protected boolean waitForPipes() {
		return !isHasMiningPipes();
	}
	
	private boolean isEnergyEnough() {
		long requiredEnergy = 512 + getMaxInputVoltage() * 4;
		for (GT_MetaTileEntity_Hatch_Energy energyHatch : mEnergyHatches) {
			requiredEnergy -= energyHatch.getEUVar();
			if (requiredEnergy <= 0) {
				return true;
			}
		}
		return false;
	}
	
	protected boolean workingDownward(ItemStack aStack, int xDrill, int yDrill, int zDrill, int xPipe, int zPipe, int yHead, int oldYHead) {
		switch (tryLowerPipeState()) {
			case 2:
				mMaxProgresstime = 0;
				return false;
			case 3:
				workState = STATE_UPWARD;
				return true;
			case 1:
				workState = STATE_AT_BOTTOM;
				return true;
			default:
				return true;
		}
	}
	
	protected boolean workingAtBottom(ItemStack aStack, int xDrill, int yDrill, int zDrill, int xPipe, int zPipe, int yHead, int oldYHead) {
		switch (tryLowerPipeState(true)) {
			case 0:
				workState = STATE_DOWNWARD;
				return true;
			default:
				workState = STATE_UPWARD;
				return true;
		}
	}
	
	protected boolean workingUpward(ItemStack aStack, int xDrill, int yDrill, int zDrill, int xPipe, int zPipe, int yHead, int oldYHead) {
		if (tryPickPipe()) {
			return true;
		} else {
			workState = STATE_DOWNWARD;
			stopMachine();
			return false;
		}
	}
	
	@Override
	public boolean checkRecipe(ItemStack aStack) {
		//Public pipe actions
		setElectricityStats();
		int oldYHead = yHead;
		if (!checkPipesAndSetYHead() || !isEnergyEnough()) {
			stopMachine();
			return false;
		}
		putMiningPipesFromInputsInController();
		switch (workState) {
			case STATE_DOWNWARD:
				return workingDownward(aStack, xDrill, yDrill, zDrill, xPipe, zPipe, yHead, oldYHead);
			case STATE_AT_BOTTOM:
				return workingAtBottom(aStack, xDrill, yDrill, zDrill, xPipe, zPipe, yHead, oldYHead);
			case STATE_UPWARD:
				return workingUpward(aStack, xDrill, yDrill, zDrill, xPipe, zPipe, yHead, oldYHead);
			default:
				return false;
		}
	}
	
	public Vector3ic rotateOffsetVector(Vector3ic forgeDirection, int x, int y, int z) {
		final Vector3i offset = new Vector3i();
		
		// В любом направлении по оси Z
		if (forgeDirection.x() == 0 && forgeDirection.z() == -1) {
			offset.x = x;
			offset.y = y;
			offset.z = z;
		}
		if (forgeDirection.x() == 0 && forgeDirection.z() == 1) {
			offset.x = -x;
			offset.y = y;
			offset.z = -z;
		}
		// В любом направлении по оси X
		if (forgeDirection.x() == -1 && forgeDirection.z() == 0) {
			offset.x = z;
			offset.y = y;
			offset.z = -x;
		}
		if (forgeDirection.x() == 1 && forgeDirection.z() == 0) {
			offset.x = -z;
			offset.y = y;
			offset.z = x;
		}
		// в любом направлении по оси Y
		if (forgeDirection.y() == -1) {
			offset.x = x;
			offset.y = z;
			offset.z = y;
		}
		
		return offset;
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
        if (BiomeDictionary.isBiomeOfType(thisController.getBiome(), PLAINS) || BiomeDictionary.isBiomeOfType(thisController.getBiome(), FOREST)) {
			this.boimeWater = 250;
		}
		if (BiomeDictionary.isBiomeOfType(thisController.getBiome(), HILLS)
				|| BiomeDictionary.isBiomeOfType(thisController.getBiome(), MOUNTAIN)
				|| BiomeDictionary.isBiomeOfType(thisController.getBiome(), SAVANNA)
				|| BiomeDictionary.isBiomeOfType(thisController.getBiome(), SANDY)
				|| BiomeDictionary.isBiomeOfType(thisController.getBiome(), MESA)) {
			this.boimeWater = 100;
		}
		
		final Vector3ic forgeDirection = new Vector3i(
				ForgeDirection.getOrientation(thisController.getBackFacing()).offsetX,
				ForgeDirection.getOrientation(thisController.getBackFacing()).offsetY,
				ForgeDirection.getOrientation(thisController.getBackFacing()).offsetZ
		);
		
		int minCasingAmount = 12;
		boolean formationChecklist = true;
		
		for (byte X = -2; X <= 2; X++) {
			for (byte Z = 0; Z >= -4; Z--) {
				
				if (X == 0 && Z == 0) {
					continue;
				}
				if (X == 0 && Z == -2) {
					continue;
				}
				
				final Vector3ic offset = rotateOffsetVector(forgeDirection, X, 0, Z);
				
				IGregTechTileEntity currentTE = thisController
						.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
				if (!super.addMaintenanceToMachineList(currentTE, casingTextureIndex)
						&& !super.addInputToMachineList(currentTE, casingTextureIndex)
						&& !super.addMufflerToMachineList(currentTE, casingTextureIndex)
						&& !super.addEnergyInputToMachineList(currentTE, casingTextureIndex)
						&& !super.addOutputToMachineList(currentTE, casingTextureIndex)) {
					if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == casingBlock)
							&& (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z())
							== casingMeta)) {
						minCasingAmount--;
					} else {
						formationChecklist = false;
					}
				}
			}
		}
		
		for (byte X = -1; X <= 1; X++) {
			for (byte Z = -1; Z >= -3; Z--) {
				final Vector3ic offset = rotateOffsetVector(forgeDirection, X, 3, Z);
				
				IGregTechTileEntity currentTE = thisController
						.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
				if (!super.addMaintenanceToMachineList(currentTE, casingTextureIndex)
						&& !super.addInputToMachineList(currentTE, casingTextureIndex)
						&& !super.addMufflerToMachineList(currentTE, casingTextureIndex)
						&& !super.addEnergyInputToMachineList(currentTE, casingTextureIndex)
						&& !super.addOutputToMachineList(currentTE, casingTextureIndex)) {
					if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == casingBlock)
							&& (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == casingMeta)) {
						minCasingAmount--;
					} else {
						formationChecklist = false;
					}
				}
			}
		}
		
		for (byte Y = 4; Y <= 6; Y++) {
			final Vector3ic offset = rotateOffsetVector(forgeDirection, 0, Y, -2);
			if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == GregTech_API.sBlockMachines)
					&& (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == frameMeta)) {
				minCasingAmount--;
			} else {
				formationChecklist = false;
			}
		}

//            final Vector3ic offset = rotateOffsetVector(forgeDirection, 0, 2, -2);
//            if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == casingBlock)
//                    && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == casingMeta)) {
//                minCasingAmount--;
//            } else {
//                formationChecklist = false;
//            }
		
		for (byte Y = -1; Y >= -2; Y--) {
			final Vector3ic offset1 = rotateOffsetVector(forgeDirection, -2, Y, 0);
			if (((thisController.getBlockOffset(offset1.x(), offset1.y(), offset1.z()) == GregTech_API.sBlockMachines)
					&& (thisController.getMetaIDOffset(offset1.x(), offset1.y(), offset1.z()) == frameMeta))) {
				minCasingAmount--;
			} else {
				formationChecklist = false;
			}
		}
		
		for (byte Y = -1; Y >= -2; Y--) {
			final Vector3ic offset2 = rotateOffsetVector(forgeDirection, -2, Y, -4);
			if ((thisController.getBlockOffset(offset2.x(), offset2.y(), offset2.z()) == GregTech_API.sBlockMachines)
					&& (thisController.getMetaIDOffset(offset2.x(), offset2.y(), offset2.z()) == frameMeta)) {
				minCasingAmount--;
			} else {
				formationChecklist = false;
			}
		}
		
		for (byte Y = -1; Y >= -2; Y--) {
			final Vector3ic offset3 = rotateOffsetVector(forgeDirection, 2, Y, 0);
			
			if (((thisController.getBlockOffset(offset3.x(), offset3.y(), offset3.z()) == GregTech_API.sBlockMachines)
					&& (thisController.getMetaIDOffset(offset3.x(), offset3.y(), offset3.z()) == frameMeta))) {
				minCasingAmount--;
			} else {
				formationChecklist = false;
			}
		}
		
		for (byte Y = -1; Y >= -2; Y--) {
			final Vector3ic offset4 = rotateOffsetVector(forgeDirection, 2, Y, -4);
			
			if (((thisController.getBlockOffset(offset4.x(), offset4.y(), offset4.z()) == GregTech_API.sBlockMachines)
					&& (thisController.getMetaIDOffset(offset4.x(), offset4.y(), offset4.z()) == frameMeta))) {
				minCasingAmount--;
			} else {
				formationChecklist = false;
			}
		}
		
		for (byte Y = 1; Y <= 2; Y++) {
			final Vector3ic offset1 = rotateOffsetVector(forgeDirection, -1, Y, -1);
			
			if (((thisController.getBlockOffset(offset1.x(), offset1.y(), offset1.z()) == GregTech_API.sBlockMachines)
					&& (thisController.getMetaIDOffset(offset1.x(), offset1.y(), offset1.z()) == frameMeta))) {
				minCasingAmount--;
			} else {
				formationChecklist = false;
			}
		}
		for (byte Y = 1; Y <= 2; Y++) {
			final Vector3ic offset2 = rotateOffsetVector(forgeDirection, 1, Y, -1);
			
			if (((thisController.getBlockOffset(offset2.x(), offset2.y(), offset2.z()) == GregTech_API.sBlockMachines)
                    && (thisController.getMetaIDOffset(offset2.x(), offset2.y(), offset2.z()) == frameMeta))) {
				minCasingAmount--;
			} else {
				formationChecklist = false;
			}
		}
		for (byte Y = 1; Y <= 2; Y++) {
			final Vector3ic offset3 = rotateOffsetVector(forgeDirection, -1, Y, -3);
			
			if (((thisController.getBlockOffset(offset3.x(), offset3.y(), offset3.z()) == GregTech_API.sBlockMachines)
					&& (thisController.getMetaIDOffset(offset3.x(), offset3.y(), offset3.z()) == frameMeta))) {
				minCasingAmount--;
			} else {
				formationChecklist = false;
			}
		}
		for (byte Y = 1; Y <= 2; Y++) {
			final Vector3ic offset4 = rotateOffsetVector(forgeDirection, 1, Y, -3);
			
			if (((thisController.getBlockOffset(offset4.x(), offset4.y(), offset4.z()) == GregTech_API.sBlockMachines)
					&& (thisController.getMetaIDOffset(offset4.x(), offset4.y(), offset4.z()) == frameMeta))) {
				minCasingAmount--;
			} else {
				formationChecklist = false;
			}
		}
		
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
		back   = ForgeDirection.getOrientation(getBaseMetaTileEntity().getBackFacing());
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
	
	private boolean checkPipesAndSetYHead() {
		yHead = yDrill - 1;
		while (checkBlockAndMeta(xPipe, yHead, zPipe, miningPipeBlock, W)) {
			yHead--; //skip pipes
		}
		//is pipe tip OR is controller layer
		if (checkBlockAndMeta(xPipe, yHead, zPipe, miningPipeTipBlock, W) || ++yHead == yDrill) {
			return true;
		}
		//pipe column is broken - try fix
		getBaseMetaTileEntity().getWorld().setBlock(xPipe, yHead, zPipe, miningPipeTipBlock);
		return true;
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
	
	protected abstract ItemList getCasingBlockItem();
	
	protected abstract Materials getFrameMaterial();
	
	protected abstract int getCasingTextureIndex();
	
	protected abstract int getMinTier();
	
	protected abstract boolean checkHatches();
	
	protected abstract void setElectricityStats();
}