package com.github.gotochan.kit;

import com.github.gotochan.BMCPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;


/**
 * @todo キットを変更する際に、 SoulBound アイテムを削除する必要がある。
 *
 */

public abstract class BMCKit implements Listener, Comparable<BMCKit>
{
	private static BMCPlugin bmc;

	public BMCKit(BMCPlugin bmc)
	{
		BMCKit.bmc = bmc;
	}

	public static final BMCKit RunnerInstance = new RunnerKit(bmc);

	public void init()
	{
		Bukkit.getPluginManager().registerEvents(RunnerInstance, bmc.getInstance());
		RunnerInstance.Initialize();
	}

	protected final ChatColor aqua = ChatColor.AQUA;

	public abstract void Initialize();

	public abstract String getName();

	public abstract IconPackage getIconPackage();

	public abstract boolean canSelect(Player p_player);

	public abstract void onPlayerSpawn(Player p_player);

	public abstract void cleanup(Player p_player);

	public int compareTo(BMCKit kit)
	{
		return getName().compareTo(kit.getName());
	}

}