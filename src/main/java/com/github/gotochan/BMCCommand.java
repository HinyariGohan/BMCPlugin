package com.github.gotochan;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.github.gotochan.command.DebugCommand;
import com.github.gotochan.command.KitCommand;
import com.github.gotochan.command.MenuCommand;
import com.github.gotochan.command.SubCommandAbst;
import org.bukkit.entity.Player;

public class BMCCommand implements CommandExecutor {

	private ArrayList<SubCommandAbst> commands;

	private BMCPlugin bmc;

	public BMCCommand(BMCPlugin plugin) {
		this.bmc = plugin;

		commands = new ArrayList<>();
		commands.add(new MenuCommand(bmc));
		commands.add(new KitCommand(bmc));
		commands.add(new DebugCommand(bmc));
	}

	public boolean onCommand(CommandSender sender, Command cmd,
			String label, String[] args) {
		for ( SubCommandAbst c : commands ) {
			if ( c.getCommandName().equalsIgnoreCase(args[0]) )
				return c.runCommand(bmc.getBMCPlayer((Player) sender), label, args);
		}
		return false;
	}
}
