package com.impact.mods.gregtech.tileentities.multi.processing.defaultmachines;

import com.google.common.collect.Lists;
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
import org.jetbrains.annotations.NotNull;
import space.impact.api.multiblocks.structure.IStructureDefinition;
import space.impact.api.multiblocks.structure.StructureDefinition;

import java.util.List;

import static com.impact.loader.ItemRegistery.SawMillBlock;
import static com.impact.mods.gregtech.blocks.Casing_Helper.sCaseCore2;
import static com.impact.mods.gregtech.enums.Texture.Icons.*;
import static space.impact.api.multiblocks.structure.StructureUtility.ofBlock;

public class GTMTE_SawMill extends GTMTE_Impact_BlockBase<GTMTE_SawMill> {
	
	static IStructureDefinition<GTMTE_SawMill> definition =
			StructureDefinition.<GTMTE_SawMill>builder()
					.addShapeOldApi("main", new String[][]{
							{"0.0", "..0", "010",},
							{"000", "...", ".1.",},
							{"0.0", "...", ".1.",},
							{"000", "...", ".1.",},
							{"0.0", "0.0", "010",},
					})
					.addElement('1', ofBlock(SawMillBlock, 0))
					.addElement('0', ofBlock(sCaseCore2, 9))
					.build();
	Block CASING = Casing_Helper.sCaseCore2;
	byte CASING_META = 9;
	ITexture INDEX_CASE = Textures.BlockIcons.casingTexturePages[3][16 + CASING_META];
	int CASING_TEXTURE_ID = CASING_META + 16 + 128 * 3;
	
	public GTMTE_SawMill(int aID, String aNameRegional) {
		super(aID, "impact.multimachine.sawmill", aNameRegional);
	}
	
	public GTMTE_SawMill(String aName) {
		super(aName);
	}
	
	@Override
	public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide, final byte aFacing, final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
		return aSide == aBaseMetaTileEntity.getBackFacing() ?
				new ITexture[]{INDEX_CASE, TextureFactory.of(aActive ? SAW_ACTIVE : SAW)} : aSide == aFacing ?
				new ITexture[]{INDEX_CASE, TextureFactory.of(aActive ? SAW_FRONT_ACTIVE : SAW_FRONT)} : new ITexture[]{INDEX_CASE};
	}
	
	@Override
	public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GTMTE_SawMill(this.mName);
	}
	
	@Override
	public void construct(ItemStack itemStack, boolean b) {
		buildPiece(itemStack, b, 0, 1, 0);
	}
	
	@Override
	public IStructureDefinition<GTMTE_SawMill> getStructureDefinition() {
		return definition;
	}
	
	@Override
	protected MultiBlockTooltipBuilder createTooltip() {
		MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder("saw_mill");
		b
				.addInfo("info.1", "Nooo! Do not saw me..")
				.addTypeMachine("name", "Saw Mill")
				.addSeparator()
				.addController()
				.addEnergyHatch()
				.addInputBus(1)
				.addOutputBus(1)
				.addInputHatch(1)
				.addCasingInfo("case", "Wooden Casing")
				.addOtherStructurePart("other.0", "Saw Mill Conveyor", "other.1", "Bottom middle")
				.signAndFinalize();
		return b;
	}
	
	@Override
	public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		return new GUI_BASE(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "MultiParallelBlockGUI.png");
	}
	
	@Override
	public boolean checkRecipe(MultiBlockRecipeBuilder<?> recipeBuilder, int indexBus) {
		noMaintenance();
		boolean checkRecipe = recipeBuilder
				.checkSizeHatches(true, true, indexBus)
				.checkVoltage()
				.checkRecipeMap(indexBus)
				.checkInputEquals(indexBus, false)
				.checkEfficiency()
				.checkConsumption()
				.checkOutputs(true)
				.build();
		
		if (!checkRecipe) {
			return false;
		}
		mEUt /= 4;
		mMaxProgresstime *= 2;
		return true;
	}
	
	@Override
	public boolean machineStructure(IGregTechTileEntity thisController) {
		final Vector3ic forgeDirection = new Vector3i(
				ForgeDirection.getOrientation(thisController.getBackFacing()).offsetX,
				ForgeDirection.getOrientation(thisController.getBackFacing()).offsetY,
				ForgeDirection.getOrientation(thisController.getBackFacing()).offsetZ
		);
		
		boolean formationChecklist = true; // Если все ок, машина собралась
		
		for (byte X = 0; X <= 2; X++) {
			for (byte Y = -1; Y <= 1; Y++) {
				for (byte Z = 0; Z >= -4; Z--) {
					
					if (X == 0 && Z == 0 && Y == 0) {
						continue;
					}
					
					if (X == 1 && Y == 0) {
						continue;
					}
					if (X == 0 && Y == 0 && !(Z == -4)) {
						continue;
					}
					if (X == 2 && Y == 0 && !(Z == 0 || Z == -4)) {
						continue;
					}
					if (X == 1 && Y == 1 && !(Z == -1 || Z == -3)) {
						continue;
					}
					if ((X == 0 || X == 2) && Y == -1 && !(Z == -0 || Z == -4)) {
						continue;
					}
					
					final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Y, Z);
					
					if (X == 1 && Y == -1) {
						if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == SawMillBlock)) {
						} else {
							formationChecklist = false;
						}
						continue;
					}
					
					IGregTechTileEntity currentTE = thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
					if (!super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)
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
		
		if (this.mInputBusses.size() > 1) {
			formationChecklist = false;
		}
		if (this.mInputHatches.size() > 1) {
			formationChecklist = false;
		}
		if (this.mOutputBusses.size() > 2) {
			formationChecklist = false;
		}
		if (this.mEnergyHatches.size() != 1) {
			formationChecklist = false;
		}
		noMaintenance();
		return formationChecklist;
	}
	
	@NotNull
	@Override
	public List<GT_Recipe.GT_Recipe_Map> getRecipesMap() {
		return Lists.newArrayList(
				GT_Recipe.GT_Recipe_Map.sSawMill0,
				GT_Recipe.GT_Recipe_Map.sSawMill1,
				GT_Recipe.GT_Recipe_Map.sSawMill2
		);
	}
	
	@Override
	public boolean hasSwitchMap() {
		return true;
	}
	
	@Override
	public boolean hasSeparate() {
		return false;
	}
}