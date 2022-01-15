package com.impact.mixins.ic2;

import ic2.api.item.ElectricItem;
import ic2.core.IC2;
import ic2.core.IC2Potion;
import ic2.core.Ic2Items;
import ic2.core.init.MainConfig;
import ic2.core.item.armor.ItemArmorElectric;
import ic2.core.item.armor.ItemArmorNanoSuit;
import ic2.core.util.ConfigUtil;
import ic2.core.util.StackUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

@Mixin(ItemArmorNanoSuit.class)
public abstract class ArmorNanoSuit_Mixin extends ItemArmorElectric {
	
	private static final Map<Integer, Integer> potionRemovalCost = new HashMap<>();

	static {
		potionRemovalCost.put(Potion.poison.id, 10000);
		potionRemovalCost.put(IC2Potion.radiation.id, 10000);
		potionRemovalCost.put(Potion.wither.id, 25000);
	}
	;
	
	private float jumpCharge;
	
	public ArmorNanoSuit_Mixin() {
		super(null, null, 0, 0, 0, 0);
	}
	
	@Override
	public double getMaxCharge(ItemStack itemStack) {
		return 5E6D;
	}
	
	@Override
	public ISpecialArmor.ArmorProperties getProperties(final EntityLivingBase player, final ItemStack armor, final DamageSource source, final double damage, final int slot) {
		if (source == DamageSource.fall && this.armorType == 3) {
			final int energyPerDamage = player.worldObj.provider.dimensionId == 0 ? this.getEnergyPerDamage() : 100;
			int damageLimit = Integer.MAX_VALUE;
			if (energyPerDamage > 0) {
				damageLimit = (int) Math.min(damageLimit, 25.0 * ElectricItem.manager.getCharge(armor) / energyPerDamage);
			}
			return new ISpecialArmor.ArmorProperties(10, (damage < 8.0) ? 1.0 : 0.875, damageLimit);
		}
		return super.getProperties(player, armor, source, damage, slot);
	}
	
	@Inject(method = "onEntityLivingFallEvent", at = @At("HEAD"), remap = false, cancellable = true)
	public void onEntityLivingFallEvent(LivingFallEvent event, CallbackInfo ci) {
		if (IC2.platform.isSimulating() && event.entity instanceof EntityLivingBase) {
			final EntityLivingBase entity = (EntityLivingBase) event.entity;
			final ItemStack armor = entity.getEquipmentInSlot(1);
			if (armor != null && armor.getItem() == this) {
				final int fallDamage = (int) event.distance - 3;
				if (fallDamage >= 8) {
					ci.cancel();
				}
				final double energyCost = entity.worldObj.provider.dimensionId == 0 ? this.getEnergyPerDamage() * fallDamage : 100 * fallDamage;
				if (energyCost <= ElectricItem.manager.getCharge(armor)) {
					ElectricItem.manager.discharge(armor, energyCost, Integer.MAX_VALUE, true, false, false);
					event.setCanceled(true);
				}
			}
		}
		ci.cancel();
	}
	
	@Override
	public void onArmorTick(final World world, final EntityPlayer player, final ItemStack itemStack) {
		final NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(itemStack);
		byte toggleTimer = nbtData.getByte("toggleTimer");
		boolean ret = false;
		switch (this.armorType) {
			case 0: {
				IC2.platform.profilerStartSection("NanoHelmet");
				if (player.getAir() <= 100 && player.inventory.hasItemStack(Ic2Items.airCell)) {
					StackUtil.consumeInventoryItem(player, Ic2Items.airCell);
					player.inventory.addItemStackToInventory(new ItemStack(Ic2Items.cell.getItem()));
					player.setAir(player.getAir() + 150);
					ret = true;
				}
				boolean Nightvision = nbtData.getBoolean("Nightvision");
				short hubmode = nbtData.getShort("HudMode");
				if (IC2.keyboard.isAltKeyDown(player) && IC2.keyboard.isModeSwitchKeyDown(player) && toggleTimer == 0) {
					toggleTimer = 10;
					Nightvision = !Nightvision;
					if (IC2.platform.isSimulating()) {
						nbtData.setBoolean("Nightvision", Nightvision);
						if (Nightvision) {
							IC2.platform.messagePlayer(player, "Nightvision enabled.");
						} else {
							IC2.platform.messagePlayer(player, "Nightvision disabled.");
						}
					}
				}
				if (IC2.keyboard.isAltKeyDown(player) && IC2.keyboard.isHudModeKeyDown(player) && toggleTimer == 0) {
					toggleTimer = 10;
					if (hubmode == 2) {
						hubmode = 0;
					} else {
						++hubmode;
					}
					if (IC2.platform.isSimulating()) {
						nbtData.setShort("HudMode", hubmode);
						switch (hubmode) {
							case 0: {
								IC2.platform.messagePlayer(player, "HUD disabled.");
								break;
							}
							case 1: {
								IC2.platform.messagePlayer(player, "HUD (basic) enabled.");
								break;
							}
							case 2: {
								IC2.platform.messagePlayer(player, "HUD (extended) enabled");
								break;
							}
						}
					}
				}
				if (IC2.platform.isSimulating() && toggleTimer > 0) {
					--toggleTimer;
					nbtData.setByte("toggleTimer", toggleTimer);
				}
				if (Nightvision && IC2.platform.isSimulating() && ElectricItem.manager.use(itemStack, 1.0, player)) {
					final int skylight = 1;
					if (skylight > 8) {
						IC2.platform.removePotion(player, Potion.nightVision.id);
						player.addPotionEffect(new PotionEffect(Potion.blindness.id, 100, 0, true));
					} else {
						IC2.platform.removePotion(player, Potion.blindness.id);
						player.addPotionEffect(new PotionEffect(Potion.nightVision.id, 300, 0, true));
					}
					ret = true;
				}
				for (final PotionEffect effect : new LinkedList<PotionEffect>(player.getActivePotionEffects())) {
					final int id = effect.getPotionID();
					Integer cost = potionRemovalCost.get(id);
					if (cost != null) {
						cost *= effect.getAmplifier() + 1;
						if (!ElectricItem.manager.canUse(itemStack, cost)) {
							continue;
						}
						ElectricItem.manager.use(itemStack, 0, null);
						IC2.platform.removePotion(player, id);
					}
				}
				if (ElectricItem.manager.canUse(itemStack, 10.0) && player.getFoodStats().getFoodLevel() < 20) {
					player.getFoodStats().addStats(20, 5.0F);
					ElectricItem.manager.use(itemStack, 10, null);
				}
				IC2.platform.profilerEndSection();
				break;
			}
			case 2: {
				IC2.platform.profilerStartSection("NanoLeggings");
				if (ElectricItem.manager.canUse(itemStack, 50.0) && IC2.keyboard.isForwardKeyDown(player) && IC2.keyboard.isBoostKeyDown(player)) {
					player.stepHeight = 1.1f;
					ElectricItem.manager.use(itemStack, 50.0, null);
				} else player.stepHeight = 0.5f;
				final boolean enableQuantumSpeedOnSprint = !IC2.platform.isRendering() || ConfigUtil.getBool(MainConfig.get(), "misc/quantumSpeedOnSprint");
				if (ElectricItem.manager.canUse(itemStack, 100.0) && (player.onGround || player.isInWater()) && IC2.keyboard.isForwardKeyDown(player) && ((enableQuantumSpeedOnSprint && player.isSprinting()) || (!enableQuantumSpeedOnSprint && IC2.keyboard.isBoostKeyDown(player)))) {
					byte speedTicker = nbtData.getByte("speedTicker");
					++speedTicker;
					if (speedTicker >= 10) {
						speedTicker = 0;
						ElectricItem.manager.use(itemStack, 100.0, null);
						ret = true;
					}
					nbtData.setByte("speedTicker", speedTicker);
					float speed = 0.15f;
					if (player.isInWater()) {
						speed = 0.1f;
						if (player.motionY < 0.0D) {
							player.motionY *= 0.2D;
						}
					}
					player.moveFlying(0.0f, 1.0f, speed);
				}
				
				
				IC2.platform.profilerEndSection();
				break;
			}
			case 3: {
				IC2.platform.profilerStartSection("NanoBoots");
				if (IC2.platform.isSimulating()) {
					final boolean wasOnGround = !nbtData.hasKey("wasOnGround") || nbtData.getBoolean("wasOnGround");
					if (wasOnGround && !player.onGround && IC2.keyboard.isJumpKeyDown(player) && IC2.keyboard.isBoostKeyDown(player)) {
						ElectricItem.manager.use(itemStack, 4000.0, null);
						ret = true;
					}
					if (player.onGround != wasOnGround) {
						nbtData.setBoolean("wasOnGround", player.onGround);
					}
				} else {
					if (ElectricItem.manager.canUse(itemStack, 50.0) && player.onGround) {
						this.jumpCharge = 1.0f;
					}
					if (player.motionY >= 0.0 && this.jumpCharge > 0.0f && !player.isInWater()) {
						if (IC2.keyboard.isJumpKeyDown(player) && IC2.keyboard.isBoostKeyDown(player)) {
							if (this.jumpCharge == 1.0f) {
								player.motionX *= 3.5;
								player.motionZ *= 3.5;
							}
							player.motionY += this.jumpCharge * 0.25f;
							this.jumpCharge *= 0.25;
						} else if (this.jumpCharge < 1.0f) {
							this.jumpCharge = 0.0f;
						}
					}
				}
				IC2.platform.profilerEndSection();
				break;
			}
		}
		if (ret) {
			player.inventoryContainer.detectAndSendChanges();
		}
	}
	
}
