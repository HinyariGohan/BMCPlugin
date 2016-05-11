package com.github.gotochan.kits;

import java.util.UUID;

import org.bukkit.entity.Player;

public interface BMCPlayer
{
	
	/**
	 * BMCPlayerに関する情報を返します。
	 * @return Player - bukkit.entity.Player
	 */
	public BMCPlayer getBMCPlayer(UUID id);
	
	/**
	 * Player型に変換します。
	 * @return Player - bukkit.entity.Player
	 */
	public Player getPlayer();
	
	/**
	 * プレイヤー名を返します。
	 * @return String - Player name.
	 */
	public String getBMCPlayerName();
	
	/**
	 * プレイヤーの現在のキットを返します。
	 * @return String - Player's Kit.
	 */
	public String getKit();
	
	public void setKit(String kitname);
	
	public String getRankName();
	
	public void setRank();
	
	public int getRankID();
	
	public UUID getUUID();
	
}
