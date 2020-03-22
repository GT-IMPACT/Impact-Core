package com.impact.mods.modChest.chestAL;

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

import static com.impact.loader.GUIHandler.GUI_ID_AlChest;


public final class ChestAl extends BaseChest
{
	public static final ChestAl instance = new ChestAl();

	private ChestAl()
	{
		super(Material.iron);
		setBlockName("AlChest")
		.setHardness(5.0F)
		.setStepSound(soundTypeMetal)
		.setHarvestLevel("wrench", 0);
	}

	@Override
	public TileEntity createNewTileEntity(final World world, final int metadata)
	{
		return new TEChestAl();
	}

	public boolean onBlockActivated(final World world, final int x, final int y, final int z, final EntityPlayer entityPlayer, final int side, final float hitX, final float hitY, final float hitZ)
	{
		if (!world.isRemote)
			FMLNetworkHandler.openGui(entityPlayer, impact.instance, GUI_ID_AlChest, world, x, y, z);
		return true;
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(final IIconRegister iIconRegister)
	{
		this.blockIcon = iIconRegister.registerIcon("snow");
	}
}