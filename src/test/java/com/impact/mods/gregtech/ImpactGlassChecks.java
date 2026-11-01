package com.impact.mods.gregtech;

import com.impact.loader.ItemRegistery;
import com.impact.mods.gregtech.blocks.ImpactCasings;
import com.impact.mods.gregtech.tileentities.multi.generators.sofc.GTMTE_SOFC_I;
import com.impact.mods.gregtech.tileentities.multi.generators.sofc.GTMTE_SOFC_II;
import com.impact.mods.gregtech.tileentities.multi.generators.sofc.GTMTE_SOFC_III;
import com.impact.mods.gregtech.tileentities.multi.storage.GTMTE_LapPowerStation;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_EnhancedMultiBlockBase;
import ic2.core.Ic2Items;
import java.lang.reflect.Field;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import space.impact.structures.structure.IStructureElement;
import space.impact.structures.structure.ISurvivalBuildEnvironment;
import space.impact.structures.structure.StructureDefinition;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Glass checks run inside the existing headless Forge fixture. */
public final class ImpactGlassChecks {
    private static final String[] ANY_COLOR = {
            "processing.parallel.GTMTE_Assembler:G", "processing.parallel.GTMTE_Brewmenter:G",
            "processing.parallel.GTMTE_Centrifuge:G", "processing.parallel.GTMTE_Cutting:G",
            "processing.parallel.GTMTE_Electrolyzer:G", "processing.parallel.GTMTE_Extradifier:G",
            "processing.parallel.GTMTE_HeavyMetalCyclone:G", "processing.parallel.GTMTE_Mixer:G",
            "processing.parallel.GTMTE_MultiChemicalReactor:G", "processing.parallel.GTMTE_Supply:G",
            "processing.parallel.GTMTE_Utility:G", "processing.parallel.GTMTE_Wire:G",
            "processing.defaultmachines.GTMTE_DDDPrinter:G", "processing.defaultmachines.GTMTE_AdvDDDPrinter:G",
            "matrixsystem.GTMTE_ParametricDiffuser:G", "matrixsystem.GTMTE_MPContainment:G",
            "matrixsystem.GTMTE_MPStabilizer:G", "matrixsystem.GTMTEMESystemProvider:D",
            "storage.GTMTE_SingleTank:G", "storage.GTMTE_MultiTank:G",
            "generators.nq.GTMTE_LiquidNqGenerator:G", "generators.nq.GTMTE_LiquidEnrichedNqGenerator:B"
    };

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void check() throws Exception {
        for (String entry : ANY_COLOR) {
            String[] parts = entry.split(":");
            GT_MetaTileEntity_EnhancedMultiBlockBase machine = machine(parts[0]);
            IStructureElement element = element(machine, parts[1].charAt(0));
            ImpactConstructionChecks.TestWorld world = new ImpactConstructionChecks.TestWorld();
            IStructureElement.BlocksToPlace candidates = element.getBlocksToPlace(machine, world, 2, 65, 0, null, null);
            for (int color = 0; color < 16; color++) {
                world.setBlock(2, 65, 0, ItemRegistery.IGlassBlock, color, 0);
                assertTrue(element.check(machine, world, 2, 65, 0), entry + " color=" + color);
                assertTrue(candidates.getPredicate().test(new ItemStack(ItemRegistery.IGlassBlock, 1, color)), entry);
                if (candidates.getStacks() != null) {
                    boolean listed = false;
                    for (ItemStack stack : candidates.getStacks()) listed |= stack.getItemDamage() == color;
                    assertTrue(listed, entry + " missing placement variant=" + color);
                }
            }
            assertFalse(candidates.getPredicate().test(new ItemStack(Blocks.dirt)), entry);
        }
        GT_MetaTileEntity_EnhancedMultiBlockBase laser = machine("processing.parallel.GTMTE_LaserEng");
        String keys = "DBEF";
        int[] colors = { 0, 11, 13, 14 };
        for (int i = 0; i < keys.length(); i++) {
            IStructureElement.BlocksToPlace candidates = element(laser, keys.charAt(i))
                    .getBlocksToPlace(laser, null, 0, 0, 0, null, null);
            for (int color = 0; color < 16; color++) {
                assertEquals(color == colors[i], candidates.getPredicate()
                        .test(new ItemStack(ItemRegistery.IGlassBlock, 1, color)));
            }
        }
        GT_MetaTileEntity_EnhancedMultiBlockBase[] reinforced = {
                machine("units.GTMTE_TinyWormHole"), machine("processing.defaultmachines.GTMTE_Farm"),
                new GTMTE_SOFC_I("glass-sofc-i", 0), new GTMTE_SOFC_II("glass-sofc-ii", 1),
                new GTMTE_SOFC_III("glass-sofc-iii", 2)
        };
        for (int i = 0; i < reinforced.length; i++) {
            IStructureElement.BlocksToPlace candidates = element(reinforced[i], i == 0 ? 'A' : 'G')
                    .getBlocksToPlace(reinforced[i], null, 0, 0, 0, null, null);
            assertTrue(candidates.getPredicate().test(Ic2Items.reinforcedGlass));
            assertFalse(candidates.getPredicate().test(new ItemStack(Blocks.dirt)));
        }
        checkColoredCapacitors();
    }

    private static GT_MetaTileEntity_EnhancedMultiBlockBase<?> machine(String name) throws Exception {
        return (GT_MetaTileEntity_EnhancedMultiBlockBase<?>) Class.forName(
                "com.impact.mods.gregtech.tileentities.multi." + name).getConstructor(String.class).newInstance("glass-test");
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static IStructureElement element(GT_MetaTileEntity_EnhancedMultiBlockBase machine, char key) {
        return (IStructureElement) ((StructureDefinition) machine.getStructureDefinition()).getElements().get(key);
    }

    private static void checkColoredCapacitors() throws Exception {
        Block originalGlass = ItemRegistery.IGlassBlock;
        Field structure = GTMTE_LapPowerStation.class.getDeclaredField("STRUCTURE");
        structure.setAccessible(true);
        Object originalStructure = structure.get(null);
        // A distinct vanilla fixture whose ItemBlock preserves all 16 metadata values.
        ItemRegistery.IGlassBlock = Blocks.carpet;
        structure.set(null, null);
        try {
            for (ForgeDirection facing : new ForgeDirection[] { ForgeDirection.NORTH, ForgeDirection.SOUTH,
                    ForgeDirection.WEST, ForgeDirection.EAST }) {
                for (int color = 0; color < 16; color++) {
                    ImpactConstructionChecks.TestWorld world = new ImpactConstructionChecks.TestWorld();
                    GTMTE_LapPowerStation machine = new GTMTE_LapPowerStation("colored-capacitor");
                    ImpactConstructionChecks.TestTile tile = new ImpactConstructionChecks.TestTile(world, machine, facing);
                    world.putTile(0, 64, 0, tile);
                    ImpactConstructionChecks.TestPlayer player = new ImpactConstructionChecks.TestPlayer(world);
                    ItemStack glass = new ItemStack(ItemRegistery.IGlassBlock, 200, color);
                    ImpactConstructionChecks.Source source = new ImpactConstructionChecks.Source(
                            new ItemStack(ImpactCasings.LSCC_CASING.getBlock(), 200, ImpactCasings.LSCC_CASING.getMetadata()),
                            glass, new ItemStack(ItemRegistery.lscLapotronicEnergyUnit, 200, 8));
                    ItemStack trigger = new ItemStack(Items.stick, 2);
                    ISurvivalBuildEnvironment environment = ISurvivalBuildEnvironment.create(source, player);
                    for (int pass = 0; pass < 100 && !machine.checkMachine(tile, null); pass++) {
                        int before = source.consumed;
                        int built = machine.survivalConstruct(trigger, 3, environment);
                        assertTrue(built >= 0 && built <= 3);
                        assertEquals(built, source.consumed - before);
                    }
                    assertTrue(machine.checkMachine(tile, null), "color=" + color + " facing=" + facing);
                    int placedGlass = 0;
                    for (String position : world.blocks.keySet()) {
                        if (world.blocks.get(position) == ItemRegistery.IGlassBlock) {
                            assertEquals(color, world.metadata.get(position).intValue());
                            placedGlass++;
                        }
                    }
                    assertEquals(51, placedGlass);
                    assertEquals(200 - placedGlass, glass.stackSize);
                }
            }
            checkInsufficientChosenColor();
        } finally {
            ItemRegistery.IGlassBlock = originalGlass;
            structure.set(null, originalStructure);
        }
    }

    private static void checkInsufficientChosenColor() {
        ImpactConstructionChecks.TestWorld world = new ImpactConstructionChecks.TestWorld();
        GTMTE_LapPowerStation machine = new GTMTE_LapPowerStation("mixed-color-capacitor");
        ImpactConstructionChecks.TestTile tile = new ImpactConstructionChecks.TestTile(world, machine, ForgeDirection.NORTH);
        world.putTile(0, 64, 0, tile);
        ItemStack orange = new ItemStack(ItemRegistery.IGlassBlock, 1, 1);
        ItemStack white = new ItemStack(ItemRegistery.IGlassBlock, 200, 0);
        ImpactConstructionChecks.Source source = new ImpactConstructionChecks.Source(
                new ItemStack(ImpactCasings.LSCC_CASING.getBlock(), 200, ImpactCasings.LSCC_CASING.getMetadata()),
                orange, white, new ItemStack(ItemRegistery.lscLapotronicEnergyUnit, 200, 8));
        ISurvivalBuildEnvironment environment = ISurvivalBuildEnvironment.create(source,
                new ImpactConstructionChecks.TestPlayer(world));
        ItemStack trigger = new ItemStack(Items.stick, 2);
        for (int pass = 0; pass < 30; pass++) machine.survivalConstruct(trigger, 3, environment);
        assertFalse(machine.checkMachine(tile, null));
        assertEquals(200, white.stackSize, "Do not mix colors when the selected glass runs out");
        orange.stackSize = 100;
        for (int pass = 0; pass < 100 && !machine.checkMachine(tile, null); pass++) {
            machine.survivalConstruct(trigger, 3, environment);
        }
        assertTrue(machine.checkMachine(tile, null));
        assertEquals(200, white.stackSize);
    }
}
