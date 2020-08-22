package com.impact.mods.IC2.block.machine;

import com.impact.mods.IC2.item.IB_blockID;
import com.impact.mods.IC2.item.items;
import com.impact.mods.IC2.tileentities.IC_AdvancedHarvester;
import cpw.mods.fml.common.registry.GameRegistry;
import ic2.core.IC2;
import ic2.core.block.BlockMultiID;
import ic2.core.block.TileEntityBlock;
import ic2.core.block.machine.tileentity.TileEntitySortingMachine;
import ic2.core.init.InternalName;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import org.apache.commons.lang3.mutable.MutableObject;

import java.util.Random;

public class blockID extends BlockMultiID {

    public blockID(InternalName internalName1) {
        super(internalName1, Material.iron, IB_blockID.class);
        this.setHarvestLevel("wrench", 0);
        this.setHardness(2.0F);
        this.setStepSound(soundTypeMetal);
        items.advCropHarvester = new ItemStack(this, 1, 0);
        GameRegistry.registerTileEntity(IC_AdvancedHarvester.class, "Advanced Crop Harvester");
    }

    public String getTextureFolder(int id) {
        return "machine";
    }

    public Item getItemDropped(int meta, Random random, int fortune) {
        return Item.getItemFromBlock(this);
    }

    public int damageDropped(int meta) {
            return meta;
    }

    @Override
    public Class<? extends TileEntity> getTeClass(int meta, MutableObject<Class<?>[]> mutableObject, MutableObject<Object[]> mutableObject1) {
        switch (meta) {
            case 0:
                return IC_AdvancedHarvester.class;
            default:
                return null;
        }
    }

    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
        if(IC2.platform.isSimulating()) {
            super.onBlockPlacedBy(world, x, y, z, entity, stack);
            TileEntityBlock te = (TileEntityBlock) this.getOwnTe(world, x, y, z);
            if(te != null) {
                if(te instanceof TileEntitySortingMachine) {
                    te.setFacing((short)2);
                }
            }
        }
    }

    public boolean hasComparatorInputOverride() {
        return true;
    }
}
