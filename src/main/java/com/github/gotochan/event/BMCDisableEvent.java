package com.github.gotochan.event;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BMCDisableEvent implements Listener
{
	
	public static List<String> blocks = new ArrayList<String>();
	
	@EventHandler
	public void BlockPlaceBlocker(BlockPlaceEvent event)
	{
		Player player = event.getPlayer();
		Block block = event.getBlock();
		ItemStack item = player.getItemInHand();
		
		if ( !(item.hasItemMeta()) )
		{
			return;
		}
		
		ItemMeta meta = item.getItemMeta();
		
		//炭素の塊キャンセル
		if ( block.getType() == Material.COAL_BLOCK )
		{
			if ( meta.getDisplayName().contains("炭素の塊") )
			{
				event.setCancelled(true);
			}
		}
		
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event)
	{
		Inventory inventory = event.getClickedInventory();
		Player player = (Player) event.getWhoClicked();
		
		if ( inventory.getName().contains("メニュー") )
		{
			//とりあえずキャンセルからのー
			event.setCancelled(true);
			
			ItemStack item = event.getCurrentItem();
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
					player.sendMessage("You clicked green!");
				}
			}
		}
	}
}
