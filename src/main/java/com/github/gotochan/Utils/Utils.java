package com.github.gotochan.Utils;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Utils
{
	public static ItemStack addItemName(ItemStack item, String name)
	{
		if ( item == null )
		{
			return item;
		}
		
		ItemMeta meta = item.getItemMeta();
		if ( meta == null )
		{
			meta = Bukkit.getItemFactory().getItemMeta(item.getType());
		}
		meta.setDisplayName(name);
		item.setItemMeta(meta);
		return item;
	}
}
