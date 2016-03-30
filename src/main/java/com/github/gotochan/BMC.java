package com.github.gotochan;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.gotochan.Koshihikari.KoshihikariEvent;

/**
 * BMCオリジナルプラグイン メインクラス
 * @author Hinyari_Gohan
 */


public class BMC
	extends JavaPlugin implements Listener {

	/**
	 * 共有String変数
	 */
	public static String g = ChatColor.GOLD + "";
	public static String r = ChatColor.RESET + "";
	public static String y = ChatColor.YELLOW + "";
	public static String h = r + " - ";

	public static String prefix = ChatColor.GREEN + "[BMCPlugin] " + ChatColor.WHITE;
	public static String inthegame = "ゲーム内で実行して下さい。";
	public static String lotargs = prefix + "引数は必要ありません。";
	public static String example = prefix + ChatColor.GOLD + "Example: ";

	private BMCCommand bmcCommand;

	@Override
	public void onEnable() {
		this.getLogger().info("BMCプラグインを開始しています。");
		Bukkit.getServer().getScoreboardManager().getMainScoreboard();
		getServer().getPluginManager().registerEvents(new KoshihikariEvent(), this);
		getServer().getPluginManager().registerEvents(new BMCEvent(), this);
		getServer().getPluginManager().registerEvents(new BMCLaunchPad(), this);
		bmcCommand = new BMCCommand();
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

		if ( cmd.getName().equalsIgnoreCase("bmc") ) {
			if ( args.length == 0 ) {
				return BMChelp(sender);
			}
			else if ( args.length >= 1 ) {
				if ( args[0].equalsIgnoreCase("rank") ) {
					return bmcCommand.onCommand(sender, cmd, label, args);
				} else if ( args[0].equalsIgnoreCase("kome")) {
					return bmcCommand.onCommand(sender, cmd, label, args);
				} else if ( args[0].equalsIgnoreCase("info")) {
					return info(sender);
				} else if ( args[0].equalsIgnoreCase("debug")) {
					return bmcCommand.onCommand(sender, cmd, label, args);
				} else {
					return BMChelp(sender);
				}
			}
		}
		return false;
	  }

	/**
	 * rankコマンドのヘルプです。
	 * @param sender
	 * @return rankコマンドのヘルプ
	 */
	public static boolean Rankhelp(CommandSender sender) {
		String content = "ランクコマンド ヘルプ";
		String cmdname = g + "/bmc rank ";
		sender.sendMessage(equal(sender, content));
		sender.sendMessage(cmdname + "show" + h + "ランクの一覧を表示します。");
		sender.sendMessage(cmdname + "stats <プレイヤー名>" + h + "ランクのスタッツを表示します。");
		sender.sendMessage(cmdname + "time" + h + "ランクの有効時間を表示します。");
		sender.sendMessage(cmdname + "rankup" + h + "ランクアップをすることが出来ます。");
		return false;
	}

	/**
	 * komeコマンドのヘルプです。
	 * @param sender
	 * @return komeコマンドのヘルプ
	 */
	public static boolean Komehelp(CommandSender sender) {
		String content = "コシヒカリコマンド ヘルプ";
		String cmdname = g + "/bmc kome ";
		sender.sendMessage(equal(sender, content));
		sender.sendMessage(cmdname + "ticket <プレイヤー名>" + h +
				"コシヒカリ交換可能チケット数を表示します。");
		return false;
	}

	/**
	 * ランクコマンドのランクアップのヘルプです。
	 * @param sender
	 * @return ランクコマンドのランクアップ
	 */
	public static boolean RankUphelp(CommandSender sender) {
		String content = "ランクアップコマンド ヘルプ";
		String cmdname = g + "/bmc rank rankup ";
		sender.sendMessage(equal(sender, content));
		sender.sendMessage(cmdname + h + "ランクアップをします。");
		sender.sendMessage(cmdname + "help" + h + "ランクアップの手順を確認します。");
		return false;
	}

	/**
	 * デバッグコマンドのヘルプです。
	 * @param sender
	 * @return デバッグコマンドのヘルプ
	 */
	public static boolean Debughelp(CommandSender sender) {
		String content = "BMCデバッグコマンド ヘルプ";
		String cmdname = "/bmc debug";
		sender.sendMessage(equal(sender, content));
		sender.sendMessage(cmdname + "itemhand" + h + "手に持っているアイテムのデータを返します。");
		sender.sendMessage(cmdname + "rank reset" + h + "ランクをリセットします。");
		sender.sendMessage(cmdname + "kome hunger" + h + "空腹状態にします。");
		sender.sendMessage(cmdname + "kome get" + h + "コシヒカリをインベントリに追加します。");
		return false;
	}

	/**
	 * bmcコマンドのヘルプです。
	 * @param sender
	 * @return BMCコマンドのヘルプです。
	 */
	public static boolean BMChelp(CommandSender sender) {
		String content = "BMCコマンド ヘルプ";
		sender.sendMessage(equal(sender, content));
		sender.sendMessage(g + "/bmc rank" + h + "ランクコマンドのヘルプを参照します。");
		sender.sendMessage(g + "/bmc kome" + h + "コシヒカリコマンドのヘルプを参照します。");
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
		String n = System.getProperty("line.separator");
		sender.sendMessage(equal(sender, content));
		sender.sendMessage("1. " + "/bmc rank stats で表示された次のランクのアイテムをクラフトします。");
		sender.sendMessage("2. " + "1. で作成したランクアイテムを手に持ち、" +
				" /bmc rank rankup " + n +"コマンドを実行します。");
		sender.sendMessage("3. " + "ランクアップに成功すると成功メッセージが表示されます。" + n);
		return false;
	}
}
