package com.impact.common.armor;

import com.impact.core.Refstrings;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

import java.util.List;

public class MaskOfVision extends ItemArmor {
	
	public static final MaskOfVision INSTANCE = new MaskOfVision();
	
	public static Item registerItem() {
		String name = "mask_of_vision";
		INSTANCE.setUnlocalizedName(name);
		INSTANCE.setMaxDamage(50);
		GameRegistry.registerItem(INSTANCE, name);
		return INSTANCE;
	}
	
	public MaskOfVision() {
		super(ArmorMaterial.DIAMOND, 0, 0);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void addInformation(ItemStack is, EntityPlayer p, List l, boolean f3) {
		l.add("The mask is for viewing the laser beams");
	}
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister) {
		this.itemIcon = iconRegister.registerIcon(Refstrings.MODID + ":armor/maskofvision");
	}
	
	@SideOnly(Side.CLIENT)
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		return Refstrings.MODID + ":" + "textures/models/maskofvision_armor.png";
	}
}