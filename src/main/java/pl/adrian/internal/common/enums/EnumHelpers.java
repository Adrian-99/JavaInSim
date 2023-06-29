package pl.adrian.internal.common.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is a helper that manages {@link EnumHelper} instances.
 */
public class EnumHelpers {
    private static final Map<Class<?>, EnumHelper<?>> enumHelpersMap = new HashMap<>();

    private EnumHelpers() {}

    /**
     * Returns {@link EnumHelper} instance for specified enum. If such helper wasn't created before,
     * this method will create it first.
     * @param enumClass class of the enum that {@link EnumHelper} will handle
     * @return appropriate {@link EnumHelper} instance
     * @param <T> type of the enum that {@link EnumHelper} will handle
     */
    @SuppressWarnings("unchecked")
    public static <T extends Enum<?>> EnumHelper<T> get(Class<T> enumClass) {
        enumHelpersMap.computeIfAbsent(enumClass, newEnumClass -> new EnumHelper<>(enumClass));
        return (EnumHelper<T>) enumHelpersMap.get(enumClass);
    }
}
