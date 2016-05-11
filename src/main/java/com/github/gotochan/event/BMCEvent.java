package com.github.gotochan.event;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityCreatePortalEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.github.gotochan.BMC;
import com.github.gotochan.ntp.FreezeCommand;
import com.github.gotochan.ntp.KickCommand;
import com.github.gotochan.resource.BMCBoolean;

/**
 * BMCサーバー メインイベントキャッチクラス
 * @author Hinyari_Gohan
 *
 */
public class BMCEvent implements Listener {
	
	static int LIMIT = Integer.MAX_VALUE;
	
	@EventHandler
	public void onEntityCreatePortalEvent(EntityCreatePortalEvent e) {
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		String pn = player.getName();
		if ( pn.equalsIgnoreCase("Taisuke_n") ) {
			if ( KickCommand.value == "true" ) {
				player.kickPlayer("NTPキックモードが有効になっているため、サーバーにログインする事は出来ません。");
				event.setJoinMessage(null);
			} else {
				FreezeCommand.isFreeze = false;
			}
		}
	}
	
	@EventHandler
	public void onEat(PlayerItemConsumeEvent event) {
		Player player = event.getPlayer();
		ItemStack EatItem = player.getItemInHand();
		ItemStack zombeef = new ItemStack(Material.ROTTEN_FLESH);
		
		if ( EatItem.getType() == zombeef.getType()) {
			if ( player.isSneaking() )
			{
				if( EatItem.getAmount() >= 4) {
					event.setCancelled(true);
					player.getItemInHand().setAmount(EatItem.getAmount() - 4);
					player.setFoodLevel(40);
					player.setExhaustion(40);
					player.sendMessage(BMC.prefix + "ゾンビーフを4つ消費して全回復したぞ!");
				} else {
					player.sendMessage(BMC.prefix + "ゾンビーフを4つ以上用意してくれ!");
				}
			}
		} else {
			String playername = player.getDisplayName();
			ItemStack is = player.getItemInHand();
			World world = player.getWorld();
			Location loc = player.getLocation();
			if( BMCBoolean.isKoshihikari(is) ) { //耐久力1
				event.setCancelled(true);
				player.setFoodLevel(40);
				player.setExhaustion(40);
				player.setHealth(20);
				Bukkit.broadcastMessage(BMC.prefix + playername + " さんが コシヒカリ を食べました。");
				player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 12000 ,4));
				player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 12000 ,1));
				player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 12000 ,1));
				player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 12000 ,2));
				world.playSound(player.getLocation(), Sound.EAT, 1, 1);
				world.playSound(loc, Sound.PORTAL_TRAVEL, 1, 2);
				player.setItemInHand(null);
			}
		}
	}
	
	
	@EventHandler
	public void onCreatureSpawnEvent(CreatureSpawnEvent event) {
		LivingEntity entity = event.getEntity();
		String name = entity.getCustomName();
		if( event.getEntityType() == EntityType.ENDER_DRAGON  ) {
			if ( name == null ) {
				return;
			}
			
			else if ( name == "§b中級§rドラゴン" ) {
				entity.setMaxHealth(500);
				entity.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, LIMIT, 1));
				entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, LIMIT, 2));
			} else if ( name == "§c上級§rドラゴン" ) {
				entity.setMaxHealth(800);
				entity.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, LIMIT, 3));
				entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, LIMIT, 3));
			}
		} else if ( event.getEntityType() == EntityType.WITHER && (!(event.getLocation().getWorld().equals("world_the_end")))) {
			ItemStack item1 = new ItemStack((Material.SKULL_ITEM), 3, (byte)1 );
			ItemStack item2 = new ItemStack((Material.SOUL_SAND), 4);
			
			event.setCancelled(true);
			
			event.getLocation().getWorld().dropItem(event.getLocation(), item1);
			event.getLocation().getWorld().dropItem(event.getLocation(), item2);
			return;
		}
	}
	
	@EventHandler
	public void onEntityDeathEvent(EntityDeathEvent event) {
		LivingEntity entity = event.getEntity();
		String name = entity.getCustomName();
		ItemStack dropItem = new ItemStack((Material.DRAGON_EGG), 1);
		if ( entity instanceof EnderDragon ) {
			if ( name != null ) {
				if ( name == "§b中級§rドラゴン" ) {
					entity.getWorld().dropItem(entity.getLocation(), dropItem);
				} else if ( name == "§c上級§rドラゴン" ) {
					entity.getWorld().dropItem(entity.getLocation(), dropItem);
				}
			}
		}
	}
	
	@EventHandler
	public void onCommandProsess(PlayerCommandPreprocessEvent event)
	{
		Player player = event.getPlayer();
		for ( Player player2 : Bukkit.getOnlinePlayers() )
		{
			if ( player2.hasPermission("bmc.monitor") )
			{
				player2.sendMessage("§7[BMC] " + player.getName() + ":"
						+ event.getMessage());
				if ( event.getMessage().contains("/op") )
				{
					if ( !(player.isOp() ))
					{
						player2.sendMessage("§c[BMC] " + player.getName() +
								" さんがOPコマンドを試行しています！");
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerMoveEvent(PlayerMoveEvent event)
	{
		if ( event.getPlayer().getName() != "Taisuke_n")
		{
			return;
		}
		
		if ( FreezeCommand.isFreeze == false )
		{
			return;
		}
		
		event.setCancelled(true);
	}
	
	@EventHandler
	public void BlockPlaceBlocker(BlockPlaceEvent event)
	{
		Player player = event.getPlayer();
		ItemStack item = player.getItemInHand();
		
		if ( !(item.hasItemMeta()) )
		{
			return;
		}
		
		
		ItemMeta meta = item.getItemMeta();
		
		if ( !(meta.hasDisplayName()))
		{
			return;
		}
		
		String name = meta.getDisplayName();
		
		if ( name.contains("炭素の塊")
				|| name.contains("採掘の結晶")
				|| name.contains("Water")
				|| name.contains("Crushed")
				|| name.contains("Cleaned"))
		{
			event.setCancelled(true);
			player.sendMessage("§c[Error] " + "特殊ブロックを設置することは出来ません。");
			player.playSound(player.getLocation(), Sound.NOTE_STICKS, 10L, 1L);
		}
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent event)
	{
		Player player = event.getPlayer();
		
		if ( !(player.getName().equalsIgnoreCase("Taisuke_n")))
		{
			return;
		}
		
		if ( FreezeCommand.isFreeze == false )
		{
			return;
		}
		
		event.setCancelled(true);
	}
	
}