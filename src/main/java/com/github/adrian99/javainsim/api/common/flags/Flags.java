/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.common.flags;

import com.github.adrian99.javainsim.internal.common.enums.EnumWithCustomValue;
import com.github.adrian99.javainsim.internal.common.flags.FlagWithCustomBehavior;
import com.github.adrian99.javainsim.internal.common.flags.FlagWithCustomValue;
import com.github.adrian99.javainsim.internal.insim.packets.structures.base.ByteInstructionStructure;
import com.github.adrian99.javainsim.internal.insim.packets.structures.base.UnsignedInstructionStructure;
import com.github.adrian99.javainsim.internal.insim.packets.structures.base.WordInstructionStructure;
import com.github.adrian99.javainsim.internal.common.enums.EnumHelpers;

import java.util.HashSet;
import java.util.Set;

/**
 * This class implements flags type used in communication with LFS.
 * @param <T> Enum that represents bits of flags field
 */
public class Flags<T extends Enum<?>> implements ByteInstructionStructure,
        WordInstructionStructure, UnsignedInstructionStructure {
    private final Set<T> flagsSet;

    /**
     * Creates flags out of enum values array.
     * @param flags enum values array
     */
    @SafeVarargs
    public Flags(T... flags) {
        this.flagsSet = new HashSet<>();
        var usedMask = 0;
        for (var flag : flags) {
            int flagMask;
            if (flag instanceof FlagWithCustomValue flagWithCustomValue) {
                flagMask = flagWithCustomValue.getValueMask();
            } else if (flag instanceof FlagWithCustomBehavior enumWithCustomValue) {
                flagMask = enumWithCustomValue.getValue();
            } else {
                flagMask = 1 << flag.ordinal();
            }
            if ((usedMask & flagMask) == 0) {
                usedMask |= flagMask;
                flagsSet.add(flag);
            }
        }
    }

    /**
     * Creates flags out of their binary representation.
     * @param enumClass class of the enum that holds possible values for these flags
     * @param value binary representation of flags
     */
    public Flags(Class<T> enumClass, long value) {
        flagsSet = new HashSet<>();
        for (var enumValue : EnumHelpers.get(enumClass).getAllValuesCached()) {
            if (enumValue instanceof FlagWithCustomValue enumCustomValue) {
                var flagValue = enumCustomValue.getValue();
                var requiredZeros = enumCustomValue.getValueMask() - flagValue;
                if ((value & flagValue) == flagValue && (value & requiredZeros) == 0) {
                    flagsSet.add(enumValue);
                }
            } else if (enumValue instanceof FlagWithCustomBehavior enumCustomBehavior) {
                if (enumCustomBehavior.isPresent(value)) {
                    flagsSet.add(enumValue);
                }
            } else {
                var flagValue = 1 << enumValue.ordinal();
                if ((value & flagValue) == flagValue) {
                    flagsSet.add(enumValue);
                }
            }
        }
    }

    @Override
    public short getByteValue() {
        return (short) getUnsignedValue();
    }

    @Override
    public int getWordValue() {
        return (int) getUnsignedValue();
    }

    @Override
    public long getUnsignedValue() {
        return flagsSet.stream().mapToLong(enumValue -> {
            if (enumValue instanceof EnumWithCustomValue enumCustomValue) {
                return enumCustomValue.getValue();
            } else {
                return (long) 1 << enumValue.ordinal();
            }
        }).reduce(0, (all, curr) -> all | curr);
    }

    /**
     * Checks if specified value is present in flags.
     * @param flag value to check
     * @return whether value is present in flags
     */
    public boolean hasFlag(T flag) {
        return flagsSet.contains(flag);
    }

    /**
     * Checks if specified value is not present in flags.
     * @param flag value to check
     * @return whether value is not present in flags
     */
    public boolean hasNoFlag(T flag) {
        return !flagsSet.contains(flag);
    }
}
