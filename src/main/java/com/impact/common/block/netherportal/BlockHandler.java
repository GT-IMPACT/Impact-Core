package com.impact.common.block.netherportal;

import cpw.mods.fml.common.registry.FMLControlledNamespacedRegistry;
import cpw.mods.fml.common.registry.GameData;
import java.lang.reflect.Field;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ObjectIntIdentityMap;
import net.minecraft.util.RegistryNamespaced;
import net.minecraft.util.RegistrySimple;

public class BlockHandler {

  public static void replaceBlock(Block toReplace, Class<? extends Block> blockClass,
      Class<? extends ItemBlock> itemBlockClass) {
    Class<?>[] classTest = new Class[4];
    Exception exception = null;

    try {
      Field modifiersField = Field.class.getDeclaredField("modifiers");
      modifiersField.setAccessible(true);

      for (Field blockField : Blocks.class.getDeclaredFields()) {
        if (Block.class.isAssignableFrom(blockField.getType())) {
          Block block = (Block) blockField.get(null);

          if (block == toReplace) {
            String registryName = Block.blockRegistry.getNameForObject(block);
            int id = Block.getIdFromBlock(block);

            Block newBlock = blockClass.newInstance();
            FMLControlledNamespacedRegistry<Block> registryBlocks = GameData.getBlockRegistry();
            Field map1 = RegistrySimple.class.getDeclaredFields()[1];
            map1.setAccessible(true);
            ((Map<String, Block>) map1.get(registryBlocks)).put(registryName, newBlock);

            Field map2 = RegistryNamespaced.class.getDeclaredFields()[0];
            map2.setAccessible(true);
            ((ObjectIntIdentityMap) map2.get(registryBlocks)).func_148746_a(newBlock, id);

            blockField.setAccessible(true);
            modifiersField.setInt(blockField, blockField.getModifiers() & 0xFFFFFFEF);
            blockField.set(null, newBlock);

            ItemBlock itemBlock = itemBlockClass.getConstructor(new Class[]{Block.class})
                .newInstance(newBlock);
            FMLControlledNamespacedRegistry<Item> registryItems = GameData.getItemRegistry();
            ((Map<String, ItemBlock>) map1.get(registryItems)).put(registryName, itemBlock);
            ((ObjectIntIdentityMap) map2.get(registryItems)).func_148746_a(itemBlock, id);

            classTest[0] = blockField.get(null).getClass();
            classTest[1] = Block.blockRegistry.getObjectById(id).getClass();
            classTest[2] = ((ItemBlock) Item.getItemFromBlock(newBlock)).field_150939_a.getClass();
            classTest[3] = Item.getItemFromBlock(newBlock).getClass();
          }
        }
      }
    } catch (Exception e) {
      exception = e;
    }

    if (classTest[0] != classTest[1] || classTest[0] != classTest[2] || classTest[0] == null
        || classTest[3] != itemBlockClass) {
      throw new RuntimeException(
          "unable to replace block " + toReplace.getUnlocalizedName()
              + "! Debug info to report: "
              + classTest[0] + "," + classTest[1] + "," + classTest[2] + "," + classTest[3],
          exception);
    }
  }
}
