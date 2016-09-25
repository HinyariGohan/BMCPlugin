package com.github.gotochan.enchant;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.github.gotochan.BMCPlugin;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.gotochan.Utils.BMCBoolean;

public class AutoSmelt implements Listener
{
	private BMCPlugin bmc;
	private BMCBoolean bmcBoolean;

	public AutoSmelt(BMCPlugin bmc) {
		this.bmc = bmc;
		this.bmcBoolean = bmc.bmcBoolean;
	}
	public final String AUTO_SMELT = "Auto Smelt";

	public List<String> lore = new ArrayList<String>();

	public final Material[] AUTOSMELT_ITEMS = new Material[]
			{
					Material.WOOD_PICKAXE,
					Material.STONE_PICKAXE,
					Material.IRON_PICKAXE,
					Material.GOLD_PICKAXE,
					Material.DIAMOND_PICKAXE
			};

	public final Material[] AUTOSMELT_BLOCKS = new Material[]
			{
					Material.IRON_ORE,
					Material.GOLD_ORE,
					Material.SAND,
					Material.COBBLESTONE,
					Material.NETHERRACK,
					Material.CACTUS,
					Material.LOG,
					Material.LOG_2,
					Material.SPONGE
			};

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event)	{
		Player player = event.getPlayer();
		Block block = event.getBlock();
		ItemStack item = player.getInventory().getItemInMainHand();
		if ( item == null ) return;
		ItemMeta meta = item.getItemMeta();
		if ( !(bmcBoolean.isBlock(block.getType())) ) return; //ブロックリストに当てはまらない
		if ( !(item.hasItemMeta()) ) return; //アイテムメタが無い
		if ( !(meta.hasLore()) ) return; // loreがない
		if ( !(bmcBoolean.isTool(item.getType())) ) return; //アイテムリストに当てはまらない
		if ( player.getGameMode() != GameMode.SURVIVAL ) return;
		if ( player.getInventory().firstEmpty() == -1 )	{
			player.sendMessage("§c[Error] インベントリに空きが無いためAutoSmeltは実行されません!");
			playsound(player, Sound.BLOCK_ANVIL_LAND, 2);
			return;
		}
		int enchlvl = 0;
		if ( meta.getLore().contains("§4Auto Smelt") ) {
			int durability = item.getDurability();
			int maxdurability = item.getType().getMaxDurability();

			if ( durability != maxdurability ) {
				int nextdurability = item.getDurability() + 1;
				item.setDurability((short) nextdurability);
			} else {
				item.setType(Material.AIR);
			}

			Material type = block.getType();
			event.setCancelled(true);
			block.setType(Material.AIR);

			ItemStack dropitem = null;
			Inventory inventory = player.getInventory();

			if ( type == Material.IRON_ORE ) {
				dropitem = new ItemStack( (Material.IRON_INGOT), enchlvl + 1);
				inventory.addItem(dropitem);
				playsound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2);
			} else if (type == Material.GOLD_ORE ) {
				dropitem = new ItemStack (( Material.GOLD_INGOT), enchlvl + 1);
				inventory.addItem(dropitem);
				playsound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2);
			} else if (type == Material.SAND) {
				dropitem = new ItemStack(( Material.GLASS));
				inventory.addItem(dropitem);
				playsound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2);
			} else if (type == Material.COBBLESTONE) {
				dropitem = new ItemStack((Material.STONE));
				inventory.addItem(dropitem);
				playsound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2);
			} else if (type == Material.NETHERRACK) {
				dropitem = new ItemStack((Material.NETHER_BRICK_ITEM), enchlvl + 1);
				inventory.addItem(dropitem);
				playsound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2);
			} else if (type == Material.LOG || type == Material.LOG_2) {
				dropitem = new ItemStack((Material.COAL), enchlvl + 1, (byte)1);
				inventory.addItem(dropitem);
				playsound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2);
			} else if (type == Material.SPONGE && block.getData() == (byte)1) {
				dropitem = new ItemStack((Material.SPONGE), enchlvl + 1);
				inventory.addItem(dropitem);
				playsound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2);
			} else if (type == Material.CACTUS) {
				dropitem = new ItemStack((Material.INK_SACK), enchlvl + 1, (byte)2);
				inventory.addItem(dropitem);
				playsound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2);
			}
		}
		if( item.containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS) )
		{
			enchlvl = meta.getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS) - 1;
		}
	}

	@EventHandler(priority=EventPriority.MONITOR)
	public void onEnchantment(EnchantItemEvent event)
	{
		Player player = event.getEnchanter();
		ItemStack item = event.getItem();
		ItemMeta meta = item.getItemMeta();
		Material items = item.getType();
		Map<Enchantment, Integer> ench = event.getEnchantsToAdd();
		if (bmcBoolean.isTool(items)) {
			if ( ench != Enchantment.SILK_TOUCH ) {
				double random = Math.random();
				if ( random < 0.1 )
					return;
				else if ( random < 0.6 )
					return;
				else if ( random < 0.7 ) // 30 %
				{
					if ( lore.isEmpty() ) {
						lore.add(0, "§4Auto Smelt");
						player.sendMessage(bmc.PREFIX + "§4Auto Smelt§rエンチャントがつきました！");
						meta.setLore(lore);
						item.setItemMeta(meta);
					} else {
						player.sendMessage(bmc.PREFIX + "§4Auto Smelt§rエンチャントがつきました！");
						meta.setLore(lore);
						item.setItemMeta(meta);
					}
				}
			}
		}
	}

	/**
	 * サウンドを再生します。
	 * @param player プレイヤー名
	 * @param sound サウンド名(Sound.example)
	 * @param pitch float ピッチ
	 */
	private void playsound(Player player, Sound sound, float pitch)	{
		player.playSound(player.getLocation(), sound, 10L, pitch);
	}
}
