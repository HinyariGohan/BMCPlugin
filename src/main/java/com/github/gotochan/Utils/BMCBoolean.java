package com.github.gotochan.Utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BMCBoolean {

	public String grappleName = "§6Grapple";

	public boolean isKoshihikari(ItemStack item) {

		if ( item == null )
			return false;

		ItemStack Kome = new ItemStack(Material.MUSHROOM_SOUP);

		String Komename = "§6コシヒカリ";
		ItemMeta im = item.getItemMeta();

		if ( item.getType() != Kome.getType() )
			return false;

		if ( im.getDisplayName() != Komename)
			return false;

		Enchantment d = Enchantment.DURABILITY;
		boolean ench = im.hasEnchant(d);

		if ( im.hasEnchants() ) {
			int elevel = im.getEnchantLevel(d);
			if ( !(ench && elevel == 1) )
				return false;
		}

		return true;
	}

	public boolean isGrappleItem(ItemStack stack)
	{
		if(stack != null && stack.hasItemMeta() && stack.getItemMeta().hasDisplayName())
		{
			String name = stack.getItemMeta().getDisplayName();
			if(name.contains(grappleName))
				return true;
		}
		return false;
	}

	public boolean isTool(Material item) {
		if ( item == Material.WOOD_PICKAXE ||
				item == Material.WOOD_SPADE ||
				item == Material.WOOD_AXE ||
				item == Material.STONE_PICKAXE ||
				item == Material.STONE_AXE ||
				item == Material.STONE_SPADE ||
				item == Material.IRON_PICKAXE ||
				item == Material.IRON_AXE ||
				item == Material.IRON_SPADE ||
				item == Material.GOLD_AXE ||
				item == Material.GOLD_PICKAXE ||
				item == Material.GOLD_SPADE ||
				item == Material.DIAMOND_AXE ||
				item == Material.DIAMOND_PICKAXE ||
				item == Material.DIAMOND_SPADE )
			return true;
		return false;
	}

	public boolean isBlock(Material block) {
		if ( block == Material.IRON_ORE ||
				block == Material.GOLD_ORE ||
				block == Material.SAND ||
				block == Material.COBBLESTONE ||
				block == Material.NETHERRACK ||
				block == Material.CACTUS ||
				block == Material.LOG ||
				block == Material.LOG_2 ||
				block == Material.SPONGE )
			return true;
		return false;
	}
}
