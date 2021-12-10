package com.impact.mixins.impactapi;

import com.impact.impact;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.ForgeDirection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import space.impact.api.multiblocks.alignment.IAlignment;
import space.impact.api.multiblocks.alignment.constructable.ConstructableUtility;
import space.impact.api.multiblocks.alignment.constructable.IConstructable;
import space.impact.api.multiblocks.alignment.constructable.IMultiBlockInfoContainer;
import space.impact.api.multiblocks.alignment.enumerable.ExtendedFacing;

@Mixin(ConstructableUtility.class)
public class ImpactAPI_Mixin {
	
	@Inject(method = "handle0", at = @At("HEAD"), remap = false, cancellable = true)
	private static void handle(ItemStack aStack, EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ, int aSide, CallbackInfoReturnable<Boolean> cir) {
		TileEntity tTileEntity = aWorld.getTileEntity(aX, aY, aZ);
		if (tTileEntity == null || aPlayer instanceof FakePlayer) {
			cir.setReturnValue(aPlayer instanceof EntityPlayerMP);
			return;
		}
		try {
			if (aPlayer == Minecraft.getMinecraft().thePlayer) {
				if (tTileEntity instanceof IGregTechTileEntity) {
					IMetaTileEntity metaTE = ((IGregTechTileEntity) tTileEntity).getMetaTileEntity();
					if (metaTE instanceof IConstructable) {
						((IConstructable) metaTE).construct(aStack, true);
						impact.proxy.addClientSideChatMessages(((IConstructable) metaTE).getStructureDescription(aStack));
						cir.setReturnValue(false);
						return;
					} else if (IMultiBlockInfoContainer.contains(metaTE.getClass())) {
						IMultiBlockInfoContainer<IMetaTileEntity> iMultiBlockInfoContainer = IMultiBlockInfoContainer.get(metaTE.getClass());
						if (metaTE instanceof IAlignment) {
							iMultiBlockInfoContainer.construct(aStack, true, metaTE, ((IAlignment) metaTE).getExtendedFacing());
						} else {
							iMultiBlockInfoContainer.construct(aStack, true, metaTE, ExtendedFacing.of(ForgeDirection.getOrientation(((IGregTechTileEntity) tTileEntity).getFrontFacing())));
						}
						impact.proxy.addClientSideChatMessages(iMultiBlockInfoContainer.getDescription(aStack));
						cir.setReturnValue(false);
						return;
					}
				}
				cir.setReturnValue(false);
				return;
			} else if (aPlayer instanceof EntityPlayerMP) {
				if (aPlayer.isSneaking() && aPlayer.capabilities.isCreativeMode) {
					if (tTileEntity instanceof IGregTechTileEntity) {
						IMetaTileEntity metaTE = ((IGregTechTileEntity) tTileEntity).getMetaTileEntity();
						if (metaTE instanceof IConstructable) {
							((IConstructable) metaTE).construct(aStack, false);
						} else if (IMultiBlockInfoContainer.contains(metaTE.getClass())) {
							IMultiBlockInfoContainer<IMetaTileEntity> iMultiBlockInfoContainer = IMultiBlockInfoContainer.get(metaTE.getClass());
							if (metaTE instanceof IAlignment) {
								iMultiBlockInfoContainer.construct(aStack, false, metaTE, ((IAlignment) metaTE).getExtendedFacing());
							} else {
								iMultiBlockInfoContainer.construct(aStack, false, metaTE, ExtendedFacing.of(ForgeDirection.getOrientation(((IGregTechTileEntity) tTileEntity).getFrontFacing())));
							}
						}
					}
				}
			}
		} catch (Exception e) {
			cir.setReturnValue(true);
			return;
		}
		cir.setReturnValue(true);
	}
}