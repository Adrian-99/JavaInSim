package pl.adrian.internal.packets.util;

import pl.adrian.api.packets.enums.PacketType;

/**
 * This class is a helper that is used while converting packets to their byte array representation.
 */
public class PacketBuilder {
    private final byte[] packetBytes;
    private int currentIndex;

    /**
     * Creates packet builder, which helps to convert packets to their byte array representation.
     * @param size total packet size - a multiple of 4
     * @param type packet identifier
     * @param reqI non-zero if the packet is a packet request
     */
    public PacketBuilder(short size, PacketType type, short reqI) {
        packetBytes = new byte[size];
        currentIndex = 0;
        writeByte(size / 4);
        writeByte(type.ordinal());
        writeByte(reqI);
    }

    /**
     * Appends to byte array byte, that is converted from short value.
     * @param value byte value
     * @return packet builder
     */
    public PacketBuilder writeByte(short value) {
        return writeByte((int) value);
    }

    /**
     * Appends to byte array byte, that is converted from int value.
     * @param value byte value
     * @return packet builder
     */
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

    /**
     * Appends to byte array byte, that is converted from Character value.
     * @param value byte value
     * @return packet builder
     */
    public PacketBuilder writeByte(Character value) {
        if (value != null) {
            return writeByte(value.charValue());
        } else {
            return writeZeroByte();
        }
    }

    /**
     * Appends to byte array byte, that is converted from boolean value.
     * @param value boolean value
     * @return packet builder
     */
    public PacketBuilder writeByte(boolean value) {
        packetBytes[currentIndex++] = (byte) (value ? 1 : 0);
        return this;
    }

    /**
     * Appends to byte array byte with value 0.
     * @return packet builder
     */
    public PacketBuilder writeZeroByte() {
        currentIndex++;
        return this;
    }

    /**
     * Appends to byte array multiple bytes with value 0.
     * @param count count of zero bytes to be appended
     * @return packet builder
     */
    public PacketBuilder writeZeroBytes(int count) {
        currentIndex += count;
        return this;
    }

    /**
     * Appends to byte array word (2 bytes), that is converted from int value.
     * @param value word value
     * @return packet builder
     */
    public PacketBuilder writeWord(int value) {
        return writeByte(value)
                .writeByte(value >> 8);
    }

    /**
     * Appends to byte array char array, that is converted from String value.
     * @param value char array value
     * @param length length of the char array that should be appended
     * @return packet builder
     */
    public PacketBuilder writeCharArray(String value, int length) {
        if (value != null) {
            var bytes = value.getBytes();
            for (var i = 0; i < length - 1; i++) {
                if (i < bytes.length) {
                    packetBytes[currentIndex++] = bytes[i];
                } else {
                    return writeZeroBytes(length - bytes.length);
                }
            }
            return writeZeroByte();
        } else {
            return writeZeroBytes(length);
        }
    }

    /**
     * Appends to byte array unsigned (4 bytes), that is converted from long value.
     * @param value unsigned value
     * @return packet builder
     */
    public PacketBuilder writeUnsigned(long value) {
        return writeByte((int) value)
                .writeByte((int) (value >> 8))
                .writeByte((int) (value >> 16))
                .writeByte((int) (value >> 24));
    }

    /**
     * Concludes building packet.
     * @return bytes array of built packet
     */
    public byte[] getBytes() {
        return packetBytes;
    }
}
