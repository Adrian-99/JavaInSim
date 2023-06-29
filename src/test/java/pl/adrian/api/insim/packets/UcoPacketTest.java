package pl.adrian.api.insim.packets;

import org.junit.jupiter.api.Test;
import pl.adrian.api.insim.packets.enums.InSimCheckpointType;
import pl.adrian.api.insim.packets.enums.ObjectType;
import pl.adrian.api.insim.packets.enums.PacketType;
import pl.adrian.api.insim.packets.enums.UcoAction;
import pl.adrian.api.insim.packets.structures.objectinfo.InSimCheckpointInfo;
import pl.adrian.api.insim.packets.structures.objectinfo.InSimCircleInfo;
import pl.adrian.internal.insim.packets.util.PacketReader;

import static org.junit.jupiter.api.Assertions.*;
import static pl.adrian.testutil.AssertionUtils.assertPacketHeaderEquals;

class UcoPacketTest {
    @Test
    void readUcoPacket_forInSimCheckpoint() {
        var headerBytes = new byte[] { 7, 59, 0 };
        var dataBytes = new byte[] {
                26, 0, 0, 0, 0, -127, 52, 39, 0, 115, 112, 24, 13, 80, 84, -62,
                -67, 68, 84, -73, -67, 12, -67, -4, 94
        };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(25, PacketType.UCO, 0, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof UcoPacket);

        var castedReadPacket = (UcoPacket) readPacket;

        assertPacketHeaderEquals(28, PacketType.UCO, 0, castedReadPacket);
        assertEquals(26, castedReadPacket.getPlid());
        assertEquals(UcoAction.CIRCLE_ENTER, castedReadPacket.getUcoAction());
        assertEquals(2569345, castedReadPacket.getTime());
        assertEquals(115, castedReadPacket.getC().getDirection());
        assertEquals(112, castedReadPacket.getC().getHeading());
        assertEquals(24, castedReadPacket.getC().getSpeed());
        assertEquals(13, castedReadPacket.getC().getZByte());
        assertEquals(21584, castedReadPacket.getC().getX());
        assertEquals(-16958, castedReadPacket.getC().getY());

        assertTrue(castedReadPacket.getInfo() instanceof InSimCheckpointInfo);
        var info = (InSimCheckpointInfo) castedReadPacket.getInfo();

        assertEquals(21572, info.getX());
        assertEquals(-16969, info.getY());
        assertEquals(12, info.getZByte());
        assertTrue(info.isFloating());
        assertEquals(InSimCheckpointType.CHECKPOINT_1, info.getCheckpointIndex());
        assertEquals(30, info.getWidth());
        assertEquals(ObjectType.MARSH_IS_CP, info.getIndex());
        assertEquals(94, info.getHeading());
    }

    @Test
    void readUcoPacket_forInSimCircle() {
        var headerBytes = new byte[] { 7, 59, 0 };
        var dataBytes = new byte[] {
                26, 0, 3, 0, 0, -127, 52, 39, 0, 115, 112, 24, 13, 80, 84, -62,
                -67, 68, 84, -73, -67, 12, 20, -3, -106
        };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(25, PacketType.UCO, 0, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof UcoPacket);

        var castedReadPacket = (UcoPacket) readPacket;

        assertPacketHeaderEquals(28, PacketType.UCO, 0, castedReadPacket);
        assertEquals(26, castedReadPacket.getPlid());
        assertEquals(UcoAction.CP_REV, castedReadPacket.getUcoAction());
        assertEquals(2569345, castedReadPacket.getTime());
        assertEquals(115, castedReadPacket.getC().getDirection());
        assertEquals(112, castedReadPacket.getC().getHeading());
        assertEquals(24, castedReadPacket.getC().getSpeed());
        assertEquals(13, castedReadPacket.getC().getZByte());
        assertEquals(21584, castedReadPacket.getC().getX());
        assertEquals(-16958, castedReadPacket.getC().getY());

        assertTrue(castedReadPacket.getInfo() instanceof InSimCircleInfo);
        var info = (InSimCircleInfo) castedReadPacket.getInfo();

        assertEquals(21572, info.getX());
        assertEquals(-16969, info.getY());
        assertEquals(12, info.getZByte());
        assertFalse(info.isFloating());
        assertEquals(10, info.getDiameter());
        assertEquals(ObjectType.MARSH_IS_AREA, info.getIndex());
        assertEquals(150, info.getCircleIndex());
    }
}
