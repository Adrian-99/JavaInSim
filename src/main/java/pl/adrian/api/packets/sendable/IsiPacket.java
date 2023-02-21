package pl.adrian.api.packets.sendable;

import pl.adrian.internal.Constants;
import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.annotations.CharArray;
import pl.adrian.internal.packets.annotations.Word;
import pl.adrian.internal.packets.base.Packet;
import pl.adrian.internal.packets.exceptions.PacketValidationException;
import pl.adrian.internal.packets.flags.Flags;
import pl.adrian.api.packets.flags.IsiFlag;
import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.internal.packets.base.SendablePacket;
import pl.adrian.internal.packets.util.PacketBuilder;
import pl.adrian.internal.packets.util.PacketValidator;

public class IsiPacket extends Packet implements SendablePacket {
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
    @CharArray(maxLength = 16)
    private final String admin;
    @CharArray(maxLength = 16)
    private final String iName;

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
                .writeCharArray(admin, 16)
                .writeCharArray(iName, 16)
                .getBytes();
    }
}
