package com.impact.mods.GregTech.tileentities.multi;

import com.github.technus.tectech.thing.metaTileEntity.hatch.GT_MetaTileEntity_Hatch_EnergyMulti;
import com.github.technus.tectech.thing.metaTileEntity.hatch.GT_MetaTileEntity_Hatch_EnergyTunnel;
import com.impact.mods.GregTech.blocks.Casing_Helper;
import com.impact.mods.GregTech.gui.GUI_BASE;
import com.impact.mods.GregTech.tileentities.multi.debug.GT_MetaTileEntity_MultiParallelBlockBase;
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
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

import static com.impact.mods.GregTech.enums.Texture.Icons.*;
import static com.impact.loader.ItemRegistery.SawMillBlock;
import static com.mojang.realmsclient.gui.ChatFormatting.*;
import static com.mojang.realmsclient.gui.ChatFormatting.YELLOW;
import static gregtech.api.enums.GT_Values.V;

public class GTMTE_SawMill extends GT_MetaTileEntity_MultiParallelBlockBase {

    public static String mModed;
    /**
     * === SET BLOCKS STRUCTURE ===
     */
    Block CASING = Casing_Helper.sCaseCore2;
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
                .addScrew()
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
        ItemStack[] inputs = tInputList.toArray(new ItemStack[tInputList.size()]);

        ArrayList<FluidStack> tFluidList = this.getStoredFluids();
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

        if (this.mInputBusses.size() > 1) formationChecklist = false;
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
    public void onScrewdriverRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
        super.onScrewdriverRightClick(aSide, aPlayer, aX, aY, aZ);

        mMode++;
        if (mMode > 2) mMode = 0;

        mModed = (mMode == 0 ? " Planks & Sawdust " : mMode == 1 ? " Wood Pulp & Sawdust " : " Only Sawdust ");
        GT_Utility.sendChatToPlayer(aPlayer, "Mode:" + EnumChatFormatting.GREEN + mModed);

    }
}