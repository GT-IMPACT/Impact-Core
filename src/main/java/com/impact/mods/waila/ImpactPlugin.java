package com.impact.mods.waila;

import static mcp.mobius.waila.api.SpecialChars.GREEN;
import static mcp.mobius.waila.api.SpecialChars.RED;
import static mcp.mobius.waila.api.SpecialChars.RESET;
import static mcp.mobius.waila.api.SpecialChars.YELLOW;

import com.enderio.core.common.util.BlockCoord;
import com.github.technus.tectech.thing.metaTileEntity.multi.GT_MetaTileEntity_EM_research;
import com.impact.mods.gregtech.tileentities.multi.generators.nuclear.GTMTE_NuclearReactorBase;
import com.impact.mods.gregtech.tileentities.multi.generators.nuclear.hatch.GTMTE_Reactor_Rod_Hatch;
import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_MBBase;
import com.impact.mods.gregtech.tileentities.multi.implement.GT_MetaTileEntity_MultiParallelBlockBase;
import com.impact.mods.gregtech.tileentities.multi.parallelsystem.GTMTE_ParallelHatch_Input;
import com.impact.mods.gregtech.tileentities.multi.parallelsystem.GTMTE_ParallelHatch_Output;
import com.impact.mods.gregtech.tileentities.multi.parallelsystem.GTMTE_SpaceSatellite_Receiver;
import com.impact.mods.gregtech.tileentities.multi.parallelsystem.GTMTE_TowerCommunication;
import com.impact.mods.gregtech.tileentities.multi.storage.GTMTE_LapPowerStation;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.BaseTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.List;

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
import tterrag.wailaplugins.api.Plugin;
import tterrag.wailaplugins.plugins.PluginBase;

@Plugin(name = "Impact-core", deps = {"impact", "gregtech"})
public class ImpactPlugin extends PluginBase {

  @Override
  public void load(IWailaRegistrar registrar) {
    super.load(registrar);

    registerBody(BaseTileEntity.class);
    registerNBT(BaseTileEntity.class);

  }

  @Override
  @SneakyThrows
  @SuppressWarnings("unused")
  protected void getBody(ItemStack stack, List<String> currenttip, IWailaDataAccessor accessor) {
    final TileEntity tile = accessor.getTileEntity();
    MovingObjectPosition pos = accessor.getPosition();
    NBTTagCompound tag = accessor.getNBTData();
    final int side = (byte) accessor.getSide().ordinal();

    final IGregTechTileEntity tBaseMetaTile = tile instanceof IGregTechTileEntity
        ? ((IGregTechTileEntity) tile) : null;
    final IMetaTileEntity tMeta = tBaseMetaTile != null
        ? tBaseMetaTile.getMetaTileEntity() : null;

    final GT_MetaTileEntity_MultiParallelBlockBase MultiParallel = tMeta instanceof GT_MetaTileEntity_MultiParallelBlockBase
        ? ((GT_MetaTileEntity_MultiParallelBlockBase) tMeta) : null;
    final GTMTE_MBBase multiBlockBaseImpact = tMeta instanceof GTMTE_MBBase
        ? ((GTMTE_MBBase) tMeta) : null;

    final GTMTE_LapPowerStation LapBuffer = tMeta instanceof GTMTE_LapPowerStation
        ? ((GTMTE_LapPowerStation) tMeta) : null;
    final GT_MetaTileEntity_EM_research Research = tMeta instanceof GT_MetaTileEntity_EM_research
        ? ((GT_MetaTileEntity_EM_research) tMeta) : null;
    final GTMTE_TowerCommunication towerCommunication = tMeta instanceof GTMTE_TowerCommunication
        ? ((GTMTE_TowerCommunication) tMeta) : null;
    
    final GTMTE_NuclearReactorBase reactor = tMeta instanceof GTMTE_NuclearReactorBase
        ? ((GTMTE_NuclearReactorBase) tMeta) : null;
    final GTMTE_Reactor_Rod_Hatch reactorHatch = tMeta instanceof GTMTE_Reactor_Rod_Hatch
            ? ((GTMTE_Reactor_Rod_Hatch) tMeta) : null;

    final GTMTE_SpaceSatellite_Receiver towerReciver = tMeta instanceof GTMTE_SpaceSatellite_Receiver
        ? ((GTMTE_SpaceSatellite_Receiver) tMeta) : null;
    final GTMTE_ParallelHatch_Input parallelHatch_input = tMeta instanceof GTMTE_ParallelHatch_Input
        ? ((GTMTE_ParallelHatch_Input) tMeta) : null;
    final GTMTE_ParallelHatch_Output parallelHatch_output = tMeta instanceof GTMTE_ParallelHatch_Output
        ? ((GTMTE_ParallelHatch_Output) tMeta) : null;

    final GT_MetaTileEntity_DigitalChestBase chestBase = tMeta instanceof GT_MetaTileEntity_DigitalChestBase
        ? ((GT_MetaTileEntity_DigitalChestBase) tMeta) : null;

    if (tMeta != null) {
  
      if (reactorHatch != null) {
        currenttip.add("ID: " + tag.getInteger("wIDhatch"));
        currenttip.add("Level Rod: " + EnumChatFormatting.YELLOW + (tag.getInteger("wDownRod") * 10) + "%");
        if (!tag.getString("wRodName").isEmpty())
          currenttip.add(EnumChatFormatting.GREEN + tag.getString("wRodName"));
        currenttip.add("Speed Decay: " + EnumChatFormatting.RED +
                (tag.getInteger("wSpeedDecay") * tag.getInteger("wDownRod")) + "d/s");
      }

      if (chestBase != null) {
        if (!tag.getString("chestBaseItemName").equals("")) {
          currenttip.add(EnumChatFormatting.GREEN + tag.getString("chestBaseItemName") + ":");
          currenttip.add( NumberFormat.getNumberInstance().format(tag.getInteger("chestBaseSizeCurrent")) +
                  " / " + NumberFormat.getNumberInstance().format(tag.getInteger("chestBaseSizeMax")));
        } else {
          currenttip.add(EnumChatFormatting.RED + "No item");
        }
      }

      if (MultiParallel != null && tag.getInteger("Parallel") > 1) {
        String str = tag.getBoolean("connectWithTower")
            ? EnumChatFormatting.GREEN + "Connection Established"
            : EnumChatFormatting.RED +  "No Connection";
        currenttip.add(str);
        currenttip.add(String.format("Parallel Point: %d", tag.getInteger("Parallel")));
      }

      if (LapBuffer != null) {
        currenttip.add("Stored: " + GREEN + NumberFormat.getNumberInstance()
            .format(tag.getDouble("Stored")) + RESET + " EU");
        currenttip.add("Capacity: " + YELLOW + NumberFormat.getNumberInstance()
            .format(tag.getDouble("Capacity")) + RESET + " EU");
        currenttip.add("Input: " + GREEN + NumberFormat.getNumberInstance()
            .format(tag.getDouble("Input")) + RESET + " EU/t");
        currenttip.add("Output: " + RED + NumberFormat.getNumberInstance()
            .format(tag.getDouble("Output")) + RESET + " EU/t");
      }

      if (Research != null) {
        currenttip.add("Computation Remaining: " + GREEN + NumberFormat.getNumberInstance()
            .format(tag.getLong("computationRemaining")) + " / " + YELLOW + NumberFormat
            .getNumberInstance().format(tag.getInteger("computationRequired")));
      }

      if (multiBlockBaseImpact != null) {
        if (tag.getBoolean("incompleteStructureImpact")) {
          currenttip.add(RED + "Incomplete Structure" + RESET);
        }
        currenttip.add((tag.getBoolean("hasProblemsImpact") ? (RED + "Need Maintenance")
            : GREEN + "Running Fine") + RESET + "  Efficiency: " + tag.getFloat("efficiencyImpact")
            + "%");

        currenttip.add(String.format("Progress: %d s / %d s", tag.getInteger("progressImpact"),
            tag.getInteger("maxProgressImpact")));
      }

      if (towerReciver != null) {
        String str = tag.getBoolean("isActiveTowerReciver")
            ? EnumChatFormatting.GREEN + "Connection Established"
            : EnumChatFormatting.RED +  "No Connection";
        currenttip.add(str);
      }

      if (parallelHatch_input != null) {
        String str = tag.getBoolean("isParallelIN")
            ? EnumChatFormatting.GREEN + "Connection Established"
            : EnumChatFormatting.RED +  "No Connection";
        currenttip.add(str);
        currenttip.add("Parallel Point: " + tag.getInteger("ppHatchIn"));
      }

      if (parallelHatch_output != null) {
        String str = tag.getBoolean("isParallelOUT")
            ? EnumChatFormatting.GREEN + "Connection Established"
            : EnumChatFormatting.RED +  "No Connection";
        currenttip.add(str);
        currenttip.add("Parallel Point: " + tag.getInteger("ppHatchOut"));
      }

      if (towerCommunication != null) {
        String str = tag.getBoolean("towerCommunicationConnect")
            ? EnumChatFormatting.GREEN + "Connection Established"
            : EnumChatFormatting.RED +  "No Connection";
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
        String str = "Temperature: " + colorTemp + "%";
        currenttip.add(str);
        if (tag.getBoolean("wMox"))
          currenttip.add(color[3] + "Mox Fuel");
        if (tag.getBoolean("wFastDecay"))
          currenttip.add(color[2] + "Fast Decay Mode");
      }
    }
  }

  @Override
  @SneakyThrows
  protected void getNBTData(TileEntity tile, NBTTagCompound tag, World world, BlockCoord pos) {

    final IGregTechTileEntity tBaseMetaTile =
        tile instanceof IGregTechTileEntity
            ? ((IGregTechTileEntity) tile) : null;
    final IMetaTileEntity tMeta = tBaseMetaTile != null
        ? tBaseMetaTile.getMetaTileEntity() : null;

    final GT_MetaTileEntity_MultiBlockBase multiBlockBase = tMeta instanceof GT_MetaTileEntity_MultiBlockBase
        ? ((GT_MetaTileEntity_MultiBlockBase) tMeta) : null;
    final GT_MetaTileEntity_MultiParallelBlockBase MultiParallel = tMeta instanceof GT_MetaTileEntity_MultiParallelBlockBase
        ? ((GT_MetaTileEntity_MultiParallelBlockBase) tMeta) : null;
    final GTMTE_MBBase multiBlockBaseImpact = tMeta instanceof GTMTE_MBBase
        ? ((GTMTE_MBBase) tMeta) : null;
    final GTMTE_TowerCommunication towerCommunication = tMeta instanceof GTMTE_TowerCommunication
        ? ((GTMTE_TowerCommunication) tMeta) : null;
    
    final GTMTE_NuclearReactorBase reactor = tMeta instanceof GTMTE_NuclearReactorBase
        ? ((GTMTE_NuclearReactorBase) tMeta) : null;
    final GTMTE_Reactor_Rod_Hatch reactorHatch = tMeta instanceof GTMTE_Reactor_Rod_Hatch
            ? ((GTMTE_Reactor_Rod_Hatch) tMeta) : null;

    final GTMTE_SpaceSatellite_Receiver towerReciver = tMeta instanceof GTMTE_SpaceSatellite_Receiver
        ? ((GTMTE_SpaceSatellite_Receiver) tMeta) : null;
    final GTMTE_ParallelHatch_Input parallelHatch_input = tMeta instanceof GTMTE_ParallelHatch_Input
        ? ((GTMTE_ParallelHatch_Input) tMeta) : null;
    final GTMTE_ParallelHatch_Output parallelHatch_output = tMeta instanceof GTMTE_ParallelHatch_Output
        ? ((GTMTE_ParallelHatch_Output) tMeta) : null;

    final GT_MetaTileEntity_DigitalChestBase chestBase = tMeta instanceof GT_MetaTileEntity_DigitalChestBase
            ? ((GT_MetaTileEntity_DigitalChestBase) tMeta) : null;

    if (tMeta != null) {
      
      if (reactorHatch != null) {
        tag.setInteger("wIDhatch", reactorHatch.mIDhatch + 1);
        tag.setInteger("wDownRod", reactorHatch.mDownRod);
        if (reactorHatch.mStartReactor)
          tag.setInteger("wSpeedDecay", reactorHatch.mSpeedDecay);
          tag.setString("wRodName", reactorHatch.mInventory[0] != null ?
                  reactorHatch.mInventory[0].getDisplayName() : "");
      }

      if (chestBase != null) {
        final int stackSizeCurrent = chestBase.getItemCount() > 0 ? chestBase.getItemCount() + 64 : chestBase.getItemCount();
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
      }

      if (parallelHatch_output != null) {
        final boolean isParallelOUT = parallelHatch_output.mIsTrueRecipe;
        tag.setBoolean("isParallelOUT", isParallelOUT);
        tag.setInteger("ppHatchOut", parallelHatch_output.mMaxParallel);
      }

      if (towerCommunication != null) {
        tag.setBoolean("towerCommunicationConnect", towerCommunication.mIsConnect);
      }

      if (reactor != null) {
        double tScale = (double) reactor.mCurrentTemp / (double) reactor.mMaxTemp;
        tScale = tScale <= 0 ? 0 : tScale;
        int temperature = Math.min(((int) (100 * tScale)), 100);
        tag.setInteger("reactorTemp",temperature);
        tag.setBoolean("wMox", reactor.isMoxFuel);
        tag.setBoolean("wFastDecay", reactor.isFastDecay);
      }


      if (MultiParallel != null) {
        final int Parallel = MultiParallel.mParallel;
        tag.setInteger("Parallel", Parallel);
        tag.setBoolean("connectWithTower", MultiParallel.mIsConnect);
      }

      if (tMeta instanceof GTMTE_LapPowerStation) {
        GTMTE_LapPowerStation mte = (GTMTE_LapPowerStation) tMeta;
        final double Capacity = mte.capacity;
        final double Stored = mte.stored;
        final double Input = mte.intputLastTick;
        final double Output = mte.outputLastTick;

        tag.setDouble("Capacity", Capacity);
        tag.setDouble("Stored", Stored);
        tag.setDouble("Input", Input);
        tag.setDouble("Output", Output);
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
    tile.writeToNBT(tag);
  }

}
