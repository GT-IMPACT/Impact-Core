package com.impact.mods.gregtech.items.tools;

import codechicken.nei.recipe.GuiCraftingRecipe;
import com.impact.mods.gregtech.enums.Texture;
import gregtech.api.GregTech_API;
import gregtech.api.interfaces.IIconContainer;
import gregtech.api.interfaces.IToolStats;
import gregtech.api.items.GT_MetaGenerated_Tool;
import gregtech.api.util.GT_Utility;
import gregtech.common.tools.GT_Tool;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.world.BlockEvent;

import java.util.List;

import static com.impact.util.ItemNBTHelper.getBoolean;
import static com.impact.util.ItemNBTHelper.setBoolean;
import static com.impact.util.vector.ToolsVector.raytraceFromEntity;

public class ForgeHammer extends GT_Tool implements IToolStats, IImpact_Tools {

    public int breakRadius;
    public int breakDepth;
    public IToolStats iToolStats;

    public ForgeHammer(int aBreakRadius, int aBreakDepth) {
        breakRadius = aBreakRadius;
        breakDepth = aBreakDepth;
        iToolStats = this;
    }

    public int getToolDamagePerBlockBreak() {
        return 20;
    }

    public int getToolDamagePerDropConversion() {
        return 20;
    }

    public int getToolDamagePerContainerCraft() {
        return 100;
    }

    public int getToolDamagePerEntityAttack() {
        return 200;
    }

    public int getBaseQuality() {
        return 0;
    }

    public float getBaseDamage() {
        return 10.0F;
    }

    public float getSpeedMultiplier() {
        return 3.0F;
    }

    public float getMaxDurabilityMultiplier() {
        return 4.5F;
    }

    public String getCraftingSound() {
        return GregTech_API.sSoundList.get(106);
    }

    public String getEntityHitSound() {
        return GregTech_API.sSoundList.get(106);
    }

    public String getBreakingSound() {
        return GregTech_API.sSoundList.get(106);
    }

    public String getMiningSound() {
        return GregTech_API.sSoundList.get(106);
    }

    public boolean canBlock() {
        return false;
    }

    public boolean isCrowbar() {
        return false;
    }

    @Override
    public boolean isMinableBlock(Block aBlock, byte aMetaData) {
        String tTool = aBlock.getHarvestTool(aMetaData);
        return aBlock.getHarvestLevel(aMetaData) != -1 && (tTool == null || tTool.isEmpty() || (tTool.equals("pickaxe"))) || (aBlock.getMaterial() == Material.rock) || (aBlock.getMaterial() == Material.iron) || (aBlock.getMaterial() == Material.anvil) || (aBlock.getMaterial() == Material.glass);
    }

    @Override
    public IIconContainer getIcon(boolean b, ItemStack itemStack) {
        return b ? Texture.Icons.FORGE_HAMMER_HEAD : Texture.Icons.FORGE_HAMMER_HANDLE;
    }

    public void onToolCrafted(ItemStack aStack, EntityPlayer aPlayer) {
        super.onToolCrafted(aStack, aPlayer);
    }
    
    public void onStatsAddedToTool(GT_MetaGenerated_Tool aItem, int aID) {
        aItem.addItemBehavior(aID, null);
    }

    @Override
    public short[] getRGBa(boolean aIsToolHead, ItemStack aStack) {
        return aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mRGBa : GT_MetaGenerated_Tool.getSecondaryMaterial(aStack).mRGBa;
    }

    @Override
    public IChatComponent getDeathMessage(EntityLivingBase aPlayer, EntityLivingBase aEntity) {
        return new ChatComponentText(EnumChatFormatting.GREEN + aPlayer.getCommandSenderName() + EnumChatFormatting.WHITE + " Forge Hammer through the yard of " + EnumChatFormatting.RED + aEntity.getCommandSenderName() + EnumChatFormatting.WHITE);
    }

    @Override
    public boolean startBreak(ItemStack stack, int x, int y, int z, EntityPlayer player) {
        // only effective materials matter. We don't want to aoe when beraking dirt with a hammer.

        Block block = player.worldObj.getBlock(x, y, z);
        int meta = player.worldObj.getBlockMetadata(x, y, z);

        if (block == null || !stack.hasTagCompound())
            return false;

        MovingObjectPosition mop = raytraceFromEntity(player.worldObj, player, false, 4.5d);
        if (mop == null)
            return false;
        int sideHit = mop.sideHit;
        //int sideHit = Minecraft.getMinecraft().objectMouseOver.sideHit;

        // we successfully destroyed a block. time to do AOE!
        int xRange = breakRadius;
        int yRange = breakRadius;
        int zRange = breakDepth;
        switch (sideHit) {
            case 0:
            case 1:
                yRange = breakDepth;
                zRange = breakRadius;
                break;
            case 2:
            case 3:
                xRange = breakRadius;
                zRange = breakDepth;
                break;
            case 4:
            case 5:
                xRange = breakDepth;
                zRange = breakRadius;
                break;
        }
            for (int xPos = x - xRange; xPos <= x + xRange; xPos++) {
                for (int yPos = y - yRange; yPos <= y + yRange; yPos++) {
                    for (int zPos = z - zRange; zPos <= z + zRange; zPos++) {
                        if (xPos == x && yPos == y && zPos == z) continue;
                        breakBlock(player.worldObj, xPos, yPos, zPos, sideHit, player, x, y, z);
                    }
                }
            }
        return false;
    }

    public void breakBlock(World world, int x, int y, int z, int sideHit, EntityPlayer playerEntity, int refX, int refY, int refZ) {
        if (world.isAirBlock(x, y, z)) return;

        if (!(playerEntity instanceof EntityPlayerMP)) return;
        EntityPlayerMP player = (EntityPlayerMP) playerEntity;

        Block block = world.getBlock(x, y, z);
        int meta = world.getBlockMetadata(x, y, z);

        Block refBlock = world.getBlock(refX, refY, refZ);
        float refStrength = ForgeHooks.blockStrength(refBlock, player, world, refX, refY, refZ);
        float strength = ForgeHooks.blockStrength(block, player, world, x, y, z);

        if (!ForgeHooks.canHarvestBlock(block, player, meta) || refStrength / strength > 10f) return;

        BlockEvent.BreakEvent event = ForgeHooks.onBlockBreakEvent(world, player.theItemInWorldManager.getGameType(), player, x, y, z);
        if (event.isCanceled())
            return;

        if (player.capabilities.isCreativeMode) {
            block.onBlockHarvested(world, x, y, z, meta, player);
            if (block.removedByPlayer(world, player, x, y, z, false))
                block.onBlockDestroyedByPlayer(world, x, y, z, meta);
            if (!world.isRemote) {
                player.playerNetServerHandler.sendPacket(new S23PacketBlockChange(x, y, z, world));
            }
            return;
        }
        ItemStack currentItem = player.getCurrentEquippedItem();
        if (currentItem != null) {
            currentItem.func_150999_a(world, block, x, y, z, player);
        }
        if (!world.isRemote) {
            block.onBlockHarvested(world, x, y, z, meta, player);

            if (block.removedByPlayer(world, player, x, y, z, true)) {
                block.onBlockDestroyedByPlayer(world, x, y, z, meta);
                block.harvestBlock(world, player, x, y, z, meta);
                block.dropXpOnBlockBreak(world, x, y, z, event.getExpToDrop());
            }

            player.playerNetServerHandler.sendPacket(new S23PacketBlockChange(x, y, z, world));
        } else {
            world.playAuxSFX(2001, x, y, z, Block.getIdFromBlock(block) + (meta << 12));
            if (block.removedByPlayer(world, player, x, y, z, true)) {
                block.onBlockDestroyedByPlayer(world, x, y, z, meta);
            }
            ItemStack itemstack = player.getCurrentEquippedItem();
            if (itemstack != null) {
                itemstack.func_150999_a(world, block, x, y, z, player);

                if (itemstack.stackSize == 0) {
                    player.destroyCurrentEquippedItem();
                }
            }
        }
    }

    @Override
    public IToolStats getTools() {
        return iToolStats;
    }
    
    @Override
    public boolean canAdDrop(ItemStack stack) {
        return getBoolean(stack, "adDrop", false);
    }
    
    @Override
    public boolean onItemUse(ItemStack aStack, EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ, int aSide, float hitX, float hitY, float hitZ) {
        return false;
    }
    
    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void addAdditionalToolTips(List aList, ItemStack aStack, EntityPlayer aPlayer) {
        boolean mode = getBoolean(aStack, "adDrop", false);
        aList.add("Shift + Right click on the block to open recipes NEI");
        aList.add("Right click on the block to change harvester mode");
        aList.add(EnumChatFormatting.WHITE + "Advanced Harvester Mode: " + EnumChatFormatting.GREEN + (mode ? "Enable" : "Disable"));
    }
    
    
    @Override
    public boolean onItemUseFirst(ItemStack aStack, EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ, int aSide, float hitX, float hitY, float hitZ) {
        if (!aWorld.isRemote) {
            if (!aPlayer.isSneaking()) {
                boolean statusAdDrop = getBoolean(aStack, "adDrop", false);
                statusAdDrop = !statusAdDrop;
                setBoolean(aStack, "adDrop", statusAdDrop);
                GT_Utility.sendChatToPlayer(aPlayer, "Advanced Drop Harvester: " + (statusAdDrop ? "Enabled" : "Disabled"));
            }
        } else {
            if (aPlayer.isSneaking()) {
                GuiCraftingRecipe.openRecipeGui("drops_hammer");
            }
        }
        return false;
    }
}