package com.impact.mods.gregtech.tileentities.multi.processing.parallel;

import com.impact.mods.gregtech.blocks.HatchCounts;
import com.impact.mods.gregtech.blocks.ImpactHatchElement;
import com.impact.mods.gregtech.tileentities.multi.implement.GT_MetaTileEntity_MultiParallelBlockBase;
import com.impact.util.ImpactMultiblockTooltipBuilder;
import gregtech.api.enums.Casings;
import gregtech.api.enums.HatchElement;
import gregtech.api.enums.HeatingCoil;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTStructureUtility;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.StructureRequirements;
import net.minecraft.item.ItemStack;
import space.impact.structures.hint.HintColor;
import space.impact.structures.structure.IStructureDefinition;
import space.impact.structures.structure.ISurvivalBuildEnvironment;
import space.impact.structures.structure.StructureDefinition;


public class GTMTE_FlotationUnit extends GT_MetaTileEntity_MultiParallelBlockBase<GTMTE_FlotationUnit> {

    private int casingCount;

    private static final StructureRequirements SHELL = StructureRequirements.builder(Casings.STABLE_TITANIUM, 6)
            .atLeast(HatchElement.Maintenance, ImpactHatchElement.Muffler).build();

    private static IStructureDefinition<GTMTE_FlotationUnit> definition;

    public GTMTE_FlotationUnit(int aID, String aNameRegional) {
        super(aID, "impact.multimachine.flotation", aNameRegional);
    }

    public GTMTE_FlotationUnit(String aName) {
        super(aName);
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex,
            boolean aActive, boolean aRedstone) {
        if (aSide == aFacing) {
            return new ITexture[] { Casings.STABLE_TITANIUM.getTexture(),
                    TextureFactory.of(aActive ? Textures.BlockIcons.OVERLAY_FRONT_DIESEL_ENGINE_ACTIVE
                            : Textures.BlockIcons.OVERLAY_FRONT_DIESEL_ENGINE) };
        }
        return new ITexture[] { Casings.STABLE_TITANIUM.getTexture() };
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GTMTE_FlotationUnit(this.mName);
    }

    @Override
    public void construct(ItemStack itemStack, boolean b) {
        buildPiece(itemStack, b, 1, 3, 0);
    }

    @Override
    public int survivalConstruct(ItemStack trigger, int budget,
            ISurvivalBuildEnvironment environment) {
        return survivalBuildPiece("main", trigger, 1, 3, 0, budget, environment);
    }

    @Override
    public IStructureDefinition<GTMTE_FlotationUnit> getStructureDefinition() {
        if (definition == null) {
            definition = StructureDefinition.<GTMTE_FlotationUnit>builder()
                    .addChannel(GTStructureUtility.HATCH_CHANNEL, 1, 1)
                    .addShape("main", new String[][] {
                            { "000", "121", "121", "0~0", },
                            { "000", "2 2", "2 2", "000", },
                            { "000", "121", "121", "000", },
                    })
                    .addElement('0', GTStructureUtility.buildHatchAdder(GTMTE_FlotationUnit.class)
                            .allow(ImpactHatchElement.Parallel, HatchElement.Output, HatchElement.Energy,
                                    ImpactHatchElement.LaserInput, HatchElement.Input)
                            .autoPlace(ImpactHatchElement.PowerInput, ImpactHatchElement.Parallel, HatchElement.Output, HatchElement.Input)
                            .buildAndChain(SHELL, machine -> machine.casingCount++).withHint(HintColor.GREEN))
                    .addElement('1', Casings.TITANIUM_PIPE.asElement())
                    .addElement('2', HeatingCoil.NICHROME.asElement())
                    .build();
        }
        return definition;
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        return new ImpactMultiblockTooltipBuilder(mName)
                .useCompactStructure()
                .addMachineType("Flotation Unit")
                .addParallelInfo()
                .addInfo("Pollution: %s per second before muffler reduction.", getPollutionPerTick(null) * 20)
                .beginStructureBlock(3, 4, 3, false)
                .addController("front center of the bottom layer")
                .addStructureSection("Components")
                .addStructurePartInfo(SHELL.getCasing(), "%s+; fill unused casing positions", SHELL.getMinimumCasings())
                .addOtherStructurePart(Casings.TITANIUM_PIPE, "exactly 4; centers of front and back walls, layers 2 and 3")
                .addOtherStructurePart(HeatingCoil.NICHROME, "exactly 12; side walls, layers 2 and 3")
                .addStructureSection("Hatches and buses")
                .addHatchInfo("Maintenance and Muffler Hatches", "exactly 1 each; casing positions")
                .addHatchInfo("Energy Hatches", "optional, up to 2 total; casing positions")
                .addHatchInfo("Input Buses", "optional, up to 2; casing positions")
                .addHatchInfo("Input Hatches", "optional, up to 2; casing positions")
                .addHatchInfo("Output Bus", "optional, up to 1; casing positions")
                .addHatchInfo("Output Hatch", "optional, up to 1; casing positions")
                .addHatchInfo("Parallel Receiver", "optional, up to 1; casing positions")
                .addStructureHint("Casings or hatches/buses", HintColor.GREEN)
                .addHatchChannelInfo()
                .toolTipFinisher();
    }


    @Override
    public GT_Recipe.GT_Recipe_Map getRecipeMap() {
        return GT_Recipe.GT_Recipe_Map.sFlotationUnitRecipes;
    }

    @Override
    public boolean machineStructure(IGregTechTileEntity thisController) {
        casingCount = 0;
        boolean check = checkPiece(1, 3, 0) && SHELL.isSatisfied(this, casingCount);

        if (HatchCounts.inputBuses(this) > 2) {
            check = false;
        }
        if (HatchCounts.inputHatches(this) > 2) {
            check = false;
        }
        if (this.mOutputBusses.size() > 1) {
            check = false;
        }
        if (this.mOutputHatches.size() > 1) {
            check = false;
        }
        if (HatchCounts.energyHatches(this) > 2) {
            check = false;
        }
        if (this.mMaintenanceHatches.size() != 1) {
            check = false;
        }
        if (this.mMufflerHatches.size() != 1) {
            check = false;
        }
        if (this.sParallHatchesIn.size() > 1) {
            check = false;
        }
        if (this.sParallHatchesOut.size() != 0) {
            check = false;
        }

        return check;
    }
}
