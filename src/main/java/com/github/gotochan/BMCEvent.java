package com.github.gotochan;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCreatePortalEvent;

public class BMCEvent implements Listener {
	@EventHandler(priority=EventPriority.HIGH)
	public void onEntityCreatePortalEvent(EntityCreatePortalEvent e) {
			e.setCancelled(true);
	}

}
