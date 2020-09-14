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
import org.lwjgl.input.Keyboard;

import static com.impact.loader.ItemRegistery.IGlassBlock;

public class GTMTE_DDDPrinter extends GT_MetaTileEntity_MultiParallelBlockBase {

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
    public GTMTE_DDDPrinter(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }
    /** === NAMED === */
    public GTMTE_DDDPrinter(String aName) {
        super(aName);
    }

    /** === META ENTITY === */
    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GTMTE_DDDPrinter(this.mName);
    }

    /** === DESCRIPTION === */
    @Override
    public String[] getDescription() {
        final MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder();
        b
                .addInfo("One-block machine analog")
                .addTypeMachine("3x3 Crafting, 4x4 Crafting")
                .addScrew()
                .addSeparatedBus()
                .addSeparator()
                .addController()
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
        return new GUI_BASE(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "MultiParallelBlockGUI.png", mModed);
    }

    @Override
    public boolean checkRecipe(ItemStack itemStack) {
        return impactRecipe(itemStack);
    }

    /** === RECIPE MAP === */
    @Override
    public GT_Recipe.GT_Recipe_Map getRecipeMap() {
        return mMode == 0 ?  GT_Recipe.GT_Recipe_Map.sPrimitiveLine : GT_Recipe.GT_Recipe_Map.sBasicline;
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
                            if (thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == IGlassBlock) {
                            } else  {
                                formationChecklist = false;
                            }
                            continue;
                        }

                        if ( (X==1||X==2||X==3) && Y==1) {
                            if (thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == IGlassBlock) {
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
                            if (thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == IGlassBlock) {
                            } else  {
                                formationChecklist = false;
                            }
                            continue;
                        }

                        if ( (X==1||X==2||X==3||X==4) && Y==1) {
                            if (thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == IGlassBlock) {
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

        if(this.mInputBusses.size() > 30)  formationChecklist = false;
        if(this.mInputHatches.size() != 0)  formationChecklist = false;
        if(this.mOutputBusses.size() > 1)  formationChecklist = false;
        if(this.mOutputHatches.size() != 0)  formationChecklist = false;
        if(this.mMufflerHatches.size() != 0)  formationChecklist = false;
        if(this.mEnergyHatches.size() > 4)  formationChecklist = false;
        if(this.mMaintenanceHatches.size() != 1)  formationChecklist = false;

        return formationChecklist;
    }

    /** === POLLUTION === */
    @Override
    public int getPollutionPerTick(ItemStack aStack) {
            return 0;
    }


    public void onScrewdriverRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
        super.onScrewdriverRightClick(aSide, aPlayer, aX, aY, aZ);

        if (aPlayer.isSneaking()) ScrewClick(aSide, aPlayer, aX, aY, aZ);
        else
        if (aSide == getBaseMetaTileEntity().getFrontFacing()) {
                mMode++;
                if (mMode > 1) mMode = 0;

            mModed = (mMode == 0 ? " 3D Printer 3x3 " : mMode == 1 ? " 3D Printer 4x4 " : null);
            GT_Utility.sendChatToPlayer(aPlayer, "Now" + EnumChatFormatting.GREEN + mModed);
            checkMachine(getBaseMetaTileEntity(), getStackInSlot(0));
        }
    }
    @Override
    public int getParallel() {
        return 1;
    }
}