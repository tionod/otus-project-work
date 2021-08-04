package net.otus.edu.enums;

public enum Browser {
    CHROME ("chrome"),
    FIREFOX ("firefox"),
    OPERA ("opera");

    private final String value;

    public String getValue() {
        return value;
    }

    Browser(String value) {
        this.value = value;
    }

    public static boolean containValue(String value) {

        for (Browser browserType : Browser.values()) {
            if (browserType.getValue().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }
}
