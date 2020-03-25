package com.impact.mods.GregTech.casings.case2;

import com.impact.mods.GregTech.GTregister.GT_ItemList;
import com.impact.mods.GregTech.tileentities.multi.NuclearReactor.GT_TileEntity_NuclearTurbine;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.enums.Textures;
import gregtech.api.objects.GT_CopiedBlockTexture;
import gregtech.api.util.GT_LanguageManager;
import gregtech.common.blocks.GT_Block_Casings_Abstract;
import gregtech.common.blocks.GT_Material_Casings;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.tileentity.TileEntity;

public class GT_Block_Case2
        extends GT_Block_Casings_Abstract {

    public static boolean mConnectedMachineTextures = true;
    public static final String RES_BLOCK = "gregtech:iconsets/";

    public GT_Block_Case2() {
        super(GT_Item_Case2.class, "gt.blockCase2", GT_Material_Casings.INSTANCE);
        //GT_Utility.addTexturePage((byte) 2);
        for (byte b = 0; b < 16; b = (byte) (b + 1)) {
            Textures.BlockIcons.casingTexturePages[3][b+16] /** 32 */ = new GT_CopiedBlockTexture(this, 6, b);
            /*IMPORTANT for block recoloring*/
        }
        int ENG = 0;
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + "." + ENG++ + ".name", "Nuclear Turbine Casing"); //400

/*================================================================================================================*/
/*================================================================================================================*/

     int CS = 0;
        GT_ItemList.NukeTurbineCase.set(new ItemStack(this, 1, CS++));

    }

    private static IIcon TEXTURES;
    private static Textures.BlockIcons.CustomIcon NUKE_TURBINE_ST1, NUKE_TURBINE_ST2, NUKE_TURBINE_ST3, NUKE_TURBINE_ST4,
    NUKE_TURBINE_ST5, NUKE_TURBINE_ST6, NUKE_TURBINE_ST7, NUKE_TURBINE_ST8, NUKE_TURBINE_ST9;
    @Override
    public void registerBlockIcons(IIconRegister aIconRegister) {
        //TEXTURES = aIconRegister.registerIcon(RES_BLOCK + "TEXTURES");

    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int aSide, int aMeta) {
        switch (aMeta) {
           case  0: return Textures.BlockIcons.MACHINE_CASING_SOLID_STEEL.getIcon();

           default: return Textures.BlockIcons.MACHINE_CASING_SOLID_STEEL.getIcon();
        }
    }

    private IIcon getTurbineCasing(int meta, int iconIndex, boolean active) {
        switch (meta) {
            case 0:
                return active ? Textures.BlockIcons.TURBINE_ACTIVE[iconIndex].getIcon() : Textures.BlockIcons.TURBINE[iconIndex].getIcon();
            default:
                return active ? Textures.BlockIcons.TURBINE_ACTIVE[iconIndex].getIcon() : Textures.BlockIcons.TURBINE[iconIndex].getIcon();
        }
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess aWorld, int xCoord, int yCoord, int zCoord, int aSide) {
        int tMeta = aWorld.getBlockMetadata(xCoord, yCoord, zCoord);
        if ((tMeta != 0) || (!mConnectedMachineTextures)) {
            return getIcon(aSide, tMeta);
        }
        if (tMeta == 0) {
            if ((aSide == 2) || (aSide == 3)) {
                TileEntity tTileEntity;
                IMetaTileEntity tMetaTileEntity;
                if ((null != (tTileEntity = aWorld.getTileEntity(xCoord + (aSide == 3 ? 1 : -1), yCoord - 1, zCoord))) && ((tTileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity) tTileEntity).getFrontFacing() == aSide) && (null != (tMetaTileEntity = ((IGregTechTileEntity) tTileEntity).getMetaTileEntity())) && ((tMetaTileEntity instanceof GT_TileEntity_NuclearTurbine))) {
                    if (((IGregTechTileEntity) tTileEntity).isActive()) {
                        return getTurbineCasing(tMeta, 0, true);
                    }
                    return getTurbineCasing(tMeta, 0, false);
                }
                if ((null != (tTileEntity = aWorld.getTileEntity(xCoord + (aSide == 3 ? 1 : -1), yCoord, zCoord))) && ((tTileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity) tTileEntity).getFrontFacing() == aSide) && (null != (tMetaTileEntity = ((IGregTechTileEntity) tTileEntity).getMetaTileEntity())) && ((tMetaTileEntity instanceof GT_TileEntity_NuclearTurbine))) {
                    if (((IGregTechTileEntity) tTileEntity).isActive()) {
                        return getTurbineCasing(tMeta, 3, true);
                    }
                    return getTurbineCasing(tMeta, 3, false);
                }
                if ((null != (tTileEntity = aWorld.getTileEntity(xCoord + (aSide == 3 ? 1 : -1), yCoord + 1, zCoord))) && ((tTileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity) tTileEntity).getFrontFacing() == aSide) && (null != (tMetaTileEntity = ((IGregTechTileEntity) tTileEntity).getMetaTileEntity())) && ((tMetaTileEntity instanceof GT_TileEntity_NuclearTurbine))) {
                    if (((IGregTechTileEntity) tTileEntity).isActive()) {
                        return getTurbineCasing(tMeta, 6, true);
                    }
                    return getTurbineCasing(tMeta, 6, false);
                }
                if ((null != (tTileEntity = aWorld.getTileEntity(xCoord, yCoord - 1, zCoord))) && ((tTileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity) tTileEntity).getFrontFacing() == aSide) && (null != (tMetaTileEntity = ((IGregTechTileEntity) tTileEntity).getMetaTileEntity())) && ((tMetaTileEntity instanceof GT_TileEntity_NuclearTurbine))) {
                    if (((IGregTechTileEntity) tTileEntity).isActive()) {
                        return getTurbineCasing(tMeta, 1, true);
                    }
                    return getTurbineCasing(tMeta, 1, false);
                }
                if ((null != (tTileEntity = aWorld.getTileEntity(xCoord, yCoord + 1, zCoord))) && ((tTileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity) tTileEntity).getFrontFacing() == aSide) && (null != (tMetaTileEntity = ((IGregTechTileEntity) tTileEntity).getMetaTileEntity())) && ((tMetaTileEntity instanceof GT_TileEntity_NuclearTurbine))) {
                    if (((IGregTechTileEntity) tTileEntity).isActive()) {
                        return getTurbineCasing(tMeta, 7, true);
                    }
                    return getTurbineCasing(tMeta, 7, false);
                }
                if ((null != (tTileEntity = aWorld.getTileEntity(xCoord + (aSide == 2 ? 1 : -1), yCoord + 1, zCoord))) && ((tTileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity) tTileEntity).getFrontFacing() == aSide) && (null != (tMetaTileEntity = ((IGregTechTileEntity) tTileEntity).getMetaTileEntity())) && ((tMetaTileEntity instanceof GT_TileEntity_NuclearTurbine))) {
                    if (((IGregTechTileEntity) tTileEntity).isActive()) {
                        return getTurbineCasing(tMeta, 8, true);
                    }
                    return getTurbineCasing(tMeta, 8, false);
                }
                if ((null != (tTileEntity = aWorld.getTileEntity(xCoord + (aSide == 2 ? 1 : -1), yCoord, zCoord))) && ((tTileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity) tTileEntity).getFrontFacing() == aSide) && (null != (tMetaTileEntity = ((IGregTechTileEntity) tTileEntity).getMetaTileEntity())) && ((tMetaTileEntity instanceof GT_TileEntity_NuclearTurbine))) {
                    if (((IGregTechTileEntity) tTileEntity).isActive()) {
                        return getTurbineCasing(tMeta, 5, true);
                    }
                    return getTurbineCasing(tMeta, 5, false);
                }
                if ((null != (tTileEntity = aWorld.getTileEntity(xCoord + (aSide == 2 ? 1 : -1), yCoord - 1, zCoord))) && ((tTileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity) tTileEntity).getFrontFacing() == aSide) && (null != (tMetaTileEntity = ((IGregTechTileEntity) tTileEntity).getMetaTileEntity())) && ((tMetaTileEntity instanceof GT_TileEntity_NuclearTurbine))) {
                    if (((IGregTechTileEntity) tTileEntity).isActive()) {
                        return getTurbineCasing(tMeta, 2, true);
                    }
                    return getTurbineCasing(tMeta, 2, false);
                }
            } else if ((aSide == 4) || (aSide == 5)) {
                TileEntity tTileEntity;
                Object tMetaTileEntity;
                if ((null != (tTileEntity = aWorld.getTileEntity(xCoord, yCoord - 1, zCoord + (aSide == 4 ? 1 : -1)))) && ((tTileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity) tTileEntity).getFrontFacing() == aSide) && (null != (tMetaTileEntity = ((IGregTechTileEntity) tTileEntity).getMetaTileEntity())) && ((tMetaTileEntity instanceof GT_TileEntity_NuclearTurbine))) {
                    if (((IGregTechTileEntity) tTileEntity).isActive()) {
                        return getTurbineCasing(tMeta, 0, true);
                    }
                    return getTurbineCasing(tMeta, 0, false);
                }
                if ((null != (tTileEntity = aWorld.getTileEntity(xCoord, yCoord, zCoord + (aSide == 4 ? 1 : -1)))) && ((tTileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity) tTileEntity).getFrontFacing() == aSide) && (null != (tMetaTileEntity = ((IGregTechTileEntity) tTileEntity).getMetaTileEntity())) && ((tMetaTileEntity instanceof GT_TileEntity_NuclearTurbine))) {
                    if (((IGregTechTileEntity) tTileEntity).isActive()) {
                        return getTurbineCasing(tMeta, 3, true);
                    }
                    return getTurbineCasing(tMeta, 3, false);
                }
                if ((null != (tTileEntity = aWorld.getTileEntity(xCoord, yCoord + 1, zCoord + (aSide == 4 ? 1 : -1)))) && ((tTileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity) tTileEntity).getFrontFacing() == aSide) && (null != (tMetaTileEntity = ((IGregTechTileEntity) tTileEntity).getMetaTileEntity())) && ((tMetaTileEntity instanceof GT_TileEntity_NuclearTurbine))) {
                    if (((IGregTechTileEntity) tTileEntity).isActive()) {
                        return getTurbineCasing(tMeta, 6, true);
                    }
                    return getTurbineCasing(tMeta, 6, false);
                }
                if ((null != (tTileEntity = aWorld.getTileEntity(xCoord, yCoord - 1, zCoord))) && ((tTileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity) tTileEntity).getFrontFacing() == aSide) && (null != (tMetaTileEntity = ((IGregTechTileEntity) tTileEntity).getMetaTileEntity())) && ((tMetaTileEntity instanceof GT_TileEntity_NuclearTurbine))) {
                    if (((IGregTechTileEntity) tTileEntity).isActive()) {
                        return getTurbineCasing(tMeta, 1, true);
                    }
                    return getTurbineCasing(tMeta, 1, false);
                }
                if ((null != (tTileEntity = aWorld.getTileEntity(xCoord, yCoord + 1, zCoord))) && ((tTileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity) tTileEntity).getFrontFacing() == aSide) && (null != (tMetaTileEntity = ((IGregTechTileEntity) tTileEntity).getMetaTileEntity())) && ((tMetaTileEntity instanceof GT_TileEntity_NuclearTurbine))) {
                    if (((IGregTechTileEntity) tTileEntity).isActive()) {
                        return getTurbineCasing(tMeta, 7, true);
                    }
                    return getTurbineCasing(tMeta, 7, false);
                }
                if ((null != (tTileEntity = aWorld.getTileEntity(xCoord, yCoord + 1, zCoord + (aSide == 5 ? 1 : -1)))) && ((tTileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity) tTileEntity).getFrontFacing() == aSide) && (null != (tMetaTileEntity = ((IGregTechTileEntity) tTileEntity).getMetaTileEntity())) && ((tMetaTileEntity instanceof GT_TileEntity_NuclearTurbine))) {
                    if (((IGregTechTileEntity) tTileEntity).isActive()) {
                        return getTurbineCasing(tMeta, 8, true);
                    }
                    return getTurbineCasing(tMeta, 8, false);
                }
                if ((null != (tTileEntity = aWorld.getTileEntity(xCoord, yCoord, zCoord + (aSide == 5 ? 1 : -1)))) && ((tTileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity) tTileEntity).getFrontFacing() == aSide) && (null != (tMetaTileEntity = ((IGregTechTileEntity) tTileEntity).getMetaTileEntity())) && ((tMetaTileEntity instanceof GT_TileEntity_NuclearTurbine))) {
                    if (((IGregTechTileEntity) tTileEntity).isActive()) {
                        return getTurbineCasing(tMeta, 5, true);
                    }
                    return getTurbineCasing(tMeta, 5, false);
                }
                if ((null != (tTileEntity = aWorld.getTileEntity(xCoord, yCoord - 1, zCoord + (aSide == 5 ? 1 : -1)))) && ((tTileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity) tTileEntity).getFrontFacing() == aSide) && (null != (tMetaTileEntity = ((IGregTechTileEntity) tTileEntity).getMetaTileEntity())) && ((tMetaTileEntity instanceof GT_TileEntity_NuclearTurbine))) {
                    if (((IGregTechTileEntity) tTileEntity).isActive()) {
                        return getTurbineCasing(tMeta, 2, true);
                    }
                    return getTurbineCasing(tMeta, 2, false);
                }
            }
            switch (tMeta) {
                case 0:
                    return Textures.BlockIcons.MACHINE_CASING_SOLID_STEEL.getIcon();
                default:
                    return Textures.BlockIcons.MACHINE_CASING_SOLID_STEEL.getIcon();
            }
        }
        return Textures.BlockIcons.MACHINE_CASING_SOLID_STEEL.getIcon();
    }
}