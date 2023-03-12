package pl.adrian.api.packets;

import pl.adrian.internal.packets.util.Constants;
import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.annotations.CharArray;
import pl.adrian.internal.packets.annotations.Word;
import pl.adrian.internal.packets.base.Packet;
import pl.adrian.internal.packets.exceptions.PacketValidationException;
import pl.adrian.api.packets.flags.Flags;
import pl.adrian.api.packets.flags.IsiFlag;
import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.internal.packets.base.InstructionPacket;
import pl.adrian.internal.packets.util.PacketBuilder;
import pl.adrian.internal.packets.util.PacketValidator;

/**
 * InSim Init - packet to initialise the InSim system.
 */
public class IsiPacket extends Packet implements InstructionPacket {
    @Word
    private final int udpPort;
    @Word
    private final Flags<IsiFlag> flags;
    @Byte
    private final short inSimVer;
    @Byte
    private final Character prefix;
    @Word
    private final int interval;
    @CharArray(length = 16, strictLengthValidation = true)
    private final String admin;
    @CharArray(length = 16, strictLengthValidation = true)
    private final String iName;

    /**
     * Creates InSim Init packet
     * @param flags bit flags for options
     * @param prefix special host message prefix character
     * @param interval time in ms between NLP or MCI (0 = none)
     * @param admin admin password (if set in LFS)
     * @param iName a short name for your program
     * @throws PacketValidationException if validation of any field in packet fails
     */
    public IsiPacket(Flags<IsiFlag> flags,
                     Character prefix,
                     int interval,
                     String admin,
                     String iName) throws PacketValidationException {
        super(44, PacketType.ISI, 1);
        this.udpPort = 0;
        this.flags = flags;
        this.inSimVer = Constants.INSIM_VERSION;
        this.prefix = prefix;
        this.interval = interval;
        this.admin = admin;
        this.iName = iName;
        PacketValidator.validate(this);
    }

    @Override
    public byte[] getBytes() {
        return new PacketBuilder(size, type, reqI)
                .writeZeroByte()
                .writeWord(udpPort)
                .writeWord(flags.getValue())
                .writeByte(inSimVer)
                .writeByte(prefix)
                .writeWord(interval)
                .writeCharArray(admin, 16, false)
                .writeCharArray(iName, 16, false)
                .getBytes();
    }
}
