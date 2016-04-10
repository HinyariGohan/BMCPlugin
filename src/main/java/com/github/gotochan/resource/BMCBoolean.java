package com.github.gotochan.resource;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BMCBoolean {
	public static String grappleName = "§6Grapple";
	
	public static boolean isKoshihikari(ItemStack item) {
		
		if ( item == null ) {
			return false;
		}
		
		ItemStack Kome = new ItemStack(Material.MUSHROOM_SOUP);
		
		String Komename = "§6コシヒカリ";
		ItemMeta im = item.getItemMeta();
		
		if ( item.getType() != Kome.getType() ) {
			return false;
		}
		
		if ( im.getDisplayName() != Komename) {
			return false;
		}
		
		Enchantment d = Enchantment.DURABILITY;
		boolean ench = im.hasEnchant(d);
		
		if ( im.hasEnchants() ) {
			int elevel = im.getEnchantLevel(d);
			if ( !(ench && elevel == 1) ) {
				return false;
			}
		}
		
		return true;
	}
	
	public static boolean isGrappleItem(ItemStack stack)
	{
		if(stack != null && stack.hasItemMeta() && stack.getItemMeta().hasDisplayName())
		{
			String name = stack.getItemMeta().getDisplayName();
			if(name.contains(grappleName))
			{
				return true;
			}
		}
		return false;
	}
}
