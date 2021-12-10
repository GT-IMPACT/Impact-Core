package com.impact.mixins.vanilla;

import com.impact.core.Config;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;
import org.lwjgl.opengl.Display;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

@Mixin(ForgeHooksClient.class)
public class ForgeHooksClient_Mixin {
	
	@Inject(method = "createDisplay", at = @At("HEAD"), remap = false)
	private static void createDisplay(CallbackInfo ci) {
		Display.setTitle("IMPACT GREGTECH EDITION");
		ResourceLocation icon = new ResourceLocation("impact", "textures/gui/title/fav.png");
		try {
			InputStream inputstream = Config.class.getResourceAsStream("/assets/" + icon.getResourceDomain() + "/" + icon.getResourcePath());
			Display.setIcon(new ByteBuffer[]{call(inputstream)});
		} catch (IOException ignore) {
		}
	}
	
	private static ByteBuffer call(InputStream is) throws IOException {
		BufferedImage bufferedimage = ImageIO.read(is);
		int[] aint = bufferedimage.getRGB(0, 0, bufferedimage.getWidth(), bufferedimage.getHeight(), null, 0, bufferedimage.getWidth());
		ByteBuffer bytebuffer = ByteBuffer.allocate(4 * aint.length);
		for (int k : aint) bytebuffer.putInt(k << 8 | k >> 24 & 255);
		bytebuffer.flip();
		return bytebuffer;
	}
}
