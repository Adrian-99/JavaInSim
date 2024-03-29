/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.common.enums;

import com.github.adrian99.javainsim.internal.common.enums.EnumHelpers;

import java.util.Optional;

/**
 * Enumeration for default cars.
 */
public enum DefaultCar {
    /**
     * bit 0, value 1: XF GTI
     */
    XFG,
    /**
     * bit 1, value 2: XR GT
     */
    XRG,
    /**
     * bit 2, value 4: XR GT TURBO
     */
    XRT,
    /**
     * bit 3, value 8:  RB4 GT
     */
    RB4,
    /**
     * bit 4, value 16: FXO TURBO
     */
    FXO,
    /**
     * bit 5, value 32: LX4
     */
    LX4,
    /**
     * bit 6, value 64: LX6
     */
    LX6,
    /**
     * bit 7, value 128: MRT5
     */
    MRT,
    /**
     * bit 8, value 256: UF 1000
     */
    UF1,
    /**
     * bit 9, value 512, RACEABOUT
     */
    RAC,
    /**
     * bit 10, value 1024: FZ50
     */
    FZ5,
    /**
     * bit 11, value 2048: FORMULA XR
     */
    FOX,
    /**
     * bit 12, value 4096: XF GTR
     */
    XFR,
    /**
     * bit 13, value 8192: UF GTR
     */
    UFR,
    /**
     * bit 14, value 16384: FORMULA V8
     */
    FO8,
    /**
     * bit 15, value 32768: FXO GTR
     */
    FXR,
    /**
     * bit 16, value 65536: XR GTR
     */
    XRR,
    /**
     * bit 17, value 131072: FZ50 GTR
     */
    FZR,
    /**
     * bit 18, value 262144: BMW SAUBER F1.06
     */
    BF1,
    /**
     * bit 19, value 524288: FORMULA BMW FB02
     */
    FBM;

    /**
     * Converts string value to enum value.
     * @param carName string value
     * @return enum value
     */
    public static Optional<DefaultCar> fromString(String carName) {
        for (var enumValue : EnumHelpers.get(DefaultCar.class).getAllValuesCached()) {
            if (enumValue.name().equalsIgnoreCase(carName)) {
                return Optional.of(enumValue);
            }
        }
        return Optional.empty();
    }
}
