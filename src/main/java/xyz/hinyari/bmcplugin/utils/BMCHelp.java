package xyz.hinyari.bmcplugin.utils;

import xyz.hinyari.bmcplugin.BMCPlayer;
import xyz.hinyari.bmcplugin.BMCPlugin;
import org.bukkit.ChatColor;

public class BMCHelp {

	private BMCPlugin bmc;

	public BMCHelp(BMCPlugin bmc)
	{
		this.bmc = bmc;
	}

	public String g = ChatColor.GOLD + "";
	public String r = ChatColor.RESET + "";
	public String y = ChatColor.YELLOW + "";
	public String h = r + " - ";

	/**
	 * rankコマンドのヘルプです。
	 * @param player
	 * @return rankコマンドのヘルプ
	 */
	public boolean Rankhelp(BMCPlayer player) {
		String content = "ランクコマンド ヘルプ";
		String cmdname = g + "/rank ";
		player.msg(bmc.equalMessage(content));
		player.msg(cmdname + "stats <プレイヤー名>" + h + "ランクの状況を表示します。");
		player.msg(cmdname + "menu" + h + "ランクのメニューを表示します。");
		player.msg(cmdname + "set <プレイヤー名> <ランク>" + h + "プレイヤーのランクを設定します。", "bmc.rank.set");
		return true;
	}

	/**
	 * komeコマンドのヘルプです。
	 * @param player
	 * @return komeコマンドのヘルプ
	 */
	public boolean Komehelp(BMCPlayer player) {
		String content = "コシヒカリコマンド ヘルプ";
		String cmdname = g + "/kome ";
		player.msg(bmc.equalMessage(content));
		player.msg(cmdname + "point <プレイヤー名>" + h +
				"コシヒカリ交換可能チケット数を表示します。");
		return true;
	}

	/**
	 * ランクコマンドのランクアップのヘルプです。
	 * @param player
	 * @return ランクコマンドのランクアップ
	 */
	public boolean RankUphelp(BMCPlayer player) {
		String content = "ランクアップコマンド ヘルプ";
		String cmdname = g + "/rankup ";
		player.msg(bmc.equalMessage(content));
		player.msg(cmdname + h + "ランクアップをします。");
		player.msg(cmdname + "help" + h + "ランクアップの手順を確認します。");
		return true;
	}

	/**
	 * デバッグコマンドのヘルプです。
	 * @param player
	 * @return デバッグコマンドのヘルプ
	 */
	public boolean Debughelp(BMCPlayer player) {
		String content = "BMCデバッグコマンド ヘルプ";
		String cmdname = "/bmc debug ";
		player.msg(bmc.equalMessage(content));
		player.msg(g + cmdname + "itemhand" + h + "手に持っているアイテムのデータを返します。");
		player.msg(g + cmdname + "rank reset" + h + "ランクをリセットします。");
		player.msg(g + cmdname + "kome hunger" + h + "空腹状態にします。");
		player.msg(g + cmdname + "kome get" + h + "コシヒカリをインベントリに追加します。");
		player.msg(g + cmdname + "ench <fire/fall/smelt>" + h + "エンチャントメントを実行します。");
		player.msg(g + cmdname + "grapple" + h + "grappleを取得します。");
		player.msg(g + cmdname + "namereset" + h + "ディスプレイネームをリセットします。");
		return true;
	}

	/**
	 * bmcコマンドのヘルプです。
	 * @param player
	 * @return BMCコマンドのヘルプです。
	 */
	public boolean BMChelp(BMCPlayer player) {
		String content = "BMCコマンド ヘルプ";
		player.msg(bmc.equalMessage(content));
		player.msg(g + "/bmc kit" + h + "キットコマンドのヘルプを参照します。");
		player.msg(g + "/bmc info" + h + "プラグインの情報を参照します。");
		player.msg(g + "/bmc menu" + h + "メニューを表示します。");
		player.msg(g + "/bmc debug" + h + "デバッグ操作を行います。", "bmc.debug");
		player.msg(g + "/bmc reload" + h + "コンフィグをリロードします。", "bmc.reload");
		player.msg(g + "/bmc vote" + h + "投票テストを行います。", "bmc.vote");
		return true;
	}

	/**
	 * Kitコマンドのヘルプです。
	 * @param player
	 * @return
	 */
	public boolean KitHelp(BMCPlayer player) {
		String content = "Kitコマンド ヘルプ";
		String cmdname = "/bmc kit ";
		player.msg(bmc.equalMessage(content));
		player.msg(g + cmdname + "list" + h + "キットの一覧を表示します。");
		player.msg(g + cmdname + "acrobat mode" + h + "Acrobatのモードを切り替えます");
		return true;
	}

	public boolean NTPhelp(BMCPlayer player) {
		String content = "NoTaisukePlusコマンド ヘルプ";
		String cmdname = g + "/ntp ";
		player.msg(bmc.equalMessage(content));
		player.msg(cmdname + "kick <on/off>" + h + "NTPキックモードのオンオフを切り替えます。");
		player.msg(cmdname + "kick status" + h + "NTPキックモードのステータスを確認します。");
		return true;
	}

	/**
	 * ランクアップの手順
	 * @param bmcPlayer
	 * @return ランクアップの手順
	 */
	public boolean RankupProcessInfo(BMCPlayer bmcPlayer) {
		String content = "ランクアップの手順";
		bmcPlayer.msg(bmc.equalMessage(content));
		bmcPlayer.msg("1. " + "/rank stats で表示された次のランクのアイテムをクラフトします。");
		bmcPlayer.msg("2. " + "1. で作成したランクアイテムを手に持ち、" +
				" /rankup コマンドを実行します。");
		bmcPlayer.msg("3. " + "ランクアップに成功すると成功メッセージが表示されます。");
		return true;
	}
}
