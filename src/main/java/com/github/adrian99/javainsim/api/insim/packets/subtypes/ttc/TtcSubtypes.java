/*
 * Copyright (c) 2025, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets.subtypes.ttc;

import com.github.adrian99.javainsim.api.insim.packets.AxmPacket;
import com.github.adrian99.javainsim.api.insim.packets.TtcPacket;

import java.util.ArrayList;
import java.util.List;

/**
 * Enumeration for subtype of {@link TtcPacket}
 */
@SuppressWarnings("unused")
public class TtcSubtypes {
    static final List<TtcSubtype> ALL = new ArrayList<>();

    private TtcSubtypes() {}

    /**
     * 0: not used
     */
    public static final TtcSubtype NONE = new TtcSubtype(0);
    /**
     * 1 - info request: send {@link AxmPacket} for a layout editor selection
     */
    public static final TtcRequestingSubtype<AxmPacket> SEL = new TtcRequestingSubtype<>(1, AxmPacket.class, true);
    /**
     * 2 - info request: send {@link AxmPacket} every time the selection changes
     */
    public static final TtcRequestingSubtype<AxmPacket> SEL_START = new TtcRequestingSubtype<>(2, AxmPacket.class, false);
    /**
     * 3 - instruction: switch off {@link AxmPacket} requested by {@link #SEL_START}
     */
    public static final TtcSubtype SEL_STOP = new TtcSubtype(3);
}
