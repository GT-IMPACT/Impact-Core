package com.impact.mods.gregtech.enums;

import gregtech.api.GregTech_API;
import gregtech.api.interfaces.IIconContainer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

import static gregtech.api.enums.GT_Values.RES_PATH_BLOCK;

public class Texture {

  public enum Icons implements IIconContainer, Runnable {

    /*OVERLAYS*/
    SAW, SAW_ACTIVE, SAW_FRONT, SAW_FRONT_ACTIVE, LPS, LPS_ACTIVE, OVERLAY_RAIL_ASSEMBLER, OVERLAY_RAIL_ASSEMBLER_ACTIVE,
    OVERLAY_SPACE_ELEVATOR_ACTIVE, OVERLAY_SPACE_ELEVATOR, OVERLAY_BASIC_MINER_ACTIVE, OVERLAY_BASIC_MINER,
    OVERLAY_MCHAMBER_ITEM_OVERLAY,

    /*BLOCK ICONS*/
    CASING_PARALLEL1, CASING_PARALLEL2, CASING_PARALLEL3, CASING_PARALLEL4, CASING_PBE, CASING_LASER, CASING_ASSEMBLER, CASING_MIXER,
    CASING_CENTRIFUGE, CASING_ELECTROLYZER, CASING_WIRE, CASING_SUPPLY, CASING_UTILITY, CASING_BREWMENTER, CASING_ARC, CASING_CUTTER,

    CASING_PRINTER, CASING_PRINTER3X3, CASING_PRINTER4X4, CASING_MACERATOR, CASING_EXTRADIFICATOR, CASING_ELECTROMAGNETIC,
    CASING_PUMP_TOP, CASING_PUMP, CASING_LSC_TOP, CASING_LSC, CASING_SAW, CASING_NAQUADAH, OVERLAY_MULTIHATCH,

    CASING_HYPER_TOP, CASING_HYPER_SIDE1, CASING_HYPER_SIDE2, CASING_CYCLONE, CASING_MOON_MINER,
    CASING_RAIL_ASSEMBLER_SIDE, CASING_RAIL_ASSEMBLER_TOP, CASING_SPACE_ELEVATOR_TOP, CASING_LAB_SAVE_LG,

    SPACE_SATELLITE_CASING, COMPUTER_CASING, TOWER_CASING, CASING_ME,
    
    PLATFORM_AEROSTATE_TOP, PLATFORM_AEROSTATE_SIDE, PLATFORM_AEROSTATE_OVERLAY, MILL_CASING,

    PRL_HATCH_RED, PRL_HATCH_YELLOW, PRL_HATCH_GREEN,

    OVERLAY_REACTOR_HATCH_INACTIVE, OVERLAY_REACTOR_HATCH_ACTIVE,

    OVERLAY_AE_CONNECTOR_INACTIVE, OVERLAY_AE_CONNECTOR_ACTIVE,

    RACK_OVERLAY, RACK_OVERLAY_ACTIVE, TOWER_OVERLAY, TOWER_OVERLAY_ACTIVE,
    REACTOR_OVERLAY, REACTOR_OVERLAY_ACTIVE,
    
    MAINTENANCE_OVERLAY,

    LONG_DISTANCE_PIPE_FLUID, LONG_DISTANCE_PIPE_ITEM,
    TLDPE_LV, TLDPE_MV, TLDPE_HV, TLDPE_EV, TLDPE_IV,
    
    OVERLAY_PIPELINE_FLUID_BACK, OVERLAY_PIPELINE_FLUID_FRONT, OVERLAY_PIPELINE_FLUID_SIDE,
    OVERLAY_PIPELINE_ITEM_BACK, OVERLAY_PIPELINE_ITEM_FRONT, OVERLAY_PIPELINE_ITEM_SIDE, OVERLAY_PIPELINE_ENERGY_SIDE,
  
    OVERLAY_LASER_INPUT, OVERLAY_LASER_OUTPUT,

    FORGE_HAMMER_HEAD, FORGE_HAMMER_HANDLE, LUMBER_AXE_HEAD, LUMBER_AXE_HANDLE,
    
    //TT textures
    READER_ONLINE, READER_OFFLINE,
  
    ORE_SAMPLE, ORE_SAMPLE_ACTIVE,
    
    OVERLAY_OIL_DRILL, OVERLAY_OIL_DRILL_ACTIVE,
    
    DATA_SCIENCE_CABLE, DATA_SCIENCE_CABLE_OVERLAY,
    DATA_CONNECTOR_ACTIVE, DATA_CONNECTOR_SIDE, DATA_CONNECTOR_CONNECT,

    VOID;

    public static final IIconContainer[]
        CONNECTED_FUSHULLS = new IIconContainer[]{
        CASING_HYPER_SIDE1,
        CASING_HYPER_SIDE2,
        CASING_HYPER_SIDE1,
        CASING_HYPER_SIDE1,
        CASING_HYPER_SIDE1,
        CASING_HYPER_SIDE1,
        CASING_HYPER_SIDE1,
        CASING_HYPER_SIDE1,
        CASING_HYPER_SIDE1,
        CASING_HYPER_SIDE1,
        CASING_HYPER_SIDE1,
        CASING_HYPER_SIDE1
    },
        Test = new IIconContainer[]{

        };
    
    protected IIcon mIcon;

    Icons() {
      GregTech_API.sGTBlockIconload.add(this);
    }

    @Override
    public IIcon getIcon() {
      return mIcon;
    }

    @Override
    public IIcon getOverlayIcon() {
      return null;
    }

    @Override
    public void run() {
      mIcon = GregTech_API.sBlockIcons.registerIcon(RES_PATH_BLOCK + "iconsets/" + this);
    }

    @Override
    public ResourceLocation getTextureFile() {
      return TextureMap.locationBlocksTexture;
    }
  }
}
