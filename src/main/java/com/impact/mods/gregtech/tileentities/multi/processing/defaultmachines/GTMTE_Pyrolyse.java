package com.impact.mods.gregtech.tileentities.multi.processing.defaultmachines;

import com.impact.mods.gregtech.gui.base.GUI_BASE;
import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_Impact_BlockBase;
import com.impact.util.string.MultiBlockTooltipBuilder;
import com.impact.util.vector.Vector3i;
import com.impact.util.vector.Vector3ic;
import gregtech.api.GregTech_API;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GT_OreDictUnificator;
import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import space.impact.api.multiblocks.structure.IStructureDefinition;
import space.impact.api.multiblocks.structure.StructureDefinition;

import static com.impact.common.item.Core_Items.Core_Items1;
import static com.impact.util.Utilits.isB;
import static com.impact.util.multis.GT_StructureUtility.ofFrame;
import static gregtech.api.GregTech_API.sBlockCasings2;
import static space.impact.api.multiblocks.structure.StructureUtility.lazy;
import static space.impact.api.multiblocks.structure.StructureUtility.ofBlock;

public class GTMTE_Pyrolyse extends GTMTE_Impact_BlockBase<GTMTE_Pyrolyse> {
	
	Block CASING = GregTech_API.sBlockCasings2;
	byte CASING_META = 0;
	ITexture INDEX_CASE = Textures.BlockIcons.casingTexturePages[3][16 + CASING_META];
	int CASING_TEXTURE_ID = CASING_META + 16 + 128 * 3;
	int frameId = 4096 + Materials.Steel.mMetaItemSubID;
	int frameMeta = GregTech_API.METATILEENTITIES[frameId].getTileEntityBaseType();
	static IStructureDefinition<GTMTE_Pyrolyse> definition =
			StructureDefinition.<GTMTE_Pyrolyse>builder()
					.addShapeOldApi("main", new String[][]{
							{"000.00000", ".00.00000", "1.1.1...1",},
							{"000.00000", "02222...0", "....00000",},
							{"000.00000", "000.00000", "1.1.1...1",},
					})
					.addElement('0', ofBlock(sBlockCasings2, 0))
					.addElement('1', lazy(t -> ofFrame(Materials.Steel)))
					.addElement('2', ofBlock(sBlockCasings2, 13))
					.build();
	
	public GTMTE_Pyrolyse(int aID, String aNameRegional) {
		super(aID, "impact.multimachine.pyrolyse", aNameRegional);
	}
	
	public GTMTE_Pyrolyse(String aName) {
		super(aName);
	}
	
	@Override
	public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide, final byte aFacing, final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
		return aSide == aFacing ? new ITexture[]{INDEX_CASE, TextureFactory.of(aActive ? Textures.BlockIcons.OVERLAY_FRONT_DISTILLATION_TOWER_ACTIVE :
				Textures.BlockIcons.OVERLAY_FRONT_DISTILLATION_TOWER)} :
				new ITexture[]{INDEX_CASE};
	}
	
	@Override
	public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GTMTE_Pyrolyse(this.mName);
	}
	
	@Override
	public IStructureDefinition<GTMTE_Pyrolyse> getStructureDefinition() {
		return definition;
	}
	
	@Override
	public void construct(ItemStack itemStack, boolean b) {
		buildPiece(itemStack, b, 0, 1, 0);
	}
	
	@Override
	protected MultiBlockTooltipBuilder createTooltip() {
		MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder("pyrolyse_oven");
		b
				.addTypeMachine("name", "Pyrolyse oven")
				.addInfo("info.0", "Converts hydrocarbons into gases, wood tar and solid fuels")
				.addInfo("info.1", "The process emits gases throughout the entire time (60s):")
				.addInfo("info.2", "for 7s - CO, for 14s - H\u2082, for 21s - CH\u2084, for 28s - CO\u2082")
				.addInfo("info.3", "and 35s - solid fuels and wood tar")
				.addPollution(100)
				.addSeparator()
				.addController()
				.addEnergyHatch()
				.addMaintenanceHatch()
				.addMuffler()
				.addInputBus(1)
				.addOutputBus(1)
				.addOutputHatch(1)
				.addEnergyHatch(1)
				.addCasingInfo("case", "Solid Steel Casing", 40)
				.addOtherStructurePart("other.0", "Steel Pipe Casing", "other.1", "Middle line")
				.addOtherStructurePart("other.2", "Steel Frame", "other.3", "Bottom angles")
				.signAndFinalize();
		return b;
	}
	
	@Override
	public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		return new GUI_BASE(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "MultiParallelBlockGUI.png");
	}
	
	@Override
	public boolean checkRecipe(ItemStack itemStack) {
		if (depleteInput(Core_Items1.getRecipe(39, 42))) {
			this.mEfficiency         = 10000;
			this.mEfficiencyIncrease = 10000;
			
			int EUt = 20;
			int maxProgresstime = 40 * 20;
			
			this.mEUt             = -EUt;
			this.mMaxProgresstime = maxProgresstime;
			
			this.updateSlots();
			return true;
		}
		return false;
	}
	
	@Override
	public boolean onRunningTick(ItemStack aStack) {
		
		if (this.mProgresstime == 7 * 20) {
			addOutput(Materials.CarbonMonoxide.getGas(72L));
		}
		
		if (this.mProgresstime == 14 * 20) {
			addOutput(Materials.Hydrogen.getGas(288L));
		}
		
		if (this.mProgresstime == 21 * 20) {
			addOutput(Materials.Methane.getGas(144L));
		}
		
		if (this.mProgresstime == 28 * 20) {
			addOutput(Materials.CarbonDioxide.getGas(216L));
		}
		
		if (this.mProgresstime == 35 * 20) {
			addOutput(Materials.WoodTar.getFluid(1440L));
			addOutput(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Charcoal, 5));
		}
		
		return super.onRunningTick(aStack);
	}
	
	@Override
	public boolean machineStructure(IGregTechTileEntity thisController) {
		final Vector3ic forgeDirection = new Vector3i(
				ForgeDirection.getOrientation(thisController.getBackFacing()).offsetX,
				ForgeDirection.getOrientation(thisController.getBackFacing()).offsetY,
				ForgeDirection.getOrientation(thisController.getBackFacing()).offsetZ
		);
		
		boolean formationChecklist = true; // Если все ок, машина собралась
		
		for (byte X = 0; X <= 8; X++) {
			for (byte Z = 0; Z >= -2; Z--) {
				
				final Vector3ic offset = rotateOffsetVector(forgeDirection, X, -1, Z);
				
				if (isB(Z, -1) && isB(X, 4, 8)) {
					if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
							&& (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 0)) {
					} else {
						formationChecklist = false;
					}
					continue;
				}
				if ((isB(Z, 0) || isB(Z, -2)) && (isB(X, 1) || isB(X, 3) || isB(X, 5, 7))) {
					continue;
				}
				if (isB(Z, -1, 3)) {
					continue;
				}
				
				if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == GregTech_API.sBlockMachines)
						&& (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == frameMeta)) {
				} else {
					formationChecklist = false;
				}
			}
		}
		
		for (byte X = 0; X <= 8; X++) {
			for (byte Z = 0; Z >= -2; Z--) {
				if (X == 0 && Z == 0) {
					continue;
				}
				
				final Vector3ic offset = rotateOffsetVector(forgeDirection, X, 0, Z);
				
				if (isB(Z, -1) && isB(X, 1, 4)) {
					if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
							&& (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 13)) {
					} else {
						formationChecklist = false;
					}
					continue;
				}
				
				if ((isB(Z, 0) || isB(Z, -2)) && isB(X, 3)) {
					continue;
				}
				if (isB(Z, -1) && isB(X, 5, 7)) {
					continue;
				}
				
				IGregTechTileEntity currentTE = thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
				if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
						&& !super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)
						&& !super.addMufflerToMachineList(currentTE, CASING_TEXTURE_ID)
						&& !super.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)
						&& !super.addOutputToMachineList(currentTE, CASING_TEXTURE_ID)) {
					
					if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
							&& (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 0)) {
					} else {
						formationChecklist = false;
					}
				}
			}
		}
//
		for (byte X = 0; X <= 8; X++) {
			for (byte Z = 0; Z >= -2; Z--) {
				
				final Vector3ic offset = rotateOffsetVector(forgeDirection, X, 1, Z);
				
				if (isB(X, 3)) {
					continue;
				}
				
				IGregTechTileEntity currentTE = thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
				if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
						&& !super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)
						&& !super.addMufflerToMachineList(currentTE, CASING_TEXTURE_ID)
						&& !super.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)
						&& !super.addOutputToMachineList(currentTE, CASING_TEXTURE_ID)) {
					
					if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
							&& (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 0)) {
					} else {
						formationChecklist = false;
					}
				}
			}
		}
		
		if (this.mInputBusses.size() > 1) {
			formationChecklist = false;
		}
		if (this.mInputHatches.size() > 1) {
			formationChecklist = false;
		}
		if (this.mOutputHatches.size() > 1) {
			formationChecklist = false;
		}
		if (this.mOutputBusses.size() > 1) {
			formationChecklist = false;
		}
		if (this.mEnergyHatches.size() > 1) {
			formationChecklist = false;
		}
		if (this.mMaintenanceHatches.size() != 1) {
			formationChecklist = false;
		}
		if (this.mMufflerHatches.size() != 1) {
			formationChecklist = false;
		}
		return formationChecklist;
	}
	
	@Override
	public int getPollutionPerTick(ItemStack aStack) {
		return 5;
	}
}