package utils.lists;

public enum CorpseTypes {
    LAPIS("Lapis Armor Helmet"),
    TUNGSTEN("Mineral Helmet"),
    UMBER("Yog Helmet"),
    VANGUARD("Vanguard Helmet");

    private final String itemName;

    CorpseTypes(String itemName) {
        this.itemName = itemName;
    }

    public String getItemName() {
        return itemName;
    }
}