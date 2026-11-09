package com.impact.mods.gregtech;

import com.impact.mods.gregtech.blocks.HatchCounts;
import com.impact.mods.gregtech.blocks.ImpactCasings;
import com.impact.mods.gregtech.blocks.ImpactHatchElement;
import com.impact.mods.gregtech.tileentities.hatches.lasers.GTMTE_LaserEnergy_In;
import com.impact.mods.gregtech.tileentities.multi.processing.parallel.GTMTE_Assembler;
import com.impact.mods.gregtech.tileentities.multi.processing.parallel.GTMTE_PressBendExtrud;
import gregtech.api.interfaces.ICasing;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.implementations.GTMTE_BusHatch_Input;
import gregtech.api.metatileentity.implementations.GTMTE_Multi_Hatch_Input;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_EnhancedMultiBlockBase;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Energy;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_EnergyMulti;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Input;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Maintenance;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Muffler;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Output;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_OutputBus;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_TooltipMultiBlockBase;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.ForgeDirection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class ImpactCasingChecks {

    private ImpactCasingChecks() {
    }

    static void checkTooltip(GT_MetaTileEntity_EnhancedMultiBlockBase<?> machine) throws Exception {
        String name = machine.getClass().getSimpleName();
        // These two drills consist of frames and a mandatory drill hatch, with no casing blocks.
        if (name.equals("GTMTEOreSamplingMachine") || name.equals("GTMTE_Mining_Coal")) {
            return;
        }
        Method tooltip = GT_MetaTileEntity_TooltipMultiBlockBase.class.getDeclaredMethod("getTooltip");
        tooltip.setAccessible(true);
        Object cached = tooltip.invoke(machine);
        String description = String.join("\n", machine.getSecondaryDescription());
        for (Class<?> type = machine.getClass(); type != null; type = type.getSuperclass()) {
            for (Field field : type.getDeclaredFields()) {
                if (Modifier.isStatic(field.getModifiers()) && ICasing.class.isAssignableFrom(field.getType())) {
                    field.setAccessible(true);
                    ICasing casing = (ICasing) field.get(null);
                    assertTrue(description.contains(casing.getDisplayName()), name + " omits " + field.getName());
                }
            }
        }
        assertSame(cached, tooltip.invoke(machine), "The structure page must use the native cache");
        GT_MetaTileEntity_TooltipMultiBlockBase.invalidateTooltipCache();
        assertNotSame(cached, tooltip.invoke(machine), "Resource reload must rebuild casing names");
        assertEquals(description, String.join("\n", machine.getSecondaryDescription()));
    }

    static void checkLimits() {
        checkPbeLimits();
        ImpactConstructionChecks.TestWorld world = new ImpactConstructionChecks.TestWorld();
        Assembler machine = new Assembler();
        world.putTile(0, 64, 0, new ImpactConstructionChecks.TestTile(world, machine, ForgeDirection.NORTH));
        machine.construct(new ItemStack(Items.stick), false);
        List<String> positions = new ArrayList<>();
        world.blocks.forEach((position, block) -> {
            if (block == ImpactCasings.ASSEMBLER_CASING.getBlock()
                    && world.metadata.get(position) == ImpactCasings.ASSEMBLER_CASING.getMetadata()) {
                positions.add(position);
            }
        });
        assertEquals(61, positions.size());
        put(world, positions.get(0), maintenance());
        for (int i = 1; i < 35; i++) {
            put(world, positions.get(i), new GT_MetaTileEntity_Hatch_Output("output", 4, "", new ITexture[0][][]));
        }
        assertTrue(machine.recheck(), "26 casings and 35 hatches must form the assembler");
        assertTrue(machine.recheck(), "Casing counts must reset between checks");
        String tooltip = EnumChatFormatting.getTextWithoutFormattingCodes(String.join("\n", machine.getSecondaryDescription()));
        assertTrue(tooltip.contains("26"), "The tooltip must expose the enforced minimum");
        put(world, positions.get(35), new GT_MetaTileEntity_Hatch_Output("output", 4, "", new ITexture[0][][]));
        assertFalse(machine.recheck(), "25 casings are below the minimum");
        for (int i = 36; i < positions.size(); i++) {
            put(world, positions.get(i), new GT_MetaTileEntity_Hatch_Output("output", 4, "", new ITexture[0][][]));
        }
        assertFalse(machine.recheck(), "A shell made entirely of hatches must be rejected");

        restore(world, positions);
        put(world, positions.get(0), maintenance());
        MetaTileEntity[] energy = {
                new GT_MetaTileEntity_Hatch_Energy("regular", 4, "", new ITexture[0][][]),
                new GT_MetaTileEntity_Hatch_EnergyMulti("multi", 4, 4, "", new ITexture[0][][]),
                new GTMTE_LaserEnergy_In("laser", 4, 4, "", new ITexture[0][][]),
                new GT_MetaTileEntity_Hatch_EnergyMulti("multi2", 4, 16, "", new ITexture[0][][]),
                new GTMTE_LaserEnergy_In("laser2", 4, 16, "", new ITexture[0][][])
        };
        for (int i = 0; i < energy.length; i++) {
            put(world, positions.get(i + 1), energy[i]);
            assertEquals(i < 4, machine.recheck(), "All energy variants share the four-hatch limit");
            if (i < 4) {
                assertEquals(i + 1, HatchCounts.energyHatches(machine));
            }
        }

        restore(world, positions);
        put(world, positions.get(0), maintenance());
        for (int i = 1; i <= 6; i++) {
            put(world, positions.get(i), new GTMTE_Multi_Hatch_Input(
                    "multi-fluid",
                    i % 2 == 0 ? 4 : 9,
                    4,
                    new String[0],
                    new ITexture[0][][],
                    16000
            ));
        }
        assertTrue(machine.recheck(), "Six multi-fluid hatches count as six physical inputs");
        assertEquals(6, ImpactHatchElement.FluidInput.count(machine));
        for (int i = 7; i <= 27; i++) {
            put(world, positions.get(i), new GTMTE_BusHatch_Input(
                    "combined",
                    4,
                    new String[0],
                    new ITexture[0][][],
                    16000
            ));
        }
        assertTrue(machine.recheck(), "Item and fluid inputs have no separate limits");
        assertEquals(27, HatchCounts.inputHatches(machine));
        assertEquals(21, HatchCounts.inputBuses(machine));
        for (int i = 28; i <= 31; i++) {
            put(world, positions.get(i), new GT_MetaTileEntity_Hatch_OutputBus("output", 4, "", new ITexture[0][][]));
        }
        assertTrue(machine.recheck(), "Output buses have no separate limit");
        assertEquals(4, machine.mOutputBusses.size());
    }

    private static void checkPbeLimits() {
        ImpactConstructionChecks.TestWorld world = new ImpactConstructionChecks.TestWorld();
        Pbe machine = new Pbe();
        world.putTile(0, 64, 0, new ImpactConstructionChecks.TestTile(world, machine, ForgeDirection.NORTH));
        machine.construct(new ItemStack(Items.stick), false);
        List<String> positions = new ArrayList<>();
        world.blocks.forEach((position, block) -> {
            if (block == ImpactCasings.PBECASING.getBlock()
                    && world.metadata.get(position) == ImpactCasings.PBECASING.getMetadata()) {
                positions.add(position);
            }
        });
        assertEquals(41, positions.size());
        put(world, positions.get(0), maintenance());
        put(world, positions.get(1), new GT_MetaTileEntity_Hatch_Muffler("muffler", 4, "", new ITexture[0][][]));
        for (int i = 2; i < 30; i++) {
            put(world, positions.get(i), new GT_MetaTileEntity_Hatch_Output("output", 4, "", new ITexture[0][][]));
        }
        assertTrue(machine.recheck(), "11 casings and 30 hatches must form the PBE");
        assertTrue(machine.recheck(), "PBE casing counts must reset between checks");
        String tooltip = EnumChatFormatting.getTextWithoutFormattingCodes(
                String.join("\n", machine.getSecondaryDescription())
        );
        assertTrue(tooltip.contains("11"), "The PBE tooltip must expose the enforced minimum");
        put(world, positions.get(30), new GT_MetaTileEntity_Hatch_Output("output", 4, "", new ITexture[0][][]));
        assertFalse(machine.recheck(), "10 casings are below the PBE minimum");
    }

    private static GT_MetaTileEntity_Hatch_Maintenance maintenance() {
        return new GT_MetaTileEntity_Hatch_Maintenance("maintenance", 1, "", new ITexture[0][][], false);
    }

    static void checkGeneratorLimits(ImpactConstructionChecks.TestWorld world,
            GT_MetaTileEntity_EnhancedMultiBlockBase<?> machine, List<String> positions,
            int total, int minimum, boolean sofc) throws Exception {
        Method setup = GT_MetaTileEntity_MultiBlockBase.class.getDeclaredMethod(
                "setupMachine", IGregTechTileEntity.class, ItemStack.class);
        setup.setAccessible(true);
        String name = machine.getClass().getSimpleName();
        int limit = total - minimum;
        for (int i = 0; i < limit; i++) {
            MetaTileEntity hatch;
            if (!sofc) {
                hatch = new GT_MetaTileEntity_Hatch_Energy("energy", 4, "", new ITexture[0][][]);
            } else if (i == 0) {
                hatch = maintenance();
            } else if (i <= 2) {
                hatch = new GT_MetaTileEntity_Hatch_Input("input", 4, "", new ITexture[0][][]);
            } else if (i <= 4) {
                hatch = new GT_MetaTileEntity_Hatch_Output("output", 4, "", new ITexture[0][][]);
            } else {
                hatch = new GT_MetaTileEntity_Hatch_Input("input", 4, "", new ITexture[0][][]);
            }
            put(world, positions.get(i), hatch);
        }
        setup.invoke(machine, machine.getBaseMetaTileEntity(), null);
        assertTrue(machine.mMachine, name + " at its minimum of " + minimum);
        setup.invoke(machine, machine.getBaseMetaTileEntity(), null);
        assertTrue(machine.mMachine, name + " repeated minimum check");
        if (sofc) {
            assertEquals(2, machine.mOutputHatches.size());
            put(world, positions.get(5), new GT_MetaTileEntity_Hatch_Output("third-output", 4, "", new ITexture[0][][]));
            setup.invoke(machine, machine.getBaseMetaTileEntity(), null);
            assertFalse(machine.mMachine, name + " rejects a third output hatch");
            put(world, positions.get(5), new GT_MetaTileEntity_Hatch_Input("input", 4, "", new ITexture[0][][]));
            setup.invoke(machine, machine.getBaseMetaTileEntity(), null);
            assertTrue(machine.mMachine, name + " forms again with two output hatches");
        }
        put(world, positions.get(limit), sofc
                ? new GT_MetaTileEntity_Hatch_Input("excess", 4, "", new ITexture[0][][])
                : new GT_MetaTileEntity_Hatch_Energy("excess", 4, "", new ITexture[0][][]));
        setup.invoke(machine, machine.getBaseMetaTileEntity(), null);
        assertFalse(machine.mMachine, name + " one casing below its minimum");
    }

    private static void put(ImpactConstructionChecks.TestWorld world, String position, MetaTileEntity hatch) {
        String[] point = position.split(":");
        world.putTile(Integer.parseInt(point[0]), Integer.parseInt(point[1]), Integer.parseInt(point[2]),
                new ImpactConstructionChecks.TestTile(world, hatch, ForgeDirection.NORTH));
    }

    private static void restore(ImpactConstructionChecks.TestWorld world, List<String> positions) {
        for (String position : positions) {
            String[] point = position.split(":");
            world.setBlock(Integer.parseInt(point[0]), Integer.parseInt(point[1]), Integer.parseInt(point[2]),
                    ImpactCasings.ASSEMBLER_CASING.getBlock(), ImpactCasings.ASSEMBLER_CASING.getMetadata(), 0);
        }
    }

    private static final class Pbe extends GTMTE_PressBendExtrud {

        Pbe() {
            super("pbe-casing-limit-test");
        }

        boolean recheck() {
            setupMachine(getBaseMetaTileEntity(), null);
            return mMachine;
        }
    }

    private static final class Assembler extends GTMTE_Assembler {

        Assembler() {
            super("casing-limit-test");
        }

        boolean recheck() {
            setupMachine(getBaseMetaTileEntity(), null);
            return mMachine;
        }
    }
}
