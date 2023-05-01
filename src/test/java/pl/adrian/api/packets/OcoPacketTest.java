package pl.adrian.api.packets;

import org.junit.jupiter.api.Test;
import pl.adrian.api.packets.enums.OcoAction;
import pl.adrian.api.packets.enums.StartLightsIndex;
import pl.adrian.api.packets.flags.Flags;
import pl.adrian.api.packets.flags.StartLightsDataFlag;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class OcoPacketTest {
    @Test
    void createOcoPacket() {
        var packet = new OcoPacket(
                OcoAction.LIGHTS_SET,
                StartLightsIndex.START_LIGHTS,
                55,
                new Flags<>(StartLightsDataFlag.RED1, StartLightsDataFlag.RED2_AMBER)
        );
        var bytes = packet.getBytes();
        var expectedBytes = new byte[] { 2, 60, 0, 0, 5, -107, 55, 3 };

        assertArrayEquals(expectedBytes, bytes);
    }
}
