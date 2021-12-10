package com.impact.mixins.ae2;

import appeng.api.movable.IMovableHandler;
import appeng.api.movable.IMovableTile;
import appeng.core.features.registries.MovableTileRegistry;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.tileentity.TileEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;

@Mixin(MovableTileRegistry.class)
public class MovableTileRegistry_Mixin {
	
	@Final
	@Shadow(remap = false)
	private IMovableHandler nullHandler;
	
	@Final
	@Shadow(remap = false)
	private HashMap<Class<? extends TileEntity>, IMovableHandler> Valid;
	
	@Inject(method = "askToMove", at = @At("HEAD"), remap = false, cancellable = true)
	public void askToMove(TileEntity te, CallbackInfoReturnable<Boolean> cir) {
		
		Class<? extends TileEntity> myClass = te.getClass();
		IMovableHandler canMove = this.Valid.get(myClass);
		if (canMove == null) {
			canMove = this.testClass(myClass, te);
		}
		
		if (canMove != this.nullHandler) {
			if (te instanceof IMovableTile) {
				((IMovableTile)te).prepareToMove();
			}
			
			te.invalidate();
			cir.setReturnValue(true);
		} else {
			if (te instanceof IGregTechTileEntity) {
				te.invalidate();
				cir.setReturnValue(true);
			}
			cir.setReturnValue(false);
		}
	}
	
	@Shadow(remap = false)
	private IMovableHandler testClass(Class<? extends TileEntity> myClass, TileEntity te) {
		throw new IllegalStateException("Mixin failed to shadow testClass()");
	}
}
