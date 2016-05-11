package com.github.gotochan.kits;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class KitSystemAbst
{
	/**
	 * KitのHashMap<br>
	 * UUIDとStringを関連付け</br>
	 */
	public static Map<UUID, String> kit = new HashMap<UUID, String>();
	
	/**
	 * キットの名前を取得します。
	 * @return String - Kit_Name
	 */
	public abstract String getKitName();
	
	
	/**
	 * そのキットを選択できるかどうかを判定します。
	 * @param player - BMCPlayer
	 * @return 可不可
	 */
	public static boolean canSelect(BMCPlayer player)
	{
		String kit = player.getKit();
		if ( kit.equals("") )
		{
			return true;
		}
		return false;
	}
	
	public static String canSelect_String(BMCPlayer player)
	{
		if ( canSelect(player) == true )
		{
			return "§a解除済み";
		}
		else {
			return "§cランクを上げる必要があります!";
		}
	}
}
