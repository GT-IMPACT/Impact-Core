package com.impact.mods.gregtech.tileentities.multi.processing.defaultmachines;


import com.impact.api.recipe.MultiBlockRecipeBuilder;
import com.impact.mods.gregtech.blocks.Casing_Helper;
import com.impact.mods.gregtech.gui.base.GUI_BASE;
import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_Impact_BlockBase;
import com.impact.util.string.MultiBlockTooltipBuilder;
import com.impact.util.vector.Vector3i;
import com.impact.util.vector.Vector3ic;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GT_Recipe;
import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import space.impact.api.multiblocks.structure.IStructureDefinition;
import space.impact.api.multiblocks.structure.StructureDefinition;

import static com.impact.loader.ItemRegistery.IGlassBlock;
import static com.impact.mods.gregtech.blocks.Casing_Helper.sCaseCore2;
import static space.impact.api.multiblocks.structure.StructureUtility.ofBlock;

public class GTMTE_AdvDDDPrinter extends GTMTE_Impact_BlockBase<GTMTE_AdvDDDPrinter> {
	
	
	static IStructureDefinition<GTMTE_AdvDDDPrinter> definition =
			StructureDefinition.<GTMTE_AdvDDDPrinter>builder()
					.addShape("main", new String[][]{
							
							{"033330", "~33330", "000000",},
							{"033330", "022220", "000000",},
							{"033330", "022220", "000000",},
							{"033330", "022220", "000000",},
							{"033330", "022220", "000000",},
							{"033330", "033330", "000000",},
					})
					.addElement('0', ofBlock(sCaseCore2, 4))
					.addElement('2', ofBlock(sCaseCore2, 6))
					.addElement('3', ofBlock(IGlassBlock, 0))
					.build();
	Block CASING = Casing_Helper.sCaseCore2;
	byte CASING_META = 4;
	ITexture INDEX_CASE = Textures.BlockIcons.casingTexturePages[3][16 + CASING_META];
	int CASING_TEXTURE_ID = CASING_META + 16 + 128 * 3;
	
	public GTMTE_AdvDDDPrinter(int aID, String aNameRegional) {
		super(aID, "impact.multimachine.advdddprinter", aNameRegional);
	}
	
	public GTMTE_AdvDDDPrinter(String aName) {
		super(aName);
	}
	
	@Override
	public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide, final byte aFacing, final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
		return aSide == aFacing ? new ITexture[]{INDEX_CASE, TextureFactory.of(aActive ? Textures.BlockIcons.MP1a : Textures.BlockIcons.MP1)} : new ITexture[]{INDEX_CASE};
	}
	
	@Override
	public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GTMTE_AdvDDDPrinter(this.mName);
	}
	
	@Override
	public IStructureDefinition<GTMTE_AdvDDDPrinter> getStructureDefinition() {
		return definition;
	}
	
	@Override
	public void construct(ItemStack itemStack, boolean b) {
		buildPiece(itemStack, b, 0, 1, 0);
	}
	
	@Override
	protected MultiBlockTooltipBuilder createTooltip() {
		MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder("adv_ddd_printer");
		b
				.addInfo("info.0", "Assembler machines")
				.addTypeMachine("name", "4x4 Crafting")
				.addScrew()
				.addSeparatedBus()
				.addSeparator()
				.addController()
				.addEnergyHatch(4)
				.addMaintenanceHatch()
				.addInputBus(30)
				.addOutputBus(3)
				.addCasingInfo("case", "3D Printed Casing", 21)
				.addOtherStructurePart("other.0", "I-Glass", "other.1", "it is glass")
				.addOtherStructurePart("other.2", "Configuration Casing 4x4", "other.3", "core structure")
				.signAndFinalize();
		return b;
	}
	
	@Override
	public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		return new GUI_BASE(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "MultiParallelBlockGUI.png");
	}
	
	@Override
	public boolean checkRecipe(MultiBlockRecipeBuilder<?> recipeBuilder, int indexBus) {
		return recipeBuilder
				.checkSizeHatches(false, true, indexBus)
				.checkVoltage()
				.checkRecipeMap(indexBus)
				.checkInputEquals(indexBus, false)
				.checkEfficiency()
				.checkConsumption()
				.checkOutputs(true)
				.build();
	}
	
	@Override
	public GT_Recipe.GT_Recipe_Map getRecipeMap() {
		return GT_Recipe.GT_Recipe_Map.sBasicline;
	}
	
	@Override
	public boolean machineStructure(IGregTechTileEntity thisController) {
		final Vector3ic forgeDirection = new Vector3i(
				ForgeDirection.getOrientation(thisController.getBackFacing()).offsetX,
				ForgeDirection.getOrientation(thisController.getBackFacing()).offsetY,
				ForgeDirection.getOrientation(thisController.getBackFacing()).offsetZ
		);
		
		int minCasingAmount = 12; // Минимальное количество кейсов
		boolean formationChecklist = true; // Если все ок, машина собралась
		
		for (byte X = 0; X <= 5; X++) {
			for (byte Y = -1; Y <= 1; Y++) {
				for (int Z = 0; Z >= -5; Z--) {
					
					if (X == 0 && Z == 0 && Y == 0) {
						continue;
					}
					final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Y, Z);
					
					if ((X == 1 || X == 2 || X == 3 || X == 4) && (Z == -1 || Z == -2 || Z == -3 || Z == -4) && Y == 0) {
						if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == Casing_Helper.sCaseCore2)
								&& (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 6)) {
						} else {
							formationChecklist = false;
						}
						continue;
						
					}
					
					if ((X == 1 || X == 2 || X == 3 || X == 4) && (Z == 0 || Z == -5) && Y == 0) {
						if (thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == IGlassBlock) {
						} else {
							formationChecklist = false;
						}
						continue;
					}
					
					if ((X == 1 || X == 2 || X == 3 || X == 4) && Y == 1) {
						if (thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == IGlassBlock) {
						} else {
							formationChecklist = false;
						}
						continue;
					}
					
					IGregTechTileEntity currentTE = thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
					if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
							&& !super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)
							&& !super.addMufflerToMachineList(currentTE, CASING_TEXTURE_ID)
							&& !super.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)
							&& !super.addOutputToMachineList(currentTE, CASING_TEXTURE_ID)) {
						if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
								&& (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == CASING_META)) {
							minCasingAmount--;
						} else {
							formationChecklist = false;
						}
					}
				}
			}
		}
		
		if (this.mInputBusses.size() > 30) {
			formationChecklist = false;
		}
		if (this.mOutputBusses.size() > 3) {
			formationChecklist = false;
		}
		if (this.mEnergyHatches.size() > 4) {
			formationChecklist = false;
		}
		if (this.mMaintenanceHatches.size() != 1) {
			formationChecklist = false;
		}
		
		return formationChecklist;
	}
}