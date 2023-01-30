package com.impact.core;

import com.impact.client.key.KeyBindings;
import com.impact.client.render.fx.*;
import com.impact.client.render.models.Model_DryingRack;
import com.impact.client.render.tesr.*;
import com.impact.command.Command_Run;
import com.impact.common.block.QuantumStuffRender;
import com.impact.common.block.blocks.Block_QuantumStuff;
import com.impact.common.block.blocks.Block_TheMill;
import com.impact.common.te.*;
import com.impact.events.ClientEvent;
import com.impact.mods.nei.impactplugin.RecipeProcessorLoader;
import com.impact.util.vector.Vector3ic;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import ic2.core.item.ItemFluidCell;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ClientProxy extends CommonProxy {
	
	public static Minecraft mc = FMLClientHandler.instance().getClient();
	
	public static void register_event(Object obj) {
		FMLCommonHandler.instance().bus().register(obj);
		MinecraftForge.EVENT_BUS.register(obj);
	}
	
	@Override
	public void registerRenderInfo() {
		Block_QuantumStuff.renderID = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(Block_QuantumStuff.renderID, new QuantumStuffRender());
		Block_TheMill.renderID = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(Block_TheMill.renderID, new Block_TheMill.BlockRender());
		RenderingRegistry.registerBlockHandler(new Model_DryingRack());
	
		ClientRegistry.bindTileEntitySpecialRenderer(TE_NqTether.class, new TESR_SETether());
		ClientRegistry.bindTileEntitySpecialRenderer(TE_SpaceElevatorTether.class, new TESR_SpaceElevatorTether());
		ClientRegistry.bindTileEntitySpecialRenderer(TilePlacedItem.class, new PlacedItemRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TE_WindMill.class, new TESR_WindMill());
		ClientRegistry.bindTileEntitySpecialRenderer(TE_TheMill.class, new TESR_TheMill());
		ClientRegistry.bindTileEntitySpecialRenderer(TE_DryingRack.class, new TESR_DryingRack());
		
		register_event(new ClientEvent());
	}
	
	public void addClientSideChatMessages(String... messages) {
		GuiNewChat chat = Minecraft.getMinecraft().ingameGUI.getChatGUI();
		for (String s : messages) {
			chat.printChatMessage(new ChatComponentText(s));
		}
	}
	
	@Override
	public void hint_particle(World w, int x, int y, int z, Block block, int meta) {
		Minecraft.getMinecraft().effectRenderer.addEffect(new BlockHint(w, x, y, z, block, meta));
	}
	
	@Override
	public void hint_particle(World w, int x, int y, int z, Block block, int meta, int age) {
		Minecraft.getMinecraft().effectRenderer.addEffect(new BlockHint(w, x, y, z, age, block, meta));
	}
	
	@Override
	public void remove_hint_particle(World w, int x, int y, int z) {
		try {
			Field cls = Minecraft.getMinecraft().effectRenderer.getClass().getDeclaredField("fxLayers");
			cls.setAccessible(true);
			List[] fxs = (List[]) cls.get(Minecraft.getMinecraft().effectRenderer);
			for (Object obj : fxs[1]) {
				EntityFX fx = (EntityFX) obj;
				if (fx.posX == x + 0.25D && fx.posY == y + 0.5D && fx.posZ == z + 0.25D) {
					fx.setDead();
					return;
				} else if (fx.posX == x && fx.posY == y && fx.posZ == z) {
					fx.setDead();
					return;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void hint_particle(World w, int x, int y, int z, int xx, int yy, int zz, Block block, int meta, int age) {
		Minecraft.getMinecraft().effectRenderer.addEffect(new BlockHintMega(w, x, y, z).setMax(xx, yy, zz).setAge(age));
	}
	
	@Override
	public void hint_particleMega(World w, int x, int y, int z, int xx, int yy, int zz, Color color, int age) {
		Minecraft.getMinecraft().effectRenderer.addEffect(new BlockHintMega(w, x, y, z).setMax(xx, yy, zz).setAge(age).setColor(color));
	}
	
	@Override
	public void hint_particleMega(World w, int x, int y, int z, Color color, int age) {
		Minecraft.getMinecraft().effectRenderer.addEffect(new BlockHintMega(w, x, y, z).setAge(age).setColor(color));
	}
	
	@Override
	public void beam(World worldObj, double sx, double sy, double sz, double tx, double ty, double tz, int type, int color, boolean reverse, float endmod, int age) {
		FXBeam beamcon = null;
		Color c = new Color(color);
		beamcon = new FXBeam(worldObj, sx, sy, sz, tx, ty, tz, (float) c.getRed() / 255.0F, (float) c.getGreen() / 255.0F, (float) c.getBlue() / 255.0F, age);
		beamcon.setType(type);
		beamcon.setEndMod(endmod);
		beamcon.setReverse(reverse);
		beamcon.setPulse(false);
		FMLClientHandler.instance().getClient().effectRenderer.addEffect(beamcon);
	}
	
	@Override
	public void beam(World worldObj, Vector3ic vec1, Vector3ic vec2, int type, int color, boolean reverse, float endmod, int age) {
		beam(worldObj, vec1.x() + 0.5d, vec1.y() + 0.5d, vec1.z() + 0.5d, vec2.x() + 0.5d, vec2.y() + 0.5d, vec2.z() + 0.5d, type, color, reverse, endmod, age);
	}
	
	@Override
	public Object beamCont(World worldObj, EntityPlayer p, double tx, double ty, double tz, int type, int color, boolean reverse, float endmod, Object input, int impact) {
		FXBeamWand beamcon = null;
		Color c = new Color(color);
		if (input instanceof FXBeamWand) {
			beamcon = (FXBeamWand) input;
		}
		
		if (beamcon != null && !beamcon.isDead) {
			beamcon.updateBeam(tx, ty, tz);
			beamcon.setEndMod(endmod);
			beamcon.impact = impact;
		} else {
			beamcon = new FXBeamWand(worldObj, p, tx, ty, tz, (float) c.getRed() / 255.0F, (float) c.getGreen() / 255.0F, (float) c.getBlue() / 255.0F, 8);
			beamcon.setType(type);
			beamcon.setEndMod(endmod);
			beamcon.setReverse(reverse);
			FMLClientHandler.instance().getClient().effectRenderer.addEffect(beamcon);
		}
		
		return beamcon;
	}
	
	@Override
	public Object beamBore(World worldObj, double px, double py, double pz, double tx, double ty, double tz, int type, int color, boolean reverse, float endmod, Object input, int impact) {
		FXBeamBore beamcon = null;
		Color c = new Color(color);
		if (input instanceof FXBeamBore) {
			beamcon = (FXBeamBore) input;
		}
		
		if (beamcon != null && !beamcon.isDead) {
			beamcon.updateBeam(tx, ty, tz);
			beamcon.setEndMod(endmod);
			beamcon.impact = impact;
		} else {
			beamcon = new FXBeamBore(worldObj, px, py, pz, tx, ty, tz, (float) c.getRed() / 255.0F, (float) c.getGreen() / 255.0F, (float) c.getBlue() / 255.0F, 8);
			beamcon.setType(type);
			beamcon.setEndMod(endmod);
			beamcon.setReverse(reverse);
			FMLClientHandler.instance().getClient().effectRenderer.addEffect(beamcon);
		}
		
		return beamcon;
	}
	
	@Override
	public void nodeBolt(World worldObj, float x, float y, float z, Entity targetedEntity) {
		FXLightningBolt bolt = new FXLightningBolt(worldObj, x, y, z, targetedEntity.posX, targetedEntity.posY + 1, targetedEntity.posZ, worldObj.rand.nextLong(), 5, 1.0F, 8);
		bolt.defaultFractal();
		bolt.setType(0);
		bolt.finalizeBolt();
	}
	
	@Override
	public void nodeBolt(World world, int tX, int tY, int tZ, int tXN, int tYN, int tZN) {
		FXLightningBolt bolt = new FXLightningBolt(world, tX + 0.5F, tY + 0.5F, tZ + 0.5F, tX + tXN + 0.5F, tY + tYN + 0.5F, tZ + tZN + 0.5F, world.rand.nextLong(), 5, 10F, 1);
		bolt.defaultFractal();
		bolt.setType(0);
		bolt.finalizeBolt();
	}
	
	@Override
	public void nodeBolt(World world, int tX, int tY, int tZ, int tXN, int tYN, int tZN, int duration, float multi, int speed) {
		FXLightningBolt bolt = new FXLightningBolt(world, tX + 0.5F, tY + 0.5F, tZ + 0.5F, tX + tXN + 0.5F, tY + tYN + 0.5F, tZ + tZN + 0.5F, world.rand.nextLong(), duration, multi, speed);
		bolt.defaultFractal();
		bolt.setType(0);
		bolt.finalizeBolt();
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}
	
	@Override
	public World getClientWorld() {
		return FMLClientHandler.instance().getClient().theWorld;
	}
	
	public void postInit() {
		ClientCommandHandler.instance.registerCommand(new Command_Run());
		RecipeProcessorLoader.init();
	}
	
	public void init() {
	}
	
	public void preInit() {
		KeyBindings.init();
	}
	
	@Override
	public void addChatFromServer(String text) {}
	
	@Override
	public void onLoadComplete(FMLLoadCompleteEvent event) {
		ArrayList<ItemStack> itemList = new ArrayList<>();
		for (Fluid fluid : FluidRegistry.getRegisteredFluids().values()) {
			if (fluid == null) continue;
			itemList.add(ItemFluidCell.getUniversalFluidCell(new FluidStack(fluid, 2147483647)));
		}
		for (ItemStack aCell : itemList) {
			codechicken.nei.api.API.hideItem(aCell);
		}
	}
}