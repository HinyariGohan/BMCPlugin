package xyz.hinyari.bmcplugin.original;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Hinyari_Gohan on 2016/10/08.
 */
public enum DyeItem {
    BLACK(new ItemStack(Material.INK_SACK, 1, (short)0, (byte)0), DyeColor.BLACK, 0),
    BLUE(new ItemStack(Material.INK_SACK, 1, (short)0, (byte)4), DyeColor.BLUE, 4),
    BROWN(new ItemStack(Material.INK_SACK, 1, (short)0, (byte)3), DyeColor.BROWN, 3),
    CYAN(new ItemStack(Material.INK_SACK, 1, (short)0, (byte)6), DyeColor.CYAN, 6),
    GRAY(new ItemStack(Material.INK_SACK, 1, (short)0, (byte)8), DyeColor.GRAY, 8),
    GREEN(new ItemStack(Material.INK_SACK, 1, (short)0, (byte)2), DyeColor.GREEN, 2),
    LIGHT_BLUE(new ItemStack(Material.INK_SACK, 1, (short)0, (byte)12), DyeColor.LIGHT_BLUE, 12),
    LIME(new ItemStack(Material.INK_SACK, 1, (short)0, (byte)10), DyeColor.LIME, 10),
    MAGENDA(new ItemStack(Material.INK_SACK, 1, (short)0, (byte)13), DyeColor.MAGENTA, 13),
    ORANGE(new ItemStack(Material.INK_SACK, 1, (short)0, (byte)14), DyeColor.ORANGE, 14),
    PINK(new ItemStack(Material.INK_SACK, 1, (short)0, (byte)9), DyeColor.PINK, 9),
    PURPLE(new ItemStack(Material.INK_SACK, 1, (short)0, (byte)5), DyeColor.PURPLE, 5),
    RED(new ItemStack(Material.INK_SACK, 1, (short)0, (byte)1), DyeColor.RED, 1),
    SILVER(new ItemStack(Material.INK_SACK, 1, (short)0, (byte)7), DyeColor.SILVER, 7),
    WHITE(new ItemStack(Material.INK_SACK, 1, (short)0, (byte)15), DyeColor.WHITE, 15),
    YELLOW(new ItemStack(Material.INK_SACK, 1, (short)0, (byte)11), DyeColor.YELLOW, 11);

    private final ItemStack itemStack;
    private final DyeColor dyeColor;
    private final int data_value;

    private DyeItem(final ItemStack itemStack, final DyeColor dyeColor, final int data_value) {
        this.itemStack = itemStack;
        this.dyeColor = dyeColor;
        this.data_value = data_value;
    }

    public ItemStack getItemStack() {
        return this.itemStack;
    }

    public DyeColor getDyeColor() {
        return this.dyeColor;
    }

    public int getData_Value() {
        return this.data_value;
    }

}
