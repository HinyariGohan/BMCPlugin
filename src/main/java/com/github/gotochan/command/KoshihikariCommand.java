package com.github.gotochan.command;

import com.github.gotochan.BMCPlayer;
import com.github.gotochan.BMCPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.github.gotochan.Utils.BMCHelp;

/**
 *
 * @author Hinyari_Gohan
 *
 */

public class KoshihikariCommand
{
	private BMCPlugin plugin;
	private BMCHelp bmcHelp;
	
	public KoshihikariCommand(BMCPlugin plugin)
	{
		this.plugin = plugin;
		this.bmcHelp = plugin.bmcHelp;
	}

	public boolean runCommand(BMCPlayer bmcPlayer, String label, String[] args) {
		if (args.length == 0 ||  args.length >= 3) { return bmcHelp.Komehelp(bmcPlayer); }
		else {
			if (args[0].equalsIgnoreCase("point")) {
				BMCPlayer target = bmcPlayer;
				if (args.length == 2) {
					if (getPlayer(args[1]) == null) { bmcPlayer.errmsg("そのプレイヤーはオンラインではありません。"); return true; }
					else { target = plugin.getBMCPlayer(getPlayer(args[1])); }
				}
				bmcPlayer.msg(target.getName() + "さんのコシヒカリ交換可能ポイントは &6" +
						target.getScoreboard().getKomePoint() + "ポイント&r です。");
			} else return bmcHelp.Komehelp(bmcPlayer);
		}
		return false;
	}
	
	private Player getPlayer(String name) {
		for ( Player player : Bukkit.getOnlinePlayers() ) {
			if ( player.getName().equals(name) )
				return player;
		}
		return null;
	}
}

