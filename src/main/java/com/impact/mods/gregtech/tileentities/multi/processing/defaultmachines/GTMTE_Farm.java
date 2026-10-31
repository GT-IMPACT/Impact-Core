package com.impact.mods.gregtech.tileentities.multi.processing.defaultmachines;

import com.impact.mods.gregtech.blocks.ImpactCasings;
import gregtech.api.enums.Casings;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.HatchElement;
import gregtech.api.enums.Textures;
import gregtech.api.gui.GT_GUIContainer_MultiMachine;
import gregtech.api.interfaces.ICasing;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_EnhancedMultiBlockBase;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTStructureUtility;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.StructureRequirements;
import ic2.core.Ic2Items;
import ic2.core.util.StackUtil;
import java.util.ArrayList;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import space.impact.structures.alignment.IAlignmentLimits;
import space.impact.structures.alignment.constructable.ISurvivalConstructable;
import space.impact.structures.hint.HintColor;
import space.impact.structures.structure.IStructureDefinition;
import space.impact.structures.structure.ISurvivalBuildEnvironment;
import space.impact.structures.structure.StructureDefinition;

import static space.impact.structures.structure.StructureUtility.ofBlockAnyMeta;
import static space.impact.structures.structure.StructureUtility.withHint;

public class GTMTE_Farm
        extends GT_MetaTileEntity_EnhancedMultiBlockBase<GTMTE_Farm>
        implements ISurvivalConstructable {
    private static final ICasing CASING = Casings.SOLID_STEEL;
    private static final StructureRequirements SHELL = StructureRequirements.builder(CASING, 0).build();
    private static IStructureDefinition<GTMTE_Farm> STRUCTURE;

    @Override
    public IStructureDefinition<GTMTE_Farm> getStructureDefinition() {
        if (STRUCTURE == null) {
            STRUCTURE = StructureDefinition.<GTMTE_Farm>builder()
                    .addChannel(GTStructureUtility.HATCH_CHANNEL, 1, 1)
                    .addShape("main", new String[][] {
                            { "CCCCC", "CCCCC", "CCCCC", "CCCCC", "AA~AA" },
                            { "CGGGC", "C---C", "C---C", "C---C", "AFFFA" },
                            { "CGGGC", "C---C", "C---C", "C---C", "AFFFA" },
                            { "CGGGC", "C---C", "C---C", "C---C", "AFFFA" },
                            { "CCCCC", "CCCCC", "CCCCC", "CCCCC", "AAAAA" }
                    })
                    .addElement('A', withHint(GTStructureUtility.buildHatchAdder(GTMTE_Farm.class)
                            .allow(HatchElement.Maintenance, HatchElement.Input, HatchElement.Output,
                                    HatchElement.Energy)
                            .buildAndChain(SHELL), HintColor.GREEN))
                    .addElement('C', CASING.asElement())
                    .addElement('G', ofBlockAnyMeta(StackUtil.getBlock(Ic2Items.reinforcedGlass)))
                    .addElement('F', ImpactCasings.CASING_FARM.asElement())
                    .build();
        }
        return STRUCTURE;
    }

    @Override
    public void construct(ItemStack trigger, boolean hintsOnly) {
        buildPiece("main", trigger, hintsOnly, 2, 4, 0);
    }

    @Override
    public int survivalConstruct(ItemStack trigger, int budget,
            ISurvivalBuildEnvironment environment) {
        return survivalBuildPiece("main", trigger, 2, 4, 0, budget, environment);
    }

    private GT_Recipe cashedRecipe = null;

    public GTMTE_Farm(int aID, String aNameRegional) {
        super(aID, "impact.multimachine.farm", aNameRegional);

    }

    public GTMTE_Farm(String aName) {
        super(aName);

    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GTMTE_Farm(mName);
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        return new MultiblockTooltipBuilder(mName)
                .addInfo("Industrial Wood producer")
                .addMachineType("Wood Farm")
                .addInfo("Overclocking without loss of efficiency")
                .addInfo("Select machine type by clicking the controller with a screwdriver")
                .addSeparator()
                .addController("See the structure hologram")
                .addOtherStructurePart("Energy Hatch", "Any casing")
                .addOtherStructurePart("Maintenance Hatch", "Any casing; maximum 1")
                .addOtherStructurePart("Input Bus", "Any casing")
                .addOtherStructurePart("Input Hatch", "Any casing")
                .addOtherStructurePart("Output Bus", "Any casing")
                .addOtherStructurePart("Output Hatch", "Any casing")
                .addRequirements(SHELL, "Bottom edge")
                .addOtherStructurePart("Farm Casings", "At the center of the bottom layer")
                .addOtherStructurePart("Reinforced Glass", "At the center of the Top layer")
                .addStructureHint("Casing or compatible hatch", HintColor.GREEN)
                .toolTipFinisher();
    }

    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing,
            byte aColorIndex, boolean aActive, boolean aRedstone) {
        if (aSide == aFacing) {
            return new ITexture[] { CASING.getTexture(), TextureFactory.of(
                    aActive ? Textures.BlockIcons.OVERLAY_FRONT_DIESEL_ENGINE_ACTIVE
                            : Textures.BlockIcons.OVERLAY_FRONT_DIESEL_ENGINE) };
        }
        return new ITexture[] { CASING.getTexture() };
    }

    @Override
    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory,
            IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_GUIContainer_MultiMachine(aPlayerInventory, aBaseMetaTileEntity, getLocalName(),
                "PyrolyseOven.png");
    }

    @Override
    public boolean isCorrectMachinePart(ItemStack aStack) {
        return true;
    }

    @Override
    protected IAlignmentLimits getInitialAlignmentLimits() {
        return (direction, rotation, flip) -> (direction.ordinal() > 1) && rotation.isNotRotated()
                && flip.isNotFlipped();
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
                            false, GT_Values.V[tTier], tFluids, tInputs);
            if ((recipe != null) && (recipe.isRecipeInputEqual(true, tFluids, tInputs))) {
                cashedRecipe = recipe;
                this.mEfficiency = (10000 - (getIdealStatus() - getRepairStatus()) * 1000);
                this.mEfficiencyIncrease = 10000;

                int EUt = recipe.mEUt;
                int maxProgresstime = recipe.mDuration;

                while (EUt <= GT_Values.V[tTier - 1] && maxProgresstime > 2) {
                    EUt *= 4;
                    maxProgresstime /= 4;
                }
                if (maxProgresstime < 2) {
                    maxProgresstime = 2;
                    EUt = recipe.mEUt * recipe.mDuration / 2;
                }

                this.mEUt = -EUt;
                this.mMaxProgresstime = maxProgresstime;
                mOutputItems = new ItemStack[recipe.mOutputs.length];
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

    public boolean checkMachine(IGregTechTileEntity controller, ItemStack stack) {
        return checkPiece("main", 2, 4, 0);
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
