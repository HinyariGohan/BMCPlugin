package xyz.hinyari.bmcplugin.command;

import xyz.hinyari.bmcplugin.BMCPlugin;
import xyz.hinyari.bmcplugin.Rank;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import xyz.hinyari.bmcplugin.BMCPlayer;
import xyz.hinyari.bmcplugin.Utils.BMCHelp;

/**
 * BMCサーバー ランクコマンド ランクアップ実装クラス
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
	
	public boolean runCommand(
			BMCPlayer bmcPlayer, String label, String[] args)
	{
		
		//Player player = bmcPlayer.getPlayer();
		ItemStack item = bmcPlayer.getItemInMainHand();
		Rank rank = bmcPlayer.getScoreboard().getRank();
		String nowRankName = bmcPlayer.getScoreboard().getRank().getName(bmcPlayer.getScoreboard().getRank());
		String name = bmcPlayer.getName();
		
		if ( args.length == 0 ) {
			
			if ( item != null )
			{
				if ( item.getType() == Material.INK_SACK )
				{
					ItemMeta im = item.getItemMeta();
					int level = im.getEnchantLevel(Enchantment.DURABILITY);
					if ( rank == Rank.VISITOR ) { //VISITOR
						bmcPlayer.msg("あなたは" + nowRankName + "ランクです。");
						bmcPlayer.msg(ChatColor.RED + "通常ワールドにスポーンしていてこのランクの場合は管理者に連絡してください。");
					} else if ( rank == Rank.INFRARED ) { //INFRARED
						bmcPlayer.msg(ChatColor.LIGHT_PURPLE + "あなたは最高ランクへ達しています。");
					} else { // それ以外
						if ( level == (rank.getInt() + 1)) {
							bmcPlayer.msg("&a* " + Rank.getLabelOfName(rank.getInt() + 1) + " ランクに昇格しました！" );
							plugin.broadcast("【ランクアップ】 " + bmcPlayer.getName() + "さんが " + Rank.getLabelOfName(rank.getInt() + 1)
									+ "ランクになりました！おめでとうございます！");
							bmcPlayer.getScoreboard().rankUP();
						} else {
							bmcPlayer.msg(plugin.ERROR + "クリアランス違反です。");
						}
					}
				}
				else
				{
					bmcPlayer.msg("ランクアイテムを持って下さい。");
				}
			}
			else
			{
				bmcPlayer.msg("手にアイテムを持っていません！");
				bmcPlayer.msg("ランクアップの方法を確認するには &6/rankup help&r コマンドを使用します。");
			}
		}
		else if ( args.length == 1 && args[0].equalsIgnoreCase("help") )
			return bmcHelp.RankupProcessInfo(bmcPlayer);
		else
			return bmcHelp.RankUphelp(bmcPlayer);
		
		return true;
	}
}