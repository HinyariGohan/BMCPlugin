package xyz.hinyari.bmcplugin.kit;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import xyz.hinyari.bmcplugin.BMCPlugin;

public class RunnerKit extends BMCKit
{
	public RunnerKit(BMCPlugin bmc)
	{
		super(bmc);
	}
	
	private static final PotionEffect S_EFFECT = new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0);
	
	@Override
	public void Initialize() {}
	
	@Override
	public String getName()
	{
		return "Runner";
	}
	
	@Override
	public IconPackage getIconPackage()
	{
		return new IconPackage(new ItemStack(Material.DIAMOND_BOOTS),
				new String[]{
						""
		});
	}
	
	@Override
	public void onPlayerSpawn(Player player)
	{
		player.addPotionEffect(S_EFFECT);
	}
	
	@Override
	public void cleanup(Player player) {}
	
	@Override
	public boolean canSelect(Player player)
	{
		return true;
	}
	
}
