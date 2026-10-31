package com.impact.mods.gregtech.tileentities.multi.generators.nuclear;

import gregtech.api.enums.Casings;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.util.GTStructureUtility;
import gregtech.api.util.HatchElementBuilder;
import net.minecraft.item.ItemStack;
import space.impact.structures.hint.HintColor;
import space.impact.structures.structure.IStructureDefinition;
import space.impact.structures.structure.ISurvivalBuildEnvironment;
import space.impact.structures.structure.StructureDefinition;

import static space.impact.structures.structure.StructureUtility.withHint;

public class GTMTE_NuclearReactorII extends GTMTE_NuclearReactorBase<GTMTE_NuclearReactorII> {

    private static IStructureDefinition<GTMTE_NuclearReactorBase<GTMTE_NuclearReactorII>> definition;

    public GTMTE_NuclearReactorII(int aID, String aNameRegional) {
        super(aID, "impact.multis.nuclear2", aNameRegional);
        enabledMaintenance = false;
    }

    public GTMTE_NuclearReactorII(String aName) {
        super(aName);
        enabledMaintenance = false;
    }

    @Override
    public IStructureDefinition<GTMTE_NuclearReactorBase<GTMTE_NuclearReactorII>> getStructureDefinition() {
        if (definition == null) {
            definition = StructureDefinition.<GTMTE_NuclearReactorBase<GTMTE_NuclearReactorII>>builder()
                    .addChannel(GTStructureUtility.HATCH_CHANNEL, 1, 1)
                    .addShape("main", new String[][] {
                            { "       ", "   C   ", "  CCC  ", "  CCC  ", " CCCCC ", " HH~HH " },
                            { "  BBB  ", " PBBBP ", " P   P ", " P   P ", "CP   PC", "HHHHHHH" },
                            { " BrrrB ", " B   B ", "C     C", "C     C", "C     C", "HHHHHHH" },
                            { " BrrrB ", "CB   BC", "C     C", "C     C", "C     C", "HHHHHHH" },
                            { " BrrrB ", " B   B ", "C     C", "C     C", "C     C", "HHHHHHH" },
                            { "  BBB  ", " PBBBP ", " P   P ", " P   P ", "CP   PC", "HHHHHHH" },
                            { "       ", "   C   ", "  CCC  ", "  CCC  ", " CCCCC ", " HHHHH " }
                    })
                    .addElement('C', shellElement(false))
                    .addElement('H', shellElement(true))
                    .addElement('P', Casings.STEEL_PIPE.asElement())
                    .addElement('B', CASING.asElement())
                    .addElement('r',
                            withHint(new HatchElementBuilder<GTMTE_NuclearReactorBase<GTMTE_NuclearReactorII>>()
                                    .allow(ReactorHatch.Rod).buildAndChain(SHELL), HintColor.GREEN))
                    .build();
        }
        return definition;
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GTMTE_NuclearReactorII(super.mName);
    }

    @Override
    public void construct(ItemStack itemStack, boolean b) {
        buildPiece(itemStack, b, 3, 5, 0);
    }

    @Override
    public int survivalConstruct(ItemStack trigger, int budget,
            ISurvivalBuildEnvironment environment) {
        return survivalBuildPiece("main", trigger, 3, 5, 0, budget, environment);
    }

    @Override
    int tierReactor() {
        return 2;
    }

    @Override
    public int maxTemperature() {
        return 100_000;
    }

    @Override
    public int coefficientReactor() {
        return 800;
    }

    public boolean checkMachineFunction(IGregTechTileEntity thisController) {
        boolean checkStructure = checkPiece(3, 5, 0);

        if (mOutputHatches.size() > 12) {
            checkStructure = false;
        }

        if (mInputHatches.size() > 3) {
            checkStructure = false;
        }

        if (mMachineHull.size() > 1) {
            checkStructure = false;
        }

        return checkStructure;
    }
}
