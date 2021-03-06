package xyz.hinyari.bmcplugin.command;

import xyz.hinyari.bmcplugin.BMCPlugin;
import xyz.hinyari.bmcplugin.BMCScoreBoard;
import xyz.hinyari.bmcplugin.rank.Rank;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import xyz.hinyari.bmcplugin.BMCPlayer;
import xyz.hinyari.bmcplugin.utils.BMCHelp;

/**
 * BMCサーバー ランクコマンド ランクアップ実装クラス
 *
 * @author Hinyari_Gohan
 */

public class RankUpCommand
{
    private BMCPlugin plugin;
    private BMCHelp bmcHelp;

    public RankUpCommand(BMCPlugin bmc)
    {
        this.plugin = bmc;
        this.bmcHelp = bmc.bmcHelp;
    }

    public boolean runCommand(BMCPlayer bmcPlayer, String label, String[] args)
    {

        //Player player = bmcPlayer.getPlayer();
        BMCScoreBoard player_board = bmcPlayer.getScoreboard();
        ItemStack item = bmcPlayer.getItemInMainHand();
        Rank rank = player_board.getRank();
        String nowRankName = player_board.getRank().getName();
        String name = bmcPlayer.getName();

        if (args.length == 0)
        {

            if (item != null)
            {
                if (item.getType() == Material.INK_SACK)
                {
                    ItemMeta im = item.getItemMeta();
                    int level = im.getEnchantLevel(Enchantment.DURABILITY);
                    if (rank == Rank.VISITOR)
                    { //VISITOR
                        bmcPlayer.msg("あなたは" + nowRankName + "ランクです。");
                        bmcPlayer.msg(ChatColor.RED + "通常ワールドにスポーンしていてこのランクの場合は管理者に連絡してください。");
                    } else if (rank == Rank.INFRARED)
                    { //INFRARED
                        bmcPlayer.msg(ChatColor.LIGHT_PURPLE + "あなたは最高ランクへ達しています。");
                    } else
                    { // それ以外
                        if (level == (rank.getInt()))
                        {
                            player_board.rankUP();
                            bmcPlayer.msg("&a* " + player_board.getRank().getName() + " ランクに昇格しました！");
                            plugin.broadcast("【ランクアップ】 " + bmcPlayer.getName() + "さんが " + player_board.getRank()
                                    .getName() + "ランクになりました！おめでとうございます！");
                            item.setType(Material.AIR);
                        } else
                        {
                            bmcPlayer.errmsg("クリアランス違反です。");
                        }
                    }
                } else
                {
                    bmcPlayer.msg("ランクアイテムを持って下さい。");
                }
            } else
            {
                bmcPlayer.errmsg("手にアイテムを持っていません！");
                bmcPlayer.errmsg("ランクアップの方法を確認するには &6/rankup help&r コマンドを使用します。");
            }
        } else if (args.length == 1 && args[0].equalsIgnoreCase("help"))
            return bmcHelp.RankupProcessInfo(bmcPlayer);
        else
            return bmcHelp.RankUphelp(bmcPlayer);

        return true;
    }
}