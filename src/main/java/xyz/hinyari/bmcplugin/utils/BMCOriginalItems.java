package xyz.hinyari.bmcplugin.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Hinyari_Gohan on 2017/04/27.
 */
public enum BMCOriginalItems
{

    LOW_PURITY_DIAMOND_POWDER(new SpecialItem(new ItemStack(Material.SUGAR), "&7純度の低い&bダイヤモンドの粉", new String[]{
            "&a***中間素材***&r"
    }, Enchantment.DAMAGE_ALL, 4, ItemFlag.HIDE_ENCHANTS)),

    DIAMOND_POWDER(new SpecialItem(new ItemStack(Material.SUGAR), "&b&nダイヤモンドの粉", null, Enchantment.DAMAGE_ALL, 6, ItemFlag.HIDE_ENCHANTS)),

    KOSHIHIKARI(new SpecialItem(new ItemStack(Material.MUSHROOM_SOUP), "&6コシヒカリ", new String[]{
            "&a***中間素材***&r"
    }, Enchantment.DURABILITY, 1, ItemFlag.HIDE_ENCHANTS)),

    KOME_POWDER(new SpecialItem(new ItemStack(Material.SUGAR), "&6&n米粉", new String[]{
            "&a***中間素材***&r"
    }, Enchantment.DURABILITY, 2, ItemFlag.HIDE_ENCHANTS)),;


    private SpecialItem item;


    private BMCOriginalItems(final SpecialItem specialItem)
    {
        this.item = specialItem;
    }

}
