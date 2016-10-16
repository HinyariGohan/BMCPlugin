package xyz.hinyari.bmcplugin.rank;

import com.avaje.ebeaninternal.server.jmx.MAdminAutofetch;
import org.bukkit.material.Dye;
import xyz.hinyari.bmcplugin.original.DyeItem;

/**
 * Created by Hinyari_Gohan on 2016/09/03.
 */
public enum Rank {
    VISITOR(0, "§7Visitor§r", DyeItem.SILVER),
    RED(1, "§4Red§r", DyeItem.RED),
    ORANGE(2, "§6Orange§r", DyeItem.ORANGE),
    YELLOW(3, "§eYellow§r", DyeItem.YELLOW),
    GREEN(4, "§2Green§r", DyeItem.GREEN),
    BLUE(5, "§9Blue§r", DyeItem.LIGHT_BLUE),
    INDIGO(6, "§1Indigo§r", DyeItem.BLUE),
    VIOLET(7, "§5Violet§r", DyeItem.MAGENDA),
    ULTRAVIOLET(8, "§fUltraViolet§r", DyeItem.WHITE),
    INFRARED(9, "§cInfraRed§r", null),
    MAXIMUM(10, "§a上限に達しました§r", null),
    NONE(11, "§c未到達ランク§r", DyeItem.GRAY);

    private final int id;
    private final String name;
    private final DyeItem dyeItem;

    private Rank(final int id, final String name, final DyeItem dyeItem) {
        this.id = id;
        this.name = name;
        this.dyeItem = dyeItem;
    }

    public int getInt() {
        return this.id;
    }

    public DyeItem getDyeItem() { return this.dyeItem; }

    public Rank getType(final int id) {
        Rank[] ranks = Rank.values();
        for (Rank rank : ranks) {
            if (rank.getInt() == id) {
                return rank;
            }
        }
        return null;
    }

    public String getName() {
        return this.name;
    }

    public static String getLabelOfName(int value) {
        String label = "";
        for (Rank rank : Rank.values()) {
            if (rank.getInt() == value) {
                label = rank.getName();
                break;
            }
        }
        return label;
    }

    public static Rank getLabelOfRank(int value) {
        for (Rank rank : Rank.values()) {
            if (rank.getInt() == value) {
                return rank;
            }
        }
        return null;
    }

    public static int getNumber(Rank rank) {
        for (Rank r : Rank.values()) {
            int i = 0;
            if (rank == r) {
                return i;
            }
            i++;
        }
        return 0;
    }

    public static Rank getLabelOfRank(String name) {
        for (Rank r : Rank.values()) {
            if (name.toUpperCase().equals(r.toString())) {
                if (r == NONE || r == MAXIMUM) return null;
                return r;
            }
        }
        return null;
    }
}
