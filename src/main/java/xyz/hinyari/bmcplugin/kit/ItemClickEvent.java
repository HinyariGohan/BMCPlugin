package xyz.hinyari.bmcplugin.kit;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class ItemClickEvent extends Event
{
	private Player player;
	private boolean goBack = false;
	private boolean close = false;
	private boolean update = false;
	private ItemStack stack;

	public ItemClickEvent(Player player, ItemStack stack)
	{
		this.player = player;
		this.stack  = stack;
	}

	public Player getPlayer()
	{
		return this.player;
	}

	public ItemStack getClickedItem()
	{
		return this.stack;
	}

	public boolean willGoBack()
	{
		return this.goBack;
	}

	public void setWillGoBack(boolean goBack)
	{
		this.goBack = goBack;
		if ( goBack )
		{
			this.close = false;
			this.update = false;
		}
	}

	public boolean willClose()
	{
		return this.close;
	}

	public void setWillClose(boolean close)
	{
		this.close = close;
		if ( close )
		{
			this.goBack = false;
			this.update = false;
		}
	}

	public boolean willUpdate()
	{
		return this.update;
	}

	public void setWillUpdate(boolean update)
	{
		this.update = update;
		if ( update )
		{
			this.goBack = false;
			this.close = false;
		}
	}

	@Override
	public HandlerList getHandlers() {
		return null;
	}

}
