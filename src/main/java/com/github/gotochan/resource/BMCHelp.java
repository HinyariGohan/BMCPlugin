package com.github.gotochan.resource;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.github.gotochan.BMC;

public class BMCHelp {
	
	public static String g = ChatColor.GOLD + "";
	public static String r = ChatColor.RESET + "";
	public static String y = ChatColor.YELLOW + "";
	public static String h = r + " - ";
	
	/**
	 * rankコマンドのヘルプです。
	 * @param sender
	 * @return rankコマンドのヘルプ
	 */
	public static boolean Rankhelp(CommandSender sender) {
		String content = "ランクコマンド ヘルプ";
		String cmdname = g + "/rank ";
		sender.sendMessage(BMC.equal(sender, content));
		sender.sendMessage(cmdname + "stats <プレイヤー名>" + h + "ランクのスタッツを表示します。");
		return false;
	}
	
	/**
	 * komeコマンドのヘルプです。
	 * @param sender
	 * @return komeコマンドのヘルプ
	 */
	public static boolean Komehelp(CommandSender sender) {
		String content = "コシヒカリコマンド ヘルプ";
		String cmdname = g + "/kome ";
		sender.sendMessage(BMC.equal(sender, content));
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
		String cmdname = g + "/rankup ";
		sender.sendMessage(BMC.equal(sender, content));
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
		String cmdname = "/bmc debug ";
		sender.sendMessage(BMC.equal(sender, content));
		sender.sendMessage(g + cmdname + "itemhand" + h + "手に持っているアイテムのデータを返します。");
		sender.sendMessage(g + cmdname + "rank reset" + h + "ランクをリセットします。");
		sender.sendMessage(g + cmdname + "kome hunger" + h + "空腹状態にします。");
		sender.sendMessage(g + cmdname + "kome get" + h + "コシヒカリをインベントリに追加します。");
		return false;
	}
	
	/**
	 * bmcコマンドのヘルプです。
	 * @param sender
	 * @return BMCコマンドのヘルプです。
	 */
	public static boolean BMChelp(CommandSender sender) {
		String content = "BMCコマンド ヘルプ";
		sender.sendMessage(BMC.equal(sender, content));
		sender.sendMessage(g + "/bmc kit" + h + "キットコマンドのヘルプを参照します。");
		sender.sendMessage(g + "/bmc info" + h + "プラグインの情報を参照します。");
		return false;
	}
	
	public static boolean KitHelp(CommandSender sender) {
		String content = "Kitコマンド ヘルプ";
		String cmdname = "/bmc kit ";
		sender.sendMessage(BMC.equal(sender, content));
		sender.sendMessage(g + cmdname + "list" + h + "キットの一覧を表示します。");
		sender.sendMessage(g + cmdname + "acrobat mode" + h + "Acrobatのモードを切り替えます");
		return false;
	}
	
	public static boolean NTPhelp(CommandSender sender) {
		String content = "NoTaisukePlusコマンド ヘルプ";
		String cmdname = g + "/ntp ";
		sender.sendMessage(BMC.equal(sender, content));
		sender.sendMessage(cmdname + "kick <on/off>" + h + "NTPキックモードのオンオフを切り替えます。");
		sender.sendMessage(cmdname + "kick status" + h + "NTPキックモードのステータスを確認します。");
		return false;
	}
}
