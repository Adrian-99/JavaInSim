package pl.adrian.api.insim.packets;

import org.junit.jupiter.api.Test;
import pl.adrian.api.insim.packets.enums.PacketType;
import pl.adrian.api.insim.packets.enums.PenaltyValue;
import pl.adrian.api.insim.packets.flags.PlayerFlag;
import pl.adrian.internal.insim.packets.util.PacketReader;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.adrian.testutil.AssertionUtils.assertFlagsEqual;
import static pl.adrian.testutil.AssertionUtils.assertPacketHeaderEquals;

class LapPacketTest {
    @Test
    void readLapPacket() {
        var headerBytes = new byte[] { 5, 24, 0 };
        var dataBytes = new byte[] {
                15, -12, -35, 0, 0, -15, 125, 3, 0, 4, 0, -111, 0, 0, 5, 1,
                115
        };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(17, PacketType.LAP, 0, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof LapPacket);

        var castedReadPacket = (LapPacket) readPacket;

        assertPacketHeaderEquals(20, PacketType.LAP, 0, castedReadPacket);
        assertEquals(15, castedReadPacket.getPlid());
        assertEquals(56820, castedReadPacket.getLTime());
        assertEquals(228849, castedReadPacket.getETime());
        assertEquals(4, castedReadPacket.getLapsDone());
        assertFlagsEqual(
                PlayerFlag.class,
                Set.of(PlayerFlag.LEFTSIDE, PlayerFlag.SHIFTER, PlayerFlag.AXIS_CLUTCH),
                castedReadPacket.getFlags()
        );
        assertEquals(PenaltyValue.PLUS_30_S, castedReadPacket.getPenalty());
        assertEquals(1, castedReadPacket.getNumStops());
        assertEquals(115, castedReadPacket.getFuel200());
    }
}
