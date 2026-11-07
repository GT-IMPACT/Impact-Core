package com.impact.mods.gregtech.tileentities.multi.parallelsystem;

import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.screen.UISettings;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.impact.mods.gregtech.gui.tower.CommunicationTowerGui;
import gregtech.api.logic.ProcessingLogic;
import gregtech.api.logic.TimedProcessingLogic;
import com.impact.addon.gt.api.parallel_system.INetworkMachine;
import com.impact.addon.gt.api.parallel_system.INetworkTower;
import com.impact.addon.gt.api.parallel_system.SatelliteNetworkLogic;
import com.impact.mods.gregtech.blocks.ImpactCasings;
import com.impact.mods.gregtech.blocks.ImpactHatchElement;
import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_Impact_BlockBase;
import com.impact.network.GTNetworkHandler;
import com.impact.network.NetworkPackets;
import com.impact.network.special.LaserPushPacket;
import com.impact.util.PositionObject;
import cpw.mods.fml.common.Optional;
import gregtech.api.enums.Dyes;
import gregtech.api.enums.HatchElement;
import gregtech.api.enums.Materials;
import gregtech.api.interfaces.ICasing;
import gregtech.api.interfaces.IHatchElement;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTStructureUtility;
import gregtech.api.util.GT_LanguageManager;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.StructureRequirements;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import org.jetbrains.annotations.NotNull;
import space.impact.structures.hint.HintColor;
import space.impact.structures.structure.IStructureDefinition;
import space.impact.structures.structure.ISurvivalBuildEnvironment;
import space.impact.structures.structure.StructureDefinition;

import static com.impact.mods.gregtech.enums.Texture.Icons.TOWER_OVERLAY;
import static com.impact.mods.gregtech.enums.Texture.Icons.TOWER_OVERLAY_ACTIVE;
import static gregtech.api.util.GTStructureUtility.ofFrame;
import static net.minecraft.util.StatCollector.translateToLocal;
import static space.impact.structures.structure.StructureUtility.lazy;
import static space.impact.structures.structure.StructureUtility.withHint;

public class GTMTE_TowerCommunication extends GTMTE_Impact_BlockBase<GTMTE_TowerCommunication>
        implements INetworkTower {

    private static final ICasing CASING = ImpactCasings.TOWER_CASING;

    private int casingCount;

    private static final StructureRequirements HATCHES_A = StructureRequirements.builder(CASING, 21).build();

    private static IStructureDefinition<GTMTE_TowerCommunication> STRUCTURE_DEFINITION;

    private final HashSet<INetworkMachine> connections = new HashSet<>();
    public final HashSet<GTMTE_CommunicationTower_Receiver> sCommunReceiver = new HashSet<>();
    private boolean isShowConnections = false;

    public GTMTE_TowerCommunication(int aID, String aNameRegional) {
        super(aID, "impact.multis.communicationtower", aNameRegional);
        enabledMaintenance = false;
    }

    public GTMTE_TowerCommunication(String aName) {
        super(aName);
        enabledMaintenance = false;
    }

    @Override
    public ModularPanel buildUI(PosGuiData data, PanelSyncManager sync, UISettings settings) {
        return CommunicationTowerGui.build(this, data, sync, settings);
    }

    @Override
    public boolean hasIndicator() {
        return true;
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GTMTE_TowerCommunication(mName);
    }

    @Override
    public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide, final byte aFacing,
            final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
        return aSide == 1
                ? new ITexture[] { CASING.getTexture(),
                        TextureFactory.of(aActive ? TOWER_OVERLAY_ACTIVE : TOWER_OVERLAY) }
                : new ITexture[] { CASING.getTexture() };
    }

    @Override
    public void onNotePadRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
        isShowConnections = !isShowConnections;
    }

    private boolean isConnected;


    @Override
    public boolean machineStructure(IGregTechTileEntity thisController) {
        casingCount = 0;
        boolean formationCheckList = checkPiece(3, 17, 3) && HATCHES_A.isSatisfied(this, casingCount);
        if (sCommunReceiver.size() != 4) {
            formationCheckList = false;
        }

        for (GTMTE_CommunicationTower_Receiver receiver : sCommunReceiver) {
            receiver.connectToTower(this);
        }

        return formationCheckList;
    }

    @Override
    protected ProcessingLogic createProcessingLogic() {
        return new TimedProcessingLogic(0, () -> 20);
    }

    @Override
    public void clearHatches() {
        sCommunReceiver.clear();
        super.clearHatches();
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        return new MultiblockTooltipBuilder(mName)
                .addMachineType("Communication Tower")
                .addInfo("Working radius is 9x9 chunks square")
                .addSeparator()
                .addController("See the structure hologram")
                .addOtherStructurePart("Communication Receiver Hatch", "Top")
                .addOtherStructurePart("Steel Frame Box", "Frames")
                .addCasingInfoMin(CASING, HATCHES_A.getMinimumCasings(), false)
                .addStructureHint("Casing or compatible hatch", HintColor.GREEN)
                .addStructureHint("Communication receiver hatch", HintColor.PURPLE)
                .addStructureInfo("Hatches replacing casings: at most 20")
                .addSubChannel(GTStructureUtility.HATCH_CHANNEL, "Installs the four tower receivers and available service hatches from your inventory.")
                .toolTipFinisher();
    }

    @Override
    public IStructureDefinition<GTMTE_TowerCommunication> getStructureDefinition() {
        if (STRUCTURE_DEFINITION == null) {
            STRUCTURE_DEFINITION = StructureDefinition
                    .<GTMTE_TowerCommunication>builder()
                    .addChannel(GTStructureUtility.HATCH_CHANNEL, 1, 1)
                    .addShape("main", new String[][] {
                            { "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", " EEEEE ", "EE   EE", "E     E", "E     E", "E     E" },
                            { "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "  EEE  ", " EE EE ", " E   E ", " E   E ", " E   E ", "EE   EE", "E     E", "       ", "  AAA  ", "  AAA  " },
                            { "       ", "       ", "       ", "       ", "   D   ", "  E E  ", "  E E  ", "  E E  ", "  E E  ", " EE EE ", " E   E ", "       ", "       ", "       ", "E     E", "       ", "       ", " AAAAA ", " AAAAA " },
                            { "   E   ", "   E   ", "   E   ", "   E   ", "  DED  ", "   E   ", "       ", "       ", "       ", " E   E ", "       ", "       ", "       ", "       ", "E     E", "       ", "       ", " AA~AA ", " AAAAA " },
                            { "       ", "       ", "       ", "       ", "   D   ", "  E E  ", "  E E  ", "  E E  ", "  E E  ", " EE EE ", " E   E ", "       ", "       ", "       ", "E     E", "       ", "       ", " AAAAA ", " AAAAA " },
                            { "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "  EEE  ", " EE EE ", " E   E ", " E   E ", " E   E ", "EE   EE", "E     E", "       ", "  AAA  ", "  AAA  " },
                            { "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", " EEEEE ", "EE   EE", "E     E", "E     E", "E     E" }
                    })
                    .addElement('A', withHint(GTStructureUtility.buildHatchAdder(GTMTE_TowerCommunication.class)
                            .allow(HatchElement.Input, HatchElement.Output, HatchElement.Maintenance,
                                    HatchElement.Energy, ImpactHatchElement.Muffler, ImpactHatchElement.Dynamo,
                                    ImpactHatchElement.LaserInput, ImpactHatchElement.LaserOutput)
                            .autoPlace(ImpactHatchElement.PowerOutput, ImpactHatchElement.PowerInput, HatchElement.Input, HatchElement.Output, HatchElement.Maintenance, ImpactHatchElement.Muffler)
                            .buildAndChain(HATCHES_A, machine -> machine.casingCount++), HintColor.GREEN))
                    .addElement('D',
                            GTStructureUtility.buildHatchAdder(GTMTE_TowerCommunication.class).hint(HintColor.PURPLE)
                                    .build(StructureRequirements.builder(CASING, 0).atLeast(SpecialHatch.Receiver)
                                            .build()))
                    .addElement('E', lazy(t -> ofFrame(Materials.Steel)))
                    .build();
        }
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
                    String title = String.format("%d. %s (X: %d, Y: %d, Z: %d)", index, name, te.getXCoord(),
                            te.getYCoord(), te.getZCoord());
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
    public int survivalConstruct(ItemStack trigger, int budget,
            ISurvivalBuildEnvironment environment) {
        return survivalBuildPiece("main", trigger, 3, 17, 3, budget, environment);
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
            if (isShowConnections && aTick % 20 == 5) {
                for (INetworkMachine connection : connections) {
                    if (connection instanceof IMetaTileEntity) {
                        IMetaTileEntity mte = (IMetaTileEntity) connection;
                        IGregTechTileEntity gte = mte.getBaseMetaTileEntity();

                        Dyes color = Dyes.dyeBlack;

                        if (gte.isActive())
                            color = Dyes.dyeWhite;

                        short[] c = color.mRGBa;
                        int colorHash = new Color(c[0], c[1], c[2]).hashCode();

                        PositionObject start = connection instanceof GTMTE_ParallelComputer
                                ? new PositionObject(iAm.getXCoord(), iAm.getYCoord() + 13, iAm.getZCoord())
                                : new PositionObject(gte);
                        PositionObject end = connection instanceof GTMTE_ParallelComputer ? new PositionObject(gte)
                                : new PositionObject(iAm.getXCoord(), iAm.getYCoord() + 13, iAm.getZCoord());

                        LaserPushPacket packet = NetworkPackets.LaserPushPacket.transaction(
                                iAm.getWorld().provider.dimensionId,
                                start.toVec3i(),
                                end.toVec3i(),
                                colorHash,
                                1,
                                20,
                                3,
                                1);
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

                        if (tile.isInvalid())
                            continue;

                        if (tile instanceof IGregTechTileEntity) {
                            IGregTechTileEntity gte = (IGregTechTileEntity) tile;
                            if (!gte.isDead() && gte.getMetaTileEntity() != null
                                    && gte.getMetaTileEntity() instanceof INetworkMachine
                                    && gte.getMetaTileEntity() != this) {
                                INetworkMachine receiver = (INetworkMachine) gte.getMetaTileEntity();
                                addConnection(receiver);
                            }
                        }

                        if (tile instanceof INetworkMachine) {
                            INetworkMachine receiver = (INetworkMachine) tile;
                            addConnection(receiver);
                        }
                    }
                }
            }
        }
        updateStatusConnections();
    }

    private void addConnection(INetworkMachine receiver) {
        if (receiver == this) {
            return;
        }
        if (receiver.hasShowConnect()) {
            connections.add(receiver);
        } else {
            receiver.setSatelliteConnected(false);
        }
    }

    @NotNull
    @Override
    public HashSet<INetworkMachine> getConnections() {
        return connections;
    }

    @Override
    public int getSupportedProcessingModes() {
        return 0;
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
    public boolean hasShowConnect() {
        return true;
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

    @Override
    @Optional.Method(modid = "Waila")
    public void getWailaBody(ItemStack stack, List<String> tooltip, IWailaDataAccessor accessor,
            IWailaConfigHandler config) {
        super.getWailaBody(stack, tooltip, accessor, config);
        NBTTagCompound tag = accessor.getNBTData();
        String str = tag.getBoolean("towerCommunicationConnect")
                ? EnumChatFormatting.GREEN + translateToLocal("impact.waila.connect.yes")
                : EnumChatFormatting.RED + translateToLocal("impact.waila.connect.no");
        tooltip.add(str);
    }

    @Override
    @Optional.Method(modid = "Waila")
    public void getWailaNBTData(EntityPlayerMP player, TileEntity tile, NBTTagCompound tag,
            World world, int x, int y, int z) {
        super.getWailaNBTData(player, tile, tag, world, x, y, z);
        tag.setBoolean("towerCommunicationConnect", isSatelliteConnected());
    }

    private enum SpecialHatch implements IHatchElement {
        Receiver {
            @Override
            public boolean matches(IMetaTileEntity tile) {
                return tile instanceof GTMTE_CommunicationTower_Receiver;
            }

            @Override
            public int count(GT_MetaTileEntity_MultiBlockBase machine) {
                return machine instanceof GTMTE_TowerCommunication
                        ? ((GTMTE_TowerCommunication) machine).sCommunReceiver.size()
                        : 0;
            }

            @Override
            public boolean add(GT_MetaTileEntity_MultiBlockBase machine, IGregTechTileEntity tile, int texture) {
                if (!(machine instanceof GTMTE_TowerCommunication) || !matches(tile.getMetaTileEntity())) {
                    return false;
                }
                GTMTE_CommunicationTower_Receiver hatch = (GTMTE_CommunicationTower_Receiver) tile.getMetaTileEntity();
                hatch.updateTexture(texture);
                return ((GTMTE_TowerCommunication) machine).sCommunReceiver.add(hatch);
            }

            @Override
            public String getDescription() {
                return StatCollector.translateToLocal("impact.structure.hatch.communication");
            }
        };
    }
}
