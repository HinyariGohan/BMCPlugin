package com.github.gotochan.kit.kits;

import com.github.gotochan.BMCPlugin;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.github.gotochan.kit.BMCKit;
import com.github.gotochan.kit.IconPackage;
import com.github.gotochan.kit.KitUtils;

public class Scout extends BMCKit
{

	private BMCPlugin bmc;

	public Scout(BMCPlugin bmc) {
		super(bmc);
		this.bmc = bmc;
	}

	private final ItemStack _GRAPPLE = KitUtils.addSoulBound(new ItemStack(Material.FISHING_ROD));
	private final ItemStack GRAPPLE = bmc.utils.createSpecialItem(_GRAPPLE, "§6Grapple", null, null, 0);



	@Override
	public void Initialize()
	{
	}

	@Override
	public String getName()
	{
		return "Scout";
	}

	@Override
	public IconPackage getIconPackage()
	{
		return new IconPackage(new ItemStack(Material.FISHING_ROD),
				new String[] {
						""
		}
				);
	}

	@Override
	public boolean canSelect(Player p_player)
	{
		return true;
	}

	@Override
	public void onPlayerSpawn(Player p_player)
	{
		p_player.getInventory().addItem(new ItemStack[] { GRAPPLE } );
	}

	@Override
	public void cleanup(Player p_player)
	{
	}

}
