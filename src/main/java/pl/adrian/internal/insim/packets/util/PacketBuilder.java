/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package pl.adrian.internal.insim.packets.util;

import pl.adrian.api.insim.packets.enums.PacketType;
import pl.adrian.internal.insim.packets.structures.base.ComplexInstructionStructure;
import pl.adrian.internal.insim.packets.structures.base.UnsignedInstructionStructure;

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
     * Appends to packet bytes short (2 bytes), that is converted from short value.
     * @param value short value
     * @return packet builder
     */
    public PacketBuilder writeShort(short value) {
        return writeWord(value);
    }

    /**
     * Appends to packet bytes char array, that is converted from String value.
     * @param value char array value
     * @param length length of the char array that should be appended
     * @return packet builder
     */
    public PacketBuilder writeCharArray(String value, int length) {
        return writeCharArray(value, length, length);
    }

    /**
     * Appends to packet bytes char array, that is converted from String value.
     * @param value char array value
     * @param maxLength maximum length of the char array that should be appended
     * @param minLength minimum length of the char array that should be appended
     * @return packet builder
     */
    public PacketBuilder writeCharArray(String value, int maxLength, int minLength) {
        if (maxLength < minLength) {
            throw new IllegalArgumentException("maxLength must not be lower than minLength");
        }
        if (value != null && !value.isEmpty()) {
            var bytes = value.getBytes();
            for (var i = 0; i < maxLength - 1; i++) {
                if (i < bytes.length) {
                    packetBytes[currentIndex++] = bytes[i];
                } else {
                    var bytesWrote = bytes.length;
                    if (bytes.length < minLength - 1) {
                        var missingBytesToMinLength = minLength - bytes.length - 1;
                        writeZeroBytes(missingBytesToMinLength);
                        bytesWrote += missingBytesToMinLength;
                    }
                    return writeZeroBytes(Math.min(4 - (bytesWrote % 4), maxLength - bytesWrote));
                }
            }
            return writeZeroByte();
        } else {
            return writeZeroBytes(minLength);
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
    public PacketBuilder writeUnsignedArray(List<? extends UnsignedInstructionStructure> value) {
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
     * Appends to packet bytes float (4 bytes), that is converted from float value.
     * @param value float value
     * @return packet builder
     */
    public PacketBuilder writeFloat(float value) {
        var intValue = Float.floatToRawIntBits(value);
        return writeUnsigned(intValue);
    }

    /**
     * Appends to packet bytes complex structure.
     * @param value structure value
     * @param structureSize size (in bytes) of structure
     * @return packet builder
     */
    public PacketBuilder writeStructure(ComplexInstructionStructure value, int structureSize) {
        if (value != null) {
            value.appendBytes(this);
        } else {
            writeZeroBytes(structureSize);
        }
        return this;
    }

    /**
     * Appends to packet bytes complex structures array.
     * @param value structures array value
     * @param singleStructureSize size (in bytes) of single structure
     * @return packet builder
     */
    public PacketBuilder writeStructureArray(ComplexInstructionStructure[] value, int singleStructureSize) {
        for (var structure : value) {
            writeStructure(structure, singleStructureSize);
        }
        return this;
    }

    /**
     * Appends to packet bytes complex structures list.
     * @param value structures list value
     * @param singleStructureSize size (in bytes) of single structure
     * @return packet builder
     */
    public PacketBuilder writeStructureArray(List<? extends ComplexInstructionStructure> value, int singleStructureSize) {
        for (var structure : value) {
            writeStructure(structure, singleStructureSize);
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
