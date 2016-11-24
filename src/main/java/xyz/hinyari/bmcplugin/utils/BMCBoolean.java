package xyz.hinyari.bmcplugin.utils;

import xyz.hinyari.bmcplugin.BMCPlugin;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class BMCBoolean {

    private final BMCPlugin plugin;

    public BMCBoolean(BMCPlugin plugin) {
        this.plugin = plugin;
    }

    public boolean isKoshihikari(ItemStack item) {
        return item.equals(plugin.utils.getKoshihikari());
    }

    public boolean isGrappleItem(ItemStack stack) {
        if (stack != null && stack.hasItemMeta() && stack.getItemMeta().hasDisplayName()) {
            String name = stack.getItemMeta().getDisplayName();
            String grappleName = "§6Grapple";
            if (name.contains(grappleName)) return true;
        }
        return false;
    }

    public boolean isApplyTool(Material item) {
        return item == Material.WOOD_PICKAXE || item == Material.WOOD_SPADE || item == Material.WOOD_AXE || item == Material.STONE_PICKAXE || item == Material.STONE_AXE || item == Material.STONE_SPADE || item == Material.IRON_PICKAXE || item == Material.IRON_AXE || item == Material.IRON_SPADE || item == Material.GOLD_AXE || item == Material.GOLD_PICKAXE || item == Material.GOLD_SPADE || item == Material.DIAMOND_AXE || item == Material.DIAMOND_PICKAXE || item == Material.DIAMOND_SPADE;
    }

    public boolean isApplyBlock(Material block) {
        if (block == Material.IRON_ORE || block == Material.GOLD_ORE || block == Material.SAND || block == Material.COBBLESTONE || block == Material.NETHERRACK || block == Material.CACTUS || block == Material.LOG || block == Material.LOG_2 || block == Material.SPONGE)
            return true;
        return false;
    }

}
