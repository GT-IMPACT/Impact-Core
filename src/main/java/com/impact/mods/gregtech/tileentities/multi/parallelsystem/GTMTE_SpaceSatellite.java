package com.impact.mods.gregtech.tileentities.multi.parallelsystem;

import gregtech.api.logic.ProcessingLogic;
import com.impact.addon.gt.api.parallel_system.IHandledNetworkNode;
import com.impact.addon.gt.api.parallel_system.NetworkDiagnostics;
import com.impact.addon.gt.api.parallel_system.NetworkNodeLifecycle;
import com.impact.addon.gt.api.satellite.SatelliteControllerHandler;
import cpw.mods.fml.common.Optional;
import gregtech.api.logic.TimedProcessingLogic;
import com.impact.mods.gregtech.blocks.ImpactCasings;
import com.impact.mods.gregtech.blocks.ImpactHatchElement;
import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_Impact_BlockBase;
import com.impact.util.ImpactMultiblockTooltipBuilder;
import com.impact.util.Utilits;
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
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.StructureRequirements;
import java.util.HashSet;
import java.util.List;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import space.impact.structures.hint.HintColor;
import space.impact.structures.structure.IStructureDefinition;
import space.impact.structures.structure.ISurvivalBuildEnvironment;
import space.impact.structures.structure.StructureDefinition;

import static space.impact.structures.structure.StructureUtility.withHint;

public class GTMTE_SpaceSatellite extends GTMTE_Impact_BlockBase<GTMTE_SpaceSatellite>
        implements IHandledNetworkNode {

    public final HashSet<GTMTE_SpaceSatellite_Transmitter> sCommunTransmitter = new HashSet<>();
    private final SatelliteControllerHandler networkHandler = new SatelliteControllerHandler(
            this,
            () -> mMachine,
            () -> mMachine && getBaseMetaTileEntity() != null && getBaseMetaTileEntity().isActive(),
            () -> sCommunTransmitter
    );

    private static final ICasing CASING = ImpactCasings.SPACE_SATELLITE_CASING;

    private int casingCount;

    private static final StructureRequirements SHELL = StructureRequirements.builder(CASING, 25).build();
    private static IStructureDefinition<GTMTE_SpaceSatellite> definition;

    public GTMTE_SpaceSatellite(int aID, String aNameRegional) {
        super(aID, "impact.multis.spacesatellite", aNameRegional);
        enabledMaintenance = false;
    }

    public GTMTE_SpaceSatellite(String aName) {
        super(aName);
        enabledMaintenance = false;
    }

    @Override
    public void construct(ItemStack itemStack, boolean b) {
        buildPiece(itemStack, b, 2, 2, 1);
    }

    @Override
    public int survivalConstruct(ItemStack trigger, int budget,
            ISurvivalBuildEnvironment environment) {
        return survivalBuildPiece("main", trigger, 2, 2, 1, budget, environment);
    }

    @Override
    public IStructureDefinition<GTMTE_SpaceSatellite> getStructureDefinition() {
        if (definition == null) {
            definition = StructureDefinition.<GTMTE_SpaceSatellite>builder()
                    .addShape("main", new String[][] {
                            { " A A ", " A A ", " A A ", " A A " },
                            { " A A ", "AAAAA", "AA~AA", "AAAAA" },
                            { " A A ", "AAAAA", "AAAAA", "AAAAA" },
                            { " A A ", " A A ", " A A ", " A A " },
                    })
                    .addChannel(GTStructureUtility.HATCH_CHANNEL, 1, 1)
                    .addElement('A', withHint(GTStructureUtility.buildHatchAdder(GTMTE_SpaceSatellite.class)
                            .allow(HatchElement.Energy, ImpactHatchElement.LaserInput, Transmitter.INSTANCE)
                            .autoPlace(ImpactHatchElement.PowerInput, Transmitter.INSTANCE)
                            .buildAndChain(SHELL, machine -> machine.casingCount++), HintColor.GREEN))
                    .build();
        }
        return definition;
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GTMTE_SpaceSatellite(this.mName);
    }


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
                .addMachineType("Space Satellite")
                .addInfo("Relays signals to linked Communication Towers.")
                .addInfo("Requires low gravity.")
                .beginStructureBlock(5, 4, 4, false)
                .addController("center of layer 2, 1 block behind the front edge")
                .addStructureSection("Components")
                .addStructurePartInfo(CASING, "%s+; fill unused casing positions", SHELL.getMinimumCasings())
                .addStructureSection("Hatches and buses")
                .addHatchInfo("Communication Transmitter", "at least one for the satellite network; casing positions")
                .addStructureHint("Casings or Communication Transmitters", HintColor.GREEN)
                .addHatchChannelInfo()
                .toolTipFinisher();
    }

    @Override
    protected ProcessingLogic createProcessingLogic() {
        return new TimedProcessingLogic(0, () -> 10);
    }

    @Override
    public SatelliteControllerHandler getNetworkHandler() {
        return networkHandler;
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
        IGregTechTileEntity base = getBaseMetaTileEntity();
        if (base != null && base.isServerSide()) {
            NetworkNodeLifecycle.unload(this);
        }
        super.inValidate();
    }

    @Override
    @Optional.Method(modid = "Waila")
    public void getWailaBody(ItemStack stack, List<String> tooltip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        super.getWailaBody(stack, tooltip, accessor, config);
        NetworkDiagnostics.append(accessor.getNBTData(), tooltip);
    }

    @Override
    @Optional.Method(modid = "Waila")
    public void getWailaNBTData(
            EntityPlayerMP player,
            TileEntity tile,
            NBTTagCompound tag,
            World world,
            int x,
            int y,
            int z
    ) {
        super.getWailaNBTData(player, tile, tag, world, x, y, z);
        NetworkDiagnostics.write(tag, this);
    }

    @Override
    public boolean machineStructure(IGregTechTileEntity thisController) {
        casingCount = 0;
        boolean formed = Utilits.isLowGravity(thisController) && checkPiece(2, 2, 1)
                && SHELL.isSatisfied(this, casingCount);
        if (formed) {
            networkHandler.register();
        }
        return formed;
    }

    @Override
    public void onRemoval() {
        networkHandler.remove();
        super.onRemoval();
    }

    @Override
    public void clearHatches() {
        networkHandler.deactivate();
        super.clearHatches();
        sCommunTransmitter.clear();
    }

    private enum Transmitter implements IHatchElement {
        INSTANCE;

        @Override
        public boolean matches(IMetaTileEntity tile) {
            return tile instanceof GTMTE_SpaceSatellite_Transmitter;
        }

        @Override
        public int count(GT_MetaTileEntity_MultiBlockBase machine) {
            return ((GTMTE_SpaceSatellite) machine).sCommunTransmitter.size();
        }

        @Override
        public boolean add(GT_MetaTileEntity_MultiBlockBase machine, IGregTechTileEntity tile, int texture) {
            return ((GTMTE_SpaceSatellite) machine).addCommunicationHatchToMachineList(tile, texture);
        }

        @Override
        public String getDescription() {
            return StatCollector.translateToLocal("impact.structure.hatch.transmitter");
        }
    }

    private boolean addCommunicationHatchToMachineList(IGregTechTileEntity aTileEntity, int aBaseCasingIndex) {
        if (aTileEntity == null) {
            return false;
        } else {
            final IMetaTileEntity aMetaTileEntity = aTileEntity.getMetaTileEntity();
            if (aMetaTileEntity == null) {
                return false;
            } else if (aMetaTileEntity instanceof GTMTE_SpaceSatellite_Transmitter) {
                ((GTMTE_SpaceSatellite_Transmitter) aMetaTileEntity).updateTexture(aBaseCasingIndex);
                return sCommunTransmitter.add((GTMTE_SpaceSatellite_Transmitter) aMetaTileEntity);
            } else {
                return false;
            }
        }
    }

    @Override
    public int getPollutionPerTick(ItemStack aStack) {
        return 0;
    }
    @Override
    public int getSupportedProcessingModes() {
        return 0;
    }
}
