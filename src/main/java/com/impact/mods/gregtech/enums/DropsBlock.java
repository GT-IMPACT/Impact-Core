package com.impact.mods.gregtech.enums;

import com.impact.core.Impact_API;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_OreDictUnificator;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class DropsBlock {
	
	public final Block block;
	public final int meta;
	public final float[] chance;
	public final List<ItemStack> drop;
	
	private DropsBlock(Block block, int meta, float[] chance, ItemStack... drop) {
		this.block = block;
		this.meta  = meta;
		if (chance != null) {
			this.chance = chance;
		} else {
			this.chance = new float[drop.length];
			Arrays.fill(this.chance, 0.5f);
		}
		this.drop = new ArrayList<>();
		this.drop.addAll(Arrays.asList(drop));
		Impact_API.dropsFromBlock.add(this);
	}
	
	private DropsBlock(Block block, int meta, float[] chance, OrePrefixes prefixes, Materials... drop) {
		this.block = block;
		this.meta  = meta;
		if (chance != null) {
			this.chance = chance;
		} else {
			this.chance = new float[drop.length];
			Arrays.fill(this.chance, 0.5f);
		}
		this.drop = new ArrayList<>();
		Stream.of(drop).forEach(material -> this.drop.add(GT_OreDictUnificator.get(prefixes, material, 1)));
		Impact_API.dropsFromBlock.add(this);
	}
	
	private DropsBlock(Block block, int meta, float chance, OrePrefixes prefixes, Materials... drop) {
		this.block  = block;
		this.meta   = meta;
		this.chance = new float[drop.length];
		Arrays.fill(this.chance, chance);
		this.drop = new ArrayList<>();
		Stream.of(drop).forEach(material -> this.drop.add(GT_OreDictUnificator.get(prefixes, material, 1)));
		Impact_API.dropsFromBlock.add(this);
	}
	
	private DropsBlock(Block block, int meta, float chance, ItemStack... drop) {
		this.block = block;
		this.meta  = meta;

		this.chance = new float[drop.length];
		Arrays.fill(this.chance, chance);
		
		this.drop = new ArrayList<>();
		this.drop.addAll(Arrays.asList(drop));
		Impact_API.dropsFromBlock.add(this);
	}
	
	/**
	 * @param block  Block
	 * @param meta   meta (damage) of Block
	 * @param chance dropped chance 1.0f - 100%
	 * @param drop   ItemStack (Array)
	 * @example DropsBlock.add(Blocks.stone, 0, 0.5f, new ItemStack ( Items.coal, 1, 1));
	 * @example DropsBlock.add(Blocks.stone, 0, 0.5f, new ItemStack ( Items.coal, 1, 1), new ItemStack(Items.stick, 1));
	 */
	public static void add(Block block, int meta, float[] chance, ItemStack... drop) {
		if (block != null) {
			new DropsBlock(block, meta, chance, drop);
		}
	}
	
	/**
	 * @param block  Block
	 * @param meta   meta (damage) of Block
	 * @param chance dropped chance 1.0f - 100%
	 * @param drop   ItemStack (Array)
	 * @example DropsBlock.add(Blocks.stone, 0, 0.5f, new ItemStack ( Items.coal, 1, 1));
	 * @example DropsBlock.add(Blocks.stone, 0, 0.5f, new ItemStack ( Items.coal, 1, 1), new ItemStack(Items.stick, 1));
	 */
	public static void add(Block block, int meta, float chance, ItemStack... drop) {
		if (block != null) {
			new DropsBlock(block, meta, chance, drop);
		}
	}
	
	/**
	 * @param block    Block
	 * @param meta     meta (damage) of Block
	 * @param chance   dropped chance 1.0f - 100%
	 * @param prefixes OrePrefix GregTech
	 * @param drop     Materials GregTech (Array)
	 * @example DropsBlock.add(Blocks.stone, 0, 0.5f, OrePrefixes.nugget, Materials.Neutronium);
	 * @example DropsBlock.add(Blocks.stone, 0, 0.5f, OrePrefixes.dust, Materials.Neutronium, Materials.Steel, Materials.Iron);
	 */
	public static void add(Block block, int meta, float[] chance, OrePrefixes prefixes, Materials... drop) {
		if (block != null) {
			new DropsBlock(block, meta, chance, prefixes, drop);
		}
	}
	
	/**
	 * @param block    Block
	 * @param meta     meta (damage) of Block
	 * @param chance   dropped chance 1.0f - 100%
	 * @param prefixes OrePrefix GregTech
	 * @param drop     Materials GregTech (Array)
	 * @example DropsBlock.add(Blocks.stone, 0, 0.5f, OrePrefixes.nugget, Materials.Neutronium);
	 * @example DropsBlock.add(Blocks.stone, 0, 0.5f, OrePrefixes.dust, Materials.Neutronium, Materials.Steel, Materials.Iron);
	 */
	public static void add(Block block, int meta, float chance, OrePrefixes prefixes, Materials... drop) {
		if (block != null) {
			new DropsBlock(block, meta, chance, prefixes, drop);
		}
	}
}