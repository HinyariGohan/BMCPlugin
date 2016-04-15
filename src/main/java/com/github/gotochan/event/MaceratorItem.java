package com.github.gotochan.event;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MaceratorItem implements Listener
{
	@EventHandler
	public void onRightClick(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		
		if ( event.getAction() == Action.RIGHT_CLICK_AIR
				|| event.getAction() == Action.RIGHT_CLICK_BLOCK )
		{
			ItemStack item = player.getItemInHand();
			if ( item == null )
			{
				return;
			}
			
			if ( !(item.hasItemMeta()) )
			{
				return;
			}
			
			ItemMeta meta = item.getItemMeta();
			
			if ( item.getType() != Material.FLINT )
			{
				return;
			}
			
			if ( meta.getDisplayName().contains("粉砕具") )
			{
				openMacerator(player);
			}
		}
	}
	
	public void openMacerator(Player player)
	{
		Inventory macerator = Bukkit.createInventory(player, 45);
		
		ItemStack bluepanel = new ItemStack((Material.STAINED_GLASS_PANE), 1, (byte)3);
		macerator.setItem(11, bluepanel);
		macerator.setItem(12, bluepanel);
		macerator.setItem(13, bluepanel);
		macerator.setItem(20, bluepanel);
		macerator.setItem(22, bluepanel);
		macerator.setItem(29, bluepanel);
		macerator.setItem(30, bluepanel);
		macerator.setItem(31, bluepanel);
		player.openInventory(macerator);
	}
}
