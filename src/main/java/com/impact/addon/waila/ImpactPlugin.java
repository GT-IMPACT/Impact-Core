package com.impact.addon.waila;

import static net.minecraft.util.StatCollector.translateToLocal;
import static mcp.mobius.waila.api.SpecialChars.*;

import com.enderio.core.common.util.BlockCoord;
import com.impact.addon.gt.api.multis.ISeparateBus;
import com.impact.addon.gt.api.multis.ISwitchRecipeMap;
import com.impact.common.te.TE_DryingRack;
import com.impact.mods.gregtech.tileentities.basic.GTMTE_LongDistancePipelineBase;
import com.impact.mods.gregtech.tileentities.basic.GTMTE_LongDistancePipelineEnergy;
import com.impact.mods.gregtech.tileentities.basic.GTMTE_Solar;
import com.impact.mods.gregtech.tileentities.multi.generators.green.GTMTE_Wind_Generator;
import com.impact.mods.gregtech.tileentities.multi.generators.nuclear.GTMTE_NuclearReactorBase;
import com.impact.mods.gregtech.tileentities.multi.generators.nuclear.hatch.GTMTE_Reactor_Rod_Hatch;
import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_MBBase;
import com.impact.mods.gregtech.tileentities.multi.implement.GT_MetaTileEntity_MultiParallelBlockBase;
import com.impact.mods.gregtech.tileentities.multi.matrixsystem.GTMTEMESystemProvider;
import com.impact.mods.gregtech.tileentities.multi.matrixsystem.GTMTE_MPContainment;
import com.impact.mods.gregtech.tileentities.multi.matrixsystem.GTMTE_MPStabilizer;
import com.impact.mods.gregtech.tileentities.multi.ores.GTMTE_AdvancedMiner;
import com.impact.mods.gregtech.tileentities.multi.ores.GTMTE_BasicMiner;
import com.impact.mods.gregtech.tileentities.multi.ores.GTMTE_Mining_Coal;
import com.impact.mods.gregtech.tileentities.multi.parallelsystem.*;
import com.impact.mods.gregtech.tileentities.multi.storage.GTMTE_LapPowerStation;
import com.impact.mods.gregtech.tileentities.multi.storage.GTMTE_MultiTank;
import com.impact.mods.gregtech.tileentities.multi.storage.GTMTE_SingleTank;
import com.impact.mods.gregtech.tileentities.multi.units.GTMTE_Aerostat;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

import java.text.NumberFormat;
import java.util.List;

import appeng.tile.AEBaseTile;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.BaseTileEntity;
import gregtech.api.util.GT_Utility;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaRegistrar;
import tterrag.wailaplugins.api.Plugin;
import tterrag.wailaplugins.plugins.PluginBase;

@Plugin(name = "Impact-core", deps = {"impact", "gregtech", "appliedenergistics2"})
public class ImpactPlugin extends PluginBase {

    @Override
    public void load(IWailaRegistrar registrar) {
        super.load(registrar);
        registerBody(BaseTileEntity.class, AEBaseTile.class, TE_DryingRack.class);
        registerNBT(BaseTileEntity.class, AEBaseTile.class, TE_DryingRack.class);
    }

    @Override
    @SuppressWarnings("unused")
    protected void getBody(ItemStack stack, List<String> currenttip, IWailaDataAccessor accessor) {
        final TileEntity tile = accessor.getTileEntity();
        NBTTagCompound tag = accessor.getNBTData();
        final int side = (byte) accessor.getSide().ordinal();

        //region gregtech
        final IGregTechTileEntity tBaseMetaTile = tile instanceof IGregTechTileEntity ? ((IGregTechTileEntity) tile) : null;
        final IMetaTileEntity tMeta = tBaseMetaTile != null ? tBaseMetaTile.getMetaTileEntity() : null;

        final GT_MetaTileEntity_MultiParallelBlockBase<?> MultiParallel = tMeta instanceof GT_MetaTileEntity_MultiParallelBlockBase ? ((GT_MetaTileEntity_MultiParallelBlockBase<?>) tMeta) : null;
        final GTMTE_MBBase multiBlockBaseImpact = tMeta instanceof GTMTE_MBBase ? ((GTMTE_MBBase) tMeta) : null;
        final GTMTE_LapPowerStation LapBuffer = tMeta instanceof GTMTE_LapPowerStation ? ((GTMTE_LapPowerStation) tMeta) : null;
        final GTMTE_TowerCommunication towerCommunication = tMeta instanceof GTMTE_TowerCommunication ? ((GTMTE_TowerCommunication) tMeta) : null;
        final GTMTE_NuclearReactorBase<?> reactor = tMeta instanceof GTMTE_NuclearReactorBase ? ((GTMTE_NuclearReactorBase<?>) tMeta) : null;
        final GTMTE_Reactor_Rod_Hatch reactorHatch = tMeta instanceof GTMTE_Reactor_Rod_Hatch ? ((GTMTE_Reactor_Rod_Hatch) tMeta) : null;
        final GTMTE_CommunicationTower_Receiver towerReciver = tMeta instanceof GTMTE_CommunicationTower_Receiver ? ((GTMTE_CommunicationTower_Receiver) tMeta) : null;
        final GTMTE_ParallelHatch_Input parallelHatch_input = tMeta instanceof GTMTE_ParallelHatch_Input ? ((GTMTE_ParallelHatch_Input) tMeta) : null;
        final GTMTE_ParallelHatch_Output parallelHatch_output = tMeta instanceof GTMTE_ParallelHatch_Output ? ((GTMTE_ParallelHatch_Output) tMeta) : null;

        final GTMTE_Aerostat aerostat = tMeta instanceof GTMTE_Aerostat ? ((GTMTE_Aerostat) tMeta) : null;
        final GTMTE_MPStabilizer tMatrixStabilizer = tMeta instanceof GTMTE_MPStabilizer ? ((GTMTE_MPStabilizer) tMeta) : null;
        final GTMTE_MPContainment tMatrixContainment = tMeta instanceof GTMTE_MPContainment ? ((GTMTE_MPContainment) tMeta) : null;
        final GTMTE_LongDistancePipelineBase pipeline = tMeta instanceof GTMTE_LongDistancePipelineBase ? ((GTMTE_LongDistancePipelineBase) tMeta) : null;

        final GTMTEMESystemProvider meSystemProvider = tMeta instanceof GTMTEMESystemProvider ? ((GTMTEMESystemProvider) tMeta) : null;
        final GTMTE_Wind_Generator wind_generator = tMeta instanceof GTMTE_Wind_Generator ? ((GTMTE_Wind_Generator) tMeta) : null;
        final GTMTE_Solar solar = tMeta instanceof GTMTE_Solar ? ((GTMTE_Solar) tMeta) : null;
        final GTMTE_Mining_Coal coal_miner = tMeta instanceof GTMTE_Mining_Coal ? ((GTMTE_Mining_Coal) tMeta) : null;
        final GTMTE_BasicMiner basic_miner = tMeta instanceof GTMTE_BasicMiner ? ((GTMTE_BasicMiner) tMeta) : null;
        final GTMTE_AdvancedMiner adv_miner = tMeta instanceof GTMTE_AdvancedMiner ? ((GTMTE_AdvancedMiner) tMeta) : null;
        final GTMTE_SingleTank single_tank = tMeta instanceof GTMTE_SingleTank ? ((GTMTE_SingleTank) tMeta) : null;
        final GTMTE_MultiTank multi_tank = tMeta instanceof GTMTE_MultiTank ? ((GTMTE_MultiTank) tMeta) : null;

        if (tMeta != null) {

            if (tMeta instanceof ISwitchRecipeMap) {
                String map = tag.getString("recipe_map_switch");
                if (!map.isEmpty()) {
                    currenttip.add("Recipe Map: " + EnumChatFormatting.YELLOW + map);
                }
            }
            if (tMeta instanceof ISeparateBus && ((ISeparateBus) tMeta).hasSeparate()) {
                boolean map = tag.getBoolean("is_separated");
                currenttip.add("Separated Mode: " + (map ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled"));
            }

            if (adv_miner != null) {
                String size = "?";
                if (!tag.getBoolean("incompleteStructure")) {
                    size = GT_Utility.formatNumbers(tag.getInteger("adv_miner.vein"));
                }
                currenttip.add("Vein Size: " + size + "%");
                currenttip.add("Layer: " + tag.getInteger("adv_miner.layer"));
            }

            if (basic_miner != null) {
                String size = "?";
                if (!tag.getBoolean("incompleteStructure")) {
                    size = GT_Utility.formatNumbers(tag.getInteger("basic_miner.vein"));
                }
                currenttip.add("Chunk Size: " + size + "%");
                currenttip.add("Layer: " + tag.getInteger("basic_miner.layer"));
            }

            if (coal_miner != null) {
                String size = "?";
                if (!tag.getBoolean("incompleteStructure")) {
                    size = GT_Utility.formatNumbers(tag.getInteger("coal_miner.vein"));
                }
                currenttip.add("Chunk Size: " + size + "%");
            }

            if (solar != null) {
                currenttip.add("Generation: " + GT_Utility.formatNumbers(tag.getInteger("solar.mOutputSalary")) + "EU/t");
            }

            if (wind_generator != null) {
                currenttip.add("Generation: " + GT_Utility.formatNumbers(tag.getInteger("wind_generator.mOutputSalary")) + "EU/t");
                currenttip.add("Capacity: " + GT_Utility.formatNumbers(tag.getInteger("wind_generator.mCapacity")) + " / 1,000,000 EU");
            }

            if (meSystemProvider != null) {
                currenttip.add("Matrix Particles : " + tag.getInteger("mMP") + " U");
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

                if (pipeline instanceof GTMTE_LongDistancePipelineEnergy) {

                    currenttip.add(tag.getBoolean("pipeline.isSender")
                            ? EnumChatFormatting.GOLD + trans("waila.pipeline.sender") + EnumChatFormatting.RESET
                            : EnumChatFormatting.BLUE + trans("waila.pipeline.recipient") + EnumChatFormatting.RESET);

                    long distance = tag.getLong("pipeline.distance");
                    currenttip.add(String.format(trans("waila.pipeline.distance"),
                            distance < pipeline.getMinDistance() ? EnumChatFormatting.RED + trans("waila.pipeline.low_distance") : distance));
                }
            }

            if (tMatrixStabilizer != null) {
                currenttip.add(GT_Utility.formatNumbers(tag.getInteger("mMPSummary")) + " particles");
            }

            if (tMatrixContainment != null) {
                currenttip.add(GT_Utility.formatNumbers(tag.getInteger("mMPStable")) + " particles");
            }

            if (aerostat != null) {
                String name = tag.getString("aerostatName");
                if (!name.isEmpty())
                    currenttip.add(trans("waila.aerostat.namestation") + ": " + EnumChatFormatting.GOLD + name);
                else
                    currenttip.add(EnumChatFormatting.RED + trans("waila.aerostat.no_valid_station"));

                String owner = tag.getString("aerostatOwner");
                if (!owner.isEmpty())
                    currenttip.add(trans("waila.aerostat.owner") + ": " + EnumChatFormatting.GREEN + owner);
            }

            if (reactorHatch != null) {
                currenttip.add(trans("waila.reactorhatch.id") + ": " + tag.getInteger("wIDhatch"));
                currenttip.add(trans("waila.reactorhatch.level") + ": " + EnumChatFormatting.YELLOW + (tag.getInteger("wDownRod") * 10) + "%");
                if (!tag.getString("wRodName").isEmpty())
                    currenttip.add(EnumChatFormatting.GREEN + tag.getString("wRodName"));
                currenttip.add(trans("waila.reactorhatch.decay") + ": " + EnumChatFormatting.RED +
                        (tag.getInteger("wSpeedDecay") * tag.getInteger("wDownRod")) + trans("waila.reactorhatch.ds"));
            }

            if (MultiParallel != null && tag.getBoolean("hasShowConnect")) {

                String str = tag.getBoolean("connectWithTower")
                        ? EnumChatFormatting.GREEN + trans("waila.connect.yes")
                        : EnumChatFormatting.RED + trans("waila.connect.no");
                currenttip.add(str);

                if (!(MultiParallel instanceof GTMTE_ParallelComputer)) {
                    currenttip.add(trans("waila.parallel.point") + String.format(": %d/%d", tag.getInteger("currentParallel"), tag.getInteger("Parallel")));
                }
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

            if (single_tank != null || multi_tank != null) {
                int maxFluids = multi_tank != null ? multi_tank.MAX_DISTINCT_FLUIDS : single_tank.MAX_DISTINCT_FLUIDS;
                int capacity = tag.getInteger("capacityPerFluid");
                final NBTTagCompound fluidsTag = (NBTTagCompound) tag.getTag("fluids");
                for (int i = 0; i < maxFluids; i++) {
                    final NBTTagCompound fnbt = (NBTTagCompound) fluidsTag.getTag("" + i);
                    if (fnbt == null) {
                        continue;
                    }
                    FluidStack fs = FluidStack.loadFluidStackFromNBT(fnbt);
                    currenttip.add(
                            NumberFormat.getNumberInstance().format(fs.amount) + "L /" +
						    NumberFormat.getNumberInstance().format(capacity) + "L: " +
                            fs.getLocalizedName());
                }
            }

        }
        //endregion

        final TE_DryingRack dryingRack = tile instanceof TE_DryingRack ? (TE_DryingRack) tile : null;
        if (dryingRack != null) {
            currenttip.add((tag.getInteger("dryingRack.time") / 20) + " / " + (tag.getInteger("dryingRack.maxTime") / 20) + "s");
        }
    }

    @Override
    protected void getNBTData(TileEntity tile, NBTTagCompound tag, World world, BlockCoord pos) {
        final IGregTechTileEntity tBaseMetaTile = tile instanceof IGregTechTileEntity ? ((IGregTechTileEntity) tile) : null;
        final IMetaTileEntity tMeta = tBaseMetaTile != null ? tBaseMetaTile.getMetaTileEntity() : null;

        final TE_DryingRack dryingRack = tile instanceof TE_DryingRack ? (TE_DryingRack) tile : null;

        final GT_MetaTileEntity_MultiParallelBlockBase<?> MultiParallel = tMeta instanceof GT_MetaTileEntity_MultiParallelBlockBase ? ((GT_MetaTileEntity_MultiParallelBlockBase<?>) tMeta) : null;
        final GTMTE_MBBase multiBlockBaseImpact = tMeta instanceof GTMTE_MBBase ? ((GTMTE_MBBase) tMeta) : null;
        final GTMTE_TowerCommunication towerCommunication = tMeta instanceof GTMTE_TowerCommunication ? ((GTMTE_TowerCommunication) tMeta) : null;
        final GTMTE_NuclearReactorBase<?> reactor = tMeta instanceof GTMTE_NuclearReactorBase ? ((GTMTE_NuclearReactorBase<?>) tMeta) : null;
        final GTMTE_Reactor_Rod_Hatch reactorHatch = tMeta instanceof GTMTE_Reactor_Rod_Hatch ? ((GTMTE_Reactor_Rod_Hatch) tMeta) : null;
        final GTMTE_CommunicationTower_Receiver towerReciver = tMeta instanceof GTMTE_CommunicationTower_Receiver ? ((GTMTE_CommunicationTower_Receiver) tMeta) : null;
        final GTMTE_ParallelHatch_Input parallelHatch_input = tMeta instanceof GTMTE_ParallelHatch_Input ? ((GTMTE_ParallelHatch_Input) tMeta) : null;
        final GTMTE_ParallelHatch_Output parallelHatch_output = tMeta instanceof GTMTE_ParallelHatch_Output ? ((GTMTE_ParallelHatch_Output) tMeta) : null;

        final GTMTE_Aerostat aerostat = tMeta instanceof GTMTE_Aerostat ? ((GTMTE_Aerostat) tMeta) : null;
        final GTMTE_MPStabilizer tMatrixStabilizer = tMeta instanceof GTMTE_MPStabilizer ? ((GTMTE_MPStabilizer) tMeta) : null;
        final GTMTE_MPContainment tMatrixContainment = tMeta instanceof GTMTE_MPContainment ? ((GTMTE_MPContainment) tMeta) : null;

        final GTMTEMESystemProvider meSystemProvider = tMeta instanceof GTMTEMESystemProvider ? ((GTMTEMESystemProvider) tMeta) : null;
        final GTMTE_Wind_Generator wind_generator = tMeta instanceof GTMTE_Wind_Generator ? ((GTMTE_Wind_Generator) tMeta) : null;
        final GTMTE_Solar solar = tMeta instanceof GTMTE_Solar ? ((GTMTE_Solar) tMeta) : null;
        final GTMTE_Mining_Coal coal_miner = tMeta instanceof GTMTE_Mining_Coal ? ((GTMTE_Mining_Coal) tMeta) : null;
        final GTMTE_BasicMiner basic_miner = tMeta instanceof GTMTE_BasicMiner ? ((GTMTE_BasicMiner) tMeta) : null;
        final GTMTE_AdvancedMiner adv_miner = tMeta instanceof GTMTE_AdvancedMiner ? ((GTMTE_AdvancedMiner) tMeta) : null;

        final GTMTE_LongDistancePipelineBase pipeline = tMeta instanceof GTMTE_LongDistancePipelineBase ? ((GTMTE_LongDistancePipelineBase) tMeta) : null;

        if (tMeta != null) {

            if (tMeta instanceof ISwitchRecipeMap) {
                tag.setString("recipe_map_switch", ((ISwitchRecipeMap) tMeta).getMapName());
            }

            if (tMeta instanceof ISeparateBus && ((ISeparateBus) tMeta).hasSeparate()) {
                tag.setBoolean("is_separated", ((ISeparateBus) tMeta).isSeparated());
            }

            if (pipeline != null) {
                if (pipeline instanceof GTMTE_LongDistancePipelineEnergy) {
                    tag.setLong("pipeline.distance", pipeline.getDistance());
                    tag.setBoolean("pipeline.isSender", pipeline.isSender());
                }
            }

            if (adv_miner != null) {
                tag.setInteger("adv_miner.vein", (int) Math.round(100 * ((double) adv_miner.sizeVeinPreStart / (double) adv_miner.maxVeinSize)));
                tag.setInteger("adv_miner.layer", adv_miner.layer);
            }

            if (basic_miner != null) {
                tag.setInteger("basic_miner.vein", basic_miner.sizeVeinPreStart);
                tag.setInteger("basic_miner.layer", basic_miner.layer);
            }

            if (coal_miner != null) {
                tag.setInteger("coal_miner.vein", coal_miner.sizeVeinPreStart);
                tag.setInteger("coal_miner.cycles", coal_miner.cycleIncrease);
            }

            if (solar != null) {
                tag.setInteger("solar.mOutputSalary", (int) solar.maxEUOutput());
            }

            if (wind_generator != null) {
                tag.setInteger("wind_generator.mOutputSalary", wind_generator.mOutputSalary);
                tag.setInteger("wind_generator.mCapacity", wind_generator.mCapacity);
            }

            if (meSystemProvider != null) {
                tag.setInteger("mMP", meSystemProvider.getMMatrixParticlesSummary());
            }

            if (tMatrixStabilizer != null) {
                tag.setInteger("mMPSummary", tMatrixStabilizer.mMPSummary);
            }

            if (tMatrixContainment != null) {
                tag.setInteger("mMPStable", tMatrixContainment.mMPStable);
            }

            if (aerostat != null) {
                tag.setString("aerostatName", aerostat.getName());
                tag.setString("aerostatOwner", aerostat.getOwner());
            }

            if (reactorHatch != null) {
                tag.setInteger("wIDhatch", reactorHatch.mIDhatch + 1);
                tag.setInteger("wDownRod", reactorHatch.mDownRod);
                if (reactorHatch.mStartReactor) tag.setInteger("wSpeedDecay", reactorHatch.mSpeedDecay);
                tag.setString("wRodName", reactorHatch.mInventory[0] != null ? reactorHatch.mInventory[0].getDisplayName() : "");
            }

            if (towerReciver != null) {
                final boolean isActiveTowerReciver = towerReciver.isConnected;
                tag.setBoolean("isActiveTowerReciver", isActiveTowerReciver);
            }

            if (parallelHatch_input != null) {
                final boolean isParallelIN = parallelHatch_input.isConnected;
                tag.setBoolean("isParallelIN", isParallelIN);
                tag.setInteger("ppHatchIn", parallelHatch_input.mMaxParallel);
                tag.setString("ppInMachineName", parallelHatch_input.machineName);
                tag.setString("ppInMachineAddress", parallelHatch_input.address);
            }

            if (parallelHatch_output != null) {
                final boolean isParallelOUT = parallelHatch_output.isConnected;
                tag.setBoolean("isParallelOUT", isParallelOUT);
                tag.setInteger("ppHatchOut", parallelHatch_output.mMaxParallel);
                tag.setString("ppOutMachineName", parallelHatch_output.machineName);
                tag.setString("ppOutMachineAddress", parallelHatch_output.address);
            }

            if (towerCommunication != null) {
                tag.setBoolean("towerCommunicationConnect", towerCommunication.isSatelliteConnected());
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
                if (!(MultiParallel instanceof GTMTE_ParallelComputer)) {
                    final int Parallel = MultiParallel.mParallel;
                    tag.setInteger("Parallel", Parallel);
                    tag.setInteger("currentParallel", MultiParallel.mCheckParallelCurrent);
                }
                tag.setBoolean("hasShowConnect", MultiParallel.hasShowConnect());
                tag.setBoolean("connectWithTower", MultiParallel.isSatelliteConnected());
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

        if (dryingRack != null) {
            tag.setInteger("dryingRack.time", dryingRack.currentTime);
            tag.setInteger("dryingRack.maxTime", dryingRack.maxTime);
        }

        tile.writeToNBT(tag);
    }

    private String trans(String s) {
        return translateToLocal(s);
    }
}
