package com.impact.util.string;

import gregtech.api.interfaces.IItemContainer;
import net.minecraft.block.Block;
import net.minecraft.util.EnumChatFormatting;

import java.util.LinkedList;
import java.util.List;

import static com.impact.util.Utilits.Blockstack;
import static com.impact.util.Utilits.impactTag;
import static com.impact.util.string.Lang.*;

/**
 * Have you ever felt like your tooltips just aren't enterprise enough? Use this!
 */
public class MultiBlockTooltipBuilder {
	
	public static final char WHITE = EnumChatFormatting.WHITE.getFormattingCode();
	public static final char ORANGE = EnumChatFormatting.GOLD.getFormattingCode();
	public static final char MAGENTA = EnumChatFormatting.DARK_RED.getFormattingCode();
	public static final char L_BLUE = EnumChatFormatting.BLUE.getFormattingCode();
	public static final char YELLOW = EnumChatFormatting.YELLOW.getFormattingCode();
	public static final char LIME = EnumChatFormatting.GREEN.getFormattingCode();
	public static final char PINK = EnumChatFormatting.LIGHT_PURPLE.getFormattingCode();
	public static final char GRAY = EnumChatFormatting.DARK_GRAY.getFormattingCode();
	public static final char L_GRAY = EnumChatFormatting.GRAY.getFormattingCode();
	public static final char CYAN = EnumChatFormatting.DARK_AQUA.getFormattingCode();
	public static final char PURPLE = EnumChatFormatting.DARK_PURPLE.getFormattingCode();
	public static final char BLUE = EnumChatFormatting.DARK_BLUE.getFormattingCode();
	public static final char GREEN = EnumChatFormatting.DARK_GREEN.getFormattingCode();
	public static final char RED = EnumChatFormatting.RED.getFormattingCode();
	public static final char BLACK = EnumChatFormatting.BLACK.getFormattingCode();
	private static final String TAB = "   ";
	private final List<String> iLines;
	private final List<String> sLines;
	private final List<String> cLines;
	private final String prefix;
	private String[] iArray;
	private String[] sArray;
	private String[] cArray;
	
	public MultiBlockTooltipBuilder() {
		iLines    = new LinkedList<>();
		sLines    = new LinkedList<>();
		cLines    = new LinkedList<>();
		prefix    = "";
	}
	
	public MultiBlockTooltipBuilder(String aPrefix) {
		iLines    = new LinkedList<>();
		sLines    = new LinkedList<>();
		cLines    = new LinkedList<>();
		prefix    = aPrefix + ".";
	}
	
	public String trans(String aKey, String aEnglish) {
		return Language.transMTB(prefix + aKey, aEnglish);
	}
	
	/**
	 * Add a basic line of information about this structure
	 *
	 * @param // line The line to be added.
	 * @return Instance this method was called on.
	 */
	public MultiBlockTooltipBuilder addInfo(String aKey, String aEnglish) {
		iLines.add(trans(aKey, aEnglish));
		return this;
	}
	
	public MultiBlockTooltipBuilder addInfo(String info, boolean notTrans) {
		iLines.add(info);
		return this;
	}
	
	public MultiBlockTooltipBuilder addEffectiveLoss() {
		iLines.add(efficiency_loss.get());
		return this;
	}
	
	public MultiBlockTooltipBuilder addMultiAmpGen() {
		iLines.add(Lang.multi_amperes_generator.get());
		return this;
	}
	
	/**
	 * Add a separator line like this:<br> -------------------------------
	 *
	 * @return Instance this method was called on.
	 */
	public MultiBlockTooltipBuilder addSeparator() {
		//iLines.add("---------------------------------");
		iLines.add(" ");
		return this;
	}
	
	public MultiBlockTooltipBuilder addParallelInfo(int aBasic, int aMax) {
		iLines.add(
				EnumChatFormatting.AQUA + parallel_point.get() + ": " +
						EnumChatFormatting.GRAY + parallel_point_basic.get() + " " + EnumChatFormatting.AQUA + aBasic +
						EnumChatFormatting.GRAY + ", " + max.get() + " " + EnumChatFormatting.AQUA + aMax);
		return this;
	}
	
	public MultiBlockTooltipBuilder addPollution(int Min, int Max) {
		iLines.add(pollution.get() + ": " +
				min.get() + " " + Min + ", " +
				max.get() + " " + Max);
		return this;
	}
	
	public MultiBlockTooltipBuilder addPollution(int aPollut) {
		iLines.add(pollution.get() + " " + per_second.get() + ": " + aPollut);
		return this;
	}
	
	public MultiBlockTooltipBuilder addPollution(int aPollut, String aKey, String aEng) {
		iLines.add(pollution.get() + " " + per_second.get() + ": " + aPollut + " " + trans(aKey, aEng));
		return this;
	}
	
	public MultiBlockTooltipBuilder addTypeMachine(String aKey, String aEng) {
		iLines.add(EnumChatFormatting.YELLOW + trans(aKey, aEng));
		return this;
	}
	
	public MultiBlockTooltipBuilder addTypeGenerator() {
		iLines.add(EnumChatFormatting.YELLOW + generator.get());
		return this;
	}
	
	public MultiBlockTooltipBuilder addTypeSteam() {
		iLines.add(EnumChatFormatting.YELLOW + steam_producer.get());
		return this;
	}
	
	public MultiBlockTooltipBuilder addScrew() {
		iLines.add(screw_type.get());
		return this;
	}
	
	public MultiBlockTooltipBuilder addScrew(String aKey, String aEng) {
		iLines.add(select.get() + " " + trans(aKey, aEng) + " " + screw_type_different.get());
		return this;
	}
	
	public MultiBlockTooltipBuilder addSeparatedBus() {
		iLines.add(separated_bus.get());
		return this;
	}
	
	public MultiBlockTooltipBuilder addSingleAnalog() {
		iLines.add(single_analog.get());
		return this;
	}
	
	/**
	 * Begin adding structural information by adding a line about the structure's dimensions and then
	 * inserting a "Structure:" line.
	 *
	 * @param // w Structure width.
	 * @param // h Structure height.
	 * @param // l Structure depth/length.
	 * @return Instance this method was called on.
	 */
	
	public MultiBlockTooltipBuilder addParallelHatch(String aKey, String aEng) {
		sLines.add(TAB + EnumChatFormatting.WHITE + parallel_hatch.get() + ": " + EnumChatFormatting.GRAY + trans(aKey, aEng));
		return this;
	}
	
	public MultiBlockTooltipBuilder addParallelHatch() {
		sLines.add(TAB + EnumChatFormatting.WHITE + parallel_hatch.get() + ": " + EnumChatFormatting.GRAY + any_case.get() + " (max 1x)");
		return this;
	}
	
	public MultiBlockTooltipBuilder addParallelHatch(String aKey, String aEng, int amount) {
		sLines.add(TAB + EnumChatFormatting.WHITE + parallel_hatch.get() + ": " + EnumChatFormatting.GRAY + trans(aKey, aEng) + " (" + max.get() + " x" + amount + ")");
		return this;
	}
	
	public MultiBlockTooltipBuilder addParallelHatch(int amount) {
		sLines.add(TAB + EnumChatFormatting.WHITE + parallel_hatch.get() + ": " + EnumChatFormatting.GRAY + any_case.get() + " (" + max.get() + " x" + amount + ")");
		return this;
	}
	
	public MultiBlockTooltipBuilder addinfoB(String aKey, String aEng) {
		cLines.add(trans(aKey, aEng));
		return this;
	}
	
	public MultiBlockTooltipBuilder addinfoBTab(String aKey, String aEng) {
		cLines.add(TAB + trans(aKey, aEng));
		return this;
	}
	
	public MultiBlockTooltipBuilder beginStructureBlock(int w, int h, int l) {
//        sLines.add(begin_structure_block .get();
		sLines.add(structure.get() + ":");
		return this;
	}
	
	public MultiBlockTooltipBuilder sizeStructure(int w, int h, int l) {
		sLines.add(dimensions.get() + ": " + w + "x" + h + "x" + l + " " + wxhxl.get());
		sLines.add(structure.get() + ":");
		return this;
	}
	
	public MultiBlockTooltipBuilder addController() {
		sLines.add(EnumChatFormatting.YELLOW + String.format(blueprint.get(), EnumChatFormatting.RED + "ImpactAPI" + EnumChatFormatting.YELLOW));
		return this;
	}
	
	public MultiBlockTooltipBuilder addCasingInfo(String aKey, String aEng) {
		sLines.add(TAB + EnumChatFormatting.WHITE + trans(aKey, aEng) + EnumChatFormatting.GRAY + " (" + at_least.get() + ")");
		return this;
	}
	
	public MultiBlockTooltipBuilder addCasingInfo(String aKey, String aEng, int minAmount) {
		sLines.add(TAB + EnumChatFormatting.WHITE + trans(aKey, aEng) + EnumChatFormatting.GRAY + " ("+ min.get() +" x" + minAmount +")");
		return this;
	}
	
	public MultiBlockTooltipBuilder addCasingInfo(Block block, int meta) {
		sLines.add(TAB + EnumChatFormatting.WHITE + Blockstack(block, 1, meta).getDisplayName() + EnumChatFormatting.GRAY + " (" + at_least.get() + ")");
		return this;
	}
	
	public MultiBlockTooltipBuilder addCasingInfo(IItemContainer item) {
		sLines.add(TAB + EnumChatFormatting.WHITE + item.get(1).getDisplayName() + EnumChatFormatting.GRAY + " (" + at_least.get() + ")");
		return this;
	}
	
	public MultiBlockTooltipBuilder addEnergyHatch() {
		sLines.add(TAB + EnumChatFormatting.WHITE + hatch_energy.get() + ": " + EnumChatFormatting.GRAY + any_case.get());
		return this;
	}
	
	public MultiBlockTooltipBuilder addEnergyHatch(int amount) {
		sLines.add(TAB + EnumChatFormatting.WHITE + hatch_energy.get() + ": " + EnumChatFormatting.GRAY + any_case.get() + " (" + max.get() + " x" + amount + ")");
		return this;
	}
	
	public MultiBlockTooltipBuilder addEnergyHatch(String aKey, String aEng) {
		sLines.add(TAB + EnumChatFormatting.WHITE + hatch_energy.get() + ": " + EnumChatFormatting.GRAY + trans(aKey, aEng));
		return this;
	}
	
	public MultiBlockTooltipBuilder addEnergyHatch(String aKey, String aEng, int amount) {
		sLines.add(TAB + EnumChatFormatting.WHITE + hatch_energy.get() + ": " + EnumChatFormatting.GRAY + trans(aKey, aEng) + " (" + max.get() + " x" + amount + ")");
		return this;
	}
	
	public MultiBlockTooltipBuilder addDynamoHatch() {
		sLines.add(TAB + EnumChatFormatting.WHITE + hatch_dynamo.get() + ": " +
				EnumChatFormatting.GRAY + any_case.get());
		return this;
	}
	
	public MultiBlockTooltipBuilder addDynamoHatch(int amount) {
		sLines.add(TAB + EnumChatFormatting.WHITE + hatch_dynamo.get() + ": " +
				EnumChatFormatting.GRAY + any_case.get() + " (" + max.get() + " x" + amount + ")");
		return this;
	}
	
	public MultiBlockTooltipBuilder addDynamoHatch(String aKey, String aEng) {
		sLines.add(TAB + EnumChatFormatting.WHITE + hatch_dynamo.get() + ": " +
				EnumChatFormatting.GRAY + trans(aKey, aEng));
		return this;
	}
	
	public MultiBlockTooltipBuilder addDynamoHatch(String aKey, String aEng, int amount) {
		sLines.add(TAB + EnumChatFormatting.WHITE + hatch_dynamo.get() + ": " +
				EnumChatFormatting.GRAY + trans(aKey, aEng) + " (" + max.get() + " x" + amount + ")");
		return this;
	}
	
	public MultiBlockTooltipBuilder addMuffler() {
		sLines.add(TAB + EnumChatFormatting.WHITE + hatch_muffler.get() + ": " + EnumChatFormatting.GRAY + any_case.get() + " (max 1x)");
		return this;
	}
	
	public MultiBlockTooltipBuilder addMuffler(String aKey, String aEng) {
		sLines.add(TAB + EnumChatFormatting.WHITE + hatch_muffler.get() + ": " + EnumChatFormatting.GRAY + trans(aKey, aEng));
		return this;
	}
	
	public MultiBlockTooltipBuilder addMuffler(String aKey, String aEng, int amount) {
		sLines.add(TAB + EnumChatFormatting.WHITE + hatch_muffler.get() + ": " + EnumChatFormatting.GRAY + trans(aKey, aEng) + " (" + max.get() + " x" + amount + ")");
		return this;
	}
	
	
	public MultiBlockTooltipBuilder addMaintenanceHatch() {
		sLines.add(TAB + EnumChatFormatting.WHITE + hatch_maintenance.get() + ": " + EnumChatFormatting.GRAY + any_case.get() + " (max 1x)");
		return this;
	}
	
	public MultiBlockTooltipBuilder addMaintenanceHatch(String aKey, String aEng) {
		sLines.add(TAB + EnumChatFormatting.WHITE + hatch_maintenance.get() + ": " + EnumChatFormatting.GRAY + trans(aKey, aEng));
		return this;
	}
	
	public MultiBlockTooltipBuilder addMaintenanceHatch(String aKey, String aEng, int amount) {
		sLines.add(TAB + EnumChatFormatting.WHITE + hatch_maintenance.get() + ": " + EnumChatFormatting.GRAY + trans(aKey, aEng) + " (" + max.get() + " x" + amount + ")");
		return this;
	}
	
	public MultiBlockTooltipBuilder addIOHatches() {
		sLines.add(TAB + EnumChatFormatting.WHITE + hatch_io.get() + ": " +
				EnumChatFormatting.GRAY + any_case.get());
		return this;
	}
	
	public MultiBlockTooltipBuilder addIOHatches(int amount) {
		sLines.add(TAB + EnumChatFormatting.WHITE + hatch_io.get() + ": " +
				EnumChatFormatting.GRAY + any_case.get() + " (" + max.get() + " x" + amount + ")");
		return this;
	}
	
	public MultiBlockTooltipBuilder addIOHatches(String aKey, String aEng) {
		sLines.add(TAB + EnumChatFormatting.WHITE + hatch_io.get() + ": " +
				EnumChatFormatting.GRAY + trans(aKey, aEng));
		return this;
	}
	
	public MultiBlockTooltipBuilder addIOHatches(String aKey, String aEng, int amount) {
		sLines.add(TAB + EnumChatFormatting.WHITE + hatch_io.get() + ": " +
				EnumChatFormatting.GRAY + trans(aKey, aEng) + " (" + max.get() + " x" + amount + ")");
		return this;
	}
	
	public MultiBlockTooltipBuilder addInputBus() {
		sLines.add(TAB + EnumChatFormatting.WHITE + hatch_bus_in.get() + ": " +
				EnumChatFormatting.GRAY + any_case.get());
		return this;
	}
	
	public MultiBlockTooltipBuilder addInputBus(int amount) {
		sLines.add(TAB + EnumChatFormatting.WHITE + hatch_bus_in.get() + ": " +
				EnumChatFormatting.GRAY + any_case.get() + " (" + max.get() + " x" + amount + ")");
		return this;
	}
	
	public MultiBlockTooltipBuilder addInputBus(String aKey, String aEng) {
		sLines.add(TAB + EnumChatFormatting.WHITE + hatch_bus_in.get() + ": " +
				EnumChatFormatting.GRAY + trans(aKey, aEng));
		return this;
	}

	public MultiBlockTooltipBuilder addInputBus(String aKey, String aEng, int amount) {
		sLines.add(TAB + EnumChatFormatting.WHITE + hatch_bus_in.get() + ": " +
				EnumChatFormatting.GRAY + trans(aKey, aEng) + " (" + max.get() + " x" + amount + ")");
		return this;
	}
	
	public MultiBlockTooltipBuilder addNuclearRod(String aKey, String aEng) {
		sLines.add(TAB + EnumChatFormatting.WHITE + hatch_nuclear.get() + ": " +
				EnumChatFormatting.GRAY + trans(aKey, aEng));
		return this;
	}
	
	public MultiBlockTooltipBuilder addNuclearRod(String aKey, String aEng, int amount) {
		sLines.add(TAB + EnumChatFormatting.WHITE + hatch_nuclear.get() + ": " +
				EnumChatFormatting.GRAY + trans(aKey, aEng) + " (" + max.get() + " x" + amount + ")");
		return this;
	}
	
	public MultiBlockTooltipBuilder addInputHatch() {
		sLines.add(TAB + EnumChatFormatting.WHITE + hatch_in.get() + ": " +
				EnumChatFormatting.GRAY + any_case.get());
		return this;
	}
	
	public MultiBlockTooltipBuilder addInputHatch(int amount) {
		sLines.add(TAB + EnumChatFormatting.WHITE + hatch_in.get() + ": " +
				EnumChatFormatting.GRAY + any_case.get() + " (" + max.get() + " x" + amount + ")");
		return this;
	}
	
	public MultiBlockTooltipBuilder addInputHatch(String aKey, String aEng) {
		sLines.add(TAB + EnumChatFormatting.WHITE + hatch_in.get() + ": " +
				EnumChatFormatting.GRAY + trans(aKey, aEng));
		return this;
	}
	
	public MultiBlockTooltipBuilder addInputHatch(String aKey, String aEng, int amount) {
		sLines.add(TAB + EnumChatFormatting.WHITE + hatch_in.get() + ": " +
				EnumChatFormatting.GRAY + trans(aKey, aEng) + " (" + max.get() + " x" + amount + ")");
		return this;
	}
	
	public MultiBlockTooltipBuilder addOutputBus() {
		sLines.add(TAB + EnumChatFormatting.WHITE + hatch_bus_out.get() + ": " +
				EnumChatFormatting.GRAY + any_case.get());
		return this;
	}
	
	public MultiBlockTooltipBuilder addOutputBus(int amount) {
		sLines.add(TAB + EnumChatFormatting.WHITE + hatch_bus_out.get() + ": " +
				EnumChatFormatting.GRAY + any_case.get() + " (" + max.get() + " x" + amount + ")");
		return this;
	}
	
	public MultiBlockTooltipBuilder addOutputBus(String aKey, String aEng) {
		sLines.add(TAB + EnumChatFormatting.WHITE + hatch_bus_out.get() + ": " +
				EnumChatFormatting.GRAY + trans(aKey, aEng));
		return this;
	}
	
	public MultiBlockTooltipBuilder addOutputBus(String aKey, String aEng, int amount) {
		sLines.add(TAB + EnumChatFormatting.WHITE + hatch_bus_out.get() + ": " +
				EnumChatFormatting.GRAY + trans(aKey, aEng) + " (" + max.get() + " x" + amount + ")");
		return this;
	}
	
	public MultiBlockTooltipBuilder addOutputHatch() {
		sLines.add(TAB + EnumChatFormatting.WHITE + hatch_out.get() + ": " +
				EnumChatFormatting.GRAY + any_case.get());
		return this;
	}
	
	public MultiBlockTooltipBuilder addOutputHatch(int amount) {
		sLines.add(TAB + EnumChatFormatting.WHITE + hatch_out.get() + ": " +
				EnumChatFormatting.GRAY + any_case.get() + " (" + max.get() + " x" + amount + ")");
		return this;
	}
	
	public MultiBlockTooltipBuilder addOutputHatch(String aKey, String aEng) {
		sLines.add(TAB + EnumChatFormatting.WHITE + hatch_out.get() + ": " +
				EnumChatFormatting.GRAY + trans(aKey, aEng));
		return this;
	}
	
	public MultiBlockTooltipBuilder addOutputHatch(String aKey, String aEng, int amount) {
		sLines.add(TAB + EnumChatFormatting.WHITE + hatch_out.get() + ": " +
				EnumChatFormatting.GRAY + trans(aKey, aEng) + " (" + max.get() + " x" + amount + ")");
		return this;
	}
	
	/**
	 * Use this method to add a structural part that isn't covered by the builders capabilities.
	 *
	 * @param aKey  Name of the hatch or other component.
	 * @param aKey2 Positional information.
	 * @return Instance this method was called on.
	 */
	public MultiBlockTooltipBuilder addOtherStructurePart(String aKey, String aEng, String aKey2, String aEng2) {
		sLines.add(TAB + EnumChatFormatting.WHITE + trans(aKey, aEng) + ": " + EnumChatFormatting.GRAY + trans(aKey2, aEng2));
		return this;
	}
	
	public MultiBlockTooltipBuilder addOtherStructurePartAny(String aKey, String aEng) {
		sLines.add(TAB + EnumChatFormatting.WHITE + trans(aKey, aEng) + ": " + EnumChatFormatting.GRAY + any_case.get());
		return this;
	}
	
	public MultiBlockTooltipBuilder addOtherStructurePartAny(String aKey, String aEng, int minAmount) {
		sLines.add(TAB + EnumChatFormatting.WHITE + trans(aKey, aEng) + ": " + EnumChatFormatting.GRAY + "(" + min.get() + " x" + minAmount + ")");
		return this;
	}
	
	public MultiBlockTooltipBuilder addOtherStructurePartAny(String aKey, String aEng, int maxAmount, boolean isMax) {
		sLines.add(TAB + EnumChatFormatting.WHITE + trans(aKey, aEng) + ": " + EnumChatFormatting.GRAY + "(" + max.get() + " x" + maxAmount + ")");
		return this;
	}
	
	/**
	 * Hologram Info
	 */
	
	public MultiBlockTooltipBuilder addHints() {
		return this;
	}
	
	public MultiBlockTooltipBuilder addWhiteHint(String aEng) {
		sLines.add(EnumChatFormatting.WHITE + "▉" + EnumChatFormatting.RESET + " - " + trans("white", aEng));
		return this;
	}
	
	public MultiBlockTooltipBuilder addOrangeHint(String aEng) {
		sLines.add(EnumChatFormatting.GOLD + "▉" + EnumChatFormatting.RESET + " - " + trans("orange", aEng));
		return this;
	}
	
	public MultiBlockTooltipBuilder addMagentaHint(String aEng) {
		sLines.add(EnumChatFormatting.DARK_RED + "▉" + EnumChatFormatting.RESET + " - " + trans("magenta", aEng));
		return this;
	}
	
	public MultiBlockTooltipBuilder addLBlueHint(String aEng) {
		sLines.add(EnumChatFormatting.BLUE + "▉" + EnumChatFormatting.RESET + " - " + trans("l_blue", aEng));
		return this;
	}
	
	public MultiBlockTooltipBuilder addYellowHint(String aEng) {
		sLines.add(EnumChatFormatting.YELLOW + "▉" + EnumChatFormatting.RESET + " - " + trans("yellow", aEng));
		return this;
	}
	
	public MultiBlockTooltipBuilder addLimeHint(String aEng) {
		sLines.add(EnumChatFormatting.GREEN + "▉" + EnumChatFormatting.RESET + " - " + trans("lime", aEng));
		return this;
	}
	
	public MultiBlockTooltipBuilder addPinkHint(String aEng) {
		sLines.add(EnumChatFormatting.LIGHT_PURPLE + "▉" + EnumChatFormatting.RESET + " - " + trans("pink", aEng));
		return this;
	}
	
	public MultiBlockTooltipBuilder addGrayHint(String aEng) {
		sLines.add(EnumChatFormatting.DARK_GRAY + "▉" + EnumChatFormatting.RESET + " - " + trans("gray", aEng));
		return this;
	}
	
	public MultiBlockTooltipBuilder addLGrayHint(String aEng) {
		sLines.add(EnumChatFormatting.GRAY + "▉" + EnumChatFormatting.RESET + " - " + trans("l_gray", aEng));
		return this;
	}
	
	public MultiBlockTooltipBuilder addCyanHint(String aEng) {
		sLines.add(EnumChatFormatting.DARK_AQUA + "▉" + EnumChatFormatting.RESET + " - " + trans("cyan", aEng));
		return this;
	}
	
	public MultiBlockTooltipBuilder addPurpleHint(String aEng) {
		sLines.add(EnumChatFormatting.DARK_PURPLE + "▉" + EnumChatFormatting.RESET + " - " + trans("purple", aEng));
		return this;
	}
	
	public MultiBlockTooltipBuilder addBlueHint(String aEng) {
		sLines.add(EnumChatFormatting.DARK_BLUE + "▉" + EnumChatFormatting.RESET + " - " + trans("blue", aEng));
		return this;
	}
	
	public MultiBlockTooltipBuilder addGreenHint(String aEng) {
		sLines.add(EnumChatFormatting.DARK_GREEN + "▉" + EnumChatFormatting.RESET + " - " + trans("green", aEng));
		return this;
	}
	
	public MultiBlockTooltipBuilder addRedHint(String aEng) {
		sLines.add(EnumChatFormatting.RED + "▉" + EnumChatFormatting.RESET + " - " + trans("red", aEng));
		return this;
	}
	
	public MultiBlockTooltipBuilder addBlackHint(String aEng) {
		sLines.add(EnumChatFormatting.BLACK + "▉" + EnumChatFormatting.RESET + " - " + trans("black", aEng));
		return this;
	}
	
	/**
	 * Call at the very end.<br> Adds a final line with the authors name and information on how to
	 * display the structure guidelines.<br> Ends the building process.
	 *
	 * @return The result of all build calls.
	 */
	
	public void signAndFinalize(String empty) {
		signAndFinalize();
	}
	
	public void signAndFinalize() {
		iLines.add(hold.get() + " " + EnumChatFormatting.DARK_BLUE +
				EnumChatFormatting.BOLD + "[" + left_shift.get() + "]" +
				EnumChatFormatting.RESET + EnumChatFormatting.GRAY + " " + to_structure.get());
		iLines.add(impactTag());
		iArray    = new String[iLines.size()];
		sArray    = new String[sLines.size()];
		iLines.toArray(iArray);
		sLines.toArray(sArray);
	}
	
	public void signAndFinalize(boolean ctrl) {
		iLines.add(hold.get() + " " + EnumChatFormatting.DARK_BLUE +
				EnumChatFormatting.BOLD + "[" + left_shift.get() + "]" +
				EnumChatFormatting.RESET + EnumChatFormatting.GRAY + " " + to_structure.get());
		iLines.add(hold.get() + " " + EnumChatFormatting.DARK_BLUE +
				EnumChatFormatting.BOLD + "[" + left_ctrl.get() + "]" +
				EnumChatFormatting.RESET + EnumChatFormatting.GRAY + " " + to_more_info.get());
		iLines.add(impactTag());
		
		iArray    = new String[iLines.size()];
		sArray    = new String[sLines.size()];
		cArray    = new String[cLines.size()];
		iLines.toArray(iArray);
		sLines.toArray(sArray);
		cLines.toArray(cArray);
	}
	
	public String[] getInformation() {
		return iArray;
	}
	
	public String[] getStructureInformation() {
		return sArray;
	}
	
	public String[] getControlInfo() {
		return cArray;
	}
}