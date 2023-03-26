package pl.adrian.internal.packets.enums;

/**
 * This class is a helper that provides useful methods to operating on enums.
 * Instances of this class are managed by {@link EnumHelpers}.
 * @param <T> type of enum that is handler by helper instance
 */
public class EnumHelper<T extends Enum<?>> {
    private final Class<T> enumClass;
    private final T[] allValuesCached;

    EnumHelper(Class<T> enumClass) {
        this.enumClass = enumClass;
        this.allValuesCached = enumClass.getEnumConstants();
    }

    /**
     * @return cached array of all possible enum values
     */
    public T[] getAllValuesCached() {
        return allValuesCached;
    }

    /**
     * Converts ordinal number to enum value.
     * @param ordinal ordinal number
     * @return enum value
     */
    public T fromOrdinal(int ordinal) {
        if (EnumWithCustomValue.class.isAssignableFrom(enumClass)) {
            for (var enumValue : allValuesCached) {
                if (((EnumWithCustomValue) enumValue).getValue() == ordinal) {
                    return enumValue;
                }
            }
            return allValuesCached[0];
        } else {
            return allValuesCached[ordinal];
        }
    }
}
