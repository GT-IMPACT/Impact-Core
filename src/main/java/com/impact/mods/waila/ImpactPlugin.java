package com.impact.mods.waila;

import appeng.tile.AEBaseTile;
import appeng.tile.crafting.TileCraftingTile;
import com.enderio.core.common.util.BlockCoord;
import com.github.technus.tectech.thing.metaTileEntity.multi.GT_MetaTileEntity_EM_research;
import com.impact.api.multis.ISeparateBus;
import com.impact.api.multis.ISwitchRecipeMap;
import com.impact.common.te.TE_DryingRack;
import com.impact.mods.gregtech.tileentities.basic.GTMTE_LongDistancePipelineBase;
import com.impact.mods.gregtech.tileentities.basic.GTMTE_Solar;
import com.impact.mods.gregtech.tileentities.multi.generators.green.GTMTE_Wind_Generator;
import com.impact.mods.gregtech.tileentities.multi.generators.nuclear.GTMTE_NuclearReactorBase;
import com.impact.mods.gregtech.tileentities.multi.generators.nuclear.hatch.GTMTE_Reactor_Rod_Hatch;
import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_MBBase;
import com.impact.mods.gregtech.tileentities.multi.implement.GT_MetaTileEntity_MultiParallelBlockBase;
import com.impact.mods.gregtech.tileentities.multi.matrixsystem.GTMTE_MESystemProvider;
import com.impact.mods.gregtech.tileentities.multi.matrixsystem.GTMTE_MPContainment;
import com.impact.mods.gregtech.tileentities.multi.matrixsystem.GTMTE_MPStabilizer;
import com.impact.mods.gregtech.tileentities.multi.ores.GTMTE_AdvancedMiner;
import com.impact.mods.gregtech.tileentities.multi.ores.GTMTE_BasicMiner;
import com.impact.mods.gregtech.tileentities.multi.ores.GTMTE_Mining_Coal;
import com.impact.mods.gregtech.tileentities.multi.parallelsystem.GTMTE_ParallelHatch_Input;
import com.impact.mods.gregtech.tileentities.multi.parallelsystem.GTMTE_ParallelHatch_Output;
import com.impact.mods.gregtech.tileentities.multi.parallelsystem.GTMTE_CommunicationTower_Receiver;
import com.impact.mods.gregtech.tileentities.multi.parallelsystem.GTMTE_TowerCommunication;
import com.impact.mods.gregtech.tileentities.multi.storage.GTMTE_LapPowerStation;
import com.impact.mods.gregtech.tileentities.multi.storage.GTMTE_MultiTank;
import com.impact.mods.gregtech.tileentities.multi.storage.GTMTE_SingleTank;
import com.impact.mods.gregtech.tileentities.multi.units.GTMTE_Aerostat;
import gregtech.api.enums.Dyes;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.BaseMetaPipeEntity;
import gregtech.api.metatileentity.BaseMetaTileEntity;
import gregtech.api.metatileentity.BaseTileEntity;
import gregtech.api.metatileentity.implementations.*;
import gregtech.api.util.GT_LanguageManager;
import gregtech.api.util.GT_Utility;
import gregtech.common.covers.GT_Cover_Fluidfilter;
import gregtech.common.tileentities.boilers.GT_MetaTileEntity_Boiler_Solar;
import gregtech.common.tileentities.machines.multi.GT_MetaTileEntity_PrimitiveBlastFurnace;
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
import net.minecraftforge.fluids.FluidStack;
import tterrag.wailaplugins.api.Plugin;
import tterrag.wailaplugins.plugins.PluginBase;

import java.text.NumberFormat;
import java.util.List;

import static com.impact.util.Utilits.translateGTItemStack;
import static mcp.mobius.waila.api.SpecialChars.*;
import static net.minecraft.util.StatCollector.translateToLocal;

@Plugin(name = "Impact-core", deps = {"impact", "gregtech", "appliedenergistics2"})
public class ImpactPlugin extends PluginBase {

    @Override
    public void load(IWailaRegistrar registrar) {
        super.load(registrar);
        addConfig("machineFacing");
        addConfig("transformer");
        addConfig("solar");
        addConfig("multiblock");
        addConfig("fluidFilter");
        addConfig("basicmachine");
        registerBody(BaseTileEntity.class, AEBaseTile.class, TE_DryingRack.class);
        registerNBT(BaseTileEntity.class, AEBaseTile.class, TE_DryingRack.class);
    }

    @Override
    @SneakyThrows
    @SuppressWarnings("unused")
    protected void getBody(ItemStack stack, List<String> currenttip, IWailaDataAccessor accessor) {
        final TileEntity tile = accessor.getTileEntity();
        MovingObjectPosition pos = accessor.getPosition();
        NBTTagCompound tag = accessor.getNBTData();
        final int side = (byte) accessor.getSide().ordinal();
        
        //region gregtech
        final IGregTechTileEntity tBaseMetaTile = tile instanceof IGregTechTileEntity ? ((IGregTechTileEntity) tile) : null;
        final IMetaTileEntity tMeta = tBaseMetaTile != null ? tBaseMetaTile.getMetaTileEntity() : null;
        final BaseMetaTileEntity mBaseMetaTileEntity = tile instanceof  BaseMetaTileEntity ? ((BaseMetaTileEntity) tile) : null;
    
        final GT_MetaTileEntity_MultiBlockBase multiBlockBase = tMeta instanceof GT_MetaTileEntity_MultiBlockBase ? ((GT_MetaTileEntity_MultiBlockBase) tMeta) : null;
        final GT_MetaTileEntity_BasicMachine BasicMachine = tMeta instanceof GT_MetaTileEntity_BasicMachine ? ((GT_MetaTileEntity_BasicMachine) tMeta) : null;
        final GT_MetaTileEntity_BasicBatteryBuffer bateryBuffer = tMeta instanceof GT_MetaTileEntity_BasicBatteryBuffer ? ((GT_MetaTileEntity_BasicBatteryBuffer) tMeta) : null;
        
        final GT_MetaTileEntity_MultiParallelBlockBase<?> MultiParallel = tMeta instanceof GT_MetaTileEntity_MultiParallelBlockBase ? ((GT_MetaTileEntity_MultiParallelBlockBase<?>) tMeta) : null;
        final GTMTE_MBBase multiBlockBaseImpact = tMeta instanceof GTMTE_MBBase ? ((GTMTE_MBBase) tMeta) : null;
        final GTMTE_LapPowerStation LapBuffer = tMeta instanceof GTMTE_LapPowerStation ? ((GTMTE_LapPowerStation) tMeta) : null;
        final GT_MetaTileEntity_EM_research Research = tMeta instanceof GT_MetaTileEntity_EM_research ? ((GT_MetaTileEntity_EM_research) tMeta) : null;
        final GTMTE_TowerCommunication towerCommunication = tMeta instanceof GTMTE_TowerCommunication ? ((GTMTE_TowerCommunication) tMeta) : null;
        final GTMTE_NuclearReactorBase<?> reactor = tMeta instanceof GTMTE_NuclearReactorBase ? ((GTMTE_NuclearReactorBase<?>) tMeta) : null;
        final GTMTE_Reactor_Rod_Hatch reactorHatch = tMeta instanceof GTMTE_Reactor_Rod_Hatch ? ((GTMTE_Reactor_Rod_Hatch) tMeta) : null;
        final GTMTE_CommunicationTower_Receiver towerReciver = tMeta instanceof GTMTE_CommunicationTower_Receiver ? ((GTMTE_CommunicationTower_Receiver) tMeta) : null;
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
        final GTMTE_AdvancedMiner adv_miner = tMeta instanceof GTMTE_AdvancedMiner ? ((GTMTE_AdvancedMiner) tMeta) : null;
        final GTMTE_SingleTank single_tank = tMeta instanceof GTMTE_SingleTank ? ((GTMTE_SingleTank) tMeta) : null;
        final GTMTE_MultiTank multi_tank = tMeta instanceof GTMTE_MultiTank ? ((GTMTE_MultiTank) tMeta) : null;
    
        final boolean showTransformer = tMeta instanceof GT_MetaTileEntity_Transformer && getConfig("transformer");
        final boolean showSolar = tMeta instanceof GT_MetaTileEntity_Boiler_Solar && getConfig("solar");
        final boolean allowedToWork = tag.hasKey("isAllowedToWork") && tag.getBoolean("isAllowedToWork");

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
    
            if (tBaseMetaTile != null) {
                if (getConfig("fluidFilter")){
                    final String filterKey = "filterInfo" + side;
                    if (tag.hasKey(filterKey)) {
                        currenttip.add(tag.getString(filterKey));
                    }
                }
                if (!tag.getString("gt_colorization").equals("INVALID COLOR")) {
                    currenttip.add("Color: " + tag.getString("gt_colorization"));
                }
            }
            
            if (adv_miner != null) {
                currenttip.add("Vein Size: " + GT_Utility.formatNumbers(tag.getInteger("adv_miner.vein")));
                currenttip.add("Layer: " + tag.getInteger("adv_miner.layer"));
            }
            
            if (basic_miner != null) {
                currenttip.add("Chunk Size: " + GT_Utility.formatNumbers(tag.getInteger("basic_miner.vein")));
                currenttip.add("Layer: " + tag.getInteger("basic_miner.layer"));
            }
            
            if (coal_miner != null) {
                currenttip.add("Chunk Size: " + GT_Utility.formatNumbers(tag.getInteger("coal_miner.vein")));
            }

            if (solar != null) {
                currenttip.add("Generation: " + GT_Utility.formatNumbers(tag.getInteger("solar.mOutputSalary")) + "EU/t");
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
                currenttip.add("Distance: " + tag.getLong("pipeline.distance") + EnumChatFormatting.RESET);
                if (side == facing) {
                    currenttip.add(EnumChatFormatting.GOLD + trans("waila.pipeline.in") + EnumChatFormatting.RESET);
                } else if (side == ForgeDirection.OPPOSITES[facing]) {
                    currenttip.add(EnumChatFormatting.BLUE + trans("waila.pipeline.out") + EnumChatFormatting.RESET);
                } else {
                    currenttip.add(trans("waila.pipeline.side"));
                }
            }

            if (tMatrixStabilizer != null) {
                currenttip.add(GT_Utility.formatNumbers(tag.getInteger("mMPSummary")) + " particles");
            }

            if (tMatrixContainment != null) {
                currenttip.add(GT_Utility.formatNumbers(tag.getInteger("mMPStable")) + " particles");
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
                if (!tag.getString("chestBaseItemName").isEmpty()) {
                    currenttip.add(EnumChatFormatting.GREEN + tag.getString("chestBaseItemName"));
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
   
            String facingStr = "Facing";
            if (showTransformer && tag.hasKey("isAllowedToWork")) {
                currenttip.add(
                        String.format(
                                "%s %d(%dA) -> %d(%dA)",
                                (allowedToWork ? (GREEN + "Step Down") : (RED + "Step Up")) + RESET,
                                tag.getLong("maxEUInput"),
                                tag.getLong("maxAmperesIn"),
                                tag.getLong("maxEUOutput"),
                                tag.getLong("maxAmperesOut")
                        )
                );
                facingStr = tag.getBoolean("isAllowedToWork") ? "Input" : "Output";
            }
    
            if (showSolar && tag.hasKey("calcificationOutput")) {
                currenttip.add(String.format((GOLD + "Solar Boiler Output: " + RESET + "%d/%d L/s"), tag.getInteger("calcificationOutput"), tag.getInteger("maxCalcificationOutput")));
            }
    
            if (tMeta instanceof GT_MetaTileEntity_PrimitiveBlastFurnace) {
                if(tag.getBoolean("incompleteStructurePrimitiveBlastFurnace")) {
                    currenttip.add(RED + trans("waila.incompletestructure") + RESET);
                }
        
                if (tag.getInteger("progressPrimitiveBlastFurnace") <= 20 && tag.getInteger("maxProgressPrimitiveBlastFurnace") <= 20) {
                    currenttip.add(trans("waila.progress") + String.format(": %d t / %d t", tag.getInteger("progressPrimitiveBlastFurnace"), tag.getInteger("maxProgressPrimitiveBlastFurnace")));
                } else {
                    currenttip.add(String.format(trans("waila.progress") + ": %d s / %d s", tag.getInteger("progressPrimitiveBlastFurnace") / 20, tag.getInteger("maxProgressPrimitiveBlastFurnace") / 20));
                }
            }
    
            if (mBaseMetaTileEntity != null && getConfig("machineFacing")) {
                final int facing = mBaseMetaTileEntity.getFrontFacing();
                if(showTransformer) {
                    if((side == facing && allowedToWork) || (side != facing && !allowedToWork)) {
                        currenttip.add(String.format(GOLD + "Input:" + RESET + " %d(%dA)", tag.getLong("maxEUInput"), tag.getLong("maxAmperesIn")));
                    } else {
                        currenttip.add(String.format(BLUE + "Output:" + RESET + " %d(%dA)", tag.getLong("maxEUOutput"), tag.getLong("maxAmperesOut")));
                    }
                } else {
                    currenttip.add(String.format("%s: %s", facingStr, ForgeDirection.getOrientation(facing).name()));
                }
            }
    
            if (BasicMachine != null && getConfig("basicmachine")) {
        
                if (tag.getInteger("progressSingleBlock") <= 20 && tag.getInteger("maxProgressSingleBlock") <= 20 ) {
                    currenttip.add(trans("waila.progress") + String.format(": %d t / %d t", tag.getInteger("progressSingleBlock"), tag.getInteger("maxProgressSingleBlock")));
                } else {
                    currenttip.add(trans("waila.progress") + String.format(": %d s / %d s", tag.getInteger("progressSingleBlock") / 20, tag.getInteger("maxProgressSingleBlock") / 20));
                }
                currenttip.add(trans("waila.consumption") + ": " + RED + tag.getInteger("EUOut") + RESET + " " + trans("waila.eut"));
            }
    
            if(multiBlockBase != null && getConfig("multiblock")) {
                if(tag.getBoolean("incompleteStructure")) {
                    currenttip.add(RED + trans("waila.incompletestructure") + RESET);
                }
                currenttip.add((tag.getBoolean("hasProblems") ? (RED + trans("waila.maintenance")) : GREEN + trans("waila.running")) + RESET + "  " + trans("waila.efficiency") + " : " + tag.getFloat("efficiency") + "%");
        
                if (tag.getInteger("progress") <= 20 && tag.getInteger("maxProgress") <= 20 ) {
                    currenttip.add(trans("waila.progress") + String.format(": %d t / %d t", tag.getInteger("progress"), tag.getInteger("maxProgress")));
                } else {
                    currenttip.add(trans("waila.progress") + String.format(": %d s / %d s", tag.getInteger("progress") / 20, tag.getInteger("maxProgress") / 20));
                }
            }
    
            if(bateryBuffer != null && getConfig("basicmachine")) {
                currenttip.add(trans("waila.usedcapacity") + ": " + GREEN + GT_Utility.formatNumbers(tag.getLong("nowStorage")) + RESET + " " + trans("waila.eu"));
                currenttip.add(trans("waila.totalcapacity") + ": " + YELLOW + GT_Utility.formatNumbers(tag.getLong("maxStorage")) + RESET + " " + trans("waila.eu"));
                currenttip.add(trans("waila.input") + ": " + GREEN + GT_Utility.formatNumbers(tag.getLong("energyInput")) + RESET + " " + trans("waila.eut"));
                currenttip.add(trans("waila.output") + ": " + RED + GT_Utility.formatNumbers(tag.getLong("energyOutput")) + RESET + " " + trans("waila.eut"));
            }
        }
        //endregion

        final AEBaseTile aeBaseTE = tile instanceof AEBaseTile ? (AEBaseTile) tile : null;
        final TileCraftingTile cpu = aeBaseTE instanceof TileCraftingTile ? (TileCraftingTile) aeBaseTE : null;

        if (aeBaseTE != null) {
            if (cpu != null) {
                if (tag.getBoolean("isBigAccelerator")) {
                    currenttip.add("Acceleration: " + tag.getInteger("bigAcceleratorAmount") + " CPU");
                }
            }
        }
        
        final TE_DryingRack dryingRack = tile instanceof TE_DryingRack ? (TE_DryingRack) tile : null;
        if (dryingRack != null) {
            currenttip.add((tag.getInteger("dryingRack.time") / 20) + " / " + (tag.getInteger("dryingRack.maxTime") / 20) + "s");
        }
    }

    @Override
    @SneakyThrows
    protected void getNBTData(TileEntity tile, NBTTagCompound tag, World world, BlockCoord pos) {
        final IGregTechTileEntity tBaseMetaTile = tile instanceof IGregTechTileEntity ? ((IGregTechTileEntity) tile) : null;
        final IMetaTileEntity tMeta = tBaseMetaTile != null ? tBaseMetaTile.getMetaTileEntity() : null;
    
        final TE_DryingRack dryingRack = tile instanceof TE_DryingRack ? (TE_DryingRack) tile : null;
    
        final GT_MetaTileEntity_BasicMachine BasicMachine = tMeta instanceof GT_MetaTileEntity_BasicMachine ? ((GT_MetaTileEntity_BasicMachine) tMeta) : null;
        final GT_MetaTileEntity_BasicBatteryBuffer bateryBuffer = tMeta instanceof GT_MetaTileEntity_BasicBatteryBuffer ? ((GT_MetaTileEntity_BasicBatteryBuffer) tMeta) : null;
        
        final GT_MetaTileEntity_MultiBlockBase multiBlockBase = tMeta instanceof GT_MetaTileEntity_MultiBlockBase ? ((GT_MetaTileEntity_MultiBlockBase) tMeta) : null;
        final GT_MetaTileEntity_MultiParallelBlockBase<?> MultiParallel = tMeta instanceof GT_MetaTileEntity_MultiParallelBlockBase ? ((GT_MetaTileEntity_MultiParallelBlockBase<?>) tMeta) : null;
        final GTMTE_MBBase multiBlockBaseImpact = tMeta instanceof GTMTE_MBBase ? ((GTMTE_MBBase) tMeta) : null;
        final GTMTE_TowerCommunication towerCommunication = tMeta instanceof GTMTE_TowerCommunication ? ((GTMTE_TowerCommunication) tMeta) : null;
        final GTMTE_NuclearReactorBase<?> reactor = tMeta instanceof GTMTE_NuclearReactorBase ? ((GTMTE_NuclearReactorBase<?>) tMeta) : null;
        final GTMTE_Reactor_Rod_Hatch reactorHatch = tMeta instanceof GTMTE_Reactor_Rod_Hatch ? ((GTMTE_Reactor_Rod_Hatch) tMeta) : null;
        final GTMTE_CommunicationTower_Receiver towerReciver = tMeta instanceof GTMTE_CommunicationTower_Receiver ? ((GTMTE_CommunicationTower_Receiver) tMeta) : null;
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
        final GTMTE_AdvancedMiner adv_miner = tMeta instanceof GTMTE_AdvancedMiner ? ((GTMTE_AdvancedMiner) tMeta) : null;
    
        final GTMTE_LongDistancePipelineBase pipeline = tMeta instanceof GTMTE_LongDistancePipelineBase ? ((GTMTE_LongDistancePipelineBase) tMeta) : null;
        
        if (tMeta != null) {
            
            if (tMeta instanceof ISwitchRecipeMap) {
                tag.setString("recipe_map_switch", ((ISwitchRecipeMap) tMeta).getMapName());
            }
    
            if (tMeta instanceof ISeparateBus && ((ISeparateBus) tMeta).hasSeparate()) {
                tag.setBoolean("is_separated", ((ISeparateBus) tMeta).isSeparated());
            }
    
            tag.setString("gt_colorization", Dyes.get(tBaseMetaTile.getColorization()).mName);
        
            if (pipeline != null) {
                tag.setLong("pipeline.distance", pipeline.getDistance());
            }
            
            if (adv_miner != null) {
                tag.setInteger("adv_miner.vein", adv_miner.sizeVeinPreStart);
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
                if (reactorHatch.mStartReactor) tag.setInteger("wSpeedDecay", reactorHatch.mSpeedDecay);
                tag.setString("wRodName", reactorHatch.mInventory[0] != null ? reactorHatch.mInventory[0].getDisplayName() : "");
            }

            if (chestBase != null) {
                final int stackSizeCurrent = chestBase.getItemCount();
                final int stackSizeMax = chestBase.getMaxItemCount();
                final String itemName = chestBase.mInventory[2] != null ? translateGTItemStack(chestBase.mInventory[2]) : "";
                tag.setInteger("chestBaseSizeCurrent", stackSizeCurrent);
                tag.setInteger("chestBaseSizeMax", stackSizeMax);
                tag.setString("chestBaseItemName", itemName);
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
                tag.setBoolean("towerCommunicationConnect", towerCommunication.getConnectionStatus());
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
                tag.setBoolean("connectWithTower", MultiParallel.getConnectionStatus());
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
    
            if (tMeta instanceof GT_MetaTileEntity_Transformer) {
                final GT_MetaTileEntity_Transformer transformer = (GT_MetaTileEntity_Transformer)tMeta;
                tag.setBoolean("isAllowedToWork", tMeta.getBaseMetaTileEntity().isAllowedToWork());
                tag.setLong("maxEUInput", transformer.maxEUInput());
                tag.setLong("maxAmperesIn", transformer.maxAmperesIn());
                tag.setLong("maxEUOutput", transformer.maxEUOutput());
                tag.setLong("maxAmperesOut", transformer.maxAmperesOut());
            } else if (tMeta instanceof GT_MetaTileEntity_Boiler_Solar) {
                final GT_MetaTileEntity_Boiler_Solar slr = (GT_MetaTileEntity_Boiler_Solar)tMeta;
                tag.setInteger("calcificationOutput", (slr.getCalcificationOutput()*20/25));
                tag.setInteger("maxCalcificationOutput", (slr.getBasicOutput()*20/25));
            } else if (tMeta instanceof  GT_MetaTileEntity_PrimitiveBlastFurnace) {
                final GT_MetaTileEntity_PrimitiveBlastFurnace blastFurnace = (GT_MetaTileEntity_PrimitiveBlastFurnace) tMeta;
                final int progress = blastFurnace.mProgresstime;
                final int maxProgress = blastFurnace.mMaxProgresstime;
                tag.setInteger("progressPrimitiveBlastFurnace", progress);
                tag.setInteger("maxProgressPrimitiveBlastFurnace", maxProgress);
                tag.setBoolean("incompleteStructurePrimitiveBlastFurnace", !blastFurnace.mMachine);
            }
    
            if (multiBlockBase != null) {
                final int problems = multiBlockBase.getIdealStatus() - multiBlockBase.getRepairStatus();
                final float efficiency = multiBlockBase.mEfficiency / 100.0F;
                final int progress = multiBlockBase.mProgresstime;
                final int maxProgress = multiBlockBase.mMaxProgresstime;
        
                tag.setBoolean("hasProblems", problems > 0);
                tag.setFloat("efficiency", efficiency);
                tag.setInteger("progress", progress);
                tag.setInteger("maxProgress", maxProgress);
                tag.setBoolean("incompleteStructure", (tBaseMetaTile.getErrorDisplayID() & 64) != 0);
            }
    
            if (BasicMachine != null) {
                final int progressSingleBlock = BasicMachine.mProgresstime;
                final int maxProgressSingleBlock = BasicMachine.mMaxProgresstime;
                final int EUOut = BasicMachine.mEUt;
                tag.setInteger("progressSingleBlock", progressSingleBlock);
                tag.setInteger("maxProgressSingleBlock", maxProgressSingleBlock);
                tag.setInteger("EUOut", EUOut);
            }
    
            if (bateryBuffer != null) {
                long[] tmp = bateryBuffer.getStoredEnergy();
                long nowStorage = tmp[0];
                long maxStorage = tmp[1];
        
                long energyInput = bateryBuffer.getBaseMetaTileEntity().getAverageElectricInput();
                long energyOutput = bateryBuffer.getBaseMetaTileEntity().getAverageElectricOutput();
                tag.setLong("nowStorage", nowStorage);
                tag.setLong("maxStorage", maxStorage);
                tag.setLong("energyInput", energyInput);
                tag.setLong("energyOutput", energyOutput);
            }
            
            if (tBaseMetaTile instanceof BaseMetaPipeEntity) {
                for(byte side=0 ; side < 6 ; side++) {
                    if(tBaseMetaTile.getCoverBehaviorAtSide(side) instanceof GT_Cover_Fluidfilter) {
                        tag.setString("filterInfo" + side, tBaseMetaTile.getCoverBehaviorAtSide(side).getDescription(side, tBaseMetaTile.getCoverIDAtSide(side), tBaseMetaTile.getCoverDataAtSide(side), tBaseMetaTile));
                    }
                }
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
