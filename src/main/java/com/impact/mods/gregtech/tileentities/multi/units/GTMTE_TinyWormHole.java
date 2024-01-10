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
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import space.impact.api.ImpactAPI;
import space.impact.api.multiblocks.structure.IStructureDefinition;
import space.impact.api.multiblocks.structure.StructureDefinition;

import java.util.ArrayList;

import static com.impact.util.multis.GT_StructureUtility.ofHatchAdder;
import static gregtech.api.GregTech_API.*;
import static gregtech.api.util.GT_Recipe.GT_Recipe_Map.sTinyWormHoleRecipes;
import static space.impact.api.multiblocks.structure.StructureUtility.*;

public class GTMTE_TinyWormHole extends GTMTE_Impact_BlockBase<GTMTE_TinyWormHole> {
	
	
	public GTMTE_TinyWormHole(int aID, String aNameRegional) {
		super(aID, "impact.multis.tinywormhole", aNameRegional);
	}
	
	public GTMTE_TinyWormHole(String aName) {
		super(aName);
	}
	
	
	@Override
	public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GTMTE_TinyWormHole(mName);
	}
	
	@Override
	public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
		ITexture[] sTexture;
		if (aSide == aFacing) {
			sTexture = new ITexture[]{TextureFactory.of(Textures.BlockIcons.MACHINE_CASING_FUSION_GLASS, Dyes.getModulation(-1, Dyes._NULL.mRGBa)),
					TextureFactory.of(Textures.BlockIcons.OVERLAY_FUSION1)};
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
		return new GT_GUIContainer_MultiMachine(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "TinyWormHole.png");
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
	public GT_Recipe.GT_Recipe_Map getRecipeMap() {
		return sTinyWormHoleRecipes;
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
	public IStructureDefinition<GTMTE_TinyWormHole> getStructureDefinition() {
		return StructureDefinition.<GTMTE_TinyWormHole>builder()
				.addShape("main", new String[][]{
						{"FFFFF","FAAAF","FAAAF","FAJAF","FAAAF","FAAAF","GG~GG"},
						{"FFFFF","ABBBA","ADDDA","ADCDA","ADDDA","ABBBA","GGGGG"},
						{"FFFFF","ABBBA","ADCDA","JCECJ","ADCDA","ABBBA","GGGGG"},
						{"FFFFF","ABBBA","ADDDA","ADCDA","ADDDA","ABBBA","GGGGG"},
						{"FFFFF","FAAAF","FAAAF","FAJAF","FAAAF","FAAAF","GGGGG"}
				})
				.addElement('A', ofBlockAnyMeta(Block.getBlockFromItem(GT_ModHandler.getModItem("IC2", "blockAlloyGlass", 1L, 0).getItem())))
				.addElement('B', ofBlock(sBlockCasings4, 8))
				.addElement('C', ofBlock(sBlockCasings5, 13))
				.addElement('D', ofBlock(sBlockCasings5, 14))
				.addElement('E', ofBlock(sBlockCasings5, 15))
				.addElement('F', ofBlock(sBlockCasings7, 0))
				.addElement('G', ofChain(ofHatchAdder(GTMTE_TinyWormHole::addToMachineList, 192, ImpactAPI.RED), ofBlock(sBlockCasings7, 0)))
				.addElement('J', ofChain(ofHatchAdder(GTMTE_TinyWormHole::addInputToMachineList, 141, ImpactAPI.GREEN), ofBlock(sBlockCasings5, 13)))
				.build();
	}
	
	@Override
	public boolean machineStructure(IGregTechTileEntity thisController) {
		boolean check = checkPiece(2, 6, 0);
		
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
	protected MultiBlockTooltipBuilder createTooltip() {
		MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder("tiny_wormhole");
		b
				.addInfo("info.0", "Tiny WORMHOLE on The Earth!!!")
				.addTypeMachine("type", "Wormhole")
				.addSeparator()
				.addController()
				.sizeStructure(5,7,5)
				.addInputBus("hatches.0", "(UV or better)", 1)
				.addOutputBus("hatches.1", "(UV or better)", 1)
				.addEnergyHatch("hatches.2", "(UV or better)", 2)
				.addMaintenanceHatch()
				.addCasingInfo("case.0", "Robust Naquadah Alloy Machine Casing", 65)
				.addCasingInfo("case.5", "Intermix Chamber Casing", 9)
				.addCasingInfo("case.1", "Dyson Ring Casing", 20)
				.addCasingInfo("case.2", "Fusion Casing MKII", 18)
				.addCasingInfo("case.3", "Fusion Coil Block", 1)
				.addCasingInfo("case.4", "Core Chamber Casing", 56)
				.addHints()
				.addRedHint("Output Bus / Energy Hatch / Maintenance or Robust Naquadah Alloy Machine Casing")
				.addGreenHint("Input Bus or Intermix Chamber Casing")
				.signAndFinalize();
		return b;
	}
	
	@Override
	public void construct(ItemStack itemStack, boolean b) {
		buildPiece(itemStack, b, 2, 6, 0);
	}
	
	@Override
	public boolean hasSeparate() {
		return false;
	}
}
