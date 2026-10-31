package com.impact.mods.gregtech.tileentities.multi.processing.parallel;

import com.impact.mods.gregtech.GT_RecipeMaps;
import com.impact.mods.gregtech.blocks.ImpactHatchElement;
import com.impact.mods.gregtech.gui.base.GUI_BASE;
import com.impact.mods.gregtech.tileentities.multi.implement.GT_MetaTileEntity_MultiParallelBlockBase;
import gregtech.api.GregTech_API;
import gregtech.api.enums.Casings;
import gregtech.api.enums.HatchElement;
import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ICasing;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Energy;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTStructureUtility;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.StructureRequirements;
import java.util.ArrayList;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import space.impact.structures.hint.HintColor;
import space.impact.structures.structure.IStructureDefinition;
import space.impact.structures.structure.ISurvivalBuildEnvironment;
import space.impact.structures.structure.StructureDefinition;

import static com.impact.util.recipe.RecipeHelper.resizeItemStackSize;
import static gregtech.api.enums.GT_Values.V;
import static gregtech.api.util.GTStructureUtility.ofFrame;
import static space.impact.structures.structure.StructureUtility.ofBlock;
import static space.impact.structures.structure.StructureUtility.ofChain;
import static space.impact.structures.structure.StructureUtility.withHint;

public class GTMTE_AdvancedPyrolyse extends GT_MetaTileEntity_MultiParallelBlockBase<GTMTE_AdvancedPyrolyse> {

    public int mParallelPoint = 1;

    private static final ICasing CASING = Casings.HSLA;

    private static final StructureRequirements SHELL = StructureRequirements.builder(CASING, 0)
            .atLeast(HatchElement.Maintenance, ImpactHatchElement.Muffler).build();
    private static IStructureDefinition<GTMTE_AdvancedPyrolyse> definition;

    public GTMTE_AdvancedPyrolyse(int aID, String aNameRegional) {
        super(aID, "impact.multimachine.advpyrolyse", aNameRegional);
    }

    public GTMTE_AdvancedPyrolyse(String aName) {
        super(aName);
    }

    @Override
    public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide, final byte aFacing,
            final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
        return aSide == aFacing
                ? new ITexture[] { CASING.getTexture(),
                        TextureFactory.of(aActive ? Textures.BlockIcons.OVERLAY_FRONT_DISTILLATION_TOWER_ACTIVE
                                : Textures.BlockIcons.OVERLAY_FRONT_DISTILLATION_TOWER) }
                : new ITexture[] { CASING.getTexture() };
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GTMTE_AdvancedPyrolyse(this.mName);
    }

    @Override
    public IStructureDefinition<GTMTE_AdvancedPyrolyse> getStructureDefinition() {
        if (definition == null) {
            int frameId = 4096 + Materials.HSLA.mMetaItemSubID;
            int frameMeta = GregTech_API.METATILEENTITIES[frameId] == null ? 32767
                    : GregTech_API.METATILEENTITIES[frameId].getTileEntityBaseType();
            definition = StructureDefinition.<GTMTE_AdvancedPyrolyse>builder()
                    .addShape("main", new String[][] {
                            { "EEE EEEEE", "~EE EEEEE", "B B B   B" },
                            { "EEE EEEEE", "EDDDD   E", "    CCCCC" },
                            { "EEE EEEEE", "EEE EEEEE", "B B B   B" }
                    })
                    .addChannel(GTStructureUtility.HATCH_CHANNEL, 1, 1)
                    .addElement('C', CASING.asElement())
                    .addElement('D', Casings.STEEL_PIPE.asElement())
                    .addElement('B', ofChain(ofFrame(Materials.HSLA), ofBlock(GregTech_API.sBlockMachines, frameMeta)))
                    .addElement('E', withHint(GTStructureUtility.buildHatchAdder(GTMTE_AdvancedPyrolyse.class)
                            .allow(HatchElement.Input, HatchElement.Energy, ImpactHatchElement.LaserInput,
                                    HatchElement.Output)
                            .buildAndChain(SHELL), HintColor.GREEN))
                    .build();
        }
        return definition;
    }

    @Override
    public void construct(ItemStack itemStack, boolean b) {
        buildPiece(itemStack, b, 0, 1, 0);
    }

    @Override
    public int survivalConstruct(ItemStack trigger, int budget,
            ISurvivalBuildEnvironment environment) {
        return survivalBuildPiece("main", trigger, 0, 1, 0, budget, environment);
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        return new MultiblockTooltipBuilder(mName)
                .addMachineType("Pyrolyse oven")
                .addInfo("Converts hydrocarbons into gases, wood tar and solid fuels")
                .addInfo("The process emits gases throughout the entire time:")
                .addInfo("Input/output of products depends of energy hatch, time does not change")
                .addInfo("Parallel recipes: 2x per energy hatch tier (LV - 2x, MV - 4x, HV - 6x and etc)")
                .addInfo("Pollution per second: 100 x tier energy hatch")
                .addSeparator()
                .addController("See the structure hologram")
                .addOtherStructurePart("Energy Hatch", "Any casing; maximum 1")
                .addOtherStructurePart("Maintenance Hatch", "Any casing; maximum 1")
                .addOtherStructurePart("Muffler Hatch", "Any casing; maximum 1")
                .addOtherStructurePart("Input Bus", "Any casing; maximum 1")
                .addOtherStructurePart("Output Bus", "Any casing; maximum 1")
                .addOtherStructurePart("Output Hatch", "Any casing; maximum 1")
                .addRequirements(SHELL, "Any casing position")
                .addOtherStructurePart("Steel Pipe Casing", "Middle line")
                .addOtherStructurePart("HSLA Frame", "Bottom angles")
                .addStructureHint("Casing or compatible hatch", HintColor.GREEN)
                .toolTipFinisher();
    }

    @Override
    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GUI_BASE(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "MultiParallelBlockGUI.png");
    }

    @Override
    public boolean checkRecipe(ItemStack itemStack) {
        int xPar = tierHatch() * 2;

        ArrayList<ItemStack> tInputList = getStoredInputs();
        ArrayList<FluidStack> tFluidList = this.getStoredFluids();
        ItemStack[] tInputs = tInputList.toArray(new ItemStack[0]);
        FluidStack[] tFluids = tFluidList.toArray(new FluidStack[0]);

        if (!tInputList.isEmpty() || !tFluidList.isEmpty()) {

            GT_Recipe tRecipe;

            long nominalV = getMaxInputVoltage();
            byte tTier = (byte) Math.max(1, GT_Utility.getTier(nominalV));

            tRecipe = getRecipeMap().findRecipe(this.getBaseMetaTileEntity(), cashedRecipe, false, V[tTier], tFluids,
                    tInputs);

            if (tRecipe != null) {
                cashedRecipe = tRecipe;
                ArrayList<FluidStack> outputFluids = new ArrayList<>();

                boolean found_Recipe = false;

                ItemStack[] tOut = new ItemStack[tRecipe.mOutputs.length];
                int processed = 0;
                while ((!tFluidList.isEmpty() || !tInputList.isEmpty()) && processed < xPar) {
                    if ((tRecipe.mEUt * (processed + 1L)) < nominalV && tRecipe
                            .isRecipeInputEqual(true, tFluids, tInputs)) {
                        found_Recipe = true;
                        for (int h = 0; h < tRecipe.mOutputs.length; h++) {
                            if (tRecipe.getOutput(h) != null) {
                                ItemStack old = tRecipe.getOutput(h).copy();
                                tOut[h] = old;
                                tOut[h].stackSize = old.stackSize * (processed + 1);
                            }
                        }

                        ++processed;

                    } else {
                        break;
                    }
                }
                mParallelPoint = processed;
                if (tRecipe.mFluidOutputs.length > 4) {
                    FluidStack fluid = tRecipe.getFluidOutput(4);
                    if (fluid != null) {
                        FluidStack fluidOut = fluid.copy();
                        fluidOut.amount *= processed;
                        outputFluids.add(fluidOut);
                    }
                }

                tOut = resizeItemStackSize(tOut);

                if (found_Recipe) {

                    this.mEfficiency = (10000 - (this.getIdealStatus() - this.getRepairStatus()) * 1000);
                    this.mEfficiencyIncrease = 10000;

                    this.mEUt = (tRecipe.mEUt) * processed * tierHatch() / 2;

                    this.mEUt = this.mEUt > 0 ? (-this.mEUt) : this.mEUt;

                    this.mMaxProgresstime = tRecipe.mDuration;

                    this.mOutputItems = tOut;
                    this.mOutputFluids = outputFluids.toArray(new FluidStack[0]);

                    this.updateSlots();
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public GT_Recipe.GT_Recipe_Map getRecipeMap() {
        return GT_RecipeMaps.sPyrolyseOven;
    }

    public int tierHatch() {
        int Tier = 0;
        for (GT_MetaTileEntity_Hatch_Energy tHatch : mEnergyHatches) {
            if (isValidMetaTileEntity(tHatch)) {
                Tier = tHatch.mTier;
            }
        }
        setParallel(Math.max(Tier, 1));
        return Math.max(Tier, 1);
    }

    @Override
    public boolean onRunningTick(ItemStack aStack) {

        if (cashedRecipe == null)
            return super.onRunningTick(aStack);

        int outIndex = -1;

        switch (this.mProgresstime) {
        case 7 * 20:
            outIndex = 0;
            break;
        case 14 * 20:
            outIndex = 1;
            break;
        case 21 * 20:
            outIndex = 2;
            break;
        case 28 * 20:
            outIndex = 3;
            break;
        }

        if (outIndex >= 0 && cashedRecipe.mFluidOutputs.length > outIndex) {
            FluidStack out = cashedRecipe.mFluidOutputs[outIndex];
            if (out != null) {
                FluidStack outCopy = out.copy();
                outCopy.amount *= mParallelPoint;
                addOutput(outCopy);
            }
        }

        return super.onRunningTick(aStack);
    }

    @Override
    public boolean machineStructure(IGregTechTileEntity thisController) {

        boolean formationChecklist = checkPiece(0, 1, 0) && SHELL.isSatisfied(this, 0);

        if (this.mInputBusses.size() > 1) {
            formationChecklist = false;
        }
        if (this.mInputHatches.size() > 1) {
            formationChecklist = false;
        }
        if (this.mOutputHatches.size() > 1) {
            formationChecklist = false;
        }
        if (this.mOutputBusses.size() > 1) {
            formationChecklist = false;
        }
        if (this.mEnergyHatches.size() > 1) {
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
        return 200;
    }

    @Override
    public boolean hasSeparate() {
        return false;
    }

    @Override
    public boolean hasShowConnect() {
        return false;
    }
}
