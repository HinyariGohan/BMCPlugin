package xyz.hinyari.bmcplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import xyz.hinyari.bmcplugin.utils.BMCUtils;

import java.util.List;

/**
 * Created by Hinyari_Gohan on 2016/09/23.
 */
public class BMCConfig {

    public FileConfiguration config;
    public final BMCPlugin plugin;

    private String PREFIX;
    private String ERROR;
    private String DEBUG_PREFIX;
    private boolean DEBUG;
    private String CONFIG_VERSIONS;
    private int KOME_GET_MINUTES;
    private int KOME_GET_VALUE;
    private int ANNOUNCEMENT_INTERVAL;
    private List<String> ANNOUNCEMENT_LIST;
    private boolean KOME_GET_ENABLE;
    private String VOTE_URL;
    private List<String> VOTE_COMMANDS;

    private String ADMIN_PASS;

    public BMCConfig(BMCPlugin plugin) {
        this.plugin = plugin;
        this.plugin.saveDefaultConfig();
        this.config = plugin.getConfig();
        this.PREFIX = BMCUtils.convert(config.getString("prefix.server") + ChatColor.RESET);
        this.ERROR = BMCUtils.convert(config.getString("prefix.error") + ChatColor.RESET);
        this.DEBUG_PREFIX = BMCUtils.convert(config.getString("prefix.debug") + ChatColor.RESET);
        this.DEBUG = config.getBoolean("debug");
        this.ADMIN_PASS = config.getString("management.admin_pass");
        this.CONFIG_VERSIONS = config.getString("versions");
        this.KOME_GET_MINUTES = config.getInt("kome.time_get_minutes");
        this.KOME_GET_VALUE = config.getInt("kome.time_get_value");
        this.ANNOUNCEMENT_LIST = config.getStringList("announcement.messages");
        this.ANNOUNCEMENT_INTERVAL = config.getInt("announcement.interval");
        this.KOME_GET_ENABLE = config.getBoolean("kome.time_get_enable");
        this.VOTE_URL = config.getString("vote.url");
        this.VOTE_COMMANDS = config.getStringList("vote.commands");

    }

    public final String getAdmin_pass() { return this.ADMIN_PASS; }
    public final String getConfigVersions() { return this.CONFIG_VERSIONS;}
    public final FileConfiguration getConfig() { return  this.config; }
    public final String getPrefix() { return this.PREFIX; }
    public final String getErrorPrefix() {return this.ERROR; }
    public final String getDebugPrefix() {return this.DEBUG_PREFIX;}
    public final boolean getDebug() { return this.DEBUG; }
    public final int getKomeGetMinutes() {return this.KOME_GET_MINUTES; }
    public final int getKomeGetValue() {return this.KOME_GET_VALUE; }
    public final List<String> getAnnouncementList() {return this.ANNOUNCEMENT_LIST;}
    public final int getAnnouncementInterval() {return this.ANNOUNCEMENT_INTERVAL;}
    public final boolean getKomeGetAvailable() {return this.KOME_GET_ENABLE;}
    public final List<String> getVoteCommands() { return this.VOTE_COMMANDS; }
    public final String getVoteURL() { return this.VOTE_URL; }
    
    public String replace(String message, BMCPlayer player) {
        String msg = message;
        msg = msg.replace("%player", player.getName()).replace("%rank", player.getScoreboard().getRank().getName()).replace('&', '§');
        return msg;
    }

    public boolean reloadConfig() {
        this.plugin.reloadConfig();
        this.plugin.saveDefaultConfig();
        this.config = plugin.getConfig();
        this.PREFIX = BMCUtils.convert(config.getString("prefix.server") + ChatColor.RESET);
        this.ERROR = BMCUtils.convert(config.getString("prefix.error") + ChatColor.RESET);
        this.DEBUG_PREFIX = BMCUtils.convert(config.getString("prefix.debug") + ChatColor.RESET);
        this.DEBUG = config.getBoolean("debug");
        this.ADMIN_PASS = config.getString("management.admin_pass");
        this.CONFIG_VERSIONS = config.getString("versions");
        this.KOME_GET_MINUTES = config.getInt("kome.time_get_minutes");
        this.KOME_GET_VALUE = config.getInt("kome.time_get_value");
        this.ANNOUNCEMENT_LIST = config.getStringList("announcement.messages");
        this.ANNOUNCEMENT_INTERVAL = config.getInt("announcement.interval");
        this.KOME_GET_ENABLE = config.getBoolean("kome.time_get_enable");
        this.VOTE_URL = config.getString("vote.url");
        this.VOTE_COMMANDS = config.getStringList("vote.commands");
        Bukkit.broadcast(this.PREFIX + "コンフィグをリロードしました。", "bmc.reload");
        return true;
    }



}
