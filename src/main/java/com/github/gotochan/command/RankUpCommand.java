package com.github.gotochan.command;

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
import com.github.gotochan.resource.BMCHelp;

/**
 * BMCサーバー ランクコマンド ランクアップ実装クラス
 * @author Hinyari_Gohan
 */

public class RankUpCommand
{
	
	@SuppressWarnings({ "deprecation" })
	
	public static boolean runCommand(
			CommandSender sender, String label, String[] args)
	{
		
		Player player = (Player) sender;
		ItemStack item = player.getItemInHand();
		Scoreboard board = player.getScoreboard();
		Objective objective = board.getObjective("rank");
		Score score = objective.getScore(player);
		int rank = score.getScore();
		String name = player.getName();
		
		if ( args.length == 0 ) {
			
			if ( item != null )
			{
				if ( item.getType() == Material.INK_SACK )
				{
					ItemMeta im = item.getItemMeta();
					int level = im.getEnchantLevel(Enchantment.DURABILITY);
					if ( rank == 0 )
					{
						player.sendMessage("あなたは" + RankCommand.RankList[rank] + "ランクです。");
						player.sendMessage(ChatColor.RED + "通常ワールドにスポーンしていてこのランクの場合は管理者に連絡してください。");
					}
					else if ( rank <= 8 )
					{
						if ( level == (rank + 1) )
						{
							score.setScore(rank + 1);
							Bukkit.broadcastMessage(BMC.prefix + name + " さんが " + RankCommand.RankList[rank] +
									ChatColor.RESET + " ランクにランクアップしました!");
						}
						else
						{
							player.sendMessage(BMC.prefix + "違うランクアイテムを持っています。正しいランクアイテムを持つ必要があります。");
						}
					}
					else if ( rank == 9 )
					{
						player.sendMessage(ChatColor.LIGHT_PURPLE + "あなたは最高ランクへ達しています。");
					}
					else
					{
						player.sendMessage(BMC.prefix + "クリアランス違反です。");
					}
				}
				else
				{
					player.sendMessage(BMC.prefix + "ランクアイテムを持って下さい。");
				}
			}
			else
			{
				player.sendMessage(BMC.prefix + "手にアイテムを持っていません！");
				player.sendMessage("ランクアップの方法を確認するには" + BMC.g + " /rankup help " + BMC.r +
						"コマンドを使用します。");
			}
		}
		else if ( args.length == 1 && args[0].equalsIgnoreCase("help") )
		{
			return BMC.RankupProcessInfo(sender);
		}
		else
		{
			return BMCHelp.RankUphelp(sender);
		}
		
		return true;
	}
}