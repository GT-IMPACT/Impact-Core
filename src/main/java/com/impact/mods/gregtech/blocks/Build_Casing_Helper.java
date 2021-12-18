package com.impact.mods.gregtech.blocks;

import gregtech.api.enums.Textures;
import gregtech.api.interfaces.IItemContainer;
import gregtech.api.interfaces.ITexture;

import static com.impact.mods.gregtech.GT_ItemList.*;

public enum Build_Casing_Helper {

    NUKE_TURBINE_CASING("Nuclear Turbine Casing", 3, 2, 0, NukeTurbineCasing),
    ELECTROMAGNETIC_CASING("Electromagnetic Casing", 3, 2, 1, ElectromagneticCasing),
    EXTRADIFICATION_CASING("Extradification Casing", 3, 2, 2, ExtradificationCasing),
    MACERATION_CASING("Maceration Casing", 3, 2, 3, MacerationCasing),
    DDD_PRINTER_CASING("3D Printed Casing", 3, 2, 4, DDDPrinterCasing),
    DDD_PRINTER_CASING3X3("Configuration 3x3 Casing", 3, 2, 5, DDDPrinterCasing3x3),
    DDD_PRINTER_CASING4X4("Configuration 4x4 Casing", 3, 2, 6, DDDPrinterCasing4x4),
    PRIMITIVE_WATER_PUMP_CASING("Primitive Pump Deck", 3, 2, 7, PrimitiveWaterPumpCase),
    LSCC_CASING("Lapotronic Super Capacitor Casing", 3, 2, 8, LSCC),
    SAW_CASING("Wooden Casing", 3, 2, 9, SawCase),
    NQ_CASING("Naquadah Base Casing", 3, 2, 10, NqCasing),
    CYCLONE_CASING("Cyclone Casing", 3, 2, 11, CycloneCasing),
    MOON_MINER_CASING("Moon Miner Casing", 3, 2, 12, MoonMinerCasing),
    RAILASSEMBLER_CASING("Rail Assembler Casing", 3, 2, 13, RailAssemblerCasing),
    SPACE_ELEVATOR_CASING("Space Elevator Casing", 3, 2, 14, SpaceElevatorCasing),
    LAB_SAFELG_CASING("Lab-Safe Low Gravity Casing", 3, 2, 15, LabSafeLGCasing),

    ME_CASING("ME Construction Casing", 3, 3, 0, MECasing),
    AEROSTATE_PLATFORM("Aerostate Platform Casing", 3, 3, 1, AerostateCasing),
    MILL("The Mill Wood Planks", 3, 3, 2, MillCasing),

    //EMPTY("", 0, 0, 0, null)
    ;

    private final String mName;
    private final int mIDCasing;
    private final int mMeta;
    private final IItemContainer itemContainer;
    private final ITexture iTexture;

    Build_Casing_Helper(String name, int pageTexture, int countBlock, int meta, IItemContainer itemContainer) {
        countBlock--;
        this.mName = name;
        this.mIDCasing = meta + (16 * (countBlock)) + (pageTexture * 128);
        this.mMeta = meta;
        this.itemContainer = itemContainer;
        this.iTexture = Textures.BlockIcons.casingTexturePages[pageTexture][meta + (16 * (countBlock))];
    }

    public ITexture getITexture() {
        return iTexture;
    }

    public IItemContainer getItemContainer() {
        return itemContainer;
    }

    public String getName() {
        return mName;
    }

    public int getIDCasing() {
        return mIDCasing;
    }

    public int getMeta() {
        return mMeta;
    }
}