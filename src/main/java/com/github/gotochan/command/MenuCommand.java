package com.github.gotochan.command;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.github.gotochan.BMC;

public class MenuCommand extends SubCommandAbst implements Listener
{
	
	private static final String COMMAND_NAME = "menu";
	
	public ArrayList<ItemStack> ItemList = new ArrayList<ItemStack>();
	public ArrayList<ItemStack> apanel = new ArrayList<ItemStack>();
	
	@Override
	public String getCommandName()
	{
		return COMMAND_NAME;
	}
	
	public static final Inventory menu =
			Bukkit.createInventory(null, 27, "BMC Menu");
	private static final Inventory adminpanel =
			Bukkit.createInventory(null, 27, "Admin Console");
	
	@Override
	public boolean runCommand(CommandSender sender, String label, String[] args)
	{
		Player player = (Player) sender;
		openInventoryMenu(sender);
		player.sendMessage(BMC.prefix + "§6メニューを開きました。");
		return false;
	}
	
	public void openInventoryMenu(CommandSender sender)
	{
		Player player = (Player) sender;
		ItemStack admin = new ItemStack(Material.LAVA_BUCKET);
		ItemMeta adminmeta = admin.getItemMeta();
		adminmeta.setDisplayName("§a管理ツール");
		admin.setItemMeta(adminmeta);
		menu.setItem(7, new ItemStack(Material.ACACIA_DOOR));
		menu.setItem(8, admin);
		player.openInventory(menu);
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();
		Inventory inventory = event.getClickedInventory();
		if ( inventory != null )
		{
			if ( inventory.getName() == "BMC Menu")
			{
				ItemStack item = event.getCurrentItem();
				if ( item != null && ItemList.contains(item))
				{
					if ( item.getType() == Material.LAVA_BUCKET)
					{
						if ( player.hasPermission("bmc.adminpanel"))
						{
							player.closeInventory();
							player.playSound(player.getLocation(),
									Sound.NOTE_PIANO, 10, 1);
							player.openInventory(adminpanel);
						}
						else
						{
							player.sendMessage("あなたは開く権限がありません。");
						}
					}
				}
			}
			else if ( inventory.getName() == "Admin Console")
			{
				ItemStack item = event.getCurrentItem();
				if ( item != null && ItemList.contains(apanel))
				{
					ItemStack ntp = new ItemStack((Material.SKULL_ITEM), 1, (byte) 3);
					SkullMeta meta = (SkullMeta) ntp.getItemMeta();
					meta.setOwner("Taisuke_n");
					ntp.setItemMeta(meta);
					if( item == ntp )
					{
						
					}
				}
			}
		}
	}
}
