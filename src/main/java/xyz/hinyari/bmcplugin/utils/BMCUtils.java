package xyz.hinyari.bmcplugin.utils;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemFlag;
import xyz.hinyari.bmcplugin.BMCPlugin;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import java.util.Arrays;

public class BMCUtils
{
    /*
     * コシヒカリ @ifingr 282 | name &6コシヒカリ | enchant durability 1 | lore &a***中間素材***
     */
    private BMCPlugin bmc;
    private final ItemStack koshihikari;
    private final ItemStack rankitem;

    public BMCUtils(BMCPlugin bmc)
    {
        this.bmc = bmc;
        this.koshihikari = new SpecialItem(new ItemStack(Material.MUSHROOM_SOUP), "&6コシヒカリ", new String[]{
                convert("&a***中間素材***")
        }, Enchantment.DURABILITY, 1, ItemFlag.HIDE_ENCHANTS).getItem();
        this.rankitem = new SpecialItem(new ItemStack(Material.NETHER_BRICK_ITEM), "&aBMCランクメニュー", new String[]{
                convert("&6右クリックで使用")
        }, Enchantment.DURABILITY, 1, ItemFlag.HIDE_ENCHANTS).getItem();
    }

    /*
     * 特殊アイテムを作成するメソッドです。
     *
     * @param item     ItemStack	書き換えるアイテム
     * @param name     String		アイテム名
     * @param lore     String[]	ロール（説明）
     * @param ench     Enchantment	エンチャント
     * @param enchlvl  int			エンチャントレベル
     * @param itemFlag ItemFlag	アイテムフラグ、特殊設定
     * @return ItemStack            特殊効果が付与されたアイテム
     */
    /*
    public ItemStack createSpecialItem(@Nonnull ItemStack item, String name, String[] lore, Enchantment ench, int enchlvl, ItemFlag... itemFlag) {
        if (item == null) return null;
        ItemStack apply = item.clone();
        ItemMeta meta = apply.getItemMeta();
        if (name != null) {
            meta.setDisplayName(convert(name));
            bmc.debug("createSpecialItem:name");
        }
        if (lore != null) {
            meta.setLore(Arrays.asList(lore));
            bmc.debug("createSpecialItem:lore");
        }
        if (ench != null & enchlvl != 0) {
            apply.addUnsafeEnchantment(ench, enchlvl);
            bmc.debug("createSpecialItem:enchantment");
            bmc.debug("enchantment " + ench.getName());
            bmc.debug("enchantmentlevel " + apply.getEnchantmentLevel(ench));
        }
        if (itemFlag != null) {
            meta.addItemFlags(itemFlag);
            bmc.debug("createSpecialItem:itemflag");
        }
        apply.setItemMeta(meta);
        bmc.debug("createSpecialItem:finished");
        return apply;
    }
    */

    public ItemStack createByteableItem(Material material, byte value)
    {
        return new ItemStack(material, 1, (short) 0, (byte) value);
    }

    public ItemStack getKoshihikari()
    {
        return this.koshihikari;
    }

    public static String convert(String message)
    {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public ItemStack getRankitem()
    {
        return this.rankitem;
    }

    /**
     * 指定したStringが数字であるかを判定
     *
     * @param num 判定したいString型
     * @return boolean
     */
    public boolean isNumber(String num)
    {
        try
        {
            Integer.parseInt(num);
            return true;
        } catch (NumberFormatException e)
        {
            return false;
        }
    }


}
