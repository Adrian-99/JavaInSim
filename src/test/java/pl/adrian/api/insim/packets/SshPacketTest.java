package pl.adrian.api.insim.packets;

import org.junit.jupiter.api.Test;
import pl.adrian.api.insim.packets.enums.PacketType;
import pl.adrian.api.insim.packets.enums.SshErrorCode;
import pl.adrian.internal.insim.packets.util.PacketReader;
import pl.adrian.testutil.MockedInSimConnection;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static pl.adrian.testutil.AssertionUtils.assertPacketHeaderEquals;
import static pl.adrian.testutil.AssertionUtils.assertRequestPacketBytesEqual;

class SshPacketTest {
    @Test
    void createSshPacket() {
        var packet = new SshPacket(91, "screenshotName1");
        var bytes = packet.getBytes();
        var expectedBytes = new byte[] {
                10, 49, 91, 0, 0, 0, 0, 0, 115, 99, 114, 101, 101, 110, 115, 104,
                111, 116, 78, 97, 109, 101, 49, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0
        };

        assertArrayEquals(expectedBytes, bytes);
    }

    @Test
    void readSshPacket() {
        var headerBytes = new byte[] { 10, 49, -112 };
        var dataBytes = new byte[] {
                3, 0, 0, 0, 0, 108, 102, 115, 95, 48, 48, 48, 48, 48, 48, 48,
                50, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0
        };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(37, PacketType.SSH, 144, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof SshPacket);

        var castedReadPacket = (SshPacket) readPacket;

        assertPacketHeaderEquals(40, PacketType.SSH, 144, castedReadPacket);
        assertEquals(SshErrorCode.NO_SAVE, castedReadPacket.getError());
        assertEquals("lfs_00000002", castedReadPacket.getName());
    }

    @Test
    void requestSshPacket() throws IOException {
        var inSimConnectionMock = new MockedInSimConnection();

        SshPacket.request(inSimConnectionMock, new SshPacket(0, "img003"))
                .listen((inSimConnection, packet) -> {});

        var expectedRequestPacketBytes = new byte[] {
                10, 49, 0, 0, 0, 0, 0, 0, 105, 109, 103, 48, 48, 51, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0
        };
        assertRequestPacketBytesEqual(expectedRequestPacketBytes, inSimConnectionMock.assertAndGetSentPacketBytes());
    }
}
