package com.impact.mods.gregtech.gui.processing;

import com.cleanroommc.modularui.screen.viewport.ModularGuiContext;
import com.cleanroommc.modularui.theme.WidgetThemeEntry;
import com.cleanroommc.modularui.widgets.ButtonWidget;
import net.minecraft.client.gui.Gui;

/** Slot-style control with the same hover highlight as the GT processing controls. */
final class ImpactControlButton extends ButtonWidget<ImpactControlButton> {

    private boolean pressed;

    @Override
    public Result onMousePressed(int mouseButton) {
        Result result = super.onMousePressed(mouseButton);
        pressed = result == Result.SUCCESS;
        return result;
    }

    @Override
    public boolean onMouseRelease(int mouseButton) {
        pressed = false;
        return super.onMouseRelease(mouseButton);
    }

    @Override
    public void drawOverlay(ModularGuiContext context, WidgetThemeEntry<?> widgetTheme) {
        super.drawOverlay(context, widgetTheme);
        if (isHovering()) {
            Gui.drawRect(1, 1, 17, 17, pressed ? 0x60000000 : 0x80ffffff);
        }
    }
}
