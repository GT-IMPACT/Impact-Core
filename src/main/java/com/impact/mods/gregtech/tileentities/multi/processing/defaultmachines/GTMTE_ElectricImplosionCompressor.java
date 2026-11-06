package com.impact.mods.gregtech.tileentities.multi.processing.defaultmachines;

import com.impact.mods.gregtech.blocks.HatchCounts;
import com.impact.mods.gregtech.blocks.ImpactHatchElement;
import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_Impact_BlockBase;
import gregtech.api.enums.Casings;
import gregtech.api.enums.HatchElement;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ICasing;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTStructureUtility;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Recipe;
import gregtech.api.logic.ProcessingInputs;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.StructureRequirements;
import net.minecraft.item.ItemStack;
import space.impact.structures.hint.HintColor;
import space.impact.structures.structure.IStructureDefinition;
import space.impact.structures.structure.ISurvivalBuildEnvironment;
import space.impact.structures.structure.StructureDefinition;

import static space.impact.structures.structure.StructureUtility.withHint;

public class GTMTE_ElectricImplosionCompressor extends GTMTE_Impact_BlockBase<GTMTE_ElectricImplosionCompressor> {

    private static final ICasing CASING = Casings.SOLID_STEEL;

    private int casingCount;

    private static final StructureRequirements SHELL = StructureRequirements.builder(CASING, 21)
            .atLeast(HatchElement.Maintenance, ImpactHatchElement.Muffler).build();
    private static IStructureDefinition<GTMTE_ElectricImplosionCompressor> definition;

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
    public int survivalConstruct(ItemStack trigger, int budget,
            ISurvivalBuildEnvironment environment) {
        return survivalBuildPiece("main", trigger, 6, 1, 0, budget, environment);
    }

    @Override
    public IStructureDefinition<GTMTE_ElectricImplosionCompressor> getStructureDefinition() {
        if (definition == null) {
            definition = StructureDefinition.<GTMTE_ElectricImplosionCompressor>builder()
                    .addShape("main", new String[][] {
                            { "DDDDDD ", "DCCCCD~", "DDDDDDD" },
                            { "DCCCCDD", "D    DD", "DCCCCDD" },
                            { "DDDDDDD", "DCCCCDD", "DDDDDDD" }
                    })
                    .addChannel(GTStructureUtility.HATCH_CHANNEL, 1, 1)
                    .addElement('C', Casings.SUPERCONDUCTING_COIL.asElement())
                    .addElement('D',
                            withHint(GTStructureUtility.buildHatchAdder(GTMTE_ElectricImplosionCompressor.class)
                                    .allow(HatchElement.Input, HatchElement.Energy, ImpactHatchElement.LaserInput,
                                            HatchElement.Output)
                                    .autoPlace(ImpactHatchElement.PowerInput, HatchElement.Input, HatchElement.Output)
                                    .buildAndChain(SHELL, machine -> machine.casingCount++), HintColor.GREEN))
                    .build();
        }
        return definition;
    }

    @Override
    public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide, final byte aFacing,
            final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
        return aSide == aFacing
                ? new ITexture[] { CASING.getTexture(),
                        TextureFactory.of(aActive ? Textures.BlockIcons.MP1a : Textures.BlockIcons.MP1) }
                : new ITexture[] { CASING.getTexture() };
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GTMTE_ElectricImplosionCompressor(this.mName);
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        return new MultiblockTooltipBuilder(mName)
                .addInfo("Babah!")
                .addMachineType("Implosion Compressor")
                .addInfo("Not used TNT!!!")
                .addInfo("Energy consumption: (EU/t) = (NEI Recipe EU/t) * 1000")
                .addInfo("Select machine type by clicking the controller with a screwdriver")
                .addInfo("Configure input bus separation in the machine GUI")
                .addSeparator()
                .beginStructureBlock(7, 3, 3, false)
                .addController("See the structure hologram")
                .addOtherStructurePart("Energy Hatch", "Any casing; maximum 2")
                .addOtherStructurePart("Maintenance Hatch", "Any casing position; exactly 1")
                .addOtherStructurePart("Muffler Hatch", "Any casing position; exactly 1")
                .addOtherStructurePart("Input Bus", "Any casing; maximum 5")
                .addOtherStructurePart("Output Bus", "Any casing; maximum 3")
                .addCasingInfoMin(SHELL.getCasing(), SHELL.getMinimumCasings(), false)
                .addOtherStructurePart(Casings.SUPERCONDUCTING_COIL, "Around the compression chamber, including the top and bottom faces", 16)
                .addStructureHint("Casing or compatible hatch", HintColor.GREEN)
                .addStructureInfo("Hatches replacing casings: at most 20")
                .addSubChannel(GTStructureUtility.HATCH_CHANNEL, "Installs maintenance, energy, input, output and muffler hatches from your inventory in the marked casing positions.")
                .toolTipFinisher();
    }


    @Override
    protected List<ProcessingInputs> collectProcessingInputs() {
        List<ProcessingInputs> groups = new ArrayList<>(super.collectProcessingInputs());
        ItemStack explosive = GT_ModHandler.getIC2Item("industrialTnt", 64, null);
        for (int index = 0; index < groups.size(); index++) {
            groups.set(index, groups.get(index).withVirtualItems(Collections.singletonList(explosive)));
        }
        return groups;
    }

    @Override
    public GT_Recipe.GT_Recipe_Map getRecipeMap() {
        return GT_Recipe.GT_Recipe_Map.sImplosionRecipes;
    }

    @Override
    public boolean machineStructure(IGregTechTileEntity thisController) {
        casingCount = 0;
        boolean formationChecklist = checkPiece(6, 1, 0) && SHELL.isSatisfied(this, casingCount);

        if (HatchCounts.inputBuses(this) > 5) {
            formationChecklist = false;
        }
        if (this.mOutputBusses.size() > 3) {
            formationChecklist = false;
        }
        if (HatchCounts.energyHatches(this) > 2) {
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
