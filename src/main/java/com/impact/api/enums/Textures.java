package com.impact.api.enums;

import gregtech.api.GregTech_API;
import gregtech.api.interfaces.IIconContainer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

import static gregtech.api.enums.GT_Values.RES_PATH_BLOCK;

public class Textures {
    public enum Icons implements IIconContainer, Runnable {

        /*OVERLAYS*/
        SAW, SAW_ACTIVE, SAW_FRONT, SAW_FRONT_ACTIVE, LPS, LPS_ACTIVE,

        /*BLOCK ICONS*/
        CASING_PARALLEL1, CASING_PARALLEL2, CASING_PARALLEL3, CASING_PARALLEL4, CASING_PBE, CASING_LASER, CASING_ASSEMBLER, CASING_MIXER,
        CASING_CENTRIFUGE, CASING_ELECTROLYZER, CASING_WIRE, CASING_SUPPLY, CASING_UTILITY, CASING_BREWMENTER, CASING_ARC, CASING_CUTTER,

        CASING_PRINTER, CASING_PRINTER3X3, CASING_PRINTER4X4, CASING_MACERATOR, CASING_EXTRADIFICATOR, CASING_ELECTROMAGNETIC,
        CASING_PUMP_TOP, CASING_PUMP, CASING_LSC_TOP, CASING_LSC, CASING_SAW, OVERLAY_MULTIHATCH,


        VOID;

        public static final IIconContainer[]
                EXAMPLE = new IIconContainer[]{}, EXAMPLE2 = new IIconContainer[]{};


        protected IIcon mIcon;

        private Icons() {
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
