package pl.adrian.api.packets;

import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.internal.packets.structures.ModSkinId;
import pl.adrian.internal.packets.annotations.Array;
import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.annotations.Unsigned;
import pl.adrian.internal.packets.base.InstructionPacket;
import pl.adrian.internal.packets.base.Packet;
import pl.adrian.internal.packets.base.RequestablePacket;
import pl.adrian.internal.packets.exceptions.PacketValidationException;
import pl.adrian.internal.packets.util.*;

import java.util.Arrays;
import java.util.List;

/**
 * Mods ALlowed - variable size.
 */
public class MalPacket extends Packet implements InstructionPacket, RequestablePacket {
    @Byte
    private final short ucid;
    @Unsigned
    @Array(length = Constants.MAL_MAX_MODS, dynamicLength = true)
    private final List<ModSkinId> skinId;

    /**
     * Creates mods allowed packet. Constructor used only internally.
     * @param size packet size
     * @param reqI 0 unless this is a reply to a {@link pl.adrian.api.packets.enums.TinySubtype#MAL Tiny MAL} request
     * @param packetDataBytes packet data bytes
     */
    public MalPacket(short size, short reqI, PacketDataBytes packetDataBytes) {
        super(size, PacketType.MAL, reqI);
        final var numM = packetDataBytes.readByte();
        ucid = packetDataBytes.readByte();
        packetDataBytes.skipZeroBytes(3);
        skinId = Arrays.stream(packetDataBytes.readUnsignedArray(numM)).mapToObj(ModSkinId::new).toList();
    }

    /**
     * Creates mods allowed packet.
     * @param skinId list of skinIds of allowed mods (empty list to allow all mods to be used)
     * @throws PacketValidationException if validation of any field in packet fails
     */
    public MalPacket(List<String> skinId) throws PacketValidationException {
        super(
                PacketUtils.getPacketSize(8, skinId.size(), 4, Constants.MAL_MAX_MODS),
                PacketType.MAL,
                0
        );
        this.ucid = 0;
        this.skinId = skinId.stream().map(ModSkinId::new).toList();

        PacketValidator.validate(this);
    }

    @Override
    public byte[] getBytes() {
        return new PacketBuilder(size, type, reqI)
                .writeByte(skinId.size())
                .writeByte(ucid)
                .writeZeroBytes(3)
                .writeUnsignedArray(skinId)
                .getBytes();
    }

    /**
     * @return number of mods in this packet
     */
    public short getNumM() {
        return (short) skinId.size();
    }

    /**
     * @return unique id of the connection that updated the list
     */
    public short getUcid() {
        return ucid;
    }

    /**
     * @return list of skinIds of allowed mods
     */
    public List<String> getSkinId() {
        return skinId.stream().map(ModSkinId::getStringValue).toList();
    }
}
