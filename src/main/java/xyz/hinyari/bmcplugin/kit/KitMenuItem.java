package xyz.hinyari.bmcplugin.kit;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import xyz.hinyari.bmcplugin.BMCPlayer;
import xyz.hinyari.bmcplugin.Utils.MenuItem;


public class KitMenuItem extends MenuItem
{
	final BMCKit kit;
	
	public KitMenuItem(BMCKit kit)
	{
		super(kit.getName(), kit.getIconPackage().Stack, kit.getIconPackage().Lore);
		this.kit = kit;
	}
	
	@Override
	public void onItemClick(ItemClickEvent event)
	{
		Player player = event.getPlayer();
		if ( player != null )
		{
			event.setWillClose(true);
			BMCPlayer bmcPlayer = BMCPlayer.getPlayer(player.getUniqueId());
			if ( ( this.kit != null ) && ( bmcPlayer != null ))
			{
				if ( this.kit.canSelect(player) )
				{
					if ( bmcPlayer.getKit() != null )
					{
						bmcPlayer.getKit().cleanup(player);
					}
					
					/* 削除処理 */
					ItemStack[] items = player.getInventory().getContents();
					for ( int i = 0; i < items.length ; i++ )
					{
						ItemStack item = items[i];
						if ( item.hasItemMeta() && item.getItemMeta().hasLore() )
						{
							if ( item.getItemMeta().getLore().contains("Soulbound") )
							{
								player.getInventory().remove(item);
								player.updateInventory();
							}
						}
					}
					
					bmcPlayer.setKit(this.kit);
					bmcPlayer.sendMessage(ChatColor.DARK_PURPLE + this.kit.getName() +
							"を選択しました。");
					
					
				}
			}
		}
	}
	
	
}
