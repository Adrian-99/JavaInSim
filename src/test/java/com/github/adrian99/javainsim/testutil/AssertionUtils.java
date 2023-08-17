/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.testutil;

import org.awaitility.core.ConditionTimeoutException;
import com.github.adrian99.javainsim.api.common.enums.DefaultCar;
import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;
import com.github.adrian99.javainsim.api.common.flags.Flags;
import com.github.adrian99.javainsim.api.common.structures.Car;
import com.github.adrian99.javainsim.internal.insim.packets.base.InfoPacket;
import com.github.adrian99.javainsim.internal.common.enums.EnumHelpers;
import com.github.adrian99.javainsim.internal.insim.packets.util.PacketReader;

import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;

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

    public static void assertRequestPacketBytesEqual(byte[] expectedBytes, byte[] actualBytes) {
        assertEquals(expectedBytes.length, actualBytes.length, "Incorrect request packet bytes length");
        for (var i = 0; i < expectedBytes.length; i++) {
            if (i != 2) {
                assertEquals(expectedBytes[i], actualBytes[i], "Incorrect request packet byte at position " + i);
            } else {
                assertNotEquals(0, actualBytes[i], "Request packet reqI byte is 0");
            }
        }
    }

    public static <T extends Enum<?>> void assertFlagsEqual(Class<T> enumClass,
                                                            Set<T> expectedFlagsValues,
                                                            Flags<T> actualFlags) {
        var enumHelper = EnumHelpers.get(enumClass);
        for (var enumValue : enumHelper.getAllValuesCached()) {
            if (expectedFlagsValues.contains(enumValue)) {
                assertTrue(actualFlags.hasFlag(enumValue), "Missing value " + enumValue + " in flags");
            } else {
                assertTrue(actualFlags.hasNoFlag(enumValue), "Unexpected value " + enumValue + " in flags");
            }
        }
    }

    public static void assertCarEquals(DefaultCar expectedCar, Car actualCar) {
        assertTrue(
                !actualCar.isMod() && actualCar.getDefaultCar().isPresent(),
                "Expected default car, was mod car"
        );
        assertEquals(expectedCar, actualCar.getDefaultCar().get(), "Unexpected car");
        assertEquals(expectedCar.name(), actualCar.getSkinId(), "Unexpected skin ID");
    }

    public static void assertCarEquals(String expectedModSkinId, Car actualCar) {
        assertTrue(
                actualCar.isMod() && actualCar.getDefaultCar().isEmpty(),
                "Expected mod car, was default car"
        );
        assertEquals(expectedModSkinId, actualCar.getSkinId(), "Unexpected skin ID");
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
