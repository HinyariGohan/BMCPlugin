package com.github.gotochan;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Score;

import com.github.gotochan.command.RankCommand;
import com.github.gotochan.kit.BMCKit;

public class BMCPlayer
{
	protected static final Map<UUID, BMCPlayer> players = new HashMap<>();
	private final UUID ID;
	private final String name;
	private BMCKit kit;
	protected static int rankID = '1';
	protected static Score rankScore;
	
	public BMCPlayer(UUID ID, String Name)
	{
		this.ID = ID;
		this.name = Name;
		this.kit = BMCKit.RunnerInstance;
	}
	
	
	public static BMCPlayer getPlayer(UUID id)
	{
		return players.get(id);
	}
	
	public static Map<UUID, BMCPlayer> getPlayers()
	{
		return Collections.unmodifiableMap(players);
	}
	
	
	
	public BMCKit getKit()
	{
		return this.kit;
	}
	
	public void setKit(BMCKit kit)
	{
		if ( kit != null )
		{
			this.kit = kit;
		}
	}
	
	public Player getPlayer()
	{
		return Bukkit.getPlayer(this.ID);
	}
	
	public Score getRankScore()
	{
		return BMCPlayer.rankScore;
	}
	
	public void sendMessage(String message)
	{
		Player player = Bukkit.getPlayer(this.ID);
		if ( player != null )
		{
			player.sendMessage(message);
		}
	}
	
	public int getRankID()
	{
		return BMCPlayer.rankID;
	}
	
	public String getRankName()
	{
		return RankCommand.getRankName(rankID);
	}
	
	public String getName()
	{
		return this.name;
	}
	
}