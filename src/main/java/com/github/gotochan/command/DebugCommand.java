package com.github.gotochan.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.github.gotochan.ability.Ability;
import com.github.gotochan.resource.BMCHelp;

public class DebugCommand extends SubCommandAbst {
	
	public static final String COMMAND_NAME = "debug";
	
	@Override
	public String getCommandName() {
		return COMMAND_NAME;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean runCommand(CommandSender sender, String label, String[] args) {
		Player p = (Player) sender;
		if ( p.hasPermission("bmc.debug")) {
			if( args.length == 1 ) {
				return BMCHelp.Debughelp(sender);
			}
			else if ( args.length >= 2 ){
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
				else if ( args[1].equalsIgnoreCase("gethashmap")) {
					if ( Ability.KIT_LIST.size() == 0) {
						sender.sendMessage("KIT_LIST には 何も格納されていません。");
					} else {
						int h;
						for( h = 0; h < Ability.KIT_LIST.size(); h++) {
							sender.sendMessage(Ability.KIT_LIST.get(h) );
						}
					}
				}
				else if ( args.length == 2 ) {
					return BMCHelp.Debughelp(sender);
				}
				else if ( args[1].equalsIgnoreCase("rank") ) {
					if ( args[2].equalsIgnoreCase("reset")) {
						p.sendMessage("スコアをリセットしました。");
						p.getScoreboard().getObjective("rank").getScore(p).setScore(1);
					}
				}
				else if ( args[1].equalsIgnoreCase("kome")) {
					if ( args[2].equalsIgnoreCase("hunger")) {
						p.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 60, 100));
					}
					else if ( args[2].equalsIgnoreCase("get")) {
						ItemStack is = new ItemStack(Material.MUSHROOM_SOUP);
						ItemMeta im = is.getItemMeta();
						List<String> lore = new ArrayList<String>();
						lore.add( "§a***中間素材***" );
						im.addEnchant(Enchantment.DURABILITY, 1, false);
						im.setLore(lore);
						im.setDisplayName("§6コシヒカリ");
						im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
						is.setItemMeta(im);
						p.getInventory().addItem(is);
					}
				}
				else if ( args[2].equalsIgnoreCase("namereset"))
				{
					Player player = (Player) sender;
					player.setDisplayName(player.getName());
				}
				
				else {
					return BMCHelp.Debughelp(sender);
				}
			}
		}
		return true;
	}
}
