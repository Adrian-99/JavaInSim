package pl.adrian.api.packets;

import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.internal.packets.annotations.Array;
import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.base.InstructionPacket;
import pl.adrian.internal.packets.base.Packet;
import pl.adrian.internal.packets.base.RequestablePacket;
import pl.adrian.internal.packets.util.PacketBuilder;
import pl.adrian.internal.packets.util.PacketUtils;
import pl.adrian.internal.packets.util.PacketValidator;

import java.util.List;

/**
 * REOrder. The packet can be sent in either direction.<br>
 * LFS sends one at the start of every race or qualifying session, listing the start order.<br>
 * The packet can be sent to LFS in two different ways, to specify the starting order:<br>
 * 1) In the race setup screen, to immediately rearrange the grid when the packet arrives.<br>
 * 2) In game, just before a restart or exit, to specify the order on the restart or exit.
 * If sending an {@link ReoPacket} in game, the packet should be sent when
 * {@link pl.adrian.api.packets.enums.SmallSubtype#VTA Small VTA} is received
 * informing that the Vote Action (VOTE_END / VOTE_RESTART / VOTE_QUALIFY) is about
 * to take place. Any {@link ReoPacket} received before the
 * {@link pl.adrian.api.packets.enums.SmallSubtype#VTA Small VTA} is sent will be ignored.
 */
public class ReoPacket extends Packet implements InstructionPacket, RequestablePacket {
    @Byte
    @Array(length = 40, dynamicLength = true)
    private final List<Short> plid;

    /**
     * Creates reorder packet. Constructor used only internally!
     * @param reqI 0 unless this is a reply to an {@link pl.adrian.api.packets.enums.TinySubtype#REO Tiny REO} request
     * @param plid all PLIDs in new order
     */
    public ReoPacket(short reqI, short[] plid) {
        super(44, PacketType.REO, reqI);
        this.plid = PacketUtils.toList(plid);
    }

    /**
     * Creates reorder packet.
     * @param plid all PLIDs in new order
     */
    public ReoPacket(List<Integer> plid) {
        super(44, PacketType.REO, 0);
        this.plid = plid.stream().map(Integer::shortValue).toList();
        PacketValidator.validate(this);
    }

    @Override
    public byte[] getBytes() {
        return new PacketBuilder(size, type, reqI)
                .writeByte(plid.size())
                .writeByteArray(plid, 40)
                .getBytes();
    }

    /**
     * @return number of players in race
     */
    public short getNumP() {
        return (short) plid.size();
    }

    /**
     * @return all PLIDs in new order
     */
    public List<Short> getPlid() {
        return plid;
    }
}
