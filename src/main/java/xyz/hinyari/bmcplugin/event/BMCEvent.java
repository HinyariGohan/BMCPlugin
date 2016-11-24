package xyz.hinyari.bmcplugin.event;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityCreatePortalEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemFactory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import xyz.hinyari.bmcplugin.BMCPlayer;
import xyz.hinyari.bmcplugin.BMCPlugin;
import xyz.hinyari.bmcplugin.utils.BMCBoolean;
import xyz.hinyari.bmcplugin.utils.SpecialItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * BMCサーバー メインイベントキャッチクラス
 *
 * @author Hinyari_Gohan
 */
public class BMCEvent implements Listener {

    private BMCPlugin bmc;
    private BMCBoolean bmcBoolean;

    private List<UUID> eat_komeUser = new ArrayList<>();


    /**
     * コンストラクター
     *
     * @param bmc メインクラス
     */
    public BMCEvent(BMCPlugin bmc) {
        this.bmc = bmc;
        this.bmcBoolean = bmc.bmcBoolean;
    }

    /**
     * コシヒカリを食べるイベント
     *
     * @param event PlayerItemConsumeEvent
     */
    @EventHandler
    public void onEat(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        BMCPlayer bmcPlayer = bmc.getBMCPlayer(player);
        ItemStack item = player.getInventory().getItemInMainHand();
        World world = player.getWorld();
        Location loc = player.getLocation();
        int godtime = bmc.config.getKomeGodTime();

        //それがコシヒカリであるか
        if (bmcBoolean.isKoshihikari(item)) { //耐久力1
            event.setCancelled(true);
            player.setFoodLevel(40);
            player.setExhaustion(40);
            player.setHealth(20);
            Bukkit.broadcastMessage(bmc.config.getPrefix() + player.getName() + " さんが §6コシヒカリ§r を食べました。");
            //player.chat("コシヒカリうますギィ！");
            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, godtime, 4));
            player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, godtime, 1));
            player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, godtime, 1));
            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, godtime, 2));
            bmcPlayer.playSound(Sound.ENTITY_GENERIC_EAT, 1, 1);
            bmcPlayer.playSound(Sound.BLOCK_PORTAL_TRAVEL, 1, 2);
            player.getInventory().remove(item);
            player.getInventory().remove(Material.BOWL);
            bmcPlayer.msg("ポーションエフェクトの付与、一定時間アイテムの耐久値を無限にします。");
            eat_komeUser.add(player.getUniqueId());
            new BukkitRunnable() {
                @Override
                public void run() {
                    eat_komeUser.remove(player.getUniqueId());
                }
            }.runTaskLater(bmc, godtime * 20);
        }

        if (item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
            String itemName = item.getItemMeta().getDisplayName();
            if (itemName.contains("燻したゾンビ肉")) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        player.removePotionEffect(PotionEffectType.HUNGER);
                    }
                }.runTaskLater(bmc, 1L);
            }
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
        Location location = event.getLocation();
        World world = location.getWorld();
        if (event.getEntityType() == EntityType.ENDER_DRAGON) {
            int LIMIT = Integer.MAX_VALUE;
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
        } else if (event.getEntityType() == EntityType.WITHER && (!(world.getName().equals("world_the_end")))) {
            ItemStack item1 = new ItemStack((Material.SKULL_ITEM), 3, (byte) 1);
            ItemStack item2 = new ItemStack((Material.SOUL_SAND), 4);
            world.dropItemNaturally(location, item1);
            world.dropItemNaturally(location, item2);
            world.playSound(location, Sound.ENTITY_WITHER_HURT, 0.6F, 1.2F);

            world.getNearbyEntities(location, 5, 5, 5).stream().filter(ent -> ent instanceof Player).forEach(ent ->
                    bmc.getBMCPlayer((Player) ent).errmsg("ウィザースケルトンをこのワールドでスポーンさせることは出来ません。"));

            new BukkitRunnable() {
                @Override
                public void run() {
                    event.getEntity().remove();
                }
            }.runTaskLater(bmc, 1L);

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
        Bukkit.broadcast("§7[BMC] " + bmcPlayer.getName() + ": " + event.getMessage(), "bmc.nclv");
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
        bmc.debug("BLOCK_NAME => " + name);
        if (name.contains("炭素の塊") || name.contains("採掘の結晶") || name.contains("Water") || name.contains("Crushed") || name.contains("Cleaned")) {
            event.setCancelled(true);
            bmcPlayer.errbar("特殊ブロックを設置することは出来ません。");
            bmcPlayer.playErrSound();
            return;
        }
        if (name.contains("Farm Block")) {
            event.getBlockPlaced().setType(Material.SOIL);
        }

    }

    /**
     * エンティティによる耕地ブロックの破壊をキャンセルする
     *
     * @param event EntityChangeBlockEvent
     */
    @EventHandler
    public void onEntityChangeBlockEvent(EntityChangeBlockEvent event) {
        if (event.getBlock().getType() == Material.SOIL) {
            event.setCancelled(true);
        }
    }

    /**
     * 耕地ブロック自然破壊をキャンセルする。
     *
     * @param event BlockFadeEvent
     */
    @EventHandler
    public void onBlockFadeEvent(BlockFadeEvent event) {
        if (event.getBlock().getType() == Material.SOIL) {
            event.setCancelled(true);
        }
    }

    private HashMap<Player, BossBar> bossBarHashMap = new HashMap<>();

    /**
     * ItemDamageEventをキャッチし、BossBarに耐久値を表示する
     *
     * @param event
     */
    @EventHandler
    public void onPlayerItemDamageEvent(PlayerItemDamageEvent event) {
        ItemStack item = event.getItem();
        Player player = event.getPlayer();
        int damage = event.getDamage();
        if (damage == 0) return;
        if (eat_komeUser.contains(player.getUniqueId())) {
            event.setCancelled(true);
            return;
        }
        int maxDurability = Short.toUnsignedInt(item.getType().getMaxDurability());
        int itemDurability = maxDurability - (Short.toUnsignedInt(item.getDurability()) + 1);
        double dMaxD = (double) maxDurability;
        double dItemD = (double) itemDurability;
        double progress = 0;
        progress = dItemD / dMaxD;
        if (itemDurability <= -1) return;
        if (progress == 1.0) return;
        bmc.debug(damage + " : " + maxDurability + " : " + itemDurability);
        bmc.debug(String.valueOf(progress));

        String title = getJapaneseNameByMaterial(item.getType()) + "の耐久値 (" + itemDurability + "/" + maxDurability + ")";
        if (bossBarHashMap.get(player) == null) {
            BossBar bossBar = Bukkit.createBossBar(title, getBarColorByDurability(progress), BarStyle.SOLID, BarFlag.PLAY_BOSS_MUSIC);
            bossBar.addPlayer(player);
            bossBar.setProgress(progress);
            bossBarHashMap.put(player, bossBar);
            /*
            BukkitTask bukkitTask = new BukkitRunnable() {
                @Override
                public void run() {
                    bossBar.removePlayer(player);
                    bossBarHashMap.remove(player);
                }
            }.runTaskLater(bmc, 35L);
            */
        } else {
            BossBar bossBar = bossBarHashMap.get(player);
            bossBar.setProgress(progress);
            bossBar.setColor(getBarColorByDurability(progress));
            bossBar.setTitle(title);
            if (progress <= 0.0) {
                bmc.debug("removed");
                bossBar.setTitle("アイテムは壊れてしまった！");
                bossBar.addFlag(BarFlag.DARKEN_SKY);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        bossBar.removePlayer(player);
                    }
                }.runTaskLater(bmc, 60L);
                bossBarHashMap.remove(player);
                return;
            }
        }
    }

    public void clearBar() {
        for (Player player : bossBarHashMap.keySet()) {
            bossBarHashMap.get(player).removePlayer(player);
        }
    }

    private BarColor getBarColorByItem(Material material) {
        String name = material.toString();
        if (name.contains("DIAMOND")) {
            return BarColor.BLUE;
        } else if (name.contains("IRON")) {
            return BarColor.WHITE;
        } else if (name.contains("GOLD")) {
            return BarColor.YELLOW;
        } else if (name.contains("WOOD")) {
            return BarColor.GREEN;
        } else if (name.contains("STONE")) {
            return BarColor.PURPLE;
        }
        return BarColor.RED;
    }

    private BarColor getBarColorByDurability(double perDurability) {
        double per = perDurability * 100;
        bmc.debug(String.valueOf(per));
        if (per <= 10) {
            return BarColor.RED;
        } else if (per <= 40) {
            return BarColor.YELLOW;
        } else {
            return BarColor.GREEN;
        }
    }

    public String getJapaneseNameByMaterial(Material material) {
        String japaneseName = material.toString();
        return japaneseName.replace("DIAMOND", "ダイヤモンドの").replace("IRON", "鉄の").replace("STONE", "石の").replace("GOLD", "金の").replace("WOOD", "木の").replace("_PICKAXE", "ピッケル").replace("_SPADE", "シャベル").replace("_HOE", "クワ").replace("_SWORD", "剣").replace("_AXE", "斧").replace("_HELMET", "ヘルメット").replace("_CHESTPLATE", "チェストプレート").replace("_LEGGINGS", "レギンス").replace("_BOOTS", "ブーツ");
    }


    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent event) {
        Block block = event.getBlock();
        BMCPlayer player = bmc.getBMCPlayer(event.getPlayer());
        ItemStack handItem = player.getItemInMainHand();
        bmc.debug("BlockBreakEvent just called");
        if (handItem.containsEnchantment(Enchantment.SILK_TOUCH)) {
            ItemStack dropItem = null;
            World world = block.getWorld();
            if (block.getType() == Material.SOIL) {
                dropItem = new SpecialItem(new ItemStack(Material.DIRT), "&rFarm Block").getItem();
            }
            if (block.getType() == Material.GRASS_PATH) {
                dropItem = new ItemStack(Material.GRASS_PATH);
            }
            if (dropItem == null) return;
            /* 処理 */
            bmc.debug("is " + block.getType().toString());
            world.dropItemNaturally(block.getLocation().add(0, 1, 0), dropItem);
            event.setCancelled(true);
            block.setType(Material.AIR);
            handItem.setDurability((short) (handItem.getDurability() + 1));
            bmc.getServer().getPluginManager().callEvent(new PlayerItemDamageEvent(player.getPlayer(), handItem, 1));
            /*
            for (Entity entity : block.getWorld().getNearbyEntities(block.getLocation(), 3.5, 2, 3.5)) {
                bmc.debug(entity.toString());
                if (entity instanceof Item) {
                    bmc.debug("is Item");
                    Item item = (Item) entity;
                    ItemStack itemStack = item.getItemStack().clone();
                    bmc.debug(itemStack.toString());
                    if (itemStack.getType() == Material.DIRT) {
                        ItemMeta meta = itemStack.getItemMeta();
                        meta.setDisplayName("Farm Block");
                        itemStack.setItemMeta(meta);
                        item.setItemStack(itemStack);
                        bmc.debug(itemStack.toString());
                    }
                }
            }
            */
        }
    }

    /**
     * BMC特殊アイテムの不正な実行を防ぐ
     *
     * @param event
     */
    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        BMCPlayer player = bmc.getBMCPlayer(event.getPlayer());
        ItemStack item = player.getItemInMainHand();
        if (item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
            String displayName = item.getItemMeta().getDisplayName();
            bmc.debug("DISPNAME => " + displayName);
            if (displayName.contains("凝縮エンチャントボトル")) {
                event.setCancelled(true);
            }
        }
    }
}