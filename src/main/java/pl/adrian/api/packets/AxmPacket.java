package pl.adrian.api.packets;

import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.api.packets.enums.PmoAction;
import pl.adrian.api.packets.flags.Flags;
import pl.adrian.api.packets.flags.PmoFlag;
import pl.adrian.api.packets.structures.objectinfo.ObjectInfo;
import pl.adrian.internal.packets.annotations.Array;
import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.annotations.Structure;
import pl.adrian.internal.packets.base.InstructionPacket;
import pl.adrian.internal.packets.base.Packet;
import pl.adrian.internal.packets.base.RequestablePacket;
import pl.adrian.internal.packets.exceptions.PacketValidationException;
import pl.adrian.internal.packets.util.*;

import java.util.ArrayList;
import java.util.List;

/**
 * AutoX Multiple objects - variable size.
 */
public class AxmPacket extends Packet implements InstructionPacket, RequestablePacket {
    @Byte
    private final short ucid;
    @Byte
    private final PmoAction pmoAction;
    @Byte
    private final Flags<PmoFlag> pmoFlags;
    @Structure
    @Array(length = Constants.AXM_MAX_OBJECTS, dynamicLength = true)
    private final List<ObjectInfo> info;

    /**
     * Creates autoX multiple objects packet. Constructor used only internally.
     * @param size packet size
     * @param reqI packet reqI
     * @param packetDataBytes packet data bytes
     */
    public AxmPacket(short size, short reqI, PacketDataBytes packetDataBytes) {
        super(size, PacketType.AXM, reqI);
        var numO = packetDataBytes.readByte();
        ucid = packetDataBytes.readByte();
        pmoAction = PmoAction.fromOrdinal(packetDataBytes.readByte());
        pmoFlags = new Flags<>(PmoFlag.class, packetDataBytes.readByte());
        packetDataBytes.skipZeroByte();
        info = new ArrayList<>();
        for (var i = 0; i < numO; i++) {
            info.add(ObjectInfo.read(packetDataBytes));
        }
    }

    /**
     * Creates autoX multiple objects packet.
     * @param ucid unique id of the connection that sent the packet
     * @param pmoAction requested action
     * @param pmoFlags flags
     * @param info info about each object
     * @throws PacketValidationException if validation of any field in packet fails
     */
    public AxmPacket(int ucid,
                     PmoAction pmoAction,
                     Flags<PmoFlag> pmoFlags,
                     List<ObjectInfo> info) throws PacketValidationException {
        super(
                PacketUtils.getPacketSize(8, info.size(), 8, Constants.AXM_MAX_OBJECTS),
                PacketType.AXM,
                0
        );
        this.ucid = (short) ucid;
        this.pmoAction = pmoAction;
        this.pmoFlags = pmoFlags;
        this.info = info;
        PacketValidator.validate(this);
    }

    /**
     * @return number of objects in this packet
     */
    public short getNumO() {
        return (short) info.size();
    }

    /**
     * @return unique id of the connection that sent the packet
     */
    public short getUcid() {
        return ucid;
    }

    /**
     * @return reported or requested action
     */
    public PmoAction getPmoAction() {
        return pmoAction;
    }

    /**
     * @return flags
     */
    public Flags<PmoFlag> getPmoFlags() {
        return pmoFlags;
    }

    /**
     * @return info about each object
     */
    public List<ObjectInfo> getInfo() {
        return info;
    }

    @Override
    public byte[] getBytes() {
        return new PacketBuilder(size, type, reqI)
                .writeByte(info.size())
                .writeByte(ucid)
                .writeByte(pmoAction.ordinal())
                .writeByte(pmoFlags.getByteValue())
                .writeZeroByte()
                .writeStructureArray(info, 8)
                .getBytes();
    }
}
