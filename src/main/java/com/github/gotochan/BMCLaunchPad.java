package com.github.gotochan;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class BMCLaunchPad implements Listener {

	static long LIMIT = 3000L;
	@SuppressWarnings({ "unchecked", "rawtypes" })
	Map<String, Long> launchTimes = new HashMap();

	  @EventHandler
	  public void onPlayerStep(PlayerInteractEvent e)
	  {
	    if (e.getAction().equals(Action.PHYSICAL))
	    {
	      Player p = e.getPlayer();
	      if ((e.getClickedBlock().getType().equals(Material.STONE_PLATE)) || (e.getClickedBlock().getType().equals(Material.IRON_PLATE)))
	      {
	        Block b = e.getClickedBlock().getLocation().subtract(0.0D, 1.0D, 0.0D).getBlock();
	        if ((b != null) && (b.getType().equals(Material.REDSTONE_BLOCK)))
	        {
	          Location l = p.getLocation().add(0.0D, 1.0D, 0.0D);
	          p.teleport(l);
	          p.setVelocity(p.getVelocity().add(p.getLocation().getDirection().multiply(3)).setY(1));
	          this.launchTimes.put(p.getName(), Long.valueOf(System.currentTimeMillis()));
	          p.playSound(p.getLocation(), Sound.WITHER_SHOOT, (float)LIMIT, (float) 2.0);
	        }
	      }
	    }
	  }

	  @EventHandler
	  public void onBlock(BlockPlaceEvent e)
	  {
		  Block b = e.getBlockPlaced().getLocation().subtract(0.0D, 1.0D, 0.0D).getBlock();
	      if ((e.getBlockPlaced().getType().equals(Material.STONE_PLATE)) && (b.getType().equals(Material.REDSTONE_BLOCK)))
	      {
	        e.getBlockPlaced().setType(Material.AIR);
	        e.getPlayer().sendMessage(ChatColor.RED + "You do not have permission to create ppl's!");
	      }
	  }

	  @EventHandler
	  public void anotherEvent(EntityDamageEvent event)
	  {
	    if ((event.getEntity() instanceof Player))
	    {
	      Player player = (Player)event.getEntity();
	      Long time = (Long)this.launchTimes.get(player.getName());
	      if (time != null) {
	        if (System.currentTimeMillis() - time.longValue() < LIMIT) {
	          event.setCancelled(true);
	        } else {
	          this.launchTimes.remove(player.getName());
	        }
	      }
	    }
	  }
}
