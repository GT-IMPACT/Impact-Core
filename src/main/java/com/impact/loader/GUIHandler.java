package com.impact.loader;

import com.impact.mods.GTSU.container.ContainerGTSU;
import com.impact.mods.GTSU.gui.GuiGTSU;
import com.impact.mods.GTSU.tileentity.TileEntityGTSU;
import com.impact.item.Circuit_Programmer.GT_Container_CircuitProgrammer;
import com.impact.item.Circuit_Programmer.GT_GUIContainer_CircuitProgrammer;
import com.impact.mods.modChest.Steel_Chest.ContainerSteelChest;
import com.impact.mods.modChest.Steel_Chest.GuiSteelChest;
import com.impact.mods.modChest.Steel_Chest.TESteelChest;
import com.impact.mods.modChest.WroughtIron_Chest.ContainerWroughtIronChest;
import com.impact.mods.modChest.WroughtIron_Chest.GuiWroughtIronChest;
import com.impact.mods.modChest.WroughtIron_Chest.TEWroughtIronChest;
import com.impact.mods.modChest.chestAL.ContainerChestAl;
import com.impact.mods.modChest.chestAL.GuiChestAl;
import com.impact.mods.modChest.chestAL.TEChestAl;
import com.impact.mods.modChest.chestCr.ContainerChestCr;
import com.impact.mods.modChest.chestCr.GuiChestCr;
import com.impact.mods.modChest.chestCr.TEChestCr;
import com.impact.mods.modChest.chestHSLA.ContainerChestHSLA;
import com.impact.mods.modChest.chestHSLA.GuiChestHSLA;
import com.impact.mods.modChest.chestHSLA.TEChestHSLA;
import com.impact.mods.modChest.chestIr.ContainerChestIr;
import com.impact.mods.modChest.chestIr.GuiChestIr;
import com.impact.mods.modChest.chestIr.TEChestIr;
import com.impact.mods.modChest.chestNt.ContainerChestNt;
import com.impact.mods.modChest.chestNt.GuiChestNt;
import com.impact.mods.modChest.chestNt.TEChestNt;
import com.impact.mods.modChest.chestOs.ContainerChestOs;
import com.impact.mods.modChest.chestOs.GuiChestOs;
import com.impact.mods.modChest.chestOs.TEChestOs;
import com.impact.mods.modChest.chestTi.ContainerChestTi;
import com.impact.mods.modChest.chestTi.GuiChestTi;
import com.impact.mods.modChest.chestTi.TEChestTi;
import com.impact.mods.modChest.chestW.ContainerChestW;
import com.impact.mods.modChest.chestW.GuiChestW;
import com.impact.mods.modChest.chestW.TEChestW;
import com.impact.mods.modSolar.client.GuiAdvSolarPanel;
import com.impact.mods.modSolar.common.TE.TileEntitySolarPanel;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GUIHandler implements IGuiHandler {

    public static final int GUI_ID_CIRCUITPROGRAMMER = 20, GUI_ID_GTSU = 0, GUI_ID_WroughtIronChest = 1, GUI_ID_SteelChest = 2,
            GUI_ID_AlChest = 3, GUI_ID_HSLA = 4, GUI_ID_TiChest = 5, GUI_ID_WChest = 6, GUI_ID_CrChest = 7, GUI_ID_IrChest = 8,
            GUI_ID_OsChest = 9, GUI_ID_NtChest = 10, GUI_ID_Solar = 11
            ;

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        final TileEntity entity = world.getTileEntity(x, y, z);
        if (entity == null) return null;
        switch (ID) {

            case GUI_ID_GTSU:
                if(entity instanceof TileEntityGTSU)
                    return new ContainerGTSU((TileEntityGTSU)entity, player);
            case GUI_ID_WroughtIronChest:
                if (entity instanceof TEWroughtIronChest)
                    return new ContainerWroughtIronChest((TEWroughtIronChest) entity, player.inventory);
            case GUI_ID_SteelChest:
                if (entity instanceof TESteelChest)
                    return new ContainerSteelChest((TESteelChest) entity, player.inventory);
            case GUI_ID_AlChest:
                if (entity instanceof TEChestAl)
                    return new ContainerChestAl((TEChestAl) entity, player.inventory);
            case GUI_ID_HSLA:
                if (entity instanceof TEChestHSLA)
                    return new ContainerChestHSLA((TEChestHSLA) entity, player.inventory);
            case GUI_ID_TiChest:
                if (entity instanceof TEChestTi)
                    return new ContainerChestTi((TEChestTi) entity, player.inventory);
            case GUI_ID_WChest:
                if (entity instanceof TEChestW)
                    return new ContainerChestW((TEChestW) entity, player.inventory);
            case GUI_ID_CrChest:
                if (entity instanceof TEChestCr)
                    return new ContainerChestCr((TEChestCr) entity, player.inventory);
            case GUI_ID_IrChest:
                if (entity instanceof TEChestIr)
                    return new ContainerChestIr((TEChestIr) entity, player.inventory);
            case GUI_ID_OsChest:
                if (entity instanceof TEChestOs)
                    return new ContainerChestOs((TEChestOs) entity, player.inventory);
            case GUI_ID_NtChest:
                if (entity instanceof TEChestNt)
                    return new ContainerChestNt((TEChestNt) entity, player.inventory);
            case GUI_ID_Solar:
                if (entity instanceof TileEntitySolarPanel)
                    return ((TileEntitySolarPanel)entity).getGuiContainer(player.inventory);


            case GUI_ID_CIRCUITPROGRAMMER:
                return new GT_Container_CircuitProgrammer(player.inventory);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (FMLCommonHandler.instance().getSide().isClient()) {
            TileEntity entity = world.getTileEntity(x, y, z);
            if (entity == null) return null;
            switch (ID) {

                case GUI_ID_GTSU:
                    if (entity instanceof TileEntityGTSU)
                        return new GuiGTSU(ID, new ContainerGTSU((TileEntityGTSU) entity, player));
                case GUI_ID_WroughtIronChest:
                    if (entity instanceof TEWroughtIronChest)
                        return new GuiWroughtIronChest((TEWroughtIronChest) entity, player.inventory);
                case GUI_ID_SteelChest:
                    if (entity instanceof TESteelChest)
                        return new GuiSteelChest((TESteelChest) entity, player.inventory);
                case GUI_ID_AlChest:
                    if (entity instanceof TEChestAl)
                        return new GuiChestAl((TEChestAl) entity, player.inventory);
                case GUI_ID_HSLA:
                    if (entity instanceof TEChestHSLA)
                        return new GuiChestHSLA((TEChestHSLA) entity, player.inventory);
                case GUI_ID_TiChest:
                    if (entity instanceof TEChestTi)
                        return new GuiChestTi((TEChestTi) entity, player.inventory);
                case GUI_ID_WChest:
                    if (entity instanceof TEChestW)
                        return new GuiChestW((TEChestW) entity, player.inventory);
                case GUI_ID_CrChest:
                    if (entity instanceof TEChestCr)
                        return new GuiChestCr((TEChestCr) entity, player.inventory);
                case GUI_ID_IrChest:
                    if (entity instanceof TEChestIr)
                        return new GuiChestIr((TEChestIr) entity, player.inventory);
                case GUI_ID_OsChest:
                    if (entity instanceof TEChestOs)
                        return new GuiChestOs((TEChestOs) entity, player.inventory);
                case GUI_ID_NtChest:
                    if (entity instanceof TEChestNt)
                        return new GuiChestNt((TEChestNt) entity, player.inventory);
                case GUI_ID_Solar:
                    if (entity instanceof TileEntitySolarPanel)
                        return new GuiAdvSolarPanel(player.inventory, (TileEntitySolarPanel) entity);


                case GUI_ID_CIRCUITPROGRAMMER:
                    return new GT_GUIContainer_CircuitProgrammer(new GT_Container_CircuitProgrammer(player.inventory));
            }
        } else
            return getServerGuiElement(ID, player, world, x, y, z);

        return null;
    }
}