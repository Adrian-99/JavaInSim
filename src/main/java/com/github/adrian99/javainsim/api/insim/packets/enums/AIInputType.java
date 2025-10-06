/*
 * Copyright (c) 2025, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets.enums;

import com.github.adrian99.javainsim.api.insim.packets.AiiPacket;
import com.github.adrian99.javainsim.api.insim.packets.flags.PlayerFlag;
import com.github.adrian99.javainsim.internal.common.enums.EnumHelpers;
import com.github.adrian99.javainsim.internal.common.enums.EnumWithCustomValue;

/**
 * Enumeration for AI input type.<hr>
 * Inputs marked 'hold' must be set back to zero after some time.<br>
 * This can be done either by use of the Time field or by sending a later packet with Value = 0.<br>
 * E.g. Set Time to 10 when issuing a CS_CHUP - hold shift up lever for 0.1 sec.<br>
 * E.g. Set Time to 50 when issuing a CS_HORN - sound horn for 0.5 sec.<hr>
 * Inputs marked 'toggle' accept the following Values:
 * <ul>
 *     <li>1 toggle</li>
 *     <li>2 switch off</li>
 *     <li>3 switch on</li>
 * </ul>
 */
public enum AIInputType implements EnumWithCustomValue {
    /**
     * 0 - steer: 1 hard left / 32768 centre / 65535 hard right
     */
    MSX,
    /**
     * 1 - throttle: 0 to 65535
     */
    THROTTLE,
    /**
     * 2 - brake: 0 to 65535
     */
    BRAKE,
    /**
     * 3 - hold shift up lever
     */
    CHUP,
    /**
     * 4 - hold shift down lever
     */
    CHDN,
    /**
     * 5 - ignition: toggle
     */
    IGNITION,
    /**
     * 6 - extra light: toggle
     */
    EXTRALIGHT,
    /**
     * 7 - headlights: 1:off / 2:side / 3:low / 4:high
     */
    HEADLIGHTS,
    /**
     * 8 - hold siren: 1:fast / 2:slow
     */
    SIREN,
    /**
     * 9 - hold horn: 1 to 5
     */
    HORN,
    /**
     * 10 - hold flash - 1:on
     */
    FLASH,
    /**
     * 11 - clutch: 0 to 65535
     */
    CLUTCH,
    /**
     * 12 - handbrake: 0 to 65535
     */
    HANDBRAKE,
    /**
     * 13 - indicators: 1: cancel / 2: left / 3: right / 4: hazard
     */
    INDICATORS,
    /**
     * 14 - gear for shifter (leave at 255 for sequential control)
     */
    GEAR,
    /**
     * 15 - look: 0: none / 4: left / 5: left+ / 6: right / 7: right+
     */
    LOOK,
    /**
     * 16 - pit speed: toggle
     */
    PITSPEED,
    /**
     * 17 - traction control: toggle
     */
    TCDISABLE,
    /**
     * 18 - rear fog lights: toggle
     */
    FOGREAR,
    /**
     * 19 - front fog lights: toggle
     */
    FOGFRONT,
    /**
     * 240 - send an {@link AiiPacket} (AI Info) packet
     */
    SEND_AI_INFO(240),
    /**
     * 241 - start or stop sending regular {@link AiiPacket} packets<br>
     * Time = time interval in hundredths of a second (0 : stop)
     */
    REPEAT_AI_INFO(241),
    /**
     * 253 - set help flags: value can be any combination of
     * <ul>
     *     <li>{@link PlayerFlag#AUTOGEARS}    - 8 - auto shift</li>
     *     <li>{@link PlayerFlag#HELP_B}       - 64 - brake help</li>
     *     <li>{@link PlayerFlag#AUTOCLUTCH}   - 512 - auto clutch</li>
     * </ul>
     * Default value for an AI driver is {@link PlayerFlag#AUTOCLUTCH} only.<br>
     * If {@link PlayerFlag#AUTOGEARS} is set, there is no need to set {@link PlayerFlag#AUTOCLUTCH}.
     */
    SET_HELP_FLAGS(253),
    /**
     * 254 - reset all inputs<br>
     * Most inputs are zero / {@link AIInputType#MSX} is 32768 / {@link AIInputType#GEAR} is 255
     */
    RESET_INPUTS(254),
    /**
     * 255 - the AI driver will stop the car
     */
    STOP_CONTROL(255);

    private final short value;

    AIInputType() {
        value = (short) ordinal();
    }

    AIInputType(int customValue) {
        value = (short) customValue;
    }

    @Override
    public int getValue() {
        return value;
    }

    /**
     * Converts ordinal number to enum value.
     * @param ordinal ordinal number
     * @return enum value
     */
    public static AIInputType fromOrdinal(int ordinal) {
        return EnumHelpers.get(AIInputType.class).fromOrdinal(ordinal);
    }
}
