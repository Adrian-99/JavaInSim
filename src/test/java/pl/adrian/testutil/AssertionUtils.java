package pl.adrian.testutil;

import org.awaitility.core.ConditionTimeoutException;
import org.opentest4j.AssertionFailedError;
import pl.adrian.api.common.enums.DefaultCar;
import pl.adrian.api.insim.packets.enums.PacketType;
import pl.adrian.api.common.flags.Flags;
import pl.adrian.api.common.structures.Car;
import pl.adrian.internal.insim.packets.base.InfoPacket;
import pl.adrian.internal.common.enums.EnumHelpers;
import pl.adrian.internal.insim.packets.util.PacketReader;

import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AssertionUtils {
    public static void assertPacketHeaderEquals(int expectedDataBytesCount,
                                                PacketType expectedPacketType,
                                                int expectedReqI,
                                                PacketReader packetReader) {
        assertEquals(expectedPacketType, packetReader.getPacketType());
        assertEquals(expectedDataBytesCount, packetReader.getDataBytesCount());
        assertEquals(expectedReqI, packetReader.getPacketReqI());
    }

    public static void assertPacketHeaderEquals(int expectedSize,
                                                PacketType expectedPacketType,
                                                int expectedReqI,
                                                InfoPacket packet) {
        assertEquals(expectedSize, packet.getSize());
        assertEquals(expectedPacketType, packet.getType());
        assertEquals(expectedReqI, packet.getReqI());
    }

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

    public static void assertCarEquals(DefaultCar expectedCar, Car actualCar) {
        if (actualCar.isMod() || actualCar.getDefaultCar().isEmpty()) {
            throw new AssertionFailedError("Expected default car, was mod car");
        }
        if (!actualCar.getDefaultCar().get().equals(expectedCar) || !actualCar.getSkinId().equals(expectedCar.name())) {
            throw new AssertionFailedError("Expected: " + expectedCar + ", was: " + actualCar.getDefaultCar().get());
        }
    }

    public static void assertCarEquals(String expectedModSkinId, Car actualCar) {
        if (!actualCar.isMod() || actualCar.getDefaultCar().isPresent()) {
            throw new AssertionFailedError("Expected mod car, was default car");
        }
        if (!actualCar.getSkinId().equals(expectedModSkinId)) {
            throw new AssertionFailedError("Expected: " + expectedModSkinId + ", was: " + actualCar.getSkinId());
        }
    }

    public static void assertConditionMet(Callable<Boolean> condition, int atMostMs, int pollIntervalMs) {
        await().atMost(atMostMs, TimeUnit.MILLISECONDS)
                .with().pollInterval(pollIntervalMs, TimeUnit.MILLISECONDS)
                .until(condition);
    }

    public static void assertConditionNotMet(Callable<Boolean> condition, int withinMs) {
        assertThrows(
                ConditionTimeoutException.class,
                () -> assertConditionMet(condition, withinMs, withinMs / 2)
        );
    }
}
