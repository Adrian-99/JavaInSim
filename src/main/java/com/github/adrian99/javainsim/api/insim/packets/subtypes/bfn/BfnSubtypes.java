/*
 * Copyright (c) 2025, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets.subtypes.bfn;

import com.github.adrian99.javainsim.api.insim.packets.BfnPacket;

import java.util.ArrayList;
import java.util.List;

/**
 * Enumeration for subtype of {@link BfnPacket}.
 */
@SuppressWarnings("unused")
public class BfnSubtypes {
    static final List<BfnSubtype> ALL = new ArrayList<>();

    private BfnSubtypes() {}

    /**
     * value 0: instruction: delete one button or range of buttons (must set ClickID)
     */
    public static final BfnSubtype DEL_BTN = new BfnSubtype(0);
    /**
     * value 1: instruction: clear all buttons made by this InSim instance
     */
    public static final BfnSubtype CLEAR = new BfnSubtype(1);
    /**
     * value 2: info: user cleared this InSim instance's buttons
     */
    public static final BfnSubtype USER_CLEAR = new BfnSubtype(2);
    /**
     * value 3: user request: SHIFT+B or SHIFT+I - request for buttons
     */
    public static final BfnSubtype REQUEST = new BfnSubtype(3);
}
