package com.github.gotochan;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class BMCRank
	implements CommandExecutor {


	@SuppressWarnings({ "deprecation" })
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
	if(command.getName().equalsIgnoreCase("rank")) {
		if( sender instanceof Player ) {

	Player player = (Player) sender;
	Scoreboard board = player.getScoreboard();
	Objective rankobject = board.getObjective("rank");
	Score MyStats = rankobject.getScore(player);
	Score BStats = rankobject.getScore(args[1]);
	Player other = Bukkit.getServer().getPlayer(args[1]);
	String[] array = new String[] {null,ChatColor.RED + "Red" , ChatColor.GOLD + "Orange",
			ChatColor.YELLOW + "Yellow", ChatColor.GREEN + "Green", ChatColor.BLUE + "Blue",
			ChatColor.DARK_BLUE + "Indigo", ChatColor.DARK_PURPLE + "Violet",
			ChatColor.WHITE + "UltraViolet"};
	int NowRank = MyStats.getScore();
	int NextRank = MyStats.getScore() + 1;
	int BNowRank = BStats.getScore();
	int BNextRank = BStats.getScore() + 1;

			if ( args.length == 1 ) {
				sender.sendMessage(BMC.prefix + ChatColor.GOLD + "/rank show" + ChatColor.GRAY + " ランク一覧を表示します");
				sender.sendMessage(BMC.prefix + ChatColor.GOLD + "/rank stats <プレイヤー名>" + ChatColor.GRAY + " ランクの状態を表示します。");
				sender.sendMessage(BMC.prefix + ChatColor.GOLD + "/rank recipe" + ChatColor.GRAY + " ランクアイテムのレシピを確認します。");
			}
			else if ( args[0].equals("show")) { //jobs browseみたいな役割。ランク一覧を表示する。
				sender.sendMessage(BMC.prefix + ChatColor.RED + "[Red] " + ChatColor.WHITE + "一般人");
				sender.sendMessage(BMC.prefix + ChatColor.GOLD + "[Orange] " + ChatColor.WHITE + "駆け出しクラフター");
				sender.sendMessage(BMC.prefix + ChatColor.YELLOW + "[Yellow] " + ChatColor.WHITE + "慣れてきた感じのクラフター");
				sender.sendMessage(BMC.prefix + ChatColor.DARK_GREEN + "[Green] " + ChatColor.WHITE + "中堅クラフター");
				sender.sendMessage(BMC.prefix + ChatColor.BLUE + "[Blue] " + ChatColor.WHITE + "セミプロクラフター");
				sender.sendMessage(BMC.prefix + ChatColor.DARK_BLUE + "[Indigo]" + ChatColor.WHITE + "プロクラフター");
				sender.sendMessage(BMC.prefix + ChatColor.DARK_PURPLE + "[Violet] " + ChatColor.WHITE + "神がかったクラフター");
				sender.sendMessage(BMC.prefix + ChatColor.WHITE + "[UltraViolet] " + "よくわからない何か");
				}
			else if ( args[0].equals("recipe") ) {
				sender.sendMessage(BMC.prefix + "未実装");
			}
			else if ( args[0].equals("stats")) { //jobs statsみたいな役割。argsがあるかで処理を分岐する。
				if ( args.length == 1 ) {
				sender.sendMessage(ChatColor.YELLOW + "========BMCサーバー ランクシステム========");
				sender.sendMessage("名前: " + player);
				sender.sendMessage("現在のランク: " + array[NowRank]);
				sender.sendMessage("次のランク: " + array[NextRank]);
				}
				else if (other == null) {
					sender.sendMessage(BMC.prefix + ChatColor.RED + "そのプレイヤーは存在しません。");
				}
				else {
					sender.sendMessage(ChatColor.YELLOW + "========BMCサーバー ランクシステム========");
					sender.sendMessage("名前: " + args[1]);
					sender.sendMessage("現在のランク: " + array[BNowRank]);
					sender.sendMessage("次のランク: " + array[BNextRank]);
				}
			}
			}
		else
			sender.sendMessage(BMC.prefix + "ゲーム内から実行して下さい。");
		}
	return false;
}
}