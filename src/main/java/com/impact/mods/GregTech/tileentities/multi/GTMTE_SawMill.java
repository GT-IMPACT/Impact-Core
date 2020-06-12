package com.impact.mods.GregTech.tileentities.multi;

import com.impact.mods.GregTech.casings.CORE_API;
import com.impact.mods.GregTech.tileentities.multi.debug.GT_MetaTileEntity_MultiParallelBlockBase;
import com.impact.mods.GregTech.tileentities.multi.gui.GUI_BASE;
import com.impact.util.MultiBlockTooltipBuilder;
import com.impact.util.Vector3i;
import com.impact.util.Vector3ic;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

import static com.impact.api.enums.Textures.Icons.*;
import static com.impact.loader.ItemRegistery.SawMillBlock;
import static gregtech.api.enums.GT_Values.V;

public class GTMTE_SawMill extends GT_MetaTileEntity_MultiParallelBlockBase {

    public static String mModed;
    /**
     * === SET BLOCKS STRUCTURE ===
     */
    Block CASING = CORE_API.sCaseCore2;
    byte CASING_META = 9;
    /**
     * === SET TEXTURES HATCHES AND CONTROLLER ===
     */
    ITexture INDEX_CASE = Textures.BlockIcons.casingTexturePages[3][16 + CASING_META];
    int CASING_TEXTURE_ID = CASING_META + 16 + 128 * 3;

    /**
     * === NAMED ===
     */
    public GTMTE_SawMill(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    /**
     * === NAMED ===
     */
    public GTMTE_SawMill(String aName) {
        super(aName);
    }

    /**
     * === SET TEXTURE ===
     */
    @Override
    public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide, final byte aFacing,
                                 final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
        return aSide == aBaseMetaTileEntity.getBackFacing()
                ? new ITexture[]{INDEX_CASE, new GT_RenderedTexture(aActive ? SAW_ACTIVE : SAW)}
                : aSide == aFacing
                ? new ITexture[]{INDEX_CASE, new GT_RenderedTexture(aActive ? SAW_FRONT_ACTIVE : SAW_FRONT)}
                : new ITexture[]{INDEX_CASE};
    }

    /**
     * === META ENTITY ===
     */
    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GTMTE_SawMill(this.mName);
    }

    /**
     * === DESCRIPTION ===
     */
    @Override
    public String[] getDescription() {
        final MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder();
        b
                .addInfo("Nooo! Do not saw me..")
                .addTypeMachine("Saw Mill")
                .addInfo("Left click + shift for change mode machine")
                .addSeparator()
                .addController()
                .addEnergyHatch("Any casing")
                .addInputBus("Any casing (max 1)")
                .addOutputBus("Any casing (max x1)")
                .addInputHatch("Any casing (max x1)")
                .addCasingInfo("Wooden Casing")
                .addOtherStructurePart("Saw Mill Conveyor", "Bottom middle")
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
    @Override
    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GUI_BASE(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "MultiParallelBlockGUI.png", mModed);

    }

    @Override
    public boolean checkRecipe(ItemStack itemStack) {
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
            long tVoltage = getMaxInputVoltage();
            byte tTier = (byte) Math.max(1, GT_Utility.getTier(tVoltage));
            GT_Recipe recipe = getRecipeMap().findRecipe(getBaseMetaTileEntity(), false,
                    false, V[tTier], fluids, inputs);
            if (recipe != null && recipe.isRecipeInputEqual(true, fluids, inputs)) {
                this.mEfficiency = (10000 - (getIdealStatus() - getRepairStatus()) * 1000);
                this.mEfficiencyIncrease = 10000;

                int EUt = recipe.mEUt;
                int maxProgresstime = recipe.mDuration;

                if (tTier > 0) {
                    EUt = recipe.mEUt * (1 << tTier - 1);
                    maxProgresstime = (recipe.mDuration / (1 << tTier - 1));
                }

                if (maxProgresstime < 1) {
                    maxProgresstime = 1;
                    EUt = recipe.mEUt * recipe.mDuration / 2;
                }

                this.mEUt = -EUt;
                this.mMaxProgresstime = maxProgresstime;
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


    public boolean checkMachine(IGregTechTileEntity thisController, ItemStack guiSlotItem) {
        TThatches();
        // Вычисляем вектор направления, в котором находится задняя поверхность контроллера
        final Vector3ic forgeDirection = new Vector3i(
                ForgeDirection.getOrientation(thisController.getBackFacing()).offsetX,
                ForgeDirection.getOrientation(thisController.getBackFacing()).offsetY,
                ForgeDirection.getOrientation(thisController.getBackFacing()).offsetZ);

        boolean formationChecklist = true; // Если все ок, машина собралась

        for (byte X = 0; X <= 2; X++) {
            for (byte Y = -1; Y <= 1; Y++) {
                for (byte Z = 0; Z >= -4; Z--) {

                    if (X == 0 && Z == 0 && Y == 0) continue;

                    if (X == 1 && Y == 0) continue;
                    if (X == 0 && Y == 0 && !(Z == -4)) continue;
                    if (X == 2 && Y == 0 && !(Z == 0 || Z == -4)) continue;
                    if (X == 1 && Y == 1 && !(Z == -1 || Z == -3)) continue;
                    if ((X == 0 || X == 2) && Y == -1 && !(Z == -0 || Z == -4)) continue;

                    final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Y, Z);

                    if (X == 1 && Y == -1) {
                        if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == SawMillBlock)) {
                        } else {
                            formationChecklist = false;
                        }
                        continue;
                    }

                    IGregTechTileEntity currentTE = thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
                    if (!super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)
                            && !super.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)
                            && !super.addOutputToMachineList(currentTE, CASING_TEXTURE_ID)) {
                        if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
                                && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == CASING_META)) {
                        } else {
                            formationChecklist = false;
                        }
                    }
                }
            }
        }

        if (this.mInputBusses.size() != 1) formationChecklist = false;
        if (this.mInputHatches.size() > 1) formationChecklist = false;
        if (this.mOutputBusses.size() > 1) formationChecklist = false;
        if (this.mEnergyHatches.size() != 1) formationChecklist = false;
        mWrench = true;
        mScrewdriver = true;
        mSoftHammer = true;
        mHardHammer = true;
        mSolderingTool = true;
        mCrowbar = true;
        return formationChecklist;
    }

    /**
     * === POLLUTION ===
     */
    @Override
    public int getPollutionPerTick(ItemStack aStack) {
        return 0;
    }

    /**
     * === RECIPE MAP ===
     */
    @Override
    public GT_Recipe.GT_Recipe_Map getRecipeMap() {
        return mMode == 0 ? GT_Recipe.GT_Recipe_Map.sSawMill0 : mMode == 1 ? GT_Recipe.GT_Recipe_Map.sSawMill1 : GT_Recipe.GT_Recipe_Map.sSawMill2;
    }

    @Override
    public void onLeftclick(IGregTechTileEntity aBaseMetaTileEntity, EntityPlayer aPlayer) {
        super.onLeftclick(aBaseMetaTileEntity, aPlayer);
        if (!aPlayer.isSneaking()) {
            GT_Utility.sendChatToPlayer(aPlayer, "ha-ha.. NOOB! Use SHIFT");
        } else {
            mMode++;
            if (mMode > 2) mMode = 0;

            mModed = (mMode == 0 ? " Planks & Sawdust " : mMode == 1 ? " Wood Pulp & Sawdust " : " Only Sawdust ");
            GT_Utility.sendChatToPlayer(aPlayer, "Mode:" + EnumChatFormatting.GREEN + mModed);
        }
    }
}