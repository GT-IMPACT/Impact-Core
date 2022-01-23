package com.impact.util.vector;

import com.impact.mods.gregtech.tileentities.hatches.lasers.GTMTE_LaserEnergy_In;
import com.impact.mods.gregtech.tileentities.hatches.lasers.GTMTE_LaserEnergy_Reflector;
import com.impact.network.special.ToClient_LaserPush;
import com.impact.util.PositionObject;
import cpw.mods.fml.common.Loader;
import gregtech.api.GregTech_API;
import gregtech.api.enums.Dyes;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.util.GT_Utility;
import mods.railcraft.common.blocks.hidden.BlockHidden;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

import java.awt.*;

public class LaserPath {
	
	public static boolean laserPath(IGregTechTileEntity te, GTMTE_LaserEnergy_Reflector thisReflector, int range) {
		byte color = te.getColorization();
		if (color < 0) return false;
		byte front = te.getFrontFacing();
		MetaTileEntity mte = (MetaTileEntity) te.getMetaTileEntity();
		
		for (short dist = 1; dist < range; dist++) {
			Block block = te.getBlockAtSideAndDistance(front, dist);
			
			if (block == Blocks.air) continue;
			if (Loader.isModLoaded("Railcraft") && block instanceof BlockHidden) continue;
			if (block != GregTech_API.sBlockMachines) return false;
		
			IGregTechTileEntity ReflectorOrLaserIn = te.getIGregTechTileEntityAtSideAndDistance(front, dist);
			
			if (ReflectorOrLaserIn != null && ReflectorOrLaserIn.getColorization() == color) {
				IMetaTileEntity metaReflectorOrLaserIn = ReflectorOrLaserIn.getMetaTileEntity();
				if (metaReflectorOrLaserIn == null) return false;
				if (metaReflectorOrLaserIn instanceof GTMTE_LaserEnergy_Reflector) {
					
					GTMTE_LaserEnergy_Reflector reflector = (GTMTE_LaserEnergy_Reflector) metaReflectorOrLaserIn;
					
					if (thisReflector != null) {
						if (!reflector.canConnect(thisReflector.sideOut())) {
							return false;
						}
					} else {
						if (!reflector.canConnect(front)) return false;
					}
					
					if (mte.maxEUOutput() > reflector.maxEUInput()) {
						reflector.doExplosion(mte.maxEUOutput());
						mte.setEUVar(te.getStoredEU() - mte.maxEUOutput());
						return false;
						
					} else if (mte.maxEUOutput() == reflector.maxEUInput()) {
						long diff = Math.min(mte.maxAmperesOut() * 20L * mte.maxEUOutput(), Math.min(reflector.maxEUStore() - reflector.getBaseMetaTileEntity().getStoredEU(), te.getStoredEU()));
						mte.setEUVar(te.getStoredEU() - diff);
						reflector.setEUVar(reflector.getBaseMetaTileEntity().getStoredEU() + diff);
						short[] c = Dyes.get(color).mRGBa;
						int colorHash = new Color(c[0], c[1], c[2]).hashCode();
						new ToClient_LaserPush(te.getWorld().provider.dimensionId, new PositionObject(te).toVec3i(), new PositionObject(ReflectorOrLaserIn).toVec3i(), colorHash, 0).sendToClients();
						return reflector.pushLaser();
						
					}
				} else if (metaReflectorOrLaserIn instanceof GTMTE_LaserEnergy_In) {
					GTMTE_LaserEnergy_In laserEnergyIn = (GTMTE_LaserEnergy_In) metaReflectorOrLaserIn;
					
					if (!laserEnergyIn.canConnect(GT_Utility.getOppositeSide(front))) return false;
					
					if (mte.maxEUOutput() > laserEnergyIn.maxEUInput()) {
						laserEnergyIn.doExplosion(mte.maxEUOutput());
						mte.setEUVar(te.getStoredEU() - mte.maxEUOutput());
						return false;
						
					} else if (mte.maxEUOutput() == laserEnergyIn.maxEUInput()) {
						long diff = Math.min(mte.maxAmperesOut() * 20L * mte.maxEUOutput(), Math.min(laserEnergyIn.maxEUStore() - laserEnergyIn.getBaseMetaTileEntity().getStoredEU(), te.getStoredEU()));
						mte.setEUVar(te.getStoredEU() - diff);
						laserEnergyIn.setEUVar(laserEnergyIn.getBaseMetaTileEntity().getStoredEU() + diff);
						short[] c = Dyes.get(color).mRGBa;
						int colorHash = new Color(c[0], c[1], c[2]).hashCode();
						new ToClient_LaserPush(te.getWorld().provider.dimensionId, new PositionObject(te).toVec3i(), new PositionObject(ReflectorOrLaserIn).toVec3i(), colorHash, 0).sendToClients();
						return true;
					}
					return false;
				} else return false;
			} else return false;
		}
		return false;
	}
}
