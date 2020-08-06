package com.impact.mods.GregTech.tileentities.storage;

import com.impact.mods.GregTech.tileentities.hatches.GTMTE_TankHatch;
import com.impact.util.MultiBlockTooltipBuilder;
import com.impact.util.MultiFluidHandler;
import com.impact.util.Vector3i;
import com.impact.util.Vector3ic;
import gregtech.api.GregTech_API;
import gregtech.api.enums.Textures;
import gregtech.api.gui.GT_GUIContainer_MultiMachine;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Output;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import org.lwjgl.input.Keyboard;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static com.impact.loader.ItemRegistery.FluidTankBlock;
import static com.impact.loader.ItemRegistery.IGlassBlock;

public class GTMTE_MultiTank extends GT_MetaTileEntity_MultiBlockBase implements IFluidHandler {

    private final HashSet<GTMTE_TankHatch> sMultiHatches = new HashSet<>();
    private final Block glassIC2 = IGlassBlock;
    private final Block CASING = GregTech_API.sBlockCasings8;
    private final Block CASING_TANK = FluidTankBlock;
    private final int CASING_TEXTURE_ID = 176;
    public MultiFluidHandler mfh;
    private int runningCost = 0;
    private int mAmountFluids;
    private boolean doVoidExcess = false;
    private byte fluidSelector = 0;


    public GTMTE_MultiTank(int aID, String aName, String aNameRegional, int aAmountFluids) {
        super(aID, aName, aNameRegional);
        this.mAmountFluids = aAmountFluids;
    }

    public GTMTE_MultiTank(String aName, int aAmountFluids) {
        super(aName);
        this.mAmountFluids = aAmountFluids;
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity var1) {
        return new GTMTE_MultiTank(super.mName, this.mAmountFluids);
    }

    @Override
    public String[] getDescription() {
        final MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder();
        if (mAmountFluids == 1) {
            b.addInfo("High-Tech fluid tank!")
                    .addInfo("Right-Click the controller with a screwdriver will turn on excess voiding.")
                    .addInfo("Fluid storage amount and running cost depends on the storage field blocks used.")
                    .addSeparator()
                    .addInfo("Note on hatch locking:")
                    .addInfo("Use an Integrated Circuit in the GUI slot to limit which fluid is output.")
                    .addSeparator()
                    .beginStructureBlock(3, 7, 3)
                    .addController()
                    .addEnergyHatch("Any top or bottom casing")
                    .addOtherStructurePart("Inner 1x5x1 tube", "Tank Storage Block")
                    .addOtherStructurePart("Outer 3x1&7x3 Casing", "Chemical Casing")
                    .addOtherStructurePart("Outer 3x5x3 glass shell", "I-Glass")
                    .addInfo("I/O Tank Hatch Instead of any casing or glass, have to touch Tank Storage Block")
                    .addInfo("I/O Tank Hatch for information and used EC2, OC systems")
                    .signAndFinalize(": " + EnumChatFormatting.YELLOW + "Kekzdealer and 4gname");
        } else {
            b.addInfo("High-Tech fluid tank that can hold up to 25 different fluids!")
                    .addInfo("Has 1/25th of the total capacity as capacity for each fluid.")
                    .addInfo("Right-Click to the controller with a screwdriver will turn on excess voiding.")
                    .addInfo("Fluid storage amount and running cost depends on the storage field blocks used.")
                    .addSeparator()
                    .addInfo("Note on hatch locking:")
                    .addInfo("Use an Integrated Circuit in the GUI slot to limit which fluid is output.")
                    .addSeparator()
                    .beginStructureBlock(5, 9, 5)
                    .addController()
                    .addEnergyHatch("Any top or bottom casing")
                    .addOtherStructurePart("Inner 3x7x3 tube", "Tank Storage Block")
                    .addOtherStructurePart("Outer 5x1&9x5 Casing", "Chemical Casing")
                    .addOtherStructurePart("Outer 5x7x5 glass shell", "I-Glass")
                    .addInfo("I/O Tank Hatch Instead of any casing or glass, have to touch Tank Storage Block")
                    .addInfo("I/O Tank Hatch for information and used EC2, OC systems")
                    .signAndFinalize(EnumChatFormatting.YELLOW + "Kekzdealer");
        }
        if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            return b.getInformation();
        } else {
            return b.getStructureInformation();
        }
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex,
                                 boolean aActive, boolean aRedstone) {
        return aSide == aFacing
                ? new ITexture[]{Textures.BlockIcons.casingTexturePages[1][48],
                new GT_RenderedTexture(aActive
                        ? Textures.BlockIcons.OVERLAY_FRONT_LARGE_CHEMICAL_REACTOR_ACTIVE
                        : Textures.BlockIcons.OVERLAY_FRONT_LARGE_CHEMICAL_REACTOR)}
                : new ITexture[]{Textures.BlockIcons.casingTexturePages[1][48]};
    }

    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_GUIContainer_MultiMachine(aPlayerInventory, aBaseMetaTileEntity, this.getLocalName(),
                "MultiblockDisplay.png");
    }

    @Override
    public boolean isCorrectMachinePart(ItemStack var1) {
        return true;
    }

    @Override
    public boolean checkRecipe(ItemStack guiSlotItem) {

        super.mEfficiency = 10000 - (super.getIdealStatus() - super.getRepairStatus()) * 1000;
        super.mEfficiencyIncrease = 10000;
        super.mEUt = runningCost;
        super.mMaxProgresstime = 10;

        if (guiSlotItem != null && guiSlotItem.getUnlocalizedName().equals("gt.integrated_circuit")) {
            this.fluidSelector = (byte) guiSlotItem.getItemDamage();
        }

        // If there are no basic I/O hatches, let multi hatches handle it and skip a lot of code!
        if (sMultiHatches.size() > 0 && super.mInputHatches.size() == 0 && super.mOutputHatches.size() == 0) {
            return true;
        }

        // Suck in fluids
        final ArrayList<FluidStack> inputHatchFluids = super.getStoredFluids();
        if (inputHatchFluids.size() > 0) {

            for (FluidStack fluidStack : inputHatchFluids) {

                final int pushed = mfh.pushFluid(fluidStack, true);
                final FluidStack toDeplete = fluidStack.copy();
                toDeplete.amount = pushed;
                super.depleteInput(toDeplete);
            }
        }

        // Push out fluids
        if (guiSlotItem != null && guiSlotItem.getUnlocalizedName().equals("gt.integrated_circuit")) {
            final FluidStack storedFluid = mfh.getFluidCopy(fluidSelector);
            // Sum available output capacity
            int possibleOutput = 0;
            for (GT_MetaTileEntity_Hatch_Output outputHatch : super.mOutputHatches) {
                if (outputHatch.isFluidLocked() && outputHatch.getLockedFluidName().equals(storedFluid.getUnlocalizedName())) {
                    possibleOutput += outputHatch.getCapacity() - outputHatch.getFluidAmount();
                } else if (outputHatch.getFluid() != null && outputHatch.getFluid().getUnlocalizedName().equals(storedFluid.getUnlocalizedName())) {
                    possibleOutput += outputHatch.getCapacity() - outputHatch.getFluidAmount();
                } else if (outputHatch.getFluid() == null) {
                    possibleOutput += outputHatch.getCapacity() - outputHatch.getFluidAmount();
                }
            }
            // Output as much as possible
            final FluidStack tempStack = storedFluid.copy();
            tempStack.amount = possibleOutput;
            tempStack.amount = mfh.pullFluid(tempStack, fluidSelector, true);
            super.addOutput(tempStack);

        } else {
            for (int i = 0; i < mfh.getDistinctFluids(); i++) {
                final FluidStack storedFluidCopy = mfh.getFluidCopy(i);
                // Calculate how much capacity all available Output Hatches offer
                for (GT_MetaTileEntity_Hatch_Output outputHatch : super.mOutputHatches) {
                    if (outputHatch.isFluidLocked() && outputHatch.getLockedFluidName().equals(storedFluidCopy.getUnlocalizedName())) {
                        storedFluidCopy.amount += outputHatch.getCapacity() - outputHatch.getFluidAmount();
                    } else if (outputHatch.getFluid() != null && outputHatch.getFluid().getUnlocalizedName().equals(storedFluidCopy.getUnlocalizedName())) {
                        storedFluidCopy.amount += outputHatch.getCapacity() - outputHatch.getFluidAmount();
                    } else if (outputHatch.getFluid() == null) {
                        storedFluidCopy.amount += outputHatch.getCapacity() - outputHatch.getFluidAmount();
                    }
                }
                // Test how much can actually be drained and drain that amount
                storedFluidCopy.amount = mfh.pullFluid(storedFluidCopy, true);
                // Add to output
                super.addOutput(storedFluidCopy);
            }
        }

        return true;
    }

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        super.onPostTick(aBaseMetaTileEntity, aTick);

        if (mfh != null) {
            mfh.setLock(!super.getBaseMetaTileEntity().isActive());
            mfh.setFluidSelector(fluidSelector);
            mfh.setDoVoidExcess(doVoidExcess);
        }
    }

    public Vector3ic rotateOffsetVector(Vector3ic forgeDirection, int x, int y, int z) {
        final Vector3i offset = new Vector3i();

        // either direction on z-axis
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
        // either direction on x-axis
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
        // either direction on y-axis
        if (forgeDirection.y() == -1) {
            offset.x = x;
            offset.y = z;
            offset.z = y;
        }

        return offset;
    }

    @Override
    public boolean checkMachine(IGregTechTileEntity thisController, ItemStack guiSlotItem) {
        sMultiHatches.clear();
        System.out.println(this.mAmountFluids);
        if (this.mAmountFluids == 1) {
            return singletank(thisController);
        } else
            return miltitank(thisController);
    }

    private boolean miltitank(IGregTechTileEntity thisController) {
        // Figure out the vector for the direction the back face of the controller is facing
        final Vector3ic forgeDirection = new Vector3i(
                ForgeDirection.getOrientation(thisController.getBackFacing()).offsetX,
                ForgeDirection.getOrientation(thisController.getBackFacing()).offsetY,
                ForgeDirection.getOrientation(thisController.getBackFacing()).offsetZ
        );
        int minCasingAmount = 20;
        boolean formationChecklist = true; // If this is still true at the end, machine is good to go :)
        float runningCostAcc = 0;
        double fluidCapacityAcc = 0;

        // Front segment
        for (int X = -2; X <= 2; X++) {
            for (int Y = -2; Y <= 2; Y++) {
                if (X == 0 && Y == 0) {
                    continue; // Skip controller
                }

                // Get next TE
                final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Y, 0);
                final IGregTechTileEntity currentTE =
                        thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());

                // Fluid hatches should touch the storage field.
                // Maintenance/Energy hatch can go anywhere
                if (X > -2 && X < 2 && Y > -2 && Y < 2) {
                    if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
                            && !super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)
                            && !super.addOutputToMachineList(currentTE, CASING_TEXTURE_ID)
                            && !super.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)
                            && !addMultiHatchToMachineList(currentTE, CASING_TEXTURE_ID)) {

                        // If it's not a hatch, is it the right casing for this machine? Check block and block meta.
                        // Also check for multi hatch
                        if (thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING) {
                            // Seems to be valid casing. Decrement counter.
                            minCasingAmount--;
                        } else {
                            formationChecklist = false;
                        }
                    }
                } else {
                    if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
                            && !super.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)) {

                        // If it's not a hatch, is it the right casing for this machine? Check block and block meta.
                        if (thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING) {
                            // Seems to be valid casing. Decrement counter.
                            minCasingAmount--;
                        } else {
                            formationChecklist = false;
                        }
                    }
                }
            }
        }

        // Middle seven long segment
        for (int X = -2; X <= 2; X++) {
            for (int Y = -2; Y <= 2; Y++) {
                for (int Z = -1; Z >= -7; Z--) {
                    final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Y, Z);
                    if (X > -2 && X < 2 && Y > -2 && Y < 2) {
                        final int meta = thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z());

                        if (thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING_TANK) {
                            switch (meta) {
                                case 0:
                                    runningCostAcc += 0.5f;
                                    fluidCapacityAcc += 6349206.3f;
                                    break;
                                case 1:
                                    runningCostAcc += 1.0f;
                                    fluidCapacityAcc += 12698412.6f;
                                    break;
                                case 2:
                                    runningCostAcc += 2.0f;
                                    fluidCapacityAcc += 25396825.3f;
                                    break;
                                case 3:
                                    runningCostAcc += 4.0f;
                                    fluidCapacityAcc += 50793650.7f;
                                    break;
                                case 4:
                                    runningCostAcc += 8.0f;
                                    fluidCapacityAcc += 101587301.5f;
                                    break;
                                case 5:
                                    runningCostAcc += 8.0f;
                                    fluidCapacityAcc += 203174603.1f;
                                    break;
                                case 6:
                                    runningCostAcc += 8.0f;
                                    fluidCapacityAcc += 406349206.3f;
                                    break;
                                case 7:
                                    runningCostAcc += 8.0f;
                                    fluidCapacityAcc += 812698412.65479f;
                                    break;
                            }
                        } else {
                            formationChecklist = false;
                        }
                        continue;
                    }

                    // Get next TE
                    final IGregTechTileEntity currentTE =
                            thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());

                    // Corner allows only glass
                    if (X == -2 && Y == -2 || X == 2 && Y == 2 || X == -2 && Y == 2 || X == 2 && Y == -2) {
                        if (!(thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == glassIC2)) {
                            formationChecklist = false;
                        }
                    } else {
                        // Tries to add TE as either of those kinds of hatches.
                        // The number is the texture index number for the texture that needs to be painted over the hatch texture (TAE for GT++)
                        if (!super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)
                                && !super.addOutputToMachineList(currentTE, CASING_TEXTURE_ID)
                                && !addMultiHatchToMachineList(currentTE, CASING_TEXTURE_ID)) {

                            // If it's not a hatch, is it the right casing for this machine? Check block and block meta.
                            // Also check for multi hatch
                            if (thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING) {
                                // Seems to be valid casing. Decrement counter.
                                minCasingAmount--;
                            } else if (!(thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == glassIC2)) {
                                formationChecklist = false;
                            }
                        }
                    }
                }
            }
        }

        // Back segment
        for (int X = -2; X <= 2; X++) {
            for (int Y = -2; Y <= 2; Y++) {
                // Get next TE
                final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Y, -8);
                final IGregTechTileEntity currentTE =
                        thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());

                // Fluid hatches should touch the storage field.
                // Maintenance/Energy hatch can go anywhere
                if (X > -2 && X < 2 && Y > -2 && Y < 2) {
                    if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
                            && !super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)
                            && !super.addOutputToMachineList(currentTE, CASING_TEXTURE_ID)
                            && !super.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)
                            && !addMultiHatchToMachineList(currentTE, CASING_TEXTURE_ID)) {

                        // If it's not a hatch, is it the right casing for this machine? Check block and block meta.
                        if (thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING) {
                            // Seems to be valid casing. Decrement counter.
                            minCasingAmount--;
                        } else {
                            formationChecklist = false;
                        }
                    }
                } else {
                    if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
                            && !super.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)) {

                        // If it's not a hatch, is it the right casing for this machine? Check block and block meta.
                        if (thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING) {
                            // Seems to be valid casing. Decrement counter.
                            minCasingAmount--;
                        } else {
                            formationChecklist = false;
                        }
                    }
                }
            }
        }

        if (this.mEnergyHatches.size() < 1) {
            formationChecklist = false;
        }

        if (this.mMaintenanceHatches.size() != 1) {
            formationChecklist = false;
        }

        if (this.mInputHatches.size() != 0) {
            formationChecklist = false;
        }

        if (this.mOutputHatches.size() != 0) {
            formationChecklist = false;
        }

        if (minCasingAmount > 0) {
            formationChecklist = false;
        }

        if (formationChecklist) {
            runningCost = Math.round(-runningCostAcc);
            // Update MultiFluidHandler in case storage cells have been changed
            final int capacityPerFluid = (int) Math.round(fluidCapacityAcc / (float) mAmountFluids);
            if (mfh == null) {
                mfh = MultiFluidHandler.newInstance(mAmountFluids, capacityPerFluid);
            } else {
                if (mfh.getCapacity() != capacityPerFluid) {
                    mfh = MultiFluidHandler.newAdjustedInstance(mfh, capacityPerFluid);
                }
            }
            for (GTMTE_TankHatch mh : sMultiHatches) {
                mh.setMultiFluidHandler(mfh);
            }
        }

        return formationChecklist;
    }

    private boolean singletank(IGregTechTileEntity thisController) {
        // Figure out the vector for the direction the back face of the controller is facing
        final Vector3ic forgeDirection = new Vector3i(
                ForgeDirection.getOrientation(thisController.getBackFacing()).offsetX,
                ForgeDirection.getOrientation(thisController.getBackFacing()).offsetY,
                ForgeDirection.getOrientation(thisController.getBackFacing()).offsetZ
        );
        int minCasingAmount = 20;
        boolean formationChecklist = true; // If this is still true at the end, machine is good to go :)
        float runningCostAcc = 0;
        double fluidCapacityAcc = 0;


        // Front segment
        for (int X = -1; X <= 1; X++) {
            for (int Y = -1; Y <= 1; Y++) {
                if (X == 0 && Y == 0) {
                    continue; // Skip controller
                }

                // Get next TE
                final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Y, 0);
                final IGregTechTileEntity currentTE =
                        thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());

                // Fluid hatches should touch the storage field.
                // Maintenance/Energy hatch can go anywhere
                if (X >= -1 && X <= 1 && Y >= -1 && Y <= 1) {
                    if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
                            && !super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)
                            && !super.addOutputToMachineList(currentTE, CASING_TEXTURE_ID)
                            && !super.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)
                            && !addMultiHatchToMachineList(currentTE, CASING_TEXTURE_ID)) {

                        // If it's not a hatch, is it the right casing for this machine? Check block and block meta.
                        // Also check for multi hatch
                        if (thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING) {
                            // Seems to be valid casing. Decrement counter.
                            minCasingAmount--;
                        } else {
                            formationChecklist = false;
                        }
                    }
                } else {
                    if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
                            && !super.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)) {

                        // If it's not a hatch, is it the right casing for this machine? Check block and block meta.
                        if (thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING) {
                            // Seems to be valid casing. Decrement counter.
                            minCasingAmount--;
                        } else {
                            formationChecklist = false;
                        }
                    }
                }
            }
        }

        // Middle seven long segment
        for (int X = -1; X <= 1; X++) {
            for (int Y = -1; Y <= 1; Y++) {
                for (int Z = -1; Z >= -5; Z--) {
                    final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Y, Z);
                    if (X == 0 && Y == 0) {
                        final int meta = thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z());

                        if (thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING_TANK) {
                            switch (meta) {
                                case 0:
                                    runningCostAcc += 1.0f;
                                    fluidCapacityAcc += 16000000f; //80kk L
                                    break;
                                case 1:
                                    runningCostAcc += 2.0f;
                                    fluidCapacityAcc += 32000000f; //160kk L
                                    break;
                                case 2:
                                    runningCostAcc += 4.0f;
                                    fluidCapacityAcc += 64000000f; //320kk L
                                    break;
                                case 3:
                                    runningCostAcc += 8.0f;
                                    fluidCapacityAcc += 128000000f; //640kk L
                                    break;
                                case 4:
                                    runningCostAcc += 16.0f;
                                    fluidCapacityAcc += 256000000f; //1.28kkk L
                                    break;
                                case 5:
                                    runningCostAcc += 32.0f;
                                    fluidCapacityAcc += 400000000f; //2kkk L
                                    break;
                            }
                        } else {

                            formationChecklist = false;
                        }
                        continue;
                    }

                    // Get next TE
                    final IGregTechTileEntity currentTE =
                            thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());

                    // Corner allows only glass
                    if (X == -1 && Y == -1 || X == 1 && Y == 1 || X == -1 && Y == 1 || X == 1 && Y == -1) {
                        if (!(thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == glassIC2)) {
                            formationChecklist = false;
                        }
                    } else {
                        // Tries to add TE as either of those kinds of hatches.
                        // The number is the texture index number for the texture that needs to be painted over the hatch texture (TAE for GT++)
                        if (!super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)
                                && !super.addOutputToMachineList(currentTE, CASING_TEXTURE_ID)
                                && !addMultiHatchToMachineList(currentTE, CASING_TEXTURE_ID)) {

                            // If it's not a hatch, is it the right casing for this machine? Check block and block meta.
                            // Also check for multi hatch
                            if (!(thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == glassIC2)) {
                                formationChecklist = false;
                            }
                        }
                    }
                }
            }
        }

        if (this.mEnergyHatches.size() < 1) {
            formationChecklist = false;
        }

        if (this.mMaintenanceHatches.size() != 1) {
            formationChecklist = false;
        }

        if (this.mInputHatches.size() != 0) {
            formationChecklist = false;
        }

        if (this.mOutputHatches.size() != 0) {
            formationChecklist = false;
        }

        // Back segment
        for (int X = -1; X <= 1; X++) {
            for (int Y = -1; Y <= 1; Y++) {
                // Get next TE
                final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Y, -6);
                final IGregTechTileEntity currentTE =
                        thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());

                // Fluid hatches should touch the storage field.
                // Maintenance/Energy hatch can go anywhere
                if (X == -1 && X == 1 && Y == -1 && Y == 1) {
                    if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
                            && !super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)
                            && !super.addOutputToMachineList(currentTE, CASING_TEXTURE_ID)
                            && !super.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)
                            && !addMultiHatchToMachineList(currentTE, CASING_TEXTURE_ID)) {

                        // If it's not a hatch, is it the right casing for this machine? Check block and block meta.
                        if (thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING) {
                            // Seems to be valid casing. Decrement counter.
                            minCasingAmount--;
                        } else {
                            formationChecklist = false;
                        }
                    }
                } else {
                    if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
                            && !super.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)) {

                        // If it's not a hatch, is it the right casing for this machine? Check block and block meta.
                        if (thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING) {
                            // Seems to be valid casing. Decrement counter.
                            minCasingAmount--;
                        } else {
                            formationChecklist = false;
                        }
                    }
                }
            }
        }

        if (formationChecklist) {
            runningCost = Math.round(-runningCostAcc);
            // Update MultiFluidHandler in case storage cells have been changed
            final int capacityPerFluid = (int) Math.round(fluidCapacityAcc);
            if (mfh == null) {
                mfh = MultiFluidHandler.newInstance(1, capacityPerFluid);
            } else {
                if (mfh.getCapacity() != capacityPerFluid) {
                    mfh = MultiFluidHandler.newAdjustedInstance(mfh, capacityPerFluid);
                }
            }
            for (GTMTE_TankHatch mh : sMultiHatches) {
                mh.setMultiFluidHandler(mfh);
            }
        }

        return formationChecklist;
    }


    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        if (mfh == null) return null;

        final List<FluidStack> fluids = mfh.getFluids();
        final FluidTankInfo[] tankInfo = new FluidTankInfo[fluids.size()];
        for (int i = 0; i < tankInfo.length; i++)
            tankInfo[i] = new FluidTankInfo(fluids.get(i), mfh.getCapacity());

        return tankInfo;
    }

    public boolean addMultiHatchToMachineList(IGregTechTileEntity aTileEntity, int aBaseCasingIndex) {
        if (aTileEntity == null) {
            return false;
        } else {
            final IMetaTileEntity aMetaTileEntity = aTileEntity.getMetaTileEntity();
            if (aMetaTileEntity == null) {
                return false;
            } else if (aMetaTileEntity instanceof GTMTE_TankHatch) {
                ((GTMTE_TankHatch) aMetaTileEntity).updateTexture(aBaseCasingIndex);
                return this.sMultiHatches.add((GTMTE_TankHatch) aMetaTileEntity);
            } else {
                return false;
            }
        }
    }

    @Override
    public void onScrewdriverRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
        doVoidExcess = !doVoidExcess;
        GT_Utility.sendChatToPlayer(aPlayer, doVoidExcess ? "Auto-voiding enabled" : "Auto-voiding disabled");
    }

    @Override
    public String[] getInfoData() {
        final ArrayList<String> ll = new ArrayList<>();

        long capacity = 0;
        if (mfh != null) {
            ll.add(EnumChatFormatting.YELLOW + "Stored Fluids:" + EnumChatFormatting.RESET);
            if (mfh.fluids != null) {
                for (int i = 0; i < mfh.fluids.size(); i++) {
                    capacity = mfh.fluids.get(i).amount;
                    ll.add(i + " - " + mfh.fluids.get(i).getLocalizedName() + ": " + NumberFormat.getNumberInstance().format(capacity) + "L (" + Long.toString(Math.round(100.0f * mfh.fluids.get(i).amount / mfh.getCapacity())) + "%)");
                }
            }
        }

        ll.add(EnumChatFormatting.YELLOW + "Operational Data:" + EnumChatFormatting.RESET);
        ll.add("Auto-voiding: " + doVoidExcess);
        if (mfh != null && mfh.fluids != null)
            ll.add("Per-Fluid Capacity: " + NumberFormat.getNumberInstance().format(mfh.getCapacity()) + "L");
        ll.add("Running Cost: " + ((-super.mEUt) * 10000 / Math.max(1000, super.mEfficiency)) + "EU/t");
        ll.add("Maintenance Status: " + ((super.getRepairStatus() == super.getIdealStatus())
                ? EnumChatFormatting.GREEN + "Working perfectly" + EnumChatFormatting.RESET
                : EnumChatFormatting.RED + "Has Problems" + EnumChatFormatting.RESET));
        ll.add("---------------------------------------------");

        final String[] a = new String[ll.size()];
        return ll.toArray(a);
    }

    @Override
    public void saveNBTData(NBTTagCompound nbt) {
        nbt = (nbt == null) ? new NBTTagCompound() : nbt;

        nbt.setInteger("runningCost", runningCost);
        nbt.setBoolean("doVoidExcess", doVoidExcess);

        nbt.setLong("capacityPerFluid", mfh.getCapacity());
        nbt.setTag("fluids", mfh.saveNBTData(new NBTTagCompound()));

        super.saveNBTData(nbt);
    }

    @Override
    public void loadNBTData(NBTTagCompound nbt) {
        nbt = (nbt == null) ? new NBTTagCompound() : nbt;

        runningCost = nbt.getInteger("runningCost");
        doVoidExcess = nbt.getBoolean("doVoidExcess");

        mfh = mfh.loadNBTData(nbt);
        for (GTMTE_TankHatch mh : sMultiHatches) {
            mh.setMultiFluidHandler(mfh);
        }
        super.loadNBTData(nbt);
    }

    @Override
    public boolean isGivingInformation() {
        return true;
    }

    @Override
    public int getMaxEfficiency(ItemStack var1) {
        return 10000;
    }

    @Override
    public int getPollutionPerTick(ItemStack var1) {
        return 0;
    }

    @Override
    public int getDamageToComponent(ItemStack var1) {
        return 0;
    }

    @Override
    public boolean explodesOnComponentBreak(ItemStack var1) {
        return false;
    }
}
