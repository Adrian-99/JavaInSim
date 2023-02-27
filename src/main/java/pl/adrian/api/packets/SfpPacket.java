package pl.adrian.api.packets;

import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.api.packets.enums.SfpFlag;
import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.annotations.Word;
import pl.adrian.internal.packets.base.Packet;
import pl.adrian.internal.packets.base.SendablePacket;
import pl.adrian.internal.packets.exceptions.PacketValidationException;
import pl.adrian.internal.packets.util.PacketBuilder;
import pl.adrian.internal.packets.util.PacketValidator;

/**
 * State Flags Pack.
 */
public class SfpPacket extends Packet implements SendablePacket {
    @Word
    private final SfpFlag flag;
    @Byte
    private final boolean on;

    /**
     * Creates state flags pack packet.
     * @param flag the state to set
     * @param on desired value of specified state
     * @throws PacketValidationException if validation of any field in packet fails
     */
    public SfpPacket(SfpFlag flag, boolean on) throws PacketValidationException {
        super(8, PacketType.SFP, 0);
        this.flag = flag;
        this.on = on;

        PacketValidator.validate(this);
    }

    @Override
    public byte[] getBytes() {
        return new PacketBuilder(size, type, reqI)
                .writeZeroByte()
                .writeWord(flag.getValue())
                .writeByte(on)
                .writeZeroByte()
                .getBytes();
    }
}
