package com.github.gotochan.Koshihikari;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import com.github.gotochan.BMC;

public class KoshihikariCommand
	implements CommandExecutor {

/**
 * @author Hinyari_Gohan
 */

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd,
			String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("koshihikari")){
		Player player = (Player) sender;
		Scoreboard board = player.getScoreboard();
		ItemStack pi = player.getItemInHand();
		String crlf = System.getProperty("line.separator");
		ItemMeta im = pi.getItemMeta();
			if(sender instanceof Player) {
				Objective objective = board.getObjective("koshihikari");
				Score KScore = objective.getScore(player);
				if ( args.length < 1 ) {
					player.sendMessage(BMC.prefix + ChatColor.GOLD  + "---[コシヒカリコマンド一覧]---");
					player.sendMessage(BMC.prefix + ChatColor.GOLD + "/koshihikari --" + crlf +
							"  ヘルプを参照します。");
					player.sendMessage(BMC.prefix + ChatColor.GOLD + "/koshihikari ticket --" + crlf +
							"  コシヒカリ交換チケット数を参照します。");
					player.sendMessage(BMC.prefix + ChatColor.GOLD + "/kosihikari change --" + crlf +
							"  コシヒカリを新米に交換します。");
					player.sendMessage(BMC.prefix + ChatColor.GRAY + "koshihikariはkomeに省略することが出来ます。");
				} else if ( args[0].equalsIgnoreCase("ticket")) {
					player.sendMessage(BMC.prefix  + "あなたのコシヒカリ交換チケット数は" + Integer.toString(KScore.getScore()) + "枚です。");
				}
				if ( args[0].equalsIgnoreCase("change") ) {
					if(!(player.getItemInHand().getType() == Material.GHAST_TEAR ||
							im.getDisplayName().equalsIgnoreCase(ChatColor.GREEN + "コシヒカリ(スタック可能)"))) {
						player.sendMessage(BMC.prefix + "手にコシヒカリを持った状態で実行して下さい。");
					}
					if (!(hasEnchantedItem(player, new ItemStack(Material.GHAST_TEAR),Enchantment.DURABILITY,1) )){
						player.sendMessage(BMC.prefix + "必要なメタデータが付与されていません。");
						//Bukkit.broadcastMessage( player.getName() + "さんが偽造コシヒカリを使用しました！"); //必要ﾅｯｼﾝｸﾞ
					}
					ItemStack koshihikari = setName(new ItemStack(Material.EMERALD, 1));
					koshihikari.addEnchantment(Enchantment.DURABILITY, 1);
					player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
					player.updateInventory();
					player.getInventory().addItem(koshihikari);
				}

		}
	}
		return false;
}

	private ItemStack setName(ItemStack itemStack) {
		ItemMeta im  = itemStack.getItemMeta();
		String koshihikariname = ChatColor.GOLD + "コシヒカリ";
		im.setDisplayName(koshihikariname);
		return itemStack ;
	}

	public Boolean err (CommandSender sender){
		sender.sendMessage(BMC.prefix + " ゲーム内から実行して下さい。");
		return Boolean.valueOf(true);
	}
	public boolean hasEnchantedItem(Player p, ItemStack i, Enchantment e, int enchantmentLevel){
	    ItemStack[] inv = p.getInventory().getContents();
	    for(ItemStack item:inv){
	    if(item.getType().equals(i.getType())){
	    if(item.getEnchantments().containsKey(e)){
	    if(item.getEnchantmentLevel(Enchantment.DURABILITY) == enchantmentLevel){
	    } } } }
		return true; }
}

