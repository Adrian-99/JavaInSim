/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets.enums;

/**
 * Enumeration for sounds to be played while sending message.
 */
public enum MessageSound {
    /**
     * 0 - no sound
     */
    SILENT,
    /**
     * 1 - message sound
     */
    MESSAGE,
    /**
     * 2 - system message sound
     */
    SYSMESSAGE,
    /**
     * 3 - invalid key sound
     */
    INVALIDKEY,
    /**
     * 4 - error sound
     */
    ERROR
}
