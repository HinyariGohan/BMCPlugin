package xyz.hinyari.bmcplugin.original;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Hinyari_Gohan on 2016/10/08.
 */
public enum DyeItem
{
    BLACK(new ItemStack(Material.INK_SACK, 1, (short) 0, (byte) 0), DyeColor.BLACK, (byte) 0, (byte) 15), BLUE(new ItemStack(Material.INK_SACK, 1, (short) 0, (byte) 4), DyeColor.BLUE, (byte) 4, (byte) 11), BROWN(new ItemStack(Material.INK_SACK, 1, (short) 0, (byte) 3), DyeColor.BROWN, (byte) 3, (byte) 12), CYAN(new ItemStack(Material.INK_SACK, 1, (short) 0, (byte) 6), DyeColor.CYAN, (byte) 6, (byte) 9), GRAY(new ItemStack(Material.INK_SACK, 1, (short) 0, (byte) 8), DyeColor.GRAY, (byte) 8, (byte) 7), GREEN(new ItemStack(Material.INK_SACK, 1, (short) 0, (byte) 2), DyeColor.GREEN, (byte) 2, (byte) 13), LIGHT_BLUE(new ItemStack(Material.INK_SACK, 1, (short) 0, (byte) 12), DyeColor.LIGHT_BLUE, (byte) 12, (byte) 3), LIME(new ItemStack(Material.INK_SACK, 1, (short) 0, (byte) 10), DyeColor.LIME, (byte) 10, (byte) 5), MAGENDA(new ItemStack(Material.INK_SACK, 1, (short) 0, (byte) 13), DyeColor.MAGENTA, (byte) 13, (byte) 2), ORANGE(new ItemStack(Material.INK_SACK, 1, (short) 0, (byte) 14), DyeColor.ORANGE, (byte) 14, (byte) 1), PINK(new ItemStack(Material.INK_SACK, 1, (short) 0, (byte) 9), DyeColor.PINK, (byte) 9, (byte) 6), PURPLE(new ItemStack(Material.INK_SACK, 1, (short) 0, (byte) 5), DyeColor.PURPLE, (byte) 5, (byte) 10), RED(new ItemStack(Material.INK_SACK, 1, (short) 0, (byte) 1), DyeColor.RED, (byte) 1, (byte) 14), SILVER(new ItemStack(Material.INK_SACK, 1, (short) 0, (byte) 7), DyeColor.SILVER, (byte) 7, (byte) 8), WHITE(new ItemStack(Material.INK_SACK, 1, (short) 0, (byte) 15), DyeColor.WHITE, (byte) 15, (byte) 0), YELLOW(new ItemStack(Material.INK_SACK, 1, (short) 0, (byte) 11), DyeColor.YELLOW, (byte) 11, (byte) 4);

    private final ItemStack itemStack;
    private final DyeColor dyeColor;
    private final byte data_value;
    private final byte panel;

    private DyeItem(final ItemStack itemStack, final DyeColor dyeColor, final byte data_value, final byte panel)
    {
        this.itemStack = itemStack;
        this.dyeColor = dyeColor;
        this.data_value = data_value;
        this.panel = panel;
    }

    public ItemStack getItemStack()
    {
        return this.itemStack;
    }

    public DyeColor getDyeColor()
    {
        return this.dyeColor;
    }

    public byte getData_Value()
    {
        return this.data_value;
    }

    public byte getGlassByte()
    {
        return this.panel;
    }

}
