/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets;

import org.junit.jupiter.api.Test;
import com.github.adrian99.javainsim.api.common.flags.Flags;
import com.github.adrian99.javainsim.api.insim.packets.flags.ButtonInstFlag;
import com.github.adrian99.javainsim.api.insim.packets.flags.ButtonStyle;
import com.github.adrian99.javainsim.internal.insim.packets.enums.ValidationFailureCategory;
import com.github.adrian99.javainsim.internal.insim.packets.exceptions.PacketValidationException;

import static org.junit.jupiter.api.Assertions.*;

class BtnPacketTest {
    @Test
    void createBtnPacket_forTypeInButton() {
        var packet = new BtnPacket(
                144,
                34,
                201,
                new Flags<>(ButtonInstFlag.ALWAYS_ON),
                new Flags<>(ButtonStyle.COLOUR_OK, ButtonStyle.CLICK, ButtonStyle.DARK, ButtonStyle.RIGHT),
                60,
                true,
                30,
                120,
                25,
                10,
                "Button text",
                "Dialog caption"
        );
        var bytes = packet.getBytes();
        var expectedBytes = new byte[] {
                10, 45, -112, 34, -55, -128, -84, -68, 30, 120, 25, 10, 0, 68, 105, 97,
                108, 111, 103, 32, 99, 97, 112, 116, 105, 111, 110, 0, 66, 117, 116, 116,
                111, 110, 32, 116, 101, 120, 116, 0
        };

        assertArrayEquals(expectedBytes, bytes);
    }

    @Test
    void createBtnPacket_forNonTypeInButton() {
        var packet = new BtnPacket(
                144,
                34,
                201,
                new Flags<>(ButtonInstFlag.ALWAYS_ON),
                new Flags<>(ButtonStyle.COLOUR_OK, ButtonStyle.CLICK, ButtonStyle.DARK, ButtonStyle.RIGHT),
                30,
                120,
                25,
                10,
                "Button text"
        );
        var bytes = packet.getBytes();
        var expectedBytes = new byte[] {
                6, 45, -112, 34, -55, -128, -84, 0, 30, 120, 25, 10, 66, 117, 116, 116,
                111, 110, 32, 116, 101, 120, 116, 0
        };

        assertArrayEquals(expectedBytes, bytes);
    }

    @Test
    void createBtnPacket_forButtonUpdate() {
        var packet = new BtnPacket(
                144,
                34,
                201,
                "New text",
                "New caption"
        );
        var bytes = packet.getBytes();
        var expectedBytes = new byte[] {
                9, 45, -112, 34, -55, 0, 0, 0, 0, 0, 0, 0, 0, 78, 101, 119,
                32, 99, 97, 112, 116, 105, 111, 110, 0, 78, 101, 119, 32, 116, 101, 120,
                116, 0, 0, 0
        };

        assertArrayEquals(expectedBytes, bytes);
    }

    @Test
    void createBtnPacket_withTooHighTypeInValue() {
        var instFlags = new Flags<>(ButtonInstFlag.ALWAYS_ON);
        var bStyleFlags = new Flags<>(ButtonStyle.COLOUR_OK, ButtonStyle.CLICK, ButtonStyle.DARK, ButtonStyle.RIGHT);

        var exception = assertThrows(
                PacketValidationException.class,
                () -> new BtnPacket(
                        144,
                        34,
                        201,
                        instFlags,
                        bStyleFlags,
                        96,
                        true,
                        30,
                        120,
                        25,
                        10,
                        "Button text",
                        "Dialog caption"
                )
        );
        assertEquals("typeIn", exception.getFieldName());
        assertEquals(ValidationFailureCategory.VALUE_OUT_OF_RANGE, exception.getFailureCategory());
    }
}
