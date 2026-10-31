package com.impact.mods.gregtech.blocks;

import gregtech.api.interfaces.ICasing;
import java.util.function.Supplier;
import net.minecraft.block.Block;

/**
 * Named casing variants shared by structures, construction, textures and
 * tooltips.
 */
public enum ImpactCasings implements ICasing {
    NUKE_TURBINE_CASING(BlockSet.CASINGS_2, 0),
    ELECTROMAGNETIC_CASING(BlockSet.CASINGS_2, 1),
    EXTRADIFICATION_CASING(BlockSet.CASINGS_2, 2),
    MACERATION_CASING(BlockSet.CASINGS_2, 3),
    DDD_PRINTER_CASING(BlockSet.CASINGS_2, 4),
    DDD_PRINTER_CASING3X3(BlockSet.CASINGS_2, 5),
    DDD_PRINTER_CASING4X4(BlockSet.CASINGS_2, 6),
    PRIMITIVE_WATER_PUMP_CASING(BlockSet.CASINGS_2, 7),
    LSCC_CASING(BlockSet.CASINGS_2, 8),
    SAW_CASING(BlockSet.CASINGS_2, 9),
    NQ_CASING(BlockSet.CASINGS_2, 10),
    CYCLONE_CASING(BlockSet.CASINGS_2, 11),
    MOON_MINER_CASING(BlockSet.CASINGS_2, 12),
    RAILASSEMBLER_CASING(BlockSet.CASINGS_2, 13),
    SPACE_ELEVATOR_CASING(BlockSet.CASINGS_2, 14),
    LAB_SAFELG_CASING(BlockSet.CASINGS_2, 15),
    ME_CASING(BlockSet.CASINGS_3, 0),
    AEROSTATE_PLATFORM(BlockSet.CASINGS_3, 1),
    MILL(BlockSet.CASINGS_3, 2),
    UPGRADE_CASING_T1(BlockSet.CASINGS_1, 0),
    UPGRADE_CASING_T2(BlockSet.CASINGS_1, 1),
    UPGRADE_CASING_T3(BlockSet.CASINGS_1, 2),
    UPGRADE_CASING_T4(BlockSet.CASINGS_1, 3),
    PBECASING(BlockSet.CASINGS_1, 4),
    ENGRAVER_CASING(BlockSet.CASINGS_1, 5),
    ASSEMBLER_CASING(BlockSet.CASINGS_1, 6),
    CENTRIFUGE_CASING(BlockSet.CASINGS_1, 7),
    ELECTROLYZER_CASING(BlockSet.CASINGS_1, 8),
    WIRE_FACTORY_CASING(BlockSet.CASINGS_1, 9),
    SUPPLY_PRODUCTION_CASING(BlockSet.CASINGS_1, 10),
    UTILITY_MACHINE_CASING(BlockSet.CASINGS_1, 11),
    BREWMENTER_CASING(BlockSet.CASINGS_1, 12),
    ARC_CASING(BlockSet.CASINGS_1, 13),
    CUTTING_CASING(BlockSet.CASINGS_1, 14),
    MIXING_CASING(BlockSet.CASINGS_1, 15),
    CASING_FARM(BlockSet.SPECIAL_CASINGS, 3),
    HUGE_CASING_TURBINE(BlockSet.SPECIAL_CASINGS, 4),
    SPACE_SATELLITE_CASING(BlockSet.SPECIAL_CASINGS, 5),
    TOWER_CASING(BlockSet.SPECIAL_CASINGS, 6),
    COMPUTER_CASING(BlockSet.SPECIAL_CASINGS, 7);

    private final BlockSet blockSet;
    private final int metadata;

    ImpactCasings(BlockSet blockSet, int metadata) {
        this.blockSet = blockSet;
        this.metadata = metadata;
    }

    @Override
    public Block getBlock() {
        return blockSet.block.get();
    }

    @Override
    public int getMetadata() {
        return metadata;
    }

    @Override
    public int getTextureId() {
        return blockSet.textureOffset + metadata;
    }

    private enum BlockSet {
        CASINGS_1(() -> ImpactBlocks.sCaseCore1, 3, 0),
        CASINGS_2(() -> ImpactBlocks.sCaseCore2, 3, 16),
        CASINGS_3(() -> ImpactBlocks.sCaseCore3, 3, 32),
        SPECIAL_CASINGS(() -> ImpactBlocks.sCasePage8_3, 8, 64);

        private final Supplier<Block> block;
        private final int textureOffset;

        BlockSet(Supplier<Block> block, int page, int firstIndex) {
            this.block = block;
            this.textureOffset = page * 128 + firstIndex;
        }
    }
}
