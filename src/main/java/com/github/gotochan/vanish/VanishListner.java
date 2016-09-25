package com.github.gotochan.vanish;

import java.util.Collection;
import java.util.HashMap;
import java.util.function.Predicate;

import com.github.gotochan.BMCPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;

/**
 * VanishListner.class
 * @author Shun
 * @see #VanishListner(BMCPlugin)
 *
 */
public class VanishListner implements Listener
{
	BMCPlugin plugin;

	HashMap<Player, Boolean> vanished = new HashMap<>();

	/* イベントをキャンセルする */
	/* イベントリスト
	 *
	 */

	/**
	 * VanishListner.class コンストラクター
	 * @param plugin BMCPlugin.class
	 */
	public VanishListner(BMCPlugin plugin)
	{
		this.plugin = plugin;
	}

	/**
	 * {@link #isVanishPlayer(Player)} <br></br>
	 * プレイヤーがvanish状態であるかどうか
	 * @param player 対象となるプレイヤー
	 * @return boolean
	 */
	private boolean isVanishPlayer(Player player)
	{
		if ( this.vanished.containsKey(player) &&
				this.vanished.get(player).equals(true))
			return true;

		return false;
	}

	/**
	 * vanishコマンドを処理
	 * @param player 送信プレイヤー
	 * @param args argment
	 */
	public boolean onVanishCommand(Player player, String[] args)
	{
		if (!player.hasPermission("bmc.vanish"))
		{
			player.sendMessage("§c< 権限がありません! >");
			return true;
		}
		if (isVanishPlayer(player))
		{
			this.vanished.put(player, false);

			sendPermMessage("§a§l[ BMCAdmin ]§r§a "
					+ player.getName() + " が監視(透明化)を終了しました。", "bmc.vanish.notice");
			Bukkit.getOnlinePlayers().stream().filter(player1 -> !player1.equals(player)).forEach(player1 -> player1.showPlayer(player));
			return true;
		}
		else
		{
			this.vanished.put(player, true);
			sendPermMessage("§a§l[ BMCAdmin ]§r§a "
					+ player.getName() + " が監視(透明化)を開始しました。", "bmc.vanish.notice");
			Bukkit.getOnlinePlayers().stream().filter(player1 -> !player1.equals(player)).forEachOrdered(player1 -> player1.hidePlayer(player));
			return true;
		}
	}

	/*
	 * イベント処理部分
	 */


	/**
	 * プレイヤーアニメーションをキャンセル
	 * @param event
	 */
	@EventHandler
	public void onPlayerAnimationEvent(PlayerAnimationEvent event)
	{
		Player player = event.getPlayer();
		if (isVanishPlayer(player))
		{
			event.setCancelled(true);
		}
	}

	/**
	 * プレイヤーログイン時にプレイヤーを非表示にする
	 * @param event
	 */
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		Bukkit.getOnlinePlayers().stream().filter((Predicate<Player>) this::isVanishPlayer).forEach(player1 -> event.getPlayer().hidePlayer(player1));
	}

	/**
	 * プレイヤーのアイテムドロップを禁止する
	 * @param event
	 */
	@EventHandler
	public void onPlayerDropItemEvent(PlayerDropItemEvent event)
	{
		Player player = event.getPlayer();
		if (isVanishPlayer(player))
		{
			event.setCancelled(true);
		}
	}

	/**
	 * エンティティに対するInteractをキャンセルする
	 * @param event
	 */
	@EventHandler
	public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent event)
	{
		if ( !(event.getRightClicked() instanceof Player) ) return;
		Player player = event.getPlayer();
		if (isVanishPlayer(player))
		{
			event.setCancelled(true);
		}
	}

	/**
	 * プレイヤーのアイテムの消費をキャンセルする
	 * @param event
	 */
	@EventHandler
	public void onPlayerItemConsumeEvent(PlayerItemConsumeEvent event)
	{
		Player player = event.getPlayer();
		if (isVanishPlayer(player))
		{
			event.setCancelled(true);
		}
	}

	/**
	 * インベントリのオープンをキャンセルする
	 * @param event
	 */
	@EventHandler
	public void onInventoryOpenEvent(InventoryOpenEvent event)
	{
		if ( event.getInventory() instanceof Chest || event.getInventory() instanceof DoubleChest )
		{
			Player player = (Player) event.getPlayer();
			if (isVanishPlayer(player))
			{
				event.setCancelled(true);
			}
		}
	}

	/**
	 * ブロックの破壊をキャンセルする
	 * @param event
	 */
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event)
	{
		Player player = event.getPlayer();
		if (isVanishPlayer(player))
		{
			event.setCancelled(true);
		}
	}

	/**
	 * プレイヤーのInteractをキャンセルしつつ、チェストの場合は
	 * {@link Player#openInventory(Inventory)}を叩く
	 * @param event
	 */
	@EventHandler
	public void onPlayerInteractEvent(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		if (isVanishPlayer(player) )
		{
			if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
			{
				Material material = event.getClickedBlock().getType();
				if (material.equals(Material.CHEST) || material.equals(Material.TRAPPED_CHEST))
				{
					Chest chest = (Chest) event.getClickedBlock().getState();
					Inventory inventory = Bukkit.createInventory(player, chest.getInventory().getSize());
					inventory.setContents(chest.getInventory().getContents());
					event.setCancelled(true);
					player.sendMessage("§a[ BMCVanish ] チェストの中身を変更することは出来ません。");
					player.openInventory(inventory);
				} else if (material.equals(Material.FURNACE) ||
						(material.equals(Material.BURNING_FURNACE)) ||
						(material.equals(Material.DISPENSER)) ||
						(material.equals(Material.DROPPER)) ||
						(material.equals(Material.BREWING_STAND)))
					return;
				event.setCancelled(true);
			}

		}
	}

	/**
	 * エンティティへのダメージをキャンセルする
	 * @param event
	 */
	@EventHandler
	public void onEntityDamage(EntityDamageEvent event)
	{
		if (event.getEntityType().equals(EntityType.PLAYER))
		{
			Player player = (Player) event.getEntity();
			if (isVanishPlayer(player))
			{
				event.setCancelled(true);
			}
		}
	}

	/**
	 * エンティティからのダメージをキャンセルする
	 * @param event
	 */
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event)
	{
		if (event.getDamager().getType().equals(EntityType.PLAYER))
		{
			Player player = (Player) event.getDamager();
			if (isVanishPlayer(player))
			{
				event.setCancelled(true);
			}
		}
	}

	/**
	 * パーミッションメッセージを送信
	 * 指定したパーミッションを持っている人のみにメッセージを送信します。<br></br>
	 * {@link Bukkit#broadcast(String, String)}
	 * @param message メッセージ内容
	 * @param permission パーミッション
	 */
	private void sendPermMessage(String message, String permission)
	{
		Bukkit.broadcast(message, permission);
	}
}
