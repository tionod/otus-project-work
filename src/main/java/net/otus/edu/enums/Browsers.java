package net.otus.edu.enums;

public enum Browsers {
    CHROME ("chrome"),
    FIREFOX ("firefox"),
    OPERA ("opera");

    private final String value;

    public String getValue() {
        return value;
    }

    Browsers(String value) {
        this.value = value;
    }

    public static boolean containValue(String value) {

        for (Browsers browserType : Browsers.values()) {
            if (browserType.getValue().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }
}
