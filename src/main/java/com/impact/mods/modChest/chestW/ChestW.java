package com.impact.mods.modChest.chestW;

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

import static com.impact.loader.GUIHandler.GUI_ID_WChest;


public class ChestW extends BaseChest {
	public static ChestW instance = new ChestW();

	private ChestW() {
		super(Material.iron);
		setBlockName("TungstenSteelChest")
		.setHardness(5.0F)
		.setStepSound(soundTypeMetal)
		.setHarvestLevel("wrench", 0);
	}

	@Override
	public TileEntity createNewTileEntity( World world, int metadata) {
		return new TEChestW();
	}

	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote)
			FMLNetworkHandler.openGui(entityPlayer, impact.instance, GUI_ID_WChest, world, x, y, z);
		return true;
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons( IIconRegister iIconRegister) {
		this.blockIcon = iIconRegister.registerIcon("snow");
	}
}