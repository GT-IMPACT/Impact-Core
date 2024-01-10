package com.impact.mods.gregtech.tileentities.multi.units;

import com.impact.addon.gt.api.recipe.MultiBlockRecipeBuilder;
import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_Impact_BlockBase;
import com.impact.util.string.MultiBlockTooltipBuilder;
import gregtech.api.enums.Dyes;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Textures;
import gregtech.api.gui.GT_GUIContainer_MultiMachine;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.*;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import space.impact.api.ImpactAPI;
import space.impact.api.multiblocks.structure.IStructureDefinition;
import space.impact.api.multiblocks.structure.StructureDefinition;

import java.util.ArrayList;

import static com.impact.util.multis.GT_StructureUtility.ofHatchAdder;
import static gregtech.api.GregTech_API.*;
import static gregtech.api.util.GT_Recipe.GT_Recipe_Map.sTesseractRecipes;
import static space.impact.api.multiblocks.structure.StructureUtility.ofBlock;
import static space.impact.api.multiblocks.structure.StructureUtility.ofChain;

public class GTMTE_Tesseract extends GTMTE_Impact_BlockBase<GTMTE_Tesseract> {
	
	public GTMTE_Tesseract(int aID, String aNameRegional) {
		super(aID, "impact.multis.tesseract", aNameRegional);
	}
	
	public GTMTE_Tesseract(String aName) {
		super(aName);
	}
	
	@Override
	public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GTMTE_Tesseract(mName);
	}
	
	@Override
	public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
		ITexture[] sTexture;
		if (aSide == aFacing) {
			sTexture = new ITexture[]{TextureFactory.of(Textures.BlockIcons.MACHINE_CASING_FUSION_GLASS, Dyes.getModulation(-1, Dyes._NULL.mRGBa)), TextureFactory.of(Textures.BlockIcons.OVERLAY_FUSION1)};
		} else {
			if (!aActive) {
				sTexture = new ITexture[]{TextureFactory.of(Textures.BlockIcons.MACHINE_CASING_FUSION_GLASS, Dyes.getModulation(-1, Dyes._NULL.mRGBa))};
			} else {
				sTexture = new ITexture[]{TextureFactory.of(Textures.BlockIcons.MACHINE_CASING_FUSION_GLASS_YELLOW, Dyes.getModulation(-1, Dyes._NULL.mRGBa))};
			}
		}
		return sTexture;
	}
	
	public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		return new GT_GUIContainer_MultiMachine(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "MultiblockDisplay.png");
	}
	
	@Override
	public boolean isCorrectMachinePart(ItemStack aStack) {
		return true;
	}
	
	@Override
	public boolean isFacingValid(byte aFacing) {
		return aFacing > 1;
	}
	
	@Override
	public int getMaxEfficiency(ItemStack aStack) {
		return 10000;
	}
	
	@Override
	public int getPollutionPerTick(ItemStack aStack) {
		return 0;
	}
	
	@Override
	public int getDamageToComponent(ItemStack aStack) {
		return 0;
	}
	
	@Override
	public boolean explodesOnComponentBreak(ItemStack aStack) {
		return false;
	}
	
	@Override
	public GT_Recipe.GT_Recipe_Map getRecipeMap() {
		return sTesseractRecipes;
	}

	@Override
	public boolean checkRecipe(MultiBlockRecipeBuilder<?> recipeBuilder, int indexBus) {
		return recipeBuilder
				.checkSizeHatches(true, true, indexBus)
				.checkVoltage()
				.checkRecipeMap(indexBus)
				.checkInputEquals(indexBus, true)
				.checkEfficiency()
				.checkConsumption()
				.checkOutputs(true)
				.build();
	}

	
	@Override
	protected MultiBlockTooltipBuilder createTooltip() {
		MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder("tesseract");
		b
				.addInfo("info.0", "Small WORMHOLE on The Earth!!!")
				.addTypeMachine("type", "Wormhole")
				.addSeparator()
				.addController()
				.sizeStructure(9,9,9)
				.addInputHatch("hatches.0", "(UHV or better)", 1)
				.addOutputHatch("hatches.1", "(UHV or better)", 1)
				.addInputBus("hatches.2", "(UHV or better)", 1)
				.addOutputBus("hatches.3", "(UHV or better)", 1)
				.addEnergyHatch("hatches.4", "(UHV or better)", 2)
				.addMaintenanceHatch()
				.addCasingInfo("case.0", "Robust Naquadah Alloy Machine Casing", 95)
				.addCasingInfo("case.5", "Intermix Chamber Casing", 23)
				.addCasingInfo("case.1", "Dyson Ring Casing", 24)
				.addCasingInfo("case.2", "Fusion Casing MKII", 4)
				.addCasingInfo("case.3", "Fusion Coil Block", 1)
				.addCasingInfo("case.4", "Core Chamber Casing", 16)
				.addHints()
				.addRedHint("Input Bus / Input Hatch or Intermix Chamber Casing")
				.addGreenHint("Output Hatch / Output Bus / Maintenance Hatch or Robust Naquadah Alloy Machine Casing")
				.addYellowHint("Energy Hatch or Robust Naquadah Alloy Machine Casing")
				.signAndFinalize();
		return b;
	}

	private static final IStructureDefinition<GTMTE_Tesseract> STRUCTURE = StructureDefinition.<GTMTE_Tesseract>builder()
			.addShape("main", new String[][]{
					{"000000000", "0       0", "0       0", "0       0", "0       0", "0       0", "0       0", "0       0", "000080000"},
					{"0       0", " 1     1 ", "         ", "         ", "         ", "         ", "         ", " 1     1 ", "0       0"},
					{"0       0", "         ", "  1   1  ", "    3    ", "   333   ", "    3    ", "  1   1  ", "         ", "0       0"},
					{"0       0", "         ", "    2    ", "   141   ", "  34443  ", "   141   ", "    3    ", "    6    ", "0  7~7  0"},
					{"0       0", "         ", "   232   ", "  34 43  ", "  34 43  ", "  34 43  ", "   333   ", "   656   ", "8  777  8"},
					{"0       0", "         ", "    2    ", "   141   ", "  34443  ", "   141   ", "    3    ", "    6    ", "0  777  0"},
					{"0       0", "         ", "  1   1  ", "    3    ", "   333   ", "    3    ", "  1   1  ", "         ", "0       0"},
					{"0       0", " 1     1 ", "         ", "         ", "         ", "         ", "         ", " 1     1 ", "0       0"},
					{"000000000", "0       0", "0       0", "0       0", "0       0", "0       0", "0       0", "0       0", "000080000"}
			})
			.addElement('0', ofBlock(sBlockCasings7, 0))
			.addElement('1', ofBlock(sBlockCasings5, 14))
			.addElement('2', ofChain(ofHatchAdder(GTMTE_Tesseract::addInputToMachineList, 141, ImpactAPI.RED), ofBlock(sBlockCasings5, 13)))
			.addElement('3', ofBlock(sBlockCasings5, 13))
			.addElement('4', ofBlock(sBlockCasings5, 15))
			.addElement('5', ofBlock(sBlockCasings4, 7))
			.addElement('6', ofBlock(sBlockCasings4, 8))
			.addElement('7', ofChain(
					ofHatchAdder(GTMTE_Tesseract::addOutputToMachineList, 192, ImpactAPI.GREEN),
					ofHatchAdder(GTMTE_Tesseract::addMaintenanceToMachineList, 192, ImpactAPI.GREEN),
					ofBlock(sBlockCasings7, 0)))
			.addElement('8', ofChain(ofHatchAdder(GTMTE_Tesseract::addEnergyInputToMachineList, 192, ImpactAPI.YELLOW), ofBlock(sBlockCasings7, 0)))
			.build();
	
	@Override
	public IStructureDefinition<GTMTE_Tesseract> getStructureDefinition() {
		return STRUCTURE;
	}
	
	@Override
	public boolean machineStructure(IGregTechTileEntity thisController) {
		boolean check = checkPiece(4, 8, 3);
		
		for (GT_MetaTileEntity_Hatch_Energy mEnergyHatch : this.mEnergyHatches) {
			if (mEnergyHatch.mTier < 8) {
				check = false;
				break;
			}
		}
		
		for (GT_MetaTileEntity_Hatch_InputBus mInputBuss : this.mInputBusses) {
			if (mInputBuss.mTier < 8) {
				check = false;
				break;
			}
		}
		
		for (GT_MetaTileEntity_Hatch_OutputBus mOutputBuss : this.mOutputBusses) {
			if (mOutputBuss.mTier < 8) {
				check = false;
				break;
			}
		}
		
		for (GT_MetaTileEntity_Hatch_Input mInputHatch : this.mInputHatches) {
			if (mInputHatch.mTier < 8) {
				check = false;
				break;
			}
		}
		
		for (GT_MetaTileEntity_Hatch_Output mOutputHatch : this.mOutputHatches) {
			if (mOutputHatch.mTier < 8) {
				check = false;
				break;
			}
		}
		
		if (mInputHatches.size() > 1) check = false;
		if (mOutputHatches.size() > 1) check = false;
		if (mInputBusses.size() > 1) check = false;
		if (mOutputBusses.size() > 1) check = false;
		if (mEnergyHatches.size() > 2) check = false;
		
		return check;
	}
	
	@Override
	public void construct(ItemStack itemStack, boolean b) {
		buildPiece(itemStack, b, 4, 8, 3);
	}
	
	@Override
	public boolean hasSeparate() {
		return false;
	}
}