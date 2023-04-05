package pl.adrian.api.packets;

import org.junit.jupiter.api.Test;
import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.api.packets.flags.ConfirmationFlag;
import pl.adrian.api.packets.flags.PlayerFlag;
import pl.adrian.internal.packets.util.PacketReader;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.adrian.testutil.AssertionUtils.assertFlagsEqual;
import static pl.adrian.testutil.AssertionUtils.assertPacketHeaderEquals;

class FinPacketTest {
    @Test
    void readFinPacket() {
        var headerBytes = new byte[] { 5, 34, 0 };
        var dataBytes = new byte[] {
                25, 60, 93, 57, 0, 78, 1, 1, 0, 0, 4, 18, 0, 55, 0, 1,
                38
        };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(17, PacketType.FIN, 0, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof FinPacket);

        var castedReadPacket = (FinPacket) readPacket;

        assertPacketHeaderEquals(20, PacketType.FIN, 0, castedReadPacket);
        assertEquals(25, castedReadPacket.getPlid());
        assertEquals(3759420, castedReadPacket.getTTime());
        assertEquals(65870, castedReadPacket.getBTime());
        assertEquals(4, castedReadPacket.getNumStops());
        assertFlagsEqual(
                ConfirmationFlag.class,
                Set.of(ConfirmationFlag.CONFIRMED, ConfirmationFlag.PENALTY_30, ConfirmationFlag.TIME),
                castedReadPacket.getConfirm()
        );
        assertEquals(55, castedReadPacket.getLapsDone());
        assertFlagsEqual(
                PlayerFlag.class,
                Set.of(PlayerFlag.LEFTSIDE, PlayerFlag.AUTOCLUTCH, PlayerFlag.MOUSE, PlayerFlag.CUSTOM_VIEW),
                castedReadPacket.getFlags()
        );
    }
}
