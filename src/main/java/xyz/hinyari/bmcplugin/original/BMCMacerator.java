package xyz.hinyari.bmcplugin.original;

import xyz.hinyari.bmcplugin.command.MenuCommand;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BMCMacerator implements Listener
{
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event)
	{
		Inventory inventory = event.getClickedInventory();
		Player player = (Player) event.getWhoClicked();

		// nullの場合 returnする。
		if ( inventory == null )
			return;

		ItemStack item = event.getCurrentItem();

		if ( player.getOpenInventory().getTitle().contains("メニュー") )
		{
			if ( item != null && item.getType() == Material.FLINT )
			{
				if ( item.hasItemMeta() &&
						item.getItemMeta().getDisplayName().contains("粉砕具") )
				{
					event.setCancelled(true);
					return;
				}
			}
		}

		if ( inventory.getName().contains("メニュー") )
		{
			if ( !(event.getSlot() == 20 || event.getSlot() == 24) )
			{

				event.setCancelled(true);
			}
			//if分岐
			if ( item == null )
				return;

			if ( !(item.hasItemMeta()) )
				return;


			ItemMeta meta = item.getItemMeta();

			if ( meta.getDisplayName().contains("OK") )
			{
				if ( inventory.getItem(20) == null )
				{
					player.sendMessage("§c[Error] " + "粉砕したいアイテムを青いガラスの中央に入れる必要があります。");
					player.playSound(player.getLocation(), Sound.BLOCK_NOTE_SNARE, 10, 1);
					return;
				}

				ItemStack slot = inventory.getItem(20);
				ItemStack out = inventory.getItem(24);

				//20番
				//24番
				int amount = slot.getAmount();
				int ramount = 0;

				if ( out != null )
				{
					ramount = out.getAmount();
				}

				if ( ramount == 64 )
					return;

				player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 10L, (float) 1.8);

				if ( slot.getType() == Material.IRON_ORE )
				{
					Material origin = Material.IRON_ORE;
					ItemStack result = new ItemStack((Material.IRON_INGOT), ramount + 2);
					if ( cantMacerator(player, out, result) )
						return;

					RED_Process(inventory, origin, result);
				}
				else if ( slot.getType() == Material.GOLD_ORE ) {
					Material origin = Material.GOLD_ORE;
					ItemStack result = new ItemStack((Material.GOLD_INGOT), ramount + 2);
					if ( cantMacerator(player, out, result) )
						return;

					RED_Process(inventory, origin, result);
				}
				else if ( slot.getType() == Material.MUSHROOM_SOUP ||
						slot.getType() == Material.GHAST_TEAR )
				{
					ItemStack result = new ItemStack((Material.SUGAR), ramount + 9);
					if ( cantMacerator(player, out, result) )
						return;

					if ( slot.containsEnchantment(Enchantment.DURABILITY) )
					{
						if ( slot.getItemMeta().getDisplayName().contains("コシヒカリ") )
						{
							addResult("§6§n米粉", result, Enchantment.DURABILITY,
									1, ItemFlag.HIDE_ENCHANTS);

							if ( amount > 1 )
							{
								slot.setAmount(amount - 1);
							}
							else {
								inventory.remove(Material.GHAST_TEAR);
								inventory.remove(Material.MUSHROOM_SOUP);
							}
							inventory.setItem(24, result);
						}
					}
				}
				else if ( slot.getType() == Material.SANDSTONE ) {
					Material origin = Material.SANDSTONE;
					ItemStack result = new ItemStack((Material.SAND), ramount + 4);
					if ( cantMacerator(player, out, result) )
						return;

					RED_Process(inventory, origin, result);
				}
				else if ( slot.getType() == Material.STONE ) {
					Material origin = Material.STONE;
					ItemStack result = new ItemStack((Material.COBBLESTONE), ramount + 1);
					if ( cantMacerator(player, out, result) )
						return;

					RED_Process(inventory, origin, result);
				}
				else if ( slot.getType() == Material.GRAVEL ) {
					Material origin = Material.FLINT;
					ItemStack result = new ItemStack((Material.FLINT), ramount + 1);
					if ( cantMacerator(player, out, result) )
						return;

					RED_Process(inventory, origin, result);
				}
				else if ( slot.getType() == Material.DIAMOND ) {
					Material origin = Material.DIAMOND;
					ItemStack result = new ItemStack((Material.SUGAR), ramount + 4);
					if ( cantMacerator(player, out, result) )
						return;
					addResult("§b§nダイヤモンドの粉", result, Enchantment.DAMAGE_ALL,
							6, ItemFlag.HIDE_ENCHANTS);

					RED_Process(inventory, origin, result);
				}
				else if ( slot.getType() == Material.DIAMOND_BLOCK ) {
					Material origin = Material.DIAMOND_BLOCK;
					ItemStack result = new ItemStack((Material.COAL), ramount + 4);
					if ( cantMacerator(player, out, result) )
						return;

					addResult("§7炭素", result, Enchantment.DURABILITY,
							6, ItemFlag.HIDE_ENCHANTS);

					RED_Process(inventory, origin, result);
				}
				else {
					player.sendMessage("§c[Error] " + "このアイテムを粉砕することは出来ません。");
					return;
				}
			}
			else if ( meta.getDisplayName().contains("CANCEL") )
			{
				player.closeInventory();
				player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 100, (float) 1.4);
				return;
			}
		}
		else if ( inventory.getName().contains("BMCPlugin") ) {
			event.setCancelled(true);
			if ( item.getType() == Material.LAVA_BUCKET )
			{
				player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BASS, 10L, (float) 2.0);
				player.openInventory(MenuCommand.adminpanel);
			}
		}
	}

	@EventHandler
	public void onInventoryDragEvent(InventoryDragEvent event)
	{
		Inventory inventory = event.getInventory();

		if ( inventory.getName().contains("メニュー") ){
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event)
	{
		Inventory inventory = event.getInventory();
		Player player = (Player) event.getPlayer();
		if ( inventory.getName().contains("メニュー") )
		{
			if ( inventory.getItem(24) != null  )
			{
				ItemStack item = inventory.getItem(24);
				player.getWorld().dropItemNaturally(player.getLocation(), item);
			}

			if ( inventory.getItem(20) != null )
			{
				ItemStack item = inventory.getItem(20);
				player.getWorld().dropItemNaturally(player.getLocation(), item);
			}
		}
	}

	/**
	 * 処理結果スロットに違うアイテムが入ってないかを確認する。
	 * @param player プレイヤー名
	 * @param out ItemStack 結果スロットに入れたいもの
	 * @param result ItemStack 結果スロットに今入っているもの
	 * @return
	 */
	private boolean cantMacerator(Player player, ItemStack out, ItemStack result)
	{
		if ( out != null && out.getType() != result.getType() )
		{
			player.sendMessage("§c[Error] アイテムを取り除いてください。");
			return true;
		}
		return false;
	}

	/*
	private void macerating(int amount, ItemStack slot, Inventory inventory,
			Material origin, ItemStack result)
	{
		if ( amount > 1 ) //Slotが2以上の場合
		{
			slot.setAmount(amount - 1); //slotを一つ減らす
		}
		else { //それ以外の場合
			inventory.remove(origin); //originアイテムを消す
		}
		inventory.setItem(24, result);
	}

	/*
	 * if ( amount > 1 )
					{
						slot.setAmount(amount - 1);
					}
					else {
						slot.setType(Material.AIR);
					}

					inventory.setItem(24, result);
	 */

	/**
	 * 材料スロットの処理
	 * @param inventory 粉砕具インベントリの取得
	 * @param origin
	 * @param result
	 */
	private void RED_Process(Inventory inventory, Material origin, ItemStack result)
	{
		ItemStack slot = inventory.getItem(20); //20番（左）のItemStackを取得する。
		int s_amount = slot.getAmount();
		if ( s_amount > 1 )
		{
			slot.setAmount( s_amount - 1 );
		}
		else {
			inventory.remove(origin);
		}
		inventory.setItem(24, result);
	}

	/**
	 * 結果に特殊効果をつける処理
	 * @param name アイテム名
	 * @param result ItemStack 結果
	 * @param ench エンチャント効果
	 * @param enchlvl エンチャントレベル
	 * @param flag ItemFlag アイテムフラグ
	 */
	private void addResult(String name, ItemStack result, Enchantment ench,
			int enchlvl, ItemFlag flag)
	{
		ItemMeta rmeta = result.getItemMeta();

		rmeta.addEnchant(ench, enchlvl, true);
		rmeta.setDisplayName(name);
		rmeta.addItemFlags(flag);

		result.setItemMeta(rmeta);
	}

	/**
	 * 粉砕具を右クリック時、インベントリを開く
	 * @param event
	 */
	@EventHandler
	public void onRightClick(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();

		if ( event.getAction() == Action.RIGHT_CLICK_AIR
				|| event.getAction() == Action.RIGHT_CLICK_BLOCK )
		{
			ItemStack item = player.getInventory().getItemInMainHand();
			if ( item == null )
				return;

			if ( !(item.hasItemMeta()) )
				return;

			ItemMeta meta = item.getItemMeta();

			if ( item.getType() != Material.FLINT )
				return;

			if ( meta.getDisplayName().contains("粉砕具") )
			{
				openMacerator(player);
			}
		}
	}

	public void openMacerator(Player player)
	{
		Inventory macerator = Bukkit.createInventory(player, 54, "粉砕メニュー");

		ItemStack bluepanel = new ItemStack((Material.STAINED_GLASS_PANE), 1, (byte)3);
		ItemStack okButton = new ItemStack((Material.STAINED_CLAY), 1, (byte)5);
		ItemStack cancelButton = new ItemStack((Material.STAINED_CLAY), 1, (byte)14);
		ItemStack yellowpanel = new ItemStack((Material.STAINED_GLASS_PANE), 1, (byte)4);

		ItemMeta OKB = okButton.getItemMeta();
		ItemMeta CANCELB = cancelButton.getItemMeta();
		ItemMeta bluepanelmeta = bluepanel.getItemMeta();
		ItemMeta yellowpanelmeta = yellowpanel.getItemMeta();

		bluepanelmeta.setDisplayName(" ");
		CANCELB.setDisplayName("§c§lCANCEL");
		OKB.setDisplayName("§a§lOK");
		yellowpanelmeta.setDisplayName(" ");

		bluepanel.setItemMeta(bluepanelmeta);
		okButton.setItemMeta(OKB);
		cancelButton.setItemMeta(CANCELB);
		yellowpanel.setItemMeta(yellowpanelmeta);

		macerator.setItem(10, bluepanel);
		macerator.setItem(11, bluepanel);
		macerator.setItem(12, bluepanel);
		macerator.setItem(19, bluepanel);
		macerator.setItem(21, bluepanel);
		macerator.setItem(28, bluepanel);
		macerator.setItem(29, bluepanel);
		macerator.setItem(30, bluepanel);

		//48

		macerator.setItem(14, yellowpanel);
		macerator.setItem(15, yellowpanel);
		macerator.setItem(16, yellowpanel);
		macerator.setItem(23, yellowpanel);
		macerator.setItem(25, yellowpanel);
		macerator.setItem(32, yellowpanel);
		macerator.setItem(33, yellowpanel);
		macerator.setItem(34, yellowpanel);

		macerator.setItem(48, okButton);

		macerator.setItem(50, cancelButton);

		player.openInventory(macerator);
	}
}
