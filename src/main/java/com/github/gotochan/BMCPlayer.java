package com.github.gotochan;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class BMCPlayer
{
	private final BMCPlugin bmc;
	private final Player player;
	private final BMCScoreBoard rank;

	public BMCPlayer(Player player) {
		this.player = player;
		this.bmc = BMCPlugin.getInstance();
		this.rank = new BMCScoreBoard(this);
	}

	private void init() {
	}

	public Player getPlayer() {
		return this.player;
	}

	public BMCPlugin getPlugin() {
		return this.bmc;
	}

	public BMCScoreBoard getScoreboard() {
	    return this.rank;
    }

    public void msg(String message) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', (bmc.PREFIX + "&r" + message)));
    }

    public void noprefix(String message) { player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));}

    public void broadcast(String message) {
        Bukkit.broadcastMessage(message);
    }

    public String getName() {
        return player.getName();
    }

    public UUID getUUID() { return player.getUniqueId(); }

	public ItemStack getItemInMainHand() { return player.getInventory().getItemInMainHand(); }

	public boolean hasPermission(String permission) { return player.hasPermission(permission); }

	public void errmsg(String message) { player.sendMessage(ChatColor.translateAlternateColorCodes('&', (bmc.ERROR + message))); }

	public boolean noperm() { errmsg("権限がありません。"); return true; }

}