package com.impact.mods.gregtech.tileentities.multi.parallelsystem;

import com.impact.mods.gregtech.blocks.ImpactCasings;
import com.impact.mods.gregtech.blocks.ImpactHatchElement;
import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_Impact_BlockBase;
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
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import space.impact.structures.hint.HintColor;
import space.impact.structures.structure.IStructureDefinition;
import space.impact.structures.structure.ISurvivalBuildEnvironment;
import space.impact.structures.structure.StructureDefinition;

import static space.impact.structures.structure.StructureUtility.withHint;

public class GTMTE_SpaceSatellite extends GTMTE_Impact_BlockBase<GTMTE_SpaceSatellite> {

    public final HashSet<GTMTE_SpaceSatellite_Transmitter> sCommunTransmitter = new HashSet<>();

    private static final ICasing CASING = ImpactCasings.SPACE_SATELLITE_CASING;

    private static final StructureRequirements SHELL = StructureRequirements.builder(CASING, 0).build();
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
                            .allow(HatchElement.Maintenance, HatchElement.Energy, ImpactHatchElement.LaserInput,
                                    Transmitter.INSTANCE)
                            .buildAndChain(SHELL), HintColor.GREEN))
                    .build();
        }
        return definition;
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GTMTE_SpaceSatellite(this.mName);
    }

    @Override
    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return false;
    }

    @Override
    public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return false;
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
        return new MultiblockTooltipBuilder(mName)
                .addMachineType("Space Satellite")
                .addInfo("Installation on the Space Station required")
                .addController("See the structure hologram")
                .addOtherStructurePart("Communication Transmitter", "Any casing")
                .addCasingInfoMin(CASING, 48, false)
                .addStructureHint("Casing or compatible hatch", HintColor.GREEN)
                .toolTipFinisher();
    }

    @Override
    public boolean checkRecipe(ItemStack aStack) {
        this.mMaxProgresstime = 10;
        this.mEfficiency = 10000;
        this.mEfficiencyIncrease = 10000;
        this.mEUt = 0;
        return true;
    }

    @Override
    public void onPostTick(IGregTechTileEntity iAm, long aTick) {
        super.onPostTick(iAm, aTick);
        if (iAm.isServerSide()) {
            if (aTick % 100 == 0) {
                for (GTMTE_SpaceSatellite_Transmitter th : sCommunTransmitter) {
                    th.updateConnectionStatus(iAm.isActive());
                }
            }
        }
    }

    @Override
    public boolean machineStructure(IGregTechTileEntity thisController) {
        return Utilits.isLowGravity(thisController) && checkPiece(2, 2, 1);
    }

    @Override
    public void clearHatches() {
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
}
