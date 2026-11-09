package com.impact.util;

import gregtech.api.interfaces.ICasing;
import gregtech.api.util.GTStructureUtility;
import gregtech.api.util.MultiblockTooltipBuilder;
import net.minecraft.item.ItemStack;
import space.impact.structures.hint.HintColor;

public class ImpactMultiblockTooltipBuilder extends MultiblockTooltipBuilder {

    public ImpactMultiblockTooltipBuilder(String machineName) {
        super(machineName);
    }

    @Override
    public ImpactMultiblockTooltipBuilder useCompactStructure() {
        super.useCompactStructure();
        return this;
    }

    @Override
    public ImpactMultiblockTooltipBuilder addMachineType(String machine) {
        super.addMachineType(machine);
        return this;
    }

    @Override
    public ImpactMultiblockTooltipBuilder addInfo(String info) {
        super.addInfo(info);
        return this;
    }

    @Override
    public ImpactMultiblockTooltipBuilder addInfo(String info, Object... arguments) {
        super.addInfo(info, arguments);
        return this;
    }

    @Override
    public ImpactMultiblockTooltipBuilder addControlInfo(String info) {
        super.addControlInfo(info);
        return this;
    }

    @Override
    public ImpactMultiblockTooltipBuilder beginStructureBlock(int width, int height, int depth, boolean hollow) {
        super.beginStructureBlock(width, height, depth, hollow);
        return this;
    }

    @Override
    public ImpactMultiblockTooltipBuilder beginVariableStructureBlock(
            int minWidth,
            int maxWidth,
            int minHeight,
            int maxHeight,
            int minDepth,
            int maxDepth,
            boolean hollow
    ) {
        super.beginVariableStructureBlock(minWidth, maxWidth, minHeight, maxHeight, minDepth, maxDepth, hollow);
        return this;
    }

    @Override
    public ImpactMultiblockTooltipBuilder addController(String location) {
        super.addController(location);
        return this;
    }

    @Override
    public ImpactMultiblockTooltipBuilder addStructureSection(String title) {
        super.addStructureSection(title);
        return this;
    }

    @Override
    public ImpactMultiblockTooltipBuilder addOtherStructurePart(ICasing casing, String location) {
        super.addOtherStructurePart(casing, location);
        return this;
    }

    @Override
    public ImpactMultiblockTooltipBuilder addOtherStructurePart(String name, String location, int... dots) {
        super.addOtherStructurePart(name, location, dots);
        return this;
    }

    @Override
    public ImpactMultiblockTooltipBuilder addOtherStructurePart(ICasing casing, String location, String expression) {
        super.addOtherStructurePart(casing, location, expression);
        return this;
    }

    @Override
    public ImpactMultiblockTooltipBuilder addOtherStructurePart(ItemStack stack, String location) {
        super.addOtherStructurePart(stack, location);
        return this;
    }

    @Override
    public ImpactMultiblockTooltipBuilder addStructurePartInfo(ICasing casing, String location, Object... arguments) {
        super.addStructurePartInfo(casing, location, arguments);
        return this;
    }

    @Override
    public ImpactMultiblockTooltipBuilder addAir(int count, String location) {
        super.addAir(count, location);
        return this;
    }

    @Override
    public ImpactMultiblockTooltipBuilder addStructureInfo(String info) {
        super.addStructureInfo(info);
        return this;
    }

    @Override
    public ImpactMultiblockTooltipBuilder addHatchInfo(String name, String requirement) {
        super.addHatchInfo(name, requirement);
        return this;
    }

    @Override
    public ImpactMultiblockTooltipBuilder addStructureHint(String name, HintColor color) {
        super.addStructureHint(name, color);
        return this;
    }

    @Override
    public ImpactMultiblockTooltipBuilder addSubChannel(String channel, String description) {
        super.addSubChannel(channel, description);
        return this;
    }

    @Override
    public ImpactMultiblockTooltipBuilder addSubChannel(String channel, String description, String expression) {
        super.addSubChannel(channel, description, expression);
        return this;
    }

    public ImpactMultiblockTooltipBuilder addParallelInfo() {
        addInfo("Parallel points: base 1, maximum 256");
        return this;
    }

    public ImpactMultiblockTooltipBuilder addHatchChannelInfo() {
        addSubChannel(GTStructureUtility.HATCH_CHANNEL, "1: available hatches in free positions; existing casings are kept.");
        return this;
    }
}
