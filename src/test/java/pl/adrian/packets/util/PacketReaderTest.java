package pl.adrian.packets.util;

import org.junit.jupiter.api.Test;
import pl.adrian.packets.exceptions.PacketReadingException;

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
    void packetReader_forUnrecognizedReadablePacketType() {
        var headerBytes = new byte[] { 1, 0, 0 };
        assertThrows(PacketReadingException.class, () -> new PacketReader(headerBytes));
    }
}
