package com.github.gotochan.Utils;

import com.github.gotochan.BMCPlugin;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

public class BMCUtils
{
	/*
	 * コシヒカリ @ifingr 282 | name &6コシヒカリ | enchant durability 1 | lore &a***中間素材***
	 */
	private BMCPlugin bmc;
	private final ItemStack koshihikari;
	private final List<String> koshihikari_lore;

	public BMCUtils(BMCPlugin bmc) {
		this.bmc = bmc;
		this.koshihikari_lore = new ArrayList<>();
		this.koshihikari_lore.add("&a***中間素材***");
		this.koshihikari = createSpecialItem(new ItemStack(Material.MUSHROOM_SOUP),
				"&6コシヒカリ", this.koshihikari_lore, Enchantment.DURABILITY, 1);
	}

	public ItemStack createSpecialItem(@Nonnull ItemStack item, String name, List<String> lore, Enchantment ench, int enchlvl) {
		if (item == null) return null;
		ItemStack apply = item.clone();
		ItemMeta meta = apply.getItemMeta();
		if (name != null)  { meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name)); }
		if (lore != null)  { meta.setLore(lore); }
		if (ench != null && enchlvl != 0)  { apply.addUnsafeEnchantment(ench, enchlvl); }
		apply.setItemMeta(meta);
		return apply;
	}

	public ItemStack getKoshihikari() {
		return this.koshihikari;
	}


}
