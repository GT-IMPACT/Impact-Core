package com.impact.client.render.tesr;

import com.impact.common.te.TE_DryingRack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class TESR_DryingRack extends TileEntitySpecialRenderer {
	
	public void render(TE_DryingRack logic, double posX, double posY, double posZ, float var8) {
		GL11.glPushMatrix();
		float var10 = (float) (posX - 0.5F);
		float var11 = (float) (posY - 0.5F);
		float var12 = (float) (posZ - 0.5F);
		GL11.glTranslatef(var10, var11, var12);
		this.func_82402_b(logic);
		GL11.glPopMatrix();
	}
	
	private void func_82402_b(TE_DryingRack logic) {
		ItemStack stack = logic.getStackInSlot(0);
		
		if (stack != null)
			renderItem(logic, stack);
	}
	
	void renderItem(TE_DryingRack logic, ItemStack stack) {
		FancyEntityItem entityitem = new FancyEntityItem(logic.getWorldObj(), 0.0D, 0.0D, 0.0D, stack);
		entityitem.getEntityItem().stackSize = 1;
		entityitem.hoverStart                = 0.0F;
		GL11.glPushMatrix();
		int meta = logic.getWorldObj().getBlockMetadata(logic.xCoord, logic.yCoord, logic.zCoord);
		if (meta <= 1)
			GL11.glTranslatef(1F, -0.375F, 0.905F);
		else {
			GL11.glTranslatef(1F, 0.375F, 0.905F);
			
			if (meta / 2 == 2) {
				GL11.glRotatef(90F, 0F, 1F, 0F);
				GL11.glTranslatef(-0.0625F, 0F, 0F);
			}
			if (meta == 2)
				GL11.glTranslatef(0F, 0F, 0.375F);
			if (meta == 3) {
				// Rotate, Flip.
				GL11.glRotatef(180F, 0F, 1F, 0F);
				GL11.glTranslatef(0F, 0F, 0.2F);
				//GL11.glTranslatef(0F, 0F, -0.375F);
			}
			if (meta == 4) {
				GL11.glTranslatef(0F, 0F, 0.2875F);
			}
			if (meta == 5) {
				//Rotate, Flip.
				GL11.glRotatef(180F, 0F, 1F, 0F);
				GL11.glTranslatef(0F, 0F, 0.3F);
				//GL11.glTranslatef(0F, 0F, -0.5F);
			}
		}
		GL11.glScalef(2F, 2F, 2F);
		if (stack.getItem() instanceof ItemBlock) {
			// GL11.glRotatef(90F, -1, 0F, 0F);
			GL11.glTranslatef(0F, 0.2125F, 0.0375F);
		}
		
		RenderItem.renderInFrame = true;
		RenderManager.instance.renderEntityWithPosYaw(entityitem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
		RenderItem.renderInFrame = false;
		
		GL11.glPopMatrix();
	}
	
	@Override
	public void renderTileEntityAt(TileEntity logic, double var2, double var4, double var6, float var8) {
		this.render((TE_DryingRack) logic, var2, var4, var6, var8);
	}
	
	public static class FancyEntityItem extends EntityItem {
		public FancyEntityItem(World par1World, double par2, double par4, double par6) {
			super(par1World, par2, par4, par6);
			this.isImmuneToFire = true;
			this.lifespan       = 72000;
		}
		
		public FancyEntityItem(World par1World, double par2, double par4, double par6, ItemStack par8ItemStack) {
			this(par1World, par2, par4, par6);
			this.setEntityItemStack(par8ItemStack);
			this.lifespan = (par8ItemStack.getItem() == null ? 6000 : par8ItemStack.getItem().getEntityLifespan(par8ItemStack, par1World));
		}
		
		public FancyEntityItem(World par1World) {
			super(par1World);
			this.isImmuneToFire = true;
			this.lifespan       = 72000;
		}
		
		public FancyEntityItem(World world, Entity original, ItemStack stack) {
			this(world, original.posX, original.posY, original.posZ);
			this.delayBeforeCanPickup = 20;
			this.motionX              = original.motionX;
			this.motionY              = original.motionY;
			this.motionZ              = original.motionZ;
			this.setEntityItemStack(stack);
		}
		
		public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
			return par1DamageSource.getDamageType().equals("outOfWorld");
		}
	}
}