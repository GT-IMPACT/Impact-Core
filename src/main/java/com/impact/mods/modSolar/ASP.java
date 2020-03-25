package com.impact.mods.modSolar;

import com.impact.mods.modSolar.common.*;
import com.impact.mods.modSolar.common.TE.*;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;

public class ASP {

    public static Block blockAdvSolarPanel;
    public static int[][] sideAndFacingToSpriteOffset;

    public static void preInit() {
        blockAdvSolarPanel = new BlockAdvSolarPanel();
        GameRegistry.registerBlock(blockAdvSolarPanel, ItemAdvSolarPanel.class, "BlockAdvSolarPanel");

        GameRegistry.registerTileEntity(TileEntity8SolarPanel.class, "ULV Solar Panel");
        GameRegistry.registerTileEntity(TileEntity32SolarPanel.class, "LV Solar Panel");
        GameRegistry.registerTileEntity(TileEntity128SolarPanel.class, "MV Solar Panel");
        GameRegistry.registerTileEntity(TileEntity512SolarPanel.class, "HV Solar Panel");
        GameRegistry.registerTileEntity(TileEntity2048SolarPanel.class, "EV Solar Panel");
        GameRegistry.registerTileEntity(TileEntity2048SolarPanel.class, "IV Solar Panel");
        GameRegistry.registerTileEntity(TileEntity32768SolarPanel.class, "LuV Solar Panel");
        GameRegistry.registerTileEntity(TileEntity131072SolarPanel.class, "ZPM Solar Panel");
        GameRegistry.registerTileEntity(TileEntity524288SolarPanel.class, "UV Solar Panel");
    }

    public static void load() {
        try {
            sideAndFacingToSpriteOffset = (int[][])((int[][])Class.forName("ic2.core.block.BlockMultiID").getField("sideAndFacingToSpriteOffset").get((Object)null));
        } catch (Exception var2) {
            sideAndFacingToSpriteOffset = new int[][]{{3, 2, 0, 0, 0, 0}, {2, 3, 1, 1, 1, 1}, {1, 1, 3, 2, 5, 4}, {0, 0, 2, 3, 4, 5}, {4, 5, 4, 5, 3, 2}, {5, 4, 5, 4, 2, 3}};
        }
    }

}
