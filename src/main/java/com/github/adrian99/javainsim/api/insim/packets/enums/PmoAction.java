/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets.enums;

import com.github.adrian99.javainsim.api.insim.packets.AxmPacket;
import com.github.adrian99.javainsim.api.insim.packets.subtypes.tiny.TinySubtypes;
import com.github.adrian99.javainsim.api.insim.packets.subtypes.ttc.TtcSubtypes;
import com.github.adrian99.javainsim.internal.common.enums.EnumHelpers;

/**
 * Enumeration for actions used in {@link AxmPacket}.
 */
public enum PmoAction {
    /**
     * value 0: sent by the layout loading system only
     */
    LOADING_FILE,
    /**
     * value 1: adding objects (from InSim or editor)
     */
    ADD_OBJECTS,
    /**
     * value 2: delete objects (from InSim or editor)
     */
    DEL_OBJECTS,
    /**
     * value 3: clear all objects (NumO must be zero)
     */
    CLEAR_ALL,
    /**
     * value 4: a reply to a {@link TinySubtypes#AXM Tiny AXM} request
     */
    TINY_AXM,
    /**
     * value 5: a reply to a {@link TtcSubtypes#SEL Ttc SEL} request
     */
    TTC_SEL,
    /**
     * value 6: set a connection's layout editor selection
     */
    SELECTION,
    /**
     * value 7: user pressed O without anything selected
     */
    POSITION,
    /**
     * value 8: request Z values / reply with Z values
     */
    GET_Z;

    /**
     * Converts ordinal number to enum value.
     * @param ordinal ordinal number
     * @return enum value
     */
    public static PmoAction fromOrdinal(int ordinal) {
        return EnumHelpers.get(PmoAction.class).fromOrdinal(ordinal);
    }
}
