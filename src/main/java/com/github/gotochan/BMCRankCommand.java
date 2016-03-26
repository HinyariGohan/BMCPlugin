package com.github.gotochan;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

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

				String[] array = new String[] {ChatColor.GRAY + "Visitor", ChatColor.RED + "Red" , ChatColor.GOLD + "Orange",
						ChatColor.YELLOW + "Yellow", ChatColor.GREEN + "Green", ChatColor.BLUE + "Blue",
						ChatColor.DARK_BLUE + "Indigo", ChatColor.DARK_PURPLE + "Violet",
						ChatColor.WHITE + "UltraViolet", ChatColor.LIGHT_PURPLE + "あなたは最高ランクに達しました。"};
				int NowRank = MyStats.getScore();
				int NextRank = MyStats.getScore() + 1;
				if( args.length < 1 ) {
					player.sendMessage(BMC.prefix + ChatColor.GOLD  + "---[ランクシステム]---");
					player.sendMessage(BMC.prefix + ChatColor.GOLD + "/rank show");
					player.sendMessage(BMC.prefix + ChatColor.GOLD + "/rank stats");
					player.sendMessage(BMC.prefix + ChatColor.GOLD + "/rank time");
				}
				else if( args[0].equalsIgnoreCase("show")) {
						sender.sendMessage(BMC.prefix + ChatColor.RED + "[Red] " + ChatColor.WHITE + "一般人");
						sender.sendMessage(BMC.prefix + ChatColor.GOLD + "[Orange] " + ChatColor.WHITE + "駆け出しクラフター");
						sender.sendMessage(BMC.prefix + ChatColor.YELLOW + "[Yellow] " + ChatColor.WHITE + "慣れてきた感じのクラフター");
						sender.sendMessage(BMC.prefix + ChatColor.DARK_GREEN + "[Green] " + ChatColor.WHITE + "中堅クラフター");
						sender.sendMessage(BMC.prefix + ChatColor.BLUE + "[Blue] " + ChatColor.WHITE + "セミプロクラフター");
						sender.sendMessage(BMC.prefix + ChatColor.DARK_BLUE + "[Indigo]" + ChatColor.WHITE + "プロクラフター");
						sender.sendMessage(BMC.prefix + ChatColor.DARK_PURPLE + "[Violet] " + ChatColor.WHITE + "神がかったクラフター");
						sender.sendMessage(BMC.prefix + ChatColor.WHITE + "[UltraViolet] " + "よくわからない何か");
					}
					else if ( args[0].equalsIgnoreCase("stats")) {


						sender.sendMessage(ChatColor.YELLOW + "========BMCサーバー ランクシステム========");
						sender.sendMessage("名前: " + myname);
						sender.sendMessage("現在のランク: " + array[NowRank]);
						sender.sendMessage("次のランク: " + array[NextRank]);
					}
					else if ( args[0].equalsIgnoreCase("time")) {
						Objective objective = board.getObjective("ranktime");
						Score Time = objective.getScore(player);
						String TimeS = Integer.toString(Time.getScore());
						Integer TimeI = Time.getScore();
						String TimeAll = ChatColor.GOLD + TimeS + "時間" + ChatColor.RESET;
						if ( TimeI == 0 ) {
							player.sendMessage(BMC.prefix  + "ランク有効時間が0です。");
							player.sendMessage(BMC.prefix  + "現在のランク: " + array[NowRank]);
						}
						else {
							player.sendMessage(BMC.prefix + "ランク有効時間: " + TimeAll );
						}

					}
					else if ( args[0].equalsIgnoreCase("menu")) {
							Inventory inv;
							inv = Bukkit.createInventory(null, 9, "GUITest");
							inv.setItem(4, new ItemStack(Material.STONE));
							player.openInventory(inv);
						}
					}

		return false;
	}
}
