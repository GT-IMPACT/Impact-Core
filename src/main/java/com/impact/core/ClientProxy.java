package com.impact.core;

import com.impact.client.key.KeyBindings;
import com.impact.client.render.BlockHint;
import com.impact.client.render.PlacedItemRenderer;
import com.impact.client.render.TESR_SETether;
import com.impact.client.render.TESR_SpaceElevatorTether;
import com.impact.client.render.fx.FXBeam;
import com.impact.client.render.fx.FXBeamBore;
import com.impact.client.render.fx.FXBeamWand;
import com.impact.common.block.QuantumStuffRender;
import com.impact.common.block.blocks.Block_QuantumStuff;
import com.impact.common.te.TE_NqTether;
import com.impact.common.te.TE_SpaceElevatorTether;
import com.impact.common.te.TilePlacedItem;
import com.impact.events.ClientEvent;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

import java.awt.*;

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
    ClientRegistry.bindTileEntitySpecialRenderer(TE_NqTether.class, new TESR_SETether());
    ClientRegistry.bindTileEntitySpecialRenderer(TE_SpaceElevatorTether.class, new TESR_SpaceElevatorTether());
    ClientRegistry.bindTileEntitySpecialRenderer(TilePlacedItem.class, new PlacedItemRenderer());
    register_event(new ClientEvent());
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
  public void hint_particle(World w, int x, int y, int z, double xx, double yy, double zz, Block block, int meta, int age) {
    Minecraft.getMinecraft().effectRenderer.addEffect(new BlockHint(w, x, y, z, xx, yy, zz, age, block, meta));
  }

  @Override
  public void beam(World worldObj, double sx, double sy, double sz, double tx, double ty, double tz, int type, int color, boolean reverse, float endmod, int age) {
    FXBeam beamcon = null;
    Color c = new Color(color);
    beamcon = new FXBeam(worldObj, sx, sy, sz, tx, ty, tz, (float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, age);
    beamcon.setType(type);
    beamcon.setEndMod(endmod);
    beamcon.setReverse(reverse);
    beamcon.setPulse(false);
    FMLClientHandler.instance().getClient().effectRenderer.addEffect(beamcon);
  }

  @Override
  public Object beamCont(World worldObj, EntityPlayer p, double tx, double ty, double tz, int type, int color, boolean reverse, float endmod, Object input, int impact) {
    FXBeamWand beamcon = null;
    Color c = new Color(color);
    if (input instanceof FXBeamWand) {
      beamcon = (FXBeamWand)input;
    }

    if (beamcon != null && !beamcon.isDead) {
      beamcon.updateBeam(tx, ty, tz);
      beamcon.setEndMod(endmod);
      beamcon.impact = impact;
    } else {
      beamcon = new FXBeamWand(worldObj, p, tx, ty, tz, (float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, 8);
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
      beamcon = (FXBeamBore)input;
    }

    if (beamcon != null && !beamcon.isDead) {
      beamcon.updateBeam(tx, ty, tz);
      beamcon.setEndMod(endmod);
      beamcon.impact = impact;
    } else {
      beamcon = new FXBeamBore(worldObj, px, py, pz, tx, ty, tz, (float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, 8);
      beamcon.setType(type);
      beamcon.setEndMod(endmod);
      beamcon.setReverse(reverse);
      FMLClientHandler.instance().getClient().effectRenderer.addEffect(beamcon);
    }

    return beamcon;
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
  }

  public void init() {
  }

  public void preInit() {
    KeyBindings.init();
  }
}
