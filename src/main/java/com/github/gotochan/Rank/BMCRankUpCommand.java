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

import com.github.gotochan.BMC;

/**
 * BMCサーバー ランクアップコマンド実装クラス
 * @author Hinyari_Gohan
 *
 */

public class BMCRankUpCommand
	implements CommandExecutor {
	@SuppressWarnings({ "deprecation" })
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if ( sender instanceof Player ){
			if ( cmd.getName().equalsIgnoreCase("rankup") ){
				if ( args.length == 0 ) {
					Player p = (Player) sender;
					ItemStack i = p.getItemInHand();
					Scoreboard b = p.getScoreboard();
					Objective o = b.getObjective("rank");
					if ( o == null ) {
						o = b.registerNewObjective("rank", "");
					}
					Score score = o.getScore(p);
					int rank = score.getScore();
					String name = p.getName();
					String[] array = new String[] {ChatColor.RED + "Red" , ChatColor.GOLD + "Orange",
							ChatColor.YELLOW + "Yellow", ChatColor.GREEN + "Green", ChatColor.BLUE + "Blue",
							ChatColor.DARK_BLUE + "Indigo", ChatColor.DARK_PURPLE + "Violet",
							ChatColor.WHITE + "UltraViolet"};
					String wrong = BMC.prefix + "違うランクアイテムを持っています。正しいランクアイテムを持つ必要があります。";
					if ( i != null && i.getTypeId() != 0 ) {
						if ( i.getType() == Material.INK_SACK ){
							ItemMeta im = i.getItemMeta();
							int level = im.getEnchantLevel(Enchantment.DURABILITY);
							if ( rank <= 7 ) {
								if ( level == (rank + 1) ) {
									score.setScore(rank + 1);
									Bukkit.broadcastMessage(BMC.prefix + name + " さんが " + array[rank] +
											ChatColor.RESET + " ランクにランクアップしました!");
								}
								else {
									p.sendMessage(wrong);
								}
							}
						else {
							if ( rank == 8 ){
								p.sendMessage(ChatColor.LIGHT_PURPLE + "あなたは最高ランクへ達しています。");
							}
							else {
								p.sendMessage(BMC.prefix + "ランクアイテムを持つ必要があります。");
							}
						}
					}
						else {
							p.sendMessage(BMC.prefix + "ランクアイテムを持つ必要があります。");
						}
				}
					else {
						p.sendMessage(BMC.prefix + "アイテムを持っていません。");
					}
				}
				else if ( args.length >= 1 ) {
					Player p = (Player) sender;
					if ( args[0].equalsIgnoreCase("debug") ) {
						if( args.length == 1 ) {
							String example = BMC.prefix + ChatColor.GOLD + "";
							String reset = ChatColor.RESET + " - ";
							p.sendMessage(example + "itemhand" + reset + "手に持っているアイテムを返します。");
							p.sendMessage(example + "reset" + reset  + "ランクのスコアをリセットします。");
						}
						else if ( args.length > 1 ){
							if ( args[1].equalsIgnoreCase("itemhand") ) {
								ItemStack i = p.getItemInHand();
								if (i.getType() == Material.AIR ) { //AIRだった場合]
									p.sendMessage("あなたは手に何も持っていません。");
								}
								else {
									if ( i.getItemMeta().getDisplayName() == null ) {
									if ( !(i.getDurability() == 0) ) {
										p.sendMessage(i.getType().toString() +
												", " + i.getDurability());
									}
									else {
										p.sendMessage(i.getType().toString() +
												", " + "0");
									}
								}
									else {
										String displayname = i.getItemMeta().getDisplayName();
										if ( !(p.getItemInHand().getDurability() == 0) ) {
											p.sendMessage(p.getItemInHand().getType().toString() +
													", " + p.getItemInHand().getDurability() +
													", " + displayname);
										}
										else {
											p.sendMessage(p.getItemInHand().getType().toString() +
													", " + "0" +
													", " + displayname);
										}
									}
							}
						}
							else if ( args[1].equalsIgnoreCase("reset") ) {
								p.sendMessage("スコアをリセットしました。");
								p.getScoreboard().getObjective("rank").getScore(p).setScore(1);
							}
						}
					}
				}
			}
		}
		return false;
	}
}