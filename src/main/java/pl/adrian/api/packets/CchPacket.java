package pl.adrian.api.packets;

import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.api.packets.enums.ViewIdentifier;
import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.base.InfoPacket;
import pl.adrian.internal.packets.base.Packet;
import pl.adrian.internal.packets.util.PacketDataBytes;

/**
 * Camera CHange. The packet is sent by LFS when any player changes the camera.<br>
 * To track cameras 3 points need to considered:<br>
 * 1) The default camera: {@link ViewIdentifier#DRIVER}<br>
 * 2) Player flags: {@link pl.adrian.api.packets.flags.PlayerFlag#CUSTOM_VIEW PlayerFlag.CUSTOM_VIEW}
 * means {@link ViewIdentifier#CUSTOM} at start or pit exit<br>
 * 3) {@link CchPacket}: sent when an existing driver changes camera
 */
public class CchPacket extends Packet implements InfoPacket {
    @Byte
    private final short plid;
    @Byte
    private final ViewIdentifier camera;

    /**
     * Creates camera change packet. Constructor used only internally.
     * @param packetDataBytes packet data bytes
     */
    public CchPacket(PacketDataBytes packetDataBytes) {
        super(8, PacketType.CCH, 0);
        plid = packetDataBytes.readByte();
        camera = ViewIdentifier.fromOrdinal(packetDataBytes.readByte());
        packetDataBytes.skipZeroBytes(3);
    }

    /**
     * @return player's unique id
     */
    public short getPlid() {
        return plid;
    }

    /**
     * @return view identifier
     */
    public ViewIdentifier getCamera() {
        return camera;
    }
}
