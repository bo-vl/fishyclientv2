package utils.lists;

import java.util.Arrays;
import java.util.List;

public enum Island {
    SinglePlayer("Singleplayer"),
    PrivateIsland("Private Island"),
    Garden("The Garden"),
    SpiderDen("Spider's Den"),
    CrimsonIsle("Crimson Isle"),
    TheEnd("The End"),
    GoldMine("Gold Mine"),
    DeepCaverns("Deep Caverns"),
    DwarvenMines("Dwarven Mines"),
    CrystalHollows("Crystal Hollows"),
    FarmingIsland("The Farming Islands"),
    ThePark("The Park"),
    Dungeon("Catacombs"),
    DungeonHub("Dungeon Hub"),
    Hub("Hub"),
    DarkAuction("Dark Auction"),
    JerryWorkshop("Jerry's Workshop"),
    Kuudra("Kuudra"),
    Mineshaft("Mineshaft"),
    Unknown("(Unknown)");

    private final String displayName;

    Island(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean isArea(Island area) {
        return this == area;
    }

    public boolean isArea(Island... areas) {
        for (Island area : areas) {
            if (this == area) {
                return true;
            }
        }
        return false;
    }

    public static List<Island> entries() {
        return Arrays.asList(Island.values());
    }

    public static boolean isArea(String areaName) {
        return Arrays.stream(Island.values())
                .anyMatch(island -> island.getDisplayName().equalsIgnoreCase(areaName));
    }
}