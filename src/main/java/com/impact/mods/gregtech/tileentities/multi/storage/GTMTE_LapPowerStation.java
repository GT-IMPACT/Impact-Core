package com.impact.mods.gregtech.tileentities.multi.storage;

import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.screen.UISettings;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.impact.mods.gregtech.gui.lap.LapChargeHistory;
import com.impact.mods.gregtech.gui.lap.LapFlowHistory;
import com.impact.mods.gregtech.gui.lap.LapHistoryPeriod;
import com.impact.mods.gregtech.gui.lap.LapPowerStationGui;
import gregtech.api.logic.ProcessingLogic;
import gregtech.api.logic.TimedProcessingLogic;
import com.impact.mods.gregtech.blocks.ImpactCasings;
import com.impact.mods.gregtech.blocks.ImpactHatchElement;
import com.impact.util.ImpactMultiblockTooltipBuilder;
import com.impact.mods.gregtech.tileentities.hatches.lasers.GTMTE_LaserEnergy_In;
import com.impact.mods.gregtech.tileentities.hatches.lasers.GTMTE_LaserEnergy_Out;
import cpw.mods.fml.common.Optional;
import gregtech.api.enums.HatchElement;
import gregtech.api.interfaces.ICasing;
import gregtech.api.interfaces.IHatchElement;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.interfaces.tileentity.IProvideEnergyCover;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_ProcessingMultiBlockBase;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Dynamo;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_DynamoMulti;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Energy;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_EnergyMulti;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTStructureUtility;
import gregtech.api.util.GTStructureChannels;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.StructureRequirements;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import space.impact.structures.alignment.constructable.ISurvivalConstructable;
import space.impact.structures.alignment.constructable.ChannelDataAccessor;
import space.impact.structures.hint.HintColor;
import space.impact.structures.structure.AutoPlaceEnvironment;
import space.impact.structures.structure.IStructureDefinition;
import space.impact.structures.structure.IStructureElement;
import space.impact.structures.structure.ISurvivalBuildEnvironment;
import space.impact.structures.structure.StructureDefinition;

import static com.impact.loader.ItemRegistery.IGlassBlock;
import static com.impact.loader.ItemRegistery.lscLapotronicEnergyUnit;
import static com.impact.mods.gregtech.enums.Texture.Icons.LPS;
import static com.impact.mods.gregtech.enums.Texture.Icons.LPS_ACTIVE;
import static net.minecraft.util.EnumChatFormatting.GREEN;
import static net.minecraft.util.EnumChatFormatting.RED;
import static net.minecraft.util.EnumChatFormatting.RESET;
import static net.minecraft.util.EnumChatFormatting.YELLOW;
import static net.minecraft.util.StatCollector.translateToLocalFormatted;
import static space.impact.structures.structure.StructureUtility.ofBlock;
import static space.impact.structures.structure.StructureUtility.ofBlockAnyMeta;
import static space.impact.structures.structure.StructureUtility.ofChain;
import static space.impact.structures.structure.StructureUtility.onElementPass;
import static space.impact.structures.structure.StructureUtility.withHint;

/**
 * Made by Kekzdealer Edit by 4gname
 */

public class GTMTE_LapPowerStation extends
        GT_MetaTileEntity_ProcessingMultiBlockBase<GTMTE_LapPowerStation>
        implements ISurvivalConstructable, IProvideEnergyCover {
    private static IStructureDefinition<GTMTE_LapPowerStation> STRUCTURE;
    private static final StructureRequirements SHELL = StructureRequirements.builder(ImpactCasings.LSCC_CASING, 25)
            .build();
    private int casingCount;
    private int checkedGlass = -1;

    @Override
    public IStructureDefinition<GTMTE_LapPowerStation> getStructureDefinition() {
        if (STRUCTURE == null) {
            STRUCTURE = StructureDefinition.<GTMTE_LapPowerStation>builder()
                    .addChannel(GTStructureUtility.HATCH_CHANNEL, 1, 1)
                    .addChannel(GTStructureChannels.HEIGHT, 1, 15)
                    .addChannel(GTStructureChannels.TIER, 4, 10)
                    .addShape("base", new String[][] {
                            { "CCCCC", "CC~CC" }, { "CCCCC", "CCCCC" }, { "CCCCC", "CCCCC" },
                            { "CCCCC", "CCCCC" }, { "CCCCC", "CCCCC" }
                    })
                    .addShape("layer",
                            new String[][] { { "GGGGG" }, { "GEEEG" }, { "GEEEG" }, { "GEEEG" }, { "GGGGG" } })
                    .addShape("roof",
                            new String[][] { { "GGGGG" }, { "GGGGG" }, { "GGGGG" }, { "GGGGG" }, { "GGGGG" } })
                    .addShape("roofProbe", new String[][] { { "P" } })
                    .addElement('C', withHint(GTStructureUtility.buildHatchAdder(GTMTE_LapPowerStation.class)
                            .allow(HatchElement.Maintenance, HatchElement.Energy, ImpactHatchElement.Dynamo,
                                    LaserHatch.Input, LaserHatch.Output)
                            .autoPlace(HatchElement.Maintenance, HatchElement.Energy, ImpactHatchElement.Dynamo, LaserHatch.Input, LaserHatch.Output)
                            .buildAndChain(SHELL, t -> t.casingCount++), HintColor.GREEN))
                    .addElement('E', capacitorElement())
                    .addElement('G', glassElement())
                    .addElement('P', ofBlockAnyMeta(IGlassBlock))
                    .build();
        }
        return STRUCTURE;
    }

    private int constructionLayers(ItemStack trigger) {
        return Math.max(1, Math.min(15, GTStructureChannels.getValue(trigger, GTStructureChannels.HEIGHT)));
    }

    @Override
    public void construct(ItemStack trigger, boolean hintsOnly) {
        int layers = constructionLayers(trigger);
        buildPiece("base", trigger, hintsOnly, 2, 1, 0);
        for (int layer = 2; layer < layers + 2; layer++) {
            buildPiece("layer", trigger, hintsOnly, 2, layer, 0);
        }
        buildPiece("roof", trigger, hintsOnly, 2, layers + 2, 0);
    }

    @Override
    public int survivalConstruct(ItemStack trigger, int budget, ISurvivalBuildEnvironment environment) {
        if (mMachine) {
            return -1;
        }
        int layers = constructionLayers(trigger);
        int built = Math.max(0, survivalBuildPiece("base", trigger, 2, 1, 0, budget, environment));
        for (int layer = 2; layer < layers + 2 && built < budget; layer++) {
            built += Math.max(0, survivalBuildPiece("layer", trigger, 2, layer, 0, budget - built, environment));
        }
        if (built < budget) {
            built += Math.max(0, survivalBuildPiece("roof", trigger, 2, layers + 2, 0, budget - built, environment));
        }
        return built;
    }

    public static final long L_IV = 0x59682f00L * 4L;
    public static final long L_LuV = 0x165a0bc00L * 4L;
    public static final long L_ZPM = 0xdf8475800L * 4L;
    public static final long L_UV = 0x8bb2c97000L * 4L;
    public static final long L_UHV = 0x45d964b800L * 4L;
    public static final long L_UEV = 0x2ba7def3000L * 4L;
    public static final long E_IV = 0x247a6100L * 4L;

    private static final ICasing CASING = ImpactCasings.LSCC_CASING;

    private final Set<GTMTE_LaserEnergy_In> mLaserIn = new HashSet<>();
    private final Set<GTMTE_LaserEnergy_Out> mLaserOut = new HashSet<>();
    // Count the amount of capacitors of each tier in each slot (translate with meta
    // - 1)
    private final int[] capacitors = new int[8];
    private final LapChargeHistory chargeHistory = new LapChargeHistory();
    private final LapFlowHistory flowHistory = new LapFlowHistory();
    public long capacity = 0;
    public long stored = 0;
    public long passiveDischargeAmount = 0;
    public long intputLastTick = 0;
    public long outputLastTick = 0;

    public GTMTE_LapPowerStation(int aID, String aNameRegional) {
        super(aID, "impact.multimachine.supercapacitor", aNameRegional);
        enabledMaintenance = false;
    }

    public GTMTE_LapPowerStation(String aName) {
        super(aName);
        enabledMaintenance = false;
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity var1) {
        return new GTMTE_LapPowerStation(super.mName);
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        return new ImpactMultiblockTooltipBuilder(mName)
                .useCompactStructure()
                .addMachineType("Battery Buffer")
                .addInfo("Capacity depends on the number and tier of installed capacitors.")
                .beginVariableStructureBlock(5, 5, 4, 18, 5, 5, false)
                .addController("front center of the bottom layer")
                .addStructureSection("Components")
                .addStructurePartInfo(CASING, "%s+; fill unused positions in the 2-layer base", SHELL.getMinimumCasings())
                .addOtherStructurePart("Capacitors", "up to 9 per layer; central 3x3 above the base; mixed tiers allowed")
                .addOtherStructurePart("I-Glass", "16 per layer and 25 in the roof; walls and roof must have the same color; may end capacitor columns")
                .addStructureSection("Hatches and buses")
                .addHatchInfo("Energy Hatches", "optional; 2-layer base")
                .addHatchInfo("Dynamo Hatches", "optional; 2-layer base")
                .addStructureHint("Base: casings or energy/dynamo hatches", HintColor.GREEN)
                .addSubChannel(GTStructureChannels.HEIGHT, "1-15: capacitor layers; %s; default: stack size.", "height = value + 3")
                .addSubChannel(GTStructureChannels.TIER, "4: Energium; 5-10: IV-UEV; unset: UEV preview, available tiers; existing capacitors are kept.")
                .addHatchChannelInfo()
                .toolTipFinisher();
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex,
            boolean aActive, boolean aRedstone) {
        return aSide == aFacing ? new ITexture[] { CASING.getTexture(), TextureFactory.of(aActive ? LPS_ACTIVE : LPS) }
                : new ITexture[] { CASING.getTexture() };
    }


    @Override
    public boolean isCorrectMachinePart(ItemStack stack) {
        return true;
    }

    @Override
    protected ProcessingLogic createProcessingLogic() {
        return new TimedProcessingLogic(0, () -> 1);
    }

    @Override
    public boolean checkMachine(IGregTechTileEntity controller, ItemStack stack) {
        Arrays.fill(capacitors, 0);
        capacity = 0;
        passiveDischargeAmount = 0;
        casingCount = 0;
        checkedGlass = -1;
        mMaintenanceHatches.clear();
        mEnergyHatches.clear();
        mDynamoHatches.clear();
        mEnergyHatchesMulti.clear();
        mDynamoHatchesMulti.clear();
        mLaserIn.clear();
        mLaserOut.clear();
        boolean formed = checkPiece("base", 2, 1, 0);
        int roof = 0;
        for (int height = 2; height <= 17; height++) {
            if (checkPiece("roofProbe", -1, height, -3)) {
                roof = height;
                break;
            }
        }
        if (roof < 3) {
            return false;
        }
        for (int height = 2; height < roof; height++) {
            formed &= checkPiece("layer", 2, height, 0);
        }
        formed &= checkPiece("roof", 2, roof, 0);
        return formed && SHELL.isSatisfied(this, casingCount);
    }

    private int constructionGlass() {
        IGregTechTileEntity base = getBaseMetaTileEntity();
        int[] offset = new int[3];
        getExtendedFacing().getWorldOffset(new int[] { -2, -2, 0 }, offset);
        int x = base.getXCoord() + offset[0], y = base.getYCoord() + offset[1], z = base.getZCoord() + offset[2];
        return base.getWorld().getBlock(x, y, z) == IGlassBlock ? base.getWorld().getBlockMetadata(x, y, z) : -1;
    }

    private static IStructureElement<GTMTE_LapPowerStation> glassElement() {
        return new IStructureElement<GTMTE_LapPowerStation>() {
            private IStructureElement<GTMTE_LapPowerStation> selected(GTMTE_LapPowerStation machine) {
                int color = machine.constructionGlass();
                return color < 0 ? ofBlockAnyMeta(IGlassBlock) : ofBlock(IGlassBlock, color);
            }

            @Override
            public boolean check(GTMTE_LapPowerStation machine, World world, int x, int y, int z) {
                if (world.getBlock(x, y, z) != IGlassBlock) {
                    return false;
                }
                int metadata = world.getBlockMetadata(x, y, z);
                if (machine.checkedGlass == -1) {
                    machine.checkedGlass = metadata;
                }
                return machine.checkedGlass == metadata;
            }

            @Override
            public boolean couldBeValid(GTMTE_LapPowerStation machine, World world, int x, int y, int z,
                    ItemStack trigger) {
                return selected(machine).couldBeValid(machine, world, x, y, z, trigger);
            }

            @Override
            public boolean spawnHint(GTMTE_LapPowerStation machine, World world, int x, int y, int z,
                    ItemStack trigger) {
                return selected(machine).spawnHint(machine, world, x, y, z, trigger);
            }

            @Override
            public boolean placeBlock(GTMTE_LapPowerStation machine, World world, int x, int y, int z,
                    ItemStack trigger) {
                return selected(machine).placeBlock(machine, world, x, y, z, trigger);
            }

            @Override
            public BlocksToPlace getBlocksToPlace(GTMTE_LapPowerStation machine, World world, int x, int y, int z,
                    ItemStack trigger, AutoPlaceEnvironment environment) {
                return selected(machine).getBlocksToPlace(machine, world, x, y, z, trigger, environment);
            }

            @Override
            public PlaceResult survivalPlaceBlock(GTMTE_LapPowerStation machine, World world, int x, int y, int z,
                    ItemStack trigger, AutoPlaceEnvironment environment) {
                return selected(machine).survivalPlaceBlock(machine, world, x, y, z, trigger, environment);
            }
        };
    }

    private static IStructureElement<GTMTE_LapPowerStation> capacitorElement() {
        IStructureElement<GTMTE_LapPowerStation> capacitor = ofChain(
                capacitorTier(8, L_UEV), capacitorTier(1, L_IV), capacitorTier(2, L_LuV),
                capacitorTier(3, L_ZPM), capacitorTier(4, L_UV),
                capacitorTier(6, E_IV), capacitorTier(7, L_UHV), ofBlockAnyMeta(IGlassBlock));
        return new IStructureElement<GTMTE_LapPowerStation>() {
            private IStructureElement<GTMTE_LapPowerStation> selected(ItemStack trigger) {
                // The master value already selects height; preserve legacy selection until tier is explicitly set.
                if (trigger == null || !ChannelDataAccessor.hasSubChannel(trigger, GTStructureChannels.TIER)) {
                    return capacitor;
                }
                int tier = Math.max(4, Math.min(10, GTStructureChannels.getValue(trigger, GTStructureChannels.TIER)));
                switch (tier) {
                    case 4:
                        return ofBlock(lscLapotronicEnergyUnit, 6);
                    case 5:
                        return ofBlock(lscLapotronicEnergyUnit, 1);
                    case 6:
                        return ofBlock(lscLapotronicEnergyUnit, 2);
                    case 7:
                        return ofBlock(lscLapotronicEnergyUnit, 3);
                    case 8:
                        return ofBlock(lscLapotronicEnergyUnit, 4);
                    case 9:
                        return ofBlock(lscLapotronicEnergyUnit, 7);
                    default:
                        return ofBlock(lscLapotronicEnergyUnit, 8);
                }
            }

            private boolean aboveGlass(GTMTE_LapPowerStation machine, World world, int x, int y, int z) {
                // A column stops at its first glass block, even below the common roof.
                for (int height = machine.getBaseMetaTileEntity().getYCoord() + 2; height < y; height++) {
                    if (world.getBlock(x, height, z) == IGlassBlock) {
                        return true;
                    }
                }
                return false;
            }

            @Override
            public boolean check(GTMTE_LapPowerStation machine, World world, int x, int y, int z) {
                return aboveGlass(machine, world, x, y, z) || capacitor.check(machine, world, x, y, z);
            }

            @Override
            public boolean couldBeValid(GTMTE_LapPowerStation machine, World world, int x, int y, int z,
                    ItemStack trigger) {
                return aboveGlass(machine, world, x, y, z) || capacitor.couldBeValid(machine, world, x, y, z, trigger);
            }

            @Override
            public boolean spawnHint(GTMTE_LapPowerStation machine, World world, int x, int y, int z,
                    ItemStack trigger) {
                return aboveGlass(machine, world, x, y, z) || selected(trigger).spawnHint(machine, world, x, y, z, trigger);
            }

            @Override
            public boolean placeBlock(GTMTE_LapPowerStation machine, World world, int x, int y, int z,
                    ItemStack trigger) {
                return couldBeValid(machine, world, x, y, z, trigger)
                        || selected(trigger).placeBlock(machine, world, x, y, z, trigger);
            }

            @Override
            public BlocksToPlace getBlocksToPlace(GTMTE_LapPowerStation machine, World world, int x, int y, int z,
                    ItemStack trigger, AutoPlaceEnvironment environment) {
                return selected(trigger).getBlocksToPlace(machine, world, x, y, z, trigger, environment);
            }

            @Override
            public PlaceResult survivalPlaceBlock(GTMTE_LapPowerStation machine, World world, int x, int y, int z,
                    ItemStack trigger, AutoPlaceEnvironment environment) {
                return couldBeValid(machine, world, x, y, z, trigger) ? PlaceResult.SKIP
                        : selected(trigger).survivalPlaceBlock(machine, world, x, y, z, trigger, environment);
            }
        };
    }

    private static IStructureElement<GTMTE_LapPowerStation> capacitorTier(int metadata, long capacity) {
        return onElementPass(machine -> {
            machine.capacitors[metadata - 1]++;
            machine.capacity += capacity;
        }, ofBlock(lscLapotronicEnergyUnit, metadata));
    }

    private enum LaserHatch implements IHatchElement {
        Input {
            @Override
            public boolean matches(IMetaTileEntity tile) {
                return tile instanceof GTMTE_LaserEnergy_In;
            }

            @Override
            public int count(GT_MetaTileEntity_MultiBlockBase machine) {
                return ((GTMTE_LapPowerStation) machine).mLaserIn.size();
            }

            @Override
            public boolean add(GT_MetaTileEntity_MultiBlockBase machine, IGregTechTileEntity tile, int texture) {
                return machine.addEnergyInputToMachineList(tile, texture);
            }

            @Override
            public String getDescription() {
                return StatCollector.translateToLocal("impact.structure.hatch.laser_input");
            }
        },
        Output {
            @Override
            public boolean matches(IMetaTileEntity tile) {
                return tile instanceof GTMTE_LaserEnergy_Out;
            }

            @Override
            public int count(GT_MetaTileEntity_MultiBlockBase machine) {
                return ((GTMTE_LapPowerStation) machine).mLaserOut.size();
            }

            @Override
            public boolean add(GT_MetaTileEntity_MultiBlockBase machine, IGregTechTileEntity tile, int texture) {
                return machine.addDynamoToMachineList(tile, texture);
            }

            @Override
            public String getDescription() {
                return StatCollector.translateToLocal("impact.structure.hatch.laser_output");
            }
        }
    }

    @Override
    public boolean addEnergyInputToMachineList(IGregTechTileEntity te, int aBaseCasingIndex) {
        if (te == null) {
            return false;
        } else {
            final IMetaTileEntity mte = te.getMetaTileEntity();
            if (mte instanceof GT_MetaTileEntity_Hatch_Energy) {
                // Add GT hatches
                ((GT_MetaTileEntity_Hatch) mte).updateTexture(aBaseCasingIndex);
                return super.mEnergyHatches.add((GT_MetaTileEntity_Hatch_Energy) mte);
            } else if (mte instanceof GTMTE_LaserEnergy_In) {
                ((GT_MetaTileEntity_Hatch) mte).updateTexture(aBaseCasingIndex);
                return mLaserIn.add((GTMTE_LaserEnergy_In) mte);
            } else if (mte instanceof GT_MetaTileEntity_Hatch_EnergyMulti) {
                // Add TT hatches
                ((GT_MetaTileEntity_Hatch) mte).updateTexture(aBaseCasingIndex);
                return mEnergyHatchesMulti.add((GT_MetaTileEntity_Hatch_EnergyMulti) mte);
            } else {
                return false;
            }
        }
    }

    @Override
    public boolean addDynamoToMachineList(IGregTechTileEntity te, int aBaseCasingIndex) {
        if (te == null) {
            return false;
        } else {
            final IMetaTileEntity mte = te.getMetaTileEntity();
            if (mte instanceof GT_MetaTileEntity_Hatch_Dynamo) {
                // Add GT hatches
                ((GT_MetaTileEntity_Hatch) mte).updateTexture(aBaseCasingIndex);
                return super.mDynamoHatches.add((GT_MetaTileEntity_Hatch_Dynamo) mte);
            } else if (mte instanceof GTMTE_LaserEnergy_Out) {
                ((GT_MetaTileEntity_Hatch) mte).updateTexture(aBaseCasingIndex);
                return mLaserOut.add((GTMTE_LaserEnergy_Out) mte);
            } else if (mte instanceof GT_MetaTileEntity_Hatch_DynamoMulti) {
                // Add TT hatches
                ((GT_MetaTileEntity_Hatch) mte).updateTexture(aBaseCasingIndex);
                return mDynamoHatchesMulti.add((GT_MetaTileEntity_Hatch_DynamoMulti) mte);
            } else {
                return false;
            }
        }
    }

    @Override
    public ModularPanel buildUI(PosGuiData data, PanelSyncManager sync, UISettings settings) {
        return LapPowerStationGui.build(this, data, sync, settings);
    }

    @Override
    public void onPostTick(IGregTechTileEntity tile, long tick) {
        if (tile.isServerSide()) {
            intputLastTick = 0;
            outputLastTick = 0;
        }
        super.onPostTick(tile, tick);
        if (tile.isServerSide()) {
            chargeHistory.sample(tick, stored, mMachine ? capacity : 0);
            flowHistory.sample(intputLastTick, outputLastTick);
        }
    }

    public int[] getChargeHistory() {
        return chargeHistory.snapshot();
    }

    public int[] getChargeHistory(LapHistoryPeriod period) {
        return chargeHistory.snapshot(period);
    }

    public long getAverageInput(LapHistoryPeriod period) {
        return flowHistory.averageInput(period);
    }

    public long getAverageOutput(LapHistoryPeriod period) {
        return flowHistory.averageOutput(period);
    }

    @Override
    public boolean onRunningTick(ItemStack stack) {
        // Reset I/O cache
        intputLastTick = 0;
        outputLastTick = 0;

        // Draw energy from GT hatches
        for (GT_MetaTileEntity_Hatch_Energy eHatch : super.mEnergyHatches) {
            if (eHatch == null || eHatch.getBaseMetaTileEntity().isInvalidTileEntity()) {
                continue;
            }
            final long power = getPowerToDraw(eHatch.maxEUInput() * eHatch.maxAmperesIn());
            if (eHatch.getEUVar() >= power) {
                eHatch.setEUVar(eHatch.getEUVar() - power);
                stored += power;
                intputLastTick += power;
            }
        }
        // Output energy to GT hatches
        for (GT_MetaTileEntity_Hatch_Dynamo eDynamo : super.mDynamoHatches) {
            if (eDynamo == null || eDynamo.getBaseMetaTileEntity().isInvalidTileEntity()) {
                continue;
            }
            final long power = getPowerToPush(eDynamo.maxEUOutput() * eDynamo.maxAmperesOut());
            if (power <= eDynamo.maxEUStore() - eDynamo.getEUVar()) {
                eDynamo.setEUVar(eDynamo.getEUVar() + power);
                stored -= power;
                outputLastTick += power;
            }
        }

        for (GTMTE_LaserEnergy_In eHatch : mLaserIn) {
            if (eHatch == null || eHatch.getBaseMetaTileEntity() == null
                    || eHatch.getBaseMetaTileEntity().isInvalidTileEntity()) {
                continue;
            }
            final long power = getPowerToDraw(eHatch.maxEUInput() * eHatch.maxAmperesIn());
            if (eHatch.getEUVar() >= power) {
                eHatch.setEUVar(eHatch.getEUVar() - power);
                stored += power;
                intputLastTick += power;
            }
        }

        for (GTMTE_LaserEnergy_Out eDynamo : mLaserOut) {
            if (eDynamo == null || eDynamo.getBaseMetaTileEntity() == null
                    || eDynamo.getBaseMetaTileEntity().isInvalidTileEntity()) {
                continue;
            }
            final long power = getPowerToPush(eDynamo.maxEUOutput() * eDynamo.maxAmperesOut());
            if (power <= eDynamo.maxEUStore() - eDynamo.getEUVar()) {
                eDynamo.setEUVar(eDynamo.getEUVar() + power);
                stored -= power;
                outputLastTick += power;
            }
        }

        // Draw energy from TT hatches
        for (GT_MetaTileEntity_Hatch_EnergyMulti eHatch : mEnergyHatchesMulti) {
            if (eHatch == null || eHatch.getBaseMetaTileEntity().isInvalidTileEntity()) {
                continue;
            }
            final long power = getPowerToDraw(eHatch.maxEUInput() * eHatch.maxAmperesIn());
            if (eHatch.getEUVar() >= power) {
                eHatch.setEUVar(eHatch.getEUVar() - power);
                stored += power;
                intputLastTick += power;
            }
        }
        // Output energy to TT hatches
        for (GT_MetaTileEntity_Hatch_DynamoMulti eDynamo : mDynamoHatchesMulti) {
            if (eDynamo == null || eDynamo.getBaseMetaTileEntity().isInvalidTileEntity()) {
                continue;
            }
            final long power = getPowerToPush(eDynamo.maxEUOutput() * eDynamo.maxAmperesOut());
            if (power <= eDynamo.maxEUStore() - eDynamo.getEUVar()) {
                eDynamo.setEUVar(eDynamo.getEUVar() + power);
                stored -= power;
                outputLastTick += power;
            }
        }
        return true;
    }

    /**
     * Calculate how much EU to draw from an Energy Hatch
     *
     * @param hatchWatts Hatch amperage * voltage
     * @return EU amount
     */
    private long getPowerToDraw(long hatchWatts) {
        final long remcapActual = capacity - stored;
        final long recampLimited = remcapActual > 0 ? remcapActual : 0;
        return Math.min(hatchWatts, recampLimited);
    }

    /**
     * Calculate how much EU to push into a Dynamo Hatch
     *
     * @param hatchWatts Hatch amperage * voltage
     * @return EU amount
     */
    private long getPowerToPush(long hatchWatts) {
        final long remStoredLimited = stored > 0 ? stored : 0;
        return Math.min(hatchWatts, remStoredLimited);
    }

    @Override
    public String[] getInfoData() {
        final ArrayList<String> ll = new ArrayList<>();
        ll.add("Used Capacity: ");
        ll.add(GREEN + NumberFormat.getNumberInstance().format(stored) + RESET + " EU");
        ll.add("Total Capacity: ");
        ll.add(YELLOW + NumberFormat.getNumberInstance().format(capacity) + RESET + " EU");
        ll.add("EU IN: ");
        ll.add(GREEN + NumberFormat.getNumberInstance().format(intputLastTick) + RESET + " EU/t");
        ll.add("EU OUT: ");
        ll.add(RED + NumberFormat.getNumberInstance().format(outputLastTick) + RESET + " EU/t");
        ll.add("Average EU IN (1m): ");
        ll.add(GREEN + NumberFormat.getNumberInstance().format(getAverageInput(LapHistoryPeriod.MINUTE))
                + RESET + " EU/t");
        ll.add("Average EU OUT (1m): ");
        ll.add(RED + NumberFormat.getNumberInstance().format(getAverageOutput(LapHistoryPeriod.MINUTE))
                + RESET + " EU/t");
        ll.add("Maintenance: " + ((super.getRepairStatus() == super.getIdealStatus())
                ? GREEN + "Good " + YELLOW + mEfficiency / 100.0F + " %" + RESET
                : RED + "Has Problems " + mEfficiency / 100.0F + " %" + RESET));
        final String[] a = new String[ll.size()];
        return ll.toArray(a);
    }

    @Override
    public void saveNBTData(NBTTagCompound nbt) {
        nbt = (nbt == null) ? new NBTTagCompound() : nbt;
        nbt.setLong("capacity", capacity);
        nbt.setLong("stored", stored);
        super.saveNBTData(nbt);
    }

    @Override
    public void loadNBTData(NBTTagCompound nbt) {
        nbt = (nbt == null) ? new NBTTagCompound() : nbt;
        capacity = nbt.getLong("capacity");
        stored = nbt.getLong("stored");
        super.loadNBTData(nbt);
    }

    @Override
    public boolean isGivingInformation() {
        return true;
    }

    @Override
    public int getMaxEfficiency(ItemStack stack) {
        return 10000;
    }

    @Override
    public int getPollutionPerTick(ItemStack stack) {
        return 0;
    }

    @Override
    public int getDamageToComponent(ItemStack stack) {
        return 0;
    }

    @Override
    public boolean explodesOnComponentBreak(ItemStack stack) {
        return false;
    }

    @Override
    public long getStored() {
        return stored;
    }

    @Override
    public long getMaxCapacity() {
        return capacity;
    }

    @Override
    @Optional.Method(modid = "Waila")
    public void getWailaBody(ItemStack stack, List<String> tooltip, IWailaDataAccessor accessor,
            IWailaConfigHandler config) {
        super.getWailaBody(stack, tooltip, accessor, config);
        NBTTagCompound tag = accessor.getNBTData();
        tooltip.add(translateToLocalFormatted("impact.waila.lsc.stored", EnumChatFormatting.GREEN
                + NumberFormat.getNumberInstance().format(tag.getLong("Stored")) + EnumChatFormatting.RESET));
        tooltip.add(translateToLocalFormatted("impact.waila.lsc.capacity", EnumChatFormatting.YELLOW
                + NumberFormat.getNumberInstance().format(tag.getLong("Capacity")) + EnumChatFormatting.RESET));
        tooltip.add(translateToLocalFormatted("impact.waila.lsc.in", EnumChatFormatting.GREEN
                + NumberFormat.getNumberInstance().format(tag.getLong("Input")) + EnumChatFormatting.RESET));
        tooltip.add(translateToLocalFormatted("impact.waila.lsc.out", EnumChatFormatting.RED
                + NumberFormat.getNumberInstance().format(tag.getLong("Output")) + EnumChatFormatting.RESET));
    }

    @Override
    @Optional.Method(modid = "Waila")
    public void getWailaNBTData(EntityPlayerMP player, TileEntity tile, NBTTagCompound tag,
            World world, int x, int y, int z) {
        super.getWailaNBTData(player, tile, tag, world, x, y, z);
        tag.setLong("Capacity", capacity);
        tag.setLong("Stored", stored);
        tag.setLong("Input", getAverageInput(LapHistoryPeriod.MINUTE));
        tag.setLong("Output", getAverageOutput(LapHistoryPeriod.MINUTE));
    }
    @Override
    public int getSupportedProcessingModes() {
        return 0;
    }
}
