package pl.adrian.packets.enums;

import java.util.HashMap;
import java.util.Map;

public enum Product {
    DEMO("DEMO"),
    S1("S1"),
    S2("S2"),
    S3("S3");

    private static Map<String, Product> allValuesCached;
    private final String value;

    Product(String value) {
        this.value = value;
    }

    public static Product fromString(String value) {
        if (allValuesCached == null) {
            allValuesCached = new HashMap<>();
            for (var enumValue : values()) {
                allValuesCached.put(enumValue.value, enumValue);
            }
        }
        return allValuesCached.get(value);
    }
}
