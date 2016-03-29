package com.github.gotochan;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.gotochan.Koshihikari.KoshihikariCommand;
import com.github.gotochan.Koshihikari.KoshihikariEvent;
import com.github.gotochan.Rank.BMCRankCommand;
import com.github.gotochan.Rank.BMCRankUpCommand;

/**
 * BMCオリジナルプラグイン メインクラス
 * @author Hinyari_Gohan
 */


public class BMC
	extends JavaPlugin implements Listener {

	public static String prefix = ChatColor.GREEN + "[BMCPlugin] " + ChatColor.WHITE;
	public static String inthegame = "ゲーム内で実行して下さい。";
	public static String lotargs = prefix + "引数は必要ありません。";
	public static String example = prefix + ChatColor.GOLD + "Example: ";


	@Override
	public void onEnable() {
		this.getLogger().info("BMCプラグインを開始しています。");
		Bukkit.getServer().getScoreboardManager().getMainScoreboard();
		getCommand("koshihikari").setExecutor( new KoshihikariCommand() );
		getCommand("rank").setExecutor( new BMCRankCommand() );
		getCommand("nannte").setExecutor( new KusoCommand() );
		getCommand("rankup").setExecutor( new BMCRankUpCommand() );
		getServer().getPluginManager().registerEvents(new KoshihikariEvent(), this);
		getServer().getPluginManager().registerEvents(new BMCEvent(), this);
		getServer().getPluginManager().registerEvents(this, this);
		getConfig().options().copyDefaults(true);
		saveConfig();
	}

	@Override
	public void onDisable() {
		this.getLogger().info("BMCプラグインを終了しています。");
	}

	@Override
	public void onLoad() {
		this.getLogger().info("BMCプラグインを読み込んでいます。");
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	  {
		if ( cmd.getName().equalsIgnoreCase("bmc")) {
			if ( args.length == 0 ) {
				sender.sendMessage(BMC.prefix + "");
			}
		}
	    if ((cmd.getName().equalsIgnoreCase("lpreload")) && (sender.hasPermission("lp.reload")))
	    {
	      getServer().reload();
	      reloadConfig();
	      sender.sendMessage(ChatColor.GREEN + "Plugin reloaded!");
	    }
	    if ((cmd.getName().equalsIgnoreCase("lpreloadcf")) && (sender.hasPermission("lpcf.reload"))) {
	      reloadConfig();
	    }
	    /* if (cmd.getName().equalsIgnoreCase("lpinfo")) {
	      if (sender.hasPermission("ppl.info"))
	      {
	        PluginDescriptionFile p = getDescription();
	        sender.sendMessage(ChatColor.GREEN + "Title: " + p.getName());
	        sender.sendMessage(ChatColor.GREEN + "Version: " + p.getVersion());
	        sender.sendMessage(ChatColor.GREEN + "Author: " + p.getAuthors());
	      }
	      else
	      {
	        sender.sendMessage(ChatColor.RED + "Invalid permissions!");
	      }
	    }
	    **/
	    return true;
	  }

	  static long LIMIT = 3000L;
	  @SuppressWarnings({ "unchecked", "rawtypes" })
	Map<String, Long> launchTimes = new HashMap();

	  @EventHandler
	  public void onPlayerStep(PlayerInteractEvent e)
	  {
	    if (e.getAction().equals(Action.PHYSICAL))
	    {
	      Player p = e.getPlayer();
	      if ((e.getClickedBlock().getType().equals(Material.STONE_PLATE)) || (e.getClickedBlock().getType().equals(Material.IRON_PLATE)))
	      {
	        Block b = e.getClickedBlock().getLocation().subtract(0.0D, 1.0D, 0.0D).getBlock();
	        FileConfiguration config = getConfig();
	        if (((config.getString("launchpads").equals("true")) || ((config.getString("launchpads").equals("true")) && (config.getString("permissions").equals("true")) && (p.hasPermission("launch.pads")))) &&
	          (b != null) &&
	          (b.getType().equals(Material.REDSTONE_BLOCK)))
	        {
	          Location l = p.getLocation().add(0.0D, 1.0D, 0.0D);
	          p.teleport(l);
	          p.setVelocity(p.getVelocity().add(p.getLocation().getDirection().multiply(config.getInt("multiply"))).setY(config.getInt("yaxis")));
	          this.launchTimes.put(p.getName(), Long.valueOf(System.currentTimeMillis()));
	          if (!config.getString("sound").equalsIgnoreCase("false"))
	          {
	        	  p.playSound(p.getLocation(), Sound.WITHER_SHOOT, (float)LIMIT, (float) 2.0);
	          }
	        }
	      }
	    }
	  }

	  @EventHandler
	  public void onBlock(BlockPlaceEvent e)
	  {
	    FileConfiguration config = getConfig();
	    if ((!config.getString("anyonecanmake").equals("false")) &&
	      (!e.getPlayer().hasPermission("ppl.place")))
	    {
	      Block b = e.getBlockPlaced().getLocation().subtract(0.0D, 1.0D, 0.0D).getBlock();
	      if ((e.getBlockPlaced().getType().equals(Material.STONE_PLATE)) && (b.getType().equals(Material.REDSTONE_BLOCK)))
	      {
	        e.getBlockPlaced().setType(Material.AIR);
	        e.getPlayer().sendMessage(ChatColor.RED + "You do not have permission to create ppl's!");
	      }
	    }
	  }

	  @EventHandler
	  public void anotherEvent(EntityDamageEvent event)
	  {
	    if ((event.getEntity() instanceof Player))
	    {
	      Player player = (Player)event.getEntity();
	      Long time = (Long)this.launchTimes.get(player.getName());
	      if (time != null) {
	        if (System.currentTimeMillis() - time.longValue() < LIMIT) {
	          event.setCancelled(true);
	        } else {
	          this.launchTimes.remove(player.getName());
	        }
	      }
	    }
	  }

	  public boolean Rankhelp(CommandSender sender) {
		sender.sendMessage(ChatColor.GOLD + "");
		return false;
	  }

	  public static String equal(CommandSender sender, String content) {
		  String equals = ChatColor.YELLOW + "========" + content + "========";
		return equals;

	  }
}
