package xyz.hinyari.bmcplugin.original;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.IUser;
import com.earth2me.essentials.User;
import org.bukkit.Sound;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import xyz.hinyari.bmcplugin.BMCPlayer;
import xyz.hinyari.bmcplugin.BMCPlugin;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

/**
 * Created by Hinyari_Gohan on 2016/09/23.
 */
public class BMCTimeManager extends BukkitRunnable
{

    private final int KOME_VALUE;
    private final int KOME_MINUTES;
    private final BMCPlugin plugin;
    private final Scoreboard scoreboard;
    private FileConfiguration config;
    private Objective login_time;

    public BMCTimeManager(BMCPlugin plugin)
    {
        this.plugin = plugin;
        this.KOME_VALUE = plugin.config.getKomeGetValue();
        this.KOME_MINUTES = plugin.config.getKomeGetMinutes();
        this.scoreboard = plugin.getScoreboard();
        this.login_time = scoreboard.getObjective("login_time");
    }

    @Override
    public void run()
    {
        for (BMCPlayer player : plugin.getBMCPlayers())
        {
            Score logintime_score = login_time.getScore(player.getName());
            int value = logintime_score.getScore();
            if (plugin.config.getKomeGetAvailable())
            {
                if (value == (KOME_MINUTES * 60))
                {
                    logintime_score.setScore(0);
                    player.getScoreboard().setKomePoint(player.getScoreboard().getKomePoint() + KOME_VALUE);
                    player.msg("コシヒカリ交換可能ポイントが " + KOME_VALUE + "ポイント追加されました！");
                    player.msg("あなたの交換可能ポイントは &6" + player.getScoreboard().getKomePoint() + " ポイント&rになりました。");
                    player.playSound(Sound.ENTITY_PLAYER_LEVELUP, 0.8F, 0.6F);
                    return;
                }
                User user = plugin.essentials.getUser(player.getPlayer());
                if (!user.isAfk() && player.getPlayer().isOnline())
                {
                    //plugin.debug(user.getName() + " : " + String.valueOf(user.isAfk()));
                    int score = value + 1;
                    logintime_score.setScore(score);
                }
            }
        }
    }
}