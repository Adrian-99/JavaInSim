package pl.adrian.packets;

import pl.adrian.Constants;
import pl.adrian.packets.annotations.Byte;
import pl.adrian.packets.annotations.CharArray;
import pl.adrian.packets.annotations.Word;
import pl.adrian.packets.base.Packet;
import pl.adrian.packets.exceptions.PacketValidationException;
import pl.adrian.packets.flags.Flags;
import pl.adrian.packets.flags.ISF;
import pl.adrian.packets.enums.PacketType;
import pl.adrian.packets.base.SendablePacket;
import pl.adrian.packets.util.PacketBuilder;
import pl.adrian.packets.util.PacketValidator;

public class IS_INI extends Packet implements SendablePacket {
    @Word
    private final int udpPort;
    @Word
    private final Flags<ISF> flags;
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

    public IS_INI(Flags<ISF> flags,
                  Character prefix,
                  int interval,
                  String admin,
                  String iName) throws PacketValidationException {
        super(44, PacketType.ISP_ISI, 1);
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
