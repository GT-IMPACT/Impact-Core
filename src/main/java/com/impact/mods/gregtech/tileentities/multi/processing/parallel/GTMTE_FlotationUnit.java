package com.impact.mods.gregtech.tileentities.multi.processing.parallel;

import com.impact.mods.gregtech.gui.base.GUI_BASE;
import com.impact.mods.gregtech.tileentities.multi.implement.GT_MetaTileEntity_MultiParallelBlockBase;
import com.impact.util.string.MultiBlockTooltipBuilder;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GT_Recipe;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import space.impact.api.ImpactAPI;
import space.impact.api.multiblocks.structure.IStructureDefinition;
import space.impact.api.multiblocks.structure.StructureDefinition;

import static com.impact.util.multis.GT_StructureUtility.ofHatchAdder;
import static gregtech.api.GregTech_API.*;
import static space.impact.api.multiblocks.structure.StructureUtility.ofBlock;
import static space.impact.api.multiblocks.structure.StructureUtility.ofChain;

public class GTMTE_FlotationUnit extends GT_MetaTileEntity_MultiParallelBlockBase<GTMTE_FlotationUnit> {
	
	static IStructureDefinition<GTMTE_FlotationUnit> definition =
			StructureDefinition.<GTMTE_FlotationUnit>builder()
					.addShapeOldApi("main", new String[][]{
							{"000", "121", "121", "0.0",},
							{"000", "2A2", "2A2", "000",},
							{"000", "121", "121", "000",},
					})
					.addElement('0', ofChain(
							ofBlock(sBlockCasings4, 2),
							ofHatchAdder(GTMTE_FlotationUnit::addParallHatchToMachineList, 50, ImpactAPI.RED),
							ofHatchAdder(GTMTE_FlotationUnit::addMaintenanceToMachineList, 50, ImpactAPI.RED),
							ofHatchAdder(GTMTE_FlotationUnit::addOutputToMachineList, 50, ImpactAPI.RED),
							ofHatchAdder(GTMTE_FlotationUnit::addMufflerToMachineList, 50, ImpactAPI.RED),
							ofHatchAdder(GTMTE_FlotationUnit::addEnergyInputToMachineList, 50, ImpactAPI.RED),
							ofHatchAdder(GTMTE_FlotationUnit::addInputToMachineList, 50, ImpactAPI.RED)
					))
					.addElement('1', ofBlock(sBlockCasings2, 14))
					.addElement('2', ofBlock(sBlockCasings5, 2))
					.build();
	
	public GTMTE_FlotationUnit(int aID, String aNameRegional) {
		super(aID, "impact.multimachine.flotation", aNameRegional);
	}
	
	public GTMTE_FlotationUnit(String aName) {
		super(aName);
	}
	
	@Override
	public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
		if (aSide == aFacing) {
			return new ITexture[]{Textures.BlockIcons.CASING_BLOCKS[50], TextureFactory.of(aActive ? Textures.BlockIcons.OVERLAY_FRONT_DIESEL_ENGINE_ACTIVE : Textures.BlockIcons.OVERLAY_FRONT_DIESEL_ENGINE)};
		}
		return new ITexture[]{Textures.BlockIcons.CASING_BLOCKS[50]};
	}
	
	@Override
	public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GTMTE_FlotationUnit(this.mName);
	}
	
	@Override
	public void construct(ItemStack itemStack, boolean b) {
		buildPiece(itemStack, b, 1, 3, 0);
	}
	
	@Override
	public IStructureDefinition<GTMTE_FlotationUnit> getStructureDefinition() {
		return definition;
	}
	
	@Override
	protected MultiBlockTooltipBuilder createTooltip() {
		MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder("multi_flotation");
		b
				.addParallelInfo(1, 256)
				.addTypeMachine("name", "Flotation Unit")
				.addInfo("info.0", "Has the all recipes as the Ore Washer and Chemical Bath")
				.addSeparatedBus()
				.addSeparator()
				.addController()
				.addEnergyHatch(2)
				.addMaintenanceHatch()
				.addMuffler()
				.addInputBus(2)
				.addOutputBus(1)
				.addOutputHatch(1)
				.addInputHatch(2)
				.addParallelHatch()
				.addCasingInfo("case", "Stable Titanium Machine Casings", 6)
				.addOtherStructurePartAny("case.1", "Titanium Pipe Casing")
				.addOtherStructurePartAny("case.2", "Nichrome Coil")
				.signAndFinalize();
		return b;
	}
	
	@Override
	public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		return new GUI_BASE(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "MultiParallelBlockGUI.png");
	}
	
	@Override
	public GT_Recipe.GT_Recipe_Map getRecipeMap() {
		return GT_Recipe.GT_Recipe_Map.sFlotationUnitRecipes;
	}
	
	@Override
	public boolean machineStructure(IGregTechTileEntity thisController) {
		boolean check = checkPiece(1, 3, 0);
		
		if (this.mInputBusses.size() > 2) {
			check = false;
		}
		if (this.mInputHatches.size() > 2) {
			check = false;
		}
		if (this.mOutputBusses.size() > 1) {
			check = false;
		}
		if (this.mOutputHatches.size() > 1) {
			check = false;
		}
		if (this.mEnergyHatches.size() > 2) {
			check = false;
		}
		if (this.mMaintenanceHatches.size() != 1) {
			check = false;
		}
		if (this.mMufflerHatches.size() != 1) {
			check = false;
		}
		if (this.sParallHatchesIn.size() > 1) {
			check = false;
		}
		if (this.sParallHatchesOut.size() != 0) {
			check = false;
		}
		
		return check;
	}
}