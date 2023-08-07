/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package pl.adrian.internal.common.enums;

/**
 * This interface must be implemented by all enums, whose values sent or received from LFS
 * should not be based off their ordinal number but rather some custom value.
 */
public interface EnumWithCustomValue {
    /**
     * @return custom value of enum
     */
    int getValue();
}
