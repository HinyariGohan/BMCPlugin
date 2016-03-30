package com.github.gotochan.Rank;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
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
 * BMCサーバー ランクコマンド ランクアップ実装クラス
 * @author Hinyari_Gohan
 */

public class BMCRankUpCommand {
	@SuppressWarnings({ "deprecation" })
	public boolean runCommand(
		CommandSender sender, String label, String[] args) {
		/*
		 * args[0] rank
		 * args[1] rankup
		 * args.length == 2
		 */
		Player p = (Player) sender;
		ItemStack i = p.getItemInHand();
		Scoreboard b = p.getScoreboard();
		Objective o = b.getObjective("rank");
		Objective rt = b.getObjective("ranktime");
		if ( o == null ) {
			o = b.registerNewObjective("rank", "");
		}
		if ( rt == null ) {
			rt = b.registerNewObjective("ranktime", "");
		}
		Score score = o.getScore(p);
		Score rtscore = rt.getScore(p);
		int rank = score.getScore();
		String name = p.getName();
		String wrong = BMC.prefix + "違うランクアイテムを持っています。正しいランクアイテムを持つ必要があります。";
		if ( args.length == 2 ) {
			if ( i != null && i.getTypeId() != 0 ) {
				if ( i.getType() == Material.INK_SACK ){
					ItemMeta im = i.getItemMeta();
					int level = im.getEnchantLevel(Enchantment.DURABILITY);
					if ( rank == 0 ) {
						p.sendMessage("あなたは" + BMCRankCommand.RankList[rank] + "ランクです。");
						p.sendMessage(ChatColor.RED + "通常ワールドにスポーンしていてこのランクの場合は管理者に連絡してください。");
					}
					else if ( rank <= 8 ) {
						if ( level == (rank + 1) ) {
							score.setScore(rank + 1);
							rtscore.setScore(2);
							Bukkit.broadcastMessage(BMC.prefix + name + " さんが " + BMCRankCommand.RankList[rank] +
									ChatColor.RESET + " ランクにランクアップしました!");
							p.sendMessage("ランク有効時間がリセットされました。ランクに応じたカラー米粉を作成してください。");
						}
						else {
							p.sendMessage(wrong);
						}
					}
				else {
					if ( rank == 9 ){
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
				p.sendMessage("ランクアップの方法を確認するには" + BMC.g + " /bmc rank rankup help " + BMC.r +
						"コマンドを使用します。");
			}
		}
		else if ( args.length >= 3 ) {
			if ( args[2].equalsIgnoreCase("help") ) {
				return BMC.RankupProcessInfo(sender);
			}
		}
		return true;
	}
}