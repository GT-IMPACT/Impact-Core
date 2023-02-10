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
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GT_Recipe.GT_Recipe_Map;
import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import space.impact.api.multiblocks.structure.IStructureDefinition;
import space.impact.api.multiblocks.structure.StructureDefinition;

import static com.impact.loader.ItemRegistery.IGlassBlock;
import static space.impact.api.multiblocks.structure.StructureUtility.ofBlock;
import static space.impact.api.multiblocks.structure.StructureUtility.ofBlockAnyMeta;

public class GTMTE_MultiChemicalReactor extends GT_MetaTileEntity_MultiParallelBlockBase<GTMTE_MultiChemicalReactor> {
	
	static Block CASING = GregTech_API.sBlockCasings8;
	static int CASING_META = 0;
	static int CASING_TEXTURE_ID = 176;
	static IStructureDefinition<GTMTE_MultiChemicalReactor> definition =
			StructureDefinition.<GTMTE_MultiChemicalReactor>builder()
					.addShape("main", new String[][]{
							{" AAA ", " ACA ", " ACA ", " ACA ", " AAA "},
							{"AAAAA", "ABBBA", "ABBBA", "ABBBA", "AAAAA"},
							{"AA~AA", "CB BC", "CB BC", "CB BC", "AAAAA"},
							{"AAAAA", "ABBBA", "ABBBA", "ABBBA", "AAAAA"},
							{" AAA ", " ACA ", " ACA ", " ACA ", " AAA "},
					})
					.addElement('A', ofBlock(CASING, CASING_META))
					.addElement('B', ofBlock(GregTech_API.sBlockCasings8, 1))
					.addElement('C', ofBlockAnyMeta(IGlassBlock))
					.build();
	
	public GTMTE_MultiChemicalReactor(int aID, String aNameRegional) {
		super(aID, "impact.multis.chemicalreactor", aNameRegional);
	}
	
	public GTMTE_MultiChemicalReactor(String aName) {
		super(aName);
	}
	
	@Override
	public IStructureDefinition<GTMTE_MultiChemicalReactor> getStructureDefinition() {
		return definition;
	}
	
	@Override
	public void construct(ItemStack itemStack, boolean b) {
		buildPiece(itemStack, b, 2, 0, 2);
	}
	
	@Override
	public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
		if (aSide == 1) {
			return new ITexture[]{Textures.BlockIcons.casingTexturePages[1][48], TextureFactory.of(aActive ? Textures.BlockIcons.OVERLAY_FRONT_LARGE_CHEMICAL_REACTOR_ACTIVE : Textures.BlockIcons.OVERLAY_FRONT_LARGE_CHEMICAL_REACTOR)};
		}
		return new ITexture[]{Textures.BlockIcons.casingTexturePages[1][48]};
	}
	
	@Override
	public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GTMTE_MultiChemicalReactor(this.mName);
	}
	
	public boolean isFacingValid(byte aFacing) {
		return aFacing > 1;
	}
	
	@Override
	protected MultiBlockTooltipBuilder createTooltip() {
		MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder("multi_chemical_reactor");
		b
				.addSingleAnalog()
				.addParallelInfo(1, 256)
				.addTypeMachine("name", "Chemical Reactor")
				.addSeparator()
				.addController()
				.addEnergyHatch(4)
				.addMaintenanceHatch()
				.addInputHatch(16)
				.addOutputHatch(8)
				.addInputBus(8)
				.addOutputBus(3)
				.addParallelHatch(1)
				.addOtherStructurePart("other.0", "PTFE Pipe Machine Casing", "mcr.other.1", "inside the hollow")
				.addCasingInfo("case", "Chemically Inert Machine Casings", 24)
				.signAndFinalize();
		return b;
	}
	
	public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		return new GUI_BASE(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "MultiParallelBlockGUI.png");
	}
	
	@Override
	public GT_Recipe_Map getRecipeMap() {
		return GT_Recipe_Map.sMultiblockChemicalRecipes;
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
			for (y = 0; y >= -4; y--) {
				for (z = -2; z <= 2; z++) {
					if (x == 0 && y == 0 && z == 0) {
						continue;
					}
					
					final Vector3ic offset = rotateOffsetVector(forgeDirection, x, y, z);
					
					if ((x == -2 || x == 2) && (z == 2 || z == -2)) {
						continue;
					}
					
					if ((y >= -3 && y <= -1) && (x == 0) && (z == -2 || z == 2)) {
						
						//debug Utilits.setBlock(thisController, offset.x(), offset.y(), offset.z(), IGlassBlock);
						
						if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == IGlassBlock)) {
						} else {
							formationChecklist = false;
						}
						continue;
					}
					
					if ((y >= -3 && y <= -1) && (x == -2 || x == 2) && (z == 0)) {
						
						//debug Utilits.setBlock(thisController, offset.x(), offset.y(), offset.z(), IGlassBlock);
						
						if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == IGlassBlock)) {
						} else {
							formationChecklist = false;
						}
						continue;
					}
					
					if ((y >= -3 && y <= -1) && x == 0 && z == 0) {
						continue;
					}
					
					if ((y >= -3 && y <= -1) && (x >= -1 && x <= 1) && (z >= -1 && z <= 1)) {
						
						if (x == 0 && z == 0) {
							continue;
						}
						
						//debug Utilits.setBlock(thisController, offset.x(), offset.y(), offset.z(), GregTech_API.sBlockCasings8, 1);
						
						if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == GregTech_API.sBlockCasings8)
								&& (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 1)) {
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
		
		if (this.mInputBusses.size() > 8) {
			formationChecklist = false;
		}
		if (this.mInputHatches.size() > 16) {
			formationChecklist = false;
		}
		if (this.mOutputBusses.size() > 3) {
			formationChecklist = false;
		}
		if (this.mOutputHatches.size() > 8) {
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
}