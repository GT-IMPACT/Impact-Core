package com.impact.mods.gregtech.tileentities.multi.matrixsystem;

import com.impact.common.item.Core_Items3;
import com.impact.impact;
import com.impact.loader.ItemRegistery;
import com.impact.mods.gregtech.blocks.Casing_Helper;
import com.impact.mods.gregtech.gui.base.GTC_ImpactBase;
import com.impact.mods.gregtech.gui.base.GUI_BASE;
import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_Impact_BlockBase;
import com.impact.util.Utilits;
import com.impact.util.string.MultiBlockTooltipBuilder;
import com.impact.util.vector.Vector3i;
import com.impact.util.vector.Vector3ic;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_InputBus;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import space.impact.api.multiblocks.structure.IStructureDefinition;
import space.impact.api.multiblocks.structure.StructureDefinition;

import java.util.ArrayList;

import static net.minecraft.util.EnumChatFormatting.*;
import static space.impact.api.multiblocks.structure.StructureUtility.*;

public class GTMTE_MPContainment extends GTMTE_Impact_BlockBase<GTMTE_MPContainment> {
	
	public static Block CASING = Casing_Helper.sCaseCore2;
	public static byte CASING_META = 15;
	static IStructureDefinition<GTMTE_MPContainment> definition =
			StructureDefinition.<GTMTE_MPContainment>builder()
					.addShape("main", new String[][]{
							{"      A", "AAAAA~A", "A    AA"},
							{"AAAAAAA", "ABBBB D", "AAAAAAA"},
							{"ABBBBAA", "D   C D", "ABBBBAA"},
							{"AAAAAAA", "ABBBB D", "AAAAAAA"},
							{"      A", "AAAAAFA", "A    AA"}
					})
					.addElement('A', ofBlock(CASING, CASING_META))
					.addElement('B', ofBlock(ItemRegistery.MPSystem, 0))
					.addElement('C', ofBlock(ItemRegistery.MPTransducer, 0))
					.addElement('D', ofBlockAnyMeta(ItemRegistery.IGlassBlock))
					.addElement('F', ofBlockHint(ItemRegistery.decorateBlock[2], 1))
					.build();
	public int mMPStable = 0;
	public ArrayList<Vector3ic> vectors = new ArrayList<>();
	ITexture INDEX_CASE = Textures.BlockIcons.casingTexturePages[3][CASING_META + 16];
	int CASING_TEXTURE_ID = CASING_META + 16 + 128 * 3;
	
	//region Register
	public GTMTE_MPContainment(int aID, String aNameRegional) {
		super(aID, "impact.multis.matrixcontainment", aNameRegional);
	}
	
	public GTMTE_MPContainment(String aName) {
		super(aName);
	}
	
	@Override
	public IStructureDefinition<GTMTE_MPContainment> getStructureDefinition() {
		return definition;
	}
	
	@Override
	public void construct(ItemStack itemStack, boolean b) {
		buildPiece(itemStack, b, 5, 1, 0);
	}
	
	@Override
	public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GTMTE_MPContainment(this.mName);
	}
	//endregion
	
	@Override
	public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide, final byte aFacing, final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
		return aSide == aFacing ? new ITexture[]{INDEX_CASE, TextureFactory.of(aActive ? Textures.BlockIcons.MP1a : Textures.BlockIcons.MP1)} : new ITexture[]{INDEX_CASE};
	}
	
	@Override
	public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		return new GUI_BASE(aPlayerInventory, aBaseMetaTileEntity, getLocalName());
	}
	
	@Override
	public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		return new GTC_ImpactBase(aPlayerInventory, aBaseMetaTileEntity);
	}
	
	@Override
	protected MultiBlockTooltipBuilder createTooltip() {
		MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder("matrix_container");
		b
				.addInfo("info.0", "Collecting stable Matrix Particles")
				.addTypeMachine("name", "Containment")
				.addInfo("info.1", "Constant power consumption: 1,920 EU/t")
				.addInfo("info.2", "Stable particles are filled in \"Portable Cell with Matrix Particles\"")
				.addInfo("info.3", "Need Low Gravity")
				.addSeparator()
				.addController()
				.addEnergyHatch()
				.addMaintenanceHatch()
				.addInputBus()
				.addOutputBus()
				.addCasingInfo("case", "Lab-Safe Low Gravity Casing", 53)
				.addOtherStructurePartAny("reflector", "Matrix Particle Reflector", 17)
				.addRedHint("Output Hatch")
				.addWhiteHint("Any I-Glass")
				.addPurpleHint("Matrix Transducer")
				.signAndFinalize();
		return b;
	}
	
	
	@Override
	public boolean checkRecipe(ItemStack aStack) {
		this.mEUt = -1920;
		this.mEfficiency = (10000 - (this.getIdealStatus() - this.getRepairStatus()) * 1000);
		this.mMaxProgresstime = 20;
		return true;
	}
	
	@Override
	public void onPostTick(IGregTechTileEntity iAm, long aTick) {
		super.onPostTick(iAm, aTick);
		
		if (iAm.isServerSide() && aTick % 20 == 0) {
			
			if (!iAm.isActive()) mMPStable = 0;
			
			if (iAm.isActive()) {
				mMPStable = Math.min(mMPStable, 100_000);
				int amount = 0;
				for (GT_MetaTileEntity_Hatch_InputBus bus : mInputBusses) {
					if (bus.mInventory.length > 0) {
						for (ItemStack is : bus.mInventory) {
							if (GT_Utility.areStacksEqual(is, Core_Items3.getInstance().get(0, 1))) {
								amount += is.stackSize;
							}
						}
					}
				}
				for (int i = 0; i < amount; i++) {
					if (mMPStable >= 1000 && depleteInput(Core_Items3.getInstance().get(0, 1))) {
						mMPStable -= 1000;
						addOutput(Core_Items3.getInstance().get(1, 1));
						Vector3ic core = vectors.get(0);
						
						final Vector3ic forgeDirection = new Vector3i(
								ForgeDirection.getOrientation(iAm.getBackFacing()).offsetX,
								ForgeDirection.getOrientation(iAm.getBackFacing()).offsetY,
								ForgeDirection.getOrientation(iAm.getBackFacing()).offsetZ
						);
						final Vector3ic offset = rotateOffsetVector(forgeDirection, -1, 0, -2);
						impact.proxy.nodeBolt(iAm.getWorld(), core.x(), core.y(), core.z(),
								offset.x(), offset.y(), offset.z(), 60, 10.0F, 1
						);
					} else break;
				}
			}
		}
	}
	
	public void setMP(int amount) {
		mMPStable += amount;
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
		vectors.clear();
		
		for (int x = -5; x <= 1; x++) {
			for (int y = 0; y <= 4; y++) {
				
				final Vector3ic offset = rotateOffsetVector(forgeDirection, x, -1, -y);
				IGregTechTileEntity currentTE = iAm.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
				
				if (x >= -4 && x <= -1 && !(y >= 1 && y <= 3)) continue;
				
				if (x >= -4 && x <= -1 && y == 2) {
					if ((iAm.getBlockOffset(offset.x(), offset.y(), offset.z()) == ItemRegistery.MPSystem)
							&& (iAm.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 0)) {
					} else {
						formationChecklist = false;
					}
					continue;
				}
				
				if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
						&& !super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)
						&& !super.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)) {
					if ((iAm.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
							&& (iAm.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == CASING_META)) {
					} else {
						formationChecklist = false;
					}
				}
			}
		}
		
		for (int x = -5; x <= 1; x++) {
			for (int y = 0; y <= 4; y++) {
				
				final Vector3ic offset = rotateOffsetVector(forgeDirection, x, 0, -y);
				IGregTechTileEntity currentTE = iAm.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
				
				if (x == 0 && y == 0) continue;
				if (x >= -4 && x <= -2 && y == 2) continue;
				if (x == 0 && y <= 3) continue;
				
				if (x == -1 && y == 2) {
					if ((iAm.getBlockOffset(offset.x(), offset.y(), offset.z()) == ItemRegistery.MPTransducer)
							&& (iAm.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 0)) {
						vectors.add(new Vector3i(iAm.getXCoord() + offset.x(), iAm.getYCoord() + offset.y(), iAm.getZCoord() + offset.z()));
					} else {
						formationChecklist = false;
					}
					continue;
				}
				
				if (x >= -4 && x <= -1 && y >= 1 && y <= 3) {
					if ((iAm.getBlockOffset(offset.x(), offset.y(), offset.z()) == ItemRegistery.MPSystem)
							&& (iAm.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 0)) {
					} else {
						formationChecklist = false;
					}
					continue;
				}
				
				if (x == -5 && y == 2) {
					if ((iAm.getBlockOffset(offset.x(), offset.y(), offset.z()) == ItemRegistery.IGlassBlock)) {
					} else {
						formationChecklist = false;
					}
					continue;
				}
				
				if (x == 1 && y >= 1 && y <= 3) {
					if ((iAm.getBlockOffset(offset.x(), offset.y(), offset.z()) == ItemRegistery.IGlassBlock)) {
					} else {
						formationChecklist = false;
					}
					continue;
				}
				
				if (x == 0) {
					if (super.addOutputToMachineList(currentTE, CASING_TEXTURE_ID)) {
					} else {
						formationChecklist = false;
					}
					continue;
				}
				
				if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
						&& !super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)
						&& !super.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)) {
					if ((iAm.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
							&& (iAm.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == CASING_META)) {
					} else {
						formationChecklist = false;
					}
				}
			}
		}
		
		for (int x = -5; x <= 1; x++) {
			for (int y = 0; y <= 4; y++) {
				
				final Vector3ic offset = rotateOffsetVector(forgeDirection, x, 1, -y);
				IGregTechTileEntity currentTE = iAm.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
				
				if (x <= 0 && !(y >= 1 && y <= 3)) continue;
				
				if (x >= -4 && x <= -1 && y == 2) {
					if ((iAm.getBlockOffset(offset.x(), offset.y(), offset.z()) == ItemRegistery.MPSystem)
							&& (iAm.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 0)) {
					} else {
						formationChecklist = false;
					}
					continue;
				}
				
				if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
						&& !super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)
						&& !super.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)) {
					if ((iAm.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
							&& (iAm.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == CASING_META)) {
					} else {
						formationChecklist = false;
					}
				}
			}
		}
		return formationChecklist;
	}
	
	@Override
	public void saveNBTData(NBTTagCompound aNBT) {
		super.saveNBTData(aNBT);
		aNBT.setInteger("mMPStable", mMPStable);
	}
	
	@Override
	public void loadNBTData(NBTTagCompound aNBT) {
		super.loadNBTData(aNBT);
		mMPStable = aNBT.getInteger("mMPStable");
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