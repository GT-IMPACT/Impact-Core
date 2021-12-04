package com.impact.util.string;

import com.impact.util.Language;
import gregtech.api.interfaces.IItemContainer;
import net.minecraft.block.Block;
import net.minecraft.util.EnumChatFormatting;

import java.util.LinkedList;
import java.util.List;

import static com.impact.util.Utilits.Blockstack;
import static com.impact.util.Utilits.impactTag;

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
	private final List<String> holoLines;
	private final String prefix;
	private String[] iArray;
	private String[] sArray;
	private String[] cArray;
	private String[] holoArray;
	
	public MultiBlockTooltipBuilder() {
		iLines    = new LinkedList<>();
		sLines    = new LinkedList<>();
		cLines    = new LinkedList<>();
		holoLines = new LinkedList<>();
		prefix    = "";
	}
	
	public MultiBlockTooltipBuilder(String aPrefix) {
		iLines    = new LinkedList<>();
		sLines    = new LinkedList<>();
		cLines    = new LinkedList<>();
		holoLines = new LinkedList<>();
		prefix    = aPrefix + ".";
	}
	
	public String transNot(String aKey, String aEnglish) {
		return Language.transMTB(aKey, aEnglish, false);
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
	
	public MultiBlockTooltipBuilder addMultiAmpGen() {
		iLines.add(transNot("multi_amperes_generator", "Multi-Amperes generator"));
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
				EnumChatFormatting.AQUA + transNot("parallel.point", "Parallel Point") + ": " +
						EnumChatFormatting.GRAY + transNot("parallel.point.basic", "basic") + " " + EnumChatFormatting.AQUA + aBasic +
						EnumChatFormatting.GRAY + ", " + transNot("max", "max") + " " + EnumChatFormatting.AQUA + aMax);
		return this;
	}
	
	public MultiBlockTooltipBuilder addPollution(int Min, int Max) {
		iLines.add(transNot("pollution", "Pollution") + ": " +
				transNot("min", "min") + " " + Min + ", " +
				transNot("max", "max") + " " + Max);
		return this;
	}
	
	public MultiBlockTooltipBuilder addPollution(int aPollut) {
		iLines.add(transNot("pollution", "Pollution") + " " + transNot("per_second", "per second") + ": " + aPollut);
		return this;
	}
	
	public MultiBlockTooltipBuilder addPollution(int aPollut, String aKey, String aEng) {
		iLines.add(transNot("pollution", "Pollution") + " " + transNot("per_second", "per second") + ": " + aPollut + " " + trans(aKey, aEng));
		return this;
	}
	
	public MultiBlockTooltipBuilder addTypeMachine(String aKey, String aEng) {
		iLines.add(EnumChatFormatting.YELLOW + trans(aKey, aEng));
		return this;
	}
	
	public MultiBlockTooltipBuilder addTypeGenerator() {
		iLines.add(EnumChatFormatting.YELLOW + transNot("generator", "Generator"));
		return this;
	}
	
	public MultiBlockTooltipBuilder addTypeSteam() {
		iLines.add(EnumChatFormatting.YELLOW + transNot("steam_producer", "Steam Producer"));
		return this;
	}
	
	public MultiBlockTooltipBuilder addScrew() {
		iLines.add(transNot("screw_type", "Select type machine with Screwdriver click in Controller"));
		return this;
	}
	
	public MultiBlockTooltipBuilder addScrew(String aKey, String aEng) {
		iLines.add(transNot("select", "Select") + " " + trans(aKey, aEng) + " " + transNot("screw_type_different", "with Screwdriver click in Controller"));
		return this;
	}
	
	/* Energy Hatch */
	
	public MultiBlockTooltipBuilder addSeparatedBus() {
		iLines.add(transNot("separated_bus", "Select Separated Buses mod with Shift + Screwdriver click in Controller"));
		return this;
	}
	
	public MultiBlockTooltipBuilder addSingleAnalog() {
		iLines.add(transNot("single_analog", "One-block machine analog"));
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
		sLines.add(TAB + EnumChatFormatting.WHITE + transNot("parallel.hatch", "Parallel Hatch") + ": " +
				EnumChatFormatting.GRAY + trans(aKey, aEng));
		return this;
	}
	
	public MultiBlockTooltipBuilder addParallelHatch() {
		sLines.add(TAB + EnumChatFormatting.WHITE + transNot("parallel.hatch", "Parallel Hatch") + ": (max 1x)" +
				EnumChatFormatting.GRAY + transNot("any_case", "Any casing"));
		return this;
	}
	
	public MultiBlockTooltipBuilder addParallelHatch(String aKey, String aEng, int amount) {
		sLines.add(TAB + EnumChatFormatting.WHITE + transNot("parallel.hatch", "Parallel Hatch") + ": " +
				EnumChatFormatting.GRAY + trans(aKey, aEng) + " (" + transNot("max", "max") + " x" + amount + ")");
		return this;
	}
	
	/* Dynamo Hatch */
	
	public MultiBlockTooltipBuilder addParallelHatch(int amount) {
		sLines.add(TAB + EnumChatFormatting.WHITE + transNot("parallel.hatch", "Parallel Hatch") + ": " +
				EnumChatFormatting.GRAY + transNot("any_case", "Any casing") + " (" + transNot("max", "max") + " x" + amount + ")");
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
//        sLines.add(transNot("begin_structure_block", "Description of the structure in development"));
		sLines.add(transNot("structure", "Structure") + ":");
		return this;
	}
	
	public MultiBlockTooltipBuilder sizeStructure(int w, int h, int l) {
		sLines.add(transNot("dimensions", "Dimensions") + ": " + w + "x" + h + "x" + l + " " + transNot("whl", "(WxHxL)"));
		sLines.add(transNot("structure", "Structure") + ":");
		return this;
	}
	
	/* Muffler Hatch */
	
	public MultiBlockTooltipBuilder addController() {
		sLines.add(EnumChatFormatting.YELLOW + transNot("blueprint.0", "Follow the") +
				EnumChatFormatting.RED + " ImpactAPI " + EnumChatFormatting.YELLOW + transNot("blueprint.1", "Projector to build the main structure"));
		return this;
	}
	
	public MultiBlockTooltipBuilder addCasingInfo(String aKey, String aEng) {
		sLines.add(TAB + EnumChatFormatting.WHITE + trans(aKey, aEng) + EnumChatFormatting.GRAY + " (" + transNot("at_least", "at least") + ")");
		return this;
	}
	
	public MultiBlockTooltipBuilder addCasingInfo(Block block, int meta) {
		sLines.add(TAB + EnumChatFormatting.WHITE + Blockstack(block, 1, meta).getDisplayName() + EnumChatFormatting.GRAY + " (" + transNot("at_least", "at least") + ")");
		return this;
	}
	
	/* Maintenance Hatch */
	
	public MultiBlockTooltipBuilder addCasingInfo(IItemContainer item) {
		sLines.add(TAB + EnumChatFormatting.WHITE + item.get(1).getDisplayName() + EnumChatFormatting.GRAY + " (" + transNot("at_least", "at least") + ")");
		return this;
	}
	
	public MultiBlockTooltipBuilder addEnergyHatch() {
		sLines.add(TAB + EnumChatFormatting.WHITE + transNot("hatch.energy", "Energy Hatch") + ": " +
				EnumChatFormatting.GRAY + transNot("any_case", "Any casing"));
		return this;
	}
	
	public MultiBlockTooltipBuilder addEnergyHatch(int amount) {
		sLines.add(TAB + EnumChatFormatting.WHITE + transNot("hatch.energy", "Energy Hatch") + ": " +
				EnumChatFormatting.GRAY + transNot("any_case", "Any casing") + " (" + transNot("max", "max") + " x" + amount + ")");
		return this;
	}
	
	public MultiBlockTooltipBuilder addEnergyHatch(String aKey, String aEng) {
		sLines.add(TAB + EnumChatFormatting.WHITE + transNot("hatch.energy", "Energy Hatch") + ": " +
				EnumChatFormatting.GRAY + trans(aKey, aEng));
		return this;
	}
	
	public MultiBlockTooltipBuilder addEnergyHatch(String aKey, String aEng, int amount) {
		sLines.add(TAB + EnumChatFormatting.WHITE + transNot("hatch.energy", "Energy Hatch") + ": " +
				EnumChatFormatting.GRAY + trans(aKey, aEng) + " (" + transNot("max", "max") + " x" + amount + ")");
		return this;
	}
	
	/* I/O Hatch */
	
	public MultiBlockTooltipBuilder addDynamoHatch() {
		sLines.add(TAB + EnumChatFormatting.WHITE + transNot("hatch.dynamo", "Dynamo Hatch") + ": " +
				EnumChatFormatting.GRAY + transNot("any_case", "Any casing"));
		return this;
	}
	
	public MultiBlockTooltipBuilder addDynamoHatch(int amount) {
		sLines.add(TAB + EnumChatFormatting.WHITE + transNot("hatch.dynamo", "Dynamo Hatch") + ": " +
				EnumChatFormatting.GRAY + transNot("any_case", "Any casing") + " (" + transNot("max", "max") + " x" + amount + ")");
		return this;
	}
	
	public MultiBlockTooltipBuilder addDynamoHatch(String aKey, String aEng) {
		sLines.add(TAB + EnumChatFormatting.WHITE + transNot("hatch.dynamo", "Dynamo Hatch") + ": " +
				EnumChatFormatting.GRAY + trans(aKey, aEng));
		return this;
	}
	
	public MultiBlockTooltipBuilder addDynamoHatch(String aKey, String aEng, int amount) {
		sLines.add(TAB + EnumChatFormatting.WHITE + transNot("hatch.dynamo", "Dynamo Hatch") + ": " +
				EnumChatFormatting.GRAY + trans(aKey, aEng) + " (" + transNot("max", "max") + " x" + amount + ")");
		return this;
	}
	
	/* Input Bus */
	
	public MultiBlockTooltipBuilder addMuffler() {
		sLines.add(TAB + EnumChatFormatting.WHITE + transNot("hatch.muffler", "Muffler Hatch") + ": " +
				EnumChatFormatting.GRAY + transNot("any_case", "Any casing"));
		return this;
	}
	
	public MultiBlockTooltipBuilder addMuffler(String aKey, String aEng) {
		sLines.add(TAB + EnumChatFormatting.WHITE + transNot("hatch.muffler", "Muffler Hatch") + ": " +
				EnumChatFormatting.GRAY + trans(aKey, aEng));
		return this;
	}
	
	public MultiBlockTooltipBuilder addMuffler(String aKey, String aEng, int amount) {
		sLines.add(TAB + EnumChatFormatting.WHITE + transNot("hatch.muffler", "Muffler Hatch") + ": " +
				EnumChatFormatting.GRAY + trans(aKey, aEng) + " (" + transNot("max", "max") + " x" + amount + ")");
		return this;
	}
	
	/* Nuclear Hatch */
	
	public MultiBlockTooltipBuilder addMaintenanceHatch() {
		sLines.add(TAB + EnumChatFormatting.WHITE + transNot("hatch.maintenance", "Maintenance Hatch") + ": " +
				EnumChatFormatting.GRAY + transNot("any_case", "Any casing"));
		return this;
	}
	
	public MultiBlockTooltipBuilder addMaintenanceHatch(String aKey, String aEng) {
		sLines.add(TAB + EnumChatFormatting.WHITE + transNot("hatch.maintenance", "Maintenance Hatch") + ": " +
				EnumChatFormatting.GRAY + trans(aKey, aEng));
		return this;
	}
	
	/* Input Hatch */
	
	public MultiBlockTooltipBuilder addMaintenanceHatch(String aKey, String aEng, int amount) {
		sLines.add(TAB + EnumChatFormatting.WHITE + transNot("hatch.maintenance", "Maintenance Hatch") + ": " +
				EnumChatFormatting.GRAY + trans(aKey, aEng) + " (" + transNot("max", "max") + " x" + amount + ")");
		return this;
	}
	
	public MultiBlockTooltipBuilder addIOHatches() {
		sLines.add(TAB + EnumChatFormatting.WHITE + transNot("hatch.io", "I/O Hatch") + ": " +
				EnumChatFormatting.GRAY + transNot("any_case", "Any casing"));
		return this;
	}
	
	public MultiBlockTooltipBuilder addIOHatches(int amount) {
		sLines.add(TAB + EnumChatFormatting.WHITE + transNot("hatch.io", "I/O Hatch") + ": " +
				EnumChatFormatting.GRAY + transNot("any_case", "Any casing") + " (" + transNot("max", "max") + " x" + amount + ")");
		return this;
	}
	
	public MultiBlockTooltipBuilder addIOHatches(String aKey, String aEng) {
		sLines.add(TAB + EnumChatFormatting.WHITE + transNot("hatch.io", "I/O Hatch") + ": " +
				EnumChatFormatting.GRAY + trans(aKey, aEng));
		return this;
	}
	
	/* Output Bus */
	
	public MultiBlockTooltipBuilder addIOHatches(String aKey, String aEng, int amount) {
		sLines.add(TAB + EnumChatFormatting.WHITE + transNot("hatch.io", "I/O Hatch") + ": " +
				EnumChatFormatting.GRAY + trans(aKey, aEng) + " (" + transNot("max", "max") + " x" + amount + ")");
		return this;
	}
	
	public MultiBlockTooltipBuilder addInputBus() {
		sLines.add(TAB + EnumChatFormatting.WHITE + transNot("hatch.bus.in", "Input Bus") + ": " +
				EnumChatFormatting.GRAY + transNot("any_case", "Any casing"));
		return this;
	}
	
	public MultiBlockTooltipBuilder addInputBus(int amount) {
		sLines.add(TAB + EnumChatFormatting.WHITE + transNot("hatch.bus.in", "Input Bus") + ": " +
				EnumChatFormatting.GRAY + transNot("any_case", "Any casing") + " (" + transNot("max", "max") + " x" + amount + ")");
		return this;
	}
	
	public MultiBlockTooltipBuilder addInputBus(String aKey, String aEng) {
		sLines.add(TAB + EnumChatFormatting.WHITE + transNot("hatch.bus.in", "Input Bus") + ": " +
				EnumChatFormatting.GRAY + trans(aKey, aEng));
		return this;
	}
	
	/* Output Hatch */
	
	public MultiBlockTooltipBuilder addInputBus(String aKey, String aEng, int amount) {
		sLines.add(TAB + EnumChatFormatting.WHITE + transNot("hatch.bus.in", "Input Bus") + ": " +
				EnumChatFormatting.GRAY + trans(aKey, aEng) + " (" + trans("max", "max") + " x" + amount + ")");
		return this;
	}
	
	public MultiBlockTooltipBuilder addNuclearRod(String aKey, String aEng) {
		sLines.add(TAB + EnumChatFormatting.WHITE + transNot("hatch.nuclear", "Nuclear Rod Hatch") + ": " +
				EnumChatFormatting.GRAY + trans(aKey, aEng));
		return this;
	}
	
	public MultiBlockTooltipBuilder addNuclearRod(String aKey, String aEng, int amount) {
		sLines.add(TAB + EnumChatFormatting.WHITE + transNot("hatch.nuclear", "Nuclear Rod Hatch") + ": " +
				EnumChatFormatting.GRAY + trans(aKey, aEng) + " (" + transNot("max", "max") + " x" + amount + ")");
		return this;
	}
	
	public MultiBlockTooltipBuilder addInputHatch() {
		sLines.add(TAB + EnumChatFormatting.WHITE + transNot("hatch.in", "Input Hatch") + ": " +
				EnumChatFormatting.GRAY + transNot("any_case", "Any casing"));
		return this;
	}
	
	public MultiBlockTooltipBuilder addInputHatch(int amount) {
		sLines.add(TAB + EnumChatFormatting.WHITE + transNot("hatch.in", "Input Hatch") + ": " +
				EnumChatFormatting.GRAY + transNot("any_case", "Any casing") + " (" + transNot("max", "max") + " x" + amount + ")");
		return this;
	}
	
	public MultiBlockTooltipBuilder addInputHatch(String aKey, String aEng) {
		sLines.add(TAB + EnumChatFormatting.WHITE + transNot("hatch.in", "Input Hatch") + ": " +
				EnumChatFormatting.GRAY + trans(aKey, aEng));
		return this;
	}
	
	public MultiBlockTooltipBuilder addInputHatch(String aKey, String aEng, int amount) {
		sLines.add(TAB + EnumChatFormatting.WHITE + transNot("hatch.in", "Input Hatch") + ": " +
				EnumChatFormatting.GRAY + trans(aKey, aEng) + " (" + transNot("max", "max") + " x" + amount + ")");
		return this;
	}
	
	public MultiBlockTooltipBuilder addOutputBus() {
		sLines.add(TAB + EnumChatFormatting.WHITE + transNot("hatch.bus.out", "Output Bus") + ": " +
				EnumChatFormatting.GRAY + transNot("any_case", "Any casing"));
		return this;
	}
	
	public MultiBlockTooltipBuilder addOutputBus(int amount) {
		sLines.add(TAB + EnumChatFormatting.WHITE + transNot("hatch.bus.out", "Output Bus") + ": " +
				EnumChatFormatting.GRAY + transNot("any_case", "Any casing") + " (" + transNot("max", "max") + " x" + amount + ")");
		return this;
	}
	
	public MultiBlockTooltipBuilder addOutputBus(String aKey, String aEng) {
		sLines.add(TAB + EnumChatFormatting.WHITE + transNot("hatch.bus.out", "Output Bus") + ": " +
				EnumChatFormatting.GRAY + trans(aKey, aEng));
		return this;
	}
	
	public MultiBlockTooltipBuilder addOutputBus(String aKey, String aEng, int amount) {
		sLines.add(TAB + EnumChatFormatting.WHITE + transNot("hatch.bus.out", "Output Bus") + ": " +
				EnumChatFormatting.GRAY + trans(aKey, aEng) + " (" + transNot("max", "max") + " x" + amount + ")");
		return this;
	}
	
	public MultiBlockTooltipBuilder addOutputHatch() {
		sLines.add(TAB + EnumChatFormatting.WHITE + transNot("hatch.out", "Output Hatch") + ": " +
				EnumChatFormatting.GRAY + transNot("any_case", "Any casing"));
		return this;
	}
	
	public MultiBlockTooltipBuilder addOutputHatch(int amount) {
		sLines.add(TAB + EnumChatFormatting.WHITE + transNot("hatch.out", "Output Hatch") + ": " +
				EnumChatFormatting.GRAY + transNot("any_case", "Any casing") + " (" + transNot("max", "max") + " x" + amount + ")");
		return this;
	}
	
	public MultiBlockTooltipBuilder addOutputHatch(String aKey, String aEng) {
		sLines.add(TAB + EnumChatFormatting.WHITE + transNot("hatch.out", "Output Hatch") + ": " +
				EnumChatFormatting.GRAY + trans(aKey, aEng));
		return this;
	}
	
	public MultiBlockTooltipBuilder addOutputHatch(String aKey, String aEng, int amount) {
		sLines.add(TAB + EnumChatFormatting.WHITE + transNot("hatch.out", "Output Hatch") + ": " +
				EnumChatFormatting.GRAY + trans(aKey, aEng) + " (" + transNot("max", "max") + " x" + amount + ")");
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
		sLines.add(TAB + EnumChatFormatting.WHITE + trans(aKey, aEng) + ": " + EnumChatFormatting.GRAY + transNot("any_case", "Any casing"));
		return this;
	}
	
	/**
	 * Hologram Info
	 */
	
	public MultiBlockTooltipBuilder addHints() {
		holoLines.add(EnumChatFormatting.RED + transNot("holo_details", "Impact Hologram Details") + ":");
		return this;
	}
	
	public MultiBlockTooltipBuilder addWhiteHint(String aEng) {
		holoLines.add(EnumChatFormatting.WHITE + "▉" + EnumChatFormatting.RESET + " - " + trans("white", aEng));
		return this;
	}
	
	public MultiBlockTooltipBuilder addOrangeHint(String aEng) {
		holoLines.add(EnumChatFormatting.GOLD + "▉" + EnumChatFormatting.RESET + " - " + trans("orange", aEng));
		return this;
	}
	
	public MultiBlockTooltipBuilder addMagentaHint(String aEng) {
		holoLines.add(EnumChatFormatting.DARK_RED + "▉" + EnumChatFormatting.RESET + " - " + trans("magenta", aEng));
		return this;
	}
	
	public MultiBlockTooltipBuilder addLBlueHint(String aEng) {
		holoLines.add(EnumChatFormatting.BLUE + "▉" + EnumChatFormatting.RESET + " - " + trans("l_blue", aEng));
		return this;
	}
	
	public MultiBlockTooltipBuilder addYellowHint(String aEng) {
		holoLines.add(EnumChatFormatting.YELLOW + "▉" + EnumChatFormatting.RESET + " - " + trans("yellow", aEng));
		return this;
	}
	
	public MultiBlockTooltipBuilder addLimeHint(String aEng) {
		holoLines.add(EnumChatFormatting.GREEN + "▉" + EnumChatFormatting.RESET + " - " + trans("lime", aEng));
		return this;
	}
	
	public MultiBlockTooltipBuilder addPinkHint(String aEng) {
		holoLines.add(EnumChatFormatting.LIGHT_PURPLE + "▉" + EnumChatFormatting.RESET + " - " + trans("pink", aEng));
		return this;
	}
	
	public MultiBlockTooltipBuilder addGrayHint(String aEng) {
		holoLines.add(EnumChatFormatting.DARK_GRAY + "▉" + EnumChatFormatting.RESET + " - " + trans("gray", aEng));
		return this;
	}
	
	public MultiBlockTooltipBuilder addLGrayHint(String aEng) {
		holoLines.add(EnumChatFormatting.GRAY + "▉" + EnumChatFormatting.RESET + " - " + trans("l_gray", aEng));
		return this;
	}
	
	public MultiBlockTooltipBuilder addCyanHint(String aEng) {
		holoLines.add(EnumChatFormatting.DARK_AQUA + "▉" + EnumChatFormatting.RESET + " - " + trans("cyan", aEng));
		return this;
	}
	
	public MultiBlockTooltipBuilder addPurpleHint(String aEng) {
		holoLines.add(EnumChatFormatting.DARK_PURPLE + " - " + "▉" + EnumChatFormatting.RESET + " - " + trans("purple", aEng));
		return this;
	}
	
	public MultiBlockTooltipBuilder addBlueHint(String aEng) {
		holoLines.add(EnumChatFormatting.DARK_BLUE + "▉" + EnumChatFormatting.RESET + " - " + trans("blue", aEng));
		return this;
	}
	
	public MultiBlockTooltipBuilder addGreenHint(String aEng) {
		holoLines.add(EnumChatFormatting.DARK_GREEN + "▉" + EnumChatFormatting.RESET + " - " + trans("green", aEng));
		return this;
	}
	
	public MultiBlockTooltipBuilder addRedHint(String aEng) {
		holoLines.add(EnumChatFormatting.RED + "▉" + EnumChatFormatting.RESET + " - " + trans("red", aEng));
		return this;
	}
	
	public MultiBlockTooltipBuilder addBlackHint(String aEng) {
		holoLines.add(EnumChatFormatting.BLACK + "▉" + EnumChatFormatting.RESET + " - " + trans("black", aEng));
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
		iLines.add(transNot("hold", "Hold") + " " + EnumChatFormatting.DARK_BLUE +
				EnumChatFormatting.BOLD + "[" + transNot("left_shift", "LSHIFT") + "]" +
				EnumChatFormatting.RESET + EnumChatFormatting.GRAY + " " + transNot("to_structure", "to structure"));
		iLines.add(impactTag());
		iArray    = new String[iLines.size()];
		sArray    = new String[sLines.size()];
		holoArray = new String[holoLines.size()];
		iLines.toArray(iArray);
		sLines.toArray(sArray);
		holoLines.toArray(holoArray);
	}
	
	public void signAndFinalize(boolean ctrl) {
		iLines.add(transNot("hold", "Hold") + " " + EnumChatFormatting.DARK_BLUE +
				EnumChatFormatting.BOLD + "[" + transNot("left_shift", "LSHIFT") + "]" +
				EnumChatFormatting.RESET + EnumChatFormatting.GRAY + " " + transNot("to_structure", "to structure"));
		iLines.add(transNot("hold", "Hold") + " " + EnumChatFormatting.DARK_BLUE +
				EnumChatFormatting.BOLD + "[" + transNot("left_ctrl", "LCTRL") + "]" +
				EnumChatFormatting.RESET + EnumChatFormatting.GRAY + " " + transNot("to_more_info", "to more info"));
		iLines.add(impactTag());
		
		iArray    = new String[iLines.size()];
		sArray    = new String[sLines.size()];
		cArray    = new String[cLines.size()];
		holoArray = new String[holoLines.size()];
		iLines.toArray(iArray);
		sLines.toArray(sArray);
		cLines.toArray(cArray);
		holoLines.toArray(holoArray);
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
	
	public String[] getHoloInfo() {
		return holoArray;
	}
}
