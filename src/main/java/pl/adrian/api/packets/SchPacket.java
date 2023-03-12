package pl.adrian.api.packets;

import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.api.packets.flags.Flags;
import pl.adrian.api.packets.flags.SchFlag;
import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.base.Packet;
import pl.adrian.internal.packets.base.InstructionPacket;
import pl.adrian.internal.packets.exceptions.PacketValidationException;
import pl.adrian.internal.packets.util.PacketBuilder;
import pl.adrian.internal.packets.util.PacketValidator;

/**
 * Single CHaracter. Allows sending individual key presses to LFS.
 * For standard keys (e.g. V and H) a capital letter should be sent.
 * This does not work with some keys like F keys, arrows or CTRL keys.
 * Use {@link MstPacket} with the /press /shift /ctrl /alt commands for this purpose.
 */
public class SchPacket extends Packet implements InstructionPacket {
    @Byte
    private final char charB;
    @Byte
    private final Flags<SchFlag> flags;

    /**
     * Creates single character packet.
     * @param charB key to press
     * @param flags key flags
     * @throws PacketValidationException if validation of any field in packet fails
     */
    public SchPacket(char charB, Flags<SchFlag> flags) throws PacketValidationException {
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
