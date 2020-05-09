package com.impact.mods.GregTech.tileentities.multi;

import com.impact.mods.GregTech.casings.CORE_API;
import com.impact.mods.GregTech.tileentities.multi.debug.GT_MetaTileEntity_MultiParallelBlockBase;
import com.impact.mods.GregTech.tileentities.multi.gui.GUI_Wire;
import com.impact.util.MultiBlockTooltipBuilder;
import com.impact.util.Vector3i;
import com.impact.util.Vector3ic;
import gregtech.api.GregTech_API;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Output;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_ModHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.input.Keyboard;

import static gregtech.api.enums.GT_Values.W;
import static net.minecraftforge.common.BiomeDictionary.Type.*;

public class GTMTE_BasicWaterPump extends GT_MetaTileEntity_MultiParallelBlockBase {

    protected int boimeWater;
    int mIsWaterSource;
    long tTime = System.currentTimeMillis();

    /**
     * === SET BLOCKS STRUCTURE ===
     */
    Block CASING = CORE_API.sCaseCore2;
    byte CASING_META = 7;

    /**
     * === SET TEXTURES HATCHES AND CONTROLLER ===
     */
    ITexture INDEX_CASE = Textures.BlockIcons.casingTexturePages[3][16 + CASING_META];
    int CASING_TEXTURE_ID = CASING_META + 16 + 128 * 3;

    /**
     * === SET TEXTURE ===
     */
    @Override
    public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide, final byte aFacing,
                                 final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
        return aSide == aFacing
                ? new ITexture[]{INDEX_CASE, new GT_RenderedTexture(
                aActive
                        ? Textures.BlockIcons.OVERLAY_PUMP
                        : Textures.BlockIcons.OVERLAY_PUMP)}
                : new ITexture[]{INDEX_CASE};
    }

    /**
     * === NAMED ===
     */
    public GTMTE_BasicWaterPump(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    /**
     * === NAMED ===
     */
    public GTMTE_BasicWaterPump(String aName) {
        super(aName);
    }

    /**
     * === META ENTITY ===
     */
    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GTMTE_BasicWaterPump(this.mName);
    }

    /**
     * === DESCRIPTION ===
     */
    @Override
    public String[] getDescription() {
        final MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder();
        b
                .addInfo("One-block machine analog")
                .addParallelInfo(1, 256)
                .addInfo("Parallel Point will upped Upgrade Casing")
                .addTypeMachine("WireMill, Wire Assembler")
                .addScrew()
                .addSeparator()
                .beginStructureBlock(3, 3, 3)
                .addController("-")
                .addParallelCase("-")
                .addEnergyHatch("Any casing")
                .addMaintenanceHatch("Any casing")
                .addInputBus("Any casing (max x6)")
                .addOutputBus("Any casing (max x3)")
                .addInputHatch("Any casing (max x3)")
                .addCasingInfo("Wire Factory Casing")
                .signAndFinalize(": " + EnumChatFormatting.RED + "IMPACT", true);
        if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            return b.getInformation();
        } else if (!Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
            return b.getControlInfo();
        } else {
            return b.getStructureInformation();
        }
    }

    /**
     * === GUI ===
     */
    @Override
    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return null;
    }

    @Override
    public boolean checkRecipe(ItemStack aStack) {

        return true;
    }

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        if (aTick % 20 == 0) {
            addOutput(GT_ModHandler.getWater(((getWaterInBiomes() * ((getTierFluidHatch() * 3) + 1)) + mIsWaterSource)));
        }
        if (aTick % 1200 == 0 || aTick == 20) {
            checkMachine(aBaseMetaTileEntity, mInventory[1]);
        }
    }

    public Vector3ic rotateOffsetVector(Vector3ic forgeDirection, int x, int y, int z) {
        final Vector3i offset = new Vector3i();

        // В любом направлении по оси Z
        if (forgeDirection.x() == 0 && forgeDirection.z() == -1) {
            offset.x = x;
            offset.y = y;
            offset.z = z;
        }
        if (forgeDirection.x() == 0 && forgeDirection.z() == 1) {
            offset.x = -x;
            offset.y = y;
            offset.z = -z;
        }
        // В любом направлении по оси X
        if (forgeDirection.x() == -1 && forgeDirection.z() == 0) {
            offset.x = z;
            offset.y = y;
            offset.z = -x;
        }
        if (forgeDirection.x() == 1 && forgeDirection.z() == 0) {
            offset.x = -z;
            offset.y = y;
            offset.z = x;
        }
        // в любом направлении по оси Y
        if (forgeDirection.y() == -1) {
            offset.x = x;
            offset.y = z;
            offset.z = y;
        }

        return offset;
    }

    public boolean checkMachine(IGregTechTileEntity thisController, ItemStack guiSlotItem) {
        if (BiomeDictionary.isBiomeOfType(thisController.getBiome(), WATER)
        ) this.boimeWater = 1000;

        if (BiomeDictionary.isBiomeOfType(thisController.getBiome(), CONIFEROUS)
        ) this.boimeWater = 175;

        if (BiomeDictionary.isBiomeOfType(thisController.getBiome(), JUNGLE)
        ) this.boimeWater = 350;

        if (BiomeDictionary.isBiomeOfType(thisController.getBiome(), SWAMP)
        ) this.boimeWater = 800;

        if (BiomeDictionary.isBiomeOfType(thisController.getBiome(), SNOWY)
        ) this.boimeWater = 300;

        if (BiomeDictionary.isBiomeOfType(thisController.getBiome(), BEACH)
        ) this.boimeWater = 170;

        if (BiomeDictionary.isBiomeOfType(thisController.getBiome(), PLAINS)
                || BiomeDictionary.isBiomeOfType(thisController.getBiome(), FOREST)
        ) this.boimeWater = 250;

        if (BiomeDictionary.isBiomeOfType(thisController.getBiome(), HILLS)
                || BiomeDictionary.isBiomeOfType(thisController.getBiome(), MOUNTAIN)
                || BiomeDictionary.isBiomeOfType(thisController.getBiome(), SAVANNA)
                || BiomeDictionary.isBiomeOfType(thisController.getBiome(), SANDY)
                || BiomeDictionary.isBiomeOfType(thisController.getBiome(), MESA)
        ) this.boimeWater = 100;

        int frameMeta = GregTech_API.METATILEENTITIES[4905] != null ? GregTech_API.METATILEENTITIES[4905].getTileEntityBaseType() : W;

        // Вычисляем вектор направления, в котором находится задняя поверхность контроллера
        final Vector3ic forgeDirection = new Vector3i(
                ForgeDirection.getOrientation(thisController.getBackFacing()).offsetX,
                ForgeDirection.getOrientation(thisController.getBackFacing()).offsetY,
                ForgeDirection.getOrientation(thisController.getBackFacing()).offsetZ);

        int minCasingAmount = 12; // Минимальное количество кейсов
        boolean formationChecklist = true; // Если все ок, машина собралась

        for (byte X = 0; X >= -3; X--) {
            for (byte Z = 0; Z >= -2; Z--) {

                if (X == 0 && Z == 0) continue;

                final Vector3ic offset = rotateOffsetVector(forgeDirection, X, 0, Z);

                if (X == -2 && Z == -1) {
                    IGregTechTileEntity currentTE = thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
                    if (!super.addOutputToMachineList(currentTE, CASING_TEXTURE_ID)) {
                        if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
                                && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == CASING_META)) {
                        } else {
                            formationChecklist = false;
                        }
                    }
                    continue;
                }

                if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
                        && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == CASING_META)) {
                    minCasingAmount--;
                } else {
                    formationChecklist = false;

                }
            }
        }

        final Vector3ic offset = rotateOffsetVector(forgeDirection, -2, 1, 0);
        if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == GregTech_API.sBlockMachines)
                && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == frameMeta)) {
            minCasingAmount--;
        } else {
            formationChecklist = false;
        }

        final Vector3ic offset2 = rotateOffsetVector(forgeDirection, -2, 1, -2);
        if ((thisController.getBlockOffset(offset2.x(), offset2.y(), offset2.z()) == GregTech_API.sBlockMachines)
                && (thisController.getMetaIDOffset(offset2.x(), offset2.y(), offset2.z()) == frameMeta)) {
            minCasingAmount--;
        } else {
            formationChecklist = false;
        }

        final Vector3ic offset3 = rotateOffsetVector(forgeDirection, -3, 1, -1);
        if ((thisController.getBlockOffset(offset3.x(), offset3.y(), offset3.z()) == GregTech_API.sBlockMachines)
                && (thisController.getMetaIDOffset(offset3.x(), offset3.y(), offset3.z()) == frameMeta)) {
            minCasingAmount--;
        } else {
            formationChecklist = false;
        }

        final Vector3ic offset4 = rotateOffsetVector(forgeDirection, 0, 1, -1);
        if ((thisController.getBlockOffset(offset4.x(), offset4.y(), offset4.z()) == GregTech_API.sBlockMachines)
                && (thisController.getMetaIDOffset(offset4.x(), offset4.y(), offset4.z()) == frameMeta)) {
            minCasingAmount--;
        } else {
            formationChecklist = false;
        }

        final Vector3ic offset5 = rotateOffsetVector(forgeDirection, -2, 2, 0);
        if ((thisController.getBlockOffset(offset5.x(), offset5.y(), offset5.z()) == GregTech_API.sBlockMachines)
                && (thisController.getMetaIDOffset(offset5.x(), offset5.y(), offset5.z()) == frameMeta)) {
            minCasingAmount--;
        } else {
            formationChecklist = false;
        }

        final Vector3ic offset6 = rotateOffsetVector(forgeDirection, -2, 2, -2);
        if ((thisController.getBlockOffset(offset6.x(), offset6.y(), offset6.z()) == GregTech_API.sBlockMachines)
                && (thisController.getMetaIDOffset(offset6.x(), offset6.y(), offset6.z()) == frameMeta)) {
            minCasingAmount--;
        } else {
            formationChecklist = false;
        }

        for (byte X = 0; X >= -3; X--) {
            final Vector3ic offset7 = rotateOffsetVector(forgeDirection, X, 2, -1);
            if ((thisController.getBlockOffset(offset7.x(), offset7.y(), offset7.z()) == GregTech_API.sBlockMachines)
                    && (thisController.getMetaIDOffset(offset7.x(), offset7.y(), offset7.z()) == frameMeta)) {
                minCasingAmount--;
            } else {
                formationChecklist = false;
            }
        }

        for (byte X = 1; X >= -4; X--) {
            for (byte Z = 1; Z >= -3; Z--) {
                final Vector3ic offset7 = rotateOffsetVector(forgeDirection, X, -1, Z);
                if (thisController.getBlockOffset(offset7.x(), offset7.y(), offset7.z()) == Blocks.water) {
                    this.mIsWaterSource = 200;
                } else this.mIsWaterSource = 0;
            }
        }


        mWrench = true;
        mScrewdriver = true;
        mSoftHammer = true;
        mHardHammer = true;
        mSolderingTool = true;
        mCrowbar = true;

        if (getTierFluidHatch() > 1) formationChecklist = false;

        return formationChecklist;
    }

    public int getTierFluidHatch() {
        int Tier = 0;
        for (GT_MetaTileEntity_Hatch_Output tHatch : mOutputHatches)
            if (isValidMetaTileEntity(tHatch)) Tier = tHatch.mTier;
        return Tier;
    }

    public int getWaterInBiomes() {
        return this.boimeWater;
    }

    public int Parallel() {
        return 0;
    }

    /**
     * === POLLUTION ===
     */
    @Override
    public int getPollutionPerTick(ItemStack aStack) {
        return 0;
    }

}