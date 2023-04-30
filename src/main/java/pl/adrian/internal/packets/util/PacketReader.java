package pl.adrian.internal.packets.util;

import pl.adrian.api.packets.*;
import pl.adrian.api.packets.enums.*;
import pl.adrian.internal.packets.base.InfoPacket;
import pl.adrian.internal.packets.exceptions.PacketReadingException;

/**
 * This class is a helper that is used to convert incoming byte array to appropriate {@link InfoPacket}.
 */
public class PacketReader {
    private final short packetSize;
    private final PacketType packetType;
    private final short packetReqI;

    /**
     * Creates packet reader, which helps to convert byte array to appropriate {@link InfoPacket}.
     * @param headerBytes header bytes of packet - length must be equal to 3 (size, type, reqI)
     * @throws PacketReadingException if length of header bytes was not equal to 3
     */
    public PacketReader(byte[] headerBytes) throws PacketReadingException {
        if (headerBytes.length == Constants.PACKET_HEADER_SIZE) {
            packetSize = (short) (PacketDataBytes.convertByte(headerBytes[0]) * 4);
            packetType = PacketType.fromOrdinal(PacketDataBytes.convertByte(headerBytes[1]));
            packetReqI = PacketDataBytes.convertByte(headerBytes[2]);
        } else {
            throw new PacketReadingException(
                    "Received packet header bytes length was not equal to " + Constants.PACKET_HEADER_SIZE
            );
        }
    }

    /**
     * @return packet identifier extracted from header bytes
     */
    public PacketType getPacketType() {
        return packetType;
    }

    /**
     * @return packet reqI value extracted from header bytes
     */
    public short getPacketReqI() {
        return packetReqI;
    }

    /**
     * @return count of data bytes of packet (total packet size minus header size)
     */
    public int getDataBytesCount() {
        return packetSize - Constants.PACKET_HEADER_SIZE;
    }

    /**
     * Converts data bytes and header bytes (passed in constructor) into appropriate {@link InfoPacket}.
     * @param dataBytes data bytes of packet
     * @return packet class instance
     * @throws PacketReadingException if reading given packet type (type is extracted from header bytes)
     * is not supported
     */
    @SuppressWarnings("java:S1479")
    public InfoPacket read(byte[] dataBytes) throws PacketReadingException {
        final var packetDataBytes = new PacketDataBytes(dataBytes);
        return switch (packetType) {
            case VER -> new VerPacket(packetReqI, packetDataBytes);
            case TINY -> new TinyPacket(packetReqI, packetDataBytes);
            case SMALL -> new SmallPacket(packetReqI, packetDataBytes);
            case STA -> new StaPacket(packetReqI, packetDataBytes);
            case MSO -> new MsoPacket(packetSize, packetDataBytes);
            case III -> new IiiPacket(packetSize, packetDataBytes);
            case ACR -> new AcrPacket(packetSize, packetDataBytes);
            case ISM -> new IsmPacket(packetReqI, packetDataBytes);
            case VTN -> new VtnPacket(packetDataBytes);
            case RST -> new RstPacket(packetReqI, packetDataBytes);
            case NCN -> new NcnPacket(packetReqI, packetDataBytes);
            case NCI -> new NciPacket(packetReqI, packetDataBytes);
            case SLC -> new SlcPacket(packetReqI, packetDataBytes);
            case MAL -> new MalPacket(packetSize, packetReqI, packetDataBytes);
            case CIM -> new CimPacket(packetDataBytes);
            case CNL -> new CnlPacket(packetDataBytes);
            case CPR -> new CprPacket(packetDataBytes);
            case NPL -> new NplPacket(packetReqI, packetDataBytes);
            case PLP -> new PlpPacket(packetDataBytes);
            case PLL -> new PllPacket(packetDataBytes);
            case CRS -> new CrsPacket(packetDataBytes);
            case LAP -> new LapPacket(packetDataBytes);
            case SPX -> new SpxPacket(packetDataBytes);
            case PIT -> new PitPacket(packetDataBytes);
            case PSF -> new PsfPacket(packetDataBytes);
            case PLA -> new PlaPacket(packetDataBytes);
            case CCH -> new CchPacket(packetDataBytes);
            case PEN -> new PenPacket(packetDataBytes);
            case TOC -> new TocPacket(packetDataBytes);
            case FLG -> new FlgPacket(packetDataBytes);
            case PFL -> new PflPacket(packetDataBytes);
            case FIN -> new FinPacket(packetDataBytes);
            case RES -> new ResPacket(packetReqI, packetDataBytes);
            case REO -> new ReoPacket(packetReqI, packetDataBytes);
            case AXI -> new AxiPacket(packetReqI, packetDataBytes);
            case AXO -> new AxoPacket(packetDataBytes);
            case NLP -> new NlpPacket(packetSize, packetReqI, packetDataBytes);
            case MCI -> new MciPacket(packetSize, packetReqI, packetDataBytes);
            case CON -> new ConPacket(packetDataBytes);
            case OBH -> new ObhPacket(packetDataBytes);
            case HLV -> new HlvPacket(packetDataBytes);
            case UCO -> new UcoPacket(packetDataBytes);
            case CSC -> new CscPacket(packetDataBytes);
            default -> throw new PacketReadingException("Unrecognized readable packet type");
        };
    }
}
