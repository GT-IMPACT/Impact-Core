package com.impact.core;

import com.impact.impact;
import com.impact.api.satellite.SatelliteNetworkManager;
import com.impact.util.files.JsonWorld;
import com.impact.util.vector.Vector3ic;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.MinecraftForge;

import java.awt.*;

public class CommonProxy implements IGuiHandler {
	
	public static void register_event(Object obj) {
		FMLCommonHandler.instance().bus().register(obj);
		MinecraftForge.EVENT_BUS.register(obj);
	}
	
	public void addTexturePage() {
	}
	
	public void registerRenderInfo() {
	}
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}
	
	public World getClientWorld() {
		return null;
	}
	
	public void registerRenderers() {
	}
	
	public void preInit() {
	}
	
	public void init() {
	}
	
	public void postInit() {
	}
	
	public void addClientSideChatMessages(String... messages) {
	}
	
	public void hint_particle(World w, int x, int y, int z, Block block, int meta) {
	}
	
	public void hint_particle(World w, int x, int y, int z, Block block, int meta, int age) {
	}
	
	public void remove_hint_particle(World w, int x, int y, int z) {
	}
	
	public void hint_particle(World w, int x, int y, int z, int xx, int yy, int zz, Block block, int meta, int age) {
	}
	
	public void hint_particleMega(World w, int x, int y, int z, int xx, int yy, int zz, Color color, int age) {
	}
	
	public void hint_particleMega(World w, int x, int y, int z, Color color, int age) {
	}
	
	public void beam(World worldObj, double sx, double sy, double sz, double tx, double ty, double tz, int type, int color, boolean reverse, float endmod, int age) {
	}
	
	public void beam(World worldObj, Vector3ic vec1, Vector3ic vec2, int type, int color, boolean reverse, float endmod, int age) {
	}
	
	public Object beamBore(World worldObj, double px, double py, double pz, double tx, double ty, double tz, int type, int color, boolean reverse, float endmod, Object input, int impact) {
		return null;
	}
	
	public Object beamCont(World worldObj, EntityPlayer p, double tx, double ty, double tz, int type, int color, boolean reverse, float endmod, Object input, int impact) {
		return null;
	}
	
	public void nodeBolt(World worldObj, float x, float y, float z, Entity targetedEntity) {
	}
	
	public void nodeBolt(World world, int tX, int tY, int tZ, int tXN, int tYN, int tZN) {
	}
	
	public void nodeBolt(World world, int tX, int tY, int tZ, int tXN, int tYN, int tZN, int duration, float multi, int speed) {
	}
	
	public String getUUID(String name) {
		for (WorldServer worldServer : MinecraftServer.getServer().worldServers) {
			for (Object o : worldServer.playerEntities) {
				if (o instanceof EntityPlayer && ((EntityPlayer) o).getGameProfile().getName().equals(name)) {
					return ((EntityPlayer) o).getGameProfile().getId().toString();
				}
			}
		}
		return null;
	}
	
	public boolean isOnlineName(String name) {
		for (WorldServer worldServer : MinecraftServer.getServer().worldServers) {
			for (Object o : worldServer.playerEntities) {
				if (o instanceof EntityPlayer && ((EntityPlayer) o).getGameProfile().getName().equals(name)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean isOnlineUUID(String uuid) {
		for (WorldServer worldServer : MinecraftServer.getServer().worldServers) {
			for (Object o : worldServer.playerEntities) {
				if (o instanceof EntityPlayer && ((EntityPlayer) o).getGameProfile().getId().toString().equals(uuid)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public void onServerStarted() {
		Impact_API.sSpaceSatellite.clear();
		Impact_API.sElevatorSpace.clear();
		Impact_API.sCommunicationTower.clear();
		Impact_API.sAerostat.clear();
		Impact_API.regionsOres.clear();
		SatelliteNetworkManager.INSTANCE.reload();
		JsonWorld.load();
	}
	
	public void onServerStopping() {
		JsonWorld.save();
		Impact_API.sSpaceSatellite.clear();
		Impact_API.sElevatorSpace.clear();
		Impact_API.sCommunicationTower.clear();
		Impact_API.sAerostat.clear();
		Impact_API.regionsOres.clear();
		SatelliteNetworkManager.INSTANCE.reload();
	}
	
	public void addChatFromServer(String text) {
		IChatComponent c = new ChatComponentText(text);
		impact.getServer().getConfigurationManager().sendChatMsgImpl(c, true);
	}
	
	public void onLoadComplete(FMLLoadCompleteEvent event) {
	}
}