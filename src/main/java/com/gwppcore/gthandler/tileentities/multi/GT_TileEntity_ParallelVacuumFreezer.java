package com.gwppcore.gthandler.tileentities.multi;

import gregtech.api.GregTech_API;
import gregtech.api.enums.Textures;
import gregtech.api.gui.GT_GUIContainer_MultiMachine;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;

import static gregtech.api.enums.GT_Values.V;

public class GT_TileEntity_ParallelVacuumFreezer extends GT_MetaTileEntity_MultiParallelBlockBase {

    public GT_TileEntity_ParallelVacuumFreezer(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public GT_TileEntity_ParallelVacuumFreezer(String aName) {
        super(aName);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_TileEntity_ParallelVacuumFreezer(this.mName);
    }

    @Override
    public String[] getDescription() {
        return new String[]{"Controller Block for the MEGA Vacuum Freezer", "Super cools hot ingots and cells", "Size(WxHxD): 3x3x3 (Hollow), Controller (Front centered)", "1x Input Bus (Any casing)", "1x Output Bus (Any casing)", "1x Maintenance Hatch (Any casing)", "1x Energy Hatch (Any casing)", "Frost Proof Machine Casings for the rest (16 at least!)"};
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
        return aSide == aFacing ? new ITexture[]{Textures.BlockIcons.CASING_BLOCKS[17], new GT_RenderedTexture(aActive ? Textures.BlockIcons.OVERLAY_FRONT_VACUUM_FREEZER_ACTIVE : Textures.BlockIcons.OVERLAY_FRONT_VACUUM_FREEZER)} : new ITexture[]{Textures.BlockIcons.CASING_BLOCKS[17]};
    }

    @Override
    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_GUIContainer_MultiMachine(aPlayerInventory, aBaseMetaTileEntity, this.getLocalName(), "VacuumFreezer.png");
    }

    @Override
    public GT_Recipe.GT_Recipe_Map getRecipeMap() {
        return GT_Recipe.GT_Recipe_Map.sVacuumRecipes;
    }

    @Override
    public boolean isCorrectMachinePart(ItemStack aStack) {
        return true;
    }

    @Override
    public boolean isFacingValid(byte aFacing) {
        return aFacing > 1;
    }

    @Override
    public boolean checkRecipe(ItemStack itemStack) {

        ItemStack[] tInputs = this.getStoredInputs().toArray(new ItemStack[0]);
        ArrayList<ItemStack> outputItems = new ArrayList<ItemStack>();

        long tVoltage = this.getMaxInputVoltage();
        byte tTier = (byte) Math.max(1, GT_Utility.getTier(tVoltage));

        GT_Recipe tRecipe = getRecipeMap().findRecipe(this.getBaseMetaTileEntity(), false, V[tTier], null, tInputs);
        boolean found_Recipe = false;
        int processed = 0;
        long nominalV = getnominalVoltage(this);
        while (this.getStoredInputs().size() > 0 && processed < 64) { // 16 Параллель
            if (tRecipe != null && (tRecipe.mEUt * (processed + 1)) < nominalV && tRecipe.isRecipeInputEqual(true, null, tInputs)) {
                found_Recipe = true;
                for (int i = 0; i < tRecipe.mOutputs.length; i++) {
                    outputItems.add(tRecipe.getOutput(i));
                }
                ++processed;
            } else
                break;
        }

        if (found_Recipe) {
            this.mEfficiency = (10000 - (this.getIdealStatus() - this.getRepairStatus()) * 1000);
            this.mEfficiencyIncrease = 10000;
            long actualEUT = (long) (tRecipe.mEUt) * processed;
            if (actualEUT > Integer.MAX_VALUE) {
                byte divider = 0;
                while (actualEUT > Integer.MAX_VALUE) {
                    actualEUT = actualEUT / 2;
                    divider++;
                }
                GT_MetaTileEntity_MultiParallelBlockBase.calculateOverclockedNessMulti((int) (actualEUT / (divider * 2)), tRecipe.mDuration * (divider * 2), 1, nominalV, this);
            } else
                GT_MetaTileEntity_MultiParallelBlockBase.calculateOverclockedNessMulti((int) actualEUT, tRecipe.mDuration, 1, nominalV, this);
            //In case recipe is too OP for that machine
            if (this.mMaxProgresstime == Integer.MAX_VALUE - 1 && this.mEUt == Integer.MAX_VALUE - 1)
                return false;
            if (this.mEUt > 0) {
                this.mEUt = (-this.mEUt);
            }
            this.mMaxProgresstime = Math.max(1, this.mMaxProgresstime);
            this.mOutputItems = new ItemStack[outputItems.size()];
            this.mOutputItems = outputItems.toArray(this.mOutputItems);
            this.updateSlots();
            return true;
        }
        return false;
    }

    @Override
    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        int xDir = ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()).offsetX;
        int zDir = ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()).offsetZ;
        if (!aBaseMetaTileEntity.getAirOffset(xDir, 0, zDir)) {
            return false;
        } else {
            int tAmount = 0;

            for(int i = -1; i < 2; ++i) {
                for(int j = -1; j < 2; ++j) {
                    for(int h = -1; h < 2; ++h) {
                        if (h != 0 || (xDir + i != 0 || zDir + j != 0) && (i != 0 || j != 0)) {
                            IGregTechTileEntity tTileEntity = aBaseMetaTileEntity.getIGregTechTileEntityOffset(xDir + i, h, zDir + j);
                            if (!this.addMaintenanceToMachineList(tTileEntity, 17) && !this.addInputToMachineList(tTileEntity, 17) && !this.addOutputToMachineList(tTileEntity, 17) && !this.addEnergyInputToMachineList(tTileEntity, 17)) {
                                if (aBaseMetaTileEntity.getBlockOffset(xDir + i, h, zDir + j) != GregTech_API.sBlockCasings2) {
                                    return false;
                                }

                                if (aBaseMetaTileEntity.getMetaIDOffset(xDir + i, h, zDir + j) != 1) {
                                    return false;
                                }

                                ++tAmount;
                            }
                        }
                    }
                }
            }

            return tAmount >= 16;
        }
    }

    @Override
    public int getMaxEfficiency(ItemStack aStack) {
        return 10000;
    }

    @Override
    public int getPollutionPerTick(ItemStack aStack) {
        return 0;
    }

    @Override
    public int getDamageToComponent(ItemStack aStack) {
        return 0;
    }

    @Override
    public boolean explodesOnComponentBreak(ItemStack aStack) {
        return false;
    }

}