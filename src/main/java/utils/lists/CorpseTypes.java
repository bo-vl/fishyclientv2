package utils.lists;

public enum CorpseTypes {
    LAPIS("Lapis Armor Helmet", "Lapis"),
    TUNGSTEN("Mineral Helmet", "Tungsten"),
    UMBER("Yog Helmet", "Umber"),
    VANGUARD("Vanguard Helmet", "Vanguard");

    private final String itemName;
    private final String displayName;

    CorpseTypes(String itemName, String displayName) {
        this.itemName = itemName;
        this.displayName = displayName;
    }

    public String getItemName() {
        return itemName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static CorpseTypes getByDisplayName(String name) {
        for (CorpseTypes type : values()) {
            if (type.displayName.equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }
}