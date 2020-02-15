package com.gwppcore.gthandler.tileentities.multi;

import com.gwppcore.gthandler.casings.GT_Container_CasingsParall;
import com.gwppcore.util.MultiBlockTooltipBuilder;
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

import static gregtech.api.enums.GT_Values.V;

public class GT_TileEntity_PlasmaArcFurnace extends GT_MetaTileEntity_MultiParallelBlockBase {

    /** === SET BLOCKS STRUCTURE === */
    Block INDEX_PAGE = GT_Container_CasingsParall.sBlockCasingsParall;
    int INDEX_CASE_PAGE = 12;

    /** === SET TEXTURES HATCHES AND CONTROLLER === */
    ITexture INDEX_CASE = Textures.BlockIcons.casingTexturePages[3][INDEX_CASE_PAGE];
    int INDEX_CASE1 = INDEX_CASE_PAGE+(3*128);

    /** === SET BLOCKS STRUCTURE PARALLEL UPGRADE === */
    Block INDEX_PAGE_PARALLEL = GT_Container_CasingsParall.sBlockCasingsParall;

    /** === RECIPE MAP === */
    @Override
    public GT_Recipe.GT_Recipe_Map getRecipeMap() {
        return GT_Recipe.GT_Recipe_Map.sPlasmaArcFurnaceRecipes;
    }
    /** === GUI === */
    @Override
    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_GUIContainer_MultiParallelBlock(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "MultiParallelBlockGUI.png", getRecipeMap().mNEIName);
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
    public GT_TileEntity_PlasmaArcFurnace(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }
    /** === NAMED === */
    public GT_TileEntity_PlasmaArcFurnace(String aName) {
        super(aName);
    }

    /** === META ENTITY === */
    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_TileEntity_PlasmaArcFurnace(this.mName);
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
                .addInputHatch("Any casing (only x1)")
                .addCasingInfo("Arc Casing", 18)
                .signAndFinalize(": "+EnumChatFormatting.RED+"IMPACT");
        if(!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            return b.getInformation();
        } else {
            return b.getStructureInformation();
        }
    }
    /** === CHECK RECIPE === */
    public boolean checkRecipe(ItemStack itemStack) {
        ArrayList<ItemStack> tInputList = this.getStoredInputs();
        ArrayList<FluidStack> tFluidList = this.getStoredFluids();
        ItemStack[] tInputs = (ItemStack[])((ItemStack[])tInputList.toArray(new ItemStack[tInputList.size()]));
        FluidStack[] tFluids = (FluidStack[])((FluidStack[])tFluidList.toArray(new FluidStack[tFluidList.size()]));
        if (tInputList.size() > 0 || tFluidList.size() > 0) {
            long tVoltage = this.getMaxInputVoltage();
            byte tTier = (byte) Math.max(1, GT_Utility.getTier(tVoltage));
            GT_Recipe tRecipe;
            tRecipe = getRecipeMap().findRecipe(this.getBaseMetaTileEntity(), false, V[tTier], tFluids, tInputs);
            if (tRecipe != null) {
                /* TODO FOR "IF" WORLD
                if (GT_Mod.gregtechproxy.mLowGravProcessing && tRecipe.mSpecialValue == -100 &&
                        !isValidForLowGravity(tRecipe, getBaseMetaTileEntity().getWorld().provider.dimensionId))
                    return false;*/
                ArrayList<ItemStack> outputItems = new ArrayList<ItemStack>();
                ArrayList<FluidStack> outputFluids = new ArrayList<FluidStack>();
                boolean found_Recipe = false;
                int processed = 0;
                long nominalV = getnominalVoltage(this);
                //int tHeatCapacityDivTiers = (this.mHeatingCapacity - tRecipe.mSpecialValue) / 900;
                //long precutRecipeVoltage = (long) (tRecipe.mEUt * Math.pow(0.95, tHeatCapacityDivTiers));
                while ((this.getStoredFluids().size() | this.getStoredInputs().size()) > 0 && processed < Parallel()) { //THIS PARALLEL
                    if (tRecipe.isRecipeInputEqual(true, tFluids, tInputs)) {
                        found_Recipe = true;
                        for (int i = 0; i < tRecipe.mOutputs.length; i++) {
                            outputItems.add(tRecipe.getOutput(i));
                        }
                        for (int i = 0; i < tRecipe.mFluidOutputs.length; i++) {
                            outputFluids.add(tRecipe.getFluidOutput(i));
                        }
                        ++processed;
                    } else
                        break;
                }
                if (found_Recipe) {
                    this.mEfficiency = 10000;
                    this.mEfficiencyIncrease = 10000;
                    long actualEUT = (long) (tRecipe.mEUt) * processed;
                    if (actualEUT > Integer.MAX_VALUE) {
                        byte divider = 0;
                        while (actualEUT > Integer.MAX_VALUE) {
                            actualEUT = actualEUT/2;
                            divider++;
                        }
                        GT_MetaTileEntity_MultiParallelBlockBase.calculateOverclockedNessMulti((int) (actualEUT / (divider * 2)), tRecipe.mDuration, 1, nominalV, this);
                    } else
                        GT_MetaTileEntity_MultiParallelBlockBase.calculateOverclockedNessMulti((int) actualEUT, tRecipe.mDuration, 1, nominalV, this);
                    //In case recipe is too OP for that machine
                    if (this.mMaxProgresstime == Integer.MAX_VALUE - 1 && this.mEUt == Integer.MAX_VALUE - 1)
                        return false;
                    if (this.mEUt > 0) {
                        this.mEUt = (-this.mEUt);
                    }
                    this.mMaxProgresstime = tRecipe.mDuration/Parallel(); //THIS PARALLEL
                    this.mOutputItems = new ItemStack[outputItems.size()];
                    this.mOutputItems = outputItems.toArray(this.mOutputItems);
                    this.mOutputFluids = new FluidStack[outputFluids.size()];
                    this.mOutputFluids = outputFluids.toArray(this.mOutputFluids);
                    this.updateSlots();
                    return true;
                }
            }
        }
        return false;
    }



    /** === CHECK STRUCTURE === */
    private int mLevel = 0;
    public boolean checkMachineFunction(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        int xDir = ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()).offsetX;
        int zDir = ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()).offsetZ;
        int casingAmount = 0;
        this.mLevel = 0;
        byte tUsedMeta = aBaseMetaTileEntity.getMetaIDOffset(xDir, 0, zDir);
        switch (tUsedMeta) {
            case 0:
                this.mLevel = 4;
                break;
            case 1:
                this.mLevel = 16;
                break;
            case 2:
                this.mLevel = 64;
                break;
            case 3:
                this.mLevel = 256;
                break;
            default:
                return false;
        }
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 1; j++) {
                if ((xDir + i != 0) || (zDir + j != 0)) {
                    if (aBaseMetaTileEntity.getBlockOffset(xDir + i, 0, zDir + j) != INDEX_PAGE_PARALLEL) {
                        return false;
                    }
                    if (aBaseMetaTileEntity.getMetaIDOffset(xDir + i, 0, zDir + j) != tUsedMeta) {
                        return false;
                    }
                }
            }
        }
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                for (int h = -1; h < 2; h++) {
                    if ((h != 0) || (((xDir + i != 0) || (zDir + j != 0)) && ((i != 0) || (j != 0)))) {
                        /** todo info hathes
                         Muffler       -   addMufflerToMachineList(tTileEntity, INDEX_CASE)
                         InputBus      -   addInputBusParallel(tTileEntity, INDEX_CASE)
                         InputHatch    -   addInputHatchParallel(tTileEntity, INDEX_CASE)
                         OutputBus     -   addOutputBusParallel(tTileEntity, INDEX_CASE)
                         OutputHatch   -   addOutputHatchParallel(tTileEntity, INDEX_CASE)
                         Maintenance   -   addMaintenanceToMachineList(tTileEntity, INDEX_CASE)
                         EnergyInput   -   addEnergyInputToMachineList(tTileEntity, INDEX_CASE)
                        */
                        IGregTechTileEntity tTileEntity = aBaseMetaTileEntity.getIGregTechTileEntityOffset(xDir + i, h, zDir + j);
                        if ((!addMaintenanceToMachineList(tTileEntity, INDEX_CASE1))
                        &&  (!addMufflerToMachineList(tTileEntity, INDEX_CASE1))
                        &&  (!addInputHatchParallel(tTileEntity, INDEX_CASE1))
                        &&  (!addInputBusParallel(tTileEntity, INDEX_CASE1))
                        &&  (!addOutputBusParallel(tTileEntity, INDEX_CASE1))
                        &&  (!addOutputHatchParallel(tTileEntity, INDEX_CASE1))
                        &&  (!addEnergyInputToMachineList(tTileEntity, INDEX_CASE1))) {
                            if (aBaseMetaTileEntity.getBlockOffset(xDir + i, h, zDir + j) != INDEX_PAGE) {
                                return false;
                            }
                            if (aBaseMetaTileEntity.getMetaIDOffset(xDir + i, h, zDir + j) != INDEX_CASE_PAGE) {
                                return false;
                            }
                            casingAmount++;
                        }
                    }
                }
            }
        }
        return casingAmount >= 18;
    }


    /** === CHECK STRUCTURE 2 === */
    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack){
        boolean result= this.checkMachineFunction(aBaseMetaTileEntity,aStack);
        if (!result) this.mLevel=0;
        return result;
    }

    /** === SET PARALLEL === */
    public int Parallel() {
        return this.mLevel;
    }

    /** === POLLUTION === */
    @Override
    public int getPollutionPerTick(ItemStack aStack) {
        if (this.mLevel == 4 ) {
            return 4*50;
        }
        else if (this.mLevel == 16 ) {
            return 16*50;
        }
        else if (this.mLevel == 64 ) {
            return 64*50;
        }
        else if (this.mLevel == 256) {
            return 256*50;
        } else
            return 0;
    } //NOT USE WITHOUT MUFFLER IN STRUCTURE

}