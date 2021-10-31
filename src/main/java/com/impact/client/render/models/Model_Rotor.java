package com.impact.client.render.models;

import com.impact.core.Refstrings;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

@SideOnly(Side.CLIENT)
public class Model_Rotor extends ModelBase {
	
	ModelRenderer arm1;
	ModelRenderer arm2;
	ModelRenderer arm3;
	ModelRenderer arm4;
	
	String texturePath;
	
	public Model_Rotor(int height) {
		
		textureWidth  = 32;
		textureHeight = 256;
		
		texturePath = Refstrings.MODID + ":" + "textures/renderers/item." + 3 + ".png";
		
		arm1 = new ModelRenderer(this, 0, 0);
		arm1.addBox(0F, 0F, -6F, 1, height, 12);
		arm1.setRotationPoint(-8F, 0F, 0F);
		arm1.setTextureSize(32, 256);
		arm1.mirror = true;
		setRotation(arm1, 0F, -0.3926991F, 0F);
		
		arm2 = new ModelRenderer(this, 0, 0);
		arm2.addBox(0F, 0F, -6F, 1, height, 12);
		arm2.setRotationPoint(-8F, 0F, 0F);
		arm2.setTextureSize(32, 256);
		arm2.mirror = true;
		setRotation(arm2, 1.570796F, 0F, -0.3926991F);
		
		arm3 = new ModelRenderer(this, 0, 0);
		arm3.addBox(0F, 0F, -6F, 1, height, 12);
		arm3.setRotationPoint(-8F, 0F, 0F);
		arm3.setTextureSize(32, 256);
		arm3.mirror = true;
		setRotation(arm3, -1.570796F, 0F, 0.3926991F);
		
		arm4 = new ModelRenderer(this, 0, 0);
		arm4.addBox(0F, 0F, -6F, 1, height, 12);
		arm4.setRotationPoint(-8F, 0F, 0F);
		arm4.setTextureSize(32, 256);
		arm4.mirror = true;
		setRotation(arm4, 3.141593F, 0.3926991F, 0F);
	}
	
	public String getRenderTexture() {
		return texturePath;
	}
	
	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5);
		arm1.render(f5);
		arm2.render(f5);
		arm3.render(f5);
		arm4.render(f5);
	}
	
	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
	
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, null);
	}
}