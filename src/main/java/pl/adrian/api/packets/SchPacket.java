package pl.adrian.api.packets;

import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.api.packets.flags.Flags;
import pl.adrian.api.packets.flags.SchFlag;
import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.base.Packet;
import pl.adrian.internal.packets.base.SendablePacket;
import pl.adrian.internal.packets.util.PacketBuilder;
import pl.adrian.internal.packets.util.PacketValidator;

/**
 * Single CHaracter.
 */
public class SchPacket extends Packet implements SendablePacket {
    @Byte
    private final char charB;
    @Byte
    private final Flags<SchFlag> flags;

    /**
     * Creates single character packet.
     * @param charB key to press
     * @param flags key flags
     */
    public SchPacket(char charB, Flags<SchFlag> flags) {
        super(8, PacketType.SCH, 0);
        this.charB = charB;
        this.flags = flags;
        PacketValidator.validate(this);
    }
    @Override
    public byte[] getBytes() {
        return new PacketBuilder(size, type, reqI)
                .writeZeroByte()
                .writeByte(charB)
                .writeByte(flags.getValue())
                .writeZeroBytes(2)
                .getBytes();
    }
}
