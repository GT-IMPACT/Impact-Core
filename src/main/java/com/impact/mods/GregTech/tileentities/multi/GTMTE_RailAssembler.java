package com.impact.mods.GregTech.tileentities.multi;

import com.github.technus.tectech.mechanics.alignment.enumerable.ExtendedFacing;
import com.github.technus.tectech.mechanics.constructable.IMultiblockInfoContainer;
import com.github.technus.tectech.mechanics.structure.IStructureDefinition;
import com.github.technus.tectech.mechanics.structure.StructureDefinition;
import com.github.technus.tectech.thing.metaTileEntity.hatch.GT_MetaTileEntity_Hatch_EnergyMulti;
import com.github.technus.tectech.thing.metaTileEntity.hatch.GT_MetaTileEntity_Hatch_EnergyTunnel;
import com.impact.mods.GregTech.blocks.Casing_Helper;
import com.impact.mods.GregTech.enums.Texture;
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
import gregtech.api.util.GT_Utility;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.input.Keyboard;

import java.util.HashSet;

import static com.github.technus.tectech.mechanics.constructable.IMultiblockInfoContainer.registerMetaClass;
import static com.github.technus.tectech.mechanics.structure.StructureUtility.ofBlock;
import static com.impact.loader.ItemRegistery.IGlassBlock;
import static com.impact.mods.GregTech.blocks.Casing_Helper.sCaseCore2;
import static com.mojang.realmsclient.gui.ChatFormatting.*;
import static com.mojang.realmsclient.gui.ChatFormatting.YELLOW;
import static gregtech.api.enums.GT_Values.E;
import static gregtech.api.enums.GT_Values.RES_PATH_GUI;

public class GTMTE_RailAssembler extends GT_MetaTileEntity_MultiParallelBlockBase {

    Block CASING = Casing_Helper.sCaseCore2;
    byte CASING_META = 13;
    ITexture INDEX_CASE = Textures.BlockIcons.casingTexturePages[3][CASING_META + 16];
    int CASING_TEXTURE_ID = CASING_META + 16 + 128 * 3;

    public static final GT_Recipe.GT_Recipe_Map sTrackAssemblerRecipes = new GT_Recipe.GT_Recipe_Map(
            new HashSet<GT_Recipe>(1000),
            "impact.recipe.railassembler",
            "Rail Assembler",
            null,
            RES_PATH_GUI + "basic/RailAssembler",
            6, 1, 0, 0,
            1, E, 1, E, true, false
    );

    @Override
    public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide, final byte aFacing, final byte aColorIndex, final boolean aActive, final boolean aRedstone)  {
        return aSide == aFacing ? new ITexture[]{INDEX_CASE, new GT_RenderedTexture(aActive ? Texture.Icons.OVERLAY_RAIL_ASSEMBLER_ACTIVE : Texture.Icons.OVERLAY_RAIL_ASSEMBLER)} : new ITexture[]{INDEX_CASE};
    }

    public GTMTE_RailAssembler(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
        holo();
    }

    public GTMTE_RailAssembler(String aName) {
        super(aName);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GTMTE_RailAssembler(this.mName);
    }

    @Override
    public String[] getDescription() {
        final MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder();
        b
                .addInfo("")
                .addTypeMachine("Rail Assembler")
                .addSeparator()
                .addController()
                .addEnergyHatch("Any casing")
                .addMaintenanceHatch("Any casing")
                .addInputBus("Any casing (max x5)")
                .addOutputBus("Any casing (max x1)")
                .addCasingInfo("Rail Assembler Casing")
                .signAndFinalize(": "+EnumChatFormatting.RED+"IMPACT");
        if(!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            return b.getInformation();
        } else {
            return b.getStructureInformation();
        }
    }

    @Override
    public GT_Recipe.GT_Recipe_Map getRecipeMap() {
        return sTrackAssemblerRecipes;
    }

    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GUI_BASE(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "MultiParallelBlockGUI.png");
    }

    public boolean checkMachine(IGregTechTileEntity thisController, ItemStack guiSlotItem) {
        TThatches();
        final Vector3ic forgeDirection = new Vector3i(
                ForgeDirection.getOrientation(thisController.getBackFacing()).offsetX,
                ForgeDirection.getOrientation(thisController.getBackFacing()).offsetY,
                ForgeDirection.getOrientation(thisController.getBackFacing()).offsetZ);
        boolean formationChecklist = true;

        for(byte X = 0; X <= 2; X++) {
            for (byte Y = -1; Y <= 0; Y++) {
                for (byte Z = 0; Z <= 3; Z++) {

                    if (X == 0 && Z == 0 && Y == 0) continue;
                    if (X == 0 && Z != 0) continue;

                    final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Y, Z);
                    IGregTechTileEntity currentTE = thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
                    if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
                            && !super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)
                            && !super.addMufflerToMachineList(currentTE, CASING_TEXTURE_ID)
                            && !super.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)
                            && !super.addOutputToMachineList(currentTE, CASING_TEXTURE_ID)) {

                        if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
                                && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == CASING_META)) {
                        } else {
                            formationChecklist = false;
                        }
                    }
                }
            }
        }


        if(this.mInputBusses.size() > 5) formationChecklist = false;
        if(this.mOutputBusses.size() > 1) formationChecklist = false;
        if(this.mEnergyHatches.size() > 1) formationChecklist = false;
        if(this.mMaintenanceHatches.size() > 1) formationChecklist = false;

        return formationChecklist;
    }

    public void holo() {
        registerMetaClass(GTMTE_RailAssembler.class, new IMultiblockInfoContainer<GTMTE_RailAssembler>() {
            //region Structure
            private final IStructureDefinition<GTMTE_RailAssembler> definition =
                    StructureDefinition.<GTMTE_RailAssembler>builder()
                            .addShape("main", new String[][]{
                                    {" AA"," AA"},
                                    {" AA"," AA"},
                                    {" AA"," AA"},
                                    {"~AA","AAA"}
                            })
                            .addElement('A', ofBlock(CASING, CASING_META))
                            .build();
            private final String[] desc = new String[]{
                    EnumChatFormatting.RED + "Impact Details:",
                    "- Rail Assembler Casing",
                    "- Hatches (any Rail Assembler Casing)",
            };
            //endregion

            @Override
            public void construct(ItemStack stackSize, boolean hintsOnly, GTMTE_RailAssembler tileEntity, ExtendedFacing aSide) {
                IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
                definition.buildOrHints(tileEntity, stackSize, "main", base.getWorld(), aSide,
                        base.getXCoord(), base.getYCoord(), base.getZCoord(),
                        0, 0, 3, hintsOnly);
            }

            @Override
            public String[] getDescription(ItemStack stackSize) {
                return desc;
            }
        });
    }

    @Override
    public boolean checkRecipe(ItemStack itemStack) {
        return impactRecipe(itemStack);
    }

    @Override
    public int getPollutionPerTick(ItemStack aStack) {
            return 0;
    }
}