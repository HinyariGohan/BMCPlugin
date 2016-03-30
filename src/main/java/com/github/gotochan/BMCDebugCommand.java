package com.github.gotochan;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BMCDebugCommand extends BMCSubCommandAbst {

	public static final String COMMAND_NAME = "debug";

	@Override
	public String getCommandName() {
		return COMMAND_NAME;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean runCommand(CommandSender sender, String label, String[] args) {
		Player p = (Player) sender;
		/*
		 * args[0] debug
		 * args.length == 1
		 */
		if ( p.hasPermission("bmc.debug")) {
			if( args.length == 1 ) {
				return BMC.Debughelp(sender);
			}
			else if ( args.length > 1 ){
				if ( args[1].equalsIgnoreCase("itemhand") ) {
					ItemStack i = p.getItemInHand();
					if (i.getType() == Material.AIR ) { //AIRだった場合]
						p.sendMessage("あなたは手に何も持っていません。");
					}
					else {
						if ( i.getItemMeta().getDisplayName() == null ) {
						if ( !(i.getDurability() == 0) ) {
							p.sendMessage(i.getType().toString() +
									", " + i.getDurability());
						}
						else {
							p.sendMessage(i.getType().toString() +
									", " + "0");
						}
					}
						else {
							String displayname = i.getItemMeta().getDisplayName();
							if ( !(p.getItemInHand().getDurability() == 0) ) {
								p.sendMessage(p.getItemInHand().getType().toString() +
										", " + p.getItemInHand().getDurability() +
										", " + displayname);
							}
							else {
								p.sendMessage(p.getItemInHand().getType().toString() +
										", " + "0" +
										", " + displayname);
							}
						}
				}
			}
				if ( args[2].equalsIgnoreCase("rank") ) {
					if ( args[3].equalsIgnoreCase("reset")) {
						p.sendMessage("スコアをリセットしました。");
						p.getScoreboard().getObjective("rank").getScore(p).setScore(1);
					}
				}
				else if ( args[2].equalsIgnoreCase("kome")) {
					if ( args.length == 3) {
						p.sendMessage(BMC.prefix + "デバッグコマンド一覧");
						p.sendMessage(BMC.prefix + ChatColor.GOLD + "hunger" + ChatColor.RESET +" - 空腹にします。");
						p.sendMessage(BMC.prefix + ChatColor.GOLD + "get" + ChatColor.RESET + " - キノコシチューを手に入れます。");
					}
					else {
						if ( args[3].equalsIgnoreCase("hunger")) {
							p.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 60, 100));
						}
						else if ( args[3].equalsIgnoreCase("get")) {
							p.getInventory().addItem(new ItemStack(Material.MUSHROOM_SOUP));
						}
					}
				}
			}
		}
		return true;
	}
}
