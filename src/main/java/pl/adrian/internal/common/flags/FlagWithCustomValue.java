/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package pl.adrian.internal.common.flags;

import pl.adrian.internal.common.enums.EnumWithCustomValue;

/**
 * This interface must be implemented by all enums representing flags,
 * whose values sent or received from LFS should not be based off their ordinal
 * number but rather some custom value. It is also possible to define value mask,
 * which allows to define which bits should not be set for given flag.
 */
public interface FlagWithCustomValue extends EnumWithCustomValue {
    /**
     * @return mask of enum value
     */
    int getValueMask();
}
