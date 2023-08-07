/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package pl.adrian.api.insim.packets.flags;

/**
 * Enumeration for player type flags.
 */
public enum PlayerTypeFlag {
    /**
     * bit 0, value 1: female
     */
    FEMALE,
    /**
     * bit 1, value 2: AI
     */
    AI,
    /**
     * bit 2, value 4: remote
     */
    REMOTE
}
