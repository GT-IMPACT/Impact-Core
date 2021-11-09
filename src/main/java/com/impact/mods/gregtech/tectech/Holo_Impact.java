package com.impact.mods.gregtech.tectech;

import com.impact.mods.gregtech.blocks.Casing_Helper;
import com.impact.mods.gregtech.tileentities.multi.processing.defaultmachines.GTMTE_BlastSmelter;
import com.impact.mods.gregtech.tileentities.multi.processing.defaultmachines.GTMTE_Pyrolyse;
import com.impact.mods.gregtech.tileentities.multi.processing.defaultmachines.GTMTE_SawMill;
import com.impact.mods.gregtech.tileentities.multi.processing.parallel.*;
import com.impact.mods.gregtech.tileentities.multi.storage.GTMTE_LapPowerStation;
import com.impact.mods.gregtech.tileentities.multi.units.GTMTE_BasicWaterPump;
import com.impact.mods.gregtech.tileentities.multi.units.GTMTE_DrillerWater;
import com.impact.mods.gregtech.tileentities.multi.units.GTMTE_SpaceElevator;
import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import space.impact.api.ImpactAPI;
import space.impact.api.multiblocks.alignment.constructable.IMultiBlockInfoContainer;
import space.impact.api.multiblocks.alignment.enumerable.ExtendedFacing;
import space.impact.api.multiblocks.structure.IStructureDefinition;
import space.impact.api.multiblocks.structure.StructureDefinition;

import static com.impact.loader.ItemRegistery.*;
import static com.impact.mods.gregtech.blocks.Casing_Helper.sCaseCore1;
import static com.impact.mods.gregtech.blocks.Casing_Helper.sCaseCore2;
import static gregtech.api.GregTech_API.*;
import static space.impact.api.multiblocks.alignment.constructable.IMultiBlockInfoContainer.registerTileClass;
import static space.impact.api.multiblocks.structure.StructureUtility.*;


public class Holo_Impact implements Runnable {
	
	@SuppressWarnings({"deprecation"})
	@Override
	public void run() {
		
		//Blast Smelter
		registerTileClass(GTMTE_BlastSmelter.class.getCanonicalName(), new IMultiBlockInfoContainer<GTMTE_BlastSmelter>() {
			//region Structure
			private final IStructureDefinition<GTMTE_BlastSmelter> definition =
					StructureDefinition.<GTMTE_BlastSmelter>builder()
							.addShapeOldApi("main", new String[][]{
									{".000.", ".111.", ".111.", ".0.0.",},
									{"00000", "1AAA1", "1AAA1", "00000",},
									{"00000", "1AAA1", "1AAA1", "00000",},
									{"00000", "1AAA1", "1AAA1", "00000",},
									{".000.", ".111.", ".111.", ".000.",},
								
							})
							.addElement('0', ofBlock(sBlockCasings8, 3))
							.addElement('1', ofBlock(sBlockCasings5, 0))
							.build();
			private final String[] desc = new String[]{
					EnumChatFormatting.RED + "Impact Details:",
					"- HSLA Casing",
					"- Coil Block (any Coil Block)",
					"- Hatches (any HSLA Casing)",
			};
			//endregion
			
			@Override
			public void construct(ItemStack stackSize, boolean hintsOnly, GTMTE_BlastSmelter tileEntity,
								  ExtendedFacing aSide) {
				IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
				definition.buildOrHints(tileEntity, stackSize, "main", base.getWorld(), aSide,
						base.getXCoord(), base.getYCoord(), base.getZCoord(),
						2, 3, 0, hintsOnly
				);
			}
			
			@Override
			public String[] getDescription(ItemStack stackSize) {
				return desc;
			}
		});
		
		//Water Driller
		registerTileClass(GTMTE_DrillerWater.class.getCanonicalName(), new IMultiBlockInfoContainer<GTMTE_DrillerWater>() {
			//region Structure
			private final IStructureDefinition<GTMTE_DrillerWater> definition =
					StructureDefinition.<GTMTE_DrillerWater>builder()
							.addShapeOldApi("main", new String[][]{
									
									{".....", ".....", ".....", ".....", ".....", ".....", "11.11", "0...0",
											"0...0",},
									{".....", ".....", ".....", ".111.", ".0.0.", ".0.0.", "11111", ".....",
											".....",},
									{"..0..", "..0..", "..0..", ".111.", ".....", ".....", "11.11", ".....",
											".....",},
									{".....", ".....", ".....", ".111.", ".0.0.", ".0.0.", "11111", ".....",
											".....",},
									{".....", ".....", ".....", ".....", ".....", ".....", "11111", "0...0",
											"0...0",},
							})
							.addElement('0', ofHintDeferred(() -> new IIcon[]{
									Textures.BlockIcons.FRAMEBOXGT.getIcon(),
									Textures.BlockIcons.FRAMEBOXGT.getIcon(),
									Textures.BlockIcons.FRAMEBOXGT.getIcon(),
									Textures.BlockIcons.FRAMEBOXGT.getIcon(),
									Textures.BlockIcons.FRAMEBOXGT.getIcon(),
									Textures.BlockIcons.FRAMEBOXGT.getIcon(),
							}, Materials.Steel.mRGBa))
							.addElement('1', ofBlock(sBlockCasings2, 0))
							.build();
			private final String[] desc = new String[]{
					EnumChatFormatting.RED + "Impact Details:",
					"- Steel Frame Box",
					"- Solid Steel Machine Casing",
					"- Hatches (any Solid Steel Machine Casing)",
			};
			//endregion
			
			@Override
			public void construct(ItemStack stackSize, boolean hintsOnly, GTMTE_DrillerWater tileEntity,
								  ExtendedFacing aSide) {
				IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
				definition.buildOrHints(tileEntity, stackSize, "main", base.getWorld(), aSide,
						base.getXCoord(), base.getYCoord(), base.getZCoord(),
						2, 6, 0, hintsOnly
				);
			}
			
			@Override
			public String[] getDescription(ItemStack stackSize) {
				return desc;
			}
		});
		
		//LapStation
		registerTileClass(
				GTMTE_LapPowerStation.class.getCanonicalName(),
				new IMultiBlockInfoContainer<GTMTE_LapPowerStation>() {
					//region Structure
					private final IStructureDefinition<GTMTE_LapPowerStation> definition =
							StructureDefinition.<GTMTE_LapPowerStation>builder()
									.addShapeOldApi("main", new String[][]{
											{"11111", "11111", "00000", "00.00",},
											{"11111", "12221", "00000", "00000",},
											{"11111", "12221", "00000", "00000",},
											{"11111", "12221", "00000", "00000",},
											{"11111", "11111", "00000", "00000",},
									})
									.addElement('0', ofBlock(Casing_Helper.sCaseCore2, 8))
									.addElement('1', ofBlock(IGlassBlock, 0))
									.addElement('2', ofBlock(lscLapotronicEnergyUnit, 8))
									.build();
					private final String[] desc = new String[]{
							EnumChatFormatting.RED + "Impact Details:",
							"- Warning! It is minimal structure",
							"- Lapotronic Super Capacitor Casing",
							"- I-Glass (any glass)",
							"- Lapotronic Capacitor (IV-UHV)",
							"- Hatches (any Lapotronic Super Capacitor Casing)",
					};
					//endregion
					
					@Override
					public void construct(ItemStack stackSize, boolean hintsOnly, GTMTE_LapPowerStation tileEntity, ExtendedFacing aSide) {
						IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
						definition.buildOrHints(tileEntity, stackSize, "main", base.getWorld(), aSide, base.getXCoord(), base.getYCoord(), base.getZCoord(),
								2, 3, 0, hintsOnly
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
