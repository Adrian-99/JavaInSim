/*
 * Copyright (c) 2024, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets.structures;

import com.github.adrian99.javainsim.internal.insim.packets.structures.base.UnsignedInstructionStructure;

import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * This class helps to convert IP address value to and from 4-byte number.
 */
public class IPAddress implements UnsignedInstructionStructure {
    private static final Pattern IP_ADDRESS_REGEX = Pattern.compile("^\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}$");

    private final long unsignedValue;

    /**
     * Creates IP address object. Constructor used only internally.
     * @param unsignedValue 4-byte value of IP address
     */
    public IPAddress(long unsignedValue) {
        this.unsignedValue = unsignedValue;
    }

    /**
     * Creates IP address object.
     * @param ipAddress string value of IP address
     * @throws IllegalArgumentException if provided IP address is invalid
     */
    public IPAddress(String ipAddress) throws IllegalArgumentException {
        if (IP_ADDRESS_REGEX.matcher(ipAddress).matches()) {
            int[] addressParts = Arrays.stream(ipAddress.split("\\."))
                    .mapToInt(Integer::parseUnsignedInt)
                    .toArray();
            for (var addressPart : addressParts) {
                if (addressPart < 0 || addressPart > 255) {
                    throw new IllegalArgumentException("Invalid IP address: " + ipAddress);
                }
            }
            unsignedValue = (long) addressParts[0] << 24 |
                    (long) addressParts[1] << 16 |
                    (long) addressParts[2] << 8 |
                    addressParts[3];
        } else {
            throw new IllegalArgumentException("Invalid IP address: " + ipAddress);
        }
    }

    @Override
    public long getUnsignedValue() {
        return unsignedValue;
    }

    /**
     * @return string value of IP address
     */
    public String getStringValue() {
        return ((unsignedValue >> 24) & 255) + "." +
                ((unsignedValue >> 16) & 255) + "." +
                ((unsignedValue >> 8) & 255) + "." +
                (unsignedValue & 255);
    }
}
