package com.impact.mods.gregtech.tileentities.multi.processing.defaultmachines;

import com.impact.api.recipe.MultiBlockRecipeBuilder;
import com.impact.mods.gregtech.blocks.Casing_Helper;
import com.impact.mods.gregtech.enums.Texture;
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

import java.util.HashSet;

import static gregtech.api.enums.GT_Values.E;
import static gregtech.api.enums.GT_Values.RES_PATH_GUI;
import static space.impact.api.multiblocks.structure.StructureUtility.ofBlock;

public class GTMTE_RailAssembler extends GTMTE_Impact_BlockBase<GTMTE_RailAssembler> {
	
	public static final GT_Recipe.GT_Recipe_Map sTrackAssemblerRecipes = new GT_Recipe.GT_Recipe_Map(
			new HashSet<GT_Recipe>(1000),
			"impact.recipe.railassembler",
			"Rail Assembler",
			null,
			RES_PATH_GUI + "basic/RailAssembler",
			6, 1, 0, 0,
			1, E, 1, E, true, false
	);
	static Block CASING = Casing_Helper.sCaseCore2;
	static byte CASING_META = 13;
	static ITexture INDEX_CASE = Textures.BlockIcons.casingTexturePages[3][CASING_META + 16];
	static int CASING_TEXTURE_ID = CASING_META + 16 + 128 * 3;
	static IStructureDefinition<GTMTE_RailAssembler> definition =
			StructureDefinition.<GTMTE_RailAssembler>builder()
					.addShape("main", new String[][]{
							{" AA", " AA"},
							{" AA", " AA"},
							{" AA", " AA"},
							{"~AA", "AAA"}
					})
					.addElement('A', ofBlock(CASING, CASING_META))
					.build();
	
	public GTMTE_RailAssembler(int aID, String aNameRegional) {
		super(aID, "impact.multimachine.railassembler", aNameRegional);
	}
	
	public GTMTE_RailAssembler(String aName) {
		super(aName);
	}
	
	@Override
	public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide, final byte aFacing, final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
		return aSide == aFacing ? new ITexture[]{INDEX_CASE, TextureFactory.of(aActive ? Texture.Icons.OVERLAY_RAIL_ASSEMBLER_ACTIVE : Texture.Icons.OVERLAY_RAIL_ASSEMBLER)} : new ITexture[]{INDEX_CASE};
	}
	
	@Override
	public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GTMTE_RailAssembler(this.mName);
	}
	
	@Override
	protected MultiBlockTooltipBuilder createTooltip() {
		MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder("rail_assembler");
		b
				.addTypeMachine("name", "Rail Assembler")
				.addController()
				.addEnergyHatch()
				.addMaintenanceHatch()
				.addInputBus(5)
				.addOutputBus(1)
				.addEnergyHatch(1)
				.addCasingInfo("case", "Rail Assembler Casing", 9)
				.signAndFinalize();
		return b;
	}
	
	@Override
	public GT_Recipe.GT_Recipe_Map getRecipeMap() {
		return sTrackAssemblerRecipes;
	}
	
	public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		return new GUI_BASE(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "MultiParallelBlockGUI.png");
	}
	
	@Override
	public boolean machineStructure(IGregTechTileEntity thisController) {
		final Vector3ic forgeDirection = new Vector3i(
				ForgeDirection.getOrientation(thisController.getBackFacing()).offsetX,
				ForgeDirection.getOrientation(thisController.getBackFacing()).offsetY,
				ForgeDirection.getOrientation(thisController.getBackFacing()).offsetZ
		);
		boolean formationChecklist = true;
		
		for (byte X = 0; X <= 2; X++) {
			for (byte Y = -1; Y <= 0; Y++) {
				for (byte Z = 0; Z <= 3; Z++) {
					
					if (X == 0 && Z == 0 && Y == 0) {
						continue;
					}
					if (X == 0 && Z != 0) {
						continue;
					}
					
					final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Y, Z);
					IGregTechTileEntity currentTE = thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
					if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
							&& !super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)
							&& !super.addMufflerToMachineList(currentTE, CASING_TEXTURE_ID)
							&& !super.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)
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
		
		if (this.mInputBusses.size() > 5) {
			formationChecklist = false;
		}
		if (this.mOutputBusses.size() > 1) {
			formationChecklist = false;
		}
		if (this.mEnergyHatches.size() > 1) {
			formationChecklist = false;
		}
		if (this.mMaintenanceHatches.size() > 1) {
			formationChecklist = false;
		}
		
		return formationChecklist;
	}
	
	@Override
	public void construct(ItemStack itemStack, boolean b) {
		buildPiece(itemStack, b, 0, 0, 3);
	}
	
	@Override
	public IStructureDefinition<GTMTE_RailAssembler> getStructureDefinition() {
		return definition;
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
}