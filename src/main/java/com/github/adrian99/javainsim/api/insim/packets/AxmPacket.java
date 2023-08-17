/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets;

import com.github.adrian99.javainsim.api.insim.packets.enums.TinySubtype;
import com.github.adrian99.javainsim.api.insim.packets.enums.TtcSubtype;
import com.github.adrian99.javainsim.api.insim.InSimConnection;
import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;
import com.github.adrian99.javainsim.api.insim.packets.enums.PmoAction;
import com.github.adrian99.javainsim.api.common.flags.Flags;
import com.github.adrian99.javainsim.api.insim.packets.flags.IsiFlag;
import com.github.adrian99.javainsim.api.insim.packets.flags.PmoFlag;
import com.github.adrian99.javainsim.api.insim.packets.structures.objectinfo.ObjectInfo;
import com.github.adrian99.javainsim.api.insim.packets.structures.objectinfo.PositionObjectInfo;
import com.github.adrian99.javainsim.internal.common.util.PacketDataBytes;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Array;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Byte;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Structure;
import com.github.adrian99.javainsim.internal.insim.packets.base.InstructionPacket;
import com.github.adrian99.javainsim.internal.insim.packets.base.AbstractPacket;
import com.github.adrian99.javainsim.internal.insim.packets.base.RequestablePacket;
import com.github.adrian99.javainsim.internal.insim.packets.exceptions.PacketValidationException;
import com.github.adrian99.javainsim.internal.insim.packets.requests.builders.AxmPacketRequestBuilder;
import com.github.adrian99.javainsim.internal.insim.packets.util.Constants;
import com.github.adrian99.javainsim.internal.insim.packets.util.PacketBuilder;
import com.github.adrian99.javainsim.internal.insim.packets.util.PacketUtils;
import com.github.adrian99.javainsim.internal.insim.packets.util.PacketValidator;

import java.util.ArrayList;
import java.util.List;

/**
 * AutoX Multiple objects - variable size.<br>
 * If the {@link IsiFlag#AXM_LOAD} flag in the {@link IsiPacket} is set, those packets will be sent by LFS
 * when a layout is loaded.<br>
 * If the {@link IsiFlag#AXM_EDIT} flag in the {@link IsiPacket} is set, those packets will be sent by LFS
 * when objects are edited by user or InSim.<br>
 * It is possible to add or remove objects by sending this packet, however some care must be taken with it.<br>
 * To request these packets for all layout objects and circles the {@link #request} and
 * {@link AxmPacketRequestBuilder#forAllLayoutObjects} can be used.<br>
 * LFS will send as many packets as needed to describe the whole layout. If there are no objects or circles,
 * there will be one packet with zero NumO. The final packet will have the {@link PmoFlag#FILE_END} flag set.<br>
 * It is also possible to get (using {@link #request} and {@link AxmPacketRequestBuilder#forConnectionLayoutEditorSelection}) or
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
public class AxmPacket extends AbstractPacket implements InstructionPacket, RequestablePacket {
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
     * @param reqI 0 unless this is a reply to a {@link TinySubtype#AXM Tiny AXM}
     *             or {@link TtcSubtype#SEL Ttc SEL} request
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

    /**
     * Creates builder for packet request for {@link AxmPacket}.
     * @param inSimConnection InSim connection to request packet from
     * @return packet request builder
     */
    public static AxmPacketRequestBuilder request(InSimConnection inSimConnection) {
        return new AxmPacketRequestBuilder(inSimConnection);
    }
}
