package com.impact.mods.GregTech.tileentities.multi.generators.sofc;

import static com.github.technus.tectech.mechanics.constructable.IMultiblockInfoContainer.registerMetaClass;
import static com.github.technus.tectech.mechanics.structure.StructureUtility.ofBlock;

import com.github.technus.tectech.mechanics.alignment.enumerable.ExtendedFacing;
import com.github.technus.tectech.mechanics.constructable.IMultiblockInfoContainer;
import com.github.technus.tectech.mechanics.structure.IStructureDefinition;
import com.github.technus.tectech.mechanics.structure.StructureDefinition;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import ic2.core.Ic2Items;
import ic2.core.util.StackUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class GTMTE_SOFC_II extends GTMTE_SOFC {

  public int TIER;

  public GTMTE_SOFC_II(int aID, String aName, String aNameRegional, int aTier) {
    super(aID, aName, aNameRegional);
    this.TIER = aTier;
    run();
  }

  public GTMTE_SOFC_II(String aName, int aTier) {
    super(aName);
    this.TIER = aTier;
    run();
  }

  @Override
  public IMetaTileEntity newMetaEntity(IGregTechTileEntity var1) {
    run();
    return new GTMTE_SOFC_II(super.mName, this.TIER);
  }

  @Override
  int getTier() {
    return TIER;
  }

  public void run() {
    registerMetaClass(GTMTE_SOFC_II.class, new IMultiblockInfoContainer<GTMTE_SOFC_II>() {
      //region Structure
      private final IStructureDefinition<GTMTE_SOFC_II> definition =
          StructureDefinition.<GTMTE_SOFC_II>builder()
              .addShape("main", new String[][]{
                  {"AAA", "A~A", "AAA"},
                  {"AAA", "CBC", "AAA"},
                  {"AAA", "CBC", "AAA"},
                  {"AAA", "CBC", "AAA"},
                  {"AAA", "AAA", "AAA"}
              })
              .addElement('A', ofBlock(CASING, 2))
              .addElement('B', ofBlock(CERAMIC, 1))
              .addElement('C', ofBlock(StackUtil.getBlock(Ic2Items.reinforcedGlass)))
              .build();
      private final String[] desc = new String[]{
          EnumChatFormatting.RED + "Impact Details:",
          "- Stable Titanium Machine Casing",
          "- GDC Ceramic Unit",
          "- Reinforced Glass",
          "- Hatches (any Casing)",
          "- Dynamo (backside any Casing)",
      };
      //endregion

      @Override
      public void construct(ItemStack stackSize, boolean hintsOnly, GTMTE_SOFC_II tileEntity,
          ExtendedFacing aSide) {
        IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
        definition.buildOrHints(tileEntity, stackSize, "main", base.getWorld(), aSide,
            base.getXCoord(), base.getYCoord(), base.getZCoord(),
            1, 1, 0, hintsOnly);
      }

      @Override
      public String[] getDescription(ItemStack stackSize) {
        return desc;
      }
    });
  }
}
