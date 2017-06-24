package xyz.hinyari.bmcplugin;

import java.util.ArrayList;

import xyz.hinyari.bmcplugin.command.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import org.bukkit.entity.Player;

public class BMCSubCommand implements CommandExecutor
{

    private ArrayList<SubCommandAbst> commands;

    private BMCPlugin bmc;

    public BMCSubCommand(BMCPlugin plugin)
    {
        this.bmc = plugin;

        commands = new ArrayList<>();
        commands.add(new MenuCommand(bmc));
        commands.add(new KitCommand(bmc));
        commands.add(new DebugCommand(bmc));
        commands.add(new PlayerInfoCommand());
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        for (SubCommandAbst c : commands)
        {
            if (c.getCommandName().equalsIgnoreCase(args[0]))
                return c.runCommand(bmc.getBMCPlayer((Player) sender), label, args);
        }
        return false;
    }
}
