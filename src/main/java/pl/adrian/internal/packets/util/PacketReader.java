package pl.adrian.internal.packets.util;

import pl.adrian.api.packets.*;
import pl.adrian.api.packets.enums.*;
import pl.adrian.api.packets.flags.Flags;
import pl.adrian.api.packets.flags.NcnFlag;
import pl.adrian.api.packets.flags.RaceFlag;
import pl.adrian.api.packets.flags.StaFlag;
import pl.adrian.internal.packets.base.InfoPacket;
import pl.adrian.internal.packets.exceptions.PacketReadingException;

/**
 * This class is a helper that is used while converting byte array to appropriate {@link InfoPacket}.
 */
public class PacketReader {
    private final int packetSize;
    private final PacketType packetType;
    private final short packetReqI;
    private byte[] dataBytes;
    private int dataBytesReaderIndex;

    /**
     * Creates packet reader, which helps to convert byte array to appropriate {@link InfoPacket}.
     * @param headerBytes header bytes of packet - length must be equal to 3 (size, type, reqI)
     * @throws PacketReadingException if length of header bytes was not equal to 3
     */
    public PacketReader(byte[] headerBytes) throws PacketReadingException {
        if (headerBytes.length == Constants.PACKET_HEADER_SIZE) {
            packetSize = convertByte(headerBytes[0]) * 4;
            packetType = PacketType.fromOrdinal(convertByte(headerBytes[1]));
            packetReqI = convertByte(headerBytes[2]);
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
    public InfoPacket read(byte[] dataBytes) throws PacketReadingException {
        this.dataBytes = dataBytes;
        dataBytesReaderIndex = 0;

        return switch (packetType) {
            case VER -> readVerPacket();
            case TINY -> readTinyPacket();
            case SMALL -> readSmallPacket();
            case STA -> readStaPacket();
            case MSO -> readMsoPacket();
            case III -> readIiiPacket();
            case ACR -> readAcrPacket();
            case ISM -> readIsmPacket();
            case VTN -> readVtnPacket();
            case RST -> readRstPacket();
            case NCN -> readNcnPacket();
            case NCI -> readNciPacket();
            default -> throw new PacketReadingException("Unrecognized readable packet type");
        };
    }

    private VerPacket readVerPacket() {
        skipZeroByte();
        var version = readCharArray(8);
        var product = Product.fromString(readCharArray(6));
        var inSimVer = readByte();

        return new VerPacket(packetReqI, version, product, inSimVer);
    }

    private TinyPacket readTinyPacket() {
        var subT = TinySubtype.fromOrdinal(readByte());

        return new TinyPacket(subT, packetReqI);
    }

    private SmallPacket readSmallPacket() {
        var subT = SmallSubtype.fromOrdinal(readByte());
        var uVal = readUnsigned();

        return new SmallPacket(subT, uVal, packetReqI);
    }

    private StaPacket readStaPacket() {
        skipZeroByte();
        var replaySpeed = readFloat();
        var flags = new Flags<>(StaFlag.class, readWord());
        var inGameCam = ViewIdentifier.fromOrdinal(readByte());
        var viewPlid = readByte();
        var numP = readByte();
        var numConns = readByte();
        var numFinished = readByte();
        var raceInProg = RaceProgress.fromOrdinal(readByte());
        var qualMins = readByte();
        var raceLaps = new RaceLaps(readByte());
        skipZeroByte();
        var serverStatus = ServerStatus.fromOrdinal(readByte());
        var track = readCharArray(6);
        var weather = readByte();
        var wind = Wind.fromOrdinal(readByte());

        return new StaPacket(
                packetReqI,
                replaySpeed,
                flags,
                inGameCam,
                viewPlid,
                numP,
                numConns,
                numFinished,
                raceInProg,
                qualMins,
                raceLaps,
                serverStatus,
                track,
                weather,
                wind
        );
    }

    private MsoPacket readMsoPacket() {
        skipZeroByte();
        var ucid = readByte();
        var plid = readByte();
        var userType = MessageType.fromOrdinal(readByte());
        var textStart = readByte();
        var msg = readCharArray(packetSize - 8);

        return new MsoPacket(ucid, plid, userType, textStart, msg);
    }

    private IiiPacket readIiiPacket() {
        skipZeroByte();
        var ucid = readByte();
        var plid = readByte();
        skipZeroBytes(2);
        var msg = readCharArray(packetSize - 8);

        return new IiiPacket(ucid, plid, msg);
    }

    private AcrPacket readAcrPacket() {
        skipZeroByte();
        var ucid = readByte();
        var isAdmin = readByte() != 0;
        var result = AcrResult.fromOrdinal(readByte());
        skipZeroByte();
        var text = readCharArray(packetSize - 8);

        return new AcrPacket(ucid, isAdmin, result, text);
    }

    private IsmPacket readIsmPacket() {
        skipZeroByte();
        var isHost = readByte() != 0;
        skipZeroBytes(3);
        var hName = readCharArray(32);

        return new IsmPacket(packetReqI, isHost, hName);
    }

    private VtnPacket readVtnPacket() {
        skipZeroByte();
        var ucid = readByte();
        var action = VoteAction.fromOrdinal(readByte());
        skipZeroBytes(2);

        return new VtnPacket(ucid, action);
    }

    private RstPacket readRstPacket() {
        skipZeroByte();
        var raceLaps = new RaceLaps(readByte());
        var qualMins = readByte();
        var numP = readByte();
        var timing = new LapTiming(readByte());
        var track = readCharArray(6);
        var weather = readByte();
        var wind = Wind.fromOrdinal(readByte());
        var flags = new Flags<>(RaceFlag.class, readWord());
        var numNodes = readWord();
        var finish = readWord();
        var split1 = readWord();
        var split2 = readWord();
        var split3 = readWord();

        return new RstPacket(
                packetReqI,
                raceLaps,
                qualMins,
                numP,
                timing,
                track,
                weather,
                wind,
                flags,
                numNodes,
                finish,
                split1,
                split2,
                split3
        );
    }

    private NcnPacket readNcnPacket() {
        var ucid = readByte();
        var uName = readCharArray(24);
        var pName = readCharArray(24);
        var isAdmin = readByte() == 1;
        var total = readByte();
        var flags = new Flags<>(NcnFlag.class, readByte());
        skipZeroByte();

        return new NcnPacket(packetReqI, ucid, uName, pName, isAdmin, total, flags);
    }

    private NciPacket readNciPacket() {
        var ucid = readByte();
        var language = Language.fromOrdinal(readByte());
        skipZeroBytes(3);
        var userId = readUnsigned();
        var ipAddress = readUnsigned();

        return new NciPacket(packetReqI, ucid, language, userId, ipAddress);
    }

    private short readByte() {
        return convertByte(dataBytes[dataBytesReaderIndex++]);
    }

    private void skipZeroByte() {
        dataBytesReaderIndex++;
    }

    private void skipZeroBytes(int count) {
        dataBytesReaderIndex += count;
    }

    private int readWord() {
        return readByte() + (readByte() << 8);
    }

    private String readCharArray(int length) {
        var stringLength = length;
        for (int i = 0; i < length; i++) {
            if (dataBytes[dataBytesReaderIndex + i] == 0) {
                stringLength = i;
                break;
            }
        }
        byte[] stringBytes = new byte[stringLength];
        System.arraycopy(dataBytes, dataBytesReaderIndex, stringBytes, 0, stringLength);
        dataBytesReaderIndex += length;
        return new String(stringBytes);
    }

    private long readUnsigned() {
        return readByte() + ((long) readByte() << 8) + ((long) readByte() << 16) + ((long) readByte() << 24);
    }

    private float readFloat() {
        var asInt = (dataBytes[dataBytesReaderIndex] & 0xFF) |
                ((dataBytes[dataBytesReaderIndex + 1] & 0xFF) << 8) |
                ((dataBytes[dataBytesReaderIndex + 2] & 0xFF) << 16) |
                ((dataBytes[dataBytesReaderIndex + 3] & 0xFF) << 24);
        dataBytesReaderIndex += 4;
        return Float.intBitsToFloat(asInt);
    }

    private short convertByte(byte value) {
        if (value < 0) {
            return (short) (value + 256);
        } else {
            return value;
        }
    }
}
