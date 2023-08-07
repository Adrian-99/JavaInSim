/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package pl.adrian.testutil;

public class TestUtils {
    private TestUtils() {}

    public static short byteToShort(byte value) {
        if (value < 0) {
            return (short) (value + 256);
        } else {
            return value;
        }
    }
}
