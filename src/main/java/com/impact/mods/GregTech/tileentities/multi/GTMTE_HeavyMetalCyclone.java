package com.impact.mods.GregTech.tileentities.multi;

import com.github.technus.tectech.mechanics.alignment.enumerable.ExtendedFacing;
import com.github.technus.tectech.mechanics.constructable.IMultiblockInfoContainer;
import com.github.technus.tectech.mechanics.structure.IStructureDefinition;
import com.github.technus.tectech.mechanics.structure.StructureDefinition;
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
import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.input.Keyboard;

import static com.github.technus.tectech.mechanics.constructable.IMultiblockInfoContainer.registerMetaClass;
import static com.github.technus.tectech.mechanics.structure.StructureUtility.ofBlock;
import static com.impact.loader.ItemRegistery.IGlassBlock;
import static com.impact.mods.GregTech.casings.CORE_API.sCaseCore2;
import static gregtech.api.util.GT_Recipe.GT_Recipe_Map.sCyclonRecipes;

public class GTMTE_HeavyMetalCyclone extends GT_MetaTileEntity_MultiParallelBlockBase {


    /**
     * === SET BLOCKS STRUCTURE ===
     */
    Block CASING = CORE_API.sCaseCore2;
    byte CASING_META = 3;
    /**
     * === SET TEXTURES HATCHES AND CONTROLLER ===
     */
    ITexture INDEX_CASE = Textures.BlockIcons.casingTexturePages[3][CASING_META + 16];
    int CASING_TEXTURE_ID = CASING_META + 16 + 128 * 3;

    /**
     * === NAMED ===
     */
    public GTMTE_HeavyMetalCyclone(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
        new build().run();
    }

    /**
     * === NAMED ===
     */
    public GTMTE_HeavyMetalCyclone(String aName) {
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
        return new GTMTE_HeavyMetalCyclone(this.mName);
    }

    /**
     * === DESCRIPTION ===
     */
    @Override
    public String[] getDescription() {
        final MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder();
        b
                .addTypeMachine("Heavy Metal Cyclone")
                .addSeparator()
                .addController()
                .addEnergyHatch("Any casing")
                .addMaintenanceHatch("Any casing")
                .addInputBus("Any casing (max x3)")
                .addInputHatch("Any casing (max x6)")
                .addOutputBus("Any casing (max x6)")
                .addOutputHatch("Any casing (max x1)")
                .addCasingInfo("x Casing")
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

    @Override
    public GT_Recipe.GT_Recipe_Map getRecipeMap() {
        return  GT_Recipe.GT_Recipe_Map.sCyclonRecipes;
    }

    public boolean checkMachine(IGregTechTileEntity thisController, ItemStack guiSlotItem) {
        TThatches();
        final Vector3ic forgeDirection = new Vector3i(
                ForgeDirection.getOrientation(thisController.getBackFacing()).offsetX,
                ForgeDirection.getOrientation(thisController.getBackFacing()).offsetY,
                ForgeDirection.getOrientation(thisController.getBackFacing()).offsetZ);

        int minCasingAmount = 12;
        boolean formationChecklist = true;

        for (byte X = -3; X <= 3; X++) {
            for (byte Z = 1; Z >= -5; Z--) {

                final Vector3ic offset = rotateOffsetVector(forgeDirection, X, -2, Z);

                if (X == -3 && Z == 1 || X == 3 && Z == 1 || X == -3 && Z == -5 || X == 3 && Z == -5) continue;


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

        for (byte X = -2; X <= 2; X++) {
            for (byte Z = 0; Z >= -4; Z--) {

                final Vector3ic offset = rotateOffsetVector(forgeDirection, X, -1, Z);

                if (X >= -1 && X <= 1 && Z >= -3 && Z <= -1) {
                    if (X == 0 && Z == -1 || X == -1 && Z == -2 || X == 1 && Z == -2 || X == 0 && Z == -3) {
                        if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == sCaseCore2)
                                && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 10)) {
                        } else {
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

        for (byte X = -2; X <= 2; X++) {
            for (byte Y = 0; Y <= 2; Y++) {
                for (byte Z = 0; Z >= -4; Z--) {

                    final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Y, Z);

                    if (X == 0 && Z == 0 && Y == 0) continue;

                    if (X == -2 && Z == 0 || X == 2 && Z == 0 || X == -2 && Z == -4 || X == 2 && Z == -4) continue;

                    if (X >= -1 && X <= 1 && Z >= -3 && Z <= -1) {
                        if (X == 0 && Z == -1 || X == -1 && Z == -2 || X == 1 && Z == -2 || X == 0 && Z == -3) {
                            if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == sCaseCore2)
                                    && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 10)) {
                            } else {
                                formationChecklist = false;
                            }
                            continue;
                        }
                        continue;
                    }

                    if (X == -2 && Z == -2 || X == 2 && Z == -2 || X == 0 && Z == -4) {
                        if (thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == IGlassBlock) {
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

        for (byte X = -2; X <= 2; X++) {
            for (byte Z = 0; Z >= -4; Z--) {

                final Vector3ic offset = rotateOffsetVector(forgeDirection, X, 3, Z);

                if (X == -2 && Z == -2 || X == 2 && Z == -2 || X == 0 && Z == -4) {
                    if (thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == IGlassBlock) {
                    } else {
                        formationChecklist = false;
                    }
                    continue;
                }

                if (X >= -1 && X <= 1 && Z >= -3 && Z <= -1) {
                    if (X == 0 && Z == -1 || X == -1 && Z == -2 || X == 1 && Z == -2 || X == 0 && Z == -3) {
                        if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == sCaseCore2)
                                && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 10)) {
                        } else {
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

        for (byte X = -2; X <= 2; X++) {
            for (byte Z = 0; Z >= -4; Z--) {

                final Vector3ic offset = rotateOffsetVector(forgeDirection, X, 4, Z);

                if (X == -2 && Z == 0 || X == 2 && Z == 0 || X == -2 && Z == -4 || X == 2 && Z == -4) continue;

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


//----------------------------------------------------------------------------------------------------------------------

        if (this.mInputBusses.size() > 3) formationChecklist = false;
        if (this.mInputHatches.size() > 6) formationChecklist = false;
        if (this.mOutputBusses.size() > 6) formationChecklist = false;
        if (this.mOutputHatches.size() > 1) formationChecklist = false;
        if (this.mMufflerHatches.size() != 0) formationChecklist = false;
        if (this.mEnergyHatches.size() > 3) formationChecklist = false;
        if (this.mDynamoHatches.size() > 1) formationChecklist = false;
        if (this.mMaintenanceHatches.size() != 1) formationChecklist = false;

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
        return impactRecipe(itemStack);
    }

    static class build implements Runnable {

        @Override
        public void run() {

            registerMetaClass(GTMTE_HeavyMetalCyclone.class, new IMultiblockInfoContainer<GTMTE_HeavyMetalCyclone>() {
                //region Structure
                private final IStructureDefinition<GTMTE_HeavyMetalCyclone> definition =
                        StructureDefinition.<GTMTE_HeavyMetalCyclone>builder()
                                .addShape("main", new String[][]{
                                        {"       ", "       ", "       ", "       ", "       ", "       ", " AAAAA "},
                                        {"  AAA  ", " AAAAA ", "  AAA  ", "  AAA  ", "  A~A  ", " AAAAA ", "AAAAAAA"},
                                        {" AAAAA ", " A B A ", " A B A ", " A B A ", " A B A ", " A B A ", "AAAAAAA"},
                                        {" AAAAA ", " CB BC ", " CB BC ", " CB BC ", " CB BC ", " AB BA ", "AAAAAAA"},
                                        {" AAAAA ", " A B A ", " A B A ", " A B A ", " A B A ", " A B A ", "AAAAAAA"},
                                        {"  AAA  ", " AACAA ", "  ACA  ", "  ACA  ", "  ACA  ", " AAAAA ", "AAAAAAA"},
                                        {"       ", "       ", "       ", "       ", "       ", "       ", " AAAAA "}
                                })
                                .addElement('A', ofBlock(CORE_API.sCaseCore2, 3))
                                .addElement('B', ofBlock(CORE_API.sCaseCore2, 10))
                                .addElement('C', ofBlock(IGlassBlock, 0))
                                .build();
                private final String[] desc = new String[] {
                        EnumChatFormatting.RED + "Impact Details:",
                        "- Empty Casing",
                        "- I-Glass (any glass)",
                        "- Держатели ядра",
                        "- Hatches (any Casing)",
                };
                //endregion

                @Override
                public void construct(ItemStack stackSize, boolean hintsOnly, GTMTE_HeavyMetalCyclone tileEntity, ExtendedFacing aSide) {
                    IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
                    definition.buildOrHints(tileEntity, stackSize, "main", base.getWorld(), aSide,
                            base.getXCoord(), base.getYCoord(), base.getZCoord(),
                            3, 4, 1, hintsOnly);
                }

                @Override
                public String[] getDescription(ItemStack stackSize) {
                    return desc;
                }
            });
        }
    }
}