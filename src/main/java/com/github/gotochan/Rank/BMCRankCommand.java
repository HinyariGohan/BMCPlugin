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

/**
 *
 * @author Hinyari_Gohan
 *
 */

public class BMCRankCommand
	implements CommandExecutor{

	@SuppressWarnings({ "deprecation" })
	public boolean onCommand(CommandSender sender, Command cmd,
			String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("rank")) {
			if(!( sender instanceof Player )) {
				sender.sendMessage(BMC.inthegame);
			}
				Player player = (Player) sender;
				Scoreboard board = player.getScoreboard();
				Objective rankobject = board.getObjective("rank");
				Score MyStats = rankobject.getScore(player);
				String myname = sender.getName();
				String cmdname = "/" + cmd.getName();

				String[] RankList = new String[] {
						null,
						ChatColor.GRAY + "Visitor",
						ChatColor.RED + "Red" ,
						ChatColor.GOLD + "Orange",
						ChatColor.YELLOW + "Yellow",
						ChatColor.GREEN + "Green",
						ChatColor.BLUE + "Blue",
						ChatColor.DARK_BLUE + "Indigo",
						ChatColor.DARK_PURPLE + "Violet",
						ChatColor.WHITE + "UltraViolet",
						ChatColor.LIGHT_PURPLE + "あなたは最高ランクに達しました。"
						};
				int NowRank = MyStats.getScore();
				int NextRank = MyStats.getScore() + 1;
				if( args.length < 1 ) {
					String content = "BMCランクシステム";
					player.sendMessage(BMC.equal(sender, content));
					player.sendMessage(ChatColor.GOLD + cmdname + " show");
					player.sendMessage(ChatColor.GOLD + cmdname + " stats");
					player.sendMessage(ChatColor.GOLD + cmdname + " time");
				}
				else if( args[0].equalsIgnoreCase("show")) {
						sender.sendMessage(ChatColor.RED + "[Red] " + ChatColor.WHITE + "一般人");
						sender.sendMessage(ChatColor.GOLD + "[Orange] " + ChatColor.WHITE + "駆け出しクラフター");
						sender.sendMessage(ChatColor.YELLOW + "[Yellow] " + ChatColor.WHITE + "慣れてきた感じのクラフター");
						sender.sendMessage(ChatColor.DARK_GREEN + "[Green] " + ChatColor.WHITE + "中堅クラフター");
						sender.sendMessage(ChatColor.BLUE + "[Blue] " + ChatColor.WHITE + "セミプロクラフター");
						sender.sendMessage(ChatColor.DARK_BLUE + "[Indigo]" + ChatColor.WHITE + "プロクラフター");
						sender.sendMessage(ChatColor.DARK_PURPLE + "[Violet] " + ChatColor.WHITE + "神がかったクラフター");
						sender.sendMessage(ChatColor.WHITE + "[UltraViolet] " + "よくわからない何か");
					}
					else if ( args[0].equalsIgnoreCase("stats")) {
						if ( args.length == 1 ) {
						sender.sendMessage(ChatColor.YELLOW + "========BMCサーバー ランクシステム========");
						sender.sendMessage("名前: " + myname);
						sender.sendMessage("現在のランク: " + RankList[NowRank]);
						sender.sendMessage("次のランク: " + RankList[NextRank]);
						return false;
						}
					else if ( args.length == 2 ) {
						Player other = getPlayer(args[1]);
						Score otherscore = rankobject.getScore(args[1]);
						int OtherNowRank = otherscore.getScore();
						int OtherNextRank = otherscore.getScore() + 1;
						if ( other == null ) {
							String offline = ChatColor.RED + "オフライン" + ChatColor.RESET;
							sender.sendMessage(BMC.prefix + " " + args[1] + " さんは" + offline + "です。");
						}
						else {
							sender.sendMessage(ChatColor.YELLOW + "========BMCサーバー ランクシステム========");
							sender.sendMessage("名前: " + args[1]);
							sender.sendMessage("現在のランク: " + RankList[OtherNowRank]);
							sender.sendMessage("次のランク: " + RankList[OtherNextRank]);
						}
					}
					else if ( args[0].equalsIgnoreCase("time")) {
						Objective objective = board.getObjective("ranktime");
						Score Time = objective.getScore(player);
						String TimeS = Integer.toString(Time.getScore());
						Integer TimeI = Time.getScore();
						String TimeAll = ChatColor.GOLD + TimeS + "カウント" + ChatColor.RESET;
						if ( TimeI == 0 ) {
							player.sendMessage(BMC.prefix  + "ランク有効時間が0です。");
							player.sendMessage(BMC.prefix  + "現在のランク: " + RankList[NowRank]);
						}
						else {
							player.sendMessage(BMC.prefix + "ランク有効時間: " + TimeAll );
						}

					}
				}

		}
		return false;
	}
	private Player getPlayer(String name) {
	    for ( Player player : Bukkit.getOnlinePlayers() ) {
	        if ( player.getName().equals(name) ) {
	            return player;
	        }
	    }
	    return null;
	}

}