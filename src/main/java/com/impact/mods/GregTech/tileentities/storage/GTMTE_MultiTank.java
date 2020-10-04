package com.impact.mods.GregTech.tileentities.storage;

import com.github.technus.tectech.mechanics.alignment.enumerable.ExtendedFacing;
import com.github.technus.tectech.mechanics.constructable.IMultiblockInfoContainer;
import com.github.technus.tectech.mechanics.structure.IStructureDefinition;
import com.github.technus.tectech.mechanics.structure.StructureDefinition;
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
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Input;
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
import java.util.Iterator;
import java.util.List;

import static com.github.technus.tectech.mechanics.constructable.IMultiblockInfoContainer.registerMetaClass;
import static com.github.technus.tectech.mechanics.structure.StructureUtility.ofBlock;
import static com.impact.loader.ItemRegistery.*;
import static com.impact.mods.GregTech.casings.CORE_API.sCaseCore2;
import static gregtech.api.GregTech_API.sBlockCasings8;

public class GTMTE_MultiTank extends GT_MetaTileEntity_MultiBlockBase implements IFluidHandler {

    private final HashSet<GTMTE_TankHatch> sMultiHatches = new HashSet<>();
    private final Block glassIC2 = IGlassBlock;
    private final Block CASING = sBlockCasings8;
    private final Block CASING_TANK = FluidTankBlock;
    int CASING_TEXTURE_ID = 176;
    public MultiFluidHandler mfh;
    private int runningCost = 0;
    private boolean doVoidExcess = false;
    private byte fluidSelector = 0;


    public GTMTE_MultiTank(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
        run();
    }

    public GTMTE_MultiTank(String aName) {
        super(aName);
        run();
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity var1) {
        run();
        return new GTMTE_MultiTank(super.mName);
    }

    @Override
    public String[] getDescription() {
        final MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder();
        b.addInfo("High-Tech fluid tank that can hold up to 25 different fluids!")
            .addInfo("Has 1/25th of the total capacity as capacity for each fluid.")
            .addInfo("Right-Click to the controller with a screwdriver will turn on excess voiding.")
            .addInfo("Fluid storage amount and running cost depends on the storage field blocks used.")
            .addSeparator()
            .addInfo("Note on hatch locking:")
            .addSeparator()
            .beginStructureBlock(5, 9, 5, true)
            .addController()
            .addEnergyHatch("Any top or bottom casing")
            .addOtherStructurePart("Inner 3x7x3 tube", "Tank Storage Block")
            .addOtherStructurePart("Outer 5x1&9x5 Casing", "Chemical Casing")
            .addOtherStructurePart("Outer 5x7x5 glass shell", "I-Glass")
            .addOtherStructurePart("I/O Tank Hatch", "Instead of any casing or glass (not angle)")
            .addInfo("I/O Tank Hatch for information and used EC2, OC systems")
            .signAndFinalize(EnumChatFormatting.RED + "Impact");
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

        this.mEfficiency = 10000 - (this.getIdealStatus() - this.getRepairStatus()) * 1000;
        this.mEfficiencyIncrease = 10000;
        this.mEUt = runningCost;
        super.mMaxProgresstime = 10;

        // If there are no basic I/O hatches, let multi hatches handle it and skip a lot of code!
        if(sMultiHatches.size() > 0 && super.mInputHatches.size() == 0 && super.mOutputHatches.size() == 0) {
            return true;
        }

        // Suck in fluids
        final ArrayList<FluidStack> inputHatchFluids = super.getStoredFluids();
        if(inputHatchFluids.size() > 0) {

            for(FluidStack fluidStack : inputHatchFluids) {

                final int pushed = mfh.pushFluid(fluidStack, true);
                final FluidStack toDeplete = fluidStack.copy();
                toDeplete.amount = pushed;
                super.depleteInput(toDeplete);
            }

            // Void excess if that is turned on
            if(doVoidExcess) {
                for(GT_MetaTileEntity_Hatch_Input inputHatch : super.mInputHatches) {
                    inputHatch.setDrainableStack(null);
                }
            }
        }

        // Push out fluids
        if(guiSlotItem != null && guiSlotItem.getUnlocalizedName().equals("gt.integrated_circuit")) {
            final int config = guiSlotItem.getItemDamage();
            final FluidStack storedFluid = mfh.getFluid(config);
            // Sum available output capacity
            int possibleOutput = 0;
            for(GT_MetaTileEntity_Hatch_Output outputHatch : super.mOutputHatches) {
                if(outputHatch.isFluidLocked() && outputHatch.getLockedFluidName().equals(storedFluid.getUnlocalizedName())) {
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
            tempStack.amount = mfh.pullFluid(tempStack, config, true);
            super.addOutput(tempStack);

        } else {
            final Iterator<FluidStack> storageIterator = mfh.getFluids().iterator();
            while(storageIterator.hasNext()) {
                FluidStack storedFluid = storageIterator.next();
                // Sum available output capacity
                int possibleOutput = 0;
                for(GT_MetaTileEntity_Hatch_Output outputHatch : super.mOutputHatches) {
                    if(outputHatch.isFluidLocked() && outputHatch.getLockedFluidName().equals(storedFluid.getUnlocalizedName())) {
                        possibleOutput += outputHatch.getCapacity() - outputHatch.getFluidAmount();
                    } else if (outputHatch.getFluid() != null && outputHatch.getFluid().getUnlocalizedName().equals(storedFluid.getUnlocalizedName())) {
                        possibleOutput += outputHatch.getCapacity() - outputHatch.getFluidAmount();
                    } else if (outputHatch.getFluid() == null) {
                        possibleOutput += outputHatch.getCapacity() - outputHatch.getFluidAmount();
                    }
                }
                // output as much as possible
                final FluidStack tempStack = storedFluid.copy();
                tempStack.amount = possibleOutput;
                // TODO possible concurrent modification exception as pullFluid calls remove() without an iterator
                tempStack.amount = mfh.pullFluid(tempStack, true);
                super.addOutput(tempStack);
            }
        }

        return true;
    }

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        super.onPostTick(aBaseMetaTileEntity, aTick);
        if(mfh != null) mfh.setLock(!super.getBaseMetaTileEntity().isActive());
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
                    if (!super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)
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
                    if (!super.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)) {

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
                    if (!super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)
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
                    if (!super.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)) {

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

        if(this.mEnergyHatches.size() < 1) {
            System.out.println("At least one energy hatch is required!");
            formationChecklist = false;
        }

//        if(this.mMaintenanceHatches.size() < 1) {
//            System.out.println("You need a maintenance hatch to do maintenance.");
//            formationChecklist = false;
//        }

        if(minCasingAmount > 0) {
            formationChecklist = false;
        }

        if(formationChecklist) {
            runningCost = (int) Math.round(-runningCostAcc);

            // Update MultiFluidHandler in case storage cells have been changed
            final int capacityPerFluid = (int) Math.round(fluidCapacityAcc / 25.0f);
            if(mfh == null) {
                mfh = new MultiFluidHandler(capacityPerFluid, 25);
            } else {
                if(mfh.getCapacity() != capacityPerFluid) {
                    mfh = new MultiFluidHandler(capacityPerFluid, mfh.getFluids(), 25);
                }
            }
            for(GTMTE_TankHatch mh : sMultiHatches) {
                mh.setMultiFluidHandler(mfh);
            }
        }
        this.mWrench = true;
        this.mScrewdriver = true;
        this.mSoftHammer = true;
        this.mHardHammer = true;
        this.mSolderingTool = true;
        this.mCrowbar = true;
        return formationChecklist;
    }

    public void run() {
        registerMetaClass(GTMTE_MultiTank.class, new IMultiblockInfoContainer<GTMTE_MultiTank>() {
            //region Structure
            private final IStructureDefinition<GTMTE_MultiTank> definition =
                    StructureDefinition.<GTMTE_MultiTank>builder()
                            .addShape("main", new String[][]{
                                    {"AAAAA","AAAAA","AA~AA","AAAAA","AAAAA"},
                                    {"CCCCC","CBBBC","CBBBC","CBBBC","CCCCC"},
                                    {"CCCCC","CBBBC","CBBBC","CBBBC","CCCCC"},
                                    {"CCCCC","CBBBC","CBBBC","CBBBC","CCCCC"},
                                    {"CCCCC","CBBBC","CBBBC","CBBBC","CCCCC"},
                                    {"CCCCC","CBBBC","CBBBC","CBBBC","CCCCC"},
                                    {"CCCCC","CBBBC","CBBBC","CBBBC","CCCCC"},
                                    {"CCCCC","CBBBC","CBBBC","CBBBC","CCCCC"},
                                    {"AAAAA","AAAAA","AAAAA","AAAAA","AAAAA"}
                            })
                            .addElement('A', ofBlock(sBlockCasings8, 0))
                            .addElement('B', ofBlock(FluidTankBlock))
                            .addElement('C', ofBlock(IGlassBlock))
                            .build();
            private final String[] desc = new String[]{
                    EnumChatFormatting.RED + "Impact Details:",
                    "- Chemical Casing",
                    "- Tank Storage Block (Tier 1-8)",
                    "- I-Glass",
                    "- Hatches (any Chemical Casing)",
                    "- I/O Tank Hatch (any Chemical Casing or I-Glass (not angle))",
            };
            //endregion
            @Override
            public void construct(ItemStack stackSize, boolean hintsOnly, GTMTE_MultiTank tileEntity, ExtendedFacing aSide) {
                IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
                definition.buildOrHints(tileEntity, stackSize, "main", base.getWorld(), aSide,
                        base.getXCoord(), base.getYCoord(), base.getZCoord(),
                        2, 2, 0, hintsOnly);
            }
            @Override
            public String[] getDescription(ItemStack stackSize) {
                return desc;
            }
        });

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
                return sMultiHatches.add((GTMTE_TankHatch) aMetaTileEntity);
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
        if (mfh != null) {
            ll.add(EnumChatFormatting.YELLOW + "Stored Fluids:" + EnumChatFormatting.RESET);
            for(int i = 0; i < mfh.fluids.size(); i++)
                ll.add(i + " - " + mfh.fluids.get(i).getLocalizedName() + ": " + mfh.fluids.get(i).amount + "L (" + (Math.round(100.0f * mfh.fluids.get(i).amount / getCapacity())) + "%)");
        }
        ll.add(EnumChatFormatting.YELLOW + "Operational Data:" + EnumChatFormatting.RESET);
        ll.add("Auto-voiding: " + doVoidExcess);
        if (mfh != null) ll.add("Per-Fluid Capacity: " + NumberFormat.getNumberInstance().format(mfh.getCapacity()) + "L");
        ll.add("Running Cost: " + ((-super.mEUt) * 10000 / Math.max(1000, super.mEfficiency)) + "EU/t");
        ll.add("Maintenance Status: " + ((super.getRepairStatus() == super.getIdealStatus()) ? EnumChatFormatting.GREEN + "Working perfectly" + EnumChatFormatting.RESET : EnumChatFormatting.RED + "Has Problems" + EnumChatFormatting.RESET));
        ll.add("---------------------------------------------");

        final String[] a = new String[ll.size()];
        return ll.toArray(a);
    }

    @Override
    public void saveNBTData(NBTTagCompound nbt) {
        nbt = (nbt == null) ? new NBTTagCompound() : nbt;

        nbt.setInteger("runningCost", runningCost);
        nbt.setBoolean("doVoidExcess", doVoidExcess);

        nbt.setInteger("capacityPerFluid", mfh.getCapacity());
        nbt.setTag("fluids", mfh.saveNBTData(new NBTTagCompound()));

        super.saveNBTData(nbt);
    }

    @Override
    public void loadNBTData(NBTTagCompound nbt) {
        nbt = (nbt == null) ? new NBTTagCompound() : nbt;

        runningCost = nbt.getInteger("runningCost");
        doVoidExcess = nbt.getBoolean("doVoidExcess");

        mfh = new MultiFluidHandler();
        mfh.loadNBTData(nbt);
        for(GTMTE_TankHatch mh : sMultiHatches) {
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
