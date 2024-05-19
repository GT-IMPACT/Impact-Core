package com.impact.mods.gregtech.tileentities.multi.parallelsystem;

import com.impact.addon.gt.api.parallel_system.INetworkMachine;
import com.impact.addon.gt.api.parallel_system.INetworkTower;
import com.impact.addon.gt.api.parallel_system.SatelliteNetworkLogic;
import com.impact.mods.gregtech.blocks.Casing_Helper;
import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_Impact_BlockBase;
import com.impact.network.GTNetworkHandler;
import com.impact.network.NetworkPackets;
import com.impact.network.special.LaserPushPacket;
import com.impact.util.PositionObject;
import com.impact.util.string.MultiBlockTooltipBuilder;
import gregtech.api.enums.Dyes;
import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GT_LanguageManager;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import org.jetbrains.annotations.NotNull;
import space.impact.api.multiblocks.structure.IStructureDefinition;
import space.impact.api.multiblocks.structure.StructureDefinition;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static com.impact.mods.gregtech.enums.Texture.Icons.TOWER_OVERLAY;
import static com.impact.mods.gregtech.enums.Texture.Icons.TOWER_OVERLAY_ACTIVE;
import static com.impact.util.multis.GT_StructureUtility.ofFrame;
import static com.impact.util.multis.GT_StructureUtility.ofHatchAdder;
import static space.impact.api.multiblocks.structure.StructureUtility.*;

public class GTMTE_TowerCommunication extends GTMTE_Impact_BlockBase<GTMTE_TowerCommunication> implements INetworkTower {

    public static Block CASING = Casing_Helper.sCasePage8_3;
    public static byte CASING_META = 6;
    public static ITexture INDEX_CASE = Textures.BlockIcons.casingTexturePages[8][CASING_META + 64];
    public static int CASING_TEXTURE_ID = CASING_META + 64 + 128 * 8;
    private static final IStructureDefinition<GTMTE_TowerCommunication> STRUCTURE_DEFINITION =
            StructureDefinition.<GTMTE_TowerCommunication>builder()
                    .addShape("main", new String[][]{
                            {"       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", " EEEEE ", "EE   EE", "E     E", "E     E", "E     E"},
                            {"       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "  EEE  ", " EE EE ", " E   E ", " E   E ", " E   E ", "EE   EE", "E     E", "       ", "  AAA  ", "  AAA  "},
                            {"       ", "       ", "       ", "       ", "   D   ", "  E E  ", "  E E  ", "  E E  ", "  E E  ", " EE EE ", " E   E ", "       ", "       ", "       ", "E     E", "       ", "       ", " AAAAA ", " AAAAA "},
                            {"   E   ", "   E   ", "   E   ", "   E   ", "  DED  ", "   E   ", "       ", "       ", "       ", " E   E ", "       ", "       ", "       ", "       ", "E     E", "       ", "       ", " AA~AA ", " AAAAA "},
                            {"       ", "       ", "       ", "       ", "   D   ", "  E E  ", "  E E  ", "  E E  ", "  E E  ", " EE EE ", " E   E ", "       ", "       ", "       ", "E     E", "       ", "       ", " AAAAA ", " AAAAA "},
                            {"       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "  EEE  ", " EE EE ", " E   E ", " E   E ", " E   E ", "EE   EE", "E     E", "       ", "  AAA  ", "  AAA  "},
                            {"       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", " EEEEE ", "EE   EE", "E     E", "E     E", "E     E"}
                    })
                    .addElement('A', ofChain(
                            ofHatchAdder(GTMTE_TowerCommunication::addToMachineList, CASING_TEXTURE_ID, CASING, CASING_META),
                            ofBlock(CASING, CASING_META)
                    ))
                    .addElement('D', ofHatchAdder(GTMTE_TowerCommunication::addCommunicationHatchToMachineList, CASING_TEXTURE_ID, CASING, CASING_META))
                    .addElement('E', lazy(t -> ofFrame(Materials.Steel)))
                    .build();

    private final HashSet<INetworkMachine> connections = new HashSet<>();
    public final HashSet<GTMTE_CommunicationTower_Receiver> sCommunReceiver = new HashSet<>();
    private boolean isShowConnections = false;

    public GTMTE_TowerCommunication(int aID, String aNameRegional) {
        super(aID, "impact.multis.communicationtower", aNameRegional);
    }

    public GTMTE_TowerCommunication(String aName) {
        super(aName);
    }

    @Override
    public boolean hasIndicator() {
        return true;
    }

    public boolean addCommunicationHatchToMachineList(IGregTechTileEntity aTileEntity, int aBaseCasingIndex) {
        if (aTileEntity == null) {
            return false;
        } else {
            final IMetaTileEntity aMetaTileEntity = aTileEntity.getMetaTileEntity();
            if (aMetaTileEntity == null) {
                return false;
            } else if (aMetaTileEntity instanceof GTMTE_CommunicationTower_Receiver) {
                ((GTMTE_CommunicationTower_Receiver) aMetaTileEntity).updateTexture(aBaseCasingIndex);
                return sCommunReceiver.add((GTMTE_CommunicationTower_Receiver) aMetaTileEntity);
            } else {
                return false;
            }
        }
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GTMTE_TowerCommunication(mName);
    }

    @Override
    public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide, final byte aFacing, final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
        return aSide == 1 ? new ITexture[]{INDEX_CASE, TextureFactory.of(aActive ? TOWER_OVERLAY_ACTIVE : TOWER_OVERLAY)} : new ITexture[]{INDEX_CASE};
    }

    @Override
    public void onNotePadRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
        isShowConnections = !isShowConnections;
    }

    private boolean isConnected;

    @Override
    public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return null;
    }

    @Override
    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return null;
    }

    @Override
    public boolean machineStructure(IGregTechTileEntity thisController) {
        boolean formationCheckList = checkPiece(3, 17, 3);
        if (sCommunReceiver.size() != 4) {
            formationCheckList = false;
        }

        for (GTMTE_CommunicationTower_Receiver receiver : sCommunReceiver) {
            receiver.connectToTower(this);
        }

        return formationCheckList;
    }

    @Override
    public boolean checkRecipe(ItemStack itemStack) {
        this.mMaxProgresstime = 20;
        this.mEfficiency = 10000;
        this.mEfficiencyIncrease = 10000;
        this.mEUt = 0;
        return true;
    }

    @Override
    public void clearHatches() {
        sCommunReceiver.clear();
        super.clearHatches();
    }

    @Override
    protected MultiBlockTooltipBuilder createTooltip() {
        MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder("ttc");
        b
                .addTypeMachine("name", "Communication Tower")
                .addInfo("info.0", "Working radius is 9x9 chunks square")
                .addSeparator()
                .addController()
                .addOtherStructurePart("other.0", "Communication Receiver Hatch", "other.1", "Top")
                .addOtherStructurePart("other.2", "Steel Frame Box", "other.3", "Frames")
                .addCasingInfo("case", "Tower Casing", 41)
                .signAndFinalize();
        return b;
    }

    @Override
    public IStructureDefinition<GTMTE_TowerCommunication> getStructureDefinition() {
        return STRUCTURE_DEFINITION;
    }

    @Override
    public String[] getInfoData() {

        List<String> list = new ArrayList<>();
        list.add("Connection: " + (isConnected ? "YES" : "NO"));

        int index = 0;
        for (INetworkMachine receiver : connections) {
            if (receiver instanceof IMetaTileEntity) {
                index++;
                IMetaTileEntity mte = (IMetaTileEntity) receiver;
                String name = GT_LanguageManager.getTranslation("gt.blockmachines." + mte.getMetaName() + ".name");
                IGregTechTileEntity te = mte.getBaseMetaTileEntity();
                if (te != null) {
                    String title = String.format("%d. %s (X: %d, Y: %d, Z: %d)", index, name, te.getXCoord(), te.getYCoord(), te.getZCoord());
                    list.add(title);
                }
            }
        }

        return list.toArray(new String[0]);
    }

    @Override
    public void construct(ItemStack stackSize, boolean hintsOnly) {
        buildPiece(stackSize, hintsOnly, 3, 17, 3);
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
        aNBT.setBoolean("isConnected", isConnected);
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
        isConnected = aNBT.getBoolean("isConnected");
    }

    @Override
    public void onPostTick(IGregTechTileEntity iAm, long aTick) {
        super.onPostTick(iAm, aTick);
        if (iAm.isServerSide()) {
            if (aTick % 20 * 60 == 0) {
                noMaintenance();
            }
            if (isShowConnections && aTick % 20 == 5) {
                for (INetworkMachine connection : connections) {
                    if (connection instanceof IMetaTileEntity) {
                        IMetaTileEntity mte = (IMetaTileEntity) connection;
                        IGregTechTileEntity gte = mte.getBaseMetaTileEntity();

                        Dyes color = Dyes.dyeBlack;

                        if (gte.isActive()) color = Dyes.dyeWhite;

                        short[] c = color.mRGBa;
                        int colorHash = new Color(c[0], c[1], c[2]).hashCode();

                        PositionObject start = connection instanceof GTMTE_ParallelComputer ? new PositionObject(iAm.getXCoord(), iAm.getYCoord() + 13, iAm.getZCoord()) : new PositionObject(gte);
                        PositionObject end = connection instanceof GTMTE_ParallelComputer ? new PositionObject(gte) : new PositionObject(iAm.getXCoord(), iAm.getYCoord() + 13, iAm.getZCoord());

                        LaserPushPacket packet = NetworkPackets.LaserPushPacket.transaction(
                                iAm.getWorld().provider.dimensionId,
                                start.toVec3i(),
                                end.toVec3i(),
                                colorHash,
                                1,
                                20,
                                3,
                                1
                        );
                        GTNetworkHandler.sendToAllAround(iAm, packet, 512);
                    }
                }
            }
        }
    }

    public void receiversUpdate() {
        IGregTechTileEntity iAm = getBaseMetaTileEntity();
        ArrayList<Boolean> checker = new ArrayList<>();
        for (GTMTE_CommunicationTower_Receiver ph : sCommunReceiver) {
            checker.add(ph.hasConnected() && ph.getBaseMetaTileEntity().isActive());
        }
        boolean isConnect = checker.stream().filter(b -> b).count() == 4 && iAm.isActive();
        if (isConnect != isConnected) {
            isConnected = isConnect;
            updateStatusConnections();
        }
    }

    @Override
    public void onFirstTick(IGregTechTileEntity aBaseMetaTileEntity) {
        super.onFirstTick(aBaseMetaTileEntity);
        createTower();
        checkZoneAndNotify();
    }

    @Override
    public void inValidate() {
        for (GTMTE_CommunicationTower_Receiver receiver : sCommunReceiver) {
            receiver.connectToTower(null);
        }
        removeTower();
        super.inValidate();
    }

    private void checkZoneAndNotify() {
        IGregTechTileEntity te = getBaseMetaTileEntity();

        if (te != null) {
            World w = te.getWorld();
            Chunk ch = w.getChunkFromBlockCoords(te.getXCoord(), te.getZCoord());

            int xChunk = ch.xPosition;
            int zChunk = ch.zPosition;

            connections.clear();

            for (int x = -8; x <= 8; x++) {
                for (int z = -8; z <= 8; z++) {
                    ch = w.getChunkFromChunkCoords(xChunk + x, zChunk + z);
                    for (Object value : ch.chunkTileEntityMap.values()) {
                        TileEntity tile = (TileEntity) value;

                        if (tile.isInvalid()) continue;

                        if (tile instanceof IGregTechTileEntity) {
                            IGregTechTileEntity gte = (IGregTechTileEntity) tile;
                            if (!gte.isDead() && gte.getMetaTileEntity() != null && gte.getMetaTileEntity() instanceof INetworkMachine && gte.getMetaTileEntity() != this) {
                                INetworkMachine receiver = (INetworkMachine) gte.getMetaTileEntity();
                                connections.add(receiver);
                            }
                        }

                        if (tile instanceof INetworkMachine) {
                            INetworkMachine receiver = (INetworkMachine) tile;
                            connections.add(receiver);
                        }
                    }
                }
            }
        }
        updateStatusConnections();
    }

    @NotNull
    @Override
    public HashSet<INetworkMachine> getConnections() {
        return connections;
    }

    @Override
    public boolean hasSeparate() {
        return false;
    }

    @Override
    public boolean isSatelliteConnected() {
        return isConnected;
    }

    @Override
    public void setSatelliteConnected(boolean b) {
        isConnected = b;
    }

    @Override
    public void onUpdateConnections() {
        checkZoneAndNotify();
    }

    @Override
    public void updateLocalNetwork() {
        receiversUpdate();
    }

    @Override
    public void updateStatusConnections() {
        for (INetworkMachine connection : connections) {
            connection.setSatelliteConnected(isSatelliteConnected());
        }
    }

    @Override
    public void createTower() {
        SatelliteNetworkLogic.INSTANCE.createTower(this);
    }

    @Override
    public void removeTower() {
        SatelliteNetworkLogic.INSTANCE.removeTower(this);
    }

    @Override
    public void createConnect() {
    }

    @Override
    public void removeConnect() {
    }
}
