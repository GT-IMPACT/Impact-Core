package com.impact.mods.GregTech.tileentities.multi;

import com.github.technus.tectech.thing.metaTileEntity.hatch.GT_MetaTileEntity_Hatch_EnergyMulti;
import com.github.technus.tectech.thing.metaTileEntity.hatch.GT_MetaTileEntity_Hatch_EnergyTunnel;
import com.impact.mods.GregTech.blocks.Casing_Helper;
import com.impact.mods.GregTech.tileentities.multi.debug.GT_MetaTileEntity_MultiParallelBlockBase;
import com.impact.mods.GregTech.gui.GUI_BASE;
import com.impact.util.MultiBlockTooltipBuilder;
import com.impact.util.Vector3i;
import com.impact.util.Vector3ic;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Energy;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.input.Keyboard;

import static com.mojang.realmsclient.gui.ChatFormatting.*;
import static com.mojang.realmsclient.gui.ChatFormatting.YELLOW;

public class GTMTE_Macerator extends GT_MetaTileEntity_MultiParallelBlockBase {

    /**
     * === SET BLOCKS STRUCTURE ===
     */
    Block CASING = Casing_Helper.sCaseCore2;
    byte CASING_META = 3;
    /**
     * === SET TEXTURES HATCHES AND CONTROLLER ===
     */
    ITexture INDEX_CASE = Textures.BlockIcons.casingTexturePages[3][CASING_META + 16];
    int CASING_TEXTURE_ID = CASING_META + 16 + 128 * 3;
    private byte mMode = -1;
    private int mLevel = 0;

    /**
     * === NAMED ===
     */
    public GTMTE_Macerator(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    /**
     * === NAMED ===
     */
    public GTMTE_Macerator(String aName) {
        super(aName);
    }

    /**
     * === SET TEXTURE ===
     */
    @Override
    public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide, final byte aFacing,
                                 final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
        return aSide == aFacing
                ? new ITexture[]{INDEX_CASE, new GT_RenderedTexture(
                aActive
                        ? Textures.BlockIcons.MP1a
                        : Textures.BlockIcons.MP1)}
                : new ITexture[]{INDEX_CASE};
    }

    /**
     * === META ENTITY ===
     */
    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GTMTE_Macerator(this.mName);
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
                .addTypeMachine("Macerator")
                .addSeparatedBus()
                .addSeparator()
                .addController()
                .addEnergyHatch("Any casing")
                .addMaintenanceHatch("Any casing")
                .addInputBus("Any casing (max x8)")
                .addOutputBus("Any casing (max x1)")
                .addMuffler("Any casing (max x1)")
                .addCasingInfo("Maceration Casing")
                .signAndFinalize(": " + EnumChatFormatting.RED + "IMPACT");
        if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            return b.getInformation();
        } else {
            return b.getStructureInformation();
        }
    }

    /**
     * === GUI ===
     */
    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GUI_BASE(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "MultiParallelBlockGUI.png", " Maceration ");
    }

    /**
     * === RECIPE MAP ===
     */
    @Override
    public GT_Recipe.GT_Recipe_Map getRecipeMap() {
        return GT_Recipe.GT_Recipe_Map.sMaceratorRecipes;
    }

    public boolean checkMachine(IGregTechTileEntity thisController, ItemStack guiSlotItem) {
        TThatches();
        // Вычисляем вектор направления, в котором находится задняя поверхность контроллера
        final Vector3ic forgeDirection = new Vector3i(
                ForgeDirection.getOrientation(thisController.getBackFacing()).offsetX,
                ForgeDirection.getOrientation(thisController.getBackFacing()).offsetY,
                ForgeDirection.getOrientation(thisController.getBackFacing()).offsetZ);

        int minCasingAmount = 12; // Минимальное количество кейсов
        boolean formationChecklist = true; // Если все ок, машина собралась


        for (byte X = -1; X <= 1; X++) {
            for (byte Z = 0; Z >= -2; Z--) {
                for (byte Y = 0; Y <= 5; Y++) {

                    if (X == 0 && Y == 0 && Z == 0) continue;

                    final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Y, Z);

                    if (X == 0 && Z == -1 && (Y == 1 || Y == 2 || Y == 3 || Y == 4)) {
                        if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == Casing_Helper.sCaseCore1)
                                && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 0)) {
                            this.mLevel = 4;
                        } else if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == Casing_Helper.sCaseCore1)
                                && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 1)) {
                            this.mLevel = 16;
                        } else if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == Casing_Helper.sCaseCore1)
                                && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 2)) {
                            this.mLevel = 64;
                        } else if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == Casing_Helper.sCaseCore1)
                                && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 3)) {
                            this.mLevel = 256;
                        } else if ((thisController.getAirOffset(offset.x(), offset.y(), offset.z()))) {
                            this.mLevel = 1;
                        } else {
                            formationChecklist = false;
                        }
                        continue;
                    }

                    IGregTechTileEntity currentTE = thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
                    if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
                            && !super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)
                            && !super.addMufflerToMachineList(currentTE, CASING_TEXTURE_ID)
                            && !super.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)
                            && !super.addOutputToMachineList(currentTE, CASING_TEXTURE_ID)) {

                        if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
                                && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == CASING_META)) {
                            minCasingAmount--;
                        } else {
                            formationChecklist = false;
                        }
                    }
                }
            }
        }

        if (this.mInputBusses.size() > 16) formationChecklist = false;
        if (this.mOutputBusses.size() > 1) formationChecklist = false;
        if (this.mEnergyHatches.size() > 4) formationChecklist = false;
        if (this.mMaintenanceHatches.size() != 1) formationChecklist = false;

        return formationChecklist;
    }

    @Override
    public int getParallel() {
        return this.mLevel;
    }

    @Override
    public void onScrewdriverRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
        super.onScrewdriverRightClick(aSide, aPlayer, aX, aY, aZ);
        if (aPlayer.isSneaking()) {
            if (aSide == getBaseMetaTileEntity().getFrontFacing()) {
                modeBuses++;
                if (modeBuses > 1) modeBuses = 0;

                GT_Utility.sendChatToPlayer(aPlayer, "Buses separated " + (modeBuses == 0 ? "on" : "off"));
            }
        }
    }

    @Override
    public boolean checkRecipe(ItemStack itemStack) {
        return impactRecipe(itemStack, mLevel, true);
    }

    /**
     * === POLLUTION ===
     */
    @Override
    public int getPollutionPerTick(ItemStack aStack) {
        switch (this.mLevel) {
            case 4: return 4 * 50;
            case 16: return 16 * 50;
            case 64: return 64 * 50;
            case 256: return 256 * 50;
            default: return 0;
        }
    }

    @Override
    public String[] getInfoData() {
        long storedEnergy = 0;
        long maxEnergy = 0;
        long pollut = 0;
        for (GT_MetaTileEntity_Hatch_Energy tHatch : mEnergyHatches) {
            if (isValidMetaTileEntity(tHatch)) {
                storedEnergy += tHatch.getBaseMetaTileEntity().getStoredEU();
                maxEnergy += tHatch.getBaseMetaTileEntity().getEUCapacity();
            }
        }
        for (GT_MetaTileEntity_Hatch_EnergyMulti tEHatch : mEnergyHatchesTT) {
            if (isValidMetaTileEntity(tEHatch)) {
                storedEnergy += tEHatch.getBaseMetaTileEntity().getStoredEU();
                maxEnergy += tEHatch.getBaseMetaTileEntity().getEUCapacity();
            }
        }
        for (GT_MetaTileEntity_Hatch_EnergyTunnel tEHatch : mEnergyTunnelsTT) {
            if (isValidMetaTileEntity(tEHatch)) {
                storedEnergy += tEHatch.getBaseMetaTileEntity().getStoredEU();
                maxEnergy += tEHatch.getBaseMetaTileEntity().getEUCapacity();
            }
        }

        return new String[]{
                "Progress: " + GREEN + mProgresstime / 20 + RESET + " s / " + mMaxProgresstime / 20 + RESET + " s",
                "Storage: " + GREEN + storedEnergy + RESET + " / " + RESET + YELLOW + maxEnergy + RESET + " EU",
                "Usage Energy: " + RED + -mEUt + RESET + " EU/t",
                "Max Voltage: " + YELLOW + getMaxInputVoltage() + RESET + " EU/t ",
                "Maintenance: " + ((super.getRepairStatus() == super.getIdealStatus()) ? GREEN + "Good " + YELLOW + mEfficiency / 100.0F + " %" + RESET : RED + "Has Problems " + mEfficiency / 100.0F + " %" + RESET),
                "Pollution: " + RED + getPollutionPerTick(null) + RESET,
                getParallel()==-1? "" : "Parallel Point: " + YELLOW + getParallel(),
        };
    }

    @Override
    public boolean isGivingInformation() {
        return true;
    }
}