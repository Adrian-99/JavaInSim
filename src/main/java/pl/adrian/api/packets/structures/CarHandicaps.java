package pl.adrian.api.packets.structures;

import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.structures.base.ComplexInstructionStructure;
import pl.adrian.internal.packets.util.PacketBuilder;

/**
 * Car handicaps in 2 bytes - there is an array of these in the {@link pl.adrian.api.packets.HcpPacket HcpPacket}.
 */
public class CarHandicaps implements ComplexInstructionStructure {
    @Byte(maxValue = 200)
    private final short hMass;
    @Byte(maxValue = 50)
    private final short hTRes;

    /**
     * Creates car handicaps.
     * @param hMass added mass (kg) - from 0 to 200
     * @param hTRes intake restriction (%) - from 0 to 50
     */
    public CarHandicaps(int hMass, int hTRes) {
        this.hMass = (short) hMass;
        this.hTRes = (short) hTRes;
    }

    @Override
    public void appendBytes(PacketBuilder packetBuilder) {
        packetBuilder.writeByte(hMass)
                .writeByte(hTRes);
    }
}
