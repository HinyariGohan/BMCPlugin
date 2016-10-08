package xyz.hinyari.bmcplugin;

import java.util.ArrayList;

import xyz.hinyari.bmcplugin.command.DebugCommand;
import xyz.hinyari.bmcplugin.command.KitCommand;
import xyz.hinyari.bmcplugin.command.MenuCommand;
import xyz.hinyari.bmcplugin.command.SubCommandAbst;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

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
