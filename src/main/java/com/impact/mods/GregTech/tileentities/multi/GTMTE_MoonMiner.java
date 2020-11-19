package com.impact.mods.GregTech.tileentities.multi;

import com.github.technus.tectech.mechanics.alignment.enumerable.ExtendedFacing;
import com.github.technus.tectech.mechanics.constructable.IMultiblockInfoContainer;
import com.github.technus.tectech.mechanics.structure.IStructureDefinition;
import com.github.technus.tectech.mechanics.structure.StructureDefinition;
import com.github.technus.tectech.thing.metaTileEntity.hatch.GT_MetaTileEntity_Hatch_EnergyMulti;
import com.github.technus.tectech.thing.metaTileEntity.hatch.GT_MetaTileEntity_Hatch_EnergyTunnel;
import com.impact.mods.GregTech.blocks.Casing_Helper;
import com.impact.mods.GregTech.tileentities.multi.debug.GT_MetaTileEntity_MultiParallelBlockBase;
import com.impact.util.MultiBlockTooltipBuilder;
import com.impact.util.Vector3i;
import com.impact.util.Vector3ic;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Energy;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_Utility;
import micdoodle8.mods.galacticraft.core.blocks.GCBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.input.Keyboard;

import java.util.Random;

import static com.github.technus.tectech.mechanics.constructable.IMultiblockInfoContainer.registerMetaClass;
import static com.github.technus.tectech.mechanics.structure.StructureUtility.ofBlock;
import static com.impact.loader.ItemRegistery.CollisionBlock;
import static com.impact.util.Utilits.Blockstack;
import static com.mojang.realmsclient.gui.ChatFormatting.*;
import static com.mojang.realmsclient.gui.ChatFormatting.YELLOW;

public class GTMTE_MoonMiner extends GT_MetaTileEntity_MultiParallelBlockBase {

    /**
     * === SET BLOCKS STRUCTURE ===
     */
    Block CASING = Casing_Helper.sCaseCore2;
    byte CASING_META = 12;
    /**
     * === SET TEXTURES HATCHES AND CONTROLLER ===
     */
    ITexture INDEX_CASE = Textures.BlockIcons.casingTexturePages[3][CASING_META + 16];
    int CASING_TEXTURE_ID = CASING_META + 16 + 128 * 3;

    /**
     * === NAMED ===
     */
    public GTMTE_MoonMiner(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
        run();
    }

    /**
     * === NAMED ===
     */
    public GTMTE_MoonMiner(String aName) {
        super(aName);
        run();
    }

    public void run() {
        registerMetaClass(GTMTE_MoonMiner.class, new IMultiblockInfoContainer<GTMTE_MoonMiner>() {
            //region Structure
            private final IStructureDefinition<GTMTE_MoonMiner> definition =
                    StructureDefinition.<GTMTE_MoonMiner>builder()
                            .addShape("main", new String[][]{
                                    {"A   A", "A   A", "A   A"},
                                    {" AAA ", "AA~AA", "     "},
                                    {" AAA ", "AAAAA", "  B  "},
                                    {" AAA ", "AAAAA", "     "},
                                    {"A   A", "A   A", "A   A"}
                            })
                            .addElement('A', ofBlock(CASING, 12))
                            .addElement('B', ofBlock(CollisionBlock, 0))
                            .build();
            private final String[] desc = new String[]{
                    EnumChatFormatting.RED + "Impact Details:",
                    "- Moon Miner Casing",
                    "- Block of Digger",
                    "- Hatches (any Casing)",
            };
            //endregion

            @Override
            public void construct(ItemStack stackSize, boolean hintsOnly, GTMTE_MoonMiner tileEntity, ExtendedFacing aSide) {
                IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
                definition.buildOrHints(tileEntity, stackSize, "main", base.getWorld(), aSide,
                        base.getXCoord(), base.getYCoord(), base.getZCoord(),
                        2, 1, 1, hintsOnly);
            }

            @Override
            public String[] getDescription(ItemStack stackSize) {
                return desc;
            }
        });
    }

    @Override
    public boolean checkRecipe(ItemStack guiSlotItem) {
        boolean check = true;
        this.mMaxProgresstime = 20;
        this.mEfficiency = (10000 - (this.getIdealStatus() - this.getRepairStatus()) * 1000);
        this.mEfficiencyIncrease = 10000;
        int tier = Math.max(1, GT_Utility.getTier(getMaxInputVoltage()));
        this.mEUt = -4 * (1 << (tier << 1));
        if (!depleteInput(new FluidStack(ItemList.sDrillingFluid, 200))) {
            mMaxProgresstime = 0;
            this.mEUt = 0;
            this.mEfficiency = 0;
            check = false;
        }
        return check;
    }

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        super.onPostTick(aBaseMetaTileEntity, aTick);
        Random rand = new Random();//3 4 5 (0-2)
        if (aTick % 20 == 0 && getBaseMetaTileEntity().isActive()) {
            addOutput(Blockstack(GCBlocks.blockMoon, (Math.max(1, GT_Utility.getTier(getMaxInputVoltage())) - 1), rand.nextInt(3) + 3));
        }
    }

    public boolean checkMachine(IGregTechTileEntity thisController, ItemStack guiSlotItem) {
        int ID = getBaseMetaTileEntity().getWorld().provider.dimensionId;
        if (!DimensionManager.getProvider(ID).getClass().getName().contains("Moon")) return false;
        if (getBaseMetaTileEntity().getYCoord() > 70) return false;

        TThatches();
        // Вычисляем вектор направления, в котором находится задняя поверхность контроллера
        final Vector3ic forgeDirection = new Vector3i(
                ForgeDirection.getOrientation(thisController.getBackFacing()).offsetX,
                ForgeDirection.getOrientation(thisController.getBackFacing()).offsetY,
                ForgeDirection.getOrientation(thisController.getBackFacing()).offsetZ);

        int minCasingAmount = 12; // Минимальное количество кейсов
        boolean formationChecklist = true; // Если все ок, машина собралась

        for (byte X = -2; X <= 2; X++) {
            for (byte Z = 1; Z >= -3; Z--) {
                for (byte Y = 0; Y >= -1; Y--) {

                    if (Y == 0 && Z == 0 && X == 0) continue;

                    if ((X == -2 || X == 2) && (Z == 0 || Z == -1 || Z == -2) && (Y == 0)) continue;
                    if ((X == -1 || X == 0 || X == 1) && (Z == 1 || Z == -3) && (Y == 0 || Y == -1)) continue;

                    final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Y, Z);
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
            for (byte Z = 1; Z >= -3; Z--) {

                if (!((X == -2 || X == 2) && (Z == 1 || Z == -3))) continue;

                final Vector3ic offset = rotateOffsetVector(forgeDirection, X, -2, Z);
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

        final Vector3ic offset = rotateOffsetVector(forgeDirection, 0, -2, -1);
        IGregTechTileEntity currentTE = thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
        if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
                && !super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)
                && !super.addMufflerToMachineList(currentTE, CASING_TEXTURE_ID)
                && !super.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)
                && !super.addOutputToMachineList(currentTE, CASING_TEXTURE_ID)) {
            if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CollisionBlock)
                    && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 0)) {
            } else {
                formationChecklist = false;
            }
        }

        if (this.mInputHatches.size() > 1) formationChecklist = false;
        if (this.mOutputBusses.size() > 1) formationChecklist = false;
        if (this.mEnergyHatches.size() > 1) formationChecklist = false;
        if (this.mMaintenanceHatches.size() != 1) formationChecklist = false;

        return formationChecklist;
    }

    @Override
    public int getPollutionPerTick(ItemStack itemStack) {
        return 0;
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        run();
        return new GTMTE_MoonMiner(this.mName);
    }

    @Override
    public String[] getDescription() {
        final MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder();
        b
                .addInfo("Digging rock on the moon, it's simple")
                .addTypeMachine("Block Digger")
                .addSeparator()
                .addController()
                .addEnergyHatch("Any casing")
                .addMaintenanceHatch("Any casing")
                .addOutputBus("Any casing (max x1)")
                .addInputHatch("Any casing (max x1)")
                .addOtherStructurePart("Block of Digger (x1)", "to the very center from below")
                .addCasingInfo("Moon Miner Casing")
                .signAndFinalize(": " + EnumChatFormatting.RED + "IMPACT");
        if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            return b.getInformation();
        } else {
            return b.getStructureInformation();
        }
    }

    @Override
    public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide,
                                 final byte aFacing, final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
        return aSide == aFacing ? new ITexture[]{INDEX_CASE, new GT_RenderedTexture(aActive ? Textures.BlockIcons.MP1a : Textures.BlockIcons.MP1)} : new ITexture[]{INDEX_CASE};
    }
}
