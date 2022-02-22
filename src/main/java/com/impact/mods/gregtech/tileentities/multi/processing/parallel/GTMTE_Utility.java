package com.impact.mods.gregtech.tileentities.multi.processing.parallel;

import com.impact.mods.gregtech.blocks.Casing_Helper;
import com.impact.mods.gregtech.gui.base.GUI_BASE;
import com.impact.mods.gregtech.tileentities.multi.implement.GT_MetaTileEntity_MultiParallelBlockBase;
import com.impact.mods.gregtech.tileentities.multi.implement.RecipeBuilder;
import com.impact.util.multis.OverclockCalculate;
import com.impact.util.multis.WorldProperties;
import com.impact.util.string.MultiBlockTooltipBuilder;
import com.impact.util.vector.Vector3i;
import com.impact.util.vector.Vector3ic;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_InputBus;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import space.impact.api.multiblocks.structure.IStructureDefinition;
import space.impact.api.multiblocks.structure.StructureDefinition;

import java.util.ArrayList;

import static com.impact.loader.ItemRegistery.IGlassBlock;
import static com.impact.mods.gregtech.blocks.Casing_Helper.sCaseCore1;
import static com.impact.util.recipe.RecipeHelper.calcTimeParallel;
import static com.impact.util.recipe.RecipeHelper.resizeItemStackSizeChance;
import static gregtech.api.enums.GT_Values.V;
import static space.impact.api.multiblocks.structure.StructureUtility.ofBlock;

public class GTMTE_Utility extends GT_MetaTileEntity_MultiParallelBlockBase<GTMTE_Utility> {
	
	public String mModed;
	Block CASING = Casing_Helper.sCaseCore1;
	byte CASING_META = 11;
	ITexture INDEX_CASE = Textures.BlockIcons.casingTexturePages[3][CASING_META];
	int CASING_TEXTURE_ID = CASING_META + 128 * 3;
	static IStructureDefinition<GTMTE_Utility> definition =
			StructureDefinition.<GTMTE_Utility>builder()
					.addShapeOldApi("main", new String[][]{
							{"......", "000...", "0.0...", "000000",},
							{"..0000", "000000", "1.0000", "000000",},
							{"..0000", "000000", "1.0000", "000000",},
							{"..0000", "000000", "1.0000", "000000",},
							{"......", "000...", "000...", "000000",},
					})
					.addElement('0', ofBlock(sCaseCore1, 11))
					.addElement('1', ofBlock(IGlassBlock, 0))
					.build();
	
	public GTMTE_Utility(int aID, String aNameRegional) {
		super(aID, "impact.multimachine.utility", aNameRegional);
	}
	
	public GTMTE_Utility(String aName) {
		super(aName);
	}
	
	@Override
	public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide, final byte aFacing, final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
		return aSide == aFacing ? new ITexture[]{INDEX_CASE, new GT_RenderedTexture(aActive ? Textures.BlockIcons.MP1a : Textures.BlockIcons.MP1)} : new ITexture[]{INDEX_CASE};
	}
	
	@Override
	public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GTMTE_Utility(this.mName);
	}
	
	@Override
	public IStructureDefinition<GTMTE_Utility> getStructureDefinition() {
		return definition;
	}
	
	@Override
	public void construct(ItemStack itemStack, boolean b) {
		buildPiece(itemStack, b, 1, 2, 0);
	}
	
	@Override
	protected MultiBlockTooltipBuilder createTooltip() {
		MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder("multi_utility");
		b
				.addSingleAnalog()
				.addParallelInfo(1, 256)
				.addTypeMachine("name.0", "Compressor, Extractor, Canning,")
				.addTypeMachine("name.1", "Packager, Recycler, Hammer,")
				.addTypeMachine("name.2", "Lathe, Polarizer")
				.addScrew()
				.addSeparatedBus()
				.addSeparator()
				.addController()
				.addEnergyHatch(4)
				.addMaintenanceHatch()
				.addMuffler()
				.addInputBus(6)
				.addOutputBus(3)
				.addParallelHatch(1)
				.addCasingInfo("case", "Utility Machine Casing", 67)
				.signAndFinalize();
		return b;
	}
	
	@Override
	public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		return new GUI_BASE(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "MultiParallelBlockGUI.png", mModed);
	}
	
	@Override
	public GT_Recipe.GT_Recipe_Map getRecipeMap() {
		return mRecipeMode == 0 ? GT_Recipe.GT_Recipe_Map.sCompressorRecipes :
				mRecipeMode == 1 ? GT_Recipe.GT_Recipe_Map.sExtractorRecipes :
						mRecipeMode == 2 ? GT_Recipe.GT_Recipe_Map.sCannerRecipes :
								mRecipeMode == 3 ? GT_Recipe.GT_Recipe_Map.sBoxinatorRecipes :
										mRecipeMode == 4 ? GT_Recipe.GT_Recipe_Map.sRecyclerRecipes :
												mRecipeMode == 5 ? GT_Recipe.GT_Recipe_Map.sHammerRecipes :
														mRecipeMode == 6 ? GT_Recipe.GT_Recipe_Map.sLatheRecipes :
																GT_Recipe.GT_Recipe_Map.sPolarizerRecipes;
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
		
		for (byte X = -1; X <= 4; X++) {
			for (byte Z = 0; Z >= -4; Z--) {
				
				final Vector3ic offset = rotateOffsetVector(forgeDirection, X, -1, Z);
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
		for (byte X = -1; X <= 1; X++) {
			for (byte Z = 0; Z >= -4; Z--) {
				for (byte Y = 0; Y <= 1; Y++) {
					final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Y, Z);
					
					if (X == 0 && Y == 0 && Z == 0) {
						continue;
					}
					
					if ((Z == -1 || Z == -2 || Z == -3) && X == 0 && Y == 0) {
						continue;
					}
					if ((Z == -1 || Z == -2 || Z == -3) && X == -1 && Y == 0) {
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
		}
		for (byte X = 2; X <= 4; X++) {
			for (byte Z = -1; Z >= -3; Z--) {
				for (byte Y = 0; Y <= 2; Y++) {
					final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Y, Z);
					
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
		}
		if (this.mInputBusses.size() > 6) {
			formationChecklist = false;
		}
		if (this.mOutputBusses.size() > 3) {
			formationChecklist = false;
		}
		if (this.mEnergyHatches.size() > 4) {
			formationChecklist = false;
		}
		if (this.mMufflerHatches.size() != 1) {
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
	public boolean checkRecipe(ItemStack itemStack) {
		if (getRecipeMap() != GT_Recipe.GT_Recipe_Map.sBoxinatorRecipes) {
			return RecipeBuilder.checkParallelMachinesRecipe(this, true, true);
		}
		return checkRecipeBoxinator();
	}
	
	public boolean checkRecipeBoxinator() {
		if (sParallHatchesIn.size() > 0 && getRecipeCheckParallel()) {
			return false;
		}
		ArrayList<ItemStack> tInputList;
		ArrayList<FluidStack> tFluidList;
		ItemStack[] tInputs;
		FluidStack[] tFluids;
		for (GT_MetaTileEntity_Hatch_InputBus tBus : mInputBusses) {
			if (modeBuses == 0) {
				ArrayList<ItemStack> tBusItems = new ArrayList<>();
				tBus.mRecipeMap = getRecipeMap();
				if (isValidMetaTileEntity(tBus)) {
					for (int i = tBus.getBaseMetaTileEntity().getSizeInventory() - 1; i >= 0; i--) {
						if (tBus.getBaseMetaTileEntity().getStackInSlot(i) != null) {
							tBusItems.add(tBus.getBaseMetaTileEntity().getStackInSlot(i));
						}
					}
				}
				tInputList = this.getStoredInputs();
				tFluidList = this.getStoredFluids();
				tInputs    = tBusItems.toArray(new ItemStack[0]);
				tFluids    = tFluidList.toArray(new FluidStack[0]);
			} else {
				tInputList = this.getStoredInputs();
				tFluidList = this.getStoredFluids();
				tInputs    = tInputList.toArray(new ItemStack[0]);
				tFluids    = tFluidList.toArray(new FluidStack[0]);
			}
			if (tInputList.size() > 0 || tFluidList.size() > 0) {
				long nominalV = getMaxInputVoltage();
				byte tTier = (byte) Math.max(1, GT_Utility.getTier(nominalV));
				GT_Recipe tRecipe = getRecipeMap()
						.findRecipe(this.getBaseMetaTileEntity(), cashedRecipe, false, false, V[tTier], tFluids, tInputs);
				if (tRecipe != null) {
					
					cashedRecipe = tRecipe;
					
					if (!WorldProperties.needCleanroom(tRecipe, this)) {
						return false;
					}
					if (!WorldProperties.needSpace(tRecipe, this)) {
						return false;
					}
					ArrayList<FluidStack> outputFluids = new ArrayList<>();
					boolean found_Recipe = false;
					int processed = 0;
					ItemStack[] tOut = new ItemStack[tRecipe.mOutputs.length];
					while ((tFluidList.size() > 0 || tInputList.size() > 0) && processed < mParallel) {
						if ((tRecipe.mEUt * (processed + 1L)) < nominalV && tRecipe
								.isRecipeInputEqual(true, tFluids, tInputs)) {
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
							++processed;
						} else {
							break;
						}
					}
					tOut = resizeItemStackSizeChance(tOut, tRecipe, this, false);
					if (found_Recipe) {
						this.mEfficiency         = (10000 - (this.getIdealStatus() - this.getRepairStatus()) * 1000);
						this.mEfficiencyIncrease = 10000;
						long actualEUT = (long) (tRecipe.mEUt) * processed;
						
						if (actualEUT > Integer.MAX_VALUE) {
							byte divider = 0;
							while (actualEUT > Integer.MAX_VALUE) {
								actualEUT = actualEUT / 2;
								divider++;
							}
							OverclockCalculate.calculateOverclockedNessMulti((int) (actualEUT / (divider * 2)),
									tRecipe.mDuration * (divider * 2), 1, nominalV, this
							);
						} else {
							OverclockCalculate.calculateOverclockedNessMulti((int) actualEUT, tRecipe.mDuration, 1, nominalV,
									this
							);
						}
						if (this.mMaxProgresstime == Integer.MAX_VALUE - 1 && this.mEUt == Integer.MAX_VALUE - 1) return false;
						this.mEUt = this.mEUt > 0 ? (-this.mEUt) : this.mEUt;
						
						this.mMaxProgresstime = calcTimeParallel(this);
						this.mOutputItems     = tOut;
						this.mOutputFluids    = outputFluids.toArray(new FluidStack[0]);
						
						this.updateSlots();
						return true;
					}
				}
			}
		}
		return false;
	}
	
	@Override
	public int getPollutionPerTick(ItemStack aStack) {
		return 200;
	}
	
	
	public void onScrewdriverRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY,
										float aZ) {
		if (aPlayer.isSneaking()) {
			ScrewClick(aSide, aPlayer, aX, aY, aZ);
		} else if (aSide == getBaseMetaTileEntity().getFrontFacing()) {
			mRecipeMode++;
			if (mRecipeMode > 7) {
				mRecipeMode = 0;
			}
			
			mModed = (mRecipeMode == 0 ? " Compressor " : mRecipeMode == 1 ? " Extractor " : mRecipeMode == 2 ? " Canning " : mRecipeMode == 3 ? " Packager " : mRecipeMode == 4 ? " Recycler " : mRecipeMode == 5 ? " Hammer " : mRecipeMode == 6 ? " Lathe " : " Polarizer ");
			GT_Utility.sendChatToPlayer(aPlayer, "Now" + EnumChatFormatting.YELLOW + mModed + EnumChatFormatting.RESET + "Mode");
		}
	}
}