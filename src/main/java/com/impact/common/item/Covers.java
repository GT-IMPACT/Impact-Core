package com.impact.common.item;

import com.impact.core.Refstrings;
import com.impact.mods.gregtech.tileentities.covers.*;
import cpw.mods.fml.common.registry.GameRegistry;
import gregtech.api.GregTech_API;
import gregtech.api.enums.Textures;
import gregtech.api.objects.GT_MultiTexture;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GT_ModHandler;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

public class Covers extends Item {

    public static int x = 5;  //количество предметов

    public static Covers covers = new Covers();
    private final IIcon[] icons = new IIcon[x + 1];

    public static Covers getInstance() {
        return covers;
    }

    public static void registerCovers() {
        ItemStack fluidDetectorBound = new ItemStack(getInstance(), 1, 0);
        ItemStack fluidDetectorRange = new ItemStack(getInstance(), 1, 3);

        ItemStack euDetectorBound = new ItemStack(getInstance(), 1, 1);
        ItemStack euDetectorRange = new ItemStack(getInstance(), 1, 4);

        GT_ModHandler.addShapelessCraftingRecipe(fluidDetectorBound, 0, new Object[]{fluidDetectorRange});
        GT_ModHandler.addShapelessCraftingRecipe(fluidDetectorRange, 0, new Object[]{fluidDetectorBound});

        GT_ModHandler.addShapelessCraftingRecipe(euDetectorBound, 0, new Object[]{euDetectorRange});
        GT_ModHandler.addShapelessCraftingRecipe(euDetectorRange, 0, new Object[]{euDetectorBound});

        GregTech_API.registerCover(
                fluidDetectorBound,
                new GT_MultiTexture(Textures.BlockIcons.MACHINE_CASINGS[2][0], TextureFactory.of(Textures.BlockIcons.OVERLAY_FLUIDDETECTOR)),
                new GTC_AdvFluidDetectorBound()
        );
        GregTech_API.registerCover(
                euDetectorBound,
                new GT_MultiTexture(Textures.BlockIcons.MACHINE_CASINGS[2][0], TextureFactory.of(Textures.BlockIcons.OVERLAY_ENERGYDETECTOR)),
                new GTC_AdvEUDetector()
        );
        GregTech_API.registerCover(
                new ItemStack(getInstance(), 1, 2),
                new GT_MultiTexture(Textures.BlockIcons.MACHINE_CASINGS[2][0], TextureFactory.of(Textures.BlockIcons.OVERLAY_ENERGY_IN_MULTI)),
                new GTCDebugEnergy()
        );
        GregTech_API.registerCover(
                new ItemStack(getInstance(), 1, 3),
                new GT_MultiTexture(Textures.BlockIcons.MACHINE_CASINGS[2][0], TextureFactory.of(Textures.BlockIcons.OVERLAY_FLUIDDETECTOR)),
                new GTC_AdvFluidDetectorRange()
        );
        GregTech_API.registerCover(
                fluidDetectorRange,
                new GT_MultiTexture(Textures.BlockIcons.MACHINE_CASINGS[2][0], TextureFactory.of(Textures.BlockIcons.OVERLAY_FLUIDDETECTOR)),
                new GTC_AdvFluidDetectorRange()
        );
        GregTech_API.registerCover(
                euDetectorRange,
                new GT_MultiTexture(Textures.BlockIcons.MACHINE_CASINGS[2][0], TextureFactory.of(Textures.BlockIcons.OVERLAY_ENERGYDETECTOR)),
                new GTC_AdvEUDetectorRange()
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
            case 3:
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