/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package pl.adrian.api.insim.packets.enums;

import pl.adrian.api.insim.packets.AxmPacket;

/**
 * Enumeration for subtype of ttc packet
 */
public enum TtcSubtype {
    /**
     * 0: not used
     */
    NONE,
    /**
     * 1 - info request: send {@link AxmPacket} for a layout editor selection
     */
    SEL,
    /**
     * 2 - info request: send {@link AxmPacket} every time the selection changes
     */
    SEL_START,
    /**
     * 3 - instruction: switch off {@link AxmPacket} requested by {@link #SEL_START}
     */
    SEL_STOP
}
