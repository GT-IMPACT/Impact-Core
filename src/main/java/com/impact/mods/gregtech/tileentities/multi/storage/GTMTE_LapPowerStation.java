package com.impact.mods.gregtech.tileentities.multi.storage;

import com.impact.mods.gregtech.blocks.Casing_Helper;
import com.impact.mods.gregtech.tileentities.hatches.lasers.GTMTE_LaserEnergy_In;
import com.impact.mods.gregtech.tileentities.hatches.lasers.GTMTE_LaserEnergy_Out;
import com.impact.util.string.MultiBlockTooltipBuilder;
import com.impact.util.vector.Vector3i;
import com.impact.util.vector.Vector3ic;
import gregtech.api.enums.Textures;
import gregtech.api.gui.GT_GUIContainer_MultiMachine;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.*;
import gregtech.api.render.TextureFactory;
import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.input.Keyboard;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.impact.loader.ItemRegistery.IGlassBlock;
import static com.impact.loader.ItemRegistery.lscLapotronicEnergyUnit;
import static com.impact.mods.gregtech.enums.Texture.Icons.LPS;
import static com.impact.mods.gregtech.enums.Texture.Icons.LPS_ACTIVE;
import static net.minecraft.util.EnumChatFormatting.*;

/**
 * Made by Kekzdealer Edit by 4gname
 */

public class GTMTE_LapPowerStation extends GT_MetaTileEntity_MultiBlockBase {
	
	public static final long L_IV = 0x59682f00L * 4L;
	public static final long L_LuV = 0x165a0bc00L * 4L;
	public static final long L_ZPM = 0xdf8475800L * 4L;
	public static final long L_UV = 0x8bb2c97000L * 4L;
	public static final long L_UHV = 0x45d964b800L * 4L;
	public static final long L_UEV = 0x2ba7def3000L * 4L;
	public static final long E_IV = 0x247a6100L * 4L;
	
	private static final Block LSC_PART = lscLapotronicEnergyUnit;
	private static final Block CASING = Casing_Helper.sCaseCore2;
	private static final int CASING_META = 8;
	private static final ITexture INDEX_CASE = Textures.BlockIcons.casingTexturePages[3][16 + CASING_META];
	private static final int CASING_TEXTURE_ID = CASING_META + 16 + 128 * 3;
	
	private final Set<GTMTE_LaserEnergy_In> mLaserIn = new HashSet<>();
	private final Set<GTMTE_LaserEnergy_Out> mLaserOut = new HashSet<>();
	// Count the amount of capacitors of each tier in each slot (translate with meta - 1)
	private final int[] capacitors = new int[8];
	public long capacity = 0;
	public long stored = 0;
	public long passiveDischargeAmount = 0;
	public long intputLastTick = 0;
	public long outputLastTick = 0;
	
	public GTMTE_LapPowerStation(int aID, String aNameRegional) {
		super(aID, "impact.multimachine.supercapacitor", aNameRegional);
	}
	
	public GTMTE_LapPowerStation(String aName) {
		super(aName);
	}
	
	@Override
	public IMetaTileEntity newMetaEntity(IGregTechTileEntity var1) {
		return new GTMTE_LapPowerStation(super.mName);
	}
	
	@Override
	public String[] getDescription() {
		final MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder("lsc");
		b
				.addInfo("info.0", "Multi battery buffer!")
				.addInfo("info.1", "Very big battery")
				.addTypeMachine("name", "Battery Buffer")
				.addSeparator()
				.beginStructureBlock(5, 4, 5)
				.addController()
				.addDynamoHatch()
				.addEnergyHatch()
				.addMaintenanceHatch()
				.addOtherStructurePart("other.0", "Lapotronic Capacitor Base", "other.1", "5x2x5 base (at least 17x)")
				.addOtherStructurePart("other.2", "Capacitors", "other.3", "Center 3x(1-15)x3 above base (9-135 blocks)")
				.addOtherStructurePart("other.4", "I-Glass", "other.5", "41-265x, Encase capacitor pillar")
				.signAndFinalize();
		if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			return b.getInformation();
		} else {
			return b.getStructureInformation();
		}
	}
	
	@Override
	public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
		return aSide == aFacing ? new ITexture[]{INDEX_CASE, TextureFactory.of(aActive ? LPS_ACTIVE : LPS)} : new ITexture[]{INDEX_CASE};
	}
	
	public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		return new GT_GUIContainer_MultiMachine(aPlayerInventory, aBaseMetaTileEntity, this.getLocalName(), "MultiblockDisplay.png");
	}
	
	@Override
	public boolean isCorrectMachinePart(ItemStack stack) {
		return true;
	}
	
	@Override
	public boolean checkRecipe(ItemStack stack) {
		this.mProgresstime       = 1;
		this.mMaxProgresstime    = 1;
		this.mEUt                = 0;
		this.mEfficiencyIncrease = 10000;
		return true;
	}
	
	public Vector3ic rotateOffsetVector(Vector3ic forgeDirection, int x, int y, int z) {
		final Vector3i offset = new Vector3i();
		
		// either direction on z-axis
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
		// either direction on x-axis
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
		// either direction on y-axis
		if (forgeDirection.y() == -1) {
			offset.x = x;
			offset.y = z;
			offset.z = y;
		}
		
		return offset;
	}
	
	@Override
	public boolean checkMachine(IGregTechTileEntity thisController, ItemStack guiSlotItem) {
		// Figure out the vector for the direction the back face of the controller is facing
		final Vector3ic forgeDirection = new Vector3i(
				ForgeDirection.getOrientation(thisController.getBackFacing()).offsetX,
				ForgeDirection.getOrientation(thisController.getBackFacing()).offsetY,
				ForgeDirection.getOrientation(thisController.getBackFacing()).offsetZ
		);
		boolean formationChecklist = true;
		int minCasingAmount = 16;
		int firstGlassMeta = -1;
		// Reset capacitor counts
		Arrays.fill(capacitors, 0);
		// Clear TT hatches
		mEnergyHatchesMulti.clear();
		mDynamoHatchesMulti.clear();
		mLaserIn.clear();
		mLaserOut.clear();
		
		long tempCapacity = 0;
		
		// Capacitor base
		for (int Y = 0; Y <= 1; Y++) {
			for (int X = -2; X <= 2; X++) {
				for (int Z = 0; Z >= -4; Z--) {
					if (X == 0 && Y == 0 && Z == 0) {
						continue; // Skip controller
					}
					
					final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Y, Z);
					final IGregTechTileEntity currentTE = thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
					
					// Tries to add TE as either of those kinds of hatches.
					// The number is the texture index number for the texture that needs to be painted over the hatch texture
					if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
							&& !this.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)
							&& !this.addDynamoToMachineList(currentTE, CASING_TEXTURE_ID)) {
						
						// If it's not a hatch, is it the right casing for this machine? Check block and block meta.
						if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
								&& (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == CASING_META)) {
							// Seems to be valid casing. Decrement counter.
							minCasingAmount--;
						} else {
							formationChecklist = false;
						}
					}
				}
			}
		}
		// Capacitor units
		int firstGlassHeight = 3; // Initialize at basic height (-1 because it's an offset)
		for (int X = -1; X <= 1; X++) {
			for (int Z = -1; Z >= -3; Z--) {
				// Y has to be the innermost loop to properly deal with the dynamic height.
				// This way each "pillar" of capacitors is checked from bottom to top until it hits glass.
				for (int Y = 2; Y <= 17; Y++) {
					final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Y, Z);
					
					final int meta = thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z());
					if (thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == LSC_PART && (meta > 0)) {
						// Add capacity
						switch (meta) {
							case 1:
								tempCapacity += L_IV;
								break;
							case 2:
								tempCapacity += L_LuV;
								break;
							case 3:
								tempCapacity += L_ZPM;
								break;
							case 4:
								tempCapacity += L_UV;
								break;
							case 6:
								tempCapacity += E_IV;
								break;
							case 7:
								tempCapacity += L_UHV;
								break;
							case 8:
								tempCapacity += L_UEV;
								break;
						}
						capacitors[meta - 1]++;
					} else if (thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == IGlassBlock) {
						firstGlassHeight = Y;
						break;
					} else {
						formationChecklist = false;
					}
				}
			}
		}
		// Glass shell
		// Make Y the outermost loop, so each layer is checked completely before moving up
		for (int Y = 2; Y <= firstGlassHeight; Y++) {
			for (int X = -2; X <= 2; X++) {
				for (int Z = 0; Z >= -4; Z--) {
					final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Y, Z);
					final int meta = thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z());
					// Check only outer ring, except when on roof height
					if ((Y < firstGlassHeight)) {
						if (X == -2 || X == 2 || Z == 0 || Z == 4) {
							if (thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == IGlassBlock) {
								if (firstGlassMeta == -1) {
									firstGlassMeta = meta;
								} else if (meta != firstGlassMeta) {
									formationChecklist = false;
								}
							} else {
								formationChecklist = false;
							}
						}
					} else {
						if (thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == IGlassBlock) {
							if (meta != firstGlassMeta) {
								formationChecklist = false;
							}
						} else {
							formationChecklist = false;
						}
					}
				}
			}
		}
		
		if (minCasingAmount > 0) {
			formationChecklist = false;
		}
		capacity               = tempCapacity;
		passiveDischargeAmount = 0;
		return formationChecklist;
	}
	
	@Override
	public boolean addEnergyInputToMachineList(IGregTechTileEntity te, int aBaseCasingIndex) {
		if (te == null) {
			return false;
		} else {
			final IMetaTileEntity mte = te.getMetaTileEntity();
			if (mte instanceof GT_MetaTileEntity_Hatch_Energy) {
				// Add GT hatches
				((GT_MetaTileEntity_Hatch) mte).updateTexture(aBaseCasingIndex);
				return super.mEnergyHatches.add((GT_MetaTileEntity_Hatch_Energy) mte);
			} else if (mte instanceof GTMTE_LaserEnergy_In) {
				((GT_MetaTileEntity_Hatch) mte).updateTexture(aBaseCasingIndex);
				return mLaserIn.add((GTMTE_LaserEnergy_In) mte);
			} else if (mte instanceof GT_MetaTileEntity_Hatch_EnergyMulti) {
				// Add TT hatches
				((GT_MetaTileEntity_Hatch) mte).updateTexture(aBaseCasingIndex);
				return mEnergyHatchesMulti.add((GT_MetaTileEntity_Hatch_EnergyMulti) mte);
			} else {
				return false;
			}
		}
	}
	
	@Override
	public boolean addDynamoToMachineList(IGregTechTileEntity te, int aBaseCasingIndex) {
		if (te == null) {
			return false;
		} else {
			final IMetaTileEntity mte = te.getMetaTileEntity();
			if (mte instanceof GT_MetaTileEntity_Hatch_Dynamo) {
				// Add GT hatches
				((GT_MetaTileEntity_Hatch) mte).updateTexture(aBaseCasingIndex);
				return super.mDynamoHatches.add((GT_MetaTileEntity_Hatch_Dynamo) mte);
			} else if (mte instanceof GTMTE_LaserEnergy_Out) {
				((GT_MetaTileEntity_Hatch) mte).updateTexture(aBaseCasingIndex);
				return mLaserOut.add((GTMTE_LaserEnergy_Out) mte);
			} else if (mte instanceof GT_MetaTileEntity_Hatch_DynamoMulti) {
				// Add TT hatches
				((GT_MetaTileEntity_Hatch) mte).updateTexture(aBaseCasingIndex);
				return mDynamoHatchesMulti.add((GT_MetaTileEntity_Hatch_DynamoMulti) mte);
			} else {
				return false;
			}
		}
	}
	
	@Override
	public boolean onRunningTick(ItemStack stack) {
		// Reset I/O cache
		intputLastTick = 0;
		outputLastTick = 0;
		
		// Draw energy from GT hatches
		for (GT_MetaTileEntity_Hatch_Energy eHatch : super.mEnergyHatches) {
			if (eHatch == null || eHatch.getBaseMetaTileEntity().isInvalidTileEntity()) {
				continue;
			}
			final long power = getPowerToDraw(eHatch.maxEUInput() * eHatch.maxAmperesIn());
			if (eHatch.getEUVar() >= power) {
				eHatch.setEUVar(eHatch.getEUVar() - power);
				stored += power;
				intputLastTick += power;
			}
		}
		// Output energy to GT hatches
		for (GT_MetaTileEntity_Hatch_Dynamo eDynamo : super.mDynamoHatches) {
			if (eDynamo == null || eDynamo.getBaseMetaTileEntity().isInvalidTileEntity()) {
				continue;
			}
			final long power = getPowerToPush(eDynamo.maxEUOutput() * eDynamo.maxAmperesOut());
			if (power <= eDynamo.maxEUStore() - eDynamo.getEUVar()) {
				eDynamo.setEUVar(eDynamo.getEUVar() + power);
				stored -= power;
				outputLastTick += power;
			}
		}
		
		for (GTMTE_LaserEnergy_In eHatch : mLaserIn) {
			if (eHatch == null || eHatch.getBaseMetaTileEntity() == null || eHatch.getBaseMetaTileEntity().isInvalidTileEntity()) {
				continue;
			}
			final long power = getPowerToDraw(eHatch.maxEUInput() * eHatch.maxAmperesIn());
			if (eHatch.getEUVar() >= power) {
				eHatch.setEUVar(eHatch.getEUVar() - power);
				stored += power;
				intputLastTick += power;
			}
		}
		
		for (GTMTE_LaserEnergy_Out eDynamo : mLaserOut) {
			if (eDynamo == null || eDynamo.getBaseMetaTileEntity() == null ||  eDynamo.getBaseMetaTileEntity().isInvalidTileEntity()) {
				continue;
			}
			final long power = getPowerToPush(eDynamo.maxEUOutput() * eDynamo.maxAmperesOut());
			if (power <= eDynamo.maxEUStore() - eDynamo.getEUVar()) {
				eDynamo.setEUVar(eDynamo.getEUVar() + power);
				stored -= power;
				outputLastTick += power;
			}
		}
		
		// Draw energy from TT hatches
		for (GT_MetaTileEntity_Hatch_EnergyMulti eHatch : mEnergyHatchesMulti) {
			if (eHatch == null || eHatch.getBaseMetaTileEntity().isInvalidTileEntity()) {
				continue;
			}
			final long power = getPowerToDraw(eHatch.maxEUInput() * eHatch.maxAmperesIn());
			if (eHatch.getEUVar() >= power) {
				eHatch.setEUVar(eHatch.getEUVar() - power);
				stored += power;
				intputLastTick += power;
			}
		}
		// Output energy to TT hatches
		for (GT_MetaTileEntity_Hatch_DynamoMulti eDynamo : mDynamoHatchesMulti) {
			if (eDynamo == null || eDynamo.getBaseMetaTileEntity().isInvalidTileEntity()) {
				continue;
			}
			final long power = getPowerToPush(eDynamo.maxEUOutput() * eDynamo.maxAmperesOut());
			if (power <= eDynamo.maxEUStore() - eDynamo.getEUVar()) {
				eDynamo.setEUVar(eDynamo.getEUVar() + power);
				stored -= power;
				outputLastTick += power;
			}
		}
		return true;
	}
	
	/**
	 * Calculate how much EU to draw from an Energy Hatch
	 *
	 * @param hatchWatts Hatch amperage * voltage
	 * @return EU amount
	 */
	private long getPowerToDraw(long hatchWatts) {
		final long remcapActual = capacity - stored;
		final long recampLimited = remcapActual > 0 ? remcapActual : 0;
		return Math.min(hatchWatts, recampLimited);
	}
	
	/**
	 * Calculate how much EU to push into a Dynamo Hatch
	 *
	 * @param hatchWatts Hatch amperage * voltage
	 * @return EU amount
	 */
	private long getPowerToPush(long hatchWatts) {
		final long remStoredLimited = stored > 0 ? stored : 0;
		return Math.min(hatchWatts, remStoredLimited);
	}
	
	@Override
	public String[] getInfoData() {
		final ArrayList<String> ll = new ArrayList<>();
		ll.add("Used Capacity: ");
		ll.add(GREEN + NumberFormat.getNumberInstance().format(stored) + RESET + " EU");
		ll.add("Total Capacity: ");
		ll.add(YELLOW + NumberFormat.getNumberInstance().format(capacity) + RESET + " EU");
		ll.add("EU IN: ");
		ll.add(GREEN + NumberFormat.getNumberInstance().format(intputLastTick) + RESET + " EU/t");
		ll.add("EU OUT: ");
		ll.add(RED + NumberFormat.getNumberInstance().format(outputLastTick) + RESET + " EU/t");
		ll.add("Maintenance: " + ((super.getRepairStatus() == super.getIdealStatus()) ? GREEN + "Good " + YELLOW + mEfficiency / 100.0F + " %" + RESET : RED + "Has Problems " + mEfficiency / 100.0F + " %" + RESET));
		final String[] a = new String[ll.size()];
		return ll.toArray(a);
	}
	
	@Override
	public void saveNBTData(NBTTagCompound nbt) {
		nbt = (nbt == null) ? new NBTTagCompound() : nbt;
		nbt.setLong("capacity", capacity);
		nbt.setLong("stored", stored);
		super.saveNBTData(nbt);
	}
	
	@Override
	public void loadNBTData(NBTTagCompound nbt) {
		nbt      = (nbt == null) ? new NBTTagCompound() : nbt;
		capacity = nbt.getLong("capacity");
		stored   = nbt.getLong("stored");
		super.loadNBTData(nbt);
	}
	
	@Override
	public boolean isGivingInformation() {
		return true;
	}
	
	@Override
	public int getMaxEfficiency(ItemStack stack) {
		return 10000;
	}
	
	@Override
	public int getPollutionPerTick(ItemStack stack) {
		return 0;
	}
	
	@Override
	public int getDamageToComponent(ItemStack stack) {
		return 0;
	}
	
	@Override
	public boolean explodesOnComponentBreak(ItemStack stack) {
		return false;
	}
}