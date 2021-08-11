package com.impact.mods.gregtech.tileentities.multi.photonsystem;

import com.github.technus.tectech.mechanics.alignment.enumerable.ExtendedFacing;
import com.github.technus.tectech.mechanics.constructable.IMultiblockInfoContainer;
import com.github.technus.tectech.mechanics.structure.IStructureDefinition;
import com.github.technus.tectech.mechanics.structure.StructureDefinition;
import com.impact.impact;
import com.impact.loader.ItemRegistery;
import com.impact.mods.gregtech.blocks.Casing_Helper;
import com.impact.mods.gregtech.tileentities.multi.implement.GT_MetaTileEntity_MultiParallelBlockBase;
import com.impact.mods.gregtech.tileentities.multi.parallelsystem.GTMTE_ParallelHatch_Output;
import com.impact.util.string.MultiBlockTooltipBuilder;
import com.impact.util.vector.Vector3i;
import com.impact.util.vector.Vector3ic;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.objects.GT_RenderedTexture;
import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.Random;

import static com.github.technus.tectech.mechanics.constructable.IMultiblockInfoContainer.registerMetaClass;
import static com.github.technus.tectech.mechanics.structure.StructureUtility.ofBlock;
import static net.minecraft.util.EnumChatFormatting.*;

public class GTMTE_PhotonStabilizer extends GT_MetaTileEntity_MultiParallelBlockBase {

    public static Block CASING = Casing_Helper.sCaseCore2;
    public static byte CASING_META = 15;
    ITexture INDEX_CASE = Textures.BlockIcons.casingTexturePages[3][CASING_META + 16];
    int CASING_TEXTURE_ID = CASING_META + 16 + 128 * 3;

    public int mPhotonsSummary = 0;
    public boolean mPhotonsBeam = false;

    public int mRangeToContainer = 0;
    public boolean mCheckContainer = true;
    public GTMTE_PhotonContainment mPhotonContainment;

    //region Register
    public GTMTE_PhotonStabilizer(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
        run();
    }

    public GTMTE_PhotonStabilizer(String aName) {
        super(aName);
        run();
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        run();
        return new GTMTE_PhotonStabilizer(this.mName);
    }
    //endregion

    @Override
    public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide,
                                 final byte aFacing, final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
        return aSide == aFacing ? new ITexture[]{INDEX_CASE,
                new GT_RenderedTexture(aActive ? Textures.BlockIcons.MP1a : Textures.BlockIcons.MP1)}
                : new ITexture[]{INDEX_CASE};
    }

    @Override
    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
//        return new GUI_SuperParallelComputer(aPlayerInventory, aBaseMetaTileEntity, getLocalName());
        return false;
    }

    @Override
    public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
//        return new Container_SuperParallelComputer(aPlayerInventory, aBaseMetaTileEntity, this);
        return false;
    }

    public void run() {
        registerMetaClass(GTMTE_PhotonStabilizer.class,
                new IMultiblockInfoContainer<GTMTE_PhotonStabilizer>() {
                    //region Structure
                    private final IStructureDefinition<GTMTE_PhotonStabilizer> definition =
                            StructureDefinition.<GTMTE_PhotonStabilizer>builder()
                                    .addShape("main", new String[][]{
                                            {"AA AA","AB~BA","AA AA"},
                                            {"ABBBA","D   D","ABBBA"},
                                            {"AA AA","ABBBA","AA AA"},
                                    })
                                    .addElement('A', ofBlock(CASING, CASING_META))
                                    .addElement('B', ofBlock(ItemRegistery.photonSystem, 0))
                                    .addElement('D', ofBlock(ItemRegistery.IGlassBlock))
                                    .build();
                    private final String[] desc = new String[]{
                            RED + "Impact Details:",
                    };

                    //endregion
                    @Override
                    public void construct(ItemStack stackSize, boolean hintsOnly, GTMTE_PhotonStabilizer tileEntity, ExtendedFacing aSide) {
                        IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
                        definition.buildOrHints(tileEntity, stackSize, "main", base.getWorld(), aSide,
                                base.getXCoord(), base.getYCoord(), base.getZCoord(),
                                2, 1, 0, hintsOnly);
                    }

                    @Override
                    public String[] getDescription(ItemStack stackSize) {
                        return desc;
                    }
                });
    }

    @Override
    public String[] getDescription() {
        final MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder();
        b
                .addTypeMachine("psc.name") //Parametric Diffuser
                .addSeparator()
                .addController()
                .addEnergyHatch("psc.hatches")
                .addMaintenanceHatch("psc.hatches")
                .addOtherStructurePart("psc.other.0", "psc.other.1")
                .addOtherStructurePart("psc.other.2", "psc.other.3")
                .addOtherStructurePart("psc.other.4", "psc.other.5")
                .addCasingInfo("psc.case")
                .signAndFinalize();
        if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            return b.getInformation();
        } else {
            return b.getStructureInformation();
        }
    }

    @Override
    public boolean checkRecipe(ItemStack aStack) {
//        ItemStack gem = mInputBusses.get(0).mInventory[0];
//        int[] idOreDict = OreDictionary.getOreIDs(gem);
//        for (int id : idOreDict) {
//            if (OreDictionary.getOreName(id).startsWith("Exquisite")) {
//                this.mMaxProgresstime = 20;
//                this.mEfficiency = (10000 - (this.getIdealStatus() - this.getRepairStatus()) * 1000);
//                this.mEfficiencyIncrease = 10000;
////        this.mEUt = 8192;
////        this.mEUt = -this.mEUt;
//                return true;
//            }
//        }
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
                ForgeDirection.getOrientation(iAm.getBackFacing()).offsetZ
        );

        if (iAm.isActive()) {

            //Color color = new Color(0x05FFFF);

            Vector3ic offset = rotateOffsetVector(forgeDirection, 0, 0, -1);
            Vector3ic offsetToStabilizer = rotateOffsetVector(forgeDirection, mRangeToContainer-1, 0, -1);

            impact.proxy.beam(iAm.getWorld(), offset.x() + x + 0.5D, offset.y() + y + 0.5D, offset.z() + z + 0.5D,
                    offsetToStabilizer.x() + x + 0.5D, offsetToStabilizer.y() + y + 0.5D, offsetToStabilizer.z() + z + 0.5D,
                    1, 0x770ED0, false, 1, 20 * 5);
        }
    }

    @Override
    public void onPostTick(IGregTechTileEntity iAm, long aTick) {
        super.onPostTick(iAm, aTick);

        if (iAm.isServerSide() && aTick % 20*5 == 0) {
            final Vector3ic forgeDirection = new Vector3i(
                    ForgeDirection.getOrientation(iAm.getBackFacing()).offsetX,
                    ForgeDirection.getOrientation(iAm.getBackFacing()).offsetY,
                    ForgeDirection.getOrientation(iAm.getBackFacing()).offsetZ
            );
            doCheckContainer(iAm);

            if (iAm.isActive()) {
                Vector3ic offsetCheckBlock = rotateOffsetVector(forgeDirection, 0, 2, -1);
                impact.proxy.nodeBolt(iAm.getWorld(), iAm.getXCoord(), iAm.getYCoord(), iAm.getZCoord(),
                        offsetCheckBlock.x(), offsetCheckBlock.y(), offsetCheckBlock.z(), 5, 10F, 1);
                impact.proxy.nodeBolt(iAm.getWorld(), iAm.getXCoord(), iAm.getYCoord(), iAm.getZCoord(),
                        offsetCheckBlock.x(), offsetCheckBlock.y(), offsetCheckBlock.z(), 5, 10F, 1);
            }

            if (!mCheckContainer && iAm.isActive()) {

                if (mPhotonsSummary >= 1000) {

                    Vector3ic offsetX = rotateOffsetVector(forgeDirection, mRangeToContainer, 0, 1);
                    IGregTechTileEntity currentTE = iAm.getIGregTechTileEntityOffset(offsetX.x(), offsetX.y(), offsetX.z());

                    if (currentTE != null) {
                        if (currentTE.getMetaTileEntity() instanceof GTMTE_PhotonContainment) {
                            mPhotonContainment = (GTMTE_PhotonContainment) currentTE.getMetaTileEntity();
                            mCheckContainer = false;
                            if (currentTE.isActive()) {
                                mPhotonContainment.setPhotons(1000);
                                addBound(iAm);
                                mPhotonsSummary -= 1000;
                            }
                        }
                    } else {
                        mPhotonContainment = null;
                        mCheckContainer = true;
                        mRangeToContainer = 0;
                    }
                }
            }
        }
    }

    public void setPhotons(int amount) {
        mPhotonsSummary += amount;
    }

    public void doCheckContainer(IGregTechTileEntity iAm) {

        if (mMachine && mCheckContainer) {

            final Vector3ic forgeDirection = new Vector3i(
                    ForgeDirection.getOrientation(iAm.getBackFacing()).offsetX,
                    ForgeDirection.getOrientation(iAm.getBackFacing()).offsetY,
                    ForgeDirection.getOrientation(iAm.getBackFacing()).offsetZ
            );

            Vector3ic offsetX;
            IGregTechTileEntity currentTE = null;

            for (; mRangeToContainer < 30; mRangeToContainer++) {

                offsetX = rotateOffsetVector(forgeDirection, mRangeToContainer, 0, 1);
                currentTE = iAm.getIGregTechTileEntityOffset(offsetX.x(), offsetX.y(), offsetX.z());
                if (currentTE != null) {
                    if (currentTE.getMetaTileEntity() instanceof GTMTE_PhotonContainment) {
                        mPhotonContainment = (GTMTE_PhotonContainment) currentTE.getMetaTileEntity();
                        mCheckContainer = false;
                        break;
                    }
                }
            }
            if (currentTE == null) {
                mPhotonContainment = null;
                mCheckContainer = true;
                mRangeToContainer = 0;
            }
        }
    }

    @Override
    public boolean machineStructure(IGregTechTileEntity iAm) {
        mWrench = mScrewdriver = mSoftHammer = mHardHammer = mSolderingTool = mCrowbar = true;
        //region Structure
        final Vector3ic forgeDirection = new Vector3i(
                ForgeDirection.getOrientation(iAm.getBackFacing()).offsetX,
                ForgeDirection.getOrientation(iAm.getBackFacing()).offsetY,
                ForgeDirection.getOrientation(iAm.getBackFacing()).offsetZ);

        boolean formationChecklist = true;

        for (int x = -2; x <= 2; x++) {
            for (int y = 0; y <= 2; y++) {

                final Vector3ic offset = rotateOffsetVector(forgeDirection, x, -1, -y);
                IGregTechTileEntity currentTE = iAm.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());

                if (x == 0 && y != 1) continue;

                if (y == 1 && x >= -1 && x <= 1) {
                    if ((iAm.getBlockOffset(offset.x(), offset.y(), offset.z()) == ItemRegistery.photonSystem)
                            && (iAm.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 0)) {
                    } else {
                        formationChecklist = false;
                    }
                    continue;
                }

                if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
                        && !super.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)
                        && !super.addCommunicationHatchToMachineList(currentTE, CASING_TEXTURE_ID)) {
                    if ((iAm.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
                            && (iAm.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == CASING_META)) {
                    } else {
                        formationChecklist = false;
                    }
                }
            }
        }

        for (int x = -2; x <= 2; x++) {
            for (int y = 0; y <= 2; y++) {

                final Vector3ic offset = rotateOffsetVector(forgeDirection, x, 0, -y);
                IGregTechTileEntity currentTE = iAm.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());

                if (x == 0 && y == 0) continue;

                if (y == 1 && x >= -1 && x <= 1) continue;

                if (y != 1 && x >= -1 && x <= 1) {
                    if ((iAm.getBlockOffset(offset.x(), offset.y(), offset.z()) == ItemRegistery.photonSystem)
                            && (iAm.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 0)) {
                    } else {
                        formationChecklist = false;
                    }
                    continue;
                }

                if (y == 1) {
                    if ((iAm.getBlockOffset(offset.x(), offset.y(), offset.z()) == ItemRegistery.IGlassBlock)
                            && (iAm.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 14)) {
                    } else {
                        formationChecklist = false;
                    }
                    continue;
                }

                if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
                        && !super.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)
                        && !super.addCommunicationHatchToMachineList(currentTE, CASING_TEXTURE_ID)) {
                    if ((iAm.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
                            && (iAm.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == CASING_META)) {
                    } else {
                        formationChecklist = false;
                    }
                }
            }
        }

        for (int x = -2; x <= 2; x++) {
            for (int y = 0; y <= 2; y++) {

                final Vector3ic offset = rotateOffsetVector(forgeDirection, x, 1, -y);
                IGregTechTileEntity currentTE = iAm.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());

                if (x == 0 && y != 1) continue;

                if (y == 1 && x >= -1 && x <= 1) {
                    if ((iAm.getBlockOffset(offset.x(), offset.y(), offset.z()) == ItemRegistery.photonSystem)
                            && (iAm.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 0)) {
                    } else {
                        formationChecklist = false;
                    }
                    continue;
                }

                if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
                        && !super.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)
                        && !super.addCommunicationHatchToMachineList(currentTE, CASING_TEXTURE_ID)) {
                    if ((iAm.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
                            && (iAm.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == CASING_META)) {
                    } else {
                        formationChecklist = false;
                    }
                }
            }
        }

        return formationChecklist;
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
        aNBT.setInteger("mPhotonsSummary", mPhotonsSummary);
        aNBT.setInteger("mRangeToContainer", mRangeToContainer);
        aNBT.setBoolean("mPhotonsBeam", mPhotonsBeam);
        aNBT.setBoolean("mCheckContainer", mCheckContainer);
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
        mPhotonsSummary = aNBT.getInteger("mPhotonsSummary");
        mRangeToContainer = aNBT.getInteger("mRangeToContainer");
        mPhotonsBeam = aNBT.getBoolean("mPhotonsBeam");
        mCheckContainer = aNBT.getBoolean("mCheckContainer");
    }

    public String[] getInfoData() {
        return new String[]{
                "Usage Energy: " + RED + -mEUt + RESET + " EU/t",
                "Max Voltage: " + YELLOW + getMaxInputVoltage() + RESET + " EU/t ",
                "Maintenance: " + ((super.getRepairStatus() == super.getIdealStatus()) ? GREEN + "Good "
                        + YELLOW + mEfficiency / 100.0F + " %" + RESET
                        : RED + "Has Problems " + mEfficiency / 100.0F + " %" + RESET),
        };
    }
}
