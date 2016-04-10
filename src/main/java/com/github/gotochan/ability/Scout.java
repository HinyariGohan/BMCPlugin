package com.github.gotochan.ability;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import com.github.gotochan.BMC;
import com.github.gotochan.command.KitCommand;
import com.github.gotochan.command.SubKitAbst;
import com.github.gotochan.resource.BMCBoolean;

public class Scout extends SubKitAbst implements Listener{
	
	public static final String KIT_NAME = "scout";
	public static final String KIT_SKILL = "立体機動装置のようなアイテムを使うことが出来ます。";
	
	public static final String GRAPPLE_NAME = "§6Grapple";
	
	public ItemStack Grapple() {
		ItemStack grappleItem = new ItemStack(Material.FISHING_ROD);
		return grappleItem;
	}
	ItemStack grappleItem = new ItemStack(Material.FISHING_ROD);
	ItemMeta im = grappleItem.getItemMeta();
	
	@Override
	public String getKitName()
	{
		return KIT_NAME;
	}
	
	@Override
	public boolean runKitCommand(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		
		// /bmc kit scoutのみだったらScoutのExampleを返す。
		if ( args.length == 2 )
		{
			return KitCommand.KitExample(sender, KIT_NAME, KIT_SKILL);
		}
		// ではなく get が実行されたらプレイヤーにグラップルを渡す
		else if ( args[2].equalsIgnoreCase("get") )
		{
			// 権限持っているか
			if ( player.hasPermission("bmc.scout.get") )
			{
				im.setDisplayName(GRAPPLE_NAME);
				grappleItem.setItemMeta(im);
				player.getInventory().addItem(grappleItem);
				player.sendMessage(GRAPPLE_NAME + BMC.r + "を手に入れました。");
			}
		}
		// give が実行されたらgiveプレイヤーにgiveする
		else if ( args[2].equalsIgnoreCase("give") )
		{
			if ( args.length == 3 ) {
				sender.sendMessage("Usage: " + BMC.g + "/bmc scout give <プレイヤー名>");
			} else {
				Player GivePlayer = getPlayer(args[3]);
				// playerがいるか
				if ( GivePlayer == null )
				{
					sender.sendMessage("そのプレイヤーは存在しません。");
				}
				else
					//いれば
				{
					Inventory inventory = GivePlayer.getInventory();
					inventory.addItem(grappleItem);
				}
			}
		}
		return false;
	}
	
	private Player getPlayer(String name)
	{
		for ( Player player : Bukkit.getOnlinePlayers() )
		{
			if ( player.getName().equals(name) )
			{
				return player;
			}
		}
		return null;
	}
	
	@EventHandler
	public void onGrapplingEvent(PlayerFishEvent event) {
		Player player = event.getPlayer();
		if ( BMCBoolean.isGrappleItem(player.getItemInHand()) ) {
			if ( Acrobat.canAcrobating.get(player) == "true") {
				player.sendMessage("アクロバットモードを無効にする必要があります。");
			} else {
				Location hookLoc = event.getHook().getLocation().clone().add(0,-1,0);
				if(hookLoc.getBlock().getType().isSolid())
				{
					Location playerloc = player.getLocation();
					Location loc = event.getHook().getLocation();
					if (playerloc.distance(loc) < 3.0D)
					{
						pullPlayerSlightly(player, loc);
					} else
					{
						pullEntityToLocation(player, loc);
					}
					player.getItemInHand().setDurability((short) 0);
					player.playSound(player.getLocation(), Sound.NOTE_STICKS, 10, 1);
				}
			}
		}
	}
	
	private void pullPlayerSlightly(Player p, Location loc)
	{
		if (loc.getY() > p.getLocation().getY())
		{
			p.setVelocity(new Vector(0.0D, 0.25D, 0.0D));
			return;
		}
		
		Location playerLoc = p.getLocation();
		
		Vector vector = loc.toVector().subtract(playerLoc.toVector());
		p.setVelocity(vector);
	}
	
	private void pullEntityToLocation(Entity e, Location loc)
	{
		Location entityLoc = e.getLocation();
		
		entityLoc.setY(entityLoc.getY() + 0.5D);
		e.teleport(entityLoc);
		
		double g = -0.08D;
		double d = loc.distance(entityLoc);
		double t = d;
		double v_x = (1.0D + 0.07000000000000001D * t)
				* (loc.getX() - entityLoc.getX()) / t;
		double v_y = (1.0D + 0.03D * t) * (loc.getY() - entityLoc.getY()) / t
				- 0.5D * g * t;
		double v_z = (1.0D + 0.07000000000000001D * t)
				* (loc.getZ() - entityLoc.getZ()) / t;
		
		Vector v = e.getVelocity();
		v.setX(v_x);
		v.setY(v_y);
		v.setZ(v_z);
		e.setVelocity(v);
	}
	
}
