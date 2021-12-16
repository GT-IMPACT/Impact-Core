//package com.impact.mixins.extracells;
//
//import extracells.tileentity.TileBase;
//import extracells.tileentity.TileEntityFluidInterface;
//import net.minecraft.nbt.NBTTagCompound;
//import net.minecraftforge.fluids.Fluid;
//import net.minecraftforge.fluids.FluidRegistry;
//import net.minecraftforge.fluids.FluidTank;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.Shadow;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//
//@Mixin(TileEntityFluidInterface.class)
//public class TileEntityFluidInterface_Mixin extends TileBase {
//
//	@Shadow(remap = false)
//	public FluidTank[] tanks;
//
//	@Shadow(remap = false)
//	public Integer[] fluidFilter;
//
//	@Inject(method = "readFromNBT", at = @At("TAIL"), remap = false)
//	public void readFromNBT(NBTTagCompound tag, CallbackInfo ci) {
//		for (int i = 0; i < tanks.length; i++) {
//			if (!tag.hasKey("notShittyFluidID" + i)) {
//				continue;
//			}
//			Fluid fluid = FluidRegistry.getFluid(tag.getString("notShittyFluidID" + i));
//			if (fluid == null) {
//				continue;
//			}
//			int id = FluidRegistry.getFluidID(fluid);
//			fluidFilter[i] = id;
//		}
//	}
//
//	@Inject(method = "writeToNBTWithoutExport", at = @At("TAIL"), remap = false)
//	public void writeToNBTWithoutExport(NBTTagCompound tag, CallbackInfo ci) {
//		for (int i = 0; i < tanks.length; i++) {
//			int id = fluidFilter[i];
//			Fluid fluid = FluidRegistry.getFluid(id);
//			if (fluid == null) {
//				continue;
//			}
//			tag.setString("notShittyFluidID" + i, FluidRegistry.getFluidName(fluid));
//		}
//	}
//
//}
