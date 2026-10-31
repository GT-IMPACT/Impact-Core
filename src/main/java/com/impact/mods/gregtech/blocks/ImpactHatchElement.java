package com.impact.mods.gregtech.blocks;

import com.impact.mods.gregtech.tileentities.hatches.lasers.GTMTE_LaserEnergy_In;
import com.impact.mods.gregtech.tileentities.hatches.lasers.GTMTE_LaserEnergy_Out;
import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_Impact_BlockBase;
import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_PrimitiveBlockBase;
import com.impact.mods.gregtech.tileentities.hatches.GT_MetaTileEntity_Primitive_InputBus;
import com.impact.mods.gregtech.tileentities.hatches.GT_MetaTileEntity_Primitive_OutputBus;
import com.impact.mods.gregtech.tileentities.hatches.GT_MetaTileEntity_Primitive_Hatch_Output;
import com.impact.mods.gregtech.tileentities.multi.implement.GT_MetaTileEntity_MultiParallelBlockBase;
import com.impact.mods.gregtech.tileentities.multi.parallelsystem.GTMTE_ParallelHatch_Input;
import gregtech.api.interfaces.IHatchElement;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Dynamo;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_DynamoMulti;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Energy;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Input;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_InputBus;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Output;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Muffler;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase;
import net.minecraft.util.StatCollector;

public enum ImpactHatchElement implements IHatchElement {
    FluidOutput("gt.tooltip.hatch.output_hatch") {
        @Override
        public boolean matches(IMetaTileEntity tile) {
            return tile instanceof GT_MetaTileEntity_Hatch_Output;
        }

        @Override
        public int count(GT_MetaTileEntity_MultiBlockBase machine) {
            return machine.mOutputHatches.size();
        }

        @Override
        public boolean add(GT_MetaTileEntity_MultiBlockBase machine, IGregTechTileEntity tile, int texture) {
            return machine.addOutputToMachineList(tile, texture);
        }
    },
    InputBus("gt.tooltip.hatch.input_bus") {
        @Override
        public boolean matches(IMetaTileEntity tile) {
            return tile instanceof GT_MetaTileEntity_Hatch_InputBus;
        }

        @Override
        public int count(GT_MetaTileEntity_MultiBlockBase machine) {
            return machine.mInputBusses.size();
        }

        @Override
        public boolean add(GT_MetaTileEntity_MultiBlockBase machine, IGregTechTileEntity tile, int texture) {
            return machine.addInputToMachineList(tile, texture);
        }
    },
    FluidInput("gt.tooltip.hatch.input_hatch") {
        @Override
        public boolean matches(IMetaTileEntity tile) {
            return tile instanceof GT_MetaTileEntity_Hatch_Input;
        }

        @Override
        public int count(GT_MetaTileEntity_MultiBlockBase machine) {
            return machine.mInputHatches.size();
        }

        @Override
        public boolean add(GT_MetaTileEntity_MultiBlockBase machine, IGregTechTileEntity tile, int texture) {
            return machine.addInputToMachineList(tile, texture);
        }
    },
    PrimitiveInput("gt.tooltip.hatch.input_bus") {
        @Override
        public boolean matches(IMetaTileEntity tile) {
            return tile instanceof GT_MetaTileEntity_Primitive_InputBus;
        }

        @Override
        public int count(GT_MetaTileEntity_MultiBlockBase machine) {
            return ((GTMTE_PrimitiveBlockBase<?>) machine).mInputBusses1.size();
        }

        @Override
        public boolean add(GT_MetaTileEntity_MultiBlockBase machine, IGregTechTileEntity tile, int texture) {
            return ((GTMTE_PrimitiveBlockBase<?>) machine).addPrimInputToMachineList(tile, texture);
        }
    },
    PrimitiveOutput("impact.structure.hatch.primitive_output") {
        @Override
        public boolean matches(IMetaTileEntity tile) {
            return tile instanceof GT_MetaTileEntity_Primitive_Hatch_Output
                    || tile instanceof GT_MetaTileEntity_Primitive_OutputBus;
        }

        @Override
        public int count(GT_MetaTileEntity_MultiBlockBase machine) {
            GTMTE_PrimitiveBlockBase<?> primitive = (GTMTE_PrimitiveBlockBase<?>) machine;
            return primitive.mOutputHatches1.size() + primitive.mOutputBusses1.size();
        }

        @Override
        public boolean add(GT_MetaTileEntity_MultiBlockBase machine, IGregTechTileEntity tile, int texture) {
            return ((GTMTE_PrimitiveBlockBase<?>) machine).addPrimOutputToMachineList(tile, texture);
        }
    },
    RegularEnergy("gt.tooltip.hatch.energy") {
        @Override
        public boolean matches(IMetaTileEntity tile) {
            return tile instanceof GT_MetaTileEntity_Hatch_Energy;
        }

        @Override
        public int count(GT_MetaTileEntity_MultiBlockBase machine) {
            return machine.mEnergyHatches.size();
        }

        @Override
        public boolean add(GT_MetaTileEntity_MultiBlockBase machine, IGregTechTileEntity tile, int texture) {
            return machine.addEnergyInputToMachineList(tile, texture);
        }
    },
    Muffler("gt.tooltip.hatch.muffler") {
        @Override
        public boolean matches(IMetaTileEntity tile) {
            return tile instanceof GT_MetaTileEntity_Hatch_Muffler;
        }

        @Override
        public int count(GT_MetaTileEntity_MultiBlockBase machine) {
            return machine.mMufflerHatches.size();
        }

        @Override
        public boolean add(GT_MetaTileEntity_MultiBlockBase machine, IGregTechTileEntity tile, int texture) {
            return machine.addMufflerToMachineList(tile, texture);
        }
    },
    Dynamo("gt.tooltip.hatch.dynamo") {
        @Override
        public boolean matches(IMetaTileEntity tile) {
            return tile instanceof GT_MetaTileEntity_Hatch_Dynamo
                    || tile instanceof GT_MetaTileEntity_Hatch_DynamoMulti;
        }

        @Override
        public int count(GT_MetaTileEntity_MultiBlockBase machine) {
            return machine.mDynamoHatches.size() + machine.mDynamoHatchesMulti.size();
        }

        @Override
        public boolean add(GT_MetaTileEntity_MultiBlockBase machine, IGregTechTileEntity tile, int texture) {
            return machine.addDynamoToMachineList(tile, texture);
        }
    },
    LaserInput("impact.structure.hatch.laser_input") {
        @Override
        public boolean matches(IMetaTileEntity tile) {
            return tile instanceof GTMTE_LaserEnergy_In;
        }

        @Override
        public int count(GT_MetaTileEntity_MultiBlockBase machine) {
            return machine instanceof GTMTE_Impact_BlockBase ? ((GTMTE_Impact_BlockBase<?>) machine).mLaserIn.size()
                    : 0;
        }

        @Override
        public boolean add(GT_MetaTileEntity_MultiBlockBase machine, IGregTechTileEntity tile, int texture) {
            return machine.addEnergyInputToMachineList(tile, texture);
        }
    },
    LaserOutput("impact.structure.hatch.laser_output") {
        @Override
        public boolean matches(IMetaTileEntity tile) {
            return tile instanceof GTMTE_LaserEnergy_Out;
        }

        @Override
        public int count(GT_MetaTileEntity_MultiBlockBase machine) {
            return machine instanceof GTMTE_Impact_BlockBase ? ((GTMTE_Impact_BlockBase<?>) machine).mLaserOut.size()
                    : 0;
        }

        @Override
        public boolean add(GT_MetaTileEntity_MultiBlockBase machine, IGregTechTileEntity tile, int texture) {
            return machine.addDynamoToMachineList(tile, texture);
        }
    },
    Parallel("impact.structure.hatch.parallel") {
        @Override
        public boolean matches(IMetaTileEntity tile) {
            return tile instanceof GTMTE_ParallelHatch_Input;
        }

        @Override
        public int count(GT_MetaTileEntity_MultiBlockBase machine) {
            return machine instanceof GT_MetaTileEntity_MultiParallelBlockBase
                    ? ((GT_MetaTileEntity_MultiParallelBlockBase<?>) machine).sParallHatchesIn.size()
                    : 0;
        }

        @Override
        public boolean add(GT_MetaTileEntity_MultiBlockBase machine, IGregTechTileEntity tile, int texture) {
            return machine instanceof GT_MetaTileEntity_MultiParallelBlockBase
                    && ((GT_MetaTileEntity_MultiParallelBlockBase<?>) machine).addParallHatchToMachineList(tile,
                            texture);
        }
    };

    private final String translationKey;

    ImpactHatchElement(String translationKey) {
        this.translationKey = translationKey;
    }

    @Override
    public String getDescription() {
        return StatCollector.translateToLocal(translationKey);
    }
}
