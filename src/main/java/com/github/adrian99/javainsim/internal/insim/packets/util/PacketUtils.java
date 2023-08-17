/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.internal.insim.packets.util;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains useful methods for dealing with packets.
 */
public class PacketUtils {
    private PacketUtils() {}

    /**
     * Returns required size of packet (in bytes) calculated based on variable size text contained in the packet.
     * The minimum size is 4, the size will always be multiply of 4.
     * @param otherFieldsSize total size of remaining fields of packet
     * @param variableText text to measure
     * @param maxVariableTextSize maximum allowed size of text
     * @return computed number of bytes
     */
    public static int getPacketSize(int otherFieldsSize, String variableText, int maxVariableTextSize) {
        return getPacketSize(
                otherFieldsSize,
                variableText != null ? variableText.length() + 1 : 1,
                1,
                maxVariableTextSize
        );
    }

    /**
     * Returns required size of packet (in bytes) calculated based on variable size structures array contained in the packet.
     * The minimum size is 4, the size will always be multiply of 4.
     * @param otherFieldsSize total size of remaining fields of packet
     * @param variableStructuresLength length of structures array
     * @param singleStructureSize size (in bytes) of single structure in array
     * @param maxVariableStructuresLength maximum allowed length of structures array
     * @return computed number of bytes
     */
    public static int getPacketSize(int otherFieldsSize,
                                    int variableStructuresLength,
                                    int singleStructureSize,
                                    int maxVariableStructuresLength) {
        var calculatedSize = otherFieldsSize + singleStructureSize *
                Math.min(variableStructuresLength, maxVariableStructuresLength);
        if (calculatedSize % 4 != 0) {
            calculatedSize += 4 - (calculatedSize % 4);
        }
        return calculatedSize;
    }

    /**
     * Converts array of short values into list of short values.
     * @param array array of short values
     * @return list of short values
     */
    public static List<Short> toList(short[] array) {
        var result = new ArrayList<Short>();
        for (var element : array) {
            result.add(element);
        }
        return result;
    }
}
