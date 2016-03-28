package com.github.gotochan;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.gotochan.Koshihikari.KoshihikariCommand;
import com.github.gotochan.Koshihikari.KoshihikariEvent;
import com.github.gotochan.Rank.BMCRankCommand;
import com.github.gotochan.Rank.BMCRankUpCommand;




public class BMC
	extends JavaPlugin implements Listener {

/**
 * BMCオリジナルプラグイン メインクラス
 * @author Hinyari_Gohan
 */

	public static String prefix = ChatColor.GREEN + "[BMCPlugin] " + ChatColor.WHITE;


	@Override
	public void onEnable() {
		this.getLogger().info("BMCプラグインを開始しています。");
		Bukkit.getServer().getScoreboardManager().getMainScoreboard();
		getCommand("koshihikari").setExecutor( new KoshihikariCommand() );
		getCommand("rank").setExecutor( new BMCRankCommand() );
		getCommand("nannte").setExecutor( new KusoCommand() );
		getCommand("rankup").setExecutor( new BMCRankUpCommand() );
		getServer().getPluginManager().registerEvents(new KoshihikariEvent(), this);
		getServer().getPluginManager().registerEvents(new BMCEvent(), this);
	}

	@Override
	public void onDisable() {
		this.getLogger().info("BMCプラグインを終了しています。");
	}

	@Override
	public void onLoad() {
		this.getLogger().info("BMCプラグインを読み込んでいます。");
	}


	public static String inthegame = "ゲーム内で実行して下さい。";
	public static String lotargs = prefix + "引数は必要ありません。";
	public static String example = prefix + ChatColor.GOLD + "Example: ";
}
