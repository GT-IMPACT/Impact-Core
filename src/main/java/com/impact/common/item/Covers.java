package com.impact.common.item;

import com.impact.core.Refstrings;
import com.impact.mods.gregtech.tileentities.covers.GTC_AdvEUDetector;
import com.impact.mods.gregtech.tileentities.covers.GTC_AdvFluidDetector;
import cpw.mods.fml.common.registry.GameRegistry;
import gregtech.api.GregTech_API;
import gregtech.api.enums.Textures;
import gregtech.api.objects.GT_MultiTexture;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.render.TextureFactory;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

public class Covers extends Item {
	
	public static int x = 2;  //количество предметов
	
	public static Covers covers = new Covers();
	private final IIcon[] icons = new IIcon[x + 1];
	
	public static Covers getInstance() {
		return covers;
	}
	
	public static void registerCovers() {
		GregTech_API.registerCover(
				new ItemStack(getInstance(), 1, 0),
				new GT_MultiTexture(Textures.BlockIcons.MACHINE_CASINGS[2][0], TextureFactory.of(Textures.BlockIcons.OVERLAY_FLUIDDETECTOR)),
				new GTC_AdvFluidDetector()
		);
		GregTech_API.registerCover(
				new ItemStack(getInstance(), 1, 1),
				new GT_MultiTexture(Textures.BlockIcons.MACHINE_CASINGS[2][0], TextureFactory.of(Textures.BlockIcons.OVERLAY_ENERGYDETECTOR)),
				new GTC_AdvEUDetector()
		);
	}
	
	public void registerItem() {
		super.setHasSubtypes(true);
		final String unlocalizedName = "impact_cover";
		super.setUnlocalizedName(unlocalizedName);
		super.setMaxStackSize(64);
		GameRegistry.registerItem(getInstance(), unlocalizedName);
		registerCovers();
	}
	
	@Override
	public void registerIcons(IIconRegister reg) {
		for (int i = 0; i < x; i++) {
			icons[i] = reg.registerIcon(Refstrings.MODID + ":impact_covers/" + i);
		}
	}
	
	@Override
	public IIcon getIconFromDamage(int meta) {
		return icons[meta];
	}
	
	@SuppressWarnings({"unchecked", "rawtypes"})
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < x; i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return super.getUnlocalizedName() + "." + stack.getItemDamage();
	}
	
	@SuppressWarnings({"unchecked", "rawtypes"})
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean b) {
		int meta = stack.getItemDamage();
		switch (meta) {
			case 0:
				list.add("Gives out Fluid Amount as Redstone");
				break;
			case 1:
				list.add("Gives out Energy Amount as Redstone");
				break;
		}
	}
	
	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		return 0.0d;
	}
	
	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return false;
	}
}