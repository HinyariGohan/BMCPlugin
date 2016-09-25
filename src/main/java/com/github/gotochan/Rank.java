package com.github.gotochan;

/**
 * Created by Hinyari_Gohan on 2016/09/03.
 */
public enum Rank {
    VISITOR(0, "§7Visitor§r"),
    RED(1, "§4Red§r"),
    ORANGE(2, "§6Orange§r"),
    YELLOW(3, "§eYellow§r"),
    GREEN(4, "§2Green§r"),
    BLUE(5, "§9Blue§r"),
    INDIGO(6, "§1Indigo§r"),
    VIOLET(7, "§5Violet§r"),
    ULTRAVIOLET(8, "§fUltraViolet§r"),
    INFRARED(9, "§cInfraRed§rT"),
    ERROR(10, "§a上限に達しました§r");

    private final int id;
    private final String name;

    private Rank(final int id, final String name) {
        this.id = id;
        this.name = name;
    }

    public int getInt() {
        return this.id;
    }

    public Rank getType(final int id) {
        Rank[] ranks = Rank.values();
        for (Rank rank : ranks) {
            if (rank.getInt() == id) {
                return rank;
            }
        }
        return null;
    }

    public String getName(Rank rank) {
        return this.name;
    }

    public static String getLabelOfName(int value) {
        String label = "";
        for (Rank rank : Rank.values()) {
            if (rank.getInt() == value) {
                label = rank.getName(rank);
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
}
