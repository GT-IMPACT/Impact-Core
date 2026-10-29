package com.impact.mods.gregtech.tileentities.multi.generators.green;

import static net.minecraft.util.StatCollector.translateToLocalFormatted;

import cpw.mods.fml.common.Optional;
import java.util.List;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.impact.common.item.ITieredDamagedItems;
import com.impact.common.te.TE_WindMill;
import com.impact.loader.ItemRegistery;
import com.impact.mods.gregtech.blocks.Casing_Helper;
import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_Impact_BlockBase;
import com.impact.util.string.MultiBlockTooltipBuilder;
import com.impact.util.vector.Structure;
import com.impact.util.vector.Vector3i;
import com.impact.util.vector.Vector3ic;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Dynamo;
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
	private byte rotorFacing = -1;
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
		if (!isUsableRotor(getRotorStack())) {
			stopRotor();
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
		rotorFacing = aNBT.hasKey("rotorFacing") ? aNBT.getByte("rotorFacing") : -1;
		wind = null;
		speedRotor    = aNBT.getInteger("speedRotor");
		mOutputSalary = aNBT.getInteger("mOutputSalary");
		mCapacity     = aNBT.getInteger("mCapacity");
	}
	
	@Override
	public void saveNBTData(NBTTagCompound aNBT) {
		super.saveNBTData(aNBT);
		aNBT.setByte("rotorFacing", rotorFacing);
		aNBT.setInteger("speedRotor", speedRotor);
		aNBT.setInteger("mOutputSalary", mOutputSalary);
		aNBT.setInteger("mCapacity", mCapacity);
	}
	
	@Override
	public void onPostTick(IGregTechTileEntity iAm, long aTick) {
		super.onPostTick(iAm, aTick);
		if (!iAm.isServerSide()) return;

		if (aTick == 20 || aTick % (20 * 60 * 60) == 0) {
			speedRotor = (int) WorldData.get(iAm.getWorld()).windSim.getWindAt(iAm.getYCoord() * 2);
		}
		if (aTick % 20 == 0) setRotor();

		if (!iAm.isActive()) {
			mOutputSalary = 0;
			if (hasLoadedRotor()) wind.setSpeed(0);
			return;
		}
		if (aTick % 20 != 0) return;

		ItemStack stack = getRotorStack();
		if (wind == null || !isUsableRotor(stack)) {
			stopRotor();
			iAm.setActive(false);
			return;
		}
		ITieredDamagedItems rotor = (ITieredDamagedItems) stack.getItem();
		int damage = stack.getTagCompound().getInteger("rotorDamage");
		if (IC2.random.nextFloat() >= 0.6F) {
			stack.getTagCompound().setInteger("rotorDamage", --damage);
			mInputBusses.get(0).updateSlots();
		}
		if (damage <= 0) {
			mInputBusses.get(0).mInventory[0] = null;
			mInputBusses.get(0).updateSlots();
			stopRotor();
			iAm.setActive(false);
			return;
		}
		wind.setRGB(rotor.getColor(stack).getRGB());
		wind.setRotorHidden(false);
		wind.setSpeed(speedRotor);
		int tier = rotor.getCoefficient(stack) + 1;
		mOutputSalary = Math.max(speedRotor << tier, 0);
		mCapacity = (int) Math.min(MAX_CAPACITY, (long) mCapacity + (long) mOutputSalary * 20);
	}

	private ItemStack getRotorStack() {
		return mInputBusses.isEmpty() ? null : mInputBusses.get(0).mInventory[0];
	}

	private boolean isUsableRotor(ItemStack stack) {
		return stack != null && stack.stackSize > 0 && stack.getItem() instanceof ITieredDamagedItems
				&& stack.hasTagCompound() && !((ITieredDamagedItems) stack.getItem()).isBroken(stack);
	}

	private void stopRotor() {
		mOutputSalary = 0;
		if (hasLoadedRotor()) {
			wind.setRotorHidden(true);
			wind.setSpeed(0);
		}
	}

	private boolean hasLoadedRotor() {
		if (wind == null) return false;
		World world = wind.getWorldObj();
		if (wind.isInvalid() || world == null || !world.blockExists(wind.xCoord, wind.yCoord, wind.zCoord)
				|| world.getTileEntity(wind.xCoord, wind.yCoord, wind.zCoord) != wind) {
			wind = null;
			return false;
		}
		return true;
	}
	
	public void setSpeed(int speedRotor) {
		this.speedRotor = speedRotor;
	}
	
	private void setRotor() {
		IGregTechTileEntity iAm = getBaseMetaTileEntity();
		if (iAm == null || !iAm.isServerSide()) return;
		byte facing = iAm.getFrontFacing();
		if (facing < 2 || facing > 5) return;
		if (rotorFacing >= 2 && rotorFacing <= 5 && rotorFacing != facing && !removeRotor()) return;
		final Vector3ic offset = getRotorOffset(facing);
		World world = iAm.getWorld();
		int x = iAm.getXCoord() + offset.x();
		int y = iAm.getYCoord() + offset.y();
		int z = iAm.getZCoord() + offset.z();
		wind = null;
		if (!world.blockExists(x, y, z)) return;
		Block block = Structure.getBlock(iAm, offset);
		if (block != ItemRegistery.Wind_rotor) {
			if (!world.isAirBlock(x, y, z)) return;
			Structure.setBlock(iAm, offset, ItemRegistery.Wind_rotor, 3);
		}
		TileEntity tile = Structure.getTE(iAm, offset);
		if (!(tile instanceof TE_WindMill)) return;
		TE_WindMill rotor = (TE_WindMill) tile;
		if (!rotor.attachController(iAm.getXCoord(), iAm.getYCoord(), iAm.getZCoord())) return;
		wind = rotor;
		if (rotorFacing != facing) {
			rotorFacing = facing;
			iAm.markDirty();
		}
		if (wind.getFacing() != facing) {
			wind.setFacing(facing);
			wind.markDirty();
		}
		ItemStack stack = getRotorStack();
		if (isUsableRotor(stack)) {
			wind.setRGB(((ITieredDamagedItems) stack.getItem()).getColor(stack).getRGB());
			wind.setRotorHidden(false);
			wind.setSpeed(iAm.isActive() ? speedRotor : 0);
		} else {
			stopRotor();
		}
	}

	private Vector3ic getRotorOffset(byte facing) {
		ForgeDirection direction = ForgeDirection.getOrientation(facing);
		return new Vector3i(direction.offsetX, 10, direction.offsetZ);
	}

	private boolean removeRotor() {
		IGregTechTileEntity iAm = getBaseMetaTileEntity();
		if (iAm == null || !iAm.isServerSide()) return false;
		if (rotorFacing >= 2 && rotorFacing <= 5) {
			Vector3ic offset = getRotorOffset(rotorFacing);
			World world = iAm.getWorld();
			int x = iAm.getXCoord() + offset.x();
			int y = iAm.getYCoord() + offset.y();
			int z = iAm.getZCoord() + offset.z();
			if (!world.blockExists(x, y, z)) {
				wind = null;
				return false;
			}
			TileEntity tile = world.getTileEntity(x, y, z);
			if (world.getBlock(x, y, z) == ItemRegistery.Wind_rotor && tile instanceof TE_WindMill
					&& ((TE_WindMill) tile).isControlledBy(iAm.getXCoord(), iAm.getYCoord(), iAm.getZCoord())) {
				Structure.setAir(iAm, offset);
			}
		}
		wind = null;
		rotorFacing = -1;
		iAm.markDirty();
		return true;
	}

	public void setWind() {
		setRotor();
	}

	@Override
	public void onFacingChange() {
		super.onFacingChange();
		setRotor();
		IGregTechTileEntity base = getBaseMetaTileEntity();
		if (base != null && base.isServerSide() && wind != null) wind.setSpeed(0);
	}
	
	@Override
	public void onFirstTick(IGregTechTileEntity aBaseMetaTileEntity) {
		setRotor();
		super.onFirstTick(aBaseMetaTileEntity);
	}
	
	@Override
	public void inValidate() {
		removeRotor();
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

    @Override
    @Optional.Method(modid = "Waila")
    public void getWailaBody(ItemStack stack, List<String> tooltip, IWailaDataAccessor accessor,
                             IWailaConfigHandler config) {
        super.getWailaBody(stack, tooltip, accessor, config);
        NBTTagCompound tag = accessor.getNBTData();
        tooltip.add(translateToLocalFormatted("impact.waila.energy.generation",
                GT_Utility.formatNumbers(tag.getInteger("wind_generator.mOutputSalary"))));
        tooltip.add(translateToLocalFormatted("impact.waila.energy.buffer",
                GT_Utility.formatNumbers(tag.getInteger("wind_generator.mCapacity")), GT_Utility.formatNumbers(1_000_000)));
    }

    @Override
    @Optional.Method(modid = "Waila")
    public void getWailaNBTData(EntityPlayerMP player, TileEntity tile, NBTTagCompound tag,
                                World world, int x, int y, int z) {
        super.getWailaNBTData(player, tile, tag, world, x, y, z);
        tag.setInteger("wind_generator.mOutputSalary", mOutputSalary);
        tag.setInteger("wind_generator.mCapacity", mCapacity);
    }
}
