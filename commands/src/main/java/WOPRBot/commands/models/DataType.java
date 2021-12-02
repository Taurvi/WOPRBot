package WOPRBot.commands.models;

public enum DataType {
    PRIMARY_WEAPON("primary_weapon"),
    SECONDARY_WEAPON("secondary_weapon");

    private String value;

    DataType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
