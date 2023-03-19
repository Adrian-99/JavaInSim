package pl.adrian.api.packets;

import pl.adrian.api.packets.enums.AcrResult;
import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.internal.packets.annotations.Array;
import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.annotations.Char;
import pl.adrian.internal.packets.base.Packet;
import pl.adrian.internal.packets.base.InfoPacket;
import pl.adrian.internal.packets.util.PacketUtils;

/**
 * Admin Command Report - a user typed an admin command - variable size.
 */
public class AcrPacket extends Packet implements InfoPacket {
    @Byte
    private final short ucid;
    @Byte
    private final boolean isAdmin;
    @Byte
    private final AcrResult result;
    @Char
    @Array(length = 64)
    private final String text;

    /**
     * Creates admin command report packet.
     * @param ucid connection's unique id (0 = host)
     * @param isAdmin true if user is an admin
     * @param result command result
     * @param text command text
     */
    public AcrPacket(short ucid, boolean isAdmin, AcrResult result, String text) {
        super(PacketUtils.getPacketSize(8, text, 64), PacketType.ACR, 0);
        this.ucid = ucid;
        this.isAdmin = isAdmin;
        this.result = result;
        this.text = text;
    }

    /**
     * @return connection's unique id (0 = host)
     */
    public short getUcid() {
        return ucid;
    }

    /**
     * @return true if user is an admin
     */
    public boolean isAdmin() {
        return isAdmin;
    }

    /**
     * @return command result
     */
    public AcrResult getResult() {
        return result;
    }

    /**
     * @return command text
     */
    public String getText() {
        return text;
    }
}
