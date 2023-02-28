package pl.adrian.api.packets;

import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.internal.packets.annotations.CharArray;
import pl.adrian.internal.packets.base.Packet;
import pl.adrian.internal.packets.base.SendablePacket;
import pl.adrian.internal.packets.util.PacketBuilder;

/**
 * MSg eXtended - like {@link MstPacket} but longer (not for commands).
 */
public class MsxPacket extends Packet implements SendablePacket {
    @CharArray(length = 96)
    private final String msg;

    /**
     * Creates msg extended packet.
     * @param msg message to send (max 95 characters)
     */
    public MsxPacket(String msg) {
        super(100, PacketType.MSX, 0);
        this.msg = msg;
    }

    @Override
    public byte[] getBytes() {
        return new PacketBuilder(size, type, reqI)
                .writeZeroByte()
                .writeCharArray(msg, 96, false)
                .getBytes();
    }
}
