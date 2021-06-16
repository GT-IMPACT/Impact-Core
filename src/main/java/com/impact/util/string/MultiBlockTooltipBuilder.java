package com.impact.util.string;

import static com.impact.util.Utilits.impactTag;
import static com.impact.util.Utilits.translate;

import java.util.LinkedList;
import java.util.List;

import com.impact.util.Utilits;
import net.minecraft.util.EnumChatFormatting;

/**
 * Have you ever felt like your tooltips just aren't enterprise enough? Use this!
 */
public class MultiBlockTooltipBuilder {

  private static final String TAB = "   ";

  private final List<String> iLines;
  private final List<String> sLines;
  private final List<String> cLines;

  private String[] iArray;
  private String[] sArray;
  private String[] cArray;

  public MultiBlockTooltipBuilder() {
    iLines = new LinkedList<>();
    sLines = new LinkedList<>();
    cLines = new LinkedList<>();
  }

  /**
   * Add a basic line of information about this structure
   *
   * @param // line The line to be added.
   * @return Instance this method was called on.
   */
  public MultiBlockTooltipBuilder addInfo(String info) {
    iLines.add(info);
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

  public MultiBlockTooltipBuilder addParallelInfo(int Basic, int Max) {
    iLines.add(EnumChatFormatting.AQUA + "Parallel Point: " + EnumChatFormatting.GRAY + "basic "
        + EnumChatFormatting.AQUA + Basic + EnumChatFormatting.GRAY + ", max "
        + EnumChatFormatting.AQUA + Max);
    return this;
  }
  
  public MultiBlockTooltipBuilder addParallelInfo(int Basic, int Max, boolean lang) {
    iLines.add(EnumChatFormatting.AQUA + translate("tooltip.parallel.point") + ": " +
            EnumChatFormatting.GRAY + translate("tooltip.parallel.point.basic") + " " + EnumChatFormatting.AQUA + Basic +
            EnumChatFormatting.GRAY + ", " + translate("tooltip.max") + " " + EnumChatFormatting.AQUA + Max);
    return this;
  }

  public MultiBlockTooltipBuilder addPollution(int Min, int Max) {
    iLines.add("Pollution: min " + Min + ", max " + Max);
    return this;
  }
  
  public MultiBlockTooltipBuilder addPollution(int Min, int Max, boolean lang) {
    iLines.add(translate("tooltip.pollution") + ": " +
            translate("tooltip.min") + Min + ", " +
            translate("tooltip.max") + Max);
    return this;
  }

  public MultiBlockTooltipBuilder addPollution(int aPollut) {
    iLines.add("Pollution per second: " + aPollut);
    return this;
  }
  
  public MultiBlockTooltipBuilder addPollution(int aPollut, boolean lang) {
    iLines.add(translate("tooltip.pollution") + translate("tooltip.per_second") + ": " + aPollut);
    return this;
  }

  public MultiBlockTooltipBuilder addPollution(int aPollut, String info) {
    iLines.add("Pollution per second: " + aPollut + " " + info);
    return this;
  }
  
  public MultiBlockTooltipBuilder addPollution(int aPollut, String infoLang, boolean lang) {
    iLines.add(translate("tooltip.pollution") + translate("tooltip.per_second") + ": " + aPollut + " " + translate(infoLang));
    return this;
  }

  public MultiBlockTooltipBuilder addTypeMachine(String info) {
    iLines.add(EnumChatFormatting.YELLOW + info);
    return this;
  }
  
  public MultiBlockTooltipBuilder addTypeMachine(String info, boolean lang) {
    iLines.add(EnumChatFormatting.YELLOW + translate(info));
    return this;
  }

  public MultiBlockTooltipBuilder addScrew() {
    iLines.add("Select type machine with Screwdriver click in Controller");
    return this;
  }
  
  public MultiBlockTooltipBuilder addScrew(boolean lang) {
    iLines.add(translate("tooltip.screw_type"));
    return this;
  }
  
  public MultiBlockTooltipBuilder addScrew(String screw) {
    iLines.add("Select " + screw + " with Screwdriver click in Controller");
    return this;
  }
  
  public MultiBlockTooltipBuilder addScrew(String screw, boolean lang) {
    iLines.add(translate("tooltip.select") + " " + translate(screw) + " " + translate("tooltip.screw_type_different"));
    return this;
  }

  public MultiBlockTooltipBuilder addSeparatedBus() {
    iLines.add("Select Separated Buses mod with Shift + Screwdriver click in Controller");
    return this;
  }
  
  public MultiBlockTooltipBuilder addSeparatedBus(boolean lang) {
    iLines.add(translate("tooltip.separated_bus"));
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

  public MultiBlockTooltipBuilder addParallelHatch(String info) {
    sLines.add(TAB + EnumChatFormatting.GREEN + "Parallel Hatch: " + EnumChatFormatting.GRAY + info);
    return this;
  }
  
  public MultiBlockTooltipBuilder addParallelHatch(String info, boolean lang) {
    sLines.add(TAB + EnumChatFormatting.GREEN + translate("tooltip.parallel.hatch") + ": " + EnumChatFormatting.GRAY + translate(info));
    return this;
  }

  public MultiBlockTooltipBuilder addinfoB(String info) {
    cLines.add(info);
    return this;
  }
  
  public MultiBlockTooltipBuilder addinfoB(String info, boolean lang) {
    cLines.add(translate(info));
    return this;
  }

  public MultiBlockTooltipBuilder addinfoBTab(String info) {
    cLines.add(TAB + info);
    return this;
  }
  
  public MultiBlockTooltipBuilder addinfoBTab(String info, boolean lang) {
    cLines.add(TAB + translate(info));
    return this;
  }

  public MultiBlockTooltipBuilder beginStructureBlock(int w, int h, int l) {
    //sLines.add("Dimensions: " + w + "x" + h + "x" + l + " (WxHxL)");
    sLines.add("Description of the structure in development");
    sLines.add("Structure:");
    return this;
  }
  
  public MultiBlockTooltipBuilder beginStructureBlock(int w, int h, int l, boolean lang) {
    //sLines.add("Dimensions: " + w + "x" + h + "x" + l + " (WxHxL)");
    sLines.add(translate("tooltip.begin_structure_block"));
    sLines.add(translate("tooltip.structure") + ":");
    return this;
  }

  public MultiBlockTooltipBuilder addController() {
    sLines.add(TAB + EnumChatFormatting.YELLOW + "Use Blueprint TecTech ");
    return this;
  }
  
  public MultiBlockTooltipBuilder addController(boolean lang) {
    sLines.add(TAB + EnumChatFormatting.YELLOW + translate("tooltip.blueprint"));
    return this;
  }

  public MultiBlockTooltipBuilder addCasingInfo(String info) {
    sLines.add(TAB + "" + EnumChatFormatting.GREEN + info + EnumChatFormatting.GRAY + " (at least)");
    return this;
  }
  
  public MultiBlockTooltipBuilder addCasingInfo(String info, boolean lang) {
    sLines.add(TAB + "" + EnumChatFormatting.GREEN + translate(info) + EnumChatFormatting.GRAY + " (" + translate("tooltip.at_least") + ")");
    return this;
  }

  public MultiBlockTooltipBuilder addEnergyHatch(String info) {
    sLines.add(TAB + EnumChatFormatting.GREEN + "Energy Hatch: " + EnumChatFormatting.GRAY + info);
    return this;
  }
  
  public MultiBlockTooltipBuilder addEnergyHatch(String info, boolean lang) {
    sLines.add(TAB + EnumChatFormatting.GREEN + translate("tooltip.hatch.energy") + ": " + EnumChatFormatting.GRAY + translate(info));
    return this;
  }

  public MultiBlockTooltipBuilder addDynamoHatch(String info) {
    sLines.add(TAB + EnumChatFormatting.GREEN + "Dynamo Hatch: " + EnumChatFormatting.GRAY + info);
    return this;
  }
  
  public MultiBlockTooltipBuilder addDynamoHatch(String info, boolean lang) {
    sLines.add(TAB + EnumChatFormatting.GREEN + translate("tooltip.hatch.dynamo") + ": " + EnumChatFormatting.GRAY + translate(info));
    return this;
  }

  public MultiBlockTooltipBuilder addMuffler(String info) {
    sLines.add(TAB + EnumChatFormatting.GREEN + "Muffler Hatch: " + EnumChatFormatting.GRAY + info);
    return this;
  }
  
  public MultiBlockTooltipBuilder addMuffler(String info, boolean lang) {
    sLines.add(TAB + EnumChatFormatting.GREEN + translate("tooltip.hatch.muffler") + ": " + EnumChatFormatting.GRAY + translate(info));
    return this;
  }

  public MultiBlockTooltipBuilder addMaintenanceHatch(String info) {
    sLines.add(TAB + EnumChatFormatting.GREEN + "Maintenance Hatch: " + EnumChatFormatting.GRAY + info);
    return this;
  }
  
  public MultiBlockTooltipBuilder addMaintenanceHatch(String info, boolean lang) {
    sLines.add(TAB + EnumChatFormatting.GREEN + translate("tooltip.hatch.maintenance") + ": " + EnumChatFormatting.GRAY + translate(info));
    return this;
  }

  public MultiBlockTooltipBuilder addIOHatches(String info) {
    sLines.add(TAB + EnumChatFormatting.GREEN + "I/O Hatches: " + EnumChatFormatting.GRAY + info);
    return this;
  }
  
  public MultiBlockTooltipBuilder addIOHatches(String info, boolean lang) {
    sLines.add(TAB + EnumChatFormatting.GREEN + translate("tooltip.hatch.io") + ": " + EnumChatFormatting.GRAY + translate(info));
    return this;
  }

  public MultiBlockTooltipBuilder addInputBus(String info) {
    sLines.add(TAB + EnumChatFormatting.GREEN + "Input Bus: " + EnumChatFormatting.GRAY + info);
    return this;
  }
  
  public MultiBlockTooltipBuilder addInputBus(String info, boolean lang) {
    sLines.add(TAB + EnumChatFormatting.GREEN + translate("tooltip.hatch.bus.in") + ": " + EnumChatFormatting.GRAY + translate(info));
    return this;
  }

  public MultiBlockTooltipBuilder addNuclearRod(String info) {
    sLines.add(TAB + EnumChatFormatting.GREEN + "Nuclear Rod Hatch: " + EnumChatFormatting.GRAY + info);
    return this;
  }
  
  public MultiBlockTooltipBuilder addNuclearRod(String info, boolean lang) {
    sLines.add(TAB + EnumChatFormatting.GREEN + translate("tooltip.hatch.nuclear") + ": " + EnumChatFormatting.GRAY + translate(info));
    return this;
  }

  public MultiBlockTooltipBuilder addInputHatch(String info) {
    sLines.add(TAB + EnumChatFormatting.GREEN + "Input Hatch: " + EnumChatFormatting.GRAY + info);
    return this;
  }
  
  public MultiBlockTooltipBuilder addInputHatch(String info, boolean lang) {
    sLines.add(TAB + EnumChatFormatting.GREEN + translate("tooltip.hatch.in") + ": " + EnumChatFormatting.GRAY + translate(info));
    return this;
  }

  public MultiBlockTooltipBuilder addOutputBus(String info) {
    sLines.add(TAB + EnumChatFormatting.GREEN + "Output Bus: " + EnumChatFormatting.GRAY + info);
    return this;
  }
  
  public MultiBlockTooltipBuilder addOutputBus(String info, boolean lang) {
    sLines.add(TAB + EnumChatFormatting.GREEN + translate("tooltip.hatch.bus.out") + ": " + EnumChatFormatting.GRAY + translate(info));
    return this;
  }

  public MultiBlockTooltipBuilder addOutputHatch(String info) {
    sLines.add(TAB + EnumChatFormatting.GREEN + "Output Hatch: " + EnumChatFormatting.GRAY + info);
    return this;
  }
  
  public MultiBlockTooltipBuilder addOutputHatch(String info, boolean lang) {
    sLines.add(TAB + EnumChatFormatting.GREEN + translate("tooltip.hatch.out") + ": " + EnumChatFormatting.GRAY + translate(info));
    return this;
  }

  /**
   * Use this method to add a structural part that isn't covered by the builders capabilities.
   *
   * @param name Name of the hatch or other component.
   * @param info Positional information.
   * @return Instance this method was called on.
   */
  public MultiBlockTooltipBuilder addOtherStructurePart(String name, String info) {
    sLines.add(TAB + EnumChatFormatting.GREEN + name + ": " + EnumChatFormatting.GRAY + info);
    return this;
  }
  
  public MultiBlockTooltipBuilder addOtherStructurePart(String localName, String info, boolean lang) {
    sLines.add(TAB + EnumChatFormatting.GREEN + translate(localName) + ": " + EnumChatFormatting.GRAY + translate(info));
    return this;
  }

  /**
   * Call at the very end.<br> Adds a final line with the authors name and information on how to
   * display the structure guidelines.<br> Ends the building process.
   *
   * @param author Name of the creator of this Machine
   * @return The result of all build calls.
   */

  public void signAndFinalize(String author) {
    iLines.add("Hold " + EnumChatFormatting.DARK_BLUE + EnumChatFormatting.BOLD + "[LSHIFT]"
        + EnumChatFormatting.RESET + EnumChatFormatting.GRAY + " to structure");
    iLines.add(impactTag());
    iArray = new String[iLines.size()];
    sArray = new String[sLines.size()];
    iLines.toArray(iArray);
    sLines.toArray(sArray);
  }
  
  public void signAndFinalize(boolean lang, String author) {
    iLines.add(translate("tooltip.hold") + " " + EnumChatFormatting.DARK_BLUE +
            EnumChatFormatting.BOLD + "[" + translate("tooltip.left_shift") + "]" +
            EnumChatFormatting.RESET + EnumChatFormatting.GRAY + " " + translate("tooltip.to_structure"));
    iLines.add(impactTag());
    iArray = new String[iLines.size()];
    sArray = new String[sLines.size()];
    iLines.toArray(iArray);
    sLines.toArray(sArray);
  }
  
  public void signAndFinalize(String author, boolean ctrl) {
    iLines.add("Hold " + EnumChatFormatting.DARK_BLUE + EnumChatFormatting.BOLD + "[LSHIFT]"
            + EnumChatFormatting.RESET + EnumChatFormatting.GRAY + " to structure");
    iLines.add("Hold " + EnumChatFormatting.DARK_BLUE + EnumChatFormatting.BOLD + "[LCTRL]"
            + EnumChatFormatting.RESET + EnumChatFormatting.GRAY + " to more info");
    iLines.add(impactTag());
    iArray = new String[iLines.size()];
    sArray = new String[sLines.size()];
    cArray = new String[cLines.size()];
    iLines.toArray(iArray);
    sLines.toArray(sArray);
    cLines.toArray(cArray);
  }
  
  public void signAndFinalize(boolean lang, String author, boolean ctrl) {
    iLines.add(translate("tooltip.hold") + " " + EnumChatFormatting.DARK_BLUE +
            EnumChatFormatting.BOLD + "[" + translate("tooltip.left_shift") + "]" +
            EnumChatFormatting.RESET + EnumChatFormatting.GRAY + " " + translate("tooltip.to_structure"));
    iLines.add(translate("tooltip.hold") + " " + EnumChatFormatting.DARK_BLUE +
            EnumChatFormatting.BOLD + "[" + translate("tooltip.left_ctrl") + "]" +
            EnumChatFormatting.RESET + EnumChatFormatting.GRAY + " " + translate("tooltip.to_more_info"));
    iLines.add(impactTag());
    iArray = new String[iLines.size()];
    sArray = new String[sLines.size()];
    cArray = new String[cLines.size()];
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
