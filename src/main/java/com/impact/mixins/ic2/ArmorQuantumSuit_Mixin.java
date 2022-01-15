package com.impact.mixins.ic2;

import ic2.api.item.ElectricItem;
import ic2.core.IC2;
import ic2.core.audio.PositionSpec;
import ic2.core.init.MainConfig;
import ic2.core.item.armor.ItemArmorElectric;
import ic2.core.item.armor.ItemArmorQuantumSuit;
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
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.LinkedList;
import java.util.Map;

import static ic2.core.item.armor.ItemArmorQuantumSuit.audioSource;

@Mixin(ItemArmorQuantumSuit.class)
public abstract class ArmorQuantumSuit_Mixin extends ItemArmorElectric {
	
	@Final
	@Shadow(remap = false)
	protected static Map<Integer, Integer> potionRemovalCost;
	@Shadow(remap = false)
	private static boolean lastJetpackUsed = false;
	@Shadow(remap = false)
	private float jumpCharge;
	
	public ArmorQuantumSuit_Mixin() {
		super(null, null, 0, 0, 0, 0);
	}
	
	@Override
	public double getMaxCharge(ItemStack itemStack) {
		return 2.8E8D;
	}
	
	@Override
	public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
		if (source == DamageSource.fall && this.armorType == 3) {
			final int energyPerDamage = player.worldObj.provider.dimensionId == 0 ? this.getEnergyPerDamage() : 100;
			int damageLimit = Integer.MAX_VALUE;
			if (energyPerDamage > 0) {
				damageLimit = (int) Math.min(damageLimit, 25.0 * ElectricItem.manager.getCharge(armor) / energyPerDamage);
			}
			return new ISpecialArmor.ArmorProperties(10, 1.0, damageLimit);
		}
		return super.getProperties(player, armor, source, damage, slot);
	}
	
	@Override
	public void onArmorTick(final World world, final EntityPlayer player, final ItemStack itemStack) {
		final NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(itemStack);
		byte toggleTimer = nbtData.getByte("toggleTimer");
		boolean ret = false;
		switch (this.armorType) {
			case 0: {
				IC2.platform.profilerStartSection("QuantumHelmet");
				final int air = player.getAir();
				if (ElectricItem.manager.canUse(itemStack, 1000.0) && air < 100) {
					player.setAir(air + 200);
					ElectricItem.manager.use(itemStack, 1000.0, null);
					ret = true;
				} else if (air <= 0) {
					IC2.achievements.issueAchievement(player, "starveWithQHelmet");
				}
				if (ElectricItem.manager.canUse(itemStack, 10.0) && player.getFoodStats().getFoodLevel() < 20) {
					player.getFoodStats().addStats(20, 5.0F);
					ElectricItem.manager.use(itemStack, 10, null);
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
				IC2.platform.profilerEndSection();
				break;
			}
			case 1: {
				IC2.platform.profilerStartSection("QuantumBodyarmor");
				boolean jetpack = nbtData.getBoolean("jetpack");
				boolean hoverMode = nbtData.getBoolean("hoverMode");
				boolean jetpackUsed = false;
				if (IC2.keyboard.isJumpKeyDown(player) && IC2.keyboard.isModeSwitchKeyDown(player) && toggleTimer == 0) {
					toggleTimer = 10;
					hoverMode   = !hoverMode;
					if (IC2.platform.isSimulating()) {
						nbtData.setBoolean("hoverMode", hoverMode);
						if (hoverMode) {
							IC2.platform.messagePlayer(player, "Quantum Hover Mode enabled.");
						} else {
							IC2.platform.messagePlayer(player, "Quantum Hover Mode disabled.");
						}
					}
				}
				if (IC2.keyboard.isBoostKeyDown(player) && IC2.keyboard.isModeSwitchKeyDown(player) && toggleTimer == 0) {
					toggleTimer = 10;
					jetpack     = !jetpack;
					if (IC2.platform.isSimulating()) {
						nbtData.setBoolean("jetpack", jetpack);
						if (jetpack) {
							IC2.platform.messagePlayer(player, "Quantum Jetpack enabled.");
						} else {
							IC2.platform.messagePlayer(player, "Quantum Jetpack disabled.");
						}
					}
				}
				if (jetpack && (IC2.keyboard.isJumpKeyDown(player) || (hoverMode && player.motionY < -0.029999999329447746))) {
					jetpackUsed = this.useJetpack(player, hoverMode);
				}
				if (IC2.platform.isSimulating() && toggleTimer > 0) {
					--toggleTimer;
					nbtData.setByte("toggleTimer", toggleTimer);
				}
				if (IC2.platform.isRendering() && player == IC2.platform.getPlayerInstance()) {
					if (lastJetpackUsed != jetpackUsed) {
						if (jetpackUsed) {
							if (audioSource == null) {
								audioSource = IC2.audioManager.createSource(player, PositionSpec.Backpack, "Tools/Jetpack/JetpackLoop.ogg", true, false, IC2.audioManager.getDefaultVolume());
							}
							if (audioSource != null) {
								audioSource.play();
							}
						} else if (audioSource != null) {
							audioSource.remove();
							audioSource = null;
						}
						lastJetpackUsed = jetpackUsed;
					}
					if (audioSource != null) {
						audioSource.updatePosition();
					}
				}
				ret = jetpackUsed;
				player.extinguish();
				IC2.platform.profilerEndSection();
				break;
			}
			case 2: {
				IC2.platform.profilerStartSection("QuantumLeggings");
				final boolean enableQuantumSpeedOnSprint = !IC2.platform.isRendering() || ConfigUtil.getBool(MainConfig.get(), "misc/quantumSpeedOnSprint");
				if (ElectricItem.manager.canUse(itemStack, 150.0) && IC2.keyboard.isForwardKeyDown(player) && IC2.keyboard.isBoostKeyDown(player)) {
					player.stepHeight = 3.1f;
					ElectricItem.manager.use(itemStack, 150.0, null);
				} else player.stepHeight = 0.5f;
				if (ElectricItem.manager.canUse(itemStack, 1000.0) && (player.onGround || player.isInWater()) && IC2.keyboard.isForwardKeyDown(player) && ((enableQuantumSpeedOnSprint && player.isSprinting()) || (!enableQuantumSpeedOnSprint && IC2.keyboard.isBoostKeyDown(player)))) {
					byte speedTicker = nbtData.getByte("speedTicker");
					++speedTicker;
					if (speedTicker >= 10) {
						speedTicker = 0;
						ElectricItem.manager.use(itemStack, 1000.0, null);
						ret = true;
					}
					nbtData.setByte("speedTicker", speedTicker);
					float speed = 0.30f;
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
				IC2.platform.profilerStartSection("QuantumBoots");
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
					if (ElectricItem.manager.canUse(itemStack, 4000.0) && player.onGround) {
						this.jumpCharge = 1.0f;
					}
					if (player.motionY >= 0.0 && this.jumpCharge > 0.0f && !player.isInWater()) {
						if (IC2.keyboard.isJumpKeyDown(player) && IC2.keyboard.isBoostKeyDown(player)) {
							if (this.jumpCharge == 1.0f) {
								player.motionX *= 3.5;
								player.motionZ *= 3.5;
							}
							player.motionY += this.jumpCharge * 0.3f;
							this.jumpCharge *= 0.75;
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
	
	@Inject(method = "onEntityLivingFallEvent", at = @At("HEAD"), remap = false, cancellable = true)
	public void onEntityLivingFallEvent(LivingFallEvent event, CallbackInfo ci) {
		if (IC2.platform.isSimulating() && event.entity instanceof EntityLivingBase) {
			final EntityLivingBase entity = (EntityLivingBase) event.entity;
			final ItemStack armor = entity.getEquipmentInSlot(1);
			if (armor != null && armor.getItem() == this) {
				final int fallDamage = Math.max((int) event.distance - 10, 0);
				final double energyCost = entity.worldObj.provider.dimensionId == 0 ? this.getEnergyPerDamage() * fallDamage : 100 * fallDamage;
				if (energyCost <= ElectricItem.manager.getCharge(armor)) {
					ElectricItem.manager.discharge(armor, energyCost, Integer.MAX_VALUE, true, false, false);
					event.setCanceled(true);
				}
			}
		}
		ci.cancel();
	}
	
	@Shadow(remap = false)
	public boolean useJetpack(final EntityPlayer player, final boolean hoverMode) {
		throw new IllegalStateException("Mixin failed to shadow");
	}
}
