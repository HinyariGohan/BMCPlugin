package com.github.gotochan.event;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.gotochan.command.MenuCommand;

public class BMCDisableEvent implements Listener
{
	@EventHandler
	public void BlockPlaceBlocker(BlockPlaceEvent event)
	{
		Player player = event.getPlayer();
		ItemStack item = player.getItemInHand();
		
		if ( !(item.hasItemMeta()) )
		{
			return;
		}
		
		ItemMeta meta = item.getItemMeta();
		String name = meta.getDisplayName();
		
		if ( name.contains("炭素の塊")
				|| name.contains("採掘の結晶")
				|| name.contains("Water")
				|| name.contains("Crushed")
				|| name.contains("Cleaned"))
		{
			event.setCancelled(true);
			player.sendMessage("§c[Error] " + "特殊ブロックを設置することは出来ません。");
			player.playSound(player.getLocation(), Sound.NOTE_STICKS, 10L, 1L);
		}
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event)
	{
		Inventory inventory = event.getClickedInventory();
		Player player = (Player) event.getWhoClicked();
		
		// nullの場合 returnする。
		if ( inventory == null )
		{
			return;
		}
		
		ItemStack item = event.getCurrentItem();
		
		if ( player.getOpenInventory().getTitle().contains("メニュー") )
		{
			if ( item != null && item.getType() == Material.FLINT )
			{
				if ( item.hasItemMeta() &&
						item.getItemMeta().getDisplayName().contains("粉砕具") )
				{
					event.setCancelled(true);
					return;
				}
			}
		}
		
		if ( inventory.getName().contains("メニュー") )
		{
			if ( !(event.getSlot() == 20 || event.getSlot() == 24) )
			{
				
				event.setCancelled(true);
			}
			//if分岐
			if ( item == null )
			{
				return;
			}
			
			if ( !(item.hasItemMeta()) )
			{
				return;
			}
			
			
			ItemMeta meta = item.getItemMeta();
			
			if ( item.getType() == Material.STAINED_CLAY )
			{
				if ( meta.getDisplayName().contains("OK") )
				{
					if ( inventory.getItem(20) == null )
					{
						player.sendMessage("§c[Error] " + "粉砕したいアイテムを青いガラスの中央に入れる必要があります。");
						player.playSound(player.getLocation(), Sound.NOTE_STICKS, 10, 1);
						return;
					}
					
					ItemStack slot = inventory.getItem(20);
					ItemStack out = inventory.getItem(24);
					
					//20番
					//24番
					int amount = slot.getAmount();
					int ramount = 0;
					
					if ( out != null )
					{
						ramount = out.getAmount();
					}
					
					if ( ramount == 64 )
					{
						return;
					}
					
					player.playSound(player.getLocation(), Sound.ITEM_BREAK, 10L, (float) 1.8);
					
					if ( slot.getType() == Material.IRON_ORE )
					{
						if ( out != null && out.getType() != Material.IRON_INGOT )
						{
							player.sendMessage("§c[Error] アイテムを取り除いてください。");
							return;
						}
						
						ItemStack result = new ItemStack((Material.IRON_INGOT), ramount + 2);
						if ( amount > 1 )
						{
							slot.setAmount(amount - 1);
						}
						else {
							inventory.remove(Material.IRON_ORE);
						}
						inventory.setItem(24, result);
					}
					else if ( slot.getType() == Material.GOLD_ORE ) {
						if ( out != null && out.getType() != Material.GOLD_INGOT )
						{
							player.sendMessage("§c[Error] アイテムを取り除いてください。");
							return;
						}
						
						ItemStack result = new ItemStack((Material.GOLD_INGOT), ramount + 2);
						if ( amount > 1 )
						{
							slot.setAmount(amount - 1);
						}
						else {
							inventory.remove(Material.GOLD_ORE);
						}
						inventory.setItem(24, result);
					}
					else if ( slot.getType() == Material.MUSHROOM_SOUP ||
							slot.getType() == Material.GHAST_TEAR )
					{
						
						if ( out != null && out.getType() != Material.SUGAR )
						{
							player.sendMessage("§c[Error] アイテムを取り除いてください。");
							return;
						}
						
						if ( slot.containsEnchantment(Enchantment.DURABILITY) )
						{
							if ( slot.getItemMeta().getDisplayName().contains("コシヒカリ") )
							{
								ItemStack result = new ItemStack((Material.SUGAR), ramount + 9);
								ItemMeta resultmeta = result.getItemMeta();
								resultmeta.setDisplayName("§6§n米粉");
								resultmeta.addEnchant(Enchantment.DURABILITY, 2, false);
								resultmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
								result.setItemMeta(resultmeta);
								if ( amount > 1 )
								{
									slot.setAmount(amount - 1);
								}
								else {
									inventory.remove(Material.GHAST_TEAR);
									inventory.remove(Material.MUSHROOM_SOUP);
								}
								inventory.setItem(24, result);
							}
						}
					}
					else {
						player.sendMessage("§c[Error] " + "このアイテムを粉砕することは出来ません。");
						return;
					}
				}
				else if ( meta.getDisplayName().contains("CANCEL") )
				{
					player.closeInventory();
					player.playSound(player.getLocation(), Sound.NOTE_PIANO, 100, (float) 1.4);
					return;
				}
			}
		}
		else if ( inventory.getName().contains("BMC") ) {
			event.setCancelled(true);
			if ( item.getType() == Material.LAVA_BUCKET )
			{
				player.openInventory(MenuCommand.adminpanel);
			}
		}
	}
	
	@EventHandler
	public void onInventoryDragEvent(InventoryDragEvent event)
	{
		Inventory inventory = event.getInventory();
		
		if ( inventory.getName().contains("メニュー") )
		{
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event)
	{
		Inventory inventory = event.getInventory();
		Player player = (Player) event.getPlayer();
		if ( inventory.getName().contains("メニュー") )
		{
			if ( inventory.getItem(24) != null  )
			{
				ItemStack item = inventory.getItem(24);
				player.getWorld().dropItemNaturally(player.getLocation(), item);
			}
			
			if ( inventory.getItem(20) != null )
			{
				ItemStack item = inventory.getItem(20);
				player.getWorld().dropItemNaturally(player.getLocation(), item);
			}
		}
	}
}
