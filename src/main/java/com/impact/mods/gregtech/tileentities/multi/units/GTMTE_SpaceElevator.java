package com.impact.mods.gregtech.tileentities.multi.units;

import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.screen.UISettings;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.impact.mods.gregtech.gui.processing.ImpactProcessingGui;
import gregtech.api.modularui2.GTGuiTextures;

import gregtech.api.logic.ProcessingLogic;
import gregtech.api.logic.TimedProcessingLogic;
import gregtech.api.recipe.check.CheckRecipeResult;
import com.impact.core.Impact_API;
import com.impact.mods.gregtech.blocks.HatchCounts;
import com.impact.mods.gregtech.blocks.ImpactCasings;
import com.impact.mods.gregtech.blocks.ImpactHatchElement;
import com.impact.mods.gregtech.enums.Texture;
import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_Impact_TargetBase;
import com.impact.util.ImpactMultiblockTooltipBuilder;
import com.impact.util.PositionObject;
import com.impact.util.Utilits;
import com.impact.util.string.Language;
import gregtech.api.enums.HatchElement;
import gregtech.api.enums.ItemList;
import gregtech.api.interfaces.ICasing;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTStructureUtility;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Utility;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.StructureRequirements;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import space.impact.structures.alignment.IAlignmentLimits;
import space.impact.structures.hint.HintColor;
import space.impact.structures.structure.IStructureDefinition;
import space.impact.structures.structure.ISurvivalBuildEnvironment;
import space.impact.structures.structure.StructureDefinition;

import static com.impact.loader.ItemRegistery.SpaceElevatorBlock;
import static com.impact.util.Utilits.isValidDim;
import static space.impact.structures.structure.StructureUtility.ofBlockAnyMeta;
import static space.impact.structures.structure.StructureUtility.withHint;

public class GTMTE_SpaceElevator extends GTMTE_Impact_TargetBase<GTMTE_SpaceElevator> {

    private final static int CAPACITY_FOR_TELEPORT = 1_000_000;

    private static final ICasing CASING = ImpactCasings.SPACE_ELEVATOR_CASING;

    private int casingCount;

    private static final StructureRequirements HATCHES_A = StructureRequirements.builder(CASING, 156)
            .atLeast(ImpactHatchElement.PowerInput).build();

    private static IStructureDefinition<GTMTE_SpaceElevator> STRUCTURE_DEFINITION;
    boolean isConnect = false;
    private int currentCharge = 0;

    public GTMTE_SpaceElevator(int aID, String aNameRegional) {
        super(aID, "impact.multimachine.spaceelevator", aNameRegional);
        enabledMaintenance = false;
    }

    public GTMTE_SpaceElevator(String aName) {
        super(aName);
        enabledMaintenance = false;
    }

    @Override
    public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide, final byte aFacing,
            final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
        return aSide == 1
                ? new ITexture[] { CASING.getTexture(), TextureFactory.of(
                        aActive ? Texture.Icons.OVERLAY_SPACE_ELEVATOR_ACTIVE : Texture.Icons.OVERLAY_SPACE_ELEVATOR) }
                : new ITexture[] { CASING.getTexture() };
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GTMTE_SpaceElevator(this.mName);
    }

    protected IAlignmentLimits getInitialAlignmentLimits() {
        return (direction, rotation, flip) -> (direction.ordinal() > 1) && rotation.isNotRotated()
                && flip.isNotFlipped();
    }


    @Override
    public boolean machineStructure(IGregTechTileEntity thisController) {
        casingCount = 0;
        return checkPiece(3, 4, 3) && HATCHES_A.isSatisfied(this, casingCount) && HatchCounts.energyHatches(this) <= 4;
    }

    public boolean checkCurrentDimension(int dim) {
        return dim == getBaseMetaTileEntity().getWorld().provider.dimensionId;
    }

    public boolean isDimensionalTeleportAvailable() {
        return GT_Utility.isRealDimension(this.mTargetD)
                && GT_Utility.isRealDimension(getBaseMetaTileEntity().getWorld().provider.dimensionId);
    }

    public int getStoredEU() {
        return getCharge(mEnergyHatches) + getCharge(mEnergyHatchesMulti);
    }

    private <T extends GT_MetaTileEntity_Hatch> int getCharge(List<T> hatches) {
        int charge = 0;
        for (T hatch : hatches) {
            charge += hatch.getEUVar();
        }
        return charge;
    }

    public void decrease() {
        decreaseEnergy(mEnergyHatches);
        decreaseEnergy(mEnergyHatchesMulti);
    }

    private <T extends GT_MetaTileEntity_Hatch> void decreaseEnergy(List<T> hatches) {
        if (currentCharge <= 0)
            return;
        for (T hatch : hatches) {
            long charge = hatch.getEUVar() - currentCharge;
            if (charge > 0) {
                hatch.setEUVar(charge);
                currentCharge = 0;
            } else {
                hatch.setEUVar(0);
                currentCharge = -(int) hatch.getEUVar();
            }
        }
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
        aNBT.setInteger("currentCharge", currentCharge);
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
        currentCharge = aNBT.getInteger("currentCharge");
    }

    @Override
    public void onPostTick(IGregTechTileEntity iAm, long aTick) {
        super.onPostTick(iAm, aTick);
        if (iAm.isServerSide() && aTick % 20 == 8) {
            currentCharge = Math.min(getStoredEU(), CAPACITY_FOR_TELEPORT);
        }
    }

    public void teleportEntity(EntityPlayer player) {
        if (!getBaseMetaTileEntity().isActive()) {
            GT_Utility.sendChatToPlayer(player, EnumChatFormatting.RED
                    + Language.translate("space_elevator_no_active", "Not Active Space Elevator!"));
            return;
        }

        if (currentCharge < CAPACITY_FOR_TELEPORT) {
            GT_Utility.sendChatToPlayer(player,
                    EnumChatFormatting.RED + Language.translate("space_elevator_no_energy", "Not Enough Energy!"));
            return;
        }

        if (!checkConnect()) {
            GT_Utility.sendChatToPlayer(player, EnumChatFormatting.RED
                    + Language.translate("space_elevator_no_connect", "Error connect to target Space Elevator.."));
            return;
        }

        boolean checkCurDim = !checkCurrentDimension(mTargetD);
        boolean isValidDimTeleport = !isDimensionalTeleportAvailable();
        boolean isMoveTeleportCoords = !GT_Utility.moveEntityToDimensionAtCoords(player, mTargetD, mTargetX + 0.5D,
                mTargetY + 1.5D, mTargetZ + 0.5D);

        if (checkCurDim || isValidDimTeleport || isMoveTeleportCoords) {
            player.setPositionAndUpdate(mTargetX + 0.5D, mTargetY + 1.5D, mTargetZ + 0.5D);
            decrease();
            isConnected = false;
        }
    }

    private boolean checkConnect() {
        isConnect = false;
        if (getBaseMetaTileEntity().isServerSide() && getBaseMetaTileEntity().isActive()) {
            IGregTechTileEntity tTile;

            if (this.mTargetD == getBaseMetaTileEntity().getWorld().provider.dimensionId) {
                tTile = getBaseMetaTileEntity().getIGregTechTileEntity(this.mTargetX, this.mTargetY, this.mTargetZ);
            } else {
                World tWorld = DimensionManager.getWorld(this.mTargetD);
                TileEntity te = tWorld != null ? tWorld.getTileEntity(this.mTargetX, this.mTargetY, this.mTargetZ)
                        : null;
                tTile = te instanceof IGregTechTileEntity ? (IGregTechTileEntity) te : null;
            }

            if (tTile != null && tTile.getMetaTileEntity() instanceof GTMTE_SpaceElevator) {
                GTMTE_SpaceElevator elevator = (GTMTE_SpaceElevator) tTile.getMetaTileEntity();
                if (tTile.isActive()) {
                    PositionObject pos = new PositionObject(getBaseMetaTileEntity());
                    elevator.setCoord(pos);
                    isConnect = true;
                }
            }
        }
        return isConnect;
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        return new ImpactMultiblockTooltipBuilder(mName)
                .useCompactStructure()
                .addInfo("Teleports players between linked Space Elevators.")
                .addMachineType("Space Elevator")
                .addInfo("Both elevators must be active; link them with a Laptop.")
                .addInfo("Teleport cost: %s EU.", CAPACITY_FOR_TELEPORT)
                .beginStructureBlock(7, 6, 7, false)
                .addController("center of layer 2")
                .addStructureSection("Components")
                .addStructurePartInfo(CASING, "%s+; fill unused casing positions", HATCHES_A.getMinimumCasings())
                .addOtherStructurePart(new ItemStack(SpaceElevatorBlock, 1, 0), "exactly 1; directly below the controller")
                .addStructureSection("Hatches and buses")
                .addHatchInfo("Energy Hatches", "1-4 total; casing positions")
                .addStructureHint("Casings or energy hatches", HintColor.BLUE)
                .addHatchChannelInfo()
                .toolTipFinisher();
    }

    public int[] getCoords() {
        return Utilits.getCoordsBaseMTE(getBaseMetaTileEntity());
    }

    public void getFrequency(EntityPlayer aPlayer) {
        int[] coords = Impact_API.sElevatorSpace.get(Utilits.inToStringUUID(1, aPlayer));
        if (coords == null) {
            GT_Utility.sendChatToPlayer(aPlayer, EnumChatFormatting.RED + "Failed Load Position");
            return;
        }
        PositionObject pos = new PositionObject(coords);
        GT_Utility.sendChatToPlayer(aPlayer, "Load Position");
        setCoord(pos);
    }

    public void setFrequency(EntityPlayer aPlayer) {
        Impact_API.sElevatorSpace.put(Utilits.inToStringUUID(1, aPlayer), getCoords());
        GT_Utility.sendChatToPlayer(aPlayer, "Save Position");
    }

    @Override
    public void onNotePadRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
        super.onNotePadRightClick(aSide, aPlayer, aX, aY, aZ);
        ItemStack tCurrentItem = aPlayer.inventory.getCurrentItem();
        if (ItemList.Tool_NoteBook.getItem() == tCurrentItem.getItem()) {
            int dimId = getBaseMetaTileEntity().getWorld().provider.dimensionId;
            if (isValidDim(dimId, "Orbit") || isValidDim(dimId, "Space") ||
                    isValidDim(dimId, "SS") || isValidDim(dimId, "SpaceStation")) {
                getFrequency(aPlayer);
            }
            if (dimId == 0) {
                setFrequency(aPlayer);
            }
            GT_ModHandler.damageOrDechargeItem(tCurrentItem, 1, 100, aPlayer);
        }
    }

    @Override
    protected ProcessingLogic createProcessingLogic() {
        return new TimedProcessingLogic(0, () -> 20);
    }

    @Override
    protected CheckRecipeResult validateProcessing() {
        if (getRepairStatus() < getIdealStatus()) {
            return CheckRecipeResult.MACHINE_CONDITION;
        }
        return super.validateProcessing();
    }

    @Override
    public void construct(ItemStack stackSize, boolean hintsOnly) {
        buildPiece(stackSize, hintsOnly, 3, 4, 3);
    }

    @Override
    public int survivalConstruct(ItemStack trigger, int budget,
            ISurvivalBuildEnvironment environment) {
        return survivalBuildPiece("main", trigger, 3, 4, 3, budget, environment);
    }

    @Override
    public IStructureDefinition<GTMTE_SpaceElevator> getStructureDefinition() {
        if (STRUCTURE_DEFINITION == null) {
            STRUCTURE_DEFINITION = StructureDefinition
                    .<GTMTE_SpaceElevator>builder()
                    .addChannel(GTStructureUtility.HATCH_CHANNEL, 1, 1)
                    .addShape("main", new String[][] {
                            { " AAAAA ", " A   A ", " A   A ", " A   A ", " AAAAA ", " AAAAA " },
                            { "AAAAAAA", "AA   AA", "AA   AA", "AA   AA", "AAAAAAA", "AAAAAAA" },
                            { "AA   AA", "       ", "       ", "       ", "AAAAAAA", "AAAAAAA" },
                            { "AA   AA", "       ", "       ", "       ", "AAA~AAA", "AAABAAA" },
                            { "AA   AA", "       ", "       ", "       ", "AAAAAAA", "AAAAAAA" },
                            { "AAAAAAA", "AA   AA", "AA   AA", "AA   AA", "AAAAAAA", "AAAAAAA" },
                            { " AAAAA ", " A   A ", " A   A ", " A   A ", " AAAAA ", " AAAAA " }
                    })
                    .addElement('A', withHint(GTStructureUtility.buildHatchAdder(GTMTE_SpaceElevator.class)
                            .allow(HatchElement.Energy, ImpactHatchElement.LaserInput)
                            .autoPlace(ImpactHatchElement.PowerInput)
                            .buildAndChain(HATCHES_A, machine -> machine.casingCount++), HintColor.BLUE))
                    .addElement('B', ofBlockAnyMeta(SpaceElevatorBlock))
                    .build();
        }
        return STRUCTURE_DEFINITION;
    }

    @Override
    public int getSupportedProcessingModes() {
        return 0;
    }

    @Override
    public ModularPanel buildUI(PosGuiData data, PanelSyncManager sync, UISettings settings) {
        ModularPanel panel = super.buildUI(data, sync, settings);
        new ImpactProcessingGui(this, panel, sync).addAction("travel", 0, 1,
                GTGuiTextures.OVERLAY_BUTTON_SIDE_SELECTION_UP, "impact.processing.travel",
                (player, shift) -> teleportEntity(player));
        return panel;
    }

    @Override
    public long getProcessingEnergyCapacity() {
        return CAPACITY_FOR_TELEPORT;
    }

    @Override
    public long getProcessingStoredEnergy() {
        return currentCharge;
    }

}
