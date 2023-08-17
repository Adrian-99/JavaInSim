/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.internal.insim.packets.structures;

import com.github.adrian99.javainsim.api.insim.packets.MalPacket;
import com.github.adrian99.javainsim.internal.insim.packets.structures.base.UnsignedInstructionStructure;

/**
 * Mod skin id in 4 bytes - there is an array of these in the {@link MalPacket MalPacket}.
 */
public class ModSkinId implements UnsignedInstructionStructure {
    private static final byte[] hexLettersBytes = "0123456789ABCDEF".getBytes();

    private final String skinId;

    /**
     * Creates mod skin id structure out of its unsigned representation.
     * @param unsignedValue unsigned value of skin id
     */
    public ModSkinId(long unsignedValue) {
        var convertedBytes = new byte[6];
        for (int i = 0; i < 6; i++) {
            convertedBytes[5 - i] = hexLettersBytes[(int) ((unsignedValue >> (i * 4)) & 15)];
        }
        skinId = new String(convertedBytes);
    }

    /**
     * Creates mod skin id structure out of its string representation.
     * @param skinId string value of skin id
     */
    public ModSkinId(String skinId) {
        var skinIdBytes = new char[6];
        var inputToUpper = skinId.toUpperCase();
        for (int i = 0; i < 6 && i < skinId.length(); i++) {
            var character = inputToUpper.charAt(i);
            if ((character >= '0' && character <= '9') || (character >= 'A' && character <= 'F')) {
                skinIdBytes[i] = character;
            }
        }
        this.skinId = new String(skinIdBytes);
    }

    @Override
    public long getUnsignedValue() {
        var result = 0L;
        for (var character : skinId.toCharArray()) {
            int bitsValue = 0;
            if (character >= '0' && character <= '9') {
                bitsValue = character - '0';
            } else if (character >= 'A' && character <= 'F') {
                bitsValue = character - 'A' + 10;
            }
            result <<= 4;
            result |= bitsValue & 0xF;
        }
        return result;
    }

    /**
     * @return string value of skin id
     */
    public String getStringValue() {
        return skinId;
    }
}
