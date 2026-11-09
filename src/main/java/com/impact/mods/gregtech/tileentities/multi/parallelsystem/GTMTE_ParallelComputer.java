package com.impact.mods.gregtech.tileentities.multi.parallelsystem;

import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.screen.UISettings;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.impact.mods.gregtech.gui.parallelcomputer.ParallelComputerGui;
import gregtech.api.logic.ProcessingLogic;
import gregtech.api.logic.TimedProcessingLogic;
import com.impact.mods.gregtech.blocks.HatchCounts;
import com.impact.mods.gregtech.blocks.ImpactCasings;
import com.impact.mods.gregtech.blocks.ImpactHatchElement;
import com.impact.mods.gregtech.tileentities.multi.implement.GT_MetaTileEntity_MultiParallelBlockBase;
import com.impact.util.ImpactMultiblockTooltipBuilder;
import gregtech.api.enums.HatchElement;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ICasing;
import gregtech.api.interfaces.IHatchElement;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTStructureUtility;
import gregtech.api.util.GTStructureChannels;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.StructureRequirements;
import java.util.HashSet;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import space.impact.structures.hint.HintColor;
import space.impact.structures.structure.IStructureDefinition;
import space.impact.structures.structure.ISurvivalBuildEnvironment;
import space.impact.structures.structure.StructureDefinition;

import static com.impact.loader.ItemRegistery.InsideBlock;
import static net.minecraft.util.EnumChatFormatting.GREEN;
import static net.minecraft.util.EnumChatFormatting.RED;
import static net.minecraft.util.EnumChatFormatting.RESET;
import static net.minecraft.util.EnumChatFormatting.YELLOW;
import static space.impact.structures.structure.StructureUtility.ofBlock;
import static space.impact.structures.structure.StructureUtility.onElementPass;
import static space.impact.structures.structure.StructureUtility.withHint;

public class GTMTE_ParallelComputer extends GT_MetaTileEntity_MultiParallelBlockBase<GTMTE_ParallelComputer> {

    private static final ICasing CASING = ImpactCasings.COMPUTER_CASING;
    private static final int MIN_RACK_LAYERS = 2;
    private static final int MAX_RACK_LAYERS = 16;

    public int mMaxCapacityPP = 0;
    public int mCurrentCapacityPP = 0;
    private int casingCount;

    private static final StructureRequirements SHELL = StructureRequirements.builder(CASING, 12)
            .atLeast(HatchElement.Maintenance, ImpactHatchElement.PowerInput).build();
    private static final StructureRequirements RACKS = StructureRequirements.builder(CASING, 0).build();
    private static IStructureDefinition<GTMTE_ParallelComputer> definition;

    public final HashSet<GTMTE_ComputerRack> sComputerRack = new HashSet<>();

    private int constructionLayers(ItemStack trigger) {
        return Math.max(MIN_RACK_LAYERS, Math.min(MAX_RACK_LAYERS,
                (GTStructureChannels.getValue(trigger, GTStructureChannels.LENGTH)) + MIN_RACK_LAYERS - 1));
    }

    @Override
    public void construct(ItemStack trigger, boolean hintsOnly) {
        int layers = constructionLayers(trigger);
        buildPiece("front", trigger, hintsOnly, 0, 2, 0);
        for (int depth = 1; depth <= layers; depth++) {
            buildPiece("layer", trigger, hintsOnly, 0, 2, -depth);
        }
        buildPiece("end", trigger, hintsOnly, 0, 2, -layers - 1);
    }

    @Override
    public int survivalConstruct(ItemStack trigger, int budget, ISurvivalBuildEnvironment environment) {
        if (mMachine) {
            return -1;
        }
        int layers = constructionLayers(trigger);
        int built = Math.max(0, survivalBuildPiece("front", trigger, 0, 2, 0, budget, environment));
        for (int depth = 1; depth <= layers && built < budget; depth++) {
            built += Math.max(0, survivalBuildPiece("layer", trigger, 0, 2, -depth, budget - built, environment));
        }
        if (built < budget) {
            built += Math.max(0, survivalBuildPiece("end", trigger, 0, 2, -layers - 1, budget - built, environment));
        }
        return built;
    }

    @Override
    public IStructureDefinition<GTMTE_ParallelComputer> getStructureDefinition() {
        if (definition == null) {
            definition = StructureDefinition.<GTMTE_ParallelComputer>builder()
                    .addChannel(GTStructureUtility.HATCH_CHANNEL, 1, 1)
                    .addChannel(GTStructureChannels.LENGTH, 1, MAX_RACK_LAYERS - MIN_RACK_LAYERS + 1)
                    .addShape("front", new String[][] { { "CC", "CC", "~C", "CC" } })
                    .addShape("layer", new String[][] { { "CC", "PR", "PR", "CC" } })
                    .addShape("end", new String[][] { { "CC", "BB", "BB", "CC" } })
                    .addShape("shell", new String[][] { { "CC", "  ", "  ", "CC" } })
                    .addShape("terminator", new String[][] { { "B" } })
                    .addShape("parallel", new String[][] { { "P" } })
                    .addShape("rack", new String[][] { { "R" } })
                    .addElement('C', withHint(GTStructureUtility.buildHatchAdder(GTMTE_ParallelComputer.class)
                            .allow(HatchElement.Energy, ImpactHatchElement.LaserInput, HatchElement.Output)
                            .autoPlace(ImpactHatchElement.PowerInput, HatchElement.Output)
                            .buildAndChain(SHELL, machine -> machine.casingCount++), HintColor.GREEN))
                    .addElement('B', onElementPass(machine -> machine.casingCount++, CASING.asElement()))
                    .addElement('P', withHint(GTStructureUtility.buildHatchAdder(GTMTE_ParallelComputer.class)
                            .allow(ComputerHatch.ParallelOutput)
                            .autoPlace(Integer.MAX_VALUE, ComputerHatch.ParallelOutput)
                            .buildAndChain(RACKS, ofBlock(InsideBlock, 2), t -> {
                            }), HintColor.PURPLE))
                    .addElement('R', withHint(GTStructureUtility.buildHatchAdder(GTMTE_ParallelComputer.class)
                            .allow(ComputerHatch.Rack)
                            .autoPlace(Integer.MAX_VALUE, ComputerHatch.Rack)
                            .buildAndChain(RACKS, ofBlock(InsideBlock, 2), t -> {
                            }), HintColor.ORANGE))
                    .build();
        }
        return definition;
    }

    private enum ComputerHatch implements IHatchElement {
        Rack {
            @Override
            public boolean matches(IMetaTileEntity tile) {
                return tile instanceof GTMTE_ComputerRack;
            }

            @Override
            public int count(GT_MetaTileEntity_MultiBlockBase machine) {
                return ((GTMTE_ParallelComputer) machine).sComputerRack.size();
            }

            @Override
            public boolean add(GT_MetaTileEntity_MultiBlockBase machine, IGregTechTileEntity tile, int texture) {
                return ((GTMTE_ParallelComputer) machine).addRackHatch(tile, texture);
            }

            @Override
            public String getDescription() {
                return StatCollector.translateToLocal("impact.structure.hatch.computer_rack");
            }
        },
        ParallelOutput {
            @Override
            public boolean matches(IMetaTileEntity tile) {
                return tile instanceof GTMTE_ParallelHatch_Output;
            }

            @Override
            public int count(GT_MetaTileEntity_MultiBlockBase machine) {
                return ((GTMTE_ParallelComputer) machine).sParallHatchesOut.size();
            }

            @Override
            public boolean add(GT_MetaTileEntity_MultiBlockBase machine, IGregTechTileEntity tile, int texture) {
                return ((GTMTE_ParallelComputer) machine).addParallHatchToMachineList(tile, texture);
            }

            @Override
            public String getDescription() {
                return StatCollector.translateToLocal("impact.structure.hatch.parallel_output");
            }
        }
    }

    // region Register
    public GTMTE_ParallelComputer(int aID, String aNameRegional) {
        super(aID, "impact.multis.parallelcomputer", aNameRegional);
    }

    public GTMTE_ParallelComputer(String aName) {
        super(aName);
    }

    @Override
    public ModularPanel buildUI(PosGuiData data, PanelSyncManager sync, UISettings settings) {
        return ParallelComputerGui.build(this, data, sync, settings);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GTMTE_ParallelComputer(this.mName);
    }
    // endregion

    @Override
    public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide, final byte aFacing,
            final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
        return aSide == aFacing
                ? new ITexture[] { CASING.getTexture(),
                        TextureFactory.of(aActive ? Textures.BlockIcons.MP1a : Textures.BlockIcons.MP1) }
                : new ITexture[] { CASING.getTexture() };
    }


    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        return new ImpactMultiblockTooltipBuilder(mName)
                .useCompactStructure()
                .addMachineType("Super Parallel Computer")
                .addInfo("Distributes parallel points from Parallel Computer Racks through Parallel Transmitters.")
                .addInfo("Requires a satellite network connection to supply parallel points.")
                .addInfo("Power consumption: 8192 EU/t plus 1 EU/t per installed parallel point.")
                .beginVariableStructureBlock(2, 2, 4, 4, 4, 18, false)
                .addController("front left of layer 2")
                .addStructureSection("Components")
                .addOtherStructurePart(CASING, "minimum %s; shell and rear ends of the 4 inner columns", "8 + 2 x rack layers")
                .addOtherStructurePart(new ItemStack(InsideBlock, 1, 2), "fill unused inner rack/transmitter positions")
                .addStructureSection("Hatches and buses")
                .addHatchInfo("Maintenance Hatch", "exactly 1; front, top and bottom casings")
                .addHatchInfo("Energy Hatches", "1-4 total; front, top and bottom casings")
                .addHatchInfo("Parallel Computer Rack", "optional, up to 2 per rack layer; right inner column")
                .addHatchInfo("Parallel Transmitter", "optional, up to 2 per rack layer; left inner column")
                .addStructureHint("Casings or energy/maintenance hatches", HintColor.GREEN)
                .addStructureHint("Empty Rack Casing or Parallel Transmitter", HintColor.PURPLE)
                .addStructureHint("Empty Rack Casing or Parallel Computer Rack", HintColor.ORANGE)
                .addSubChannel(GTStructureChannels.LENGTH, "1-15; %s; default: stack size.", "rack layers = value + 1; length = value + 3")
                .addHatchChannelInfo()
                .toolTipFinisher();
    }

    @Override
    public void onPostTick(IGregTechTileEntity te, long aTick) {
        super.onPostTick(te, aTick);
        if (te.isServerSide() && aTick % 100 == 0) {
            updateStatusParallelHatches(te);
        }
    }

    private void updateStatusParallelHatches(IGregTechTileEntity te) {
        int maxCurrentParallel = 0;

        boolean isActiveRack = false;
        for (GTMTE_ComputerRack rack : sComputerRack) {
            if (te.isActive()) {
                maxCurrentParallel += rack.mCapacityP;
                isActiveRack = true;
            }
            rack.getBaseMetaTileEntity().setActive(isActiveRack);
        }

        setMaxCapacityPP(maxCurrentParallel);

        for (GTMTE_ParallelHatch_Output ph : sParallHatchesOut) {

            int currentParallel = ph.getParallel();
            boolean checkSum = mCurrentCapacityPP - currentParallel >= 0;
            boolean parallelIsDone = false;

            if (checkSum && ph.getBaseMetaTileEntity().isAllowedToWork()) {
                mCurrentCapacityPP -= currentParallel;
                parallelIsDone = isSatelliteConnected && te.isActive();
            }
            ph.updateStatus(parallelIsDone);
        }
    }

    @Override
    protected ProcessingLogic createProcessingLogic() {
        return new TimedProcessingLogic(() -> 8192L + mMaxCapacityPP, () -> 10);
    }

    public int getCurrentCapacityPP() {
        return mCurrentCapacityPP;
    }

    public void setCurrentCapacityPP(int curCaPP) {
        mCurrentCapacityPP = curCaPP;
    }

    public int getMaxCapacityPP() {
        return mMaxCapacityPP;
    }

    public void setMaxCapacityPP(int setMax) {
        setCurrentCapacityPP(setMax);
        mMaxCapacityPP = setMax;
        mParallel = setMax;
    }

    public boolean addRackHatch(IGregTechTileEntity aTileEntity, int aBaseCasingIndex) {
        if (aTileEntity == null) {
            return false;
        } else {
            final IMetaTileEntity aMetaTileEntity = aTileEntity.getMetaTileEntity();
            if (aMetaTileEntity == null) {
                return false;
            } else if (aMetaTileEntity instanceof GTMTE_ComputerRack) {
                ((GTMTE_ComputerRack) aMetaTileEntity).updateTexture(aBaseCasingIndex);
                return sComputerRack.add((GTMTE_ComputerRack) aMetaTileEntity);
            } else {
                return false;
            }
        }
    }

    public boolean addParallHatchToMachineList(IGregTechTileEntity aTileEntity, int aBaseCasingIndex) {
        if (aTileEntity == null) {
            return false;
        } else {
            final IMetaTileEntity aMetaTileEntity = aTileEntity.getMetaTileEntity();
            if (aMetaTileEntity == null) {
                return false;
            } else if (aMetaTileEntity instanceof GTMTE_ParallelHatch_Output) {
                ((GTMTE_ParallelHatch_Output) aMetaTileEntity).updateTexture(aBaseCasingIndex);
                return sParallHatchesOut.add((GTMTE_ParallelHatch_Output) aMetaTileEntity);
            } else {
                return false;
            }
        }
    }

    @Override
    public void clearHatches() {
        sComputerRack.clear();
        sParallHatchesOut.clear();
        super.clearHatches();
    }

    @Override
    public boolean machineStructure(IGregTechTileEntity thisController) {
        casingCount = 0;
        boolean formed = checkPiece("front", 0, 2, 0);
        int[] ends = new int[2];
        int checkedDepth = 0;
        // Each rack column may stop at its own casing, as in existing installations.
        for (int x = 0; x <= 1; x++) {
            for (int y = 0; y <= 1; y++) {
                boolean terminated = false;
                for (int depth = 1; depth <= MAX_RACK_LAYERS + 1; depth++) {
                    checkedDepth = Math.max(checkedDepth, depth);
                    if (checkColumnPosition("terminator", x, y, depth)) {
                        formed &= depth >= MIN_RACK_LAYERS + 1;
                        terminated = true;
                        ends[x] = depth;
                        break;
                    }
                    formed &= checkColumnPosition(x == 0 ? "parallel" : "rack", x, y, depth);
                }
                formed &= terminated;
            }
        }
        formed &= ends[0] == ends[1];
        // Scan unfinished layers too, so automatic construction sees existing power hatches.
        for (int depth = 1; depth <= checkedDepth; depth++) {
            formed &= checkPiece("shell", 0, 2, -depth);
        }
        return formed
                && SHELL.isSatisfied(this, casingCount)
                && casingCount >= (12 + 4 * ends[1]) / 2
                && mMaintenanceHatches.size() == 1
                && HatchCounts.energyHatches(this) <= 4;
    }

    private boolean checkColumnPosition(String piece, int column, int row, int depth) {
        IGregTechTileEntity base = getBaseMetaTileEntity();
        int[] offset = new int[3];
        getExtendedFacing().getWorldOffset(new int[] { column, -row, depth }, offset);
        World world = base.getWorld();
        int x = base.getXCoord() + offset[0];
        int y = base.getYCoord() + offset[1];
        int z = base.getZCoord() + offset[2];
        // These single-block probes try different elements at one position, not construction alternatives.
        return world.blockExists(x, y, z)
                && getStructureDefinition().getStructureFor(piece)[0].check(this, world, x, y, z);
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
        aNBT.setInteger("mMaxCapacityPP", mMaxCapacityPP);
        aNBT.setInteger("mCurrentCapacityPP", mCurrentCapacityPP);
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
        mMaxCapacityPP = aNBT.getInteger("mMaxCapacityPP");
        mCurrentCapacityPP = aNBT.getInteger("mCurrentCapacityPP");
    }

    @Override
    public int getCurrentParallel() {
        if (getMaxCapacityPP() < 4) {
            return -1;
        }
        return getMaxCapacityPP();
    }

    @Override
    public int getSupportedProcessingModes() {
        return 0;
    }

    public String[] getInfoData() {
        return new String[] {
                "Storage PP: " + GREEN + getMaxCapacityPP() + RESET + " / " + YELLOW + getCurrentCapacityPP() + "PP",
                "Usage Energy: " + RED + -mEUt + RESET + " EU/t",
                "Max Voltage: " + YELLOW + getMaxInputVoltage() + RESET + " EU/t ",
                "Maintenance: " + ((super.getRepairStatus() == super.getIdealStatus())
                        ? GREEN + "Good " + YELLOW + mEfficiency / 100.0F + " %" + RESET
                        : RED + "Has Problems " + mEfficiency / 100.0F + " %" + RESET),
        };
    }
    @Override
    public String[] getProcessingInfo() {
        return new String[] {
                StatCollector.translateToLocalFormatted("impact.processing.parallel_capacity",
                        mCurrentCapacityPP, mMaxCapacityPP)
        };
    }

}
