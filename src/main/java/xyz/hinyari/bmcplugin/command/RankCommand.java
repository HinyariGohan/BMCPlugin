package xyz.hinyari.bmcplugin.command;

import xyz.hinyari.bmcplugin.Utils.BMCUtils;
import xyz.hinyari.bmcplugin.rank.Rank;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import xyz.hinyari.bmcplugin.BMCPlugin;
import xyz.hinyari.bmcplugin.BMCPlayer;
import xyz.hinyari.bmcplugin.Utils.BMCHelp;

/**
 * @author Hinyari_Gohan
 */

public class RankCommand {

    private BMCPlugin bmc;
    private BMCHelp bmcHelp;

    public RankCommand(BMCPlugin plugin) {
        this.bmc = plugin;
        this.bmcHelp = bmc.bmcHelp;
    }


    public boolean runCommand(BMCPlayer bmcPlayer, String label, String[] args) {
        //Player player = bmcPlayer.getPlayer();
        String myname = bmcPlayer.getName();

        if (args.length == 0) {
            bmcHelp.Rankhelp(bmcPlayer);
            return true;
        }
        if (args[0].equalsIgnoreCase("stats")) {
            if (args.length == 1) {
                int score = bmcPlayer.getScoreboard().getRankPoint();
                bmcPlayer.msg(ChatColor.YELLOW + "========BMCサーバー ランクシステム========");
                bmcPlayer.msg("名前: " + myname);
                bmcPlayer.msg("現在のランク: " + Rank.getLabelOfName(score));
                bmcPlayer.msg("次のランク: " + Rank.getLabelOfName(score + 1));
                return true;
            } else if (args.length == 2) {
                Player player_other = getPlayer(args[1]);
                if (player_other == null) {
                    bmcPlayer.msg(bmc.ERROR + "そのプレイヤーはオフラインです。");
                } else {
                    BMCPlayer other = bmc.getBMCPlayer(player_other);
                    int other_score = other.getScoreboard().getRankPoint();
                    bmcPlayer.msg(ChatColor.YELLOW + "========BMCサーバー ランクシステム========");
                    bmcPlayer.msg("名前: " + args[1]);
                    bmcPlayer.msg("現在のランク: " + Rank.getLabelOfName(other_score));
                    bmcPlayer.msg("次のランク: " + Rank.getLabelOfName(other_score + 1));
                }
                return true;
            } else {
                return bmcHelp.Rankhelp(bmcPlayer);
            }
        } else if (args[0].equalsIgnoreCase("set")) {
            if (bmcPlayer.hasPermission("bmc.rank.set")) {
                if (args.length <= 2) {
                    bmcPlayer.msg("/rank set <プレイヤー名> <ランク名>");
                    return true;
                } else {
                    if (getBMCPlayer(args[1]) == null) {
                        bmcPlayer.errmsg("そのプレイヤーはオフラインです。");
                        return true;
                    } else {
                        Rank rank = Rank.getLabelOfRank(args[2]);
                        if (rank == null) {
                            bmcPlayer.errmsg("正しいランク名を入力してください。");
                        } else {
                            getBMCPlayer(args[1]).getScoreboard().setRank(rank);
                            bmcPlayer.msg(getBMCPlayer(args[1]).getName() + " さんを " + rank.getName() + " ランクにしました。");
                        }
                        return true;
                    }
                }
            } else return bmcPlayer.noperm();
        } else if (args[0].equalsIgnoreCase("menu")) {
            if (bmcPlayer.hasPermission("bmc.rank.menu")) {
                bmcPlayer.openRankmenu();
                return true;
            }
            return bmcPlayer.noperm();
        } else if (args[0].equalsIgnoreCase("item")) {
            if (bmcPlayer.hasPermission("bmc.rank.item")) {
                bmcPlayer.getPlayer().getInventory().addItem(bmc.utils.getRankitem());
                return true;
            }
            return bmcPlayer.noperm();
        }
        return bmcHelp.Rankhelp(bmcPlayer);
    }

    private Player getPlayer(String name) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getName().equals(name)) return player;
        }
        return null;
    }

    private BMCPlayer getBMCPlayer(String name) {
        return bmc.getBMCPlayer(getPlayer(name));
    }
}