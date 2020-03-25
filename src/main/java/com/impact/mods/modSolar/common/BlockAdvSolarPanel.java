package com.impact.mods.modSolar.common;

import com.impact.impact;
import com.impact.mods.modSolar.ASP;
import com.impact.mods.modSolar.common.TE.*;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

import static com.impact.loader.GUIHandler.GUI_ID_Solar;

public class BlockAdvSolarPanel extends BlockContainer {
    public boolean qgActive;
    private IIcon[][] iconBuffer;

    public BlockAdvSolarPanel() {
        super(Material.iron);
        this.setHardness(3.0F);
        this.setHarvestLevel("wrench", 1);
        this.qgActive = false;
    }

    public void registerBlockIcons(IIconRegister par1IconRegister) {
        this.iconBuffer = new IIcon[9][12];
        for (byte Num = 0; Num <= 8; Num++) {
            for (byte i = 0; i <= 11; i++) {
                if (i == 1 || i == 7) {
                    this.iconBuffer[Num][i] = par1IconRegister.registerIcon("advancedsolarpanel:Panel" + Num + "_top");
                } else {
                    this.iconBuffer[Num][i] = par1IconRegister.registerIcon("advancedsolarpanel:Panel" + Num + "_side");
                }
            }
        }
    }

    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int blockSide) {
        int blockMeta = world.getBlockMetadata(x, y, z);
        TileEntity te = world.getTileEntity(x, y, z);
        int facing = te instanceof TileEntityBase ? ((TileEntityBase)te).getFacing() : 0;
        return isActive(world, x, y, z)
                ? this.iconBuffer[blockMeta][ASP.sideAndFacingToSpriteOffset[blockSide][facing] + 6]
                : this.iconBuffer[blockMeta][ASP.sideAndFacingToSpriteOffset[blockSide][facing]];
    }

    public IIcon getIcon(int blockSide, int blockMeta) {
        return this.iconBuffer[blockMeta][ASP.sideAndFacingToSpriteOffset[blockSide][3]];
    }

    public static boolean isActive(IBlockAccess var0, int var1, int var2, int var3) {
        return ((TileEntityBase)var0.getTileEntity(var1, var2, var3)).getActive();
    }

    public void breakBlock(World world, int i, int j, int k, Block par5, int par6) {
        TileEntity tileentity = world.getTileEntity(i, j, k);
        if (tileentity != null) {
            this.dropItems((TileEntitySolarPanel)tileentity, world);
        }

        world.removeTileEntity(i, j, k);
        super.breakBlock(world, i, j, k, par5, par6);
    }

    public int quantityDropped(Random random) {
        return 1;
    }

    public int damageDropped(int i) {
        return i;
    }

    public TileEntity getBlockEntity(int i) {
        switch(i) {
            case 0:
                return new TileEntity8SolarPanel();
            case 1:
                return new TileEntity32SolarPanel();
            case 2:
                return new TileEntity128SolarPanel();
            case 3:
                return new TileEntity512SolarPanel();
            case 4:
                return new TileEntity2048SolarPanel();
            case 5:
                return new TileEntity8192SolarPanel();
            case 6:
                return new TileEntity32768SolarPanel();
            case 7:
                return new TileEntity131072SolarPanel();
            case 8:
                return new TileEntity524288SolarPanel();
            default:
                return new TileEntity8SolarPanel();
        }
    }

    public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer player, int s, float f1, float f2, float f3) {
        if (player.isSneaking()) {
            return false;
        } else if (world.isRemote) {
            return true;
        } else {
            TileEntity tileentity = world.getTileEntity(i, j, k);
            if (tileentity != null) {
                player.openGui(impact.instance, GUI_ID_Solar, world, i, j, k);
            }

            return true;
        }
    }

    private void dropItems(TileEntitySolarPanel tileentity, World world) {
        Random rand = new Random();
        if (tileentity instanceof IInventory) {
            IInventory inventory = tileentity;

            for(int i = 0; i < inventory.getSizeInventory(); ++i) {
                ItemStack item = inventory.getStackInSlot(i);
                if (item != null && item.stackSize > 0) {
                    float rx = rand.nextFloat() * 0.8F + 0.1F;
                    float ry = rand.nextFloat() * 0.8F + 0.1F;
                    float rz = rand.nextFloat() * 0.8F + 0.1F;
                    EntityItem entityItem = new EntityItem(world, (double)((float)tileentity.xCoord + rx), (double)((float)tileentity.yCoord + ry), (double)((float)tileentity.zCoord + rz), new ItemStack(item.getItem(), item.stackSize, item.getItemDamage()));
                    if (item.hasTagCompound()) {
                        entityItem.getEntityItem().setTagCompound((NBTTagCompound)item.getTagCompound().copy());
                    }

                    float factor = 0.05F;
                    entityItem.motionX = rand.nextGaussian() * (double)factor;
                    entityItem.motionY = rand.nextGaussian() * (double)factor + 0.20000000298023224D;
                    entityItem.motionZ = rand.nextGaussian() * (double)factor;
                    world.spawnEntityInWorld(entityItem);
                    item.stackSize = 0;
                }
            }

        }
    }

    public TileEntity createNewTileEntity(World var1, int var2) {
        return this.getBlockEntity(var2);
    }

    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List subItems) {
        for(int ix = 0; ix < this.iconBuffer.length; ++ix) {
            subItems.add(new ItemStack(this, 1, ix));
        }

    }
}
