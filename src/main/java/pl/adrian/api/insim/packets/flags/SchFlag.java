/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package pl.adrian.api.insim.packets.flags;

/**
 * Flags used in {@link pl.adrian.api.insim.packets.SchPacket SchPacket}.
 */
public enum SchFlag {
    /**
     * bit 0, value 1: shift key
     */
    SHIFT,
    /**
     * bit 1, value 2: control key
     */
    CTRL
}
