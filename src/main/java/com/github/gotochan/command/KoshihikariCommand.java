package com.github.gotochan.command;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import com.github.gotochan.BMC;
import com.github.gotochan.Utils.BMCHelp;

/**
 *
 * @author Hinyari_Gohan
 *
 */

public class KoshihikariCommand
{
	@SuppressWarnings("deprecation")
	public static boolean runCommand(CommandSender sender, String label, String[] args) {
		Player player = (Player) sender;
		Scoreboard board = player.getScoreboard();
		Objective objective = board.getObjective("koshihikari");
		
		if ( args.length == 0 )
		{
			BMCHelp.Komehelp(sender);
		}
		else if ( args[0].equalsIgnoreCase("ticket"))
		{
			if ( args.length == 1 )
			{
				Score KScore = objective.getScore(player);
				player.sendMessage(BMC.prefix  + "あなたのコシヒカリ交換チケット数は" +
						Integer.toString(KScore.getScore()) + "枚です。");
			}
			else if ( args.length == 2 )
			{
				Player other = getPlayer(args[1]);
				if ( other == null )
				{
					sender.sendMessage(BMC.prefix + "そのプレイヤーはオフラインです。");
				}
				else
				{
					Score OScore = objective.getScore(other);
					player.sendMessage(BMC.prefix + args[1] + " さんのコシヒカリ交換チケット数は" +
							Integer.toString(OScore.getScore()) + "枚です。");
				}
			}
		}
		else
		{
			return BMCHelp.Komehelp(sender);
		}
		return true;
	}
	
	private static Player getPlayer(String name) {
		for ( Player player : Bukkit.getOnlinePlayers() ) {
			if ( player.getName().equals(name) ) {
				return player;
			}
		}
		return null;
	}
}

