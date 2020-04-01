package com.impact.mods.GregTech.tileentities.multi;

import com.impact.util.Vector3i;
import com.impact.util.Vector3ic;
import gregtech.api.GregTech_API;
import gregtech.api.enums.Textures;
import gregtech.api.gui.GT_GUIContainer_MultiMachine;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.*;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;

import static gregtech.api.enums.GT_Values.V;
import static gregtech.api.enums.GT_Values.VN;

public class GTMTE_BlastSmelter extends GT_MetaTileEntity_MultiBlockBase {
    private int mHeatingCapacity = 0;
    private int controllerY;
    private Block CASING = GregTech_API.sBlockCasings5;
    private FluidStack[] pollutionFluidStacks = new FluidStack[]{};

    public GTMTE_BlastSmelter(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public GTMTE_BlastSmelter(String aName) {
        super(aName);
    }

    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GTMTE_BlastSmelter(this.mName);
    }

    public String[] getDescription() {
        return new String[]{
                "Controller Block for the Blast Smelter",
                "Size(WxHxD): 5x4x5 (Hollow), Controller (Front middle bottom)",
                "24x Heating Coils (Two middle Layers, hollow)",
                "1x Input Hatch/Bus (Any bottom layer casing)",
                "1x Output Hatch/Bus (Any bottom layer casing)",
                "1x Energy Hatch (Any bottom layer casing)",
                "1x Maintenance Hatch (Any bottom layer casing)",
                "1x Muffler Hatch (Top middle)",
                "Heat Proof Machine Casings for the rest",
                "Each 900K over the min. Heat Capacity multiplies eu/t by 0.95",
                "Each 1800K over the min. Heat Capacity allows for one upgraded overclock",
                "Upgraded overclocks reduce recipe time to 25% and increase EU/t to 400%",
                "Causes " + 20 * getPollutionPerTick(null) + " Pollution per second"};
    }

    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
        if (aSide == aFacing) {
            return new ITexture[]{Textures.BlockIcons.casingTexturePages[1][51], new GT_RenderedTexture(aActive ? Textures.BlockIcons.OVERLAY_FRONT_ELECTRIC_BLAST_FURNACE_ACTIVE : Textures.BlockIcons.OVERLAY_FRONT_ELECTRIC_BLAST_FURNACE)};
        }
        return new ITexture[]{Textures.BlockIcons.casingTexturePages[1][51]};
    }

    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_GUIContainer_MultiMachine(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "ElectricBlastFurnace.png");
    }

    public GT_Recipe.GT_Recipe_Map getRecipeMap() {
        return GT_Recipe.GT_Recipe_Map.sBlastSmelterRecipes;
    }

    public boolean isCorrectMachinePart(ItemStack aStack) {
        return true;
    }

    public boolean isFacingValid(byte aFacing) {
        return aFacing > 1;
    }

    public boolean checkRecipe(ItemStack aStack) {
        for (GT_MetaTileEntity_Hatch_InputBus tBus : mInputBusses) {
            ArrayList<ItemStack> tBusItems = new ArrayList<ItemStack>();
            tBus.mRecipeMap = getRecipeMap();
            if (isValidMetaTileEntity(tBus)) {
                for (int i = tBus.getBaseMetaTileEntity().getSizeInventory() - 1; i >= 0; i--) {
                    if (tBus.getBaseMetaTileEntity().getStackInSlot(i) != null)
                        tBusItems.add(tBus.getBaseMetaTileEntity().getStackInSlot(i));
                }
            }
            ArrayList<ItemStack> tInputList = getStoredInputs();
            int tInputList_sS = tInputList.size();
            for (int i = 0; i < tInputList_sS - 1; i++) {
                for (int j = i + 1; j < tInputList_sS; j++) {
                    if (GT_Utility.areStacksEqual(tInputList.get(i), tInputList.get(j))) {
                        if (tInputList.get(i).stackSize >= tInputList.get(j).stackSize) {
                            tInputList.remove(j--);
                            tInputList_sS = tInputList.size();
                        } else {
                            tInputList.remove(i--);
                            tInputList_sS = tInputList.size();
                            break;
                        }
                    }
                }
            }
            ItemStack[] tInputs = tBusItems.toArray(new ItemStack[]{});
            ArrayList<FluidStack> tFluidList = getStoredFluids();
            int tFluidList_sS = tFluidList.size();
            for (int i = 0; i < tFluidList_sS - 1; i++) {
                for (int j = i + 1; j < tFluidList_sS; j++) {
                    if (GT_Utility.areFluidsEqual(tFluidList.get(i), tFluidList.get(j))) {
                        if (tFluidList.get(i).amount >= tFluidList.get(j).amount) {
                            tFluidList.remove(j--);
                            tFluidList_sS = tFluidList.size();
                        } else {
                            tFluidList.remove(i--);
                            tFluidList_sS = tFluidList.size();
                            break;
                        }
                    }
                }
            }
            FluidStack[] tFluids = tFluidList.toArray(new FluidStack[tFluidList.size()]);
            if (tInputList.size() > 0) {
                long tVoltage = getMaxInputVoltage();
                byte tTier = (byte) Math.max(1, GT_Utility.getTier(tVoltage));
                GT_Recipe tRecipe = GT_Recipe.GT_Recipe_Map.sBlastSmelterRecipes.findRecipe(getBaseMetaTileEntity(), false, gregtech.api.enums.GT_Values.V[tTier], tFluids, tInputs);
                if ((tRecipe != null) && (this.mHeatingCapacity >= tRecipe.mSpecialValue) && (tRecipe.isRecipeInputEqual(true, tFluids, tInputs))) {
                    this.mEfficiency = (10000 - (getIdealStatus() - getRepairStatus()) * 1000);
                    this.mEfficiencyIncrease = 10000;
                    int tHeatCapacityDivTiers = (mHeatingCapacity - tRecipe.mSpecialValue) / 900;
                    byte overclockCount = calculateOverclockednessEBF(tRecipe.mEUt, tRecipe.mDuration, tVoltage);
                    //In case recipe is too OP for that machine
                    if (mMaxProgresstime == Integer.MAX_VALUE - 1 && mEUt == Integer.MAX_VALUE - 1)
                        return false;
                    if (this.mEUt > 0) {
                        this.mEUt = (-this.mEUt);
                    }
                    if (tHeatCapacityDivTiers > 0) {
                        this.mEUt = (int) (this.mEUt * (Math.pow(0.95, tHeatCapacityDivTiers)));
                        this.mMaxProgresstime >>= Math.min(tHeatCapacityDivTiers / 2, overclockCount);//extra free overclocking if possible
                        if (this.mMaxProgresstime < 1) this.mMaxProgresstime = 1;//no eu efficiency correction
                    }
                    this.mMaxProgresstime = Math.max(1, this.mMaxProgresstime);
                    this.mOutputItems = new ItemStack[]{tRecipe.getOutput(0), tRecipe.getOutput(1)};
                    this.mOutputFluids = new FluidStack[]{tRecipe.getFluidOutput(0)};
                    updateSlots();
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Calcualtes overclocked ness using long integers
     * @param aEUt          - recipe EUt
     * @param aDuration     - recipe Duration
     */
    protected byte calculateOverclockednessEBF(int aEUt, int aDuration, long maxInputVoltage) {
        byte mTier=(byte)Math.max(0, GT_Utility.getTier(maxInputVoltage)), timesOverclocked=0;
        if(mTier==0){
            //Long time calculation
            long xMaxProgresstime = ((long)aDuration)<<1;
            if(xMaxProgresstime>Integer.MAX_VALUE-1){
                //make impossible if too long
                mEUt=Integer.MAX_VALUE-1;
                mMaxProgresstime=Integer.MAX_VALUE-1;
            }else{
                mEUt=aEUt>>2;
                mMaxProgresstime=(int)xMaxProgresstime;
            }
            //return 0;
        }else{
            //Long EUt calculation
            long xEUt=aEUt;
            //Isnt too low EUt check?
            long tempEUt = xEUt<V[1] ? V[1] : xEUt;

            mMaxProgresstime = aDuration;

            while (tempEUt <= V[mTier -1]) {
                tempEUt<<=2;//this actually controls overclocking
                //xEUt *= 4;//this is effect of everclocking
                mMaxProgresstime>>=1;//this is effect of overclocking
                xEUt = mMaxProgresstime==0 ? xEUt>>1 : xEUt<<2;//U know, if the time is less than 1 tick make the machine use less power
                timesOverclocked++;
            }
            if(xEUt>Integer.MAX_VALUE-1){
                mEUt = Integer.MAX_VALUE-1;
                mMaxProgresstime = Integer.MAX_VALUE-1;
            }else{
                mEUt = (int)xEUt;
                if(mEUt==0)
                    mEUt = 1;
                if(mMaxProgresstime==0)
                    mMaxProgresstime = 1;//set time to 1 tick
            }
        }
        return timesOverclocked;
    }

    public Vector3ic rotateOffsetVector(Vector3ic forgeDirection, int x, int y, int z) {
        final Vector3i offset = new Vector3i();

        // В любом направлении по оси Z
        if(forgeDirection.x() == 0 && forgeDirection.z() == -1) {
            offset.x = x;
            offset.y = y;
            offset.z = z;
        }
        if(forgeDirection.x() == 0 && forgeDirection.z() == 1) {
            offset.x = -x;
            offset.y = y;
            offset.z = -z;
        }
        // В любом направлении по оси X
        if(forgeDirection.x() == -1 && forgeDirection.z() == 0) {
            offset.x = z;
            offset.y = y;
            offset.z = -x;
        }
        if(forgeDirection.x() == 1 && forgeDirection.z() == 0) {
            offset.x = -z;
            offset.y = y;
            offset.z = x;
        }
        // в любом направлении по оси Y
        if(forgeDirection.y() == -1) {
            offset.x = x;
            offset.y = z;
            offset.z = y;
        }

        return offset;
    }

    public boolean checkMachine(IGregTechTileEntity thisController, ItemStack guiSlotItem) {
        // Вычисляем вектор направления, в котором находится задняя поверхность контроллера
        final Vector3ic forgeDirection = new Vector3i(
                ForgeDirection.getOrientation(thisController.getBackFacing()).offsetX,
                ForgeDirection.getOrientation(thisController.getBackFacing()).offsetY,
                ForgeDirection.getOrientation(thisController.getBackFacing()).offsetZ);

        int minCasingAmount = 12; // Минимальное количество кейсов
        boolean formationChecklist = true; // Если все ок, машина собралась

        for(byte X = -2; X <= 2; X++) {
            for(byte Z = 0; Z >= -4; Z--) {
                for( byte Y = 0; Y <= 3; Y++) {

                    if (X==0&&Y==0&&Z==0) continue;
                    if ( (Y==1||Y==2) && ((X==1&&(Z==-1||Z==-2||Z==-3)) || (X==-1&&(Z==-1||Z==-2||Z==-3)) || (X==0&&(Z==-1||Z==-2||Z==-3))) ) continue;


                    final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Y, Z);

                    if ((Y==1||Y==2)) {
                        if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
                                && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 0)) {
                            this.mHeatingCapacity = 1801;
                        } else if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
                                && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 1)) {
                            this.mHeatingCapacity = 2701;
                        } else if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
                                && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 2)) {
                            this.mHeatingCapacity = 3601;
                        } else if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
                                && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 3)) {
                            this.mHeatingCapacity = 4501;
                        } else if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
                                && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 4)) {
                            this.mHeatingCapacity = 5401;
                        } else if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
                                && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 5)) {
                            this.mHeatingCapacity = 7201;
                        } else if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
                                && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 6)) {
                            this.mHeatingCapacity = 9001;
                        } else if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
                                && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 7)) {
                            this.mHeatingCapacity = 10801;
                        } else if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
                                && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 8)) {
                            this.mHeatingCapacity = 12601;
                        } else {
                            formationChecklist = false;
                        }
                        continue;
                    }

                    IGregTechTileEntity currentTE = thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
                    if (!super.addMaintenanceToMachineList(currentTE, 179)
                            && !super.addInputToMachineList(currentTE, 179)
                            && !super.addMufflerToMachineList(currentTE, 179)
                            && !super.addEnergyInputToMachineList(currentTE, 179)
                            && !super.addOutputToMachineList(currentTE, 179)) {

                        if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == GregTech_API.sBlockCasings8)
                                && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 3)) {
                            minCasingAmount--;
                        } else {
                            formationChecklist = false;
                        }
                    }
                }
            }
        }

        if(this.mInputBusses.size() > 5) {
            formationChecklist = false;
        }
        if(this.mInputHatches.size() > 3) {
            formationChecklist = false;
        }
        if(this.mOutputBusses.size() !=0) {
            formationChecklist = false;
        }
        if(this.mOutputHatches.size() !=1) {
            formationChecklist = false;
        }
        if(this.mEnergyHatches.size() > 4) {
            formationChecklist = false;
        }
        if(this.mMaintenanceHatches.size() != 1) {
            formationChecklist = false;
        }
        this.mHeatingCapacity += 100 * (GT_Utility.getTier(getMaxInputVoltage()) - 2);
        return formationChecklist;
    }


    public int getMaxEfficiency(ItemStack aStack) {
        return 10000;
    }

    public int getPollutionPerTick(ItemStack aStack) {
        return 200;
    }

    public int getDamageToComponent(ItemStack aStack) {
        return 0;
    }

    public boolean explodesOnComponentBreak(ItemStack aStack) {
        return false;
    }

    private void replaceDeprecatedCoils(IGregTechTileEntity aBaseMetaTileEntity) {
        int xDir = ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()).offsetX;
        int zDir = ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()).offsetZ;
        int tX = aBaseMetaTileEntity.getXCoord() + xDir;
        int tY = (int) aBaseMetaTileEntity.getYCoord();
        int tZ = aBaseMetaTileEntity.getZCoord() + zDir;
        int tUsedMeta;
        for (int xPos = tX - 1; xPos <= tX + 1; xPos++) {
            for (int zPos = tZ - 1; zPos <= tZ + 1; zPos++) {
                if ((xPos == tX) && (zPos == tZ)) {
                    continue;
                }
                for (int yPos = tY + 1; yPos <= tY + 2; yPos++) {
                    tUsedMeta = aBaseMetaTileEntity.getMetaID(xPos, yPos, zPos);
                    if (tUsedMeta >= 12 && tUsedMeta <= 14 && aBaseMetaTileEntity.getBlock(xPos, yPos, zPos) == GregTech_API.sBlockCasings8) {
                        aBaseMetaTileEntity.getWorld().setBlock(xPos, yPos, zPos, GregTech_API.sBlockCasings5, tUsedMeta - 12, 4);
                    }
                }
            }
        }
    }
    @Override
    public boolean addOutput(FluidStack aLiquid) {
        if (aLiquid == null) return false;
        int targetHeight;
        FluidStack tLiquid = aLiquid.copy();
        boolean isOutputPollution = false;
        for (FluidStack pollutionFluidStack : pollutionFluidStacks) {
            if (tLiquid.isFluidEqual(pollutionFluidStack)) {
                isOutputPollution = true;
                break;
            }
        }
        if (isOutputPollution) {
            targetHeight = this.controllerY + 3;
            int pollutionReduction = 0;
            for (GT_MetaTileEntity_Hatch_Muffler tHatch : mMufflerHatches) {
                if (isValidMetaTileEntity(tHatch)) {
                    pollutionReduction = 100 - tHatch.calculatePollutionReduction(100);
                    break;
                }
            }
            tLiquid.amount = tLiquid.amount * (pollutionReduction + 5) / 100;
        } else {
            targetHeight = this.controllerY;
        }
        for (GT_MetaTileEntity_Hatch_Output tHatch : mOutputHatches) {
            if (isValidMetaTileEntity(tHatch) && GT_ModHandler.isSteam(aLiquid) ? tHatch.outputsSteam() : tHatch.outputsLiquids()) {
                if (tHatch.getBaseMetaTileEntity().getYCoord() == targetHeight) {
                    int tAmount = tHatch.fill(tLiquid, false);
                    if (tAmount >= tLiquid.amount) {
                        return tHatch.fill(tLiquid, true) >= tLiquid.amount;
                    } else if (tAmount > 0) {
                        tLiquid.amount = tLiquid.amount - tHatch.fill(tLiquid, true);
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String[] getInfoData() {
        int mPollutionReduction=0;
        for (GT_MetaTileEntity_Hatch_Muffler tHatch : mMufflerHatches) {
            if (isValidMetaTileEntity(tHatch)) {
                mPollutionReduction=Math.max(tHatch.calculatePollutionReduction(100),mPollutionReduction);
            }
        }

        long storedEnergy=0;
        long maxEnergy=0;
        for(GT_MetaTileEntity_Hatch_Energy tHatch : mEnergyHatches) {
            if (isValidMetaTileEntity(tHatch)) {
                storedEnergy+=tHatch.getBaseMetaTileEntity().getStoredEU();
                maxEnergy+=tHatch.getBaseMetaTileEntity().getEUCapacity();
            }
        }

        return new String[]{
                StatCollector.translateToLocal("GT5U.multiblock.Progress")+": " + EnumChatFormatting.GREEN + Integer.toString(mProgresstime/20) + EnumChatFormatting.RESET +" s / "+
                        EnumChatFormatting.YELLOW + Integer.toString(mMaxProgresstime/20) + EnumChatFormatting.RESET +" s",
                StatCollector.translateToLocal("GT5U.multiblock.energy")+": " +EnumChatFormatting.GREEN + Long.toString(storedEnergy) + EnumChatFormatting.RESET +" EU / "+
                        EnumChatFormatting.YELLOW + Long.toString(maxEnergy) + EnumChatFormatting.RESET +" EU",
                StatCollector.translateToLocal("GT5U.multiblock.usage")+": "+EnumChatFormatting.RED + Integer.toString(-mEUt) + EnumChatFormatting.RESET + " EU/t",
                StatCollector.translateToLocal("GT5U.multiblock.mei")+": "+EnumChatFormatting.YELLOW+Long.toString(getMaxInputVoltage())+EnumChatFormatting.RESET+" EU/t(*2A) "+StatCollector.translateToLocal("GT5U.machines.tier")+": "+
                        EnumChatFormatting.YELLOW+VN[GT_Utility.getTier(getMaxInputVoltage())]+ EnumChatFormatting.RESET,
                StatCollector.translateToLocal("GT5U.multiblock.problems")+": "+
                        EnumChatFormatting.RED+ (getIdealStatus() - getRepairStatus())+EnumChatFormatting.RESET+
                        " "+StatCollector.translateToLocal("GT5U.multiblock.efficiency")+": "+
                        EnumChatFormatting.YELLOW+Float.toString(mEfficiency / 100.0F)+EnumChatFormatting.RESET + " %",
                StatCollector.translateToLocal("GT5U.EBF.heat")+": "+
                        EnumChatFormatting.GREEN+mHeatingCapacity+EnumChatFormatting.RESET+" K",
                StatCollector.translateToLocal("GT5U.multiblock.pollution")+": "+ EnumChatFormatting.GREEN + mPollutionReduction+ EnumChatFormatting.RESET+" %"
        };
    }
}