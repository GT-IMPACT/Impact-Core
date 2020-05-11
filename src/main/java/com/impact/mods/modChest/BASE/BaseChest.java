package com.impact.mods.modChest.BASE;

import com.impact.impact;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public class BaseChest extends BlockContainer {

    private static Random rand = new Random();
    private static int mGuiID;
    private static int mIDChest;
    private static TE_BaseChest TE;
    private static Container_BaseChest Container_BaseChest;
    private static Gui_BaseChest Gui_BaseChest;

    public BaseChest() {
        super(Material.iron);
        setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
        setCreativeTab(CreativeTabs.tabDecorations);
        setBlockName("Chest" + mIDChest);
        setHardness(5.0F);
        setStepSound(soundTypeMetal);
        setHarvestLevel("wrench", 0);
    }

    public void RegisterChestType(
            TE_BaseChest aTE, int IDChest, int aGuiHandlerID, String aName, int aMaxStackSize, int aSlot, //TE
            int aGuiXSize, int aGuiYSize, int aGuiScale, int aGuiNamedPos, //GUI
            int aContValueSlotX, int aContValueSlotY, int aContPlayerSlotX, int aContPlayerSlotY //Container
    ) {
        mGuiID = aGuiHandlerID;
        mIDChest = IDChest;
        TE = aTE;
        TE.RegisterTE(IDChest, aName, aMaxStackSize, aSlot, aSlot);
        Gui_BaseChest.RegisterGui(IDChest, TE, aGuiXSize, aGuiYSize, aGuiScale, aGuiNamedPos);
        Container_BaseChest.RegisterContainer(TE, aSlot, aContValueSlotX, aContValueSlotY, aContPlayerSlotX, aContPlayerSlotY);
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int getRenderType() {
        return -1;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int metadata) {
        TE_BaseChest TEBaseChest = (TE_BaseChest) world.getTileEntity(x, y, z);
        if (TEBaseChest != null) {
            ItemStack droppedStack = new ItemStack(block, 1, 0);
            droppedStack.setTagCompound(TEBaseChest.writeCustomNBT(new NBTTagCompound()));
            world.spawnEntityInWorld(new EntityItem(world, x + rand.nextFloat() * 0.8F + 0.1F, y + rand.nextFloat() * 0.8F + 0.1F, z + rand.nextFloat() * 0.8F + 0.1F, droppedStack));
        }
        super.breakBlock(world, x, y, z, block, metadata);
        world.func_147453_f(x, y, z, block);
    }

    @Override
    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return null;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemStack) {
        int side = MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        TE_BaseChest TEBaseChest = (TE_BaseChest) world.getTileEntity(x, y, z);
        if (TEBaseChest != null) {
            TEBaseChest.setFacingSide(side == 0 ? 180 : side == 1 ? -90 : side == 2 ? 0 : side == 3 ? 90 : 0);
            if (itemStack.stackTagCompound != null)
                TEBaseChest.readCustomNBT(itemStack.stackTagCompound);
        }
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return TE;
    }

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote)
            FMLNetworkHandler.openGui(entityPlayer, impact.instance, mGuiID, world, x, y, z);
        return true;
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iIconRegister) {
        this.blockIcon = iIconRegister.registerIcon("snow");
    }
}