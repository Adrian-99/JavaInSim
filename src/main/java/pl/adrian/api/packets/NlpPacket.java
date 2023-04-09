package pl.adrian.api.packets;

import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.api.packets.structures.NodeLap;
import pl.adrian.internal.packets.annotations.Array;
import pl.adrian.internal.packets.annotations.Structure;
import pl.adrian.internal.packets.base.Packet;
import pl.adrian.internal.packets.base.RequestablePacket;
import pl.adrian.internal.packets.util.Constants;
import pl.adrian.internal.packets.util.PacketDataBytes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Node and Lap Packet - variable size. To receive this packet at a specified interval:<br>
 * 1) Set the Interval field in the {@link IsiPacket} packet (10, 20, 30... 8000 ms),<br>
 * 2) Set flag {@link pl.adrian.api.packets.flags.IsiFlag#NLP Isi NLP} in the {@link IsiPacket}.
 */
public class NlpPacket extends Packet implements RequestablePacket {
    @Structure
    @Array(length = Constants.MAX_CARS, dynamicLength = true)
    private final List<NodeLap> info;

    /**
     * Creates node and lap packet. Constructor used only internally.
     * @param size packet size
     * @param reqI 0 unless this is a reply to a {@link pl.adrian.api.packets.enums.TinySubtype#NLP Tiny NLP} request
     * @param packetDataBytes packet data bytes
     */
    public NlpPacket(short size, short reqI, PacketDataBytes packetDataBytes) {
        super(size, PacketType.NLP, reqI);
        var numP = packetDataBytes.readByte();
        var infoTmp = new ArrayList<NodeLap>();
        for (var i = 0; i < numP; i++) {
            infoTmp.add(new NodeLap(packetDataBytes));
        }
        info = Collections.unmodifiableList(infoTmp);
    }

    /**
     * @return number of players in race
     */
    public short getNumP() {
        return (short) info.size();
    }

    /**
     * @return node and lap of each player
     */
    public List<NodeLap> getInfo() {
        return info;
    }
}
