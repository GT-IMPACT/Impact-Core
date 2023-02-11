package com.impact.mods.gregtech.tileentities.multi.processing.parallel;

import com.impact.mods.gregtech.gui.base.GUI_BASE;
import com.impact.mods.gregtech.tileentities.multi.implement.GT_MetaTileEntity_MultiParallelBlockBase;
import com.impact.util.string.MultiBlockTooltipBuilder;
import com.impact.util.vector.Vector3i;
import com.impact.util.vector.Vector3ic;
import gregtech.api.GregTech_API;
import gregtech.api.enums.Textures;
import gregtech.api.enums.Textures.BlockIcons;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Recipe.GT_Recipe_Map;
import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import space.impact.api.multiblocks.structure.IStructureDefinition;
import space.impact.api.multiblocks.structure.StructureDefinition;

import static com.impact.loader.ItemRegistery.InsideBlock;
import static space.impact.api.multiblocks.structure.StructureUtility.ofBlock;

public class GTMTE_ElectromagneticInductionFurnace extends GT_MetaTileEntity_MultiParallelBlockBase<GTMTE_ElectromagneticInductionFurnace> {
	
	static Block CASING = GregTech_API.sBlockCasings1;
	static int CASING_META = 11;
	static int CASING_TEXTURE_ID = 11;
	static IStructureDefinition<GTMTE_ElectromagneticInductionFurnace> definition =
			StructureDefinition.<GTMTE_ElectromagneticInductionFurnace>builder()
					.addShape("main", new String[][]{
							{" AAA ", "  A  ", "  A  ", "  A  ", " A~A "},
							{"AAAAA", " BBB ", " CCC ", " BBB ", "AAAAA"},
							{"AAAAA", "ABBBA", "ACCCA", "ABBBA", "AAAAA"},
							{"AAAAA", " BBB ", " CCC ", " BBB ", "AAAAA"},
							{" AAA ", "  A  ", "  A  ", "  A  ", " AAA "},
					})
					.addElement('A', ofBlock(CASING, CASING_META))
					.addElement('B', ofBlock(GregTech_API.sBlockCasings7, 6))
					.addElement('C', ofBlock(InsideBlock, 5))
					.build();
	
	public GTMTE_ElectromagneticInductionFurnace(int aID, String aNameRegional) {
		super(aID, "impact.multis.eif", aNameRegional);
	}
	
	public GTMTE_ElectromagneticInductionFurnace(String aName) {
		super(aName);
	}
	
	@Override
	public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
		return aSide == aFacing ? new ITexture[]{Textures.BlockIcons.CASING_BLOCKS[CASING_TEXTURE_ID], TextureFactory.of(aActive ? BlockIcons.OVERLAY_FRONT_ELECTRIC_BLAST_FURNACE_ACTIVE : Textures.BlockIcons.OVERLAY_FRONT_ELECTRIC_BLAST_FURNACE)} : new ITexture[]{Textures.BlockIcons.CASING_BLOCKS[CASING_TEXTURE_ID]};
	}
	
	@Override
	public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GTMTE_ElectromagneticInductionFurnace(this.mName);
	}
	
	@Override
	public void construct(ItemStack itemStack, boolean b) {
		buildPiece(itemStack, b, 2, 4, 0);
	}
	
	@Override
	public IStructureDefinition<GTMTE_ElectromagneticInductionFurnace> getStructureDefinition() {
		return definition;
	}
	
	@Override
	protected MultiBlockTooltipBuilder createTooltip() {
		MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder("multi_eif");
		b
				.addInfo("info.0", "Hey, its not EBF ha-ha!")
				.addParallelInfo(1, 256)
				.addTypeMachine("name", "Electric Blast Furnace")
				.addSeparatedBus()
				.addSeparator()
				.addController()
				.addEnergyHatch(4)
				.addMaintenanceHatch()
				.addMuffler()
				.addInputHatch(8)
				.addOutputHatch(3)
				.addInputBus(8)
				.addOutputBus(3)
				.addParallelHatch()
				.addOtherStructurePart("other.0", "Electromagnetic Chamber", "other.1", "middle center")
				.addOtherStructurePart("other.2", "Magnetic Coil", "other.3", "middle outside")
				.addCasingInfo("case", "Heat Proof Machine Casing", 24)
				.signAndFinalize();
		return b;
	}
	
	public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		return new GUI_BASE(aPlayerInventory, aBaseMetaTileEntity, "Electrom. Induction Furn.", "MultiParallelBlockGUI.png");
	}
	
	@Override
	public GT_Recipe.GT_Recipe_Map getRecipeMap() {
		return GT_Recipe_Map.sBlastRecipes;
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
			for (y = 0; y <= 4; y++) {
				for (z = 0; z >= -4; z--) {
					if (x == 0 && y == 0 && z == 0) {
						continue;
					}
					if ((x == -2 || x == 2) && (z == 0 || z == -4)) {
						continue;
					}
					
					final Vector3ic offset = rotateOffsetVector(forgeDirection, x, y, z);
					
					if ((y >= 1 && y <= 3) && (x == -2 || x == 2) && (z == -1 || z == -3)) {
						continue;
					}
					
					if ((y >= 1 && y <= 3) && (x == -1 || x == 1) && (z == 0 || z == -4)) {
						continue;
					}
					
					if ((y == 1 || y == 3) && (x == -1 || x == 0 || x == 1) && (z == -1 || z == -2 || z == -3)) {
						if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == GregTech_API.sBlockCasings7)
								&& (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 6)) {
						} else {
							formationChecklist = false;
						}
						continue;
					}
					
					if (y == 2 && (x == -1 || x == 0 || x == 1) && (z == -1 || z == -2 || z == -3)) {
						if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == InsideBlock)
								&& (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 5)) {
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
		
		if (this.mMufflerHatches.size() != 1) {
			formationChecklist = false;
		}
		if (this.mInputBusses.size() > 8) {
			formationChecklist = false;
		}
		if (this.mInputHatches.size() > 8) {
			formationChecklist = false;
		}
		if (this.mOutputBusses.size() > 3) {
			formationChecklist = false;
		}
		if (this.mOutputHatches.size() > 3) {
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
		return 50;
	}
}