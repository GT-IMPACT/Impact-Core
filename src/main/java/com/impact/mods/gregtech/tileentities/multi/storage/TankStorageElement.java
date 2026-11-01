package com.impact.mods.gregtech.tileentities.multi.storage;

import gregtech.api.util.GTStructureChannels;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import space.impact.structures.structure.AutoPlaceEnvironment;
import space.impact.structures.structure.IStructureElement;

import static com.impact.loader.ItemRegistery.FluidTankBlock;
import static space.impact.structures.structure.StructureUtility.ofBlock;

/** Selects new storage blocks without changing the capacity checks for installed blocks. */
final class TankStorageElement<T> implements IStructureElement<T> {
    private final IStructureElement<T> installed;
    private final int maximumTier;

    TankStorageElement(IStructureElement<T> installed, int maximumTier) {
        this.installed = installed;
        this.maximumTier = maximumTier;
    }

    private IStructureElement<T> selected(ItemStack trigger) {
        int tier = Math.max(1, Math.min(maximumTier, GTStructureChannels.getValue(trigger, GTStructureChannels.TIER)));
        return ofBlock(FluidTankBlock, tier - 1);
    }

    @Override
    public boolean check(T machine, World world, int x, int y, int z) {
        return installed.check(machine, world, x, y, z);
    }

    @Override
    public boolean couldBeValid(T machine, World world, int x, int y, int z, ItemStack trigger) {
        return installed.couldBeValid(machine, world, x, y, z, trigger);
    }

    @Override
    public boolean spawnHint(T machine, World world, int x, int y, int z, ItemStack trigger) {
        return selected(trigger).spawnHint(machine, world, x, y, z, trigger);
    }

    @Override
    public boolean placeBlock(T machine, World world, int x, int y, int z, ItemStack trigger) {
        return couldBeValid(machine, world, x, y, z, trigger)
                || selected(trigger).placeBlock(machine, world, x, y, z, trigger);
    }

    @Override
    public BlocksToPlace getBlocksToPlace(T machine, World world, int x, int y, int z,
            ItemStack trigger, AutoPlaceEnvironment environment) {
        return selected(trigger).getBlocksToPlace(machine, world, x, y, z, trigger, environment);
    }

    @Override
    public PlaceResult survivalPlaceBlock(T machine, World world, int x, int y, int z,
            ItemStack trigger, AutoPlaceEnvironment environment) {
        return couldBeValid(machine, world, x, y, z, trigger) ? PlaceResult.SKIP
                : selected(trigger).survivalPlaceBlock(machine, world, x, y, z, trigger, environment);
    }
}
