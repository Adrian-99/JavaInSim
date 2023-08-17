/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets.flags;

import com.github.adrian99.javainsim.api.insim.packets.BtnPacket;
import com.github.adrian99.javainsim.internal.common.flags.FlagWithCustomValue;

/**
 * Enumeration for style flags for the button used in {@link BtnPacket}.
 */
public enum ButtonStyle implements FlagWithCustomValue {
    /**
     * bits 0-2, value 0: not user editable
     */
    COLOUR_LIGHT_GRAY(0, 7),
    /**
     * bits 0-2, value 1: default:yellow
     */
    COLOUR_TITLE(1, 7),
    /**
     * bits 0-2, value 2: default:black
     */
    COLOUR_UNSELECTED_TEXT(2, 7),
    /**
     * bits 0-2, value 3: default:white
     */
    COLOUR_SELECTED_TEXT(3, 7),
    /**
     * bits 0-2, value 4: default:green
     */
    COLOUR_OK(4, 7),
    /**
     * bits 0-2, value 5: default:red
     */
    COLOUR_CANCEL(5, 7),
    /**
     * bits 0-2, value 6: default:pale blue
     */
    COLOUR_TEXT_STRING(6, 7),
    /**
     * bits 0-2, value 7: default:grey
     */
    COLOUR_UNAVAILABLE(7, 7),
    /**
     * bit 3, value 8: click this button to send IS_BTC
     */
    CLICK(8, 8),
    /**
     * bit 4, value 16: light button
     */
    LIGHT(16, 16),
    /**
     * bit 5, value 32: dark button
     */
    DARK(32, 32),
    /**
     * bit 6, value 64: align text to left
     */
    LEFT(64, 64),
    /**
     * bit 7, value 128: align text to right
     */
    RIGHT(128, 128);

    private final int value;
    private final int mask;

    ButtonStyle(int value, int mask) {
        this.value = value;
        this.mask = mask;
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public int getValueMask() {
        return mask;
    }
}
