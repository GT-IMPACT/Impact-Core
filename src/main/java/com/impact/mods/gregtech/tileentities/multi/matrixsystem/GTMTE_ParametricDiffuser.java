package com.impact.mods.gregtech.tileentities.multi.matrixsystem;

import gregtech.api.recipe.check.CheckRecipeResult;
import gregtech.api.logic.TimedProcessingLogic;
import gregtech.api.logic.ProcessingLogic;
import com.impact.loader.ItemRegistery;
import com.impact.mods.gregtech.blocks.HatchCounts;
import com.impact.mods.gregtech.blocks.ImpactCasings;
import com.impact.mods.gregtech.blocks.ImpactHatchElement;
import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_Impact_BlockBase;
import com.impact.network.GTNetworkHandler;
import com.impact.network.NetworkPackets;
import com.impact.network.special.LaserPushPacket;
import com.impact.util.ImpactMultiblockTooltipBuilder;
import com.impact.util.Utilits;
import com.impact.util.vector.Vector3i;
import com.impact.util.vector.Vector3ic;
import gregtech.api.enums.HatchElement;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ICasing;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_InputBus;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTStructureUtility;
import gregtech.api.util.GT_Utility;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.StructureRequirements;
import java.util.Random;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.oredict.OreDictionary;
import space.impact.structures.hint.HintColor;
import space.impact.structures.structure.IStructureDefinition;
import space.impact.structures.structure.ISurvivalBuildEnvironment;
import space.impact.structures.structure.StructureDefinition;

import static net.minecraft.util.EnumChatFormatting.GREEN;
import static net.minecraft.util.EnumChatFormatting.RED;
import static net.minecraft.util.EnumChatFormatting.RESET;
import static net.minecraft.util.EnumChatFormatting.YELLOW;
import static space.impact.structures.structure.StructureUtility.ofBlock;
import static space.impact.structures.structure.StructureUtility.ofBlockAnyMeta;

public class GTMTE_ParametricDiffuser extends GTMTE_Impact_BlockBase<GTMTE_ParametricDiffuser> {

    private static final ICasing CASING = ImpactCasings.LAB_SAFELG_CASING;

    private int casingCount;

    private static final StructureRequirements SHELL = StructureRequirements.builder(CASING, 43)
            .atLeast(HatchElement.Maintenance, ImpactHatchElement.InputBus).build();
    private static IStructureDefinition<GTMTE_ParametricDiffuser> definition;
    public int mMPGenerate = 0;
    public boolean checkStabilizer = true;
    public int rangeToStabilizer = 0;
    public int mPeakBeamMP = 1;
    public GTMTE_MPStabilizer mMPStabilizer;

    // region Register
    public GTMTE_ParametricDiffuser(int aID, String aNameRegional) {
        super(aID, "impact.multis.parametricdiffuser", aNameRegional);
    }

    public GTMTE_ParametricDiffuser(String aName) {
        super(aName);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        checkStabilizer = true;
        return new GTMTE_ParametricDiffuser(this.mName);
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
    public void construct(ItemStack itemStack, boolean b) {
        buildPiece(itemStack, b, 0, 2, 1);
    }

    @Override
    public int survivalConstruct(ItemStack trigger, int budget,
            ISurvivalBuildEnvironment environment) {
        return survivalBuildPiece("main", trigger, 0, 2, 1, budget, environment);
    }

    @Override
    public IStructureDefinition<GTMTE_ParametricDiffuser> getStructureDefinition() {
        if (definition == null) {
            definition = StructureDefinition.<GTMTE_ParametricDiffuser>builder()
                    .addChannel(GTStructureUtility.HATCH_CHANNEL, 1, 1)
                    .addShape("main", new String[][] {
                            { "     C   C", "     CCCCC", "     CCCCC", "          " },
                            { "      CCC ", "CCCCCCCCCC", "~CGGGRRRRC", "CCCCCCCCCC" },
                            { "      CCC ", "CCGGGRRRRC", "CT       G", "CCRRRRRRRC" },
                            { "      CCC ", "CCCCCCCCCC", "CCGGGRRRRC", "CCCCCCCCCC" },
                            { "     C   C", "     CCCCC", "     CCCCC", "          " }
                    })
                    .addElement('C', GTStructureUtility.buildHatchAdder(GTMTE_ParametricDiffuser.class)
                            .allow(HatchElement.Energy, ImpactHatchElement.LaserInput, HatchElement.Input)
                            .autoPlace(ImpactHatchElement.PowerInput, HatchElement.Input)
                            .buildAndChain(SHELL, machine -> machine.casingCount++).withHint(HintColor.BLUE))
                    .addElement('R', ofBlock(ItemRegistery.MPSystem, 0))
                    .addElement('G', ofBlockAnyMeta(ItemRegistery.IGlassBlock))
                    .addElement('T', ofBlock(ItemRegistery.MPTransducer, 0))
                    .build();
        }
        return definition;
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        return new ImpactMultiblockTooltipBuilder(mName)
                .useCompactStructure()
                .addInfo("Generates unstable Matrix Particles.")
                .addMachineType("Parametric Diffuser")
                .addInfo("Requires an Exquisite Gem in the first Input Bus slot; the gem is not consumed.")
                .addInfo("Particle generation requires EV or higher; output increases with voltage tier.")
                .addInfo("Range to Matrix Particles Stabilizer: less than 30 blocks.")
                .addInfo("Requires low gravity.")
                .beginStructureBlock(10, 4, 5, false)
                .addController("left end of layer 2, 1 block behind the front edge")
                .addStructureSection("Components")
                .addStructurePartInfo(CASING, "%s+; fill unused casing positions", SHELL.getMinimumCasings())
                .addOtherStructurePart("I-Glass", "exactly 10; inner chamber walls; any color")
                .addOtherStructurePart(new ItemStack(ItemRegistery.MPSystem, 1, 0), "exactly 19; inner chamber")
                .addOtherStructurePart(new ItemStack(ItemRegistery.MPTransducer, 1, 0), "exactly 1; behind the controller, 1 block to the right")
                .addStructureSection("Hatches and buses")
                .addHatchInfo("Maintenance Hatch", "exactly 1; casing positions")
                .addHatchInfo("Input Bus", "exactly 1; casing positions")
                .addHatchInfo("Energy Hatches", "optional, up to 7 total; casing positions")
                .addStructureHint("Casings or hatches/buses", HintColor.BLUE)
                .addHatchChannelInfo()
                .toolTipFinisher();
    }

    @Override
    protected ProcessingLogic createProcessingLogic() {
        return new TimedProcessingLogic(
                () -> Math.multiplyExact(getAverageInputVoltage(), getProcessingAmperage()), () -> 20);
    }

    @Override
    protected CheckRecipeResult validateProcessing() {
        for (GT_MetaTileEntity_Hatch_InputBus bus : mInputBusses) {
            ItemStack gem = bus.mInventory[0];
            if (gem == null) {
                return CheckRecipeResult.MACHINE_CONDITION;
            }
            for (int oreId : OreDictionary.getOreIDs(gem)) {
                if (OreDictionary.getOreName(oreId).startsWith("gemExquisite")) {
                    return super.validateProcessing();
                }
            }
        }
        return CheckRecipeResult.MACHINE_CONDITION;
    }

    @Override
    protected void startCycle(ProcessingLogic.Result result, int efficiency) {
        super.startCycle(result, efficiency);
        int tier = GT_Utility.getTier(getAverageInputVoltage());
        mPeakBeamMP = efficiency < 10000 ? 0 : Math.max(0, tier - 3);
    }

    public void addBound(IGregTechTileEntity iAm) {
        int x = iAm.getXCoord();
        int y = iAm.getYCoord();
        int z = iAm.getZCoord();

        final Vector3ic forgeDirection = new Vector3i(
                ForgeDirection.getOrientation(iAm.getBackFacing()).offsetX,
                ForgeDirection.getOrientation(iAm.getBackFacing()).offsetY,
                ForgeDirection.getOrientation(iAm.getBackFacing()).offsetZ);

        if (iAm.isActive()) {
            Vector3ic offset = rotateOffsetVector(forgeDirection, 1, 0, -1);
            Vector3ic offsetToStabilizer = rotateOffsetVector(forgeDirection, rangeToStabilizer, 0, -1);
            LaserPushPacket packet = NetworkPackets.LaserPushPacket.transaction(
                    iAm.getWorld().provider.dimensionId,
                    new Vector3i(
                            offset.x() + x,
                            offset.y() + y,
                            offset.z() + z),
                    new Vector3i(
                            offsetToStabilizer.x() + x,
                            offsetToStabilizer.y() + y,
                            offsetToStabilizer.z() + z),
                    0x770ED0,
                    1,
                    20,
                    0,
                    1.3f);
            GTNetworkHandler.sendToAllAround(iAm, packet, 50);
        }
    }

    @Override
    public void onPostTick(IGregTechTileEntity iAm, long aTick) {
        super.onPostTick(iAm, aTick);

        if (iAm.isServerSide() && aTick % 20 == 0) {
            mMPGenerate = 0;
            final Vector3ic forgeDirection = new Vector3i(
                    ForgeDirection.getOrientation(iAm.getBackFacing()).offsetX,
                    ForgeDirection.getOrientation(iAm.getBackFacing()).offsetY,
                    ForgeDirection.getOrientation(iAm.getBackFacing()).offsetZ);
            doCheckStabilizer(iAm);

            if (!checkStabilizer && iAm.isActive()) {

                Vector3ic offsetX = rotateOffsetVector(forgeDirection, rangeToStabilizer, 0, 0);
                IGregTechTileEntity currentTE = iAm.getIGregTechTileEntityOffset(offsetX.x(), offsetX.y(), offsetX.z());

                if (currentTE != null) {
                    if (currentTE.getMetaTileEntity() instanceof GTMTE_MPStabilizer) {
                        mMPStabilizer = (GTMTE_MPStabilizer) currentTE.getMetaTileEntity();
                        checkStabilizer = false;
                        if (currentTE.isActive() && mMPStabilizer.mMPSummary < 100_000) {
                            Random random = new Random();
                            mMPGenerate = random.nextInt(200) * mPeakBeamMP;
                            // Utilits.sendChatByTE(iAm, "" + mMPGenerate);

                            mMPStabilizer.setMP(mMPGenerate);
                            addBound(iAm);
                            if (mMPStabilizer.mMPSummary > 100_000) {
                                mMPStabilizer.mMPSummary = 100_000;
                            }
                        }
                    }
                } else {
                    mMPStabilizer = null;
                    checkStabilizer = true;
                    rangeToStabilizer = 0;
                }
            }
        }
    }

    public void doCheckStabilizer(IGregTechTileEntity iAm) {

        if (mMachine && checkStabilizer) {

            final Vector3ic forgeDirection = new Vector3i(
                    ForgeDirection.getOrientation(iAm.getBackFacing()).offsetX,
                    ForgeDirection.getOrientation(iAm.getBackFacing()).offsetY,
                    ForgeDirection.getOrientation(iAm.getBackFacing()).offsetZ);

            Vector3ic offsetX;
            IGregTechTileEntity currentTE = null;

            for (; rangeToStabilizer < 30; rangeToStabilizer++) {

                offsetX = rotateOffsetVector(forgeDirection, rangeToStabilizer, 0, 0);

                currentTE = iAm.getIGregTechTileEntityOffset(offsetX.x(), offsetX.y(), offsetX.z());

                if (currentTE != null) {
                    if (currentTE.getMetaTileEntity() instanceof GTMTE_MPStabilizer) {
                        mMPStabilizer = (GTMTE_MPStabilizer) currentTE.getMetaTileEntity();
                        checkStabilizer = false;
                        break;
                    }
                }
            }
            if (currentTE == null) {
                mMPStabilizer = null;
                checkStabilizer = true;
                rangeToStabilizer = 0;
            }
        }
    }

    @Override
    public boolean machineStructure(IGregTechTileEntity iAm) {
        casingCount = 0;
        return Utilits.isLowGravity(iAm) && checkPiece(0, 2, 1)
                && SHELL.isSatisfied(this, casingCount)
                && HatchCounts.inputBuses(this) == 1 && mMaintenanceHatches.size() == 1 && HatchCounts.energyHatches(this) < 8;
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
        aNBT.setInteger("mMPGenerate", mMPGenerate);
        aNBT.setInteger("rangeToStabilizer", rangeToStabilizer);
        aNBT.setInteger("mPeakBeamMP", mPeakBeamMP);
        aNBT.setBoolean("checkStabilizer", checkStabilizer);
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
        mMPGenerate = aNBT.getInteger("mMPGenerate");
        rangeToStabilizer = aNBT.getInteger("rangeToStabilizer");
        mPeakBeamMP = aNBT.getInteger("mPeakBeamMP");
        checkStabilizer = aNBT.getBoolean("checkStabilizer");
    }

    public String[] getInfoData() {
        return new String[] {
                "Usage Energy: " + RED + -mEUt + RESET + " EU/t",
                "Max Voltage: " + YELLOW + getMaxInputVoltage() + RESET + " EU/t ",
                "Maintenance: " + ((super.getRepairStatus() == super.getIdealStatus())
                        ? GREEN + "Good " + YELLOW + mEfficiency / 100.0F + " %" + RESET
                        : RED + "Has Problems " + mEfficiency / 100.0F + " %" + RESET),
        };
    }

    @Override
    public int getSupportedProcessingModes() {
        return 0;
    }
}
