package com.github.gotochan.ntp;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.gotochan.BMC;
import com.github.gotochan.command.SubCommandAbst;

public class FreezeCommand extends SubCommandAbst
{
	
	public static final String COMMAND_NAME = "freeze";
	
	public static boolean isFreeze;
	
	@Override
	public String getCommandName()
	{
		return COMMAND_NAME;
	}
	
	@Override
	public boolean runCommand(CommandSender sender, String label, String[] args)
	{
		Player player = (Player) sender;
		if ( Bukkit.getPlayer("Taisuke_n") == null )
		{
			sender.sendMessage(BMC.ntp + "たいすけはこのサーバーにいません。");
			return false;
		}
		
		if ( isFreeze == true )
		{
			isFreeze = false;
			player.sendMessage(BMC.ntp + "フリーズモードを " + isFreeze + " に変更しました。");
		}
		else {
			isFreeze = true;
			player.sendMessage(BMC.ntp + "フリーズモードを " + isFreeze + " に変更しました。");
		}
		return false;
	}
	
	
}

