/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package pl.adrian.api.insim.packets.flags;

import pl.adrian.api.insim.packets.MciPacket;
import pl.adrian.api.insim.packets.RipPacket;

/**
 * Options used in {@link RipPacket}.
 */
public enum RipOption {
    /**
     * bit 0, value 1: replay will loop if this bit is set
     */
    LOOP,
    /**
     * bit 1, value 2: set this bit to download missing skins
     */
    SKINS,
    /**
     * bit 2, value 4: use full physics when searching an MPR<br>
     * WARNING: Setting this flag makes MPR searching much slower so should not normally be used.
     * This flag was added to allow high accuracy {@link MciPacket} to be output when fast forwarding.
     */
    FULL_PHYS
}
