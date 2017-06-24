package xyz.hinyari.bmcplugin.command;

/**
 * Created by Hinyari_Gohan on 2016/11/26.
 */

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.hinyari.bmcplugin.BMCPlayer;
import xyz.hinyari.bmcplugin.BMCPlugin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 日経平均株価の最新値を取得します。
 */
public class NikkeiCommand extends BukkitRunnable implements CommandExecutor
{

    private BMCPlugin bmcPlugin;

    public NikkeiCommand(BMCPlugin plugin)
    {
        this.bmcPlugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        BMCPlayer bmcPlayer = bmcPlugin.getBMCPlayer((Player) sender);
        if (!bmcPlayer.hasPermission("nikkei.show"))
        {
            bmcPlayer.errbar("権限がありません。");
            return true;
        }

        bmcPlayer.msg("日経平均株価(終値):" + columns.get(1)[5]);
        bmcPlayer.msg("※取引が行われていない場合、終了時点の最新のデータを表示します。");
        return true;
    }

    private List<String[]> columns = new ArrayList<>();

    private String line = "";

    @Override
    public void run()
    {

    }
}
