package com.github.gotochan.kit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import com.github.gotochan.BMC;


/**
 * @todo キットを変更する際に、 SoulBound アイテムを削除する必要がある。
 *
 */

public abstract class BMCKit implements Listener, Comparable<BMCKit>
{
	
	public static final BMCKit RunnerInstance = new RunnerKit();
	
	static
	{
		Bukkit.getPluginManager().registerEvents(RunnerInstance, BMC.getInstance());
		RunnerInstance.Initialize();
	}
	
	protected final ChatColor aqua = ChatColor.AQUA;
	
	public abstract void Initialize();
	
	public abstract String getName();
	
	public abstract IconPackage getIconPackage();
	
	public abstract boolean canSelect(Player p_player);
	
	public abstract void onPlayerSpawn(Player p_player);
	
	public abstract void cleanup(Player p_player);
	
	@Override
	public int compareTo(BMCKit kit)
	{
		return getName().compareTo(kit.getName());
	}
	
}