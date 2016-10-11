package xyz.hinyari.bmcplugin.rank;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
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
import xyz.hinyari.bmcplugin.Utils.BMCUtils;
import xyz.hinyari.bmcplugin.original.DyeItem;

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
        ITEM_RED = utils.createSpecialItem(DyeItem.RED.getItemStack(), Rank.RED.getName(), null, null, 0, null);
        ITEM_ORAGNE = utils.createSpecialItem(DyeItem.ORANGE.getItemStack(), Rank.ORANGE.getName(), null, null, 0, null);
        ITEM_YELLOW = utils.createSpecialItem(DyeItem.YELLOW.getItemStack(), Rank.YELLOW.getName(), null, null, 0, null);
        ITEM_GREEN = utils.createSpecialItem(DyeItem.GREEN.getItemStack(), Rank.GREEN.getName(), null, null, 0, null);
        ITEM_BLUE = utils.createSpecialItem(DyeItem.LIGHT_BLUE.getItemStack(), Rank.BLUE.getName(), null, null, 0, null);
        ITEM_INDIGO = utils.createSpecialItem(DyeItem.BLUE.getItemStack(), Rank.INDIGO.getName(), null, null, 0, null);
        ITEM_VIOLET = utils.createSpecialItem(DyeItem.MAGENDA.getItemStack(), Rank.VIOLET.getName(), null, null, 0, null);
        ITEM_ULTRAVIOLET = utils.createSpecialItem(DyeItem.WHITE.getItemStack(), Rank.ULTRAVIOLET.getName(), null, null, 0, null);
        ITEM_NONE = utils.createSpecialItem(DyeItem.GRAY.getItemStack(), Rank.NONE.getName(), null, null, 0, null);
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
        int i = 0;
        for (Rank ranks : Rank.values()) {
            if (rank.getInt() >= ranks.getInt() ) {
                if (rank.getInt() == ranks.getInt()) {
                    inventory.addItem(utils.createSpecialItem(ITEM_COLOED_LIST.get(i), "saishu", null, Enchantment.DURABILITY, 1, ItemFlag.HIDE_ENCHANTS));
                    break;
                }
                inventory.addItem(ITEM_COLOED_LIST.get(i));
            } else {break;}
            i++;
        }
        return inventory;
    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event) {

    }

    @EventHandler
    public void onPlayerIntetactEvent(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {
            BMCPlayer player = bmcPlugin.getBMCPlayer(event.getPlayer());
            if (player.getItemInMainHand().equals(utils.getRankitem())) {
                player.openRankmenu();
            }
        }
    }

}
