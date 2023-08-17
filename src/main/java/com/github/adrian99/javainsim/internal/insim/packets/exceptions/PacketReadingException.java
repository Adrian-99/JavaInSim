/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.internal.insim.packets.exceptions;

/**
 * Exception that is thrown when error occurs while reading packet
 */
public class PacketReadingException extends RuntimeException {
    /**
     * Creates PacketReadingException
     * @param message exception message
     */
    public PacketReadingException(String message) {
        super(message);
    }
}
