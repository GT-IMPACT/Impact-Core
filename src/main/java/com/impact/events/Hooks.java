package com.impact.events;


import com.impact.core.Config;
import gloomyfolken.hooklib.asm.Hook;
import gloomyfolken.hooklib.asm.ReturnCondition;
import net.minecraft.block.Block;
import net.minecraft.block.BlockObsidian;
import net.minecraft.block.BlockQuartz;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;
import org.lwjgl.opengl.Display;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class Hooks {
    //-Dfml.coreMods.load=com.impact.loader.HooksLoader
    @Hook
    public static void createDisplay(ForgeHooksClient mc) {
        Display.setTitle("Impact");
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
        int[] aint1 = aint;
        int i = aint.length;

        for (int j = 0; j < i; ++j) {
            int k = aint1[j];
            bytebuffer.putInt(k << 8 | k >> 24 & 255);
        }

        bytebuffer.flip();
        return bytebuffer;
    }

    @Hook(injectOnExit = true, returnCondition = ReturnCondition.ALWAYS)
    public static void setHarvestLevel(BlockQuartz block, String toolClass, int level) {
        block.setHarvestLevel(toolClass, 0);
    }

    @Hook(injectOnExit = true, returnCondition = ReturnCondition.ALWAYS)
    public static void setHarvestLevel(BlockObsidian block, String toolClass, int level) {
        block.setHarvestLevel(toolClass, 3);
    }
}
