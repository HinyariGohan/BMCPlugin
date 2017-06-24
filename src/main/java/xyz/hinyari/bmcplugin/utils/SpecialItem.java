package xyz.hinyari.bmcplugin.utils;

import org.bukkit.ChatColor;
import org.bukkit.Utility;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by Hinyari_Gohan on 2016/10/12.
 */
public class SpecialItem
{

    private final ItemStack itemStack;
    private String name;
    private String[] lore;
    private Enchantment enchantment;
    private Map<Enchantment, Integer> enchantments;
    private int enchlvl;
    private ItemFlag[] itemFlags;
    private byte b;

    private ItemStack resurt;

    /**
     * 名前のみのアイテムを作成します。
     *
     * @param itemStack 作成する元となるアイテム
     * @param name      名前
     */
    public SpecialItem(@Nonnull ItemStack itemStack, String name)
    {
        this.itemStack = itemStack;
        this.name = name;
        create();
    }

    /**
     * 説明文付きアイテムを作成します。
     *
     * @param itemStack 作成する元となるアイテム
     * @param name      名前
     * @param lore      説明文(LORE)
     */
    public SpecialItem(@Nonnull ItemStack itemStack, String name, String[] lore)
    {
        this.itemStack = itemStack;
        this.name = name;
        this.lore = lore;
        create();
    }

    /**
     * エンチャント付きアイテムを作成します。
     *
     * @param itemStack   作成する元となるアイテム
     * @param name        名前
     * @param lore        説明文
     * @param enchantment エンチャント
     * @param enchlvl     エンチャントレベル
     */
    public SpecialItem(@Nonnull ItemStack itemStack, String name, String[] lore, Enchantment enchantment, int enchlvl)
    {
        this.itemStack = itemStack;
        this.name = name;
        this.lore = lore;
        this.enchantment = enchantment;
        this.enchlvl = enchlvl;
        create();
    }

    /**
     * アイテムフラグ付きアイテムを作成します。
     *
     * @param itemStack   作成する元となるアイテム
     * @param name        名前
     * @param lore        説明文
     * @param enchantment エンチャント
     * @param enchlvl     エンチャントレベル
     * @param itemFlags   アイテムフラグ
     */
    public SpecialItem(@Nonnull ItemStack itemStack, String name, String[] lore, Enchantment enchantment, int enchlvl, ItemFlag... itemFlags)
    {
        this.itemStack = itemStack;
        this.name = name;
        this.lore = lore;
        this.enchantment = enchantment;
        this.enchlvl = enchlvl;
        this.itemFlags = itemFlags;
        create();
    }

    /**
     * 複数のエンチャント付きアイテムを作成します。
     *
     * @param itemStack    作成する元となるアイテム
     * @param name         名前
     * @param lore         説明文
     * @param enchantments マップ（エンチャント、エンチャントレベル】
     * @param itemFlags    アイテムフラグ
     */
    public SpecialItem(@Nonnull ItemStack itemStack, String name, String[] lore, Map<Enchantment, Integer> enchantments, ItemFlag... itemFlags)
    {
        this.itemStack = itemStack;
        this.name = name;
        this.lore = lore;
        this.enchantments = enchantments;
        this.itemFlags = itemFlags;
        create();
    }

    /**
     * バイト（メタデータ）付きアイテムを作成します。
     *
     * @param itemStack 作成する元となるアイテム
     * @param name      名前
     * @param lore      説明文
     * @param b         byte
     */
    public SpecialItem(@Nonnull ItemStack itemStack, String name, String[] lore, byte b)
    {
        this.itemStack = itemStack;
        this.name = name;
        this.lore = lore;
        this.b = b;
        create();
    }

    private void create()
    {
        resurt = itemStack.clone();
        ItemMeta meta = resurt.getItemMeta();
        if (name != null)
        {
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        }
        if (lore != null)
        {
            List<String> listLore = new ArrayList<>();

            for (String st : Arrays.asList(lore))
            {
                listLore.add(ChatColor.translateAlternateColorCodes('&', st));
            }
            meta.setLore(listLore);
        }
        if (enchantments != null)
        {
            resurt.addUnsafeEnchantments(enchantments);
        }
        if (itemFlags != null)
        {
            meta.addItemFlags(itemFlags);
        }
        if (b != 0)
        {
            resurt = addMaterialData(resurt, new MaterialData(resurt.getType(), b));
        }
        resurt.setItemMeta(meta);
        if (enchantment != null && enchlvl != 0)
        {
            resurt = addEnchant(resurt, enchantment, enchlvl);
        }
    }

    /**
     * ItemStack型を返します。
     *
     * @return ItemStack
     */
    @Utility
    public ItemStack getItem()
    {
        return this.resurt;
    }

    /**
     * アイテムの名前を返します。
     *
     * @return String          名前
     */
    @Utility
    public String getName()
    {
        return this.name;
    }

    /**
     * 説明文を返します。
     *
     * @return List<String>    説明文
     */
    @Utility
    public List<String> getLore()
    {
        return Arrays.asList(this.lore);
    }

    /**
     * エンチャントメントを返します。
     *
     * @return Enchantment     エンチャントメント
     */
    @Utility
    public Enchantment getEnchantment()
    {
        return this.enchantment;
    }

    /**
     * エンチャントレベルを返します。
     *
     * @return int             エンチャントレベル
     */
    @Utility
    public int getEnchantLevel()
    {
        return this.enchlvl;
    }

    /**
     * 複数のエンチャントメントを返します。
     *
     * @return Map<Enchantment, Integer>   複数のエンチャントメント
     */
    @Utility
    public Map<Enchantment, Integer> getEnchantments()
    {
        return this.enchantments;
    }

    /**
     * アイテムフラグを返します。
     *
     * @return ItemFlag[]      複数のアイテムフラグ
     */
    @Utility
    public ItemFlag[] getItemFlags()
    {
        return this.itemFlags;
    }

    private ItemStack addEnchant(ItemStack itemStack, Enchantment ench, int enchlvl)
    {
        itemStack.addUnsafeEnchantment(ench, enchlvl);
        return itemStack;
    }

    private ItemStack addMaterialData(ItemStack itemStack, MaterialData materialData)
    {
        itemStack.setData(materialData);
        return itemStack;
    }


}
