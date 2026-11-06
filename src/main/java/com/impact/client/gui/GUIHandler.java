package com.impact.client.gui;

import com.impact.addon.gt.api.security.SecurityContainer;
import com.impact.addon.gt.api.security.SecurityGui;
import com.impact.impact;
import com.impact.mods.gregtech.gui.aerostat.ContainerSelectAerostat;
import com.impact.mods.gregtech.gui.aerostat.Container_FirstAerostat;
import com.impact.mods.gregtech.gui.aerostat.GUISelectAerostat;
import com.impact.mods.gregtech.gui.aerostat.GUI_FirstAerostat;
import com.impact.mods.gregtech.tileentities.multi.units.GTMTE_Aerostat;
import com.impact.mods.gregtech.gui.laptop.Container_LapTopSetting;
import com.impact.mods.gregtech.gui.laptop.GUI_LapTopSetting;
import com.impact.mods.gregtech.gui.regulatechest.Container_ValueRegulateChest;
import com.impact.mods.gregtech.gui.regulatechest.GUI_ValueRegulateChest;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import static com.impact.core.impactLog.INFO;
import static com.impact.core.impactLog.WARNING;

public class GUIHandler implements IGuiHandler {

    public static final int GUI_ID_LapTop = 3, GUI_ID_RegulatingChest = 6, GUI_ID_Security = 8;

    public static final int GUI_ID_AerostatSelect = 4;
    public static final int GUI_ID_AerostatName = 5;

    public GUIHandler() {
        NetworkRegistry.INSTANCE.registerGuiHandler(impact.instance, this);
    }

    @Override
    public Object getServerGuiElement(
            int ID,
            EntityPlayer player,
            World world,
            int x,
            int y,
            int z
    ) {
        TileEntity te = world.getTileEntity(x, y, z);

        switch (ID) {
            case GUI_ID_LapTop:
                if (te instanceof IGregTechTileEntity) {
                    IGregTechTileEntity gte = (IGregTechTileEntity) te;
                    return new Container_LapTopSetting(player.inventory, gte);
                }
            case GUI_ID_AerostatSelect:
            case GUI_ID_AerostatName:
                if (te instanceof IGregTechTileEntity) {
                    IGregTechTileEntity tile = (IGregTechTileEntity) te;
                    if (tile.getMetaTileEntity() instanceof GTMTE_Aerostat) {
                        return ID == GUI_ID_AerostatSelect
                                ? new ContainerSelectAerostat(player.inventory, tile)
                                : new Container_FirstAerostat(player.inventory, tile);
                    }
                }
                return null;
            case GUI_ID_RegulatingChest:
                if (te instanceof IGregTechTileEntity) {
                    IGregTechTileEntity gte = (IGregTechTileEntity) te;
                    return new Container_ValueRegulateChest(player.inventory, gte);
                }
            case GUI_ID_Security:
                return new SecurityContainer(player);
        }
        WARNING("GUIHandler Server - Not Loaded");
        return null;
    }

    @Override
    public Object getClientGuiElement(
            int ID,
            EntityPlayer player,
            World world,
            int x,
            int y,
            int z
    ) {
        TileEntity te = world.getTileEntity(x, y, z);

        switch (ID) {
            case GUI_ID_LapTop:
                if (te instanceof IGregTechTileEntity) {
                    IGregTechTileEntity gte = (IGregTechTileEntity) te;
                    return new GUI_LapTopSetting(player.inventory, gte, gte.getInventoryName());
                }
            case GUI_ID_AerostatSelect:
            case GUI_ID_AerostatName:
                if (te instanceof IGregTechTileEntity) {
                    IGregTechTileEntity tile = (IGregTechTileEntity) te;
                    if (tile.getMetaTileEntity() instanceof GTMTE_Aerostat) {
                        return ID == GUI_ID_AerostatSelect
                                ? new GUISelectAerostat(player.inventory, tile, "Aerostat")
                                : new GUI_FirstAerostat(player.inventory, tile);
                    }
                }
                return null;
            case GUI_ID_RegulatingChest:
                if (te instanceof IGregTechTileEntity) {
                    IGregTechTileEntity gte = (IGregTechTileEntity) te;
                    return new GUI_ValueRegulateChest(player.inventory, gte);
                }
            case GUI_ID_Security:
                return new SecurityGui(player);
        }
        INFO("GUIHandler Client - Loaded");
        return null;
    }
}
