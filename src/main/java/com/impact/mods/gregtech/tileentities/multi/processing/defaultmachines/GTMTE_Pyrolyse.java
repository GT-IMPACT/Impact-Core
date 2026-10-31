package com.impact.mods.gregtech.tileentities.multi.processing.defaultmachines;

import com.impact.mods.gregtech.GT_RecipeMaps;
import com.impact.mods.gregtech.blocks.ImpactHatchElement;
import com.impact.mods.gregtech.gui.base.GUI_BASE;
import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_Impact_BlockBase;
import gregtech.api.GregTech_API;
import gregtech.api.enums.Casings;
import gregtech.api.enums.HatchElement;
import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ICasing;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTStructureUtility;
import gregtech.api.util.GT_Recipe;
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

import static gregtech.api.util.GTStructureUtility.ofFrame;
import static space.impact.structures.structure.StructureUtility.ofBlock;
import static space.impact.structures.structure.StructureUtility.ofChain;
import static space.impact.structures.structure.StructureUtility.withHint;

public class GTMTE_Pyrolyse extends GTMTE_Impact_BlockBase<GTMTE_Pyrolyse> {

    private static final ICasing CASING = Casings.SOLID_STEEL;

    private static final StructureRequirements SHELL = StructureRequirements.builder(CASING, 0)
            .atLeast(HatchElement.Maintenance, ImpactHatchElement.Muffler).build();
    private static IStructureDefinition<GTMTE_Pyrolyse> definition;

    public GTMTE_Pyrolyse(int aID, String aNameRegional) {
        super(aID, "impact.multimachine.pyrolyse", aNameRegional);
    }

    public GTMTE_Pyrolyse(String aName) {
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
        return new GTMTE_Pyrolyse(this.mName);
    }

    @Override
    public IStructureDefinition<GTMTE_Pyrolyse> getStructureDefinition() {
        if (definition == null) {
            int frameId = 4096 + Materials.Steel.mMetaItemSubID;
            int frameMeta = GregTech_API.METATILEENTITIES[frameId] == null ? 32767
                    : GregTech_API.METATILEENTITIES[frameId].getTileEntityBaseType();
            definition = StructureDefinition.<GTMTE_Pyrolyse>builder()
                    .addShape("main", new String[][] {
                            { "EEE EEEEE", "~EE EEEEE", "B B B   B" },
                            { "EEE EEEEE", "ECCCC   E", "    DDDDD" },
                            { "EEE EEEEE", "EEE EEEEE", "B B B   B" }
                    })
                    .addChannel(GTStructureUtility.HATCH_CHANNEL, 1, 1)
                    .addElement('C', Casings.STEEL_PIPE.asElement())
                    .addElement('D', CASING.asElement())
                    .addElement('B', ofChain(ofFrame(Materials.Steel), ofBlock(GregTech_API.sBlockMachines, frameMeta)))
                    .addElement('E', withHint(GTStructureUtility.buildHatchAdder(GTMTE_Pyrolyse.class)
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
                .addPollutionAmount(100)
                .addSeparator()
                .addController("See the structure hologram")
                .addOtherStructurePart("Energy Hatch", "Any casing")
                .addOtherStructurePart("Maintenance Hatch", "Any casing; maximum 1")
                .addOtherStructurePart("Muffler Hatch", "Any casing; maximum 1")
                .addOtherStructurePart("Input Bus", "Any casing; maximum 1")
                .addOtherStructurePart("Output Bus", "Any casing; maximum 1")
                .addOtherStructurePart("Output Hatch", "Any casing; maximum 1")
                .addOtherStructurePart("Energy Hatch", "Any casing; maximum 1")
                .addRequirements(SHELL, "Any casing position")
                .addOtherStructurePart("Steel Pipe Casing", "Middle line")
                .addOtherStructurePart("Steel Frame", "Bottom angles")
                .addStructureHint("Casing or compatible hatch", HintColor.GREEN)
                .toolTipFinisher();
    }

    @Override
    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GUI_BASE(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "MultiParallelBlockGUI.png");
    }

    @Override
    public boolean checkRecipe(ItemStack itemStack) {
        ArrayList<ItemStack> stored = getStoredInputs();
        ItemStack[] inputs = stored.toArray(new ItemStack[stored.size()]);
        FluidStack[] fluids = new FluidStack[] {};

        GT_Recipe recipe = getRecipeMap()
                .findRecipe(getBaseMetaTileEntity(), cashedRecipe, false,
                        false, 20L, null, inputs);

        if ((recipe != null) && (recipe.isRecipeInputEqual(true, fluids, inputs))) {

            cashedRecipe = recipe;

            this.mEfficiency = 10000;
            this.mEfficiencyIncrease = 10000;

            int EUt = 20;
            int maxProgresstime = 40 * 20;

            this.mEUt = -EUt;
            this.mMaxProgresstime = maxProgresstime;
            this.mOutputItems = new ItemStack[] { recipe.getOutput(0) };

            FluidStack fluid = cashedRecipe.mFluidOutputs[4];
            if (fluid != null)
                this.mOutputFluids = new FluidStack[] { fluid };

            this.updateSlots();
            return true;

        }
        return false;
    }

    @Override
    public GT_Recipe.GT_Recipe_Map getRecipeMap() {
        return GT_RecipeMaps.sPyrolyseOven;
    }

    @Override
    public boolean onRunningTick(ItemStack aStack) {

        if (cashedRecipe == null)
            return super.onRunningTick(aStack);

        FluidStack out = null;

        switch (this.mProgresstime) {
        case 7 * 20:
            out = cashedRecipe.mFluidOutputs[0];
            break;
        case 14 * 20:
            out = cashedRecipe.mFluidOutputs[1];
            break;
        case 21 * 20:
            out = cashedRecipe.mFluidOutputs[2];
            break;
        case 28 * 20:
            out = cashedRecipe.mFluidOutputs[3];
            break;
        }

        if (out != null)
            addOutput(out);

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
        return 5;
    }

    @Override
    public boolean hasSeparate() {
        return false;
    }
}
