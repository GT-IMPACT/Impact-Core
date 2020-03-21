package com.gwppcore.modChest.chestCr;

import com.gwppcore.gwppcore;
import com.gwppcore.modChest.BASE.BaseChest;
import com.gwppcore.modChest.chestHSLA.TEChestHSLA;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import static com.gwppcore.guihandler.GUIHandler.GUI_ID_CrChest;


public final class ChestCr extends BaseChest
{
	public static final ChestCr instance = new ChestCr();

	private ChestCr()
	{
		super(Material.iron);
		setBlockName("ChromeChest")
		.setHardness(5.0F)
		.setStepSound(soundTypeMetal)
		.setHarvestLevel("wrench", 0);
	}

	@Override
	public TileEntity createNewTileEntity(final World world, final int metadata)
	{
		return new TEChestCr();
	}

	public boolean onBlockActivated(final World world, final int x, final int y, final int z, final EntityPlayer entityPlayer, final int side, final float hitX, final float hitY, final float hitZ)
	{
		if (!world.isRemote)
			FMLNetworkHandler.openGui(entityPlayer, gwppcore.instance, GUI_ID_CrChest, world, x, y, z);
		return true;
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(final IIconRegister iIconRegister)
	{
		this.blockIcon = iIconRegister.registerIcon("snow");
	}
}