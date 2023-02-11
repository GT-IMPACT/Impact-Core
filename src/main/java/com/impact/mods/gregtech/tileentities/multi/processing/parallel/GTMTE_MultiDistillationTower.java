package com.impact.mods.gregtech.tileentities.multi.processing.parallel;

import com.impact.mods.gregtech.gui.base.GUI_BASE;
import com.impact.mods.gregtech.tileentities.multi.implement.GT_MetaTileEntity_MultiParallelBlockBase;
import com.impact.util.string.MultiBlockTooltipBuilder;
import com.impact.util.vector.Vector3i;
import com.impact.util.vector.Vector3ic;
import gregtech.api.GregTech_API;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Output;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Recipe.GT_Recipe_Map;
import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import space.impact.api.multiblocks.structure.IStructureDefinition;
import space.impact.api.multiblocks.structure.StructureDefinition;

import java.util.ArrayList;

import static space.impact.api.multiblocks.structure.StructureUtility.ofBlock;

public class GTMTE_MultiDistillationTower extends GT_MetaTileEntity_MultiParallelBlockBase<GTMTE_MultiDistillationTower> {
	
	static Block CASING = GregTech_API.sBlockCasings4;
	static byte CASING_META = 1;
	static byte CASING_TEXTURE_ID = 49;
	static IStructureDefinition<GTMTE_MultiDistillationTower> definition =
			StructureDefinition.<GTMTE_MultiDistillationTower>builder()
					.addShape("main", new String[][]{
							{" AAA ", " AAA ", " AAA ", " AAA ", " AAA ", " AAA ", " AAA ", " AAA ", " AAA ", " AAA ", " AAA ", " A~A ",},
							{"AAAAA", "ABBBA", "ABBBA", "A   A", "A   A", "ABBBA", "ABBBA", "A   A", "A   A", "ABBBA", "ABBBA", "AAAAA",},
							{"AAAAA", "AB BA", "AB BA", "A   A", "A   A", "AB BA", "AB BA", "A   A", "A   A", "AB BA", "AB BA", "AAAAA",},
							{"AAAAA", "ABBBA", "ABBBA", "A   A", "A   A", "ABBBA", "ABBBA", "A   A", "A   A", "ABBBA", "ABBBA", "AAAAA",},
							{" AAA ", " AAA ", " AAA ", " AAA ", " AAA ", " AAA ", " AAA ", " AAA ", " AAA ", " AAA ", " AAA ", " AAA ",},
					})
					.addElement('A', ofBlock(CASING, CASING_META))
					.addElement('B', ofBlock(GregTech_API.sBlockCasings5, 1))
					.build();
	
	public GTMTE_MultiDistillationTower(int aID, String aNameRegional) {
		super(aID, "impact.multis.distilltower", aNameRegional);
	}
	
	public GTMTE_MultiDistillationTower(String aName) {
		super(aName);
	}
	
	@Override
	public space.impact.api.multiblocks.structure.IStructureDefinition<GTMTE_MultiDistillationTower> getStructureDefinition() {
		return definition;
	}
	
	@Override
	public void construct(ItemStack itemStack, boolean b) {
		buildPiece(itemStack, b, 2, 11, 0);
	}
	
	@Override
	public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
		return aSide == aFacing ? new ITexture[]{Textures.BlockIcons.CASING_BLOCKS[CASING_TEXTURE_ID], TextureFactory.of(aActive ? Textures.BlockIcons.OVERLAY_FRONT_VACUUM_FREEZER_ACTIVE : Textures.BlockIcons.OVERLAY_FRONT_VACUUM_FREEZER)} : new ITexture[]{Textures.BlockIcons.CASING_BLOCKS[CASING_TEXTURE_ID]};
	}
	
	@Override
	public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GTMTE_MultiDistillationTower(this.mName);
	}
	
	@Override
	protected MultiBlockTooltipBuilder createTooltip() {
		MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder("multi_distill");
		b
				.addInfo("info.0", "OMG!? Where Bart`s Big Tower?")
				.addSingleAnalog()
				.addParallelInfo(1, 256)
				.addTypeMachine("name", "Multi Distillation Tower")
				.addSeparator()
				.addController()
				.addEnergyHatch(4)
				.addMaintenanceHatch()
				.addInputHatch(9)
				.addOutputHatch(66)
				.addInputBus(4)
				.addOutputBus(1)
				.addParallelHatch(1)
				.addCasingInfo("case", "Clean Stainless Steel Machine Casing", 75)
				.addOtherStructurePart("other.0", "Kanthal Coil", "other.1", "inside the hollow")
				.signAndFinalize();
		return b;
	}
	
	public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		return new GUI_BASE(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "MultiParallelBlockGUI.png");
	}
	
	@Override
	public GT_Recipe.GT_Recipe_Map getRecipeMap() {
		return GT_Recipe_Map.sDistillationRecipes;
	}
	
	@Override
	public boolean machineStructure(IGregTechTileEntity thisController) {
		
		final Vector3ic forgeDirection = new Vector3i(
				ForgeDirection.getOrientation(thisController.getBackFacing()).offsetX,
				ForgeDirection.getOrientation(thisController.getBackFacing()).offsetY,
				ForgeDirection.getOrientation(thisController.getBackFacing()).offsetZ
		);
		
		boolean formationChecklist = true; // Если все ок, машина собралась
		
		int x, y, z;
		
		for (x = -2; x <= 2; x++) {
			for (y = 0; y <= 11; y++) {
				for (z = 0; z >= -4; z--) {
					if (x == 0 && y == 0 && z == 0) {
						continue;
					}
					
					final Vector3ic offset = rotateOffsetVector(forgeDirection, x, y, z);
					
					if ((x == -2 || x == 2) && (z == 0 || z == -4)) {
						continue;
					}
					
					if ((y > 0 && y < 11) && (x > -2 && x < 2) && (z < 0 && z > -4)) {
						if ((y <= 2 || y >= 5 && y <= 6 || y >= 9)) {
							if (x == 0 && z == -2) {
								continue;
							}
							
							if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == GregTech_API.sBlockCasings5)
									&& (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 1)) {
							} else {
								formationChecklist = false;
							}
							continue;
						}
						continue;
					}
					
					IGregTechTileEntity currentTE = thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
					if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
							&& !super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)
							&& !super.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)
							&& !super.addParallHatchToMachineList(currentTE, CASING_TEXTURE_ID)
							&& !super.addOutputToMachineList(currentTE, CASING_TEXTURE_ID)) {
						
						if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
								&& (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == CASING_META)) {
						} else {
							formationChecklist = false;
						}
					}
				}
			}
		}
		
		if (this.mInputBusses.size() > 6) {
			formationChecklist = false;
		}
		if (this.mInputHatches.size() > 9) {
			formationChecklist = false;
		}
		if (this.mOutputBusses.size() > 3) {
			formationChecklist = false;
		}
		if (this.mOutputHatches.size() > 66) {
			formationChecklist = false;
		}
		if (this.mEnergyHatches.size() > 4) {
			formationChecklist = false;
		}
		if (this.mMaintenanceHatches.size() != 1) {
			formationChecklist = false;
		}
		if (this.sParallHatchesIn.size() > 1) {
			formationChecklist = false;
		}
		if (this.sParallHatchesOut.size() != 0) {
			formationChecklist = false;
		}
		return formationChecklist;
	}
	
	private boolean dumpFluid(ArrayList<GT_MetaTileEntity_Hatch_Output> outputHatches, FluidStack copiedFluidStack, boolean restrictiveHatchesOnly) {
		for (GT_MetaTileEntity_Hatch_Output tHatch : outputHatches) {
			if (!isValidMetaTileEntity(tHatch) || (restrictiveHatchesOnly && tHatch.mMode == 0)) {
				continue;
			}
			if (GT_ModHandler.isSteam(copiedFluidStack)) {
				if (!tHatch.outputsSteam()) {
					continue;
				}
			} else {
				if (!tHatch.outputsLiquids()) {
					continue;
				}
				if (tHatch.isFluidLocked() && tHatch.getLockedFluidName() != null && !tHatch.getLockedFluidName().equals(copiedFluidStack.getUnlocalizedName())) {
					continue;
				}
			}
			int tAmount = tHatch.fill(copiedFluidStack, false);
			if (tAmount >= copiedFluidStack.amount) {
				boolean filled = tHatch.fill(copiedFluidStack, true) >= copiedFluidStack.amount;
				tHatch.onEmptyingContainerWhenEmpty();
				return filled;
			} else if (tAmount > 0) {
				copiedFluidStack.amount = copiedFluidStack.amount - tHatch.fill(copiedFluidStack, true);
				tHatch.onEmptyingContainerWhenEmpty();
			}
		}
		return false;
	}
	
	public boolean addOutput(FluidStack aLiquid, int i) {
		if (aLiquid == null) return false;
		FluidStack copiedFluidStack = aLiquid.copy();
		
		ArrayList<GT_MetaTileEntity_Hatch_Output> tOutputHatches = new ArrayList<>();
		for (GT_MetaTileEntity_Hatch_Output tHatch : mOutputHatches) {
			if (tHatch.getBaseMetaTileEntity().getYCoord() == getBaseMetaTileEntity().getYCoord() + 1 + i) {
				tOutputHatches.add(tHatch);
			}
		}
		if (!dumpFluid(tOutputHatches, copiedFluidStack, true)) {
			dumpFluid(tOutputHatches, copiedFluidStack, false);
		}
		return false;
	}
	
	@Override
	protected void addFluidOutputs(FluidStack[] mOutputFluids2) {
		for (int i = 0; i < mOutputFluids2.length; i++) {
			addOutput(mOutputFluids2[i], i);
		}
	}
}