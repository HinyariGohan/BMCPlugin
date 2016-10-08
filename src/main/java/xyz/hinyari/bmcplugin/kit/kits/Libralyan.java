package xyz.hinyari.bmcplugin.kit.kits;

import xyz.hinyari.bmcplugin.kit.BMCKit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import xyz.hinyari.bmcplugin.BMCPlugin;
import xyz.hinyari.bmcplugin.kit.IconPackage;
import xyz.hinyari.bmcplugin.kit.KitUtils;

public class Libralyan extends BMCKit
{
	
	public Libralyan(BMCPlugin bmc)
	{
		super(bmc);
	}
	
	private static final ItemStack Book = KitUtils.addSoulBound(new ItemStack(Material.BOOK));
	
	@Override
	public void Initialize()
	{
	}
	
	@Override
	public String getName()
	{
		return "Libralyan";
	}
	
	@Override
	public IconPackage getIconPackage()
	{
		return new IconPackage(new ItemStack(Material.BOOK),
				new String[] {
						""
		});
	}
	
	@Override
	public boolean canSelect(Player p_player)
	{
		return true;
	}
	
	@Override
	public void onPlayerSpawn(Player p_player)
	{
		p_player.getInventory().addItem(new ItemStack[] { Book });
	}
	
	@Override
	public void cleanup(Player p_player)
	{
	}
	
}
