package com.impact.common.block.blocks;

import com.impact.common.block.itemblock.IB_TheMill;
import com.impact.common.te.TE_TheMill;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import gregtech.api.interfaces.IAxeWrenchable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Collections;


public class Block_TheMill extends gtUpdateBlockAPI implements IAxeWrenchable {
	
	public static Block_TheMill instance = new Block_TheMill();
	public static int renderID;
	
	protected Block_TheMill() {
		super(Material.iron);
	}
	
	public static Block registerBlock() {
		final String blockName = "the_mill";
		instance.setBlockName(blockName);
		instance.setHardness(5.0f);
		instance.setResistance(6.0f);
		GameRegistry.registerBlock(instance, IB_TheMill.class, blockName);
		return instance;
	}
	
	@Override
	public boolean apply() {
		return true;
	}
	
	@Override
	public int getRenderType() {
		return renderID;
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		this.setBlockBoundsBasedOnState(world, x, y, z);
		return super.getCollisionBoundingBoxFromPool(world, x, y, z);
	}
	
	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
		this.setBlockBoundsBasedOnState(world, x, y, z);
		return super.getCollisionBoundingBoxFromPool(world, x, y, z);
	}
	
	@Override
	public boolean hasTileEntity(int metadata) {
		return true;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public TileEntity createTileEntity(World world, int meta) {
		return new TE_TheMill();
	}
	
	public static class BlockRender implements ISimpleBlockRenderingHandler {
		private static final TE_TheMill windMill = new TE_TheMill();
		
		public BlockRender() {
		}
		public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
			GL11.glPushMatrix();
			try {
				GL11.glScalef(0.125F, 0.125F, 0.125F);
				TileEntityRendererDispatcher.instance.renderTileEntityAt(windMill, 0.0D, 0.0D, 0.0D, 0.0F);
			} catch (Exception var6) {
				var6.printStackTrace();
			}
			GL11.glEnable(32826);
			GL11.glPopMatrix();
		}
		
		public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
			return false;
		}
		
		public boolean shouldRender3DInInventory(int modelID) {
			return true;
		}
		
		public int getRenderId() {
			return Block_TheMill.renderID;
		}
		
	}
}