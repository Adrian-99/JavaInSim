/*
 * Copyright (c) 2025, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets.subtypes.small;

import com.github.adrian99.javainsim.api.insim.packets.AiiPacket;
import com.github.adrian99.javainsim.api.insim.packets.SmallPacket;

import java.util.ArrayList;
import java.util.List;

/**
 * Enumeration for subtype of {@link SmallPacket}
 */
@SuppressWarnings("unused")
public class SmallSubtypes {
    static final List<SmallSubtype> ALL = new ArrayList<>();

    private SmallSubtypes() {}

    /**
     * 0: not used
     */
    public static final SmallSubtype NONE = new SmallSubtype(0);
    /**
     * 1 - instruction: start sending positions
     */
    public static final SmallSubtype SSP = new SmallSubtype(1);
    /**
     * 2 - instruction: start sending gauges
     */
    public static final SmallSubtype SSG = new SmallSubtype(2);
    /**
     * 3 - report: vote action
     */
    public static final SmallSubtype VTA = new SmallSubtype(3);
    /**
     * 4 - instruction: time stop
     */
    public static final SmallSubtype TMS = new SmallSubtype(4);
    /**
     * 5 - instruction: time step
     */
    public static final SmallSubtype STP = new SmallSubtype(5);
    /**
     * 6 - info: race time packet (reply to GTH)
     */
    public static final SmallSubtype RTP = new SmallSubtype(6);
    /**
     * 7 - instruction: set node lap interval
     */
    public static final SmallSubtype NLI = new SmallSubtype(7);
    /**
     * 8 - both ways: set or get allowed cars (TINY_ALC)
     */
    public static final SmallSubtype ALC = new SmallSubtype(8);
    /**
     * 9 - instruction: set local car switches (flash, horn, siren)
     */
    public static final SmallSubtype LCS = new SmallSubtype(9);
    /**
     * 10 - instruction: set local car lights
     */
    public static final SmallSubtype LCL = new SmallSubtype(10);
    /**
     * 11 - info request: get local AI info
     */
    public static final SmallRequestingSubtype<AiiPacket> AII = new SmallRequestingSubtype<>(11, AiiPacket.class, true);
}
