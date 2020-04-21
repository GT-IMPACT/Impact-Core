package com.impact.mods.modChest.chestHSLA;

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


public class ChestHSLA extends BaseChest {
	public static ChestHSLA instance = new ChestHSLA();

	private ChestHSLA() {
		super(Material.iron);
		setBlockName("HSLAChest")
		.setHardness(5.0F)
		.setStepSound(soundTypeMetal)
		.setHarvestLevel("wrench", 0);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata)
	{
		return new TEChestHSLA();
	}

	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote)
			FMLNetworkHandler.openGui(entityPlayer, impact.instance, GUI_ID_AlChest, world, x, y, z);
		return true;
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iIconRegister) {
		this.blockIcon = iIconRegister.registerIcon("snow");
	}
}