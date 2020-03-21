package com.gwppcore.modChest.chestOs;

import com.gwppcore.gwppcore;
import com.gwppcore.modChest.BASE.BaseChest;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import static com.gwppcore.guihandler.GUIHandler.GUI_ID_IrChest;
import static com.gwppcore.guihandler.GUIHandler.GUI_ID_OsChest;


public final class ChestOs extends BaseChest
{
	public static final ChestOs instance = new ChestOs();

	private ChestOs()
	{
		super(Material.iron);
		setBlockName("OsmiumChest")
		.setHardness(5.0F)
		.setStepSound(soundTypeMetal)
		.setHarvestLevel("wrench", 0);
	}

	@Override
	public TileEntity createNewTileEntity(final World world, final int metadata)
	{
		return new TEChestOs();
	}

	public boolean onBlockActivated(final World world, final int x, final int y, final int z, final EntityPlayer entityPlayer, final int side, final float hitX, final float hitY, final float hitZ)
	{
		if (!world.isRemote)
			FMLNetworkHandler.openGui(entityPlayer, gwppcore.instance, GUI_ID_OsChest, world, x, y, z);
		return true;
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(final IIconRegister iIconRegister)
	{
		this.blockIcon = iIconRegister.registerIcon("snow");
	}
}