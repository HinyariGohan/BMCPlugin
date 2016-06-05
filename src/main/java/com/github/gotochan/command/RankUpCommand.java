package com.github.gotochan.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scoreboard.Score;

import com.github.gotochan.BMC;
import com.github.gotochan.BMCPlayer;
import com.github.gotochan.Utils.BMCHelp;

/**
 * BMCサーバー ランクコマンド ランクアップ実装クラス
 * @author Hinyari_Gohan
 */

public class RankUpCommand
{
	
	public static boolean runCommand(
			CommandSender sender, String label, String[] args)
	{
		
		Player player = (Player) sender;
		BMCPlayer bmcPlayer = BMCPlayer.getPlayer(player.getUniqueId());
		ItemStack item = player.getItemInHand();
		int nowRank = bmcPlayer.getRankID();
		Score score = bmcPlayer.getRankScore();
		String nowRankName = bmcPlayer.getRankName();
		String name = player.getName();
		
		if ( args.length == 0 ) {
			
			if ( item != null )
			{
				if ( item.getType() == Material.INK_SACK )
				{
					ItemMeta im = item.getItemMeta();
					int level = im.getEnchantLevel(Enchantment.DURABILITY);
					if ( nowRank == 0 )
					{
						player.sendMessage("あなたは" + nowRankName + "ランクです。");
						player.sendMessage(ChatColor.RED + "通常ワールドにスポーンしていてこのランクの場合は管理者に連絡してください。");
					}
					else if ( nowRank <= 8 )
					{
						if ( level == (nowRank + 1) )
						{
							score.setScore(nowRank + 1);
							Bukkit.broadcastMessage(BMC.prefix + name + " さんが " + nowRankName +
									ChatColor.RESET + " ランクにランクアップしました!");
						}
						else
						{
							player.sendMessage(BMC.prefix + "違うランクアイテムを持っています。正しいランクアイテムを持つ必要があります。");
						}
					}
					else if ( nowRank == 9 )
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