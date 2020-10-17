package com.impact.mods.GalactiCraft.SolarSystem.planets;

import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import net.minecraft.world.World;

public class EmptyBiomeDecorator extends BiomeDecoratorSpace {
    private World world;

    protected void decorate() {
    }

    protected World getCurrentWorld() {
        return this.world;
    }

    protected void setCurrentWorld(World world) {
        this.world = world;
    }
}
