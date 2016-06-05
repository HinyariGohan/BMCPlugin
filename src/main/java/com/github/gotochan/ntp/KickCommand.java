package com.github.gotochan.ntp;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.gotochan.BMC;
import com.github.gotochan.Utils.BMCHelp;
import com.github.gotochan.command.SubCommandAbst;

/**
 * NoTaisukePlus(NTP) Kick コマンドクラス
 * @author Hinyari_Gohan
 *
 */
public class KickCommand extends SubCommandAbst {
	
	public static final String COMMAND_NAME = "kick";
	
	@Override
	public String getCommandName() {
		return COMMAND_NAME;
	}
	
	public static String value;
	
	public static String values;
	
	@Override
	public boolean runCommand(CommandSender sender, String label, String[] args) {
		
		Player p = (Player) sender;
		if ( args.length == 1 ) {
			return BMCHelp.NTPhelp(sender);
		}
		else if ( args.length >= 2 ) {
			if ( p.hasPermission("bmc.acrobat")) {
				if ( args[1].equalsIgnoreCase("on") ) {
					value = "true";
					p.sendMessage(BMC.prefix + BMC.g + "NTPキックモードを on にしました。");
				}
				else if ( args[1].equalsIgnoreCase("off") ) {
					value = "false";
					p.sendMessage(BMC.prefix + BMC.g + "NTPキックモードを off にしました。");
				}
				else if ( args[1].equalsIgnoreCase("status")) {
					if ( value == "true" ) {
						values = "on";
					} else {
						values = "off";
					}
					String content = "NTP Kickモード ステータス";
					p.sendMessage(BMC.equal(sender, content));
					p.sendMessage(BMC.g + "NTPキックモードの状況: " + BMC.r + values);
				}
			}
		}
		return true;
	}
}
