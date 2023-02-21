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

public class PacketReader {
    private final int packetSize;
    private final PacketType packetType;
    private final int packetReqI;
    private byte[] dataBytes;
    private int dataBytesReaderIndex;

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

    public PacketType getPacketType() {
        return packetType;
    }

    public int getDataBytesCount() {
        return packetSize - Constants.PACKET_HEADER_SIZE;
    }

    public ReadablePacket read(byte[] dataBytes) {
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

    private int readByte() {
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

    private int convertByte(byte value) {
        if (value < 0) {
            return value + 256;
        } else {
            return value;
        }
    }
}
