package com.impact.mods.waila;

import static mcp.mobius.waila.api.SpecialChars.GREEN;
import static mcp.mobius.waila.api.SpecialChars.RED;
import static mcp.mobius.waila.api.SpecialChars.RESET;
import static mcp.mobius.waila.api.SpecialChars.YELLOW;

import com.enderio.core.common.util.BlockCoord;
import com.github.technus.tectech.thing.metaTileEntity.multi.GT_MetaTileEntity_EM_research;
import com.impact.mods.GregTech.tileentities.multi.implement.GTMTE_MBBase;
import com.impact.mods.GregTech.tileentities.multi.implement.GT_MetaTileEntity_MultiParallelBlockBase;
import com.impact.mods.GregTech.tileentities.multi.storage.GTMTE_LapPowerStation;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.BaseTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.List;
import lombok.SneakyThrows;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
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

    final IGregTechTileEntity tBaseMetaTile =
        tile instanceof IGregTechTileEntity ? ((IGregTechTileEntity) tile) : null;
    final IMetaTileEntity tMeta = tBaseMetaTile != null ? tBaseMetaTile.getMetaTileEntity() : null;

    final GT_MetaTileEntity_MultiParallelBlockBase MultiParallel =
        tMeta instanceof GT_MetaTileEntity_MultiParallelBlockBase
            ? ((GT_MetaTileEntity_MultiParallelBlockBase) tMeta) : null;
    final GTMTE_MBBase multiBlockBaseImpact =
        tMeta instanceof GTMTE_MBBase ? ((GTMTE_MBBase) tMeta) : null;

    final GTMTE_LapPowerStation LapBuffer =
        tMeta instanceof GTMTE_LapPowerStation ? ((GTMTE_LapPowerStation) tMeta) : null;
    final GT_MetaTileEntity_EM_research Research =
        tMeta instanceof GT_MetaTileEntity_EM_research ? ((GT_MetaTileEntity_EM_research) tMeta)
            : null;

    if (tMeta != null) {
      if (MultiParallel != null && tag.getInteger("Parallel") > 1) {
        currenttip.add(String.format("Parallel Point: %d", tag.getInteger("Parallel")));
      }

      if (LapBuffer != null) {
        currenttip.add("Stored: " + GREEN + NumberFormat.getNumberInstance()
            .format(new BigInteger(tag.getByteArray("Stored"))) + RESET + " EU");
        currenttip.add("Capacity: " + YELLOW + NumberFormat.getNumberInstance()
            .format(new BigInteger(tag.getByteArray("Capacity"))) + RESET + " EU");
        currenttip.add("Input: " + GREEN + NumberFormat.getNumberInstance()
            .format(new BigInteger(tag.getByteArray("Input"))) + RESET + " EU/t");
        currenttip.add("Output: " + RED + NumberFormat.getNumberInstance()
            .format(new BigInteger(tag.getByteArray("Output"))) + RESET + " EU/t");
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
    }
  }

  @Override
  @SneakyThrows
  protected void getNBTData(TileEntity tile, NBTTagCompound tag, World world, BlockCoord pos) {

    final IGregTechTileEntity tBaseMetaTile =
        tile instanceof IGregTechTileEntity ? ((IGregTechTileEntity) tile) : null;
    final IMetaTileEntity tMeta = tBaseMetaTile != null ? tBaseMetaTile.getMetaTileEntity() : null;

    final GT_MetaTileEntity_MultiBlockBase multiBlockBase =
        tMeta instanceof GT_MetaTileEntity_MultiBlockBase
            ? ((GT_MetaTileEntity_MultiBlockBase) tMeta) : null;
    final GT_MetaTileEntity_MultiParallelBlockBase MultiParallel =
        tMeta instanceof GT_MetaTileEntity_MultiParallelBlockBase
            ? ((GT_MetaTileEntity_MultiParallelBlockBase) tMeta) : null;
    final GTMTE_MBBase multiBlockBaseImpact =
        tMeta instanceof GTMTE_MBBase ? ((GTMTE_MBBase) tMeta) : null;

    if (tMeta != null) {

      if (MultiParallel != null) {
        final int Parallel = MultiParallel.mParallel;
        tag.setInteger("Parallel", Parallel);
      }

      if (tMeta instanceof GTMTE_LapPowerStation) {
        GTMTE_LapPowerStation mte = (GTMTE_LapPowerStation) tMeta;
        final BigInteger Capacity = mte.capacity;
        final BigInteger Stored = mte.stored;
        final BigInteger Input = mte.intputLastTick;
        final BigInteger Output = mte.outputLastTick;

        tag.setByteArray("Capacity", Capacity.toByteArray());
        tag.setByteArray("Stored", Stored.toByteArray());
        tag.setByteArray("Input", Input.toByteArray());
        tag.setByteArray("Output", Output.toByteArray());
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
