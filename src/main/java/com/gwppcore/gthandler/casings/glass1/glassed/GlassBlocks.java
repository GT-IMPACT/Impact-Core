package com.gwppcore.gthandler.casings.glass1.glassed;

import com.gwppcore.creativetab.ModTabList;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import eu.usrv.yamcore.blocks.BlockBase;
import gregtech.api.GregTech_API;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import static com.gwppcore.lib.Refstrings.MODID;

public class GlassBlocks extends BlockBase {

    public static IIcon stuff;
    public static int renderID;
    public static GlassBlocks INSTANCE;

    public GlassBlocks() {
        super(Material.iron);
        setBlockBounds(0, 0, 0, 1, 1, 1);
        setBlockName("GlassBlock1");
        setHarvestLevel("wrench", 1);
        setHardness(10);
        setResistance(10);
        setLightOpacity(0);
        setStepSound(Block.soundTypeMetal);
        setBlockTextureName(MODID + ":blockGlassBlock1");
    }

    @Override
    public boolean isBeaconBase(IBlockAccess worldObj, int x, int y, int z, int beaconX, int beaconY, int beaconZ) {
        return true;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean getCanBlockGrass() {
        return false;
    }

    @Override
    public boolean canBeReplacedByLeaves(IBlockAccess world, int x, int y, int z) {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderBlockPass() {
        return 1;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_) {
        Block block = p_149646_1_.getBlock(p_149646_2_, p_149646_3_, p_149646_4_);
        return block != this;
    }

    @Override
    public int getRenderType() {
        return renderID;
    }


    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister p_149651_1_) {
        super.registerBlockIcons(p_149651_1_);
        stuff = blockIcon;
    }

    public static void run() {
        INSTANCE = new GlassBlocks();
        GameRegistry.registerBlock(INSTANCE, GlassBlocksItem.class, INSTANCE.getUnlocalizedName());
        GregTech_API.registerMachineBlock(INSTANCE, -1);
    }

    @Override
    public void breakBlock(World aWorld, int aX, int aY, int aZ, Block aBlock, int aMeta) {
        if (GregTech_API.isMachineBlock(this, aWorld.getBlockMetadata(aX, aY, aZ))) {
            GregTech_API.causeMachineUpdate(aWorld, aX, aY, aZ);
        }

    }

    @Override
    public void onBlockAdded(World aWorld, int aX, int aY, int aZ) {
        if (GregTech_API.isMachineBlock(this, aWorld.getBlockMetadata(aX, aY, aZ))) {
            GregTech_API.causeMachineUpdate(aWorld, aX, aY, aZ);
        }
    }

}
