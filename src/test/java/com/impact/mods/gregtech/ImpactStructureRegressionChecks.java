package com.impact.mods.gregtech;

import com.impact.loader.ItemRegistery;
import com.impact.mods.gregtech.tileentities.multi.generators.nuclear.GTMTE_HugeSteamTurbine;
import com.impact.mods.gregtech.tileentities.multi.processing.defaultmachines.GTMTE_Pyrolyse;
import com.impact.mods.gregtech.tileentities.multi.processing.parallel.GTMTE_AdvancedPyrolyse;
import com.impact.mods.gregtech.tileentities.multi.units.GTMTE_Tesseract;
import com.impact.mods.gregtech.tileentities.multi.units.GTMTE_TinyWormHole;
import com.impact.mods.gregtech.tileentities.multi.units.GTMTE_AntimatterReactor;
import com.impact.mods.gregtech.tileentities.hatches.lasers.GTMTE_LaserEnergy_In;
import gregtech.api.GregTech_API;
import gregtech.api.enums.Materials;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.implementations.*;
import gregtech.api.metatileentity.implementations.GT_MetaPipeEntity_Frame;
import java.lang.reflect.Method;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import space.impact.structures.structure.IStructureElement;
import space.impact.structures.structure.StructureDefinition;
import static com.impact.mods.gregtech.ImpactConstructionChecks.*;
import static org.junit.jupiter.api.Assertions.*;

final class ImpactStructureRegressionChecks {
    private static Method setup;

    static void check() throws Exception {
        setup = GT_MetaTileEntity_MultiBlockBase.class.getDeclaredMethod("setupMachine", IGregTechTileEntity.class, ItemStack.class);
        setup.setAccessible(true);
        checkInputTiers();
        checkFrames();
        checkTurbineRotor();
        checkTankCasings();
    }

    private static boolean recheck(GT_MetaTileEntity_EnhancedMultiBlockBase<?> machine) throws Exception {
        setup.invoke(machine, machine.getBaseMetaTileEntity(), null);
        return machine.mMachine;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static int[] position(GT_MetaTileEntity_EnhancedMultiBlockBase machine, char symbol, int horizontal, int vertical, int depth) {
        IStructureElement target = (IStructureElement) ((StructureDefinition) machine.getStructureDefinition()).getElements().get(symbol);
        int[][] found = { null };
        machine.getStructureDefinition().iterate("main", machine.getBaseMetaTileEntity().getWorld(), machine.getExtendedFacing(),
                0, 64, 0, horizontal, vertical, depth, (element, world, x, y, z, a, b, c) -> {
                    if (element == target && found[0] == null) {
                        net.minecraft.tileentity.TileEntity tile = world.getTileEntity(x, y, z);
                        IMetaTileEntity meta = tile instanceof IGregTechTileEntity
                                ? ((IGregTechTileEntity) tile).getMetaTileEntity() : null;
                        if (!(meta instanceof GT_MetaTileEntity_Hatch_Maintenance)) {
                            found[0] = new int[] { x, y, z };
                        }
                    }
                    return true;
                });
        assertNotNull(found[0], "Structure symbol " + symbol);
        return found[0];
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static void checkInputTiers() throws Exception {
        int[][] origins = { {4, 8, 3}, {2, 6, 0}, {7, 7, 0} };
        for (int index = 0; index < 3; index++) {
            int minimum = index == 0 ? 9 : 8;
            for (int kind = index == 2 ? 2 : 0; kind < 4; kind++) {
                for (int tier : new int[] { 1, minimum - 1, minimum }) {
                    for (ForgeDirection facing : new ForgeDirection[] { ForgeDirection.NORTH, ForgeDirection.EAST }) {
                        GT_MetaTileEntity_EnhancedMultiBlockBase<?> machine = index == 0 ? new GTMTE_Tesseract("tier-tesseract")
                                : index == 1 ? new GTMTE_TinyWormHole("tier-wormhole") : new GTMTE_AntimatterReactor("tier-antimatter");
                        TestWorld world = new TestWorld();
                        world.putTile(0, 64, 0, new TestTile(world, machine, facing));
                        machine.construct(new ItemStack(Items.stick), false);
                        if (index < 2) {
                            int[] maintenance = position(machine, index == 0 ? '7' : 'G',
                                    origins[index][0], origins[index][1], origins[index][2]);
                            GT_MetaTileEntity_Hatch_Maintenance hatch = new GT_MetaTileEntity_Hatch_Maintenance(
                                    "maintenance",
                                    1,
                                    "",
                                    new ITexture[0][][],
                                    false
                            );
                            world.putTile(maintenance[0], maintenance[1], maintenance[2],
                                    new TestTile(world, hatch, facing));
                        }
                        assertTrue(recheck(machine), "Valid shell " + machine.getClass().getSimpleName());
                        MetaTileEntity hatch = kind == 0 ? new GT_MetaTileEntity_Hatch_EnergyMulti("tier-multi-energy", tier, 4, "", new ITexture[0][][])
                                : kind == 1 ? new GTMTE_LaserEnergy_In("tier-laser", tier, 4, "", new ITexture[0][][])
                                : kind == 2 ? new GTMTE_BusHatch_Input("tier-combined", tier, new String[0], new ITexture[0][][], 1000)
                                : new GTMTE_Multi_Hatch_Input("tier-quad", 4, tier, new String[0], new ITexture[0][][], 1000);
                        char symbol = index == 0 ? (kind < 2 ? '8' : '2') : index == 1 ? (kind < 2 ? 'G' : 'J') : 'I';
                        int[] point = position(machine, symbol, origins[index][0], origins[index][1], origins[index][2]);
                        world.putTile(point[0], point[1], point[2], new TestTile(world, hatch, facing));
                        IStructureElement element = (IStructureElement) ((StructureDefinition) machine.getStructureDefinition()).getElements().get(symbol);
                        assertEquals(tier >= minimum, element.couldBeValid(machine, world, point[0], point[1], point[2], new ItemStack(Items.stick)),
                                "Wrong-tier inputs must also be marked as invalid in the hologram");
                        assertEquals(tier >= minimum, recheck(machine), machine.getClass().getSimpleName() + " " + hatch.getClass().getSimpleName() + " tier=" + tier);
                    }
                }
            }
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static void checkFrames() {
        Block previous = GregTech_API.sBlockMachines;
        GregTech_API.sBlockMachines = new Block(Material.iron) {
            @Override
            public int getDamageValue(World world, int x, int y, int z) {
                return ((IGregTechTileEntity) world.getTileEntity(x, y, z)).getMetaTileID();
            }
        };
        try {
            for (boolean advanced : new boolean[] { false, true }) {
                GT_MetaTileEntity_EnhancedMultiBlockBase machine = advanced ? new GTMTE_AdvancedPyrolyse("frame-advanced") : new GTMTE_Pyrolyse("frame-basic");
                Materials material = advanced ? Materials.HSLA : Materials.Steel;
                IStructureElement frame = (IStructureElement) ((StructureDefinition) machine.getStructureDefinition()).getElements().get('B');
                TestWorld world = new TestWorld();
                for (int id = 0; id < 16; id++) {
                    final int metaId = id;
                    TestTile fake = new TestTile(world, new GT_MetaTileEntity_Hatch_Maintenance("not-frame", 1, "", new ITexture[0][][], false), ForgeDirection.NORTH) {
                        @Override public int getMetaTileID() { return metaId; }
                    };
                    world.putTile(10, 64, 10, fake);
                    world.blocks.put(TestWorld.key(10, 64, 10), GregTech_API.sBlockMachines);
                    assertFalse(frame.check(machine, world, 10, 64, 10), "A machine is not a frame: " + id);
                }
                for (Materials candidate : new Materials[] { material, Materials.Platinum }) {
                    IMetaTileEntity actualFrame = new GT_MetaPipeEntity_Frame("real-frame", candidate);
                    TestTile tile = new TestTile(world, new GT_MetaTileEntity_Hatch_Energy("frame-base", 1, "", new ITexture[0][][]), ForgeDirection.NORTH) {
                        @Override public IMetaTileEntity getMetaTileEntity() { return actualFrame; }
                    };
                    world.putTile(10, 64, 10, tile);
                    assertEquals(candidate == material, frame.check(machine, world, 10, 64, 10));
                }
            }
        } finally {
            GregTech_API.sBlockMachines = previous;
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static void checkTankCasings() {
        for (boolean multi : new boolean[] { false, true }) {
            GT_MetaTileEntity_EnhancedMultiBlockBase machine = multi
                    ? new com.impact.mods.gregtech.tileentities.multi.storage.GTMTE_MultiTank("tank-casing-tier")
                    : new com.impact.mods.gregtech.tileentities.multi.storage.GTMTE_SingleTank("tank-casing-tier");
            TestWorld world = new TestWorld();
            for (char symbol : multi ? new char[] { 'A', 'E', 'M' } : new char[] { 'A', 'E' }) {
                IStructureElement casing = (IStructureElement) ((StructureDefinition) machine.getStructureDefinition()).getElements().get(symbol);
                for (int metadata = 0; metadata < 16; metadata++) {
                    world.setBlock(20, 64, 20, gregtech.api.enums.Casings.CHEMICALLY_INERT.getBlock(), metadata, 0);
                    assertEquals(metadata == gregtech.api.enums.Casings.CHEMICALLY_INERT.getMetadata(),
                            casing.check(machine, world, 20, 64, 20), "Tank casing " + symbol + " metadata=" + metadata);
                }
            }
        }
    }

    private static void checkTurbineRotor() throws Exception {
        for (ForgeDirection direction : new ForgeDirection[] { ForgeDirection.NORTH, ForgeDirection.SOUTH, ForgeDirection.WEST, ForgeDirection.EAST }) {
            GTMTE_HugeSteamTurbine turbine = new GTMTE_HugeSteamTurbine("safe-turbine-rotor");
            TestWorld world = new TestWorld();
            world.putTile(0, 64, 0, new TestTile(world, turbine, direction));
            turbine.construct(new ItemStack(Items.stick), false);
            int[] service = position(turbine, 'C', 2, 2, 0);
            world.putTile(service[0], service[1], service[2], new TestTile(world,
                    new GT_MetaTileEntity_Hatch_Maintenance("rotor-maintenance", 1, "", new ITexture[0][][], false), direction));
            assertTrue(recheck(turbine));
            int[] rotor = position(turbine, 'R', 2, 2, 0);
            assertSame(ItemRegistery.InsideBlock, world.getBlock(rotor[0], rotor[1], rotor[2]));
            world.setBlock(rotor[0], rotor[1], rotor[2], Blocks.bedrock, 0, 0);
            int changes = world.changes;
            assertFalse(recheck(turbine), "Obstructed rotor must invalidate the turbine");
            Method update = GTMTE_HugeSteamTurbine.class.getDeclaredMethod("rotorTopTrigger", boolean.class);
            update.setAccessible(true);
            update.invoke(turbine, true);
            assertEquals(changes, world.changes, "Neither validation nor an effect update may destroy the obstruction");
            assertSame(Blocks.bedrock, world.getBlock(rotor[0], rotor[1], rotor[2]));
            world.setBlock(rotor[0], rotor[1], rotor[2], Blocks.air, 0, 0);
            assertTrue(recheck(turbine));
            update.invoke(turbine, true);
            assertEquals(3, world.getBlockMetadata(rotor[0], rotor[1], rotor[2]));
            assertTrue(recheck(turbine), "The turbine accepts its own rotor effect");
        }
    }
}
