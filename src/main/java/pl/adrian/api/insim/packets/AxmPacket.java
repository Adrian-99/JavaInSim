package pl.adrian.api.insim.packets;

import pl.adrian.api.insim.InSimConnection;
import pl.adrian.api.insim.PacketListener;
import pl.adrian.api.insim.packets.enums.PacketType;
import pl.adrian.api.insim.packets.enums.PmoAction;
import pl.adrian.api.common.flags.Flags;
import pl.adrian.api.insim.packets.flags.IsiFlag;
import pl.adrian.api.insim.packets.flags.PmoFlag;
import pl.adrian.api.insim.packets.structures.objectinfo.ObjectInfo;
import pl.adrian.api.insim.packets.structures.objectinfo.PositionObjectInfo;
import pl.adrian.internal.common.util.PacketDataBytes;
import pl.adrian.internal.insim.packets.annotations.Array;
import pl.adrian.internal.insim.packets.annotations.Byte;
import pl.adrian.internal.insim.packets.annotations.Structure;
import pl.adrian.internal.insim.packets.base.InstructionPacket;
import pl.adrian.internal.insim.packets.base.Packet;
import pl.adrian.internal.insim.packets.base.RequestablePacket;
import pl.adrian.internal.insim.packets.exceptions.PacketValidationException;
import pl.adrian.internal.insim.packets.util.*;

import java.util.ArrayList;
import java.util.List;

/**
 * AutoX Multiple objects - variable size.<br>
 * If the {@link IsiFlag#AXM_LOAD} flag in the {@link IsiPacket} is set, those packets will be sent by LFS
 * when a layout is loaded.<br>
 * If the {@link IsiFlag#AXM_EDIT} flag in the {@link IsiPacket} is set, those packets will be sent by LFS
 * when objects are edited by user or InSim.<br>
 * It is possible to add or remove objects by sending this packet, however some care must be taken with it.<br>
 * To request these packets for all layout objects and circles the {@link InSimConnection#request(Class, PacketListener)}
 * can be used.<br>
 * LFS will send as many packets as needed to describe the whole layout. If there are no objects or circles,
 * there will be one packet with zero NumO. The final packet will have the {@link PmoFlag#FILE_END} flag set.<br>
 * It is also possible to get (using {@link InSimConnection#request(int, PacketListener)}) or
 * set ({@link PmoAction#POSITION}) the current editor selection.<br>
 * The packet with {@link PmoAction#POSITION} is sent with a single object in the packet if a user
 * presses O without any object type selected. Information only - no object is added. The only valid values
 * in Info are X, Y, Zbyte and Heading.<br>
 * {@link PmoAction#GET_Z} can be used to request the resulting Zbyte values for given X, Y, Zbyte
 * positions listed in the packet. A similar reply (information only) will be sent
 * with adjusted Zbyte values. Index and Heading are ignored and set to zero in the
 * reply. Flags is set to 0x80 if Zbyte was successfully adjusted, zero if not.
 * Suggested input values for Zbyte are either 240 to get the highest point at X, Y
 * or you may use the approximate altitude (see layout file format).
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
        var asUnknownObjectInfo = pmoAction.equals(PmoAction.GET_Z) || pmoAction.equals(PmoAction.POSITION);
        for (var i = 0; i < numO; i++) {
            info.add(ObjectInfo.read(packetDataBytes, asUnknownObjectInfo));
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
     * Creates autoX multiple objects packet with {@link PmoAction#GET_Z} action.
     * @param info info about each position
     * @param reqI reqI, must not be zero
     * @throws PacketValidationException if validation of any field in packet fails
     */
    public AxmPacket(List<PositionObjectInfo> info, int reqI) throws PacketValidationException {
        super(
                PacketUtils.getPacketSize(8, info.size(), 8, Constants.AXM_MAX_OBJECTS),
                PacketType.AXM,
                reqI
        );
        this.ucid = 0;
        pmoAction = PmoAction.GET_Z;
        pmoFlags = new Flags<>();
        this.info = new ArrayList<>();
        this.info.addAll(info);
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
