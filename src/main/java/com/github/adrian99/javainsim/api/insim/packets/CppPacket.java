/*
 * Copyright (c) 2024, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets;

import com.github.adrian99.javainsim.api.insim.packets.subtypes.tiny.TinySubtypes;
import com.github.adrian99.javainsim.internal.common.util.PacketDataBytes;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Byte;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Float;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Structure;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Word;
import com.github.adrian99.javainsim.internal.insim.packets.base.InstructionPacket;
import com.github.adrian99.javainsim.api.common.flags.Flags;
import com.github.adrian99.javainsim.api.common.structures.Vec;
import com.github.adrian99.javainsim.api.insim.InSimConnection;
import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;
import com.github.adrian99.javainsim.api.insim.packets.enums.ViewIdentifier;
import com.github.adrian99.javainsim.api.insim.packets.flags.CppFlag;
import com.github.adrian99.javainsim.internal.insim.packets.base.AbstractPacket;
import com.github.adrian99.javainsim.internal.insim.packets.base.RequestablePacket;
import com.github.adrian99.javainsim.internal.insim.packets.exceptions.PacketValidationException;
import com.github.adrian99.javainsim.internal.insim.packets.requests.builders.SingleTinyPacketRequestBuilder;
import com.github.adrian99.javainsim.internal.insim.packets.util.PacketBuilder;
import com.github.adrian99.javainsim.internal.insim.packets.util.PacketValidator;

/**
 * Cam Pos Pack - Full camera packet (in car OR free view mode).<br>
 * On receiving this packet, LFS will set up the camera
 * to match the values in the packet, including switching into or out of free view mode depending on the
 * {@link CppFlag#SHIFTU} flag.<br>
 * If {@link CppFlag#VIEW_OVERRIDE} is set, the in-car view Heading, Pitch, Roll and FOV [not smooth]
 * can be set using this packet. Otherwise, normal in game control will be used.<br>
 * Position vector ({@link #pos}) - in free view mode, {@link #pos} can be either relative or absolute.
 * If {@link CppFlag#SHIFTU_FOLLOW} is set, it's a following camera, so the position is relative to
 * the selected car  Otherwise, the position is absolute, as used in normal free view mode.<br>
 * The {@link #time} value in the packet is used for camera smoothing. A zero {@link #time} means instant
 * positioning. Any other value (milliseconds) will cause the camera to move smoothly to
 * the requested position in that time. This is most useful in free view camera modes or
 * for smooth changes of internal view when using the {@link CppFlag#VIEW_OVERRIDE} flag.<br>
 * NOTE: You can use frequently updated camera positions with a longer {@link #time} value than
 * the update frequency. For example, sending a camera position every 100 ms, with a
 * {@link #time} value of 1000 ms. LFS will make a smooth motion from the rough inputs.<br>
 * If the requested camera mode is different from the one LFS is already in, it cannot
 * move smoothly to the new position, so in this case the {@link #time} value is ignored.
 */
public class CppPacket extends AbstractPacket implements InstructionPacket, RequestablePacket {
    @Structure
    private final Vec pos;
    @Word
    private final int h;
    @Word
    private final int p;
    @Word
    private final int r;
    @Byte
    private final short viewPlid;
    @Byte
    private final ViewIdentifier inGameCam;
    @Float
    private final float fov;
    @Word
    private final int time;
    @Word
    private final Flags<CppFlag> flags;

    /**
     * Creates cam pos pack packet. Constructor used only internally.
     * @param reqI 0 unless this is a reply to an {@link TinySubtypes#SCP Tiny SCP} request
     * @param packetDataBytes packet data bytes
     */
    public CppPacket(short reqI, PacketDataBytes packetDataBytes) {
        super(32, PacketType.CPP, reqI);
        packetDataBytes.skipZeroByte();
        pos = new Vec(packetDataBytes);
        h = packetDataBytes.readWord();
        p = packetDataBytes.readWord();
        r = packetDataBytes.readWord();
        viewPlid = packetDataBytes.readByte();
        inGameCam = ViewIdentifier.fromOrdinal(packetDataBytes.readByte());
        fov = packetDataBytes.readFloat();
        time = packetDataBytes.readWord();
        flags = new Flags<>(CppFlag.class, packetDataBytes.readWord());
    }

    /**
     * Creates cam pos pack packet.
     * @param pos position vector
     * @param h heading - 0 points along Y axis
     * @param p pitch
     * @param r roll
     * @param viewPlid unique ID of viewed player (0 = none) - set to 255 to leave unchanged
     * @param inGameCam camera - set to 255 ({@link ViewIdentifier#ANOTHER}) to leave unchanged
     * @param fov FOV in degrees
     * @param time time in ms to get there (0 means instant)
     * @param flags flags
     * @throws PacketValidationException if validation of any field in packet fails
     */
    @SuppressWarnings("java:S107")
    public CppPacket(Vec pos,
                     int h,
                     int p,
                     int r,
                     int viewPlid,
                     ViewIdentifier inGameCam,
                     float fov,
                     int time,
                     Flags<CppFlag> flags) throws PacketValidationException {
        super(32, PacketType.CPP, 0);
        this.pos = pos;
        this.h = h;
        this.p = p;
        this.r = r;
        this.viewPlid = (short) viewPlid;
        this.inGameCam = inGameCam;
        this.fov = fov;
        this.time = time;
        this.flags = flags;
        PacketValidator.validate(this);
    }

    /**
     * @return position vector
     */
    public Vec getPos() {
        return pos;
    }

    /**
     * @return heading - 0 points along Y axis
     */
    public int getH() {
        return h;
    }

    /**
     * @return pitch
     */
    public int getP() {
        return p;
    }

    /**
     * @return roll
     */
    public int getR() {
        return r;
    }

    /**
     * @return unique ID of viewed player (0 = none)
     */
    public short getViewPlid() {
        return viewPlid;
    }

    /**
     * @return camera
     */
    public ViewIdentifier getInGameCam() {
        return inGameCam;
    }

    /**
     * @return FOV in degrees
     */
    public float getFov() {
        return fov;
    }

    /**
     * @return time in ms to get there (0 means instant)
     */
    public int getTime() {
        return time;
    }

    /**
     * @return flags
     */
    public Flags<CppFlag> getFlags() {
        return flags;
    }

    @Override
    public byte[] getBytes() {
        return new PacketBuilder(size, type, reqI)
                .writeZeroByte()
                .writeStructure(pos, 12)
                .writeWord(h)
                .writeWord(p)
                .writeWord(r)
                .writeByte(viewPlid)
                .writeByte(inGameCam.getValue())
                .writeFloat(fov)
                .writeWord(time)
                .writeWord(flags.getWordValue())
                .getBytes();
    }

    /**
     * Creates builder for packet request for {@link CppPacket}.
     * @param inSimConnection InSim connection to request packet from
     * @return packet request builder
     */
    public static SingleTinyPacketRequestBuilder<CppPacket> request(InSimConnection inSimConnection) {
        return new SingleTinyPacketRequestBuilder<>(inSimConnection, TinySubtypes.SCP);
    }
}
