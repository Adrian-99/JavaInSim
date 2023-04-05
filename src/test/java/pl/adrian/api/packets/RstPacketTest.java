package pl.adrian.api.packets;

import org.junit.jupiter.api.Test;
import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.api.packets.enums.Wind;
import pl.adrian.api.packets.flags.RaceFlag;
import pl.adrian.internal.packets.util.PacketReader;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.adrian.testutil.AssertionUtils.assertFlagsEqual;
import static pl.adrian.testutil.AssertionUtils.assertPacketHeaderEquals;

class RstPacketTest {
    @Test
    void readRstPacket() {
        var headerBytes = new byte[] { 7, 17, -112 };
        var dataBytes = new byte[] {
                0, 15, 0, 20, 67, 70, 69, 49, 82, 0, 0, 1, 2, 33, 1, -24,
                3, 0, 0, -32, 0, 77, 2, -67, 2
        };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(25, PacketType.RST, 144, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof RstPacket);

        var castedReadPacket = (RstPacket) readPacket;

        assertPacketHeaderEquals(28, PacketType.RST, 144, castedReadPacket);
        assertFalse(castedReadPacket.getRaceLaps().isPractice());
        assertTrue(castedReadPacket.getRaceLaps().areLaps());
        assertFalse(castedReadPacket.getRaceLaps().areHours());
        assertEquals(15, castedReadPacket.getRaceLaps().getValue());
        assertEquals(0, castedReadPacket.getQualMins());
        assertEquals(20, castedReadPacket.getNumP());
        assertTrue(castedReadPacket.getTiming().isStandardLapTiming());
        assertFalse(castedReadPacket.getTiming().isCustomLapTiming());
        assertFalse(castedReadPacket.getTiming().isNoLapTiming());
        assertEquals(3, castedReadPacket.getTiming().getNumberOfCheckpoints());
        assertEquals("FE1R", castedReadPacket.getTrack());
        assertEquals(1, castedReadPacket.getWeather());
        assertEquals(Wind.STRONG, castedReadPacket.getWind());
        assertFlagsEqual(
                RaceFlag.class,
                Set.of(RaceFlag.CAN_VOTE, RaceFlag.MID_RACE, RaceFlag.FCV),
                castedReadPacket.getFlags()
        );
        assertEquals(1000, castedReadPacket.getNumNodes());
        assertEquals(0, castedReadPacket.getFinish());
        assertEquals(224, castedReadPacket.getSplit1());
        assertEquals(589, castedReadPacket.getSplit2());
        assertEquals(701, castedReadPacket.getSplit3());
    }
}
