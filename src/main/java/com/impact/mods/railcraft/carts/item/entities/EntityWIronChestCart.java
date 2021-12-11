package com.impact.mods.railcraft.carts.item.entities;

import com.impact.mods.railcraft.carts.item.ChestCartItems;
import com.impact.mods.railcraft.carts.item.client.ContainerWIronChestCart;
import com.impact.mods.railcraft.carts.item.client.GuiWIronChestCart;
import com.impact.mods.railcraft.carts.item.client.OpenableGUI;
import cpw.mods.fml.common.Optional;
import mods.railcraft.api.carts.IMinecart;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

@Optional.Interface(iface = "mods.railcraft.api.carts.IMinecart", modid = "RailcraftAPI|carts")
public class EntityWIronChestCart extends EntityExtraCartChestMinecart implements OpenableGUI, IMinecart {
	
	private final Block Chest = Block.getBlockFromName("chestup:Blockchestup");
	
	public EntityWIronChestCart(World world) {
		super(world);
		this.setDisplayTileData(0);
	}
	
	@Override
	public int getSizeInventory() {
		return 45;
	}
	
	@Override
	public Block func_145817_o() {
		return Chest;
	}
	
	@Override
	public void killMinecart(DamageSource par1DamageSource) {
		super.killMinecart(par1DamageSource, new ItemStack(Chest, 1, 0));
	}
	
	@Override
	public boolean interactFirst(EntityPlayer player) {
		return super.interactFirst(player);
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GuiWIronChestCart(player.inventory, this);
	}
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerWIronChestCart(player.inventory, this);
	}
	
	@Optional.Method(modid = "RailcraftAPI|carts")
	public boolean doesCartMatchFilter(ItemStack stack, EntityMinecart cart) {
		ItemStack CartStack = new ItemStack(ChestCartItems.ChestCart, 1, 0);
		return cart instanceof EntityWIronChestCart && stack.getItem() == CartStack.getItem() && stack.getItemDamage() == 0;
	}
}
