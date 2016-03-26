package com.github.gotochan;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * @author lshun
 */
public class KusoCommand
    implements CommandExecutor {

    public boolean onCommand(CommandSender commandSender,
                             Command cmd, String s, String[] strings) {
        if(cmd.getName().equalsIgnoreCase("nannte")){ //なんてー
            commandSender.sendMessage("kotta!"); //こったー。
        }
        return false; //以上
    }
}
