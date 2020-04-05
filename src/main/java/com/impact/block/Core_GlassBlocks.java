package com.impact.block;

import com.impact.System.Refstrings;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.GregTech_API;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class Core_GlassBlocks extends Core_Blocks {

    @SideOnly(Side.CLIENT)
    private IIcon[] connectedTexture;

    private final boolean connectedTex;
    private boolean fake;
    //private short[][] color = new short[this.textureNames.length][3];

    public Core_GlassBlocks(String name, String[] texture, CreativeTabs tabs) {
        super(name, texture, tabs, Material.glass);
        this.connectedTex = false;
    }


    public Core_GlassBlocks(String name, String[] texture, CreativeTabs tabs, boolean connectedTex, boolean fake) {
        super(name, texture, tabs, Material.glass);
        setBlockBounds(0, 0, 0, 1, 1, 1);
        setHarvestLevel("pickaxe", 1);
        setHarvestLevel("wrench", 1);
        setHardness(5);
        setResistance(5);
        setLightOpacity(0);
        setStepSound(Block.soundTypeGlass);
        this.connectedTex = connectedTex;
        //this.color = color;
        this.fake = fake;
    }

    //public short[] getColor(int meta) {
    //    return meta < this.texture.length ? this.color[meta] : this.color[0];
    //}

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess worldClient, int xCoord, int yCoord, int zCoord, int aSide) {
        if (worldClient.getBlock(xCoord, yCoord, zCoord) instanceof Core_GlassBlocks)
            return false;
        return super.shouldSideBeRendered(worldClient, xCoord, yCoord, zCoord, aSide);
    }


    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return meta < this.texture.length ? this.texture[meta] : this.texture[0];
    }



    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister par1IconRegister) {
        if (!this.connectedTex) {
            this.texture = new IIcon[this.textureNames.length];
            for (int i = 0; i < this.textureNames.length; i++) {
                this.texture[i] = par1IconRegister.registerIcon(this.textureNames[i]);
            }
            return;
        }
        this.texture = new IIcon[this.textureNames.length];
        this.connectedTexture = new IIcon[16];
        for (int i = 0; i < this.textureNames.length; i++) {
            this.texture[i] = par1IconRegister.registerIcon(this.textureNames[i]);
            for (int j = 0; j < 16; j++) {
                this.connectedTexture[j] = par1IconRegister.registerIcon(this.textureNames[0] + "_" + j);
            }
        }
    }




    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess worldClient, int xCoord, int yCoord, int zCoord, int aSide) {
        if (!this.connectedTex)
            return super.getIcon(worldClient, xCoord, yCoord, zCoord, aSide);

        ForgeDirection dir = ForgeDirection.getOrientation(aSide);
        byte sides = 0;
        switch (dir) {
            case UP:
            case DOWN: {
                if (worldClient.getBlock(xCoord, yCoord, zCoord - 1) instanceof Core_GlassBlocks)
                    sides = (byte) (sides | 0b0001);
                if (worldClient.getBlock(xCoord, yCoord, zCoord + 1) instanceof Core_GlassBlocks)
                    sides = (byte) (sides | 0b0010);
                if (worldClient.getBlock(xCoord - 1, yCoord, zCoord) instanceof Core_GlassBlocks)
                    sides = (byte) (sides | 0b0100);
                if (worldClient.getBlock(xCoord + 1, yCoord, zCoord) instanceof Core_GlassBlocks)
                    sides = (byte) (sides | 0b1000);
                break;
            }
            case EAST: {
                if (worldClient.getBlock(xCoord, yCoord + 1, zCoord) instanceof Core_GlassBlocks)
                    sides = (byte) (sides | 0b0001);
                if (worldClient.getBlock(xCoord, yCoord - 1, zCoord) instanceof Core_GlassBlocks)
                    sides = (byte) (sides | 0b0010);
                if (worldClient.getBlock(xCoord, yCoord, zCoord + 1) instanceof Core_GlassBlocks)
                    sides = (byte) (sides | 0b0100);
                if (worldClient.getBlock(xCoord, yCoord, zCoord - 1) instanceof Core_GlassBlocks)
                    sides = (byte) (sides | 0b1000);
                break;
            }
            case WEST: {
                if (worldClient.getBlock(xCoord, yCoord + 1, zCoord) instanceof Core_GlassBlocks)
                    sides = (byte) (sides | 0b0001);
                if (worldClient.getBlock(xCoord, yCoord - 1, zCoord) instanceof Core_GlassBlocks)
                    sides = (byte) (sides | 0b0010);
                if (worldClient.getBlock(xCoord, yCoord, zCoord - 1) instanceof Core_GlassBlocks)
                    sides = (byte) (sides | 0b0100);
                if (worldClient.getBlock(xCoord, yCoord, zCoord + 1) instanceof Core_GlassBlocks)
                    sides = (byte) (sides | 0b1000);
                break;

            }
            case NORTH: {
                if (worldClient.getBlock(xCoord, yCoord + 1, zCoord) instanceof Core_GlassBlocks)
                    sides = (byte) (sides | 0b0001);
                if (worldClient.getBlock(xCoord, yCoord - 1, zCoord) instanceof Core_GlassBlocks)
                    sides = (byte) (sides | 0b0010);
                if (worldClient.getBlock(xCoord + 1, yCoord, zCoord) instanceof Core_GlassBlocks)
                    sides = (byte) (sides | 0b0100);
                if (worldClient.getBlock(xCoord - 1, yCoord, zCoord) instanceof Core_GlassBlocks)
                    sides = (byte) (sides | 0b1000);
                break;
            }
            case SOUTH: {
                if (worldClient.getBlock(xCoord, yCoord + 1, zCoord) instanceof Core_GlassBlocks)
                    sides = (byte) (sides | 0b0001);
                if (worldClient.getBlock(xCoord, yCoord - 1, zCoord) instanceof Core_GlassBlocks)
                    sides = (byte) (sides | 0b0010);
                if (worldClient.getBlock(xCoord - 1, yCoord, zCoord) instanceof Core_GlassBlocks)
                    sides = (byte) (sides | 0b0100);
                if (worldClient.getBlock(xCoord + 1, yCoord, zCoord) instanceof Core_GlassBlocks)
                    sides = (byte) (sides | 0b1000);
                break;
            }
            case UNKNOWN:
            default: {
                break;
            }
        }
        return this.connectedTexture[sides];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderBlockPass() {
        return 1;
    }

    @Override
    public int getRenderType() {
        if (!this.fake && FMLCommonHandler.instance().getSide().isClient())
            return RenderGlassBlock.RID;
        else
            return 0;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    protected boolean canSilkHarvest() {
        return false;
    }

    public static void run() {
            GregTech_API.registerMachineBlock(glass2,  -1);
            GregTech_API.registerMachineBlock(glass3,  -1);
            GregTech_API.registerMachineBlock(glass4,  -1);
            GregTech_API.registerMachineBlock(glass5,  -1);
            GregTech_API.registerMachineBlock(glass6,  -1);
            GregTech_API.registerMachineBlock(glass7,  -1);
            GregTech_API.registerMachineBlock(glass8,  -1);
            GregTech_API.registerMachineBlock(glass9,  -1);
            GregTech_API.registerMachineBlock(glass10, -1);
            GregTech_API.registerMachineBlock(glass11, -1);
            GregTech_API.registerMachineBlock(glass12, -1);
            GregTech_API.registerMachineBlock(glass13, -1);
            GregTech_API.registerMachineBlock(glass14, -1);
            GregTech_API.registerMachineBlock(glass15, -1);
            GregTech_API.registerMachineBlock(glass16, -1);

    }

    @Override
    public void breakBlock(World aWorld, int aX, int aY, int aZ, Block aBlock, int aMeta) {

         if (GregTech_API.isMachineBlock(glass1,  aWorld.getBlockMetadata(aX, aY, aZ)) ||
             GregTech_API.isMachineBlock(glass2,  aWorld.getBlockMetadata(aX, aY, aZ)) ||
             GregTech_API.isMachineBlock(glass3,  aWorld.getBlockMetadata(aX, aY, aZ)) ||
             GregTech_API.isMachineBlock(glass4,  aWorld.getBlockMetadata(aX, aY, aZ)) ||
             GregTech_API.isMachineBlock(glass5,  aWorld.getBlockMetadata(aX, aY, aZ)) ||
             GregTech_API.isMachineBlock(glass6,  aWorld.getBlockMetadata(aX, aY, aZ)) ||
             GregTech_API.isMachineBlock(glass7,  aWorld.getBlockMetadata(aX, aY, aZ)) ||
             GregTech_API.isMachineBlock(glass8,  aWorld.getBlockMetadata(aX, aY, aZ)) ||
             GregTech_API.isMachineBlock(glass9,  aWorld.getBlockMetadata(aX, aY, aZ)) ||
             GregTech_API.isMachineBlock(glass10, aWorld.getBlockMetadata(aX, aY, aZ)) ||
             GregTech_API.isMachineBlock(glass11, aWorld.getBlockMetadata(aX, aY, aZ)) ||
             GregTech_API.isMachineBlock(glass12, aWorld.getBlockMetadata(aX, aY, aZ)) ||
             GregTech_API.isMachineBlock(glass13, aWorld.getBlockMetadata(aX, aY, aZ)) ||
             GregTech_API.isMachineBlock(glass14, aWorld.getBlockMetadata(aX, aY, aZ)) ||
             GregTech_API.isMachineBlock(glass15, aWorld.getBlockMetadata(aX, aY, aZ)) ||
             GregTech_API.isMachineBlock(glass16, aWorld.getBlockMetadata(aX, aY, aZ)) ) {
                GregTech_API.causeMachineUpdate(aWorld, aX, aY, aZ);
            }
    }

    public static Block glass1 = new Core_GlassBlocks("GlassBlock1",  new String[]{ Refstrings.MODID +  ":blockGB1" },null, true, true);
    public static Block glass2 = new Core_GlassBlocks("GlassBlock2",  new String[]{ Refstrings.MODID +  ":blockGB2" },null, true, true);
    public static Block glass3 = new Core_GlassBlocks("GlassBlock3",  new String[]{ Refstrings.MODID +  ":blockGB3" },null, true, true);
    public static Block glass4 = new Core_GlassBlocks("GlassBlock4",  new String[]{ Refstrings.MODID +  ":blockGB4" },null, true, true);
    public static Block glass5 = new Core_GlassBlocks("GlassBlock5",  new String[]{ Refstrings.MODID +  ":blockGB5" },null, true, true);
    public static Block glass6 = new Core_GlassBlocks("GlassBlock6",  new String[]{ Refstrings.MODID +  ":blockGB6" },null, true, true);
    public static Block glass7 = new Core_GlassBlocks("GlassBlock7",  new String[]{ Refstrings.MODID +  ":blockGB7" },null, true, true);
    public static Block glass8 = new Core_GlassBlocks("GlassBlock8",  new String[]{ Refstrings.MODID +  ":blockGB8" },null, true, true);
    public static Block glass9 = new Core_GlassBlocks("GlassBlock9",  new String[]{ Refstrings.MODID +  ":blockGB9" },null, true, true);
    public static Block glass10 =new Core_GlassBlocks("GlassBlock10", new String[]{ Refstrings.MODID + ":blockGB10" },null, true, true);
    public static Block glass11 =new Core_GlassBlocks("GlassBlock11", new String[]{ Refstrings.MODID + ":blockGB11" },null, true, true);
    public static Block glass12 =new Core_GlassBlocks("GlassBlock12", new String[]{ Refstrings.MODID + ":blockGB12" },null, true, true);
    public static Block glass13 =new Core_GlassBlocks("GlassBlock13", new String[]{ Refstrings.MODID + ":blockGB13" },null, true, true);
    public static Block glass14 =new Core_GlassBlocks("GlassBlock14", new String[]{ Refstrings.MODID + ":blockGB14" },null, true, true);
    public static Block glass15 =new Core_GlassBlocks("GlassBlock15", new String[]{ Refstrings.MODID + ":blockGB15" },null, true, true);
    public static Block glass16 =new Core_GlassBlocks("GlassBlock16", new String[]{ Refstrings.MODID + ":blockGB16" },null, true, true);

    @Override
    public void onBlockAdded(World aWorld, int aX, int aY, int aZ) {

            if (GregTech_API.isMachineBlock(glass1,  aWorld.getBlockMetadata(aX, aY, aZ)) ||
                GregTech_API.isMachineBlock(glass2,  aWorld.getBlockMetadata(aX, aY, aZ)) ||
                GregTech_API.isMachineBlock(glass3,  aWorld.getBlockMetadata(aX, aY, aZ)) ||
                GregTech_API.isMachineBlock(glass4,  aWorld.getBlockMetadata(aX, aY, aZ)) ||
                GregTech_API.isMachineBlock(glass5,  aWorld.getBlockMetadata(aX, aY, aZ)) ||
                GregTech_API.isMachineBlock(glass6,  aWorld.getBlockMetadata(aX, aY, aZ)) ||
                GregTech_API.isMachineBlock(glass7,  aWorld.getBlockMetadata(aX, aY, aZ)) ||
                GregTech_API.isMachineBlock(glass8,  aWorld.getBlockMetadata(aX, aY, aZ)) ||
                GregTech_API.isMachineBlock(glass9,  aWorld.getBlockMetadata(aX, aY, aZ)) ||
                GregTech_API.isMachineBlock(glass10, aWorld.getBlockMetadata(aX, aY, aZ)) ||
                GregTech_API.isMachineBlock(glass11, aWorld.getBlockMetadata(aX, aY, aZ)) ||
                GregTech_API.isMachineBlock(glass12, aWorld.getBlockMetadata(aX, aY, aZ)) ||
                GregTech_API.isMachineBlock(glass13, aWorld.getBlockMetadata(aX, aY, aZ)) ||
                GregTech_API.isMachineBlock(glass14, aWorld.getBlockMetadata(aX, aY, aZ)) ||
                GregTech_API.isMachineBlock(glass15, aWorld.getBlockMetadata(aX, aY, aZ)) ||
                GregTech_API.isMachineBlock(glass16, aWorld.getBlockMetadata(aX, aY, aZ)) ) {
                GregTech_API.causeMachineUpdate(aWorld, aX, aY, aZ);
            }
    }

}
