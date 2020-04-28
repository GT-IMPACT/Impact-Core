package com.impact.mods.GregTech.GTregister;

import com.github.technus.tectech.mechanics.alignment.enumerable.ExtendedFacing;
import com.github.technus.tectech.mechanics.constructable.IMultiblockInfoContainer;
import com.github.technus.tectech.mechanics.structure.IStructureDefinition;
import com.github.technus.tectech.mechanics.structure.StructureDefinition;
import com.impact.mods.GregTech.tileentities.multi.*;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

import static com.github.technus.tectech.mechanics.constructable.IMultiblockInfoContainer.registerMetaClass;
import static com.github.technus.tectech.mechanics.structure.StructureUtility.ofBlock;
import static com.impact.loader.ItemRegistery.decorateBlock;
import static com.impact.mods.GregTech.casings.CORE_API.sCaseCore1;
import static com.impact.mods.GregTech.casings.CORE_API.sCaseCore2;
import static gregtech.api.GregTech_API.*;
import static gregtech.api.enums.GT_Values.E;


public class TecTech_BuildGuide_Impact implements Runnable {

    @Override
    public void run() {
        //PBE
        registerMetaClass(GTMTE_PressBendExtrud.class, new IMultiblockInfoContainer<GTMTE_PressBendExtrud>() {
            //region Structure
            private final IStructureDefinition<GTMTE_PressBendExtrud> definition=
                    StructureDefinition.<GTMTE_PressBendExtrud>builder()
                            .addShapeOldApi("main",new String[][]{
                                    {"000","0.0","000",},
                                    {"000","010","000",},
                                    {"000","010","000",},
                                    {"000","010","000",},
                                    {"000","000","000",},
                            })
                            .addElement('0', ofBlock(sCaseCore1,4))
                            .addElement('1', ofBlock(sCaseCore1,0))
                            .build();
            private final String[] desc=new String[]{
                    EnumChatFormatting.RED+"Impact Details:",
                    "- PBE Casing",
                    "- Upgrade Casing (Tier 1-4) or Air (no parallel)",
                    "- Hatches (any PBE Casing)",
            };
            //endregion

            @Override
            public void construct(ItemStack stackSize, boolean hintsOnly, GTMTE_PressBendExtrud tileEntity, ExtendedFacing aSide) {
                IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
                definition.buildOrHints(tileEntity,"main", base.getWorld(),aSide,
                        base.getXCoord(),base.getYCoord(),base.getZCoord(),
                        1, 1, 0,hintsOnly);
            }

            @Override
            public String[] getDescription(ItemStack stackSize) {
                return desc;
            }
        });

        //Assembler
        registerMetaClass(GTMTE_Assembler.class, new IMultiblockInfoContainer<GTMTE_Assembler>() {
            //region Structure
            private final IStructureDefinition<GTMTE_Assembler> definition=
                    StructureDefinition.<GTMTE_Assembler>builder()
                            .addShapeOldApi("main", new String[][]{
                                    {"01110AAA0","011100.00","000000000",},
                                    {"011100000","022222220","000000000",},
                                    {"000000000","000000000","000000000",},
                            })
                            .addElement('0', ofBlock(sCaseCore1,6))
                            .addElement('1', ofBlock(decorateBlock[2],0))
                            .addElement('2', ofBlock(sCaseCore1,0))
                            .build();
            private final String[] desc=new String[]{
                    EnumChatFormatting.RED+"Impact Details:",
                    "- Assembler Casing",
                    "- I-Glass (any I-Glass)",
                    "- Upgrade Casing (Tier 1-4) or Air (no parallel)",
                    "- Hatches (any Assembler Casing)",
            };
            //endregion

            @Override
            public void construct(ItemStack stackSize, boolean hintsOnly, GTMTE_Assembler tileEntity, ExtendedFacing aSide) {
                IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
                definition.buildOrHints(tileEntity,"main", base.getWorld(),aSide,
                        base.getXCoord(),base.getYCoord(),base.getZCoord(),
                        6, 1, 0,hintsOnly);
            }

            @Override
            public String[] getDescription(ItemStack stackSize) {
                return desc;
            }
        });

        //Blast Smelter
        registerMetaClass(GTMTE_BlastSmelter.class, new IMultiblockInfoContainer<GTMTE_BlastSmelter>() {
            //region Structure
            private final IStructureDefinition<GTMTE_BlastSmelter> definition=
                    StructureDefinition.<GTMTE_BlastSmelter>builder()
                            .addShapeOldApi("main", new String[][]{
                                    {".000.",".111.",".111.",".0.0.",},
                                    {"00000","1AAA1","1AAA1","00000",},
                                    {"00000","1AAA1","1AAA1","00000",},
                                    {"00000","1AAA1","1AAA1","00000",},
                                    {".000.",".111.",".111.",".000.",},

                            })
                            .addElement('0', ofBlock(sBlockCasings8,3))
                            .addElement('1', ofBlock(sBlockCasings5,0))
                            .build();
            private final String[] desc=new String[]{
                    EnumChatFormatting.RED+"Impact Details:",
                    "- HSLA Casing",
                    "- Coil Block (any Coil Block)",
                    "- Hatches (any HSLA Casing)",
            };
            //endregion

            @Override
            public void construct(ItemStack stackSize, boolean hintsOnly, GTMTE_BlastSmelter tileEntity, ExtendedFacing aSide) {
                IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
                definition.buildOrHints(tileEntity,"main", base.getWorld(),aSide,
                        base.getXCoord(),base.getYCoord(),base.getZCoord(),
                        2, 3, 0,hintsOnly);
            }

            @Override
            public String[] getDescription(ItemStack stackSize) {
                return desc;
            }
        });

        //Freezifier
        registerMetaClass(GTMTE_FreezerSolidifier.class, new IMultiblockInfoContainer<GTMTE_FreezerSolidifier>() {
            //region Structure
            private final IStructureDefinition<GTMTE_FreezerSolidifier> definition=
                    StructureDefinition.<GTMTE_FreezerSolidifier>builder()
                            .addShapeOldApi("main", new String[][]{
                                    {".000.",".0.0.",".0.0.",".000.",},
                                    {"00000","0.0.0","0.0.0","00000",},
                                    {"00.00",".010.",".010.","00000",},
                                    {"00000","0.0.0","0.0.0","00000",},
                                    {".000.",".0.0.",".0.0.",".000.",},

                            })
                            .addElement('0', ofBlock(sBlockCasings2,1))
                            .addElement('1', ofBlock(sCaseCore1,0))
                            .build();
            private final String[] desc=new String[]{
                    EnumChatFormatting.RED+"Impact Details:",
                    "- Frost Proof Machine Casing",
                    "- Upgrade Casing (Tier 1-4) or Air (no parallel)",
                    "- Hatches (any Frost Proof Machine Casing)",
            };
            //endregion

            @Override
            public void construct(ItemStack stackSize, boolean hintsOnly, GTMTE_FreezerSolidifier tileEntity, ExtendedFacing aSide) {
                IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
                definition.buildOrHints(tileEntity,"main", base.getWorld(),aSide,
                        base.getXCoord(),base.getYCoord(),base.getZCoord(),
                        2, 0, 2,hintsOnly);
            }

            @Override
            public String[] getDescription(ItemStack stackSize) {
                return desc;
            }
        });

        //Laser Engraver
        registerMetaClass(GTMTE_LaserEng.class, new IMultiblockInfoContainer<GTMTE_LaserEng>() {
            //region Structure
            private final IStructureDefinition<GTMTE_LaserEng> definition=
                    StructureDefinition.<GTMTE_LaserEng>builder()
                            .addShapeOldApi("main", new String[][]{
                                    {"...","...","...","0.0","000",},
                                    {"000","...","...","432","000",},
                                    {"010",".5.","...","432","000",},
                                    {"010","...","...","432","000",},
                                    {"000","000","000","000","000",},

                            })
                            .addElement('0', ofBlock(sCaseCore1,5))
                            .addElement('1', ofBlock(sCaseCore1,0))
                            .addElement('2', ofBlock(decorateBlock[2],3))
                            .addElement('3', ofBlock(decorateBlock[2],2))
                            .addElement('4', ofBlock(decorateBlock[2],1))
                            .addElement('5', ofBlock(decorateBlock[2],0))
                            .build();
            private final String[] desc=new String[]{
                    EnumChatFormatting.RED+"Impact Details:",
                    "- Engraver Casing",
                    "- I-Glass (Red, Green, Blue and White)",
                    "- Upgrade Casing (Tier 1-4) or Engraver Casing (no parallel)",
                    "- Hatches (any Engraver Casing)",
            };
            //endregion

            @Override
            public void construct(ItemStack stackSize, boolean hintsOnly, GTMTE_LaserEng tileEntity, ExtendedFacing aSide) {
                IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
                definition.buildOrHints(tileEntity,"main", base.getWorld(),aSide,
                        base.getXCoord(),base.getYCoord(),base.getZCoord(),
                        1, 3, 0,hintsOnly);
            }

            @Override
            public String[] getDescription(ItemStack stackSize) {
                return desc;
            }
        });

        //Centrifuge
        registerMetaClass(GTMTE_Centrifuge.class, new IMultiblockInfoContainer<GTMTE_Centrifuge>() {
            //region Structure
            private final IStructureDefinition<GTMTE_Centrifuge> definition=
                    StructureDefinition.<GTMTE_Centrifuge>builder()
                            .addShapeOldApi("main", new String[][]{
                                    {"00000",".202.",".202.","00.00",},
                                    {"00000","2AAA2","2AAA2","00000",},
                                    {"00000","0A1A0","0A1A0","00000",},
                                    {"00000","2AAA2","2AAA2","00000",},
                                    {"00000",".202.",".202.","00000",},
                            })
                            .addElement('0', ofBlock(sCaseCore1,7))
                            .addElement('1', ofBlock(sCaseCore1,0))
                            .addElement('2', ofBlock(decorateBlock[2],0))
                            .build();
            private final String[] desc=new String[]{
                    EnumChatFormatting.RED+"Impact Details:",
                    "- Centrifuge Casing",
                    "- I-Glass (any I-Glass)",
                    "- Upgrade Casing (Tier 1-4) or Air (no parallel)",
                    "- Hatches (any Centrifuge Casing)",
            };
            //endregion

            @Override
            public void construct(ItemStack stackSize, boolean hintsOnly, GTMTE_Centrifuge tileEntity, ExtendedFacing aSide) {
                IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
                definition.buildOrHints(tileEntity,"main", base.getWorld(),aSide,
                        base.getXCoord(),base.getYCoord(),base.getZCoord(),
                        2, 3, 0,hintsOnly);
            }

            @Override
            public String[] getDescription(ItemStack stackSize) {
                return desc;
            }
        });

        //Electrolyzer
        registerMetaClass(GTMTE_Electrolyzer.class, new IMultiblockInfoContainer<GTMTE_Electrolyzer>() {
            //region Structure
            private final IStructureDefinition<GTMTE_Electrolyzer> definition=
                    StructureDefinition.<GTMTE_Electrolyzer>builder()
                            .addShapeOldApi("main", new String[][]{
                                    {"000","000","0.0","000",},
                                    {"020","212","000",".0.",},
                                    {"020","212","000",".0.",},
                                    {"020","212","000",".0.",},
                                    {"000","000","000","000",},
                            })
                            .addElement('0', ofBlock(sCaseCore1,8))
                            .addElement('1', ofBlock(sCaseCore1,0))
                            .addElement('2', ofBlock(decorateBlock[2],0))
                            .build();
            private final String[] desc=new String[]{
                    EnumChatFormatting.RED+"Impact Details:",
                    "- Electrolyzer Casing",
                    "- I-Glass (any I-Glass)",
                    "- Upgrade Casing (Tier 1-4) or Air (no parallel)",
                    "- Hatches (any Electrolyzer Casing)",
            };
            //endregion

            @Override
            public void construct(ItemStack stackSize, boolean hintsOnly, GTMTE_Electrolyzer tileEntity, ExtendedFacing aSide) {
                IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
                definition.buildOrHints(tileEntity,"main", base.getWorld(),aSide,
                        base.getXCoord(),base.getYCoord(),base.getZCoord(),
                        1, 2, 0,hintsOnly);
            }

            @Override
            public String[] getDescription(ItemStack stackSize) {
                return desc;
            }
        });

        //Wire Factory
        registerMetaClass(GTMTE_Wire.class, new IMultiblockInfoContainer<GTMTE_Wire>() {
            //region Structure
            private final IStructureDefinition<GTMTE_Wire> definition=
                    StructureDefinition.<GTMTE_Wire>builder()
                            .addShapeOldApi("main", new String[][]{
                                    {".0220","..220",".0000",},
                                    {".0220","00110","00000",},
                                    {"00220","00000","00000",},
                            })
                            .addElement('0', ofBlock(sCaseCore1,9))
                            .addElement('1', ofBlock(sCaseCore1,0))
                            .addElement('2', ofBlock(decorateBlock[2],0))
                            .build();
            private final String[] desc=new String[]{
                    EnumChatFormatting.RED+"Impact Details:",
                    "- Wire Factory Casing",
                    "- I-Glass (any I-Glass)",
                    "- Upgrade Casing (Tier 1-4) or Air (no parallel)",
                    "- Hatches (any Wire Factory Casing)",
            };
            //endregion

            @Override
            public void construct(ItemStack stackSize, boolean hintsOnly, GTMTE_Wire tileEntity, ExtendedFacing aSide) {
                IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
                definition.buildOrHints(tileEntity,"main", base.getWorld(),aSide,
                        base.getXCoord(),base.getYCoord(),base.getZCoord(),
                        1, 1, 0,hintsOnly);
            }

            @Override
            public String[] getDescription(ItemStack stackSize) {
                return desc;
            }
        });

        //Supply Production
        registerMetaClass(GTMTE_Supply.class, new IMultiblockInfoContainer<GTMTE_Supply>() {
            //region Structure
            private final IStructureDefinition<GTMTE_Supply> definition=
                    StructureDefinition.<GTMTE_Supply>builder()
                            .addShapeOldApi("main", new String[][]{
                                    {".000.",".202.",".202.",".202.",".000.",},
                                    {"00000","2AAA2","2AAA2","2AAA2","00000",},
                                    {"00.00","0A1A0","0A1A0","0A1A0","00000",},
                                    {"00000","2AAA2","2AAA2","2AAA2","00000",},
                                    {".000.",".202.",".202.",".202.",".000.",},
                            })
                            .addElement('0', ofBlock(sCaseCore1,10))
                            .addElement('1', ofBlock(sCaseCore1,0))
                            .addElement('2', ofBlock(decorateBlock[2],0))
                            .build();
            private final String[] desc=new String[]{
                    EnumChatFormatting.RED+"Impact Details:",
                    "- Supply Production Casing",
                    "- I-Glass (any I-Glass)",
                    "- Upgrade Casing (Tier 1-4) or Air (no parallel)",
                    "- Hatches (any Supply Production Casing)",
            };
            //endregion

            @Override
            public void construct(ItemStack stackSize, boolean hintsOnly, GTMTE_Supply tileEntity, ExtendedFacing aSide) {
                IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
                definition.buildOrHints(tileEntity,"main", base.getWorld(),aSide,
                        base.getXCoord(),base.getYCoord(),base.getZCoord(),
                        2, 0, 2,hintsOnly);
            }

            @Override
            public String[] getDescription(ItemStack stackSize) {
                return desc;
            }
        });

        //Utility Machine
        registerMetaClass(GTMTE_Utility.class, new IMultiblockInfoContainer<GTMTE_Utility>() {
            //region Structure
            private final IStructureDefinition<GTMTE_Utility> definition=
                    StructureDefinition.<GTMTE_Utility>builder()
                            .addShapeOldApi("main", new String[][]{
                                    {"......","000...","0.0...","000000",},
                                    {"..0000","000000","210000","000000",},
                                    {"..0000","000000","210000","000000",},
                                    {"..0000","000000","210000","000000",},
                                    {"......","000...","000...","000000",},
                            })
                            .addElement('0', ofBlock(sCaseCore1,11))
                            .addElement('1', ofBlock(sCaseCore1,0))
                            .addElement('2', ofBlock(decorateBlock[2],0))
                            .build();
            private final String[] desc=new String[]{
                    EnumChatFormatting.RED+"Impact Details:",
                    "- Utility Machine Casing",
                    "- I-Glass (any I-Glass)",
                    "- Upgrade Casing (Tier 1-4) or Air (no parallel)",
                    "- Hatches (any Utility Machine Casing)",
            };
            //endregion

            @Override
            public void construct(ItemStack stackSize, boolean hintsOnly, GTMTE_Utility tileEntity, ExtendedFacing aSide) {
                IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
                definition.buildOrHints(tileEntity,"main", base.getWorld(),aSide,
                        base.getXCoord(),base.getYCoord(),base.getZCoord(),
                        1, 2, 0,hintsOnly);
            }

            @Override
            public String[] getDescription(ItemStack stackSize) {
                return desc;
            }
        });

        //Brewmenter
        registerMetaClass(GTMTE_Brewmenter.class, new IMultiblockInfoContainer<GTMTE_Brewmenter>() {
            //region Structure
            private final IStructureDefinition<GTMTE_Brewmenter> definition=
                    StructureDefinition.<GTMTE_Brewmenter>builder()
                            .addShapeOldApi("main", new String[][]{
                                    {".000.",".000.",".000.",".0.0.",},
                                    {"00200","00.00","00.00","00000",},
                                    {"02020","0.1.0","0.1.0","00000",},
                                    {"00200","00.00","00.00","00000",},
                                    {".000.",".000.",".000.",".000.",},
                            })
                            .addElement('0', ofBlock(sCaseCore1,12))
                            .addElement('1', ofBlock(sCaseCore1,0))
                            .addElement('2', ofBlock(decorateBlock[2],0))
                            .build();
            private final String[] desc=new String[]{
                    EnumChatFormatting.RED+"Impact Details:",
                    "- Brewmenter Casing",
                    "- I-Glass (any I-Glass)",
                    "- Upgrade Casing (Tier 1-4) or Air (no parallel)",
                    "- Hatches (any Brewmenter Casing)",
            };
            //endregion

            @Override
            public void construct(ItemStack stackSize, boolean hintsOnly, GTMTE_Brewmenter tileEntity, ExtendedFacing aSide) {
                IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
                definition.buildOrHints(tileEntity,"main", base.getWorld(),aSide,
                        base.getXCoord(),base.getYCoord(),base.getZCoord(),
                        2, 3, 0,hintsOnly);
            }

            @Override
            public String[] getDescription(ItemStack stackSize) {
                return desc;
            }
        });

        //Arc Furnace
        registerMetaClass(GTMTE_ArcFurnace.class, new IMultiblockInfoContainer<GTMTE_ArcFurnace>() {
            //region Structure
            private final IStructureDefinition<GTMTE_ArcFurnace> definition=
                    StructureDefinition.<GTMTE_ArcFurnace>builder()
                            .addShapeOldApi("main", new String[][]{
                                    {".....",".000.",".000.","00.00",},
                                    {".000.","0...0","0...0","00000",},
                                    {".000.","0.1.0","0.1.0","00000",},
                                    {".000.","0...0","0...0","00000",},
                                    {".....",".000.",".000.","00000",},
                            })
                            .addElement('0', ofBlock(sCaseCore1,13))
                            .addElement('1', ofBlock(sCaseCore1,0))
                            .build();
            private final String[] desc=new String[]{
                    EnumChatFormatting.RED+"Impact Details:",
                    "- Arc Casing",
                    "- Upgrade Casing (Tier 1-4) or Air (no parallel)",
                    "- Hatches (any Arc Casing)",
            };
            //endregion

            @Override
            public void construct(ItemStack stackSize, boolean hintsOnly, GTMTE_ArcFurnace tileEntity, ExtendedFacing aSide) {
                IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
                definition.buildOrHints(tileEntity,"main", base.getWorld(),aSide,
                        base.getXCoord(),base.getYCoord(),base.getZCoord(),
                        2, 3, 0,hintsOnly);
            }

            @Override
            public String[] getDescription(ItemStack stackSize) {
                return desc;
            }
        });

        //Cutting
        registerMetaClass(GTMTE_Cutting.class, new IMultiblockInfoContainer<GTMTE_Cutting>() {
            //region Structure
            private final IStructureDefinition<GTMTE_Cutting> definition=
                    StructureDefinition.<GTMTE_Cutting>builder()
                            .addShapeOldApi("main", new String[][]{
                                    {"..020","00020","00.20","00000",},
                                    {"..020","00010","00010","00000",},
                                    {"..020","00020","00020","00000",},
                            })
                            .addElement('0', ofBlock(sCaseCore1,14))
                            .addElement('1', ofBlock(sCaseCore1,0))
                            .addElement('2', ofBlock(decorateBlock[2],0))
                            .build();
            private final String[] desc=new String[]{
                    EnumChatFormatting.RED+"Impact Details:",
                    "- Cutting Casing",
                    "- I-Glass (any I-Glass)",
                    "- Upgrade Casing (Tier 1-4) or Air (no parallel)",
                    "- Hatches (any Cutting Casing)",
            };
            //endregion

            @Override
            public void construct(ItemStack stackSize, boolean hintsOnly, GTMTE_Cutting tileEntity, ExtendedFacing aSide) {
                IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
                definition.buildOrHints(tileEntity,"main", base.getWorld(),aSide,
                        base.getXCoord(),base.getYCoord(),base.getZCoord(),
                        2, 2, 0,hintsOnly);
            }

            @Override
            public String[] getDescription(ItemStack stackSize) {
                return desc;
            }
        });

        //Extradification
        registerMetaClass(GTMTE_Extradifier.class, new IMultiblockInfoContainer<GTMTE_Extradifier>() {
            //region Structure
            private final IStructureDefinition<GTMTE_Extradifier> definition=
                    StructureDefinition.<GTMTE_Extradifier>builder()
                            .addShapeOldApi("main", new String[][]{
                                    {".000.","0...0","0...0","0...0",".0.0.",},
                                    {"00200",".020.",".020.",".020.","00000",},
                                    {"02020",".212.",".212.",".212.","00000",},
                                    {"00200",".020.",".020.",".020.","00000",},
                                    {".000.","0...0","0...0","0...0",".000.",},
                            })
                            .addElement('0', ofBlock(sCaseCore2,2))
                            .addElement('1', ofBlock(sCaseCore1,0))
                            .addElement('2', ofBlock(decorateBlock[2],0))
                            .build();
            private final String[] desc=new String[]{
                    EnumChatFormatting.RED+"Impact Details:",
                    "- Extradification Casing",
                    "- I-Glass (any I-Glass)",
                    "- Upgrade Casing (Tier 1-4) or Air (no parallel)",
                    "- Hatches (any Extradification Casing)",
            };
            //endregion

            @Override
            public void construct(ItemStack stackSize, boolean hintsOnly, GTMTE_Extradifier tileEntity, ExtendedFacing aSide) {
                IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
                definition.buildOrHints(tileEntity,"main", base.getWorld(),aSide,
                        base.getXCoord(),base.getYCoord(),base.getZCoord(),
                        2, 4, 0,hintsOnly);
            }

            @Override
            public String[] getDescription(ItemStack stackSize) {
                return desc;
            }
        });

        //Maceration Stack
        registerMetaClass(GTMTE_Macerator.class, new IMultiblockInfoContainer<GTMTE_Macerator>() {
            //region Structure
            private final IStructureDefinition<GTMTE_Macerator> definition=
                    StructureDefinition.<GTMTE_Macerator>builder()
                            .addShapeOldApi("main", new String[][]{
                                    {"000","000","000","000","000","0.0",},
                                    {"000","010","010","010","010","000",},
                                    {"000","000","000","000","000","000",},
                            })
                            .addElement('0', ofBlock(sCaseCore2,3))
                            .addElement('1', ofBlock(sCaseCore1,0))
                            .build();
            private final String[] desc=new String[]{
                    EnumChatFormatting.RED+"Impact Details:",
                    "- Maceration Casing",
                    "- Upgrade Casing (Tier 1-4) or Air (no parallel)",
                    "- Hatches (any Maceration Casing)",
            };
            //endregion

            @Override
            public void construct(ItemStack stackSize, boolean hintsOnly, GTMTE_Macerator tileEntity, ExtendedFacing aSide) {
                IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
                definition.buildOrHints(tileEntity,"main", base.getWorld(),aSide,
                        base.getXCoord(),base.getYCoord(),base.getZCoord(),
                        1, 5, 0,hintsOnly);
            }

            @Override
            public String[] getDescription(ItemStack stackSize) {
                return desc;
            }
        });

        //Mixer
        registerMetaClass(GTMTE_Mixer.class, new IMultiblockInfoContainer<GTMTE_Mixer>() {
            //region Structure
            private final IStructureDefinition<GTMTE_Mixer> definition=
                    StructureDefinition.<GTMTE_Mixer>builder()
                            .addShapeOldApi("main", new String[][]{
                                    {"000",".2.",".2.","0.0",},
                                    {"000","212","212","000",},
                                    {"000",".2.",".2.","000",},
                                    {"000","000","000","000",},
                            })
                            .addElement('0', ofBlock(sCaseCore1,15))
                            .addElement('1', ofBlock(sCaseCore1,0))
                            .addElement('2', ofBlock(decorateBlock[2],0))
                            .build();
            private final String[] desc=new String[]{
                    EnumChatFormatting.RED+"Impact Details:",
                    "- Mixing Casing",
                    "- I-Glass (any I-Glass)",
                    "- Upgrade Casing (Tier 1-4) or Air (no parallel)",
                    "- Hatches (any Mixing Casing)",
            };
            //endregion

            @Override
            public void construct(ItemStack stackSize, boolean hintsOnly, GTMTE_Mixer tileEntity, ExtendedFacing aSide) {
                IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
                definition.buildOrHints(tileEntity,"main", base.getWorld(),aSide,
                        base.getXCoord(),base.getYCoord(),base.getZCoord(),
                        1, 3, 0,hintsOnly);
            }

            @Override
            public String[] getDescription(ItemStack stackSize) {
                return desc;
            }
        });

        //Siftarator
        registerMetaClass(GTMTE_Siftarator.class, new IMultiblockInfoContainer<GTMTE_Siftarator>() {
            //region Structure
            private final IStructureDefinition<GTMTE_Siftarator> definition=
                    StructureDefinition.<GTMTE_Siftarator>builder()
                            .addShapeOldApi("main", new String[][]{
                                    {"000..",".0...","000.0","0.000","000.0",},
                                    {"000..","000..","01000","01100","00000",},
                                    {"000..",".0...","000.0","00000","000.0",},
                            })
                            .addElement('0', ofBlock(sCaseCore2,1))
                            .addElement('1', ofBlock(sCaseCore1,0))
                            .build();
            private final String[] desc=new String[]{
                    EnumChatFormatting.RED+"Impact Details:",
                    "- Electromagnetic Casing",
                    "- Upgrade Casing (Tier 1-4) or Air (no parallel)",
                    "- Hatches (any Electromagnetic Casing)",
            };
            //endregion

            @Override
            public void construct(ItemStack stackSize, boolean hintsOnly, GTMTE_Siftarator tileEntity, ExtendedFacing aSide) {
                IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
                definition.buildOrHints(tileEntity,"main", base.getWorld(),aSide,
                        base.getXCoord(),base.getYCoord(),base.getZCoord(),
                        1, 3, 0,hintsOnly);
            }

            @Override
            public String[] getDescription(ItemStack stackSize) {
                return desc;
            }
        });

        //3D Printer
        registerMetaClass(GTMTE_3DPrinter.class, new IMultiblockInfoContainer<GTMTE_3DPrinter>() {
            //region Structure
            private final IStructureDefinition<GTMTE_3DPrinter> definition=
                    StructureDefinition.<GTMTE_3DPrinter>builder()
                            .addShapeOldApi("main", new String[][]{
                                    {".03330",".03330",".00000",},
                                    {".03330",".01110",".00000",},
                                    {".03330",".01110",".00000",},
                                    {".03330",".01110",".00000",},
                                    {".03330",".03334",".00000",},
                                    {E,E,E,},
                                    {E,E,E,},
                                    {E,E,E,},
                                    {"033330","033335","000000",},
                                    {"033330","022220","000000",},
                                    {"033330","022220","000000",},
                                    {"033330","022220","000000",},
                                    {"033330","022220","000000",},
                                    {"033330","033330","000000",},
                            })
                            .addElement('0', ofBlock(sCaseCore2,4))
                            .addElement('1', ofBlock(sCaseCore2,5))
                            .addElement('2', ofBlock(sCaseCore2,6))
                            .addElement('3', ofBlock(decorateBlock[2],0))
                            .addElement('4', ofBlock(decorateBlock[2],1))
                            .addElement('5', ofBlock(decorateBlock[2],3))
                            .build();
            private final String[] desc=new String[]{
                    EnumChatFormatting.RED+"Impact Details:",
                    "- 3D Printed Casing",
                    "- I-Glass (any I-Glass)",
                    "- Configuration Casing (3x3 or 4x4)",
                    "- Controller: " + EnumChatFormatting.RED + "Red - 3x3" + EnumChatFormatting.RESET + " | " + EnumChatFormatting.BLUE + "Blue - 4x4",
                    "- Hatches (any 3D Printed Casing)",
            };
            //endregion

            @Override
            public void construct(ItemStack stackSize, boolean hintsOnly, GTMTE_3DPrinter tileEntity, ExtendedFacing aSide) {
                IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
                definition.buildOrHints(tileEntity,"main", base.getWorld(),aSide,
                        base.getXCoord(),base.getYCoord(),base.getZCoord(),
                        5, 1, 6,hintsOnly);
            }

            @Override
            public String[] getDescription(ItemStack stackSize) {
                return desc;
            }
        });
    }
}
