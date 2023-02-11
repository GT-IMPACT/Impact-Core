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
import gregtech.api.util.GT_Recipe;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import space.impact.api.multiblocks.structure.IStructureDefinition;
import space.impact.api.multiblocks.structure.StructureDefinition;

import static gregtech.api.GregTech_API.sBlockCasings2;
import static space.impact.api.multiblocks.structure.StructureUtility.ofBlock;

public class GTMTE_FreezerSolidifier extends GT_MetaTileEntity_MultiParallelBlockBase<GTMTE_FreezerSolidifier> {
	
	static IStructureDefinition<GTMTE_FreezerSolidifier> definition =
			StructureDefinition.<GTMTE_FreezerSolidifier>builder()
					.addShapeOldApi("main", new String[][]{
							{".000.", ".0.0.", ".0.0.", ".000.",},
							{"00000", "0.0.0", "0.0.0", "00000",},
							{"00.00", ".0.0.", ".0.0.", "00000",},
							{"00000", "0.0.0", "0.0.0", "00000",},
							{".000.", ".0.0.", ".0.0.", ".000.",},
						
					})
					.addElement('0', ofBlock(sBlockCasings2, 1))
					.build();
	
	@Override
	public void construct(ItemStack itemStack, boolean b) {
		buildPiece(itemStack, b, 2, 0, 2);
	}
	
	@Override
	public IStructureDefinition<GTMTE_FreezerSolidifier> getStructureDefinition() {
		return definition;
	}
	
	public GTMTE_FreezerSolidifier(int aID, String aNameRegional) {
		super(aID, "impact.multimachine.freezsolidifier", aNameRegional);
	}
	
	public GTMTE_FreezerSolidifier(String aName) {
		super(aName);
	}
	
	public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GTMTE_FreezerSolidifier(this.mName);
	}
	
	@Override
	protected MultiBlockTooltipBuilder createTooltip() {
		MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder("multi_freezsolidifier");
		b
				.addSingleAnalog()
				.addParallelInfo(1, 256)
				.addTypeMachine("name", "Freezer Solidification")
				.addSeparatedBus()
				.addSeparator()
				.addController()
				.addEnergyHatch(4)
				.addMaintenanceHatch()
				.addInputBus(2)
				.addInputHatch(2)
				.addOutputBus(1)
				.addParallelHatch(1)
				.addCasingInfo("case", "Frost Proof Machine Casing", 55)
				.signAndFinalize();
		return b;
	}
	
	public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
		return aSide == 1 ? new ITexture[]{Textures.BlockIcons.CASING_BLOCKS[17], TextureFactory.of(aActive ? Textures.BlockIcons.OVERLAY_FRONT_VACUUM_FREEZER_ACTIVE : Textures.BlockIcons.OVERLAY_FRONT_VACUUM_FREEZER)} : new ITexture[]{Textures.BlockIcons.CASING_BLOCKS[17]};
	}
	
	public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		return new GUI_BASE(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "MultiParallelBlockGUI.png");
	}
	
	public GT_Recipe.GT_Recipe_Map getRecipeMap() {
		return GT_Recipe.GT_Recipe_Map.sFreezerSolidficationRecipes;
	}
	
	public boolean isCorrectMachinePart(ItemStack aStack) {
		return true;
	}
	
	public boolean isFacingValid(byte aFacing) {
		return aFacing > 1;
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
			for (byte Z = 0; Z >= -3; Z--) {
				for (byte Y = -2; Y <= 2; Y++) {
					
					if (X == 0 && Y == 0 && Z == 0) {
						continue;
					}
					if ((X == 2 || X == -2) && (Y == 2 || Y == -2)) {
						continue;
					}
					if ((Z == -1 || Z == -2) && (((X == 2 || X == -2) && Y == 0) || ((Y == 2 || Y == -2) && X == 0))) {
						continue;
					}
					
					if ((Z == -1 || Z == -2) && ((X == -1 && (Y == 1 || Y == -1)) || (X == 1 && (Y == 1 || Y == -1)))) {
						continue;
					}
					
					final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Z, Y);
					if ((Z == -1 || Z == -2) && Y == 0 && X == 0) {
						continue;
					}
					
					IGregTechTileEntity currentTE = thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
					if (!super.addMaintenanceToMachineList(currentTE, 17)
							&& !super.addInputToMachineList(currentTE, 17)
							&& !super.addMufflerToMachineList(currentTE, 17)
							&& !super.addEnergyInputToMachineList(currentTE, 17)
							&& !super.addParallHatchToMachineList(currentTE, 17)
							&& !super.addOutputToMachineList(currentTE, 17)) {
						
						if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == GregTech_API.sBlockCasings2)
								&& (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 1)) {
							minCasingAmount--;
						} else {
							formationChecklist = false;
						}
					}
				}
			}
		}
		
		if (this.mInputBusses.size() > 2) {
			formationChecklist = false;
		}
		if (this.mInputHatches.size() > 2) {
			formationChecklist = false;
		}
		if (this.mOutputBusses.size() > 1) {
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