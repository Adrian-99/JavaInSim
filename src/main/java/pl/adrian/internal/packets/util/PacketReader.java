package pl.adrian.internal.packets.util;

import pl.adrian.api.packets.enums.Product;
import pl.adrian.api.packets.SmallPacket;
import pl.adrian.api.packets.TinyPacket;
import pl.adrian.api.packets.enums.SmallSubtype;
import pl.adrian.api.packets.enums.TinySubtype;
import pl.adrian.internal.Constants;
import pl.adrian.api.packets.VerPacket;
import pl.adrian.internal.packets.base.ReadablePacket;
import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.internal.packets.exceptions.PacketReadingException;

/**
 * This class is a helper that is used while converting byte array to appropriate {@link ReadablePacket}.
 */
public class PacketReader {
    private final int packetSize;
    private final PacketType packetType;
    private final short packetReqI;
    private byte[] dataBytes;
    private int dataBytesReaderIndex;

    /**
     * Creates packet reader, which helps to convert byte array to appropriate {@link ReadablePacket}.
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
     * Converts data bytes and header bytes (passed in constructor) into appropriate {@link ReadablePacket}.
     * @param dataBytes data bytes of packet
     * @return packet class instance
     * @throws PacketReadingException if reading given packet type (type is extracted from header bytes)
     * is not supported
     */
    public ReadablePacket read(byte[] dataBytes) throws PacketReadingException {
        this.dataBytes = dataBytes;
        dataBytesReaderIndex = 0;

        return switch (packetType) {
            case VER -> readVerPacket();
            case TINY -> readTinyPacket();
            case SMALL -> readSmallPacket();
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

        return new TinyPacket(packetReqI, subT);
    }

    private SmallPacket readSmallPacket() {
        var subT = SmallSubtype.fromOrdinal(readByte());
        var uVal = readUnsigned();

        return new SmallPacket(packetReqI, subT, uVal);
    }

    private short readByte() {
        return convertByte(dataBytes[dataBytesReaderIndex++]);
    }

    private void skipZeroByte() {
        dataBytesReaderIndex++;
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

    private short convertByte(byte value) {
        if (value < 0) {
            return (short) (value + 256);
        } else {
            return value;
        }
    }
}
