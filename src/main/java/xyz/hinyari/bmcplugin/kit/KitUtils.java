package xyz.hinyari.bmcplugin.kit;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;


public class KitUtils
{
	public static ItemStack addSoulBound(ItemStack item)
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
		
		List<String> lore = meta.getLore();
		if ( lore == null )
		{
			lore = new ArrayList<String>();
		}
		lore.add(ChatColor.GOLD + "Soulbound");
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack addEnchant(ItemStack s, Enchantment m, int level)
	{
		s.addUnsafeEnchantment(m, level);
		return s;
	}
	
}
