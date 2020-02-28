package com.gwppcore.GregTech.tileentities.multi;

import com.gwppcore.GregTech.casings.CORE_API;
import com.gwppcore.GregTech.tileentities.multi.debug.GT_MetaTileEntity_MultiParallelBlockBase;
import com.gwppcore.util.MultiBlockTooltipBuilder;
import com.gwppcore.util.Vector3i;
import com.gwppcore.util.Vector3ic;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import gregtech.common.gui.GT_GUIContainer_MultiParallelBlock;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.input.Keyboard;

public class GT_TileEntity_Bender extends GT_MetaTileEntity_MultiParallelBlockBase {

    private byte mFormingMode = -1;

    /** === SET TEXTURES HATCHES AND CONTROLLER === */
    ITexture INDEX_CASE = Textures.BlockIcons.casingTexturePages[3][4];
    int CASING_TEXTURE_ID = 388;
    /** === SET BLOCKS STRUCTURE === */
    Block CASING = CORE_API.sCaseCore1;
    byte CASING_META = 4;

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
    public GT_TileEntity_Bender(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }
    /** === NAMED === */
    public GT_TileEntity_Bender(String aName) {
        super(aName);
    }

    /** === META ENTITY === */
    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_TileEntity_Bender(this.mName);
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
                .addParallelCase("Middle center")
                .addEnergyHatch("Any casing")
                .addMaintenanceHatch("Any casing")
                .addInputBus("Any casing (only x1)")
                .addOutputBus("Any casing (only x1)")
                .addCasingInfo("Bending Casing", 20)
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

    /** === RECIPE MAP === */
    @Override
    public GT_Recipe.GT_Recipe_Map getRecipeMap() {
        return mFormingMode == 0 ?  GT_Recipe.GT_Recipe_Map.sPressRecipes : mFormingMode == 1 ? GT_Recipe.GT_Recipe_Map.sBenderRecipes : GT_Recipe.GT_Recipe_Map.sExtruderRecipes;
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

    private int mLevel = 0;
    public boolean checkMachine(IGregTechTileEntity thisController, ItemStack guiSlotItem) {
        // Вычисляем вектор направления, в котором находится задняя поверхность контроллера
        final Vector3ic forgeDirection = new Vector3i(
                ForgeDirection.getOrientation(thisController.getBackFacing()).offsetX,
                ForgeDirection.getOrientation(thisController.getBackFacing()).offsetY,
                ForgeDirection.getOrientation(thisController.getBackFacing()).offsetZ);

        int minCasingAmount = 12; // Минимальное количество кейсов
        boolean formationChecklist = true; // Если все ок, машина собралась

        // Фронт сайз

        // [к][к][к]  [к][][][][]
        // [к][Г][к]  [Г][][][][]
        // [к][к][к]  [к][][][][]

        for(int X = -1; X <= 1; X++) {
            for(int Y = -1; Y <= 1; Y++) {
                if(X == 0 && Y == 0) {
                    continue; // Здесь контролер
                }

                // Следущее ТЕ
                final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Y, 0);
                IGregTechTileEntity currentTE = thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());

                // Хэтчи
                if (       !super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
                        && !super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)
                        && !super.addMufflerToMachineList(currentTE, CASING_TEXTURE_ID)
                        && !super.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)
                        && !super.addOutputToMachineList(currentTE, CASING_TEXTURE_ID)) {

                    // Кейсы вместо хэтчей
                    if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
                            && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == CASING_META)) {
                        minCasingAmount--;
                    } else {
                        formationChecklist = false;
                    }
                }
            }
        }

        //Мидл сайз
        // [к][к][к]      [][к][к][к][]
        // [к][к][к]      [][к][к][к][]
        // [к][к][к]      [][к][к][к][]
        for(int X = -1; X <= 1; X++) {
            for(int Y = -1; Y <= 1; Y++) {
                for(int Z = -1; Z >= -3; Z--) {

                    final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Y, Z);

                    if(X == 0 && Y == 0) {
                        // [к][к][к]      [][ [к] ][ [к] ][ [к] ][]
                        // [к][п][к]      [][ [п] ][ [п] ][ [п] ][]
                        // [к][к][к]      [][ [к] ][ [к] ][ [к] ][]
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
                        } else {
                            formationChecklist = false;
                        }
                        continue;
                    }

                    // Следущее ТЕ
                    IGregTechTileEntity currentTE = thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());// x, y ,z

                    // Хэтчи
                    if (       !super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
                            && !super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)
                            && !super.addMufflerToMachineList(currentTE, CASING_TEXTURE_ID)
                            && !super.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)
                            && !super.addOutputToMachineList(currentTE, CASING_TEXTURE_ID)) {

                        // Кейсы вместо хэтчей
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

        //Бэк Сайз

        // [к][к][к]    [][][][][к]
        // [к][к][к]    [][][][][к]
        // [к][к][к]    [][][][][к]

        for(int X = -1; X <= 1; X++) {
            for(int Y = -1; Y <= 1; Y++) {

                final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Y, -4);
                // Следущее ТЕ
                IGregTechTileEntity currentTE = thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());// x, y ,z

                //Хэтчи
                if (       !super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
                        && !super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)
                        && !super.addMufflerToMachineList(currentTE, CASING_TEXTURE_ID)
                        && !super.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)
                        && !super.addOutputToMachineList(currentTE, CASING_TEXTURE_ID)) {
                    // Кейсы
                    if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
                            && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == CASING_META)) {
                        minCasingAmount--;
                    } else {
                        formationChecklist = false;
                    }
                }
            }
        }

        if(minCasingAmount > 0) {
            formationChecklist = false;
        }
        if(this.mInputBusses.size() > 15) {
            formationChecklist = false;
        }
        if(this.mInputHatches.size() !=0) {
            formationChecklist = false;
        }
        if(this.mOutputBusses.size() !=1) {
            formationChecklist = false;
        }
        if(this.mOutputHatches.size() !=0) {
            formationChecklist = false;
        }
        if(this.mMufflerHatches.size() != 1) {
            formationChecklist = false;
        }
        if(this.mEnergyHatches.size() != 1) {
            formationChecklist = false;
        }
        if(this.mMaintenanceHatches.size() != 1) {
            formationChecklist = false;
        }

        return formationChecklist;
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


    public void onScrewdriverRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
             if (mFormingMode == -1) { mFormingMode += 1; }
        else if (mFormingMode ==  0) { mFormingMode += 1; }
        else if (mFormingMode ==  1) { mFormingMode += 1; }
                                else { mFormingMode =  0; }

        String mMode = (mFormingMode == 0 ? " Forming Press " : mFormingMode == 1 ? " Bending " : mFormingMode == 2 ? " Extruder " : null);
        GT_Utility.sendChatToPlayer(aPlayer, "Now running in" + mMode + "Mode.");
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        aNBT.setByte("mInternalMode", mFormingMode);
        super.saveNBTData(aNBT);
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        this.mFormingMode = aNBT.getByte("mInternalMode");
        super.loadNBTData(aNBT);
    }

}