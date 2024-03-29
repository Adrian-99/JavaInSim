/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets.flags;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConfirmationFlagTest {
    @Test
    void isPresent() {
        assertTrue(ConfirmationFlag.DISQ.isPresent(10));
        assertFalse(ConfirmationFlag.DISQ.isPresent(33));
        assertFalse(ConfirmationFlag.TIME.isPresent(10));
        assertTrue(ConfirmationFlag.TIME.isPresent(33));
        assertTrue(ConfirmationFlag.CONFIRMED.isPresent(10));
        assertFalse(ConfirmationFlag.CONFIRMED.isPresent(33));
    }

    @Test
    void getValue() {
        assertEquals(0, ConfirmationFlag.DISQ.getValue());
        assertEquals(0, ConfirmationFlag.TIME.getValue());
        assertEquals(4, ConfirmationFlag.PENALTY_DT.getValue());
    }
}
