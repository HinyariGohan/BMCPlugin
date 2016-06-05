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

import com.github.gotochan.Utils.BMCHelp;
import com.github.gotochan.enchant.AutoSmelt;
import com.github.gotochan.event.Scout;

public class DebugCommand extends SubCommandAbst {
	
	public static final String COMMAND_NAME = "debug";
	
	@Override
	public String getCommandName() {
		return COMMAND_NAME;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean runCommand(CommandSender sender, String label, String[] args) {
		Player player = (Player) sender;
		if ( !(player.hasPermission("bmc.debug")) )
		{
			player.sendMessage("§4You don't have permission!");
		}
		
		if( args.length == 1 )
		{
			return BMCHelp.Debughelp(sender);
		}
		
		else if ( args.length >= 2 )
		{
			if ( args[1].equalsIgnoreCase("itemhand") )
			{
				ItemStack i = player.getItemInHand();
				
				if ( i.getType() != null && i.getTypeId() != 0 )
				{
					
					if ( i.getItemMeta().getDisplayName() == null ) {
						if ( !(i.getDurability() == 0) ) {
							player.sendMessage(i.getType().toString() +
									", " + i.getDurability());
						}
						else {
							player.sendMessage(i.getType().toString() +
									", " + "0");
						}
					}
					else {
						String displayname = i.getItemMeta().getDisplayName();
						if ( !(player.getItemInHand().getDurability() == 0) ) {
							player.sendMessage(player.getItemInHand().getType().toString() +
									", " + player.getItemInHand().getDurability() +
									", " + displayname);
						}
						else {
							player.sendMessage(player.getItemInHand().getType().toString() +
									", " + "0" +
									", " + displayname);
						}
					}
				}
				else {
					player.sendMessage("手に何も持っていません。");
				}
			}
			
			else if ( args[1].equalsIgnoreCase("rank") ) {
				if ( args[2].equalsIgnoreCase("reset")) {
					player.sendMessage("スコアをリセットしました。");
					player.getScoreboard().getObjective("rank").getScore(player).setScore(1);
				}
			}
			else if ( args[1].equalsIgnoreCase("kome")) {
				if ( args[2].equalsIgnoreCase("hunger")) {
					player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 60, 100));
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
					player.getInventory().addItem(is);
				}
			}
			else if ( args[1].equalsIgnoreCase("namereset"))
			{
				player.setDisplayName(player.getName());
			}
			
			else if ( args[1].equalsIgnoreCase("ench"))
			{
				if ( args.length == 2 )
				{
					return DebugEnchCommandHelp(sender);
				}
				
				ItemStack item = player.getItemInHand();
				ItemMeta meta = item.getItemMeta();
				
				if ( item.getType() != null && item.getTypeId() != 0 )
				{
					if ( args[2].equalsIgnoreCase("fall") ) {
						if ( item.getType() == Material.DIAMOND_BOOTS )
						{
							Enchantment ench = Enchantment.PROTECTION_FALL;
							if ( item.containsEnchantment(ench) )
							{
								player.sendMessage("Already item has " + ench.getName() + " Enchant!");
								return false;
							}
							
							item.addUnsafeEnchantment(ench, 10);
							player.sendMessage("正常にエンチャントメントが実行されました。");
						}
						else {
							player.sendMessage("ダイヤモンドのブーツである必要があります。");
						}
					}
					else if ( args[2].equalsIgnoreCase("fire") ) {
						if ( item.getType() == Material.DIAMOND_CHESTPLATE
								|| item.getType() == Material.IRON_CHESTPLATE )
						{
							Enchantment ench = Enchantment.PROTECTION_FIRE;
							if ( item.containsEnchantment(ench) )
							{
								player.sendMessage("Already item has " + ench.getName() + " Enchant!");
								return false;
							}
							
							item.addUnsafeEnchantment(ench, 10);
							player.sendMessage("正常にエンチャントメントが実行されました。");
						}
						else {
							player.sendMessage("ダイヤ・鉄のチェストプレートである必要があります。");
						}
					}
					else if ( args[2].equalsIgnoreCase("smelt")) {
						if ( meta.hasLore() )
						{
							player.sendMessage("このアイテムにエンチャントをつけることは出来ません。");
							return false;
						}
						
						if ( !(AutoSmelt.isTool(item.getType())) )
						{
							player.sendMessage("このアイテムにエンチャントをつけることは出来ません。");
							return false;
						}
						
						Enchantment ench = Enchantment.SILK_TOUCH;
						if ( item.containsEnchantment(ench) )
						{
							player.sendMessage("シルクタッチと一緒にすることは出来ません。");
							return false;
						}
						
						if ( AutoSmelt.lore.isEmpty() )
						{
							AutoSmelt.lore.add(0, "§4Auto Smelt");
							meta.setLore(AutoSmelt.lore);
							item.setItemMeta(meta);
							player.sendMessage("正常にエンチャントメントが実行されました。");
						}
						else {
							meta.setLore(AutoSmelt.lore);
							item.setItemMeta(meta);
							player.sendMessage("正常にエンチャントメントが実行されました。");
						}
					}
					else if ( args[2].equalsIgnoreCase("unbreaking") )
					{
						if ( args.length == 3 )
						{
							player.sendMessage("§c[BMC] 引数を指定してください。");
						}
						else if ( args.length == 4 )
						{
							item.getItemMeta().addEnchant(
									Enchantment.DURABILITY,
									Integer.valueOf(args[3]),
									true);
							player.updateInventory();
						}
						else {
							player.sendMessage("§c[BMC] 引数指定が間違っています。");
							return true;
						}
					}
					else {
						return DebugEnchCommandHelp(sender);
					}
				}
				else {
					player.sendMessage("エンチャントしたいアイテムを手に持つ必要があります。");
				}
			}
			else if ( args[1].equalsIgnoreCase("grapple") ) {
				Scout.im.setDisplayName(Scout.GRAPPLE_NAME);
				Scout.grappleItem.setItemMeta(Scout.im);
				player.getInventory().addItem(Scout.grappleItem);
			}
			else {
				return BMCHelp.Debughelp(sender);
			}
		}
		return false;
	}
	
	private boolean DebugEnchCommandHelp(CommandSender sender)
	{
		Player player = (Player) sender;
		player.sendMessage("Useful: " + "/bmc debug ench <fall/fire/smelt>");
		return false;
	}
}
