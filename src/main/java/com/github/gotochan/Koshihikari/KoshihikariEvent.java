package com.github.gotochan.Koshihikari;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.github.gotochan.BMC;

public class KoshihikariEvent
	implements Listener {

/**
 * イベントからのコシヒカリを食べるモーション
 * @author Hinyari_Gohan
 * @param e
 * @param ItemStack
 * @param String
 */



	@EventHandler
	public void onEat(PlayerItemConsumeEvent e) {
		Player player = e.getPlayer();
		String playername = player.getDisplayName();
		ItemStack is = player.getItemInHand();
		if(is.getType().equals(Material.MUSHROOM_SOUP)) { //耐久力1
		e.setCancelled(true);
		player.setFoodLevel(40);
		player.setExhaustion(40);
		player.setHealth(20);
		Bukkit.broadcastMessage(BMC.prefix + playername + "さんが コシヒカリ を食べました。");
		player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 1200 ,4));
		player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 1200 ,1));
		player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 1200 ,1));
		player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 1200 ,2));
        player.getWorld().playSound(player.getLocation(), Sound.EAT, 1, 1);
        player.setItemInHand(null);
        player.updateInventory();
				}
			}
}
