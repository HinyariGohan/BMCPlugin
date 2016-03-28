package com.github.gotochan.Koshihikari;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import com.github.gotochan.BMC;

public class KoshihikariCommand
	implements CommandExecutor {
	String crlf = System.getProperty("line.separator");
/**
 * @author Hinyari_Gohan
 * @see org.bukkit.command.CommandExecutor#onCommand(org.bukkit.command.CommandSender, org.bukkit.command.Command, java.lang.String, java.lang.String[])
 */

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd,
			String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("koshihikari")){
			if(sender instanceof Player) {
				Player player = (Player) sender;
				Scoreboard board = player.getScoreboard();
				Objective objective = board.getObjective("koshihikari");
				if ( args.length < 1 ) {
					player.sendMessage(BMC.prefix + ChatColor.GOLD  + "---[コシヒカリコマンド一覧]---");
					player.sendMessage(BMC.prefix + ChatColor.GOLD + "/koshihikari --" + crlf +
							"  ヘルプを参照します。");
					player.sendMessage(BMC.prefix + ChatColor.GOLD + "/koshihikari ticket --" + crlf +
							"  コシヒカリ交換チケット数を参照します。");
					player.sendMessage(BMC.prefix + ChatColor.GRAY + "koshihikariはkomeに省略することが出来ます。");
				}
				else if ( args[0].equalsIgnoreCase("ticket")) {
						Score KScore = objective.getScore(player);
						player.sendMessage(BMC.prefix  + "あなたのコシヒカリ交換チケット数は" + Integer.toString(KScore.getScore()) + "枚です。");
					}
				else if ( args[0].equalsIgnoreCase("debug")) {
					if( args.length == 1) {
						player.sendMessage(BMC.prefix + "デバッグコマンド一覧");
						player.sendMessage(BMC.prefix + ChatColor.GOLD + "hunger" + ChatColor.RESET +" - 空腹にします。");
						player.sendMessage(BMC.prefix + ChatColor.GOLD + "get" + ChatColor.RESET + " - キノコシチューを手に入れます。");
					}
					else if ( args.length == 2) {
						if ( args[1].equalsIgnoreCase("hunger")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 60, 100));
						}
						else if ( args[1].equalsIgnoreCase("get")) {
							player.setItemInHand(new ItemStack(Material.MUSHROOM_SOUP));
						}
					}
				}
			}
		}
	return false;
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

