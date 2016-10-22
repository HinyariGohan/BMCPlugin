package xyz.hinyari.bmcplugin.command;

import xyz.hinyari.bmcplugin.BMCPlayer;
import xyz.hinyari.bmcplugin.BMCPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import xyz.hinyari.bmcplugin.BMCScoreBoard;
import xyz.hinyari.bmcplugin.utils.BMCHelp;

/**
 * @author Hinyari_Gohan
 */

public class KoshihikariCommand {
    private BMCPlugin plugin;
    private BMCHelp bmcHelp;

    public KoshihikariCommand(BMCPlugin plugin) {
        this.plugin = plugin;
        this.bmcHelp = plugin.bmcHelp;
    }

    public boolean runCommand(BMCPlayer bmcPlayer, String label, String[] args) {
        if (args.length == 0) {
            return bmcHelp.Komehelp(bmcPlayer);
        }
        if (args[0].equalsIgnoreCase("point")) {
            BMCPlayer target = bmcPlayer;
            if (args.length == 2) {
                if (Bukkit.getPlayer(args[1]) == null) {
                    bmcPlayer.errmsg("そのプレイヤーはオンラインではありません。");
                    return true;
                } else {
                    target = plugin.getBMCPlayer(Bukkit.getPlayer(args[1]));
                }
                bmcPlayer.msg(target.getName() + "さんのコシヒカリ交換可能ポイントは &6" + target.getScoreboard().getKomePoint() + "ポイント&r です。");
                return true;
            } else if (args.length == 4 || args.length == 5) {
                if (args[2].equalsIgnoreCase("set") || args[2].equalsIgnoreCase("add")) {
                    if (!bmcPlayer.hasPermission("bmc.kome.admin")) {
                        if (args.length == 4) return bmcPlayer.noperm();
                        if (args.length == 5 && !args[4].equals(plugin.config.getAdmin_pass())) {
                            return bmcPlayer.noperm();
                        }
                    }
                    if (!plugin.utils.isNumber(args[3])) { // 数字でなかった
                        bmcPlayer.errmsg("有効な数字を入力してください。");
                        return true;
                    }
                    int score = Integer.parseInt(args[3]);
                    target = plugin.getBMCPlayer(Bukkit.getPlayer(args[1]));
                    if (score <= -1) { // -1以下
                        bmcPlayer.errmsg("0以上の整数を入力してください。");
                        return true;
                    }
                    if (target == null) {
                        bmcPlayer.errmsg("そのプレイヤーはオフラインです。");
                        return true;
                    }
                    BMCScoreBoard bmcScoreBoard = target.getScoreboard();
                    if (args[2].equalsIgnoreCase("set")) {
                        bmcScoreBoard.setKomePoint(score);
                    }
                    if (args[2].equalsIgnoreCase("add")) {
                        bmcScoreBoard.setKomePoint(bmcScoreBoard.getKomePoint() + score);
                    }
                    bmcPlayer.msg(target.getName() + " さんのコシヒカリポイントを &6" + bmcScoreBoard.getKomePoint() + "ポイント&r にしました。");
                    return true;
                }
            }
        }
        if (args[0].equalsIgnoreCase("get")) {
            if (bmcPlayer.hasPermission("bmc.kome.get")) {
                bmcPlayer.getPlayer().getInventory().addItem(plugin.utils.getKoshihikari());
                bmcPlayer.msg("コシヒカリをインベントリに追加しました。");
                return true;
            }
            return bmcPlayer.noperm();
        }
        return bmcHelp.Komehelp(bmcPlayer);
    }
}

