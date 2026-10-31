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

public class GTMTE_NuclearReactorI extends GTMTE_NuclearReactorBase<GTMTE_NuclearReactorI> {

    private static IStructureDefinition<GTMTE_NuclearReactorBase<GTMTE_NuclearReactorI>> definition;

    public GTMTE_NuclearReactorI(int aID, String aNameRegional) {
        super(aID, "impact.multis.nuclear1", aNameRegional);
        enabledMaintenance = false;
    }

    public GTMTE_NuclearReactorI(String aName) {
        super(aName);
        enabledMaintenance = false;
    }

    @Override
    public IStructureDefinition<GTMTE_NuclearReactorBase<GTMTE_NuclearReactorI>> getStructureDefinition() {
        if (definition == null) {
            definition = StructureDefinition.<GTMTE_NuclearReactorBase<GTMTE_NuclearReactorI>>builder()
                    .addChannel(GTStructureUtility.HATCH_CHANNEL, 1, 1)
                    .addShape("main", new String[][] {
                            { "     ", "  C  ", " CCC ", " H~H " },
                            { " CCC ", " PCP ", "CP PC", "HHHHH" },
                            { " CRC ", "CC CC", "C   C", "HHHHH" },
                            { " CCC ", " PCP ", "CP PC", "HHHHH" },
                            { "     ", "  C  ", " CCC ", " HHH " }
                    })
                    .addElement('C', shellElement(false))
                    .addElement('H', shellElement(true))
                    .addElement('P', Casings.STEEL_PIPE.asElement())
                    .addElement('R',
                            new HatchElementBuilder<GTMTE_NuclearReactorBase<GTMTE_NuclearReactorI>>()
                                    .hint(HintColor.RED).build(RODS))
                    .build();
        }
        return definition;
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GTMTE_NuclearReactorI(super.mName);
    }

    @Override
    public void construct(ItemStack itemStack, boolean b) {
        buildPiece(itemStack, b, 2, 3, 0);
    }

    @Override
    public int survivalConstruct(ItemStack trigger, int budget,
            ISurvivalBuildEnvironment environment) {
        return survivalBuildPiece("main", trigger, 2, 3, 0, budget, environment);
    }

    @Override
    int tierReactor() {
        return 1;
    }

    @Override
    public int maxTemperature() {
        return 10_000;
    }

    @Override
    public int coefficientReactor() {
        return 200;
    }

    public boolean checkMachineFunction(IGregTechTileEntity thisController) {
        boolean checkStructure = checkPiece(2, 3, 0);

        if (mOutputHatches.size() > 6) {
            checkStructure = false;
        }

        if (mInputHatches.size() > 2) {
            checkStructure = false;
        }

        if (mMachineHull.size() > 1) {
            checkStructure = false;
        }

        return checkStructure;
    }
}
