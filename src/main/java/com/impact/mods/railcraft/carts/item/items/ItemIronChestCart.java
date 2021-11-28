package com.impact.mods.railcraft.carts.item.items;

import com.impact.mods.railcraft.carts.item.entities.EntityAlChestCart;
import com.impact.mods.railcraft.carts.item.entities.EntitySteelChestCart;
import com.impact.mods.railcraft.carts.item.entities.EntityWIronChestCart;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.List;

public class ItemIronChestCart extends ExtraCartItem {
	
	@SideOnly(Side.CLIENT)
	private IIcon itemChestCart0, itemChestCart1, itemChestCart2;
	
	public ItemIronChestCart() {
		super(1);
		this.hasSubtypes = true;
	}
	
	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int par7, float par8, float par9, float par10) {
		EntityMinecart entityMinecart;
		switch (itemstack.getItemDamage()) {
			case 1:
				entityMinecart = new EntitySteelChestCart(world);
				break;
			case 2:
				entityMinecart = new EntityAlChestCart(world);
				break;
			default:
				entityMinecart = new EntityWIronChestCart(world);
				break;
		}
		return placeCart(itemstack, player, world, x, y, z, entityMinecart);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister register) {
		itemChestCart1 = register.registerIcon("impact:minecart_chest_steel");
		itemChestCart2 = register.registerIcon("impact:minecart_chest_aluminium");
		itemChestCart0 = register.registerIcon("impact:minecart_chest_wriron");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int dmg) {
		switch (dmg) {
			case 1:
				return itemChestCart1;
			case 2:
				return itemChestCart2;
			default:
				return itemChestCart0;
		}
		
	}
	
	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		switch (itemstack.getItemDamage()) {
			default:
				return "item.ChestCart.0";
			case 1:
				return "item.ChestCart.1";
			case 2:
				return "item.ChestCart.2";
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < 3; i++) {
			ItemStack stack = new ItemStack(item, 1, i);
			list.add(stack);
		}
	}
}
