package com.impact.mods.GregTech.tileentities.multi.generators;

import com.impact.mods.GregTech.casings.CORE_API;
import com.impact.mods.GregTech.tileentities.multi.debug.GT_MetaTileEntity_MultiParallelBlockBase;
import com.impact.mods.GregTech.tileentities.multi.gui.GUI_BASE;
import com.impact.util.MultiBlockTooltipBuilder;
import com.impact.util.Vector3i;
import com.impact.util.Vector3ic;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import static gregtech.api.enums.GT_Values.RA;

public class GTMTE_LiquidNqGenerator extends GT_MetaTileEntity_MultiParallelBlockBase {



    public static String mModed;
    public byte mMode = -1;
    /**
     * === SET BLOCKS STRUCTURE ===
     */
    Block CASING = CORE_API.sCaseCore1;
    byte CASING_META = 4;
    /**
     * === SET TEXTURES HATCHES AND CONTROLLER ===
     */
    ITexture INDEX_CASE = Textures.BlockIcons.casingTexturePages[3][CASING_META];
    int CASING_TEXTURE_ID = CASING_META + 128 * 3;
    private int mLevel = 0;

    public int EU_PER_TICK = 524288;

    /**
     * === NAMED ===
     */
    public GTMTE_LiquidNqGenerator(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
        new FuelNqGenerator().run();
    }

    /**
     * === NAMED ===
     */
    public GTMTE_LiquidNqGenerator(String aName) {
        super(aName);
    }

    /**
     * === SET TEXTURE ===
     */
    @Override
    public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide, final byte aFacing,
                                 final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
        return aSide == aFacing
                ? new ITexture[]{INDEX_CASE, new GT_RenderedTexture(
                aActive
                        ? Textures.BlockIcons.MP1a
                        : Textures.BlockIcons.MP1)}
                : new ITexture[]{INDEX_CASE};
    }

    /**
     * === META ENTITY ===
     */
    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GTMTE_LiquidNqGenerator(this.mName);
    }

    /**
     * === DESCRIPTION ===
     */
    @Override
    public String[] getDescription() {
        final MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder();
        b
                .addInfo("One-block machine analog")
                .addParallelInfo(1, 256)
                .addInfo("Parallel Point will upped Upgrade Casing")
                .addPollution(200, 12800)
                .addTypeMachine("Extruder, Bender, Presser")
                .addScrew()
                .addSeparatedBus()
                .addSeparator()
                .addController()
                .addEnergyHatch("Any casing")
                .addMaintenanceHatch("Any casing")
                .addInputBus("Any casing (max x15)")
                .addOutputBus("Any casing (max x3)")
                .addMuffler("Any casing")
                .addCasingInfo("PBE Casing")
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
        return new GUI_BASE(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "MultiParallelBlockGUI.png", mModed);
    }

    public boolean checkMachine(IGregTechTileEntity thisController, ItemStack guiSlotItem) {
        TThatches();
        // Вычисляем вектор направления, в котором находится задняя поверхность контроллера
        final Vector3ic forgeDirection = new Vector3i(
                ForgeDirection.getOrientation(thisController.getBackFacing()).offsetX,
                ForgeDirection.getOrientation(thisController.getBackFacing()).offsetY,
                ForgeDirection.getOrientation(thisController.getBackFacing()).offsetZ);

        int minCasingAmount = 12; // Минимальное количество кейсов
        boolean formationChecklist = true; // Если все ок, машина собралась

        for (byte X = -1; X <= 1; X++) {
            for (byte Y = -1; Y <= 1; Y++) {
                for (byte Z = 0; Z >= -4; Z--) {

                    if (X == 0 && Y == 0 && Z == 0) continue;

                    final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Y, Z);

                    IGregTechTileEntity currentTE = thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
                    if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
                            && !super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)
                            && !super.addMufflerToMachineList(currentTE, CASING_TEXTURE_ID)
                            && !super.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)
                            && !super.addDynamoToMachineList(currentTE, CASING_TEXTURE_ID)
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

//        if (this.mInputBusses.size() > 15) formationChecklist = false;
//        if (this.mInputHatches.size() > 0) formationChecklist = false;
//        if (this.mOutputBusses.size() > 3) formationChecklist = false;
//        if (this.mOutputHatches.size() != 0) formationChecklist = false;
//        if (this.mMufflerHatches.size() > 0) formationChecklist = false;
//        if (this.mEnergyHatches.size() > 4) formationChecklist = false;
//        if (this.mDynamoHatches.size() > 1) formationChecklist = false;
//        if (this.mMaintenanceHatches.size() != 1) formationChecklist = false;

        return formationChecklist;
    }

    @Override
    public int getParallel() {
        return 1;
    }

    @Override
    public int getPollutionPerTick(ItemStack aStack) {
        return 0;
    }

    @Override
    public boolean checkRecipe(ItemStack itemStack) {

        final ArrayList<FluidStack> storedFluids = super.getStoredFluids();

        Collection<GT_Recipe> recipeList = GT_Recipe.GT_Recipe_Map.sLiquidNqGenerator.mRecipeList;

        if ((storedFluids.size() > 0 && recipeList != null)) {

            final Iterator<FluidStack> fluidsIterator = storedFluids.iterator();

            while (fluidsIterator.hasNext()) {

                final FluidStack hatchFluid = fluidsIterator.next();
                final Iterator<GT_Recipe> recipeIterator = recipeList.iterator();

                while (recipeIterator.hasNext()) {

                    final GT_Recipe aFuel = recipeIterator.next(); // FUEL_NAME Iterator
                    FluidStack liquid; // Register FluidStack (name fluid from oredict cell materials, amount)

                    if ((liquid = GT_Utility.getFluidForFilledItem(aFuel.getRepresentativeInput(0), true)) != null
                            && hatchFluid.isFluidEqual(liquid)) { // check: fluid cell and fluid without cell

                        liquid.amount = aFuel.mSpecialValue; // set Amount: FUEL_PER_SECOND

                        if (super.depleteInput(liquid)) {

                            super.mEUt = EU_PER_TICK; // LuV * 16A
                            super.mMaxProgresstime = 20; // 1 Second
                            super.mEfficiencyIncrease = 500; // 500 - 5% per cycle
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


}

class FuelNqGenerator implements Runnable {

    public int[] FUEL_PER_SECOND = new int[] { //for 1A
            20,
            10,
            5,
    };

    public ItemStack[] FUEL_NAME = new ItemStack[] {
            GT_OreDictUnificator.get(OrePrefixes.cell, Materials.NaquadahHeavyFuel, 1),
            GT_OreDictUnificator.get(OrePrefixes.cell, Materials.NaquadahMediumFuel, 1),
            GT_OreDictUnificator.get(OrePrefixes.cell, Materials.NaquadahLightFuel, 1),
    };

    @Override
    public void run() {
        for (int i = 0; i < FUEL_NAME.length; i++)
            RA.addFuel(FUEL_NAME[i], GT_Utility.getFluidForFilledItem(FUEL_NAME[i], true) == null ? GT_Utility.getContainerItem(FUEL_NAME[i], true) : null, FUEL_PER_SECOND[i], 8);
    }
}