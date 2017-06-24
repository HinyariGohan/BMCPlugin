package xyz.hinyari.bmcplugin;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import xyz.hinyari.bmcplugin.rank.Rank;

/**
 * Created by Shun on 2016/09/03.
 */
public class BMCScoreBoard
{

    private final BMCPlayer bmcPlayer;
    private final BMCPlugin bmcPlugin;
    private Rank rank;
    private final Player player;
    private final Objective rankObj;
    private final Objective komeObj;
    private int rankPoint = 0;
    private int komePoint = 0;

    private Score rankScore;
    private Score komeScore;

    public BMCScoreBoard(BMCPlayer player)
    {
        this.bmcPlayer = player;
        bmcPlugin = player.getPlugin();
        Scoreboard scoreboard = bmcPlugin.getScoreboard();
        this.rankObj = scoreboard.getObjective("rank");
        this.komeObj = scoreboard.getObjective("koshihikari");
        this.player = player.getPlayer();
        init();
    }

    private void init()
    {
        rankScore = rankObj.getScore(player.getName());
        komeScore = komeObj.getScore(player.getName());
        rankPoint = rankScore.getScore();
        komePoint = komeScore.getScore();
    }

    /**
     * 使用側にてエラーの原因を除去してください
     */
    public void rankUP()
    {
        if (rankPoint >= 9)
        {
            return;
        }
        setRank(Rank.getLabelOfRank(getRankPoint() + 1));
    }

    /**
     * 使用側にてエラーの原因を除去してください
     */
    public void rankDOWN()
    {
        if (rankPoint <= 0)
        {
            return;
        }
        setRank(Rank.getLabelOfRank(getRankPoint() - 1));
    }

    public void addKomePoint()
    {
        komePoint++;
        komeScore.setScore(komePoint);
    }

    public int getKomePoint()
    {
        return komeScore.getScore();
    }

    public int getRankPoint()
    {
        return rankScore.getScore();
    }

    public void setRankPoint(int score)
    {
        rankScore.setScore(score);
    }

    public Rank getRank()
    {
        return Rank.getLabelOfRank(getRankPoint());
    }

    public void setRank(Rank rank)
    {
        bmcPlugin.debug(getRank().toString());

        String before = "pex user " + bmcPlayer.getName() + " group remove " + StringUtils.capitalize(getRank().toString());
        Bukkit.dispatchCommand(bmcPlugin.getServer().getConsoleSender(), before);
        bmcPlugin.debug(before);

        rankScore.setScore(rank.getInt());

        String after = "pex user " + bmcPlayer.getName() + " group add " + StringUtils.capitalize(getRank().toString());
        Bukkit.dispatchCommand(bmcPlugin.getServer().getConsoleSender(), after);
        bmcPlugin.debug(after);
    }

    public void setKomePoint(int score)
    {
        komeScore.setScore(score);
    }


}
