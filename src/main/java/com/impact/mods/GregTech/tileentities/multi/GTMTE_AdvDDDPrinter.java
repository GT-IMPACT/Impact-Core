package com.impact.mods.GregTech.tileentities.multi;

import com.github.technus.tectech.mechanics.alignment.enumerable.ExtendedFacing;
import com.github.technus.tectech.mechanics.constructable.IMultiblockInfoContainer;
import com.github.technus.tectech.mechanics.structure.IStructureDefinition;
import com.github.technus.tectech.mechanics.structure.StructureDefinition;
import com.github.technus.tectech.thing.metaTileEntity.hatch.GT_MetaTileEntity_Hatch_EnergyMulti;
import com.github.technus.tectech.thing.metaTileEntity.hatch.GT_MetaTileEntity_Hatch_EnergyTunnel;
import com.impact.mods.GregTech.blocks.Casing_Helper;
import com.impact.mods.GregTech.gui.GUI_BASE;
import com.impact.mods.GregTech.tileentities.multi.debug.GT_MetaTileEntity_MultiParallelBlockBase;
import com.impact.util.MultiBlockTooltipBuilder;
import com.impact.util.Vector3i;
import com.impact.util.Vector3ic;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Energy;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_Recipe;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.input.Keyboard;

import static com.github.technus.tectech.mechanics.constructable.IMultiblockInfoContainer.registerMetaClass;
import static com.github.technus.tectech.mechanics.structure.StructureUtility.ofBlock;
import static com.impact.loader.ItemRegistery.IGlassBlock;
import static com.impact.mods.GregTech.blocks.Casing_Helper.sCaseCore2;
import static com.mojang.realmsclient.gui.ChatFormatting.*;
import static com.mojang.realmsclient.gui.ChatFormatting.YELLOW;

public class GTMTE_AdvDDDPrinter extends GT_MetaTileEntity_MultiParallelBlockBase {


    Block CASING = Casing_Helper.sCaseCore2;
    byte CASING_META = 4;
    ITexture INDEX_CASE = Textures.BlockIcons.casingTexturePages[3][16 + CASING_META];
    int CASING_TEXTURE_ID = CASING_META + 16 + 128 * 3;

    public GTMTE_AdvDDDPrinter(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
        holo();
    }

    public GTMTE_AdvDDDPrinter(String aName) {
        super(aName);
    }

    @Override
    public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide, final byte aFacing,
                                 final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
        return aSide == aFacing ? new ITexture[]{INDEX_CASE, new GT_RenderedTexture(aActive ?
                Textures.BlockIcons.MP1a : Textures.BlockIcons.MP1)} : new ITexture[]{INDEX_CASE};
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GTMTE_AdvDDDPrinter(this.mName);
    }

    @Override
    public String[] getDescription() {
        final MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder();
        b
                .addInfo("Assembler machines")
                .addTypeMachine("4x4 Crafting")
                .addScrew()
                .addSeparatedBus()
                .addSeparator()
                .addController()
                .addEnergyHatch("Any casing")
                .addMaintenanceHatch("Any casing")
                .addInputBus("Any casing (max x30)")
                .addOutputBus("Any casing (max x1)")
                .addCasingInfo("3D Printed Casing")
                .addOtherStructurePart("I-Glass", "it is glass")
                .addOtherStructurePart("Configuration Casing 4x4", "core structure")
                .signAndFinalize(": " + EnumChatFormatting.RED + "IMPACT");
        if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            return b.getInformation();
        } else {
            return b.getStructureInformation();
        }
    }

    public void holo() {
        registerMetaClass(GTMTE_AdvDDDPrinter.class, new IMultiblockInfoContainer<GTMTE_AdvDDDPrinter>() {
            //region Structure
            private final IStructureDefinition<GTMTE_AdvDDDPrinter> definition =
                    StructureDefinition.<GTMTE_AdvDDDPrinter>builder()
                            .addShape("main", new String[][]{

                                    {"033330", "~33330", "000000",},
                                    {"033330", "022220", "000000",},
                                    {"033330", "022220", "000000",},
                                    {"033330", "022220", "000000",},
                                    {"033330", "022220", "000000",},
                                    {"033330", "033330", "000000",},
                            })
                            .addElement('0', ofBlock(sCaseCore2, 4))
                            .addElement('2', ofBlock(sCaseCore2, 6))
                            .addElement('3', ofBlock(IGlassBlock, 0))
                            .build();
            private final String[] desc = new String[]{
                    EnumChatFormatting.RED + "Impact Details:",
                    "- 3D Printed Casing",
                    "- I-Glass (any I-Glass)",
                    "- Configuration Casing (4x4)",
                    "- Hatches (any 3D Printed Casing)",
            };
            //endregion

            @Override
            public void construct(ItemStack stackSize, boolean hintsOnly, GTMTE_AdvDDDPrinter tileEntity, ExtendedFacing aSide) {
                IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
                definition.buildOrHints(tileEntity, stackSize, "main", base.getWorld(), aSide,
                        base.getXCoord(), base.getYCoord(), base.getZCoord(),
                        0, 1, 0, hintsOnly);
            }

            @Override
            public String[] getDescription(ItemStack stackSize) {
                return desc;
            }
        });
    }

    @Override
    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GUI_BASE(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "MultiParallelBlockGUI.png", "4x4 Crafting");
    }

    @Override
    public boolean checkRecipe(ItemStack itemStack) {
        return impactRecipe(itemStack);
    }

    @Override
    public GT_Recipe.GT_Recipe_Map getRecipeMap() {
        return GT_Recipe.GT_Recipe_Map.sBasicline;
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

        for (byte X = 0; X <= 5; X++) {
            for (byte Y = -1; Y <= 1; Y++) {
                for (int Z = 0; Z >= -5; Z--) {

                    if (X == 0 && Z == 0 && Y == 0) continue;
                    final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Y, Z);

                    if ((X == 1 || X == 2 || X == 3 || X == 4) && (Z == -1 || Z == -2 || Z == -3 || Z == -4) && Y == 0) {
                        if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == Casing_Helper.sCaseCore2)
                                && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 6)) {
                        } else {
                            formationChecklist = false;
                        }
                        continue;

                    }

                    if ((X == 1 || X == 2 || X == 3 || X == 4) && (Z == 0 || Z == -5) && Y == 0) {
                        if (thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == IGlassBlock) {
                        } else {
                            formationChecklist = false;
                        }
                        continue;
                    }

                    if ((X == 1 || X == 2 || X == 3 || X == 4) && Y == 1) {
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


        if (this.mInputBusses.size() > 30) formationChecklist = false;
        if (this.mOutputBusses.size() > 1) formationChecklist = false;
        if (this.mEnergyHatches.size() > 4) formationChecklist = false;
        if (this.mMaintenanceHatches.size() != 1) formationChecklist = false;

        return formationChecklist;
    }

    @Override
    public int getPollutionPerTick(ItemStack aStack) {
        return 0;
    }

    public void onScrewdriverRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
        super.onScrewdriverRightClick(aSide, aPlayer, aX, aY, aZ);
        if (aPlayer.isSneaking()) ScrewClick(aSide, aPlayer, aX, aY, aZ);
    }

    @Override
    public int getParallel() {
        return -1;
    }

    @Override
    public String[] getInfoData() {
        long storedEnergy = 0;
        long maxEnergy = 0;
        long pollut = 0;
        for (GT_MetaTileEntity_Hatch_Energy tHatch : mEnergyHatches) {
            if (isValidMetaTileEntity(tHatch)) {
                storedEnergy += tHatch.getBaseMetaTileEntity().getStoredEU();
                maxEnergy += tHatch.getBaseMetaTileEntity().getEUCapacity();
            }
        }
        for (GT_MetaTileEntity_Hatch_EnergyMulti tEHatch : mEnergyHatchesTT) {
            if (isValidMetaTileEntity(tEHatch)) {
                storedEnergy += tEHatch.getBaseMetaTileEntity().getStoredEU();
                maxEnergy += tEHatch.getBaseMetaTileEntity().getEUCapacity();
            }
        }
        for (GT_MetaTileEntity_Hatch_EnergyTunnel tEHatch : mEnergyTunnelsTT) {
            if (isValidMetaTileEntity(tEHatch)) {
                storedEnergy += tEHatch.getBaseMetaTileEntity().getStoredEU();
                maxEnergy += tEHatch.getBaseMetaTileEntity().getEUCapacity();
            }
        }

        return new String[]{
                "Progress: " + GREEN + mProgresstime / 20 + RESET + " s / " + mMaxProgresstime / 20 + RESET + " s",
                "Storage: " + GREEN + storedEnergy + RESET + " / " + RESET + YELLOW + maxEnergy + RESET + " EU",
                "Usage Energy: " + RED + -mEUt + RESET + " EU/t",
                "Max Voltage: " + YELLOW + getMaxInputVoltage() + RESET + " EU/t ",
                "Maintenance: " + ((super.getRepairStatus() == super.getIdealStatus()) ? GREEN + "Good " + YELLOW + mEfficiency / 100.0F + " %" + RESET : RED + "Has Problems " + mEfficiency / 100.0F + " %" + RESET),
                "Pollution: " + RED + getPollutionPerTick(null) + RESET,
                getParallel()==-1? "" : "Parallel Point: " + YELLOW + getParallel(),
        };
    }

    @Override
    public boolean isGivingInformation() {
        return true;
    }
}