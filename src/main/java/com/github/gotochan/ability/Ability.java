package com.github.gotochan.ability;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Player;

public class Ability
{
	public static HashMap<Player, String> KIT_NOW = new HashMap<Player, String>();
	
	public static ArrayList<String> KIT_LIST = new ArrayList<String>();
	
	public static void kitlist() {
		KIT_LIST.add("acrobat");
		KIT_LIST.add("scout");
	}
	
	public static boolean isnullKit (Player player) {
		if ( KIT_NOW.get(player) == null ) {
			return true;
		}
		return false;
	}
	
	public static boolean isthiskit(Player player, String KIT_NAME) {
		if ( KIT_NOW.get(player) == KIT_NAME ) {
			return true;
		}
		return false;
	}
}
