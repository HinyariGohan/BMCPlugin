package xyz.hinyari.bmcplugin.rank;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import xyz.hinyari.bmcplugin.BMCPlayer;
import xyz.hinyari.bmcplugin.BMCPlugin;
import xyz.hinyari.bmcplugin.utils.BMCUtils;
import xyz.hinyari.bmcplugin.original.DyeItem;
import xyz.hinyari.bmcplugin.utils.SpecialItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hinyari_Gohan on 2016/10/08.
 */
public class RankGUIMenu implements Listener {

    private final BMCPlugin bmcPlugin;
    private final BMCUtils utils;

    private final ItemStack ITEM_RED;
    private final ItemStack ITEM_ORAGNE;
    private final ItemStack ITEM_YELLOW;
    private final ItemStack ITEM_GREEN;
    private final ItemStack ITEM_BLUE;
    private final ItemStack ITEM_INDIGO;
    private final ItemStack ITEM_VIOLET;
    private final ItemStack ITEM_ULTRAVIOLET;
    private final ItemStack ITEM_NONE;
    private final List<ItemStack> ITEM_COLOED_LIST = new ArrayList<>();


    public RankGUIMenu(BMCPlugin bmcPlugin) {
        this.bmcPlugin = bmcPlugin;
        this.utils = bmcPlugin.utils;
        ITEM_RED = new SpecialItem(DyeItem.RED.getItemStack(), Rank.RED.getName(), null, null, 0, null).getItem();
        ITEM_ORAGNE = new SpecialItem(DyeItem.ORANGE.getItemStack(), Rank.ORANGE.getName(), null, null, 0, null).getItem();
        ITEM_YELLOW = new SpecialItem(DyeItem.YELLOW.getItemStack(), Rank.YELLOW.getName(), null, null, 0, null).getItem();
        ITEM_GREEN = new SpecialItem(DyeItem.GREEN.getItemStack(), Rank.GREEN.getName(), null, null, 0, null).getItem();
        ITEM_BLUE = new SpecialItem(DyeItem.LIGHT_BLUE.getItemStack(), Rank.BLUE.getName(), null, null, 0, null).getItem();
        ITEM_INDIGO = new SpecialItem(DyeItem.BLUE.getItemStack(), Rank.INDIGO.getName(), null, null, 0, null).getItem();
        ITEM_VIOLET = new SpecialItem(DyeItem.MAGENDA.getItemStack(), Rank.VIOLET.getName(), null, null, 0, null).getItem();
        ITEM_ULTRAVIOLET = new SpecialItem(DyeItem.WHITE.getItemStack(), Rank.ULTRAVIOLET.getName(), null, null, 0, null).getItem();
        ITEM_NONE = new SpecialItem(DyeItem.GRAY.getItemStack(), Rank.NONE.getName(), null, null, 0, null).getItem();
        init();
    }

    private void init() {
        ITEM_COLOED_LIST.add(new ItemStack(Material.AIR));
        ITEM_COLOED_LIST.add(ITEM_RED);
        ITEM_COLOED_LIST.add(ITEM_ORAGNE);
        ITEM_COLOED_LIST.add(ITEM_YELLOW);
        ITEM_COLOED_LIST.add(ITEM_GREEN);
        ITEM_COLOED_LIST.add(ITEM_BLUE);
        ITEM_COLOED_LIST.add(ITEM_INDIGO);
        ITEM_COLOED_LIST.add(ITEM_VIOLET);
        ITEM_COLOED_LIST.add(ITEM_ULTRAVIOLET);
    }

    public Inventory getMainMenu(BMCPlayer player) {
        Rank rank = player.getScoreboard().getRank();
        Inventory inventory = Bukkit.createInventory(player.getPlayer(), 9, ("BMCランクメニュー 現在のランク: " + rank.getName()));
        inventory.setItem(8, new SpecialItem(new ItemStack(Material.BARRIER), "&c- 閉じる -").getItem());
        int i = 0;
        for (Rank ranks : Rank.values()) {
            if (ranks.equals(Rank.INFRARED)) {
                continue;
            }
            if (rank.getInt() >= ranks.getInt()) {
                if (rank.getInt() == ranks.getInt()) {
                    ItemStack Final = new SpecialItem(ITEM_COLOED_LIST.get(i), null, null, Enchantment.DURABILITY, 1, ItemFlag.HIDE_ENCHANTS).getItem();
                    inventory.addItem(Final);
                    break;
                }
                inventory.addItem(ITEM_COLOED_LIST.get(i));
            } else {
                break;
            }
            i++;
        }
        return inventory;
    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event) {
        Inventory inventory = event.getClickedInventory();
        Player player = (Player) event.getWhoClicked();
        BMCPlayer bmcPlayer = bmcPlugin.getBMCPlayer(player);
        ItemStack item = event.getCurrentItem();
        String itemname = item.getItemMeta().getDisplayName();
        if (item == null) return;
        if (inventory.getName().contains("BMCランクメニュー")) {
            event.setCancelled(true);
            if (itemname.contains("閉じる")) {
                player.closeInventory();
                return;
            }
            Rank selectedRank = Rank.getInGameNameOfRank(itemname);
            if (selectedRank == null) {
                bmcPlayer.errbar("不明なエラー");
                return;
            } else {
                player.openInventory(getRankOfInventory(selectedRank));
            }
        }
    }

    @EventHandler
    public void onPlayerIntetactEvent(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {
            BMCPlayer player = bmcPlugin.getBMCPlayer(event.getPlayer());
            if (player.getItemInMainHand().equals(utils.getRankitem())) {
                Rank rank = player.getScoreboard().getRank();
                if (rank == Rank.VISITOR || rank == Rank.INFRARED) {
                    player.errbar("ランクが不正です。");
                    return;
                }
                player.openRankmenu();
            }
            if (bmcPlugin.config.getDebug()) {
                if (player.getPlayer().isSneaking()) {
                    ItemStack item = player.getItemInMainHand();
                    bmcPlugin.debug("Material : " + item.getType().toString());
                    bmcPlugin.debug("Amount : " + item.getAmount());
                    for (Enchantment ench : item.getEnchantments().keySet()) {
                        bmcPlugin.debug("Enchant(" + ench.getName() + ") : " + item.getEnchantmentLevel(ench));
                    }
                    event.setCancelled(true);
                }
            }
        }
    }

    public Inventory getRankOfInventory(Rank rank) {
        Inventory inventory = Bukkit.createInventory(null, 54, (rank.getName() + "ランクメニュー"));
        return inventory;
    }

}
