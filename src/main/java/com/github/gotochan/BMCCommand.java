package com.github.gotochan;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.github.gotochan.Koshihikari.KoshihikariCommand;
import com.github.gotochan.Rank.BMCRankCommand;

public class BMCCommand implements CommandExecutor {

	private ArrayList<BMCSubCommandAbst> commands;

	public BMCCommand() {
		commands = new ArrayList<BMCSubCommandAbst>();

		commands.add(new BMCRankCommand());
		commands.add(new KoshihikariCommand());
		commands.add(new BMCDebugCommand());
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String label, String[] args) {
		for ( BMCSubCommandAbst c : commands ) {
			if ( c.getCommandName().equalsIgnoreCase(args[0]) ) {
				return c.runCommand(sender, label, args);
			}
		}
		return false;
	}
}
