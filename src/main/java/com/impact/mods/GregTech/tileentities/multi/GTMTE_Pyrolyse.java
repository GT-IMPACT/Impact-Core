package com.impact.mods.GregTech.tileentities.multi;

import com.impact.mods.GregTech.tileentities.multi.debug.GT_MetaTileEntity_MultiParallelBlockBase;
import com.impact.mods.GregTech.gui.GUI_BASE;
import com.impact.util.MultiBlockTooltipBuilder;
import com.impact.util.Vector3i;
import com.impact.util.Vector3ic;
import gregtech.api.GregTech_API;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_OreDictUnificator;
import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.input.Keyboard;

import static com.impact.item.Core_Items.Core_Items1;
import static com.impact.util.Utilits.isB;

public class GTMTE_Pyrolyse extends GT_MetaTileEntity_MultiParallelBlockBase {

    /**
     * === SET BLOCKS STRUCTURE ===
     */
    Block CASING = GregTech_API.sBlockCasings2;
    byte CASING_META = 0;
    /**
     * === SET TEXTURES HATCHES AND CONTROLLER ===
     */
    ITexture INDEX_CASE = Textures.BlockIcons.casingTexturePages[3][16 + CASING_META];
    int CASING_TEXTURE_ID = CASING_META + 16 + 128 * 3;

    int frameId = 4096 + Materials.Steel.mMetaItemSubID;
    int frameMeta = GregTech_API.METATILEENTITIES[frameId].getTileEntityBaseType();

    /**
     * === NAMED ===
     */
    public GTMTE_Pyrolyse(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    /**
     * === NAMED ===
     */
    public GTMTE_Pyrolyse(String aName) {
        super(aName);
    }

    /**
     * === SET TEXTURE ===
     */
    @Override
    public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide, final byte aFacing,
                                 final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
        return aSide == aFacing ?
                new ITexture[]{INDEX_CASE,
                        new GT_RenderedTexture(aActive ? Textures.BlockIcons.OVERLAY_FRONT_DISTILLATION_TOWER_ACTIVE : Textures.BlockIcons.OVERLAY_FRONT_DISTILLATION_TOWER)} :
                new ITexture[]{INDEX_CASE};
    }

    /**
     * === META ENTITY ===
     */
    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GTMTE_Pyrolyse(this.mName);
    }

    /**
     * === DESCRIPTION ===
     */
    @Override
    public String[] getDescription() {
        final MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder();
        b
                .addTypeMachine("Pyrolyse oven")
                .addInfo("Converts hydrocarbons into gases, wood tar and solid fuels")
                .addInfo("The process emits gases throughout the entire time (60s):")
                .addInfo("for 6s - CO, for 15s - H\u2082, for 30s - CH\u2084, for 45s - CO\u2082 ")
                .addInfo("and 55s - solid fuels and wood tar")
                .addPollution(100)
                .addSeparator()
                .addController()
                .addEnergyHatch("Any Solid Steel casing")
                .addMaintenanceHatch("Any Solid Steel casing")
                .addMuffler("Any Solid Steel casing")
                .addInputBus("Any Solid Steel casing (max x1)")
                .addOutputBus("Any Solid Steel casing (max x1)")
                .addOutputHatch("Any Solid Steel casing (max x1)")
                .addCasingInfo("Solid Steel Casing")
                .addOtherStructurePart("Steel Pipe Casing", "Middle line" )
                .addOtherStructurePart("Steel Frame", "Bottom angles" )
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
        return new GUI_BASE(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "MultiParallelBlockGUI.png", " Pyrolyse ");
    }

    @Override
    public boolean checkRecipe(ItemStack itemStack) {
        if (depleteInput(Core_Items1.getRecipe(39,40))) {
            this.mEfficiency = 10000;
            this.mEfficiencyIncrease = 10000;

            int EUt = 24;
            int maxProgresstime = 60 * 20;

            this.mEUt = -EUt;
            this.mMaxProgresstime = maxProgresstime;

            this.updateSlots();
            return true;
        }
        return false;
    }

    @Override
    public boolean onRunningTick(ItemStack aStack) {

        if (this.mProgresstime == 6 * 20)
            addOutput(Materials.CarbonMonoxide.getGas(72L));

        if (this.mProgresstime == 15 * 20)
            addOutput(Materials.Hydrogen.getGas(288L));

        if (this.mProgresstime == 30 * 20)
            addOutput(Materials.Methane.getGas(144L));

        if (this.mProgresstime == 45 * 20)
            addOutput(Materials.CarbonDioxide.getGas(216L));

        if (this.mProgresstime == 55 * 20) {
            addOutput(Materials.WoodTar.getFluid(1440L));
            addOutput(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Charcoal, 5));
        }

        return super.onRunningTick(aStack);
    }

    public boolean checkMachine(IGregTechTileEntity thisController, ItemStack guiSlotItem) {
        TThatches();
        // Вычисляем вектор направления, в котором находится задняя поверхность контроллера
        final Vector3ic forgeDirection = new Vector3i(
                ForgeDirection.getOrientation(thisController.getBackFacing()).offsetX,
                ForgeDirection.getOrientation(thisController.getBackFacing()).offsetY,
                ForgeDirection.getOrientation(thisController.getBackFacing()).offsetZ);

        boolean formationChecklist = true; // Если все ок, машина собралась

        for (byte X = 0; X <= 8; X++) {
            for (byte Z = 0; Z >= -2; Z--) {

                final Vector3ic offset = rotateOffsetVector(forgeDirection, X, -1, Z);


                if (isB(Z, -1) && isB(X, 4, 8)) {
                    if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
                            && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 0)) {
                    } else {
                        formationChecklist = false;
                    }
                    continue;
                }
                if ((isB(Z, 0) || isB(Z, -2)) && (isB(X, 1) || isB(X, 3) || isB(X, 5, 7))) continue;
                if (isB(Z, -1, 3)) continue;

                if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == GregTech_API.sBlockMachines)
                        && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == frameMeta)) {
                } else {
                    formationChecklist = false;
                }
            }
        }

        for (byte X = 0; X <= 8; X++) {
            for (byte Z = 0; Z >= -2; Z--) {
                if (X == 0 && Z == 0) continue;

                final Vector3ic offset = rotateOffsetVector(forgeDirection, X, 0, Z);

                if (isB(Z, -1) && isB(X, 1, 4)) {
                    if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
                            && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 13)) {
                    } else {
                        formationChecklist = false;
                    }
                    continue;
                }

                if ((isB(Z, 0) || isB(Z, -2)) && isB(X, 3)) continue;
                if (isB(Z, -1) && isB(X, 5, 7)) continue;

                IGregTechTileEntity currentTE = thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
                if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
                        && !super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)
                        && !super.addMufflerToMachineList(currentTE, CASING_TEXTURE_ID)
                        && !super.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)
                        && !super.addOutputToMachineList(currentTE, CASING_TEXTURE_ID)) {

                    if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
                            && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 0)) {
                    } else {
                        formationChecklist = false;
                    }
                }
            }
        }
//
        for (byte X = 0; X <= 8; X++) {
            for (byte Z = 0; Z >= -2; Z--) {

                final Vector3ic offset = rotateOffsetVector(forgeDirection, X, 1, Z);

                if (isB(X, 3)) continue;

                IGregTechTileEntity currentTE = thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
                if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
                        && !super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)
                        && !super.addMufflerToMachineList(currentTE, CASING_TEXTURE_ID)
                        && !super.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)
                        && !super.addOutputToMachineList(currentTE, CASING_TEXTURE_ID)) {

                    if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
                            && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 0)) {
                    } else {
                        formationChecklist = false;
                    }
                }
            }
        }

        if (this.mInputBusses.size() > 1)  formationChecklist = false;
        if (this.mInputHatches.size() > 1)  formationChecklist = false;
        if (this.mOutputHatches.size() > 1)  formationChecklist = false;
        if (this.mOutputBusses.size() > 1)  formationChecklist = false;
        if (this.mEnergyHatches.size() > 1)  formationChecklist = false;
        if (this.mMaintenanceHatches.size() != 1)  formationChecklist = false;
        if (this.mMufflerHatches.size() != 1)  formationChecklist = false;
        return formationChecklist;
    }

    @Override
    public int getParallel() {
        return 1;
    }

    /**
     * === POLLUTION ===
     */
    @Override
    public int getPollutionPerTick(ItemStack aStack) {
        return 5;
    }
}