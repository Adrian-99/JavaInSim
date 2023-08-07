/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package pl.adrian.api.insim.packets.enums;

/**
 * Enumeration for lap timing types used in {@link pl.adrian.api.insim.packets.structures.LapTiming LapTiming}.
 */
public enum LapTimingType {
    /**
     * standard lap timing
     */
    STANDARD,
    /**
     * custom timing - user checkpoints have been placed
     */
    CUSTOM,
    /**
     * no lap timing - e.g. open config with no user checkpoints
     */
    NONE
}
