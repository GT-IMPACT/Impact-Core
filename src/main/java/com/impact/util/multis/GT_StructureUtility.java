package com.impact.util.multis;

import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.BaseMetaPipeEntity;
import gregtech.api.metatileentity.implementations.GT_MetaPipeEntity_Frame;
import gregtech.api.util.GT_OreDictUnificator;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import space.impact.api.ImpactAPI;
import space.impact.api.multiblocks.structure.IStructureElement;
import space.impact.api.multiblocks.structure.IStructureElementNoPlacement;

import java.util.Arrays;

public class GT_StructureUtility {
	private GT_StructureUtility() {
		throw new AssertionError("Not instantiable");
	}
	
	public static <T> IStructureElementNoPlacement<T> ofHatchAdder(IGT_HatchAdder<T> IGT_HatchAdder, int textureIndex, int dots) {
		return ofHatchAdder(IGT_HatchAdder, textureIndex, ImpactAPI.getBlockHint(), dots);
	}
	
	public static <T> IStructureElementNoPlacement<T> ofHatchAdder(IGT_HatchAdder<T> IGT_HatchAdder, int textureIndex, Block hintBlock, int hintMeta) {
		if (IGT_HatchAdder == null || hintBlock == null) {
			throw new IllegalArgumentException();
		}
		return new IStructureElementNoPlacement<T>() {
			@Override
			public boolean check(T t, World world, int x, int y, int z) {
				TileEntity tileEntity = world.getTileEntity(x, y, z);
				return tileEntity instanceof IGregTechTileEntity && IGT_HatchAdder.apply(t, (IGregTechTileEntity) tileEntity, (short) textureIndex);
			}
			
			@Override
			public boolean spawnHint(T t, World world, int x, int y, int z, ItemStack trigger) {
				ImpactAPI.hintParticle(world, x, y, z, hintBlock, hintMeta);
				return true;
			}
		};
	}
	
	public static <T> IStructureElement<T> ofFrame(Materials aFrameMaterial) {
		if (aFrameMaterial == null) throw new IllegalArgumentException();
		return new IStructureElement<T>() {
			
			private IIcon[] mIcons;
			
			@Override
			public boolean check(T t, World world, int x, int y, int z) {
				TileEntity tBase = world.getTileEntity(x, y, z);
				if (tBase instanceof BaseMetaPipeEntity) {
					BaseMetaPipeEntity tPipeBase = (BaseMetaPipeEntity) tBase;
					if (tPipeBase.isInvalidTileEntity()) return false;
					if (tPipeBase.getMetaTileEntity() instanceof GT_MetaPipeEntity_Frame)
						return aFrameMaterial == ((GT_MetaPipeEntity_Frame) tPipeBase.getMetaTileEntity()).mMaterial;
				}
				return false;
			}
			
			@Override
			public boolean spawnHint(T t, World world, int x, int y, int z, ItemStack trigger) {
				if (mIcons == null) {
					mIcons = new IIcon[6];
					Arrays.fill(mIcons, aFrameMaterial.mIconSet.mTextures[OrePrefixes.frameGt.mTextureIndex].getIcon());
				}
				ImpactAPI.hintParticleTinted(world, x, y, z, mIcons, aFrameMaterial.mRGBa);
				return true;
			}
			
			@Override
			public boolean placeBlock(T t, World world, int x, int y, int z, ItemStack trigger) {
				ItemStack tFrameStack = GT_OreDictUnificator.get(OrePrefixes.frameGt, aFrameMaterial, 1);
				if (tFrameStack.getItem() instanceof ItemBlock) {
					ItemBlock tFrameStackItem = (ItemBlock) tFrameStack.getItem();
					return tFrameStackItem.placeBlockAt(tFrameStack, null, world, x, y, z, 6, 0, 0, 0, Items.feather.getDamage(tFrameStack));
				}
				return false;
			}
		};
	}
	
	public static <T> IStructureElement<T> ofHatchAdderOptional(IGT_HatchAdder<T> IGT_HatchAdder, int textureIndex, int dots, Block placeCasing, int placeCasingMeta) {
		return ofHatchAdderOptional(IGT_HatchAdder, textureIndex, ImpactAPI.getBlockHint(), dots - 1, placeCasing, placeCasingMeta);
	}
	
	public static <T> IStructureElement<T> ofHatchAdderOptional(IGT_HatchAdder<T> IGT_HatchAdder, int textureIndex, Block hintBlock, int hintMeta, Block placeCasing, int placeCasingMeta) {
		if (IGT_HatchAdder == null || hintBlock == null) {
			throw new IllegalArgumentException();
		}
		return new IStructureElement<T>() {
			@Override
			public boolean check(T t, World world, int x, int y, int z) {
				TileEntity tileEntity = world.getTileEntity(x, y, z);
				Block worldBlock = world.getBlock(x, y, z);
				return (tileEntity instanceof IGregTechTileEntity &&
						IGT_HatchAdder.apply(t, (IGregTechTileEntity) tileEntity, (short) textureIndex)) ||
						(worldBlock == placeCasing && worldBlock.getDamageValue(world, x, y, z) == placeCasingMeta);
			}
			
			@Override
			public boolean spawnHint(T t, World world, int x, int y, int z, ItemStack trigger) {
				ImpactAPI.hintParticle(world, x, y, z, hintBlock, hintMeta);
				return true;
			}
			
			@Override
			public boolean placeBlock(T t, World world, int x, int y, int z, ItemStack trigger) {
				world.setBlock(x, y, z, placeCasing, placeCasingMeta, 2);
				return true;
			}
		};
	}
}
