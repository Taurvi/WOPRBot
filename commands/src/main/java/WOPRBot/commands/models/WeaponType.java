package WOPRBot.commands.models;

public enum WeaponType {
    SWORD("Sword", "sword"),
    RAPIER("Rapier", "rapier"),
    HATCHET("Hatchet", "hatchet"),
    SPEAR("Spear", "spear"),
    GREAT_AXE("Great Axe", "greataxe"),
    WAR_HAMMER("War Hammer", "warhammer"),
    BOW("Bow", "bow"),
    MUSKET("Musket", "musket"),
    FIRE_STAFF("Fire Staff", "firestaff"),
    LIFE_STAFF("Life Staff", "lifestaff"),
    ICE_GAUNTLET("Ice Gauntlet", "icegauntlet"),
    VOID_GAUNTLET("Void Gauntlet", "voidgauntlet"),
    UNKNOWN("Unknown", "unknown");

    private String name;
    private String value;

    WeaponType(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public String getValue() {
        return this.value;
    }

    public static WeaponType fromValue(String value) {
        switch(value) {
            case "sword":
                return WeaponType.SWORD;
            case "rapier":
                return WeaponType.RAPIER;
            case "hatchet":
                return WeaponType.HATCHET;
            case "spear":
                return WeaponType.SPEAR;
            case "greataxe":
                return WeaponType.GREAT_AXE;
            case "warhammer":
                return WeaponType.WAR_HAMMER;
            case "bow":
                return WeaponType.BOW;
            case "musket":
                return WeaponType.MUSKET;
            case "firestaff":
                return WeaponType.FIRE_STAFF;
            case "lifestaff":
                return WeaponType.LIFE_STAFF;
            case "icegauntlet":
                return WeaponType.ICE_GAUNTLET;
            case "voidgauntlet":
                return WeaponType.VOID_GAUNTLET;
            default:
                return WeaponType.UNKNOWN;
        }
    }
}
