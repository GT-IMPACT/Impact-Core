package com.impact.mods.gregtech.tileentities.multi.units;

import com.impact.core.Impact_API;
import com.impact.mods.gregtech.blocks.ImpactCasings;
import com.impact.mods.gregtech.blocks.ImpactHatchElement;
import com.impact.mods.gregtech.enums.Texture;
import com.impact.mods.gregtech.gui.base.GTC_ImpactBase;
import com.impact.mods.gregtech.gui.base.GUI_BASE;
import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_Impact_TargetBase;
import com.impact.util.PositionObject;
import com.impact.util.Utilits;
import com.impact.util.string.Language;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.enums.HatchElement;
import gregtech.api.enums.ItemList;
import gregtech.api.gui.GT_Slot_Holo;
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
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
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
import static space.impact.structures.structure.StructureUtility.*;
import static space.impact.structures.structure.StructureUtility.withHint;

public class GTMTE_SpaceElevator extends GTMTE_Impact_TargetBase<GTMTE_SpaceElevator> {

    private final static int CAPACITY_FOR_TELEPORT = 1_000_000;

    private static final ICasing CASING = ImpactCasings.SPACE_ELEVATOR_CASING;

    private static final StructureRequirements HATCHES_A = StructureRequirements.builder(CASING, 0).build();

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
    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GUI(aPlayerInventory, aBaseMetaTileEntity);
    }

    @Override
    public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new CONTAINER(aPlayerInventory, aBaseMetaTileEntity);
    }

    @Override
    public boolean machineStructure(IGregTechTileEntity thisController) {
        return checkPiece(3, 4, 3);
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
        return new MultiblockTooltipBuilder(mName)
                .addInfo("Teleportation on Space Satellite")
                .addMachineType("Space Elevator")
                .addInfo("Setup is done using Laptop")
                .addInfo("Consume energy for teleport: " + CAPACITY_FOR_TELEPORT + " EU")
                .addController("See the structure hologram")
                .addOtherStructurePart("Energy Hatch", "Any casing; maximum 4")
                .addCasingInfoMin(CASING, 150, false)
                .addOtherStructurePart("Space Elevator Hawser", "Center below Controller")
                .addStructureHint("Casing or compatible hatch", HintColor.GREEN)
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
    public boolean checkRecipe(ItemStack itemStack) {
        this.mMaxProgresstime = 20;
        this.mEUt = 0;
        this.mEfficiencyIncrease = 10000;
        this.mEfficiency = (10000 - (this.getIdealStatus() - this.getRepairStatus()) * 1000);
        return this.mEfficiency >= 10000;
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
                            .buildAndChain(HATCHES_A), HintColor.GREEN))
                    .addElement('B', ofBlockAnyMeta(SpaceElevatorBlock))
                    .build();
        }
        return STRUCTURE_DEFINITION;
    }

    @Override
    public boolean hasSeparate() {
        return false;
    }

    private static class GUI extends GUI_BASE {

        public GUI(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity) {
            super("Space Elevator", "SpaceElevator.png", new CONTAINER(aInventoryPlayer, aTileEntity));
        }

        @Override
        protected void drawGuiContainerForegroundLayer(int par1, int par2) {
            fontRendererObj.drawString(mName, 10, 8, 16448255);
            needTools(true);
            if (this.mContainer != null && ((mContainer).mDisplayErrorCode & 64) == 0) {
                long charge = Math.min(((CONTAINER) mContainer).currentCharge, CAPACITY_FOR_TELEPORT);
                String needCharge = String.format("%s / %s EU", GT_Utility.formatNumbers(charge),
                        GT_Utility.formatNumbers(CAPACITY_FOR_TELEPORT));
                fontRendererObj.drawString(
                        Language.translate("space_elevator_need-charge", "Need energy for teleport:"), 10, 30,
                        16448255);
                fontRendererObj.drawString(needCharge, 10, 40, 16448255);
                double tScale = ((double) charge / (double) CAPACITY_FOR_TELEPORT) * 100;
                fontRendererObj.drawString(GT_Utility.formatNumbers((int) tScale) + "%", 71, 56, 16448255);
            }
        }

        @Override
        protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
            super.drawGuiContainerBackgroundLayer(par1, par2, par3);
            int x = (this.width - this.xSize) / 2;
            int y = (this.height - this.ySize) / 2;
            this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
            if (this.mContainer != null && ((mContainer).mDisplayErrorCode & 64) == 0) {
                double tScale = (double) Math.min(((CONTAINER) mContainer).currentCharge, CAPACITY_FOR_TELEPORT)
                        / (double) CAPACITY_FOR_TELEPORT;
                drawTexturedModalRect(x + 22, y + 55, 0, 232, Math.min(113, (int) (tScale * 113)), 9);
                drawTexturedModalRect(x + 19, y + 52, 0, 241, 119, 15);
            }
        }
    }

    private static class CONTAINER extends GTC_ImpactBase {

        public int currentCharge = 0;

        public CONTAINER(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity) {
            super(aInventoryPlayer, aTileEntity);
        }

        @Override
        public void addSlots(InventoryPlayer aInventoryPlayer) {
            super.addSlots(aInventoryPlayer);
            addSlotToContainer(new GT_Slot_Holo(mTileEntity, 1, 152, 42, false, false, 1));
        }

        @Override
        public void detectAndSendChanges() {
            super.detectAndSendChanges();
            if (mTileEntity.isClientSide() || mTileEntity.getMetaTileEntity() == null)
                return;
            currentCharge = ((GTMTE_SpaceElevator) mTileEntity.getMetaTileEntity()).currentCharge;

            for (Object crafter : this.crafters) {
                ICrafting var1 = (ICrafting) crafter;
                var1.sendProgressBarUpdate(this, 100, currentCharge & 65535);
                var1.sendProgressBarUpdate(this, 101, currentCharge >>> 16);
            }
        }

        @SideOnly(Side.CLIENT)
        @Override
        public void updateProgressBar(int id, int data) {
            super.updateProgressBar(id, data);
            switch (id) {
            case 100:
                currentCharge = currentCharge & -65536 | data;
                break;
            case 101:
                currentCharge = currentCharge & 65535 | data << 16;
                break;
            }
        }

        @Override
        public ItemStack slotClick(int aSlotIndex, int aMouseclick, int aShifthold, EntityPlayer aPlayer) {
            if (aSlotIndex == 1 && mte != null && mte instanceof GTMTE_SpaceElevator) {
                GTMTE_SpaceElevator elevator = (GTMTE_SpaceElevator) mte;
                elevator.teleportEntity(aPlayer);
                return null;
            } else {
                return super.slotClick(aSlotIndex, aMouseclick, aShifthold, aPlayer);
            }
        }
    }
}
