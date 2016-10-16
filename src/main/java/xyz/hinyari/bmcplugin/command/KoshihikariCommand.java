package xyz.hinyari.bmcplugin.command;

import xyz.hinyari.bmcplugin.BMCPlayer;
import xyz.hinyari.bmcplugin.BMCPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

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
        if (args.length == 0 || args.length >= 3) {
            return bmcHelp.Komehelp(bmcPlayer);
        }
        if (args[0].equalsIgnoreCase("point")) {
            BMCPlayer target = bmcPlayer;
            if (args.length == 2) {
                if (getPlayer(args[1]) == null) {
                    bmcPlayer.errmsg("そのプレイヤーはオンラインではありません。");
                    return true;
                } else {
                    target = plugin.getBMCPlayer(getPlayer(args[1]));
                }
            }
            bmcPlayer.msg(target.getName() + "さんのコシヒカリ交換可能ポイントは &6" + target.getScoreboard().getKomePoint() + "ポイント&r です。");
            return true;
        }
        if (args[0].equalsIgnoreCase("get")) {
            if (bmcPlayer.hasPermission("bmc.kome.get")) {
                bmcPlayer.getPlayer().getInventory().addItem(plugin.utils.getKoshihikari());
                bmcPlayer.msg("コシヒカリをインベントリに追加しました。");
                return true;
            }
        }
        return bmcHelp.Komehelp(bmcPlayer);
    }

    private Player getPlayer(String name) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getName().equals(name)) return player;
        }
        return null;
    }
}

