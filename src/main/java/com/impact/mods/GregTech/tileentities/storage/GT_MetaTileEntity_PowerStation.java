package com.impact.mods.GregTech.tileentities.storage;

import com.impact.mods.GregTech.casings.CORE_API;
import gregtech.api.enums.Textures;
import gregtech.api.gui.GT_GUIContainer_MultiMachine;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Dynamo;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Energy;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.ForgeDirection;

import static gregtech.api.enums.GT_Values.VN;

public class GT_MetaTileEntity_PowerStation extends GT_MetaTileEntity_MultiBlockBase {

    /** === SET BLOCKS STRUCTURE === */
    Block INDEX_PAGE = CORE_API.sCaseCore1;
    int INDEX_CASE_PAGE = 6;

    /** === SET TEXTURES HATCHES AND CONTROLLER === */
    ITexture INDEX_CASE = Textures.BlockIcons.casingTexturePages[3][INDEX_CASE_PAGE];
    int INDEX_CASE1 = INDEX_CASE_PAGE+(3*128);

    /** === SET BLOCKS STRUCTURE PARALLEL UPGRADE === */
    Block INDEX_PAGE_PARALLEL = CORE_API.sCaseCore1;

    public GT_MetaTileEntity_PowerStation(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public GT_MetaTileEntity_PowerStation(String aName) {
        super(aName);
    }

    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_PowerStation(this.mName);
    }

    public String[] getDescription() {
        return new String[]{
                "Controller Block for the Power Station"};
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
        if (aSide == aFacing) {
            return new ITexture[]{INDEX_CASE, new GT_RenderedTexture(aActive ? Textures.BlockIcons.MP1a : Textures.BlockIcons.MP1)};
        }
        return new ITexture[]{INDEX_CASE};
    }

    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_GUIContainer_MultiMachine(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "AdvancedProcessingArray.png");
    }


    public boolean isCorrectMachinePart(ItemStack aStack) {
        return true;

    }


    long nowStored = 0;
    @Override
    public boolean onRunningTick(ItemStack aStack) {
//        if (this.mLevel == 5000000000000000000L) {
//            nowStored = 5000000000000000000L;  // debug mod
//        } else {
//            nowStored = 0;
//        }
        int maxAmpers = 256;
        for(GT_MetaTileEntity_Hatch_Energy aHatch: mEnergyHatches){
            if(maxAmpers <= 0)
                continue;

            if (!(Capacity() <= nowStored)) {

                if ((Capacity() > (nowStored + maxAmpers >= aHatch.maxAmperesIn()
                        ? aHatch.AmpInput()
                        : aHatch.maxEUInput() * maxAmpers))

                        && aHatch.getBaseMetaTileEntity().decreaseStoredEnergyUnits(maxAmpers >= aHatch.maxAmperesIn()
                        ? aHatch.AmpInput()
                        : aHatch.maxEUInput() * maxAmpers, false)) {

                    nowStored += maxAmpers >= aHatch.maxAmperesIn()
                            ? aHatch.AmpInput()
                            : aHatch.maxEUInput() * maxAmpers;

                    maxAmpers -= maxAmpers >= aHatch.maxAmperesIn()
                            ? aHatch.maxAmperesIn()
                            : maxAmpers;
                }
            }
            else nowStored += 0;
        }
        maxAmpers = 256;
        for(GT_MetaTileEntity_Hatch_Dynamo aDynamo: mDynamoHatches) {
            if (maxAmpers <= 0)
                continue;
            if (nowStored >= 0) {

                if ((nowStored > (maxAmpers >= aDynamo.maxAmperesOut()
                        ? aDynamo.AmpOutput()
                        : aDynamo.maxEUOutput() * maxAmpers))

                        && aDynamo.getBaseMetaTileEntity().increaseStoredEnergyUnits(maxAmpers >= aDynamo.maxAmperesOut()
                        ? aDynamo.AmpOutput()
                        : aDynamo.maxEUOutput() * maxAmpers, false)) {

                    nowStored -= maxAmpers >= aDynamo.maxAmperesOut()
                            ? aDynamo.AmpOutput()
                            : aDynamo.maxEUOutput() * maxAmpers;

                    maxAmpers -= maxAmpers >= aDynamo.maxAmperesOut()
                            ? aDynamo.maxAmperesOut()
                            : maxAmpers;
                }
            }
            else nowStored -= 0;
        }
        return super.onRunningTick(aStack);
    }


    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        super.onPostTick(aBaseMetaTileEntity, aTick);
    }

    @Override
    public void onScrewdriverRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
        GT_Utility.sendChatToPlayer(aPlayer,nowStored + " / " + Capacity());
    }

    public boolean isFacingValid(byte aFacing) {
        return aFacing > 1;
    }

    public boolean checkRecipe(ItemStack aStack) {
        mEfficiencyIncrease = 10000;
        mMaxProgresstime = 20;
        mEUt = 10;
        return true;
    }


    private long mLevel = 0;
    public boolean checkMachineFunction(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        int xDir = ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()).offsetX * 3;
        int zDir = ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()).offsetZ * 3;
        this.mLevel = 0;
        byte tUsedMeta = aBaseMetaTileEntity.getMetaIDOffset(xDir+1, 3, zDir);
        switch (tUsedMeta) {
            case 0:
                this.mLevel = 50000000000L;
                break;
            case 1:
                this.mLevel = 50000000000000L;
                break;
            case 2:
                this.mLevel = 50000000000000000L;
                break;
            case 3:
                this.mLevel = 5000000000000000000L;
                break;
            default:
                return false;
        }
        for (int i = -3; i < 4; i++) {
            for (int j = -3; j < 4; j++) {
                for (int h = 0; h < 7; h++) {
                    IGregTechTileEntity tTileEntity = aBaseMetaTileEntity.getIGregTechTileEntityOffset(xDir + i, h, zDir + j);
                    if ((i != -3 && i != 3) && (j != -3 && j != 3)) {
                        if(h == 0){
                            if ((!addMaintenanceToMachineList(tTileEntity, INDEX_CASE1)) && (!addDynamoToMachineList(tTileEntity, INDEX_CASE1)) && (!addEnergyInputToMachineList(tTileEntity, INDEX_CASE1))) {
                                if (aBaseMetaTileEntity.getBlockOffset(xDir + i, h, zDir + j) != INDEX_PAGE_PARALLEL) {
                                    return false;
                                }
                                if (aBaseMetaTileEntity.getMetaIDOffset(xDir + i, h, zDir + j) != INDEX_CASE_PAGE) {
                                    return false;
                                }
                            }
                        } else if (h == 6) {
                            if ((!addDynamoToMachineList(tTileEntity, INDEX_CASE1)) && (!addEnergyInputToMachineList(tTileEntity, INDEX_CASE1))) {
                                if (aBaseMetaTileEntity.getBlockOffset(xDir + i, h, zDir + j) != INDEX_PAGE_PARALLEL) {
                                    return false;
                                }
                                if (aBaseMetaTileEntity.getMetaIDOffset(xDir + i, h, zDir + j) != INDEX_CASE_PAGE) {
                                    return false;
                                }
                            }
                        }  else {// innen air
                            if (aBaseMetaTileEntity.getBlockOffset(xDir + i, h, zDir + j) != INDEX_PAGE_PARALLEL) {
                                return false;
                            }
                            if (aBaseMetaTileEntity.getMetaIDOffset(xDir + i, h, zDir + j) != tUsedMeta) {
                                return false;
                            }
                        }
                    }  else {
                        if (h == 0) {
                            if ((!addMaintenanceToMachineList(tTileEntity, INDEX_CASE1)) && (!addDynamoToMachineList(tTileEntity, INDEX_CASE1)) && (!addEnergyInputToMachineList(tTileEntity, INDEX_CASE1))) {
                                if ((xDir + i != 0) || (zDir + j != 0)) {
                                    if (aBaseMetaTileEntity.getBlockOffset(xDir + i, h, zDir + j) != INDEX_PAGE_PARALLEL) {
                                        return false;
                                    }
                                    if (aBaseMetaTileEntity.getMetaIDOffset(xDir + i, h, zDir + j) != INDEX_CASE_PAGE) {
                                        return false;
                                    }
                                }
                            }
                        } else {
                            if ((!addDynamoToMachineList(tTileEntity, INDEX_CASE1)) && (!addEnergyInputToMachineList(tTileEntity, INDEX_CASE1))) {
                                if (aBaseMetaTileEntity.getBlockOffset(xDir + i, h, zDir + j) != INDEX_PAGE_PARALLEL) {
                                    return false;
                                }
                                if (aBaseMetaTileEntity.getMetaIDOffset(xDir + i, h, zDir + j) != INDEX_CASE_PAGE) {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }



    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack){
        boolean result= this.checkMachineFunction(aBaseMetaTileEntity,aStack);
        if (!result) this.mLevel=0;
        return result;
    }


    public long Capacity() {
        return this.mLevel;
    }


    public int getMaxEfficiency(ItemStack aStack) {
        return 10000;
    }

    public int getPollutionPerTick(ItemStack aStack) {
        return 0;
    }

    public int getDamageToComponent(ItemStack aStack) {
        return 0;
    }

    public boolean explodesOnComponentBreak(ItemStack aStack) {
        return false;
    }

    @Override
    public String[] getInfoData() {
        long input = 0, output  = 0;
        long inputV = 0, outputV  = 0;
        int iAmp = 0, oAmp = 0;

        for(GT_MetaTileEntity_Hatch_Energy tHatch : mEnergyHatches) {
            if (isValidMetaTileEntity(tHatch)) {
                input+=tHatch.getBaseMetaTileEntity().getAverageElectricInput();
                inputV=tHatch.getBaseMetaTileEntity().getInputVoltage();
                iAmp+=tHatch.getBaseMetaTileEntity().getInputAmperage();

            }
        }
        for(GT_MetaTileEntity_Hatch_Dynamo tDynamo : mDynamoHatches) {
            if (isValidMetaTileEntity(tDynamo)) {
                output+=tDynamo.getBaseMetaTileEntity().getAverageElectricOutput();
                outputV=tDynamo.getBaseMetaTileEntity().getOutputVoltage();
                oAmp+=tDynamo.getBaseMetaTileEntity().getOutputAmperage();

            }
        }
        EnumChatFormatting GREEN = EnumChatFormatting.GREEN;
        EnumChatFormatting RESET = EnumChatFormatting.RESET;
        EnumChatFormatting YELLOW = EnumChatFormatting.YELLOW;
        EnumChatFormatting RED = EnumChatFormatting.RED;

        return new String[]{
                "Storage:",
                GREEN + Long.toString(nowStored),
                "Max Storage:",
                YELLOW + Long.toString(Capacity()) + RESET +" EU",
                "I: " + GREEN + Long.toString(Math.abs(input)) + RESET + " EU/t " + YELLOW + VN[GT_Utility.getTier(inputV)] + RESET + " | " + YELLOW + iAmp + RESET + " A",
                "O: " + RED + Long.toString(Math.abs(output)) + RESET + " EU/t " + YELLOW + VN[GT_Utility.getTier(outputV)] + RESET + " | " + YELLOW + oAmp + RESET+ " A",
                "Maintenance: " + ((super.getRepairStatus() == super.getIdealStatus())
                        ? GREEN + "Good " + YELLOW + mEfficiency / 100.0F  + " %" + RESET
                        : RED + "Problems " + mEfficiency / 100.0F  + " %" + RESET)
        };
    }

}
