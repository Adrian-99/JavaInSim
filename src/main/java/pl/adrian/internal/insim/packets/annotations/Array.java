/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package pl.adrian.internal.insim.packets.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation indicating that given field of packet is sent to and from LFS
 * in form of array. The same field should also contain another annotation to specify
 * type of array element.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Array {
    /**
     * @return length of the array
     */
    int length();

    /**
     * @return whether array has dynamic length (may have fewer elements than maximum allowed)
     */
    boolean dynamicLength() default false;
}
