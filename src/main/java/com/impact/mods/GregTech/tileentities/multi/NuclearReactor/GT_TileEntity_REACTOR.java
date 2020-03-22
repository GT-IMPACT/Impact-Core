package com.impact.mods.GregTech.tileentities.multi.NuclearReactor;

import com.impact.mods.GregTech.GTregister.GT_Materials;
import com.impact.mods.GregTech.casings.CORE_API;
import com.impact.mods.GregTech.tileentities.multi.debug.GT_MetaTileEntity_MultiParallelBlockBase;
import com.impact.util.MultiBlockTooltipBuilder;
import gregtech.api.GregTech_API;
import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import gregtech.common.gui.GT_GUIContainer_MultiParallelBlock;
import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class GT_TileEntity_REACTOR extends GT_MetaTileEntity_MultiParallelBlockBase {

    int amountFuel = 0;
    /** === SET BLOCKS STRUCTURE === */
    Block INDEX_PAGE =  GregTech_API.sBlockCasings3;
    byte INDEX_CASE_PAGE = 12;

    /** === SET TEXTURES HATCHES AND CONTROLLER === */
    ITexture INDEX_CASE = Textures.BlockIcons.CASING_BLOCKS[12+32];
    int INDEX_CASE1 = INDEX_CASE_PAGE+(3*128);

    /** === SET BLOCKS STRUCTURE PARALLEL UPGRADE === */
    Block INDEX_PAGE_PARALLEL = CORE_API.sCaseCore1;

    /** === RECIPE MAP === */
    @Override
    public GT_Recipe.GT_Recipe_Map getRecipeMap() {
        return GT_Recipe.GT_Recipe_Map.sNuclearReactorRecipes;
    }

    /** === SET TEXTURE === */
    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
        if (aSide == aFacing) {
            return new ITexture[]{INDEX_CASE, new GT_RenderedTexture(aActive ? Textures.BlockIcons.MP1a : Textures.BlockIcons.MP1)};
        }
        return new ITexture[]{INDEX_CASE};
    }

    /** === NAMED === */
    public GT_TileEntity_REACTOR(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }
    /** === NAMED === */
    public GT_TileEntity_REACTOR(String aName) {
        super(aName);
    }

    /** === META ENTITY === */
    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_TileEntity_REACTOR(this.mName);
    }

    /** === DESCRIPTION === */
    @Override
    public String[] getDescription() {
        final MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder();
        b
                .addInfo("One-block machine analog")
                .addParallelInfo(4,256)
                .addInfo("Parallel Point will upped Upgrade Casing")
                .addPollution(200, 12800)
                .addSeparator()
                .beginStructureBlock(3, 3, 3)
                .addController("Front middle center")
                .addParallelCase("Middle сenter")
                .addEnergyHatch("Any casing")
                .addMaintenanceHatch("Any casing")
                .addInputBus("Any casing (only x1)")
                .addOutputBus("Any casing (only x1)")
                .addCasingInfo("Wiremill Casing")
                .signAndFinalize(": "+EnumChatFormatting.RED+"IMPACT");
        if(!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            return b.getInformation();
        } else {
            return b.getStructureInformation();
        }
    }

    /** === GUI === */
    @Override
    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_GUIContainer_MultiParallelBlock(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "MultiParallelBlockGUI.png", getRecipeMap().mNEIName);
    }

    @Override
    public boolean checkRecipe(ItemStack aStack) {

        ArrayList<ItemStack> tInputList = getStoredInputs();
        int tInputList_sS = tInputList.size();
        for (int i = 0; i < tInputList_sS - 1; i++) {
            for (int j = i + 1; j < tInputList_sS; j++) {
                if (GT_Utility.areStacksEqual((ItemStack) tInputList.get(i), (ItemStack) tInputList.get(j))) {
                    if (((ItemStack) tInputList.get(i)).stackSize >= ((ItemStack) tInputList.get(j)).stackSize) {
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
        tInputList.add(mInventory[1]);
        ItemStack[] inputs = tInputList.toArray(new ItemStack[tInputList.size()]);

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
        FluidStack[] fluids = tFluidList.toArray(new FluidStack[tFluidList.size()]);

        if (inputs.length > 0 || fluids.length > 0) {
            long voltage = getMaxInputVoltage();
            byte tier = (byte) Math.max(1, GT_Utility.getTier(voltage));
            GT_Recipe recipe = getRecipeMap().findRecipe(getBaseMetaTileEntity(), false,
                    false, gregtech.api.enums.GT_Values.V[tier], fluids, inputs);
            if (recipe != null && recipe.isRecipeInputEqual(true, fluids, inputs)) {
                this.mEfficiency = (10000 - (getIdealStatus() - getRepairStatus()) * 1000);
                this.mEfficiencyIncrease = 10000;
                this.amountFuel = 1*recipe.mDuration;
                this.mMaxProgresstime = recipe.mDuration;
                mOutputItems = new ItemStack[recipe.mOutputs.length];
                for (int i = 0; i < recipe.mOutputs.length; i++) {
                    if (getBaseMetaTileEntity().getRandomNumber(10000) < recipe.getOutputChance(i)) {
                        this.mOutputItems[i] = recipe.getOutput(i);
                    }
                }
                this.mOutputFluids = recipe.mFluidOutputs;
                this.updateSlots();
                return true;
            }
        }
        return false;
    }

    public boolean onRunningTick(ItemStack aStack) {
        if (this.mEfficiency > 0) {
            this.amountFuel=this.mMaxProgresstime*1;
            long amount = (((this.amountFuel * Parallel()) +160)/5);
            depleteInput(Materials.Water.getFluid(amount));
            addOutput(GT_Materials.SupercriticalSteam.getFluid(this.amountFuel * Parallel()));
        }
        return true;
    }




    /** === CHECK STRUCTURE 2 === */
    int mCoolingCasing;
    public boolean checkMachineFunction(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        int xDir = ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()).offsetX * 3;
        int zDir = ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()).offsetZ * 3;
        addInputToMachineList(aBaseMetaTileEntity.getIGregTechTileEntityOffset(xDir, 6, zDir), 44);
        this.mCoolingCasing = 0;
        byte tUsedMeta = aBaseMetaTileEntity.getMetaIDOffset(xDir-1, 1, zDir);
        switch (tUsedMeta) {
            case 0: this.mCoolingCasing = 1; break;
            case 1: this.mCoolingCasing = 2; break;
            case 2: this.mCoolingCasing = 4; break;
            case 3: this.mCoolingCasing = 8; break;
            default: return false;
        }
        // Top
       for (int i = -2; i < 3; i++) {
           for (int j = -2; j < 3; j++) {
               int x = i==-2 ? -2 : i==-1 ? -1 : i==0 ? 1 : i==2 ? 2 : 2;
               int z = j==-2 ? -2 : j==-1 ? -1 : j==0 ? 1 : j==2 ? 2 : 2;

               if (aBaseMetaTileEntity.getBlockOffset(xDir + x, 6, zDir + z) != INDEX_PAGE) return false;
               if (aBaseMetaTileEntity.getMetaIDOffset(xDir + x, 6, zDir + z) != INDEX_CASE_PAGE) return false;
           }
       }
        // Under
        for (int h = 1; h < 6; h++) {
            for (int i = -2; i <= 2; i++) {
                int j = -1;
                if (aBaseMetaTileEntity.getBlockOffset(xDir + i, h, zDir + j) != INDEX_PAGE_PARALLEL)
                    return false;
                if (aBaseMetaTileEntity.getMetaIDOffset(xDir + i, h, zDir + j) != tUsedMeta)
                    return false;
            }
            for (int i = -2; i <= 2; i++) {
                int j = 1;
                if (aBaseMetaTileEntity.getBlockOffset(xDir + i, h, zDir + j) != INDEX_PAGE_PARALLEL)
                    return false;
                if (aBaseMetaTileEntity.getMetaIDOffset(xDir + i, h, zDir + j) != tUsedMeta)
                    return false;
            }
            for (int j = -2; j <= 2; j++) {
                int i = -1;
                if (aBaseMetaTileEntity.getBlockOffset(xDir + i, h, zDir + j) != INDEX_PAGE_PARALLEL)
                    return false;
                if (aBaseMetaTileEntity.getMetaIDOffset(xDir + i, h, zDir + j) != tUsedMeta)
                    return false;
            }
            for (int j = -2; j <= 2; j++) {
                int i = 1;
                if (aBaseMetaTileEntity.getBlockOffset(xDir + i, h, zDir + j) != INDEX_PAGE_PARALLEL)
                    return false;
                if (aBaseMetaTileEntity.getMetaIDOffset(xDir + i, h, zDir + j) != tUsedMeta)
                    return false;
            }
        }
        // Walls
        for (int h = 1; h <= 6; h++) {
            for (int j = -2; j <= 2; j++) {
                int i = -3;
                if (!addOutputToMachineList(aBaseMetaTileEntity.getIGregTechTileEntityOffset(xDir + i, h, zDir + j), 44)) {
                    if (aBaseMetaTileEntity.getBlockOffset(xDir + i, h, zDir + j) != INDEX_PAGE) return false;
                    if (aBaseMetaTileEntity.getMetaIDOffset(xDir + i, h, zDir + j) != INDEX_CASE_PAGE) return false;
                }
            }
            for (int j = -2; j <= 2; j++) {
                int i = 3;
                if (!addOutputToMachineList(aBaseMetaTileEntity.getIGregTechTileEntityOffset(xDir + i, h, zDir + j), 44)) {
                    if (aBaseMetaTileEntity.getBlockOffset(xDir + i, h, zDir + j) != INDEX_PAGE) return false;
                    if (aBaseMetaTileEntity.getMetaIDOffset(xDir + i, h, zDir + j) != INDEX_CASE_PAGE) return false;
                }
            }
            for (int i = -2; i <= 2; i++) {
                int j = -3;
                if (!addOutputToMachineList(aBaseMetaTileEntity.getIGregTechTileEntityOffset(xDir + i, h, zDir + j), 44)) {
                    if (aBaseMetaTileEntity.getBlockOffset(xDir + i, h, zDir + j) != INDEX_PAGE) return false;
                    if (aBaseMetaTileEntity.getMetaIDOffset(xDir + i, h, zDir + j) != INDEX_CASE_PAGE) return false;
                }
            }
            for (int i = -2; i <= 2; i++) {
                int j = 3;
                if (!addOutputToMachineList(aBaseMetaTileEntity.getIGregTechTileEntityOffset(xDir + i, h, zDir + j), 44)) {
                    if (aBaseMetaTileEntity.getBlockOffset(xDir + i, h, zDir + j) != INDEX_PAGE) return false;
                    if (aBaseMetaTileEntity.getMetaIDOffset(xDir + i, h, zDir + j) != INDEX_CASE_PAGE) return false;
                }
            }
        }
        // Controller
        for (int i = -3; i < 4; i++) {
            for (int j = -3; j < 4; j++) {
                if (xDir + i != 0 || zDir + j != 0) {
                    IGregTechTileEntity tTileEntity = aBaseMetaTileEntity.getIGregTechTileEntityOffset(xDir + i, 0, zDir + j);
                    if ((!addMaintenanceToMachineList(tTileEntity, 44)) && (!addInputToMachineList(tTileEntity, 44))) {
                        if (aBaseMetaTileEntity.getBlockOffset(xDir + i, 0, zDir + j) != INDEX_PAGE) return false;
                        if (aBaseMetaTileEntity.getMetaIDOffset(xDir + i, 0, zDir + j) != INDEX_CASE_PAGE) return false;
                    }
                }
            }
        }
        return true;
    }


/** === CHECK === */
    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack){
        boolean result= this.checkMachineFunction(aBaseMetaTileEntity,aStack);
        if (!result) this.mCoolingCasing = 0;
        return result;
    }

    @Override
    public String[] getInfoData() {
        return new String[]{
                "Output SC Steam: " +EnumChatFormatting.GREEN + this.amountFuel * Parallel() + EnumChatFormatting.RESET+" L",
                "Input Water: " +EnumChatFormatting.RED + ((this.amountFuel * Parallel()) +160)/5 + EnumChatFormatting.RESET+" L",
                "Decay Time: " +EnumChatFormatting.GREEN +this.mProgresstime/20 +EnumChatFormatting.RESET+ " / " +EnumChatFormatting.YELLOW +this.mMaxProgresstime/20 + EnumChatFormatting.RESET + " s",
                "Cooling Coefficient: " +EnumChatFormatting.YELLOW + Parallel() + EnumChatFormatting.RESET+" L",
                "Efficiency: " + EnumChatFormatting.YELLOW + (mEfficiency/100F)+" %",
        };
    }

    /** === SET PARALLEL === */
    public int Parallel() {
        return this.mCoolingCasing;
    }

    /** === POLLUTION === */
    @Override
    public int getPollutionPerTick(ItemStack aStack) {
            return 0;
    }
}