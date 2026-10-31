package com.impact.mods.gregtech.tileentities.multi.processing.parallel;

import com.impact.mods.gregtech.blocks.ImpactHatchElement;
import com.impact.mods.gregtech.gui.base.GUI_BASE;
import com.impact.mods.gregtech.tileentities.multi.implement.GT_MetaTileEntity_MultiParallelBlockBase;
import gregtech.api.enums.Casings;
import gregtech.api.enums.HatchElement;
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
import ic2.core.init.BlocksItems;
import ic2.core.init.InternalName;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import space.impact.structures.hint.HintColor;
import space.impact.structures.structure.IStructureDefinition;
import space.impact.structures.structure.ISurvivalBuildEnvironment;
import space.impact.structures.structure.StructureDefinition;

import static com.impact.util.Utilits.getFluidStack;
import static space.impact.structures.structure.StructureUtility.ofBlockAnyMeta;
import static space.impact.structures.structure.StructureUtility.withHint;

public class GTMTE_AdvancedVacuumFreezer extends GT_MetaTileEntity_MultiParallelBlockBase<GTMTE_AdvancedVacuumFreezer> {

    private static final ICasing CASING = Casings.FROST_PROOF;

    private static final StructureRequirements SHELL = StructureRequirements.builder(CASING, 0)
            .atLeast(HatchElement.Maintenance).build();
    private static IStructureDefinition<GTMTE_AdvancedVacuumFreezer> definition;

    public GTMTE_AdvancedVacuumFreezer(int aID, String aNameRegional) {
        super(aID, "impact.multimachine.advvf", aNameRegional);
    }

    public GTMTE_AdvancedVacuumFreezer(String aName) {
        super(aName);
    }

    @Override
    public void construct(ItemStack itemStack, boolean b) {
        buildPiece(itemStack, b, 2, 2, 0);
    }

    @Override
    public int survivalConstruct(ItemStack trigger, int budget,
            ISurvivalBuildEnvironment environment) {
        return survivalBuildPiece("main", trigger, 2, 2, 0, budget, environment);
    }

    @Override
    public IStructureDefinition<GTMTE_AdvancedVacuumFreezer> getStructureDefinition() {
        if (definition == null) {
            definition = StructureDefinition.<GTMTE_AdvancedVacuumFreezer>builder()
                    .addShape("main", new String[][] {
                            { " DDD ", " DDD ", " D~D ", " DDD " },
                            { " DCD ", " DCD ", "DDCDD", "DDDDD" },
                            { " DCD ", "DDCDD", "DDCDD", "DDDDD" },
                            { " DCD ", "DDDDD", "DDDDD", "DDDDD" },
                            { " DDD ", " DDD ", " DDD ", " DDD " }
                    })
                    .addChannel(GTStructureUtility.HATCH_CHANNEL, 1, 1)
                    .addElement('C', ofBlockAnyMeta(BlocksItems.getFluidBlock(InternalName.fluidCoolant)))
                    .addElement('D', withHint(GTStructureUtility.buildHatchAdder(GTMTE_AdvancedVacuumFreezer.class)
                            .allow(HatchElement.Input, ImpactHatchElement.Muffler, HatchElement.Energy,
                                    ImpactHatchElement.LaserInput, ImpactHatchElement.Parallel, HatchElement.Output)
                            .buildAndChain(SHELL), HintColor.GREEN))
                    .build();
        }
        return definition;
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        return new MultiblockTooltipBuilder(mName)
                .addInfo("Speed Freeez!")
                .addInfo("Parallel points: base 1, maximum 256")
                .addInfo("Super Coolant is required for operation: 50 per second")
                .addInfo("At the output, get Hot Coolant: 25 per second")
                .addMachineType("Vacuum Freezer")
                .addInfo("Shift-click the controller with a screwdriver to switch separated buses")
                .addSeparator()
                .addController("See the structure hologram")
                .addOtherStructurePart("Energy Hatch", "Any casing; maximum 4")
                .addOtherStructurePart("Maintenance Hatch", "Any casing; maximum 1")
                .addOtherStructurePart("Input Hatch", "Any casing; maximum 1")
                .addOtherStructurePart("Output Hatch", "Any casing; maximum 1")
                .addOtherStructurePart("Input Bus", "Any casing; maximum 8")
                .addOtherStructurePart("Output Bus", "Any casing; maximum 1")
                .addOtherStructurePart("Parallel Hatch", "Any casing; maximum 1")
                .addRequirements(SHELL, "Any casing position")
                .addOtherStructurePart("IC2 Coolant fluid", "Any casing")
                .addStructureHint("Casing or compatible hatch", HintColor.GREEN)
                .toolTipFinisher();
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
        return new GTMTE_AdvancedVacuumFreezer(this.mName);
    }

    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GUI_BASE(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "MultiParallelBlockGUI.png");
    }

    @Override
    public GT_Recipe.GT_Recipe_Map getRecipeMap() {
        return GT_Recipe.GT_Recipe_Map.sVacuumRecipes;
    }

    @Override
    public boolean onRunningTick(ItemStack aStack) {
        if (getBaseMetaTileEntity().getTimer() % 20 == 0) {
            if (this.mEfficiency > 0 && depleteInput(getFluidStack("supercoolant", 50))) {
                addOutput(getFluidStack("ic2hotcoolant", 25));
            } else {
                stopMachine();
            }
        }
        return super.onRunningTick(aStack);
    }

    @Override
    public boolean machineStructure(IGregTechTileEntity thisController) {
        boolean formationChecklist = checkPiece(2, 2, 0) && SHELL.isSatisfied(this, 0);

        if (this.mInputBusses.size() > 8) {
            formationChecklist = false;
        }
        if (this.mInputHatches.size() > 3) {
            formationChecklist = false;
        }
        if (this.mOutputBusses.size() > 3) {
            formationChecklist = false;
        }
        if (this.mOutputHatches.size() > 1) {
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
