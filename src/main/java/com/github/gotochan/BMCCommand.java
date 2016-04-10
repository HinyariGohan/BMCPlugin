package com.github.gotochan;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.github.gotochan.command.DebugCommand;
import com.github.gotochan.command.KitCommand;
import com.github.gotochan.command.MenuCommand;
import com.github.gotochan.command.SubCommandAbst;
import com.github.gotochan.ntp.KickCommand;

public class BMCCommand implements CommandExecutor {
	
	private ArrayList<SubCommandAbst> commands;
	
	public BMCCommand() {
		commands = new ArrayList<SubCommandAbst>();
		
		commands.add(new DebugCommand());
		commands.add(new KickCommand());
		commands.add(new MenuCommand());
		commands.add(new KitCommand());
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String label, String[] args) {
		for ( SubCommandAbst c : commands ) {
			if ( c.getCommandName().equalsIgnoreCase(args[0]) ) {
				return c.runCommand(sender, label, args);
			}
		}
		return false;
	}
}
