package pl.adrian.api.packets.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Enumeration for LFS product
 */
public enum Product {
    /**
     * LFS Demo
     */
    DEMO("DEMO"),
    /**
     * LFS S1
     */
    S1("S1"),
    /**
     * LFS S2
     */
    S2("S2"),
    /**
     * LFS S3
     */
    S3("S3");

    private static Map<String, Product> allValuesCached;
    private final String value;

    Product(String value) {
        this.value = value;
    }

    /**
     * Converts string value to enum value
     * @param value string value
     * @return enum value
     */
    public static Product fromString(String value) {
        if (allValuesCached == null) {
            allValuesCached = new HashMap<>();
            for (var enumValue : values()) {
                allValuesCached.put(enumValue.value, enumValue);
            }
        }
        return allValuesCached.get(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
