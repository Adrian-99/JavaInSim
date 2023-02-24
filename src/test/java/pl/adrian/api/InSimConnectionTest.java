package pl.adrian.api;

import org.awaitility.core.ConditionTimeoutException;
import org.junit.jupiter.api.*;
import pl.adrian.api.packets.IsiPacket;
import pl.adrian.api.packets.SmallPacket;
import pl.adrian.api.packets.TinyPacket;
import pl.adrian.api.packets.enums.Product;
import pl.adrian.api.packets.flags.Flags;
import pl.adrian.api.packets.flags.IsiFlag;
import pl.adrian.testutil.LfsMock;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;

class InSimConnectionTest {
    private static final int LFS_MOCK_PORT = 29999;
    private static final IsiPacket INIT_PACKET = new IsiPacket(
            new Flags<>(IsiFlag.LOCAL),
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
    private static final byte[] CLOSE_PACKET_BYTES = new byte[] {
            1, 3, 0, 2
    };
    private static final byte[] KEEP_ALIVE_PACKET_BYTES = new byte[] {
            1, 3, 0, 0
    };
    private static final byte[] SMALL_PACKET_BYTES = new byte[] {
            2, 4, 0, 0, 15, 0, 0, 0
    };

    private LfsMock lfsMock;
    private InSimConnection inSimConnection;

    @BeforeEach
    void beforeEach() throws IOException {
        lfsMock = new LfsMock(LFS_MOCK_PORT, Product.S3, "0.7D");
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

        lfsReceivedPackets = lfsMock.awaitReceivedPackets(2);
        assertEquals(2, lfsReceivedPackets.size());
        assertArrayEquals(CLOSE_PACKET_BYTES, lfsReceivedPackets.get(1));
    }

    @Test
    void lostConnectionToLFS() throws IOException {
        lfsMock.awaitReceivedPackets(1);

        assertTrue(inSimConnection.isConnected());

        lfsMock.close();

        assertFalse(inSimConnection.isConnected());
    }

    @Test
    void keepAlivePackets() throws IOException {
        lfsMock.send(KEEP_ALIVE_PACKET_BYTES);

        var lfsReceivedPackets = lfsMock.awaitReceivedPackets(2);
        assertEquals(2, lfsReceivedPackets.size());
        assertArrayEquals(KEEP_ALIVE_PACKET_BYTES, lfsReceivedPackets.get(1));
    }

    @Test
    void listen() throws IOException {
        var tinyPacketListener1Calls = new AtomicInteger();
        var tinyPacketListener2Calls = new AtomicInteger();
        var smallPacketListenerCalls = new AtomicInteger();

        PacketListener<TinyPacket> tinyPacketListener1 = (inSimConnection, packet) -> {
            assertEquals(this.inSimConnection, inSimConnection);
            assertArrayEquals(KEEP_ALIVE_PACKET_BYTES, packet.getBytes());
            tinyPacketListener1Calls.getAndIncrement();
        };
        PacketListener<TinyPacket> tinyPacketListener2 = (inSimConnection, packet) -> {
            assertEquals(this.inSimConnection, inSimConnection);
            assertArrayEquals(KEEP_ALIVE_PACKET_BYTES, packet.getBytes());
            tinyPacketListener2Calls.getAndIncrement();
        };
        PacketListener<SmallPacket> smallPacketListener = (inSimConnection, packet) -> {
            assertEquals(this.inSimConnection, inSimConnection);
            assertArrayEquals(SMALL_PACKET_BYTES, packet.getBytes());
            smallPacketListenerCalls.getAndIncrement();
        };

        inSimConnection.listen(TinyPacket.class, tinyPacketListener1);
        inSimConnection.listen(TinyPacket.class, tinyPacketListener2);
        inSimConnection.listen(TinyPacket.class, tinyPacketListener2);
        inSimConnection.listen(SmallPacket.class, smallPacketListener);

        lfsMock.send(KEEP_ALIVE_PACKET_BYTES);
        lfsMock.send(KEEP_ALIVE_PACKET_BYTES);

        await().atMost(5, TimeUnit.SECONDS)
                .with().pollInterval(100, TimeUnit.MILLISECONDS)
                .until(() -> tinyPacketListener1Calls.get() == 2 && tinyPacketListener2Calls.get() == 2);

        assertEquals(0, smallPacketListenerCalls.get());

        lfsMock.send(SMALL_PACKET_BYTES);

        await().atMost(1, TimeUnit.SECONDS)
                .with().pollInterval(100, TimeUnit.MILLISECONDS)
                .until(() -> smallPacketListenerCalls.get() == 1);

        assertEquals(2, tinyPacketListener1Calls.get());
        assertEquals(2, tinyPacketListener2Calls.get());
        assertEquals(1, smallPacketListenerCalls.get());
    }

    @Test
    void stopListening() throws IOException {
        AtomicInteger smallPacketListener1Calls = new AtomicInteger();
        AtomicInteger smallPacketListener2Calls = new AtomicInteger();

        PacketListener<SmallPacket> smallPacketListener1 =
                (inSimConnection, packet) -> smallPacketListener1Calls.getAndIncrement();
        PacketListener<SmallPacket> smallPacketListener2 =
                (inSimConnection, packet) -> smallPacketListener2Calls.getAndIncrement();

        inSimConnection.listen(SmallPacket.class, smallPacketListener1);
        inSimConnection.listen(SmallPacket.class, smallPacketListener2);

        inSimConnection.stopListening(SmallPacket.class, smallPacketListener1);
        inSimConnection.stopListening(SmallPacket.class, smallPacketListener1);
        inSimConnection.stopListening(SmallPacket.class, smallPacketListener2);

        lfsMock.send(SMALL_PACKET_BYTES);

        var await = await().atMost(1, TimeUnit.SECONDS)
                .with().pollInterval(100, TimeUnit.MILLISECONDS);
        assertThrows(
                ConditionTimeoutException.class,
                () -> await.until(() -> smallPacketListener1Calls.get() >= 1 || smallPacketListener2Calls.get() >= 1)
        );
    }
}
