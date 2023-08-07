/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package pl.adrian.internal.common.flags;

import pl.adrian.api.common.flags.Flags;
import pl.adrian.internal.common.enums.EnumWithCustomValue;

/**
 * This interface should be implemented by all enums representing flags,
 * whose presence in {@link Flags Flags} object
 * should not be dependent only on their value, but on some other custom conditions.
 */
public interface FlagWithCustomBehavior extends EnumWithCustomValue {
    /**
     * Checks if given enum value is present in flags of specified value.
     * @param flagsValue value of flags
     * @return whether enum is present in flags
     */
    boolean isPresent(long flagsValue);
}
