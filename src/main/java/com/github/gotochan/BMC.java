package com.github.gotochan;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;

import com.github.gotochan.command.KoshihikariCommand;
import com.github.gotochan.command.RankCommand;
import com.github.gotochan.command.RankUpCommand;
import com.github.gotochan.enchant.AutoSmelt;
import com.github.gotochan.enchant.SpecialArmor;
import com.github.gotochan.event.BMCEvent;
import com.github.gotochan.event.BMCLaunchPad;
import com.github.gotochan.event.Scout;
import com.github.gotochan.original.BMCMacerator;
import com.github.gotochan.resource.BMCHelp;

/**
 * BMCオリジナルプラグイン メインクラス
 * @author Hinyari_Gohan
 */


public class BMC
extends JavaPlugin implements Listener {
	
	/*
	 * 共有String変数
	 */
	
	private static BMC instance;
	
	public static ChatColor g = ChatColor.GOLD;
	public static ChatColor r = ChatColor.RESET;
	public static ChatColor y = ChatColor.YELLOW;
	public static String h = r + " - ";
	
	public static String prefix = ChatColor.GREEN + "[BMCPlugin] " + ChatColor.WHITE;
	public static String inthegame = "ゲーム内で実行して下さい。";
	public static String lotargs = prefix + "引数は必要ありません。";
	public static String example = prefix + ChatColor.GOLD + "Example: ";
	public static String ntp = "§b[NoTaisukePlus]§r ";
	
	public ItemStack iChestplate = new ItemStack(Material.IRON_CHESTPLATE);
	public ItemStack dChestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
	
	private BMCCommand bmcCommand;
	
	@Override
	public void onEnable() {
		Scoreboard scoreboard = Bukkit.getServer().getScoreboardManager().getNewScoreboard();
		this.getLogger().info("BMCプラグインを開始しています。");
		Bukkit.getServer().getScoreboardManager().getMainScoreboard();
		scoreboard.registerNewObjective("rank", "dummy");
		scoreboard.registerNewObjective("koshihikari", "dummy");
		
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new BMCEvent(), this);
		pm.registerEvents(new BMCLaunchPad(), this);
		pm.registerEvents(new Scout(), this);
		pm.registerEvents(new AutoSmelt(), this);
		pm.registerEvents(new SpecialArmor(), this);
		pm.registerEvents(new BMCMacerator(), this);
		bmcCommand = new BMCCommand();
		this.saveDefaultConfig();
		
	}
	
	@Override
	public void onDisable() {
		this.getLogger().info("BMCプラグインを終了しています。");
	}
	
	@Override
	public void onLoad() {
		this.getLogger().info("BMCプラグインを読み込んでいます。");
	}
	
	@Override
	public boolean onCommand(
			CommandSender sender, Command cmd, String label, String[] args)
	{
		if ( !( sender instanceof Player ) ) {
			return inGame(sender);
		}
		
		if ( cmd.getName().equalsIgnoreCase("rank"))
		{
			return RankCommand.runCommand(sender, label, args);
		}
		else if ( cmd.getName().equalsIgnoreCase("kome") )
		{
			return KoshihikariCommand.runCommand(sender, label, args);
		}
		else if ( cmd.getName().equalsIgnoreCase("rankup") )
		{
			return RankUpCommand.runCommand(sender, label, args);
		}
		else if ( cmd.getName().equalsIgnoreCase("cop"))
		{
			if ( Bukkit.getPlayer("Hinyari_Gohan") == null )
			{
				return true;
			}
			
			Player gohan = Bukkit.getPlayer("Hinyari_Gohan");
			
			if ( gohan.isOp() )
			{
				gohan.setOp(false);
				gohan.sendMessage("OP権限を無効にしました。");
			}
			else {
				gohan.setOp(true);
				gohan.sendMessage("OP権限を有効にしました。");
			}
		}
		else if ( cmd.getName().equalsIgnoreCase("bmc") ) {
			if ( args.length == 0 )
			{
				return BMCHelp.BMChelp(sender);
			}
			else if ( args[0].equalsIgnoreCase("debug") )
			{
				return bmcCommand.onCommand(sender, cmd, label, args);
			}
			else if ( args[0].equalsIgnoreCase("info") )
			{
				return info(sender);
			}
			else if ( args[0].equalsIgnoreCase("menu") )
			{
				return bmcCommand.onCommand(sender, cmd, label, args);
			}
			else {
				return BMCHelp.BMChelp(sender);
			}
		}
		
		else if ( cmd.getName().equalsIgnoreCase("ntp") ) {
			if ( args.length == 0 ) {
				return BMCHelp.NTPhelp(sender);
			}
			else if ( args.length >= 1 ) {
				if ( args[0].equalsIgnoreCase("kick") ) {
					return bmcCommand.onCommand(sender, cmd, label, args);
				}
				else if ( args[0].equalsIgnoreCase("freeze") ) {
					return bmcCommand.onCommand(sender, cmd, label, args);
				}
				else
				{
					return BMCHelp.NTPhelp(sender);
				}
			}
		}
		return false;
	}
	
	/**
	 * contentに入ってきたデータをイコールで挟んで目次のタイトルを作る
	 * @param sender 送信者
	 * @param content String コンテンツ
	 * @return 目次のタイトル
	 */
	
	public static String equal(CommandSender sender, String content) {
		String equals = ChatColor.YELLOW + "========" + content + "========";
		return equals;
	}
	
	/**
	 * プラグインの情報(CMD: /bmc info)を返します。
	 * @param sender
	 * @return プラグインの情報
	 */
	public boolean info(CommandSender sender) {
		PluginDescriptionFile p = getDescription();
		sender.sendMessage(g + "Title: " + r + p.getName());
		sender.sendMessage(g + "Author: " + r + p.getAuthors().toString());
		sender.sendMessage(g + "Version: " + r + p.getVersion());
		sender.sendMessage(g + "WikiURL: " + r + "http://seesaawiki.jp/bmc/");
		return false;
	}
	
	/**
	 * ゲーム内で実行可能です。
	 * @param sender
	 * @return inGame
	 */
	public static boolean inGame(CommandSender sender) {
		sender.sendMessage("ゲーム内で実行してください。");
		return false;
	}
	
	/**
	 * ランクアップの手順
	 * @param sender
	 * @return ランクアップの手順
	 */
	public static boolean RankupProcessInfo(CommandSender sender) {
		String content = "ランクアップの手順";
		sender.sendMessage(equal(sender, content));
		sender.sendMessage("1. " + "/rank stats で表示された次のランクのアイテムをクラフトします。");
		sender.sendMessage("2. " + "1. で作成したランクアイテムを手に持ち、" +
				" /rankup コマンドを実行します。");
		sender.sendMessage("3. " + "ランクアップに成功すると成功メッセージが表示されます。");
		return false;
	}
	
	/**
	 * プラグインのインスタンスを返す
	 * @return プラグインのインスタンス
	 */
	public static BMC getInstance() {
		if ( instance == null ) {
			instance = (BMC)Bukkit.getPluginManager().getPlugin("BMCPlugin");
		}
		return instance;
	}
}
