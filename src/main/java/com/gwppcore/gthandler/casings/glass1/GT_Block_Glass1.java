package com.gwppcore.gthandler.casings.glass1;

        import com.gwppcore.gthandler.CustomItemList;
        import com.gwppcore.gthandler.casings.case1.GT_Block_Case1;
        import com.gwppcore.gthandler.tileentities.multi.GT_TileEntity_Centrifuge;
        import com.gwppcore.gthandler.tileentities.multi.NuclearReactor.GT_TileEntity_NuclearTurbine;
        import cpw.mods.fml.common.FMLCommonHandler;
        import cpw.mods.fml.relauncher.Side;
        import cpw.mods.fml.relauncher.SideOnly;
        import gregtech.api.enums.Textures;
        import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
        import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
        import gregtech.api.objects.GT_CopiedBlockTexture;
        import gregtech.api.util.GT_LanguageManager;
        import gregtech.api.util.GT_Utility;
        import gregtech.common.blocks.GT_Block_Casings_Abstract;
        import gregtech.common.blocks.GT_Material_Casings;
        import net.minecraft.block.Block;
        import net.minecraft.client.renderer.texture.IIconRegister;
        import net.minecraft.init.Blocks;
        import net.minecraft.item.ItemStack;
        import net.minecraft.tileentity.TileEntity;
        import net.minecraft.util.Facing;
        import net.minecraft.util.IIcon;
        import net.minecraft.world.IBlockAccess;
        import net.minecraft.world.World;
        import net.minecraftforge.common.util.ForgeDirection;

public class GT_Block_Glass1
        extends GT_Block_Casings_Abstract {

    @SideOnly(Side.CLIENT)
    private IIcon[] connectedTexture;
    private  boolean connectedTex;

    public static final String RES_BLOCK = "gregtech:iconsets/";

    public GT_Block_Glass1() {
        super(GT_Item_Glass1.class, "gt.blockGlass1", GT_Material_Casings.INSTANCE);
        for (byte b = 0; b < 16; b = (byte) (b + 1)) {
            Textures.BlockIcons.casingTexturePages[3][b + 80] /** 80 */= new GT_CopiedBlockTexture(this, 6, b);
            /*IMPORTANT for block recoloring*/
        }
        int ENG = 0;
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + "." + ENG++ + ".name", "Glass Core 1"); //464
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + "." + ENG++ + ".name", "Glass Core 2"); //400

        /*================================================================================================================*/
        /*================================================================================================================*/

        int CS = 0;
        CustomItemList.GLASS_CASSING_1.set(new ItemStack(this, 1, CS++));
        CustomItemList.GLASS_CASSING_2.set(new ItemStack(this, 1, CS++));
        setLightOpacity(0);
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    /*================================================================================================================*/

    private static IIcon GLASS_CASSING_1,GLASS_CASSING_1TB, GLASS_CASSING_2, GLASS_CASSING_2TB,
            end;

    @Override
    public void registerBlockIcons(IIconRegister aIconRegister) {
        GLASS_CASSING_1 = aIconRegister.registerIcon(RES_BLOCK + "GLASS_CASSING_1");
        GLASS_CASSING_1TB = aIconRegister.registerIcon(RES_BLOCK + "GLASS_CASSING_1TB");
        GLASS_CASSING_2 = aIconRegister.registerIcon(RES_BLOCK + "GLASS_CASSING_2");
        GLASS_CASSING_2TB = aIconRegister.registerIcon(RES_BLOCK + "GLASS_CASSING_2TB");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int aSide, int aMeta) {
        switch (aMeta) {
            case 0:return aSide == 0 ? GLASS_CASSING_1TB : aSide == 1 ? GLASS_CASSING_1TB : GLASS_CASSING_1;
            case 1:return aSide == 0 ? GLASS_CASSING_2TB : aSide == 1 ? GLASS_CASSING_2TB : GLASS_CASSING_2;

            default:return GLASS_CASSING_2;
        }
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess aWorld, int aX, int aY, int aZ, int aSide)
    {
        Block block = aWorld.getBlock(aX, aY, aZ);

        if (this == Blocks.glass || this instanceof GT_Block_Glass1)
        {
            if (aWorld.getBlockMetadata(aX, aY, aZ) != aWorld.getBlockMetadata(aX - Facing.offsetsXForSide[aSide], aY - Facing.offsetsYForSide[aSide], aZ - Facing.offsetsZForSide[aSide]))
            {
                return true;
            }
            if(aWorld.getBlockMetadata(aX, aY, aZ)==0)
                return true;

            if (block == this)
            {
                return false;
            }
        }

        return false;
    }
    @Override
    public int getRenderBlockPass() {
        return 0;
    }
    @Override
    public void onNeighborChange(IBlockAccess world, int x, int y, int z, int tileX, int tileY, int tileZ) {
        super.onNeighborChange(world, x, y, z, tileX, tileY, tileZ);
    }


}
