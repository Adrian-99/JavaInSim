package pl.adrian.packets.util;

import pl.adrian.Constants;
import pl.adrian.packets.IS_VER;
import pl.adrian.packets.base.ReadablePacket;
import pl.adrian.packets.enums.PacketType;
import pl.adrian.packets.exceptions.PacketReadingException;

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
                case ISP_VER -> packet = new IS_VER(reqI);
                default -> throw new PacketReadingException("Unrecognized packet type");
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
        byte[] stringBytes = new byte[length];
        System.arraycopy(dataBytes, dataBytesReaderIndex, stringBytes, 0, length);
        return new String(stringBytes);
    }

    private int convertByte(byte value) {
        if (value < 0) {
            return value + 256;
        } else {
            return value;
        }
    }
}
