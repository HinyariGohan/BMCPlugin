package com.github.gotochan.command;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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
	public static final Inventory adminpanel =
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
}
