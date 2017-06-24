package xyz.hinyari.bmcplugin.command;

import xyz.hinyari.bmcplugin.BMCPlayer;
import xyz.hinyari.bmcplugin.BMCPlugin;

public class KitCommand extends SubCommandAbst
{

    public KitCommand(BMCPlugin bmc)
    {
        this.bmc = bmc;
    }

    private BMCPlugin bmc;

    @Override
    public String getCommandName()
    {
        return "KIT";
    }

    public boolean runCommand(BMCPlayer bmcPlayer, String label, String[] args)
    {
        bmcPlayer.errmsg("現在開発中です。");
        return true;
    }

}
