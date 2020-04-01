package com.impact.mods.GregTech.tileentities.multi;

import com.impact.mods.GregTech.casings.CORE_API;
import com.impact.mods.GregTech.tileentities.multi.debug.GT_MetaTileEntity_MultiParallelBlockBase;
import com.impact.mods.GregTech.tileentities.multi.gui.GUI_NotMultiMachine;
import com.impact.util.MultiBlockTooltipBuilder;
import com.impact.util.Vector3i;
import com.impact.util.Vector3ic;
import gregtech.api.GregTech_API;
import gregtech.api.enums.Textures;
import gregtech.api.gui.GT_GUIContainer_MultiMachine;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class GTMTE_FreezerSolidifier extends GT_MetaTileEntity_MultiParallelBlockBase {



    public GTMTE_FreezerSolidifier(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public GTMTE_FreezerSolidifier(String aName) {
        super(aName);
    }

    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GTMTE_FreezerSolidifier(this.mName);
    }

    /** === DESCRIPTION === */
    @Override
    public String[] getDescription() {
        final MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder();
        b
                .addInfo("One-block machine analog")
                .addParallelInfo(1,256)
                .addInfo("Parallel Point will upped Upgrade Casing")
                //.addPollution(200, 12800)
                .addTypeMachine("Freezer Solidification")
                .addScrew()
                .addSeparator()
                .beginStructureBlock(3, 3, 3)
                .addController("-")
                .addParallelCase("-")
                .addEnergyHatch("Any casing")
                .addMaintenanceHatch("Any casing")
                .addInputBus("Any casing (max x2)")
                .addInputHatch("Any casing (max x2)")
                .addOutputBus("Any casing (max x1)")
                .addMuffler("Any casing (max x1)")
                .addCasingInfo("Frost Proof Machine Casing")
                .signAndFinalize(": "+ EnumChatFormatting.RED+"IMPACT");
        if(!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            return b.getInformation();
        } else {
            return b.getStructureInformation();
        }
    }

    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
        return aSide == 1
                ? new ITexture[]{Textures.BlockIcons.CASING_BLOCKS[17],
                new GT_RenderedTexture(aActive
                        ? Textures.BlockIcons.OVERLAY_FRONT_VACUUM_FREEZER_ACTIVE
                        : Textures.BlockIcons.OVERLAY_FRONT_VACUUM_FREEZER)}
                : new ITexture[]{Textures.BlockIcons.CASING_BLOCKS[17]};
    }

    /** === GUI === */
    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GUI_NotMultiMachine(aPlayerInventory, aBaseMetaTileEntity, getLocalName(),
                "MultiParallelBlockGUI.png"," Freezification ");
    }

    public GT_Recipe.GT_Recipe_Map getRecipeMap() {
        return GT_Recipe.GT_Recipe_Map.sFreezerSolidficationRecipes;
    }

    public boolean isCorrectMachinePart(ItemStack aStack) {
        return true;
    }

    public boolean isFacingValid(byte aFacing) {
        return aFacing > 1;
    }

    private int mLevel = 0;
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

    public boolean checkMachine(IGregTechTileEntity thisController, ItemStack guiSlotItem) {
        // Вычисляем вектор направления, в котором находится задняя поверхность контроллера
        final Vector3ic forgeDirection = new Vector3i(
                ForgeDirection.getOrientation(thisController.getBackFacing()).offsetX,
                ForgeDirection.getOrientation(thisController.getBackFacing()).offsetY,
                ForgeDirection.getOrientation(thisController.getBackFacing()).offsetZ);

        int minCasingAmount = 12; // Минимальное количество кейсов
        boolean formationChecklist = true; // Если все ок, машина собралась

        for(byte X = -2; X <= 2; X++) {
            for(byte Z = 0; Z >= -3; Z--) {
                for( byte Y = -2; Y <= 2; Y++) {

                    if (X==0&&Y==0&&Z==0) continue;
                    if ((X==2||X==-2)&&(Y==2||Y==-2)) continue;
                    if ( (Z==-1||Z==-2) && ( ((X==2||X==-2)&&Y==0) || ((Y==2||Y==-2)&&X==0)) ) continue;

                    if ( (Z==-1||Z==-2)&& ( (X==-1&&(Y==1||Y==-1)) || (X==1&&(Y==1||Y==-1)) ) ) continue;

                    final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Z, Y);
                    if ( (Z==-1||Z==-2) && Y==0 && X==0 ) {
                        if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CORE_API.sCaseCore1)
                                && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 0)) {
                            this.mLevel = 4;
                        } else if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) ==  CORE_API.sCaseCore1)
                                && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 1)) {
                            this.mLevel = 16;
                        } else if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) ==  CORE_API.sCaseCore1)
                                && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 2)) {
                            this.mLevel = 64;
                        } else if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) ==  CORE_API.sCaseCore1)
                                && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 3)) {
                            this.mLevel = 256;
                        } else if (thisController.getAirOffset(offset.x(), offset.y(), offset.z())) {
                            this.mLevel = 1;
                        } else {
                            formationChecklist = false;
                        }
                        continue;
                    }




                    IGregTechTileEntity currentTE = thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
                    if (!super.addMaintenanceToMachineList(currentTE, 17)
                            && !super.addInputToMachineList(currentTE, 17)
                            && !super.addMufflerToMachineList(currentTE, 17)
                            && !super.addEnergyInputToMachineList(currentTE, 17)
                            && !super.addOutputToMachineList(currentTE, 17)) {

                        if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == GregTech_API.sBlockCasings2)
                                && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 1)) {
                            minCasingAmount--;
                        } else {
                            formationChecklist = false;
                        }
                    }
                }
            }
        }

       if(this.mInputBusses.size() > 2) {
           formationChecklist = false;
       }
       if(this.mInputHatches.size() !=2) {
           formationChecklist = false;
       }
       if(this.mOutputBusses.size() !=1) {
           formationChecklist = false;
       }
       if(this.mOutputHatches.size() !=0) {
           formationChecklist = false;
       }
       if(this.mEnergyHatches.size() > 2) {
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

    public int getPollutionPerTick(ItemStack aStack) {
        return 0;
    }
}