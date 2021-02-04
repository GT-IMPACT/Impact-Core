package com.impact.util;

import java.util.LinkedList;
import java.util.List;
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

  public MultiBlockTooltipBuilder addPollution(int Min, int Max) {
    iLines.add("Pollution: min " + Min + ", max " + Max);
    return this;
  }

  public MultiBlockTooltipBuilder addPollution(int aPollut) {
    iLines.add("Pollution per second: " + aPollut);
    return this;
  }

  public MultiBlockTooltipBuilder addPollution(int aPollut, String info) {
    iLines.add("Pollution per second: " + aPollut + " " + info);
    return this;
  }

  public MultiBlockTooltipBuilder addTypeMachine(String info) {
    iLines.add(EnumChatFormatting.YELLOW + info);
    return this;
  }

  public MultiBlockTooltipBuilder addScrew() {
    iLines.add("Select type machine with Screwdriver click in Controller");
    return this;
  }

  public MultiBlockTooltipBuilder addSeparatedBus() {
    iLines.add("Select Separated Buses mod with Shift + Screwdriver click in Controller");
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

  public MultiBlockTooltipBuilder addParallelCase(String info) {
    sLines.add(TAB + EnumChatFormatting.RED + "Upgrade Case: " + EnumChatFormatting.GRAY + info);
    return this;
  }

  public MultiBlockTooltipBuilder addinfoB(String info) {
    cLines.add(info);
    return this;
  }

  public MultiBlockTooltipBuilder addinfoBTab(String info) {
    cLines.add(TAB + info);
    return this;
  }

  public MultiBlockTooltipBuilder beginStructureBlock(int w, int h, int l) {
    //sLines.add("Dimensions: " + w + "x" + h + "x" + l + " (WxHxL)");
    sLines.add(EnumChatFormatting.RED + "[WIP]" + EnumChatFormatting.GRAY + " Coming soon..");
    sLines.add("Structure:");
    return this;
  }

  public MultiBlockTooltipBuilder beginStructureBlock(int w, int h, int l, boolean structure) {
    sLines.add("Dimensions: " + w + "x" + h + "x" + l + " (WxHxL)");
    sLines.add("Structure:");
    return this;
  }

  public MultiBlockTooltipBuilder addController() {
    sLines.add(TAB + EnumChatFormatting.YELLOW + "Use Blueprint TecTech ");
    return this;
  }

  public MultiBlockTooltipBuilder addCasingInfo(String info) {
    sLines
        .add(TAB + "" + EnumChatFormatting.GREEN + info + EnumChatFormatting.GRAY + " (at least)");
    return this;
  }

  public MultiBlockTooltipBuilder addEnergyHatch(String info) {
    sLines.add(TAB + EnumChatFormatting.GREEN + "Energy Hatch: " + EnumChatFormatting.GRAY + info);
    return this;
  }

  public MultiBlockTooltipBuilder addDynamoHatch(String info) {
    sLines.add(TAB + EnumChatFormatting.GREEN + "Dynamo Hatch: " + EnumChatFormatting.GRAY + info);
    return this;
  }

  public MultiBlockTooltipBuilder addMuffler(String info) {
    sLines.add(TAB + EnumChatFormatting.GREEN + "Muffler Hatch: " + EnumChatFormatting.GRAY + info);
    return this;
  }

  public MultiBlockTooltipBuilder addMaintenanceHatch(String info) {
    sLines.add(
        TAB + EnumChatFormatting.GREEN + "Maintenance Hatch: " + EnumChatFormatting.GRAY + info);
    return this;
  }

  public MultiBlockTooltipBuilder addIOHatches(String info) {
    sLines.add(TAB + EnumChatFormatting.GREEN + "I/O Hatches: " + EnumChatFormatting.GRAY + info);
    return this;
  }

  public MultiBlockTooltipBuilder addInputBus(String info) {
    sLines.add(TAB + EnumChatFormatting.GREEN + "Input Bus: " + EnumChatFormatting.GRAY + info);
    return this;
  }

  public MultiBlockTooltipBuilder addInputHatch(String info) {
    sLines.add(TAB + EnumChatFormatting.GREEN + "Input Hatch: " + EnumChatFormatting.GRAY + info);
    return this;
  }

  public MultiBlockTooltipBuilder addOutputBus(String info) {
    sLines.add(TAB + EnumChatFormatting.GREEN + "Output Bus: " + EnumChatFormatting.GRAY + info);
    return this;
  }

  public MultiBlockTooltipBuilder addOutputHatch(String info) {
    sLines.add(TAB + EnumChatFormatting.GREEN + "Output Hatch: " + EnumChatFormatting.GRAY + info);
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
    iLines.add("Added by " + author);
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
    iLines.add("Added by " + author);
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
