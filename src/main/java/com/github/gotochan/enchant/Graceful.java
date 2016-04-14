package com.github.gotochan.enchant;

import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.EntityEquipment;

public class Graceful implements Listener
{
	@EventHandler(priority=EventPriority.MONITOR)
	public void ProtectDamageEnchant(EntityDamageEvent event)
	{
		if ( event.getEntityType() != EntityType.PLAYER)
		{
			return;
		}
		
		DamageCause damage = event.getCause();
		Player player = (Player) event.getEntity();
		EntityEquipment armor = player.getEquipment();
		
		if ( armor == null )
		{
			player.sendMessage("armor null");
			return;
		}
		
		if ( damage == DamageCause.LAVA
				|| damage == DamageCause.FIRE
				|| damage == DamageCause.FIRE_TICK )
		{
			if ( armor.getChestplate() == null)
			{
				player.sendMessage("chestplate null");
				return;
			}
			
			if ( !(armor.getChestplate().hasItemMeta()) )
			{
				player.sendMessage("dont hasitemmeta");
				return;
			}
			
			Enchantment fire = Enchantment.PROTECTION_FIRE;
			if ( armor.getChestplate().containsEnchantment(fire))
			{
				int level = armor.getChestplate().getEnchantmentLevel(fire);
				if ( level == 10 )
				{
					event.setCancelled(true);
				}
			}
		}
		else if ( damage == DamageCause.FALL ) {
			if ( armor.getBoots() == null )
			{
				return;
			}
			
			if ( !(armor.getBoots().hasItemMeta()) )
			{
				return;
			}
			
			Enchantment fall = Enchantment.PROTECTION_FALL;
			if ( armor.getBoots().containsEnchantment(fall))
			{
				int level = armor.getBoots().getEnchantmentLevel(fall);
				if ( level == 10 )
				{
					event.setCancelled(true);
					player.sendMessage("§6Landing success!");
					player.playSound(player.getLocation(), Sound.BAT_TAKEOFF, 1L, (float) 1.5);
				}
			}
		}
	}
}
