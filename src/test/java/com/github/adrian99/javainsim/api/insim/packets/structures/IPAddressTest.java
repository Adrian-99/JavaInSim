/*
 * Copyright (c) 2024, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets.structures;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IPAddressTest {
    @Test
    void createIPAddress_fromUnsignedValue() {
        var ipAddress = new IPAddress(3389200562L);

        assertEquals(3389200562L, ipAddress.getUnsignedValue());
        assertEquals("202.3.24.178", ipAddress.getStringValue());
    }

    @Test
    void createIPAddress_fromStringValue() {
        var ipAddress = new IPAddress("202.3.24.178");

        assertEquals(3389200562L, ipAddress.getUnsignedValue());
        assertEquals("202.3.24.178", ipAddress.getStringValue());
    }

    @Test
    void createIPAddress_withInvalidStringValue() {
        assertThrows(IllegalArgumentException.class, () -> new IPAddress("202.3.24.1178"));
        assertThrows(IllegalArgumentException.class, () -> new IPAddress("262.3.24.178"));
    }
}
