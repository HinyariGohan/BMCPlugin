package xyz.hinyari.bmcplugin.command;

import org.bukkit.Location;
import xyz.hinyari.bmcplugin.BMCPlayer;
import xyz.hinyari.bmcplugin.BMCPlugin;

/**
 * Created by Hinyari_Gohan on 2017/06/02.
 */
public class PlayerInfoCommand extends SubCommandAbst
{



    private final static String COMMAND_NAME = "player";

    @Override
    public String getCommandName()
    {
        return COMMAND_NAME;
    }

    @Override
    public boolean runCommand(BMCPlayer player, String label, String[] args)
    {
        if (!player.hasPermission("bmc.playerinfo"))
        {
            return player.noperm();
        }

        if (args.length <= 1)
        {
            return player.errmsg("引数が不足しています");
        }

        BMCPlayer target = player.getPlugin().getBMCPlayer(args[1]);

        if (target == null)
        {
            return player.errmsg("そのプレイヤーは存在しません！");
        }

        StringBuilder locationB = new StringBuilder();
        Location tarLoc = target.getPlayer().getLocation();
        locationB.append("(").append(tarLoc.getWorld().getName()).append(", ")
                .append(tarLoc.getBlockX()).append(", ").append(tarLoc.getBlockY()).append(", ")
                .append(tarLoc.getBlockZ()).append(")");

        player.msg("&a=== &r" + target.getName() + " &rさんの情報 &a===");
        player.msg("プレイヤー名 : " + target.getName());
        player.msg("UUID : " + target.getUUID().toString());
        player.msg("場所 : " + locationB.toString());
        player.msg("ゲームモード : " + target.getPlayer().getGameMode().toString());
        player.msg("フライ状態 : " + (target.getPlayer().isFlying() ? "有効" : "無効"));
        player.msg("IPアドレス : " + target.getPlayer().getAddress().getAddress().toString());
        player.msg("導入MOD : " + target.getPlayer().getListeningPluginChannels().toString());

        return false;
    }
}
