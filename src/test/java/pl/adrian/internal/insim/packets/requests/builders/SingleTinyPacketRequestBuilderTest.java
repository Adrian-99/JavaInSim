package pl.adrian.internal.insim.packets.requests.builders;

import org.junit.jupiter.api.Test;
import pl.adrian.api.insim.packets.SmallPacket;
import pl.adrian.api.insim.packets.enums.PacketType;
import pl.adrian.api.insim.packets.enums.SmallSubtype;
import pl.adrian.api.insim.packets.enums.TinySubtype;
import pl.adrian.internal.common.util.PacketDataBytes;
import pl.adrian.testutil.MockedInSimConnection;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.adrian.testutil.TestUtils.byteToShort;

class SingleTinyPacketRequestBuilderTest {
    @Test
    void asCompletableFuture() throws IOException, ExecutionException, InterruptedException {
        var inSimConnectionMock = new MockedInSimConnection();
        var packetCompletableFuture = new SingleTinyPacketRequestBuilder<>(inSimConnectionMock, TinySubtype.GTH)
                .asCompletableFuture();

        var requestPacketBytes = inSimConnectionMock.assertAndGetSentPacketBytes();
        var reqI = requestPacketBytes[2];
        var expectedRequestPacketBytes = new byte[] { 1, 3, reqI, 8 };
        assertArrayEquals(expectedRequestPacketBytes, requestPacketBytes);

        var packetRequest = inSimConnectionMock.assertAndGetPacketRequest();
        packetRequest.handleReceivedPacket(
                inSimConnectionMock,
                new SmallPacket(byteToShort(reqI), new PacketDataBytes(new byte[] { 6, -80, 100, 5, 0 }))
        );

        var packet = packetCompletableFuture.get();
        assertEquals(8, packet.getSize());
        assertEquals(PacketType.SMALL, packet.getType());
        assertNotEquals(0, packet.getReqI());
        assertEquals(SmallSubtype.RTP, packet.getSubT());
        assertEquals(353456, packet.getUVal());
    }
}
