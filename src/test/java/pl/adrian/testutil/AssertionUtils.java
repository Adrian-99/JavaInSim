package pl.adrian.testutil;

import org.opentest4j.AssertionFailedError;
import pl.adrian.api.packets.enums.DefaultCar;
import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.api.packets.flags.Flags;
import pl.adrian.api.packets.structures.Car;
import pl.adrian.internal.packets.base.InfoPacket;
import pl.adrian.internal.packets.enums.EnumHelpers;
import pl.adrian.internal.packets.util.PacketReader;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
