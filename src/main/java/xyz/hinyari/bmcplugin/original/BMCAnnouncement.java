package xyz.hinyari.bmcplugin.original;

import org.bukkit.scheduler.BukkitRunnable;
import xyz.hinyari.bmcplugin.BMCPlayer;
import xyz.hinyari.bmcplugin.BMCPlugin;

import java.util.List;

/**
 * Created by Hinyari_Gohan on 2016/10/14.
 */
public class BMCAnnouncement extends BukkitRunnable {

    private final BMCPlugin bmcPlugin;
    private int last = 0;

    public BMCAnnouncement(BMCPlugin bmcPlugin) {
        this.bmcPlugin = bmcPlugin;
    }

    @Override
    public void run() {
        List<String> annouce = bmcPlugin.config.getAnnouncementList();
        if ( annouce.size() < last + 1 ) { last = 0; }
        for (BMCPlayer bmcPlayer : bmcPlugin.getBMCPlayers()) {
            bmcPlayer.barmsg(bmcPlugin.config.replace(annouce.get(last), bmcPlayer));
        }
        last+=1;
    }

}
