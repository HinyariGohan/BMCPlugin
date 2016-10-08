package xyz.hinyari.bmcplugin.Utils;

import java.util.ArrayList;
import java.util.List;

import xyz.hinyari.bmcplugin.kit.ItemClickEvent;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MenuItem
{
	private String displayName;
	private ItemStack icon;
	private List<String> lore;
	
	public MenuItem(String displayName, ItemStack icon, String... lore)
	{
		this.displayName = displayName;
		this.icon = icon;
		this.lore = new ArrayList<String>();
		String[] arrayOfString;
		int y = (arrayOfString = lore).length;
		for ( int i = 0; i < y; i++ )
		{
			String str = arrayOfString[i];
			this.lore.add(str);
		}
	}
	
	public String getDisplayName()
	{
		return this.displayName;
	}
	
	public ItemStack getIcon()
	{
		return this.icon;
	}
	
	public void setIcon(ItemStack newIcon)
	{
		this.icon = newIcon;
	}
	
	public void setDisplayName(String name)
	{
		this.displayName = name;
	}
	
	public List<String> getLore()
	{
		return this.lore;
	}
	
	public ItemStack getFinalIcon(Player player)
	{
		return setNameAndLore(getIcon().clone(), getDisplayName(), getLore());
	}
	
	public void onItemClick (ItemClickEvent event) {}
	
	public static ItemStack setNameAndLore(
			ItemStack itemStack,
			String displayName,
			List<String> lore)
	{
		ItemMeta meta = itemStack.getItemMeta();
		meta.setDisplayName(displayName);
		meta.setLore(lore);
		itemStack.setItemMeta(meta);
		
		return itemStack;
	}
	
}
