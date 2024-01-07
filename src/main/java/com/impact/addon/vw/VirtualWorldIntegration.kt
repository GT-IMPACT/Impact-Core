package com.impact.addon.vw

import com.impact.common.oregeneration.Dimensions
import gregtech.api.enums.ItemList
import gregtech.api.enums.Materials
import gregtech.api.enums.OrePrefixes
import gregtech.api.util.GT_OreDictUnificator
import gregtech.api.util.GT_Utility
import com.impact.common.oregeneration.Dimensions.*
import gregtech.api.util.GT_ModHandler
import net.minecraft.item.ItemStack
import net.minecraftforge.fluids.Fluid
import net.minecraftforge.fluids.FluidStack
import space.gtimpact.virtual_world.api.*
import java.awt.Color

object VirtualWorldIntegration {

    @JvmStatic
    fun init() {
        registerOre0()
        registerOre1()
        registerFluidVeins()

        virtualWorldNeiFluidHandler = object : IFluidDisplayHandler {
            override val isModified: Boolean = true
            override val itemDisplay: ItemStack? = ItemList.Display_Fluid.get(1)

            override fun getDrillFluid(): Fluid? = ItemList.sDrillingFluid

            override fun getFluidFromStack(stack: ItemStack?): FluidStack? {
                if (stack == null) return null
                return GT_Utility.getFluidFromDisplayStack(stack)
            }

            override fun getItemFromFluid(stack: FluidStack?, useStackSize: Boolean): ItemStack? {
                if (stack == null) return null
                return GT_Utility.getFluidDisplayStack(stack, useStackSize)
            }
        }
    }

    private fun registerOre1() {
        VirtualOreVein(
            id = 0,
            layer = 1,
            name = "Uraninite",
            weight = 20.0,
            rangeSize = 10..400,
            color = Materials.Uran.color(),
            special = Materials.SulfuricAcid.getFluid(50),
            dimensions = listOf(
                KuiperBelt,
                Haumea,
                Ganymede,
                Proteus,
                Deimos,
                Makemake,
                Ceres,
                Phobos,
                Titan
            ).map { it.id to "${it.name} (${it.tier})" },
            ores = listOf(
                Materials.Uraninite.toOreComponent(90),
                Materials.Uraninite.toOreComponent(60),
                Materials.Uranium.toOreComponent(60),
                Materials.Uranium.toOreComponent(30),
            )
        )
        VirtualOreVein(
            id = 1,
            layer = 1,
            name = "Scheelite",
            weight = 10.0,
            rangeSize = 10..400,
            color = Materials.Scheelite.color(),
            dimensions = listOf(
                Mars,
                KuiperBelt,
                Enceladus,
                Oberon,
                Haumea,
                Ganymede,
                Asteroids,
                Deimos,
                Pluto,
                Callisto,
                Triton,
                Makemake,
                Titan,
                Overworld
            ).map { it.id to "${it.name} (${it.tier})" },
            ores = listOf(
                Materials.Scheelite.toOreComponent(90),
                Materials.Scheelite.toOreComponent(60),
                Materials.Tungstate.toOreComponent(60),
                Materials.Lithium.toOreComponent(30),
            )
        )
        VirtualOreVein(
            id = 2,
            layer = 1,
            name = "Orichalcum",
            weight = 40.0,
            rangeSize = 10..200,
            color = Materials.Orichalcum.color(),
            dimensions = listOf(
                Haumea,
                Miranda,
                Triton,
                Titan
            ).map { it.id to "${it.name} (${it.tier})" },
            ores = listOf(
                Materials.Orichalcum.toOreComponent(90),
                Materials.Tanzanite.toOreComponent(60),
                Materials.Opal.toOreComponent(60),
                Materials.BlueTopaz.toOreComponent(30),
            )
        )
        VirtualOreVein(
            id = 3,
            layer = 1,
            name = "Graphite",
            weight = 40.0,
            rangeSize = 10..100,
            color = Materials.Graphite.color(),
            dimensions = listOf(
                KuiperBelt,
                Overworld,
                Mercury,
                Enceladus,
                Ganymede,
                Miranda,
                Proteus,
                Io,
                Pluto,
                Asteroids,
                Phobos,
                Titan
            ).map { it.id to "${it.name} (${it.tier})" },
            ores = listOf(
                Materials.Graphite.toOreComponent(90),
                Materials.Graphite.toOreComponent(60),
                Materials.Diamond.toOreComponent(60),
                Materials.Coal.toOreComponent(30),
            )
        )
        VirtualOreVein(
            id = 4,
            layer = 1,
            name = "Lazurite",
            weight = 40.0,
            rangeSize = 50..400,
            color = Materials.Lazurite.color(),
            dimensions = listOf(
                Enceladus,
                Overworld,
                Asteroids,
                Deimos,
                Ceres
            ).map { it.id to "${it.name} (${it.tier})" },
            ores = listOf(
                Materials.Lazurite.toOreComponent(90),
                Materials.Sodalite.toOreComponent(60),
                Materials.Lapis.toOreComponent(60),
                Materials.Calcite.toOreComponent(30),
            )
        )
        VirtualOreVein(
            id = 5,
            layer = 1,
            name = "Wulfenite",
            weight = 10.0,
            rangeSize = 50..400,
            color = Materials.Wulfenite.color(),
            dimensions = listOf(
                Overworld,
                Mercury,
                Proteus,
                Asteroids,
                Pluto,
                Moon,
                Ceres,
                Phobos,
                Titan
            ).map { it.id to "${it.name} (${it.tier})" },
            ores = listOf(
                Materials.Wulfenite.toOreComponent(90),
                Materials.Molybdenite.toOreComponent(60),
                Materials.Molybdenum.toOreComponent(60),
                Materials.Powellite.toOreComponent(30),
            )
        )
        VirtualOreVein(
            id = 6,
            layer = 1,
            name = "Oilsands",
            weight = 40.0,
            rangeSize = 200..400,
            color = Materials.Oilsands.color(),
            dimensions = listOf(
                Overworld,
                Europa
            ).map { it.id to "${it.name} (${it.tier})" },
            ores = listOf(
                Materials.Oilsands.toOreComponent(90),
                Materials.Sulfur.toOreComponent(60),
                Materials.Sulfur.toOreComponent(60),
                Materials.Oilsands.toOreComponent(30),
            )
        )
        VirtualOreVein(
            id = 7,
            layer = 1,
            name = "Orichalcum",
            weight = 40.0,
            rangeSize = 50..200,
            color = Materials.Orichalcum.color(),
            dimensions = listOf(
                Haumea,
                Miranda,
                Pluto,
                Makemake,
                Titan
            ).map { it.id to "${it.name} (${it.tier})" },
            ores = listOf(
                Materials.Orichalcum.toOreComponent(90),
                Materials.Tanzanite.toOreComponent(60),
                Materials.Europium.toOreComponent(60),
                Materials.MeteoricIron.toOreComponent(30),
            )
        )
        VirtualOreVein(
            id = 8,
            layer = 1,
            name = "Sheldonite",
            weight = 20.0,
            rangeSize = 50..200,
            color = Materials.Sheldonite.color(),
            dimensions = listOf(
                Venus,
                Enceladus,
                Haumea,
                Miranda,
                Pluto,
                Makemake
            ).map { it.id to "${it.name} (${it.tier})" },
            ores = listOf(
                Materials.Sheldonite.toOreComponent(90),
                Materials.Osmirinigon.toOreComponent(60),
                Materials.Chromite.toOreComponent(60),
                Materials.MeteoricIron.toOreComponent(30),
            )
        )
        VirtualOreVein(
            id = 9,
            layer = 1,
            name = "Bentonite",
            weight = 60.0,
            rangeSize = 50..200,
            color = Materials.Bentonite.color(),
            dimensions = listOf(
                Overworld,
                Enceladus,
                Haumea,
                Asteroids,
                Makemake,
                Ceres
            ).map { it.id to "${it.name} (${it.tier})" },
            ores = listOf(
                Materials.Bentonite.toOreComponent(90),
                Materials.Magnesite.toOreComponent(60),
                Materials.Olivine.toOreComponent(60),
                Materials.Glauconite.toOreComponent(30),
            )
        )
        VirtualOreVein(
            id = 10,
            layer = 1,
            name = "Nickel",
            weight = 10.0,
            rangeSize = 50..200,
            color = Materials.Nickel.color(),
            dimensions = listOf(
                Overworld,
                Enceladus,
                Haumea,
                Asteroids,
                Makemake,
                Ceres
            ).map { it.id to "${it.name} (${it.tier})" },
            ores = listOf(
                Materials.Nickel.toOreComponent(90),
                Materials.Osmium.toOreComponent(60),
                Materials.Iridium.toOreComponent(60),
                Materials.Nickel.toOreComponent(30),
            )
        )
        VirtualOreVein(
            id = 11,
            layer = 1,
            name = "Tungsten",
            weight = 16.0,
            rangeSize = 50..200,
            color = Materials.Tungsten.color(),
            dimensions = listOf(
                Oberon,
                Proteus,
                Pluto,
                Triton,
                Titan
            ).map { it.id to "${it.name} (${it.tier})" },
            ores = listOf(
                Materials.Tungsten.toOreComponent(90),
                Materials.Silicon.toOreComponent(60),
                Materials.DeepIron.toOreComponent(60),
                Materials.Andradite.toOreComponent(30),
            )
        )
        VirtualOreVein(
            id = 12,
            layer = 1,
            name = "Naqlatigon",
            weight = 40.0,
            rangeSize = 50..200,
            color = Materials.Naqlatigon.color(),
            dimensions = listOf(
                Oberon,
                Proteus,
                Pluto,
                Triton,
                Titan,
                Mercury,
                Haumea,
                KuiperBelt
            ).map { it.id to "${it.name} (${it.tier})" },
            ores = listOf(
                Materials.Naqlatigon.toOreComponent(90),
                Materials.Naquadah.toOreComponent(60),
                Materials.Pyrolusite.toOreComponent(60),
                Materials.Ilmenite.toOreComponent(30),
            )
        )
        VirtualOreVein(
            id = 13,
            layer = 1,
            name = "Coal",
            weight = 60.0,
            rangeSize = 50..200,
            color = Materials.Coal.color(),
            dimensions = listOf(Overworld).map { it.id to "${it.name} (${it.tier})" },
            ores = listOf(
                Materials.Coal.toOreComponent(90),
                Materials.Coal.toOreComponent(60),
                Materials.Coal.toOreComponent(60),
                Materials.Lignite.toOreComponent(30),
            )
        )
        VirtualOreVein(
            id = 14,
            layer = 1,
            name = "Grossular",
            weight = 20.0,
            rangeSize = 50..200,
            color = Materials.Grossular.color(),
            dimensions = listOf(
                Overworld,
                Oberon,
                Asteroids,
                Io,
                Triton,
                Ceres,
                Titan
            ).map { it.id to "${it.name} (${it.tier})" },
            ores = listOf(
                Materials.Grossular.toOreComponent(90),
                Materials.Spessartine.toOreComponent(60),
                Materials.Pyrolusite.toOreComponent(60),
                Materials.Tantalite.toOreComponent(30),
            )
        )
        VirtualOreVein(
            id = 15,
            layer = 1,
            name = "Desh",
            weight = 30.0,
            rangeSize = 50..200,
            color = Materials.Desh.color(),
            dimensions = listOf(
                Mars,
                Enceladus,
                Miranda,
                Triton,
                Makemake,
                Titan
            ).map { it.id to "${it.name} (${it.tier})" },
            ores = listOf(
                Materials.Desh.toOreComponent(90),
                Materials.Desh.toOreComponent(60),
                Materials.Scheelite.toOreComponent(60),
                Materials.Tungstate.toOreComponent(30),
            )
        )
        VirtualOreVein(
            id = 16,
            layer = 1,
            name = "Nickel",
            weight = 30.0,
            rangeSize = 50..200,
            color = Materials.Desh.color(),
            dimensions = listOf(
                Venus,
                KuiperBelt,
                Mercury,
                Miranda,
                Io,
                Triton,
                Ceres,
                Titan
            ).map { it.id to "${it.name} (${it.tier})" },
            ores = listOf(
                Materials.Nickel.toOreComponent(90),
                Materials.Iridium.toOreComponent(60),
                Materials.Palladium.toOreComponent(60),
                Materials.Platinum.toOreComponent(30),
            )
        )
        VirtualOreVein(
            id = 17,
            layer = 1,
            name = "Quartz",
            weight = 30.0,
            rangeSize = 50..200,
            color = Materials.Quartzite.color(),
            dimensions = listOf(
                Mars,
                Venus,
                Overworld,
                Enceladus,
                Proteus,
                Io,
                Moon,
                Phobos
            ).map { it.id to "${it.name} (${it.tier})" },
            ores = listOf(
                Materials.Quartzite.toOreComponent(90),
                Materials.NetherQuartz.toOreComponent(60),
                Materials.CertusQuartz.toOreComponent(60),
                Materials.Barite.toOreComponent(30),
            )
        )
        VirtualOreVein(
            id = 18,
            layer = 1,
            name = "Pitchblende",
            weight = 30.0,
            rangeSize = 50..400,
            color = Materials.Pitchblende.color(),
            special = Materials.SulfuricAcid.getFluid(50),
            dimensions = listOf(
                Mars,
                Venus,
                KuiperBelt,
                Oberon,
                Haumea,
                Io,
                Makemake,
                Phobos
            ).map { it.id to "${it.name} (${it.tier})" },
            ores = listOf(
                Materials.Pitchblende.toOreComponent(90),
                Materials.Pitchblende.toOreComponent(60),
                Materials.Uraninite.toOreComponent(60),
                Materials.Uraninite.toOreComponent(30),
            )
        )
        VirtualOreVein(
            id = 19,
            layer = 1,
            name = "Desh",
            weight = 40.0,
            rangeSize = 50..400,
            color = Materials.Desh.color(),
            dimensions = listOf(
                Haumea,
                Triton
            ).map { it.id to "${it.name} (${it.tier})" },
            ores = listOf(
                Materials.Desh.toOreComponent(90),
                Materials.Europium.toOreComponent(60),
                Materials.Quantium.toOreComponent(60),
                Materials.Tantalite.toOreComponent(30),
            )
        )
        VirtualOreVein(
            id = 20,
            layer = 1,
            name = "Redstone",
            weight = 60.0,
            rangeSize = 50..400,
            color = Materials.Redstone.color(),
            dimensions = listOf(
                Mars,
                Venus,
                Overworld,
                Mercury,
                Ganymede,
                Miranda
            ).map { it.id to "${it.name} (${it.tier})" },
            ores = listOf(
                Materials.Redstone.toOreComponent(90),
                Materials.Redstone.toOreComponent(60),
                Materials.Ruby.toOreComponent(60),
                Materials.Cinnabar.toOreComponent(30),
            )
        )
        VirtualOreVein(
            id = 21,
            layer = 1,
            name = "Almandine",
            weight = 60.0,
            rangeSize = 50..400,
            color = Materials.Almandine.color(),
            dimensions = listOf(
                Overworld,
                Enceladus,
                Haumea,
                Proteus,
                Makemake
            ).map { it.id to "${it.name} (${it.tier})" },
            ores = listOf(
                Materials.Almandine.toOreComponent(90),
                Materials.Pyrope.toOreComponent(60),
                Materials.Sapphire.toOreComponent(60),
                Materials.GreenSapphire.toOreComponent(30),
            )
        )
        VirtualOreVein(
            id = 22,
            layer = 1,
            name = "Uranium 238",
            weight = 20.0,
            rangeSize = 50..400,
            color = Materials.Uranium.color(),
            special = Materials.SulfuricAcid.getFluid(50),
            dimensions = listOf(Makemake).map { it.id to "${it.name} (${it.tier})" },
            ores = listOf(
                Materials.Uranium.toOreComponent(90),
                Materials.Plutonium.toOreComponent(60),
                Materials.Forcicium.toOreComponent(60),
                Materials.Monazite.toOreComponent(30),
            )
        )
        VirtualOreVein(
            id = 23,
            layer = 1,
            name = "Bastnasite",
            weight = 30.0,
            rangeSize = 50..400,
            color = Materials.Bastnasite.color(),
            dimensions = listOf(
                Venus,
                Enceladus,
                Haumea,
                Deimos,
                Io,
                Callisto,
                Triton,
                Makemake,
                Moon,
                Titan
            ).map { it.id to "${it.name} (${it.tier})" },
            ores = listOf(
                Materials.Bastnasite.toOreComponent(90),
                Materials.Bastnasite.toOreComponent(60),
                Materials.Monazite.toOreComponent(60),
                Materials.Neodymium.toOreComponent(30),
            )
        )
        VirtualOreVein(
            id = 24,
            layer = 1,
            name = "Mithril",
            weight = 40.0,
            rangeSize = 50..400,
            color = Materials.Mithril.color(),
            dimensions = listOf(
                KuiperBelt,
                Mercury,
                Ganymede,
                Miranda,
                Asteroids
            ).map { it.id to "${it.name} (${it.tier})" },
            ores = listOf(
                Materials.Mithril.toOreComponent(90),
                Materials.GarnetRed.toOreComponent(60),
                Materials.GarnetYellow.toOreComponent(60),
                Materials.Electrotine.toOreComponent(30),
            )
        )
        VirtualOreVein(
            id = 25,
            layer = 1,
            name = "Thorium",
            weight = 60.0,
            rangeSize = 50..400,
            color = Materials.Thorium.color(),
            special = Materials.SulfuricAcid.getFluid(50),
            dimensions = listOf(
                Haumea,
                Pluto,
                Triton
            ).map { it.id to "${it.name} (${it.tier})" },
            ores = listOf(
                Materials.Thorium.toOreComponent(90),
                Materials.Europium.toOreComponent(60),
                Materials.Plutonium241.toOreComponent(60),
                Materials.Uranium235.toOreComponent(30),
            )
        )
        VirtualOreVein(
            id = 26,
            layer = 1,
            name = "Black Plutonium",
            weight = 40.0,
            rangeSize = 50..400,
            color = Materials.BlackPlutonium.color(),
            dimensions = listOf(
                Haumea,
                Pluto,
                Makemake
            ).map { it.id to "${it.name} (${it.tier})" },
            ores = listOf(
                Materials.BlackPlutonium.toOreComponent(90),
                Materials.GarnetRed.toOreComponent(60),
                Materials.GarnetYellow.toOreComponent(60),
                Materials.Infuscolium.toOreComponent(30),
            )
        )
        VirtualOreVein(
            id = 27,
            layer = 1,
            name = "Garnierite",
            weight = 40.0,
            rangeSize = 50..400,
            color = Materials.Garnierite.color(),
            dimensions = listOf(
                Titan,
                Mars,
                Overworld,
                Proteus,
                Asteroids,
                Deimos,
                Triton,
                Phobos
            ).map { it.id to "${it.name} (${it.tier})" },
            ores = listOf(
                Materials.Garnierite.toOreComponent(90),
                Materials.Nickel.toOreComponent(60),
                Materials.Cobaltite.toOreComponent(60),
                Materials.Pentlandite.toOreComponent(30),
            )
        )
        VirtualOreVein(
            id = 28,
            layer = 1,
            name = "Rutile",
            weight = 16.0,
            rangeSize = 50..400,
            color = Materials.Rutile.color(),
            dimensions = listOf(
                Mercury,
                Ganymede,
                Miranda,
                Proteus,
                Pluto,
                Callisto,
                Asteroids,
                Moon,
                Titan
            ).map { it.id to "${it.name} (${it.tier})" },
            ores = listOf(
                Materials.Rutile.toOreComponent(90),
                Materials.Chromite.toOreComponent(60),
                Materials.Uvarovite.toOreComponent(60),
                Materials.Perlite.toOreComponent(30),
            )
        )
        VirtualOreVein(
            id = 29,
            layer = 1,
            name = "Platinum",
            weight = 10.0,
            rangeSize = 50..400,
            color = Materials.Platinum.color(),
            dimensions = listOf(
                KuiperBelt,
                Mercury,
                Oberon,
                Ganymede,
                Io,
                Pluto,
                Callisto,
                Ceres,
                Titan
            ).map { it.id to "${it.name} (${it.tier})" },
            ores = listOf(
                Materials.Platinum.toOreComponent(90),
                Materials.Chromite.toOreComponent(60),
                Materials.Sheldonite.toOreComponent(60),
                Materials.Palladium.toOreComponent(30),
            )
        )
        VirtualOreVein(
            id = 30,
            layer = 1,
            name = "Mysterious Crystal",
            weight = 16.0,
            rangeSize = 50..400,
            color = Materials.MysteriousCrystal.color(),
            dimensions = listOf(
                Enceladus,
                Miranda,
                Triton
            ).map { it.id to "${it.name} (${it.tier})" },
            ores = listOf(
                Materials.MysteriousCrystal.toOreComponent(90),
                Materials.Mithril.toOreComponent(60),
                Materials.Amethyst.toOreComponent(60),
                Materials.Lapotron.toOreComponent(30),
            )
        )
        VirtualOreVein(
            id = 31,
            layer = 1,
            name = "Diamond",
            weight = 100.0,
            rangeSize = 50..400,
            color = Materials.Diamond.color(),
            dimensions = listOf(
                Enceladus,
                Pluto
            ).map { it.id to "${it.name} (${it.tier})" },
            ores = listOf(
                Materials.Diamond.toOreComponent(90),
                Materials.Bentonite.toOreComponent(60),
                Materials.Diamond.toOreComponent(60),
                Materials.Bentonite.toOreComponent(30),
            )
        )
        VirtualOreVein(
            id = 32,
            layer = 1,
            name = "Arsenic",
            weight = 60.0,
            rangeSize = 50..400,
            color = Materials.Arsenic.color(),
            dimensions = listOf(
                Mercury,
                Phobos,
                Titan
            ).map { it.id to "${it.name} (${it.tier})" },
            ores = listOf(
                Materials.Arsenic.toOreComponent(90),
                Materials.Bismuth.toOreComponent(60),
                Materials.Antimony.toOreComponent(60),
                Materials.Antimony.toOreComponent(30),
            )
        )
        VirtualOreVein(
            id = 33,
            layer = 1,
            name = "Chalcopyrite",
            weight = 80.0,
            rangeSize = 50..400,
            color = Materials.Chalcopyrite.color(),
            dimensions = listOf(
                Overworld,
                Proteus,
                Asteroids,
                Callisto,
                Moon,
                Ceres,
                Titan
            ).map { it.id to "${it.name} (${it.tier})" },
            ores = listOf(
                Materials.Chalcopyrite.toOreComponent(90),
                Materials.Iron.toOreComponent(60),
                Materials.Pyrite.toOreComponent(60),
                Materials.Copper.toOreComponent(30),
            )
        )
        VirtualOreVein(
            id = 34,
            layer = 1,
            name = "Vanadium",
            weight = 60.0,
            rangeSize = 50..400,
            color = Materials.Vanadium.color(),
            dimensions = listOf(
                Proteus,
                Makemake,
                Titan
            ).map { it.id to "${it.name} (${it.tier})" },
            ores = listOf(
                Materials.Vanadium.toOreComponent(90),
                Materials.Magnetite.toOreComponent(60),
                Materials.Gold.toOreComponent(60),
                Materials.Chrome.toOreComponent(30),
            )
        )
        VirtualOreVein(
            id = 35,
            layer = 1,
            name = "Bauxite",
            weight = 60.0,
            rangeSize = 50..400,
            color = Materials.Bauxite.color(),
            dimensions = listOf(
                KuiperBelt,
                Mercury,
                Haumea,
                Ganymede,
                Proteus,
                Asteroids,
                Pluto,
                Makemake,
                Moon,
                Phobos,
                Titan
            ).map { it.id to "${it.name} (${it.tier})" },
            ores = listOf(
                Materials.Bauxite.toOreComponent(90),
                Materials.Ilmenite.toOreComponent(60),
                Materials.Aluminium.toOreComponent(60),
                Materials.Ilmenite.toOreComponent(30),
            )
        )
        VirtualOreVein(
            id = 36,
            layer = 1,
            name = "Bauxite",
            weight = 15.0,
            rangeSize = 4..25,
            color = Materials.Bauxite.color(),
            dimensions = listOf(Overworld).map { it.id to "${it.name} (${it.tier})" },
            ores = listOf(
                Materials.Bauxite.toOreComponent(90),
                Materials.Ilmenite.toOreComponent(60),
                Materials.Aluminium.toOreComponent(60),
                Materials.Ilmenite.toOreComponent(30),
            )
        )
        VirtualOreVein(
            id = 37,
            layer = 1,
            name = "Soapstone",
            weight = 15.0,
            rangeSize = 50..400,
            color = Materials.Soapstone.color(),
            dimensions = listOf(
                Overworld,
                Oberon,
                Miranda,
                Ceres
            ).map { it.id to "${it.name} (${it.tier})" },
            ores = listOf(
                Materials.Soapstone.toOreComponent(90),
                Materials.Talc.toOreComponent(60),
                Materials.Glauconite.toOreComponent(60),
                Materials.Pentlandite.toOreComponent(30),
            )
        )
        VirtualOreVein(
            id = 38,
            layer = 1,
            name = "Neutronium",
            weight = 10.0,
            rangeSize = 50..400,
            color = Materials.Neutronium.color(),
            dimensions = listOf(
                KuiperBelt,
                Makemake
            ).map { it.id to "${it.name} (${it.tier})" },
            ores = listOf(
                Materials.Neutronium.toOreComponent(90),
                Materials.Adamantium.toOreComponent(60),
                Materials.Naquadah.toOreComponent(60),
                Materials.Titanium.toOreComponent(30),
            )
        )
        VirtualOreVein(
            id = 39,
            layer = 1,
            name = "Stibnite",
            weight = 100.0,
            rangeSize = 50..400,
            color = Materials.Stibnite.color(),
            dimensions = listOf(
                Oberon,
                Pluto,
                Triton,
                Moon,
                Ceres
            ).map { it.id to "${it.name} (${it.tier})" },
            ores = listOf(
                Materials.Stibnite.toOreComponent(90),
                Materials.Tantalite.toOreComponent(60),
                Materials.Stibnite.toOreComponent(60),
                Materials.Tantalite.toOreComponent(30),
            )
        )
        VirtualOreVein(
            id = 40,
            layer = 1,
            name = "Gold Magnetite",
            weight = 160.0,
            rangeSize = 50..400,
            color = Materials.Stibnite.color(),
            dimensions = listOf(
                Mars,
                Overworld,
                Asteroids,
                Pluto,
                Callisto,
                Triton,
                Phobos
            ).map { it.id to "${it.name} (${it.tier})" },
            ores = listOf(
                Materials.Magnetite.toOreComponent(90),
                Materials.Magnetite.toOreComponent(60),
                Materials.VanadiumMagnetite.toOreComponent(60),
                Materials.Gold.toOreComponent(30),
            )
        )
        VirtualOreVein(
            id = 41,
            layer = 1,
            name = "Brown Limonite",
            weight = 120.0,
            rangeSize = 50..400,
            color = Materials.BrownLimonite.color(),
            dimensions = listOf(
                Mars,
                Overworld,
                Mercury,
                Oberon,
                Ganymede,
                Pluto,
                Callisto,
                Ceres
            ).map { it.id to "${it.name} (${it.tier})" },
            ores = listOf(
                Materials.BrownLimonite.toOreComponent(90),
                Materials.YellowLimonite.toOreComponent(60),
                Materials.BandedIron.toOreComponent(60),
                Materials.Malachite.toOreComponent(30),
            )
        )
        VirtualOreVein(
            id = 42,
            layer = 1,
            name = "Plutonium 239",
            weight = 100.0,
            rangeSize = 50..400,
            color = Materials.Plutonium.color(),
            special = Materials.SulfuricAcid.getFluid(50),
            dimensions = listOf(
                Haumea,
                Pluto,
                Triton
            ).map { it.id to "${it.name} (${it.tier})" },
            ores = listOf(
                Materials.Plutonium.toOreComponent(90),
                Materials.Uranium.toOreComponent(60),
                Materials.Plutonium.toOreComponent(60),
                Materials.Uranium.toOreComponent(30),
            )
        )
        VirtualOreVein(
            id = 43,
            layer = 1,
            name = "Beryllium",
            weight = 30.0,
            rangeSize = 50..400,
            color = Materials.BrownLimonite.color(),
            special = Materials.SulfuricAcid.getFluid(50),
            dimensions = listOf(
                Mars,
                Venus,
                Overworld,
                Enceladus,
                Haumea,
                Pluto,
                Makemake,
                Ceres,
                Titan
            ).map { it.id to "${it.name} (${it.tier})" },
            ores = listOf(
                Materials.Beryllium.toOreComponent(90),
                Materials.Beryllium.toOreComponent(60),
                Materials.Emerald.toOreComponent(60),
                Materials.Thorium.toOreComponent(30),
            )
        )
        VirtualOreVein(
            id = 44,
            layer = 1,
            name = "Copper",
            weight = 100.0,
            rangeSize = 50..400,
            color = Materials.Copper.color(),
            dimensions = listOf(Makemake).map { it.id to "${it.name} (${it.tier})" },
            ores = listOf(
                Materials.Copper.toOreComponent(90),
                Materials.Antimony.toOreComponent(60),
                Materials.Copper.toOreComponent(60),
                Materials.Antimony.toOreComponent(30),
            )
        )
        VirtualOreVein(
            id = 45,
            layer = 1,
            name = "Sheldonite",
            weight = 5.0,
            rangeSize = 50..400,
            color = Materials.Sheldonite.color(),
            dimensions = listOf(
                Haumea,
                Ganymede,
                Proteus,
                Asteroids,
                Pluto,
                Triton,
                Moon,
                Overworld
            ).map { it.id to "${it.name} (${it.tier})" },
            ores = listOf(
                Materials.Sheldonite.toOreComponent(90),
                Materials.Palladium.toOreComponent(60),
                Materials.Platinum.toOreComponent(60),
                Materials.Iridium.toOreComponent(30),
            )
        )
        VirtualOreVein(
            id = 46,
            layer = 1,
            name = "Tetrahedrite",
            weight = 70.0,
            rangeSize = 50..400,
            color = Materials.Tetrahedrite.color(),
            dimensions = listOf(
                Mars,
                Venus,
                KuiperBelt,
                Ganymede,
                Miranda,
                Asteroids,
                Deimos,
                Overworld
            ).map { it.id to "${it.name} (${it.tier})" },
            ores = listOf(
                Materials.Tetrahedrite.toOreComponent(90),
                Materials.Tetrahedrite.toOreComponent(60),
                Materials.Copper.toOreComponent(60),
                Materials.Stibnite.toOreComponent(30),
            )
        )
        VirtualOreVein(
            id = 47,
            layer = 1,
            name = "Galena",
            weight = 40.0,
            rangeSize = 50..400,
            color = Materials.Galena.color(),
            dimensions = listOf(
                Mars,
                Venus,
                Overworld,
                Oberon,
                Ganymede,
                Triton,
                Moon
            ).map { it.id to "${it.name} (${it.tier})" },
            ores = listOf(
                Materials.Galena.toOreComponent(90),
                Materials.Galena.toOreComponent(60),
                Materials.Silver.toOreComponent(60),
                Materials.Lead.toOreComponent(30),
            )
        )
        VirtualOreVein(
            id = 48,
            layer = 1,
            name = "Naquadah",
            weight = 30.0,
            rangeSize = 50..400,
            color = Materials.Naquadah.color(),
            dimensions = listOf(
                Venus,
                KuiperBelt,
                Mercury,
                Oberon,
                Haumea,
                Pluto,
                Asteroids,
                Titan
            ).map { it.id to "${it.name} (${it.tier})" },
            ores = listOf(
                Materials.Naquadah.toOreComponent(90),
                Materials.Naquadah.toOreComponent(60),
                Materials.Naquadah.toOreComponent(60),
                Materials.Plutonium.toOreComponent(30),
            )
        )
        VirtualOreVein(
            id = 49,
            layer = 1,
            name = "Apatite",
            weight = 60.0,
            rangeSize = 50..400,
            color = Materials.Apatite.color(),
            dimensions = listOf(Overworld).map { it.id to "${it.name} (${it.tier})" },
            ores = listOf(
                Materials.Apatite.toOreComponent(90),
                Materials.Apatite.toOreComponent(60),
                Materials.Phosphorus.toOreComponent(60),
                Materials.Pyrochlore.toOreComponent(30),
            )
        )
        VirtualOreVein(
            id = 50,
            layer = 1,
            name = "Tantalite",
            weight = 10.0,
            rangeSize = 50..400,
            color = Materials.Tantalite.color(),
            dimensions = listOf(
                Enceladus,
                Miranda,
                Proteus,
                Pluto,
                Makemake,
                Titan
            ).map { it.id to "${it.name} (${it.tier})" },
            ores = listOf(
                Materials.Tantalite.toOreComponent(90),
                Materials.Ilmenite.toOreComponent(60),
                Materials.Tungstate.toOreComponent(60),
                Materials.Pyrolusite.toOreComponent(30),
            )
        )
        VirtualOreVein(
            id = 51,
            layer = 1,
            name = "Chrome",
            weight = 5.0,
            rangeSize = 50..400,
            color = Materials.Chrome.color(),
            dimensions = listOf(Europa).map { it.id to "${it.name} (${it.tier})" },
            ores = listOf(
                Materials.Chrome.toOreComponent(90),
                Materials.Tungsten.toOreComponent(60),
                Materials.Molybdenum.toOreComponent(60),
                Materials.Manganese.toOreComponent(30),
            )
        )
        VirtualOreVein(
            id = 52,
            layer = 1,
            name = "Ledox",
            weight = 20.0,
            rangeSize = 50..400,
            color = Materials.Ledox.color(),
            dimensions = listOf(
                Enceladus,
                Europa,
                Pluto,
                Callisto,
                Ceres
            ).map { it.id to "${it.name} (${it.tier})" },
            ores = listOf(
                Materials.Cassiterite.toOreComponent(90),
                Materials.Ledox.toOreComponent(60),
                Materials.Palladium.toOreComponent(60),
                Materials.Zinc.toOreComponent(30),
            )
        )
        VirtualOreVein(
            id = 53,
            layer = 1,
            name = "Iron Magnetite",
            weight = 160.0,
            rangeSize = 50..400,
            color = Materials.Iron.color(),
            dimensions = listOf(
                Overworld,
                Deimos,
                Io,
                Makemake,
                Ceres
            ).map { it.id to "${it.name} (${it.tier})" },
            ores = listOf(
                Materials.Magnetite.toOreComponent(90),
                Materials.Magnetite.toOreComponent(60),
                Materials.Iron.toOreComponent(60),
                Materials.VanadiumMagnetite.toOreComponent(30),
            )
        )
        VirtualOreVein(
            id = 54,
            layer = 1,
            name = "Meteoric Iron",
            weight = 60.0,
            rangeSize = 50..400,
            color = Materials.MeteoricIron.color(),
            dimensions = listOf(
                Deimos,
                Io,
                Makemake,
                Ceres
            ).map { it.id to "${it.name} (${it.tier})" },
            ores = listOf(
                Materials.MeteoricIron.toOreComponent(90),
                Materials.Ledox.toOreComponent(60),
                Materials.Chromite.toOreComponent(60),
                Materials.MeteoricIron.toOreComponent(30),
            )
        )
        VirtualOreVein(
            id = 55,
            layer = 1,
            name = "Rock Salt",
            weight = 50.0,
            rangeSize = 50..400,
            color = Materials.Salt.color(),
            dimensions = listOf(
                Mars,
                Overworld
            ).map { it.id to "${it.name} (${it.tier})" },
            ores = listOf(
                Materials.RockSalt.toOreComponent(90),
                Materials.Salt.toOreComponent(60),
                Materials.Lepidolite.toOreComponent(60),
                Materials.Spodumene.toOreComponent(30),
            )
        )
        VirtualOreVein(
            id = 56,
            layer = 1,
            name = "Tin",
            weight = 50.0,
            rangeSize = 50..400,
            color = Materials.Tin.color(),
            dimensions = listOf(
                Venus,
                Overworld,
                Io,
                Asteroids,
                Miranda
            ).map { it.id to "${it.name} (${it.tier})" },
            ores = listOf(
                Materials.Tin.toOreComponent(90),
                Materials.Tin.toOreComponent(60),
                Materials.Cassiterite.toOreComponent(60),
                Materials.Tin.toOreComponent(30),
            )
        )
        VirtualOreVein(
            id = 57,
            layer = 1,
            name = "Zinc",
            weight = 50.0,
            rangeSize = 50..400,
            color = Materials.Zinc.color(),
            dimensions = listOf(
                Mars,
                Venus,
                Deimos,
                Io,
                Phobos,
                Overworld
            ).map { it.id to "${it.name} (${it.tier})" },
            ores = listOf(
                Materials.Sphalerite.toOreComponent(90),
                Materials.Zinc.toOreComponent(60),
                Materials.Sulfur.toOreComponent(60),
                Materials.Pyrite.toOreComponent(30),
            )
        )
    }

    private fun registerOre0() {
        VirtualOreVein(
            id = 500,
            name = "Copper",
            dimensions = listOf(
                Mars,
                Overworld,
                Proteus,
                Triton,
                Phobos,
                Asteroids
            ).map { it.id to "${it.name} (${it.tier})" },
            rangeSize = 1..10,
            layer = 0,
            weight = 32.0,
            color = Materials.Copper.color(),
            ores = listOf(
                Materials.Copper.toOreComponent(100)
            )
        )
        VirtualOreVein(
            id = 501,
            name = "Tin",
            dimensions = listOf(
                Overworld,
                Mars,
                Deimos,
                Ganymede,
                Titan,
                Proteus,
                Asteroids
            ).map { it.id to "${it.name} (${it.tier})" },
            rangeSize = 1..10,
            layer = 0,
            weight = 32.0,
            color = Materials.Tin.color(),
            ores = listOf(
                Materials.Tin.toOreComponent(100)
            )
        )
        VirtualOreVein(
            id = 502,
            name = "Bismuth",
            dimensions = listOf(
                Overworld,
                Mars,
                Callisto,
                Mercury,
                Io,
                Proteus,
                Makemake
            ).map { it.id to "${it.name} (${it.tier})" },
            rangeSize = 1..10,
            layer = 0,
            weight = 8.0,
            color = Materials.Bismuth.color(),
            ores = listOf(
                Materials.Bismuth.toOreComponent(100)
            )
        )
        VirtualOreVein(
            id = 503,
            name = "Coal",
            dimensions = listOf(Overworld).map { it.id to "${it.name} (${it.tier})" },
            rangeSize = 1..10,
            layer = 0,
            weight = 24.0,
            color = Materials.Coal.color(),
            ores = listOf(
                Materials.Coal.toOreComponent(100)
            )
        )
        VirtualOreVein(
            id = 504,
            name = "Iron",
            dimensions = listOf(
                Overworld,
                Mars,
                Io,
                Callisto,
                Triton,
                Enceladus,
                Asteroids,
                Haumea,
                Ganymede,
                Miranda,
                Phobos,
                Titan
            ).map { it.id to "${it.name} (${it.tier})" },
            rangeSize = 1..10,
            layer = 0,
            weight = 16.0,
            color = Materials.Iron.color(),
            ores = listOf(
                Materials.Iron.toOreComponent(100)
            )
        )
        VirtualOreVein(
            id = 505,
            name = "Lead",
            dimensions = listOf(
                Overworld,
                Mars,
                Venus,
                Mercury,
                Pluto,
                Triton,
                Asteroids,
                Makemake,
                Oberon,
                Ceres,
                Ganymede,
                Deimos
            ).map { it.id to "${it.name} (${it.tier})" },
            rangeSize = 1..10,
            layer = 0,
            weight = 16.0,
            color = Materials.Lead.color(),
            ores = listOf(
                Materials.Lead.toOreComponent(100)
            )
        )
        VirtualOreVein(
            id = 506,
            name = "Zinc",
            dimensions = listOf(
                Overworld,
                Mars,
                Io,
                Mercury,
                Enceladus,
                Haumea,
                Ganymede,
                Proteus,
                Titan
            ).map { it.id to "${it.name} (${it.tier})" },
            rangeSize = 1..10,
            layer = 0,
            weight = 12.0,
            color = Materials.Zinc.color(),
            ores = listOf(
                Materials.Zinc.toOreComponent(100)
            )
        )
        VirtualOreVein(
            id = 507,
            name = "Gold",
            dimensions = listOf(
                Overworld,
                Mars,
                Venus,
                Pluto,
                Callisto,
                Asteroids,
                Ceres,
                Miranda,
                Phobos,
                Mercury
            ).map { it.id to "${it.name} (${it.tier})" },
            rangeSize = 1..10,
            layer = 0,
            weight = 8.0,
            color = Materials.Gold.color(),
            ores = listOf(
                Materials.Gold.toOreComponent(100)
            )
        )
        VirtualOreVein(
            id = 508,
            name = "Silver",
            dimensions = listOf(
                Overworld,
                Io,
                Pluto,
                Triton,
                Enceladus,
                Oberon,
                Proteus,
                Titan
            ).map { it.id to "${it.name} (${it.tier})" },
            rangeSize = 1..10,
            layer = 0,
            weight = 8.0,
            color = Materials.Silver.color(),
            ores = listOf(
                Materials.Silver.toOreComponent(100)
            )
        )
        VirtualOreVein(
            id = 509,
            name = "Nickel",
            dimensions = listOf(
                Overworld,
                Mars,
                Venus,
                Mercury,
                Pluto,
                Makemake,
                Asteroids,
                Ceres,
                Deimos
            ).map { it.id to "${it.name} (${it.tier})" },
            rangeSize = 1..10,
            layer = 0,
            weight = 8.0,
            color = Materials.Nickel.color(),
            ores = listOf(
                Materials.Nickel.toOreComponent(100)
            )
        )
        VirtualOreVein(
            id = 510,
            name = "Lapis",
            dimensions = listOf(
                Overworld,
                Io,
                Ganymede,
                Enceladus,
                Phobos,
                Oberon
            ).map { it.id to "${it.name} (${it.tier})" },
            rangeSize = 1..10,
            layer = 0,
            weight = 4.0,
            color = Materials.Lapis.color(),
            ores = listOf(
                Materials.Lapis.toOreComponent(100)
            )
        )
        VirtualOreVein(
            id = 511,
            name = "Diamond",
            dimensions = listOf(
                Overworld,
                Venus,
                Callisto,
                Triton,
                Enceladus,
                Asteroids,
                Oberon,
                Ceres,
                Deimos,
                Titan
            ).map { it.id to "${it.name} (${it.tier})" },
            rangeSize = 1..10,
            layer = 0,
            weight = 2.0,
            color = Materials.Diamond.color(),
            ores = listOf(
                Materials.Diamond.toOreComponent(100)
            )
        )
        VirtualOreVein(
            id = 512,
            name = "Emerald",
            dimensions = listOf(
                Overworld,
                Haumea,
                Proteus,
                Enceladus,
                Makemake
            ).map { it.id to "${it.name} (${it.tier})" },
            rangeSize = 1..10,
            layer = 0,
            weight = 1.0,
            color = Materials.Emerald.color(),
            ores = listOf(
                Materials.Emerald.toOreComponent(100)
            )
        )
        VirtualOreVein(
            id = 513,
            name = "Ruby",
            dimensions = listOf(
                Overworld,
                Haumea,
                Proteus,
                Enceladus,
                Makemake
            ).map { it.id to "${it.name} (${it.tier})" },
            rangeSize = 1..10,
            layer = 0,
            weight = 1.0,
            color = Materials.Ruby.color(),
            ores = listOf(
                Materials.Ruby.toOreComponent(100)
            )
        )
        VirtualOreVein(
            id = 514,
            name = "Sapphire",
            dimensions = listOf(
                Overworld,
                Haumea,
                Proteus,
                Enceladus,
                Makemake
            ).map { it.id to "${it.name} (${it.tier})" },
            rangeSize = 1..10,
            layer = 0,
            weight = 1.0,
            color = Materials.Sapphire.color(),
            ores = listOf(
                Materials.Sapphire.toOreComponent(100)
            )
        )
        VirtualOreVein(
            id = 515,
            name = "Green Sapphire",
            dimensions = listOf(
                Overworld,
                Haumea,
                Proteus,
                Enceladus,
                Makemake
            ).map { it.id to "${it.name} (${it.tier})" },
            rangeSize = 1..10,
            layer = 0,
            weight = 1.0,
            color = Materials.GreenSapphire.color(),
            ores = listOf(
                Materials.GreenSapphire.toOreComponent(100)
            )
        )
        VirtualOreVein(
            id = 516,
            name = "Olivine",
            dimensions = listOf(
                Overworld,
                Haumea,
                Proteus,
                Enceladus,
                Makemake
            ).map { it.id to "${it.name} (${it.tier})" },
            rangeSize = 1..10,
            layer = 0,
            weight = 1.0,
            color = Materials.Olivine.color(),
            ores = listOf(
                Materials.Olivine.toOreComponent(100)
            )
        )
        VirtualOreVein(
            id = 517,
            name = "Topaz",
            dimensions = listOf(
                Overworld,
                Haumea,
                Proteus,
                Enceladus,
                Makemake
            ).map { it.id to "${it.name} (${it.tier})" },
            rangeSize = 1..10,
            layer = 0,
            weight = 1.0,
            color = Materials.Topaz.color(),
            ores = listOf(
                Materials.Topaz.toOreComponent(100)
            )
        )
        VirtualOreVein(
            id = 518,
            name = "Tanzanite",
            dimensions = listOf(
                Overworld,
                Haumea,
                Proteus,
                Enceladus,
                Makemake
            ).map { it.id to "${it.name} (${it.tier})" },
            rangeSize = 1..10,
            layer = 0,
            weight = 1.0,
            color = Materials.Tanzanite.color(),
            ores = listOf(
                Materials.Tanzanite.toOreComponent(100)
            )
        )
        VirtualOreVein(
            id = 519,
            name = "Amethyst",
            dimensions = listOf(
                Overworld,
                Haumea,
                Proteus,
                Enceladus,
                Makemake
            ).map { it.id to "${it.name} (${it.tier})" },
            rangeSize = 1..10,
            layer = 0,
            weight = 1.0,
            color = Materials.Amethyst.color(),
            ores = listOf(
                Materials.Amethyst.toOreComponent(100)
            )
        )
        VirtualOreVein(
            id = 520,
            name = "Opal",
            dimensions = listOf(
                Overworld,
                Haumea,
                Proteus,
                Enceladus,
                Makemake
            ).map { it.id to "${it.name} (${it.tier})" },
            rangeSize = 1..10,
            layer = 0,
            weight = 1.0,
            color = Materials.Opal.color(),
            ores = listOf(
                Materials.Opal.toOreComponent(100)
            )
        )
        VirtualOreVein(
            id = 521,
            name = "Blue Topaz",
            dimensions = listOf(
                Overworld,
                Haumea,
                Proteus,
                Enceladus,
                Makemake
            ).map { it.id to "${it.name} (${it.tier})" },
            rangeSize = 1..10,
            layer = 0,
            weight = 1.0,
            color = Materials.BlueTopaz.color(),
            ores = listOf(
                Materials.BlueTopaz.toOreComponent(100)
            )
        )
        VirtualOreVein(
            id = 522,
            name = "Red Garnet",
            dimensions = listOf(
                Overworld,
                Haumea,
                Proteus,
                Enceladus,
                Makemake
            ).map { it.id to "${it.name} (${it.tier})" },
            rangeSize = 1..10,
            layer = 0,
            weight = 1.0,
            color = Materials.GarnetRed.color(),
            ores = listOf(
                Materials.GarnetRed.toOreComponent(100)
            )
        )
        VirtualOreVein(
            id = 523,
            name = "Yellow Garnet",
            dimensions = listOf(
                Overworld,
                Haumea,
                Proteus,
                Enceladus,
                Makemake
            ).map { it.id to "${it.name} (${it.tier})" },
            rangeSize = 1..10,
            layer = 0,
            weight = 1.0,
            color = Materials.GarnetYellow.color(),
            ores = listOf(
                Materials.GarnetYellow.toOreComponent(100)
            )
        )
        VirtualOreVein(
            id = 524,
            name = "Iridium",
            dimensions = listOf(
                Venus,
                Io,
                Mercury,
                Pluto,
                Callisto,
                Triton,
                Enceladus,
                Asteroids,
                Oberon,
                Ceres,
                Ganymede,
                Proteus,
                Titan
            ).map { it.id to "${it.name} (${it.tier})" },
            rangeSize = 1..10,
            layer = 0,
            weight = 8.0,
            color = Materials.Iridium.color(),
            ores = listOf(
                Materials.Iridium.toOreComponent(100)
            )
        )
        VirtualOreVein(
            id = 525,
            name = "Nether Quartz",
            dimensions = listOf(
                Overworld,
                Enceladus
            ).map { it.id to "${it.name} (${it.tier})" },
            rangeSize = 1..10,
            layer = 0,
            weight = 20.0,
            color = Materials.NetherQuartz.color(),
            ores = listOf(
                Materials.NetherQuartz.toOreComponent(100)
            )
        )
        VirtualOreVein(
            id = 526,
            name = "Saltpeter",
            dimensions = listOf(
                Overworld,
                Mars,
                Venus,
                Io,
                Ganymede,
                Proteus,
                Enceladus,
                Deimos
            ).map { it.id to "${it.name} (${it.tier})" },
            rangeSize = 1..10,
            layer = 0,
            weight = 8.0,
            color = Materials.Saltpeter.color(),
            ores = listOf(
                Materials.Saltpeter.toOreComponent(100)
            )
        )
        VirtualOreVein(
            id = 527,
            name = "Sulfur",
            dimensions = listOf(Overworld).map { it.id to "${it.name} (${it.tier})" },
            rangeSize = 1..10,
            layer = 0,
            weight = 16.0,
            color = Materials.Sulfur.color(),
            ores = listOf(
                Materials.Sulfur.toOreComponent(100)
            )
        )
        VirtualOreVein(
            id = 528,
            name = "Osmium",
            dimensions = listOf(
                Haumea,
                Ganymede,
                Miranda,
                Pluto,
                Proteus,
                Triton,
                Makemake,
                Titan,
                Oberon
            ).map { it.id to "${it.name} (${it.tier})" },
            rangeSize = 1..10,
            layer = 0,
            weight = 1.0,
            color = Materials.Osmium.color(),
            ores = listOf(
                Materials.Osmium.toOreComponent(100)
            )
        )
        VirtualOreVein(
            id = 529,
            name = "Titanium",
            dimensions = listOf(
                Mars,
                Venus,
                Io,
                Mercury,
                Pluto,
                Callisto,
                Triton,
                Enceladus,
                Makemake,
                Asteroids,
                Oberon,
                Haumea,
                Ceres,
                Ganymede,
                Miranda,
                Proteus,
                Deimos,
                Phobos,
                Titan
            ).map { it.id to "${it.name} (${it.tier})" },
            rangeSize = 1..10,
            layer = 0,
            weight = 32.0,
            color = Materials.Titanium.color(),
            ores = listOf(
                Materials.Titanium.toOreComponent(100)
            )
        )
        VirtualOreVein(
            id = 530,
            name = "Meteoric Iron",
            dimensions = listOf(
                Mars,
                Venus,
                Moon,
                Io,
                Pluto,
                Deimos,
                Phobos
            ).map { it.id to "${it.name} (${it.tier})" },
            rangeSize = 1..10,
            layer = 0,
            weight = 8.0,
            color = Materials.MeteoricIron.color(),
            ores = listOf(
                Materials.MeteoricIron.toOreComponent(100)
            )
        )
        VirtualOreVein(
            id = 531,
            name = "Neutronium",
            dimensions = listOf(Makemake).map { it.id to "${it.name} (${it.tier})" },
            rangeSize = 1..10,
            layer = 0,
            weight = 8.0,
            color = Materials.Neutronium.color(),
            ores = listOf(
                Materials.Neutronium.toOreComponent(100)
            )
        )
        VirtualOreVein(
            id = 532,
            name = "Chromite",
            dimensions = listOf(
                Mars,
                Venus,
                Io,
                Mercury,
                Pluto,
                Callisto,
                Triton,
                Enceladus,
                Makemake,
                Asteroids,
                Oberon,
                Haumea,
                Ceres,
                Ganymede,
                Proteus,
                Deimos,
                Phobos,
                Titan
            ).map { it.id to "${it.name} (${it.tier})" },
            rangeSize = 1..10,
            layer = 0,
            weight = 8.0,
            color = Materials.Chromite.color(),
            ores = listOf(
                Materials.Chromite.toOreComponent(100)
            )
        )
        VirtualOreVein(
            id = 533,
            name = "Tungstate",
            dimensions = listOf(
                Mars,
                Venus,
                Io,
                Mercury,
                Pluto,
                Callisto,
                Triton,
                Enceladus,
                Asteroids,
                Oberon,
                Ceres,
                Ganymede,
                Miranda,
                Proteus,
                Deimos,
                Phobos,
                Titan
            ).map { it.id to "${it.name} (${it.tier})" },
            rangeSize = 1..10,
            layer = 0,
            weight = 8.0,
            color = Materials.Tungstate.color(),
            ores = listOf(
                Materials.Tungstate.toOreComponent(100)
            )
        )
        VirtualOreVein(
            id = 534,
            name = "Naquadah",
            dimensions = listOf(
                Venus,
                Io,
                Ceres,
                Mercury,
                Pluto,
                Proteus,
                Enceladus
            ).map { it.id to "${it.name} (${it.tier})" },
            rangeSize = 1..10,
            layer = 0,
            weight = 8.0,
            color = Materials.Naquadah.color(),
            ores = listOf(
                Materials.Naquadah.toOreComponent(100)
            )
        )
        VirtualOreVein(
            id = 535,
            name = "Quantium",
            dimensions = listOf(
                Pluto,
                Triton
            ).map { it.id to "${it.name} (${it.tier})" },
            rangeSize = 1..10,
            layer = 0,
            weight = 6.0,
            color = Materials.Quantium.color(),
            ores = listOf(
                Materials.Quantium.toOreComponent(100)
            )
        )
        VirtualOreVein(
            id = 536,
            name = "Mithril",
            dimensions = listOf(
                Venus,
                Ganymede,
                Triton,
                Titan
            ).map { it.id to "${it.name} (${it.tier})" },
            rangeSize = 1..10,
            layer = 0,
            weight = 8.0,
            color = Materials.Mithril.color(),
            ores = listOf(
                Materials.Mithril.toOreComponent(100)
            )
        )
        VirtualOreVein(
            id = 537,
            name = "Ledox",
            dimensions = listOf(
                Haumea,
                Mercury,
                Pluto,
                Callisto,
                Enceladus,
                Oberon
            ).map { it.id to "${it.name} (${it.tier})" },
            rangeSize = 1..10,
            layer = 0,
            weight = 4.0,
            color = Materials.Ledox.color(),
            ores = listOf(
                Materials.Ledox.toOreComponent(100)
            )
        )
        VirtualOreVein(
            id = 538,
            name = "Oriharukon",
            dimensions = listOf(
                Pluto,
                Titan
            ).map { it.id to "${it.name} (${it.tier})" },
            rangeSize = 1..10,
            layer = 0,
            weight = 6.0,
            color = Materials.Oriharukon.color(),
            ores = listOf(
                Materials.Oriharukon.toOreComponent(100)
            )
        )
        VirtualOreVein(
            id = 539,
            name = "Meitnerium",
            dimensions = listOf(
                Haumea,
                Pluto,
                Makemake
            ).map { it.id to "${it.name} (${it.tier})" },
            rangeSize = 1..10,
            layer = 0,
            weight = 4.0,
            color = Materials.Draconium.color(),
            ores = listOf(
                Materials.Draconium.toOreComponent(100)
            )
        )
        VirtualOreVein(
            id = 540,
            name = "Desh",
            dimensions = listOf(
                Mars,
                Haumea,
                Miranda,
                Proteus,
                Callisto,
                Triton,
                Deimos,
                Makemake,
                Phobos,
                Mercury
            ).map { it.id to "${it.name} (${it.tier})" },
            rangeSize = 1..10,
            layer = 0,
            weight = 6.0,
            color = Materials.Desh.color(),
            ores = listOf(
                Materials.Desh.toOreComponent(100)
            )
        )
        VirtualOreVein(
            id = 541,
            name = "Deep Iron",
            dimensions = listOf(
                Mercury,
                Titan
            ).map { it.id to "${it.name} (${it.tier})" },
            rangeSize = 1..10,
            layer = 0,
            weight = 8.0,
            color = Materials.DeepIron.color(),
            ores = listOf(
                Materials.DeepIron.toOreComponent(100)
            )
        )
        VirtualOreVein(
            id = 542,
            name = "Graphite",
            dimensions = listOf(
                Venus,
                Io,
                Mercury,
                Titan
            ).map { it.id to "${it.name} (${it.tier})" },
            rangeSize = 1..10,
            layer = 0,
            weight = 8.0,
            color = Materials.Graphite.color(),
            ores = listOf(Materials.Graphite.toOreComponent(100))
        )
        VirtualOreVein(
            id = 543,
            name = "Forcicium",
            dimensions = listOf(
                Pluto,
                Proteus,
                Makemake
            ).map { it.id to "${it.name} (${it.tier})" },
            rangeSize = 1..10,
            layer = 0,
            weight = 8.0,
            color = Materials.Forcicium.color(),
            ores = listOf(Materials.Forcicium.toOreComponent(100))
        )
        VirtualOreVein(
            id = 544,
            name = "Enriched Naquadah",
            dimensions = listOf(
                Miranda,
                Proteus
            ).map { it.id to "${it.name} (${it.tier})" },
            rangeSize = 1..10,
            layer = 0,
            weight = 6.0,
            color = Materials.NaquadahEnriched.color(),
            ores = listOf(Materials.NaquadahEnriched.toOreComponent(100))
        )
        VirtualOreVein(
            id = 545,
            name = "Naquadria",
            dimensions = listOf(KuiperBelt).map { it.id to "${it.name} (${it.tier})" },
            rangeSize = 1..10,
            layer = 0,
            weight = 4.0,
            color = Materials.Naquadria.color(),
            ores = listOf(Materials.Naquadria.toOreComponent(100))
        )
        VirtualOreVein(
            id = 546,
            name = "Redstone",
            dimensions = listOf(
                Overworld,
                Mars,
                Ganymede,
                Mercury,
                Titan,
                Proteus,
                Enceladus
            ).map { it.id to "${it.name} (${it.tier})" },
            rangeSize = 1..10,
            layer = 0,
            weight = 16.0,
            color = Materials.Redstone.color(),
            ores = listOf(Materials.Redstone.toOreComponent(100))
        )
        VirtualOreVein(
            id = 547,
            name = "Platinum",
            dimensions = listOf(
                Mars,
                Phobos,
                Asteroids,
                Ceres,
                Callisto,
                Miranda,
                Oberon,
                Makemake,
                Pluto,
                Haumea
            ).map { it.id to "${it.name} (${it.tier})" },
            rangeSize = 1..10,
            layer = 0,
            weight = 16.0,
            color = Materials.Platinum.color(),
            ores = listOf(Materials.Platinum.toOreComponent(100))
        )

        VirtualOreVein(
            id = 1000,
            name = "Empty",
            dimensions = Dimensions.values().map { it.id to "${it.name} (${it.tier})" },
            rangeSize = 1..10,
            layer = 0,
            weight = 5.0,
            color = Materials._NULL.color(),
            ores = listOf(),
            isHidden = true,
        )
        VirtualOreVein(
            id = 1001,
            name = "Empty",
            dimensions = Dimensions.values().map { it.id to "${it.name} (${it.tier})" },
            rangeSize = 1..10,
            layer = 1,
            weight = 5.0,
            color = Materials._NULL.color(),
            ores = listOf(),
            isHidden = true,
        )
    }

    private fun registerFluidVeins() {
        VirtualFluidVein(
            id = 0,
            name = "Empty",
            weight = 33.0,
            rangeSize = 1..10,
            color = Materials._NULL.color(),
            dimensions = Dimensions.values().map { it.id to "${it.name} (${it.tier})" },
            fluid = Materials.Water.getAnyFluid(),
            isHidden = true,
        )
        VirtualFluidVein(
            id = 1,
            name = "Oil",
            weight = 20.0,
            rangeSize = 80..800,
            color = Materials.Oil.color(),
            dimensions = listOf(Overworld, Callisto).map { it.id to "${it.name} (${it.tier})" },
            fluid = Materials.Oil.getAnyFluid()
        )
        VirtualFluidVein(
            id = 2,
            name = "Heavy Oil",
            weight = 20.0,
            rangeSize = 70..800,
            color = color(255, 0, 255),
            dimensions = listOf(Overworld, Europa).map { it.id to "${it.name} (${it.tier})" },
            fluid = Materials.OilHeavy.getAnyFluid()
        )
        VirtualFluidVein(
            id = 3,
            name = "Medium Oil",
            weight = 20.0,
            rangeSize = 80..600,
            color = color(0, 255, 0),
            dimensions = listOf(Overworld).map { it.id to "${it.name} (${it.tier})" },
            fluid = Materials.OilMedium.getAnyFluid()
        )
        VirtualFluidVein(
            id = 4,
            name = "Light Oil",
            weight = 20.0,
            rangeSize = 90..650,
            color = color(255, 255, 0),
            dimensions = listOf(Overworld).map { it.id to "${it.name} (${it.tier})" },
            fluid = Materials.OilLight.getAnyFluid()
        )
        VirtualFluidVein(
            id = 5,
            name = "Natural Gas",
            weight = 20.0,
            rangeSize = 100..700,
            color = color(0, 255, 255),
            dimensions = listOf(Overworld, Ganymede).map { it.id to "${it.name} (${it.tier})" },
            fluid = Materials.NatruralGas.getAnyFluid()
        )
        VirtualFluidVein(
            id = 6,
            name = "Helium-3",
            weight = 15.0,
            rangeSize = 100..650,
            color = color(128, 32, 224),
            dimensions = listOf(Moon, Mercury).map { it.id to "${it.name} (${it.tier})" },
            fluid = Materials.Helium_3.getAnyFluid()
        )
        VirtualFluidVein(
            id = 7,
            name = "Salt Water",
            weight = 10.0,
            rangeSize = 1..650,
            color = color(128, 255, 128),
            dimensions = listOf(Moon, Mars, Europa).map { it.id to "${it.name} (${it.tier})" },
            fluid = Materials.SaltWater.getAnyFluid()
        )
        VirtualFluidVein(
            id = 8,
            name = "Chlorobenzene",
            weight = 15.0,
            rangeSize = 150..400,
            color = color(64, 128, 64),
            dimensions = listOf(Mars).map { it.id to "${it.name} (${it.tier})" },
            fluid = Materials.Chlorobenzene.getAnyFluid()
        )
        VirtualFluidVein(
            id = 9,
            name = "Bacterial Sludge",
            weight = 18.0,
            rangeSize = 70..200,
            color = color(80, 250, 80),
            dimensions = listOf(Mars).map { it.id to "${it.name} (${it.tier})" },
            fluid = Materials.BacterialSludge.getAnyFluid()
        )
        VirtualFluidVein(
            id = 10,
            name = "Nitrogen",
            weight = 15.0,
            rangeSize = 70..200,
            color = color(0, 128, 208),
            dimensions = listOf(Deimos, Ceres, Haumea).map { it.id to "${it.name} (${it.tier})" },
            fluid = Materials.Nitrogen.getAnyFluid()
        )
        VirtualFluidVein(
            id = 11,
            name = "Extra Heavy Oil",
            weight = 15.0,
            rangeSize = 80..400,
            color = color(0, 0, 80),
            dimensions = listOf(Europa).map { it.id to "${it.name} (${it.tier})" },
            fluid = FluidStack(ItemList.sOilExtraHeavy, 0)
        )
        VirtualFluidVein(
            id = 12,
            name = "Distilled Water",
            weight = 15.0,
            rangeSize = 400..3500,
            color = color(53, 96, 171),
            dimensions = listOf(Europa).map { it.id to "${it.name} (${it.tier})" },
            fluid = GT_ModHandler.getDistilledWater(0)
        )
        VirtualFluidVein(
            id = 13,
            name = "Oxygen",
            weight = 20.0,
            rangeSize = 80..200,
            color = color(64, 64, 160),
            dimensions = listOf(Callisto, Makemake).map { it.id to "${it.name} (${it.tier})" },
            fluid = Materials.Oxygen.getAnyFluid()
        )
        VirtualFluidVein(
            id = 14,
            name = "Liquid Air",
            weight = 5.0,
            rangeSize = 10..300,
            color = color(64, 128, 64),
            dimensions = listOf(Callisto).map { it.id to "${it.name} (${it.tier})" },
            fluid = Materials.LiquidAir.getAnyFluid()
        )
        VirtualFluidVein(
            id = 15,
            name = "Helium",
            weight = 10.0,
            rangeSize = 1..400,
            color = color(255, 255, 0),
            dimensions = listOf(Ganymede, Makemake).map { it.id to "${it.name} (${it.tier})" },
            fluid = Materials.Helium.getAnyFluid()
        )
        VirtualFluidVein(
            id = 16,
            name = "Fluorine",
            weight = 15.0,
            rangeSize = 5..400,
            color = color(255, 255, 255),
            dimensions = listOf(Pluto, Ceres).map { it.id to "${it.name} (${it.tier})" },
            fluid = Materials.Fluorine.getAnyFluid()
        )
        VirtualFluidVein(
            id = 17,
            name = "Argon",
            weight = 12.0,
            rangeSize = 1..50,
            color = color(0, 255, 0),
            dimensions = listOf(Venus, Makemake).map { it.id to "${it.name} (${it.tier})" },
            fluid = Materials.Argon.getAnyFluid()
        )
        VirtualFluidVein(
            id = 18,
            name = "Lead",
            weight = 25.0,
            rangeSize = 100..600,
            color = color(208, 208, 208),
            dimensions = listOf(Venus).map { it.id to "${it.name} (${it.tier})" },
            fluid = Materials.Lead.getAnyFluid()
        )
        VirtualFluidVein(
            id = 19,
            name = "Carbon Dioxide",
            weight = 15.0,
            rangeSize = 5..1000,
            color = color(169, 208, 245),
            dimensions = listOf(Venus, Io).map { it.id to "${it.name} (${it.tier})" },
            fluid = Materials.CarbonDioxide.getAnyFluid()
        )
        VirtualFluidVein(
            id = 20,
            name = "Sulfuric Acid",
            weight = 15.0,
            rangeSize = 5..300,
            color = color(255, 128, 0),
            dimensions = listOf(Venus).map { it.id to "${it.name} (${it.tier})" },
            fluid = Materials.SulfuricAcid.getAnyFluid()
        )
        VirtualFluidVein(
            id = 21,
            name = "Lava",
            weight = 25.0,
            rangeSize = 600..2000,
            color = color(255, 0, 0),
            dimensions = listOf(Io).map { it.id to "${it.name} (${it.tier})" },
            fluid = Materials.Lava.getAnyFluid()
        )
        VirtualFluidVein(
            id = 22,
            name = "Methane",
            weight = 20.0,
            rangeSize = 200..800,
            color = color(128, 32, 32),
            dimensions = listOf(Titan).map { it.id to "${it.name} (${it.tier})" },
            fluid = Materials.Methane.getAnyFluid()
        )
        VirtualFluidVein(
            id = 23,
            name = "Ethane",
            weight = 18.0,
            rangeSize = 50..200,
            color = color(64, 128, 32),
            dimensions = listOf(Titan).map { it.id to "${it.name} (${it.tier})" },
            fluid = Materials.Ethane.getAnyFluid()
        )
        VirtualFluidVein(
            id = 24,
            name = "Hydric Sulfide",
            weight = 15.0,
            rangeSize = 200..900,
            color = color(255, 255, 255),
            dimensions = listOf(Miranda).map { it.id to "${it.name} (${it.tier})" },
            fluid = Materials.HydricSulfide.getAnyFluid()
        )
        VirtualFluidVein(
            id = 25,
            name = "Sulfuric Light Fuel",
            weight = 25.0,
            rangeSize = 100..500,
            color = color(255, 255, 0),
            dimensions = listOf(Miranda).map { it.id to "${it.name} (${it.tier})" },
            fluid = Materials.SulfuricLightFuel.getAnyFluid()
        )
        VirtualFluidVein(
            id = 26,
            name = "Sulfuric Naphtha",
            weight = 23.0,
            rangeSize = 100..500,
            color = color(255, 255, 0),
            dimensions = listOf(Oberon).map { it.id to "${it.name} (${it.tier})" },
            fluid = Materials.SulfuricNaphtha.getAnyFluid()
        )
        VirtualFluidVein(
            id = 27,
            name = "Sulfuric Heavy Fuel",
            weight = 23.0,
            rangeSize = 100..500,
            color = color(255, 255, 0),
            dimensions = listOf(Oberon).map { it.id to "${it.name} (${it.tier})" },
            fluid = Materials.SulfuricHeavyFuel.getAnyFluid()
        )
        VirtualFluidVein(
            id = 28,
            name = "Radon",
            weight = 5.0,
            rangeSize = 1..200,
            color = color(255, 0, 255),
            dimensions = listOf(Enceladus, Haumea).map { it.id to "${it.name} (${it.tier})" },
            fluid = Materials.Radon.getAnyFluid()
        )
        VirtualFluidVein(
            id = 29,
            name = "Sulfuric Gas",
            weight = 25.0,
            rangeSize = 200..500,
            color = color(255, 255, 0),
            dimensions = listOf(Enceladus).map { it.id to "${it.name} (${it.tier})" },
            fluid = Materials.SulfuricGas.getAnyFluid()
        )
        VirtualFluidVein(
            id = 30,
            name = "Glowstone",
            weight = 25.0,
            rangeSize = 1..400,
            color = color(255, 255, 0),
            dimensions = listOf(Enceladus).map { it.id to "${it.name} (${it.tier})" },
            fluid = Materials.Glowstone.getAnyFluid()
        )
        VirtualFluidVein(
            id = 31,
            name = "Indium",
            weight = 3.0,
            rangeSize = 1..300,
            color = color(64, 0, 128),
            dimensions = listOf(Triton).map { it.id to "${it.name} (${it.tier})" },
            fluid = Materials.Indium.getAnyFluid()
        )
        VirtualFluidVein(
            id = 32,
            name = "Naquadah",
            weight = 3.0,
            rangeSize = 50..200,
            color = color(32, 32, 32),
            dimensions = listOf(Triton).map { it.id to "${it.name} (${it.tier})" },
            fluid = Materials.Naquadah.getAnyFluid()
        )
        VirtualFluidVein(
            id = 33,
            name = "Toluene",
            weight = 30.0,
            rangeSize = 50..200,
            color = color(80, 29, 5),
            dimensions = listOf(Proteus).map { it.id to "${it.name} (${it.tier})" },
            fluid = Materials.Toluene.getAnyFluid()
        )
        VirtualFluidVein(
            id = 34,
            name = "Lutetium",
            weight = 5.0,
            rangeSize = 1..100,
            color = color(0, 170, 255),
            dimensions = listOf(Proteus).map { it.id to "${it.name} (${it.tier})" },
            fluid = Materials.Lutetium.getAnyFluid()
        )
        VirtualFluidVein(
            id = 35,
            name = "Ethylene",
            weight = 22.0,
            rangeSize = 400..800,
            color = color(208, 208, 208),
            dimensions = listOf(Pluto).map { it.id to "${it.name} (${it.tier})" },
            fluid = Materials.Ethylene.getAnyFluid()
        )
        VirtualFluidVein(
            id = 36,
            name = "Chlorine",
            weight = 15.0,
            rangeSize = 100..400,
            color = color(255, 255, 255),
            dimensions = listOf(Pluto).map { it.id to "${it.name} (${it.tier})" },
            fluid = Materials.Chlorine.getAnyFluid()
        )
        VirtualFluidVein(
            id = 37,
            name = "Enriched Naquadah",
            weight = 5.0,
            rangeSize = 50..200,
            color = color(96, 96, 96),
            dimensions = listOf(Makemake).map { it.id to "${it.name} (${it.tier})" },
            fluid = Materials.NaquadahEnriched.getAnyFluid()
        )
    }
    
    private fun Materials.getAnyFluid(): FluidStack {
        return getFluid(0) ?: getGas(0) ?: getMolten(0)!!
    }

    private fun Materials.toOreComponent(chance: Int): VirtualOreComponent {
        return VirtualOreComponent(
            chance = chance,
            ore = GT_OreDictUnificator.get(OrePrefixes.crushed, this, 1)
        )
    }

    private fun color(r: Int, g: Int, b: Int): Int {
        return Color(r, g, b).hashCode()
    }

    private fun Materials.color(): Int {
        return Color(
            rgba[0].toInt(),
            rgba[1].toInt(),
            rgba[2].toInt()
        ).hashCode()
    }
}