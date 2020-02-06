package com.gwppcore.gthandler.tileentities.multi;

import com.gwppcore.util.MultiBlockTooltipBuilder;
import gregtech.api.GregTech_API;
import gregtech.api.enums.Textures;
import gregtech.api.gui.GT_GUIContainer_MultiMachine;
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

public class GT_TileEntity_Bender extends GT_MetaTileEntity_MultiParallelBlockBase {

    /** === SET OVERLAY CONTROLER === */
    Textures.BlockIcons INDEX_OVERLAY_ACTIVE = Textures.BlockIcons.OVERLAY_FRONT_VACUUM_FREEZER_ACTIVE;
    Textures.BlockIcons INDEX_OVERLAY= Textures.BlockIcons.OVERLAY_FRONT_VACUUM_FREEZER;

    /** === SET GUI CONTROLER === */
    String INDEX_GUI = "VacuumFreezer.png";

    /** === SET TEXTURES HATCHES AND CONTROLLER === */
    byte INDEX_CASE = 17;

    /** === SET BLOCKS STRUCTURE === */
    Block INDEX_PAGE = GregTech_API.sBlockCasings2;
    byte INDEX_CASE_PAGE = 1;

    /** === SET BLOCKS STRUCTURE PARALLEL UPGRADE === */
    Block INDEX_PAGE_PARALLEL = GregTech_API.sBlockCasings5;

    /** === NAMED === */
    public GT_TileEntity_Bender(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }
    /** === NAMED === */
    public GT_TileEntity_Bender(String aName) {
        super(aName);
    }

    /** === META ENTITY === */
    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_TileEntity_Bender(this.mName);
    }

    /** === DESCRIPTION === */
    @Override
    public String[] getDescription() {
        final MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder();
        b.addInfo("Boo, he's bad! We want BENDER")
                .addInfo("One-block machine analog")
                .addParallelInfo(4,256)
                .addInfo("Parallel Point will upped Upgrade Casing")
                .addPollution(4*50, 256*50)
                .addSeparator()
                .beginStructureBlock(3, 3, 3)
                .addController("Front middle center")
                .addParallelCase("Middle Center")
                .addEnergyHatch("Any casing")
                .addMaintenanceHatch("Any casing")
                .addInputBus("Any casing (only x1)")
                .addOutputBus("Any casing (only x1)")
                .addCasingInfo("Bending Casing", 18)
                .signAndFinalize(": "+EnumChatFormatting.RED+"IMPACT");
        if(!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            return b.getInformation();
        } else {
            return b.getStructureInformation();
        }
    }

    /** === TEXTURE === */
    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
        return aSide == aFacing ? new ITexture[]{Textures.BlockIcons.CASING_BLOCKS[INDEX_CASE], new GT_RenderedTexture(aActive ?  INDEX_OVERLAY_ACTIVE : INDEX_OVERLAY)} : new ITexture[]{Textures.BlockIcons.CASING_BLOCKS[INDEX_CASE]};
    }

    /** === GUI === */
    @Override
    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_GUIContainer_MultiMachine(aPlayerInventory, aBaseMetaTileEntity, this.getLocalName(), INDEX_GUI);
    }

    /** === RECIPE MAP === */
    @Override
    public GT_Recipe.GT_Recipe_Map getRecipeMap() {
        return GT_Recipe.GT_Recipe_Map.sBenderRecipes;
    }

    /** === CHECK STRUCTURE === */
    private int mLevel = 0;
    public boolean checkMachineFunction(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        int xDir = ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()).offsetX;
        int zDir = ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()).offsetZ;
        int casingAmount = 0;
        this.mLevel = 0;
        byte tUsedMeta = aBaseMetaTileEntity.getMetaIDOffset(xDir, 0, zDir);
        switch (tUsedMeta) {
            case 0:
                this.mLevel = 4;
                break;
            case 1:
                this.mLevel = 16;
                break;
            case 2:
                this.mLevel = 64;
                break;
            case 3:
                this.mLevel = 256;
                break;
            default:
                return false;
        }
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 1; j++) {
                if ((xDir + i != 0) || (zDir + j != 0)) {
                    if (aBaseMetaTileEntity.getBlockOffset(xDir + i, 0, zDir + j) != INDEX_PAGE_PARALLEL) {
                        return false;
                    }
                    if (aBaseMetaTileEntity.getMetaIDOffset(xDir + i, 0, zDir + j) != tUsedMeta) {
                        return false;
                    }
                }
            }
        }
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                for (int h = -1; h < 2; h++) {
                    if ((h != 0) || (((xDir + i != 0) || (zDir + j != 0)) && ((i != 0) || (j != 0)))) {
                        /** todo info hathes
                         Muffler       -   addMufflerToMachineList(tTileEntity, INDEX_CASE)
                         InputBus      -   addInputBusParallel(tTileEntity, INDEX_CASE)
                         InputHatch    -   addInputHatchParallel(tTileEntity, INDEX_CASE)
                         OutputBus     -   addOutputBusParallel(tTileEntity, INDEX_CASE)
                         OutputHatch   -   addOutputHatchParallel(tTileEntity, INDEX_CASE)
                         Maintenance   -   addMaintenanceToMachineList(tTileEntity, INDEX_CASE)
                         EnergyInput   -   addEnergyInputToMachineList(tTileEntity, INDEX_CASE)
                        */
                        IGregTechTileEntity tTileEntity = aBaseMetaTileEntity.getIGregTechTileEntityOffset(xDir + i, h, zDir + j);
                        if ((!addMaintenanceToMachineList(tTileEntity, INDEX_CASE))
                        &&  (!addMufflerToMachineList(tTileEntity, INDEX_CASE))
                        &&  (!addInputBusParallel(tTileEntity, INDEX_CASE))
                        &&  (!addOutputBusParallel(tTileEntity, INDEX_CASE))
                        &&  (!addEnergyInputToMachineList(tTileEntity, INDEX_CASE))) {
                            if (aBaseMetaTileEntity.getBlockOffset(xDir + i, h, zDir + j) != INDEX_PAGE) {
                                return false;
                            }
                            if (aBaseMetaTileEntity.getMetaIDOffset(xDir + i, h, zDir + j) != INDEX_CASE_PAGE) {
                                return false;
                            }
                            casingAmount++;
                        }
                    }
                }
            }
        }
        return casingAmount >= 20;
    }


    /** === CHECK STRUCTURE 2 === */
    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack){
        boolean result= this.checkMachineFunction(aBaseMetaTileEntity,aStack);
        if (!result) this.mLevel=0;
        return result;
    }

    /** === SET PARALLEL === */
    public int Parallel() {
        return this.mLevel;
    }

    /** === POLLUTION === */
    @Override
    public int getPollutionPerTick(ItemStack aStack) {
        if (this.mLevel == 4 ) {
            return 4*50;
        }
        else if (this.mLevel == 16 ) {
            return 16*50;
        }
        else if (this.mLevel == 64 ) {
            return 64*50;
        }
        else if (this.mLevel == 256) {
            return 256*50;
        } else
            return 0;
    } //NOT USE WITHOUT MUFFLER IN STRUCTURE
}