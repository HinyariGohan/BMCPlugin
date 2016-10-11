package xyz.hinyari.bmcplugin.enchant;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import xyz.hinyari.bmcplugin.BMCPlayer;
import xyz.hinyari.bmcplugin.Utils.BMCBoolean;
import xyz.hinyari.bmcplugin.BMCPlugin;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static org.bukkit.Material.*;

public class AutoSmelt implements Listener {
    private BMCPlugin bmc;
    private BMCBoolean bmcBoolean;

    public AutoSmelt(BMCPlugin bmc) {
        this.bmc = bmc;
        this.bmcBoolean = bmc.bmcBoolean;
    }

    public final String AUTO_SMELT = "Auto Smelt";

    public List<String> lore = new ArrayList<String>();

    public final Material[] AUTOSMELT_ITEMS = new Material[]{
            WOOD_PICKAXE, STONE_PICKAXE, IRON_PICKAXE, GOLD_PICKAXE, DIAMOND_PICKAXE
    };

    public final Material[] AUTOSMELT_BLOCKS = new Material[]{
            IRON_ORE, GOLD_ORE, SAND, COBBLESTONE, NETHERRACK, CACTUS, LOG, LOG_2
    };

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        BMCPlayer bmcPlayer = bmc.getBMCPlayer(player);
        Block block = event.getBlock();
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item == null) return;
        ItemMeta meta = item.getItemMeta();
        if (!(bmcBoolean.isApplyBlock(block.getType()))) return; //ブロックリストに当てはまらない
        if (!(item.hasItemMeta())) return; //アイテムメタが無い
        if (!(meta.hasLore())) return; // loreがない
        if (!(bmcBoolean.isApplyTool(item.getType()))) return; //アイテムリストに当てはまらない
        if (player.getGameMode() != GameMode.SURVIVAL) return;
        if (player.getInventory().firstEmpty() == -1) {
            bmcPlayer.errmsg("インベントリに空きが無いためAutoSmeltは実行されません!");
            bmcPlayer.playSound(Sound.BLOCK_ANVIL_LAND, 0.5F, 2);
            return;
        }
        int enchlvl = 0;
        if (meta.getLore().contains("§4Auto Smelt")) {
            int durability = item.getDurability();
            int maxdurability = item.getType().getMaxDurability();

            if (durability != maxdurability) {
                int nextdurability = item.getDurability() + 1;
                item.setDurability((short) nextdurability);
            } else {
                item.setType(AIR);
            }

            Material type = block.getType();
            event.setCancelled(true);
            block.setType(AIR);

            ItemStack dropitem = null;
            Inventory inventory = player.getInventory();
            if (item.containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)) {
                enchlvl = meta.getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS) - 1;
            }

            switch (type) {
                case IRON_ORE:
                    dropitem = new ItemStack((IRON_INGOT), enchlvl + 1);
                case GOLD_ORE:
                    dropitem = new ItemStack((GOLD_INGOT), enchlvl + 1);
                case SAND:
                    dropitem = new ItemStack((GLASS));
                case COBBLESTONE:
                    dropitem = new ItemStack((STONE));
                case NETHERRACK:
                    dropitem = new ItemStack((NETHER_BRICK_ITEM), enchlvl + 1);
                case LOG:
                    dropitem = new ItemStack((COAL), enchlvl + 1, (byte) 1);
                case LOG_2:
                    dropitem = new ItemStack((COAL), enchlvl + 1, (byte) 1);
                case CACTUS:
                    dropitem = new ItemStack((INK_SACK), enchlvl + 1, (byte) 2);
            }
            inventory.addItem(dropitem);
            bmcPlayer.playSound(Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 1.5F);
            return;
        }

    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEnchantment(EnchantItemEvent event) {
        Player player = event.getEnchanter();
        BMCPlayer bmcPlayer = bmc.getBMCPlayer(player);
        ItemStack item = event.getItem();
        ItemMeta meta = item.getItemMeta();
        Material items = item.getType();
        Map<Enchantment, Integer> ench = event.getEnchantsToAdd();
        if (bmcBoolean.isApplyTool(items)) {
            if (ench != Enchantment.SILK_TOUCH) {
                double random = Math.random();
                if (random < 0.6) return;
                else {
                    if (lore.isEmpty()) {
                        lore.add(0, "§4Auto Smelt");
                    }
                    bmcPlayer.msg("§4Auto Smelt§rエンチャントがつきました！");
                    meta.setLore(lore);
                    item.setItemMeta(meta);
                }
            }
        }
    }
}


