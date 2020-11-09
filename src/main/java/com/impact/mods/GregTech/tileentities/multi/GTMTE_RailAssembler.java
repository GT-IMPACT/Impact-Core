package com.impact.mods.GregTech.tileentities.multi;

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

import static com.impact.loader.ItemRegistery.IGlassBlock;
import static gregtech.api.enums.GT_Values.E;
import static gregtech.api.enums.GT_Values.RES_PATH_GUI;

public class GTMTE_RailAssembler extends GT_MetaTileEntity_MultiParallelBlockBase {

    Block CASING = Casing_Helper.sCaseCore1;
    byte CASING_META = 14;
    ITexture INDEX_CASE = Textures.BlockIcons.casingTexturePages[3][CASING_META];
    int CASING_TEXTURE_ID = CASING_META + 128*3;

    public static final GT_Recipe.GT_Recipe_Map sTrackAssemblerRecipes = new GT_Recipe.GT_Recipe_Map(
            new HashSet<GT_Recipe>(1000),
            "impact.recipe.trackassembler",
            "Track Assembler",
            null,
            RES_PATH_GUI + "basicmachines/Default",
            6, 1, 0, 0,
            1, E, 1, E, true, true
    );

    @Override
    public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide, final byte aFacing, final byte aColorIndex, final boolean aActive, final boolean aRedstone)  {
        return aSide == aFacing ? new ITexture[]{INDEX_CASE, new GT_RenderedTexture(aActive ? Textures.BlockIcons.MP1a : Textures.BlockIcons.MP1)} : new ITexture[]{INDEX_CASE};
    }

    public GTMTE_RailAssembler(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
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
                .addScrew()
                .addSeparatedBus()
                .addSeparator()
                .addController()
//                .addEnergyHatch("Any casing")
//                .addMaintenanceHatch("Any casing")
//                .addInputBus("Any casing (max x16)")
//                .addInputHatch("Any casing (max x3)")
//                .addOutputBus("Any casing (max x1)")
                .addCasingInfo(" Casing")
                .signAndFinalize(": "+EnumChatFormatting.RED+"IMPACT");
        if(!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            return b.getInformation();
        } else {
            return b.getStructureInformation();
        }
    }

    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GUI_BASE(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "MultiParallelBlockGUI.png");
    }

    public boolean checkMachine(IGregTechTileEntity thisController, ItemStack guiSlotItem) {
        return true;
    }

    @Override
    public int getParallel() {
        return -1;
    }

    @Override
    public boolean checkRecipe(ItemStack itemStack) {
        return true;
    }

    @Override
    public int getPollutionPerTick(ItemStack aStack) {
            return 0;
    }

}