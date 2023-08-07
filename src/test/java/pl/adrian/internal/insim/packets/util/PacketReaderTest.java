/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package pl.adrian.internal.insim.packets.util;

import org.junit.jupiter.api.Test;
import pl.adrian.api.insim.packets.enums.PacketType;
import pl.adrian.internal.insim.packets.exceptions.PacketReadingException;

import static org.junit.jupiter.api.Assertions.*;

class PacketReaderTest {

    @Test
    void packetReader_tooShortHeader() {
        var headerBytes = new byte[] { 1, 1 };
        assertThrows(PacketReadingException.class, () -> new PacketReader(headerBytes));
    }

    @Test
    void packetReader_tooLongHeader() {
        var headerBytes = new byte[] { 1, 1, 1, 1 };
        assertThrows(PacketReadingException.class, () -> new PacketReader(headerBytes));
    }

    @Test
    void read_forUnrecognizedReadablePacketType() {
        var headerBytes = new byte[] { 1, 0, 0 };
        var dataBytes = new byte[] { 0 };
        var packetBuilder = new PacketReader(headerBytes);

        assertEquals(PacketType.NONE, packetBuilder.getPacketType());
        assertEquals(1, packetBuilder.getDataBytesCount());
        assertEquals(0, packetBuilder.getPacketReqI());
        assertThrows(PacketReadingException.class, () -> packetBuilder.read(dataBytes));
    }
}
