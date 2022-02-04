package com.impact.mods.gregtech.tileentities.basic.ae.craftsup;

import appeng.api.AEApi;
import appeng.api.networking.IGrid;
import appeng.api.networking.crafting.*;
import appeng.api.networking.security.BaseActionSource;
import appeng.api.storage.data.IAEItemStack;
import appeng.helpers.NonNullArrayIterator;
import com.google.common.collect.ImmutableSet;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.concurrent.Future;

public class CraftingHandler {
	private final int size;
	private final ICraftingRequester machine;
	private Future<ICraftingJob>[] jobs = null;
	private ICraftingLink[] links = null;
	
	public CraftingHandler(ICraftingRequester machine, int size) {
		this.size    = size;
		this.machine = machine;
	}
	
	public void readFromNBT(NBTTagCompound extra) {
		for (int x = 0; x < this.size; ++x) {
			NBTTagCompound link = extra.getCompoundTag("links-" + x);
			if (link != null && !link.hasNoTags()) {
				this.setLink(x, AEApi.instance().storage().loadCraftingLink(link, this.machine));
			}
		}
	}
	
	public void writeToNBT(NBTTagCompound extra) {
		for (int x = 0; x < this.size; ++x) {
			ICraftingLink link = this.getLink(x);
			if (link != null) {
				NBTTagCompound ln = new NBTTagCompound();
				link.writeToNBT(ln);
				extra.setTag("links-" + x, ln);
			}
		}
	}
	
	public boolean handleCrafting(int x, long itemToCraft, IAEItemStack ais, World w, IGrid g, ICraftingGrid cg, BaseActionSource mySrc) {
		if (ais != null) {
			Future<ICraftingJob> craftingJob = this.getJob(x);
			if (this.getLink(x) != null) {
				return false;
			}
			
			if (craftingJob != null) {
				try {
					ICraftingJob job = null;
					if (craftingJob.isDone()) {
						job = craftingJob.get();
					}
					
					if (job != null) {
						ICraftingLink link = cg.submitJob(job, this.machine, null, false, mySrc);
						this.setJob(x, null);
						if (link != null) {
							this.setLink(x, link);
							return true;
						}
					}
				} catch (Exception ignored) {
				}
			} else if (this.getLink(x) == null) {
				IAEItemStack aisC = ais.copy();
				aisC.setStackSize(itemToCraft);
				this.setJob(x, cg.beginCraftingJob(w, g, mySrc, aisC, null));
			}
		}
		
		return false;
	}
	
	public ImmutableSet<ICraftingLink> getRequestedJobs() {
		return this.links == null ? ImmutableSet.of() : ImmutableSet.copyOf(new NonNullArrayIterator(this.links));
	}
	
	public int jobStateChange(ICraftingLink link) {
		if (this.links != null) {
			for (int x = 0; x < this.links.length; ++x) {
				if (this.links[x] == link) {
					this.setLink(x, null);
					return x;
				}
			}
		}
		
		return -1;
	}
	
	int getSlot(ICraftingLink link) {
		if (this.links != null) {
			for (int x = 0; x < this.links.length; ++x) {
				if (this.links[x] == link) {
					return x;
				}
			}
		}
		return -1;
	}
	
	void cancel() {
		int var2;
		int var3;
		if (this.links != null) {
			ICraftingLink[] var1 = this.links;
			var2 = var1.length;
			
			for (var3 = 0; var3 < var2; ++var3) {
				ICraftingLink l = var1[var3];
				if (l != null) {
					l.cancel();
				}
			}
			this.links = null;
		}
		
		if (this.jobs != null) {
			Future<ICraftingJob>[] var5 = this.jobs;
			var2 = var5.length;
			
			for (var3 = 0; var3 < var2; ++var3) {
				Future<ICraftingJob> l = var5[var3];
				if (l != null) {
					l.cancel(true);
				}
			}
			
			this.jobs = null;
		}
		
	}
	
	boolean isBusy(int slot) {
		return this.getLink(slot) != null || this.getJob(slot) != null;
	}
	
	private ICraftingLink getLink(int slot) {
		return this.links == null ? null : this.links[slot];
	}
	
	private void setLink(int slot, ICraftingLink l) {
		if (this.links == null) {
			this.links = new ICraftingLink[this.size];
		}
		
		this.links[slot] = l;
		boolean hasStuff = false;
		
		for (int x = 0; x < this.links.length; ++x) {
			ICraftingLink g = this.links[x];
			if (g != null && !g.isCanceled() && !g.isDone()) {
				hasStuff = true;
			} else {
				this.links[x] = null;
			}
		}
		
		if (!hasStuff) {
			this.links = null;
		}
		
	}
	
	private Future<ICraftingJob> getJob(int slot) {
		return this.jobs == null ? null : this.jobs[slot];
	}
	
	private void setJob(int slot, Future<ICraftingJob> l) {
		if (this.jobs == null) {
			this.jobs = new Future[this.size];
		}
		
		this.jobs[slot] = l;
		boolean hasStuff = false;
		Future<ICraftingJob>[] var4 = this.jobs;
		int var5 = var4.length;
		
		for (Future<ICraftingJob> job : var4) {
			if (job != null) {
				hasStuff = true;
				break;
			}
		}
		
		if (!hasStuff) {
			this.jobs = null;
		}
		
	}
}