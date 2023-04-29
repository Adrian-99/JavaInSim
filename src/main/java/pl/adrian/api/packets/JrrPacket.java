package pl.adrian.api.packets;

import pl.adrian.api.packets.enums.JrrAction;
import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.api.packets.structures.objectinfo.PositionObjectInfo;
import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.annotations.Structure;
import pl.adrian.internal.packets.base.InstructionPacket;
import pl.adrian.internal.packets.base.Packet;
import pl.adrian.internal.packets.exceptions.PacketValidationException;
import pl.adrian.internal.packets.util.PacketBuilder;
import pl.adrian.internal.packets.util.PacketValidator;

/**
 * Join Request Reply. The packet should be sent to LFS in response to a join request
 * ({@link NplPacket} with zero in NumP field). In this case, {@link JrrPacket#plid}
 * must be zero and {@link JrrPacket#jrrAction} must be {@link JrrAction#REJECT} or
 * {@link JrrAction#SPAWN}. Upon allowing the join, and if is successful, LFS will
 * send a normal {@link NplPacket} with NumP set. It is also possible to specify the start
 * position of the car using the {@link JrrPacket#startPos} structure.<br>
 * The packet can also be used to move an existing car to a different location
 * In this case, {@link JrrPacket#plid} must be set, {@link JrrPacket#jrrAction} must be
 * {@link JrrAction#RESET} or higher and {@link JrrPacket#startPos} must be set.
 */
public class JrrPacket extends Packet implements InstructionPacket {
    @Byte
    private final short plid;
    @Byte
    private final short ucid;
    @Byte
    private final JrrAction jrrAction;
    @Structure
    private final PositionObjectInfo startPos;

    /**
     * Creates join request reply packet with default start point.
     * @param plid ZERO when this is a reply to a join request - SET to move a car
     * @param ucid set when this is a reply to a join request - ignored when moving a car
     * @param jrrAction action
     * @throws PacketValidationException if validation of any field in packet fails
     */
    public JrrPacket(int plid, int ucid, JrrAction jrrAction) throws PacketValidationException {
        this(plid, ucid, jrrAction, null);
    }

    /**
     *
     * @param plid ZERO when this is a reply to a join request - SET to move a car
     * @param ucid set when this is a reply to a join request - ignored when moving a car
     * @param jrrAction action
     * @param startPos start point
     * @throws PacketValidationException if validation of any field in packet fails
     */
    public JrrPacket(int plid, int ucid, JrrAction jrrAction, PositionObjectInfo startPos)
            throws PacketValidationException {
        super(16, PacketType.JRR, 0);
        this.plid = (short) plid;
        this.ucid = (short) ucid;
        this.jrrAction = jrrAction;
        this.startPos = startPos;
        PacketValidator.validate(this);
    }

    @Override
    public byte[] getBytes() {
        return new PacketBuilder(size, type, reqI)
                .writeByte(plid)
                .writeByte(ucid)
                .writeByte(jrrAction.getValue())
                .writeZeroBytes(2)
                .writeStructure(startPos, 8)
                .getBytes();
    }
}
