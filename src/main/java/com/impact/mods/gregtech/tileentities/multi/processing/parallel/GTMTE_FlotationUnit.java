package com.impact.mods.gregtech.tileentities.multi.processing.parallel;

import com.impact.mods.gregtech.blocks.ImpactHatchElement;
import com.impact.mods.gregtech.gui.base.GUI_BASE;
import com.impact.mods.gregtech.tileentities.multi.implement.GT_MetaTileEntity_MultiParallelBlockBase;
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
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import space.impact.structures.hint.HintColor;
import space.impact.structures.structure.IStructureDefinition;
import space.impact.structures.structure.ISurvivalBuildEnvironment;
import space.impact.structures.structure.StructureDefinition;

import static space.impact.structures.structure.StructureUtility.withHint;

public class GTMTE_FlotationUnit extends GT_MetaTileEntity_MultiParallelBlockBase<GTMTE_FlotationUnit> {

    private static final StructureRequirements SHELL = StructureRequirements.builder(Casings.STABLE_TITANIUM, 0)
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
                    .addElement('0', withHint(GTStructureUtility.buildHatchAdder(GTMTE_FlotationUnit.class)
                            .allow(ImpactHatchElement.Parallel, HatchElement.Output, HatchElement.Energy,
                                    ImpactHatchElement.LaserInput, HatchElement.Input)
                            .buildAndChain(SHELL), HintColor.RED))
                    .addElement('1', Casings.TITANIUM_PIPE.asElement())
                    .addElement('2', HeatingCoil.NICHROME.asElement())
                    .build();
        }
        return definition;
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        return new MultiblockTooltipBuilder(mName)
                .addInfo("Parallel points: base 1, maximum 256")
                .addMachineType("Flotation Unit")
                .addInfo("Has the all recipes as the Ore Washer and Chemical Bath")
                .addInfo("Shift-click the controller with a screwdriver to switch separated buses")
                .addSeparator()
                .addController("See the structure hologram")
                .addOtherStructurePart("Energy Hatch", "Any casing; maximum 2")
                .addOtherStructurePart("Maintenance Hatch", "Any casing; maximum 1")
                .addOtherStructurePart("Muffler Hatch", "Any casing; maximum 1")
                .addOtherStructurePart("Input Bus", "Any casing; maximum 2")
                .addOtherStructurePart("Output Bus", "Any casing; maximum 1")
                .addOtherStructurePart("Output Hatch", "Any casing; maximum 1")
                .addOtherStructurePart("Input Hatch", "Any casing; maximum 2")
                .addOtherStructurePart("Parallel Hatch", "Any casing; maximum 1")
                .addCasingInfoMin(Casings.STABLE_TITANIUM, 6, false)
                .addOtherStructurePart("Titanium Pipe Casing", "Any casing")
                .addOtherStructurePart("Nichrome Coil", "Any casing")
                .addStructureHint("Casing or compatible hatch", HintColor.RED)
                .toolTipFinisher();
    }

    @Override
    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GUI_BASE(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "MultiParallelBlockGUI.png");
    }

    @Override
    public GT_Recipe.GT_Recipe_Map getRecipeMap() {
        return GT_Recipe.GT_Recipe_Map.sFlotationUnitRecipes;
    }

    @Override
    public boolean machineStructure(IGregTechTileEntity thisController) {
        boolean check = checkPiece(1, 3, 0);

        if (this.mInputBusses.size() > 2) {
            check = false;
        }
        if (this.mInputHatches.size() > 2) {
            check = false;
        }
        if (this.mOutputBusses.size() > 1) {
            check = false;
        }
        if (this.mOutputHatches.size() > 1) {
            check = false;
        }
        if (this.mEnergyHatches.size() > 2) {
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
