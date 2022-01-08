package com.impact.mods.gregtech.tileentities.multi.processing.parallel;

import com.impact.mods.gregtech.blocks.Casing_Helper;
import com.impact.mods.gregtech.gui.base.GUI_BASE;
import com.impact.mods.gregtech.tileentities.multi.implement.GT_MetaTileEntity_MultiParallelBlockBase;
import com.impact.util.string.MultiBlockTooltipBuilder;
import com.impact.util.vector.Vector3i;
import com.impact.util.vector.Vector3ic;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.ForgeDirection;
import space.impact.api.ImpactAPI;
import space.impact.api.multiblocks.structure.IStructureDefinition;
import space.impact.api.multiblocks.structure.StructureDefinition;

import static com.impact.loader.ItemRegistery.IGlassBlock;
import static com.impact.mods.gregtech.blocks.Casing_Helper.sCaseCore2;
import static com.impact.util.multis.GT_StructureUtility.ofHatchAdder;
import static gregtech.api.GregTech_API.*;
import static space.impact.api.multiblocks.structure.StructureUtility.ofBlock;
import static space.impact.api.multiblocks.structure.StructureUtility.ofChain;

public class GTMTE_FlotationUnit extends GT_MetaTileEntity_MultiParallelBlockBase<GTMTE_FlotationUnit> {
	
	public static String mModed;
	Block CASING = Casing_Helper.sCaseCore2;
	byte CASING_META = 2;
	ITexture INDEX_CASE = Textures.BlockIcons.casingTexturePages[3][CASING_META + 16];
	int CASING_TEXTURE_ID = CASING_META + 16 + 128 * 3;
	static IStructureDefinition<GTMTE_FlotationUnit> definition =
			StructureDefinition.<GTMTE_FlotationUnit>builder()
					.addShapeOldApi("main", new String[][]{
							{"000", "121", "121", "0.0",},
							{"000", "2A2", "2A2", "000",},
							{"000", "121", "121", "000",},
					})
					.addElement('0', ofChain(
							ofBlock(sBlockCasings4, 2),
							ofHatchAdder(GTMTE_FlotationUnit::addParallHatchToMachineList, 50, ImpactAPI.RED),
							ofHatchAdder(GTMTE_FlotationUnit::addMaintenanceToMachineList, 50, ImpactAPI.RED),
							ofHatchAdder(GTMTE_FlotationUnit::addOutputToMachineList, 50, ImpactAPI.RED),
							ofHatchAdder(GTMTE_FlotationUnit::addMufflerToMachineList, 50, ImpactAPI.RED),
							ofHatchAdder(GTMTE_FlotationUnit::addEnergyInputToMachineList, 50, ImpactAPI.RED),
							ofHatchAdder(GTMTE_FlotationUnit::addInputToMachineList, 50, ImpactAPI.RED)
					))
					.addElement('1', ofBlock(sBlockCasings2, 14))
					.addElement('2', ofBlock(sBlockCasings5, 2))
					.build();
	
	public GTMTE_FlotationUnit(int aID, String aNameRegional) {
		super(aID, "impact.multimachine.flotation", aNameRegional);
	}
	
	public GTMTE_FlotationUnit(String aName) {
		super(aName);
	}
	
	@Override
	public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
		if (aSide == aFacing) {
			return new ITexture[]{Textures.BlockIcons.CASING_BLOCKS[50], new GT_RenderedTexture(aActive ? Textures.BlockIcons.OVERLAY_FRONT_DIESEL_ENGINE_ACTIVE : Textures.BlockIcons.OVERLAY_FRONT_DIESEL_ENGINE)};
		}
		return new ITexture[]{Textures.BlockIcons.CASING_BLOCKS[50]};
	}
	
	@Override
	public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GTMTE_FlotationUnit(this.mName);
	}
	
	@Override
	public void construct(ItemStack itemStack, boolean b) {
		buildPiece(itemStack, b, 1, 3, 0);
	}
	
	@Override
	public IStructureDefinition<GTMTE_FlotationUnit> getStructureDefinition() {
		return definition;
	}
	
	@Override
	protected MultiBlockTooltipBuilder createTooltip() {
		MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder("multi_flotation");
		b
				.addParallelInfo(1, 256)
				.addTypeMachine("name", "Flotation Unit")
				.addInfo("info.0", "Has the all recipes as the Ore Washer and Chemical Bath")
				.addSeparatedBus()
				.addSeparator()
				.addController()
				.addEnergyHatch(2)
				.addMaintenanceHatch()
				.addMuffler()
				.addInputBus(2)
				.addOutputBus(1)
				.addOutputHatch(1)
				.addInputHatch(2)
				.addParallelHatch()
				.addCasingInfo("case", "Stable Titanium Machine Casings", 6)
				.addOtherStructurePartAny("case.1", "Titanium Pipe Casing")
				.addOtherStructurePartAny("case.2", "Nichrome Coil")
				.signAndFinalize();
		return b;
	}
	
	@Override
	public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		return new GUI_BASE(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "MultiParallelBlockGUI.png", mModed);
	}
	
	@Override
	public GT_Recipe.GT_Recipe_Map getRecipeMap() {
		return GT_Recipe.GT_Recipe_Map.sFlotationUnitRecipes;
	}
	
	@Override
	public boolean machineStructure(IGregTechTileEntity thisController) {
		boolean check = checkPiece(1, 3, 0);
		
		if (this.mInputBusses.size() > 2) {
			check = false;
		}
		if (this.mInputHatches.size() > 2) {
			check = false;
		}
		if (this.mOutputBusses.size() > 1) {
			check = false;
		}
		if (this.mOutputHatches.size() > 1) {
			check = false;
		}
		if (this.mEnergyHatches.size() > 2) {
			check = false;
		}
		if (this.mMaintenanceHatches.size() != 1) {
			check = false;
		}
		if (this.mMufflerHatches.size() != 1) {
			check = false;
		}
		if (this.sParallHatchesIn.size() > 1) {
			check = false;
		}
		if (this.sParallHatchesOut.size() != 0) {
			check = false;
		}
		
		return check;
	}
	
	//	@Override
//	public boolean machineStructure(IGregTechTileEntity thisController) {
//		final Vector3ic forgeDirection = new Vector3i(
//				ForgeDirection.getOrientation(thisController.getBackFacing()).offsetX,
//				ForgeDirection.getOrientation(thisController.getBackFacing()).offsetY,
//				ForgeDirection.getOrientation(thisController.getBackFacing()).offsetZ
//		);
//
//		int minCasingAmount = 12; // Минимальное количество кейсов
//		boolean formationChecklist = true; // Если все ок, машина собралась
//
//		for (byte X = -2; X <= 2; X++) {
//			for (byte Z = 0; Z >= -4; Z--) {
//
//				if (X == 0 && Z == 0) {
//					continue;
//				}
//
//				if ((X == -2 || X == 2) && (Z == 0 || Z == -4)) {
//					continue;
//				}
//
//				final Vector3ic offset = rotateOffsetVector(forgeDirection, X, 0, Z);
//
//				IGregTechTileEntity currentTE = thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
//				if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
//						&& !super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)
//						&& !super.addMufflerToMachineList(currentTE, CASING_TEXTURE_ID)
//						&& !super.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)
//						&& !super.addParallHatchToMachineList(currentTE, CASING_TEXTURE_ID)
//						&& !super.addOutputToMachineList(currentTE, CASING_TEXTURE_ID)) {
//
//					if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
//							&& (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == CASING_META)) {
//						minCasingAmount--;
//					} else {
//						formationChecklist = false;
//					}
//				}
//			}
//		}
//		for (byte X = -2; X <= 2; X++) {
//			for (byte Z = 0; Z >= -4; Z--) {
//				for (byte Y = 1; Y <= 3; Y++) {
//					final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Y, Z);
//
//					if (X == 0 && Z == 0) {
//						continue;
//					}
//
//					if ((X == -1 || X == 0 || X == 1) && (Z == 0 || Z == -4)) {
//						continue;
//					}
//
//					if ((Z == -1 || Z == -2 || Z == -3) && (X == -2 || X == 2)) {
//						continue;
//					}
//
//					if ((X == 0 && (Z == -1 || Z == -3)) || (Z == -2 && (X == -1 || X == 1))) {
//						if (thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == IGlassBlock) {
//						} else {
//							formationChecklist = false;
//						}
//						continue;
//					}
//
//					if (X == 0 && Z == -2) {
//						continue;
//					}
//
//					IGregTechTileEntity currentTE = thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
//					if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
//							&& !super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)
//							&& !super.addMufflerToMachineList(currentTE, CASING_TEXTURE_ID)
//							&& !super.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)
//							&& !super.addParallHatchToMachineList(currentTE, CASING_TEXTURE_ID)
//							&& !super.addOutputToMachineList(currentTE, CASING_TEXTURE_ID)) {
//
//						if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
//								&& (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == CASING_META)) {
//							minCasingAmount--;
//						} else {
//							formationChecklist = false;
//						}
//					}
//				}
//			}
//		}
//		for (byte X = -2; X <= 2; X++) {
//			for (byte Z = 0; Z >= -4; Z--) {
//
//				if ((X == -2 || X == 2) && (Z == 0 || Z == -4)) {
//					continue;
//				}
//
//				final Vector3ic offset = rotateOffsetVector(forgeDirection, X, 4, Z);
//
//				if ((X == 0 && (Z == -1 || Z == -3)) || (Z == -2 && (X == -1 || X == 1))) {
//					if (thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == IGlassBlock) {
//					} else {
//						formationChecklist = false;
//					}
//					continue;
//				}
//
//				IGregTechTileEntity currentTE = thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
//				if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
//						&& !super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)
//						&& !super.addMufflerToMachineList(currentTE, CASING_TEXTURE_ID)
//						&& !super.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)
//						&& !super.addParallHatchToMachineList(currentTE, CASING_TEXTURE_ID)
//						&& !super.addOutputToMachineList(currentTE, CASING_TEXTURE_ID)) {
//
//					if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
//							&& (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == CASING_META)) {
//						minCasingAmount--;
//					} else {
//						formationChecklist = false;
//					}
//				}
//			}
//		}
//
//		if (this.mInputBusses.size() > 6) {
//			formationChecklist = false;
//		}
//		if (this.mInputHatches.size() > 6) {
//			formationChecklist = false;
//		}
//		if (this.mOutputBusses.size() > 6) {
//			formationChecklist = false;
//		}
//		if (this.mOutputHatches.size() > 6) {
//			formationChecklist = false;
//		}
//		if (this.mEnergyHatches.size() > 4) {
//			formationChecklist = false;
//		}
//		if (this.mMaintenanceHatches.size() != 1) {
//			formationChecklist = false;
//		}
//		if (this.sParallHatchesIn.size() > 1) {
//			formationChecklist = false;
//		}
//		if (this.sParallHatchesOut.size() != 0) {
//			formationChecklist = false;
//		}
//
//		return formationChecklist;
//	}
	
	@Override
	public boolean checkRecipe(ItemStack itemStack) {
		return impactRecipeWithStackSize();
	}
	
	@Override
	public int getPollutionPerTick(ItemStack aStack) {
		return 0;
	}
	
	
	public void onScrewdriverRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
		if (aPlayer.isSneaking()) { 
			ScrewClick(aSide, aPlayer, aX, aY, aZ);
		}
	}
}