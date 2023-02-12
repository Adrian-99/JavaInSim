package pl.adrian.packets.util;

import pl.adrian.packets.enums.PacketType;

public class PacketBuilder {
    private final byte[] packetBytes;
    private int currentIndex;

    public PacketBuilder(short size, PacketType type, short reqI) {
        packetBytes = new byte[size];
        currentIndex = 0;
        writeByte(size / 4);
        writeByte(type.ordinal());
        writeByte(reqI);
    }

    public PacketBuilder writeByte(short value) {
        return writeByte((int) value);
    }

    public PacketBuilder writeByte(int value) {
        var cut = value & 0xff;
        byte byteValue;
        if (cut > 127) {
            byteValue = (byte) (cut - 256);
        } else {
            byteValue = (byte) cut;
        }
        packetBytes[currentIndex++] = byteValue;
        return this;
    }

    public PacketBuilder writeByte(Character value) {
        if (value != null) {
            return writeByte(value.charValue());
        } else {
            return writeZeroByte();
        }
    }

    public PacketBuilder writeZeroByte() {
        currentIndex++;
        return this;
    }

    public PacketBuilder writeZeroBytes(int count) {
        currentIndex += count;
        return this;
    }

    public PacketBuilder writeWord(int value) {
        return writeByte(value)
                .writeByte(value >> 2);
    }

    public PacketBuilder writeCharArray(String value, int length) {
        if (value != null) {
            var bytes = value.getBytes();
            for (var i = 0; i < length; i++) {
                if (i < bytes.length) {
                    packetBytes[currentIndex++] = bytes[i];
                } else {
                    return writeZeroBytes(length - bytes.length);
                }
            }
            return this;
        } else {
            return writeZeroBytes(length);
        }
    }

    public byte[] getBytes() {
        return packetBytes;
    }
}
