package com.impact.mods.gregtech.tileentities.multi.matrixsystem;

import com.impact.impact;
import com.impact.loader.ItemRegistery;
import com.impact.mods.gregtech.blocks.ImpactCasings;
import com.impact.mods.gregtech.blocks.ImpactHatchElement;
import com.impact.mods.gregtech.gui.base.GTC_ImpactBase;
import com.impact.mods.gregtech.gui.base.GUI_BASE;
import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_Impact_BlockBase;
import com.impact.network.GTNetworkHandler;
import com.impact.network.NetworkPackets;
import com.impact.network.special.LaserPushPacket;
import com.impact.util.Utilits;
import com.impact.util.vector.Vector3i;
import com.impact.util.vector.Vector3ic;
import cpw.mods.fml.common.Optional;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.HatchElement;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ICasing;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTStructureUtility;
import gregtech.api.util.GT_Utility;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.StructureRequirements;
import java.util.List;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import space.impact.structures.hint.HintColor;
import space.impact.structures.structure.IStructureDefinition;
import space.impact.structures.structure.ISurvivalBuildEnvironment;
import space.impact.structures.structure.StructureDefinition;

import static net.minecraft.util.EnumChatFormatting.*;
import static net.minecraft.util.StatCollector.translateToLocalFormatted;
import static space.impact.structures.structure.StructureUtility.ofBlock;
import static space.impact.structures.structure.StructureUtility.ofBlockAnyMeta;
import static space.impact.structures.structure.StructureUtility.withHint;

public class GTMTE_MPStabilizer extends GTMTE_Impact_BlockBase<GTMTE_MPStabilizer> {

    private static final ICasing CASING = ImpactCasings.LAB_SAFELG_CASING;

    private static final StructureRequirements SHELL = StructureRequirements.builder(CASING, 0).build();
    private static IStructureDefinition<GTMTE_MPStabilizer> definition;
    public int mMPSummary = 0;
    public boolean mMPBeam = false;
    public int mRangeToContainer = 0;
    public boolean mCheckContainer = true;
    public GTMTE_MPContainment mMPContainment;

    // region Register
    public GTMTE_MPStabilizer(int aID, String aNameRegional) {
        super(aID, "impact.multis.matrixstabilizer", aNameRegional);
    }

    public GTMTE_MPStabilizer(String aName) {
        super(aName);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GTMTE_MPStabilizer(this.mName);
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
        return new GUI_BASE(aPlayerInventory, aBaseMetaTileEntity, getLocalName());
    }

    @Override
    public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GTC_ImpactBase(aPlayerInventory, aBaseMetaTileEntity);
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        return new MultiblockTooltipBuilder(mName)
                .addInfo("Stabilizes unstable Matrix Particles")
                .addMachineType("Stabilizer")
                .addInfo("Max range to Matrix Particle Containment: 30 blocks")
                .addInfo("Constant power consumption: 1,920 EU/t")
                .addInfo("Need Low Gravity")
                .addSeparator()
                .addController("See the structure hologram")
                .addOtherStructurePart("Energy Hatch", "Any casing")
                .addOtherStructurePart("Maintenance Hatch", "Any casing; maximum 1")
                .addRequirements(SHELL, "Any casing position")
                .addOtherStructurePart("Any I-Glass", "Any casing")
                .addOtherStructurePart("Matrix Particle Reflector", "At least 11")
                .addStructureHint("Casing or compatible hatch", HintColor.GREEN)
                .toolTipFinisher();
    }

    @Override
    public void construct(ItemStack itemStack, boolean b) {
        buildPiece(itemStack, b, 2, 1, 0);
    }

    @Override
    public int survivalConstruct(ItemStack trigger, int budget,
            ISurvivalBuildEnvironment environment) {
        return survivalBuildPiece("main", trigger, 2, 1, 0, budget, environment);
    }

    @Override
    public IStructureDefinition<GTMTE_MPStabilizer> getStructureDefinition() {
        if (definition == null) {
            definition = StructureDefinition.<GTMTE_MPStabilizer>builder()
                    .addChannel(GTStructureUtility.HATCH_CHANNEL, 1, 1)
                    .addShape("main", new String[][] {
                            { "CC CC", "CR~RC", "CC CC" },
                            { "CRRRC", "G   G", "CRRRC" },
                            { "CC CC", "CRRRC", "CC CC" }
                    })
                    .addElement('C', withHint(GTStructureUtility.buildHatchAdder(GTMTE_MPStabilizer.class)
                            .allow(HatchElement.Maintenance, HatchElement.Energy, ImpactHatchElement.LaserInput)
                            .buildAndChain(SHELL), HintColor.GREEN))
                    .addElement('R', ofBlock(ItemRegistery.MPSystem, 0))
                    .addElement('G', ofBlockAnyMeta(ItemRegistery.IGlassBlock))
                    .build();
        }
        return definition;
    }

    @Override
    public boolean checkRecipe(ItemStack aStack) {
        this.mEUt = -1920;
        this.mEfficiency = (10000 - (this.getIdealStatus() - this.getRepairStatus()) * 1000);
        this.mMaxProgresstime = 40;
        return true;
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

            Vector3ic offset = rotateOffsetVector(forgeDirection, 0, 0, -1);
            Vector3ic offsetToStabilizer = rotateOffsetVector(forgeDirection, mRangeToContainer - 1, 0, -1);

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
                    20 * 2,
                    1,
                    1);
            GTNetworkHandler.sendToAllAround(iAm, packet, 50);
        }
    }

    @Override
    public void onPostTick(IGregTechTileEntity iAm, long aTick) {
        super.onPostTick(iAm, aTick);

        if (iAm.isServerSide() && aTick % 20 * 5 == 0) {

            if (!iAm.isActive())
                mMPSummary = 0;

            final Vector3ic forgeDirection = new Vector3i(
                    ForgeDirection.getOrientation(iAm.getBackFacing()).offsetX,
                    ForgeDirection.getOrientation(iAm.getBackFacing()).offsetY,
                    ForgeDirection.getOrientation(iAm.getBackFacing()).offsetZ);
            doCheckContainer(iAm);

            if (iAm.isActive()) {
                Vector3ic offsetCheckBlock = rotateOffsetVector(forgeDirection, 0, 2, -1);
                impact.proxy.nodeBolt(iAm.getWorld(), iAm.getXCoord(), iAm.getYCoord(), iAm.getZCoord(),
                        offsetCheckBlock.x(), offsetCheckBlock.y(), offsetCheckBlock.z(), 5, 10F, 1);
                impact.proxy.nodeBolt(iAm.getWorld(), iAm.getXCoord(), iAm.getYCoord(), iAm.getZCoord(),
                        offsetCheckBlock.x(), offsetCheckBlock.y(), offsetCheckBlock.z(), 5, 10F, 1);

                for (Object o : iAm.getWorld().playerEntities) {
                    if (o instanceof EntityPlayer) {
                        EntityPlayer player1 = (EntityPlayer) o;
                        if (Utilits.distanceBetween3D(iAm.getXCoord(), (int) player1.posX, iAm.getYCoord(),
                                (int) player1.posY, iAm.getZCoord(), (int) player1.posZ) < 8) {
                            impact.proxy.nodeBolt(iAm.getWorld(), iAm.getXCoord(), iAm.getYCoord(), iAm.getZCoord(),
                                    player1);
                        }
                    }
                }
            }

            if (!mCheckContainer && iAm.isActive()) {

                if (mMPSummary >= 1000) {

                    Vector3ic offsetX = rotateOffsetVector(forgeDirection, mRangeToContainer, 0, 1);
                    IGregTechTileEntity currentTE = iAm.getIGregTechTileEntityOffset(offsetX.x(), offsetX.y(),
                            offsetX.z());

                    if (currentTE != null) {
                        if (currentTE.getMetaTileEntity() instanceof GTMTE_MPContainment) {
                            mMPContainment = (GTMTE_MPContainment) currentTE.getMetaTileEntity();
                            mCheckContainer = false;
                            if (currentTE.isActive()) {
                                if (mMPSummary >= 100_000) {
                                    this.mEUt = -((int) GT_Values.V[4] / 4);
                                }
                                if (mMPContainment.mMPStable <= 99_900) {
                                    mMPContainment.setMP(1000);
                                    addBound(iAm);
                                    mMPSummary -= 1000;
                                }
                            }
                        }
                    } else {
                        mMPContainment = null;
                        mCheckContainer = true;
                        mRangeToContainer = 0;
                    }
                }
            }
        }
    }

    public void setMP(int amount) {
        mMPSummary += amount;
    }

    public void doCheckContainer(IGregTechTileEntity iAm) {
        if (mMachine && mCheckContainer) {

            final Vector3ic forgeDirection = new Vector3i(
                    ForgeDirection.getOrientation(iAm.getBackFacing()).offsetX,
                    ForgeDirection.getOrientation(iAm.getBackFacing()).offsetY,
                    ForgeDirection.getOrientation(iAm.getBackFacing()).offsetZ);

            Vector3ic offsetX;
            IGregTechTileEntity currentTE = null;

            for (; mRangeToContainer < 30; mRangeToContainer++) {

                offsetX = rotateOffsetVector(forgeDirection, mRangeToContainer, 0, 1);
                currentTE = iAm.getIGregTechTileEntityOffset(offsetX.x(), offsetX.y(), offsetX.z());
                if (currentTE != null) {
                    if (currentTE.getMetaTileEntity() instanceof GTMTE_MPContainment) {
                        mMPContainment = (GTMTE_MPContainment) currentTE.getMetaTileEntity();
                        mCheckContainer = false;
                        break;
                    }
                }
            }
            if (currentTE == null) {
                mMPContainment = null;
                mCheckContainer = true;
                mRangeToContainer = 0;
            }
        }
    }

    @Override
    public boolean machineStructure(IGregTechTileEntity iAm) {

        if (!Utilits.isLowGravity(iAm)) {
            return false;
        }
        boolean formed = checkPiece(2, 1, 0);
        return formed;
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
        aNBT.setInteger("mMPSummary", mMPSummary);
        aNBT.setInteger("mRangeToContainer", mRangeToContainer);
        aNBT.setBoolean("mMPBeam", mMPBeam);
        aNBT.setBoolean("mCheckContainer", mCheckContainer);
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
        mMPSummary = aNBT.getInteger("mMPSummary");
        mRangeToContainer = aNBT.getInteger("mRangeToContainer");
        mMPBeam = aNBT.getBoolean("mMPBeam");
        mCheckContainer = aNBT.getBoolean("mCheckContainer");
    }

    public String[] getInfoData() {
        return new String[] {
                "Usage Energy: " + RED + -mEUt + RESET + " EU/t",
                "Max Voltage: " + YELLOW + getMaxInputVoltage() + RESET + " EU/t ",
                "Maintenance: " + ((super.getRepairStatus() == super.getIdealStatus()) ? GREEN + "Good "
                        + YELLOW + mEfficiency / 100.0F + " %" + RESET
                        : RED + "Has Problems " + mEfficiency / 100.0F + " %" + RESET),
        };
    }

    @Override
    public boolean hasSeparate() {
        return false;
    }

    @Override
    @Optional.Method(modid = "Waila")
    public void getWailaBody(ItemStack stack, List<String> tooltip, IWailaDataAccessor accessor,
            IWailaConfigHandler config) {
        super.getWailaBody(stack, tooltip, accessor, config);
        NBTTagCompound tag = accessor.getNBTData();
        tooltip.add(translateToLocalFormatted("impact.waila.matrix.particles",
                GT_Utility.formatNumbers(tag.getInteger("mMPSummary"))));
    }

    @Override
    @Optional.Method(modid = "Waila")
    public void getWailaNBTData(EntityPlayerMP player, TileEntity tile, NBTTagCompound tag,
            World world, int x, int y, int z) {
        super.getWailaNBTData(player, tile, tag, world, x, y, z);
        tag.setInteger("mMPSummary", mMPSummary);
    }
}
