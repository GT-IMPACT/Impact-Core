package com.impact.mods.gregtech.tileentities.multi.photonsystem;

import com.github.technus.tectech.mechanics.alignment.enumerable.ExtendedFacing;
import com.github.technus.tectech.mechanics.constructable.IMultiblockInfoContainer;
import com.github.technus.tectech.mechanics.structure.IStructureDefinition;
import com.github.technus.tectech.mechanics.structure.StructureDefinition;
import com.impact.impact;
import com.impact.loader.ItemRegistery;
import com.impact.mods.gregtech.blocks.Casing_Helper;
import com.impact.mods.gregtech.tileentities.multi.implement.GT_MetaTileEntity_MultiParallelBlockBase;
import com.impact.util.string.MultiBlockTooltipBuilder;
import com.impact.util.vector.Vector3i;
import com.impact.util.vector.Vector3ic;
import gregtech.api.GregTech_API;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.oredict.OreDictionary;
import org.lwjgl.input.Keyboard;

import java.util.Random;

import static com.github.technus.tectech.mechanics.constructable.IMultiblockInfoContainer.registerMetaClass;
import static com.github.technus.tectech.mechanics.structure.StructureUtility.ofBlock;
import static net.minecraft.util.EnumChatFormatting.*;

public class GTMTE_ParametricDiffuser extends GT_MetaTileEntity_MultiParallelBlockBase {

    public static Block CASING = Casing_Helper.sCaseCore2;
    public static byte CASING_META = 15;
    public int mPhotonsGenerate = 0;
    public boolean checkStabilizer = true;
    public int rangeToStabilizer = 0;
    public int mPeakBeamPhotons = 1;
    public GTMTE_PhotonStabilizer mPhotonStabilizer;
    ITexture INDEX_CASE = Textures.BlockIcons.casingTexturePages[3][CASING_META + 16];
    int CASING_TEXTURE_ID = CASING_META + 16 + 128 * 3;

    //region Register
    public GTMTE_ParametricDiffuser(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
        run();
    }

    public GTMTE_ParametricDiffuser(String aName) {
        super(aName);
        run();
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        run();
        checkStabilizer = true;
        return new GTMTE_ParametricDiffuser(this.mName);
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
        registerMetaClass(GTMTE_ParametricDiffuser.class,
                new IMultiblockInfoContainer<GTMTE_ParametricDiffuser>() {
                    //region Structure
                    private final IStructureDefinition<GTMTE_ParametricDiffuser> definition =
                            StructureDefinition.<GTMTE_ParametricDiffuser>builder()
                                    .addShape("main", new String[][]{
                                            {"     A   A", "     AAAAA", "     AAAAA", "          "},
                                            {"      AAA ", "AAAAAAAAAA", "~AFFFCCCCA", "AAAAAAAAAA"},
                                            {"      AAA ", "AAFFFCCCCA", "AD       F", "AACCCCCCCA"},
                                            {"      AAA ", "AAAAAAAAAA", "AAFFFCCCCA", "AAAAAAAAAA"},
                                            {"     A   A", "     AAAAA", "     AAAAA", "          "}
                                    })
                                    .addElement('A', ofBlock(CASING, CASING_META))
                                    .addElement('C', ofBlock(ItemRegistery.photonSystem, 0))
                                    .addElement('D', ofBlock(ItemRegistery.photonTransducer, 0))
                                    .addElement('F', ofBlock(ItemRegistery.IGlassBlock))
                                    .build();
                    private final String[] desc = new String[]{
                            RED + "Impact Details:",
                    };

                    //endregion
                    @Override
                    public void construct(ItemStack stackSize, boolean hintsOnly, GTMTE_ParametricDiffuser tileEntity, ExtendedFacing aSide) {
                        IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
                        definition.buildOrHints(tileEntity, stackSize, "main", base.getWorld(), aSide,
                                base.getXCoord(), base.getYCoord(), base.getZCoord(),
                                0, 2, 1, hintsOnly);
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
        ItemStack gem = mInputBusses.get(0).mInventory[0];
        int[] idOreDict = OreDictionary.getOreIDs(gem);
        for (int id : idOreDict) {
            if (OreDictionary.getOreName(id).startsWith("gemExquisite")) {
                this.mMaxProgresstime = 20;
                this.mEfficiency = (10000 - (this.getIdealStatus() - this.getRepairStatus()) * 1000);
                this.mEfficiencyIncrease = 10000;
                int tier = GT_Utility.getTier(getMaxInputVoltage());
                this.mPeakBeamPhotons = mEfficiency < 10000 ? 0 : tier > 6 ? tier - 5 : 0;
                this.mEUt = -(int) getMaxInputVoltage();
                return true;
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
                ForgeDirection.getOrientation(iAm.getBackFacing()).offsetZ
        );

        if (iAm.isActive()) {

            Vector3ic offset = rotateOffsetVector(forgeDirection, 1, 0, -1);
            Vector3ic offsetToStabilizer = rotateOffsetVector(forgeDirection, rangeToStabilizer, 0, -1);

            impact.proxy.beam(iAm.getWorld(), offset.x() + x + 0.5D, offset.y() + y + 0.5D, offset.z() + z + 0.5D,
                    offsetToStabilizer.x() + x + 0.5D, offsetToStabilizer.y() + y + 0.5D, offsetToStabilizer.z() + z + 0.5D,
                    0, 0x770ED0, false, 1, 20);

        }
    }

    @Override
    public void onPostTick(IGregTechTileEntity iAm, long aTick) {
        super.onPostTick(iAm, aTick);

        if (iAm.isServerSide() && aTick % 20 == 0) {
            final Vector3ic forgeDirection = new Vector3i(
                    ForgeDirection.getOrientation(iAm.getBackFacing()).offsetX,
                    ForgeDirection.getOrientation(iAm.getBackFacing()).offsetY,
                    ForgeDirection.getOrientation(iAm.getBackFacing()).offsetZ
            );
            doCheckStabilizer(iAm);

            if (!checkStabilizer && iAm.isActive()) {

                Vector3ic offsetX = rotateOffsetVector(forgeDirection, rangeToStabilizer, 0, 0);

                IGregTechTileEntity currentTE = iAm.getIGregTechTileEntityOffset(offsetX.x(), offsetX.y(), offsetX.z());

                if (currentTE != null) {
                    if (currentTE.getMetaTileEntity() instanceof GTMTE_PhotonStabilizer) {
                        mPhotonStabilizer = (GTMTE_PhotonStabilizer) currentTE.getMetaTileEntity();
                        checkStabilizer = false;
                        if (currentTE.isActive()) {
                            Random random = new Random();
                            mPhotonsGenerate = random.nextInt(200) * mPeakBeamPhotons;
                            mPhotonStabilizer.setPhotons(mPhotonsGenerate);
                            addBound(iAm);
                            GT_Utility.doSoundAtClient(GregTech_API.sSoundList.get(210),
                                    100, 1.0F, iAm.getXCoord(), iAm.getYCoord(), iAm.getZCoord());
                            if (mPhotonStabilizer.mPhotonsSummary >= 100000) {
                                mPhotonStabilizer.getBaseMetaTileEntity().doExplosion(Long.MAX_VALUE);
                            }
                        }
                    }
                } else {
                    mPhotonStabilizer = null;
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
                    ForgeDirection.getOrientation(iAm.getBackFacing()).offsetZ
            );

            Vector3ic offsetX;
            IGregTechTileEntity currentTE = null;

            for (; rangeToStabilizer < 30; rangeToStabilizer++) {

                offsetX = rotateOffsetVector(forgeDirection, rangeToStabilizer, 0, 0);

                currentTE = iAm.getIGregTechTileEntityOffset(offsetX.x(), offsetX.y(), offsetX.z());

                if (currentTE != null) {
                    if (currentTE.getMetaTileEntity() instanceof GTMTE_PhotonStabilizer) {
                        mPhotonStabilizer = (GTMTE_PhotonStabilizer) currentTE.getMetaTileEntity();
                        checkStabilizer = false;
                        break;
                    }
                }
            }
            if (currentTE == null) {
                mPhotonStabilizer = null;
                checkStabilizer = true;
                rangeToStabilizer = 0;
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

        for (int x = 0; x <= 9; x++) {
            for (int y = 0; y <= 2; y++) {

                final Vector3ic offset = rotateOffsetVector(forgeDirection, x, -1, -y);
                IGregTechTileEntity currentTE = iAm.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());

                if (y == 1 && (x >= 2 && x <= 8)) {
                    if ((iAm.getBlockOffset(offset.x(), offset.y(), offset.z()) == ItemRegistery.photonSystem)
                            && (iAm.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 0)) {
                    } else {
                        formationChecklist = false;
                    }
                    continue;
                }

                if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
                        && !super.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)
                        && !super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)) {
                    if ((iAm.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
                            && (iAm.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == CASING_META)) {
                    } else {
                        formationChecklist = false;
                    }
                }
            }
        }

        for (int x = 0; x <= 9; x++) {
            for (int y = -1; y <= 3; y++) {

                if (x == 0 && y == 0) continue;

                final Vector3ic offset = rotateOffsetVector(forgeDirection, x, 0, -y);
                IGregTechTileEntity currentTE = iAm.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());

                if (y == 1 && (x >= 2 && x <= 8)) continue;

                if (!(y >= 0 && y <= 2) && !(x >= 5)) continue;

                if (x >= 2 && x <= 4) {
                    if ((iAm.getBlockOffset(offset.x(), offset.y(), offset.z()) == ItemRegistery.IGlassBlock)) {
                    } else {
                        formationChecklist = false;
                    }
                    continue;
                }

                if (x >= 5 && x <= 8 && y >= 0 && y <= 2) {
                    if ((iAm.getBlockOffset(offset.x(), offset.y(), offset.z()) == ItemRegistery.photonSystem)
                            && (iAm.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 0)) {
                    } else {
                        formationChecklist = false;
                    }
                    continue;
                }

                // todo нос
                if (x == 9 && y == 1) {
                    if ((iAm.getBlockOffset(offset.x(), offset.y(), offset.z()) == ItemRegistery.IGlassBlock)) {
                    } else {
                        formationChecklist = false;
                    }
                    continue;
                }

                // todo начинка
                if (x == 1 && y == 1) {
                    if ((iAm.getBlockOffset(offset.x(), offset.y(), offset.z()) == ItemRegistery.photonTransducer)
                            && (iAm.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 0)) {
                    } else {
                        formationChecklist = false;
                    }
                    continue;
                }

                if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
                        && !super.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)
                        && !super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)) {
                    if ((iAm.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
                            && (iAm.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == CASING_META)) {
                    } else {
                        formationChecklist = false;
                    }
                }
            }
        }

        for (int x = 0; x <= 9; x++) {
            for (int y = -1; y <= 3; y++) {

                final Vector3ic offset = rotateOffsetVector(forgeDirection, x, 1, -y);
                IGregTechTileEntity currentTE = iAm.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());

                if (y == -1 && !(x >= 5)) continue;
                if (y == 3 && !(x >= 5)) continue;

                if (x >= 2 && x <= 4 && y == 1) {
                    if ((iAm.getBlockOffset(offset.x(), offset.y(), offset.z()) == ItemRegistery.IGlassBlock)) {
                    } else {
                        formationChecklist = false;
                    }
                    continue;
                }

                if (x >= 5 && x <= 8 && y == 1) {
                    if ((iAm.getBlockOffset(offset.x(), offset.y(), offset.z()) == ItemRegistery.photonSystem)
                            && (iAm.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 0)) {
                    } else {
                        formationChecklist = false;
                    }
                    continue;
                }

                if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
                        && !super.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)
                        && !super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)) {
                    if ((iAm.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
                            && (iAm.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == CASING_META)) {
                    } else {
                        formationChecklist = false;
                    }
                }
            }
        }

        for (int x = 5; x <= 9; x++) {
            for (int y = -1; y <= 3; y++) {

                final Vector3ic offset = rotateOffsetVector(forgeDirection, x, 2, -y);
                IGregTechTileEntity currentTE = iAm.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());

                if (x == 5 && y >= 0 && y <= 2) continue;
                if (x == 9 && y >= 0 && y <= 2) continue;

                if (y == -1 && x >= 6 && x <= 8) continue;
                if (y == 3 && x >= 6 && x <= 8) continue;

                if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
                        && !super.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)
                        && !super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)) {
                    if ((iAm.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
                            && (iAm.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == CASING_META)) {
                    } else {
                        formationChecklist = false;
                    }
                }
            }
        }

//        if (this.mEnergyHatches.size() > 1) {
//            formationChecklist = false;
//        }
//        if (this.mMaintenanceHatches.size() != 1) {
//            formationChecklist = false;
//        }
        return formationChecklist;
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
        aNBT.setInteger("mPhotonsGenerate", mPhotonsGenerate);
        aNBT.setInteger("rangeToStabilizer", rangeToStabilizer);
        aNBT.setInteger("mPeakBeamPhotons", mPeakBeamPhotons);
        aNBT.setBoolean("checkStabilizer", checkStabilizer);
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
        mPhotonsGenerate = aNBT.getInteger("mPhotonsGenerate");
        rangeToStabilizer = aNBT.getInteger("rangeToStabilizer");
        mPeakBeamPhotons = aNBT.getInteger("mPeakBeamPhotons");
        checkStabilizer = aNBT.getBoolean("checkStabilizer");
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
