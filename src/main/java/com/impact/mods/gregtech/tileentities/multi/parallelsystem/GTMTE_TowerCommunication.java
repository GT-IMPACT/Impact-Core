package com.impact.mods.gregtech.tileentities.multi.parallelsystem;

import com.impact.addon.gt.api.parallel_system.NetworkDiagnostics;
import com.impact.addon.gt.api.parallel_system.NetworkNodeLifecycle;
import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.screen.UISettings;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.impact.mods.gregtech.gui.tower.CommunicationTowerGui;
import gregtech.api.logic.ProcessingLogic;
import gregtech.api.logic.TimedProcessingLogic;
import com.impact.addon.gt.api.parallel_system.INetworkMachine;
import com.impact.addon.gt.api.parallel_system.NetworkNodeAccess;
import com.impact.addon.gt.api.parallel_system.IHandledNetworkNode;
import com.impact.addon.gt.api.parallel_system.TowerNetworkHandler;
import com.impact.addon.gt.api.parallel_system.INetworkTower;
import com.impact.addon.gt.api.parallel_system.SatelliteNetworkLogic;
import com.impact.mods.gregtech.blocks.ImpactCasings;
import com.impact.mods.gregtech.blocks.ImpactHatchElement;
import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_Impact_BlockBase;
import com.impact.network.special.TowerConnectionsPacket;
import com.impact.util.ImpactMultiblockTooltipBuilder;
import cpw.mods.fml.common.Optional;
import gregtech.api.enums.HatchElement;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.interfaces.ICasing;
import gregtech.api.interfaces.IHatchElement;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTStructureUtility;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_LanguageManager;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.StructureRequirements;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import space.impact.structures.hint.HintColor;
import space.impact.structures.structure.IStructureDefinition;
import space.impact.structures.structure.ISurvivalBuildEnvironment;
import space.impact.structures.structure.StructureDefinition;
import space.impact.packet_network.network.NetworkHandler;

import static com.impact.mods.gregtech.enums.Texture.Icons.TOWER_OVERLAY;
import static com.impact.mods.gregtech.enums.Texture.Icons.TOWER_OVERLAY_ACTIVE;
import static gregtech.api.util.GTStructureUtility.ofFrame;
import static space.impact.structures.structure.StructureUtility.lazy;
import static space.impact.structures.structure.StructureUtility.withHint;

public class GTMTE_TowerCommunication extends GTMTE_Impact_BlockBase<GTMTE_TowerCommunication>
        implements INetworkTower, IHandledNetworkNode {

    private static final ICasing CASING = ImpactCasings.TOWER_CASING;

    private int casingCount;

    private static final StructureRequirements HATCHES_A = StructureRequirements.builder(CASING, 21).build();

    private static IStructureDefinition<GTMTE_TowerCommunication> STRUCTURE_DEFINITION;

    public final HashSet<GTMTE_CommunicationTower_Receiver> sCommunReceiver = new HashSet<>();
    private final TowerNetworkHandler networkHandler = new TowerNetworkHandler(
            this,
            () -> mMachine && getBaseMetaTileEntity() != null && getBaseMetaTileEntity().isActive(),
            () -> sCommunReceiver
    );
    private boolean connectionOverlayEnabled;

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

    public boolean isConnectionOverlayEnabled() {
        return connectionOverlayEnabled;
    }

    public void toggleConnectionOverlay() {
        IGregTechTileEntity tile = getBaseMetaTileEntity();
        if (tile != null && tile.isServerSide()) {
            connectionOverlayEnabled = !connectionOverlayEnabled;
            sendConnectionOverlay(tile);
        }
    }

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
        detachReceivers();
        super.clearHatches();
    }

    private void detachReceivers() {
        for (GTMTE_CommunicationTower_Receiver receiver : sCommunReceiver) {
            receiver.connectToTower(null);
        }
        sCommunReceiver.clear();
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        return new ImpactMultiblockTooltipBuilder(mName)
                .useCompactStructure()
                .addMachineType("Communication Tower")
                .addInfo("Connects nearby machines to the satellite network.")
                .addInfo("Coverage: 17x17 chunks, centered on the tower's chunk.")
                .addInfo("Select a satellite in the tower controller GUI to link all four Communication Receivers.")
                .beginStructureBlock(7, 19, 7, false)
                .addController("center of layer 2")
                .addStructureSection("Components")
                .addStructurePartInfo(CASING, "%s+; fill unused base positions", HATCHES_A.getMinimumCasings())
                .addOtherStructurePart(GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Steel, 1), "exactly 110; support frame and mast")
                .addStructureSection("Hatches and buses")
                .addHatchInfo("Communication Receiver", "exactly 4; around the mast in layer 15")
                .addStructureHint("Base casings", HintColor.GREEN)
                .addStructureHint("Communication Receivers", HintColor.PURPLE)
                .addHatchChannelInfo()
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
                            .allow(
                                    HatchElement.Input,
                                    HatchElement.Output,
                                    HatchElement.Energy,
                                    ImpactHatchElement.Muffler,
                                    ImpactHatchElement.Dynamo,
                                    ImpactHatchElement.LaserInput,
                                    ImpactHatchElement.LaserOutput)
                            .autoPlace(
                                    ImpactHatchElement.PowerOutput,
                                    ImpactHatchElement.PowerInput,
                                    HatchElement.Input,
                                    HatchElement.Output,
                                    ImpactHatchElement.Muffler)
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
        list.add("Connection: " + (isSatelliteConnected() ? "YES" : "NO"));

        int index = 0;
        for (INetworkMachine receiver : getConnections()) {
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
        aNBT.setBoolean("isConnected", isSatelliteConnected());
        networkHandler.save(aNBT);
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
        networkHandler.load(aNBT);
    }

    @Override
    public void onPostTick(IGregTechTileEntity iAm, long aTick) {
        super.onPostTick(iAm, aTick);
        if (iAm.isServerSide() && connectionOverlayEnabled && aTick % 20 == 5) {
            sendConnectionOverlay(iAm);
        }
    }

    private void sendConnectionOverlay(IGregTechTileEntity tower) {
        World world = tower.getWorld();
        if (world == null) {
            return;
        }
        TowerConnectionsPacket packet = connectionOverlayEnabled && isSatelliteConnected()
                ? captureConnections(world) : TowerConnectionsPacket.empty();
        NetworkHandler.sendToAllAround(
                world,
                packet,
                tower.getXCoord(),
                tower.getYCoord(),
                tower.getZCoord(),
                TowerConnectionsPacket.RANGE
        );
    }

    private TowerConnectionsPacket captureConnections(World world) {
        Set<INetworkMachine> connections = getConnections();
        int capacity = Math.min(connections.size(), TowerConnectionsPacket.MAX_LINKS);
        int[] links = new int[capacity * TowerConnectionsPacket.LINK_SIZE];
        int count = 0;
        for (INetworkMachine connection : connections) {
            if (!(connection instanceof IMetaTileEntity) || !connection.isSatelliteConnected()
                    || NetworkNodeAccess.worldOf(connection) != world || !NetworkNodeAccess.isLoaded(connection)) {
                continue;
            }
            IGregTechTileEntity target = ((IMetaTileEntity) connection).getBaseMetaTileEntity();
            int offset = count * TowerConnectionsPacket.LINK_SIZE;
            links[offset] = target.getXCoord();
            links[offset + 1] = target.getYCoord();
            links[offset + 2] = target.getZCoord();
            links[offset + 3] = target.isActive() ? 1 : 0;
            if (++count == capacity) {
                break;
            }
        }
        return TowerConnectionsPacket.snapshot(links, count);
    }

    public void receiversUpdate() {
        networkHandler.update();
    }

    @Override
    public void onFirstTick(IGregTechTileEntity base) {
        super.onFirstTick(base);
        if (base.isServerSide()) {
            NetworkNodeLifecycle.load(this);
        }
    }

    @Override
    public void inValidate() {
        IGregTechTileEntity tile = getBaseMetaTileEntity();
        if (connectionOverlayEnabled && tile != null && tile.getWorld() != null && tile.isServerSide()) {
            connectionOverlayEnabled = false;
            sendConnectionOverlay(tile);
        }
        detachReceivers();
        if (tile != null && tile.isServerSide()) {
            NetworkNodeLifecycle.unload(this);
        }
        super.inValidate();
    }

    @NotNull
    @Override
    public Set<INetworkMachine> getConnections() {
        return networkHandler.getConnections();
    }

    @Override
    public void replaceConnections(Set<? extends INetworkMachine> machines) {
        networkHandler.replaceConnections(machines);
    }

    @Override
    public int getSupportedProcessingModes() {
        return 0;
    }

    @Override
    public boolean isSatelliteConnected() {
        return networkHandler.isConnected();
    }

    @Override
    public void setSatelliteConnected(boolean b) {
        networkHandler.setConnected(b);
    }

    @Override
    public void onUpdateConnections() {
        networkHandler.refreshCoverage();
    }

    @Override
    public void updateLocalNetwork() {
        receiversUpdate();
    }

    @Override
    public void updateStatusConnections() {
        SatelliteNetworkLogic.INSTANCE.updateStatusConnections(this);
    }

    @Override
    public boolean hasShowConnect() {
        return true;
    }

    @Override
    public TowerNetworkHandler getNetworkHandler() {
        return networkHandler;
    }

    @Override
    public void createTower() {
        networkHandler.onLoad();
    }

    @Override
    public void removeTower() {
        networkHandler.onUnload();
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
        NetworkDiagnostics.appendConnection(tag, tooltip, tag.getBoolean("towerCommunicationConnect"));
    }

    @Override
    @Optional.Method(modid = "Waila")
    public void getWailaNBTData(EntityPlayerMP player, TileEntity tile, NBTTagCompound tag,
            World world, int x, int y, int z) {
        super.getWailaNBTData(player, tile, tag, world, x, y, z);
        NetworkDiagnostics.write(tag, this);
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
