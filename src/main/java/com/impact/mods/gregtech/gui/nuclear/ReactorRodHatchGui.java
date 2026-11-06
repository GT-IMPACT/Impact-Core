package com.impact.mods.gregtech.gui.nuclear;

import com.cleanroommc.modularui.api.drawable.IDrawable;
import com.cleanroommc.modularui.drawable.UITexture;
import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.screen.UISettings;
import com.cleanroommc.modularui.screen.viewport.ModularGuiContext;
import com.cleanroommc.modularui.theme.WidgetThemeEntry;
import com.cleanroommc.modularui.utils.serialization.ByteBufAdapters;
import com.cleanroommc.modularui.value.sync.GenericSyncValue;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.cleanroommc.modularui.widget.Widget;
import com.cleanroommc.modularui.widgets.slot.ItemSlot;
import com.impact.mods.gregtech.tileentities.multi.generators.nuclear.hatch.GTMTE_Reactor_Rod_Hatch;
import gregtech.api.modularui2.GTGuis;
import gregtech.api.modularui2.GTGuiTextures;
import gregtech.common.gui.modularui.util.MachineModularSlot;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

public final class ReactorRodHatchGui {

    private ReactorRodHatchGui() {
    }

    public static ModularPanel build(
            GTMTE_Reactor_Rod_Hatch hatch,
            PosGuiData data,
            PanelSyncManager sync,
            UISettings settings
    ) {
        GenericSyncValue<NBTTagCompound, ?> state = GenericSyncValue.builder(NBTTagCompound.class)
                .getter(() -> {
                    NBTTagCompound snapshot = new NBTTagCompound();
                    if (!sync.isClient()) {
                        snapshot.setBoolean("ready", true);
                        snapshot.setInteger("id", hatch.mIDhatch);
                        snapshot.setInteger("position", hatch.mDownRod);
                    }
                    return snapshot;
                })
                .adapter(ByteBufAdapters.NBT)
                .build();
        sync.syncValue("rodState", state);
        sync.registerSlotGroup("fuel", 0);
        return GTGuis.mteTemplatePanelBuilder(hatch, data, sync, settings)
                .doesAddTitle(false)
                .doesAddCoverTabs(false)
                .doesAddGregTechLogo(false)
                .doesAddGhostCircuitSlot(false)
                .build()
                .child(new RodDisplay(state).pos(4, 4).size(168, 74))
                .child(new ItemSlot()
                        .name("reactor_rod_fuel")
                        .slot(new MachineModularSlot(hatch.getInventoryHandler(), 0, hatch.getBaseMetaTileEntity())
                                .slotGroup("fuel"))
                        .background(GTGuiTextures.SLOT_ITEM_STANDARD)
                        .pos(146, 30))
                .child(RodPositionSlider.create(hatch, sync, () -> hatch.mDownRod, hatch::setRodPosition)
                        .pos(10, 58)
                        .size(126, 12)
                        .setEnabledIf(widget -> state.getValue().getBoolean("ready")));
    }

    private static final class RodDisplay extends Widget<RodDisplay> {

        private static final UITexture SCREEN = UITexture.builder()
                .location("gregtech", "gui/picture/screen_black")
                .imageSize(16, 16)
                .adaptable(2)
                .build();
        private final GenericSyncValue<NBTTagCompound, ?> state;

        private RodDisplay(GenericSyncValue<NBTTagCompound, ?> state) {
            this.state = state;
            background(IDrawable.EMPTY);
        }

        @Override
        public void drawBackground(ModularGuiContext context, WidgetThemeEntry<?> theme) {
            SCREEN.draw(0, 0, 168, 74);
        }

        @Override
        public void draw(ModularGuiContext context, WidgetThemeEntry<?> theme) {
            FontRenderer font = Minecraft.getMinecraft().fontRenderer;
            font.drawString(StatCollector.translateToLocal("impact.reactor.rod_title"), 6, 6, 0x8999aa);
            NBTTagCompound snapshot = state.getValue();
            if (snapshot.getBoolean("ready")) {
                font.drawString(StatCollector.translateToLocalFormatted("impact.reactor.rod_id",
                        snapshot.getInteger("id") + 1), 6, 20, 0xe4eaf0);
                font.drawString(StatCollector.translateToLocalFormatted("impact.reactor.rod_depth",
                        snapshot.getInteger("position") * 10), 6, 32, 0x73ce8d);
            }
        }
    }
}
