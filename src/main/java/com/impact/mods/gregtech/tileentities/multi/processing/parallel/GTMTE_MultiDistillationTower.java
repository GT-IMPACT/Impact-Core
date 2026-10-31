package com.impact.mods.gregtech.tileentities.multi.processing.parallel;

import com.impact.mods.gregtech.blocks.ImpactHatchElement;
import com.impact.mods.gregtech.gui.base.GUI_BASE;
import com.impact.mods.gregtech.tileentities.multi.implement.GT_MetaTileEntity_MultiParallelBlockBase;
import gregtech.api.enums.Casings;
import gregtech.api.enums.HatchElement;
import gregtech.api.enums.HeatingCoil;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ICasing;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Output;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTStructureUtility;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Recipe.GT_Recipe_Map;
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

import static space.impact.structures.structure.StructureUtility.withHint;

public class GTMTE_MultiDistillationTower
        extends GT_MetaTileEntity_MultiParallelBlockBase<GTMTE_MultiDistillationTower> {

    private static final ICasing CASING = Casings.CLEAN_STAINLESS_STEEL;

    private static final StructureRequirements SHELL = StructureRequirements.builder(CASING, 0)
            .atLeast(HatchElement.Maintenance).build();
    private static IStructureDefinition<GTMTE_MultiDistillationTower> definition;

    public GTMTE_MultiDistillationTower(int aID, String aNameRegional) {
        super(aID, "impact.multis.distilltower", aNameRegional);
    }

    public GTMTE_MultiDistillationTower(String aName) {
        super(aName);
    }

    @Override
    public IStructureDefinition<GTMTE_MultiDistillationTower> getStructureDefinition() {
        if (definition == null) {
            definition = StructureDefinition.<GTMTE_MultiDistillationTower>builder()
                    .addShape("main", new String[][] {
                            { " DDD ", " DDD ", " DDD ", " DDD ", " DDD ", " DDD ", " DDD ", " DDD ", " DDD ", " DDD ", " DDD ", " D~D " },
                            { "DDDDD", "DCCCD", "DCCCD", "D   D", "D   D", "DCCCD", "DCCCD", "D   D", "D   D", "DCCCD", "DCCCD", "DDDDD" },
                            { "DDDDD", "DC CD", "DC CD", "D   D", "D   D", "DC CD", "DC CD", "D   D", "D   D", "DC CD", "DC CD", "DDDDD" },
                            { "DDDDD", "DCCCD", "DCCCD", "D   D", "D   D", "DCCCD", "DCCCD", "D   D", "D   D", "DCCCD", "DCCCD", "DDDDD" },
                            { " DDD ", " DDD ", " DDD ", " DDD ", " DDD ", " DDD ", " DDD ", " DDD ", " DDD ", " DDD ", " DDD ", " DDD " }
                    })
                    .addChannel(GTStructureUtility.HATCH_CHANNEL, 1, 1)
                    .addElement('C', HeatingCoil.KANTHAL.asElement())
                    .addElement('D', withHint(GTStructureUtility.buildHatchAdder(GTMTE_MultiDistillationTower.class)
                            .allow(HatchElement.Input, HatchElement.Energy, ImpactHatchElement.LaserInput,
                                    ImpactHatchElement.Parallel, HatchElement.Output)
                            .buildAndChain(SHELL), HintColor.GREEN))
                    .build();
        }
        return definition;
    }

    @Override
    public void construct(ItemStack itemStack, boolean b) {
        buildPiece(itemStack, b, 2, 11, 0);
    }

    @Override
    public int survivalConstruct(ItemStack trigger, int budget,
            ISurvivalBuildEnvironment environment) {
        return survivalBuildPiece("main", trigger, 2, 11, 0, budget, environment);
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex,
            boolean aActive, boolean aRedstone) {
        return aSide == aFacing
                ? new ITexture[] { CASING.getTexture(),
                        TextureFactory.of(aActive ? Textures.BlockIcons.OVERLAY_FRONT_VACUUM_FREEZER_ACTIVE
                                : Textures.BlockIcons.OVERLAY_FRONT_VACUUM_FREEZER) }
                : new ITexture[] { CASING.getTexture() };
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GTMTE_MultiDistillationTower(this.mName);
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        return new MultiblockTooltipBuilder(mName)
                .addInfo("OMG!? Where Bart`s Big Tower?")
                .addInfo("Single-block machine equivalent")
                .addInfo("Parallel points: base 1, maximum 256")
                .addMachineType("Multi Distillation Tower")
                .addSeparator()
                .addController("See the structure hologram")
                .addOtherStructurePart("Energy Hatch", "Any casing; maximum 4")
                .addOtherStructurePart("Maintenance Hatch", "Any casing; maximum 1")
                .addOtherStructurePart("Input Hatch", "Any casing; maximum 9")
                .addOtherStructurePart("Output Hatch", "Any casing; maximum 66")
                .addOtherStructurePart("Input Bus", "Any casing; maximum 4")
                .addOtherStructurePart("Output Bus", "Any casing; maximum 1")
                .addOtherStructurePart("Parallel Hatch", "Any casing; maximum 1")
                .addRequirements(SHELL, "Any casing position")
                .addOtherStructurePart("Kanthal Coil", "inside the hollow")
                .addStructureHint("Casing or compatible hatch", HintColor.GREEN)
                .toolTipFinisher();
    }

    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GUI_BASE(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "MultiParallelBlockGUI.png");
    }

    @Override
    public GT_Recipe.GT_Recipe_Map getRecipeMap() {
        return GT_Recipe_Map.sDistillationRecipes;
    }

    @Override
    public boolean machineStructure(IGregTechTileEntity thisController) {
        boolean formationChecklist = checkPiece(2, 11, 0) && SHELL.isSatisfied(this, 0);

        if (this.mInputBusses.size() > 6) {
            formationChecklist = false;
        }
        if (this.mInputHatches.size() > 9) {
            formationChecklist = false;
        }
        if (this.mOutputBusses.size() > 3) {
            formationChecklist = false;
        }
        if (this.mOutputHatches.size() > 66) {
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

    private boolean dumpFluid(ArrayList<GT_MetaTileEntity_Hatch_Output> outputHatches, FluidStack copiedFluidStack,
            boolean restrictiveHatchesOnly) {
        for (GT_MetaTileEntity_Hatch_Output tHatch : outputHatches) {
            if (!isValidMetaTileEntity(tHatch) || (restrictiveHatchesOnly && tHatch.mMode == 0)) {
                continue;
            }
            if (GT_ModHandler.isSteam(copiedFluidStack)) {
                if (!tHatch.outputsSteam()) {
                    continue;
                }
            } else {
                if (!tHatch.outputsLiquids()) {
                    continue;
                }
                if (tHatch.isFluidLocked() && tHatch.getLockedFluidName() != null
                        && !tHatch.getLockedFluidName().equals(copiedFluidStack.getUnlocalizedName())) {
                    continue;
                }
            }
            int tAmount = tHatch.fill(copiedFluidStack, false);
            if (tAmount >= copiedFluidStack.amount) {
                boolean filled = tHatch.fill(copiedFluidStack, true) >= copiedFluidStack.amount;
                tHatch.onEmptyingContainerWhenEmpty();
                return filled;
            } else if (tAmount > 0) {
                copiedFluidStack.amount = copiedFluidStack.amount - tHatch.fill(copiedFluidStack, true);
                tHatch.onEmptyingContainerWhenEmpty();
            }
        }
        return false;
    }

    public boolean addOutput(FluidStack aLiquid, int i) {
        if (aLiquid == null)
            return false;
        FluidStack copiedFluidStack = aLiquid.copy();

        ArrayList<GT_MetaTileEntity_Hatch_Output> tOutputHatches = new ArrayList<>();
        for (GT_MetaTileEntity_Hatch_Output tHatch : mOutputHatches) {
            if (tHatch.getBaseMetaTileEntity().getYCoord() == getBaseMetaTileEntity().getYCoord() + 1 + i) {
                tOutputHatches.add(tHatch);
            }
        }
        if (!dumpFluid(tOutputHatches, copiedFluidStack, true)) {
            dumpFluid(tOutputHatches, copiedFluidStack, false);
        }
        return false;
    }

    @Override
    protected void addFluidOutputs(FluidStack[] mOutputFluids2) {
        for (int i = 0; i < mOutputFluids2.length; i++) {
            addOutput(mOutputFluids2[i], i);
        }
    }
}
