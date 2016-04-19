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
		Inventory macerator = Bukkit.createInventory(player, 54, "粉砕メニュー");
		
		ItemStack bluepanel = new ItemStack((Material.STAINED_GLASS_PANE), 1, (byte)3);
		ItemStack okButton = new ItemStack((Material.STAINED_CLAY), 1, (byte)5);
		ItemStack cancelButton = new ItemStack((Material.STAINED_CLAY), 1, (byte)14);
		ItemStack yellowpanel = new ItemStack((Material.STAINED_GLASS_PANE), 1, (byte)4);
		
		ItemMeta OKB = okButton.getItemMeta();
		ItemMeta CANCELB = cancelButton.getItemMeta();
		ItemMeta bluepanelmeta = bluepanel.getItemMeta();
		ItemMeta yellowpanelmeta = yellowpanel.getItemMeta();
		
		bluepanelmeta.setDisplayName(" ");
		CANCELB.setDisplayName("§c§lCANCEL");
		OKB.setDisplayName("§a§lOK");
		yellowpanelmeta.setDisplayName(" ");
		
		bluepanel.setItemMeta(bluepanelmeta);
		okButton.setItemMeta(OKB);
		cancelButton.setItemMeta(CANCELB);
		yellowpanel.setItemMeta(yellowpanelmeta);
		
		macerator.setItem(10, bluepanel);
		macerator.setItem(11, bluepanel);
		macerator.setItem(12, bluepanel);
		macerator.setItem(19, bluepanel);
		macerator.setItem(21, bluepanel);
		macerator.setItem(28, bluepanel);
		macerator.setItem(29, bluepanel);
		macerator.setItem(30, bluepanel);
		
		//48
		
		macerator.setItem(14, yellowpanel);
		macerator.setItem(15, yellowpanel);
		macerator.setItem(16, yellowpanel);
		macerator.setItem(23, yellowpanel);
		macerator.setItem(25, yellowpanel);
		macerator.setItem(32, yellowpanel);
		macerator.setItem(33, yellowpanel);
		macerator.setItem(34, yellowpanel);
		
		macerator.setItem(48, okButton);
		
		macerator.setItem(50, cancelButton);
		
		player.openInventory(macerator);
	}
}
