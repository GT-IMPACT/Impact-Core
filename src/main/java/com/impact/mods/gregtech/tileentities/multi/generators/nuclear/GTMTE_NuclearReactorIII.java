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

public class GTMTE_NuclearReactorIII extends GTMTE_NuclearReactorBase<GTMTE_NuclearReactorIII> {

    private static IStructureDefinition<GTMTE_NuclearReactorBase<GTMTE_NuclearReactorIII>> definition;

    public GTMTE_NuclearReactorIII(int aID, String aNameRegional) {
        super(aID, "impact.multis.nuclear3", aNameRegional);
        enabledMaintenance = false;
    }

    public GTMTE_NuclearReactorIII(String aName) {
        super(aName);
        enabledMaintenance = false;
    }

    @Override
    int tierReactor() {
        return 3;
    }

    @Override
    public int maxTemperature() {
        return 1_000_000;
    }

    @Override
    public int coefficientReactor() {
        return 1400;
    }

    @Override
    public IStructureDefinition<GTMTE_NuclearReactorBase<GTMTE_NuclearReactorIII>> getStructureDefinition() {
        if (definition == null) {
            definition = StructureDefinition.<GTMTE_NuclearReactorBase<GTMTE_NuclearReactorIII>>builder()
                    .addChannel(GTStructureUtility.HATCH_CHANNEL, 1, 1)
                    .addShape("main", new String[][] {
                            { "         ", "    C    ", "   CCC   ", "   CCC   ", "  CCCCC  ", "  HH~HH  " },
                            { "  CCCCC  ", "  PCCCP  ", "  P   P  ", "  P   P  ", " CP   PC ", " HHHHHHH " },
                            { " CrrrrrC ", " P     P ", " P     P ", " P     P ", "CP     PC", "HHHHHHHHH" },
                            { " CrrrrrC ", " C     C ", "C       C", "C       C", "C       C", "HHHHHHHHH" },
                            { " CrrrrrC ", "CC     CC", "C       C", "C       C", "C       C", "HHHHHHHHH" },
                            { " CrrrrrC ", " C     C ", "C       C", "C       C", "C       C", "HHHHHHHHH" },
                            { " CrrrrrC ", " P     P ", " P     P ", " P     P ", "CP     PC", "HHHHHHHHH" },
                            { "  CCCCC  ", "  PCCCP  ", "  P   P  ", "  P   P  ", " CP   PC ", " HHHHHHH " },
                            { "         ", "    C    ", "   CCC   ", "   CCC   ", "  CCCCC  ", "  HHHHH  " }
                    })
                    .addElement('C', shellElement(false))
                    .addElement('H', shellElement(true))
                    .addElement('P', Casings.STEEL_PIPE.asElement())
                    .addElement('r',
                            withHint(new HatchElementBuilder<GTMTE_NuclearReactorBase<GTMTE_NuclearReactorIII>>()
                                    .allow(ReactorHatch.Rod).buildAndChain(SHELL), HintColor.GREEN))
                    .build();
        }
        return definition;
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GTMTE_NuclearReactorIII(super.mName);
    }

    @Override
    public void construct(ItemStack itemStack, boolean b) {
        buildPiece(itemStack, b, 4, 5, 0);
    }

    @Override
    public int survivalConstruct(ItemStack trigger, int budget,
            ISurvivalBuildEnvironment environment) {
        return survivalBuildPiece("main", trigger, 4, 5, 0, budget, environment);
    }

    public boolean checkMachineFunction(IGregTechTileEntity thisController) {
        boolean checkStructure = checkPiece(4, 5, 0);

        if (mOutputHatches.size() > 24) {
            checkStructure = false;
        }

        if (mInputHatches.size() > 6) {
            checkStructure = false;
        }

        if (mMachineHull.size() > 1) {
            checkStructure = false;
        }

        return checkStructure;
    }
}
