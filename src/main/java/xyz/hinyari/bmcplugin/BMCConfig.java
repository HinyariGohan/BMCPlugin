package xyz.hinyari.bmcplugin;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Created by Hinyari_Gohan on 2016/09/23.
 */
public class BMCConfig {

    public final FileConfiguration config;
    public final BMCPlugin plugin;

    public final String PREFIX;
    public final String ERROR;
    public final String DEBUG_PREFIX;
    public final boolean DEBUG;
    public final String CONFIG_VERSIONS;
    public final int KOME_GET_MINUTES;
    public final int KOME_GET_VALUE;

    private final String ADMIN_PASS;

    public BMCConfig(BMCPlugin plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
        this.PREFIX = config.getString("prefix.server") + ChatColor.RESET;
        this.ERROR = config.getString("prefix.error") + ChatColor.RESET;
        this.DEBUG_PREFIX = config.getString("prefix.debug") + ChatColor.RESET;
        this.DEBUG = config.getBoolean("debug");
        this.ADMIN_PASS = config.getString("management.admin_pass");
        this.CONFIG_VERSIONS = config.getString("versions");
        this.KOME_GET_MINUTES = config.getInt("kome.time_get_minutes");
        this.KOME_GET_VALUE = config.getInt("kome.time_get_value");
    }

    public final String getAdmin_pass() { return this.ADMIN_PASS; }
    public final String getConfigVersions() { return this.CONFIG_VERSIONS;}



}
