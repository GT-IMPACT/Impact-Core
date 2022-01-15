package com.impact.mixins.ic2;

import ic2.core.item.tool.ItemElectricTool;
import ic2.core.item.tool.ItemToolMiningLaser;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ItemToolMiningLaser.class)
	public class MiningLaser_Mixin extends ItemElectricTool {

		public MiningLaser_Mixin() {
			super(null, 0);
		}

		@Override
		public double getMaxCharge(ItemStack itemStack) {
			this.maxCharge = 100000000;
			return maxCharge;
		}
	}
