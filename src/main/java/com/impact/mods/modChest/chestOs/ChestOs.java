package com.impact.mods.modChest.chestOs;

import com.impact.impact;
import com.impact.mods.modChest.BASE.BaseChest;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import static com.impact.loader.GUIHandler.GUI_ID_OsChest;


public class ChestOs extends BaseChest {
	public static ChestOs instance = new ChestOs();

	private ChestOs() {
		super(Material.iron);
		setBlockName("OsmiumChest")
		.setHardness(5.0F)
		.setStepSound(soundTypeMetal)
		.setHarvestLevel("wrench", 0);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata)
	{
		return new TEChestOs();
	}

	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote)
			FMLNetworkHandler.openGui(entityPlayer, impact.instance, GUI_ID_OsChest, world, x, y, z);
		return true;
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(final IIconRegister iIconRegister) {
		this.blockIcon = iIconRegister.registerIcon("snow");
	}
}