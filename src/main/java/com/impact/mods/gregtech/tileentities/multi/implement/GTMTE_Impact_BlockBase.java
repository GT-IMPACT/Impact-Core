package com.impact.mods.gregtech.tileentities.multi.implement;

import com.impact.mods.gregtech.gui.base.GTC_ImpactBase;
import com.impact.mods.gregtech.tileentities.hatches.lasers.GTMTE_LaserEnergy_In;
import com.impact.mods.gregtech.tileentities.hatches.lasers.GTMTE_LaserEnergy_Out;
import com.impact.util.multis.EnergyHelper;
import com.impact.util.string.MultiBlockTooltipBuilder;
import com.impact.util.vector.Vector3i;
import com.impact.util.vector.Vector3ic;
import cpw.mods.fml.common.network.NetworkRegistry;
import gregtech.api.GregTech_API;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.implementations.*;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.input.Keyboard;
import space.impact.api.ImpactAPI;
import space.impact.api.multiblocks.alignment.IAlignment;
import space.impact.api.multiblocks.alignment.IAlignmentLimits;
import space.impact.api.multiblocks.alignment.IAlignmentProvider;
import space.impact.api.multiblocks.alignment.constructable.IConstructable;
import space.impact.api.multiblocks.alignment.enumerable.ExtendedFacing;
import space.impact.api.multiblocks.alignment.enumerable.Flip;
import space.impact.api.multiblocks.alignment.enumerable.Rotation;
import space.impact.api.multiblocks.structure.IStructureDefinition;

import java.util.HashSet;
import java.util.concurrent.atomic.AtomicReferenceArray;

import static com.impact.util.string.Lang.holo_details;
import static gregtech.api.enums.GT_Values.V;

public abstract class GTMTE_Impact_BlockBase<MULTIS extends GTMTE_Impact_BlockBase<MULTIS>> extends GT_MetaTileEntity_MultiBlockBase implements IAlignment, IConstructable {
	
	private static final AtomicReferenceArray<MultiBlockTooltipBuilder> tooltips = new AtomicReferenceArray<>(GregTech_API.METATILEENTITIES.length);
	
	private final HashSet<GTMTE_LaserEnergy_In> mLaserIn = new HashSet<>();
	private final HashSet<GTMTE_LaserEnergy_Out> mLaserOut = new HashSet<>();
	
	public int modeBuses = 0;
	public byte mRecipeMode = -1;
	
	private ExtendedFacing mExtendedFacing = ExtendedFacing.DEFAULT;
	private IAlignmentLimits mLimits = getInitialAlignmentLimits();
	
	protected final MultiBlockRecipeBuilder<GTMTE_Impact_BlockBase<MULTIS>> RECIPE_BUILDER = new MultiBlockRecipeBuilder<>(this);
	
	public GTMTE_Impact_BlockBase(final int aID, final String aName, final String aNameRegional) {
		super(aID, aName, aNameRegional);
	}
	
	public GTMTE_Impact_BlockBase(final int aID, final String aName, final String aNameRegional, int slots) {
		super(aID, aName, aNameRegional, slots);
	}
	
	public GTMTE_Impact_BlockBase(final String aName, int slots) {
		super(aName, slots);
	}
	
	public GTMTE_Impact_BlockBase(final String aName) {
		super(aName);
	}
	
	public static boolean isValidMetaTileEntity(MetaTileEntity aMetaTileEntity) {
		return aMetaTileEntity.getBaseMetaTileEntity() != null
				&& aMetaTileEntity.getBaseMetaTileEntity().getMetaTileEntity() == aMetaTileEntity
				&& !aMetaTileEntity.getBaseMetaTileEntity().isDead();
	}
	
	public ItemStack get() {
		return getStackForm(1L);
	}
	
	public void ScrewClick(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
		if (aPlayer.isSneaking()) {
			if (aSide == getBaseMetaTileEntity().getFrontFacing()) {
				modeBuses++;
				if (modeBuses > 1) {
					modeBuses = 0;
				}
				GT_Utility.sendChatToPlayer(aPlayer, "Buses separated " + (modeBuses == 0 ? "on" : "off"));
			}
		}
	}
	
	public boolean inputStack(IGregTechTileEntity te, int slotIndex, int side, ItemStack stack) {
		return false;
	}
	
	public boolean outputStack(IGregTechTileEntity te, int slotIndex, int side, ItemStack stack) {
		return false;
	}
	
	@Override
	public boolean allowPullStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack) {
		return outputStack(aBaseMetaTileEntity, aIndex, aSide, aStack);
	}
	
	@Override
	public boolean allowPutStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack) {
		return inputStack(aBaseMetaTileEntity, aIndex, aSide, aStack);
	}
	
	@Override
	public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		return new GTC_ImpactBase(aPlayerInventory, aBaseMetaTileEntity);
	}
	
	public int getMaxEfficiency(ItemStack aStack) {
		return 10000;
	}
	
	public boolean isCorrectMachinePart(ItemStack aStack) {
		return true;
	}
	
	public boolean isFacingValid(byte aFacing) {
		return aFacing > 1;
	}
	
	public int getDamageToComponent(ItemStack aStack) {
		return 0;
	}
	
	public boolean explodesOnComponentBreak(ItemStack aStack) {
		return false;
	}
	
	protected void noMaintenance() {
		mWrench        = true;
		mScrewdriver   = true;
		mSoftHammer    = true;
		mHardHammer    = true;
		mSolderingTool = true;
		mCrowbar       = true;
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
	
	public long getMaxInputVoltageVanila() {
		long rVoltage = 0;
		for (GT_MetaTileEntity_Hatch_Energy tHatch : mEnergyHatches) {
			if (isValidMetaTileEntity(tHatch)) {
				rVoltage += tHatch.getBaseMetaTileEntity().getInputVoltage();
			}
		}
		for (GT_MetaTileEntity_Hatch_EnergyMulti tHatch : mEnergyHatchesMulti) {
			if (isValidMetaTileEntity(tHatch)) {
				rVoltage += tHatch.getBaseMetaTileEntity().getInputVoltage() * tHatch.Amp;
			}
		}
		for (GTMTE_LaserEnergy_In tHatch : mLaserIn) {
			if (isValidMetaTileEntity(tHatch)) {
				rVoltage += tHatch.getBaseMetaTileEntity().getInputVoltage() * tHatch.Amp;
			}
		}
		return rVoltage;
	}
	
	@Override
	public boolean addEnergyOutput(long aEU) {
		if (aEU <= 0) {
			return true;
		}
		if (mDynamoHatches.size() > 0 || mDynamoHatchesMulti.size() > 0 || mLaserOut.size() > 0) {
			return addEnergyOutputMultipleDynamos(aEU, true);
		}
		return super.addEnergyOutput(aEU);
	}
	
	@Override
	public boolean addEnergyOutputMultipleDynamos(long aEU, boolean aAllowMixedVoltageDynamos) {
		int injected = 0;
		long totalOutput = 0;
		long aFirstVoltageFound = -1;
		boolean aFoundMixedDynamos = false;
		for (GT_MetaTileEntity_Hatch_Dynamo aDynamo : mDynamoHatches) {
			if (aDynamo == null) {
				return false;
			}
			if (isValidMetaTileEntity(aDynamo)) {
				long aVoltage = aDynamo.maxEUOutput();
				long aTotal = aDynamo.maxAmperesOut() * aVoltage;
				// Check against voltage to check when hatch mixing
				if (aFirstVoltageFound == -1) {
					aFirstVoltageFound = aVoltage;
				} else {
					//Long time calculation
					if (aFirstVoltageFound != aVoltage) {
						aFoundMixedDynamos = true;
					}
				}
				totalOutput += aTotal;
			}
		}
		
		for (GTMTE_LaserEnergy_Out aDynamo : mLaserOut) {
			if (aDynamo == null) {
				return false;
			}
			if (isValidMetaTileEntity(aDynamo)) {
				long aVoltage = aDynamo.maxEUOutput();
				long aTotal = aDynamo.maxAmperesOut() * aVoltage;
				// Check against voltage to check when hatch mixing
				if (aFirstVoltageFound == -1) {
					aFirstVoltageFound = aVoltage;
				} else {
					//Long time calculation
					if (aFirstVoltageFound != aVoltage) {
						aFoundMixedDynamos = true;
					}
				}
				totalOutput += aTotal;
			}
		}
		
		for (GT_MetaTileEntity_Hatch_DynamoMulti aDynamo : mDynamoHatchesMulti) {
			if (aDynamo == null) {
				return false;
			}
			if (isValidMetaTileEntity(aDynamo)) {
				long aVoltage = aDynamo.maxEUOutput();
				long aTotal = aDynamo.maxAmperesOut() * aVoltage;
				// Check against voltage to check when hatch mixing
				if (aFirstVoltageFound == -1) {
					aFirstVoltageFound = aVoltage;
				} else {
					//Long time calculation
					if (aFirstVoltageFound != aVoltage) {
						aFoundMixedDynamos = true;
					}
				}
				totalOutput += aTotal;
			}
		}
		
		if (totalOutput < aEU || (aFoundMixedDynamos && !aAllowMixedVoltageDynamos)) {
			explodeMultiblock();
			return false;
		}
		
		//xEUt *= 4;//this is effect of everclocking
		for (GT_MetaTileEntity_Hatch_Dynamo aDynamo : mDynamoHatches) {
			injected = EnergyHelper.getInjected(aEU, injected, isValidMetaTileEntity(aDynamo), aDynamo.maxEUOutput(), aDynamo.maxAmperesOut(), aDynamo.getBaseMetaTileEntity());
		}
		for (GTMTE_LaserEnergy_Out aDynamo : mLaserOut) {
			injected = EnergyHelper.getInjected(aEU, injected, isValidMetaTileEntity(aDynamo), aDynamo.maxEUOutput(), aDynamo.maxAmperesOut(), aDynamo.getBaseMetaTileEntity());
		}
		for (GT_MetaTileEntity_Hatch_DynamoMulti aDynamo : mDynamoHatchesMulti) {
			injected = EnergyHelper.getInjected(aEU, injected, isValidMetaTileEntity(aDynamo), aDynamo.maxEUOutput(), aDynamo.maxAmperesOut(), aDynamo.getBaseMetaTileEntity());
		}
		return injected > 0;
	}
	
	public long getMaxInputVoltage() {
		long rVoltage = 0;
		for (GT_MetaTileEntity_Hatch_Energy tHatch : mEnergyHatches) {
			if (isValidMetaTileEntity(tHatch)) {
				rVoltage += tHatch.getBaseMetaTileEntity().getInputVoltage() * tHatch.mAmpers;
			}
		}
		for (GTMTE_LaserEnergy_In tHatch : mLaserIn) {
			if (isValidMetaTileEntity(tHatch)) {
				rVoltage += tHatch.getBaseMetaTileEntity().getInputVoltage() * tHatch.Amp;
			}
		}
		for (GT_MetaTileEntity_Hatch_EnergyMulti tHatch : mEnergyHatchesMulti) {
			if (isValidMetaTileEntity(tHatch)) {
				rVoltage += tHatch.getBaseMetaTileEntity().getInputVoltage() * tHatch.Amp;
			}
		}
		return rVoltage;
	}
	
	public long getMaxOutputVoltage() {
		long rVoltage = 0;
		for (GT_MetaTileEntity_Hatch_Dynamo tHatch : mDynamoHatches) {
			if (isValidMetaTileEntity(tHatch)) {
				rVoltage += tHatch.getBaseMetaTileEntity().getOutputVoltage() * tHatch.mAmpers;
			}
		}
		for (GTMTE_LaserEnergy_Out tHatch : mLaserOut) {
			if (isValidMetaTileEntity(tHatch)) {
				rVoltage += tHatch.getBaseMetaTileEntity().getOutputVoltage() * tHatch.Amp;
			}
		}
		for (GT_MetaTileEntity_Hatch_DynamoMulti tHatch : mDynamoHatchesMulti) {
			if (isValidMetaTileEntity(tHatch)) {
				rVoltage += tHatch.getBaseMetaTileEntity().getOutputVoltage() * tHatch.Amp;
			}
		}
		return rVoltage;
	}
	
	public int getTierEnergyHatch() {
		int aTier = 0;
		for (GT_MetaTileEntity_Hatch_Energy tEHatch : mEnergyHatches) {
			if (isValidMetaTileEntity(tEHatch)) {
				if (aTier < tEHatch.mTier) aTier = tEHatch.mTier;
			}
		}
		for (GTMTE_LaserEnergy_In tEHatch : mLaserIn) {
			if (isValidMetaTileEntity(tEHatch)) {
				if (aTier < tEHatch.mTier) aTier = tEHatch.mTier;
			}
		}
		for (GT_MetaTileEntity_Hatch_EnergyMulti tEHatch : mEnergyHatchesMulti) {
			if (isValidMetaTileEntity(tEHatch)) {
				if (aTier < tEHatch.mTier) aTier = tEHatch.mTier;
			}
		}
		return aTier;
	}
	
	@Override
	public void saveNBTData(NBTTagCompound aNBT) {
		aNBT.setByte("mRecipeMode", mRecipeMode);
		aNBT.setInteger("modeBuses", this.modeBuses);
		aNBT.setByte("eRotation", (byte) this.mExtendedFacing.getRotation().getIndex());
		aNBT.setByte("eFlip", (byte) this.mExtendedFacing.getFlip().getIndex());
		super.saveNBTData(aNBT);
	}
	
	@Override
	public void loadNBTData(NBTTagCompound aNBT) {
		this.mRecipeMode           = aNBT.getByte("mRecipeMode");
		this.modeBuses             = aNBT.getInteger("modeBuses");
		this.mExtendedFacing       = ExtendedFacing.of(
				ForgeDirection.getOrientation(getBaseMetaTileEntity().getFrontFacing()),
				Rotation.byIndex(aNBT.getByte("eRotation")),
				Flip.byIndex(aNBT.getByte("eFlip"))
		);
		super.loadNBTData(aNBT);
	}
	
	public int getPollutionPerTick(ItemStack aStack) {
		return 0;
	}
	
	public abstract boolean machineStructure(IGregTechTileEntity thisController);
	
	@Override
	public boolean checkMachine(IGregTechTileEntity thisController, ItemStack guiSlotItem) {
		clearHatches();
		boolean check = machineStructure(thisController);
		RECIPE_BUILDER.updateHatches();
		return check;
	}
	
	public void clearHatches() {
		mLaserIn.clear();
		mLaserOut.clear();
	}
	
	@Override
	public void explodeMultiblock() {
		for (MetaTileEntity tTileEntity : mLaserOut) tTileEntity.getBaseMetaTileEntity().doExplosion(V[8]);
		for (MetaTileEntity tTileEntity : mLaserIn) tTileEntity.getBaseMetaTileEntity().doExplosion(V[8]);
		super.explodeMultiblock();
	}
	
	@Override
	public boolean drainEnergyInput(long aEU) {
		for (GTMTE_LaserEnergy_In tHatch : mLaserIn) {
			if (isValidMetaTileEntity(tHatch)) {
				if (tHatch.getBaseMetaTileEntity().decreaseStoredEnergyUnits(aEU, false)) return true;
			}
		}
		return super.drainEnergyInput(aEU);
	}
	
	@Override
	public boolean addToMachineList(IGregTechTileEntity aTileEntity, int aBaseCasingIndex) {
		
		if (aTileEntity == null) return false;
		IMetaTileEntity aMetaTileEntity = aTileEntity.getMetaTileEntity();
		if (aMetaTileEntity == null) return false;
		if (aMetaTileEntity instanceof GT_MetaTileEntity_Hatch) {
			((GT_MetaTileEntity_Hatch) aMetaTileEntity).updateTexture(aBaseCasingIndex);
		}
		if (aMetaTileEntity instanceof GTMTE_LaserEnergy_In)
			return mLaserIn.add((GTMTE_LaserEnergy_In) aMetaTileEntity);
		if (aMetaTileEntity instanceof GTMTE_LaserEnergy_Out)
			return mLaserOut.add((GTMTE_LaserEnergy_Out) aMetaTileEntity);
		
		return super.addToMachineList(aTileEntity, aBaseCasingIndex);
	}
	
	@Override
	public boolean addEnergyInputToMachineList(IGregTechTileEntity aTileEntity, int aBaseCasingIndex) {
		if (aTileEntity == null) {
			return false;
		}
		IMetaTileEntity aMetaTileEntity = aTileEntity.getMetaTileEntity();
		if (aMetaTileEntity == null) return false;
		if (aMetaTileEntity instanceof GTMTE_LaserEnergy_In) {
			((GTMTE_LaserEnergy_In) aMetaTileEntity).updateTexture(aBaseCasingIndex);
			return mLaserIn.add((GTMTE_LaserEnergy_In) aMetaTileEntity);
		}
		return super.addEnergyInputToMachineList(aTileEntity, aBaseCasingIndex);
	}
	
	@Override
	public boolean addDynamoToMachineList(IGregTechTileEntity aTileEntity, int aBaseCasingIndex) {
		if (aTileEntity == null) {
			return false;
		}
		IMetaTileEntity aMetaTileEntity = aTileEntity.getMetaTileEntity();
		if (aMetaTileEntity == null) return false;
		if (aMetaTileEntity instanceof GTMTE_LaserEnergy_Out) {
			((GTMTE_LaserEnergy_Out) aMetaTileEntity).updateTexture(aBaseCasingIndex);
			return mLaserOut.add((GTMTE_LaserEnergy_Out) aMetaTileEntity);
		}
		return super.addDynamoToMachineList(aTileEntity, aBaseCasingIndex);
	}
	
	protected abstract MultiBlockTooltipBuilder createTooltip();
	
	protected MultiBlockTooltipBuilder getTooltip() {
		int tId = getBaseMetaTileEntity().getMetaTileID();
		MultiBlockTooltipBuilder tooltip = tooltips.get(tId);
		if (tooltip == null) {
			tooltip = createTooltip();
			tooltips.set(tId, tooltip);
		}
		return tooltip;
	}
	
	@Override
	public String[] getDescription() {
		if (getTooltip() == null) return new String[]{"Error Description"};
		if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
			return getTooltip().getControlInfo();
		} else if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			return getTooltip().getStructureInformation();
		} else {
			return getTooltip().getInformation();
		}
	}
	
	@Override
	public String[] getStructureDescription(ItemStack stackSize) {
		String[] desc;
		if (getTooltip() != null) {
			desc    = new String[getTooltip().getStructureInformation().length];
			desc[0] = EnumChatFormatting.RED + holo_details.get() + ":";
			for (int i = 1; i < getTooltip().getStructureInformation().length; i++) {
				desc[i] = getTooltip().getStructureInformation()[i];
			}
		} else {
			desc    = new String[2];
			desc[0] = EnumChatFormatting.RED + holo_details.get() + ":";
			desc[1] = "No found description";
		}
		return desc;
	}
	
	public abstract IStructureDefinition<MULTIS> getStructureDefinition();
	
	@Override
	public abstract IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity);
	
	@SuppressWarnings("unchecked")
	private IStructureDefinition<GTMTE_Impact_BlockBase<MULTIS>> getCastedStructureDefinition() {
		return (IStructureDefinition<GTMTE_Impact_BlockBase<MULTIS>>) getStructureDefinition();
	}
	
	protected final boolean buildPiece(String piece, ItemStack trigger, boolean hintOnly, int horizontalOffset, int verticalOffset, int depthOffset) {
		IGregTechTileEntity tTile = getBaseMetaTileEntity();
		return getCastedStructureDefinition().buildOrHints(this, trigger, piece, tTile.getWorld(), getExtendedFacing(), tTile.getXCoord(), tTile.getYCoord(), tTile.getZCoord(), horizontalOffset, verticalOffset, depthOffset, hintOnly);
	}
	
	protected final boolean buildPiece(ItemStack trigger, boolean hintOnly, int x, int y, int z) {
		return buildPiece("main", trigger, hintOnly, x, y, z);
	}
	
	protected final boolean checkPiece(String piece, int horizontalOffset, int verticalOffset, int depthOffset) {
		IGregTechTileEntity tTile = getBaseMetaTileEntity();
		return getCastedStructureDefinition().check(this, piece, tTile.getWorld(), getExtendedFacing(), tTile.getXCoord(), tTile.getYCoord(), tTile.getZCoord(), horizontalOffset, verticalOffset, depthOffset, !mMachine);
	}
	
	protected final boolean checkPiece(int x, int y, int z) {
		return checkPiece("main", x, y, z);
	}
	
	@Override
	public ExtendedFacing getExtendedFacing() {
		return mExtendedFacing;
	}
	
	@Override
	public void setExtendedFacing(ExtendedFacing newExtendedFacing) {
		if (mExtendedFacing != newExtendedFacing) {
			if (mMachine) stopMachine();
			mExtendedFacing = newExtendedFacing;
			IGregTechTileEntity base = getBaseMetaTileEntity();
			mMachine = false;
			mUpdate  = 100;
			if (getBaseMetaTileEntity().isServerSide()) {
				ImpactAPI.sendAlignment(
						(IAlignmentProvider) base,
						new NetworkRegistry.TargetPoint(base.getWorld().provider.dimensionId, base.getXCoord(), base.getYCoord(), base.getZCoord(), 512)
				);
			} else {
				base.issueTextureUpdate();
			}
		}
	}
	
	@Override
	public boolean checkRecipe(ItemStack itemStack) {
		IGregTechTileEntity te = getBaseMetaTileEntity();
		if (te != null && te.isClientSide()) return false;
		
		MultiBlockRecipeBuilder<?> recipeBuilder = RECIPE_BUILDER.start().checkItemsBySeparateBus();
		if (modeBuses == 0) {
			for (int indexBus = 0; indexBus < recipeBuilder.startSeparateRecipe(); indexBus++) {
				boolean isOk = checkRecipe(recipeBuilder.clear(), indexBus);
				if (isOk) return true;
			}
		} else {
			return checkRecipe(recipeBuilder.clear(), -1);
		}
		return false;
	}
	
	public boolean checkRecipe(MultiBlockRecipeBuilder<?> recipeBuilder, int indexBus) {
		return false;
	}
	
	@Override
	public void onFacingChange() {
		toolSetDirection(ForgeDirection.getOrientation(getBaseMetaTileEntity().getFrontFacing()));
	}
	
	@Override
	public IAlignmentLimits getAlignmentLimits() {
		return mLimits;
	}
	
	protected void setAlignmentLimits(IAlignmentLimits mLimits) {
		this.mLimits = mLimits;
	}
	
	protected IAlignmentLimits getInitialAlignmentLimits() {
		return (d, r, f) -> r.isNotRotated() && f.isNotFlipped();
	}
	
	@Override
	public void onFirstTick(IGregTechTileEntity aBaseMetaTileEntity) {
		super.onFirstTick(aBaseMetaTileEntity);
		if (aBaseMetaTileEntity.isClientSide())
			ImpactAPI.queryAlignment((IAlignmentProvider) aBaseMetaTileEntity);
	}
}
