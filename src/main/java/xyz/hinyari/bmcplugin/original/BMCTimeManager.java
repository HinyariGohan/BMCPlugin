package xyz.hinyari.bmcplugin.original;

import xyz.hinyari.bmcplugin.BMCPlayer;
import xyz.hinyari.bmcplugin.Utils.ConfigAccessor;
import xyz.hinyari.bmcplugin.BMCPlugin;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

/**
 * Created by Hinyari_Gohan on 2016/09/23.
 */
public class BMCTimeManager extends BukkitRunnable {

    private List<Map<String, Integer>> data_list = new ArrayList<>();
    private Map<String, Object> data = new HashMap<>();
    private final int KOME_VALUE;
    private final int KOME_MINUTES;
    private final String FILE_NAME = "database.yml";
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
            String uuid = player.getUUID().toString();
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
        configAccessor = new ConfigAccessor(plugin, FILE_NAME, plugin);
        configAccessor.saveDefaultConfig();
        config = configAccessor.getConfig();
        data_list = null;
        Material.valueOf(config.getString("").toUpperCase());

    }

    public void getDatafromUUID(UUID uuid) {
        if (config.getConfigurationSection("kome_list").getInt(uuid.toString()) == null)
    }

    public void saveDatabase() {
        config.set("kome_time", data);
        configAccessor.saveConfig();
    }



}
