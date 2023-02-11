package com.impact.mods.gregtech.tileentities.multi.processing.parallel;

import com.google.common.collect.Lists;
import com.impact.api.recipe.MultiBlockRecipeBuilder;
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

import static com.impact.loader.ItemRegistery.IGlassBlock;
import static com.impact.mods.gregtech.blocks.Casing_Helper.sCaseCore1;
import static space.impact.api.multiblocks.structure.StructureUtility.ofBlock;

public class GTMTE_Supply extends GT_MetaTileEntity_MultiParallelBlockBase<GTMTE_Supply> {
	
	
	Block CASING = Casing_Helper.sCaseCore1;
	byte CASING_META = 10;
	ITexture INDEX_CASE = Textures.BlockIcons.casingTexturePages[3][CASING_META];
	int CASING_TEXTURE_ID = CASING_META + 128 * 3;
	static IStructureDefinition<GTMTE_Supply> definition =
			StructureDefinition.<GTMTE_Supply>builder()
					.addShapeOldApi("main", new String[][]{
							{".000.", ".101.", ".101.", ".101.", ".000.",},
							{"00000", "1AAA1", "1AAA1", "1AAA1", "00000",},
							{"00.00", "0A.A0", "0A.A0", "0A.A0", "00000",},
							{"00000", "1AAA1", "1AAA1", "1AAA1", "00000",},
							{".000.", ".101.", ".101.", ".101.", ".000.",},
					})
					.addElement('0', ofBlock(sCaseCore1, 10))
					.addElement('1', ofBlock(IGlassBlock, 0))
					.build();
	
	public GTMTE_Supply(int aID, String aNameRegional) {
		super(aID, "impact.multimachine.supply", aNameRegional);
	}
	
	public GTMTE_Supply(String aName) {
		super(aName);
	}
	
	@Override
	public IStructureDefinition<GTMTE_Supply> getStructureDefinition() {
		return definition;
	}
	
	@Override
	public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide, final byte aFacing, final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
		return aSide == 1 ? new ITexture[]{INDEX_CASE, TextureFactory.of(aActive ? Textures.BlockIcons.MP1a : Textures.BlockIcons.MP1)} : new ITexture[]{INDEX_CASE};
	}
	
	@Override
	public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GTMTE_Supply(this.mName);
	}
	
	@Override
	public void construct(ItemStack itemStack, boolean b) {
		buildPiece(itemStack, b, 2, 0, 2);
	}
	
	@Override
	protected MultiBlockTooltipBuilder createTooltip() {
		MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder("multi_supply");
		b
				.addSingleAnalog()
				.addParallelInfo(1, 256)
				.addTypeMachine("name", "Autoclave, Dust Washer, Ore Washer, Chemical Bath")
				.addScrew()
				.addSeparatedBus()
				.addSeparator()
				.addController()
				.addEnergyHatch(4)
				.addMaintenanceHatch()
				.addInputBus(6)
				.addOutputBus(3)
				.addInputHatch(3)
				.addParallelHatch(1)
				.addCasingInfo("case", "Supply Production Casing", 38)
				.signAndFinalize();
		return b;
	}
	
	@Override
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
		
		int minCasingAmount = 12; // Минимальное количество кейсов
		boolean formationChecklist = true; // Если все ок, машина собралась
		
		for (byte X = -2; X <= 2; X++) {
			for (byte Y = -2; Y <= 2; Y++) {
				if (X == 0 && Y == 0) {
					continue;
				}
				if ((X == 2 || X == -2) && (Y == 2 || Y == -2)) {
					continue;
				}
				
				final Vector3ic offset = rotateOffsetVector(forgeDirection, X, 0, Y);
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
			for (byte Y = -2; Y <= 2; Y++) {
				for (byte Z = -1; Z >= -3; Z--) {
					
					if ((X == 2 || X == -2) && (Y == 2 || Y == -2)) {
						continue;
					}
					
					if (X == 1 && Y == 1 || X == -1 && Y == -1 || X == 1 && Y == -1 || X == -1 && Y == 1 || X == 0 && Y == 1 || X == 0 && Y == -1 || X == 1 && Y == 0 || X == -1 && Y == 0) {
						continue;
					}
					
					final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Z, Y);
					
					String glass = thisController.getBlockOffset(offset.x(), offset.y(), offset.z()).getUnlocalizedName();
					if (Y == 2 && X == 1 || Y == -2 && X == 1 || Y == 2 && X == -1 || Y == -2 && X == -1 || Y == 1 && X == 2 || Y == -1 && X == 2 || Y == 1 && X == -2 || Y == -1 && X == -2) {
						if (thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == IGlassBlock) {
						} else {
							formationChecklist = false;
						}
						continue;
					}
					
					if (X == 0 && Y == 0) {
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
		for (byte X = -2; X <= 2; X++) {
			for (byte Y = -2; Y <= 2; Y++) {
				if (X == 0 && Y == 0) {
					continue;
				}
				if ((X == 2 || X == -2) && (Y == 2 || Y == -2)) {
					continue;
				}
				
				final Vector3ic offset = rotateOffsetVector(forgeDirection, X, -4, Y);
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
		if (this.mOutputBusses.size() > 3) {
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
	
	@NotNull
	@Override
	public List<GT_Recipe.GT_Recipe_Map> getRecipesMap() {
		return Lists.newArrayList(
				GT_Recipe.GT_Recipe_Map.sAutoclaveRecipes,
				GT_Recipe.GT_Recipe_Map.sDustWashRecipes,
				GT_Recipe.GT_Recipe_Map.sOreWasherRecipes,
				GT_Recipe.GT_Recipe_Map.sChemicalBathRecipes
		);
	}
	
	@Override
	public boolean hasSwitchMap() {
		return true;
	}
}