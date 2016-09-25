package com.github.gotochan.original;

import com.github.gotochan.BMCPlayer;
import com.github.gotochan.BMCPlugin;
import com.github.gotochan.Utils.ConfigAccessor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Hinyari_Gohan on 2016/09/23.
 */
public class BMCTimeManager extends BukkitRunnable {

    private HashMap<UUID, Integer> data = new HashMap<>();
    private final int KOME_VALUE;
    private final int KOME_MINUTES;
    private final BMCPlugin plugin;
    public ConfigAccessor configAccessor;
    private FileConfiguration config;

    public BMCTimeManager(BMCPlugin plugin) {
        this.plugin = plugin;
        this.KOME_VALUE = plugin.config.KOME_GET_VALUE;
        this.KOME_MINUTES = plugin.config.KOME_GET_MINUTES;
        loadDatabase();
    }

    @Override
    public void run() {
        for (BMCPlayer player : plugin.getBMCPlayers()) {
            UUID uuid = player.getUUID();
            if (data.get(uuid) == null) { data.put(uuid,1); return;}
            else {
                int value = data.get(uuid);
                if (value == (KOME_MINUTES * 60)) {
                    data.put(uuid, 0);
                    player.getScoreboard().addKomePoint();
                    player.msg("コシヒカリポイントを追加しました。");
                    player.msg("あなたのコシヒカリ交換可能ポイントは " +
                    player.getScoreboard().getKomePoint() + " 枚になりました。");
                    return;
                }
                data.put(uuid, value + 1);
            }
        }
    }

    public void loadDatabase() {
        configAccessor = new ConfigAccessor(plugin, "database.yml");
        configAccessor.saveDefaultConfig();
        config = configAccessor.getConfig();
        data = (HashMap<UUID, Integer>) config.getMapList("kome_time");
    }

    public void saveDatabase() {
        config.set("kome_time", data);
        configAccessor.saveConfig();
    }



}
