package com.impact.command;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;


public class Command_Run extends CommandBase implements ICommandSender {
	
	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}
	
	@Override
	public String getCommandName() {
		return "impact";
	}
	
	@Override
	public void processCommand(ICommandSender ics, String[] args) {
		
		if (args[0].isEmpty()) {
			ics.addChatMessage(
					new ChatComponentText("Use " +
							EnumChatFormatting.GREEN + "/impact panel" +
							EnumChatFormatting.RESET + " or " +
							EnumChatFormatting.GREEN + "/impact nec")
			);
		}
		try {
			if (args[0].equals("panel")) {
				Runtime.getRuntime().exec("java -jar " + Minecraft.getMinecraft().mcDataDir.getPath() + "/impact/ImpactPanel.jar");
			} else if (args[0].equals("nec")) {
				Runtime.getRuntime().exec("java -jar " + Minecraft.getMinecraft().mcDataDir.getPath() + "/impact/nec-0.3.0-9df2870-all.jar");
			}
		} catch (Exception e) {
			ics.addChatMessage(new ChatComponentText("Not Found Files"));
		}
	}
	
	@Override
	public String getCommandUsage(ICommandSender p_71518_1_) {
		return "impactPanel";
	}
	
	@Override
	public String getCommandSenderName() {
		return "client";
	}
	
	@Override
	public IChatComponent func_145748_c_() {
		return null;
	}
	
	@Override
	public void addChatMessage(IChatComponent p_145747_1_) {
	
	}
	
	@Override
	public boolean canCommandSenderUseCommand(int p_70003_1_, String p_70003_2_) {
		return true;
	}
	
	@Override
	public boolean canCommandSenderUseCommand(ICommandSender p_71519_1_) {
		return true;
	}
	
	@Override
	public ChunkCoordinates getPlayerCoordinates() {
		return null;
	}
	
	@Override
	public World getEntityWorld() {
		return null;
	}
}