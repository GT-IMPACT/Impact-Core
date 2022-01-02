package com.impact.common.block.blocks;

import com.impact.client.render.models.Model_DryingRack;
import com.impact.common.te.TE_DryingRack;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.core.IC2;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;

import java.util.List;
import java.util.Random;

public class Block_DryingRack extends BlockContainer {
	
	private final Random rand = new Random();
	public IIcon[] icons;
	
	public static Block_DryingRack instance = new Block_DryingRack();
	
	public Block_DryingRack() {
		super(Material.wood);
		stepSound = soundTypeMetal;
	}
	
	public static Block registerBlock() {
		final String blockName = "drying_rack";
		instance.setBlockName(blockName);
		instance.setHardness(2.0f);
		instance.setResistance(6.0f);
		GameRegistry.registerBlock(instance, blockName);
		return instance;
	}
	
	public static void spawnItemAtPlayer(EntityPlayer player, ItemStack stack) {
		if (!player.worldObj.isRemote) {
			// try to put it into the players inventory
			if (player instanceof FakePlayer || !player.inventory.addItemStackToInventory(stack)) // note that the addItemStackToInventory is not called for fake players
			{
				// drop the rest as an entity
				EntityItem entityitem = new EntityItem(player.worldObj, player.posX + 0.5D, player.posY + 0.5D, player.posZ + 0.5D, stack);
				player.worldObj.spawnEntityInWorld(entityitem);
				if (!(player instanceof FakePlayer))
					entityitem.onCollideWithPlayer(player);
			}
			// if it got picked up, we're playing the sound
			else {
				if (player instanceof EntityPlayerMP) {
					player.worldObj.playSoundAtEntity(player, "random.pop", 0.2F, ((IC2.random.nextFloat() - IC2.random.nextFloat()) * 0.7F + 1.0F) * 2.0F);
					player.inventoryContainer.detectAndSendChanges();
				}
			}
		}
	}
	
	@Override
	public int getRenderBlockPass() {
		return 1;
	}
	
	@Override
	public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta) {
		if (side > 1)
			return side;
		return meta;
	}
	
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving living, ItemStack stack) {
		int meta = world.getBlockMetadata(x, y, z);
		if (meta == 0) {
			int l = MathHelper.floor_double((double) (living.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
			int direction = l % 2;
			if (direction == 1)
				world.setBlockMetadataWithNotify(x, y, z, 1, 2);
		}
	}
	
	@Override
	public void registerBlockIcons(IIconRegister iconRegister) {
		String[] textureNames = getTextureNames();
		this.icons = new IIcon[textureNames.length];
		
		for (int i = 0; i < this.icons.length; ++i) {
			this.icons[i] = iconRegister.registerIcon(getTextureDomain(i) + ":" + textureNames[i]);
		}
	}
	
	@Override
	public void breakBlock(World par1World, int x, int y, int z, Block blockID, int meta) {
		TileEntity te = par1World.getTileEntity(x, y, z);
		
		if (te instanceof TE_DryingRack) {
			TE_DryingRack logic = (TE_DryingRack) te;
			for (int iter = 0; iter < logic.getSizeInventory(); ++iter) {
				ItemStack stack = logic.getStackInSlot(iter);
				
				if (stack != null && logic.canDropInventorySlot(iter)) {
					float jumpX = rand.nextFloat() * 0.8F + 0.1F;
					float jumpY = rand.nextFloat() * 0.8F + 0.1F;
					float jumpZ = rand.nextFloat() * 0.8F + 0.1F;
					
					while (stack.stackSize > 0) {
						int itemSize = rand.nextInt(21) + 10;
						
						if (itemSize > stack.stackSize) {
							itemSize = stack.stackSize;
						}
						
						stack.stackSize -= itemSize;
						EntityItem entityitem = new EntityItem(par1World, (float) x + jumpX, (float) y + jumpY, (float) z + jumpZ, new ItemStack(stack.getItem(),
								itemSize, stack.getItemDamage()
						));
						
						if (stack.hasTagCompound()) {
							entityitem.getEntityItem().setTagCompound((NBTTagCompound) stack.getTagCompound().copy());
						}
						
						float offset = 0.05F;
						entityitem.motionX = (float) rand.nextGaussian() * offset;
						entityitem.motionY = (float) rand.nextGaussian() * offset + 0.2F;
						entityitem.motionZ = (float) rand.nextGaussian() * offset;
						par1World.spawnEntityInWorld(entityitem);
					}
				}
			}
		}
		super.breakBlock(par1World, x, y, z, blockID, meta);
	}
	
	/* Activation */
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float clickX, float clickY, float clickZ) {
		return activateDryingRack(world, x, y, z, player);
	}
	
	boolean activateDryingRack(World world, int x, int y, int z, EntityPlayer player) {
		if (!world.isRemote) {
			TE_DryingRack logic = (TE_DryingRack) world.getTileEntity(x, y, z);
			
			if (!logic.isStackInSlot(0)) {
				ItemStack stack = player.getCurrentEquippedItem();
				if (stack != null) {
					stack = player.inventory.decrStackSize(player.inventory.currentItem, 1);
					logic.setInventorySlotContents(0, stack);
				}
			} else {
				ItemStack decrStack = logic.decrStackSize(0, 1);
				if (decrStack != null)
					spawnItemAtPlayer(player, decrStack);
			}
			
			world.markBlockForUpdate(x, y, z);
		}
		return true;
	}
	
	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
		int metadata = world.getBlockMetadata(x, y, z);
		float xMin = 0F;
		float yMin = 0F;
		float zMin = 0F;
		float xMax = 1F;
		float yMax = 1F;
		float zMax = 1F;
		switch (metadata) {
			case 0:
				zMin = 0.375F;
				yMax = 0.25F;
				zMax = 0.625F;
				break;
			case 1:
				xMin = 0.375F;
				yMax = 0.25F;
				xMax = 0.625F;
				break;
			case 2:
				zMin = 0.75F;
				yMin = 0.75F;
				break;
			case 3:
				zMax = 0.25F;
				yMin = 0.75F;
				break;
			case 4:
				xMin = 0.75F;
				yMin = 0.75F;
				break;
			case 5:
				xMax = 0.25F;
				yMin = 0.75F;
				break;
		}
		return AxisAlignedBB.getBoundingBox((double) x + xMin, (double) y + yMin, (double) z + zMin, (double) x + xMax, (double) y + yMax, (double) z + zMax);
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		int metadata = world.getBlockMetadata(x, y, z);
		float xMin = 0F;
		float yMin = 0F;
		float zMin = 0F;
		float xMax = 1F;
		float yMax = 1F;
		float zMax = 1F;
		switch (metadata) {
			case 0:
				zMin = 0.375F;
				yMax = 0.25F;
				zMax = 0.625F;
				break;
			case 1:
				xMin = 0.375F;
				yMax = 0.25F;
				xMax = 0.625F;
				break;
			case 2:
				zMin = 0.75F;
				yMin = 0.75F;
				break;
			case 3:
				zMax = 0.25F;
				yMin = 0.75F;
				break;
			case 4:
				xMin = 0.75F;
				yMin = 0.75F;
				break;
			case 5:
				xMax = 0.25F;
				yMin = 0.75F;
				break;
		}
		this.setBlockBounds(xMin, yMin, zMin, xMax, yMax, zMax);
	}
	
	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB aabb, List list, Entity entity) {
		this.setBlockBoundsBasedOnState(world, x, y, z);
		super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
	}
	
	/* Rendering */
	@Override
	public int getRenderType() {
		return Model_DryingRack.model;
	}
	
	public String[] getTextureNames() {
		return new String[]{
				"castingtable_top",
				"castingtable_side",
				"castingtable_bottom",
				"faucet",
				"blockcast_top",
				"blockcast_side",
				"blockcast_bottom"
		};
	}
	
	public String getTextureDomain(int textureNameIndex) {
		return "impact";
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return Blocks.planks.getIcon(side, 0);
	}
	
	public int getTextureIndex(int side) {
		if (side == 0)
			return 2;
		if (side == 1)
			return 0;
		
		return 1;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public TileEntity createTileEntity(World world, int metadata) {
		return new TE_DryingRack();
	}
	
	@Override
	public TileEntity createNewTileEntity(World w, int meta) {
		return new TE_DryingRack();
	}
}