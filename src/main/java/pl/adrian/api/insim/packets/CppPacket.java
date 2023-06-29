package pl.adrian.api.insim.packets;

import pl.adrian.api.common.flags.Flags;
import pl.adrian.api.common.structures.Vec;
import pl.adrian.api.insim.packets.enums.PacketType;
import pl.adrian.api.insim.packets.enums.ViewIdentifier;
import pl.adrian.api.insim.packets.flags.CppFlag;
import pl.adrian.internal.common.util.PacketDataBytes;
import pl.adrian.internal.insim.packets.annotations.Byte;
import pl.adrian.internal.insim.packets.annotations.Float;
import pl.adrian.internal.insim.packets.annotations.Structure;
import pl.adrian.internal.insim.packets.annotations.Word;
import pl.adrian.internal.insim.packets.base.InstructionPacket;
import pl.adrian.internal.insim.packets.base.Packet;
import pl.adrian.internal.insim.packets.base.RequestablePacket;
import pl.adrian.internal.insim.packets.util.PacketBuilder;

/**
 * Cam Pos Pack - Full camera packet (in car OR SHIFT+U mode).
 */
public class CppPacket extends Packet implements InstructionPacket, RequestablePacket {
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
     * @param reqI 0 unless this is a reply to an {@link pl.adrian.api.insim.packets.enums.TinySubtype#SCP Tiny SCP} request
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
                     Flags<CppFlag> flags) {
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
}
