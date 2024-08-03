/*
 * Copyright (c) 2024, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.internal.insim.packets.util;

import com.github.adrian99.javainsim.api.insim.packets.AxmPacket;
import com.github.adrian99.javainsim.api.insim.packets.MciPacket;
import com.github.adrian99.javainsim.api.insim.packets.NlpPacket;
import com.github.adrian99.javainsim.api.insim.packets.PlhPacket;

/**
 * This class contains constant values used in library
 */
public class Constants {
    /**
     * InSim version supported by library
     */
    public static final short INSIM_VERSION = 9;
    /**
     * Size (in bytes) of header of each packet
     */
    public static final short PACKET_HEADER_SIZE = 3;
    /**
     * Maximum number of player handicaps that can be sent in single {@link PlhPacket}.
     */
    public static final short PLH_MAX_PLAYERS = 40;
    /**
     * Maximum number of mods that can be allowed to be used on host
     */
    public static final short MAL_MAX_MODS = 120;
    /**
     * Maximum number of IP addresses that are not allowed to join a host
     */
    public static final short IPB_MAX_BANS = 120;
    /**
     * Maximum number of cars that can be sent in single {@link NlpPacket}
     */
    public static final short NLP_MAX_CARS = 40;
    /**
     * Maximum number of cars that can be sent in single {@link MciPacket}
     */
    public static final short MCI_MAX_CARS = 16;
    /**
     * Maximum number of objects that can be sent in single {@link AxmPacket}.
     */
    public static final short AXM_MAX_OBJECTS = 60;
    /**
     * Maximum value of clickID of the button.
     */
    public static final short BUTTON_MAX_CLICK_ID = 239;

    private Constants() {}
}
