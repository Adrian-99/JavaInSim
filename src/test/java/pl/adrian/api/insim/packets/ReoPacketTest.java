package pl.adrian.api.insim.packets;

import org.junit.jupiter.api.Test;
import pl.adrian.api.insim.packets.enums.PacketType;
import pl.adrian.internal.insim.packets.util.PacketReader;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.adrian.testutil.AssertionUtils.assertPacketHeaderEquals;

class ReoPacketTest {
    @Test
    void createReoPacket() {
        var packet = new ReoPacket(List.of(
                1, 17, 5, 2, 18, 19, 28, 21, 3, 23, 14, 11, 9, 7, 6, 25,
                27, 30, 15, 20, 26, 22, 4, 16, 31, 29, 10, 12, 13, 24, 8
        ));
        var bytes = packet.getBytes();
        var expectedBytes = new byte[] {
                11, 36, 0, 31, 1, 17, 5, 2, 18, 19, 28, 21, 3, 23, 14, 11,
                9, 7, 6, 25, 27, 30, 15, 20, 26, 22, 4, 16, 31, 29, 10, 12,
                13, 24, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0
        };

        assertArrayEquals(expectedBytes, bytes);
    }

    @Test
    void readReoPacket() {
        var headerBytes = new byte[] { 11, 36, -112 };
        var dataBytes = new byte[] {
                23, 1, 5, 6, 22, 2, 4, 3, 21, 15, 12, 14, 7, 13, 8, 10,
                20, 19, 16, 17, 9, 23, 11, 18, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0
        };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(41, PacketType.REO, 144, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof ReoPacket);

        var castedReadPacket = (ReoPacket) readPacket;

        assertPacketHeaderEquals(44, PacketType.REO, 144, castedReadPacket);
        assertEquals(23, castedReadPacket.getNumP());
        assertEquals(
                List.of(
                        (short) 1,
                        (short) 5,
                        (short) 6,
                        (short) 22,
                        (short) 2,
                        (short) 4,
                        (short) 3,
                        (short) 21,
                        (short) 15,
                        (short) 12,
                        (short) 14,
                        (short) 7,
                        (short) 13,
                        (short) 8,
                        (short) 10,
                        (short) 20,
                        (short) 19,
                        (short) 16,
                        (short) 17,
                        (short) 9,
                        (short) 23,
                        (short) 11,
                        (short) 18
                ),
                castedReadPacket.getPlid()
        );
    }
}
