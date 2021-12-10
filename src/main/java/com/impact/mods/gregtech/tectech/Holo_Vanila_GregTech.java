package com.impact.mods.gregtech.tectech;

import com.impact.mods.gregtech.tileentities.multi.processing.defaultmachines.GTMTE_CokeOven;
import gregtech.api.enums.Materials;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.common.tileentities.machines.multi.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import space.impact.api.ImpactAPI;
import space.impact.api.multiblocks.alignment.constructable.IMultiBlockInfoContainer;
import space.impact.api.multiblocks.alignment.enumerable.ExtendedFacing;
import space.impact.api.multiblocks.structure.IStructureDefinition;
import space.impact.api.multiblocks.structure.StructureDefinition;

import static com.impact.util.multis.GT_StructureUtility.ofFrame;
import static gregtech.api.GregTech_API.*;
import static space.impact.api.multiblocks.alignment.constructable.IMultiBlockInfoContainer.registerTileClass;
import static space.impact.api.multiblocks.structure.StructureUtility.*;


public class Holo_Vanila_GregTech implements Runnable {
	
	@SuppressWarnings({"deprecation"})
	@Override
	public void run() {
		//FusionComputer1
		registerTileClass(
				GT_MetaTileEntity_FusionComputer1.class.getCanonicalName(),
				new IMultiBlockInfoContainer<GT_MetaTileEntity_FusionComputer1>() {
					//region Structure
					private final IStructureDefinition<GT_MetaTileEntity_FusionComputer1> definition =
							StructureDefinition.<GT_MetaTileEntity_FusionComputer1>builder()
									.addShapeOldApi("main", new String[][]{
											{"...............", "......414......", "...............",},
											{"......313......", "....1100011....", "......111......",},
											{"....11...11....", "...200414002...", "....11...11....",},
											{"...1.......1...", "..2021...1202..", "...1.......1...",},
											{"..1.........1..", ".102.......201.", "..1.........1..",},
											{"..1.........1..", ".101.......101.", "..1.........1..",},
											{".3...........3.", "404.........404", ".1...........1.",},
											{".1...........1.", "101.........101", ".1...........1.",},
											{".3...........3.", "404.........404", ".1...........1.",},
											{"..1.........1..", ".101.......101.", "..1.........1..",},
											{"..1.........1..", ".102.......201.", "..1.........1..",},
											{"...1.......1...", "..2021...1202..", "...1.......1...",},
											{"....11...11....", "...2004.4002...", "....11...11....",},
											{"......313......", "....1100011....", "......111......",},
											{"...............", "......414......", "...............",},
									})
									.addElement('0', ofBlock(sBlockCasings1, 15))
									.addElement('1', ofBlock(sBlockCasings1, 6))
									.addElement('2', ofBlockHint(ImpactAPI.getBlockHint(), ImpactAPI.WHITE))
									.addElement('3', ofBlockHint(ImpactAPI.getBlockHint(), ImpactAPI.RED))
									.addElement('4', ofBlockHint(ImpactAPI.getBlockHint(), ImpactAPI.BLUE))
									.build();
					private final String[] desc = new String[]{
							EnumChatFormatting.RED + "Impact Details:",
							"- LuV Casing",
							"- Superconductor Wire",
							"- Energy Hatches or LuV Casing (any" + EnumChatFormatting.WHITE + " White)",
							"- Input Hatches or LuV Casing (any" + EnumChatFormatting.RED + " Red"
									+ EnumChatFormatting.RESET + ")",
							"- Output Hatches or LuV Casing (any" + EnumChatFormatting.BLUE + " Blue"
									+ EnumChatFormatting.RESET + ")",
					};
					//endregion
					
					@Override
					public void construct(ItemStack stackSize, boolean hintsOnly,
										  GT_MetaTileEntity_FusionComputer1 tileEntity, ExtendedFacing aSide) {
						IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
						definition.buildOrHints(tileEntity, stackSize, "main", base.getWorld(), aSide,
								base.getXCoord(), base.getYCoord(), base.getZCoord(),
								7, 1, 12, hintsOnly
						);
					}
					
					@Override
					public String[] getDescription(ItemStack stackSize) {
						return desc;
					}
				}
		);
		
		//FusionComputer2
		registerTileClass(
				GT_MetaTileEntity_FusionComputer2.class.getCanonicalName(),
				new IMultiBlockInfoContainer<GT_MetaTileEntity_FusionComputer2>() {
					//region Structure
					private final IStructureDefinition<GT_MetaTileEntity_FusionComputer2> definition =
							StructureDefinition.<GT_MetaTileEntity_FusionComputer2>builder()
									.addShapeOldApi("main", new String[][]{
											{"...............", "......414......", "...............",},
											{"......313......", "....1100011....", "......111......",},
											{"....11...11....", "...200414002...", "....11...11....",},
											{"...1.......1...", "..2021...1202..", "...1.......1...",},
											{"..1.........1..", ".102.......201.", "..1.........1..",},
											{"..1.........1..", ".101.......101.", "..1.........1..",},
											{".3...........3.", "404.........404", ".1...........1.",},
											{".1...........1.", "101.........101", ".1...........1.",},
											{".3...........3.", "404.........404", ".1...........1.",},
											{"..1.........1..", ".101.......101.", "..1.........1..",},
											{"..1.........1..", ".102.......201.", "..1.........1..",},
											{"...1.......1...", "..2021...1202..", "...1.......1...",},
											{"....11...11....", "...2004.4002...", "....11...11....",},
											{"......313......", "....1100011....", "......111......",},
											{"...............", "......414......", "...............",},
									})
									.addElement('0', ofBlock(sBlockCasings4, 7))
									.addElement('1', ofBlock(sBlockCasings4, 6))
									.addElement('2', ofBlockHint(ImpactAPI.getBlockHint(), ImpactAPI.WHITE))
									.addElement('3', ofBlockHint(ImpactAPI.getBlockHint(), ImpactAPI.RED))
									.addElement('4', ofBlockHint(ImpactAPI.getBlockHint(), ImpactAPI.BLUE))
									.build();
					private final String[] desc = new String[]{
							EnumChatFormatting.RED + "Impact Details:",
							"- Fusion Machine Casing MK I",
							"- Fusion Coil Block",
							"- Energy Hatches or Fusion Machine Casing MK I (any" + EnumChatFormatting.WHITE
									+ " White)",
							"- Input Hatches or Fusion Machine Casing MK I (any" + EnumChatFormatting.RED + " Red"
									+ EnumChatFormatting.RESET + ")",
							"- Output Hatches or Fusion Machine Casing MK I (any" + EnumChatFormatting.BLUE
									+ " Blue" + EnumChatFormatting.RESET + ")",
					};
					//endregion
					
					@Override
					public void construct(ItemStack stackSize, boolean hintsOnly,
										  GT_MetaTileEntity_FusionComputer2 tileEntity, ExtendedFacing aSide) {
						IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
						definition.buildOrHints(tileEntity, stackSize, "main", base.getWorld(), aSide,
								base.getXCoord(), base.getYCoord(), base.getZCoord(),
								7, 1, 12, hintsOnly
						);
					}
					
					@Override
					public String[] getDescription(ItemStack stackSize) {
						return desc;
					}
				}
		);
		
		//FusionComputer3
		registerTileClass(
				GT_MetaTileEntity_FusionComputer3.class.getCanonicalName(),
				new IMultiBlockInfoContainer<GT_MetaTileEntity_FusionComputer3>() {
					//region Structure
					private final IStructureDefinition<GT_MetaTileEntity_FusionComputer3> definition =
							StructureDefinition.<GT_MetaTileEntity_FusionComputer3>builder()
									.addShapeOldApi("main", new String[][]{
											{"...............", "......414......", "...............",},
											{"......313......", "....1100011....", "......111......",},
											{"....11...11....", "...200414002...", "....11...11....",},
											{"...1.......1...", "..2021...1202..", "...1.......1...",},
											{"..1.........1..", ".102.......201.", "..1.........1..",},
											{"..1.........1..", ".101.......101.", "..1.........1..",},
											{".3...........3.", "404.........404", ".1...........1.",},
											{".1...........1.", "101.........101", ".1...........1.",},
											{".3...........3.", "404.........404", ".1...........1.",},
											{"..1.........1..", ".101.......101.", "..1.........1..",},
											{"..1.........1..", ".102.......201.", "..1.........1..",},
											{"...1.......1...", "..2021...1202..", "...1.......1...",},
											{"....11...11....", "...2004.4002...", "....11...11....",},
											{"......313......", "....1100011....", "......111......",},
											{"...............", "......414......", "...............",},
									})
									.addElement('0', ofBlock(sBlockCasings4, 7))
									.addElement('1', ofBlock(sBlockCasings4, 8))
									.addElement('2', ofBlockHint(ImpactAPI.getBlockHint(), ImpactAPI.WHITE))
									.addElement('3', ofBlockHint(ImpactAPI.getBlockHint(), ImpactAPI.RED))
									.addElement('4', ofBlockHint(ImpactAPI.getBlockHint(), ImpactAPI.BLUE))
									.build();
					private final String[] desc = new String[]{
							EnumChatFormatting.RED + "Impact Details:",
							"- Fusion Machine Casing MK II",
							"- Fusion Coil Block",
							"- Energy Hatches or Fusion Machine Casing MK II (any" + EnumChatFormatting.WHITE
									+ " White)",
							"- Input Hatches or Fusion Machine Casing MK II (any" + EnumChatFormatting.RED
									+ " Red" + EnumChatFormatting.RESET + ")",
							"- Output Hatches or Fusion Machine Casing MK II (any" + EnumChatFormatting.BLUE
									+ " Blue" + EnumChatFormatting.RESET + ")",
					};
					//endregion
					
					@Override
					public void construct(ItemStack stackSize, boolean hintsOnly,
										  GT_MetaTileEntity_FusionComputer3 tileEntity, ExtendedFacing aSide) {
						IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
						definition.buildOrHints(tileEntity, stackSize, "main", base.getWorld(), aSide,
								base.getXCoord(), base.getYCoord(), base.getZCoord(),
								7, 1, 12, hintsOnly
						);
					}
					
					@Override
					public String[] getDescription(ItemStack stackSize) {
						return desc;
					}
				}
		);
		
		//FusionComputer4
		registerTileClass(
				GT_MetaTileEntity_FusionComputer4.class.getCanonicalName(),
				new IMultiBlockInfoContainer<GT_MetaTileEntity_FusionComputer4>() {
					//region Structure
					private final IStructureDefinition<GT_MetaTileEntity_FusionComputer4> definition =
							StructureDefinition.<GT_MetaTileEntity_FusionComputer4>builder()
									.addShapeOldApi("main", new String[][]{
											{"...............", "......414......", "...............",},
											{"......313......", "....1100011....", "......111......",},
											{"....11...11....", "...200414002...", "....11...11....",},
											{"...1.......1...", "..2021...1202..", "...1.......1...",},
											{"..1.........1..", ".102.......201.", "..1.........1..",},
											{"..1.........1..", ".101.......101.", "..1.........1..",},
											{".3...........3.", "404.........404", ".1...........1.",},
											{".1...........1.", "101.........101", ".1...........1.",},
											{".3...........3.", "404.........404", ".1...........1.",},
											{"..1.........1..", ".101.......101.", "..1.........1..",},
											{"..1.........1..", ".102.......201.", "..1.........1..",},
											{"...1.......1...", "..2021...1202..", "...1.......1...",},
											{"....11...11....", "...2004.4002...", "....11...11....",},
											{"......313......", "....1100011....", "......111......",},
											{"...............", "......414......", "...............",},
									})
									.addElement('0', ofBlock(sBlockCasings5, 11))
									.addElement('1', ofBlock(sBlockCasings5, 9))
									.addElement('2', ofBlockHint(ImpactAPI.getBlockHint(), ImpactAPI.WHITE))
									.addElement('3', ofBlockHint(ImpactAPI.getBlockHint(), ImpactAPI.RED))
									.addElement('4', ofBlockHint(ImpactAPI.getBlockHint(), ImpactAPI.BLUE))
									.build();
					private final String[] desc = new String[]{
							EnumChatFormatting.RED + "Impact Details:",
							"- Fusion Machine Casing MK III",
							"- Superconductor Fusion Coil Block MK I",
							"- Energy Hatches or Fusion Machine Casing MK III (any" + EnumChatFormatting.WHITE
									+ " White)",
							"- Input Hatches or Fusion Machine Casing MK III (any" + EnumChatFormatting.RED
									+ " Red" + EnumChatFormatting.RESET + ")",
							"- Output Hatches or Fusion Machine Casing MK III (any" + EnumChatFormatting.BLUE
									+ " Blue" + EnumChatFormatting.RESET + ")",
					};
					//endregion
					
					@Override
					public void construct(ItemStack stackSize, boolean hintsOnly,
										  GT_MetaTileEntity_FusionComputer4 tileEntity, ExtendedFacing aSide) {
						IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
						definition.buildOrHints(tileEntity, stackSize, "main", base.getWorld(), aSide,
								base.getXCoord(), base.getYCoord(), base.getZCoord(),
								7, 1, 12, hintsOnly
						);
					}
					
					@Override
					public String[] getDescription(ItemStack stackSize) {
						return desc;
					}
				}
		);
		
		//FusionComputer5
		registerTileClass(
				GT_MetaTileEntity_FusionComputer5.class.getCanonicalName(),
				new IMultiBlockInfoContainer<GT_MetaTileEntity_FusionComputer5>() {
					//region Structure
					private final IStructureDefinition<GT_MetaTileEntity_FusionComputer5> definition =
							StructureDefinition.<GT_MetaTileEntity_FusionComputer5>builder()
									.addShapeOldApi("main", new String[][]{
											{"...............", "......414......", "...............",},
											{"......313......", "....1100011....", "......111......",},
											{"....11...11....", "...200414002...", "....11...11....",},
											{"...1.......1...", "..2021...1202..", "...1.......1...",},
											{"..1.........1..", ".102.......201.", "..1.........1..",},
											{"..1.........1..", ".101.......101.", "..1.........1..",},
											{".3...........3.", "404.........404", ".1...........1.",},
											{".1...........1.", "101.........101", ".1...........1.",},
											{".3...........3.", "404.........404", ".1...........1.",},
											{"..1.........1..", ".101.......101.", "..1.........1..",},
											{"..1.........1..", ".102.......201.", "..1.........1..",},
											{"...1.......1...", "..2021...1202..", "...1.......1...",},
											{"....11...11....", "...2004.4002...", "....11...11....",},
											{"......313......", "....1100011....", "......111......",},
											{"...............", "......414......", "...............",},
									})
									.addElement('0', ofBlock(sBlockCasings7, 5))
									.addElement('1', ofBlock(sBlockCasings5, 10))
									.addElement('2', ofBlockHint(ImpactAPI.getBlockHint(), ImpactAPI.WHITE))
									.addElement('3', ofBlockHint(ImpactAPI.getBlockHint(), ImpactAPI.RED))
									.addElement('4', ofBlockHint(ImpactAPI.getBlockHint(), ImpactAPI.BLUE))
									.build();
					private final String[] desc = new String[]{
							EnumChatFormatting.RED + "Impact Details:",
							"- Fusion Machine Casing MK IV",
							"- Superconductor Fusion Coil Block MK II",
							"- Energy Hatches or Fusion Machine Casing MK IV (any" + EnumChatFormatting.WHITE
									+ " White)",
							"- Input Hatches or Fusion Machine Casing MK IV (any" + EnumChatFormatting.RED
									+ " Red" + EnumChatFormatting.RESET + ")",
							"- Output Hatches or Fusion Machine Casing MK IV (any" + EnumChatFormatting.BLUE
									+ " Blue" + EnumChatFormatting.RESET + ")",
					};
					//endregion
					
					@Override
					public void construct(ItemStack stackSize, boolean hintsOnly,
										  GT_MetaTileEntity_FusionComputer5 tileEntity, ExtendedFacing aSide) {
						IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
						definition.buildOrHints(tileEntity, stackSize, "main", base.getWorld(), aSide,
								base.getXCoord(), base.getYCoord(), base.getZCoord(),
								7, 1, 12, hintsOnly
						);
					}
					
					@Override
					public String[] getDescription(ItemStack stackSize) {
						return desc;
					}
				}
		);
		
		//BronzeBoiler
		registerTileClass(
				GT_MetaTileEntity_LargeBoiler_Bronze.class.getCanonicalName(),
				new IMultiBlockInfoContainer<GT_MetaTileEntity_LargeBoiler_Bronze>() {
					//region Structure
					private final IStructureDefinition<GT_MetaTileEntity_LargeBoiler_Bronze> definition =
							StructureDefinition.<GT_MetaTileEntity_LargeBoiler_Bronze>builder()
									.addShapeOldApi("main", new String[][]{
											{"000", "000", "000", "000", "2.2",},
											{"000", "010", "010", "010", "222",},
											{"000", "000", "000", "000", "222",},
									})
									.addElement('0', ofBlock(sBlockCasings1, 10))
									.addElement('1', ofBlock(sBlockCasings2, 12))
									.addElement('2', ofBlock(sBlockCasings3, 13))
									.build();
					private final String[] desc = new String[]{
							EnumChatFormatting.RED + "Impact Details:",
							"- Bronze Plated Bricks",
							"- Bronze Pipe Casing",
							"- Bronze Firebox Casing",
						
					};
					//endregion
					
					@Override
					public void construct(ItemStack stackSize, boolean hintsOnly,
										  GT_MetaTileEntity_LargeBoiler_Bronze tileEntity, ExtendedFacing aSide) {
						IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
						definition.buildOrHints(tileEntity, stackSize, "main", base.getWorld(), aSide,
								base.getXCoord(), base.getYCoord(), base.getZCoord(),
								1, 4, 0, hintsOnly
						);
					}
					
					@Override
					public String[] getDescription(ItemStack stackSize) {
						return desc;
					}
				}
		);
		
		//SteelBoiler
		registerTileClass(
				GT_MetaTileEntity_LargeBoiler_Steel.class.getCanonicalName(),
				new IMultiBlockInfoContainer<GT_MetaTileEntity_LargeBoiler_Steel>() {
					//region Structure
					private final IStructureDefinition<GT_MetaTileEntity_LargeBoiler_Steel> definition =
							StructureDefinition.<GT_MetaTileEntity_LargeBoiler_Steel>builder()
									.addShapeOldApi("main", new String[][]{
											{"000", "000", "000", "000", "2.2",},
											{"000", "010", "010", "010", "222",},
											{"000", "000", "000", "000", "222",},
									})
									.addElement('0', ofBlock(sBlockCasings2, 0))
									.addElement('1', ofBlock(sBlockCasings2, 13))
									.addElement('2', ofBlock(sBlockCasings3, 14))
									.build();
					private final String[] desc = new String[]{
							EnumChatFormatting.RED + "Impact Details:",
							"- Solid Steel Machine Casing",
							"- Steel Pipe Casing",
							"- Steel Firebox Casing",
						
					};
					//endregion
					
					@Override
					public void construct(ItemStack stackSize, boolean hintsOnly,
										  GT_MetaTileEntity_LargeBoiler_Steel tileEntity, ExtendedFacing aSide) {
						IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
						definition.buildOrHints(tileEntity, stackSize, "main", base.getWorld(), aSide,
								base.getXCoord(), base.getYCoord(), base.getZCoord(),
								1, 4, 0, hintsOnly
						);
					}
					
					@Override
					public String[] getDescription(ItemStack stackSize) {
						return desc;
					}
				}
		);
		
		//TitanBoiler
		registerTileClass(
				GT_MetaTileEntity_LargeBoiler_Titanium.class.getCanonicalName(),
				new IMultiBlockInfoContainer<GT_MetaTileEntity_LargeBoiler_Titanium>() {
					//region Structure
					private final IStructureDefinition<GT_MetaTileEntity_LargeBoiler_Titanium> definition =
							StructureDefinition.<GT_MetaTileEntity_LargeBoiler_Titanium>builder()
									.addShapeOldApi("main", new String[][]{
											{"000", "000", "000", "000", "2.2",},
											{"000", "010", "010", "010", "222",},
											{"000", "000", "000", "000", "222",},
									})
									.addElement('0', ofBlock(sBlockCasings4, 2))
									.addElement('1', ofBlock(sBlockCasings2, 14))
									.addElement('2', ofBlock(sBlockCasings4, 3))
									.build();
					private final String[] desc = new String[]{
							EnumChatFormatting.RED + "Impact Details:",
							"- Stable Titanium Machine Casing",
							"- Titanium Pipe Casing",
							"- Titanium Firebox Casing",
						
					};
					//endregion
					
					@Override
					public void construct(ItemStack stackSize, boolean hintsOnly,
										  GT_MetaTileEntity_LargeBoiler_Titanium tileEntity, ExtendedFacing aSide) {
						IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
						definition.buildOrHints(tileEntity, stackSize, "main", base.getWorld(), aSide,
								base.getXCoord(), base.getYCoord(), base.getZCoord(),
								1, 4, 0, hintsOnly
						);
					}
					
					@Override
					public String[] getDescription(ItemStack stackSize) {
						return desc;
					}
				}
		);
		
		//TungstenSteelBoiler
		registerTileClass(
				GT_MetaTileEntity_LargeBoiler_TungstenSteel.class.getCanonicalName(),
				new IMultiBlockInfoContainer<GT_MetaTileEntity_LargeBoiler_TungstenSteel>() {
					//region Structure
					private final IStructureDefinition<GT_MetaTileEntity_LargeBoiler_TungstenSteel> definition =
							StructureDefinition.<GT_MetaTileEntity_LargeBoiler_TungstenSteel>builder()
									.addShapeOldApi("main", new String[][]{
											{"000", "000", "000", "000", "2.2",},
											{"000", "010", "010", "010", "222",},
											{"000", "000", "000", "000", "222",},
									})
									.addElement('0', ofBlock(sBlockCasings4, 0))
									.addElement('1', ofBlock(sBlockCasings2, 15))
									.addElement('2', ofBlock(sBlockCasings3, 15))
									.build();
					private final String[] desc = new String[]{
							EnumChatFormatting.RED + "Impact Details:",
							"- Robust TungstenSteel Machine Casing",
							"- TungstenSteel Pipe Casing",
							"- TungstenSteel Firebox Casing",
						
					};
					//endregion
					
					@Override
					public void construct(ItemStack stackSize, boolean hintsOnly,
										  GT_MetaTileEntity_LargeBoiler_TungstenSteel tileEntity, ExtendedFacing aSide) {
						IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
						definition.buildOrHints(tileEntity, stackSize, "main", base.getWorld(), aSide,
								base.getXCoord(), base.getYCoord(), base.getZCoord(),
								1, 4, 0, hintsOnly
						);
					}
					
					@Override
					public String[] getDescription(ItemStack stackSize) {
						return desc;
					}
				}
		);
		
		//ProcessingArray
		registerTileClass(
				GT_MetaTileEntity_ProcessingArray.class.getCanonicalName(),
				new IMultiBlockInfoContainer<GT_MetaTileEntity_ProcessingArray>() {
					//region Structure
					private final IStructureDefinition<GT_MetaTileEntity_ProcessingArray> definition =
							StructureDefinition.<GT_MetaTileEntity_ProcessingArray>builder()
									.addShapeOldApi("main", new String[][]{
											{"000", "0.0", "000",},
											{"000", "0A0", "000",},
											{"000", "000", "000",},
									})
									.addElement('0', ofBlock(sBlockCasings4, 0))
									.build();
					private final String[] desc = new String[]{
							EnumChatFormatting.RED + "Impact Details:",
							"- Robust Tungstensteel Machine Casing",
						
					};
					//endregion
					
					@Override
					public void construct(ItemStack stackSize, boolean hintsOnly,
										  GT_MetaTileEntity_ProcessingArray tileEntity, ExtendedFacing aSide) {
						IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
						definition.buildOrHints(tileEntity, stackSize, "main", base.getWorld(), aSide,
								base.getXCoord(), base.getYCoord(), base.getZCoord(),
								1, 1, 0, hintsOnly
						);
					}
					
					@Override
					public String[] getDescription(ItemStack stackSize) {
						return desc;
					}
				}
		);
		
		//ProcessingArray2
		registerTileClass(
				GT_MetaTileEntity_ProcessingArray2.class.getCanonicalName(),
				new IMultiBlockInfoContainer<GT_MetaTileEntity_ProcessingArray2>() {
					//region Structure
					private final IStructureDefinition<GT_MetaTileEntity_ProcessingArray2> definition =
							StructureDefinition.<GT_MetaTileEntity_ProcessingArray2>builder()
									.addShapeOldApi("main", new String[][]{
											{"000", "0.0", "000",},
											{"000", "0A0", "000",},
											{"000", "000", "000",},
									})
									.addElement('0', ofBlock(sBlockCasings4, 0))
									.build();
					private final String[] desc = new String[]{
							EnumChatFormatting.RED + "Impact Details:",
							"- Robust Tungstensteel Machine Casing",
						
					};
					//endregion
					
					@Override
					public void construct(ItemStack stackSize, boolean hintsOnly,
										  GT_MetaTileEntity_ProcessingArray2 tileEntity, ExtendedFacing aSide) {
						IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
						definition.buildOrHints(tileEntity, stackSize, "main", base.getWorld(), aSide,
								base.getXCoord(), base.getYCoord(), base.getZCoord(),
								1, 1, 0, hintsOnly
						);
					}
					
					@Override
					public String[] getDescription(ItemStack stackSize) {
						return desc;
					}
				}
		);
		
		//ProcessingArray3
		registerTileClass(
				GT_MetaTileEntity_ProcessingArray3.class.getCanonicalName(),
				new IMultiBlockInfoContainer<GT_MetaTileEntity_ProcessingArray3>() {
					//region Structure
					private final IStructureDefinition<GT_MetaTileEntity_ProcessingArray3> definition =
							StructureDefinition.<GT_MetaTileEntity_ProcessingArray3>builder()
									.addShapeOldApi("main", new String[][]{
											{"000", "0.0", "000",},
											{"000", "0A0", "000",},
											{"000", "000", "000",},
									})
									.addElement('0', ofBlock(sBlockCasings4, 0))
									.build();
					private final String[] desc = new String[]{
							EnumChatFormatting.RED + "Impact Details:",
							"- Robust Tungstensteel Machine Casing",
						
					};
					//endregion
					
					@Override
					public void construct(ItemStack stackSize, boolean hintsOnly,
										  GT_MetaTileEntity_ProcessingArray3 tileEntity, ExtendedFacing aSide) {
						IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
						definition.buildOrHints(tileEntity, stackSize, "main", base.getWorld(), aSide,
								base.getXCoord(), base.getYCoord(), base.getZCoord(),
								1, 1, 0, hintsOnly
						);
					}
					
					@Override
					public String[] getDescription(ItemStack stackSize) {
						return desc;
					}
				}
		);
		
		//OilCracker
		registerTileClass(
				GT_MetaTileEntity_OilCracker.class.getCanonicalName(),
				new IMultiBlockInfoContainer<GT_MetaTileEntity_OilCracker>() {
					//region Structure
					private final IStructureDefinition<GT_MetaTileEntity_OilCracker> definition =
							StructureDefinition.<GT_MetaTileEntity_OilCracker>builder()
									.addShapeOldApi("main", new String[][]{
											{"10101", "10.01", "10101",},
											{"10101", "1AAA1", "10101",},
											{"10101", "10101", "10101",},
									})
									.addElement('1', ofBlock(sBlockCasings4, 1))
									.addElement('0', ofBlock(sBlockCasings5, 0))
									.build();
					private final String[] desc = new String[]{
							EnumChatFormatting.RED + "Impact Details:",
							"- Clean Stainless Steel Machine Casing",
							"- Cupronickel Coil Block ",
						
					};
					//endregion
					
					@Override
					public void construct(ItemStack stackSize, boolean hintsOnly,
										  GT_MetaTileEntity_OilCracker tileEntity, ExtendedFacing aSide) {
						IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
						definition.buildOrHints(tileEntity, stackSize, "main", base.getWorld(), aSide,
								base.getXCoord(), base.getYCoord(), base.getZCoord(),
								2, 1, 0, hintsOnly
						);
					}
					
					@Override
					public String[] getDescription(ItemStack stackSize) {
						return desc;
					}
				}
		);
		
		//PrimitiveBlastFurnace
		registerTileClass(
				GT_MetaTileEntity_BrickedBlastFurnace.class.getCanonicalName(),
				new IMultiBlockInfoContainer<GT_MetaTileEntity_BrickedBlastFurnace>() {
					//region Structure
					private final IStructureDefinition<GT_MetaTileEntity_BrickedBlastFurnace> definition =
							StructureDefinition.<GT_MetaTileEntity_BrickedBlastFurnace>builder()
									.addShapeOldApi("main", new String[][]{
											{"000", "000", "0.0", "000",},
											{"0A0", "0A0", "0A0", "000",},
											{"000", "000", "000", "000",},
									})
									.addElement('0', ofBlock(sBlockCasings4, 15))
									.build();
					private final String[] desc = new String[]{
							EnumChatFormatting.RED + "Impact Details:",
							"- Fired Brick Blocks",
						
					};
					//endregion
					
					@Override
					public void construct(ItemStack stackSize, boolean hintsOnly,
										  GT_MetaTileEntity_BrickedBlastFurnace tileEntity, ExtendedFacing aSide) {
						IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
						definition.buildOrHints(tileEntity, stackSize, "main", base.getWorld(), aSide,
								base.getXCoord(), base.getYCoord(), base.getZCoord(),
								1, 2, 0, hintsOnly
						);
					}
					
					@Override
					public String[] getDescription(ItemStack stackSize) {
						return desc;
					}
				}
		);
		
		//BronzeBlastFurnace
		registerTileClass(
				GT_MetaTileEntity_BronzeBlastFurnace.class.getCanonicalName(),
				new IMultiBlockInfoContainer<GT_MetaTileEntity_BronzeBlastFurnace>() {
					//region Structure
					private final IStructureDefinition<GT_MetaTileEntity_BronzeBlastFurnace> definition =
							StructureDefinition.<GT_MetaTileEntity_BronzeBlastFurnace>builder()
									.addShapeOldApi("main", new String[][]{
											{"000", "000", "0.0", "000",},
											{"0A0", "0A0", "0A0", "000",},
											{"000", "000", "000", "000",},
									})
									.addElement('0', ofBlock(sBlockCasings1, 10))
									.build();
					private final String[] desc = new String[]{
							EnumChatFormatting.RED + "Impact Details:",
							"- Bronze Plated Bricks",
						
					};
					//endregion
					
					@Override
					public void construct(ItemStack stackSize, boolean hintsOnly,
										  GT_MetaTileEntity_BronzeBlastFurnace tileEntity, ExtendedFacing aSide) {
						IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
						definition.buildOrHints(tileEntity, stackSize, "main", base.getWorld(),
								aSide, base.getXCoord(), base.getYCoord(),
								base.getZCoord(), 1, 2, 0, hintsOnly
						);
					}
					
					
					@Override
					public String[] getDescription(ItemStack stackSize) {
						return desc;
					}
				}
		);
		
		//ConcreteBackfiller1
		registerTileClass(
				GT_MetaTileEntity_ConcreteBackfiller1.class.getCanonicalName(),
				new IMultiBlockInfoContainer<GT_MetaTileEntity_ConcreteBackfiller1>() {
					//region Structure
					private final IStructureDefinition<GT_MetaTileEntity_ConcreteBackfiller1> definition =
							StructureDefinition.<GT_MetaTileEntity_ConcreteBackfiller1>builder()
									.addShapeOldApi("main", new String[][]{
											{"...", "...", "...", ".0.", ".0.", ".0.", "1.1",},
											{".0.", ".0.", ".0.", "010", "010", "010", "111",},
											{"...", "...", "...", ".0.", ".0.", ".0.", "111",},
									})
									.addElement('0', lazy(t -> ofFrame(Materials.Steel)))
									.addElement('1', ofBlock(sBlockCasings2, 0))
									.build();
					private final String[] desc = new String[]{
							EnumChatFormatting.RED + "Impact Details:",
							"- Steel Frame Box",
							"- Solid Steel Machine Casing",
						
					};
					//endregion
					
					@Override
					public void construct(ItemStack stackSize, boolean hintsOnly,
										  GT_MetaTileEntity_ConcreteBackfiller1 tileEntity, ExtendedFacing aSide) {
						IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
						definition.buildOrHints(tileEntity, stackSize, "main", base.getWorld(), aSide,
								base.getXCoord(), base.getYCoord(), base.getZCoord(),
								1, 6, 0, hintsOnly
						);
					}
					
					@Override
					public String[] getDescription(ItemStack stackSize) {
						return desc;
					}
				}
		);
		
		//ConcreteBackfiller2
		registerTileClass(
				GT_MetaTileEntity_ConcreteBackfiller2.class.getCanonicalName(),
				new IMultiBlockInfoContainer<GT_MetaTileEntity_ConcreteBackfiller2>() {
					//region Structure
					private final IStructureDefinition<GT_MetaTileEntity_ConcreteBackfiller2> definition =
							StructureDefinition.<GT_MetaTileEntity_ConcreteBackfiller2>builder()
									.addShapeOldApi("main", new String[][]{
											{"...", "...", "...", ".0.", ".0.", ".0.", "1.1",},
											{".0.", ".0.", ".0.", "010", "010", "010", "111",},
											{"...", "...", "...", ".0.", ".0.", ".0.", "111",},
									})
									.addElement('0', lazy(t -> ofFrame(Materials.Titanium)))
									.addElement('1', ofBlock(sBlockCasings4, 2))
									.build();
					private final String[] desc = new String[]{
							EnumChatFormatting.RED + "Impact Details:",
							"- Titanium Frame Box",
							"- Stable Titanium Machine Casing",
					};
					//endregion
					
					@Override
					public void construct(ItemStack stackSize, boolean hintsOnly,
										  GT_MetaTileEntity_ConcreteBackfiller2 tileEntity, ExtendedFacing aSide) {
						IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
						definition.buildOrHints(tileEntity, stackSize, "main", base.getWorld(), aSide,
								base.getXCoord(), base.getYCoord(), base.getZCoord(),
								1, 6, 0, hintsOnly
						);
					}
					
					@Override
					public String[] getDescription(ItemStack stackSize) {
						return desc;
					}
				}
		);
		
		//OilDrill1
		registerTileClass(
				GT_MetaTileEntity_OilDrill1.class.getCanonicalName(),
				new IMultiBlockInfoContainer<GT_MetaTileEntity_OilDrill1>() {
					//region Structure
					private final IStructureDefinition<GT_MetaTileEntity_OilDrill1> definition =
							StructureDefinition.<GT_MetaTileEntity_OilDrill1>builder()
									.addShapeOldApi("main", new String[][]{
											{"...", "...", "...", ".0.", ".0.", ".0.", "1.1",},
											{".0.", ".0.", ".0.", "010", "010", "010", "111",},
											{"...", "...", "...", ".0.", ".0.", ".0.", "111",},
									})
									.addElement('0', lazy(t -> ofFrame(Materials.Steel)))
									.addElement('1', ofBlock(sBlockCasings2, 0))
									.build();
					private final String[] desc = new String[]{
							EnumChatFormatting.RED + "Impact Details:",
							"- Steel Frame Box",
							"- Solid Steel Machine Casing",
					};
					//endregion
					
					@Override
					public void construct(ItemStack stackSize, boolean hintsOnly,
										  GT_MetaTileEntity_OilDrill1 tileEntity, ExtendedFacing aSide) {
						IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
						definition.buildOrHints(tileEntity, stackSize, "main", base.getWorld(), aSide,
								base.getXCoord(), base.getYCoord(), base.getZCoord(),
								1, 6, 0, hintsOnly
						);
					}
					
					@Override
					public String[] getDescription(ItemStack stackSize) {
						return desc;
					}
				}
		);
		
		//OilDrill2
		registerTileClass(
				GT_MetaTileEntity_OilDrill2.class.getCanonicalName(),
				new IMultiBlockInfoContainer<GT_MetaTileEntity_OilDrill2>() {
					//region Structure
					private final IStructureDefinition<GT_MetaTileEntity_OilDrill2> definition =
							StructureDefinition.<GT_MetaTileEntity_OilDrill2>builder()
									.addShapeOldApi("main", new String[][]{
											{"...", "...", "...", ".0.", ".0.", ".0.", "1.1",},
											{".0.", ".0.", ".0.", "010", "010", "010", "111",},
											{"...", "...", "...", ".0.", ".0.", ".0.", "111",},
									})
									.addElement('0', lazy(t -> ofFrame(Materials.Titanium)))
									.addElement('1', ofBlock(sBlockCasings4, 2))
									.build();
					private final String[] desc = new String[]{
							EnumChatFormatting.RED + "Impact Details:",
							"- Titanium Frame Box",
							"- Stable Titanium Machine Casing",
					};
					//endregion
					
					@Override
					public void construct(ItemStack stackSize, boolean hintsOnly,
										  GT_MetaTileEntity_OilDrill2 tileEntity, ExtendedFacing aSide) {
						IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
						definition.buildOrHints(tileEntity, stackSize, "main", base.getWorld(), aSide,
								base.getXCoord(), base.getYCoord(), base.getZCoord(),
								1, 6, 0, hintsOnly
						);
					}
					
					@Override
					public String[] getDescription(ItemStack stackSize) {
						return desc;
					}
				}
		);
		
		//OilDrill3
		registerTileClass(
				GT_MetaTileEntity_OilDrill3.class.getCanonicalName(),
				new IMultiBlockInfoContainer<GT_MetaTileEntity_OilDrill3>() {
					//region Structure
					private final IStructureDefinition<GT_MetaTileEntity_OilDrill3> definition =
							StructureDefinition.<GT_MetaTileEntity_OilDrill3>builder()
									.addShapeOldApi("main", new String[][]{
											{"...", "...", "...", ".0.", ".0.", ".0.", "1.1",},
											{".0.", ".0.", ".0.", "010", "010", "010", "111",},
											{"...", "...", "...", ".0.", ".0.", ".0.", "111",},
									})
									.addElement('0', lazy(t -> ofFrame(Materials.TungstenSteel)))
									.addElement('1', ofBlock(sBlockCasings4, 0))
									.build();
					private final String[] desc = new String[]{
							EnumChatFormatting.RED + "Impact Details:",
							"- Tungstensteel Frame Box",
							"- Robust Tungstensteel Machine Casing",
					};
					//endregion
					
					@Override
					public void construct(ItemStack stackSize, boolean hintsOnly,
										  GT_MetaTileEntity_OilDrill3 tileEntity, ExtendedFacing aSide) {
						IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
						definition.buildOrHints(tileEntity, stackSize, "main", base.getWorld(), aSide,
								base.getXCoord(), base.getYCoord(), base.getZCoord(),
								1, 6, 0, hintsOnly
						);
					}
					
					@Override
					public String[] getDescription(ItemStack stackSize) {
						return desc;
					}
				}
		);
		
		//OreDrillingPlant1
		registerTileClass(
				GT_MetaTileEntity_OreDrillingPlant1.class.getCanonicalName(),
				new IMultiBlockInfoContainer<GT_MetaTileEntity_OreDrillingPlant1>() {
					//region Structure
					private final IStructureDefinition<GT_MetaTileEntity_OreDrillingPlant1> definition =
							StructureDefinition.<GT_MetaTileEntity_OreDrillingPlant1>builder()
									.addShapeOldApi("main", new String[][]{
											{"...", "...", "...", ".0.", ".0.", ".0.", "1.1",},
											{".0.", ".0.", ".0.", "010", "010", "010", "111",},
											{"...", "...", "...", ".0.", ".0.", ".0.", "111",},
									})
									.addElement('0', lazy(t -> ofFrame(Materials.Steel)))
									.addElement('1', ofBlock(sBlockCasings2, 0))
									.build();
					private final String[] desc = new String[]{
							EnumChatFormatting.RED + "Impact Details:",
							"- Steel Frame Box",
							"- Solid Steel Machine Casing",
					};
					//endregion
					
					@Override
					public void construct(ItemStack stackSize, boolean hintsOnly,
										  GT_MetaTileEntity_OreDrillingPlant1 tileEntity, ExtendedFacing aSide) {
						IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
						definition.buildOrHints(tileEntity, stackSize, "main", base.getWorld(), aSide,
								base.getXCoord(), base.getYCoord(), base.getZCoord(),
								1, 6, 0, hintsOnly
						);
					}
					
					@Override
					public String[] getDescription(ItemStack stackSize) {
						return desc;
					}
				}
		);
		
		//OreDrillingPlant2
		registerTileClass(
				GT_MetaTileEntity_OreDrillingPlant2.class.getCanonicalName(),
				new IMultiBlockInfoContainer<GT_MetaTileEntity_OreDrillingPlant2>() {
					//region Structure
					private final IStructureDefinition<GT_MetaTileEntity_OreDrillingPlant2> definition =
							StructureDefinition.<GT_MetaTileEntity_OreDrillingPlant2>builder()
									.addShapeOldApi("main", new String[][]{
											{"...", "...", "...", ".0.", ".0.", ".0.", "1.1",},
											{".0.", ".0.", ".0.", "010", "010", "010", "111",},
											{"...", "...", "...", ".0.", ".0.", ".0.", "111",},
									})
									.addElement('0', lazy(t -> ofFrame(Materials.Titanium)))
									.addElement('1', ofBlock(sBlockCasings4, 2))
									.build();
					private final String[] desc = new String[]{
							EnumChatFormatting.RED + "Impact Details:",
							"- Titanium Frame Box",
							"- Stable Titanium Machine Casing",
					};
					//endregion
					
					@Override
					public void construct(ItemStack stackSize, boolean hintsOnly,
										  GT_MetaTileEntity_OreDrillingPlant2 tileEntity, ExtendedFacing aSide) {
						IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
						definition.buildOrHints(tileEntity, stackSize, "main", base.getWorld(), aSide,
								base.getXCoord(), base.getYCoord(), base.getZCoord(),
								1, 6, 0, hintsOnly
						);
					}
					
					@Override
					public String[] getDescription(ItemStack stackSize) {
						return desc;
					}
				}
		);
		
		//OreDrillingPlant3
		registerTileClass(
				GT_MetaTileEntity_OreDrillingPlant3.class.getCanonicalName(),
				new IMultiBlockInfoContainer<GT_MetaTileEntity_OreDrillingPlant3>() {
					//region Structure
					private final IStructureDefinition<GT_MetaTileEntity_OreDrillingPlant3> definition =
							StructureDefinition.<GT_MetaTileEntity_OreDrillingPlant3>builder()
									.addShapeOldApi("main", new String[][]{
											{"...", "...", "...", ".0.", ".0.", ".0.", "1.1",},
											{".0.", ".0.", ".0.", "010", "010", "010", "111",},
											{"...", "...", "...", ".0.", ".0.", ".0.", "111",},
									})
									.addElement('0', lazy(t -> ofFrame(Materials.TungstenSteel)))
									.addElement('1', ofBlock(sBlockCasings4, 0))
									.build();
					private final String[] desc = new String[]{
							EnumChatFormatting.RED + "Impact Details:",
							"- Tungstensteel Frame Box",
							"- Robust Tungstensteel Machine Casing",
					};
					//endregion
					
					@Override
					public void construct(ItemStack stackSize, boolean hintsOnly,
										  GT_MetaTileEntity_OreDrillingPlant3 tileEntity, ExtendedFacing aSide) {
						IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
						definition.buildOrHints(tileEntity, stackSize, "main", base.getWorld(), aSide,
								base.getXCoord(), base.getYCoord(), base.getZCoord(),
								1, 6, 0, hintsOnly
						);
					}
					
					@Override
					public String[] getDescription(ItemStack stackSize) {
						return desc;
					}
				}
		);
		
		//OreDrillingPlant4
		registerTileClass(
				GT_MetaTileEntity_OreDrillingPlant4.class.getCanonicalName(),
				new IMultiBlockInfoContainer<GT_MetaTileEntity_OreDrillingPlant4>() {
					//region Structure
					private final IStructureDefinition<GT_MetaTileEntity_OreDrillingPlant4> definition =
							StructureDefinition.<GT_MetaTileEntity_OreDrillingPlant4>builder()
									.addShapeOldApi("main", new String[][]{
											{"...", "...", "...", ".0.", ".0.", ".0.", "1.1",},
											{".0.", ".0.", ".0.", "010", "010", "010", "111",},
											{"...", "...", "...", ".0.", ".0.", ".0.", "111",},
									})
									.addElement('0', lazy(t -> ofFrame(Materials.Osmiridium)))
									.addElement('1', ofBlock(sBlockCasings4, 14))
									.build();
					private final String[] desc = new String[]{
							EnumChatFormatting.RED + "Impact Details:",
							"- Osmiridium Frame Box",
							"- Mining Osmiridium Machine Casing",
					};
					//endregion
					
					@Override
					public void construct(ItemStack stackSize, boolean hintsOnly,
										  GT_MetaTileEntity_OreDrillingPlant4 tileEntity, ExtendedFacing aSide) {
						IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
						definition.buildOrHints(tileEntity, stackSize, "main", base.getWorld(), aSide,
								base.getXCoord(), base.getYCoord(), base.getZCoord(),
								1, 6, 0, hintsOnly
						);
					}
					
					@Override
					public String[] getDescription(ItemStack stackSize) {
						return desc;
					}
				}
		);
		
		//OreDrillingPlant5
		registerTileClass(
				GT_MetaTileEntity_OreDrillingPlant5.class.getCanonicalName(),
				new IMultiBlockInfoContainer<GT_MetaTileEntity_OreDrillingPlant5>() {
					//region Structure
					private final IStructureDefinition<GT_MetaTileEntity_OreDrillingPlant5> definition =
							StructureDefinition.<GT_MetaTileEntity_OreDrillingPlant5>builder()
									.addShapeOldApi("main", new String[][]{
											{"...", "...", "...", ".0.", ".0.", ".0.", "1.1",},
											{".0.", ".0.", ".0.", "010", "010", "010", "111",},
											{"...", "...", "...", ".0.", ".0.", ".0.", "111",},
									})
									.addElement('0', lazy(t -> ofFrame(Materials.Neutronium)))
									.addElement('1', ofBlock(sBlockCasings7, 2))
									.build();
					private final String[] desc = new String[]{
							EnumChatFormatting.RED + "Impact Details:",
							"- Neutronium Frame Box",
							"- Robust Neutronium Machine Casing",
					};
					//endregion
					
					@Override
					public void construct(ItemStack stackSize, boolean hintsOnly,
										  GT_MetaTileEntity_OreDrillingPlant5 tileEntity, ExtendedFacing aSide) {
						IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
						definition.buildOrHints(tileEntity, stackSize, "main", base.getWorld(), aSide,
								base.getXCoord(), base.getYCoord(), base.getZCoord(),
								1, 6, 0, hintsOnly
						);
					}
					
					@Override
					public String[] getDescription(ItemStack stackSize) {
						return desc;
					}
				}
		);
		
		//SteamTurbine
		registerTileClass(
				GT_MetaTileEntity_LargeTurbine_Steam.class.getCanonicalName(),
				new IMultiBlockInfoContainer<GT_MetaTileEntity_LargeTurbine_Steam>() {
					//region Structure
					private final IStructureDefinition<GT_MetaTileEntity_LargeTurbine_Steam> definition =
							StructureDefinition.<GT_MetaTileEntity_LargeTurbine_Steam>builder()
									.addShapeOldApi("main", new String[][]{
											{"000", "0.0", "000",},
											{"000", "0A0", "000",},
											{"000", "0A0", "000",},
											{"000", "010", "000",},
									})
									.addElement('0', ofBlock(sBlockCasings4, 9))
									.addElement('1', ofBlockHint(ImpactAPI.getBlockHint(), ImpactAPI.RED))
									.build();
					private final String[] desc = new String[]{
							EnumChatFormatting.RED + "Impact Details:",
							"- Turbine Casing",
							"-" + EnumChatFormatting.RED + " Red:" + EnumChatFormatting.RESET + " Dynamo Hatch",
					};
					//endregion
					
					@Override
					public void construct(ItemStack stackSize, boolean hintsOnly,
										  GT_MetaTileEntity_LargeTurbine_Steam tileEntity, ExtendedFacing aSide) {
						IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
						definition.buildOrHints(tileEntity, stackSize, "main", base.getWorld(), aSide,
								base.getXCoord(), base.getYCoord(), base.getZCoord(),
								1, 1, 0, hintsOnly
						);
					}
					
					@Override
					public String[] getDescription(ItemStack stackSize) {
						return desc;
					}
				}
		);
		
		//GasTurbine
		registerTileClass(
				GT_MetaTileEntity_LargeTurbine_Gas.class.getCanonicalName(),
				new IMultiBlockInfoContainer<GT_MetaTileEntity_LargeTurbine_Gas>() {
					//region Structure
					private final IStructureDefinition<GT_MetaTileEntity_LargeTurbine_Gas> definition =
							StructureDefinition.<GT_MetaTileEntity_LargeTurbine_Gas>builder()
									.addShapeOldApi("main", new String[][]{
											{"000", "0.0", "000",},
											{"000", "0A0", "000",},
											{"000", "0A0", "000",},
											{"000", "010", "000",},
									})
									.addElement('0', ofBlock(sBlockCasings4, 10))
									.addElement('1', ofBlockHint(ImpactAPI.getBlockHint(), ImpactAPI.RED))
									.build();
					private final String[] desc = new String[]{
							EnumChatFormatting.RED + "Impact Details:",
							"- Stainless Steel Turbine Casing",
							"-" + EnumChatFormatting.RED + " Red:" + EnumChatFormatting.RESET + " Dynamo Hatch",
					};
					//endregion
					
					@Override
					public void construct(ItemStack stackSize, boolean hintsOnly,
										  GT_MetaTileEntity_LargeTurbine_Gas tileEntity, ExtendedFacing aSide) {
						IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
						definition.buildOrHints(tileEntity, stackSize, "main", base.getWorld(), aSide,
								base.getXCoord(), base.getYCoord(), base.getZCoord(),
								1, 1, 0, hintsOnly
						);
					}
					
					@Override
					public String[] getDescription(ItemStack stackSize) {
						return desc;
					}
				}
		);
		
		//HPSteamTurbine
		registerTileClass(
				GT_MetaTileEntity_LargeTurbine_HPSteam.class.getCanonicalName(),
				new IMultiBlockInfoContainer<GT_MetaTileEntity_LargeTurbine_HPSteam>() {
					//region Structure
					private final IStructureDefinition<GT_MetaTileEntity_LargeTurbine_HPSteam> definition =
							StructureDefinition.<GT_MetaTileEntity_LargeTurbine_HPSteam>builder()
									.addShapeOldApi("main", new String[][]{
											{"000", "0.0", "000",},
											{"000", "0A0", "000",},
											{"000", "0A0", "000",},
											{"000", "010", "000",},
									})
									.addElement('0', ofBlock(sBlockCasings4, 11))
									.addElement('1', ofBlockHint(ImpactAPI.getBlockHint(), ImpactAPI.RED))
									.build();
					private final String[] desc = new String[]{
							EnumChatFormatting.RED + "Impact Details:",
							"- Titanium Turbine Casing",
							"-" + EnumChatFormatting.RED + " Red:" + EnumChatFormatting.RESET + " Dynamo Hatch",
					};
					//endregion
					
					@Override
					public void construct(ItemStack stackSize, boolean hintsOnly,
										  GT_MetaTileEntity_LargeTurbine_HPSteam tileEntity, ExtendedFacing aSide) {
						IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
						definition.buildOrHints(tileEntity, stackSize, "main", base.getWorld(), aSide,
								base.getXCoord(), base.getYCoord(), base.getZCoord(),
								1, 1, 0, hintsOnly
						);
					}
					
					@Override
					public String[] getDescription(ItemStack stackSize) {
						return desc;
					}
				}
		);
		
		//PlasmaTurbine
		registerTileClass(
				GT_MetaTileEntity_LargeTurbine_Plasma.class.getCanonicalName(),
				new IMultiBlockInfoContainer<GT_MetaTileEntity_LargeTurbine_Plasma>() {
					//region Structure
					private final IStructureDefinition<GT_MetaTileEntity_LargeTurbine_Plasma> definition =
							StructureDefinition.<GT_MetaTileEntity_LargeTurbine_Plasma>builder()
									.addShapeOldApi("main", new String[][]{
											{"000", "0.0", "000",},
											{"000", "0A0", "000",},
											{"000", "0A0", "000",},
											{"000", "010", "000",},
									})
									.addElement('0', ofBlock(sBlockCasings4, 12))
									.addElement('1', ofBlockHint(ImpactAPI.getBlockHint(), ImpactAPI.RED))
									.build();
					private final String[] desc = new String[]{
							EnumChatFormatting.RED + "Impact Details:",
							"- Tungstensteel Turbine Casing",
							"-" + EnumChatFormatting.RED + " Red:" + EnumChatFormatting.RESET + " Dynamo Hatch",
					};
					//endregion
					
					@Override
					public void construct(ItemStack stackSize, boolean hintsOnly,
										  GT_MetaTileEntity_LargeTurbine_Plasma tileEntity, ExtendedFacing aSide) {
						IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
						definition.buildOrHints(tileEntity, stackSize, "main", base.getWorld(), aSide,
								base.getXCoord(), base.getYCoord(), base.getZCoord(),
								1, 1, 0, hintsOnly
						);
					}
					
					@Override
					public String[] getDescription(ItemStack stackSize) {
						return desc;
					}
				}
		);
		
		//Diesel Engine
		registerTileClass(
				GT_MetaTileEntity_DieselEngine.class.getCanonicalName(),
				new IMultiBlockInfoContainer<GT_MetaTileEntity_DieselEngine>() {
					//region Structure
					private final IStructureDefinition<GT_MetaTileEntity_DieselEngine> definition =
							StructureDefinition.<GT_MetaTileEntity_DieselEngine>builder()
									.addShapeOldApi("main", new String[][]{
											{"000", "0.0", "000"},
											{"131", "323", "131"},
											{"131", "323", "131"},
											{"111", "141", "111"},
									})
									.addElement('0', ofBlock(sBlockCasings4, 13))
									.addElement('1', ofBlock(sBlockCasings4, 2))
									.addElement('2', ofBlock(sBlockCasings2, 4))
									.addElement('3', ofBlockHint(ImpactAPI.getBlockHint(), ImpactAPI.WHITE))
									.addElement('4', ofBlockHint(ImpactAPI.getBlockHint(), ImpactAPI.RED))
									.build();
					private final String[] desc = new String[]{
							EnumChatFormatting.RED + "Impact Details:",
							"- Engine Intake Casing",
							"- Stable Titanium Machine Casing",
							"- Titanium Gear Box Casing",
							"-" + EnumChatFormatting.RED + " Red: " + EnumChatFormatting.RESET + "Dynamo Hatch",
							"-" + EnumChatFormatting.WHITE + " White: " + EnumChatFormatting.RESET
									+ "Hathes or Stable Titanium Machine Casing",
					};
					//endregion
					
					@Override
					public void construct(ItemStack stackSize, boolean hintsOnly,
										  GT_MetaTileEntity_DieselEngine tileEntity, ExtendedFacing aSide) {
						IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
						definition.buildOrHints(tileEntity, stackSize, "main", base.getWorld(), aSide,
								base.getXCoord(), base.getYCoord(), base.getZCoord(),
								1, 1, 0, hintsOnly
						);
					}
					
					@Override
					public String[] getDescription(ItemStack stackSize) {
						return desc;
					}
				}
		);
		
		//Diesel Engine 2
		registerTileClass(
				GT_MetaTileEntity_DieselEngine2.class.getCanonicalName(),
				new IMultiBlockInfoContainer<GT_MetaTileEntity_DieselEngine2>() {
					//region Structure
					private final IStructureDefinition<GT_MetaTileEntity_DieselEngine2> definition =
							StructureDefinition.<GT_MetaTileEntity_DieselEngine2>builder()
									.addShapeOldApi("main", new String[][]{
											{"000", "0.0", "000"},
											{"131", "323", "131"},
											{"131", "323", "131"},
											{"111", "141", "111"},
									})
									.addElement('0', ofBlock(sBlockCasings2, 15))
									.addElement('1', ofBlock(sBlockCasings4, 0))
									.addElement('2', ofBlock(sBlockCasings7, 1))
									.addElement('3', ofBlockHint(ImpactAPI.getBlockHint(), ImpactAPI.WHITE))
									.addElement('4', ofBlockHint(ImpactAPI.getBlockHint(), ImpactAPI.RED))
									.build();
					private final String[] desc = new String[]{
							EnumChatFormatting.RED + "Impact Details:",
							"- Tungstensteel Pipe Casing",
							"- Robust Tungstensteel Machine Casing",
							"- Tungstensteel Gear Box Casing",
							"-" + EnumChatFormatting.RED + " Red: " + EnumChatFormatting.RESET + "Dynamo Hatch",
							"-" + EnumChatFormatting.WHITE + " White: " + EnumChatFormatting.RESET
									+ "Hathes or Robust Tungstensteel Machine Casing",
					};
					//endregion
					
					@Override
					public void construct(ItemStack stackSize, boolean hintsOnly,
										  GT_MetaTileEntity_DieselEngine2 tileEntity, ExtendedFacing aSide) {
						IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
						definition.buildOrHints(tileEntity, stackSize, "main", base.getWorld(), aSide,
								base.getXCoord(), base.getYCoord(), base.getZCoord(),
								1, 1, 0, hintsOnly
						);
					}
					
					@Override
					public String[] getDescription(ItemStack stackSize) {
						return desc;
					}
				}
		);
		
		//MultiFurnace
		registerTileClass(
				GT_MetaTileEntity_MultiFurnace.class.getCanonicalName(),
				new IMultiBlockInfoContainer<GT_MetaTileEntity_MultiFurnace>() {
					//region Structure
					private final IStructureDefinition<GT_MetaTileEntity_MultiFurnace> definition =
							StructureDefinition.<GT_MetaTileEntity_MultiFurnace>builder()
									.addShapeOldApi("main", new String[][]{
											{"000", "111", "0.0",},
											{"000", "1A1", "000",},
											{"000", "111", "000",},
									})
									.addElement('0', ofBlock(sBlockCasings1, 11))
									.addElement('1', ofBlock(sBlockCasings5, 0))
									.build();
					private final String[] desc = new String[]{
							EnumChatFormatting.RED + "Impact Details:",
							"- Heat Proof Machine Casing",
							"- Coil Block (any Coil Block)",
					};
					//endregion
					
					@Override
					public void construct(ItemStack stackSize, boolean hintsOnly,
										  GT_MetaTileEntity_MultiFurnace tileEntity, ExtendedFacing aSide) {
						IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
						definition.buildOrHints(tileEntity, stackSize, "main", base.getWorld(), aSide,
								base.getXCoord(), base.getYCoord(), base.getZCoord(),
								1, 2, 0, hintsOnly
						);
					}
					
					@Override
					public String[] getDescription(ItemStack stackSize) {
						return desc;
					}
				}
		);
		
		//LargeChemicalReactor
		registerTileClass(
				GT_MetaTileEntity_LargeChemicalReactor.class.getCanonicalName(),
				new IMultiBlockInfoContainer<GT_MetaTileEntity_LargeChemicalReactor>() {
					//region Structure
					private final IStructureDefinition<GT_MetaTileEntity_LargeChemicalReactor> definition =
							StructureDefinition.<GT_MetaTileEntity_LargeChemicalReactor>builder()
									.addShapeOldApi("main", new String[][]{
											{"000", "0.0", "000",},
											{"020", "010", "000",},
											{"000", "000", "000",},
									})
									.addElement('0', ofBlock(sBlockCasings8, 0))
									.addElement('1', ofBlock(sBlockCasings8, 1))
									.addElement('2', ofBlock(sBlockCasings5, 0))
									.build();
					private final String[] desc = new String[]{
							EnumChatFormatting.RED + "Impact Details:",
							"- Heat Proof Machine Casing",
							"- PTFE Pipe Casing",
							"- Cupronickel Coil Block",
						
						
					};
					//endregion
					
					@Override
					public void construct(ItemStack stackSize, boolean hintsOnly,
										  GT_MetaTileEntity_LargeChemicalReactor tileEntity, ExtendedFacing aSide) {
						IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
						definition.buildOrHints(tileEntity, stackSize, "main", base.getWorld(), aSide,
								base.getXCoord(), base.getYCoord(), base.getZCoord(),
								1, 1, 0, hintsOnly
						);
					}
					
					@Override
					public String[] getDescription(ItemStack stackSize) {
						return desc;
					}
				}
		);
		
		//Vacuum Freezer
		registerTileClass(
				GT_MetaTileEntity_VacuumFreezer.class.getCanonicalName(),
				new IMultiBlockInfoContainer<GT_MetaTileEntity_VacuumFreezer>() {
					//region Structure
					private final IStructureDefinition<GT_MetaTileEntity_VacuumFreezer> definition =
							StructureDefinition.<GT_MetaTileEntity_VacuumFreezer>builder()
									.addShapeOldApi("main", new String[][]{
											{"000", "0.0", "000",},
											{"000", "0A0", "000",},
											{"000", "000", "000",},
									})
									.addElement('0', ofBlock(sBlockCasings2, 1))
									.build();
					private final String[] desc = new String[]{
							EnumChatFormatting.RED + "Impact Details:",
							"- Frost Proof Machine Casing",
					};
					//endregion
					
					@Override
					public void construct(ItemStack stackSize, boolean hintsOnly,
										  GT_MetaTileEntity_VacuumFreezer tileEntity, ExtendedFacing aSide) {
						IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
						definition.buildOrHints(tileEntity, stackSize, "main", base.getWorld(), aSide,
								base.getXCoord(), base.getYCoord(), base.getZCoord(),
								1, 1, 0, hintsOnly
						);
					}
					
					@Override
					public String[] getDescription(ItemStack stackSize) {
						return desc;
					}
				}
		);
		
		//ImplosionCompressor
		registerTileClass(
				GT_MetaTileEntity_ImplosionCompressor.class.getCanonicalName(),
				new IMultiBlockInfoContainer<GT_MetaTileEntity_ImplosionCompressor>() {
					//region Structure
					private final IStructureDefinition<GT_MetaTileEntity_ImplosionCompressor> definition =
							StructureDefinition.<GT_MetaTileEntity_ImplosionCompressor>builder()
									.addShapeOldApi("main", new String[][]{
											{"000", "0.0", "000",},
											{"000", "0A0", "000",},
											{"000", "000", "000",},
									})
									.addElement('0', ofBlock(sBlockCasings2, 0))
									.build();
					private final String[] desc = new String[]{
							EnumChatFormatting.RED + "Impact Details:",
							"- Solid Steel Machine Casing",
					};
					//endregion
					
					@Override
					public void construct(ItemStack stackSize, boolean hintsOnly,
										  GT_MetaTileEntity_ImplosionCompressor tileEntity, ExtendedFacing aSide) {
						IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
						definition.buildOrHints(tileEntity, stackSize, "main", base.getWorld(), aSide,
								base.getXCoord(), base.getYCoord(), base.getZCoord(),
								1, 1, 0, hintsOnly
						);
					}
					
					@Override
					public String[] getDescription(ItemStack stackSize) {
						return desc;
					}
				}
		);
		
		//IndustrialPulverizer
		registerTileClass(
				GT_MetaTileEntity_IndustrialPulverizer.class.getCanonicalName(),
				new IMultiBlockInfoContainer<GT_MetaTileEntity_IndustrialPulverizer>() {
					//region Structure
					private final IStructureDefinition<GT_MetaTileEntity_IndustrialPulverizer> definition =
							StructureDefinition.<GT_MetaTileEntity_IndustrialPulverizer>builder()
									.addShapeOldApi("main", new String[][]{
											{"000", "0.0", "000",},
											{"010", "020", "000",},
											{"000", "000", "000",},
									})
									.addElement('0', ofBlock(sBlockCasings2, 0))
									.addElement('1', ofBlock(sBlockCasings2, 13))
									.addElement('2', ofBlock(sBlockCasings2, 3))
									.build();
					private final String[] desc = new String[]{
							EnumChatFormatting.RED + "Impact Details:",
							"- Solid Steel Machine Casing",
							"- Steel Gear Box Casing",
							"- Steel Pipe Casing",
					};
					//endregion
					
					@Override
					public void construct(ItemStack stackSize, boolean hintsOnly,
										  GT_MetaTileEntity_IndustrialPulverizer tileEntity, ExtendedFacing aSide) {
						IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
						definition.buildOrHints(tileEntity, stackSize, "main", base.getWorld(), aSide,
								base.getXCoord(), base.getYCoord(), base.getZCoord(),
								1, 1, 0, hintsOnly
						);
					}
					
					@Override
					public String[] getDescription(ItemStack stackSize) {
						return desc;
					}
				}
		);
		
		//HeatExchanger
		registerTileClass(
				GT_MetaTileEntity_HeatExchanger.class.getCanonicalName(),
				new IMultiBlockInfoContainer<GT_MetaTileEntity_HeatExchanger>() {
					//region Structure
					private final IStructureDefinition<GT_MetaTileEntity_HeatExchanger> definition =
							StructureDefinition.<GT_MetaTileEntity_HeatExchanger>builder()
									.addShapeOldApi("main", new String[][]{
											{"000", "000", "000", "0.0",},
											{"000", "010", "010", "000",},
											{"000", "000", "000", "000",},
									})
									.addElement('0', ofBlock(sBlockCasings4, 2))
									.addElement('1', ofBlock(sBlockCasings2, 14))
									.build();
					private final String[] desc = new String[]{
							EnumChatFormatting.RED + "Impact Details:",
							"- Stable Titanium Machine Casing",
							"- Titanium Pipe Casing",
					};
					//endregion
					
					@Override
					public void construct(ItemStack stackSize, boolean hintsOnly,
										  GT_MetaTileEntity_HeatExchanger tileEntity, ExtendedFacing aSide) {
						IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
						definition.buildOrHints(tileEntity, stackSize, "main", base.getWorld(), aSide,
								base.getXCoord(), base.getYCoord(), base.getZCoord(),
								1, 3, 0, hintsOnly
						);
					}
					
					@Override
					public String[] getDescription(ItemStack stackSize) {
						return desc;
					}
				}
		);
		
		//MultiblockCentrifuge
		registerTileClass(
				GT_MetaTileEntity_MultiblockCentrifuge.class.getCanonicalName(),
				new IMultiBlockInfoContainer<GT_MetaTileEntity_MultiblockCentrifuge>() {
					//region Structure
					private final IStructureDefinition<GT_MetaTileEntity_MultiblockCentrifuge> definition =
							StructureDefinition.<GT_MetaTileEntity_MultiblockCentrifuge>builder()
									.addShapeOldApi("main", new String[][]{
											{"000", "0.0", "000",},
											{"000", "010", "000",},
											{"000", "000", "000",},
									})
									.addElement('0', ofBlock(sBlockCasings4, 1))
									.addElement('1', ofBlock(sBlockCasings2, 11))
									.build();
					private final String[] desc = new String[]{
							EnumChatFormatting.RED + "Impact Details:",
							"- Clean Stainless Steel Machine Casing",
							"- Motor Machine Casing",
					};
					//endregion
					
					@Override
					public void construct(ItemStack stackSize, boolean hintsOnly,
										  GT_MetaTileEntity_MultiblockCentrifuge tileEntity, ExtendedFacing aSide) {
						IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
						definition.buildOrHints(tileEntity, stackSize, "main", base.getWorld(), aSide,
								base.getXCoord(), base.getYCoord(), base.getZCoord(),
								1, 1, 0, hintsOnly
						);
					}
					
					@Override
					public String[] getDescription(ItemStack stackSize) {
						return desc;
					}
				}
		);
		
		//MultiblockCentrifuge
		registerTileClass(
				GT_MetaTileEntity_MultiblockElectrolyzer.class.getCanonicalName(),
				new IMultiBlockInfoContainer<GT_MetaTileEntity_MultiblockElectrolyzer>() {
					//region Structure
					private final IStructureDefinition<GT_MetaTileEntity_MultiblockElectrolyzer> definition =
							StructureDefinition.<GT_MetaTileEntity_MultiblockElectrolyzer>builder()
									.addShapeOldApi("main", new String[][]{
											{"000", "0.0", "000",},
											{"010", "020", "000",},
											{"000", "000", "000",},
									})
									.addElement('0', ofBlock(sBlockCasings4, 2))
									.addElement('1', ofBlock(sBlockCasings5, 0))
									.addElement('2', ofBlock(sBlockCasings2, 10))
									.build();
					private final String[] desc = new String[]{
							EnumChatFormatting.RED + "Impact Details:",
							"- Stable Titanium Machine Casing",
							"- Pump Machine Casing",
							"- Cupronickel Coil Block",
					};
					//endregion
					
					@Override
					public void construct(ItemStack stackSize, boolean hintsOnly,
										  GT_MetaTileEntity_MultiblockElectrolyzer tileEntity, ExtendedFacing aSide) {
						IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
						definition.buildOrHints(tileEntity, stackSize, "main", base.getWorld(), aSide,
								base.getXCoord(), base.getYCoord(), base.getZCoord(),
								1, 1, 0, hintsOnly
						);
					}
					
					@Override
					public String[] getDescription(ItemStack stackSize) {
						return desc;
					}
				}
		);
		
		//AirFilter
		registerTileClass(
				GT_MetaTileEntity_AirFilter.class.getCanonicalName(),
				new IMultiBlockInfoContainer<GT_MetaTileEntity_AirFilter>() {
					//region Structure
					private final IStructureDefinition<GT_MetaTileEntity_AirFilter> definition =
							StructureDefinition.<GT_MetaTileEntity_AirFilter>builder()
									.addShapeOldApi("main", new String[][]{
											{"000", "121", "121", "0.0",},
											{"000", "2A2", "2A2", "000",},
											{"000", "121", "121", "000",},
									})
									.addElement('0', ofBlock(sBlockCasingsNH, 0))
									.addElement('1', ofBlock(sBlockCasingsNH, 1))
									.addElement('2', ofBlockHint(ImpactAPI.getBlockHint(), ImpactAPI.WHITE))
									.build();
					private final String[] desc = new String[]{
							EnumChatFormatting.RED + "Impact Details:",
							"- Air Filter Machine Casing",
							"- Air Filter Vent Casing",
							"- White: Any Muffler Hatch",
					};
					//endregion
					
					@Override
					public void construct(ItemStack stackSize, boolean hintsOnly,
										  GT_MetaTileEntity_AirFilter tileEntity, ExtendedFacing aSide) {
						IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
						definition.buildOrHints(tileEntity, stackSize, "main", base.getWorld(), aSide,
								base.getXCoord(), base.getYCoord(), base.getZCoord(),
								1, 3, 0, hintsOnly
						);
					}
					
					@Override
					public String[] getDescription(ItemStack stackSize) {
						return desc;
					}
				}
		);
		
		//Cleanroom
		registerTileClass(
				GT_MetaTileEntity_Cleanroom.class.getCanonicalName(),
				new IMultiBlockInfoContainer<GT_MetaTileEntity_Cleanroom>() {
					//region Structure
					private final IStructureDefinition<GT_MetaTileEntity_Cleanroom> definition =
							StructureDefinition.<GT_MetaTileEntity_Cleanroom>builder()
									.addShapeOldApi("main", new String[][]{
											{"000000000000000", "000000000000000", "000000000000000", "000000000000000", "000000000000000", "000000000000000", "000000000000000", "000000000000000", "000000000000000", "000000000000000", "000000000000000", "000000000000000", "000000000000000", "000000000000000", "000000000000000",},
											{"011111111111110", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "000000000000000",},
											{"011111111111110", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "000000000000000",},
											{"011111111111110", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "000000000000000",},
											{"011111111111110", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "000000000000000",},
											{"011111111111110", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "000000000000000",},
											{"011111111111110", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "000000000000000",},
											{"0111111.1111110", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "000000000000000",},
											{"011111111111110", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "000000000000000",},
											{"011111111111110", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "000000000000000",},
											{"011111111111110", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "000000000000000",},
											{"011111111111110", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "000000000000000",},
											{"011111111111110", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "000000000000000",},
											{"011111111111110", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "0AAAAAAAAAAAAA0", "000000000000000",},
											{"000000000000000", "000000000000000", "000000000000000", "000000000000000", "000000000000000", "000000000000000", "000000000000000", "000000000000000", "000000000000000", "000000000000000", "000000000000000", "000000000000000", "000000000000000", "000000000000000", "000000000000000",},
									})
									.addElement('0', ofBlock(sBlockReinforced, 2))
									.addElement('1', ofBlock(sBlockCasings3, 11))
									.addElement('2', ofBlockHint(ImpactAPI.getBlockHint(), ImpactAPI.RED))
									.build();
					private final String[] desc = new String[]{
							EnumChatFormatting.RED + "Impact Details:",
							"- Maximum Size Cleanroom",
							"- Plascrete Block",
							"- Filter Machine Casing",
							"- Reinforced Door any Plascrete Block",
					};
					//endregion
					
					@Override
					public void construct(ItemStack stackSize, boolean hintsOnly,
										  GT_MetaTileEntity_Cleanroom tileEntity, ExtendedFacing aSide) {
						IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
						definition.buildOrHints(tileEntity, stackSize, "main", base.getWorld(), aSide,
								base.getXCoord(), base.getYCoord(), base.getZCoord(),
								7, 0, 7, hintsOnly
						);
					}
					
					@Override
					public String[] getDescription(ItemStack stackSize) {
						return desc;
					}
				}
		);
		
		//DistillationTower
		registerTileClass(
				GT_MetaTileEntity_DistillationTower.class.getCanonicalName(),
				new IMultiBlockInfoContainer<GT_MetaTileEntity_DistillationTower>() {
					//region Structure
					private final IStructureDefinition<GT_MetaTileEntity_DistillationTower> definition =
							StructureDefinition.<GT_MetaTileEntity_DistillationTower>builder()
									.addShapeOldApi("main", new String[][]{
											{"000", "000", "000", "000", "000", "000", "000", "000", "000", "000", "000", "0.0",},
											{"000", "0A0", "0A0", "0A0", "0A0", "0A0", "0A0", "0A0", "0A0", "0A0", "0A0", "000",},
											{"000", "000", "000", "000", "000", "000", "000", "000", "000", "000", "000", "000",},
									})
									.addElement('0', ofBlock(sBlockCasings4, 1))
									.build();
					private final String[] desc = new String[]{
							EnumChatFormatting.RED + "Impact Details:",
							"- Maximum Size Distillation Tower",
							"- Clean Stainless Steel Machine Casing",
					};
					//endregion
					
					@Override
					public void construct(ItemStack stackSize, boolean hintsOnly,
										  GT_MetaTileEntity_DistillationTower tileEntity, ExtendedFacing aSide) {
						IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
						definition.buildOrHints(tileEntity, stackSize, "main", base.getWorld(), aSide,
								base.getXCoord(), base.getYCoord(), base.getZCoord(),
								1, 11, 0, hintsOnly
						);
					}
					
					@Override
					public String[] getDescription(ItemStack stackSize) {
						return desc;
					}
				}
		);
		
		//Coke Oven
		registerTileClass(GTMTE_CokeOven.class.getCanonicalName(), new IMultiBlockInfoContainer<GTMTE_CokeOven>() {
			//region Structure
			private final IStructureDefinition<GTMTE_CokeOven> definition =
					StructureDefinition.<GTMTE_CokeOven>builder()
							.addShapeOldApi("main", new String[][]{
									{"000", "0.0", "000"},
									{"000", "0A0", "000"},
									{"000", "000", "000"},
							})
							.addElement('0', ofBlock(sBlockCasings8, 5))
							.build();
			private final String[] desc = new String[]{
					EnumChatFormatting.RED + "Impact Details:",
					"- Coke Oven Bricks",
					"- Hathes: any coke Oven Bricks",
			};
			//endregion
			
			@Override
			public void construct(ItemStack stackSize, boolean hintsOnly, GTMTE_CokeOven tileEntity,
								  ExtendedFacing aSide) {
				IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
				definition.buildOrHints(tileEntity, stackSize, "main", base.getWorld(), aSide,
						base.getXCoord(), base.getYCoord(), base.getZCoord(),
						1, 1, 0, hintsOnly
				);
			}
			
			@Override
			public String[] getDescription(ItemStack stackSize) {
				return desc;
			}
		});
		
		//EBF
		registerTileClass(
				GT_MetaTileEntity_ElectricBlastFurnace.class.getCanonicalName(),
				new IMultiBlockInfoContainer<GT_MetaTileEntity_ElectricBlastFurnace>() {
					//region Structure
					private final IStructureDefinition<GT_MetaTileEntity_ElectricBlastFurnace> definition =
							StructureDefinition.<GT_MetaTileEntity_ElectricBlastFurnace>builder()
									.addShapeOldApi("main", new String[][]{
											{"000", "111", "111", "0.0"},
											{"000", "1A1", "1A1", "000"},
											{"000", "111", "111", "000"},
										
									})
									.addElement('0', ofBlock(sBlockCasings1, 11))
									.addElement('1', ofBlock(sBlockCasings5, 0))
									.build();
					private final String[] desc = new String[]{
							EnumChatFormatting.RED + "Impact Details:",
							"- Heat Proof Machine Casing",
							"- Coil Block (any Coil Block)",
							"- Muffler Hatch (top side center Heat Proof Machine Casing)",
							"- Other Hatches (any Heat Proof Machine Casing)",
							"- * Output Hatch for Muffler Pollution (any top side Heat Proof Machine Casing)",
					};
					//endregion
					
					@Override
					public void construct(ItemStack stackSize, boolean hintsOnly,
										  GT_MetaTileEntity_ElectricBlastFurnace tileEntity, ExtendedFacing aSide) {
						IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
						definition.buildOrHints(tileEntity, stackSize, "main", base.getWorld(), aSide,
								base.getXCoord(), base.getYCoord(), base.getZCoord(),
								1, 3, 0, hintsOnly
						);
					}
					
					@Override
					public String[] getDescription(ItemStack stackSize) {
						return desc;
					}
				}
		);
	}
}