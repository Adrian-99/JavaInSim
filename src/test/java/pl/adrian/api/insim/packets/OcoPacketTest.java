package pl.adrian.api.insim.packets;

import org.junit.jupiter.api.Test;
import pl.adrian.api.insim.packets.enums.OcoAction;
import pl.adrian.api.insim.packets.enums.StartLightsIndex;
import pl.adrian.api.common.flags.Flags;
import pl.adrian.api.insim.packets.flags.StartLightsDataFlag;

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
