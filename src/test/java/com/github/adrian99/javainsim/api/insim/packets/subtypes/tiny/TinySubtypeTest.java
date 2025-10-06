/*
 * Copyright (c) 2025, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets.subtypes.tiny;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TinySubtypeTest {
    @Test
    void shouldReturnFoundSubtype() {
        assertEquals(
                TinySubtypes.GTH,
                TinySubtype.fromOrdinal(8)
        );
    }

    @Test
    void shouldReturnDefaultSubtypeWhenNotFound() {
        assertEquals(
                TinySubtypes.NONE,
                TinySubtype.fromOrdinal(100)
        );
    }
}
