package pl.adrian.api.packets;

import org.junit.jupiter.api.Test;
import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.api.packets.flags.PlayerFlag;
import pl.adrian.internal.packets.util.PacketReader;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.adrian.testutil.AssertionUtils.assertFlagsEqual;
import static pl.adrian.testutil.AssertionUtils.assertPacketHeaderEquals;

class PflPacketTest {
    @Test
    void readPflPacket() {
        var headerBytes = new byte[] { 2, 33, 0 };
        var dataBytes = new byte[] { 22, 9, 11, 0, 0 };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(5, PacketType.PFL, 0, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof PflPacket);

        var castedReadPacket = (PflPacket) readPacket;

        assertPacketHeaderEquals(8, PacketType.PFL, 0, castedReadPacket);
        assertEquals(22, castedReadPacket.getPlid());
        assertFlagsEqual(
                PlayerFlag.class,
                Set.of(
                        PlayerFlag.LEFTSIDE,
                        PlayerFlag.AUTOGEARS,
                        PlayerFlag.INPITS,
                        PlayerFlag.AUTOCLUTCH,
                        PlayerFlag.KB_NO_HELP
                ),
                castedReadPacket.getFlags()
        );
    }
}
