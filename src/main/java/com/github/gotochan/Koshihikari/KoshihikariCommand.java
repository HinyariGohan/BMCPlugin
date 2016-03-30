package com.github.gotochan.Koshihikari;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import com.github.gotochan.BMC;
import com.github.gotochan.BMCSubCommandAbst;

/**
 *
 * @author Hinyari_Gohan
 *
 */

public class KoshihikariCommand
	extends BMCSubCommandAbst {

	public static final String COMMAND_NAME = "kome";

	@Override
	public String getCommandName() {
		return COMMAND_NAME;
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean runCommand(CommandSender sender, String label, String[] args) {
		Player player = (Player) sender;
		Scoreboard board = player.getScoreboard();
		Objective objective = board.getObjective("koshihikari");
		if ( args.length == 1 ) {
			BMC.Komehelp(sender);
		}
		else if ( args[1].equalsIgnoreCase("ticket")) {
			if ( args.length == 2 ) {
				Score KScore = objective.getScore(player);
				player.sendMessage(BMC.prefix  + "あなたのコシヒカリ交換チケット数は" +
							Integer.toString(KScore.getScore()) + "枚です。");
			}
			else if ( args.length == 3 ){
				Player other = getPlayer(args[2]);
				if ( other == null ) {
					String offline = ChatColor.RED + "オフライン" + ChatColor.RESET;
					sender.sendMessage(BMC.prefix + args[2] + " さんは" + offline + "です。");
				}
				else {
					Score OScore = objective.getScore(other);
					player.sendMessage(BMC.prefix + args[2] + " さんのコシヒカリ交換チケット数は" +
							Integer.toString(OScore.getScore()) + "枚です。");
				}
			}
		}
	return true;
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

