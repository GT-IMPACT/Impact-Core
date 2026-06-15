package com.impact.mods.gregtech.tileentities.multi.processing.defaultmachines;

import static space.impact.api.multiblocks.structure.StructureUtility.ofBlock;

import com.impact.addon.gt.api.recipe.MultiBlockRecipeBuilder;
import com.impact.mods.gregtech.gui.base.GUI_BASE;
import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_Impact_BlockBase;
import com.impact.util.string.MultiBlockTooltipBuilder;
import com.impact.util.vector.Vector3i;
import com.impact.util.vector.Vector3ic;

import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

import gregtech.api.GregTech_API;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Recipe;
import space.impact.api.multiblocks.structure.IStructureDefinition;
import space.impact.api.multiblocks.structure.StructureDefinition;

public class GTMTE_ElectricImplosionCompressor extends GTMTE_Impact_BlockBase<GTMTE_ElectricImplosionCompressor> {
	
	
	static Block CASING = GregTech_API.sBlockCasings2;
	static byte CASING_META = 0;
	static ITexture INDEX_CASE = Textures.BlockIcons.casingTexturePages[0][16 + CASING_META];
	static int CASING_TEXTURE_ID = CASING_META + 16;
	static IStructureDefinition<GTMTE_ElectricImplosionCompressor> definition =
			StructureDefinition.<GTMTE_ElectricImplosionCompressor>builder()
					.addShape("main", new String[][]{
							{"BBBBBB ", "BAAAAB~", "BBBBBBB"},
							{"BAAAABB", "B    BB", "BAAAABB"},
							{"BBBBBBB", "BAAAABB", "BBBBBBB"}
					})
					.addElement('A', ofBlock(GregTech_API.sBlockCasings1, 15))
					.addElement('B', ofBlock(CASING, CASING_META))
					.build();
	
	public GTMTE_ElectricImplosionCompressor(int aID, String aNameRegional) {
		super(aID, "impact.multis.electricimplosion", aNameRegional);
	}
	
	public GTMTE_ElectricImplosionCompressor(String aName) {
		super(aName);
	}
	
	@Override
	public void construct(ItemStack itemStack, boolean b) {
		buildPiece(itemStack, b, 6, 1, 0);
	}
	
	@Override
	public IStructureDefinition<GTMTE_ElectricImplosionCompressor> getStructureDefinition() {
		return definition;
	}
	
	@Override
	public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide, final byte aFacing, final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
		return aSide == aFacing ? new ITexture[]{INDEX_CASE, TextureFactory.of(aActive ? Textures.BlockIcons.MP1a : Textures.BlockIcons.MP1)} : new ITexture[]{INDEX_CASE};
	}
	
	@Override
	public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GTMTE_ElectricImplosionCompressor(this.mName);
	}
	
	@Override
	protected MultiBlockTooltipBuilder createTooltip() {
		MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder("aic");
		b
				.addInfo("info.0", "Babah!")
				.addTypeMachine("name", "Implosion Compressor")
				.addInfo("info.1", "Not used TNT!!!")
				.addInfo("info.2", "Energy consumption: (EU/t) = (NEI Recipe EU/t) * 1000")
				.addScrew()
				.addSeparatedBus()
				.addSeparator()
				.addController()
				.addEnergyHatch(2)
				.addMaintenanceHatch()
				.addMuffler()
				.addInputBus(5)
				.addOutputBus(3)
				.addCasingInfo("case", "Solid Steel Machine Casing", 29)
				.addOtherStructurePart("other.0", "Superconductor Coil", "other.1", "inside")
				.signAndFinalize();
		return b;
	}
	
	@Override
	public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		return new GUI_BASE(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "MultiParallelBlockGUI.png");
	}

	@Override
	public boolean checkRecipe(MultiBlockRecipeBuilder<?> recipeBuilder, int indexBus) {
		ItemStack specialInput = GT_ModHandler.getIC2Item("industrialTnt", 64, null);
		return recipeBuilder
				.addFakeItems(indexBus, specialInput)
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
		return GT_Recipe.GT_Recipe_Map.sImplosionRecipes;
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
		
		for (x = -6; x <= 0; x++) {
			for (y = -1; y <= 1; y++) {
				for (z = 0; z >= -2; z--) {
					
					if (x == 0 && y == 0 && z == 0) {
						continue;
					}
					if (x == 0 && y == 1 && z == 0) {
						continue;
					}
					
					if ((x >= -5 && x <= -2) && y == 0 && z == -1) {
						continue;
					}
					
					final Vector3ic offset = rotateOffsetVector(forgeDirection, x, y, z);
					
					if ((x >= -5 && x <= -2) && y == 0 && (z == 0 || z == -2)) {
						
						//debug Utilits.setBlock(thisController, offset.x(), offset.y(), offset.z(), GregTech_API.sBlockCasings1, 15);
						
						if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == GregTech_API.sBlockCasings1)
								&& (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 15)) {
						} else {
							formationChecklist = false;
						}
						continue;
					}
					
					if ((x >= -5 && x <= -2) && (y == -1 || y == 1) && (z == -1)) {
						
						//debug Utilits.setBlock(thisController, offset.x(), offset.y(), offset.z(), GregTech_API.sBlockCasings1, 15);
						
						if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == GregTech_API.sBlockCasings1)
								&& (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 15)) {
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
						
						//debug Utilits.setBlock(thisController, offset.x(), offset.y(), offset.z(), CASING, CASING_META);
						
						if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
								&& (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == CASING_META)) {
						} else {
							formationChecklist = false;
						}
					}
				}
			}
		}
		
		if (this.mInputBusses.size() > 5) {
			formationChecklist = false;
		}
		if (this.mOutputBusses.size() > 3) {
			formationChecklist = false;
		}
		if (this.mEnergyHatches.size() > 2) {
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
		return 500;
	}
}