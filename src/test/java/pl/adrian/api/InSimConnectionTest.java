package pl.adrian.api;

import org.awaitility.core.ConditionTimeoutException;
import org.junit.jupiter.api.*;
import pl.adrian.api.packets.*;
import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.api.packets.enums.Product;
import pl.adrian.api.packets.enums.SmallSubtype;
import pl.adrian.api.packets.enums.TinySubtype;
import pl.adrian.api.packets.enums.DefaultCar;
import pl.adrian.api.packets.flags.Flags;
import pl.adrian.api.packets.flags.IsiFlag;
import pl.adrian.api.packets.flags.NcnFlag;
import pl.adrian.testutil.LfsMock;
import pl.adrian.testutil.TestInSimConnection;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;
import static pl.adrian.testutil.AssertionUtils.assertFlagsEqual;

class InSimConnectionTest {
    private static final int LFS_MOCK_PORT = 49999;
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
        inSimConnection = new TestInSimConnection("localhost", LFS_MOCK_PORT, INIT_PACKET);
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

        assertConditionMet(
                () -> tinyPacketListener1Calls.get() == 2 && tinyPacketListener2Calls.get() == 2,
                1000,
                100
        );

        assertEquals(0, smallPacketListenerCalls.get());

        lfsMock.send(SMALL_PACKET_BYTES);

        assertConditionMet(() -> smallPacketListenerCalls.get() == 1, 1000, 100);

        assertEquals(2, tinyPacketListener1Calls.get());
        assertEquals(2, tinyPacketListener2Calls.get());
        assertEquals(1, smallPacketListenerCalls.get());
    }

    @Test
    void listen_withThrowingCallback() throws IOException {
        var firstListenerCalled = new AtomicBoolean();
        var secondListenerCalled = new AtomicBoolean();

        inSimConnection.listen(TinyPacket.class, (inSimConnection1, packet) -> {
            firstListenerCalled.set(true);
            throw new RuntimeException("Exception in listener callback");
        });
        inSimConnection.listen(TinyPacket.class, (inSimConnection1, packet) -> secondListenerCalled.set(true));

        lfsMock.send(KEEP_ALIVE_PACKET_BYTES);

        assertConditionMet(
                () -> firstListenerCalled.get() && secondListenerCalled.get(),
                1000,
                100
        );
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

        assertConditionNotMet(
                () -> smallPacketListener1Calls.get() >= 1 || smallPacketListener2Calls.get() >= 1,
                1000
        );
    }

    @Test
    void request() throws IOException {
        var receivedResponsesCount = new AtomicInteger();

        inSimConnection.request(IsmPacket.class, (inSimConnection, packet) -> {
            assertEquals(this.inSimConnection, inSimConnection);
            assertEquals(40, packet.getSize());
            assertEquals(PacketType.ISM, packet.getType());
            assertTrue(packet.getReqI() >= 1 && packet.getReqI() <= 255);
            assertFalse(packet.isHost());
            assertEquals("Example LFS Server", packet.getHName());
            receivedResponsesCount.getAndIncrement();
        });

        var lfsReceivedPackets = lfsMock.awaitReceivedPackets(2);
        assertEquals(2, lfsReceivedPackets.size());
        assertEquals(4, lfsReceivedPackets.get(1).length);
        assertEquals(1, lfsReceivedPackets.get(1)[0]);
        assertEquals(3, lfsReceivedPackets.get(1)[1]);
        assertNotEquals(0, lfsReceivedPackets.get(1)[2]);
        assertEquals(10, lfsReceivedPackets.get(1)[3]);

        var responsePacketBytes = new byte[] {
                10, 10, lfsReceivedPackets.get(1)[2], 0, 0, 0, 0, 0, 69, 120, 97, 109, 112, 108, 101, 32, 76,
                70, 83, 32, 83, 101, 114, 118, 101, 114, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0
        };
        lfsMock.send(responsePacketBytes);

        assertConditionMet(() -> receivedResponsesCount.get() == 1, 1000, 100);

        lfsMock.send(responsePacketBytes);

        assertConditionNotMet(() -> receivedResponsesCount.get() > 1, 1000);
    }

    @Test
    void request_smallPacket() throws IOException {
        var receivedResponsesCount = new AtomicInteger();

        inSimConnection.request(TinySubtype.ALC, (inSimConnection, packet) -> {
            assertEquals(this.inSimConnection, inSimConnection);
            assertEquals(8, packet.getSize());
            assertEquals(PacketType.SMALL, packet.getType());
            assertNotEquals(0, packet.getReqI());
            assertEquals(SmallSubtype.ALC, packet.getSubT());
            assertEquals(12561, packet.getUVal());
            assertTrue(packet.getVoteAction().isEmpty());
            assertTrue(packet.getCars().isPresent());
            assertFlagsEqual(DefaultCar.class, Set.of(DefaultCar.XFG, DefaultCar.FXO, DefaultCar.UF1, DefaultCar.XFR, DefaultCar.UFR), packet.getCars().get());
            receivedResponsesCount.getAndIncrement();
        });

        var lfsReceivedPackets = lfsMock.awaitReceivedPackets(2);
        assertEquals(2, lfsReceivedPackets.size());
        assertEquals(4, lfsReceivedPackets.get(1).length);
        assertEquals(1, lfsReceivedPackets.get(1)[0]);
        assertEquals(3, lfsReceivedPackets.get(1)[1]);
        assertNotEquals(0, lfsReceivedPackets.get(1)[2]);
        assertEquals(24, lfsReceivedPackets.get(1)[3]);

        var responsePacketBytes = new byte[] { 2, 4, lfsReceivedPackets.get(1)[2], 8, 17, 49, 0, 0 };
        lfsMock.send(responsePacketBytes);

        assertConditionMet(() -> receivedResponsesCount.get() == 1, 1000, 100);

        lfsMock.send(responsePacketBytes);

        assertConditionNotMet(() -> receivedResponsesCount.get() > 1, 1000);
    }

    @Test
    void request_multiplePacketResponse() throws IOException {
        var receivedResponsesCount = new AtomicInteger();

        inSimConnection.request(NcnPacket.class, (inSimConnection, packet) -> {
            assertEquals(this.inSimConnection, inSimConnection);
            assertEquals(56, packet.getSize());
            assertEquals(PacketType.NCN, packet.getType());
            assertNotEquals(0, packet.getReqI());
            assertEquals(18, packet.getUcid());
            assertEquals("theuser", packet.getUName());
            assertEquals("New User Nick", packet.getPName());
            assertFalse(packet.isAdmin());
            assertEquals(12, packet.getTotal());
            assertFlagsEqual(NcnFlag.class, Set.of(), packet.getFlags());
            receivedResponsesCount.getAndIncrement();
        });

        var lfsReceivedPackets = lfsMock.awaitReceivedPackets(2);
        assertEquals(2, lfsReceivedPackets.size());
        assertEquals(4, lfsReceivedPackets.get(1).length);
        assertEquals(1, lfsReceivedPackets.get(1)[0]);
        assertEquals(3, lfsReceivedPackets.get(1)[1]);
        assertNotEquals(0, lfsReceivedPackets.get(1)[2]);
        assertEquals(13, lfsReceivedPackets.get(1)[3]);

        var responsePacketBytes = new byte[] {
                14, 18, lfsReceivedPackets.get(1)[2], 18, 116, 104, 101, 117, 115, 101, 114, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 78, 101, 119, 32,
                85, 115, 101, 114, 32, 78, 105, 99, 107, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 12, 0, 0
        };
        lfsMock.send(responsePacketBytes);
        lfsMock.send(responsePacketBytes);
        lfsMock.send(responsePacketBytes);

        assertConditionMet(() -> receivedResponsesCount.get() == 3, 1000, 100);
        assertConditionNotMet(() -> receivedResponsesCount.get() > 3, 1500);

        lfsMock.send(responsePacketBytes);

        assertConditionNotMet(() -> receivedResponsesCount.get() > 3, 1000);
    }

    @Test
    void request_withThrowingCallback() throws IOException {
        var firstRequestCalled = new AtomicBoolean();
        var secondRequestCalled = new AtomicBoolean();

        inSimConnection.request(TinySubtype.ALC, (inSimConnection1, packet) -> {
            firstRequestCalled.set(true);
            throw new RuntimeException("Exception in request callback");
        });

        var lfsReceivedPackets = lfsMock.awaitReceivedPackets(2);
        var responsePacketBytes = new byte[] { 2, 4, lfsReceivedPackets.get(1)[2], 8, 17, 49, 0, 0 };
        lfsMock.send(responsePacketBytes);

        assertConditionMet(() -> firstRequestCalled.get() && !secondRequestCalled.get(), 1000, 100);

        inSimConnection.request(TinySubtype.ALC, (inSimConnection1, packet) -> secondRequestCalled.set(true));

        lfsReceivedPackets = lfsMock.awaitReceivedPackets(3);
        responsePacketBytes = new byte[] { 2, 4, lfsReceivedPackets.get(2)[2], 8, 17, 49, 0, 0 };
        lfsMock.send(responsePacketBytes);

        assertConditionMet(secondRequestCalled::get, 1000, 100);
    }

    @Test
    void request_uniqueReqICheck() throws IOException {
        for (int i = 0; i < 255; i++) {
            inSimConnection.request(StaPacket.class, (inSimConnection, packet) -> {});
        }
        var lfsReceivedPackets = lfsMock.awaitReceivedPackets(256);
        var distinctReqIs = lfsReceivedPackets.stream().skip(1).map(bytes -> bytes[2]).distinct().count();
        assertEquals(255, distinctReqIs);
    }

    private void assertConditionMet(Callable<Boolean> condition, int atMostMs, int pollIntervalMs) {
        await().atMost(atMostMs, TimeUnit.MILLISECONDS)
                .with().pollInterval(pollIntervalMs, TimeUnit.MILLISECONDS)
                .until(condition);
    }

    private void assertConditionNotMet(Callable<Boolean> condition, int withinMs) {
        assertThrows(
                ConditionTimeoutException.class,
                () -> assertConditionMet(condition, withinMs, withinMs / 2)
        );
    }
}
