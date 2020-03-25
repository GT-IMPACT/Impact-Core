package com.impact.mods.GTSU.container;

import com.impact.mods.GTSU.tileentity.TileEntityGTSU;
import ic2.core.ContainerFullInv;
import ic2.core.slot.SlotArmor;
import ic2.core.slot.SlotInvSlot;
import net.minecraft.entity.player.EntityPlayer;

import java.util.List;

public class ContainerGTSU extends ContainerFullInv<TileEntityGTSU> {

	public ContainerGTSU(TileEntityGTSU tileentity, EntityPlayer player) {
		super(player, tileentity, 179, 166);
		for (int i = 0; i < 4; i++){
			addSlotToContainer(new SlotArmor(player.inventory, i, 152, 5 + i * 18));
		}

        addSlotToContainer(new SlotInvSlot(tileentity.mChargeSlot, 0, 128, 14));
        addSlotToContainer(new SlotInvSlot(tileentity.mDischargeSlot, 0, 128, 50));
	}

	@Override
	public List<String> getNetworkedFields(){
	    List<String> list = super.getNetworkedFields();
	    list.add("mEnergy");
	    list.add("mRedstoneMode");
	    return list;
	}

}
