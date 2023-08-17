/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets.enums;

import com.github.adrian99.javainsim.api.insim.packets.CimPacket;
import com.github.adrian99.javainsim.api.insim.packets.ObhPacket;
import com.github.adrian99.javainsim.api.insim.packets.structures.objectinfo.ObjectInfo;
import com.github.adrian99.javainsim.internal.common.enums.EnumWithCustomValue;
import com.github.adrian99.javainsim.internal.common.enums.EnumHelpers;

/**
 * Enumeration for object types used in {@link ObjectInfo},
 * {@link CimPacket} and {@link ObhPacket}.
 */
public enum ObjectType implements EnumWithCustomValue {
    /**
     * value 0: no object selected or unknown object
     */
    NULL(0),
    /**
     * value 4: chalk line
     */
    CHALK_LINE(4),
    /**
     * value 5: chalk line 2
     */
    CHALK_LINE2(5),
    /**
     * value 6: chalk ahead
     */
    CHALK_AHEAD(6),
    /**
     * value 7: chalk ahead 2
     */
    CHALK_AHEAD2(7),
    /**
     * value 8: chalk left
     */
    CHALK_LEFT(8),
    /**
     * value 9: chalk left 2
     */
    CHALK_LEFT2(9),
    /**
     * value 10: chalk left 3
     */
    CHALK_LEFT3(10),
    /**
     * value 11: chalk right
     */
    CHALK_RIGHT(11),
    /**
     * value 12: chalk right 2
     */
    CHALK_RIGHT2(12),
    /**
     * value 13: chalk right 3
     */
    CHALK_RIGHT3(13),
    /**
     * value 20: cone red
     */
    CONE_RED(20),
    /**
     * value 21: cone red 2
     */
    CONE_RED2(21),
    /**
     * value 22: cone red 3
     */
    CONE_RED3(22),
    /**
     * value 23: cone blue
     */
    CONE_BLUE(23),
    /**
     * value 24: cone blue 2
     */
    CONE_BLUE2(24),
    /**
     * value 25: cone green
     */
    CONE_GREEN(25),
    /**
     * value 26: cone green 2
     */
    CONE_GREEN2(26),
    /**
     * value 27: cone orange
     */
    CONE_ORANGE(27),
    /**
     * value 28: cone white
     */
    CONE_WHITE(28),
    /**
     * value 29: cone yellow
     */
    CONE_YELLOW(29),
    /**
     * value 30: cone yellow 2
     */
    CONE_YELLOW2(30),
    /**
     * value 40: cone red ptr
     */
    CONE_PTR_RED(40),
    /**
     * value 41: cone blue ptr
     */
    CONE_PTR_BLUE(41),
    /**
     * value 42: cone green ptr
     */
    CONE_PTR_GREEN(42),
    /**
     * value 43: cone yellow ptr
     */
    CONE_PTR_YELLOW(43),
    /**
     * value 48: tyre single
     */
    TYRE_SINGLE(48),
    /**
     * value 49: tyre stack 2
     */
    TYRE_STACK2(49),
    /**
     * value 50: tyre stack 3
     */
    TYRE_STACK3(50),
    /**
     * value 51: tyre stack 4
     */
    TYRE_STACK4(51),
    /**
     * value 52: tyre single big
     */
    TYRE_SINGLE_BIG(52),
    /**
     * value 53: tyre stack 2 big
     */
    TYRE_STACK2_BIG(53),
    /**
     * value 54: tyre stack 3 big
     */
    TYRE_STACK3_BIG(54),
    /**
     * value 55: tyre stack 4 big
     */
    TYRE_STACK4_BIG(55),
    /**
     * value 64: marker curve l
     */
    MARKER_CURVE_L(64),
    /**
     * value 65: marker curve r
     */
    MARKER_CURVE_R(65),
    /**
     * value 66: marker l
     */
    MARKER_L(66),
    /**
     * value 67: marker r
     */
    MARKER_R(67),
    /**
     * value 68: marker hard l
     */
    MARKER_HARD_L(68),
    /**
     * value 69: marker hard r
     */
    MARKER_HARD_R(69),
    /**
     * value 70: marker l r
     */
    MARKER_L_R(70),
    /**
     * value 71: marker r l
     */
    MARKER_R_L(71),
    /**
     * value 72: marker s l
     */
    MARKER_S_L(72),
    /**
     * value 73: marker s r
     */
    MARKER_S_R(73),
    /**
     * value 74: marker s2 l
     */
    MARKER_S2_L(74),
    /**
     * value 75: marker s2 r
     */
    MARKER_S2_R(75),
    /**
     * value 76: marker u l
     */
    MARKER_U_L(76),
    /**
     * value 77: marker u r
     */
    MARKER_U_R(77),
    /**
     * value 84: marker 25
     */
    DIST25(84),
    /**
     * value 85: marker 50
     */
    DIST50(85),
    /**
     * value 86: marker 75
     */
    DIST75(86),
    /**
     * value 87: marker 100
     */
    DIST100(87),
    /**
     * value 88: marker 125
     */
    DIST125(88),
    /**
     * value 89: marker 150
     */
    DIST150(89),
    /**
     * value 90: marker 200
     */
    DIST200(90),
    /**
     * value 91: marker 250
     */
    DIST250(91),
    /**
     * value 96: armco 1
     */
    ARMCO1(96),
    /**
     * value 97: armco 3
     */
    ARMCO3(97),
    /**
     * value 98: armco 5
     */
    ARMCO5(98),
    /**
     * value 104: barrier long
     */
    BARRIER_LONG(104),
    /**
     * value 105: barrier red
     */
    BARRIER_RED(105),
    /**
     * value 106: barrier white
     */
    BARRIER_WHITE(106),
    /**
     * value 112: banner 1
     */
    BANNER1(112),
    /**
     * value 113: banner 2
     */
    BANNER2(113),
    /**
     * value 120: ramp 1
     */
    RAMP1(120),
    /**
     * value 121: ramp 2
     */
    RAMP2(121),
    /**
     * value 128: speed hump 10m
     */
    SPEED_HUMP_10M(128),
    /**
     * value 129: speed hump 6m
     */
    SPEED_HUMP_6M(129),
    /**
     * value 136: post green
     */
    POST_GREEN(136),
    /**
     * value 137: post orange
     */
    POST_ORANGE(137),
    /**
     * value 138: post red
     */
    POST_RED(138),
    /**
     * value 139: post white
     */
    POST_WHITE(139),
    /**
     * value 144: bale
     */
    BALE(144),
    /**
     * value 148: railing
     */
    RAILING(148),
    /**
     * value 149: start lights
     */
    START_LIGHTS(149),
    /**
     * value 160: sign keep left
     */
    SIGN_KEEP_LEFT(160),
    /**
     * value 161: sign keep right
     */
    SIGN_KEEP_RIGHT(161),
    /**
     * value 168: sign speed 80
     */
    SIGN_SPEED_80(168),
    /**
     * value 169: sign speed 50
     */
    SIGN_SPEED_50(169),
    /**
     * value 172: concrete slab
     */
    CONCRETE_SLAB(172),
    /**
     * value 173: concrete ramp
     */
    CONCRETE_RAMP(173),
    /**
     * value 174: concrete wall
     */
    CONCRETE_WALL(174),
    /**
     * value 175: concrete pillar
     */
    CONCRETE_PILLAR(175),
    /**
     * value 176: concrete slab wall
     */
    CONCRETE_SLAB_WALL(176),
    /**
     * value 177: concrete ramp wall
     */
    CONCRETE_RAMP_WALL(177),
    /**
     * value 178: concrete short slab wall
     */
    CONCRETE_SHORT_SLAB_WALL(178),
    /**
     * value 179: concrete wedge
     */
    CONCRETE_WEDGE(179),
    /**
     * value 184: start position
     */
    START_POSITION(184),
    /**
     * value 185: pit start point
     */
    PIT_START_POINT(185),
    /**
     * value 186: pit stop box
     */
    PIT_STOP_BOX(186),
    /**
     * value 252: insim checkpoint
     */
    MARSH_IS_CP(252),
    /**
     * value 253: insim circle
     */
    MARSH_IS_AREA(253),
    /**
     * value 254: restricted area
     */
    MARSH_MARSHAL(254),
    /**
     * value 255: route checker
     */
    MARSH_ROUTE(255);

    private final short value;

    ObjectType(int value) {
        this.value = (short) value;
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
    public static ObjectType fromOrdinal(int ordinal) {
        return EnumHelpers.get(ObjectType.class).fromOrdinal(ordinal);
    }
}
