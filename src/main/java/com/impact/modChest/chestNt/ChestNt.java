package com.impact.modChest.chestNt;

import com.impact.impact;
import com.impact.modChest.BASE.BaseChest;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import static com.impact.guihandler.GUIHandler.GUI_ID_NtChest;


public final class ChestNt extends BaseChest
{
	public static final ChestNt instance = new ChestNt();

	private ChestNt()
	{
		super(Material.iron);
		setBlockName("NeutroniumChest")
		.setHardness(5.0F)
		.setStepSound(soundTypeMetal)
		.setHarvestLevel("wrench", 0);
	}

	@Override
	public TileEntity createNewTileEntity(final World world, final int metadata)
	{
		return new TEChestNt();
	}

	public boolean onBlockActivated(final World world, final int x, final int y, final int z, final EntityPlayer entityPlayer, final int side, final float hitX, final float hitY, final float hitZ)
	{
		if (!world.isRemote)
			FMLNetworkHandler.openGui(entityPlayer, impact.instance, GUI_ID_NtChest, world, x, y, z);
		return true;
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(final IIconRegister iIconRegister)
	{
		this.blockIcon = iIconRegister.registerIcon("snow");
	}
}