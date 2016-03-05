package com.github.gotochan.Koshihikari;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class KoshihikariEvent
	implements Listener {

/**
 * イベントからのコシヒカリを食べるモーション
 * @author Attaka_Gohan
 * @param e
 * @param ItemStack
 * @param String
 */

	@EventHandler
	public void onEat(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		ItemStack is = player.getItemInHand();
		String isname = player.getDisplayName();

		if(!(player.isSneaking())) {
			return; //スニークしてなかったらreturnをする。
		}
		if(!(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
			return; //右クリックイベントでなければreturnをする。
		}

		if(!(is.getType().equals(Material.MUSHROOM_SOUP) //マッシュルームスープで
				|| isname == "コシヒカリ")) { //名前がコシヒカリの場合
			return; //コシヒカリじゃなかったらreturnをする。
		}

		e.setCancelled(true);
		player.sendMessage("You eat " + ChatColor.GOLD + "コシヒカリ " + ChatColor.RESET + ",");

		int heal = 0;
		if(is.getType().equals(Material.MUSHROOM_SOUP) //マッシュルームスープで
				|| isname == "コシヒカリ") {
			heal = 6;
		}

		if (player.getHealth() + heal >= player.getMaxHealth()) {
            player.setHealth(player.getMaxHealth());
        } else {
            player.setHealth(player.getHealth() + heal);
        }

        if (player.getFoodLevel() + heal >= 20) {
            player.setFoodLevel(20);
        } else {
            player.setFoodLevel(player.getFoodLevel() + heal);
        }

        if (player.getExhaustion() + heal >= 20) {
        	player.setExhaustion(20);
        } else {
        	player.setExhaustion(player.getExhaustion() + heal);
        }


        player.getWorld().playSound(player.getLocation(), Sound.EAT, 1, 1);
        player.getInventory().remove(new ItemStack(Material.MUSHROOM_SOUP, 1));
        player.updateInventory();
        Bukkit.broadcastMessage("COMING REMOVE!");
	}


}
