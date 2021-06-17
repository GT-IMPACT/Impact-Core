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
    iLines.add(translate(info));
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
    iLines.add(EnumChatFormatting.AQUA + translate("parallel.point") + ": " +
            EnumChatFormatting.GRAY + translate("parallel.point.basic") + " " + EnumChatFormatting.AQUA + Basic +
            EnumChatFormatting.GRAY + ", " + translate("max") + " " + EnumChatFormatting.AQUA + Max);
    return this;
  }
  
  
  public MultiBlockTooltipBuilder addPollution(int Min, int Max) {
    iLines.add(translate("pollution") + ": " +
            translate("min") + Min + ", " +
            translate("max") + Max);
    return this;
  }
  
  public MultiBlockTooltipBuilder addPollution(int aPollut) {
    iLines.add(translate("pollution") + " " + translate("per_second") + ": " + aPollut);
    return this;
  }
  
  public MultiBlockTooltipBuilder addPollution(int aPollut, String infoLang) {
    iLines.add(translate("pollution") + " " + translate("per_second") + ": " + aPollut + " " + translate(infoLang));
    return this;
  }
  
  public MultiBlockTooltipBuilder addTypeMachine(String info) {
    iLines.add(EnumChatFormatting.YELLOW + translate(info));
    return this;
  }
  
  public MultiBlockTooltipBuilder addScrew() {
    iLines.add(translate("screw_type"));
    return this;
  }
  
  public MultiBlockTooltipBuilder addScrew(String screw) {
    iLines.add(translate("select") + " " + translate(screw) + " " + translate("screw_type_different"));
    return this;
  }
  
  public MultiBlockTooltipBuilder addSeparatedBus() {
    iLines.add(translate("separated_bus"));
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
    sLines.add(TAB + EnumChatFormatting.GREEN + translate("parallel.hatch") + ": " + EnumChatFormatting.GRAY + translate(info));
    return this;
  }
  
  public MultiBlockTooltipBuilder addinfoB(String info) {
    cLines.add(translate(info));
    return this;
  }
  
  public MultiBlockTooltipBuilder addinfoBTab(String info) {
    cLines.add(TAB + translate(info));
    return this;
  }
  
  public MultiBlockTooltipBuilder beginStructureBlock(int w, int h, int l) {
    //sLines.add("Dimensions: " + w + "x" + h + "x" + l + " (WxHxL)");
    sLines.add(translate("begin_structure_block"));
    sLines.add(translate("structure") + ":");
    return this;
  }
  
  public MultiBlockTooltipBuilder addController() {
    sLines.add(TAB + EnumChatFormatting.YELLOW + translate("blueprint"));
    return this;
  }
  
  public MultiBlockTooltipBuilder addCasingInfo(String info) {
    sLines.add(TAB + "" + EnumChatFormatting.GREEN + translate(info) + EnumChatFormatting.GRAY + " (" + translate("at_least") + ")");
    return this;
  }
  
  public MultiBlockTooltipBuilder addEnergyHatch(String info) {
    sLines.add(TAB + EnumChatFormatting.GREEN + translate("hatch.energy") + ": " + EnumChatFormatting.GRAY + translate(info));
    return this;
  }
  
  public MultiBlockTooltipBuilder addDynamoHatch(String info) {
    sLines.add(TAB + EnumChatFormatting.GREEN + translate("hatch.dynamo") + ": " + EnumChatFormatting.GRAY + translate(info));
    return this;
  }
  
  public MultiBlockTooltipBuilder addMuffler(String info) {
    sLines.add(TAB + EnumChatFormatting.GREEN + translate("hatch.muffler") + ": " + EnumChatFormatting.GRAY + translate(info));
    return this;
  }
  
  public MultiBlockTooltipBuilder addMaintenanceHatch(String info) {
    sLines.add(TAB + EnumChatFormatting.GREEN + translate("hatch.maintenance") + ": " + EnumChatFormatting.GRAY + translate(info));
    return this;
  }
  
  public MultiBlockTooltipBuilder addIOHatches(String info) {
    sLines.add(TAB + EnumChatFormatting.GREEN + translate("hatch.io") + ": " + EnumChatFormatting.GRAY + translate(info));
    return this;
  }

  public MultiBlockTooltipBuilder addInputBus(String info) {
    sLines.add(TAB + EnumChatFormatting.GREEN + translate("hatch.bus.in") + ": " + EnumChatFormatting.GRAY + translate(info));
    return this;
  }
  
  public MultiBlockTooltipBuilder addNuclearRod(String info) {
    sLines.add(TAB + EnumChatFormatting.GREEN + translate("hatch.nuclear") + ": " + EnumChatFormatting.GRAY + translate(info));
    return this;
  }

  public MultiBlockTooltipBuilder addInputHatch(String info) {
    sLines.add(TAB + EnumChatFormatting.GREEN + translate("hatch.in") + ": " + EnumChatFormatting.GRAY + translate(info));
    return this;
  }
  
  public MultiBlockTooltipBuilder addOutputBus(String info) {
    sLines.add(TAB + EnumChatFormatting.GREEN + translate("hatch.bus.out") + ": " + EnumChatFormatting.GRAY + translate(info));
    return this;
  }

  public MultiBlockTooltipBuilder addOutputHatch(String info) {
    sLines.add(TAB + EnumChatFormatting.GREEN + translate("hatch.out") + ": " + EnumChatFormatting.GRAY + translate(info));
    return this;
  }

  /**
   * Use this method to add a structural part that isn't covered by the builders capabilities.
   *
   * @param localName Name of the hatch or other component.
   * @param info Positional information.
   * @return Instance this method was called on.
   */
  public MultiBlockTooltipBuilder addOtherStructurePart(String localName, String info) {
    sLines.add(TAB + EnumChatFormatting.GREEN + translate(localName) + ": " + EnumChatFormatting.GRAY + translate(info));
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
    iLines.add(translate("hold") + " " + EnumChatFormatting.DARK_BLUE +
            EnumChatFormatting.BOLD + "[" + translate("left_shift") + "]" +
            EnumChatFormatting.RESET + EnumChatFormatting.GRAY + " " + translate("to_structure"));
    iLines.add(impactTag());
    iArray = new String[iLines.size()];
    sArray = new String[sLines.size()];
    iLines.toArray(iArray);
    sLines.toArray(sArray);
  }
  
  public void signAndFinalize(String author, boolean ctrl) {
    iLines.add(translate("hold") + " " + EnumChatFormatting.DARK_BLUE +
            EnumChatFormatting.BOLD + "[" + translate("left_shift") + "]" +
            EnumChatFormatting.RESET + EnumChatFormatting.GRAY + " " + translate("to_structure"));
    iLines.add(translate("hold") + " " + EnumChatFormatting.DARK_BLUE +
            EnumChatFormatting.BOLD + "[" + translate("left_ctrl") + "]" +
            EnumChatFormatting.RESET + EnumChatFormatting.GRAY + " " + translate("to_more_info"));
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
