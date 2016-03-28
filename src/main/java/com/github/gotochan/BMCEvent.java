package com.github.gotochan;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCreatePortalEvent;
import org.bukkit.event.player.PlayerMoveEvent;



public class BMCEvent implements Listener {
	@EventHandler(priority=EventPriority.HIGH)
	public void onEntityCreatePortalEvent(EntityCreatePortalEvent e) {
		e.setCancelled(true);
	}

    @SuppressWarnings("unused")
	@EventHandler
    public void onPlayerMoveEvent(PlayerMoveEvent e) {
    	Player p = e.getPlayer();
    	double locx = p.getLocation().getX();
    	double locy = p.getLocation().getY() + 3;
    	double locz = p.getLocation().getZ();
    	Location loc = new Location(p.getWorld(), locx, locy, locz);
    	Location ploc = p.getLocation();
    	if ( p.hasPermission("bmc.effect")) {
    		p.getWorld().playEffect(ploc, Effect.LAVA_POP, 0);
    	}
    }


}
