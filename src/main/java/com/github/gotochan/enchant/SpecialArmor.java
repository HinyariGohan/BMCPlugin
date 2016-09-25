package com.github.gotochan.enchant;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class SpecialArmor extends BukkitRunnable implements Listener
{
	/*
		int timer;
		int unbreaking;
	 */

	/*
	@EventHandler(priority=EventPriority.MONITOR)
	public void ProtectDamageEnchant(EntityDamageEvent event)
	{
		if ( event.getEntityType() != EntityType.PLAYER)
			return;

		DamageCause damage = event.getCause();
		Player player = (Player) event.getEntity();
		final EntityEquipment armor = player.getEquipment();

		if ( armor == null )
			return;

		if ( damage == DamageCause.LAVA
				|| damage == DamageCause.FIRE
				|| damage == DamageCause.FIRE_TICK )
		{
			ItemStack chestplace = armor.getChestplate();

			if ( chestplace == null)
				return;

			if ( !(chestplace.hasItemMeta()) )
				return;

			Enchantment fire = Enchantment.PROTECTION_FIRE;
			if ( chestplace.containsEnchantment(fire))
			{
				int level = chestplace.getEnchantmentLevel(fire);
				int durability = chestplace.getDurability();
				int maxdurability = chestplace.getType().getMaxDurability();


				if ( armor.getChestplate().containsEnchantment(Enchantment.DURABILITY) )
				{
					unbreaking = armor.getChestplate().getEnchantmentLevel
							(Enchantment.DURABILITY);
				}

				if ( level == 10 )
				{
					timer = timer + 1;
					if ( timer == 30 )
					{
						timer = 0;
						player.playSound(player.getLocation(),
								Sound.BLOCK_ANVIL_LAND, 1, (float) 2.0);

						if ( durability != maxdurability )
						{
							chestplace.setDurability((short) (durability + 1));
						}
						else {
							player.getInventory().setChestplate(new ItemStack(Material.AIR));
						}
					}
					event.setCancelled(true);
				}
			}
		}
		else if ( damage == DamageCause.FALL ) {
			ItemStack boots = armor.getBoots();
			if ( boots == null )
				return;

			if ( !(boots.hasItemMeta()) )
				return;

			Enchantment fall = Enchantment.PROTECTION_FALL;
			if ( boots.containsEnchantment(fall))
			{
				int level = boots.getEnchantmentLevel(fall);
				int durability = boots.getDurability();
				int maxdurability = boots.getType().getMaxDurability();

				if ( boots.containsEnchantment(Enchantment.DURABILITY) )
				{
					unbreaking = boots.getEnchantmentLevel
							(Enchantment.DURABILITY);
				}

				if ( level == 10 )
				{
					event.setCancelled(true);
					player.sendMessage("§6Landing success!");
					player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1L, (float) 2.0);

					if ( durability != maxdurability )
					{
						boots.setDurability((short) (boots.getDurability() + 1));
					}
					else {
						player.getInventory().setBoots(new ItemStack(Material.AIR));
					}
				}
			}
		}

	}
	 */

	public void run() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			ItemStack[] armor = player.getInventory().getArmorContents();
			for (ItemStack item : armor) {
				if (item==null) {
					continue;
				}
				if (item.getType().equals(Material.DIAMOND_CHESTPLATE) &&
						item.containsEnchantment(Enchantment.PROTECTION_FIRE)) {
					if (item.getItemMeta().getEnchantLevel(Enchantment.PROTECTION_FIRE)==10) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 40, 1), true);
					}
				}
			}
		}
	}
}
