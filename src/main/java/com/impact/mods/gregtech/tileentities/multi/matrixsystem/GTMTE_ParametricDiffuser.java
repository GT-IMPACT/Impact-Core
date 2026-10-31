package com.impact.mods.gregtech.tileentities.multi.matrixsystem;

import com.impact.loader.ItemRegistery;
import com.impact.mods.gregtech.blocks.ImpactCasings;
import com.impact.mods.gregtech.blocks.ImpactHatchElement;
import com.impact.mods.gregtech.gui.matrixsystem.GT_Container_ParametricDiffuser;
import com.impact.mods.gregtech.gui.matrixsystem.GUI_ParametricDiffuser;
import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_Impact_BlockBase;
import com.impact.network.GTNetworkHandler;
import com.impact.network.NetworkPackets;
import com.impact.network.special.LaserPushPacket;
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
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.oredict.OreDictionary;
import space.impact.structures.hint.HintColor;
import space.impact.structures.structure.IStructureDefinition;
import space.impact.structures.structure.ISurvivalBuildEnvironment;
import space.impact.structures.structure.StructureDefinition;

import static net.minecraft.util.EnumChatFormatting.*;
import static space.impact.structures.structure.StructureUtility.ofBlock;
import static space.impact.structures.structure.StructureUtility.ofBlockAnyMeta;
import static space.impact.structures.structure.StructureUtility.withHint;

public class GTMTE_ParametricDiffuser extends GTMTE_Impact_BlockBase<GTMTE_ParametricDiffuser> {

    private static final ICasing CASING = ImpactCasings.LAB_SAFELG_CASING;

    private static final StructureRequirements SHELL = StructureRequirements.builder(CASING, 0)
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
    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GUI_ParametricDiffuser(aPlayerInventory, aBaseMetaTileEntity, getLocalName());
    }

    @Override
    public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_Container_ParametricDiffuser(aPlayerInventory, aBaseMetaTileEntity);
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
                    .addElement('C', withHint(GTStructureUtility.buildHatchAdder(GTMTE_ParametricDiffuser.class)
                            .allow(HatchElement.Energy, ImpactHatchElement.LaserInput, HatchElement.Input)
                            .buildAndChain(SHELL), HintColor.GREEN))
                    .addElement('R', ofBlock(ItemRegistery.MPSystem, 0))
                    .addElement('G', ofBlockAnyMeta(ItemRegistery.IGlassBlock))
                    .addElement('T', ofBlock(ItemRegistery.MPTransducer, 0))
                    .build();
        }
        return definition;
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        return new MultiblockTooltipBuilder(mName)
                .addInfo("Generation of unstable Matrix Particles")
                .addMachineType("Parametric Diffuser")
                .addInfo("Need any Exquisite Gem")
                .addInfo("Power Beam: (Tier Energy Hatch - 3) * ~200")
                .addInfo("Max range to Matrix Particle Stabilizer: 30 blocks")
                .addInfo("Need Low Gravity")
                .addSeparator()
                .addController("See the structure hologram")
                .addOtherStructurePart("Energy Hatch", "EV and above; maximum 7")
                .addOtherStructurePart("Input Bus", "Any casing; maximum 1")
                .addOtherStructurePart("Maintenance Hatch", "Any casing; maximum 1")
                .addRequirements(SHELL, "Any casing position")
                .addOtherStructurePart("Any I-Glass", "Any casing")
                .addOtherStructurePart("Matrix Particle Reflector", "At least 20")
                .addOtherStructurePart("Matrix Transducer", "At least 1")
                .addStructureHint("Casing or compatible hatch", HintColor.GREEN)
                .toolTipFinisher();
    }

    @Override
    public boolean checkRecipe(ItemStack aStack) {
        for (GT_MetaTileEntity_Hatch_InputBus mInputBuss : mInputBusses) {
            ItemStack gem = mInputBuss.mInventory[0];
            if (gem == null)
                return false;
            int[] idOreDict = OreDictionary.getOreIDs(gem);
            for (int id : idOreDict) {
                if (OreDictionary.getOreName(id).startsWith("gemExquisite")) {
                    this.mMaxProgresstime = 20;
                    this.mEfficiency = (10000 - (this.getIdealStatus() - this.getRepairStatus()) * 1000);
                    this.mEfficiencyIncrease = 10000;
                    int tier = GT_Utility.getTier(getMaxInputVoltage());
                    this.mPeakBeamMP = mEfficiency < 10000 ? 0 : tier >= 4 ? tier - 3 : 0;
                    this.mEUt = -(int) getMaxInputVoltage();
                    return true;
                }
            }
        }
        return false;
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
        return Utilits.isLowGravity(iAm) && checkPiece(0, 2, 1)
                && SHELL.isSatisfied(this, 0)
                && mInputBusses.size() == 1 && mMaintenanceHatches.size() == 1 && mEnergyHatches.size() < 8;
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
    public boolean hasSeparate() {
        return false;
    }
}
