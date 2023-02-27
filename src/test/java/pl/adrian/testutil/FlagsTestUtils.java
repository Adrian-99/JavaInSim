package pl.adrian.testutil;

import org.opentest4j.AssertionFailedError;
import pl.adrian.api.packets.flags.Flags;
import pl.adrian.internal.packets.enums.EnumHelpers;

import java.util.Set;

public class FlagsTestUtils {
    public static <T extends Enum<?>> void assertFlagsEqual(Class<T> enumClass,
                                                            Set<T> expectedFlagsValues,
                                                            Flags<T> actualFlags) {
        var enumHelper = EnumHelpers.get(enumClass);
        for (var enumValue : enumHelper.getAllValuesCached()) {
            if (expectedFlagsValues.contains(enumValue)) {
                if (!actualFlags.hasFlag(enumValue)) {
                    throw new AssertionFailedError("Missing value " + enumValue + " in flags");
                }
            } else {
                if (!actualFlags.hasNoFlag(enumValue)) {
                    throw new AssertionFailedError("Unexpected value " + enumValue + " in flags");
                }
            }
        }
    }
}
