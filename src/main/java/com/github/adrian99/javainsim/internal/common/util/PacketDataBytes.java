/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.internal.common.util;

import com.github.adrian99.javainsim.api.outsim.OutSimPacket2;
import com.github.adrian99.javainsim.internal.insim.packets.base.InfoPacket;

/**
 * This class is a helper that is used while creating {@link InfoPacket} or {@link OutSimPacket2} out of its byte representation.
 */
public class PacketDataBytes {
    private final byte[] dataBytes;
    private int dataBytesReaderIndex;

    /**
     * Creates packet data bytes. Constructor used only internally.
     * @param packetDataBytes packet bytes array
     */
    public PacketDataBytes(byte[] packetDataBytes) {
        dataBytes = packetDataBytes;
        dataBytesReaderIndex = 0;
    }

    /**
     * Reads next single byte value from packet bytes.
     * @return read byte value
     */
    public short readByte() {
        return convertByte(dataBytes[dataBytesReaderIndex++]);
    }

    /**
     * Reads next byte array value from packet bytes.
     * @param length length of the byte array to read
     * @return read byte array value
     */
    public short[] readByteArray(int length) {
        var result = new short[length];
        for (var i = 0; i < length; i++) {
            result[i] = readByte();
        }
        return result;
    }

    /**
     * Skips next single byte in packet bytes.
     */
    public void skipZeroByte() {
        dataBytesReaderIndex++;
    }

    /**
     * Skips next bytes in packet bytes.
     * @param count count of bytes to skip
     */
    public void skipZeroBytes(int count) {
        dataBytesReaderIndex += count;
    }

    /**
     * Reads next word value (2 bytes) from packet bytes.
     * @return read word value
     */
    public int readWord() {
        return readByte() + (readByte() << 8);
    }

    /**
     * Reads next short value (2 bytes) from packet bytes.
     * @return read short value
     */
    public short readShort() {
        return (short) readWord();
    }

    /**
     * Reads next char value (1 byte) from packet bytes.
     * @return read char value
     */
    public byte readChar() {
        return dataBytes[dataBytesReaderIndex++];
    }

    /**
     * Reads next char array value from packet bytes.
     * @param length length of the char array to read
     * @return read char array value
     */
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

    /**
     * Reads next unsigned value (4 bytes) from packet bytes.
     * @return read unsigned value
     */
    public long readUnsigned() {
        return readByte() + ((long) readByte() << 8) + ((long) readByte() << 16) + ((long) readByte() << 24);
    }

    /**
     * Reads next unsigned array value from packet bytes.
     * @param length length of the unsigned array to read
     * @return read unsigned array value
     */
    public long[] readUnsignedArray(int length) {
        var result = new long[length];
        for (var i = 0; i < length; i++) {
            result[i] = readUnsigned();
        }
        return result;
    }

    /**
     * Reads next int value (4 bytes) from packet bytes.
     * @return read int value
     */
    public int readInt() {
        return (int) readUnsigned();
    }

    /**
     * Reads next float value (4 bytes) from packet bytes.
     * @return read float value
     */
    public float readFloat() {
        var asInt = (dataBytes[dataBytesReaderIndex] & 0xFF) |
                ((dataBytes[dataBytesReaderIndex + 1] & 0xFF) << 8) |
                ((dataBytes[dataBytesReaderIndex + 2] & 0xFF) << 16) |
                ((dataBytes[dataBytesReaderIndex + 3] & 0xFF) << 24);
        dataBytesReaderIndex += 4;
        return Float.intBitsToFloat(asInt);
    }

    /**
     * Converts byte into short, eliminating negative values.
     * @param value byte value
     * @return non-negative short value
     */
    public static short convertByte(byte value) {
        if (value < 0) {
            return (short) (value + 256);
        } else {
            return value;
        }
    }
}
