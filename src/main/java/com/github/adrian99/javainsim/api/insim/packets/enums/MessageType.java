/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets.enums;

import com.github.adrian99.javainsim.api.insim.packets.IsiPacket;
import com.github.adrian99.javainsim.internal.common.enums.EnumHelpers;

/**
 * Enumeration for type of message.
 */
public enum MessageType {
    /**
     * 0 - system message
     */
    SYSTEM,
    /**
     * 1 - normal visible user message
     */
    USER,
    /**
     * 2 - hidden message starting with special prefix (see {@link IsiPacket IsiPacket})
     */
    PREFIX,
    /**
     * 3 - hidden message typed on local pc with /o command
     */
    O;

    /**
     * Converts ordinal number to enum value.
     * @param ordinal ordinal number
     * @return enum value
     */
    public static MessageType fromOrdinal(int ordinal) {
        return EnumHelpers.get(MessageType.class).fromOrdinal(ordinal);
    }
}
