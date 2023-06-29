package pl.adrian.testutil;

import pl.adrian.api.insim.InSimConnection;
import pl.adrian.api.insim.packets.IsiPacket;

import java.io.IOException;

public class TestInSimConnection extends InSimConnection {
    public TestInSimConnection(String hostname, int port, IsiPacket initializationPacket) throws IOException {
        super(hostname, port, initializationPacket);
        packetRequestTimeoutMs = 800;
        clearPacketRequestsIntervalMs = 450;
    }
}
