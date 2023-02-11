package com.impact.mods.gregtech.tileentities.multi.processing.parallel;

import com.impact.mods.gregtech.gui.base.GUI_BASE;
import com.impact.mods.gregtech.tileentities.multi.implement.GT_MetaTileEntity_MultiParallelBlockBase;
import com.impact.util.string.MultiBlockTooltipBuilder;
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
import space.impact.api.ImpactAPI;
import space.impact.api.multiblocks.structure.IStructureDefinition;
import space.impact.api.multiblocks.structure.StructureDefinition;

import static com.impact.util.multis.GT_StructureUtility.ofHatchAdder;
import static space.impact.api.multiblocks.structure.StructureUtility.ofBlock;
import static space.impact.api.multiblocks.structure.StructureUtility.ofChain;

public class GTMTE_AdvancedCrackingUnit extends GT_MetaTileEntity_MultiParallelBlockBase<GTMTE_AdvancedCrackingUnit> {
	
	static Block CASING = GregTech_API.sBlockCasings4;
	static byte CASING_META = 1;
	static byte CASING_TEXTURE_ID = 49;
	static IStructureDefinition<GTMTE_AdvancedCrackingUnit> definition =
			StructureDefinition.<GTMTE_AdvancedCrackingUnit>builder()
					.addShape("main", new String[][]{
							{"     ", " AAA ", "  A  ", "  A  ", "  A  ", " AAA ", " A~A "},
							{" AAA ", "ABBBA", " BBB ", " BBB ", " BBB ", "ABBBA", "AAAAA"},
							{" ADA ", "ABCBA", "ABCBA", "ABCBA", "ABCBA", "ABCBA", "AAAAA"},
							{" AAA ", "ABBBA", " BBB ", " BBB ", " BBB ", "ABBBA", "AAAAA"},
							{"     ", " AAA ", "  A  ", "  A  ", "  A  ", " AAA ", " AAA "}
					})
					.addElement('A', ofChain(
							ofHatchAdder(GTMTE_AdvancedCrackingUnit::addParallHatchToMachineList, 49, CASING, CASING_META),
							ofHatchAdder(GTMTE_AdvancedCrackingUnit::addToMachineList, 49, CASING, CASING_META),
							ofBlock(CASING, CASING_META)
					))
					.addElement('B', ofBlock(GregTech_API.sBlockCasings5, 5))
					.addElement('C', ofBlock(GregTech_API.sBlockCasings8, 1))
					.addElement('D', ofHatchAdder(GTMTE_AdvancedCrackingUnit::addMufflerToMachineList, 49, ImpactAPI.RED))
					.build();
	
	public GTMTE_AdvancedCrackingUnit(int aID, String aNameRegional) {
		super(aID, "impact.multis.cracking_unit", aNameRegional);
	}
	
	public GTMTE_AdvancedCrackingUnit(String aName) {
		super(aName);
	}
	
	@Override
	public IStructureDefinition<GTMTE_AdvancedCrackingUnit> getStructureDefinition() {
		return definition;
	}
	
	@Override
	public void construct(ItemStack itemStack, boolean b) {
		buildPiece(itemStack, b, 2, 6, 0);
	}
	
	@Override
	public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
		return aSide == aFacing ?
				new ITexture[]{Textures.BlockIcons.CASING_BLOCKS[CASING_TEXTURE_ID],
						TextureFactory.of(aActive ?
								Textures.BlockIcons.OVERLAY_FRONT_VACUUM_FREEZER_ACTIVE :
								Textures.BlockIcons.OVERLAY_FRONT_VACUUM_FREEZER)} :
				new ITexture[]{Textures.BlockIcons.CASING_BLOCKS[CASING_TEXTURE_ID]};
		
	}
	
	@Override
	public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GTMTE_AdvancedCrackingUnit(this.mName);
	}
	
	public boolean isFacingValid(byte aFacing) {
		return aFacing > 1;
	}
	
	@Override
	protected MultiBlockTooltipBuilder createTooltip() {
		MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder("multi_cracking_unit");
		b
				.addParallelInfo(1, 256)
				.addTypeMachine("name", "Cracking Unit")
				.addSeparatedBus()
				.addSeparator()
				.addController()
				.addEnergyHatch(4)
				.addMaintenanceHatch()
				.addInputHatch(16)
				.addOutputHatch(8)
				.addInputBus(8)
				.addOutputBus(3)
				.addParallelHatch(1)
				.addMuffler()
				.addOtherStructurePart("other.0", "Naquadah Coil", "other.1", "around Pipe Machine Casing")
				.addOtherStructurePart("other.2", "PTFE Pipe Machine Casing", "other.3", "inside the hollow")
				.addCasingInfo("case", "Stainless Steel Machine Casings", 10)
				.addRedHint("Muffler Hatch")
				.signAndFinalize();
		return b;
	}
	
	public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		return new GUI_BASE(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "MultiParallelBlockGUI.png");
	}
	
	@Override
	public GT_Recipe_Map getRecipeMap() {
		return GT_Recipe_Map.sCrakingRecipes;
	}
	
	@Override
	public boolean machineStructure(IGregTechTileEntity thisController) {
		
		boolean formationChecklist = checkPiece(2, 6, 0);
		
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