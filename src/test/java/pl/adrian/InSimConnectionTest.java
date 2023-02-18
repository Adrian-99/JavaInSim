package pl.adrian;

import org.junit.jupiter.api.*;
import pl.adrian.packets.IS_INI;
import pl.adrian.packets.enums.Product;
import pl.adrian.packets.flags.Flags;
import pl.adrian.packets.flags.ISF;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class InSimConnectionTest {
    private static final int LFS_MOCK_PORT = 29999;
    private static final IS_INI INIT_PACKET = new IS_INI(
            new Flags<>(ISF.LOCAL),
            null,
            250,
            "password",
            "application"
    );
    private static final byte[] INIT_PACKET_BYTES = new byte[] {
            11, 1, 1, 0, 0, 0, 4, 0, 9, 0, -6, 0, 112, 97, 115, 115,
            119, 111, 114, 100, 0, 0, 0, 0, 0, 0, 0, 0, 97, 112, 112, 108,
            105, 99, 97, 116, 105, 111, 110, 0, 0, 0, 0, 0
    };

    private LFSMock lfsMock;
    private InSimConnection inSimConnection;

    @BeforeEach
    void beforeEach() throws IOException {
        lfsMock = new LFSMock(LFS_MOCK_PORT, Product.S3, "0.7D");
        inSimConnection = new InSimConnection("localhost", 29999, INIT_PACKET);
    }

    @AfterEach
    void afterEach() throws IOException {
        lfsMock.close();
        inSimConnection.close();
    }

    @Test
    void closeInSimConnection() throws IOException {
        var lfsReceivedPackets = lfsMock.awaitReceivedPackets(1);
        assertEquals(1, lfsReceivedPackets.size());
        assertArrayEquals(INIT_PACKET_BYTES, lfsReceivedPackets.get(0));

        assertTrue(inSimConnection.isConnected());

        inSimConnection.close();

        assertFalse(inSimConnection.isConnected());
    }

    @Test
    void lostConnectionToLFS() throws IOException {
        lfsMock.awaitReceivedPackets(1);

        assertTrue(inSimConnection.isConnected());

        lfsMock.close();

        assertFalse(inSimConnection.isConnected());
    }
}
