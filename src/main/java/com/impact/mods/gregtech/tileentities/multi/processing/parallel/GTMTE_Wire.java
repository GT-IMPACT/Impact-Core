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

public class GTMTE_Wire extends GT_MetaTileEntity_MultiParallelBlockBase<GTMTE_Wire> {
	
	static Block CASING = Casing_Helper.sCaseCore1;
	static byte CASING_META = 9;
	static ITexture INDEX_CASE = Textures.BlockIcons.casingTexturePages[3][CASING_META];
	static int CASING_TEXTURE_ID = CASING_META + 128 * 3;
	static IStructureDefinition<GTMTE_Wire> definition =
			StructureDefinition.<GTMTE_Wire>builder()
					.addShapeOldApi("main", new String[][]{
							{".0110", "..110", ".0000",},
							{".0110", "00..0", "00000",},
							{"00110", "00000", "00000",},
					})
					.addElement('0', ofBlock(sCaseCore1, 9))
					.addElement('1', ofBlock(IGlassBlock, 0))
					.build();
	
	public GTMTE_Wire(int aID, String aNameRegional) {
		super(aID, "impact.multimachine.wire", aNameRegional);
	}
	
	public GTMTE_Wire(String aName) {
		super(aName);
	}
	
	@Override
	public void construct(ItemStack itemStack, boolean b) {
		buildPiece(itemStack, b, 1, 1, 0);
	}
	
	@Override
	protected MultiBlockTooltipBuilder createTooltip() {
		MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder("multi_wire");
		b
				.addSingleAnalog()
				.addParallelInfo(1, 256)
				.addTypeMachine("name", "WireMill, Wire Assembler")
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
				.addCasingInfo("case", "Wire Factory Casing", 12)
				.signAndFinalize();
		return b;
	}
	
	@Override
	public IStructureDefinition<GTMTE_Wire> getStructureDefinition() {
		return definition;
	}
	
	@Override
	public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide, final byte aFacing, final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
		return aSide == aFacing ? new ITexture[]{INDEX_CASE, TextureFactory.of(aActive ? Textures.BlockIcons.MP1a : Textures.BlockIcons.MP1)} : new ITexture[]{INDEX_CASE};
	}
	
	@Override
	public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GTMTE_Wire(this.mName);
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
		
		for (byte X = -1; X <= 3; X++) {
			for (byte Y = -1; Y <= 1; Y++) {
				for (byte Z = 0; Z >= -2; Z--) {
					
					if (X == 0 && Z == 0 && Y == 0) {
						continue;
					}
					
					if ((X == -1 && Z == 0) || (X == -1 && Y == 1 && Z == -1)) {
						continue;
					}
					
					final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Y, Z);
					String glass = thisController.getBlockOffset(offset.x(), offset.y(), offset.z()).getUnlocalizedName();
					if ((X == 1 || X == 2) && (((Y == 0 || Y == 1) && (Z == 0)) || (Y == 1 && (Z == -1 || Z == -2)))) {
						if (thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == IGlassBlock) {
						} else {
							formationChecklist = false;
						}
						continue;
					}
					
					if ((X == 1 || X == 2) && Z == -1 && Y == 0) {
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
				GT_Recipe.GT_Recipe_Map.sWiremillRecipes,
				GT_Recipe.GT_Recipe_Map.sWireAssemblerRecipes
		);
	}
	
	@Override
	public boolean hasSwitchMap() {
		return true;
	}
}