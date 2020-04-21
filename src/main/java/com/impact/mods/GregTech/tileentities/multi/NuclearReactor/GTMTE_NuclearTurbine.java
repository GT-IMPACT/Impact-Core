package com.impact.mods.GregTech.tileentities.multi.NuclearReactor;

import com.impact.mods.GregTech.casings.CORE_API;
import com.impact.util.Vector3i;
import com.impact.util.Vector3ic;
import gregtech.api.GregTech_API;
import gregtech.api.enums.Textures;
import gregtech.api.gui.GT_GUIContainer_MultiMachine;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Dynamo;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.Collection;

import static gregtech.api.enums.GT_Values.VN;

public class GTMTE_NuclearTurbine extends GT_MetaTileEntity_MultiBlockBase {

    int INDEX_HATCH = 16;

    protected int fuelConsumption = 0;
    protected int dynamo;

    public GTMTE_NuclearTurbine(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public GTMTE_NuclearTurbine(String aName) {
        super(aName);
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
        return new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[1][aColorIndex + 1], aFacing == aSide ? aActive ? new GT_RenderedTexture(Textures.BlockIcons.LARGETURBINE_ST_ACTIVE5) : new GT_RenderedTexture(Textures.BlockIcons.LARGETURBINE_ST5) : Textures.BlockIcons.CASING_BLOCKS[57]};
    }

    public String[] getDescription() {
        return new String[]{
                "Controller Block for the Large Steam Turbine",
                "Size(WxHxD): 3x3x4 (Hollow), Controller (Front centered)",
                "1x Steam Input Hatch (Side centered)",
                "1x Maintenance Hatch (Side centered)",
                "1x Dynamo Hatch (Back centered)",
                "1x Output Hatch for Distilled Water (Side centered)",
                "Turbine Casings for the rest (24 at least!)",
                "Needs a Turbine Item (Inside controller GUI)",
                "Output depending on Rotor and fitting",
                "Use screwdriver to adjust fitting of turbine"};
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GTMTE_NuclearTurbine(mName);
    }

    @Override
    public boolean checkRecipe(ItemStack aStack) {
        long outputV  = 0, oAmp = 0;
        for(GT_MetaTileEntity_Hatch_Dynamo tDynamo : mDynamoHatches) {
            if (isValidMetaTileEntity(tDynamo)) {
                outputV=tDynamo.getBaseMetaTileEntity().getOutputVoltage();
                oAmp+=tDynamo.getBaseMetaTileEntity().getOutputAmperage();
            }
        }

        ArrayList<FluidStack> tFluids = getStoredFluids();
        Collection<GT_Recipe> tRecipeList = GT_Recipe.GT_Recipe_Map.sNukeFuels.mRecipeList;

        if(tFluids.size() > 0 && tRecipeList != null) {
            for (FluidStack hatchFluid1 : tFluids) {
                for(GT_Recipe aFuel : tRecipeList) {
                    FluidStack tLiquid;
                    if ((tLiquid = GT_Utility.getFluidForFilledItem(aFuel.getRepresentativeInput(0), true)) != null) {
                        if (hatchFluid1.isFluidEqual(tLiquid)) {
                            fuelConsumption = tLiquid.amount = (dynamo / aFuel.mSpecialValue);
                            dynamo = (outputV == 8192 ? 8192 : outputV == 32768 ? 32768 : outputV == 131072 ? 131072 : 0);
                            if (outputV < 8192 || outputV > 131072 || oAmp != 4) {
                                stopMachine();
                            }
                            if(depleteInput(tLiquid)) {
                                this.mEUt = dynamo * 4;
                                this.mProgresstime = 1;
                                this.mMaxProgresstime = 1;
                                this.mEfficiencyIncrease = 10000;

                                if (this.mEfficiency == 10000 ) { addOutput(GT_ModHandler.getSteam(fuelConsumption / 4)); } else { addOutput(GT_ModHandler.getSteam(0));}

                                //System.out.println("выход еу " + this.mEUt);
                                return true;
                            }
                        }
                    }
                }
            }
        }
        this.mEUt = 0;
        return false;
    }

    @Override
    public boolean isCorrectMachinePart(ItemStack aStack) {
        return getMaxEfficiency(aStack) > 0;
    }

    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_GUIContainer_MultiMachine(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "LargeDieselEngine.png");
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
    }

    @Override
    public int getDamageToComponent(ItemStack aStack) {
        return 1;
    }

    public int getMaxEfficiency(ItemStack aStack) {
        return 10000;
    }

    @Override
    public int getPollutionPerTick(ItemStack aStack) {
        return 0;
    }

    @Override
    public boolean explodesOnComponentBreak(ItemStack aStack) {
        return true;
    }

    @Override
    public String[] getInfoData() {
        long output = 0, outputV = 0, oAmp = 0;
        for(GT_MetaTileEntity_Hatch_Dynamo tDynamo : mDynamoHatches) {
            if (isValidMetaTileEntity(tDynamo)) {
                output+=tDynamo.getBaseMetaTileEntity().getAverageElectricOutput();
                outputV=tDynamo.getBaseMetaTileEntity().getOutputVoltage();
                oAmp+=tDynamo.getBaseMetaTileEntity().getOutputAmperage();
            }
        }
        return new String[]{
                "Output: " + EnumChatFormatting.GREEN + Long.toString(Math.abs(mEUt)) + EnumChatFormatting.RESET + " EU/t "
                        + EnumChatFormatting.YELLOW + VN[GT_Utility.getTier(outputV)] + EnumChatFormatting.RESET + " | "
                        + EnumChatFormatting.YELLOW + Long.toString(oAmp) + EnumChatFormatting.RESET+ " A",
                "Fuel Consuption: " +EnumChatFormatting.RED+fuelConsumption+EnumChatFormatting.RESET+" L/t",
                "Efficiency: " + EnumChatFormatting.YELLOW + (mEfficiency/100F)+" %",
        };
    }

    @Override
    public boolean isGivingInformation() {
        return true;
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

    byte CASING_TEXTURE_ID = 1;
    byte CASING_META = 0;

    Block CASING = CORE_API.sCaseCore2;

    //Block ROTOR = GregTech_API.sBlockCasings2;
    //byte ROTOR_META = 3;

    public boolean checkMachine(IGregTechTileEntity thisController, ItemStack guiSlotItem) {
        final Vector3ic forgeDirection = new Vector3i(
                ForgeDirection.getOrientation(thisController.getBackFacing()).offsetX,
                ForgeDirection.getOrientation(thisController.getBackFacing()).offsetY,
                ForgeDirection.getOrientation(thisController.getBackFacing()).offsetZ);

        int minCasingAmount = 12;

        boolean formationChecklist = true;

        for (int Z = 0; Z >= -8; Z--) {

            final Vector3ic offset = rotateOffsetVector(forgeDirection, 0, 0, Z);

            if (Z == 0) {
                continue; // Здесь контролер
            }

            if (Z == -8) {
                IGregTechTileEntity currentTE = thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());// x, y ,z
                if (!super.addDynamoToMachineList(currentTE, 16)) {
                    if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
                            && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == CASING_META)) {
                        minCasingAmount--;
                    } else {
                        formationChecklist = false;
                    }
                }
                continue;
            }

            if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == GregTech_API.sBlockCasings2)
                    && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 3)) {
                minCasingAmount--;
            } else {
                formationChecklist = false;
            }
        }

        for (int X=-1; X <= 1; X++){
            for (int Y=-1; Y <= 1; Y++){
                for (int Z = 0; Z >= -8; Z--) {

                    final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Y, Z);
                    if (!(X == 0 && Y == 0)) {

                        if (((Y == -1 && X==-1) || (Y == 1 && X==1) || (Y == -1 && X==1) || (Y == 1 && X==-1)) && !(Z==(Z==0?0:-8))) {
                            continue; //углы
                        }

                        if (Z==(Z==0?0:-8)) {
                                if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
                                        && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == CASING_META)) {
                                    minCasingAmount--;
                                } else {
                                    formationChecklist = false;
                                }
                            continue;
                        }

                        if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == GregTech_API.sBlockCasings2)
                                && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 13)) {
                            minCasingAmount--;
                        } else {
                            formationChecklist = false;
                        }

                    }
                }
            }
        }
        for (int X=-2; X <= 2; X++) {
            for (int Y = -2; Y <= 2; Y++) {
                for (int Z = 0; Z >= -8; Z--) {

                    final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Y, Z);

                    if (X==(X==-1?-1:X==0?0:1) && Y==(Y==-1?-1:Y==0?0:1)) {
                        continue;
                    }

                    if (((Y == -2 && X==-2) || (Y == 2 && X==2) || (Y == -2 && X==2) || (Y == 2 && X==-2)) && !(Z==(Z==-1?-1:-7))) {
                        continue; //углы
                    }
                    if (X==(X==-2?-2:2) && Z==(Z==-2?-2:Z==-3?-3:Z==-4?-4:Z==-5?-5:-6) && Y==0){
                        if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CORE_API.sGlassCore1)
                                && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 0)) {
                        } else {
                            formationChecklist = false;
                        }
                        continue;
                    }

                    IGregTechTileEntity currentTE = thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());// x, y ,z
                    if (!super.addInputToMachineList(currentTE, 16)
                            && !super.addOutputToMachineList(currentTE, 16)) {
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

       if(this.mInputBusses.size() != 0) {
           formationChecklist = false;
       }
       if(this.mInputHatches.size() > 5) {
           formationChecklist = false;
       }
       if(this.mOutputBusses.size() != 0) {
           formationChecklist = false;
       }
       if(this.mOutputHatches.size() != 1) {
           formationChecklist = false;
       }
       if(this.mDynamoHatches.size() != 1) {
           formationChecklist = false;
       }
       if(this.mMaintenanceHatches.size() != 0) {
           formationChecklist = false;
       }
        mWrench = true;
        mScrewdriver = true;
        mSoftHammer = true;
        mHardHammer = true;
        mSolderingTool = true;
        mCrowbar = true;
        return formationChecklist;
    }
}
