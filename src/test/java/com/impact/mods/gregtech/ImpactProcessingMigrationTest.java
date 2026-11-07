package com.impact.mods.gregtech;

import com.impact.addon.gt.api.recipe.ContinuousFluidOutputs;
import com.impact.addon.gt.api.recipe.StagedFluidOutputs;
import com.impact.core.Config;
import com.impact.mods.gregtech.tileentities.hatches.lasers.GTMTE_LaserEnergy_In;
import com.impact.mods.gregtech.tileentities.multi.generators.nq.GTMTE_HyperNaquadahGenerator;
import com.impact.mods.gregtech.tileentities.multi.generators.nq.GTMTE_LiquidEnrichedNqGenerator;
import com.impact.mods.gregtech.tileentities.multi.generators.nq.GTMTE_LiquidNqGenerator;
import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_Impact_BlockBase;
import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_Impact_FuelGeneratorBase;
import com.impact.mods.gregtech.tileentities.multi.matrixsystem.GTMTEMESystemProvider;
import com.impact.mods.gregtech.tileentities.multi.parallelsystem.GTMTE_ParallelHatch_Input;
import com.impact.mods.gregtech.tileentities.multi.processing.parallel.GTMTE_AdvancedPyrolyse;
import com.impact.mods.gregtech.tileentities.multi.processing.parallel.GTMTE_Brewmenter;
import com.impact.mods.gregtech.tileentities.multi.processing.parallel.GTMTE_Mixer;
import com.impact.mods.gregtech.tileentities.multi.ores.GTMTE_Mining_Coal;
import com.impact.mods.gregtech.tileentities.multi.units.GTMTE_AntimatterReactor;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.IFMLSidedHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.relauncher.Side;
import gregtech.GT_Mod;
import gregtech.api.GregTech_API;
import gregtech.api.enums.GT_Values;
import gregtech.api.interfaces.ITexture;
import gregtech.api.logic.IProcessingOutputSink;
import gregtech.api.logic.ProcessingInputs;
import gregtech.api.logic.ProcessingLogic;
import gregtech.api.logic.ProcessingOutputAccess;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.implementations.GTMTE_BusHatch_Input;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Energy;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_EnergyMulti;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Input;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_InputBus;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Output;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_OutputBus;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_OutputBus_ME;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Output_ME;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_ProcessingMultiBlockBase;
import gregtech.api.recipe.check.CheckRecipeResult;
import gregtech.api.util.GT_Config;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import gregtech.api.util.OverclockCalculator;
import gregtech.common.GT_Server;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import net.minecraft.init.Bootstrap;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.launchwrapper.LaunchClassLoader;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry.FluidContainerData;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ImpactProcessingMigrationTest {

    private LaunchClassLoader loader;
    private Class<?> scenarios;

    @BeforeAll
    void initialize() throws Exception {
        loader = new LaunchClassLoader(((URLClassLoader) getClass().getClassLoader()).getURLs());
        loader.addClassLoaderExclusion("org.junit.");
        scenarios = loader.loadClass(getClass().getName() + "$Scenarios");
        scenarios.getMethod("initialize").invoke(null);
    }

    @AfterAll
    void closeLoader() throws Exception {
        if (loader != null) {
            loader.close();
        }
    }

    private void run(String scenario) throws Throwable {
        try {
            scenarios.getMethod("resetRecipes").invoke(null);
            scenarios.getMethod(scenario).invoke(null);
        } catch (InvocationTargetException failure) {
            throw failure.getTargetException();
        }
    }

    @Test
    void cachedRecipeUsesCurrentParallelConnectionAndSpeedBonus() throws Throwable {
        run("parallelTransitions");
    }

    @Test
    void disconnectedParallelHatchRejectsWithoutSpendingInputs() throws Throwable {
        run("disconnectedHatch");
    }

    @Test
    void modesInheritGtAndOldSeparationIsMigratedOnlyOnce() throws Throwable {
        run("modesMigration");
    }

    @Test
    void oldActiveCycleKeepsItsSavedParallelCountAndOutputs() throws Throwable {
        run("legacyCycle");
    }

    @Test
    void stagedFractionsSurviveReloadAndBlockedOutputsWithoutDuplication() throws Throwable {
        run("stagedOutputs");
    }

    @Test
    void legacyPyrolysisDoesNotInventMissingFractions() throws Throwable {
        run("legacyPyrolysis");
    }

    @Test
    void parallelAndBatchMatrixPreservesInputsOutputsPowerAndDuration() throws Throwable {
        run("parallelMatrix");
    }

    @Test
    void ordinaryMultiAmpAndLaserHatchesNeverRaiseAdmissionTier() throws Throwable {
        run("powerMatrix");
    }

    @Test
    void machineFailuresNeverConsumeInputs() throws Throwable {
        run("failureMatrix");
    }

    @Test
    void lostNetworkDoesNotChangeActiveCycleAndBlocksRequiredNextStart() throws Throwable {
        run("networkLoss");
    }

    @Test
    void partiallyDeliveredParallelOutputsSurviveRepeatedReloadsAndStructureLoss() throws Throwable {
        run("pendingParallelOutputs");
    }

    @Test
    void subTickBypassAndPerfectOverclockAreExplicitMachineSettings() throws Throwable {
        run("subTickMatrix");
    }

    @Test
    void matrixParticlesLimitParallelAndBatchAndAreNeverSpentOnRejectedStart() throws Throwable {
        run("matrixParticles");
    }

    @Test
    void specialResourceChangeImmediatelyBeforeCommitRejectsAllConsumption() throws Throwable {
        run("matrixParticlesChanged");
    }

    @Test
    void advancedPyrolysisKeepsItsOwnTierBasedParallelSource() throws Throwable {
        run("pyrolysisParallelSource");
    }

    @Test
    void mixedEnergyHatchesKeepAverageVoltageAndIndependentAmpBudget() throws Throwable {
        run("mixedPower");
    }

    @Test
    void zeroChanceAndGuaranteedOutputPoliciesRemainDistinct() throws Throwable {
        run("outputChancePolicy");
    }

    @Test
    void fluidAndItemLimitsConstrainParallelAndBatchWithoutLosingRemainders() throws Throwable {
        run("fluidParallelMatrix");
    }

    @Test
    void protectedFluidRemaindersSurviveReloadAndModeChanges() throws Throwable {
        run("pendingParallelFluids");
    }

    @Test
    void busSeparationNeverCombinesDifferentBusesAndKeepsCombinedHatchInputs() throws Throwable {
        run("separationMatrix");
    }

    @Test
    void debugAndSatelliteConnectionsCannotBypassRequiredNetworkGate() throws Throwable {
        run("connectionMatrix");
    }

    @Test
    void activeMapAndRecipeLockFollowTheSelectedMapAcrossReload() throws Throwable {
        run("recipeMapSwitching");
    }

    @Test
    void legacyRecipeMapSurvivesLoadingBeforeWorldAttachment() throws Throwable {
        run("legacyRecipeMap");
    }

    @Test
    void continuousProductsSurvivePartialDeliveryReloadAndCycleCompletion() throws Throwable {
        run("continuousOutputs");
    }

    @Test
    void unprotectedContinuousProductsAreVoidedWithoutVoidingEarlierProtectedProducts() throws Throwable {
        run("continuousProtectionChange");
    }

    @Test
    void continuousFluidAmountsAboveIntegerRangeRemainSeparateAndPersist() throws Throwable {
        run("continuousOverflow");
    }

    @Test
    void meCapacityLossDuringParallelCycleRetainsItemsAndFluids() throws Throwable {
        run("mePendingOutputs");
    }

    @Test
    void cachedRecipeRechecksCleanroomAndPreservesInputsOnFailure() throws Throwable {
        run("cachedConditions");
    }

    @Test
    void batchBoundaryMatrixHonorsDurationAndNeverIncreasesSimultaneousParallel() throws Throwable {
        run("batchBoundaries");
    }

    @Test
    void subTickParallelAndBatchComposeWithoutDoubleChargingPower() throws Throwable {
        run("subTickBatchMatrix");
    }

    @Test
    void combustionFinishesAgainstFullOutputsAndRetainsProductsAcrossReload() throws Throwable {
        run("antimatterBurn");
    }

    @Test
    void naquadahGeneratorsKeepWarmupFuelConsumptionAndRunningSave() throws Throwable {
        run("naquadahWarmup");
    }

    @Test
    void coalMinerKeepsPenultimateOutputAndNeverDuplicatesItAfterReload() throws Throwable {
        run("coalIntermediateOutput");
    }

    @Test
    void everyTierAndEnergyHatchUsesTheCurrentGregTechOverclockCalculator() throws Throwable {
        run("gregTechOverclockMatrix");
    }

    public static final class Scenarios {

        public static void initialize() throws Exception {
            Loader.injectData("7", "10", "99", "99", "1.7.10", "9.05", new File("."), Collections.emptyList());
            Bootstrap.func_151354_b();
            Class<?> factoryClass = Class.forName("sun.reflect.ReflectionFactory");
            Object factory = factoryClass.getMethod("getReflectionFactory").invoke(null);
            Constructor<?> constructor = (Constructor<?>) factoryClass
                    .getMethod("newConstructorForSerialization", Class.class, Constructor.class)
                    .invoke(factory, GT_Server.class, Object.class.getDeclaredConstructor());
            constructor.setAccessible(true);
            GT_Mod.gregtechproxy = (GT_Server) constructor.newInstance();
            Field delegate = FMLCommonHandler.class.getDeclaredField("sidedDelegate");
            delegate.setAccessible(true);
            delegate.set(FMLCommonHandler.instance(), Proxy.newProxyInstance(IFMLSidedHandler.class.getClassLoader(),
                    new Class<?>[] { IFMLSidedHandler.class }, (proxy, method, arguments) -> {
                        if (method.getName().equals("getSide")) {
                            return Side.SERVER;
                        }
                        if (method.getName().equals("getCurrentLanguage")) {
                            return "en_US";
                        }
                        return null;
                    }));
            Config.MAX_TICK_RATE = 2;
            GregTech_API.sPostloadFinished = true;
            GregTech_API.sMachineFile = new GT_Config(new Configuration() {
                @Override
                public void load() {
                }

                @Override
                public void save() {
                }
            });
        }

        public static void resetRecipes() {
            // One Forge runtime per class; each scenario owns fresh maps and controller instances.
            GT_RecipeMaps.sMESystemProvider.clear();
            for (GT_Recipe.GT_Recipe_Map map : GT_Recipe.GT_Recipe_Map.sMappings) {
                map.clear();
            }
            Config.MAX_TICK_RATE = 2;
        }

        public static void coalIntermediateOutput() throws Exception {
            for (boolean full : new boolean[] { false, true }) {
                GTMTE_Mining_Coal machine = new GTMTE_Mining_Coal("coal");
                attach(machine);
                machine.mMachine = true;
                machine.mStartUpCheck = -1;
                machine.mEfficiency = 10000;
                machine.cBurnTime = 100;
                machine.mMaxProgresstime = 400;
                machine.mProgresstime = 398;
                machine.mOutputItems = new ItemStack[] { new ItemStack(Items.coal) };
                machine.mInventory[1] = full ? new ItemStack(Items.coal, 64) : null;
                machine.onPostTick(machine.getBaseMetaTileEntity(), 101);
                assertEquals(399, machine.mProgresstime);
                assertEquals(full ? 64 : 1, machine.mInventory[1].stackSize);
                assertEquals(full ? 1 : 0, machine.mOutputItems[0].stackSize);
                NBTTagCompound saved = new NBTTagCompound();
                machine.saveNBTData(saved);
                GTMTE_Mining_Coal restored = new GTMTE_Mining_Coal("coal-restored");
                attach(restored);
                restored.loadNBTData(saved);
                restored.mInventory[1] = machine.mInventory[1];
                finish(restored);
                assertEquals(full, restored.isWaitingForOutput());
                if (full) {
                    restored.mInventory[1] = null;
                    finish(restored);
                }
                assertEquals(1, restored.mInventory[1].stackSize);
                assertFalse(restored.isWaitingForOutput());
            }
        }

        public static void fluidParallelMatrix() {
            fluidRecipe();
            for (int limit : new int[] { 1, 16, 64, 256 }) {
                for (boolean batch : new boolean[] { false, true }) {
                    for (int fluidOperations : new int[] { 0, 1, limit - 1, limit, limit * 4 }) {
                        TestMixer machine = machine();
                        machine.amperage = 256;
                        GTMTE_ParallelHatch_Input parallel = hatch(machine);
                        parallel.isDebug = true;
                        parallel.mMaxParallel = limit;
                        machine.setProcessingModes(batch ? ProcessingLogic.BATCH : 0);
                        ItemStack input = new ItemStack(Items.iron_ingot, limit * 4);
                        machine.inputs.add(input);
                        GT_MetaTileEntity_Hatch_Input fluid = fluidInput(machine, fluidOperations * 100 + 17);
                        String context = "limit=" + limit + ", batch=" + batch + ", fluidOps=" + fluidOperations;
                        if (fluidOperations == 0) {
                            assertFalse(machine.checkRecipe(null), context);
                            assertEquals(limit * 4, input.stackSize, context);
                            assertEquals(17, fluid.mFluid.amount, context);
                            continue;
                        }
                        int parallelCount = Math.min(limit, fluidOperations);
                        int duration = limit == 256 ? 20 : limit == 64 ? 26 : limit == 16 ? 40 : 80;
                        int operations = batch ? Math.min(fluidOperations, parallelCount * 128 / duration)
                                : parallelCount;
                        assertTrue(machine.checkRecipe(null), context);
                        assertEquals(parallelCount, machine.getCurrentParallel(), context);
                        assertEquals(operations, machine.getCurrentOperations(), context);
                        assertEquals(limit * 4 - operations, input.stackSize, context);
                        assertEquals((fluidOperations - operations) * 100 + 17, fluid.mFluid.amount, context);
                        assertEquals(operations, machine.mOutputItems[0].stackSize, context);
                        assertEquals(operations * 40, machine.mOutputFluids[0].amount, context);
                    }
                }
            }
        }

        public static void pendingParallelFluids() {
            fluidRecipe();
            for (int limit : new int[] { 1, 16, 64, 256 }) {
                TestMixer machine = machine();
                machine.amperage = 256;
                GTMTE_ParallelHatch_Input parallel = hatch(machine);
                parallel.isDebug = true;
                parallel.mMaxParallel = limit;
                ItemStack input = new ItemStack(Items.iron_ingot, limit * 2);
                machine.inputs.add(input);
                GT_MetaTileEntity_Hatch_Input source = fluidInput(machine, limit * 200);
                GT_MetaTileEntity_Hatch_Output output = fluidOutput(machine);
                machine.setProcessingModes(ProcessingLogic.PROTECT_FLUIDS);
                assertTrue(machine.checkRecipe(null));
                int produced = machine.getCurrentOperations() * 40;
                output.mFluid = new FluidStack(FluidRegistry.LAVA, output.getCapacity() - 1);
                machine.finish();
                assertTrue(machine.isWaitingForOutput());
                assertEquals(output.getCapacity(), output.mFluid.amount);
                assertEquals(produced - 1, machine.mOutputFluids[0].amount);
                for (int reload = 0; reload < 3; reload++) {
                    NBTTagCompound saved = new NBTTagCompound();
                    machine.saveNBTData(saved);
                    machine = machine();
                    machine.loadNBTData(saved);
                    machine.mOutputHatches.add(output);
                    machine.setProcessingModes(0);
                    machine.stopMachine();
                    machine.finish();
                    assertTrue(machine.isWaitingForOutput());
                    assertFalse(machine.checkRecipe(null));
                    assertEquals(produced - 1, machine.mOutputFluids[0].amount);
                }
                output.mFluid = null;
                machine.finish();
                assertFalse(machine.isWaitingForOutput());
                assertEquals(produced - 1, output.mFluid.amount);
                machine.finish();
                assertEquals(produced - 1, output.mFluid.amount);
                assertEquals(limit, input.stackSize);
                assertEquals(limit * 100, source.mFluid.amount);
            }
        }

        public static void separationMatrix() {
            GT_Recipe recipe = machine().getRecipeMap().addRecipe(false,
                    new ItemStack[] { new ItemStack(Items.iron_ingot), new ItemStack(Items.sugar) },
                    new ItemStack[] { new ItemStack(Items.gold_ingot) }, null, new int[] { 10000 },
                    new FluidStack[] { water(10) }, null, 80, 24, 0);
            assertTrue(recipe != null);
            for (boolean combined : new boolean[] { false, true }) {
                for (boolean separated : new boolean[] { false, true }) {
                    for (int limit : new int[] { 1, 16, 64, 256 }) {
                        TestMixer machine = machine();
                        machine.realInputs = true;
                        machine.amperage = 256;
                        GTMTE_ParallelHatch_Input parallel = hatch(machine);
                        parallel.isDebug = true;
                        parallel.mMaxParallel = limit;
                        machine.setProcessingModes(separated ? ProcessingLogic.SEPARATE_INPUTS : 0);
                        GT_MetaTileEntity_Hatch_InputBus first = addInput(machine, limit * 2);
                        ItemStack iron = first.mInventory[0];
                        ItemStack sugar = new ItemStack(Items.sugar, limit * 2);
                        ItemStack[] secondInventory;
                        int sugarSlot;
                        if (combined) {
                            GTMTE_BusHatch_Input second = new GTMTE_BusHatch_Input(
                                    "combined", 1, new String[0], new ITexture[0][][], 32000);
                            attach(second);
                            machine.mInputBusHatches.add(second);
                            secondInventory = second.mInventory;
                            sugarSlot = 3;
                        } else {
                            GT_MetaTileEntity_Hatch_InputBus second = addInput(machine, 0);
                            secondInventory = second.mInventory;
                            sugarSlot = 0;
                        }
                        secondInventory[sugarSlot] = sugar;
                        GT_MetaTileEntity_Hatch_Input fluid = fluidInput(machine, limit * 20);
                        assertEquals(!separated, machine.checkRecipe(null));
                        if (separated) {
                            assertEquals(limit * 2, iron.stackSize);
                            assertEquals(limit * 2, sugar.stackSize);
                            assertEquals(limit * 20, fluid.mFluid.amount);
                            first.mInventory[1] = sugar;
                            secondInventory[sugarSlot] = null;
                            assertTrue(machine.checkRecipe(null));
                        }
                        assertEquals(limit, machine.getCurrentParallel());
                        assertEquals(limit, iron.stackSize);
                        int remainingSugar = 0;
                        for (ItemStack stack : machine.getStoredInputs()) {
                            if (stack.getItem() == Items.sugar) {
                                remainingSugar += stack.stackSize;
                            }
                        }
                        assertEquals(limit, remainingSugar, "combined=" + combined + ", separated=" + separated);
                        assertEquals(limit * 10, fluid.mFluid.amount);
                    }
                }
            }
        }

        public static void connectionMatrix() {
            recipe(machine());
            for (boolean required : new boolean[] { false, true }) {
                for (boolean satellite : new boolean[] { false, true }) {
                    for (boolean connected : new boolean[] { false, true }) {
                        for (boolean debug : new boolean[] { false, true }) {
                            TestMixer machine = machine();
                            machine.requireNetwork = required;
                            machine.setSatelliteConnected(satellite);
                            GTMTE_ParallelHatch_Input parallel = hatch(machine);
                            parallel.isConnected = connected;
                            parallel.isDebug = debug;
                            ItemStack input = new ItemStack(Items.iron_ingot, 64);
                            machine.inputs.add(input);
                            boolean success = (!required || satellite) && (connected || debug);
                            String context = "required=" + required + ", satellite=" + satellite
                                    + ", connected=" + connected + ", debug=" + debug;
                            assertEquals(success, machine.checkRecipe(null), context);
                            int operations = success ? debug || satellite ? 16 : 1 : 0;
                            assertEquals(operations, machine.getCurrentOperations(), context);
                            assertEquals(64 - operations, input.stackSize, context);
                        }
                    }
                }
            }
        }

        public static void recipeMapSwitching() throws Exception {
            GTMTE_Brewmenter machine = new GTMTE_Brewmenter("maps");
            attach(machine);
            addLaser(machine, 1, 256);
            GT_MetaTileEntity_Hatch_Input fluid = fluidInput(machine, 10000);
            GT_MetaTileEntity_Hatch_InputBus bus = addInput(machine, 4);
            GT_Recipe.GT_Recipe_Map brewing = GT_Recipe.GT_Recipe_Map.sBrewingRecipes;
            GT_Recipe.GT_Recipe_Map fermenting = GT_Recipe.GT_Recipe_Map.sFermentingRecipes;
            for (GT_Recipe.GT_Recipe_Map map : Arrays.asList(brewing, fermenting)) {
                assertTrue(map.addRecipe(false, new ItemStack[] { new ItemStack(Items.iron_ingot) },
                        null, null, null, new FluidStack[] { water(100) },
                        new FluidStack[] { new FluidStack(FluidRegistry.LAVA, map == brewing ? 10 : 20) },
                        80, 24, 0) != null);
            }
            machine.setProcessingModes(ProcessingLogic.LOCK_RECIPE);
            assertEquals(fermenting, machine.getRecipeMap());
            assertTrue(machine.checkRecipe(null));
            assertEquals(20, machine.mOutputFluids[0].amount);
            assertTrue(machine.setActiveRecipeMap(brewing));
            assertEquals(brewing, machine.getRecipeMapForNEI());
            assertFalse(machine.checkRecipe(null));
            assertEquals(20, machine.mOutputFluids[0].amount);
            NBTTagCompound saved = new NBTTagCompound();
            machine.saveNBTData(saved);
            GTMTE_Brewmenter restored = new GTMTE_Brewmenter("maps-restored");
            attach(restored);
            addLaser(restored, 1, 256);
            restored.mInputHatches.add(fluid);
            restored.mInputBusses.add(bus);
            restored.loadNBTData(saved);
            assertEquals(brewing, restored.getRecipeMap());
            assertEquals(20, restored.mOutputFluids[0].amount);
            finish(restored);
            assertTrue(restored.checkRecipe(null), restored.getProcessingResult().toString());
            assertEquals(10, restored.mOutputFluids[0].amount);
            finish(restored);
            assertTrue(restored.setActiveRecipeMap(fermenting));
            assertTrue(restored.checkRecipe(null));
            assertEquals(20, restored.mOutputFluids[0].amount);
            assertEquals(9700, fluid.mFluid.amount);
            assertEquals(1, bus.mInventory[0].stackSize);
        }

        public static void legacyRecipeMap() {
            GTMTE_Brewmenter machine = new GTMTE_Brewmenter("legacy-maps") {
                @Override
                public void createConnect() {
                }
            };
            ImpactConstructionChecks.TestTile tile =
                    new ImpactConstructionChecks.TestTile(null, machine, ForgeDirection.NORTH);
            GT_Recipe.GT_Recipe_Map brewing = machine.getRecipesMap().get(0);
            GT_Recipe.GT_Recipe_Map fermenting = machine.getRecipesMap().get(1);
            NBTTagCompound legacy = new NBTTagCompound();
            legacy.setByte("mRecipeMode", (byte) 0);
            legacy.setBoolean("isSeparated", true);

            machine.loadNBTData(legacy);
            assertEquals(fermenting, machine.getRecipeMap());
            assertEquals(ProcessingLogic.SEPARATE_INPUTS, machine.getProcessingModes());
            NBTTagCompound saved = new NBTTagCompound();
            machine.saveNBTData(saved);
            assertEquals(brewing.mUnlocalizedName, saved.getCompoundTag("processing").getString("recipeMap"));

            tile.setWorldObj(new ImpactConstructionChecks.TestWorld());
            machine.onFirstTick(tile);
            assertEquals(brewing, machine.getRecipeMap());
            assertTrue(machine.setActiveRecipeMap(fermenting));
            machine.saveNBTData(saved);
            assertEquals(fermenting.mUnlocalizedName, saved.getCompoundTag("processing").getString("recipeMap"));
        }

        public static void continuousOutputs() {
            ContinuousFluidOutputs outputs = new ContinuousFluidOutputs();
            Tank sink = new Tank(37);
            FluidStack product = water(100);
            outputs.emit(product, true, sink);
            assertEquals(100, product.amount);
            assertEquals(37, sink.received);
            for (int tick = 1; tick < 20; tick++) {
                NBTTagCompound saved = new NBTTagCompound();
                outputs.save(saved);
                outputs = new ContinuousFluidOutputs();
                outputs.load(saved);
                outputs.emit(product, true, sink);
            }
            FluidStack[] remaining = outputs.finish(new FluidStack[] { water(100) });
            assertEquals(2100 - 37, totalFluids(remaining));
            assertTrue(outputs.isEmpty());
            sink.capacity = 10000;
            assertTrue(sink.deliver(null, remaining, false, true));
            assertEquals(2100, sink.received);
            assertTrue(outputs.flush(sink));
            assertEquals(2100, sink.received);
        }

        public static void continuousProtectionChange() {
            ContinuousFluidOutputs outputs = new ContinuousFluidOutputs();
            Tank sink = new Tank(0);
            outputs.emit(water(100), true, sink);
            outputs.emit(water(200), false, sink);
            NBTTagCompound saved = new NBTTagCompound();
            outputs.save(saved);
            outputs = new ContinuousFluidOutputs();
            outputs.load(saved);
            sink.capacity = 1000;
            assertTrue(outputs.flush(sink));
            assertEquals(100, sink.received);
            outputs.emit(water(200), false, sink);
            assertEquals(300, sink.received);
            assertTrue(outputs.isEmpty());
        }

        public static void continuousOverflow() {
            ContinuousFluidOutputs outputs = new ContinuousFluidOutputs();
            Tank sink = new Tank(0);
            outputs.emit(water(Integer.MAX_VALUE), true, sink);
            outputs.emit(water(27), true, sink);
            NBTTagCompound saved = new NBTTagCompound();
            outputs.save(saved);
            outputs = new ContinuousFluidOutputs();
            outputs.load(saved);
            FluidStack[] remaining = outputs.finish(null);
            assertEquals(2, remaining.length);
            assertEquals((long) Integer.MAX_VALUE + 27, totalFluids(remaining));
            assertEquals(Integer.MAX_VALUE, remaining[0].amount);
            assertEquals(27, remaining[1].amount);
        }

        public static void mePendingOutputs() {
            fluidRecipe();
            TestMixer machine = machine();
            hatch(machine).isDebug = true;
            machine.setProcessingModes(ProcessingLogic.PROTECT_ITEMS | ProcessingLogic.PROTECT_FLUIDS);
            ItemStack input = new ItemStack(Items.iron_ingot, 64);
            machine.inputs.add(input);
            GT_MetaTileEntity_Hatch_Input fluid = fluidInput(machine, 6400);
            MeItemOutput items = new MeItemOutput();
            MeFluidOutput fluids = new MeFluidOutput();
            attach(items);
            attach(fluids);
            machine.mOutputBusses.add(items);
            machine.mOutputHatches.add(fluids);
            assertTrue(machine.checkRecipe(null));
            assertEquals(0, items.stored);
            assertEquals(0, fluids.stored);
            items.capacity = 3;
            fluids.capacity = 37;
            machine.finish();
            assertTrue(machine.isWaitingForOutput());
            assertEquals(3, items.stored);
            assertEquals(37, fluids.stored);
            NBTTagCompound saved = new NBTTagCompound();
            machine.saveNBTData(saved);
            TestMixer restored = machine();
            restored.loadNBTData(saved);
            restored.mOutputBusses.add(items);
            restored.mOutputHatches.add(fluids);
            assertFalse(restored.checkRecipe(null));
            restored.finish();
            assertEquals(3, items.stored);
            assertEquals(37, fluids.stored);
            items.capacity = 1000;
            fluids.capacity = 10000;
            restored.finish();
            assertFalse(restored.isWaitingForOutput());
            assertEquals(16, items.stored);
            assertEquals(640, fluids.stored);
            restored.finish();
            assertEquals(16, items.stored);
            assertEquals(640, fluids.stored);
            assertEquals(48, input.stackSize);
            assertEquals(4800, fluid.mFluid.amount);
        }

        public static void cachedConditions() {
            recipe(machine());
            TestMixer machine = machine();
            hatch(machine).isDebug = true;
            ItemStack input = new ItemStack(Items.iron_ingot, 64);
            machine.inputs.add(input);
            assertTrue(machine.checkRecipe(null));
            machine.finish();
            GT_Recipe recipe = machine.getRecipeMap().mRecipeList.iterator().next();
            recipe.mSpecialValue = -200;
            assertFalse(machine.checkRecipe(null));
            assertEquals(CheckRecipeResult.MACHINE_CONDITION, machine.getProcessingResult());
            assertEquals(48, input.stackSize);
            recipe.mSpecialValue = 0;
            assertTrue(machine.checkRecipe(null));
            assertEquals(32, input.stackSize);
        }

        private static long totalFluids(FluidStack[] fluids) {
            long total = 0;
            for (FluidStack fluid : fluids) {
                if (fluid != null) {
                    total += fluid.amount;
                }
            }
            return total;
        }

        private static void finish(GT_MetaTileEntity_ProcessingMultiBlockBase<?> machine) throws Exception {
            Method method = GT_MetaTileEntity_ProcessingMultiBlockBase.class.getDeclaredMethod("completeRecipe");
            method.setAccessible(true);
            method.invoke(machine);
        }

        private static void fluidRecipe() {
            GT_Recipe recipe = machine().getRecipeMap().addRecipe(false,
                    new ItemStack[] { new ItemStack(Items.iron_ingot) },
                    new ItemStack[] { new ItemStack(Items.gold_ingot) }, null, new int[] { 10000 },
                    new FluidStack[] { water(100) }, new FluidStack[] { new FluidStack(FluidRegistry.LAVA, 40) },
                    80, 24, 0);
            assertTrue(recipe != null);
        }

        private static GT_MetaTileEntity_Hatch_Input fluidInput(
                GT_MetaTileEntity_ProcessingMultiBlockBase<?> machine, int amount
        ) {
            GT_MetaTileEntity_Hatch_Input input = new GT_MetaTileEntity_Hatch_Input(
                    "fluid-input", 8, "", new ITexture[0][][]);
            attach(input);
            input.mFluid = water(amount);
            machine.mInputHatches.add(input);
            return input;
        }

        private static GT_MetaTileEntity_Hatch_Output fluidOutput(
                GT_MetaTileEntity_ProcessingMultiBlockBase<?> machine
        ) {
            GT_MetaTileEntity_Hatch_Output output = new GT_MetaTileEntity_Hatch_Output(
                    "fluid-output", 4, "", new ITexture[0][][]);
            attach(output);
            machine.mOutputHatches.add(output);
            return output;
        }

        public static void batchBoundaries() {
            recipe(machine());
            GT_Recipe recipe = machine().getRecipeMap().mRecipeList.iterator().next();
            for (int limit : new int[] { 1, 16, 64, 256 }) {
                int divisor = limit == 256 ? 4 : limit == 64 ? 3 : limit == 16 ? 2 : 1;
                for (int duration : new int[] { 2, 3, 63, 64, 65, 127, 128, 129, 400 }) {
                    recipe.mDuration = duration * divisor;
                    for (boolean batch : new boolean[] { false, true }) {
                        TestMixer machine = machine();
                        machine.amperage = 256;
                        GTMTE_ParallelHatch_Input hatch = hatch(machine);
                        hatch.isDebug = true;
                        hatch.mMaxParallel = limit;
                        machine.setProcessingModes(batch ? ProcessingLogic.BATCH : 0);
                        ItemStack input = new ItemStack(Items.iron_ingot, limit * 128);
                        machine.inputs.add(input);
                        String context = "limit=" + limit + ", duration=" + duration + ", batch=" + batch;
                        assertTrue(machine.checkRecipe(null), context);
                        int operations = batch && duration < 128 ? limit * 128 / duration : limit;
                        assertEquals(limit, machine.getCurrentParallel(), context);
                        assertEquals(operations, machine.getCurrentOperations(), context);
                        assertEquals(limit * 128 - operations, input.stackSize, context);
                        assertEquals(operations, machine.mOutputItems[0].stackSize, context);
                        assertEquals((duration * operations + limit - 1) / limit,
                                machine.mMaxProgresstime, context);
                        assertEquals(-24L * limit, machine.getRunningEUt(), context);
                    }
                }
            }
        }

        public static void subTickBatchMatrix() {
            recipe(machine());
            machine().getRecipeMap().mRecipeList.iterator().next().mDuration = 1;
            int[] limits = { 1, 16, 64, 256 };
            int[] normalSubTick = { 32, 128, 256, 512 };
            for (int index = 0; index < limits.length; index++) {
                for (boolean perfect : new boolean[] { false, true }) {
                    for (boolean bypass : new boolean[] { false, true }) {
                        for (boolean batch : new boolean[] { false, true }) {
                            TestMixer machine = machine();
                            machine.amperage = 1024;
                            machine.overclock = true;
                            machine.perfect = perfect;
                            machine.bypass = bypass;
                            GTMTE_ParallelHatch_Input hatch = hatch(machine);
                            hatch.isDebug = true;
                            hatch.mMaxParallel = limits[index];
                            machine.setProcessingModes(batch ? ProcessingLogic.BATCH : 0);
                            ItemStack input = new ItemStack(Items.iron_ingot, 100000);
                            machine.inputs.add(input);
                            String context = "limit=" + limits[index] + ", perfect=" + perfect
                                    + ", bypass=" + bypass + ", batch=" + batch;
                            assertTrue(machine.checkRecipe(null), context);
                            int parallel = bypass ? perfect ? 1024 : normalSubTick[index] : limits[index];
                            int operations = batch ? parallel * 64 : parallel;
                            assertEquals(parallel, machine.getCurrentParallel(), context);
                            assertEquals(operations, machine.getCurrentOperations(), context);
                            assertEquals(100000 - operations, input.stackSize, context);
                            assertEquals(operations, machine.mOutputItems[0].stackSize, context);
                            assertEquals(batch ? 128 : 2, machine.mMaxProgresstime, context);
                            assertEquals(-24576L, machine.getRunningEUt(), context);
                            NBTTagCompound saved = new NBTTagCompound();
                            machine.saveNBTData(saved);
                            TestMixer restored = machine();
                            restored.loadNBTData(saved);
                            assertEquals(parallel, restored.getCurrentParallel(), context);
                            assertEquals(operations, restored.mOutputItems[0].stackSize, context);
                        }
                    }
                }
            }
        }

        public static void antimatterBurn() throws Exception {
            TestAntimatter machine = new TestAntimatter();
            attach(machine);
            GT_Recipe recipe = machine.getRecipeMap().addRecipe(false,
                    new ItemStack[] { new ItemStack(Items.iron_ingot), new ItemStack(Items.sugar) },
                    new ItemStack[] { new ItemStack(Items.gold_ingot) }, null, new int[] { 10000 },
                    new FluidStack[] { water(10) }, new FluidStack[] { new FluidStack(FluidRegistry.LAVA, 4) },
                    20, 0, 1);
            assertTrue(recipe != null);
            GT_MetaTileEntity_Hatch_InputBus bus = addInput(machine, 4);
            bus.mInventory[1] = new ItemStack(Items.sugar, 4);
            GT_MetaTileEntity_Hatch_Input coolant = fluidInput(machine, 1000);
            GT_MetaTileEntity_Hatch_Output output = fluidOutput(machine);
            machine.setProcessingModes(ProcessingLogic.PROTECT_FLUIDS);
            assertTrue(machine.checkRecipe(null));
            assertEquals(990, coolant.mFluid.amount);
            assertEquals(3, bus.mInventory[0].stackSize);
            assertEquals(20, machine.mMaxProgresstime);
            assertEquals(2097152L, machine.getRunningEUt());
            output.mFluid = new FluidStack(FluidRegistry.LAVA, output.getCapacity());
            long generated = 0;
            for (int tick = 0; tick < 20; tick++) {
                assertTrue(machine.onRunningTick(null), "tick=" + tick);
                machine.mProgresstime++;
                assertEquals(990 - 10 * (tick + 1), coolant.mFluid.amount);
                if (tick == 9) {
                    generated += machine.generated;
                    NBTTagCompound saved = new NBTTagCompound();
                    machine.saveNBTData(saved);
                    machine = new TestAntimatter();
                    attach(machine);
                    machine.loadNBTData(saved);
                    machine.mInputBusses.add(bus);
                    machine.mInputHatches.add(coolant);
                    machine.mOutputHatches.add(output);
                    assertEquals(10, machine.mProgresstime);
                    assertFalse(machine.checkRecipe(null));
                }
            }
            generated += machine.generated;
            assertEquals(20L * 2097152, generated);
            finish(machine);
            assertTrue(machine.isWaitingForOutput());
            // Legacy reactor also emits one final product in addition to its per-tick products.
            assertEquals(84, totalFluids(machine.mOutputFluids));
            assertFalse(machine.checkRecipe(null));
            assertEquals(790, coolant.mFluid.amount);
            output.mFluid = null;
            finish(machine);
            assertFalse(machine.isWaitingForOutput());
            assertEquals(84, output.mFluid.amount);
            assertFalse(machine.checkRecipe(null));
            assertEquals(CheckRecipeResult.FLUID_OUTPUT_FULL, machine.getProcessingResult());
            assertEquals(3, bus.mInventory[0].stackSize);
            output.mFluid = null;
            assertTrue(machine.checkRecipe(null));
            assertEquals(780, coolant.mFluid.amount);
            assertEquals(2, bus.mInventory[0].stackSize);
        }

        public static void naquadahWarmup() throws Exception {
            GT_Utility.addFluidContainerData(new FluidContainerData(water(1000),
                    new ItemStack(Items.water_bucket), new ItemStack(Items.bucket)));
            GTMTE_Impact_FuelGeneratorBase<?>[] generators = {
                    new GTMTE_HyperNaquadahGenerator("hyper"),
                    new GTMTE_LiquidNqGenerator("liquid"),
                    new GTMTE_LiquidEnrichedNqGenerator("enriched")
            };
            for (GTMTE_Impact_FuelGeneratorBase<?> machine : generators) {
                attach(machine);
                assertTrue(machine.getRecipeMap().addRecipe(false,
                        new ItemStack[] { new ItemStack(Items.water_bucket) }, null, null, null,
                        null, null, 20, 0, 10) != null);
                GT_MetaTileEntity_Hatch_Input fuel = fluidInput(machine, 1000);
                for (int cycle = 0; cycle < 21; cycle++) {
                    assertTrue(machine.checkRecipe(null), machine.getClass().getSimpleName());
                    assertEquals(20, machine.mMaxProgresstime);
                    assertEquals(1000 - (cycle + 1) * 10, fuel.mFluid.amount);
                    assertEquals(Math.min(10000, cycle * 500), machine.mEfficiency);
                    assertEquals(500, machine.mEfficiencyIncrease);
                    assertEquals(10, machine.fuelConsumption);
                    assertTrue(Arrays.stream(machine.getInfoData()).anyMatch(line -> line.contains("10§r L/s")));
                    assertEquals(cycle > 18, machine.getRunningEUt() > 0);
                    if (cycle == 10) {
                        NBTTagCompound saved = new NBTTagCompound();
                        machine.mProgresstime = 7;
                        machine.saveNBTData(saved);
                        machine = (GTMTE_Impact_FuelGeneratorBase<?>) machine.getClass()
                                .getConstructor(String.class).newInstance("restored-generator");
                        attach(machine);
                        machine.loadNBTData(saved);
                        machine.mInputHatches.add(fuel);
                        assertEquals(7, machine.mProgresstime);
                        assertEquals(5000, machine.mEfficiency);
                        assertEquals(10, machine.fuelConsumption);
                        assertFalse(machine.checkRecipe(null));
                        assertEquals(890, fuel.mFluid.amount);
                    }
                    finish(machine);
                }
                fuel.mFluid = null;
                assertFalse(machine.checkRecipe(null));
                assertEquals(0, machine.mEfficiency);
            }
        }

        public static void parallelMatrix() {
            recipe(machine());
            for (int limit : new int[] { 1, 16, 64, 256 }) {
                for (boolean batch : new boolean[] { false, true }) {
                    for (int count : new int[] { 0, 1, Math.max(1, limit - 1), limit, limit * 4 }) {
                        for (int powered : new int[] { 1, limit }) {
                            TestMixer machine = machine();
                            machine.amperage = powered;
                            GTMTE_ParallelHatch_Input hatch = hatch(machine);
                            hatch.isDebug = true;
                            hatch.mMaxParallel = limit;
                            machine.setProcessingModes(batch ? ProcessingLogic.BATCH : 0);
                            ItemStack input = new ItemStack(Items.iron_ingot, count);
                            machine.inputs.add(input);
                            String context = "limit=" + limit + ", batch=" + batch + ", items=" + count
                                    + ", amperage=" + powered;
                            if (count == 0) {
                                assertFalse(machine.checkRecipe(null), context);
                                assertEquals(0, input.stackSize, context);
                                continue;
                            }
                            int parallel = Math.min(count, Math.min(limit, powered * 32 / 24));
                            int duration = limit == 256 ? 20 : limit == 64 ? 26 : limit == 16 ? 40 : 80;
                            int operations = batch ? Math.min(count, parallel * 128 / duration) : parallel;
                            assertTrue(machine.checkRecipe(null), context);
                            assertEquals(limit, machine.getAvailableParallel(), context);
                            assertEquals(parallel, machine.getCurrentParallel(), context);
                            assertEquals(operations, machine.getCurrentOperations(), context);
                            assertEquals(count - operations, input.stackSize, context);
                            assertEquals(operations, machine.mOutputItems[0].stackSize, context);
                            assertEquals(-24L * parallel, machine.getRunningEUt(), context);
                            assertEquals((duration * operations + parallel - 1) / parallel,
                                    machine.mMaxProgresstime, context);
                        }
                    }
                }
            }
        }

        public static void gregTechOverclockMatrix() {
            recipe(machine());
            GT_Recipe recipe = machine().getRecipeMap().mRecipeList.iterator().next();
            for (int tier = 0; tier < GT_Values.V.length; tier++) {
                for (int kind = 0; kind < 3; kind++) {
                    for (int amps : new int[] { 1, 16, 65536 }) {
                        for (int limit : new int[] { 1, 16, 64, 256 }) {
                            for (boolean perfect : new boolean[] { false, true }) {
                                for (int duration : new int[] { 17, 128, 12000 }) {
                                    TestMixer machine = machine();
                                    machine.realPower = true;
                                    machine.overclock = true;
                                    machine.perfect = perfect;
                                    addEnergyHatch(machine, tier, amps, kind);
                                    GTMTE_ParallelHatch_Input parallelHatch = hatch(machine);
                                    parallelHatch.isDebug = true;
                                    parallelHatch.mMaxParallel = limit;
                                    ItemStack input = new ItemStack(Items.iron_ingot, limit * 2);
                                    machine.inputs.add(input);
                                    recipe.mDuration = duration;
                                    long power = GT_Values.V[tier] * (kind == 0 ? 1L : amps);
                                    String context = "tier=" + tier + ", kind=" + kind + ", amps=" + amps
                                            + ", limit=" + limit + ", perfect=" + perfect + ", ticks=" + duration;
                                    if (power < recipe.mEUt) {
                                        assertFalse(machine.checkRecipe(null), context);
                                        assertEquals(limit * 2, input.stackSize, context);
                                        continue;
                                    }
                                    int parallel = (int) Math.min(limit, power / recipe.mEUt);
                                    OverclockCalculator.Result expected = new OverclockCalculator()
                                            .setOverclock(perfect ? 4 : 2, 4)
                                            .calculate(recipe.mEUt, duration, power, parallel);
                                    int divisor = limit == 256 ? 4 : limit == 64 ? 3 : limit == 16 ? 2 : 1;
                                    assertTrue(machine.checkRecipe(null), context);
                                    assertEquals(-expected.eut, machine.getRunningEUt(), context);
                                    assertEquals(Math.max(Config.MAX_TICK_RATE, expected.duration / divisor),
                                            machine.mMaxProgresstime, context);
                                    assertEquals(parallel, machine.getCurrentParallel(), context);
                                    assertEquals(limit * 2 - parallel, input.stackSize, context);
                                    assertEquals(parallel, machine.mOutputItems[0].stackSize, context);
                                }
                            }
                        }
                    }
                }
            }
        }

        public static void powerMatrix() {
            recipe(machine());
            GT_Recipe recipe = machine().getRecipeMap().mRecipeList.iterator().next();
            int[] amperages = { 1, 2, 4, 16, 64, 256, 1024, 4096, 16384, 65536, 262144, 1048576 };
            for (int tier = 0; tier < GT_Values.V.length; tier++) {
                for (int amps : amperages) {
                    for (int kind = 0; kind < 3; kind++) {
                        for (int count : new int[] { 1, 2, 4 }) {
                            TestMixer machine = machine();
                            machine.realPower = true;
                            for (int index = 0; index < count; index++) {
                                addEnergyHatch(machine, tier, amps, kind);
                            }
                            long availableAmps = kind == 0 && count == 1 ? 1 : (long) amps * count;
                            String context = "tier=" + tier + ", amps=" + amps + ", kind=" + kind
                                    + ", hatches=" + count;
                            assertEquals(GT_Values.V[tier], machine.getAverageInputVoltage(), context);
                            assertEquals(availableAmps, machine.getProcessingAmperage(), context);
                            ItemStack input = new ItemStack(Items.iron_ingot, 1);
                            machine.inputs.add(input);
                            boolean expected = GT_Values.V[tier] * availableAmps >= 24;
                            assertEquals(expected, machine.checkRecipe(null), context);
                            assertEquals(expected ? 0 : 1, input.stackSize, context);
                        }
                    }
                }
            }
            for (int tier = 0; tier < GT_Values.V.length && GT_Values.V[tier] < Integer.MAX_VALUE / 4; tier++) {
                for (int kind = 0; kind < 3; kind++) {
                    TestMixer machine = machine();
                    machine.realPower = true;
                    for (int index = 0; index < 4; index++) {
                        addEnergyHatch(machine, tier, 256, kind);
                    }
                    ItemStack input = new ItemStack(Items.iron_ingot, 2);
                    machine.inputs.add(input);
                    recipe.mEUt = (int) (GT_Values.V[tier] * 4 + 1);
                    assertFalse(machine.checkRecipe(null), "tier=" + tier + ", kind=" + kind);
                    assertEquals(CheckRecipeResult.INSUFFICIENT_VOLTAGE, machine.getProcessingResult());
                    assertEquals(2, input.stackSize);
                    recipe.mEUt--;
                    assertTrue(machine.checkRecipe(null), "tier=" + tier + ", kind=" + kind);
                    assertEquals(1, input.stackSize);
                }
            }
        }

        private static void addEnergyHatch(TestMixer machine, int tier, int amps, int kind) {
            if (kind == 0) {
                GT_MetaTileEntity_Hatch_Energy hatch = new GT_MetaTileEntity_Hatch_Energy(
                        "ordinary", tier, new String[0], new ITexture[0][][], amps);
                attach(hatch);
                machine.mEnergyHatches.add(hatch);
            } else if (kind == 1) {
                GT_MetaTileEntity_Hatch_EnergyMulti hatch = new GT_MetaTileEntity_Hatch_EnergyMulti(
                        "multi", tier, amps, "", new ITexture[0][][]);
                attach(hatch);
                machine.mEnergyHatchesMulti.add(hatch);
            } else {
                addLaser(machine, tier, amps);
            }
        }

        public static void failureMatrix() {
            fluidRecipe();
            for (int limit : new int[] { 1, 16, 64, 256 }) {
                for (int failure = 0; failure < 6; failure++) {
                    TestMixer machine = machine();
                    GTMTE_ParallelHatch_Input parallel = hatch(machine);
                    parallel.isDebug = true;
                    parallel.mMaxParallel = limit;
                    ItemStack input = new ItemStack(Items.iron_ingot, 64);
                    machine.inputs.add(input);
                    GT_MetaTileEntity_Hatch_Input fluid = fluidInput(machine, 6400);
                    CheckRecipeResult expected;
                    switch (failure) {
                        case 0:
                            machine.voltage = 23;
                            machine.amperage = 1;
                            expected = CheckRecipeResult.INSUFFICIENT_POWER;
                            break;
                        case 1:
                            machine.voltage = 5;
                            expected = CheckRecipeResult.INSUFFICIENT_VOLTAGE;
                            break;
                        case 2:
                            machine.voltage = Long.MAX_VALUE;
                            machine.amperage = 2;
                            expected = CheckRecipeResult.POWER_OVERFLOW;
                            break;
                        case 3:
                            machine.setProcessingModes(ProcessingLogic.PROTECT_ITEMS | ProcessingLogic.BATCH);
                            expected = CheckRecipeResult.ITEM_OUTPUT_FULL;
                            break;
                        case 4:
                            machine.setProcessingModes(ProcessingLogic.PROTECT_FLUIDS | ProcessingLogic.BATCH);
                            expected = CheckRecipeResult.FLUID_OUTPUT_FULL;
                            break;
                        default:
                            machine.requireNetwork = true;
                            expected = CheckRecipeResult.MACHINE_CONDITION;
                            break;
                    }
                    assertFalse(machine.checkRecipe(null), "limit=" + limit + ", failure=" + failure);
                    assertEquals(expected, machine.getProcessingResult());
                    assertEquals(64, input.stackSize);
                    assertEquals(6400, fluid.mFluid.amount);
                    assertEquals(0, machine.mMaxProgresstime);
                    assertEquals(0, machine.getCurrentOperations());
                }
            }
        }

        public static void networkLoss() {
            recipe(machine());
            TestMixer machine = machine();
            machine.requireNetwork = true;
            machine.setSatelliteConnected(true);
            GTMTE_ParallelHatch_Input parallel = hatch(machine);
            parallel.isConnected = true;
            ItemStack input = new ItemStack(Items.iron_ingot, 64);
            machine.inputs.add(input);
            assertTrue(machine.checkRecipe(null));
            machine.setSatelliteConnected(false);
            parallel.isConnected = false;
            assertEquals(16, machine.getCurrentParallel());
            assertEquals(48, input.stackSize);
            machine.finish();
            assertFalse(machine.checkRecipe(null));
            assertEquals(48, input.stackSize);
            machine.setSatelliteConnected(true);
            parallel.isConnected = true;
            assertTrue(machine.checkRecipe(null));
            assertEquals(32, input.stackSize);
        }

        public static void pendingParallelOutputs() {
            recipe(machine());
            TestMixer machine = machine();
            hatch(machine).isDebug = true;
            GT_MetaTileEntity_Hatch_OutputBus output = output(machine);
            machine.setProcessingModes(ProcessingLogic.PROTECT_ITEMS);
            ItemStack input = new ItemStack(Items.iron_ingot, 64);
            machine.inputs.add(input);
            assertTrue(machine.checkRecipe(null));
            output.mInventory[0] = new ItemStack(Items.gold_ingot, 63);
            machine.finish();
            assertTrue(machine.isWaitingForOutput());
            assertEquals(64, output.mInventory[0].stackSize);
            assertEquals(15, machine.mOutputItems[0].stackSize);
            assertEquals(48, input.stackSize);
            for (int reload = 0; reload < 3; reload++) {
                NBTTagCompound saved = new NBTTagCompound();
                machine.saveNBTData(saved);
                machine = machine();
                machine.mOutputBusses.add(output);
                machine.loadNBTData(saved);
                machine.stopMachine();
                machine.setProcessingModes(0);
                machine.finish();
                assertTrue(machine.isWaitingForOutput());
                assertEquals(15, machine.mOutputItems[0].stackSize);
                assertFalse(machine.checkRecipe(null));
            }
            output.mInventory[0] = null;
            machine.finish();
            assertFalse(machine.isWaitingForOutput());
            assertEquals(15, output.mInventory[0].stackSize);
            machine.finish();
            assertEquals(15, output.mInventory[0].stackSize);
            assertEquals(48, input.stackSize);
        }

        public static void subTickMatrix() {
            recipe(machine());
            GT_Recipe recipe = GT_Recipe.GT_Recipe_Map.sMultuMixerRecipes.mRecipeList.iterator().next();
            recipe.mDuration = 1;
            for (boolean perfect : new boolean[] { false, true }) {
                for (boolean bypass : new boolean[] { false, true }) {
                    TestMixer machine = machine();
                    machine.overclock = true;
                    machine.perfect = perfect;
                    machine.bypass = bypass;
                    ItemStack input = new ItemStack(Items.iron_ingot, 64);
                    machine.inputs.add(input);
                    assertTrue(machine.checkRecipe(null));
                    int operations = bypass ? perfect ? 16 : 4 : 1;
                    assertEquals(operations, machine.getCurrentParallel());
                    assertEquals(operations, machine.getCurrentOperations());
                    assertEquals(64 - operations, input.stackSize);
                    assertEquals(-384L, machine.getRunningEUt());
                    assertEquals(2, machine.mMaxProgresstime);
                }
            }
        }

        private static GT_MetaTileEntity_Hatch_OutputBus output(TestMixer machine) {
            GT_MetaTileEntity_Hatch_OutputBus output = new GT_MetaTileEntity_Hatch_OutputBus(
                    "output", 0, new String[0], new ITexture[0][][]);
            new ImpactConstructionChecks.TestTile(machine.getBaseMetaTileEntity().getWorld(), output,
                    ForgeDirection.NORTH);
            machine.mOutputBusses.add(output);
            return output;
        }

        public static void matrixParticles() throws Exception {
            GT_Recipe recipe = GT_RecipeMaps.sMESystemProvider.addRecipe(false,
                    new ItemStack[] { new ItemStack(Items.iron_ingot) },
                    new ItemStack[] { new ItemStack(Items.gold_ingot) }, null, new int[] { 10000 },
                    null, null, 8, 24, 10);
            assertTrue(recipe != null);
            for (int limit : new int[] { 1, 16, 64, 256 }) {
                for (int particles : new int[] { 0, 9, 10, 150, 640 }) {
                    for (boolean blockedOutput : new boolean[] { false, true }) {
                        GTMTEMESystemProvider machine = new GTMTEMESystemProvider("matrix-test");
                        attach(machine);
                        addLaser(machine, 1, 256);
                        GT_MetaTileEntity_Hatch_InputBus bus = addInput(machine, 128);
                        GTMTE_ParallelHatch_Input parallel = new GTMTE_ParallelHatch_Input(
                                "parallel", 6, new String[0], new ITexture[0][][], limit);
                        attach(parallel);
                        parallel.isDebug = true;
                        machine.sParallHatchesIn.add(parallel);
                        machine.setMMatrixParticlesSummary(particles);
                        machine.setProcessingModes(ProcessingLogic.BATCH
                                | (blockedOutput ? ProcessingLogic.PROTECT_ITEMS : 0));
                        logic(machine).setCalculatorSupplier(() -> new OverclockCalculator().setMaxOverclocks(0));
                        ItemStack input = bus.mInventory[0];
                        boolean expected = particles >= 10 && !blockedOutput;
                        assertEquals(expected, machine.checkRecipe(null));
                        if (expected) {
                            int operations = machine.getCurrentOperations();
                            assertTrue(operations > 0 && operations <= particles / 10);
                            assertTrue(machine.getCurrentParallel() <= limit);
                            assertEquals(particles - operations * 10, machine.getMMatrixParticlesSummary());
                            assertEquals(128 - operations, input.stackSize);
                            assertEquals(operations, machine.mOutputItems[0].stackSize);
                            NBTTagCompound tag = new NBTTagCompound();
                            machine.saveNBTData(tag);
                            GTMTEMESystemProvider restored = new GTMTEMESystemProvider("matrix-restored");
                            attach(restored);
                            restored.loadNBTData(tag);
                            assertEquals(particles - operations * 10, restored.getMMatrixParticlesSummary());
                            assertEquals(operations, restored.getCurrentOperations());
                            assertFalse(restored.checkRecipe(null));
                        } else {
                            assertEquals(particles, machine.getMMatrixParticlesSummary());
                            assertEquals(128, input.stackSize);
                        }
                    }
                }
            }
        }

        public static void matrixParticlesChanged() throws Exception {
            GTMTEMESystemProvider machine = new GTMTEMESystemProvider("matrix-atomic");
            attach(machine);
            GT_Recipe recipe = machine.getRecipeMap().addRecipe(false,
                    new ItemStack[] { new ItemStack(Items.iron_ingot) },
                    new ItemStack[] { new ItemStack(Items.gold_ingot) }, null, new int[] { 10000 },
                    new FluidStack[] { water(10) }, null, 80, 24, 10);
            assertTrue(recipe != null);
            machine.setMMatrixParticlesSummary(100);
            ProcessingLogic logic = logic(machine);
            logic.setCalculatorSupplier(() -> new OverclockCalculator().setMaxOverclocks(0));
            ItemStack item = new ItemStack(Items.iron_ingot, 4);
            FluidStack fluid = water(40);
            ProcessingOutputAccess output = new ProcessingOutputAccess() {
                @Override
                public CheckRecipeResult canFit(ItemStack[] items, FluidStack[] fluids, boolean protectItems,
                        boolean protectFluids) {
                    machine.setMMatrixParticlesSummary(0);
                    return CheckRecipeResult.SUCCESS;
                }

                @Override
                public boolean areFluidOutputsEmpty() {
                    return true;
                }
            };
            ProcessingLogic.Result result = logic.process(new ProcessingLogic.Request(
                    Collections.singletonList(new ProcessingInputs(Collections.singletonList(item),
                            Collections.singletonList(fluid))), 32, 1, 0, output, bound -> 0));
            assertEquals(CheckRecipeResult.INPUT_CHANGED, result.status);
            assertEquals(4, item.stackSize);
            assertEquals(40, fluid.amount);
            assertEquals(0, machine.getMMatrixParticlesSummary());
        }

        public static void pyrolysisParallelSource() throws Exception {
            GT_Recipe recipe = GT_RecipeMaps.sPyrolyseOven.addRecipe(false,
                    new ItemStack[] { new ItemStack(Items.iron_ingot) },
                    new ItemStack[] { new ItemStack(Items.gold_ingot) }, null, new int[] { 0 },
                    null, null, 800, 2, 0);
            assertTrue(recipe != null);
            for (int tier = 1; tier <= 8; tier++) {
                GTMTE_AdvancedPyrolyse machine = new GTMTE_AdvancedPyrolyse("pyrolysis-test");
                attach(machine);
                GT_MetaTileEntity_Hatch_Energy energy = new GT_MetaTileEntity_Hatch_Energy(
                        "energy", tier, "", new ITexture[0][][]);
                attach(energy);
                machine.mEnergyHatches.add(energy);
                GT_MetaTileEntity_Hatch_InputBus bus = addInput(machine, 64);
                assertTrue(machine.checkRecipe(null));
                assertEquals(tier * 2, machine.getCurrentParallel());
                assertEquals(tier * 2, machine.getAvailableParallel());
                assertEquals(64 - tier * 2, bus.mInventory[0].stackSize);
                assertEquals(800, machine.mMaxProgresstime);
                assertEquals(tier * 2, machine.mOutputItems[0].stackSize);
                assertEquals(-2L * tier * tier, machine.getRunningEUt());
            }
        }

        public static void mixedPower() {
            TestMixer machine = machine();
            machine.realPower = true;
            GT_MetaTileEntity_Hatch_Energy ordinary = new GT_MetaTileEntity_Hatch_Energy(
                    "ordinary", 1, "", new ITexture[0][][], 4);
            GT_MetaTileEntity_Hatch_EnergyMulti multi = new GT_MetaTileEntity_Hatch_EnergyMulti(
                    "multi", 2, 16, "", new ITexture[0][][]);
            attach(ordinary);
            attach(multi);
            machine.mEnergyHatches.add(ordinary);
            machine.mEnergyHatchesMulti.add(multi);
            addLaser(machine, 3, 64);
            assertEquals((32 + 128 + 512) / 3, machine.getAverageInputVoltage());
            assertEquals(84, machine.getProcessingAmperage());
            recipe(machine);
            GT_Recipe recipe = machine.getRecipeMap().mRecipeList.iterator().next();
            recipe.mEUt = 897;
            ItemStack input = new ItemStack(Items.iron_ingot, 4);
            machine.inputs.add(input);
            assertFalse(machine.checkRecipe(null));
            assertEquals(CheckRecipeResult.INSUFFICIENT_VOLTAGE, machine.getProcessingResult());
            assertEquals(4, input.stackSize);
            recipe.mEUt = 896;
            assertTrue(machine.checkRecipe(null));
            assertEquals(3, input.stackSize);
        }

        public static void outputChancePolicy() {
            recipe(machine());
            GT_Recipe recipe = GT_Recipe.GT_Recipe_Map.sMultuMixerRecipes.mRecipeList.iterator().next();
            recipe.mChances[0] = 0;
            TestMixer machine = machine();
            machine.inputs.add(new ItemStack(Items.iron_ingot, 64));
            hatch(machine).isDebug = true;
            assertTrue(machine.checkRecipe(null));
            assertEquals(16, machine.getCurrentParallel());
            assertTrue(machine.mOutputItems[0] == null);
            machine.finish();
            recipe.mChances[0] = 10000;
            assertTrue(machine.checkRecipe(null));
            assertEquals(16, machine.mOutputItems[0].stackSize);
        }

        private static void attach(MetaTileEntity machine) {
            ImpactConstructionChecks.TestTile tile = new ImpactConstructionChecks.TestTile(
                    new ImpactConstructionChecks.TestWorld(), machine, ForgeDirection.NORTH);
            tile.disableWorking();
            if (machine instanceof GT_MetaTileEntity_ProcessingMultiBlockBase) {
                GT_MetaTileEntity_ProcessingMultiBlockBase<?> controller =
                        (GT_MetaTileEntity_ProcessingMultiBlockBase<?>) machine;
                controller.enabledMaintenance = false;
                controller.mWrench = controller.mScrewdriver = controller.mSoftHammer = true;
                controller.mHardHammer = controller.mSolderingTool = controller.mCrowbar = true;
            }
        }

        private static void addLaser(GTMTE_Impact_BlockBase<?> machine, int tier, int amps) {
            GTMTE_LaserEnergy_In laser = new GTMTE_LaserEnergy_In("laser", tier, amps, "", new ITexture[0][][]);
            attach(laser);
            machine.mLaserIn.add(laser);
        }

        private static GT_MetaTileEntity_Hatch_InputBus addInput(
                GT_MetaTileEntity_ProcessingMultiBlockBase<?> machine, int amount
        ) {
            GT_MetaTileEntity_Hatch_InputBus bus = new GT_MetaTileEntity_Hatch_InputBus(
                    "input", 1, "", new ITexture[0][][]);
            attach(bus);
            bus.mInventory[0] = new ItemStack(Items.iron_ingot, amount);
            machine.mInputBusses.add(bus);
            return bus;
        }

        private static ProcessingLogic logic(GT_MetaTileEntity_ProcessingMultiBlockBase<?> machine) throws Exception {
            Method getter = GT_MetaTileEntity_ProcessingMultiBlockBase.class.getDeclaredMethod("getProcessingLogic");
            getter.setAccessible(true);
            return (ProcessingLogic) getter.invoke(machine);
        }

        public static void parallelTransitions() {
            TestMixer machine = machine();
            recipe(machine);
            ItemStack input = new ItemStack(Items.iron_ingot, 64);
            machine.inputs.add(input);
            assertTrue(machine.checkRecipe(null));
            assertEquals(1, machine.getCurrentParallel());
            assertEquals(80, machine.mMaxProgresstime);
            machine.finish();

            GTMTE_ParallelHatch_Input hatch = hatch(machine);
            hatch.isDebug = true;
            assertTrue(machine.checkRecipe(null));
            assertEquals(16, machine.getCurrentParallel());
            assertEquals(40, machine.mMaxProgresstime);
            hatch.mMaxParallel = 1;
            assertEquals(16, machine.getCurrentParallel());
            assertEquals(40, machine.mMaxProgresstime);
            machine.finish();
            assertTrue(machine.checkRecipe(null));
            assertEquals(1, machine.getCurrentParallel());
            assertEquals(80, machine.mMaxProgresstime);
            assertEquals(46, input.stackSize);
        }

        public static void disconnectedHatch() {
            TestMixer machine = machine();
            recipe(machine);
            ItemStack input = new ItemStack(Items.iron_ingot, 32);
            machine.inputs.add(input);
            hatch(machine);
            assertFalse(machine.checkRecipe(null));
            assertEquals(32, input.stackSize);
            assertEquals(CheckRecipeResult.MACHINE_CONDITION, machine.getProcessingResult());
        }

        public static void modesMigration() {
            TestMixer original = machine();
            assertEquals(0, original.getProcessingModes());
            NBTTagCompound old = new NBTTagCompound();
            old.setBoolean("isSeparated", true);
            original.loadNBTData(old);
            assertEquals(ProcessingLogic.SEPARATE_INPUTS, original.getProcessingModes());
            for (int modes = 0; modes <= ProcessingLogic.ALL_MODES; modes++) {
                original.setProcessingModes(modes);
                NBTTagCompound saved = new NBTTagCompound();
                original.saveNBTData(saved);
                saved.setBoolean("isSeparated", true);
                TestMixer restored = machine();
                restored.loadNBTData(saved);
                assertEquals(modes, restored.getProcessingModes(), "modes=" + modes);
            }
        }

        public static void legacyCycle() {
            TestMixer original = machine();
            original.mMaxProgresstime = 80;
            original.mProgresstime = 25;
            original.mOutputItems = new ItemStack[] { new ItemStack(Items.gold_ingot, 16) };
            NBTTagCompound saved = new NBTTagCompound();
            original.saveNBTData(saved);
            saved.removeTag("processing");
            saved.setInteger("mCheckParallelCurrent", 16);
            TestMixer restored = machine();
            restored.loadNBTData(saved);
            assertEquals(80, restored.mMaxProgresstime);
            assertEquals(25, restored.mProgresstime);
            assertEquals(16, restored.getCurrentParallel());
            assertEquals(16, restored.mOutputItems[0].stackSize);
            assertFalse(restored.checkRecipe(null));
        }

        public static void stagedOutputs() {
            StagedFluidOutputs original = new StagedFluidOutputs();
            original.start(new FluidStack[] { water(100), water(200), water(300), water(400), water(500) }, true);
            Tank sink = new Tank(40);
            original.tick(139, sink);
            assertEquals(0, sink.received);
            original.tick(140, sink);
            assertEquals(40, sink.received);
            NBTTagCompound saved = new NBTTagCompound();
            original.save(saved);
            StagedFluidOutputs restored = new StagedFluidOutputs();
            restored.load(saved);
            sink.capacity = 1000;
            restored.tick(140, sink);
            assertEquals(100, sink.received);
            restored.tick(140, sink);
            assertEquals(100, sink.received);
            restored.tick(560, sink);
            assertEquals(1000, sink.received);
            FluidStack[] finalOutputs = restored.finish(new FluidStack[] { water(500) });
            assertEquals(1, finalOutputs.length);
            assertEquals(500, finalOutputs[0].amount);
        }

        public static void legacyPyrolysis() {
            StagedFluidOutputs restored = new StagedFluidOutputs();
            restored.load(new NBTTagCompound());
            Tank sink = new Tank(10000);
            restored.tick(799, sink);
            assertEquals(0, sink.received);
            FluidStack[] finalOutputs = restored.finish(new FluidStack[] { water(500) });
            assertEquals(1, finalOutputs.length);
            assertEquals(500, finalOutputs[0].amount);
        }

        private static FluidStack water(int amount) {
            return new FluidStack(FluidRegistry.WATER, amount);
        }

        private static TestMixer machine() {
            TestMixer machine = new TestMixer();
            ImpactConstructionChecks.TestTile tile = new ImpactConstructionChecks.TestTile(
                    new ImpactConstructionChecks.TestWorld(), machine, ForgeDirection.NORTH);
            tile.disableWorking();
            machine.enabledMaintenance = false;
            return machine;
        }

        private static GTMTE_ParallelHatch_Input hatch(TestMixer machine) {
            GTMTE_ParallelHatch_Input hatch = new GTMTE_ParallelHatch_Input(
                    "parallel-test", 6, new String[0], new ITexture[0][][], 16);
            new ImpactConstructionChecks.TestTile(machine.getBaseMetaTileEntity().getWorld(), hatch,
                    ForgeDirection.NORTH);
            machine.sParallHatchesIn.add(hatch);
            return hatch;
        }

        private static void recipe(TestMixer machine) {
            GT_Recipe recipe = machine.getRecipeMap().addRecipe(false,
                    new ItemStack[] { new ItemStack(Items.iron_ingot) },
                    new ItemStack[] { new ItemStack(Items.gold_ingot) }, null, new int[] { 10000 },
                    null, null, 80, 24, 0);
            assertTrue(recipe != null);
        }
    }

    public static final class TestMixer extends GTMTE_Mixer {

        private final ArrayList<ItemStack> inputs = new ArrayList<>();
        private long voltage = 32;
        private long amperage = 16;
        private boolean realPower;
        private boolean realInputs;
        private boolean requireNetwork;
        private boolean overclock;
        private boolean perfect;
        private boolean bypass;

        private TestMixer() {
            super("processing-test");
        }

        @Override
        public ArrayList<ItemStack> getStoredInputs() {
            return realInputs ? super.getStoredInputs() : inputs;
        }

        @Override
        public long getAverageInputVoltage() {
            return realPower ? super.getAverageInputVoltage() : voltage;
        }

        @Override
        public long getProcessingAmperage() {
            return realPower ? super.getProcessingAmperage() : amperage;
        }

        @Override
        protected ProcessingLogic createProcessingLogic() {
            return super.createProcessingLogic()
                    .setCalculatorSupplier(() -> new OverclockCalculator()
                            .setMaxOverclocks(overclock ? Integer.MAX_VALUE : 0))
                    .setOverclock(perfect ? 4 : 2, 4)
                    .setSubTickParallelBypassesLimit(bypass);
        }

        @Override
        public boolean hasConnectForce() {
            return requireNetwork;
        }

        private void finish() {
            completeRecipe();
        }
    }

    public static final class TestAntimatter extends GTMTE_AntimatterReactor {

        private long generated;

        private TestAntimatter() {
            super("antimatter-test");
        }

        @Override
        public boolean addEnergyOutput(long amount) {
            generated += amount;
            return true;
        }
    }

    private static final class MeItemOutput extends GT_MetaTileEntity_Hatch_OutputBus_ME {

        private int capacity = 1000;
        private int stored;

        private MeItemOutput() {
            super("me-items", 4, new String[0], new ITexture[0][][]);
        }

        @Override
        public long getProcessingCapacity(ItemStack stack) {
            return capacity - stored;
        }

        @Override
        public boolean storeAll(ItemStack stack) {
            int accepted = Math.min(stack.stackSize, capacity - stored);
            stored += accepted;
            stack.stackSize -= accepted;
            return stack.stackSize == 0;
        }
    }

    private static final class MeFluidOutput extends GT_MetaTileEntity_Hatch_Output_ME {

        private int capacity = 10000;
        private int stored;

        private MeFluidOutput() {
            super("me-fluids", 4, new String[0], new ITexture[0][][]);
        }

        @Override
        public long getProcessingCapacity(FluidStack stack) {
            return capacity - stored;
        }

        @Override
        public int fill(FluidStack stack, boolean execute) {
            int accepted = Math.min(stack.amount, capacity - stored);
            if (execute) {
                stored += accepted;
            }
            return accepted;
        }
    }

    private static final class Tank implements IProcessingOutputSink {

        private int capacity;
        private int received;

        private Tank(int capacity) {
            this.capacity = capacity;
        }

        @Override
        public CheckRecipeResult canFit(ItemStack[] items, FluidStack[] fluids, boolean protectItems,
                boolean protectFluids) {
            return CheckRecipeResult.SUCCESS;
        }

        @Override
        public boolean areFluidOutputsEmpty() {
            return received == 0;
        }

        @Override
        public boolean deliver(ItemStack[] items, FluidStack[] fluids, boolean protectItems, boolean protectFluids) {
            boolean complete = true;
            for (FluidStack fluid : fluids) {
                int amount = Math.min(fluid.amount, capacity - received);
                received += amount;
                fluid.amount -= amount;
                if (!protectFluids) {
                    fluid.amount = 0;
                }
                complete &= fluid.amount == 0;
            }
            return complete;
        }
    }
}
