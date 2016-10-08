package xyz.hinyari.bmcplugin.kit;

import org.bukkit.inventory.ItemStack;

public class IconPackage
{
	public final ItemStack Stack;
	public final String[] Lore;
	
	public IconPackage(ItemStack stack, String[] lore)
	{
		this.Stack = stack;
		this.Lore = lore;
	}
	
}
