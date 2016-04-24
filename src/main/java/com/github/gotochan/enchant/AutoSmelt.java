package com.github.gotochan.enchant;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
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

import com.github.gotochan.BMC;

public class AutoSmelt implements Listener
{
	private int enchlvl;
	
	public static final String AUTO_SMELT = "Auto Smelt";
	
	public static List<String> lore = new ArrayList<String>();
	
	public static final Material[] AUTOSMELT_ITEMS = new Material[]
			{
					Material.WOOD_PICKAXE,
					Material.STONE_PICKAXE,
					Material.IRON_PICKAXE,
					Material.GOLD_PICKAXE,
					Material.DIAMOND_PICKAXE
			};
	
	public static final Material[] AUTOSMELT_BLOCKS = new Material[]
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
	public void onBlockBreak(BlockBreakEvent event)
	{
		Player player = event.getPlayer();
		Block block = event.getBlock();
		ItemStack item = player.getItemInHand();
		World world = block.getWorld();
		
		if ( item == null )
		{
			return;
		}
		
		if ( !(isBlock(block.getType())) )
		{
			return;
		}
		
		if ( !(item.hasItemMeta()) )
		{
			return;
		}
		
		ItemMeta meta = item.getItemMeta();
		
		if ( !(meta.hasLore()) )
		{
			return;
		}
		
		if ( !(isTool(item.getType())) )
		{
			return;
		}
		
		if ( player.getGameMode() != GameMode.SURVIVAL )
		{
			return;
		}
		
		if ( player.getInventory().firstEmpty() == -1 )
		{
			player.sendMessage("§c[Error] インベントリに空きが無いためAutoSmeltは実行されません!");
			playsound(player, Sound.ANVIL_LAND, 2);
			return;
		}
		
		if ( meta.getLore().contains("§4Auto Smelt") )
		{
			int durability = item.getDurability();
			int maxdurability = item.getType().getMaxDurability();
			
			if ( durability != maxdurability )
			{
				int nextdurability = item.getDurability() + 1;
				item.setDurability((short) nextdurability);
			}
			else {
				item.setType(Material.AIR);
			}
			
			Location loc = block.getLocation();
			Material type = block.getType();
			event.setCancelled(true);
			block.setType(Material.AIR);
			
			ItemStack dropitem = null;
			Inventory inventory = player.getInventory();
			
			if ( type == Material.IRON_ORE )
			{
				dropitem = new ItemStack( (Material.IRON_INGOT), enchlvl + 1);
				inventory.addItem(dropitem);
				playsound(player, Sound.ORB_PICKUP, 2);
			}
			else if (type == Material.GOLD_ORE )
			{
				dropitem = new ItemStack (( Material.GOLD_INGOT), enchlvl + 1);
				inventory.addItem(dropitem);
				playsound(player, Sound.ORB_PICKUP, 2);
			}
			else if (type == Material.SAND)
			{
				dropitem = new ItemStack(( Material.GLASS));
				inventory.addItem(dropitem);
				playsound(player, Sound.ORB_PICKUP, 2);
			}
			else if (type == Material.COBBLESTONE) {
				dropitem = new ItemStack((Material.STONE));
				inventory.addItem(dropitem);
				playsound(player, Sound.ORB_PICKUP, 2);
			}
			else if (type == Material.NETHERRACK) {
				dropitem = new ItemStack((Material.NETHER_BRICK_ITEM), enchlvl + 1);
				inventory.addItem(dropitem);
				playsound(player, Sound.ORB_PICKUP, 2);
				
			}
			else if (type == Material.LOG || type == Material.LOG_2) {
				dropitem = new ItemStack((Material.COAL), enchlvl + 1, (byte)1);
				inventory.addItem(dropitem);
				playsound(player, Sound.ORB_PICKUP, 2);
			}
			else if (type == Material.SPONGE && block.getData() == (byte)1) {
				dropitem = new ItemStack((Material.SPONGE), enchlvl + 1);
				inventory.addItem(dropitem);
				playsound(player, Sound.ORB_PICKUP, 2);
			}
			else if (type == Material.CACTUS) {
				dropitem = new ItemStack((Material.INK_SACK), enchlvl + 1, (byte)2);
				inventory.addItem(dropitem);
				playsound(player, Sound.ORB_PICKUP, 2);
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
		if ( isTool(items) )
		{
			if ( ench != Enchantment.SILK_TOUCH )
			{
				double random = Math.random();
				if ( random < 0.1 ) {
					return;
				}
				else if ( random < 0.6 ) {
					return;
				}
				else if ( random < 0.7 ) // 30 %
				{
					
					if ( lore.isEmpty() )
					{
						lore.add(0, "§4Auto Smelt");
						player.sendMessage(BMC.prefix + "§4Auto Smelt§rエンチャントがつきました！");
						meta.setLore(lore);
						item.setItemMeta(meta);
					}
					else {
						player.sendMessage(BMC.prefix + "§4Auto Smelt§rエンチャントがつきました！");
						meta.setLore(lore);
						item.setItemMeta(meta);
					}
				}
			}
		}
	}
	
	public static boolean isTool(Material item)
	{
		if ( item == Material.WOOD_PICKAXE ||
				item == Material.WOOD_SPADE ||
				item == Material.WOOD_AXE ||
				item == Material.STONE_PICKAXE ||
				item == Material.STONE_AXE ||
				item == Material.STONE_SPADE ||
				item == Material.IRON_PICKAXE ||
				item == Material.IRON_AXE ||
				item == Material.IRON_SPADE ||
				item == Material.GOLD_AXE ||
				item == Material.GOLD_PICKAXE ||
				item == Material.GOLD_SPADE ||
				item == Material.DIAMOND_AXE ||
				item == Material.DIAMOND_PICKAXE ||
				item == Material.DIAMOND_SPADE )
		{
			return true;
		}
		return false;
	}
	
	public boolean isBlock(Material block)
	{
		if ( block == Material.IRON_ORE ||
				block == Material.GOLD_ORE ||
				block == Material.SAND ||
				block == Material.COBBLESTONE ||
				block == Material.NETHERRACK ||
				block == Material.CACTUS ||
				block == Material.LOG ||
				block == Material.LOG_2 ||
				block == Material.SPONGE )
		{
			return true;
		}
		return false;
	}
	
	private void playsound(Player player, Sound sound, float pitch)
	{
		player.playSound(player.getLocation(), sound, 10L, pitch);
	}
}
