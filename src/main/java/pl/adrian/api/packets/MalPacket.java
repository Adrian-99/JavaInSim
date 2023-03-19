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
import pl.adrian.internal.packets.util.Constants;
import pl.adrian.internal.packets.util.PacketBuilder;
import pl.adrian.internal.packets.util.PacketUtils;
import pl.adrian.internal.packets.util.PacketValidator;

import java.util.List;
import java.util.stream.Stream;

/**
 * Mods ALlowed - variable size.
 */
public class MalPacket extends Packet implements InstructionPacket, RequestablePacket {
    @Byte
    private final short ucid;
    @Unsigned
    @Array(length = Constants.MAX_MODS, dynamicLength = true)
    private final ModSkinId[] skinId;

    /**
     * Creates mods allowed packet. Constructor used only internally!
     * @param reqI 0 unless this is a reply to a {@link pl.adrian.api.packets.enums.TinySubtype#MAL Tiny MAL} request
     * @param numM number of mods in this packet
     * @param ucid unique id of the connection that updated the list
     * @param skinId SkinID of each mod in compressed format, 0 to {@link Constants#MAX_MODS} (numM)
     */
    public MalPacket(short reqI, short numM, short ucid, long[] skinId) {
        super(8 + numM * 4, PacketType.MAL, reqI);
        this.ucid = ucid;
        this.skinId = new ModSkinId[numM];
        for (var i = 0; i < numM; i++) {
            this.skinId[i] = new ModSkinId(skinId[i]);
        }
    }

    /**
     * Creates mods allowed packet.
     * @param skinId list of skinIds of allowed mods (empty list to allow all mods to be used)
     * @throws PacketValidationException if validation of any field in packet fails
     */
    public MalPacket(List<String> skinId) throws PacketValidationException {
        super(
                PacketUtils.getPacketSize(8, skinId.size(), 4, Constants.MAX_MODS),
                PacketType.MAL,
                0
        );
        this.ucid = 0;
        this.skinId = skinId.stream().map(ModSkinId::new).toArray(ModSkinId[]::new);

        PacketValidator.validate(this);
    }

    @Override
    public byte[] getBytes() {
        return new PacketBuilder(size, type, reqI)
                .writeByte(skinId.length)
                .writeByte(ucid)
                .writeZeroBytes(3)
                .writeUnsignedArray(skinId)
                .getBytes();
    }

    /**
     * @return number of mods in this packet
     */
    public short getNumM() {
        return (short) skinId.length;
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
        return Stream.of(skinId).map(ModSkinId::getStringValue).toList();
    }
}
