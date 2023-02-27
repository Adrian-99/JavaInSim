package pl.adrian.internal.packets.enums;

/**
 * This class is a helper that provides useful methods to operating on enums.
 * Instances of this class are managed by {@link EnumHelpers}.
 * @param <T> type of enum that is handler by helper instance
 */
public class EnumHelper<T extends Enum<?>> {
    private final T[] allValuesCached;

    @SuppressWarnings("unchecked")
    EnumHelper(Class<T> enumClass) {
        try {
            this.allValuesCached = (T[]) enumClass.getMethod("values").invoke(enumClass);
        } catch (Exception exception) {
            throw new IllegalStateException("Something went wrong while creating EnumHelper", exception);
        }
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
        return allValuesCached[ordinal];
    }
}
