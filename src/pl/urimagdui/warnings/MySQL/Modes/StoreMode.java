package pl.urimagdui.warnings.MySQL.Modes;

public enum StoreMode  {
    SQLITE("sqlite"),
    MYSQL("mysql");

    private String name;

    private StoreMode(final String name) {
        this.name = name;
    }

    public static StoreMode getByName(final String name) {
        for (final StoreMode sm : values()) {
            if (sm.getName().equalsIgnoreCase(name)) {
                return sm;
            }
        }
        return SQLITE;
    }

    public String getName() {
        return this.name;
    }
}