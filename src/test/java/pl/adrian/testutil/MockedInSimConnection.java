package pl.adrian.testutil;

import pl.adrian.api.common.flags.Flags;
import pl.adrian.api.insim.InSimConnection;
import pl.adrian.api.insim.packets.IsiPacket;
import pl.adrian.internal.insim.packets.base.InstructionPacket;
import pl.adrian.internal.insim.packets.requests.PacketRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MockedInSimConnection extends InSimConnection {
    private final List<InstructionPacket> caughtSentPackets = new ArrayList<>();
    private final List<PacketRequest> caughtPacketRequests = new ArrayList<>();

    public MockedInSimConnection() throws IOException {
        super("", 0, new IsiPacket(29998, new Flags<>(), '!', 5, "", ""));
    }

    @Override
    public void send(InstructionPacket packet) {
        caughtSentPackets.add(packet);
    }

    @Override
    public void request(PacketRequest packetRequest) throws IOException {
        super.request(packetRequest);
        caughtPacketRequests.add(packetRequest);
    }

    @Override
    protected void connect(String hostname, int port, IsiPacket initializationPacket) {}

    public byte[] assertAndGetSentPacketBytes() {
        assertEquals(1, caughtSentPackets.size(), "Unexpected size of caught sent packets");
        return caughtSentPackets.get(0).getBytes();
    }

    public PacketRequest assertAndGetPacketRequest() {
        assertEquals(1, caughtPacketRequests.size(), "Unexpected size of caught packet requests");
        return caughtPacketRequests.get(0);
    }
}
