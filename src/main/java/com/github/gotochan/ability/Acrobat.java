package com.github.gotochan.ability;

import java.util.HashMap;

import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.gotochan.BMC;
import com.github.gotochan.command.KitCommand;
import com.github.gotochan.command.SubKitAbst;

public class Acrobat extends SubKitAbst implements Listener{
	
	private static String KIT_NAME = "acrobat";
	private static String KIT_SKILL = "二段ジャンプをすることが出来ます。";
	
	public static HashMap<Player, String> canAcrobating = new HashMap<Player, String>();
	
	@Override
	public String getKitName()
	{
		return KIT_NAME;
	}
	
	@Override
	public boolean runKitCommand(CommandSender sender, String[] args)
	{
		Player player = (Player) sender;
		if ( args.length == 2 )
		{
			return KitCommand.KitExample(sender, KIT_NAME, KIT_SKILL);
		}
		else if ( args[2].equalsIgnoreCase("mode") )
		{
			// キットが格納されていなければそのままアクロバットを有効にする
			if ( Ability.isnullKit(player) )
			{
				Ability.KIT_NOW.put(player, KIT_NAME);
				canAcrobating.put(player, "true");
				player.sendMessage(BMC.prefix + "アクロバット機能を有効にしました。");
				player.setAllowFlight(true);
			}
			// 格納されていなくてアクロバットの場合はモードを変更する
			else if ( Ability.isthiskit(player, KIT_NAME) )
			{
				if ( canAcrobating.get(player).contains("true") )
				{
					canAcrobating.put(player, "false");
					Ability.KIT_NOW.put(player, null);
					player.sendMessage(BMC.prefix + "アクロバット機能を無効にしました。");
					player.setAllowFlight(false);
				}
				else
				{
					canAcrobating.put(player, "false");
					Ability.KIT_NOW.put(player, KIT_NAME);
					player.sendMessage("アクロバット機能を有効にしました。");
					player.setAllowFlight(true);
				}
			}
			else
				// 他のキットが有効になっている場合
			{
				player.sendMessage(Ability.KIT_NOW.get(player) + " キットを無効にする必要があります。");
			}
		}
		return true;
	}
	
	@EventHandler(priority=EventPriority.HIGH)
	public void AcrobatDoubleJump(PlayerToggleFlightEvent event)
	{
		final Player player = event.getPlayer();
		World world = player.getWorld();
		if(player.getGameMode() != GameMode.CREATIVE && player.hasPermission("bmc.acrobat"))
		{
			if ( canAcrobating.get(player) == "true") {
				event.setCancelled(true);
				player.setAllowFlight(false);
				player.setFlying(false);
				player.setVelocity(player.getLocation().getDirection().setY(1).multiply(1));
				player.playSound(player.getLocation(), Sound.ZOMBIE_INFECT, 1.0F, 2.0F);
				world.playEffect(player.getLocation(), Effect.LARGE_SMOKE, 0);
				player.setAllowFlight(true);
				canAcrobating.put(player, "false");
				
				new BukkitRunnable() {
					
					@Override
					public void run()
					{
						canAcrobating.put(player, "true");
					}
				}.runTaskLater(BMC.getInstance(), 60L);
			} else {
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onGamemodeChange(PlayerGameModeChangeEvent event) {
		Player player = event.getPlayer();
		if ( event.getNewGameMode() == GameMode.SURVIVAL )
		{
			if ( canAcrobating.get(player) == "true")
			{
				player.setAllowFlight(true);
			}
		}
	}
	
	@EventHandler
	public void onFallDamage(EntityDamageEvent event)
	{
		if( event.getEntityType() == EntityType.PLAYER && event.getCause() == DamageCause.FALL )
		{
			Player player = (Player) event.getEntity();
			
			if ( canAcrobating.get(player) == "true" )
			{
				event.setCancelled(true);
			}
		}
	}
}
