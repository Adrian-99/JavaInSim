/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package pl.adrian.api.insim.packets.flags;

import pl.adrian.api.insim.packets.OcoPacket;
import pl.adrian.api.insim.packets.enums.StartLightsIndex;

/**
 * Enumeration for start lights data flags used in {@link OcoPacket}.
 */
public enum StartLightsDataFlag {
    /**
     * bit 0, value 1: red 1 (for {@link StartLightsIndex#INDEX_MAIN}) or red (for {@link StartLightsIndex#START_LIGHTS})
     */
    RED1,
    /**
     * bit 1, value 2: red 2 (for {@link StartLightsIndex#INDEX_MAIN}) or amber (for {@link StartLightsIndex#START_LIGHTS})
     */
    RED2_AMBER,
    /**
     * bit 2, value 4: red 3 (for {@link StartLightsIndex#INDEX_MAIN}, no meaning for {@link StartLightsIndex#START_LIGHTS})
     */
    RED3,
    /**
     * bit 3, value 8: green (for {@link StartLightsIndex#INDEX_MAIN} and {@link StartLightsIndex#START_LIGHTS})
     */
    GREEN
}
