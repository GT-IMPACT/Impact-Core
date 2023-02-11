package com.impact.mods.gregtech.tileentities.multi.generators.nuclear;

import com.impact.mods.gregtech.blocks.Casing_Helper;
import com.impact.mods.gregtech.gui.base.GUI_BASE;
import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_Impact_BlockBase;
import com.impact.util.string.MultiBlockTooltipBuilder;
import com.impact.util.vector.Structure;
import com.impact.util.vector.Vector3i;
import com.impact.util.vector.Vector3ic;
import gregtech.api.GregTech_API;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.render.TextureFactory;
import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import space.impact.api.multiblocks.structure.IStructureDefinition;
import space.impact.api.multiblocks.structure.StructureDefinition;

import java.text.NumberFormat;
import java.util.ArrayList;

import static com.impact.loader.ItemRegistery.InsideBlock;
import static com.impact.loader.ItemRegistery.decorateBlock;
import static space.impact.api.multiblocks.structure.StructureUtility.ofBlock;
import static space.impact.api.multiblocks.structure.StructureUtility.ofBlockHint;

public class GTMTE_HugeSteamTurbine extends GTMTE_Impact_BlockBase<GTMTE_HugeSteamTurbine> {
	
	static final Block CASING = Casing_Helper.sCasePage8_3;
	static final Block GEARBOX = GregTech_API.sBlockCasings2;
	static final int CASING_META = 4;
	static final int GEARBOX_META = 3;
	static final ITexture INDEX_CASE = Textures.BlockIcons.casingTexturePages[0][16];
	static final int CASING_TEXTURE_ID = 16;
	int mStoredFluids = 0;
	long mOutputSalary = 0;
	static IStructureDefinition<GTMTE_HugeSteamTurbine> definition =
			StructureDefinition.<GTMTE_HugeSteamTurbine>builder()
					.addShape("main", new String[][]{
							{"     ", " BBB ", " B~B ", " BBB "},
							{"     ", "BBBBB", "B A B", "BBBBB"},
							{" BBB ", "BBBBB", "B A B", "BBBBB"},
							{"BBBBB", "BBBBB", "B A B", "BBBBB"},
							{"BBBBB", "BBABB", "B A B", "BBBBB"},
							{"BBBBB", "BBBBB", "B A B", "BBBBB"},
							{" BBB ", "BBBBB", "BBBBB", "BBBBB"},
							{"     ", " CCC ", " CCC ", " CCC "}
					})
					.addElement('A', ofBlock(GEARBOX, GEARBOX_META))
					.addElement('B', ofBlock(CASING, CASING_META))
					.addElement('C', ofBlockHint(decorateBlock[2], 1))
					.build();
	
	public GTMTE_HugeSteamTurbine(int aID, String aNameRegional) {
		super(aID, "impact.multis.hugesteamturbine", aNameRegional);
	}
	
	public GTMTE_HugeSteamTurbine(String aName) {
		super(aName);
	}
	
	@Override
	public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide, final byte aFacing, final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
		return aSide == aFacing ? new ITexture[]{INDEX_CASE, TextureFactory.of(aActive ? Textures.BlockIcons.LARGETURBINE_ST_ACTIVE5 : Textures.BlockIcons.LARGETURBINE_ST5)} : new ITexture[]{INDEX_CASE};
	}
	
	@Override
	public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GTMTE_HugeSteamTurbine(this.mName);
	}
	
	@Override
	public void construct(ItemStack itemStack, boolean b) {
		buildPiece(itemStack, b, 2, 2, 0);
	}
	
	@Override
	public IStructureDefinition<GTMTE_HugeSteamTurbine> getStructureDefinition() {
		return definition;
	}
	
	@Override
	protected MultiBlockTooltipBuilder createTooltip() {
		MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder("htg");
		b
				.addInfo("info.0", "Mega steam turbine!")
				.addTypeGenerator()
				.addInfo("info.1", "The turbine operates at 100% efficiency")
				.addInfo("info.2", "Accepts both steam (2L = 1 EU/t)")
				.addInfo("info.3", "and superheated steam (1L = 1 EU/t)")
				.addSeparator()
				.addController()
				.addDynamoHatch("dynamo", "Any casing back side", 9)
				.addMaintenanceHatch()
				.addInputHatch(20)
				.addCasingInfo("case", "Huge Turbine Casing", 82)
				.addOtherStructurePart("htg.other.0", "Steel GearBox Casing", "htg.other.1", "inside structure")
				.signAndFinalize();
		return b;
	}
	
	@Override
	public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		return new GUI_BASE(aPlayerInventory, aBaseMetaTileEntity, "Huge Steam Turbine", "MultiParallelBlockGUI.png");
	}
	
	@Override
	public boolean machineStructure(IGregTechTileEntity thisController) {
		final Vector3ic forgeDirection = new Vector3i(
				ForgeDirection.getOrientation(thisController.getBackFacing()).offsetX,
				ForgeDirection.getOrientation(thisController.getBackFacing()).offsetY,
				ForgeDirection.getOrientation(thisController.getBackFacing()).offsetZ
		);
		int aCasingAmount = 0;
		boolean formationChecklist = true;
		int x, y, z;
		for (x = -1; x <= 1; x++) {
			for (y = -1; y <= 1; y++) {
				if (x == 0 && y == 0) {
					continue;
				}
				final Vector3ic offset = rotateOffsetVector(forgeDirection, x, y, 0);
				if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
						&& (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == CASING_META)) {
					aCasingAmount++;
				} else {
					formationChecklist = false;
				}
			}
		}
		for (x = -2; x <= 2; x++) {
			for (y = -1; y <= 1; y++) {
				for (z = -1; z >= -6; z--) {
					final Vector3ic offset = rotateOffsetVector(forgeDirection, x, y, z);
					if (x >= -1 && x <= 1 && z <= -1 && z >= -5 && y == 0) {
						if (x == 0 && z > -4) {
							if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == GEARBOX)
									&& (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == GEARBOX_META)) {
								aCasingAmount++;
							} else {
								formationChecklist = false;
							}
							continue;
						}
						continue;
					}
					if (x == 0 && z == -4 && y == 1) {
						if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == GEARBOX)
								&& (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == GEARBOX_META)) {
							aCasingAmount++;
						} else {
							formationChecklist = false;
						}
						continue;
					}
					IGregTechTileEntity currentTE = thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
					if (!addInputToMachineList(currentTE, CASING_TEXTURE_ID)
							&& !addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)) {
						if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
								&& (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == CASING_META)) {
							aCasingAmount++;
						} else {
							formationChecklist = false;
						}
					}
				}
			}
		}
		for (x = -2; x <= 2; x++) {
			for (z = -2; z >= -6; z--) {
				if ((x == -2 || x == 2) && (z == -2 || z == -6)) {
					continue;
				}
				final Vector3ic offset = rotateOffsetVector(forgeDirection, x, 2, z);
				if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
						&& (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == CASING_META)) {
					aCasingAmount++;
				} else {
					formationChecklist = false;
				}
			}
		}
		for (x = -1; x <= 1; x++) {
			for (y = -1; y <= 1; y++) {
				final Vector3ic offset = rotateOffsetVector(forgeDirection, x, y, -7);
				IGregTechTileEntity currentTE = thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
				if (!addDynamoToMachineList(currentTE, CASING_TEXTURE_ID)) {
					if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
							&& (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == CASING_META)) {
						aCasingAmount++;
					} else {
						formationChecklist = false;
					}
				}
			}
		}
		
		if (mInputHatches.size() > 20) {
			formationChecklist = false;
		}
		
		if (mMaintenanceHatches.size() != 1) {
			formationChecklist = false;
		}
		
		if (mDynamoHatches.size() > 9 || mDynamoHatchesMulti.size() > 9) {
			formationChecklist = false;
		}
		
		if (formationChecklist) {
			rotorTopTrigger(false);
		}
		return formationChecklist;
	}
	
	@Override
	public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
		super.onPostTick(aBaseMetaTileEntity, aTick);
		if (mMachine && aTick % 40 == 0) {
			rotorTopTrigger(aBaseMetaTileEntity.isActive());
		}
	}
	
	@Override
	public boolean checkRecipe(ItemStack itemStack) {
		ArrayList<FluidStack> tFluids = getStoredFluids();
		this.mEfficiency = (10000 - (this.getIdealStatus() - this.getRepairStatus()) * 1000);
		int totalFlow = 0;
		int countEU = 0;
		long outEU = 0;
		this.mStoredFluids = 0;
		int countFluid = 0;
		for (FluidStack fluidStack : tFluids) {
			String fluidName = fluidStack.getUnlocalizedName();
			if (fluidName.equals("fluid.steam") || fluidName.equals("ic2.fluidSteam")) {
				countFluid = fluidStack.amount;
				depleteInput(new FluidStack(fluidStack, countFluid));
				this.mStoredFluids += countFluid;
				totalFlow += countFluid;
				countEU = 2;
			} else if (fluidName.equals("ic2.fluidSuperheatedSteam")) {
				countFluid = fluidStack.amount;
				depleteInput(new FluidStack(fluidStack, countFluid));
				this.mStoredFluids += countFluid;
				totalFlow += countFluid;
				countEU = 1;
			}
			outEU = totalFlow / countEU;
		}
		
		if (tFluids.isEmpty() || totalFlow == 0) {
			return false;
		}
		
		outEU = mEfficiency < 10000 ? outEU / 2 : outEU;
		outEU /= 20;
		mStoredFluids /= 20;
		mOutputSalary = Math.min(outEU, getMaxOutputVoltage());
		
		this.mMaxProgresstime    = 20;
		this.mEfficiencyIncrease = 10000;
		return true;
	}
	
	@Override
	public boolean onRunningTick(ItemStack aStack) {
		super.addEnergyOutputMultipleDynamos(mOutputSalary, true);
		return super.onRunningTick(aStack);
	}
	
	private void rotorTopTrigger(boolean shouldExist) {
		IGregTechTileEntity base = getBaseMetaTileEntity();
		
		final Vector3ic forgeDirection = new Vector3i(
				ForgeDirection.getOrientation(base.getBackFacing()).offsetX,
				ForgeDirection.getOrientation(base.getBackFacing()).offsetY,
				ForgeDirection.getOrientation(base.getBackFacing()).offsetZ
		);
		
		final Vector3ic offset = rotateOffsetVector(forgeDirection, 0, 0, -4);
		
		if (base.isActive()) {
			Structure.setBlock(base, offset, InsideBlock, 3);
		} else {
			Structure.setBlock(base, offset, InsideBlock, 4);
		}
	}
	
	@Override
	public String[] getInfoData() {
		return new String[]{
				"Steam Input: " + EnumChatFormatting.RED + "" + NumberFormat.getNumberInstance().format(mStoredFluids) + EnumChatFormatting.RESET + " L/t",
				"Output Energy: " + EnumChatFormatting.GREEN + NumberFormat.getNumberInstance().format(mOutputSalary) + EnumChatFormatting.RESET + " EU/t",
				"Void Steam: " + EnumChatFormatting.RED + (int) Math.abs(mStoredFluids - mOutputSalary) + EnumChatFormatting.RESET + " L/t",
				"Efficiency: " + EnumChatFormatting.YELLOW + (float) this.mEfficiency / 100.0F + EnumChatFormatting.YELLOW + " %",
				"Maintenance: " + ((super.getRepairStatus() == super.getIdealStatus()) ? EnumChatFormatting.GREEN + "No Problems" + EnumChatFormatting.RESET : EnumChatFormatting.RED + "Has Problems" + EnumChatFormatting.RESET),
		};
	}
	
	
	@Override
	public boolean hasSeparate() {
		return false;
	}
}