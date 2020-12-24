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

import static com.impact.loader.ItemRegistery.IGlassBlock;
import static com.mojang.realmsclient.gui.ChatFormatting.*;
import static com.mojang.realmsclient.gui.ChatFormatting.YELLOW;

public class GTMTE_Brewmenter extends GT_MetaTileEntity_MultiParallelBlockBase {

    /** === SET BLOCKS STRUCTURE === */
    Block CASING = Casing_Helper.sCaseCore1;
    byte CASING_META = 12;

    /** === SET TEXTURES HATCHES AND CONTROLLER === */
    ITexture INDEX_CASE = Textures.BlockIcons.casingTexturePages[3][CASING_META];
    int CASING_TEXTURE_ID = CASING_META + 128*3;
    public static String mModed;
    /** === SET TEXTURE === */
    @Override
    public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide, final byte aFacing,
                                 final byte aColorIndex, final boolean aActive, final boolean aRedstone)  {
        return aSide == aFacing
                                ? new ITexture[]{INDEX_CASE, new GT_RenderedTexture(
                                        aActive
                                               ? Textures.BlockIcons.MP1a
                                               : Textures.BlockIcons.MP1)}
                                : new ITexture[]{INDEX_CASE};
    }

    /** === NAMED === */
    public GTMTE_Brewmenter(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }
    /** === NAMED === */
    public GTMTE_Brewmenter(String aName) {
        super(aName);
    }

    /** === META ENTITY === */
    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GTMTE_Brewmenter(this.mName);
    }

    /** === DESCRIPTION === */
    @Override
    public String[] getDescription() {
        final MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder();
        b
                .addInfo("One-block machine analog")
                .addParallelInfo(1,256)
                .addInfo("Parallel Point will upped Upgrade Casing")
                .addTypeMachine("Brewery, Fermenter")
                .addScrew()
                .addSeparatedBus()
                .addSeparator()
                .addController()
                .addEnergyHatch("Any casing")
                .addMaintenanceHatch("Any casing")
                .addInputBus("Any casing (max x3)")
                .addOutputBus("Any casing (max x1)")
                .addInputHatch("Any casing (max x3)")
                .addOutputHatch("Any casing (max x3)")
                .addMuffler("Any casing (max x1)")
                .addCasingInfo("Brewmenter Casing")
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
        return new GUI_BASE(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "MultiParallelBlockGUI.png", mModed);
    }

    /** === RECIPE MAP === */
    @Override
    public GT_Recipe.GT_Recipe_Map getRecipeMap() {
        return mMode == 0 ?  GT_Recipe.GT_Recipe_Map.sBrewingRecipes : GT_Recipe.GT_Recipe_Map.sFermentingRecipes;
    }

    private int mLevel = 0;
    public boolean checkMachine(IGregTechTileEntity thisController, ItemStack guiSlotItem) {
        TThatches();
        // Вычисляем вектор направления, в котором находится задняя поверхность контроллера
        final Vector3ic forgeDirection = new Vector3i(
                ForgeDirection.getOrientation(thisController.getBackFacing()).offsetX,
                ForgeDirection.getOrientation(thisController.getBackFacing()).offsetY,
                ForgeDirection.getOrientation(thisController.getBackFacing()).offsetZ);

        int minCasingAmount = 12; // Минимальное количество кейсов
        boolean formationChecklist = true; // Если все ок, машина собралась

        for(byte X = -2; X <= 2; X++) {
            for (byte Z = 0; Z >= -4; Z--) {
                for (byte Y = 0; Y <= 3; Y++) {

                    if (X==0 && Y==0 && Z==0) continue;

                    if ((X==-2||X==2) && (Z==0||Z==-4) ) continue;

                    if ( (Y==1||Y==2) && (((X==-1||X==1) && Z==-2) || (X==0 && (Z==-1||Z==-3))) ) continue;

                    final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Y, Z);

                    if ((X==0 && Z==-2) && (Y==1||Y==2)) {
                        if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
                                && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 0)) {
                            this.mLevel = 4;
                        } else if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
                                && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 1)) {
                            this.mLevel = 16;
                        } else if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
                                && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 2)) {
                            this.mLevel = 64;
                        } else if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
                                && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 3)) {
                            this.mLevel = 256;
                        } else if (thisController.getAirOffset(offset.x(), offset.y(), offset.z())) {
                            this.mLevel = 1;
                        } else {
                            formationChecklist = false;
                        }
                        continue;
                    }
                    if ( (Y==3) && (((X==-1||X==1) && Z==-2) || (X==0 && (Z==-1||Z==-3))) ) {
                        if ( thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == IGlassBlock){
                        } else  {
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

        setParallel(this.mLevel);

        if(this.mInputBusses.size() > 3) formationChecklist = false;
        if(this.mInputHatches.size() > 3) formationChecklist = false;
        if(this.mOutputBusses.size() > 3) formationChecklist = false;
        if(this.mOutputHatches.size() > 3) formationChecklist = false;
        if(this.mMufflerHatches.size() != 1) formationChecklist = false;
        if(this.mEnergyHatches.size() > 4) formationChecklist = false;
        if(this.mMaintenanceHatches.size() != 1) formationChecklist = false;

        return formationChecklist;
    }

    @Override
    public boolean checkRecipe(ItemStack itemStack) {
        return impactRecipe(itemStack, mLevel);
    }
    /** === POLLUTION === */
    @Override
    public int getPollutionPerTick(ItemStack aStack) {
            return 0;
    }


    public void onScrewdriverRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {

        if (aPlayer.isSneaking()) ScrewClick(aSide, aPlayer, aX, aY, aZ);
        else
        if (aSide == getBaseMetaTileEntity().getFrontFacing()) {
            mMode++;
            if (mMode > 1) mMode = 0;

            mModed = (mMode == 0 ? " Brewery " : " Fermenter ");
            GT_Utility.sendChatToPlayer(aPlayer, "Now" + EnumChatFormatting.YELLOW + mModed + EnumChatFormatting.RESET + "Mode");
        }
    }

}