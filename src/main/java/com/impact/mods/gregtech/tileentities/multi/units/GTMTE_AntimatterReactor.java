package com.impact.mods.gregtech.tileentities.multi.units;

import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_Impact_BlockBase;
import com.impact.util.string.MultiBlockTooltipBuilder;
import gregtech.api.enums.Dyes;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.*;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import gregtech.common.gui.GT_GUIContainer_AntimatterReactor;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import space.impact.api.ImpactAPI;
import space.impact.api.multiblocks.structure.IStructureDefinition;
import space.impact.api.multiblocks.structure.StructureDefinition;

import java.util.ArrayList;
import java.util.Arrays;

import static com.impact.util.multis.GT_StructureUtility.ofHatchAdder;
import static gregtech.api.GregTech_API.*;
import static gregtech.api.util.GT_Recipe.GT_Recipe_Map.sAntimatterReactorFuels;
import static space.impact.api.multiblocks.structure.StructureUtility.ofBlock;
import static space.impact.api.multiblocks.structure.StructureUtility.ofChain;

public class GTMTE_AntimatterReactor extends GTMTE_Impact_BlockBase<GTMTE_AntimatterReactor> {
	
	protected int mColantConsumption = 0, mPerFluidAmount;
	private FluidStack[] mInputFluids;
	
	public GTMTE_AntimatterReactor(int aID, String aNameRegional) {
		super(aID, "impact.multis.antimatterreactor", aNameRegional);
	}
	
	public GTMTE_AntimatterReactor(String aName) {
		super(aName);
	}
	
	@Override
	public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GTMTE_AntimatterReactor(mName);
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
		return new GT_GUIContainer_AntimatterReactor(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "AntimatterReactor.png", sAntimatterReactorFuels.mNEIName);
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
	public boolean checkRecipe(ItemStack aStack) {
		ArrayList<ItemStack> tInputList = getStoredInputs();
		for (int i = 0; i < tInputList.size() - 1; i++) {
			for (int j = i + 1; j < tInputList.size(); j++) {
				if (GT_Utility.areStacksEqual(tInputList.get(i), tInputList.get(j))) {
					if (tInputList.get(i).stackSize >= tInputList.get(j).stackSize) {
						tInputList.remove(j--);
					} else {
						tInputList.remove(i--);
						break;
					}
				}
			}
		}
		ItemStack[] tInputs = Arrays.copyOfRange(tInputList.toArray(new ItemStack[0]), 0, 2);
		
		ArrayList<FluidStack> tFluidList = getStoredFluids();
		for (int i = 0; i < tFluidList.size() - 1; i++) {
			for (int j = i + 1; j < tFluidList.size(); j++) {
				if (GT_Utility.areFluidsEqual(tFluidList.get(i), tFluidList.get(j))) {
					if (tFluidList.get(i).amount >= tFluidList.get(j).amount) {
						tFluidList.remove(j--);
					} else {
						tFluidList.remove(i--);
						break;
					}
				}
			}
		}
		FluidStack[] tFluids = Arrays.copyOfRange(tFluidList.toArray(new FluidStack[tInputList.size()]), 0, 1);
		
		if (tInputList.size() > 1) {
			GT_Recipe tRecipe = getRecipeMap().findRecipe(getBaseMetaTileEntity(), cashedRecipe, false, Long.MAX_VALUE, tFluids, tInputs);
			if (tRecipe == null) {
				return false;
			}
			if (tRecipe.isRecipeInputEqual(true, tFluids, tInputs)) {
				cashedRecipe = tRecipe;
				this.mEUt                = 2097152;
				this.mMaxProgresstime    = tRecipe.mSpecialValue * 20;
				this.mEfficiency         = 10000;
				this.mEfficiencyIncrease = 10000;
				this.mInputFluids        = tRecipe.mFluidInputs;
				this.mPerFluidAmount     = tRecipe.mFluidInputs.length;
				this.mOutputFluids       = tRecipe.mFluidOutputs;
				this.mOutputItems        = tRecipe.mOutputs;
				this.mColantConsumption  = tRecipe.mFluidInputs[0].amount;
				updateSlots();
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void saveNBTData(NBTTagCompound aNBT) {
		super.saveNBTData(aNBT);
		aNBT.setInteger("mPerFluidAmount", mPerFluidAmount);
		for (int i = 0; i < mPerFluidAmount; i++) {
			if (mInputFluids[i] != null && mInputFluids[i].amount > 0) {
				aNBT.setTag("mInputFluids" + (i == 0 ? "" : i), mInputFluids[i].writeToNBT(new NBTTagCompound()));
			}
		}
	}
	
	@Override
	public void loadNBTData(NBTTagCompound aNBT) {
		super.loadNBTData(aNBT);
		mPerFluidAmount = aNBT.getInteger("mPerFluidAmount");
		mInputFluids = new FluidStack[mPerFluidAmount];
		for (int i = 0; i < mPerFluidAmount; i++) {
			mInputFluids[i] = FluidStack.loadFluidStackFromNBT(aNBT.getCompoundTag("mInputFluids" + (i == 0 ? "" : i)));
		}
	}
	
	@Override
	public GT_Recipe.GT_Recipe_Map getRecipeMap() {
		return sAntimatterReactorFuels;
	}
	
	@Override
	public boolean onRunningTick(ItemStack aStack) {
		if (mEUt > 0 && mInputFluids != null) {
			if (depleteInput(mInputFluids[0])) {
				addEnergyOutput(((long) mEUt * mEfficiency) / 10000);
				addOutput(mOutputFluids[0]);
				return true;
			}
		}
		stopMachine();
		return false;
	}
	
	@Override
	public int getMaxEfficiency(ItemStack aStack) {
		return 10000;
	}
	
	@Override
	public IStructureDefinition<GTMTE_AntimatterReactor> getStructureDefinition() {
		return StructureDefinition.<GTMTE_AntimatterReactor>builder()
				.addShape("main", new String[][]{
						{"               ","               ","               ","               ","               ","               ","     FAAAF     ","     AJ~LA     ","     FAAAF     ","               ","               ","               ","               ","               ","               "},
						{"               ","               ","               ","               ","               ","               ","    F     F    ","    ADDDDDA    ","    F     F    ","               ","               ","               ","               ","               ","               "},
						{"               ","               ","               ","               ","               ","               ","   F       F   ","   AD     DA   ","   F       F   ","               ","               ","               ","               ","               ","               "},
						{"               ","               ","               ","               ","               ","               ","  F         F  ","  AD       DA  ","  F         F  ","               ","               ","               ","               ","               ","               "},
						{"               ","               ","               ","               ","               ","               "," F           F "," AD         DA "," F           F ","               ","               ","               ","               ","               ","               "},
						{"               ","               ","               ","               ","               ","               ","F      B      F","AD    BBB    DA","F      B      F","               ","               ","               ","               ","               ","               "},
						{"    FFFFFFF    ","   F       F   ","  F         F  "," F           F ","F             F","F      C      F","A     BBB     A","AD   BBEBB   DA","A     BBB     A","F      C      F","F             F"," F           F ","  F         F  ","   F       F   ","    FFFFFFF    "},
						{"    AAAAAAA    ","   A       A   ","  A  DDDDD  A  "," A  D     D  A ","A  D       D  A","A D   ICK   D A","A D  BBEBB  D A","ADD  BE EB  DDA","A D  BBEBB  D A","A D   ICK   D A","A  D       D  A"," A  D     D  A ","  A  DDDDD  A  ","   A       A   ","    AAAAAAA    "},
						{"    FFFFFFF    ","   F       F   ","  F         F  "," F           F ","F             F","F      C      F","A     BBB     A","AD   BBEBB   DA","A     BBB     A","F      C      F","F             F"," F           F ","  F         F  ","   F       F   ","    FFFFFFF    "},
						{"               ","               ","               ","               ","               ","               ","F      B      F","AD    BBB    DA","F      B      F","               ","               ","               ","               ","               ","               "},
						{"               ","               ","               ","               ","               ","               "," F           F "," AD         DA "," F           F ","               ","               ","               ","               ","               ","               "},
						{"               ","               ","               ","               ","               ","               ","  F         F  ","  AD       DA  ","  F         F  ","               ","               ","               ","               ","               ","               "},
						{"               ","               ","               ","               ","               ","               ","   F       F   ","   AD     DA   ","   F       F   ","               ","               ","               ","               ","               ","               "},
						{"               ","               ","               ","               ","               ","               ","    F     F    ","    ADDDDDA    ","    F     F    ","               ","               ","               ","               ","               ","               "},
						{"               ","               ","               ","               ","               ","               ","     FAAAF     ","     AAHAA     ","     FAAAF     ","               ","               ","               ","               ","               ","               "}
				})
				.addElement('A', ofBlock(sBlockCasings3, 12))
				.addElement('B', ofBlock(sBlockCasings4, 8))
				.addElement('C', ofBlock(sBlockCasings5, 13))
				.addElement('D', ofBlock(sBlockCasings5, 14))
				.addElement('E', ofBlock(sBlockCasings5, 15))
				.addElement('F', ofBlock(sBlockCasings7, 6))
				.addElement('H', ofChain(ofHatchAdder(GTMTE_AntimatterReactor::addDynamoToMachineList, 44, ImpactAPI.YELLOW), ofBlock(sBlockCasings3, 12)))
				.addElement('I', ofChain(ofHatchAdder(GTMTE_AntimatterReactor::addInputToMachineList, 141, ImpactAPI.RED), ofBlock(sBlockCasings5, 13)))
				.addElement('J', ofChain(ofHatchAdder(GTMTE_AntimatterReactor::addInputToMachineList, 44, ImpactAPI.BLUE), ofBlock(sBlockCasings3, 12)))
				.addElement('K', ofChain(ofHatchAdder(GTMTE_AntimatterReactor::addOutputToMachineList, 141, ImpactAPI.ORANGE), ofBlock(sBlockCasings5, 13)))
				.addElement('L', ofChain(ofHatchAdder(GTMTE_AntimatterReactor::addOutputToMachineList, 44, ImpactAPI.GREEN), ofBlock(sBlockCasings3, 12)))
				.build();
	}
	
	@Override
	protected MultiBlockTooltipBuilder createTooltip() {
		MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder("antimatter_reactor");
		b
				.addInfo("info.0", "REBIRTH IN A NEW ERA!!!")
				.addTypeMachine("type", "Antimatter Reactor")
				.addInfo("info.1", "2,097,152 EU/t and 4 L/t plasma (in cooling)")
				.addSeparator()
				.addController()
				.sizeStructure(15,15,15)
				.addInputHatch("hatches.0", "(UV or better)", 2)
				.addOutputHatch("hatches.1", "(UV or better)", 2)
				.addInputBus("hatches.2", "(UV or better)", 1)
				.addOutputBus("hatches.3", "(UV or better)", 1)
				.addDynamoHatch("hatches.4", "(UHV or better)", 1)
				.addMaintenanceHatch()
				.addCasingInfo("case.1", "Radiation Proof Casing", 89)
				.addCasingInfo("case.5", "Intermix Chamber Casing", 6)
				.addCasingInfo("case.0", "Magnetic Coil Block", 116)
				.addCasingInfo("case.2", "Dyson Ring Casing", 60)
				.addCasingInfo("case.3", "Fusion Casing MKII", 28)
				.addCasingInfo("case.4", "Core Chamber Casing", 7)
				.addHints()
				.addRedHint("Input Hatch or Intermix Chamber Casing")
				.addOrangeHint("Output Hatch or Intermix Chamber Casing")
				.addBlueHint("Input Bus or Radiation Proof Casing")
				.addYellowHint("Dynamo Hatch or Radiation Proof Casing")
				.addGreenHint("Output Bus or Radiation Proof Casing")
				.signAndFinalize();
		return b;
	}

	@Override
	public boolean machineStructure(IGregTechTileEntity thisController) {
		mWrench = mScrewdriver = mSoftHammer = mHardHammer = mSolderingTool = mCrowbar = true;
		
		boolean check = checkPiece(7, 7, 0);
		
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
		
		return check;
	}
	
	@Override
	public void construct(ItemStack itemStack, boolean b) {
		buildPiece(itemStack, b, 7, 7, 0);
	}
	
	@Override
	public boolean hasSeparate() {
		return false;
	}
}
