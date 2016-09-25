package com.github.gotochan.command;

import com.github.gotochan.Rank;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.github.gotochan.BMCPlugin;
import com.github.gotochan.BMCPlayer;
import com.github.gotochan.Utils.BMCHelp;

import static sun.management.snmp.jvminstr.JvmThreadInstanceEntryImpl.ThreadStateMap.Byte1.other;

/**
 *
 * @author Hinyari_Gohan
 *
 */

public class RankCommand {
	
	private BMCPlugin bmc;
	private BMCHelp bmcHelp;
	
	public RankCommand(BMCPlugin plugin)
	{
		this.bmc = plugin;
		this.bmcHelp = bmc.bmcHelp;
	}
	
	
	public boolean runCommand(BMCPlayer bmcPlayer, String label, String[] args)
	{
		//Player player = bmcPlayer.getPlayer();
		String myname = bmcPlayer.getName();
		
		if( args.length == 0 )
		{
			bmcHelp.Rankhelp(bmcPlayer);
		}
		else
		{
			/**
			 * if( args[0].equalsIgnoreCase("show"))
			{
				bmcPlayer.msg(ChatColor.RED + "[Red] " + ChatColor.WHITE + "一般人");
				bmcPlayer.msg(ChatColor.GOLD + "[Orange] " + ChatColor.WHITE + "駆け出しクラフター");
				bmcPlayer.msg(ChatColor.YELLOW + "[Yellow] " + ChatColor.WHITE + "慣れてきた感じのクラフター");
				bmcPlayer.msg(ChatColor.DARK_GREEN + "[Green] " + ChatColor.WHITE + "中堅クラフター");
				bmcPlayer.msg(ChatColor.BLUE + "[Blue] " + ChatColor.WHITE + "セミプロクラフター");
				bmcPlayer.msg(ChatColor.DARK_BLUE + "[Indigo]" + ChatColor.WHITE + "プロクラフター");
				bmcPlayer.msg(ChatColor.DARK_PURPLE + "[Violet] " + ChatColor.WHITE + "神がかったクラフター");
				bmcPlayer.msg(ChatColor.WHITE + "[UltraViolet] " + "よくわからない何か");
			}
			 **/
			if ( args[0].equalsIgnoreCase("stats"))
			{
				if ( args.length == 1 )
				{
					int score = bmcPlayer.getScoreboard().getRankPoint();
					bmcPlayer.msg(ChatColor.YELLOW + "========BMCサーバー ランクシステム========");
					bmcPlayer.msg("名前: " + myname);
					bmcPlayer.msg("現在のランク: " + Rank.getLabelOfName(score));
					bmcPlayer.msg("次のランク: " + Rank.getLabelOfName(score + 1));
				}
				else if ( args.length == 2 )
				{
					Player player_other = getPlayer(args[1]);
					if ( player_other == null )
					{
						bmcPlayer.msg(bmc.ERROR + "そのプレイヤーはオフラインです。");
					}
					else
					{
						BMCPlayer other = bmc.getBMCPlayer(player_other);
						int other_score = other.getScoreboard().getRankPoint();
						bmcPlayer.msg(ChatColor.YELLOW + "========BMCサーバー ランクシステム========");
						bmcPlayer.msg("名前: " + args[1]);
						bmcPlayer.msg("現在のランク: " + Rank.getLabelOfName(other_score));
						bmcPlayer.msg("次のランク: " + Rank.getLabelOfName(other_score + 1));
					}
				} else {
					return bmcHelp.Rankhelp(bmcPlayer);
				}
			} else
				return bmcHelp.Rankhelp(bmcPlayer);
		}
		return false;
	}
	private static Player getPlayer(String name)
	{
		for ( Player player : Bukkit.getOnlinePlayers() )
		{
			if ( player.getName().equals(name) )
				return player;
		}
		return null;
	}
}