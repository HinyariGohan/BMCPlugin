package xyz.hinyari.bmcplugin.command;

import java.util.ArrayList;

import xyz.hinyari.bmcplugin.BMCPlayer;
import xyz.hinyari.bmcplugin.BMCPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MenuCommand extends SubCommandAbst implements Listener
{
	
	private static final String COMMAND_NAME = "menu";
	
	public ArrayList<ItemStack> ItemList = new ArrayList<ItemStack>();
	public ArrayList<ItemStack> apanel = new ArrayList<ItemStack>();
	
	private BMCPlugin bmc;
	
	public MenuCommand(BMCPlugin bmc)
	{
		this.bmc = bmc;
	}
	
	@Override
	public String getCommandName()
	{
		return COMMAND_NAME;
	}
	
	public static final Inventory menu =
			Bukkit.createInventory(null, 27, "BMCPlugin Menu");
	public static final Inventory adminpanel =
			Bukkit.createInventory(null, 27, "Admin Console");

	@Override
	public boolean runCommand(BMCPlayer player, String label, String[] args) {
		return false;
	}

	private void openInventoryMenu(Player player)
	{
		ItemStack admin = new ItemStack(Material.LAVA_BUCKET);
		ItemMeta adminmeta = admin.getItemMeta();
		adminmeta.setDisplayName("§a管理ツール");
		admin.setItemMeta(adminmeta);
		menu.setItem(7, new ItemStack(Material.ACACIA_DOOR));
		menu.setItem(8, admin);
		player.openInventory(menu);
	}
}
