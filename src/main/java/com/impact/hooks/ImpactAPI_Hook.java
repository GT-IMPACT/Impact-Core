package com.impact.hooks;

import com.impact.impact;
import gloomyfolken.hooklib.asm.Hook;
import gloomyfolken.hooklib.asm.ReturnCondition;
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
import space.impact.api.multiblocks.alignment.IAlignment;
import space.impact.api.multiblocks.alignment.constructable.ConstructableUtility;
import space.impact.api.multiblocks.alignment.constructable.IConstructable;
import space.impact.api.multiblocks.alignment.constructable.IMultiBlockInfoContainer;
import space.impact.api.multiblocks.alignment.enumerable.ExtendedFacing;

public class ImpactAPI_Hook {
	@Hook(returnCondition = ReturnCondition.ALWAYS, isMandatory = true)
	public static boolean handle(ConstructableUtility constructableUtility, ItemStack aStack, EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ, int aSide) {
		TileEntity tTileEntity = aWorld.getTileEntity(aX, aY, aZ);
		if (tTileEntity == null || aPlayer instanceof FakePlayer) {
			return aPlayer instanceof EntityPlayerMP;
		}
		try {
			if (aPlayer == Minecraft.getMinecraft().thePlayer) {
				if (tTileEntity instanceof IGregTechTileEntity) {
					IMetaTileEntity metaTE = ((IGregTechTileEntity) tTileEntity).getMetaTileEntity();
					if (metaTE instanceof IConstructable) {
						((IConstructable) metaTE).construct(aStack, true);
						impact.proxy.addClientSideChatMessages(((IConstructable) metaTE).getStructureDescription(aStack));
						return false;
					} else if (IMultiBlockInfoContainer.contains(metaTE.getClass())) {
						IMultiBlockInfoContainer<IMetaTileEntity> iMultiBlockInfoContainer = IMultiBlockInfoContainer.get(metaTE.getClass());
						if (metaTE instanceof IAlignment) {
							iMultiBlockInfoContainer.construct(aStack, true, metaTE, ((IAlignment) metaTE).getExtendedFacing());
						} else {
							iMultiBlockInfoContainer.construct(aStack, true, metaTE, ExtendedFacing.of(ForgeDirection.getOrientation(((IGregTechTileEntity) tTileEntity).getFrontFacing())));
						}
						impact.proxy.addClientSideChatMessages(iMultiBlockInfoContainer.getDescription(aStack));
						return false;
					}
				}
				return false;
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
			return true;
		}
		return true;
	}
}
