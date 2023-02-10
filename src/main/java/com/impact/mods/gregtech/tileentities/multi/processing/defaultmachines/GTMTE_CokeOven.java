package com.impact.mods.gregtech.tileentities.multi.processing.defaultmachines;

import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_MBBase;
import com.impact.util.string.MultiBlockTooltipBuilder;
import gregtech.api.GregTech_API;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import gregtech.common.GT_Pollution;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.ChunkPosition;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class GTMTE_CokeOven extends GTMTE_MBBase {
	
	private GT_Recipe cashedRecipe = null;
	public GTMTE_CokeOven(int aID, String aNameRegional) {
		super(aID, "impact.multimachine.cokeoven", aNameRegional);
	}
	
	public GTMTE_CokeOven(String aName) {
		super(aName);
	}
	
	public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GTMTE_CokeOven(this.mName);
	}
	
	public String[] getDescription() {
		final MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder("coke_oven");
		b
				.addTypeMachine("name", "Coke Oven")
				.addPollution(25)
				.addSeparator()
				.addController()
				.addCasingInfo("case", "Coke Oven Bricks", 16)
				.addOtherStructurePartAny("hatch.bus.in", "Primitive Input Bus", 1, true)
				.addOtherStructurePartAny("hatch.bus.out", "Primitive Output Bus", 1, true)
				.addOtherStructurePartAny("hatch.out", "Primitive Output Hatch", 1, true)
				.signAndFinalize();
		if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			return b.getInformation();
		} else {
			return b.getStructureInformation();
		}
	}
	
	public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing,
								 byte aColorIndex, boolean aActive, boolean aRedstone) {
		if (aSide == aFacing) {
			return new ITexture[]{Textures.BlockIcons.casingTexturePages[1][53], TextureFactory.of(
					aActive ? Textures.BlockIcons.OVERLAY_COKE_OVEN_BRICK_ACTIVE
							: Textures.BlockIcons.OVERLAY_COKE_OVEN_BRICK)};
		}
		return new ITexture[]{Textures.BlockIcons.casingTexturePages[1][53]};
	}
	
	public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory,
							   IGregTechTileEntity aBaseMetaTileEntity) {
		return null;
	}
	
	@Override
	public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		return null;
	}
	
	public GT_Recipe.GT_Recipe_Map getRecipeMap() {
		return GT_Recipe.GT_Recipe_Map.sCokeOvenRecipes;
	}
	
	public boolean isCorrectMachinePart(ItemStack aStack) {
		return true;
	}
	
	public boolean isFacingValid(byte aFacing) {
		return aFacing > 1;
	}
	
	@Override
	public boolean checkRecipe(ItemStack aStack) {
		ArrayList<ItemStack> tInputList = getStoredInputs();
		int tInputList_sS = tInputList.size();
		for (int i = 0; i < tInputList_sS - 1; i++) {
			for (int j = i + 1; j < tInputList_sS; j++) {
				if (GT_Utility.areStacksEqual(tInputList.get(i), tInputList.get(j))) {
					if ((tInputList.get(i)).stackSize >= (tInputList.get(j)).stackSize) {
						tInputList.remove(j--);
						tInputList_sS = tInputList.size();
					} else {
						tInputList.remove(i--);
						tInputList_sS = tInputList.size();
						break;
					}
				}
			}
		}
		tInputList.add(mInventory[1]);
		ItemStack[] inputs = tInputList.toArray(new ItemStack[tInputList.size()]);
		
		ArrayList<FluidStack> tFluidList = getStoredFluids();
		int tFluidList_sS = tFluidList.size();
		for (int i = 0; i < tFluidList_sS - 1; i++) {
			for (int j = i + 1; j < tFluidList_sS; j++) {
				if (GT_Utility.areFluidsEqual(tFluidList.get(i), tFluidList.get(j))) {
					if (tFluidList.get(i).amount >= tFluidList.get(j).amount) {
						tFluidList.remove(j--);
						tFluidList_sS = tFluidList.size();
					} else {
						tFluidList.remove(i--);
						tFluidList_sS = tFluidList.size();
						break;
					}
				}
			}
		}
		FluidStack[] fluids = tFluidList.toArray(new FluidStack[tFluidList.size()]);
		
		if (inputs.length > 0 || fluids.length > 0) {
			long voltage = getMaxInputVoltage();
			GT_Recipe recipe = getRecipeMap()
					.findRecipe(getBaseMetaTileEntity(), cashedRecipe, false,
							false, 0, fluids, inputs
					);
			if (recipe != null && recipe.isRecipeInputEqual(true, fluids, inputs)) {
				cashedRecipe = recipe;
				this.mEfficiency         = 10000;
				this.mEfficiencyIncrease = 10000;
				
				int maxProgresstime = recipe.mDuration;
				this.mEUt             = 0;
				this.mMaxProgresstime = maxProgresstime;
				mOutputItems          = new ItemStack[recipe.mOutputs.length];
				for (int i = 0; i < recipe.mOutputs.length; i++) {
					if (getBaseMetaTileEntity().getRandomNumber(10000) < recipe.getOutputChance(i)) {
						this.mOutputItems[i] = recipe.getOutput(i);
					}
				}
				this.mOutputFluids = recipe.mFluidOutputs;
				this.updateSlots();
				this.mWrench        = true;
				this.mScrewdriver   = true;
				this.mSoftHammer    = true;
				this.mHardHammer    = true;
				this.mSolderingTool = true;
				this.mCrowbar       = true;
				return true;
			}
		}
		return false;
	}
	
	@SuppressWarnings({"deprecation"})
	@Override
	public boolean onRunningTick(ItemStack aStack) {
		if (this.mMaxProgresstime > 0 && (getBaseMetaTileEntity().getTimer() % 20L == 0L)) {
			GT_Pollution.addPollution(
					this.getBaseMetaTileEntity().getWorld(),
					new ChunkPosition(this.getBaseMetaTileEntity().getXCoord(),
							this.getBaseMetaTileEntity().getYCoord(), this.getBaseMetaTileEntity().getZCoord()
					),
					25
			);
		}
		return super.onRunningTick(aStack);
	}
	
	public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
		int xDir = ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()).offsetX;
		int zDir = ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()).offsetZ;
		mInputBusses1.clear();
		mOutputBusses1.clear();
		mOutputHatches1.clear();
		if (!aBaseMetaTileEntity.getAirOffset(xDir, 0, zDir)) {
			return false;
		}
		int tAmount = 0;
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				for (int h = -1; h < 2; h++) {
					if ((h != 0) || (((xDir + i != 0) || (zDir + j != 0)) && ((i != 0) || (j != 0)))) {
						IGregTechTileEntity tTileEntity = aBaseMetaTileEntity
								.getIGregTechTileEntityOffset(xDir + i, h, zDir + j);
						if ((!addPrimInputToMachineList(tTileEntity, 181)) && (!addPrimOutputToMachineList(
								tTileEntity, 181))) {
							if (aBaseMetaTileEntity.getBlockOffset(xDir + i, h, zDir + j)
									!= GregTech_API.sBlockCasings8) {
								return false;
							}
							if (aBaseMetaTileEntity.getMetaIDOffset(xDir + i, h, zDir + j) != 5) {
								return false;
							}
							
							mWrench        = true;
							mScrewdriver   = true;
							mSoftHammer    = true;
							mHardHammer    = true;
							mSolderingTool = true;
							mCrowbar       = true;
							tAmount++;
						}
					}
				}
			}
		}
		return tAmount >= 16;
	}
	
	public int getMaxEfficiency(ItemStack aStack) {
		return 10000;
	}
	
	public int getPollutionPerTick(ItemStack aStack) {
		return 0;
	}
	
	public int getDamageToComponent(ItemStack aStack) {
		return 0;
	}
	
	public boolean explodesOnComponentBreak(ItemStack aStack) {
		return false;
	}
}