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

public class BMCCommand
	implements CommandExecutor {


	@SuppressWarnings({ "deprecation" })
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if(command.getName().equalsIgnoreCase("koshihikari")) {
			if( sender instanceof Player ) {
				Player player = (Player) sender;
				Scoreboard board = player.getScoreboard();
				Objective objective = board.getObjective("koshihikari");
				Score KScore = objective.getScore(player);
				if ( args.length < 1 ) {
					player.sendMessage(BMC.prefix  + "あなたのコシヒカリ交換チケット数は" + Integer.toString(KScore.getScore()) + "枚です。");
				}
				else {
						Player other = Bukkit.getServer().getPlayer(args[0]);
						String other1 = args[0];
					if (other == null) {
						player.sendMessage(BMC.prefix + ChatColor.RED + "そのプレイヤーは存在しません。");
					}
					else {
						Score OScore = objective.getScore(args[0]);
						player.sendMessage(BMC.prefix + other1 + "さんのコシヒカリ交換チケット数は" + Integer.toString(OScore.getScore())+ "枚です。" );
					}
				}
			}
		else {
			sender.sendMessage(BMC.prefix + "ゲーム内から実行して下さい。");
			 }
		}
		return false;



}
}

/*
 * 処理内容の整理。
 *
 * getScore()で数値を取り出す。
 * ↓
 * それを文字列に変換する。
 * ↓
 * プレイヤーに送信。
 */
