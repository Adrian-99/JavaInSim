/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.internal.insim.packets.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation indicating that given field of packet is sent to and from LFS
 * in form of 4-byte float.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Float {
    /**
     * @return minimum allowed value, {@link java.lang.Float#MIN_VALUE} by default
     */
    float minValue() default java.lang.Float.MIN_VALUE;

    /**
     * @return maximum allowed value, {@link java.lang.Float#MAX_VALUE} by default
     */
    float maxValue() default java.lang.Float.MAX_VALUE;
}
