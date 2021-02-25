package com.impact.mods.nei.oreplugin.helper;

import cpw.mods.fml.common.FMLLog;
import gregtech.api.GregTech_API;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

@SuppressWarnings("ALL")
public class GT5CFGHelper {

  private static File F = GregTech_API.sWorldgenFile.mConfig.getConfigFile();

  public static String GT5CFGSmallOres(String Veinname) {
    List<String> raw = new ArrayList<>();
    List<String> rawbools = new ArrayList<>();
    String st = null;
    Configuration c = new Configuration(F);
    ConfigCategory configCategory = c.getCategory("worldgen." + Veinname);
    for (Property p : configCategory.getOrderedValues()) {
      if (p.isBooleanValue() && p.getBoolean()) {
        raw.add(p.getName() + "=" + p.getBoolean());
      }
    }
    if (!raw.isEmpty()) {
      for (int i = 0; i < raw.size(); i++) {
        for (int j = 0; j < DimensionHelper.DimName.length; j++) {
          if (raw.get(i).contains(DimensionHelper.DimName[j])) {
            rawbools.add(raw.get(i));
          }
        }
      }
    }

    String ret = " ";

    HashSet<String> rawboolsset = new HashSet<String>();
    if (!rawbools.isEmpty()) {
      for (int i = 0; i < rawbools.size(); i++) {
        st = rawbools.get(i).replace("B:", "").replace("_true", "").replace("_false", "")
            .replaceAll(" ", "").replaceAll("\"", "");
        rawboolsset.add(st);
      }
      rawbools = new ArrayList<String>(rawboolsset);
      for (int i = 0; i < rawbools.size(); i++) {
        st = rawbools.get(i);
        for (int j = 0; j < DimensionHelper.DimName.length; j++) {
          if (st.contains(DimensionHelper.DimName[j])) {
            if (st.contains("=true")) {
              ret = (ret + DimensionHelper.DimNameDisplayed[j] + ",");
            }
          }
        }
      }
    }
    ret = ret.trim();
    if (ret.equals("") || ret.equals(" ")) {
      ret = "Not aviable in any Galactic Dim!";
    }
    return ret;
  }

  public static String GT5CFG(String Veinname) {
    if (F == null) {
      FMLLog.bigWarning("GT_CFG_NOT_found[0]");
      return "Error while Loading CFG";
    } else {
      try {
        int buffer = (int) (0.1 * Runtime.getRuntime().freeMemory());
        if (buffer > F.length()) {
          buffer = (int) F.length();
        }
        //allocate 10% of free memory for read-in-buffer, if there is less than filesize memory aviable
        FileReader in = new FileReader(F);
        BufferedReader reader = new BufferedReader(in, buffer);
        String st = null;
        List<String> raw = new ArrayList<String>();
        List<String> rawbools = new ArrayList<String>();
        Boolean[] found = new Boolean[2];
        found[0] = false;
        found[1] = false;

        do {
          //read until reached eof or mix {
          st = reader.readLine();
          if (st != null && st.trim().equals("mix {")) {
            while (!((st == null) || ((st != null) && found[0]))) {
              st = reader.readLine();
              //read until reached eof or Veinname {
              if (st != null && st.trim().equals(Veinname + " {")) {
                while (!((st == null) || ((st != null) && found[0]))) {
                  st = reader.readLine();
                  if ((!(st == null)) && st.trim().equals("}")) {
                    found[0] = true;
                  }
                  //add everything below Veinname { undtil } to raw
                  raw.add(st);
                }
              }
            }
          }

          if (st != null && st.trim().equals("dimensions {")) {
            while (!((st == null) || ((st != null) && found[1]))) {
              st = reader.readLine();
              if (st != null && (st.trim().equals("mix {"))) {
                while (!((st == null) || ((st != null) && found[1]))) {
                  st = reader.readLine();
                  //read until reached eof or Veinname {
                  if (st != null && st.trim().equals(Veinname + " {")) {
                    while (!((st == null) || ((st != null) && found[1]))) {
                      st = reader.readLine();
                      if ((!(st == null)) && st.trim().equals("}")) {
                        found[1] = true;
                      }
                      //add everything below Veinname { undtil } to raw
                      raw.add(st);
                    }
                  }
                }
              }
            }
          }
        } while (st != null);
        reader.close();//not needed anymore

        if (!raw.isEmpty()) {
          for (int i = 0; i < raw.size(); i++) {
            //filter needed booleans from raw
            for (int j = 0; j < DimensionHelper.DimName.length; j++) {
              if (raw.get(i).contains(DimensionHelper.DimName[j])) {
                rawbools.add(raw.get(i));
              }
            }
          }
        }

        String ret = " ";

        HashSet<String> rawboolsset = new HashSet<String>();
        if (!rawbools.isEmpty()) {
          //remove dublicats
          for (int i = 0; i < rawbools.size(); i++) {
            st = rawbools.get(i).replace("B:", "").replace("_true", "").replace("_false", "")
                .replaceAll(" ", "").replaceAll("\"", "");
            rawboolsset.add(st);
          }
          rawbools = new ArrayList<String>(rawboolsset);
          //filter for dims set to true
          for (int i = 0; i < rawbools.size(); i++) {
            st = rawbools.get(i);
            for (int j = 0; j < DimensionHelper.DimName.length; j++) {
              if (st.contains(DimensionHelper.DimName[j])) {
                if (st.contains("=true")) {
                  ret = (ret + DimensionHelper.DimNameDisplayed[j] + ",");
                }
              }
            }
          }
        }
        ret = ret.trim();
        if (ret.equals("") || ret.equals(" ")) {
          ret = "Not aviable in any Galactic Dim!";
        }
        return ret;
      } catch (IOException e) {
        e.printStackTrace();
        return "Error while Loading CFG";
      }
    }
  }
}
