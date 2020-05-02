package com.impact.mods.GregTech.tileentities.multi;

import com.impact.mods.GregTech.casings.CORE_API;
import com.impact.mods.GregTech.tileentities.multi.debug.GT_MetaTileEntity_MultiParallelBlockBase;
import com.impact.mods.GregTech.tileentities.multi.gui.GUI_Wire;
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
import minetweaker.api.chat.IChatMessage;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.input.Keyboard;

import static com.impact.loader.ItemRegistery.decorateBlock;

public class GTMTE_3DPrinter extends GT_MetaTileEntity_MultiParallelBlockBase {

    private byte mMode = -1;
    public static String mModed;

    /** === SET BLOCKS STRUCTURE === */
    Block CASING = CORE_API.sCaseCore2;
    byte CASING_META = 4;

    /** === SET TEXTURES HATCHES AND CONTROLLER === */
    ITexture INDEX_CASE = Textures.BlockIcons.casingTexturePages[3][16+CASING_META];
    int CASING_TEXTURE_ID = CASING_META + 16 + 128*3;

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
    public GTMTE_3DPrinter(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }
    /** === NAMED === */
    public GTMTE_3DPrinter(String aName) {
        super(aName);
    }

    /** === META ENTITY === */
    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GTMTE_3DPrinter(this.mName);
    }

    /** === DESCRIPTION === */
    @Override
    public String[] getDescription() {
        final MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder();
        b
                .addInfo("One-block machine analog")
                //.addParallelInfo(1,256)
                //.addInfo("Parallel Point will upped Upgrade Casing")
                .addTypeMachine("3x3 Crafting, 4x4 Crafting")
                .addScrew()
                .addSeparator()
                .beginStructureBlock(3, 3, 3)
                .addController("-")
                .addParallelCase("-")
                .addEnergyHatch("Any casing")
                .addMaintenanceHatch("Any casing")
                .addInputBus("Any casing (max x30)")
                .addOutputBus("Any casing (max x1)")
                .addCasingInfo("3D Printed Casing")
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
        return new GUI_Wire(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "MultiParallelBlockGUI.png");
    }

    /** === RECIPE MAP === */
    @Override
    public GT_Recipe.GT_Recipe_Map getRecipeMap() {
        return mMode == 0 ?  GT_Recipe.GT_Recipe_Map.sPrimitiveLine : GT_Recipe.GT_Recipe_Map.sBasicline;
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

        if (mMode == 0) {
            for(byte X = 0; X <= 4; X++) {
                for (byte Y = -1; Y <= 1; Y++) {
                    for (int Z = 0; Z >= -4; Z--) {

                        if (X==0 && Z==0 && Y==0) continue;

                        final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Y, Z);


                        if ( (X==1||X==2||X==3) && (Z==-1||Z==-2||Z==-3) && Y==0) {
                            if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CORE_API.sCaseCore2)
                                    && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 5)) {
                                this.mLevel = 1;
                            } else {
                                formationChecklist = false;
                            }
                            continue;
                        }


                        if ( (X==1||X==2||X==3) && (Z==0||Z==-4) && Y==0) {
                            if (thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == decorateBlock[3]) {
                            } else  {
                                formationChecklist = false;
                            }
                            continue;
                        }

                        if ( (X==1||X==2||X==3) && Y==1) {
                            if (thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == decorateBlock[3]) {
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
                        if (mMode == 1) formationChecklist = false;
                    }
                }
            }
        } else if (mMode == 1) {
            for(byte X = 0; X <= 5; X++) {
                for (byte Y = -1; Y <= 1; Y++) {
                    for (int Z = 0; Z >= -5; Z--) {

                        if (X==0 && Z==0 && Y==0) continue;
                        final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Y, Z);



                        if ( (X==1||X==2||X==3||X==4) && (Z==-1||Z==-2||Z==-3||Z==-4) && Y==0) {
                            if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CORE_API.sCaseCore2)
                                    && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 6)) {
                                this.mLevel = 1;
                            } else {
                                formationChecklist = false;
                            }
                            continue;

                        }

                        if ( (X==1||X==2||X==3||X==4) && (Z==0||Z==-5) && Y==0) {
                            if (thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == decorateBlock[3]) {
                            } else  {
                                formationChecklist = false;
                            }
                            continue;
                        }

                        if ( (X==1||X==2||X==3||X==4) && Y==1) {
                            if (thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == decorateBlock[3]) {
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
                        if (mMode == 0) formationChecklist = false;
                    }
                }
            }
        } else {
            formationChecklist = false;
        }


        if(this.mInputBusses.size() > 30) {
            formationChecklist = false;
        }
        if(this.mInputHatches.size() != 0) {
            formationChecklist = false;
        }
        if(this.mOutputBusses.size() > 1) {
            formationChecklist = false;
        }
        if(this.mOutputHatches.size() != 0) {
            formationChecklist = false;
        }
        if(this.mMufflerHatches.size() != 0) {
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
//        if (this.mLevel == 4 ) {
//            return 4*50;
//        }
//        else if (this.mLevel == 16 ) {
//            return 16*50;
//        }
//        else if (this.mLevel == 64 ) {
//            return 64*50;
//        }
//        else if (this.mLevel == 256) {
//            return 256*50;
//        } else
            return 0;
    } //NOT USE WITHOUT MUFFLER IN STRUCTURE


    public void onScrewdriverRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
        super.onScrewdriverRightClick(aSide, aPlayer, aX, aY, aZ);
        checkMachine(getBaseMetaTileEntity(), getStackInSlot(0));
             if (mMode == -1) { mMode += 1; }
        else if (mMode ==  0) { mMode += 1; }
                                else { mMode =  0; }
        mModed = (mMode == 0 ? " 3D Printer 3x3 " : mMode == 1 ? " 3D Printer 4x4 " : null);
        GT_Utility.sendChatToPlayer(aPlayer, "Now" + EnumChatFormatting.GREEN + mModed);
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        aNBT.setByte("mMode", mMode);
        super.saveNBTData(aNBT);
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        this.mMode = aNBT.getByte("mMode");
        super.loadNBTData(aNBT);
    }

}