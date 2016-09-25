package com.github.gotochan.command;

import com.github.gotochan.BMCPlayer;
import com.github.gotochan.BMCPlugin;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KitCommand extends SubCommandAbst
{
	
	public KitCommand(BMCPlugin bmc)
	{
		this.bmc = bmc;
	}
	
	private BMCPlugin bmc;
	
	@Override
	public String getCommandName()
	{
		return "KIT";
	}

	public boolean runCommand(BMCPlayer bmcPlayer, String label, String[] args)
	{
		bmcPlayer.errmsg("現在開発中です。");
		return true;
	}
	
}
