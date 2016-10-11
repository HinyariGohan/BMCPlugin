package xyz.hinyari.bmcplugin.original;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.IUser;
import com.earth2me.essentials.User;
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
public class BMCTimeManager extends BukkitRunnable {

    private final int KOME_VALUE;
    private final int KOME_MINUTES;
    private final BMCPlugin plugin;
    private final Scoreboard scoreboard;
    private FileConfiguration config;
    private Objective login_time;

    public BMCTimeManager(BMCPlugin plugin) {
        this.plugin = plugin;
        this.KOME_VALUE = plugin.config.KOME_GET_VALUE;
        this.KOME_MINUTES = plugin.config.KOME_GET_MINUTES;
        this.scoreboard = plugin.scoreboard;
        this.login_time = scoreboard.getObjective("login_time");
    }

    @Override
    public void run() {
        for (BMCPlayer player : plugin.getBMCPlayers()) {
            Score logintime_score = login_time.getScore(player.getName());
            int value = logintime_score.getScore();
                if (value == (KOME_MINUTES * 60)) {
                    logintime_score.setScore(0);
                    player.getScoreboard().addKomePoint();
                    player.msg("コシヒカリポイントを追加しました。");
                    player.msg("あなたのコシヒカリ交換可能ポイントは " +
                    player.getScoreboard().getKomePoint() + " 枚になりました。");
                    return;
                }
                User user = plugin.essentials.getUser(player.getPlayer());
                if (!user.isAfk()) {
                    plugin.debug(user.getName() + " : " + String.valueOf(user.isAfk()));
                    logintime_score.setScore(value++);
                }
            }
        }
    }