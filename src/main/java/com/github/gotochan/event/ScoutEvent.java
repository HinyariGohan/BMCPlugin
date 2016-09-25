package com.github.gotochan.event;

import com.github.gotochan.BMCPlugin;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import com.github.gotochan.Utils.BMCBoolean;

public class ScoutEvent implements Listener
{
	public static final String KIT_NAME = "scout";
	public static final String KIT_SKILL = "立体機動装置のようなアイテムを使うことが出来ます。";

	public static final String GRAPPLE_NAME = "§6Grapple§r";

	public static ItemStack grappleItem = new ItemStack(Material.FISHING_ROD);
	public static ItemMeta im = grappleItem.getItemMeta();

	private BMCPlugin bmc;
	private BMCBoolean bmcBoolean;

	public ScoutEvent(BMCPlugin bmc) {
		this.bmc = bmc;
		this.bmcBoolean = bmc.bmcBoolean;
	}

	/**
	 * Scout的なイベント。
	 * @param event
	 */
	@EventHandler
	public void onGrapplingEvent(PlayerFishEvent event) {
		Player player = event.getPlayer();
		ItemStack item = player.getInventory().getItemInMainHand();
		if ( bmcBoolean.isGrappleItem(item) ) {
			Location hookLoc = event.getHook().getLocation().clone().add(0,-1,0);
			if(hookLoc.getBlock().getType().isSolid())
			{
				Location playerloc = player.getLocation();
				Location loc = event.getHook().getLocation();
				if (playerloc.distance(loc) < 3.0D)	{
					pullPlayerSlightly(player, loc);
				} else {
					pullEntityToLocation(player, loc);
				}
				item.setDurability((short) 0);
				player.playSound(player.getLocation(), Sound.ITEM_HOE_TILL, 10, 2);
			} else {
				hookLoc = event.getHook().getLocation().clone().add(0, 1, 0);
				if (!hookLoc.getBlock().getType().isSolid()) {
					return;
				}
				Location playerloc = player.getLocation();
				Location loc = event.getHook().getLocation();
				if (playerloc.distance(loc) < 3.0D)	{
                    pullPlayerSlightly(player, loc);
                } else {
                    pullEntityToLocation(player, loc);
                }
				item.setDurability((short) 0);
				player.playSound(player.getLocation(), Sound.ITEM_HOE_TILL, 10, 2);
			}
		}
	}

	/**
	 * GrappleItem所持時、落下ダメージを半分に軽減する。
	 * @param event
	 */
	@EventHandler
	public void onDamageRegenEvent(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if (event.getCause().equals(DamageCause.FALL)) {
				if (bmcBoolean.isGrappleItem(player.getInventory().getItemInMainHand())) {
					if (player.isSneaking()) {
						event.setCancelled(true);
					}else { event.setDamage(event.getDamage()/2); }
				}
			}
		}

	}

	/**
	 * プレイヤーを指定したロケーションに飛ばす
	 * @param p
	 * @param loc
	 */
	private void pullPlayerSlightly(Player p, Location loc)
	{
		int locx = loc.getBlockX();
		int locy = loc.getBlockY() - 1;
		int locz = loc.getBlockZ();
		Location bloc = new Location(loc.getWorld(), locx, locy, locz);
		if (bloc.getBlock().getType().equals(Material.WATER))
			return;
		if (loc.getY() > p.getLocation().getY())
		{
			p.setVelocity(new Vector(0.0D, 0.25D, 0.0D));
			return;
		}
		Location playerLoc = p.getLocation();

		Vector vector = loc.toVector().subtract(playerLoc.toVector());
		p.setVelocity(vector);
	}

	/**
	 * プレイヤーを指定したロケーションに飛ばす
	 * @param e Player
	 * @param loc
	 */
	private void pullEntityToLocation(Player e, Location loc)
	{
		Location entityLoc = e.getLocation();

		entityLoc.setY(entityLoc.getY() + 0.5D);
		e.teleport(entityLoc);

		double g = -0.08D;
		double d = loc.distance(entityLoc);
		double t = d;

		double v_x = (0.9D + 0.10D * t) * (loc.getX() - entityLoc.getX()) / t;
		double v_y = (1.3D + 0.05D * t) * (loc.getY() - entityLoc.getY()) / t - 0.5D * g * t;
		double v_z = (0.9D + 0.10D * t) * (loc.getZ() - entityLoc.getZ()) / t;

		Vector v = e.getVelocity();
		v.setX(v_x);
		v.setY(v_y);
		v.setZ(v_z);
		e.setVelocity(v);
	}
}

