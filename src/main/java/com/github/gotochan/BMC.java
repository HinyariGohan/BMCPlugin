package com.github.gotochan;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class BMC extends JavaPlugin {


	public static String prefix = ChatColor.GREEN + "[BMCPlugin]" + ChatColor.WHITE;

	public void onEnable() {
		this.getLogger().info("BMCプラグインを開始しています。");
		Bukkit.getServer().getScoreboardManager().getMainScoreboard();
		getCommand("koshihikari").setExecutor( new BMCCommand() );
		getCommand("rank").setExecutor( new BMCRank() );
	}

	public void onDisable() {
		this.getLogger().info("BMCプラグインを終了しています。");
	}

	public void onLoad() {
		this.getLogger().info("BMCプラグインを読み込んでいます。");
	}

}
