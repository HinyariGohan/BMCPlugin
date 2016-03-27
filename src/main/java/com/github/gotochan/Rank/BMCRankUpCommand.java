package com.github.gotochan.Rank;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import com.github.gotochan.BMC;

@SuppressWarnings("deprecation")
public class BMCRankUpCommand
	implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if ( sender instanceof Player ){
			if ( cmd.getName().equalsIgnoreCase("rankup") ){
				Player player = (Player) sender;
				Scoreboard board = player.getScoreboard();
				Objective objective = board.getObjective("rank");
				Score score = objective.getScore(player);
				int pscore = score.getScore();
				String[] array = new String[] {ChatColor.GRAY + "Visitor", ChatColor.RED + "Red" , ChatColor.GOLD + "Orange",
						ChatColor.YELLOW + "Yellow", ChatColor.GREEN + "Green", ChatColor.BLUE + "Blue",
						ChatColor.DARK_BLUE + "Indigo", ChatColor.DARK_PURPLE + "Violet",
						ChatColor.WHITE + "UltraViolet"};
				String msg = ChatColor.RESET + "ランクにランクアップしました。（未実装）";
				String PlayerName = player.getName();
					if( pscore == 0 ) {
						player.sendMessage("ランクアップを行いません。");
					}
					else if ( pscore == 1 ){
						Bukkit.broadcastMessage(BMC.prefix + PlayerName + " " + "さんが" + array[pscore] + msg);
						score.setScore(2);
					}
					else if ( pscore == 2 ){
						Bukkit.broadcastMessage(BMC.prefix + PlayerName + " " + "さんが" + array[pscore] + msg);
						score.setScore(3);
					}
					else if ( pscore == 3 ){
						Bukkit.broadcastMessage(BMC.prefix + PlayerName + " " + "さんが" + array[pscore] + msg);
						score.setScore(4);
					}
					else if ( pscore == 4 ){
						Bukkit.broadcastMessage(BMC.prefix + PlayerName + " " + "さんが" + array[pscore] + msg);
						score.setScore(5);
					}
					else if ( pscore == 5 ){
						Bukkit.broadcastMessage(BMC.prefix + PlayerName + " " + "さんが" + array[pscore] + msg);
						score.setScore(6);
					}
					else if ( pscore == 6 ){
						Bukkit.broadcastMessage(BMC.prefix + PlayerName + " " + "さんが" + array[pscore] + msg);
						score.setScore(7);
					}
					else if ( pscore == 7 ){
						player.sendMessage(ChatColor.LIGHT_PURPLE + "あなたは最高ランクへ達しています。");
					}
					else {
						player.sendMessage(ChatColor.RED + "ランクスコアが不正です。管理者に問い合わせてください。");
					}
			}
		}
		return false;

	}
}
