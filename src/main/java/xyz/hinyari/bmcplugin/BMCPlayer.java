package xyz.hinyari.bmcplugin;

import xyz.hinyari.bmcplugin.Utils.BMCUtils;
import xyz.hinyari.bmcplugin.Utils.ActionBar;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class BMCPlayer
{
	private final BMCPlugin bmc;
	private final Player player;
	private final BMCScoreBoard bmcScoreBoard;

	public BMCPlayer(Player player, BMCPlugin bmcPlugin) {
		this.player = player;
		this.bmc = bmcPlugin;
		this.bmcScoreBoard = new BMCScoreBoard(this);
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
	    return this.bmcScoreBoard;
    }

    public void msg(String message) { player.sendMessage(BMCUtils.convert((bmc.PREFIX + message))); }

    public void noprefix(String message) { player.sendMessage(BMCUtils.convert(message));}

    public void broadcast(String message) {
        Bukkit.broadcastMessage(message);
    }

    public String getName() {
        return player.getName();
    }

    public UUID getUUID() { return player.getUniqueId(); }

	public ItemStack getItemInMainHand() { return player.getInventory().getItemInMainHand(); }

	public boolean hasPermission(String permission) { return player.hasPermission(permission); }

	public void errmsg(String message) { player.sendMessage(BMCUtils.convert((bmc.ERROR + message))); }

	public void errbar(String message) { new ActionBar(BMCUtils.convert(bmc.ERROR + message)).sendToPlayer(player);}

    public void barmsg(String message) { new ActionBar(BMCUtils.convert(bmc.PREFIX + message)).sendToPlayer(player);}

	public boolean noperm() { errmsg("権限がありません。"); return true; }

	public void openRankmenu() { player.openInventory(bmc.rankGUIMenu.getMainMenu(this)); }

	/**
	 * サウンドを再生します。
	 * @param sound 			サウンド名(Sound.example)
	 * @param volume 			float ボリューム
	 * @param pitch 			float ピッチ
	 */
	public void playSound(Sound sound, float volume, float pitch) { player.playSound(player.getLocation(), sound, volume, pitch); }

}