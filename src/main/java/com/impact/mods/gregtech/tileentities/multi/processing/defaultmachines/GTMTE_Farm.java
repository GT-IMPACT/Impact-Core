package com.impact.mods.gregtech.tileentities.multi.processing.defaultmachines;

import com.impact.mods.gregtech.blocks.Casing_Helper;
import com.impact.util.string.MultiBlockTooltipBuilder;
import gregtech.api.GregTech_API;
import gregtech.api.enums.Textures;
import gregtech.api.gui.GT_GUIContainer_MultiMachine;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import ic2.core.Ic2Items;
import ic2.core.util.StackUtil;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.input.Keyboard;
import space.impact.api.multiblocks.alignment.constructable.IMultiBlockInfoContainer;
import space.impact.api.multiblocks.alignment.enumerable.ExtendedFacing;
import space.impact.api.multiblocks.structure.IStructureDefinition;
import space.impact.api.multiblocks.structure.StructureDefinition;

import java.util.ArrayList;

import static space.impact.api.multiblocks.alignment.constructable.IMultiBlockInfoContainer.registerTileClass;
import static space.impact.api.multiblocks.structure.StructureUtility.ofBlock;
import static space.impact.api.multiblocks.structure.StructureUtility.ofBlockAnyMeta;

public class GTMTE_Farm extends GT_MetaTileEntity_MultiBlockBase {
	
	private GT_Recipe cashedRecipe = null;
	
	public GTMTE_Farm(int aID, String aNameRegional) {
		super(aID, "impact.multimachine.farm", aNameRegional);
		run();
	}
	
	public GTMTE_Farm(String aName) {
		super(aName);
		run();
	}
	
	public void run() {
		registerTileClass(GTMTE_Farm.class.getCanonicalName(), new IMultiBlockInfoContainer<GTMTE_Farm>() {
			//region Structure
			private final IStructureDefinition<GTMTE_Farm> definition =
					StructureDefinition.<GTMTE_Farm>builder()
							.addShape("main", new String[][]{
									{"AAAAA", "AAAAA", "AAAAA", "AAAAA", "AA~AA"},
									{"ABBBA", "A   A", "A   A", "A   A", "ACCCA"},
									{"ABBBA", "A   A", "A   A", "A   A", "ACCCA"},
									{"ABBBA", "A   A", "A   A", "A   A", "ACCCA"},
									{"AAAAA", "AAAAA", "AAAAA", "AAAAA", "AAAAA"},
							})
							.addElement('A', ofBlock(GregTech_API.sBlockCasings2, 0))
							.addElement('B', ofBlockAnyMeta(StackUtil.getBlock(Ic2Items.reinforcedGlass)))
							.addElement('C', ofBlock(Casing_Helper.sCasePage8_3, 3))
							.build();
			private final String[] desc = new String[]{
					EnumChatFormatting.RED + "Impact Details:",
					"- Solid Steel Casing",
					"- Farm Casing",
					"- Reinforced Glass",
					"- Hatches (any Solid Steel Casing)",
			};
			
			//endregion
			@Override
			public void construct(ItemStack stackSize, boolean hintsOnly, GTMTE_Farm tileEntity, ExtendedFacing aSide) {
				IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
				definition.buildOrHints(tileEntity, stackSize, "main", base.getWorld(), aSide,
						base.getXCoord(), base.getYCoord(), base.getZCoord(),
						2, 4, 0, hintsOnly
				);
			}
			
			@Override
			public String[] getDescription(ItemStack stackSize) {
				return desc;
			}
		});
	}
	
	@Override
	public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GTMTE_Farm(mName);
	}
	
	@Override
	public String[] getDescription() {
		final MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder("wood_farm");
		b
				.addInfo("info.0", "Industrial Wood producer")
				.addTypeMachine("name", "Wood Farm")
				.addEffectiveLoss()
				.addScrew()
				.addSeparator()
				.addController()
				.addEnergyHatch()
				.addMaintenanceHatch()
				.addInputBus()
				.addInputHatch()
				.addOutputBus()
				.addOutputHatch()
				.addCasingInfo("case", "Solid Steel Casings", 64)
				.addOtherStructurePart("other.0", "Farm Casings", "other.1", "At the center of the bottom layer")
				.addOtherStructurePart("other.2", "Reinforced Glass", "other.3", "At the center of the Top layer")
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
			return new ITexture[]{Textures.BlockIcons.CASING_BLOCKS[16], TextureFactory.of(
					aActive ? Textures.BlockIcons.OVERLAY_FRONT_DIESEL_ENGINE_ACTIVE
							: Textures.BlockIcons.OVERLAY_FRONT_DIESEL_ENGINE)};
		}
		return new ITexture[]{Textures.BlockIcons.CASING_BLOCKS[16]};
	}
	
	@Override
	public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory,
							   IGregTechTileEntity aBaseMetaTileEntity) {
		return new GT_GUIContainer_MultiMachine(aPlayerInventory, aBaseMetaTileEntity, getLocalName(),
				"PyrolyseOven.png"
		);
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
		return GT_Recipe.GT_Recipe_Map.sFarmRecipes;
	}
	
	@Override
	public boolean checkRecipe(ItemStack aStack) {
		ArrayList<ItemStack> tInputList = getStoredInputs();
		int tInputList_sS = tInputList.size();
		for (int i = 0; i < tInputList_sS - 1; i++) {
			for (int j = i + 1; j < tInputList_sS; j++) {
				if (GT_Utility.areStacksEqual(tInputList.get(i), tInputList.get(j))) {
					if (tInputList.get(i).stackSize >= tInputList.get(j).stackSize) {
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
		ItemStack[] tInputs = tInputList.toArray(new ItemStack[tInputList.size()]);
		
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
		FluidStack[] tFluids = tFluidList.toArray(new FluidStack[tFluidList.size()]);
		if (tInputList.size() > 0) {
			long tVoltage = getMaxInputVoltage();
			byte tTier = (byte) Math.max(1, GT_Utility.getTier(tVoltage));
			GT_Recipe recipe = getRecipeMap()
					.findRecipe(getBaseMetaTileEntity(), cashedRecipe, false,
							false, gregtech.api.enums.GT_Values.V[tTier], tFluids, tInputs
					);
			if ((recipe != null) && (recipe.isRecipeInputEqual(true, tFluids, tInputs))) {
				cashedRecipe = recipe;
				this.mEfficiency         = (10000 - (getIdealStatus() - getRepairStatus()) * 1000);
				this.mEfficiencyIncrease = 10000;
				
				int EUt = recipe.mEUt;
				int maxProgresstime = recipe.mDuration;
				
				while (EUt <= gregtech.api.enums.GT_Values.V[tTier - 1] && maxProgresstime > 2) {
					EUt *= 4;
					maxProgresstime /= 4;
				}
				if (maxProgresstime < 2) {
					maxProgresstime = 2;
					EUt             = recipe.mEUt * recipe.mDuration / 2;
				}
				
				this.mEUt             = -EUt;
				this.mMaxProgresstime = maxProgresstime;
				mOutputItems          = new ItemStack[recipe.mOutputs.length];
				for (int i = 0; i < recipe.mOutputs.length; i++) {
					if (getBaseMetaTileEntity().getRandomNumber(10000) < recipe.getOutputChance(i)) {
						this.mOutputItems[i] = recipe.getOutput(i);
					}
				}
				this.mOutputFluids = recipe.mFluidOutputs;
				this.updateSlots();
				return true;
			}
		}
		return false;
	}
	
	public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
		int xDir = ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()).offsetX * 2;
		int zDir = ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()).offsetZ * 2;
		for (int i = -2; i < 3; i++) {
			for (int j = -2; j < 3; j++) {
				for (int h = 0; h < 5; h++) {
					IGregTechTileEntity tTileEntity = aBaseMetaTileEntity
							.getIGregTechTileEntityOffset(xDir + i, h, zDir + j);
					if ((i != -2 && i != 2) && (j != -2 && j != 2)) {
						if (h == 0) {
							if (aBaseMetaTileEntity.getBlockOffset(xDir + i, h, zDir + j)
									!= Casing_Helper.sCasePage8_3) {
								return false;
							}
							if (aBaseMetaTileEntity.getMetaIDOffset(xDir + i, h, zDir + j) != 3) {
								return false;
							}
						} else if (h == 4) {
							if (!aBaseMetaTileEntity.getBlockOffset(xDir + i, h, zDir + j).getUnlocalizedName()
									.equals("blockAlloyGlass")) {
								return false;
							}
						} else {// innen air
							if (!aBaseMetaTileEntity.getAirOffset(xDir + i, h, zDir + j)) {
								return false;
							}
						}
					} else {
						if (h == 0) {
							if ((!addMaintenanceToMachineList(tTileEntity, 16)) && (!addInputToMachineList(
									tTileEntity, 16)) && (!addOutputToMachineList(tTileEntity, 16))
									&& (!addEnergyInputToMachineList(tTileEntity, 16))) {
								if ((xDir + i != 0) || (zDir + j != 0)) {
									if (aBaseMetaTileEntity.getBlockOffset(xDir + i, h, zDir + j)
											!= GregTech_API.sBlockCasings2) {
										return false;
									}
									if (aBaseMetaTileEntity.getMetaIDOffset(xDir + i, h, zDir + j) != 0) {
										return false;
									}
								}
							}
						} else {
							if (aBaseMetaTileEntity.getBlockOffset(xDir + i, h, zDir + j)
									!= GregTech_API.sBlockCasings2) {
								return false;
							}
							if (aBaseMetaTileEntity.getMetaIDOffset(xDir + i, h, zDir + j) != 0) {
								return false;
							}
						}
					}
				}
			}
		}
		return true;
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
	
}
