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
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GT_Recipe;
import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import space.impact.api.multiblocks.structure.IStructureDefinition;
import space.impact.api.multiblocks.structure.StructureDefinition;

import static com.impact.loader.ItemRegistery.IGlassBlock;
import static com.impact.loader.ItemRegistery.InsideBlock;
import static com.impact.mods.gregtech.blocks.Casing_Helper.sCaseCore2;
import static space.impact.api.multiblocks.structure.StructureUtility.ofBlock;

public class GTMTE_HeavyMetalCyclone extends GT_MetaTileEntity_MultiParallelBlockBase<GTMTE_HeavyMetalCyclone> {
	
	static IStructureDefinition<GTMTE_HeavyMetalCyclone> definition =
			StructureDefinition.<GTMTE_HeavyMetalCyclone>builder()
					.addShape("main", new String[][]{
							{"       ", "       ", "       ", "       ", "       ", "       ", " AAAAA "},
							{"  AAA  ", " AAAAA ", "  AAA  ", "  AAA  ", "  A~A  ", " AAAAA ", "AAAAAAA"},
							{" AAAAA ", " A B A ", " A B A ", " A B A ", " A B A ", " A B A ", "AAAAAAA"},
							{" AAAAA ", " CB BC ", " CB BC ", " CB BC ", " CB BC ", " AB BA ", "AAAAAAA"},
							{" AAAAA ", " A B A ", " A B A ", " A B A ", " A B A ", " A B A ", "AAAAAAA"},
							{"  AAA  ", " AACAA ", "  ACA  ", "  ACA  ", "  ACA  ", " AAAAA ", "AAAAAAA"},
							{"       ", "       ", "       ", "       ", "       ", "       ", " AAAAA "}
					})
					.addElement('A', ofBlock(sCaseCore2, 11))
					.addElement('B', ofBlock(InsideBlock, 1))
					.addElement('C', ofBlock(IGlassBlock, 0))
					.build();
	Block CASING = Casing_Helper.sCaseCore2;
	byte CASING_META = 11;
	ITexture INDEX_CASE = Textures.BlockIcons.casingTexturePages[3][CASING_META + 16];
	int CASING_TEXTURE_ID = CASING_META + 16 + 128 * 3;
	
	public GTMTE_HeavyMetalCyclone(int aID, String aNameRegional) {
		super(aID, "impact.multimachine.heavymetalcyclone", aNameRegional);
	}
	
	public GTMTE_HeavyMetalCyclone(String aName) {
		super(aName);
	}
	
	@Override
	public void construct(ItemStack itemStack, boolean b) {
		buildPiece(itemStack, b, 3, 4, 1);
	}
	
	@Override
	public IStructureDefinition<GTMTE_HeavyMetalCyclone> getStructureDefinition() {
		return definition;
	}
	
	@Override
	public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide, final byte aFacing, final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
		return aSide == aFacing ? new ITexture[]{INDEX_CASE, TextureFactory.of(aActive ? Textures.BlockIcons.MP1a : Textures.BlockIcons.MP1)} : new ITexture[]{INDEX_CASE};
	}
	
	@Override
	public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GTMTE_HeavyMetalCyclone(this.mName);
	}
	
	@Override
	protected MultiBlockTooltipBuilder createTooltip() {
		MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder("heavy_metal_cyclone");
		b
				.addInfo("info.0", "Centrifuge for heavy materials")
				.addParallelInfo(1, 256)
				.addTypeMachine("name", "Heavy Metal Cyclone")
				.addSeparator()
				.addController()
				.addEnergyHatch(3)
				.addMaintenanceHatch()
				.addInputBus(4)
				.addInputHatch(6)
				.addOutputBus(4)
				.addOutputHatch(4)
				.addParallelHatch()
				.addCasingInfo("case", "Cyclone Casing", 98)
				.addOtherStructurePart("other.0", "Cyclone Chamber Casing", "other.1", "inside structure")
				.addOtherStructurePart("other.2", "I-Glass", "other.3", "glass for structure")
				.signAndFinalize();
		return b;
	}
	
	@Override
	public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		return new GUI_BASE(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "MultiParallelBlockGUI.png");
	}
	
	@Override
	public GT_Recipe.GT_Recipe_Map getRecipeMap() {
		return GT_Recipe.GT_Recipe_Map.sCyclonRecipes;
	}
	
	@Override
	public boolean machineStructure(IGregTechTileEntity thisController) {
		final Vector3ic forgeDirection = new Vector3i(
				ForgeDirection.getOrientation(thisController.getBackFacing()).offsetX,
				ForgeDirection.getOrientation(thisController.getBackFacing()).offsetY,
				ForgeDirection.getOrientation(thisController.getBackFacing()).offsetZ
		);
		
		int minCasingAmount = 12;
		boolean formationChecklist = true;
		
		for (byte X = -3; X <= 3; X++) {
			for (byte Z = 1; Z >= -5; Z--) {
				
				final Vector3ic offset = rotateOffsetVector(forgeDirection, X, -2, Z);
				
				if (X == -3 && Z == 1 || X == 3 && Z == 1 || X == -3 && Z == -5 || X == 3 && Z == -5) {
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
		
		for (byte X = -2; X <= 2; X++) {
			for (byte Z = 0; Z >= -4; Z--) {
				
				final Vector3ic offset = rotateOffsetVector(forgeDirection, X, -1, Z);
				
				if (X >= -1 && X <= 1 && Z >= -3 && Z <= -1) {
					if (X == 0 && Z == -1 || X == -1 && Z == -2 || X == 1 && Z == -2 || X == 0 && Z == -3) {
						if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == InsideBlock)
								&& (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 1)) {
						} else {
							formationChecklist = false;
						}
						continue;
					}
					continue;
				}
				
				IGregTechTileEntity currentTE = thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
				if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
						&& !super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)
						&& !super.addParallHatchToMachineList(currentTE, CASING_TEXTURE_ID)
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
		
		for (byte X = -2; X <= 2; X++) {
			for (byte Y = 0; Y <= 2; Y++) {
				for (byte Z = 0; Z >= -4; Z--) {
					
					final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Y, Z);
					
					if (X == 0 && Z == 0 && Y == 0) {
						continue;
					}
					
					if (X == -2 && Z == 0 || X == 2 && Z == 0 || X == -2 && Z == -4 || X == 2 && Z == -4) {
						continue;
					}
					
					if (X >= -1 && X <= 1 && Z >= -3 && Z <= -1) {
						if (X == 0 && Z == -1 || X == -1 && Z == -2 || X == 1 && Z == -2 || X == 0 && Z == -3) {
							if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == InsideBlock)
									&& (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 1)) {
							} else {
								formationChecklist = false;
							}
							continue;
						}
						continue;
					}
					
					if (X == -2 && Z == -2 || X == 2 && Z == -2 || X == 0 && Z == -4) {
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
							&& !super.addParallHatchToMachineList(currentTE, CASING_TEXTURE_ID)
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
		
		for (byte X = -2; X <= 2; X++) {
			for (byte Z = 0; Z >= -4; Z--) {
				
				final Vector3ic offset = rotateOffsetVector(forgeDirection, X, 3, Z);
				
				if (X == -2 && Z == -2 || X == 2 && Z == -2 || X == 0 && Z == -4) {
					if (thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == IGlassBlock) {
					} else {
						formationChecklist = false;
					}
					continue;
				}
				
				if (X >= -1 && X <= 1 && Z >= -3 && Z <= -1) {
					if (X == 0 && Z == -1 || X == -1 && Z == -2 || X == 1 && Z == -2 || X == 0 && Z == -3) {
						if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == InsideBlock)
								&& (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 1)) {
						} else {
							formationChecklist = false;
						}
						continue;
					}
					continue;
				}
				
				IGregTechTileEntity currentTE = thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
				if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
						&& !super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)
						&& !super.addMufflerToMachineList(currentTE, CASING_TEXTURE_ID)
						&& !super.addParallHatchToMachineList(currentTE, CASING_TEXTURE_ID)
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
		
		for (byte X = -2; X <= 2; X++) {
			for (byte Z = 0; Z >= -4; Z--) {
				
				final Vector3ic offset = rotateOffsetVector(forgeDirection, X, 4, Z);
				
				if (X == -2 && Z == 0 || X == 2 && Z == 0 || X == -2 && Z == -4 || X == 2 && Z == -4) {
					continue;
				}
				
				IGregTechTileEntity currentTE = thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
				if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
						&& !super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)
						&& !super.addMufflerToMachineList(currentTE, CASING_TEXTURE_ID)
						&& !super.addParallHatchToMachineList(currentTE, CASING_TEXTURE_ID)
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
		
		if (this.mInputBusses.size() > 4) {
			formationChecklist = false;
		}
		if (this.mInputHatches.size() > 6) {
			formationChecklist = false;
		}
		if (this.mOutputBusses.size() > 4) {
			formationChecklist = false;
		}
		if (this.mOutputHatches.size() > 4) {
			formationChecklist = false;
		}
		if (this.mEnergyHatches.size() > 3) {
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