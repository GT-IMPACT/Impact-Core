package com.impact.mods.gregtech.tileentities.multi.processing.parallel;

import com.google.common.collect.Lists;
import com.impact.mods.gregtech.GT_RecipeMaps;
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

import static com.impact.mods.gregtech.blocks.Casing_Helper.sCaseCore1;
import static space.impact.api.multiblocks.structure.StructureUtility.ofBlock;

public class GTMTE_ArcFurnace extends GT_MetaTileEntity_MultiParallelBlockBase<GTMTE_ArcFurnace> {
	
	
	Block CASING = Casing_Helper.sCaseCore1;
	byte CASING_META = 13;
	ITexture INDEX_CASE = Textures.BlockIcons.casingTexturePages[3][CASING_META];
	int CASING_TEXTURE_ID = CASING_META + 128 * 3;
	static IStructureDefinition<GTMTE_ArcFurnace> definition =
			StructureDefinition.<GTMTE_ArcFurnace>builder()
					.addShapeOldApi("main", new String[][]{
							{".....", ".000.", ".000.", "00.00",},
							{".000.", "0...0", "0...0", "00000",},
							{".000.", "0...0", "0...0", "00000",},
							{".000.", "0...0", "0...0", "00000",},
							{".....", ".000.", ".000.", "00000",},
					})
					.addElement('0', ofBlock(sCaseCore1, 13))
					.build();
	
	public GTMTE_ArcFurnace(int aID, String aNameRegional) {
		super(aID, "impact.multimachine.arcfurnace", aNameRegional);
	}
	
	public GTMTE_ArcFurnace(String aName) {
		super(aName);
	}
	
	@Override
	public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide, final byte aFacing, final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
		return aSide == aFacing ? new ITexture[]{INDEX_CASE, TextureFactory.of(aActive ? Textures.BlockIcons.MP1a : Textures.BlockIcons.MP1)} : new ITexture[]{INDEX_CASE};
	}
	
	@Override
	public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GTMTE_ArcFurnace(this.mName);
	}
	
	@Override
	public void construct(ItemStack itemStack, boolean b) {
		buildPiece(itemStack, b, 2, 3, 0);
	}
	
	@Override
	public IStructureDefinition<GTMTE_ArcFurnace> getStructureDefinition() {
		return definition;
	}
	
	@Override
	protected MultiBlockTooltipBuilder createTooltip() {
		MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder("multi_furnace");
		b
				.addSingleAnalog()
				.addParallelInfo(1, 256)
				.addPollution(200, 12800)
				.addTypeMachine("name", "Arc Furnace, Alloy Smelter, Drying Oven")
				.addScrew()
				.addSeparatedBus()
				.addSeparator()
				.addController()
				.addEnergyHatch(4)
				.addMaintenanceHatch()
				.addInputBus(6)
				.addOutputBus(1)
				.addInputHatch(3)
				.addOutputHatch(1)
				.addMuffler()
				.addParallelHatch()
				.addCasingInfo("case", "Arc Casing", 39)
				.signAndFinalize();
		return b;
	}
	
	@Override
	public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		return new GUI_BASE(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "MultiParallelBlockGUI.png");
	}
	
	@NotNull
	@Override
	public List<GT_Recipe.GT_Recipe_Map> getRecipesMap() {
		return Lists.newArrayList(
				GT_Recipe.GT_Recipe_Map.sArcFurnaceRecipes,
				GT_Recipe.GT_Recipe_Map.sAlloySmelterRecipes,
				GT_RecipeMaps.sDryingOven
		);
	}
	
	@Override
	public boolean hasSwitchMap() {
		return true;
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
		
		for (byte X = -2; X <= 2; X++) {
			for (byte Z = 0; Z >= -4; Z--) {
				
				if (X == 0 && Z == 0) {
					continue;
				}
				
				final Vector3ic offset = rotateOffsetVector(forgeDirection, X, 0, Z);
				
				IGregTechTileEntity currentTE = thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
				if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
						&& !super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)
						&& !super.addMufflerToMachineList(currentTE, CASING_TEXTURE_ID)
						&& !super.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)
						&& !super.addParallHatchToMachineList(currentTE, CASING_TEXTURE_ID)
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
		for (byte X = -2; X <= 2; X++) {
			for (byte Z = 0; Z >= -4; Z--) {
				for (byte Y = 1; Y <= 2; Y++) {
					
					if ((X == -2 || X == 2) && (Z == 0 || Z == -4)) {
						continue;
					}
					
					if ((X == -1 || X == 1) && (Z == -1 || Z == -2 || Z == -3)) {
						continue;
					}
					
					if (X == 0 && (Z == -1 || Z == -3)) {
						continue;
					}
					
					final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Y, Z);
					
					if (X == 0 && Z == -2) {
						continue;
					}
					
					IGregTechTileEntity currentTE = thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
					if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
							&& !super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)
							&& !super.addMufflerToMachineList(currentTE, CASING_TEXTURE_ID)
							&& !super.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)
							&& !super.addParallHatchToMachineList(currentTE, CASING_TEXTURE_ID)
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
		for (byte X = -1; X <= 1; X++) {
			for (byte Z = -1; Z >= -3; Z--) {
				final Vector3ic offset = rotateOffsetVector(forgeDirection, X, 3, Z);
				
				IGregTechTileEntity currentTE = thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
				if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
						&& !super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)
						&& !super.addMufflerToMachineList(currentTE, CASING_TEXTURE_ID)
						&& !super.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)
						&& !super.addParallHatchToMachineList(currentTE, CASING_TEXTURE_ID)
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
		
		if (this.mInputBusses.size() > 6) {
			formationChecklist = false;
		}
		if (this.mInputHatches.size() > 3) {
			formationChecklist = false;
		}
		if (this.mOutputBusses.size() > 1) {
			formationChecklist = false;
		}
		if (this.mOutputHatches.size() > 3) {
			formationChecklist = false;
		}
		if (this.mMufflerHatches.size() != 1) {
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
	
	@Override
	public int getPollutionPerTick(ItemStack aStack) {
		return 200;
	}
}