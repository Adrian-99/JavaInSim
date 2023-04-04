package pl.adrian.internal.packets.util;

import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.internal.packets.structures.base.ComplexInstructionStructure;
import pl.adrian.internal.packets.structures.base.UnsignedInstructionStructure;

import java.util.List;

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
     * Appends to packet bytes byte, that is converted from short value.
     * @param value byte value
     * @return packet builder
     */
    public PacketBuilder writeByte(short value) {
        return writeByte((int) value);
    }

    /**
     * Appends to packet bytes byte, that is converted from int value.
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
     * Appends to packet bytes byte, that is converted from Character value.
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
     * Appends to packet bytes byte, that is converted from boolean value.
     * @param value boolean value
     * @return packet builder
     */
    public PacketBuilder writeByte(boolean value) {
        packetBytes[currentIndex++] = (byte) (value ? 1 : 0);
        return this;
    }

    /**
     * Appends to packet bytes byte array.
     * @param value byte array value
     * @param length desired length of array - if value is shorter trailing zeros will be added
     * @return packet builder
     */
    public PacketBuilder writeByteArray(List<Short> value, int length) {
        for (var i = 0; i < length && i < value.size(); i++) {
            if (value.get(i) != null) {
                writeByte(value.get(i));
            } else {
                writeZeroByte();
            }
        }
        return writeZeroBytes(length - value.size());
    }

    /**
     * Appends to packet bytes byte with value 0.
     * @return packet builder
     */
    public PacketBuilder writeZeroByte() {
        currentIndex++;
        return this;
    }

    /**
     * Appends to packet bytes multiple bytes with value 0.
     * @param count count of zero bytes to be appended
     * @return packet builder
     */
    public PacketBuilder writeZeroBytes(int count) {
        currentIndex += count;
        return this;
    }

    /**
     * Appends to packet bytes word (2 bytes), that is converted from int value.
     * @param value word value
     * @return packet builder
     */
    public PacketBuilder writeWord(int value) {
        return writeByte(value)
                .writeByte(value >> 8);
    }

    /**
     * Appends to packet bytes char array, that is converted from String value.
     * @param value char array value
     * @param length length of the char array that should be appended
     * @param variableLength whether length of char array is variable (the lowest possible multiply of 4)
     * @return packet builder
     */
    public PacketBuilder writeCharArray(String value, int length, boolean variableLength) {
        if (value != null && value.length() > 0) {
            var bytes = value.getBytes();
            for (var i = 0; i < length - 1; i++) {
                if (i < bytes.length) {
                    packetBytes[currentIndex++] = bytes[i];
                } else if (variableLength) {
                    return writeZeroBytes(4 - (bytes.length % 4));
                } else {
                    return writeZeroBytes(length - bytes.length);
                }
            }
            return writeZeroByte();
        } else if (variableLength) {
            return writeZeroBytes(4);
        } else {
            return writeZeroBytes(length);
        }
    }

    /**
     * Appends to packet bytes unsigned (4 bytes), that is converted from long value.
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
     * Appends to packet bytes unsigned array.
     * @param value unsigned array value
     * @return packet builder
     */
    public PacketBuilder writeUnsignedArray(UnsignedInstructionStructure[] value) {
        for (var unsigned : value) {
            if (unsigned != null) {
                writeUnsigned(unsigned.getUnsignedValue());
            } else {
                writeZeroBytes(4);
            }
        }
        return this;
    }

    /**
     * Appends to packet bytes int (4 bytes), that is converted from int value.
     * @param value int value
     * @return packet builder
     */
    public PacketBuilder writeInt(int value) {
        return writeUnsigned(value);
    }

    /**
     * Appends to packet bytes complex structures array.
     * @param value structures array value
     * @param singleStructureSize size (in bytes) of single structure
     * @return packet builder
     */
    public PacketBuilder writeStructureArray(ComplexInstructionStructure[] value, int singleStructureSize) {
        for (var structure : value) {
            if (structure != null) {
                structure.appendBytes(this);
            } else {
                writeZeroBytes(singleStructureSize);
            }
        }
        return this;
    }

    /**
     * Concludes building packet.
     * @return bytes array of built packet
     */
    public byte[] getBytes() {
        return packetBytes;
    }
}
