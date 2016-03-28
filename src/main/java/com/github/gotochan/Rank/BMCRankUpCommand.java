package com.github.gotochan.Rank;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class BMCRankUpCommand
	implements CommandExecutor {
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if ( sender instanceof Player ){
			if ( cmd.getName().equalsIgnoreCase("rankup") ){
				if ( args.length < 1 ) {
					Player p = (Player) sender;
					ItemStack i = p.getItemInHand();
					ItemMeta im = i.getItemMeta();
					int level = im.getEnchantLevel(Enchantment.DURABILITY);
					Scoreboard b = p.getScoreboard();
					Objective o = b.getObjective("rank");
					Score score = o.getScore(p);
					int rank = score.getScore();
					String name = p.getName();
					String[] array = new String[] {ChatColor.GRAY + "Visitor", ChatColor.RED + "Red" , ChatColor.GOLD + "Orange",
							ChatColor.YELLOW + "Yellow", ChatColor.GREEN + "Green", ChatColor.BLUE + "Blue",
							ChatColor.DARK_BLUE + "Indigo", ChatColor.DARK_PURPLE + "Violet",
							ChatColor.WHITE + "UltraViolet"};
					String wrong = "不正なアイテムです。";
					if ( p.getItemInHand() != null )
					if ( i.getType() == Material.INK_SACK ){
						if ( rank == 1 ){
							if ( level == 2 ){
								Bukkit.broadcastMessage(name + "さんが" + array[rank] + "にランクアップしました!");
							}
							else {
								p.sendMessage(wrong);
							}
						}
						if ( rank == 2 ){
							if ( level == 3 ){
								Bukkit.broadcastMessage(name + "さんが" + array[rank] + "にランクアップしました!");
							}
							else {
								p.sendMessage(wrong);
							}
						}
						if ( rank == 3 ){
							if ( level == 4 ){
								Bukkit.broadcastMessage(name + "さんが" + array[rank] + "にランクアップしました!");
							}
							else {
								p.sendMessage(wrong);
							}
						}
						if ( rank == 4 ){
							if ( level == 5 ){
								Bukkit.broadcastMessage(name + "さんが" + array[rank] + "にランクアップしました!");
							}
							else {
								p.sendMessage(wrong);
							}
						}
						if ( rank == 5 ){
							if ( level == 6 ){
								Bukkit.broadcastMessage(name + "さんが" + array[rank] + "にランクアップしました!");
							}
							else {
								p.sendMessage(wrong);
							}
						}
						if ( rank == 6 ){
							if ( level == 7 ){
								Bukkit.broadcastMessage(name + "さんが" + array[rank] + "にランクアップしました!");
							}
							else {
								p.sendMessage(wrong);
							}
						}
						if ( rank == 7 ){
							if ( level == 8 ){
								Bukkit.broadcastMessage(name + "さんが" + array[rank] + "にランクアップしました!");
							}
							else {
								p.sendMessage(wrong);
							}
						}
					}
				}
			}
		}
		return false;
	}
}