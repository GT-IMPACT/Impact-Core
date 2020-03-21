package com.impact.guihandler;

import com.impact.GTSU.container.ContainerGTSU;
import com.impact.GTSU.gui.GuiGTSU;
import com.impact.GTSU.tileentity.TileEntityGTSU;
import com.impact.item.Circuit_Programmer.GT_Container_CircuitProgrammer;
import com.impact.item.Circuit_Programmer.GT_GUIContainer_CircuitProgrammer;
import com.impact.modChest.Steel_Chest.ContainerSteelChest;
import com.impact.modChest.Steel_Chest.GuiSteelChest;
import com.impact.modChest.Steel_Chest.TESteelChest;
import com.impact.modChest.WroughtIron_Chest.ContainerWroughtIronChest;
import com.impact.modChest.WroughtIron_Chest.GuiWroughtIronChest;
import com.impact.modChest.WroughtIron_Chest.TEWroughtIronChest;
import com.impact.modChest.chestAL.ContainerChestAl;
import com.impact.modChest.chestAL.GuiChestAl;
import com.impact.modChest.chestAL.TEChestAl;
import com.impact.modChest.chestCr.ContainerChestCr;
import com.impact.modChest.chestCr.GuiChestCr;
import com.impact.modChest.chestCr.TEChestCr;
import com.impact.modChest.chestHSLA.ContainerChestHSLA;
import com.impact.modChest.chestHSLA.GuiChestHSLA;
import com.impact.modChest.chestHSLA.TEChestHSLA;
import com.impact.modChest.chestIr.ContainerChestIr;
import com.impact.modChest.chestIr.GuiChestIr;
import com.impact.modChest.chestIr.TEChestIr;
import com.impact.modChest.chestNt.ContainerChestNt;
import com.impact.modChest.chestNt.GuiChestNt;
import com.impact.modChest.chestNt.TEChestNt;
import com.impact.modChest.chestOs.ContainerChestOs;
import com.impact.modChest.chestOs.GuiChestOs;
import com.impact.modChest.chestOs.TEChestOs;
import com.impact.modChest.chestTi.ContainerChestTi;
import com.impact.modChest.chestTi.GuiChestTi;
import com.impact.modChest.chestTi.TEChestTi;
import com.impact.modChest.chestW.ContainerChestW;
import com.impact.modChest.chestW.GuiChestW;
import com.impact.modChest.chestW.TEChestW;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GUIHandler implements IGuiHandler {

    public static final int GUI_ID_CIRCUITPROGRAMMER = 20, GUI_ID_GTSU = 0, GUI_ID_WroughtIronChest = 1, GUI_ID_SteelChest = 2,
            GUI_ID_AlChest = 3, GUI_ID_HSLA = 4, GUI_ID_TiChest = 5, GUI_ID_WChest = 6, GUI_ID_CrChest = 7, GUI_ID_IrChest = 8,
            GUI_ID_OsChest = 9, GUI_ID_NtChest = 10
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


                case GUI_ID_CIRCUITPROGRAMMER:
                    return new GT_GUIContainer_CircuitProgrammer(new GT_Container_CircuitProgrammer(player.inventory));
            }
        } else
            return getServerGuiElement(ID, player, world, x, y, z);

        return null;
    }

}