//package com.impact.mods.GregTech.tileentities.multi;
//
//import gregtech.api.GregTech_API;
//import gregtech.api.enums.Textures;
//import gregtech.api.gui.GT_GUIContainer_MultiMachine;
//import gregtech.api.interfaces.ITexture;
//import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
//import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
//import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase;
//import gregtech.api.objects.GT_RenderedTexture;
//import gregtech.api.util.GT_Recipe;
//import gregtech.api.util.GT_Utility;
//import net.minecraft.entity.player.InventoryPlayer;
//import net.minecraft.item.ItemStack;
//import net.minecraftforge.common.util.ForgeDirection;
//import net.minecraftforge.fluids.FluidStack;
//
//import java.util.ArrayList;
//
//public class GT_MetaTileEntity_FreezerSolidifier extends GT_MetaTileEntity_MultiBlockBase {
//
//    public GT_MetaTileEntity_FreezerSolidifier(int aID, String aName, String aNameRegional) {
//        super(aID, aName, aNameRegional);
//    }
//
//    public GT_MetaTileEntity_FreezerSolidifier(String aName) {
//        super(aName);
//    }
//
//    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
//        return new GT_MetaTileEntity_FreezerSolidifier(this.mName);
//    }
//
//    public String[] getDescription() {
//        return new String[]{
//                "Controller Block for the Freezer Solidifier",
//                "Super cools hot ingots and cells",
//                "Size(WxHxD): 3x3x3 (Hollow), Controller (Front centered)",
//                "1x Input Bus (Any casing)",
//                "1x Output Bus (Any casing)",
//                "1x Maintenance Hatch (Any casing)",
//                "1x Energy Hatch (Any casing)",
//                "Frost Proof Machine Casings for the rest (16 at least!)"};
//    }
//
//    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
//        if (aSide == aFacing) {
//            return new ITexture[]{Textures.BlockIcons.CASING_BLOCKS[17], new GT_RenderedTexture(aActive ? Textures.BlockIcons.OVERLAY_FRONT_VACUUM_FREEZER_ACTIVE : Textures.BlockIcons.OVERLAY_FRONT_VACUUM_FREEZER)};
//        }
//        return new ITexture[]{Textures.BlockIcons.CASING_BLOCKS[17]};
//    }
//
//    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
//        return new GT_GUIContainer_MultiMachine(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "VacuumFreezer.png");
//    }
//
//    public GT_Recipe.GT_Recipe_Map getRecipeMap() {
//        return GT_Recipe.GT_Recipe_Map.sFreezerSolidficationRecipes;
//    }
//
//    public boolean isCorrectMachinePart(ItemStack aStack) {
//        return true;
//    }
//
//    public boolean isFacingValid(byte aFacing) {
//        return aFacing > 1;
//    }
//
//    @Override
//    public boolean checkRecipe(ItemStack aStack) {
//        ArrayList<ItemStack> tInputList = getStoredInputs();
//        int tInputList_sS = tInputList.size();
//        for (int i = 0; i < tInputList_sS - 1; i++) {
//            for (int j = i + 1; j < tInputList_sS; j++) {
//                if (GT_Utility.areStacksEqual((ItemStack) tInputList.get(i), (ItemStack) tInputList.get(j))) {
//                    if (((ItemStack) tInputList.get(i)).stackSize >= ((ItemStack) tInputList.get(j)).stackSize) {
//                        tInputList.remove(j--);
//                        tInputList_sS = tInputList.size();
//                    } else {
//                        tInputList.remove(i--);
//                        tInputList_sS = tInputList.size();
//                        break;
//                    }
//                }
//            }
//        }
//        tInputList.add(mInventory[1]);
//        ItemStack[] inputs = tInputList.toArray(new ItemStack[tInputList.size()]);
//
//        ArrayList<FluidStack> tFluidList = getStoredFluids();
//        int tFluidList_sS = tFluidList.size();
//        for (int i = 0; i < tFluidList_sS - 1; i++) {
//            for (int j = i + 1; j < tFluidList_sS; j++) {
//                if (GT_Utility.areFluidsEqual(tFluidList.get(i), tFluidList.get(j))) {
//                    if (tFluidList.get(i).amount >= tFluidList.get(j).amount) {
//                        tFluidList.remove(j--);
//                        tFluidList_sS = tFluidList.size();
//                    } else {
//                        tFluidList.remove(i--);
//                        tFluidList_sS = tFluidList.size();
//                        break;
//                    }
//                }
//            }
//        }
//        FluidStack[] fluids = tFluidList.toArray(new FluidStack[tFluidList.size()]);
//
//        if (inputs.length > 0 || fluids.length > 0) {
//            long voltage = getMaxInputVoltage();
//            byte tier = (byte) Math.max(1, GT_Utility.getTier(voltage));
//            GT_Recipe recipe = GT_Recipe.GT_Recipe_Map.sFreezerSolidficationRecipes.findRecipe(getBaseMetaTileEntity(), false,
//                    false, gregtech.api.enums.GT_Values.V[tier], fluids, inputs);
//            if (recipe != null && recipe.isRecipeInputEqual(true, fluids, inputs)) {
//                this.mEfficiency = (10000 - (getIdealStatus() - getRepairStatus()) * 1000);
//                this.mEfficiencyIncrease = 10000;
//
//                int EUt = recipe.mEUt;
//                int maxProgresstime = recipe.mDuration;
//
//                while (EUt <= gregtech.api.enums.GT_Values.V[tier - 1] && maxProgresstime > 2) {
//                    EUt *= 4;
//                    maxProgresstime /= 4;
//                }
//                if (maxProgresstime < 2) {
//                    maxProgresstime = 2;
//                    EUt = recipe.mEUt * recipe.mDuration / 2;
//                }
//
//                this.mEUt = -EUt;
//                this.mMaxProgresstime = maxProgresstime;
//                mOutputItems = new ItemStack[recipe.mOutputs.length];
//                for (int i = 0; i < recipe.mOutputs.length; i++) {
//                    if (getBaseMetaTileEntity().getRandomNumber(10000) < recipe.getOutputChance(i)) {
//                        this.mOutputItems[i] = recipe.getOutput(i);
//                    }
//                }
//                this.mOutputFluids = recipe.mFluidOutputs;
//                this.updateSlots();
//                return true;
//            }
//        }
//        return false;
//    }
//
//    /* @Override
//     public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
//         int xDir = ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()).offsetX*2;
//         int zDir = ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()).offsetZ*2;
//         for (int i = -2; i < 3; i++) {
//             for (int j = -2; j < 3; j++) {
//                 for (int h = 0; h < 1; h++) {
//                     if ((i != -2 && i != 2) && (j != -2 && j != 2)) {
//                         if(h == 0){
//                             if (aBaseMetaTileEntity.getBlockOffset(xDir + i, h, zDir + j) != GregTech_API.sBlockCasings8) {
//                                 return false;
//                             }
//                             if (aBaseMetaTileEntity.getMetaIDOffset(xDir + i, h, zDir + j) != 5) {
//                                 return false;
//                             }
//                         }
//                     } else {
//                         if (h == 0) {
//                             IGregTechTileEntity tTileEntity = aBaseMetaTileEntity.getIGregTechTileEntityOffset(xDir + i, h, zDir + j);
//                             if ((!addMaintenanceToMachineList(tTileEntity, 17)) && (!addInputToMachineList(tTileEntity, 17)) && (!addOutputToMachineList(tTileEntity, 17))) {
//                                 if ((xDir + i != 0) || (zDir + j != 0)) {
//                                     if (aBaseMetaTileEntity.getBlockOffset(xDir + i, h, zDir + j) != GregTech_API.sBlockCasings2) {
//                                         return false;
//                                     }
//                                     if (aBaseMetaTileEntity.getMetaIDOffset(xDir + i, h, zDir + j) != 1) {
//                                         return false;
//                                     }
//                                 }
//                             }
//                         } else {
//                             if (aBaseMetaTileEntity.getBlockOffset(xDir + i, h, zDir + j) != GregTech_API.sBlockCasings2) {
//                                 return false;
//                             }
//                             if (aBaseMetaTileEntity.getMetaIDOffset(xDir + i, h, zDir + j) != 1) {
//                                 return false;
//                             }
//                         }
//                     }
//                 }
//             }
//         }
//         for (int i = -1; i < 2; i++) {
//             for (int j = -1; j < 2; j++) { {
//                 if ((xDir + i != 0) || (zDir + j != 0)) {
//                     IGregTechTileEntity tTileEntity = aBaseMetaTileEntity.getIGregTechTileEntityOffset(xDir + i, -2, zDir + j);
//                     if ((!addEnergyInputToMachineList(tTileEntity, 17))) {
//                             if (aBaseMetaTileEntity.getBlockOffset(xDir + i, 2, zDir + j) != GregTech_API.sBlockCasings2) {
//                                 return false;
//                             }
//                             if (aBaseMetaTileEntity.getMetaIDOffset(xDir + i, 2, zDir + j) != 1) {
//                                 return false;
//                             }
//                             if (aBaseMetaTileEntity.getBlockOffset(xDir + i, -2, zDir + j) != GregTech_API.sBlockCasings2) {
//                                 return false;
//                             }
//                             if (aBaseMetaTileEntity.getMetaIDOffset(xDir + i, -2, zDir + j) != 1) {
//                                 return false;
//                             }
//                         }
//                         if (aBaseMetaTileEntity.getBlockOffset(xDir + i, 1, zDir + j) != GregTech_API.sBlockCasings8) {
//                             return false;
//                         }
//                         if (aBaseMetaTileEntity.getMetaIDOffset(xDir + i, 1, zDir + j) != 5) {
//                             return false;
//                         }
//                         if (aBaseMetaTileEntity.getBlockOffset(xDir + i, -1, zDir + j) != GregTech_API.sBlockCasings8) {
//                             return false;
//                         }
//                         if (aBaseMetaTileEntity.getMetaIDOffset(xDir + i, -1, zDir + j) != 5) {
//                             return false;
//                         }
//                     }
//                 }
//             }
//         }
//         for (int i = -2; i < 3; i++) {
//             for (int j = -2; j < 3; j++) {
//                 if (Math.abs(i) == 2 || Math.abs(j) == 2) {
//                     if (aBaseMetaTileEntity.getBlockOffset(xDir + i, 1, zDir + j) != GregTech_API.sBlockCasings2) {
//                         return false;
//                     }
//                     if (aBaseMetaTileEntity.getMetaIDOffset(xDir + i, 1, zDir + j) != 1) {
//                         return false;
//                     }
//                     if (aBaseMetaTileEntity.getBlockOffset(xDir + i, -1, zDir + j) != GregTech_API.sBlockCasings2) {
//                         return false;
//                     }
//                     if (aBaseMetaTileEntity.getMetaIDOffset(xDir + i, -1, zDir + j) != 1) {
//                         return false;
//                     }
//                 }
//             }
//         }
//         return false;
//     }*/
//    @Override
//    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
//        int xDir = ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()).offsetX*2;
//        int zDir = ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()).offsetZ*2;
//        for (int i = -2; i < 3; i++) {
//            for (int j = -2; j < 3; j++) {
//                IGregTechTileEntity tTileEntity = aBaseMetaTileEntity.getIGregTechTileEntityOffset(xDir + i, 0, zDir + j);
//                if ((!addMaintenanceToMachineList(tTileEntity, 17)) && (!addInputToMachineList(tTileEntity, 17)) && (!addOutputToMachineList(tTileEntity, 17)) && (!addEnergyInputToMachineList(tTileEntity, 17))) {
//                    if (Math.abs(i)==2 || Math.abs(j)==2){
//                        if (aBaseMetaTileEntity.getBlockOffset(xDir + i, 0, zDir + j) != GregTech_API.sBlockCasings2) {
//                            return false;
//                        }
//                        if (aBaseMetaTileEntity.getMetaIDOffset(xDir + i, 0, zDir + j) != 1) {
//                            return false;
//                        }
//                    }
//                }
//            }
//        }
//        return true;
//    }
//
//
//    public int getMaxEfficiency(ItemStack aStack) {
//        return 10000;
//    }
//
//    public int getPollutionPerTick(ItemStack aStack) {
//        return 0;
//    }
//
//    public int getDamageToComponent(ItemStack aStack) {
//        return 0;
//    }
//
//    public boolean explodesOnComponentBreak(ItemStack aStack) {
//        return false;
//    }
//
//}
