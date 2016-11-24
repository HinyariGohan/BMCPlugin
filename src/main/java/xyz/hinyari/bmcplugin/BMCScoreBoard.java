package xyz.hinyari.bmcplugin;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import xyz.hinyari.bmcplugin.rank.Rank;

/**
 * Created by Shun on 2016/09/03.
 */
public class BMCScoreBoard {

    private Rank rank;
    private final Player player;
    private final Objective rankObj;
    private final Objective komeObj;
    private int rankPoint = 0;
    private int komePoint = 0;

    private Score rankScore;
    private Score komeScore;

    public BMCScoreBoard(BMCPlayer player) {
        BMCPlugin bmcPlugin = player.getPlugin();
        Scoreboard scoreboard = bmcPlugin.getScoreboard();
        this.rankObj = scoreboard.getObjective("rank");
        this.komeObj = scoreboard.getObjective("koshihikari");
        this.player = player.getPlayer();
        init();
    }

    private void init() {
        rankScore = rankObj.getScore(player);
        komeScore = komeObj.getScore(player);
        rankPoint = rankScore.getScore();
        komePoint = komeScore.getScore();
    }

    public void rankUP() {
        if (rankPoint >= 9) { return; }
        rankScore.setScore(rankPoint++);
    }

    public void rankDOWN() {
        if (rankPoint <= 0) { return; }
        rankScore.setScore(rankPoint--);
    }

    public void addKomePoint() {
        komeScore.setScore(komePoint++);
    }

    public int getKomePoint() {
        return komeScore.getScore();
    }

    public int getRankPoint() {
        return rankScore.getScore();
    }

    public void setRankPoint(int score) {
        rankScore.setScore(score);
    }

    public Rank getRank() {
        return Rank.getLabelOfRank(getRankPoint());
    }

    public void setRank(Rank rank) {
        rankScore.setScore(rank.getInt());
    }

    public void setKomePoint(int score) {
        komeScore.setScore(score);
    }




}
