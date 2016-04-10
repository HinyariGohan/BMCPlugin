package com.github.gotochan.command;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.gotochan.BMC;
import com.github.gotochan.ability.Ability;
import com.github.gotochan.ability.Acrobat;
import com.github.gotochan.ability.Scout;
import com.github.gotochan.resource.BMCHelp;

public class KitCommand extends SubCommandAbst {
	
	private ArrayList<SubKitAbst> kits;
	
	public KitCommand() {
		kits = new ArrayList<SubKitAbst>();
		
		kits.add(new Acrobat());
		kits.add(new Scout());
	}
	
	public boolean onKitCommand(CommandSender sender, String[] args) {
		for ( SubKitAbst c : kits ) {
			if ( c.getKitName().equalsIgnoreCase(args[1]) ) {
				return c.runKitCommand(sender, args);
			}
		}
		return false;
	}
	
	public static final String COMMAND_NAME = "kit";
	
	
	@Override
	public String getCommandName() {
		return COMMAND_NAME;
	}
	
	@Override
	public boolean runCommand(CommandSender sender, String label, String[] args) {
		Player player = (Player) sender;
		if ( args.length == 1 ) {
			return BMCHelp.KitHelp(sender);
		}
		else if ( args.length >= 2 ) {
			if ( Ability.KIT_LIST.contains(args[1]) ) {
				return onKitCommand(sender, args);
			} else if ( args[1].equalsIgnoreCase("list") ) {
				player.sendMessage("bmc kit list");
			} else {
				return BMCHelp.KitHelp(sender);
			}
		}
		return true;
	}
	
	public static boolean KitExample(CommandSender sender, String KIT_NAME, String KIT_SKILL) {
		Player player = (Player) sender;
		player.sendMessage(BMC.g + "キット名: " + BMC.r + KIT_NAME);
		player.sendMessage(BMC.g + "特殊機能: " + BMC.r + KIT_SKILL);
		return false;
	}
}
