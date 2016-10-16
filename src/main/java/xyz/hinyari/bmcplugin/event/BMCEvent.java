package xyz.hinyari.bmcplugin.event;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityCreatePortalEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import xyz.hinyari.bmcplugin.BMCPlayer;
import xyz.hinyari.bmcplugin.BMCPlugin;
import xyz.hinyari.bmcplugin.utils.BMCBoolean;

/**
 * BMCサーバー メインイベントキャッチクラス
 *
 * @author Hinyari_Gohan
 */
public class BMCEvent implements Listener {

    private BMCPlugin bmc;
    private BMCBoolean bmcBoolean;

    /**
     * コンストラクター
     *
     * @param bmc メインクラス
     */
    public BMCEvent(BMCPlugin bmc) {
        this.bmc = bmc;
        this.bmcBoolean = bmc.bmcBoolean;
    }

    private int LIMIT = Integer.MAX_VALUE;

    /**
     * エンダードラゴンのポータル作成をキャンセル
     *
     * @param event
     */
    @EventHandler
    public void onEntityCreatePortalEvent(EntityCreatePortalEvent event) {
        if (event.getEntity() instanceof EnderDragon) {
            event.setCancelled(true);
        }
    }


    /**
     * コシヒカリを食べるイベント
     *
     * @param event PlayerItemConsumeEvent
     */
    @EventHandler
    public void onEat(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        String playername = player.getDisplayName();
        World world = player.getWorld();
        Location loc = player.getLocation();

        //それがコシヒカリであるか
        if (bmcBoolean.isKoshihikari(item)) { //耐久力1
            event.setCancelled(true);
            player.setFoodLevel(40);
            player.setExhaustion(40);
            player.setHealth(20);
            Bukkit.broadcastMessage(bmc.config.getPrefix() + playername + " さんが コシヒカリ を食べました。");
            //player.chat("コシヒカリうますギィ！");
            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 12000, 4));
            player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 12000, 1));
            player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 12000, 1));
            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 12000, 2));
            world.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EAT, 1, 1);
            world.playSound(loc, Sound.BLOCK_PORTAL_TRAVEL, 1, 2);
            player.getInventory().remove(item);
            player.getInventory().remove(Material.BOWL);
        }
    }


    /**
     * 特殊エンダードラゴン
     *
     * @param event
     */
    @EventHandler
    public void onCreatureSpawnEvent(CreatureSpawnEvent event) {
        LivingEntity entity = event.getEntity();
        String name = entity.getCustomName();
        if (event.getEntityType() == EntityType.ENDER_DRAGON) {
            if (name == null) return;
            else if (name.equals("§b中級§rドラゴン")) {
                entity.setMaxHealth(500);
                entity.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, LIMIT, 1));
                entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, LIMIT, 2));
            } else if (name.equals("§c上級§rドラゴン")) {
                entity.setMaxHealth(800);
                entity.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, LIMIT, 3));
                entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, LIMIT, 3));
            }
        } else if (event.getEntityType() == EntityType.WITHER && (!(event.getLocation().getWorld().equals("world_the_end")))) {
            ItemStack item1 = new ItemStack((Material.SKULL_ITEM), 3, (byte) 1);
            ItemStack item2 = new ItemStack((Material.SOUL_SAND), 4);
            event.getLocation().getWorld().dropItemNaturally(event.getLocation(), item1);
            event.getLocation().getWorld().dropItemNaturally(event.getLocation(), item2);
            event.getEntity().remove();
            return;
        }
    }

    /**
     * ドラゴン死亡時、アイテムドロップ
     *
     * @param event
     */
    @EventHandler
    public void onEntityDeathEvent(EntityDeathEvent event) {
        LivingEntity entity = event.getEntity();
        String name = entity.getCustomName();
        ItemStack dropItem = new ItemStack((Material.DRAGON_EGG), 1);
        if (entity instanceof EnderDragon) {
            if (name != null) {
                if (name.equals("§b中級§rドラゴン") || name.equals("§c上級§rドラゴン")) {
                    entity.getWorld().dropItem(entity.getLocation(), dropItem);
                }
            }
        }
    }

    /**
     * OPコマンドを試行した場合、キャンセルし、権限者への通知を行う
     *
     * @param event
     */
    @EventHandler
    public void onCommandProsess(PlayerCommandPreprocessEvent event) {
        BMCPlayer bmcPlayer = bmc.getBMCPlayer(event.getPlayer());
        Bukkit.broadcast("§7[BMC]" + bmcPlayer.getName() + ": " +event.getMessage(), "bmc.nclv");
    }

    /**
     * BMC特殊ブロックの設置を禁止する。
     *
     * @param event
     */
    @EventHandler
    public void BlockPlaceBlocker(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        BMCPlayer bmcPlayer = bmc.getBMCPlayer(player);
        ItemStack item = bmcPlayer.getItemInMainHand();

        if (!(item.hasItemMeta())) return;


        ItemMeta meta = item.getItemMeta();

        if (!(meta.hasDisplayName())) return;

        String name = meta.getDisplayName();

        if (name.contains("炭素の塊") || name.contains("採掘の結晶") || name.contains("Water") || name.contains("Crushed") || name.contains("Cleaned")) {
            event.setCancelled(true);
            bmcPlayer.errmsg("特殊ブロックを設置することは出来ません。");
            bmcPlayer.playSound(Sound.BLOCK_NOTE_SNARE, 10L, 1L);
        }
    }

}