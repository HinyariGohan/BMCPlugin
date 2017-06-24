package xyz.hinyari.bmcplugin.command;

import xyz.hinyari.bmcplugin.BMCPlayer;

public abstract class SubCommandAbst
{

    /**
     * コマンドを取得します。
     *
     * @return コマンド
     */
    public abstract String getCommandName();

    /**
     * コマンドを実行します。
     *
     * @param player コマンド実行者(BMCPlayer)
     * @param label  ラベル
     * @param args   引数
     * @return コマンドが実行されたか(boolean)
     */
    public abstract boolean runCommand(BMCPlayer player, String label, String[] args);


}
