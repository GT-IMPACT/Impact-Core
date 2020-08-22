package com.impact.mods.GregTech.tileentities.multi;

import com.impact.util.MultiBlockTooltipBuilder;
import com.impact.util.Vector3i;
import com.impact.util.Vector3ic;
import gregtech.api.GregTech_API;
import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
import gregtech.api.gui.GT_GUIContainer_MultiMachine;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import static com.impact.loader.ItemRegistery.CeramicBlock;

public class GTMTE_SOFC extends GT_MetaTileEntity_MultiBlockBase {

    private final Block CASING = GregTech_API.sBlockCasings4;
    private final Block CERAMIC = CeramicBlock;
    private final int[] CERAMIC_META = new int[]{0, 1, 2};
    private final int[] CASING_META = new int[]{1, 2, 0};
    private final int[] CASING_TEXTURE_ID = new int[]{49, 50, 48};

    private final int[] OXYGEN_PER_SEC = new int[]{400, 1200, 2000};
    private final int[] EU_PER_TICK  = new int[]{2048, 8192, 32768};
    private final int[] STEAM_PER_SEC = new int[]{18000, 36000, 72000};

    private final String[] CASING_STRING = new String[]{"Clean Stainless Steel Casing", "Stable Titanium Machine Casing", "Robust Tungstensteel Machine Casing"};
    private final String[] CERAMIC_STRING = new String[]{"YSZ Ceramic Unit", "GDC Ceramic Unit", "LSCF Ceramic Unit"};
    protected int fuelConsumption = 0;
    private int TIER;

    public GTMTE_SOFC(int aID, String aName, String aNameRegional, int aTier) {
        super(aID, aName, aNameRegional);
        this.TIER = aTier;
    }

    public GTMTE_SOFC(String aName, int aTier) {
        super(aName);
        this.TIER = aTier;
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity var1) {
        return new GTMTE_SOFC(super.mName, this.TIER);
    }

    @Override
    public String[] getDescription() {
        final MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder();
        b.addInfo("Oxidizes gas fuels to generate electricity without polluting the environment")
                .addInfo("Steam production requires the SOFC to heat up completely first")
                .addInfo("Outputs " + EU_PER_TICK[this.TIER] + "EU/t and " + (this.TIER == 0 ? STEAM_PER_SEC[this.TIER] + "L/s Steam" : STEAM_PER_SEC[this.TIER] + "L/s SH Steam"))
                .addInfo("Additionally requires " + OXYGEN_PER_SEC[this.TIER] + "L/s Oxygen gas")
                .addSeparator()
                .beginStructureBlock(3, 3, 5)
                .addController()
                .addDynamoHatch("Back Center")
                .addOtherStructurePart(CERAMIC_STRING[this.TIER], "3x, Center 1x1x3")
                .addOtherStructurePart("Reinforced Glass", "6x, touching the electrolyte units on the horizontal sides")
                .addCasingInfo(CASING_STRING[this.TIER])
                .addMaintenanceHatch("Instead of any casing")
                .addIOHatches("Instead of any casing")
                .signAndFinalize(EnumChatFormatting.RED + "Impact");
        if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            return b.getInformation();
        } else {
            return b.getStructureInformation();
        }
    }

    @Override
    public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide, final byte aFacing,
                                 final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
        return aSide == aFacing
                ? new ITexture[]{Textures.BlockIcons.CASING_BLOCKS[CASING_TEXTURE_ID[this.TIER]],
                new GT_RenderedTexture(aActive ?
                        Textures.BlockIcons.OVERLAY_FRONT_HEAT_EXCHANGER_ACTIVE
                        : Textures.BlockIcons.OVERLAY_FRONT_HEAT_EXCHANGER)}
                : new ITexture[]{Textures.BlockIcons.CASING_BLOCKS[CASING_TEXTURE_ID[this.TIER]]};
    }

    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_GUIContainer_MultiMachine(aPlayerInventory, aBaseMetaTileEntity, this.getLocalName(),
                "MultiblockDisplay.png");
    }

    @Override
    public boolean isCorrectMachinePart(ItemStack stack) {
        return true;
    }

    @Override
    public boolean checkRecipe(ItemStack stack) {
        final ArrayList<FluidStack> storedFluids = super.getStoredFluids();
        Collection<GT_Recipe> recipeList = GT_Recipe.GT_Recipe_Map.sTurbineFuels.mRecipeList;

        if ((storedFluids.size() > 0 && recipeList != null)) {

            final Iterator<FluidStack> fluidsIterator = storedFluids.iterator();
            while (fluidsIterator.hasNext()) {

                final FluidStack hatchFluid = fluidsIterator.next();
                final Iterator<GT_Recipe> recipeIterator = recipeList.iterator();
                while (recipeIterator.hasNext()) {

                    final GT_Recipe aFuel = recipeIterator.next();
                    FluidStack liquid;
                    if ((liquid = GT_Utility.getFluidForFilledItem(aFuel.getRepresentativeInput(0), true)) != null
                            && hatchFluid.isFluidEqual(liquid)) {

                        fuelConsumption = liquid.amount = (EU_PER_TICK[this.TIER] * 20)/ aFuel.mSpecialValue;

                        if (super.depleteInput(liquid)) {

                            if (!super.depleteInput(Materials.Oxygen.getGas(OXYGEN_PER_SEC[this.TIER]))) {
                                super.mEUt = 0;
                                super.mEfficiency = 0;
                                return false;
                            }

                            super.mEUt = EU_PER_TICK[this.TIER];
                            super.mMaxProgresstime = 20;
                            super.mEfficiencyIncrease = 40;
                            if (super.mEfficiency == getMaxEfficiency(null)) {
                                if(this.TIER == 0)
                                    super.addOutput(GT_ModHandler.getSteam(STEAM_PER_SEC[this.TIER]));
                                else
                                    super.addOutput(FluidRegistry.getFluidStack("ic2superheatedsteam", STEAM_PER_SEC[this.TIER]));

                            }
                            return true;
                        }
                    }
                }
            }
        }

        super.mEUt = 0;
        super.mEfficiency = 0;
        return false;
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
        // Figure out the vector for the direction the back face of the controller is facing
        final Vector3ic forgeDirection = new Vector3i(
                ForgeDirection.getOrientation(thisController.getBackFacing()).offsetX,
                ForgeDirection.getOrientation(thisController.getBackFacing()).offsetY,
                ForgeDirection.getOrientation(thisController.getBackFacing()).offsetZ
        );
        int minCasingAmount = 12;
        boolean formationChecklist = true; // if this is still true at the end, machine is good to go :)

        // Front slice
        for (int X = -1; X <= 1; X++) {
            for (int Y = -1; Y <= 1; Y++) {
                if (X == 0 && Y == 0) {
                    continue; // is controller
                }

                // Get next TE
                final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Y, 0);
                IGregTechTileEntity currentTE =
                        thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());

                // Tries to add TE as either of those kinds of hatches.
                // The number is the texture index number for the texture that needs to be painted over the hatch texture (TAE for GT++)
                if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID[this.TIER])
                        && !super.addInputToMachineList(currentTE, CASING_TEXTURE_ID[this.TIER])
                        && !super.addOutputToMachineList(currentTE, CASING_TEXTURE_ID[this.TIER])) {

                    // If it's not a hatch, is it the right casing for this machine? Check block and block meta.
                    if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
                            && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == CASING_META[this.TIER])) {
                        // Seems to be valid casing. Decrement counter.
                        minCasingAmount--;
                    } else {
                        formationChecklist = false;
                    }
                }
            }
        }

        // Middle three slices
        for (int X = -1; X <= 1; X++) {
            for (int Y = -1; Y <= 1; Y++) {
                for (int Z = -1; Z >= -3; Z--) {
                    final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Y, Z);
                    if (X == 0 && Y == 0) {
                        if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) != CERAMIC)
                                    && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) != CERAMIC_META[this.TIER])) {
                            formationChecklist = false;
                        }
                        continue;
                    }
                    if (Y == 0 && (X == -1 || X == 1)) {
                        if (!thisController.getBlockOffset(offset.x(), offset.y(), offset.z()).getUnlocalizedName()
                                .equals("blockAlloyGlass")) {
                            formationChecklist = false;
                        }
                        continue;
                    }
                    // Get next TE
                    IGregTechTileEntity currentTE =
                            thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());// x, y ,z

                    // Tries to add TE as either of those kinds of hatches.
                    // The number is the texture index number for the texture that needs to be painted over the hatch texture (TAE for GT++)
                    if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID[this.TIER])
                            && !super.addInputToMachineList(currentTE, CASING_TEXTURE_ID[this.TIER])
                            && !super.addOutputToMachineList(currentTE, CASING_TEXTURE_ID[this.TIER])) {

                        // If it's not a hatch, is it the right casing for this machine? Check block and block meta.
                        if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
                                && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == CASING_META[this.TIER])) {
                            // Seems to be valid casing. Decrement counter.
                            minCasingAmount--;
                        } else {
                            formationChecklist = false;
                        }
                    }
                }
            }
        }

        // Back slice
        for (int X = -1; X <= 1; X++) {
            for (int Y = -1; Y <= 1; Y++) {
                // Get next TE
                final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Y, -4);
                IGregTechTileEntity currentTE =
                        thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());// x, y ,z

                // Tries to add TE as either of those kinds of hatches.
                // The number is the texture index number for the texture that needs to be painted over the hatch texture (TAE for GT++)
                if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID[this.TIER])
                        && !super.addInputToMachineList(currentTE, CASING_TEXTURE_ID[this.TIER])
                        && !super.addOutputToMachineList(currentTE, CASING_TEXTURE_ID[this.TIER])
                        && !super.addDynamoToMachineList(currentTE, CASING_TEXTURE_ID[this.TIER])) {

                    // If it's not a hatch, is it the right casing for this machine? Check block and block meta.
                    if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
                            && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == CASING_META[this.TIER])) {
                        // Seems to be valid casing. Decrement counter.
                        minCasingAmount--;
                    } else {
                        formationChecklist = false;
                    }
                }
            }
        }

        if (minCasingAmount > 0) formationChecklist = false;
        if (this.mDynamoHatches.size() != 1) formationChecklist = false;
        if (this.mInputHatches.size() < 2) formationChecklist = false;
        if (this.mMaintenanceHatches.size() < 1) formationChecklist = false;

        return formationChecklist;
    }

    public String[] getInfoData() {

        return new String[]{
                StatCollector.translateToLocal("GT5U.engine.output") + ": "
                        + EnumChatFormatting.GREEN + this.mEUt * this.mEfficiency / 10000
                        + EnumChatFormatting.RESET + " EU/t",
                StatCollector.translateToLocal("GT5U.engine.efficiency") + ": "
                        + EnumChatFormatting.YELLOW + (float) this.mEfficiency / 100.0F
                        + EnumChatFormatting.YELLOW + " %",
                (this.TIER == 0 ? "Output Steam: " : "Output SH Steam: ") + (((float) this.mEfficiency / 100.0F == 100)
                        ? EnumChatFormatting.GREEN + "" + STEAM_PER_SEC[this.TIER] + EnumChatFormatting.RESET + " L/s"
                        : EnumChatFormatting.GREEN + "0" + EnumChatFormatting.RESET + " L/s"),
                "Maintenance: " + ((super.getRepairStatus() == super.getIdealStatus())
                        ? EnumChatFormatting.GREEN + "No Problems" + EnumChatFormatting.RESET
                        : EnumChatFormatting.RED + "Has Problems" + EnumChatFormatting.RESET),
                "Fuel supply: " + ((this.mEUt * this.mEfficiency / 10000 >= 1)
                        ? EnumChatFormatting.RED + "" + fuelConsumption + EnumChatFormatting.RESET + " L/s"
                        : EnumChatFormatting.RED + "0" + EnumChatFormatting.RESET + " L/s"),
                "Oxygen supply: " + ((this.mEUt * this.mEfficiency / 10000 >= 1)
                        ? EnumChatFormatting.RED + "" + OXYGEN_PER_SEC[this.TIER] + EnumChatFormatting.RESET + " L/s"
                        : EnumChatFormatting.RED + "0" + EnumChatFormatting.RESET + " L/s")
        };
    }

    @Override
    public int getMaxEfficiency(ItemStack stack) {
        return 10000;
    }

    @Override
    public int getPollutionPerTick(ItemStack stack) {
        return 0;
    }

    @Override
    public int getDamageToComponent(ItemStack stack) {
        return 0;
    }

    @Override
    public boolean explodesOnComponentBreak(ItemStack stack) {
        return false;
    }

}
