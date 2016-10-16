package xyz.hinyari.bmcplugin.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import xyz.hinyari.bmcplugin.rank.Rank;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import xyz.hinyari.bmcplugin.BMCPlugin;
import xyz.hinyari.bmcplugin.BMCPlayer;
import xyz.hinyari.bmcplugin.utils.BMCHelp;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Hinyari_Gohan
 */

public class RankCommand {

    private BMCPlugin bmc;
    private BMCHelp bmcHelp;
    private final List<String> RANK_NAME_LIST = new ArrayList<>();

    public RankCommand(BMCPlugin plugin) {
        this.bmc = plugin;
        this.bmcHelp = bmc.bmcHelp;
        for (Rank rank : Rank.values()) {
            if (rank == Rank.NONE || rank == Rank.MAXIMUM) {
                continue;
            }
            RANK_NAME_LIST.add(rank.toString());
        }
    }


    public boolean runCommand(BMCPlayer bmcPlayer, String label, String[] args) {
        //Player player = bmcPlayer.getPlayer();
        String myname = bmcPlayer.getName();

        if (args.length == 0) {
            bmcHelp.Rankhelp(bmcPlayer);
            return true;
        }
        if (args[0].equalsIgnoreCase("stats")) {
            BMCPlayer msgPlayer;
            if (args.length == 1) {
                msgPlayer = bmcPlayer;
            } else if (args.length == 2) {
                msgPlayer = bmc.getBMCPlayer(Bukkit.getPlayer(args[1]));
            } else {
                return bmcHelp.Rankhelp(bmcPlayer);
            }
            if (msgPlayer == null) { bmcPlayer.errmsg("そのプレイヤーはオフラインです。"); return true; }
            Rank rank = msgPlayer.getScoreboard().getRank();
            bmcPlayer.msg(bmc.equalMessage(msgPlayer.getName() + " さんのランク"));
            bmcPlayer.msg("現在のランク: " + rank.getName());
            bmcPlayer.msg("次のランク: " + Rank.getLabelOfName(rank.getInt() + 1));
            return true;
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
            if (bmcPlayer.hasPermission("bmc.rankmenu")) {
                bmcPlayer.openRankmenu();
                return true;
            }
            return bmcPlayer.noperm();
        } else if (args[0].equalsIgnoreCase("item")) {
            if (bmcPlayer.hasPermission("bmc.rankmenu.get")) {
                bmcPlayer.getPlayer().getInventory().addItem(bmc.utils.getRankitem());
                return true;
            }
            return bmcPlayer.noperm();
        }
        return bmcHelp.Rankhelp(bmcPlayer);
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        BMCPlayer player = bmc.getBMCPlayer((Player)sender);
        if (args.length == 3 && args[0].equalsIgnoreCase("set")) {
            ArrayList<String> list = new ArrayList<>();
            String arg = args[2].toLowerCase();
            list.addAll(RANK_NAME_LIST.stream().filter(name -> name.toLowerCase().startsWith(arg)).collect(Collectors.toList()));
            return list;
        }
        return null;
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