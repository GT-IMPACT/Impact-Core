package com.impact.mods.waila;

import appeng.tile.AEBaseTile;
import appeng.tile.crafting.TileCraftingTile;
import com.enderio.core.common.util.BlockCoord;
import com.github.technus.tectech.thing.metaTileEntity.multi.GT_MetaTileEntity_EM_research;
import com.impact.mods.gregtech.tileentities.basic.GTMTE_LongDistancePipelineBase;
import com.impact.mods.gregtech.tileentities.basic.GTMTE_Solar;
import com.impact.mods.gregtech.tileentities.multi.biomeores.GTMTE_BasicMiner;
import com.impact.mods.gregtech.tileentities.multi.biomeores.GTMTE_Mining_Coal;
import com.impact.mods.gregtech.tileentities.multi.generators.green.GTMTE_Wind_Generator;
import com.impact.mods.gregtech.tileentities.multi.generators.nuclear.GTMTE_NuclearReactorBase;
import com.impact.mods.gregtech.tileentities.multi.generators.nuclear.hatch.GTMTE_Reactor_Rod_Hatch;
import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_MBBase;
import com.impact.mods.gregtech.tileentities.multi.implement.GT_MetaTileEntity_MultiParallelBlockBase;
import com.impact.mods.gregtech.tileentities.multi.parallelsystem.GTMTE_ParallelHatch_Input;
import com.impact.mods.gregtech.tileentities.multi.parallelsystem.GTMTE_ParallelHatch_Output;
import com.impact.mods.gregtech.tileentities.multi.parallelsystem.GTMTE_SpaceSatellite_Receiver;
import com.impact.mods.gregtech.tileentities.multi.parallelsystem.GTMTE_TowerCommunication;
import com.impact.mods.gregtech.tileentities.multi.matrixsystem.GTMTE_MESystemProvider;
import com.impact.mods.gregtech.tileentities.multi.matrixsystem.GTMTE_MPContainment;
import com.impact.mods.gregtech.tileentities.multi.matrixsystem.GTMTE_MPStabilizer;
import com.impact.mods.gregtech.tileentities.multi.storage.GTMTE_LapPowerStation;
import com.impact.mods.gregtech.tileentities.multi.units.GTMTE_Aerostat;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.BaseTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase;
import gregtech.api.util.GT_Utility;
import gregtech.common.tileentities.storage.GT_MetaTileEntity_DigitalChestBase;
import lombok.SneakyThrows;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import tterrag.wailaplugins.api.Plugin;
import tterrag.wailaplugins.plugins.PluginBase;

import java.text.NumberFormat;
import java.util.List;

import static mcp.mobius.waila.api.SpecialChars.*;
import static net.minecraft.util.StatCollector.translateToLocal;

@Plugin(name = "Impact-core", deps = {"impact", "gregtech"})
public class ImpactPlugin extends PluginBase {

    @Override
    public void load(IWailaRegistrar registrar) {
        super.load(registrar);
        registerBody(BaseTileEntity.class, AEBaseTile.class);
        registerNBT(BaseTileEntity.class, AEBaseTile.class);
    }

    @Override
    @SneakyThrows
    @SuppressWarnings("unused")
    protected void getBody(ItemStack stack, List<String> currenttip, IWailaDataAccessor accessor) {
        final TileEntity tile = accessor.getTileEntity();
        MovingObjectPosition pos = accessor.getPosition();
        NBTTagCompound tag = accessor.getNBTData();
        final int side = (byte) accessor.getSide().ordinal();
        final IGregTechTileEntity tBaseMetaTile = tile instanceof IGregTechTileEntity ? ((IGregTechTileEntity) tile) : null;
        final IMetaTileEntity tMeta = tBaseMetaTile != null ? tBaseMetaTile.getMetaTileEntity() : null;
        final GT_MetaTileEntity_MultiParallelBlockBase<?> MultiParallel = tMeta instanceof GT_MetaTileEntity_MultiParallelBlockBase ? ((GT_MetaTileEntity_MultiParallelBlockBase<?>) tMeta) : null;
        final GTMTE_MBBase multiBlockBaseImpact = tMeta instanceof GTMTE_MBBase ? ((GTMTE_MBBase) tMeta) : null;
        final GTMTE_LapPowerStation LapBuffer = tMeta instanceof GTMTE_LapPowerStation ? ((GTMTE_LapPowerStation) tMeta) : null;
        final GT_MetaTileEntity_EM_research Research = tMeta instanceof GT_MetaTileEntity_EM_research ? ((GT_MetaTileEntity_EM_research) tMeta) : null;
        final GTMTE_TowerCommunication towerCommunication = tMeta instanceof GTMTE_TowerCommunication ? ((GTMTE_TowerCommunication) tMeta) : null;
        final GTMTE_NuclearReactorBase<?> reactor = tMeta instanceof GTMTE_NuclearReactorBase ? ((GTMTE_NuclearReactorBase<?>) tMeta) : null;
        final GTMTE_Reactor_Rod_Hatch reactorHatch = tMeta instanceof GTMTE_Reactor_Rod_Hatch ? ((GTMTE_Reactor_Rod_Hatch) tMeta) : null;
        final GTMTE_SpaceSatellite_Receiver towerReciver = tMeta instanceof GTMTE_SpaceSatellite_Receiver ? ((GTMTE_SpaceSatellite_Receiver) tMeta) : null;
        final GTMTE_ParallelHatch_Input parallelHatch_input = tMeta instanceof GTMTE_ParallelHatch_Input ? ((GTMTE_ParallelHatch_Input) tMeta) : null;
        final GTMTE_ParallelHatch_Output parallelHatch_output = tMeta instanceof GTMTE_ParallelHatch_Output ? ((GTMTE_ParallelHatch_Output) tMeta) : null;
        final GT_MetaTileEntity_DigitalChestBase chestBase = tMeta instanceof GT_MetaTileEntity_DigitalChestBase ? ((GT_MetaTileEntity_DigitalChestBase) tMeta) : null;
        final GTMTE_Aerostat aerostat = tMeta instanceof GTMTE_Aerostat ? ((GTMTE_Aerostat) tMeta) : null;
        final GTMTE_MPStabilizer tMatrixStabilizer = tMeta instanceof GTMTE_MPStabilizer ? ((GTMTE_MPStabilizer) tMeta) : null;
        final GTMTE_MPContainment tMatrixContainment = tMeta instanceof GTMTE_MPContainment ? ((GTMTE_MPContainment) tMeta) : null;
        final GTMTE_LongDistancePipelineBase pipeline = tMeta instanceof GTMTE_LongDistancePipelineBase ? ((GTMTE_LongDistancePipelineBase) tMeta) : null;
        final GT_MetaTileEntity_Hatch hatch = tMeta instanceof GT_MetaTileEntity_Hatch ? ((GT_MetaTileEntity_Hatch) tMeta) : null;
        final GTMTE_MESystemProvider meSystemProvider = tMeta instanceof GTMTE_MESystemProvider ? ((GTMTE_MESystemProvider) tMeta) : null;
        final GTMTE_Wind_Generator wind_generator = tMeta instanceof GTMTE_Wind_Generator ? ((GTMTE_Wind_Generator) tMeta) : null;
        final GTMTE_Solar solar = tMeta instanceof GTMTE_Solar ? ((GTMTE_Solar) tMeta) : null;
        final GTMTE_Mining_Coal coal_miner = tMeta instanceof GTMTE_Mining_Coal ? ((GTMTE_Mining_Coal) tMeta) : null;
        final GTMTE_BasicMiner basic_miner = tMeta instanceof GTMTE_BasicMiner ? ((GTMTE_BasicMiner) tMeta) : null;

        if (tMeta != null) {
            
            if (basic_miner != null) {
                currenttip.add("Vein Size: " + tag.getInteger("basic_miner.vein"));
            }
            
            if (coal_miner != null) {
                currenttip.add("Vein Size: " + tag.getInteger("coal_miner.vein"));
            }

            if (solar != null) {
                currenttip.add("Generation: " + tag.getInteger("solar.mOutputSalary") + "EU/t");
            }

            if (wind_generator != null) {
                currenttip.add("Generation: " + GT_Utility.formatNumbers(tag.getInteger("wind_generator.mOutputSalary")) + "EU/t");
                currenttip.add("Capacity: " + GT_Utility.formatNumbers(tag.getInteger("wind_generator.mCapacity")) + " / 1,000,000 EU");
            }

            if (meSystemProvider != null) {
                currenttip.add("Acceleration ME CPU and OC: " + tag.getInteger("mSpeedUp"));
                currenttip.add("Matrix Particles : " + tag.getInteger("mMP") + " U");
            }

            if (hatch != null) {
                if (tag.getInteger("hatchId") > 0) {
                    currenttip.add(EnumChatFormatting.GOLD + "ID: " + tag.getInteger("hatchId") + EnumChatFormatting.RESET);
                }
            }

            if (pipeline != null) {
                final int facing = pipeline.getBaseMetaTileEntity().getFrontFacing();
                if (side == facing) {
                    currenttip.add(EnumChatFormatting.GOLD + trans("waila.pipeline.in") + EnumChatFormatting.RESET);
                } else if (side == ForgeDirection.OPPOSITES[facing]) {
                    currenttip.add(EnumChatFormatting.BLUE + trans("waila.pipeline.out") + EnumChatFormatting.RESET);
                } else {
                    currenttip.add(trans("waila.pipeline.side"));
                }
            }

            if (tMatrixStabilizer != null) {
                currenttip.add(tag.getInteger("mMPSummary") + "");
            }

            if (tMatrixContainment != null) {
                currenttip.add(tag.getInteger("mMPStable") + "");
            }

            if (aerostat != null) {
                if (!tag.getString("aerostatName").isEmpty())
                    currenttip.add(trans("waila.aerostat.namestation") + ": " + EnumChatFormatting.GOLD + tag.getString("aerostatName"));
            }

            if (reactorHatch != null) {
                currenttip.add(trans("waila.reactorhatch.id") + ": " + tag.getInteger("wIDhatch"));
                currenttip.add(trans("waila.reactorhatch.level") + ": " + EnumChatFormatting.YELLOW + (tag.getInteger("wDownRod") * 10) + "%");
                if (!tag.getString("wRodName").isEmpty())
                    currenttip.add(EnumChatFormatting.GREEN + tag.getString("wRodName"));
                currenttip.add(trans("waila.reactorhatch.decay") + ": " + EnumChatFormatting.RED +
                        (tag.getInteger("wSpeedDecay") * tag.getInteger("wDownRod")) + trans("waila.reactorhatch.ds"));
            }

            if (chestBase != null) {
                if (!tag.getString("chestBaseItemName").equals("")) {
                    currenttip.add(EnumChatFormatting.GREEN + tag.getString("chestBaseItemName") + ":");
                    currenttip.add(NumberFormat.getNumberInstance().format(tag.getInteger("chestBaseSizeCurrent")) +
                            " / " + NumberFormat.getNumberInstance().format(tag.getInteger("chestBaseSizeMax")));
                } else {
                    currenttip.add(EnumChatFormatting.RED + trans("waila.inventory.noitem"));
                }
            }

            if (MultiParallel != null && tag.getInteger("Parallel") > 1) {
                String str = tag.getBoolean("connectWithTower")
                        ? EnumChatFormatting.GREEN + trans("waila.connect.yes")
                        : EnumChatFormatting.RED + trans("waila.connect.no");
                currenttip.add(str);
                currenttip.add(trans("waila.parallel.point") + String.format(": %d/%d", tag.getInteger("currentParallel"), tag.getInteger("Parallel")));
            }

            if (LapBuffer != null) {
                currenttip.add(trans("waila.lsc.stored") + ": " + GREEN + NumberFormat.getNumberInstance()
                        .format(tag.getLong("Stored")) + RESET + " " + trans("waila.eu"));
                currenttip.add(trans("waila.lsc.capacity") + ": " + YELLOW + NumberFormat.getNumberInstance()
                        .format(tag.getLong("Capacity")) + RESET + " " + trans("waila.eu"));
                currenttip.add(trans("waila.lsc.in") + ": " + GREEN + NumberFormat.getNumberInstance()
                        .format(tag.getLong("Input")) + RESET + " " + trans("waila.eut"));
                currenttip.add(trans("waila.lsc.out") + ": " + RED + NumberFormat.getNumberInstance()
                        .format(tag.getLong("Output")) + RESET + " " + trans("waila.eut"));
            }

            if (Research != null) {
                currenttip.add(trans("waila.reseatch.computation") + ": " + GREEN + NumberFormat.getNumberInstance()
                        .format(tag.getLong("computationRemaining")) + " / " + YELLOW + NumberFormat
                        .getNumberInstance().format(tag.getInteger("computationRequired")));
            }

            if (multiBlockBaseImpact != null) {
                if (tag.getBoolean("incompleteStructureImpact")) {
                    currenttip.add(RED + trans("waila.multis.incomplete") + RESET);
                }
                currenttip.add((tag.getBoolean("hasProblemsImpact") ? (RED + trans("waila.multis.incomplete"))
                        : GREEN + trans("waila.multis.running")) + RESET + " " + trans("waila.multis.efficiency") + ": "
                        + tag.getFloat("efficiencyImpact") + "%");

                currenttip.add(trans("waila.multis.progress") + String.format(": %d s / %d s", tag.getInteger("progressImpact"),
                        tag.getInteger("maxProgressImpact")));
            }

            if (towerReciver != null) {
                String str = tag.getBoolean("isActiveTowerReciver")
                        ? EnumChatFormatting.GREEN + trans("waila.connect.yes")
                        : EnumChatFormatting.RED + trans("waila.connect.no");
                currenttip.add(str);
            }

            if (parallelHatch_input != null) {
                currenttip.add(tag.getString("ppInMachineName"));
                currenttip.add(trans("waila.parallel.address") + ": " + EnumChatFormatting.YELLOW + tag.getString("ppInMachineAddress"));
                String str = tag.getBoolean("isParallelIN")
                        ? EnumChatFormatting.GREEN + trans("waila.connect.yes")
                        : EnumChatFormatting.RED + trans("waila.connect.no");
                currenttip.add(str);
                currenttip.add(trans("waila.parallel.point") + ": " + tag.getInteger("ppHatchIn"));
            }

            if (parallelHatch_output != null) {
                currenttip.add(tag.getString("ppOutMachineName"));
                currenttip.add(trans("waila.parallel.address") + ": " + EnumChatFormatting.YELLOW + tag.getString("ppOutMachineAddress"));
                String str = tag.getBoolean("isParallelOUT")
                        ? EnumChatFormatting.GREEN + trans("waila.connect.yes")
                        : EnumChatFormatting.RED + trans("waila.connect.no");
                currenttip.add(str);
                currenttip.add(trans("waila.parallel.point") + ": " + tag.getInteger("ppHatchOut"));
            }

            if (towerCommunication != null) {
                String str = tag.getBoolean("towerCommunicationConnect")
                        ? EnumChatFormatting.GREEN + trans("waila.connect.yes")
                        : EnumChatFormatting.RED + trans("waila.connect.no");
                currenttip.add(str);
            }

            if (reactor != null) {
                int temp = tag.getInteger("reactorTemp");
                EnumChatFormatting[] color = {
                        EnumChatFormatting.BLUE,
                        EnumChatFormatting.AQUA,
                        EnumChatFormatting.YELLOW,
                        EnumChatFormatting.RED,
                };
                String colorTemp = "" + (temp > 75 ? color[3] : temp > 50 ? color[2] : temp > 25 ? color[1] : color[0]) + temp;
                String str = trans("waila.reactor.temp") + ": " + colorTemp + "%";
                currenttip.add(str);
                if (tag.getBoolean("wMox")) currenttip.add(color[3] + trans("waila.reactor.mox"));
                if (tag.getBoolean("wFastDecay")) currenttip.add(color[2] + trans("waila.reactor.fastdecay"));
            }
        }

        final AEBaseTile aeBaseTE = tile instanceof AEBaseTile ? (AEBaseTile) tile : null;
        final TileCraftingTile cpu = aeBaseTE instanceof TileCraftingTile ? (TileCraftingTile) aeBaseTE : null;

        if (aeBaseTE != null) {
            if (cpu != null) {
                if (tag.getBoolean("isBigAccelerator")) {
                    currenttip.add("Acceleration: " + tag.getInteger("bigAcceleratorAmount") + " CPU");
                }
            }
        }
    }

    @Override
    @SneakyThrows
    protected void getNBTData(TileEntity tile, NBTTagCompound tag, World world, BlockCoord pos) {
        final IGregTechTileEntity tBaseMetaTile = tile instanceof IGregTechTileEntity ? ((IGregTechTileEntity) tile) : null;
        final IMetaTileEntity tMeta = tBaseMetaTile != null ? tBaseMetaTile.getMetaTileEntity() : null;
        final GT_MetaTileEntity_MultiBlockBase multiBlockBase = tMeta instanceof GT_MetaTileEntity_MultiBlockBase ? ((GT_MetaTileEntity_MultiBlockBase) tMeta) : null;
        final GT_MetaTileEntity_MultiParallelBlockBase<?> MultiParallel = tMeta instanceof GT_MetaTileEntity_MultiParallelBlockBase ? ((GT_MetaTileEntity_MultiParallelBlockBase<?>) tMeta) : null;
        final GTMTE_MBBase multiBlockBaseImpact = tMeta instanceof GTMTE_MBBase ? ((GTMTE_MBBase) tMeta) : null;
        final GTMTE_TowerCommunication towerCommunication = tMeta instanceof GTMTE_TowerCommunication ? ((GTMTE_TowerCommunication) tMeta) : null;
        final GTMTE_NuclearReactorBase<?> reactor = tMeta instanceof GTMTE_NuclearReactorBase ? ((GTMTE_NuclearReactorBase<?>) tMeta) : null;
        final GTMTE_Reactor_Rod_Hatch reactorHatch = tMeta instanceof GTMTE_Reactor_Rod_Hatch ? ((GTMTE_Reactor_Rod_Hatch) tMeta) : null;
        final GTMTE_SpaceSatellite_Receiver towerReciver = tMeta instanceof GTMTE_SpaceSatellite_Receiver ? ((GTMTE_SpaceSatellite_Receiver) tMeta) : null;
        final GTMTE_ParallelHatch_Input parallelHatch_input = tMeta instanceof GTMTE_ParallelHatch_Input ? ((GTMTE_ParallelHatch_Input) tMeta) : null;
        final GTMTE_ParallelHatch_Output parallelHatch_output = tMeta instanceof GTMTE_ParallelHatch_Output ? ((GTMTE_ParallelHatch_Output) tMeta) : null;
        final GT_MetaTileEntity_DigitalChestBase chestBase = tMeta instanceof GT_MetaTileEntity_DigitalChestBase ? ((GT_MetaTileEntity_DigitalChestBase) tMeta) : null;
        final GTMTE_Aerostat aerostat = tMeta instanceof GTMTE_Aerostat ? ((GTMTE_Aerostat) tMeta) : null;
        final GTMTE_MPStabilizer tMatrixStabilizer = tMeta instanceof GTMTE_MPStabilizer ? ((GTMTE_MPStabilizer) tMeta) : null;
        final GTMTE_MPContainment tMatrixContainment = tMeta instanceof GTMTE_MPContainment ? ((GTMTE_MPContainment) tMeta) : null;
        final GT_MetaTileEntity_Hatch hatch = tMeta instanceof GT_MetaTileEntity_Hatch ? ((GT_MetaTileEntity_Hatch) tMeta) : null;
        final GTMTE_MESystemProvider meSystemProvider = tMeta instanceof GTMTE_MESystemProvider ? ((GTMTE_MESystemProvider) tMeta) : null;
        final GTMTE_Wind_Generator wind_generator = tMeta instanceof GTMTE_Wind_Generator ? ((GTMTE_Wind_Generator) tMeta) : null;
        final GTMTE_Solar solar = tMeta instanceof GTMTE_Solar ? ((GTMTE_Solar) tMeta) : null;
        final GTMTE_Mining_Coal coal_miner = tMeta instanceof GTMTE_Mining_Coal ? ((GTMTE_Mining_Coal) tMeta) : null;
        final GTMTE_BasicMiner basic_miner = tMeta instanceof GTMTE_BasicMiner ? ((GTMTE_BasicMiner) tMeta) : null;
        
        if (tMeta != null) {
    
            if (basic_miner != null) {
                tag.setInteger("basic_miner.vein", basic_miner.sizeVeinPreStart - basic_miner.cycleIncrease);
            }
            
            if (coal_miner != null) {
                tag.setInteger("coal_miner.vein", coal_miner.sizeVeinPreStart - coal_miner.cycleIncrease);
            }

            if (solar != null) {
                tag.setInteger("solar.mOutputSalary", (int) solar.maxEUOutput());
            }

            if (wind_generator != null) {
                tag.setInteger("wind_generator.mOutputSalary", wind_generator.mOutputSalary);
                tag.setInteger("wind_generator.mCapacity", wind_generator.mCapacity);
            }

            if (meSystemProvider != null) {
                tag.setInteger("mSpeedUp", meSystemProvider.mSpeedUp);
                tag.setInteger("mMP", meSystemProvider.mMatrixParticlesSummary);
            }

            if (tMatrixStabilizer != null) {
                tag.setInteger("mMPSummary", tMatrixStabilizer.mMPSummary);
            }

            if (tMatrixContainment != null) {
                tag.setInteger("mMPStable", tMatrixContainment.mMPStable);
            }

            if (hatch != null) {
                tag.setInteger("hatchId", hatch.idHatch);
            }

            if (aerostat != null) {
                tag.setString("aerostatName", aerostat.aerName);
            }

            if (reactorHatch != null) {
                tag.setInteger("wIDhatch", reactorHatch.mIDhatch + 1);
                tag.setInteger("wDownRod", reactorHatch.mDownRod);
                if (reactorHatch.mStartReactor)
                    tag.setInteger("wSpeedDecay", reactorHatch.mSpeedDecay);
                tag.setString("wRodName", reactorHatch.mInventory[0] != null ?
                        reactorHatch.mInventory[0].getDisplayName() : "");
            }

            if (chestBase != null) {
                final int stackSizeCurrent = chestBase.getItemCount();
                final int stackSizeMax = chestBase.getMaxItemCount();
                final String itemName = chestBase.mInventory[2] != null ? chestBase.mInventory[2].getDisplayName() : "";
                tag.setInteger("chestBaseSizeCurrent", stackSizeCurrent);
                tag.setInteger("chestBaseSizeMax", stackSizeMax);
                tag.setString("chestBaseItemName", itemName);
            }

            if (towerReciver != null) {
                final boolean isActiveTowerReciver = towerReciver.mIsReceive;
                tag.setBoolean("isActiveTowerReciver", isActiveTowerReciver);
            }

            if (parallelHatch_input != null) {
                final boolean isParallelIN = parallelHatch_input.mTrueRecipe;
                tag.setBoolean("isParallelIN", isParallelIN);
                tag.setInteger("ppHatchIn", parallelHatch_input.mMaxParallel);
                tag.setString("ppInMachineName", parallelHatch_input.machineName);
                tag.setString("ppInMachineAddress", parallelHatch_input.address);
            }

            if (parallelHatch_output != null) {
                final boolean isParallelOUT = parallelHatch_output.mIsTrueRecipe;
                tag.setBoolean("isParallelOUT", isParallelOUT);
                tag.setInteger("ppHatchOut", parallelHatch_output.mMaxParallel);
                tag.setString("ppOutMachineName", parallelHatch_output.machineName);
                tag.setString("ppOutMachineAddress", parallelHatch_output.address);
            }

            if (towerCommunication != null) {
                tag.setBoolean("towerCommunicationConnect", towerCommunication.mIsConnect);
            }

            if (reactor != null) {
                double tScale = (double) reactor.mCurrentTemp / (double) reactor.maxTemperature();
                tScale = tScale <= 0 ? 0 : tScale;
                int temperature = Math.min(((int) (100 * tScale)), 100);
                tag.setInteger("reactorTemp", temperature);
                tag.setBoolean("wMox", reactor.isMoxFuel);
                tag.setBoolean("wFastDecay", reactor.isFastDecay);
            }


            if (MultiParallel != null) {
                final int Parallel = MultiParallel.mParallel;
                tag.setInteger("Parallel", Parallel);
                tag.setInteger("currentParallel", MultiParallel.mCheckParallelCurrent);
                tag.setBoolean("connectWithTower", MultiParallel.mIsConnect);
            }

            if (tMeta instanceof GTMTE_LapPowerStation) {
                GTMTE_LapPowerStation mte = (GTMTE_LapPowerStation) tMeta;
                final long Capacity = mte.capacity;
                final long Stored = mte.stored;
                final long Input = mte.intputLastTick;
                final long Output = mte.outputLastTick;

                tag.setLong("Capacity", Capacity);
                tag.setLong("Stored", Stored);
                tag.setLong("Input", Input);
                tag.setLong("Output", Output);
            }

            if (tMeta instanceof GT_MetaTileEntity_EM_research) {
                GT_MetaTileEntity_EM_research mte = (GT_MetaTileEntity_EM_research) tMeta;
                final long computationRemaining = mte.computationRemaining / 20L;
                final long computationRequired = mte.computationRequired / 20L;

                tag.setLong("computationRemaining", computationRemaining);
                tag.setLong("computationRequired", computationRequired);
            }

            if (multiBlockBaseImpact != null) {
                final int problems =
                        multiBlockBaseImpact.getIdealStatus() - multiBlockBaseImpact.getRepairStatus();
                final float efficiency = multiBlockBaseImpact.mEfficiency / 100.0F;
                final int progress = multiBlockBaseImpact.mProgresstime / 20;
                final int maxProgress = multiBlockBaseImpact.mMaxProgresstime / 20;

                tag.setBoolean("hasProblemsImpact", problems > 0);
                tag.setFloat("efficiencyImpact", efficiency);
                tag.setInteger("progressImpact", progress);
                tag.setInteger("maxProgressImpact", maxProgress);
                tag.setBoolean("incompleteStructureImpact", (tBaseMetaTile.getErrorDisplayID() & 64) != 0);
            }
        }

        final AEBaseTile aeBaseTE = tile instanceof AEBaseTile ? (AEBaseTile) tile : null;
        final TileCraftingTile cpu = aeBaseTE instanceof TileCraftingTile ? (TileCraftingTile) aeBaseTE : null;

        if (aeBaseTE != null) {
            if (cpu != null) {
                if (cpu.isBigAccelerator()) {
                    tag.setBoolean("isBigAccelerator", cpu.isBigAccelerator());
                    tag.setInteger("bigAcceleratorAmount", cpu.getBigAccelerator());
                }
            }
        }

        tile.writeToNBT(tag);
    }

    private String trans(String s) {
        return translateToLocal(s);
    }
}
