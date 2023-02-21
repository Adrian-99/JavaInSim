package pl.adrian.internal.packets.util;

import pl.adrian.api.packets.readable.SmallPacket;
import pl.adrian.api.packets.readable.TinyPacket;
import pl.adrian.internal.Constants;
import pl.adrian.api.packets.readable.VerPacket;
import pl.adrian.internal.packets.base.ReadablePacket;
import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.internal.packets.exceptions.PacketReadingException;

public class PacketReader {
    private final ReadablePacket packet;
    private byte[] dataBytes;
    private int dataBytesReaderIndex;

    public PacketReader(byte[] headerBytes) throws PacketReadingException {
        if (headerBytes.length == Constants.PACKET_HEADER_SIZE) {
            var size = convertByte(headerBytes[0]) * 4;
            var type = PacketType.fromOrdinal(convertByte(headerBytes[1]));
            var reqI = convertByte(headerBytes[2]);

            switch (type) {
                case VER -> packet = new VerPacket(reqI);
                case TINY -> packet = new TinyPacket(reqI);
                case SMALL -> packet = new SmallPacket(reqI);
                default -> throw new PacketReadingException("Unrecognized readable packet type");
            }
        } else {
            throw new PacketReadingException(
                    "Received packet header bytes length was not equal to " + Constants.PACKET_HEADER_SIZE
            );
        }
    }

    public PacketType getPacketType() {
        return packet.getType();
    }

    public int getDataBytesCount() {
        return packet.getSize() - Constants.PACKET_HEADER_SIZE;
    }

    public ReadablePacket read(byte[] dataBytes) {
        this.dataBytes = dataBytes;
        dataBytesReaderIndex = 0;
        return packet.readDataBytes(this);
    }

    public int readByte() {
        return convertByte(dataBytes[dataBytesReaderIndex++]);
    }

    public void skipZeroByte() {
        dataBytesReaderIndex++;
    }

    public int readWord() {
        return readByte() + (readByte() << 8);
    }

    public String readCharArray(int length) {
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

    public long readUnsigned() {
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
