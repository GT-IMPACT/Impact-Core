package com.impact.mods.gregtech.tileentities.multi.generators.green;

import com.impact.common.item.ITieredDamagedItems;
import com.impact.common.te.TE_WindMill;
import com.impact.loader.ItemRegistery;
import com.impact.mods.gregtech.blocks.Casing_Helper;
import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_Impact_BlockBase;
import com.impact.util.string.MultiBlockTooltipBuilder;
import com.impact.util.vector.Structure;
import com.impact.util.vector.Vector3ic;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Dynamo;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GT_Utility;
import ic2.core.IC2;
import ic2.core.WorldData;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import space.impact.api.multiblocks.structure.IStructureDefinition;
import space.impact.api.multiblocks.structure.StructureDefinition;

import static com.impact.mods.gregtech.enums.Texture.Icons.REACTOR_OVERLAY;
import static com.impact.mods.gregtech.enums.Texture.Icons.REACTOR_OVERLAY_ACTIVE;

public class GTMTE_Wind_Generator extends GTMTE_Impact_BlockBase<GTMTE_Wind_Generator> {
	
	public static int MAX_CAPACITY = 1_000_000;
	static IStructureDefinition<GTMTE_Wind_Generator> definition =
			StructureDefinition.<GTMTE_Wind_Generator>builder()
					.addShape("main", new String[][]{
							{"~"},
					}).build();
	public int speedRotor = 10;
	public TE_WindMill wind = null;
	public int mOutputSalary = 0;
	public int mCapacity = 0;
	ITexture INDEX_CASE = Textures.BlockIcons.CASING_BLOCKS[12 + 32];
	
	public GTMTE_Wind_Generator(int aID, String aNameRegional) {
		super(aID, "impact.multis.generator.wind_generator", aNameRegional);
	}
	
	public GTMTE_Wind_Generator(String aName) {
		super(aName);
	}
	
	@Override
	public boolean machineStructure(IGregTechTileEntity iAm) {
		boolean formationChecklist = true;
		
		for (int y = 1; y <= 2; y++) {
			Vector3ic offset = Structure.goBuild(iAm, 0, y, 0);
			IGregTechTileEntity te = Structure.getIGTE(iAm, offset);
			if (!super.addToMachineList(te, 16)) {
				if (Structure.getBlock(iAm, offset) != Casing_Helper.sCaseCore3) {
					formationChecklist = false;
				}
			}
		}
		return formationChecklist;
	}
	
	@Override
	public IStructureDefinition<GTMTE_Wind_Generator> getStructureDefinition() {
		return definition;
	}
	
	@Override
	public void construct(ItemStack itemStack, boolean b) {
		buildPiece(itemStack, b, 0, 0, 0);
	}
	
	@Override
	public boolean checkRecipe(ItemStack itemStack) {
		this.mEfficiency = (10000 - (this.getIdealStatus() - this.getRepairStatus()) * 1000);
		if (mEfficiency < getMaxEfficiency(null)) return false;
		ItemStack is = mInputBusses.get(0).mInventory[0];
		if (is == null || !(is.getItem() instanceof ITieredDamagedItems) || is.stackSize <= 0) {
			wind.setRotorHidden(true);
			wind.setSpeed(0);
			return false;
		}
		this.mMaxProgresstime    = 8;
		this.mEfficiencyIncrease = 10000;
		return true;
	}
	
	private long getPowerToPush(long hatchWatts) {
		final long remStoredLimited = Math.max(mCapacity, 0);
		return Math.min(hatchWatts, remStoredLimited);
	}
	
	@Override
	public boolean onRunningTick(ItemStack aStack) {
		for (GT_MetaTileEntity_Hatch_Dynamo eDynamo : super.mDynamoHatches) {
			if (eDynamo == null || eDynamo.getBaseMetaTileEntity().isInvalidTileEntity()) {
				continue;
			}
			final long power = getPowerToPush(eDynamo.maxEUOutput() * eDynamo.maxAmperesOut());
			if (power <= eDynamo.maxEUStore() - eDynamo.getEUVar()) {
				eDynamo.setEUVar(eDynamo.getEUVar() + power);
				mCapacity -= power;
			}
		}
		return super.onRunningTick(aStack);
	}
	
	@Override
	public IMetaTileEntity newMetaEntity(IGregTechTileEntity iAm) {
		return new GTMTE_Wind_Generator(mName);
	}
	
	@Override
	public void onScrewdriverRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
		super.onScrewdriverRightClick(aSide, aPlayer, aX, aY, aZ);
		int wind = (int) WorldData.get(getBaseMetaTileEntity().getWorld()).windSim.getWindAt(getBaseMetaTileEntity().getYCoord() * 2);
		GT_Utility.sendChatToPlayer(aPlayer, wind + "");
	}
	
	@Override
	public void loadNBTData(NBTTagCompound aNBT) {
		super.loadNBTData(aNBT);
		speedRotor    = aNBT.getInteger("speedRotor");
		mOutputSalary = aNBT.getInteger("mOutputSalary");
		mCapacity     = aNBT.getInteger("mCapacity");
	}
	
	@Override
	public void saveNBTData(NBTTagCompound aNBT) {
		super.saveNBTData(aNBT);
		aNBT.setInteger("speedRotor", speedRotor);
		aNBT.setInteger("mOutputSalary", mOutputSalary);
		aNBT.setInteger("mCapacity", mCapacity);
	}
	
	@Override
	public void onPostTick(IGregTechTileEntity iAm, long aTick) {
		super.onPostTick(iAm, aTick);
		
		if ((aTick == 20 || aTick % (20 * 60 * 60) == 0)) {
			if (iAm.isServerSide()) {
				speedRotor = (int) WorldData.get(iAm.getWorld()).windSim.getWindAt(iAm.getYCoord() * 2);
			}
		}
		if (!iAm.isActive()) {
			mOutputSalary = 0;
			return;
		}
		
		if (iAm.isServerSide() && aTick % 20 == 0) {
			setRotor();
			
			if (mInputBusses.size() <= 0) {
				wind.setRotorHidden(true);
			}
			ItemStack is = mInputBusses.get(0).mInventory[0];
			
			if (is == null || !(is.getItem() instanceof ITieredDamagedItems) || is.stackSize <= 0) {
				wind.setRotorHidden(true);
				iAm.setActive(false);
			} else {
				ITieredDamagedItems rotor = (ITieredDamagedItems) is.getItem();
				if (rotor.isBroken(is) || !is.hasTagCompound()) {
					wind.setRotorHidden(true);
					mInputBusses.get(0).mInventory[0] = null;
					mInputBusses.get(0).updateSlots();
				}
				
				wind.setRotorHidden(false);
				
				if (iAm.isActive()) {
					
					int damage = is.getTagCompound().getInteger("rotorDamage");
					damage = damage - (IC2.random.nextFloat() >= 0.6F && IC2.random.nextInt(damage - 1) > 0 ? 1 : 0);
					is.stackTagCompound.setInteger("rotorDamage", damage);
					
					wind.setRGB(((ITieredDamagedItems) is.getItem()).getColor(is).getRGB());
					int tier = ((ITieredDamagedItems) is.getItem()).getCoefficient(is) + 1;
					mOutputSalary = Math.max(speedRotor << tier, 0);
					mCapacity += mOutputSalary * 20;
					if (mCapacity >= MAX_CAPACITY) mCapacity = MAX_CAPACITY;
					wind.setSpeed(speedRotor);
				} else {
					wind.setSpeed(0);
				}
			}
		}
	}
	
	public void setSpeed(int speedRotor) {
		this.speedRotor = speedRotor;
	}
	
	private void setRotor() {
		IGregTechTileEntity iAm = getBaseMetaTileEntity();
		final Vector3ic offset = rotateOffsetVector(Structure.getForgeDirection(iAm), 0, 10, 1);
		Block block = Structure.getBlock(iAm, offset);
		if (block != ItemRegistery.Wind_rotor) {
			Structure.setBlock(iAm, offset, ItemRegistery.Wind_rotor, 3);
		}
		setWind();
	}
	
	public void setWind() {
		IGregTechTileEntity iAm = getBaseMetaTileEntity();
		if (iAm.isServerSide()) {
			final Vector3ic offset = rotateOffsetVector(Structure.getForgeDirection(iAm), 0, 10, 1);
			TileEntity tile = Structure.getTE(iAm, offset);
			if (tile instanceof TE_WindMill) {
				wind = (TE_WindMill) tile;
				wind.setFacing(iAm.getFrontFacing());
			}
		}
	}
	
	@Override
	public void onFirstTick(IGregTechTileEntity aBaseMetaTileEntity) {
		setRotor();
		super.onFirstTick(aBaseMetaTileEntity);
	}
	
	@Override
	public void inValidate() {
		IGregTechTileEntity iAm = getBaseMetaTileEntity();
		final Vector3ic offset = rotateOffsetVector(Structure.getForgeDirection(iAm), 0, 10, 1);
		Structure.setAir(iAm, offset);
		super.inValidate();
	}
	
	@Override
	public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
		return aSide == aFacing ? new ITexture[]{INDEX_CASE, TextureFactory.of(aActive ? REACTOR_OVERLAY_ACTIVE : REACTOR_OVERLAY)} : new ITexture[]{INDEX_CASE};
	}
	
	@Override
	protected MultiBlockTooltipBuilder createTooltip() {
		final MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder("wind_generator");
		b.addTypeGenerator().signAndFinalize();
		return b;
	}
	
	@Override
	public boolean hasSeparate() {
		return false;
	}
}