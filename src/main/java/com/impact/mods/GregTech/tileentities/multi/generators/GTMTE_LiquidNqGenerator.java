package com.impact.mods.GregTech.tileentities.multi.generators;

import com.github.technus.tectech.mechanics.alignment.enumerable.ExtendedFacing;
import com.github.technus.tectech.mechanics.constructable.IMultiblockInfoContainer;
import com.github.technus.tectech.mechanics.structure.IStructureDefinition;
import com.github.technus.tectech.mechanics.structure.StructureDefinition;
import com.github.technus.tectech.thing.block.QuantumStuffBlock;
import com.impact.block.blocks.Block_QuantumStuff;
import com.impact.mods.GregTech.casings.CORE_API;
import com.impact.mods.GregTech.tileentities.multi.debug.GT_MetaTileEntity_MultiParallelBlockBase;
import com.impact.mods.GregTech.tileentities.multi.gui.GUI_BASE;
import com.impact.mods.GregTech.tileentities.storage.GTMTE_LapPowerStation;
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

import static com.github.technus.tectech.mechanics.constructable.IMultiblockInfoContainer.registerMetaClass;
import static com.github.technus.tectech.mechanics.structure.StructureUtility.ofBlock;
import static com.impact.loader.ItemRegistery.IGlassBlock;
import static com.impact.loader.ItemRegistery.lscLapotronicEnergyUnit;
import static com.impact.mods.GregTech.casings.CORE_API.sCaseCore2;
import static gregtech.api.enums.GT_Values.RA;

public class GTMTE_LiquidNqGenerator extends GT_MetaTileEntity_MultiParallelBlockBase {


    public static String mModed;
    public byte mMode = -1;
    public int EU_PER_TICK = 524288;
    /**
     * === SET BLOCKS STRUCTURE ===
     */
    Block CASING = CORE_API.sCaseCore2;
    byte CASING_META = 3;
    /**
     * === SET TEXTURES HATCHES AND CONTROLLER ===
     */
    ITexture INDEX_CASE = Textures.BlockIcons.casingTexturePages[3][CASING_META+16];
    int CASING_TEXTURE_ID = CASING_META + 16 + 128 * 3;
    private int mLevel = 0;

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
        return new GUI_BASE(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "MultiParallelBlockGUI.png");
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

    public boolean checkMachine(IGregTechTileEntity thisController, ItemStack guiSlotItem) {
        TThatches();
        final Vector3ic forgeDirection = new Vector3i(
                ForgeDirection.getOrientation(thisController.getBackFacing()).offsetX,
                ForgeDirection.getOrientation(thisController.getBackFacing()).offsetY,
                ForgeDirection.getOrientation(thisController.getBackFacing()).offsetZ);

        int minCasingAmount = 12;
        boolean formationChecklist = true;

        for (byte X = -2; X <= 2; X++) {
            for (byte Y = -2; Y <= 2; Y++) {

                final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Y, 1);

                if (X == -2 && Y == 0 || X == 2 && Y == 0 || X == 0 && Y == -2 || X == 0 && Y == 2) continue;
                if (X > -2 && X < 2 && Y > -2 && Y < 2) continue;

                IGregTechTileEntity currentTE = thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
                if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
                        && !super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)
                        && !super.addMufflerToMachineList(currentTE, CASING_TEXTURE_ID)
                        && !super.addOutputToMachineList(currentTE, CASING_TEXTURE_ID)) {

                    if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
                            && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == CASING_META)) {
                        minCasingAmount--;
                    } else {
                        System.out.println("Ошибка Z = 1");
                        formationChecklist = false;
                    }
                }
            }
        }

        for (byte X = -3; X <= 3; X++) {
            for (byte Y = -3; Y <= 3; Y++) {

                final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Y, 0);

                if (X == 0 && Y == 0) continue;
                if (X == -3 && Y == -3 || X == 3 && Y == 3 || X == 3 && Y == -3 || X == -3 && Y == 3) continue;
                if (X == 3 && Y == -2 || X == 3 && Y == 2 || X == -3 && Y == -2 || X == -3 && Y == 2) continue;
                if (X == 2 && Y == -3 || X == 2 && Y == 3 || X == -2 && Y == -3 || X == -2 && Y == 3) continue;

                IGregTechTileEntity currentTE = thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
                if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
                        && !super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)
                        && !super.addMufflerToMachineList(currentTE, CASING_TEXTURE_ID)
                        && !super.addOutputToMachineList(currentTE, CASING_TEXTURE_ID)) {

                    if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
                            && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == CASING_META)) {
                        minCasingAmount--;
                    } else {
                        System.out.println("Ошибка Z = 0");
                        formationChecklist = false;
                    }
                }
            }
        }

        for (byte X = -3; X <= 3; X++) {
            for (byte Y = -3; Y <= 3; Y++) {
                for (int Z = -1; Z >= -5; Z--) {

                    final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Y, Z);

                    if (X == -3 && Y == -3 || X == 3 && Y == 3 || X == 3 && Y == -3 || X == -3 && Y == 3) continue;
                    if (X == 3 && Y == -2 || X == 3 && Y == 2 || X == -3 && Y == -2 || X == -3 && Y == 2) continue;
                    if (X == 2 && Y == -3 || X == 2 && Y == 3 || X == -2 && Y == -3 || X == -2 && Y == 3) continue;

                    if ((Y >= -1 && Y <= 1 && (X == 3 || X == -3)) || (X >= -1 && X <= 1 && (Y == 3 || Y == -3))) {
                        if (thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == IGlassBlock) {
                            minCasingAmount--;
                        } else {
                            System.out.println("Ошибка Z = -1 до -5 в стекле X ");
                            formationChecklist = false;
                        }
                        continue;
                    }

                    if ((X >= -2 && X <= 2 && Y >= -2 && Y <= 2) && !(X==2 && Y==2 || X==-2 && Y ==2 || X == 2 && Y == -2 || X == -2 && Y == -2)) {
                        if ((X == 1 || X == -1) && (Z == -1 || Z == -5) && (Y >= -1 && Y <= 1)) {
                            if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == sCaseCore2)
                                    && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 10)) {

                            } else {
                                System.out.println("Ошибка Z = -1 до -5 держатели ядра");
                                formationChecklist = false;
                            }
                            continue;
                        }
                        continue;
                    }


                    IGregTechTileEntity currentTE = thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
                    if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
                            && !super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)
                            && !super.addMufflerToMachineList(currentTE, CASING_TEXTURE_ID)
                            && !super.addOutputToMachineList(currentTE, CASING_TEXTURE_ID)) {

                        if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
                                && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == CASING_META)) {
                            minCasingAmount--;
                        } else {
                            System.out.println("Ошибка Z = -1 до -5 " + X + " " + Y);
                            formationChecklist = false;
                        }
                    }
                }
            }
        }

        for (byte X = -3; X <= 3; X++) {
            for (byte Y = -3; Y <= 3; Y++) {

                final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Y, -6);

                if (X == -3 && Y == -3 || X == 3 && Y == 3 || X == 3 && Y == -3 || X == -3 && Y == 3) continue;
                if (X == 3 && Y == -2 || X == 3 && Y == 2 || X == -3 && Y == -2 || X == -3 && Y == 2) continue;
                if (X == 2 && Y == -3 || X == 2 && Y == 3 || X == -2 && Y == -3 || X == -2 && Y == 3) continue;

                IGregTechTileEntity currentTE = thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
                if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
                        && !super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)
                        && !super.addMufflerToMachineList(currentTE, CASING_TEXTURE_ID)
                        && !super.addOutputToMachineList(currentTE, CASING_TEXTURE_ID)) {

                    if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
                            && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == CASING_META)) {
                        minCasingAmount--;
                    } else {
                        System.out.println("Ошибка Z = -6");
                        formationChecklist = false;
                    }
                }
            }
        }

        for (byte X = -2; X <= 2; X++) {
            for (byte Y = -2; Y <= 2; Y++) {

                final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Y, -7);

                if (X == -2 && Y == 0 || X == 2 && Y == 0 || X == 0 && Y == -2 || X == 0 && Y == 2) continue;

                IGregTechTileEntity currentTE = thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
                if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
                        && !super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)
                        && !super.addMufflerToMachineList(currentTE, CASING_TEXTURE_ID)
                        && !super.addDynamoToMachineList(currentTE, CASING_TEXTURE_ID)
                        && !super.addOutputToMachineList(currentTE, CASING_TEXTURE_ID)) {

                    if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
                            && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == CASING_META)) {
                        minCasingAmount--;
                    } else {
                        System.out.println("Ошибка Z = -7");
                        formationChecklist = false;
                    }
                }
            }
        }


//----------------------------------------------------------------------------------------------------------------------

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

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        super.onPostTick(aBaseMetaTileEntity, aTick);
        if (checkMachine(aBaseMetaTileEntity, null)) {
            if (aBaseMetaTileEntity.isActive()) quantumStuff(true);
            else quantumStuff(false);
        }
    }

    private void quantumStuff(boolean shouldExist) {
        IGregTechTileEntity base = getBaseMetaTileEntity();
        if (base != null && base.getWorld() != null) {
            int xDir = ForgeDirection.getOrientation(base.getBackFacing()).offsetX * 3 + base.getXCoord();
            int yDir = ForgeDirection.getOrientation(base.getBackFacing()).offsetY * 3 + base.getYCoord();
            int zDir = ForgeDirection.getOrientation(base.getBackFacing()).offsetZ * 3 + base.getZCoord();
            Block block = base.getWorld().getBlock(xDir, yDir, zDir);
            if (shouldExist) {
                if (block != null) {
                    base.getWorld().setBlock(xDir, yDir, zDir, Block_QuantumStuff.INSTANCE, 0, 2);
                }
            } else {
                base.getWorld().setBlock(xDir, yDir, zDir, QuantumStuffBlock.INSTANCE, 0, 2);
            }
        }
    }

    static class FuelNqGenerator implements Runnable {

        public int[] FUEL_PER_SECOND = new int[]{ //for 1A
                20,
                10,
                5,
        };

        public ItemStack[] FUEL_NAME = new ItemStack[]{
                GT_OreDictUnificator.get(OrePrefixes.cell, Materials.NaquadahHeavyFuel, 1),
                GT_OreDictUnificator.get(OrePrefixes.cell, Materials.NaquadahMediumFuel, 1),
                GT_OreDictUnificator.get(OrePrefixes.cell, Materials.NaquadahLightFuel, 1),
        };

        @Override
        public void run() {
            for (int i = 0; i < FUEL_NAME.length; i++)
                RA.addFuel(FUEL_NAME[i], GT_Utility.getFluidForFilledItem(FUEL_NAME[i], true) == null ? GT_Utility.getContainerItem(FUEL_NAME[i], true) : null, FUEL_PER_SECOND[i] * 16, 8);

            registerMetaClass(GTMTE_LiquidNqGenerator.class, new IMultiblockInfoContainer<GTMTE_LiquidNqGenerator>() {
                //region Structure
                private final IStructureDefinition<GTMTE_LiquidNqGenerator> definition =
                        StructureDefinition.<GTMTE_LiquidNqGenerator>builder()
                                .addShape("main", new String[][]{
                                        {"       "," 00 00 "," 0   0 ","       "," 0   0 "," 00 00 ","       "},
                                        {"  000  "," 00000 ","0000000","000~000","0000000"," 00000 ","  000  "},
                                        {"  222  "," 0   0 ","2 1 1 2","2 1 1 2","2 1 1 2"," 0   0 ","  222  "},
                                        {"  222  "," 0   0 ","2     2","2     2","2     2"," 0   0 ","  222  "},
                                        {"  222  "," 0   0 ","2     2","2     2","2     2"," 0   0 ","  222  "},
                                        {"  222  "," 0   0 ","2     2","2     2","2     2"," 0   0 ","  222  "},
                                        {"  222  "," 0   0 ","2 1 1 2","2 1 1 2","2 1 1 2"," 0   0 ","  222  "},
                                        {"  000  "," 00000 ","0000000","0000000","0000000"," 00000 ","  000  "},
                                        {"       "," 00 00 "," 00000 ","  000  "," 00000 "," 00 00 ","       "}
                                })
                                .addElement('0', ofBlock(CORE_API.sCaseCore2, 3))
                                .addElement('1', ofBlock(CORE_API.sCaseCore2, 10))
                                .addElement('2', ofBlock(IGlassBlock, 0))
                                .build();
                private final String[] desc = new String[]{
                        EnumChatFormatting.RED + "Impact Details:",
                        "- Empty Casing",
                        "- I-Glass (any glass)",
                        "- Держатели ядра",
                        "- Hatches (any Casing)",
                        "- Dynamo (any back side structure Casing)",
                };
                //endregion

                @Override
                public void construct(ItemStack stackSize, boolean hintsOnly, GTMTE_LiquidNqGenerator tileEntity, ExtendedFacing aSide) {
                    IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
                    definition.buildOrHints(tileEntity, stackSize, "main", base.getWorld(), aSide,
                            base.getXCoord(), base.getYCoord(), base.getZCoord(),
                            3, 3, 1, hintsOnly);
                }
                @Override
                public String[] getDescription(ItemStack stackSize) {
                    return desc;
                }
            });
        }
    }
}