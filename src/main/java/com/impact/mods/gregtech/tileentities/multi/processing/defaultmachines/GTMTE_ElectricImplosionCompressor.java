package com.impact.mods.gregtech.tileentities.multi.processing.defaultmachines;

import com.impact.mods.gregtech.gui.base.GUI_BASE;
import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_Impact_BlockBase;
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
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_InputBus;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import space.impact.api.multiblocks.structure.IStructureDefinition;
import space.impact.api.multiblocks.structure.StructureDefinition;

import java.util.ArrayList;

import static gregtech.api.enums.GT_Values.V;
import static space.impact.api.multiblocks.structure.StructureUtility.ofBlock;

public class GTMTE_ElectricImplosionCompressor extends GTMTE_Impact_BlockBase<GTMTE_ElectricImplosionCompressor> {
	
	
	static Block CASING = GregTech_API.sBlockCasings2;
	static byte CASING_META = 0;
	static ITexture INDEX_CASE = Textures.BlockIcons.casingTexturePages[0][16 + CASING_META];
	static int CASING_TEXTURE_ID = CASING_META + 16;
	static IStructureDefinition<GTMTE_ElectricImplosionCompressor> definition =
			StructureDefinition.<GTMTE_ElectricImplosionCompressor>builder()
					.addShape("main", new String[][]{
							{"BBBBBB ", "BAAAAB~", "BBBBBBB"},
							{"BAAAABB", "B    BB", "BAAAABB"},
							{"BBBBBBB", "BAAAABB", "BBBBBBB"}
					})
					.addElement('A', ofBlock(GregTech_API.sBlockCasings1, 15))
					.addElement('B', ofBlock(CASING, CASING_META))
					.build();
	
	public GTMTE_ElectricImplosionCompressor(int aID, String aNameRegional) {
		super(aID, "impact.multis.electricimplosion", aNameRegional);
	}
	
	public GTMTE_ElectricImplosionCompressor(String aName) {
		super(aName);
	}
	
	@Override
	public void construct(ItemStack itemStack, boolean b) {
		buildPiece(itemStack, b, 6, 1, 0);
	}
	
	@Override
	public IStructureDefinition<GTMTE_ElectricImplosionCompressor> getStructureDefinition() {
		return definition;
	}
	
	@Override
	public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide, final byte aFacing, final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
		return aSide == aFacing ? new ITexture[]{INDEX_CASE, TextureFactory.of(aActive ? Textures.BlockIcons.MP1a : Textures.BlockIcons.MP1)} : new ITexture[]{INDEX_CASE};
	}
	
	@Override
	public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GTMTE_ElectricImplosionCompressor(this.mName);
	}
	
	@Override
	protected MultiBlockTooltipBuilder createTooltip() {
		MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder("aic");
		b
				.addInfo("info.0", "Babah!")
				.addTypeMachine("name", "Implosion Compressor")
				.addInfo("info.1", "Not used TNT!!!")
				.addInfo("info.2", "Energy consumption: (EU/t) = (NEI Recipe EU/t) * 1000")
				.addScrew()
				.addSeparatedBus()
				.addSeparator()
				.addController()
				.addEnergyHatch(2)
				.addMaintenanceHatch()
				.addMuffler()
				.addInputBus(5)
				.addOutputBus(3)
				.addCasingInfo("case", "Solid Steel Machine Casing", 29)
				.addOtherStructurePart("other.0", "Superconductor Coil", "other.1", "inside")
				.signAndFinalize();
		return b;
	}
	
	@Override
	public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		return new GUI_BASE(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "MultiParallelBlockGUI.png");
	}
	
	@Override
	public boolean checkRecipe(ItemStack itemStack) {
		ArrayList<ItemStack> tInputList = null;
		ArrayList<FluidStack> tFluidList = null;
		ItemStack[] tInputs = null;
		FluidStack[] tFluids = null;
		for (GT_MetaTileEntity_Hatch_InputBus tBus : mInputBusses) {
			if (isSeparated()) {
				ArrayList<ItemStack> tBusItems = new ArrayList<ItemStack>();
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
				tBusItems.add(GT_ModHandler.getIC2Item("industrialTnt", 64, null));
				tInputs = tBusItems.toArray(new ItemStack[]{});
				tFluids = tFluidList.toArray(new FluidStack[tFluidList.size()]);
			} else {
				tInputList = this.getStoredInputs();
				tInputList.add(GT_ModHandler.getIC2Item("industrialTnt", 64, null));
				tFluidList = this.getStoredFluids();
				tInputs    = tInputList.toArray(new ItemStack[tInputList.size()]);
				tFluids    = tFluidList.toArray(new FluidStack[tFluidList.size()]);
			}
			if (tInputList.size() > 0 || tFluidList.size() > 0) {
				long nominalV = getMaxInputVoltage();
				byte tTier = (byte) Math.max(1, GT_Utility.getTier(nominalV));
				
				GT_Recipe tRecipe;
				tRecipe = getRecipeMap().findRecipe(this.getBaseMetaTileEntity(), cashedRecipe, false, V[tTier], tFluids, tInputs);
				
				if (tRecipe != null) {
					cashedRecipe = tRecipe;
					if (!WorldProperties.needCleanroom(tRecipe, this)) {
						return false;
					}
					if (!WorldProperties.needSpace(tRecipe, this)) {
						return false;
					}
					
					ArrayList<ItemStack> outputItems = new ArrayList<>();
					ArrayList<FluidStack> outputFluids = new ArrayList<>();
					boolean found_Recipe = false;
					int processed = 0;
					while ((this.getStoredFluids().size() | this.getStoredInputs().size()) > 0 && processed < 1) {
						if ((tRecipe.mEUt * 1000 * (processed + 1L)) < nominalV && tRecipe.isRecipeInputEqual(true, tFluids, tInputs)) {
							found_Recipe = true;
							for (int i = 0; i < tRecipe.mOutputs.length; i++) {
								outputItems.add(tRecipe.getOutput(i));
							}
							for (int i = 0; i < tRecipe.mFluidOutputs.length; i++) {
								outputFluids.add(tRecipe.getFluidOutput(i));
							}
							++processed;
						} else {
							break;
						}
					}
					if (found_Recipe) {
						this.mEfficiency         = (10000 - (this.getIdealStatus() - this.getRepairStatus()) * 1000);
						this.mEfficiencyIncrease = 10000;
						long actualEUT = (tRecipe.mEUt * 1000L) * processed;
						
						OverclockCalculate.calculateOverclockedNessMulti((int) actualEUT, tRecipe.mDuration, 1, nominalV, this);
						
						if (this.mMaxProgresstime == Integer.MAX_VALUE - 1 && this.mEUt == Integer.MAX_VALUE - 1) {
							return false;
						}
						if (this.mEUt > 0) {
							this.mEUt = (-this.mEUt);
						}
						mOutputItems = new ItemStack[tRecipe.mOutputs.length];
						for (int i = 0; i < tRecipe.mOutputs.length; i++) {
							if (getBaseMetaTileEntity().getRandomNumber(10000) < tRecipe.getOutputChance(i)) {
								this.mOutputItems[i] = tRecipe.getOutput(i);
							}
						}
						this.mOutputFluids = new FluidStack[outputFluids.size()];
						this.mOutputFluids = outputFluids.toArray(this.mOutputFluids);
						this.updateSlots();
						return true;
					}
				}
			}
		}
		return false;
	}
	
	@Override
	public GT_Recipe.GT_Recipe_Map getRecipeMap() {
		return GT_Recipe.GT_Recipe_Map.sImplosionRecipes;
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
		
		for (x = -6; x <= 0; x++) {
			for (y = -1; y <= 1; y++) {
				for (z = 0; z >= -2; z--) {
					
					if (x == 0 && y == 0 && z == 0) {
						continue;
					}
					if (x == 0 && y == 1 && z == 0) {
						continue;
					}
					
					if ((x >= -5 && x <= -2) && y == 0 && z == -1) {
						continue;
					}
					
					final Vector3ic offset = rotateOffsetVector(forgeDirection, x, y, z);
					
					if ((x >= -5 && x <= -2) && y == 0 && (z == 0 || z == -2)) {
						
						//debug Utilits.setBlock(thisController, offset.x(), offset.y(), offset.z(), GregTech_API.sBlockCasings1, 15);
						
						if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == GregTech_API.sBlockCasings1)
								&& (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 15)) {
						} else {
							formationChecklist = false;
						}
						continue;
					}
					
					if ((x >= -5 && x <= -2) && (y == -1 || y == 1) && (z == -1)) {
						
						//debug Utilits.setBlock(thisController, offset.x(), offset.y(), offset.z(), GregTech_API.sBlockCasings1, 15);
						
						if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == GregTech_API.sBlockCasings1)
								&& (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 15)) {
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
		
		if (this.mInputBusses.size() > 5) {
			formationChecklist = false;
		}
		if (this.mOutputBusses.size() > 3) {
			formationChecklist = false;
		}
		if (this.mEnergyHatches.size() > 2) {
			formationChecklist = false;
		}
		if (this.mMaintenanceHatches.size() != 1) {
			formationChecklist = false;
		}
		if (this.mMufflerHatches.size() != 1) {
			formationChecklist = false;
		}
		
		return formationChecklist;
	}
	
	@Override
	public int getPollutionPerTick(ItemStack aStack) {
		return 500;
	}
}