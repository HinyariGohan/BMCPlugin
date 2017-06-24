package xyz.hinyari.bmcplugin.command;

import org.bukkit.Material;
import xyz.hinyari.bmcplugin.BMCPlayer;
import xyz.hinyari.bmcplugin.BMCPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import xyz.hinyari.bmcplugin.BMCScoreBoard;
import xyz.hinyari.bmcplugin.utils.BMCHelp;

/**
 * @author Hinyari_Gohan
 */

public class KoshihikariCommand
{
    private BMCPlugin plugin;
    private BMCHelp bmcHelp;

    public KoshihikariCommand(BMCPlugin plugin)
    {
        this.plugin = plugin;
        this.bmcHelp = plugin.bmcHelp;
    }

    public boolean runCommand(BMCPlayer bmcPlayer, String label, String[] args)
    {
        if (args.length == 0)
        {
            return bmcHelp.Komehelp(bmcPlayer);
        }
        if (args[0].equalsIgnoreCase("point"))
        {
            BMCPlayer target;
            if (args.length == 1)
            {
                bmcPlayer.msg("あなたのコシヒカリ交換可能ポイントは &6" + bmcPlayer.getScoreboard().getKomePoint() + "ポイント&r です。");
                return true;
            }
            if (args.length == 2)
            {
                if (Bukkit.getPlayer(args[1]) == null)
                {
                    bmcPlayer.errmsg("そのプレイヤーはオンラインではありません。");
                    return true;
                } else
                {
                    target = plugin.getBMCPlayer(Bukkit.getPlayer(args[1]));
                }
                bmcPlayer.msg(target.getName() + "さんのコシヒカリ交換可能ポイントは &6" + target.getScoreboard()
                        .getKomePoint() + "ポイント&r です。");
                bmcPlayer.msg("/kome get コマンドで交換することが出来ます。");
                return true;
            } else if (args.length == 4 || args.length == 5)
            {
                if (args[2].equalsIgnoreCase("set") || args[2].equalsIgnoreCase("add"))
                {
                    if (!bmcPlayer.hasPermission("bmc.kome.admin"))
                    {
                        if (args.length == 4 || !args[4].equals(plugin.config.getAdmin_pass()))
                            return bmcPlayer.noperm();
                    }
                    if (!plugin.utils.isNumber(args[3]))
                    { // 数字でなかった
                        bmcPlayer.errmsg("有効な数字を入力してください。");
                        return true;
                    }
                    int score = Integer.parseInt(args[3]);
                    target = plugin.getBMCPlayer(Bukkit.getPlayer(args[1]));
                    if (score <= -1)
                    { // -1以下
                        bmcPlayer.errmsg("0以上の整数を入力してください。");
                        return true;
                    }
                    if (target == null)
                    {
                        bmcPlayer.errmsg("そのプレイヤーはオフラインです。");
                        return true;
                    }
                    BMCScoreBoard bmcScoreBoard = target.getScoreboard();
                    if (args[2].equalsIgnoreCase("set"))
                    {
                        bmcScoreBoard.setKomePoint(score);
                    }
                    if (args[2].equalsIgnoreCase("add"))
                    {
                        bmcScoreBoard.setKomePoint(bmcScoreBoard.getKomePoint() + score);
                    }
                    bmcPlayer.msg(target.getName() + " さんのコシヒカリポイントを &6" + bmcScoreBoard.getKomePoint() + "ポイント&r にしました。");
                    return true;
                }
            }
        }
        if (args[0].equalsIgnoreCase("get"))
        {
            if (bmcPlayer.hasPermission("bmc.kome.get") || (args.length == 2 && args[1].equals(plugin.config.getAdmin_pass())))
            {
                bmcPlayer.getPlayer().getInventory().addItem(plugin.utils.getKoshihikari());
                bmcPlayer.msg("コシヒカリをインベントリに追加しました。");
                return true;
            } else
            {
                int komepoint = bmcPlayer.getScoreboard().getKomePoint();
                if (komepoint >= 1)
                {
                    bmcPlayer.getPlayer().getInventory().addItem(plugin.utils.getKoshihikari());
                    bmcPlayer.msg("コシヒカリを交換しました。残りのコシヒカリチケット数は " + (komepoint - 1) + "枚です。");
                    bmcPlayer.getScoreboard().setKomePoint(komepoint - 1);
                    return true;
                }
            }
            return bmcPlayer.noperm();
        }
        if (args[0].equalsIgnoreCase("give"))
        {
            if (bmcPlayer.hasPermission("bmc.kome.give"))
            {
                if (args.length >= 2)
                {
                    if (plugin.getBMCPlayer(args[1]) == null)
                    {
                        bmcPlayer.errmsg("そのプレイヤーはオフラインです。");
                        return true;
                    }
                    plugin.getBMCPlayer(args[1]).getPlayer().getInventory().addItem(plugin.utils.getKoshihikari());
                    bmcPlayer.msg(plugin.getBMCPlayer(args[1]).getName() + "さんのインベントリにコシヒカリを追加しました。");
                    return true;
                }
            } else
            {
                return bmcPlayer.noperm();
            }
        }
        if (args[0].equalsIgnoreCase("changepoint"))
        {
            if (plugin.bmcBoolean.isKoshihikari(bmcPlayer.getItemInMainHand()))
            {
                bmcPlayer.getItemInMainHand().setType(Material.AIR);
                bmcPlayer.getScoreboard().setKomePoint(bmcPlayer.getScoreboard().getKomePoint() + 1);
            }
        }
        return bmcHelp.Komehelp(bmcPlayer);
    }
}

