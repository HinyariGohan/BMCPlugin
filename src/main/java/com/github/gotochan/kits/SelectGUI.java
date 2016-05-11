package com.github.gotochan.kits;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

public class SelectGUI implements Listener
{
	public static List<String> lore = new ArrayList<String>();
	
	public static void openGUI(BMCPlayer player)
	{
		String lore_canselect = KitSystemAbst.canSelect_String(player);
		Player bukkitplayer = player.getPlayer();
		
		if ( lore.isEmpty() )
		{
			lore.add( lore_canselect );
		} else {
			lore.set(0, lore_canselect );
		}
		
		Inventory GUI = Bukkit.createInventory(bukkitplayer, 9, "BMC Rank Kits");
		
		GUI.addItem();
	}
	
	
}
