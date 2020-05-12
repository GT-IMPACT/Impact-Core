package com.impact.mods.GregTech.tileentities.multi;

import com.impact.mods.GregTech.casings.CORE_API;
import com.impact.mods.GregTech.tileentities.multi.debug.GT_MetaTileEntity_MultiParallelBlockBase;
import com.impact.mods.GregTech.tileentities.multi.gui.GUI_NotMultiMachine;
import com.impact.util.MultiBlockTooltipBuilder;
import com.impact.util.Vector3i;
import com.impact.util.Vector3ic;
import gregtech.api.GregTech_API;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_Recipe;
import ic2.core.init.BlocksItems;
import ic2.core.init.InternalName;
import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.input.Keyboard;

import static com.impact.util.Utilits.getFluidStack;

public class GTMTE_AdvancedVacuumFreezer extends GT_MetaTileEntity_MultiParallelBlockBase {

    /**
     * === SET BLOCKS STRUCTURE ===
     */
    Block CASING = GregTech_API.sBlockCasings2;
    byte CASING_META = 1;
    byte CASING_TEXTURE_ID = 17;
    private int mLevel = 0;

    /**
     * === NAMED ===
     */
    public GTMTE_AdvancedVacuumFreezer(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
        setRecipe();
    }

    /**
     * === NAMED ===
     */
    public GTMTE_AdvancedVacuumFreezer(String aName) {
        super(aName);
    }

    /**
     * === SET TEXTURE ===
     */
    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
        return aSide == aFacing
                ? new ITexture[]{Textures.BlockIcons.CASING_BLOCKS[CASING_TEXTURE_ID],
                new GT_RenderedTexture(aActive
                        ? Textures.BlockIcons.OVERLAY_FRONT_VACUUM_FREEZER_ACTIVE
                        : Textures.BlockIcons.OVERLAY_FRONT_VACUUM_FREEZER)}
                : new ITexture[]{Textures.BlockIcons.CASING_BLOCKS[CASING_TEXTURE_ID]};
    }

    /**
     * === META ENTITY ===
     */
    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GTMTE_AdvancedVacuumFreezer(this.mName);
    }

    /**
     * === DESCRIPTION ===
     */
    @Override
    public String[] getDescription() {
        final MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder();
        b
                .addInfo("Speed Freeez!")
                .addParallelInfo(1, 256)
                .addInfo("Parallel Point will upped Upgrade Casing")
                .addInfo("Super Coolant is required for operation: 50 per second")
                .addInfo("At the output, get Hot Сoolant: 25 per second")
                //.addPollution(200, 12800)
                .addTypeMachine("Vacuum Freezer")
                .addSeparator()
//                .beginStructureBlock(3, 3, 3)
//                .addController("-")
//                .addParallelCase("-")
                .addEnergyHatch("Any casing")
                .addMaintenanceHatch("Any casing")
                .addInputHatch("Any casing (max x1)")
                .addOutputHatch("Any casing (max x1)")
                .addInputBus("Any casing (max x8)")
                .addOutputBus("Any casing (max x1)")
                .addCasingInfo("Frost Proof Machine Casing and IC2 Coolant fluid")
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
    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GUI_NotMultiMachine(aPlayerInventory, aBaseMetaTileEntity, getLocalName(),
                "MultiParallelBlockGUI.png", " Freezing ");
    }

    /**
     * === RECIPE MAP ===
     */
    @Override
    public GT_Recipe.GT_Recipe_Map getRecipeMap() {
        return GT_Recipe.GT_Recipe_Map.sVacuumRecipes;
    }

    public void setRecipe() {
        setRecipe(true, "supercoolant", 50, "ic2hotcoolant", 25);
    }

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        super.onPostTick(aBaseMetaTileEntity, aTick);
        if (aTick % 20 == 0) {
            if (mEfficiency > 0 && depleteInput(getFluidStack("supercoolant", 50))) {
                addOutput(getFluidStack("ic2hotcoolant", 25));
            } else stopMachine();
        }
    }

    public Vector3ic rotateOffsetVector(Vector3ic forgeDirection, int x, int y, int z) {
        final Vector3i offset = new Vector3i();

        // В любом направлении по оси Z
        if (forgeDirection.x() == 0 && forgeDirection.z() == -1) {
            offset.x = x;
            offset.y = y;
            offset.z = z;
        }
        if (forgeDirection.x() == 0 && forgeDirection.z() == 1) {
            offset.x = -x;
            offset.y = y;
            offset.z = -z;
        }
        // В любом направлении по оси X
        if (forgeDirection.x() == -1 && forgeDirection.z() == 0) {
            offset.x = z;
            offset.y = y;
            offset.z = -x;
        }
        if (forgeDirection.x() == 1 && forgeDirection.z() == 0) {
            offset.x = -z;
            offset.y = y;
            offset.z = x;
        }
        // в любом направлении по оси Y
        if (forgeDirection.y() == -1) {
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

        for (byte X = -1; X <= 1; X++) {
            for (byte Z = 0; Z >= -4; Z--) {
                for (byte Y = -1; Y <= 2; Y++) {

                    if (X == 0 && Y == 0 && Z == 0) continue;

                    final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Y, Z);

                    if (X == 0 && (((Y == 0 || Y == 1 || Y == 2) && (Z == -1 || Z == -2)) || Y == 2 && Z == -3)) {
                        if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == BlocksItems.getFluidBlock(InternalName.fluidCoolant))) {
                        } else {
                            formationChecklist = false;
                        }
                        continue;
                    }

                    if (Z == -4 && (Y == 0 || Y == 1)) {
                        if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CORE_API.sCaseCore1)
                                && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 0)) {
                            this.mLevel = 4;
                        } else if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CORE_API.sCaseCore1)
                                && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 1)) {
                            this.mLevel = 16;
                        } else if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CORE_API.sCaseCore1)
                                && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 2)) {
                            this.mLevel = 64;
                        } else if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CORE_API.sCaseCore1)
                                && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 3)) {
                            this.mLevel = 256;
                        } else if ((thisController.getAirOffset(offset.x(), offset.y(), offset.z()))) {
                            this.mLevel = 1;
                        } else {
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

        for (byte Z = -1; Z >= -3; Z--) {
            for (byte Y = -1; Y <= 1; Y++) {
                final Vector3ic offset = rotateOffsetVector(forgeDirection, -2, Y, Z);

                if (Z == -1 && Y == 1) continue;

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

        for (byte Z = -1; Z >= -3; Z--) {
            for (byte Y = -1; Y <= 1; Y++) {
                final Vector3ic offset = rotateOffsetVector(forgeDirection, 2, Y, Z);

                if (Z == -1 && Y == 1) continue;

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


//        if(this.mInputBusses.size() > 16) {
//            formationChecklist = false;
//        }
//        if(this.mInputHatches.size() !=0) {
//            formationChecklist = false;
//        }
//        if(this.mOutputBusses.size() > 1) {
//            formationChecklist = false;
//        }
//        if(this.mOutputHatches.size() !=0) {
//            formationChecklist = false;
//        }
//        if(this.mEnergyHatches.size() != 1) {
//            formationChecklist = false;
//        }
//        if(this.mMaintenanceHatches.size() != 1) {
//            formationChecklist = false;
//        }
        return formationChecklist;
    }


    /**
     * === SET PARALLEL ===
     */
    public int Parallel() {
        return this.mLevel;
    }

    /**
     * === POLLUTION ===
     */
    @Override
    public int getPollutionPerTick(ItemStack aStack) {
        return 0;
    }
}