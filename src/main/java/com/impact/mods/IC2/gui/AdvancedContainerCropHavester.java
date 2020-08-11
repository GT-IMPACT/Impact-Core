package com.impact.mods.IC2.gui;

import com.impact.mods.IC2.tileentities.IC_AdvancedHarvester;
import ic2.core.block.machine.container.ContainerElectricMachine;
import ic2.core.slot.SlotInvSlot;
import net.minecraft.entity.player.EntityPlayer;

import java.util.List;

public class AdvancedContainerCropHavester extends ContainerElectricMachine<IC_AdvancedHarvester> {
    public AdvancedContainerCropHavester(EntityPlayer entityPlayer, IC_AdvancedHarvester base) {
        super(entityPlayer, base, 191, 152, 58);

        for (int y = 0; y < base.contentSlot.size() / 5; ++y) {
            for (int x = 0; x < 5; ++x) {
                this.addSlotToContainer(new SlotInvSlot(base.contentSlot, x + y * 5, 44 + x * 18, 22 + y * 18));
            }
        }

        this.addSlotToContainer(new SlotInvSlot(base.upgradeSlot, 0, 80, 80));
        this.addSlotToContainer(new SlotInvSlot(base.cropnalyzerSlot, 0, 15, 40));
    }

    public List<String> getNetworkedFields() {
        List<String> ret = super.getNetworkedFields();
        ret.add("energy");
        return ret;
    }
}