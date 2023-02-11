package com.impact.mods.gregtech.tileentities.multi.matrixsystem;

import com.impact.loader.ItemRegistery;
import com.impact.mods.gregtech.blocks.Casing_Helper;
import com.impact.mods.gregtech.gui.matrixsystem.GT_Container_ParametricDiffuser;
import com.impact.mods.gregtech.gui.matrixsystem.GUI_ParametricDiffuser;
import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_Impact_BlockBase;
import com.impact.network.special.ToClient_LaserPush;
import com.impact.util.Utilits;
import com.impact.util.string.MultiBlockTooltipBuilder;
import com.impact.util.vector.Structure;
import com.impact.util.vector.Vector3i;
import com.impact.util.vector.Vector3ic;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_InputBus;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.oredict.OreDictionary;
import space.impact.api.multiblocks.structure.IStructureDefinition;
import space.impact.api.multiblocks.structure.StructureDefinition;

import java.util.Random;

import static net.minecraft.util.EnumChatFormatting.*;
import static space.impact.api.multiblocks.structure.StructureUtility.ofBlock;
import static space.impact.api.multiblocks.structure.StructureUtility.ofBlockAnyMeta;

public class GTMTE_ParametricDiffuser extends GTMTE_Impact_BlockBase<GTMTE_ParametricDiffuser> {
	
	public static Block CASING = Casing_Helper.sCaseCore2;
	public static byte CASING_META = 15;
	static IStructureDefinition<GTMTE_ParametricDiffuser> definition =
			StructureDefinition.<GTMTE_ParametricDiffuser>builder()
					.addShape("main", new String[][]{
							{"     A   A", "     AAAAA", "     AAAAA", "          "},
							{"      AAA ", "AAAAAAAAAA", "~AFFFCCCCA", "AAAAAAAAAA"},
							{"      AAA ", "AAFFFCCCCA", "AD       F", "AACCCCCCCA"},
							{"      AAA ", "AAAAAAAAAA", "AAFFFCCCCA", "AAAAAAAAAA"},
							{"     A   A", "     AAAAA", "     AAAAA", "          "}
					})
					.addElement('A', ofBlock(CASING, CASING_META))
					.addElement('C', ofBlock(ItemRegistery.MPSystem, 0))
					.addElement('D', ofBlock(ItemRegistery.MPTransducer, 0))
					.addElement('F', ofBlockAnyMeta(ItemRegistery.IGlassBlock))
					.build();
	public int mMPGenerate = 0;
	public boolean checkStabilizer = true;
	public int rangeToStabilizer = 0;
	public int mPeakBeamMP = 1;
	public GTMTE_MPStabilizer mMPStabilizer;
	ITexture INDEX_CASE = Textures.BlockIcons.casingTexturePages[3][CASING_META + 16];
	int CASING_TEXTURE_ID = CASING_META + 16 + 128 * 3;
	
	//region Register
	public GTMTE_ParametricDiffuser(int aID, String aNameRegional) {
		super(aID, "impact.multis.parametricdiffuser", aNameRegional);
	}
	
	public GTMTE_ParametricDiffuser(String aName) {
		super(aName);
	}
	
	@Override
	public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		checkStabilizer = true;
		return new GTMTE_ParametricDiffuser(this.mName);
	}
	//endregion
	
	@Override
	public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide, final byte aFacing, final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
		return aSide == aFacing ? new ITexture[]{INDEX_CASE, TextureFactory.of(aActive ? Textures.BlockIcons.MP1a : Textures.BlockIcons.MP1)} : new ITexture[]{INDEX_CASE};
	}
	
	@Override
	public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		return new GUI_ParametricDiffuser(aPlayerInventory, aBaseMetaTileEntity, getLocalName());
	}
	
	@Override
	public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		return new GT_Container_ParametricDiffuser(aPlayerInventory, aBaseMetaTileEntity);
	}
	
	@Override
	public void construct(ItemStack itemStack, boolean b) {
		buildPiece(itemStack, b, 0, 2, 1);
	}
	
	@Override
	public IStructureDefinition<GTMTE_ParametricDiffuser> getStructureDefinition() {
		return definition;
	}
	
	@Override
	protected MultiBlockTooltipBuilder createTooltip() {
		MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder("matrix_diffuser");
		b
				.addInfo("info.0", "Generation of unstable Matrix Particles")
				.addTypeMachine("name", "Parametric Diffuser")
				.addInfo("info.1", "Need any Exquisite Gem")
				.addInfo("info.2", "Power Beam: (Tier Energy Hatch - 3) * ~200")
				.addInfo("info.3", "Max range to Matrix Particle Stabilizer: 30 blocks")
				.addInfo("info.4", "Need Low Gravity")
				.addSeparator()
				.addController()
				.addEnergyHatch("energy", "EV and above", 1)
				.addInputBus(1)
				.addMaintenanceHatch()
				.addCasingInfo("case", "Lab-Safe Low Gravity Casing", 82)
				.addOtherStructurePartAny("glass", "Any I-Glass")
				.addOtherStructurePartAny("reflector", "Matrix Particle Reflector", 20)
				.addOtherStructurePartAny("core", "Matrix Transducer", 1)
				.signAndFinalize();
		return b;
	}
	
	@Override
	public boolean checkRecipe(ItemStack aStack) {
		for (GT_MetaTileEntity_Hatch_InputBus mInputBuss : mInputBusses) {
			ItemStack gem = mInputBuss.mInventory[0];
			if (gem == null) return false;
			int[] idOreDict = OreDictionary.getOreIDs(gem);
			for (int id : idOreDict) {
				if (OreDictionary.getOreName(id).startsWith("gemExquisite")) {
					this.mMaxProgresstime    = 20;
					this.mEfficiency         = (10000 - (this.getIdealStatus() - this.getRepairStatus()) * 1000);
					this.mEfficiencyIncrease = 10000;
					int tier = GT_Utility.getTier(getMaxInputVoltage());
					this.mPeakBeamMP = mEfficiency < 10000 ? 0 : tier >= 4 ? tier - 3 : 0;
					this.mEUt        = -(int) getMaxInputVoltage();
					return true;
				}
			}
		}
		return false;
	}
	
	public void addBound(IGregTechTileEntity iAm) {
		int x = iAm.getXCoord();
		int y = iAm.getYCoord();
		int z = iAm.getZCoord();
		
		final Vector3ic forgeDirection = new Vector3i(
				ForgeDirection.getOrientation(iAm.getBackFacing()).offsetX,
				ForgeDirection.getOrientation(iAm.getBackFacing()).offsetY,
				ForgeDirection.getOrientation(iAm.getBackFacing()).offsetZ
		);
		
		if (iAm.isActive()) {
			Vector3ic offset = rotateOffsetVector(forgeDirection, 1, 0, -1);
			Vector3ic offsetToStabilizer = rotateOffsetVector(forgeDirection, rangeToStabilizer, 0, -1);
			
			new ToClient_LaserPush(iAm.getWorld().provider.dimensionId, new Vector3i(offset.x() + x, offset.y() + y, offset.z() + z),
					new Vector3i(offsetToStabilizer.x() + x, offsetToStabilizer.y() + y, offsetToStabilizer.z() + z), 0x770ED0, 1, 20, 0, 1.3f).sendToClients();
		}
	}
	
	@Override
	public void onPostTick(IGregTechTileEntity iAm, long aTick) {
		super.onPostTick(iAm, aTick);
		
		if (iAm.isServerSide() && aTick % 20 == 0) {
			mMPGenerate = 0;
			final Vector3ic forgeDirection = new Vector3i(
					ForgeDirection.getOrientation(iAm.getBackFacing()).offsetX,
					ForgeDirection.getOrientation(iAm.getBackFacing()).offsetY,
					ForgeDirection.getOrientation(iAm.getBackFacing()).offsetZ
			);
			doCheckStabilizer(iAm);
			
			if (!checkStabilizer && iAm.isActive()) {
				
				Vector3ic offsetX = rotateOffsetVector(forgeDirection, rangeToStabilizer, 0, 0);
				IGregTechTileEntity currentTE = iAm.getIGregTechTileEntityOffset(offsetX.x(), offsetX.y(), offsetX.z());
				
				if (currentTE != null) {
					if (currentTE.getMetaTileEntity() instanceof GTMTE_MPStabilizer) {
						mMPStabilizer   = (GTMTE_MPStabilizer) currentTE.getMetaTileEntity();
						checkStabilizer = false;
						if (currentTE.isActive() && mMPStabilizer.mMPSummary < 100_000) {
							Random random = new Random();
							mMPGenerate = random.nextInt(200) * mPeakBeamMP;
//							Utilits.sendChatByTE(iAm, "" + mMPGenerate);
							
							mMPStabilizer.setMP(mMPGenerate);
							addBound(iAm);
							if (mMPStabilizer.mMPSummary > 100_000) {
								mMPStabilizer.mMPSummary = 100_000;
							}
						}
					}
				} else {
					mMPStabilizer     = null;
					checkStabilizer   = true;
					rangeToStabilizer = 0;
				}
			}
		}
	}
	
	public void doCheckStabilizer(IGregTechTileEntity iAm) {
		
		if (mMachine && checkStabilizer) {
			
			final Vector3ic forgeDirection = new Vector3i(
					ForgeDirection.getOrientation(iAm.getBackFacing()).offsetX,
					ForgeDirection.getOrientation(iAm.getBackFacing()).offsetY,
					ForgeDirection.getOrientation(iAm.getBackFacing()).offsetZ
			);
			
			Vector3ic offsetX;
			IGregTechTileEntity currentTE = null;
			
			for (; rangeToStabilizer < 30; rangeToStabilizer++) {
				
				offsetX = rotateOffsetVector(forgeDirection, rangeToStabilizer, 0, 0);
				
				currentTE = iAm.getIGregTechTileEntityOffset(offsetX.x(), offsetX.y(), offsetX.z());
				
				if (currentTE != null) {
					if (currentTE.getMetaTileEntity() instanceof GTMTE_MPStabilizer) {
						mMPStabilizer   = (GTMTE_MPStabilizer) currentTE.getMetaTileEntity();
						checkStabilizer = false;
						break;
					}
				}
			}
			if (currentTE == null) {
				mMPStabilizer     = null;
				checkStabilizer   = true;
				rangeToStabilizer = 0;
			}
		}
	}
	
	@Override
	public boolean machineStructure(IGregTechTileEntity iAm) {
		if (!Utilits.isLowGravity(iAm)) {
			return false;
		}
		//region Structure
		final Vector3ic forgeDirection = new Vector3i(
				ForgeDirection.getOrientation(iAm.getBackFacing()).offsetX,
				ForgeDirection.getOrientation(iAm.getBackFacing()).offsetY,
				ForgeDirection.getOrientation(iAm.getBackFacing()).offsetZ
		);
		boolean formationChecklist = true;
		
		for (int x = 0; x <= 9; x++) {
			for (int y = 0; y <= 2; y++) {
				
				final Vector3ic offset = rotateOffsetVector(forgeDirection, x, -1, -y);
				IGregTechTileEntity currentTE = iAm.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
				
				if (y == 1 && (x >= 2 && x <= 8)) {
					if ((iAm.getBlockOffset(offset.x(), offset.y(), offset.z()) != ItemRegistery.MPSystem)
							&& (iAm.getMetaIDOffset(offset.x(), offset.y(), offset.z()) != 0)) {
						formationChecklist = false;
					}
					continue;
				}
				formationChecklist = isFormationChecklist(iAm, formationChecklist, offset, currentTE);
			}
		}
		
		for (int x = 0; x <= 9; x++) {
			for (int y = -1; y <= 3; y++) {
				
				if (x == 0 && y == 0) continue;
				
				final Vector3ic offset = rotateOffsetVector(forgeDirection, x, 0, -y);
				IGregTechTileEntity currentTE = iAm.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
				
				if (y == 1 && (x >= 2 && x <= 8)) continue;
				
				if (!(y >= 0 && y <= 2) && !(x >= 5)) continue;
				
				if (x >= 2 && x <= 4) {
					formationChecklist = Structure.doCheck(formationChecklist, iAm, offset, ItemRegistery.IGlassBlock);
					continue;
				}
				if (x >= 5 && x <= 8 && y >= 0 && y <= 2) {
					formationChecklist = Structure.doCheck(formationChecklist, iAm, offset, ItemRegistery.MPSystem, 0);
					continue;
				}
				// todo нос
				if (x == 9 && y == 1) {
					formationChecklist = Structure.doCheck(formationChecklist, iAm, offset, ItemRegistery.IGlassBlock);
					continue;
				}
				// todo начинка
				if (x == 1 && y == 1) {
					formationChecklist = Structure.doCheck(formationChecklist, iAm, offset, ItemRegistery.MPTransducer, 0);
					continue;
				}
				formationChecklist = isFormationChecklist(iAm, formationChecklist, offset, currentTE);
			}
		}
		
		for (int x = 0; x <= 9; x++) {
			for (int y = -1; y <= 3; y++) {
				
				final Vector3ic offset = rotateOffsetVector(forgeDirection, x, 1, -y);
				IGregTechTileEntity currentTE = iAm.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
				
				if (y == -1 && !(x >= 5)) continue;
				if (y == 3 && !(x >= 5)) continue;
				
				if (x >= 2 && x <= 4 && y == 1) {
					formationChecklist = Structure.doCheck(formationChecklist, iAm, offset, ItemRegistery.IGlassBlock);
					continue;
				}
				if (x >= 5 && x <= 8 && y == 1) {
					formationChecklist = Structure.doCheck(formationChecklist, iAm, offset, ItemRegistery.MPSystem, 0);
					continue;
				}
				formationChecklist = isFormationChecklist(iAm, formationChecklist, offset, currentTE);
			}
		}
		
		for (int x = 5; x <= 9; x++) {
			for (int y = -1; y <= 3; y++) {
				
				final Vector3ic offset = rotateOffsetVector(forgeDirection, x, 2, -y);
				IGregTechTileEntity currentTE = iAm.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
				
				if (x == 5 && y >= 0 && y <= 2) continue;
				if (x == 9 && y >= 0 && y <= 2) continue;
				
				if (y == -1 && x >= 6 && x <= 8) continue;
				if (y == 3 && x >= 6 && x <= 8) continue;
				
				formationChecklist = isFormationChecklist(iAm, formationChecklist, offset, currentTE);
			}
		}
		formationChecklist = Structure.doSizeHatchesEqual(formationChecklist, mInputBusses, 1);
		formationChecklist = Structure.doSizeHatchesLess(formationChecklist, mEnergyHatches, 8);
		formationChecklist = Structure.doSizeHatchesEqual(formationChecklist, mMaintenanceHatches, 1);
		
		return formationChecklist;
	}
	
	private boolean isFormationChecklist(IGregTechTileEntity iAm, boolean formationChecklist, Vector3ic offset, IGregTechTileEntity currentTE) {
		if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID) &&
				!super.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID) &&
				!super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)) {
			formationChecklist = Structure.doCheck(formationChecklist, iAm, offset, CASING, CASING_META);
		}
		return formationChecklist;
	}
	
	@Override
	public void saveNBTData(NBTTagCompound aNBT) {
		super.saveNBTData(aNBT);
		aNBT.setInteger("mMPGenerate", mMPGenerate);
		aNBT.setInteger("rangeToStabilizer", rangeToStabilizer);
		aNBT.setInteger("mPeakBeamMP", mPeakBeamMP);
		aNBT.setBoolean("checkStabilizer", checkStabilizer);
	}
	
	@Override
	public void loadNBTData(NBTTagCompound aNBT) {
		super.loadNBTData(aNBT);
		mMPGenerate       = aNBT.getInteger("mMPGenerate");
		rangeToStabilizer = aNBT.getInteger("rangeToStabilizer");
		mPeakBeamMP       = aNBT.getInteger("mPeakBeamMP");
		checkStabilizer   = aNBT.getBoolean("checkStabilizer");
	}
	
	public String[] getInfoData() {
		return new String[]{
				"Usage Energy: " + RED + -mEUt + RESET + " EU/t",
				"Max Voltage: " + YELLOW + getMaxInputVoltage() + RESET + " EU/t ",
				"Maintenance: " + ((super.getRepairStatus() == super.getIdealStatus()) ? GREEN + "Good " + YELLOW + mEfficiency / 100.0F + " %" + RESET : RED + "Has Problems " + mEfficiency / 100.0F + " %" + RESET),
		};
	}
	
	@Override
	public boolean hasSeparate() {
		return false;
	}
}