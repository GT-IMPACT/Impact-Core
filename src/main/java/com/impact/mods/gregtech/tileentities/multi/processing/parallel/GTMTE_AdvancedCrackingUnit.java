package com.impact.mods.gregtech.tileentities.multi.processing.parallel;

import com.impact.mods.gregtech.gui.base.GUI_BASE;
import com.impact.mods.gregtech.tileentities.multi.implement.GT_MetaTileEntity_MultiParallelBlockBase;
import com.impact.util.multis.OverclockCalculate;
import com.impact.util.multis.WorldProperties;
import com.impact.util.string.MultiBlockTooltipBuilder;
import com.impact.util.vector.Vector3i;
import com.impact.util.vector.Vector3ic;
import gregtech.api.GregTech_API;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Recipe.GT_Recipe_Map;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import space.impact.api.ImpactAPI;
import space.impact.api.multiblocks.structure.IStructureDefinition;
import space.impact.api.multiblocks.structure.StructureDefinition;

import java.util.ArrayList;

import static com.impact.loader.ItemRegistery.IGlassBlock;
import static com.impact.util.multis.GT_StructureUtility.ofHatchAdder;
import static com.impact.util.recipe.RecipeHelper.calcTimeParallel;
import static com.impact.util.recipe.RecipeHelper.resizeItemStackSizeChance;
import static gregtech.api.enums.GT_Values.V;
import static space.impact.api.multiblocks.structure.StructureUtility.*;

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
						new GT_RenderedTexture(aActive ?
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
				.addSingleAnalog()
				.addParallelInfo(1, 256)
				.addTypeMachine("name", "Cracking Unit")
				.addSeparator()
				.addController()
				.addEnergyHatch(4)
				.addMaintenanceHatch()
				.addInputHatch(16)
				.addOutputHatch(8)
				.addInputBus(8)
				.addOutputBus(3)
				.addParallelHatch(1)
				.addOtherStructurePart("other.0", "Naquadah Coil", "other.1", "around Pipe Machine Casing")
				.addOtherStructurePart("other.2", "PTFE Pipe Machine Casing", "other.3", "inside the hollow")
				.addCasingInfo("case", "Stainless Steel Machine Casings")
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
	
	public boolean checkRecipe(ItemStack itemStack) {
		mCheckParallelCurrent = 0;
		if (sParallHatchesIn.size() > 0 && getRecipeCheckParallel()) {
			return false;
		}
		ArrayList<ItemStack> tInputList = this.getStoredInputs();
		ArrayList<FluidStack> tFluidList = this.getStoredFluids();
		ItemStack[] tInputs = tInputList.toArray(new ItemStack[0]);
		FluidStack[] tFluids = tFluidList.toArray(new FluidStack[0]);
		
		if (tInputList.size() > 0 || tFluidList.size() > 0) {
			long nominalV = getMaxInputVoltage();
			byte tTier = (byte) Math.max(1, GT_Utility.getTier(nominalV));
			GT_Recipe tRecipe = getRecipeMap().findRecipe(this.getBaseMetaTileEntity(), cashedRecipe, false, false, V[tTier], tFluids, tInputs);
			if (tRecipe != null) {
				
				cashedRecipe = tRecipe;
				
				if (!WorldProperties.needCleanroom(tRecipe, this)) {
					return false;
				}
				if (!WorldProperties.needSpace(tRecipe, this)) {
					return false;
				}
				ArrayList<ItemStack> outputItems = new ArrayList<ItemStack>();
				ArrayList<FluidStack> outputFluids = new ArrayList<FluidStack>();
				boolean found_Recipe = false;
				ItemStack[] tOut = new ItemStack[tRecipe.mOutputs.length];
				while ((tFluidList.size() > 0 || tInputList.size() > 0) && mCheckParallelCurrent < mParallel) {
					if ((tRecipe.mEUt * (mCheckParallelCurrent + 1L)) < nominalV && tRecipe.isRecipeInputEqual(true, tFluids, tInputs)) {
						found_Recipe = true;
						for (int h = 0; h < tRecipe.mOutputs.length; h++) {
							if (tRecipe.getOutput(h) != null) {
								tOut[h]           = tRecipe.getOutput(h).copy();
								tOut[h].stackSize = 0;
							}
						}
						for (int i = 0; i < tRecipe.mFluidOutputs.length; i++) {
							outputFluids.add(tRecipe.getFluidOutput(i));
						}
						++mCheckParallelCurrent;
					} else {
						break;
					}
				}
				tOut = resizeItemStackSizeChance(tOut, tRecipe, this);
				if (found_Recipe) {
					this.mEfficiency         = (10000 - (this.getIdealStatus() - this.getRepairStatus()) * 1000);
					this.mEfficiencyIncrease = 10000;
					long actualEUT = (long) (tRecipe.mEUt) * mCheckParallelCurrent;
					
					if (actualEUT > Integer.MAX_VALUE) {
						byte divider = 0;
						while (actualEUT > Integer.MAX_VALUE) {
							actualEUT = actualEUT / 2;
							divider++;
						}
						OverclockCalculate.calculateOverclockedNessMulti((int) (actualEUT / (divider * 2)), tRecipe.mDuration * (divider * 2), 1, nominalV, this);
					} else {
						OverclockCalculate.calculateOverclockedNessMulti((int) actualEUT, tRecipe.mDuration, 1, nominalV, this);
					}
					if (this.mMaxProgresstime == Integer.MAX_VALUE - 1 && this.mEUt == Integer.MAX_VALUE - 1) return false;
					this.mEUt             = this.mEUt > 0 ? (-this.mEUt) : this.mEUt;
					this.mMaxProgresstime = calcTimeParallel(this);
					this.mOutputItems     = tOut;
					this.mOutputFluids    = outputFluids.toArray(new FluidStack[0]);
					this.updateSlots();
					return true;
				}
			}
		}
		return false;
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