/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package pl.adrian.api.insim.packets.enums;

import pl.adrian.api.insim.packets.RipPacket;
import pl.adrian.internal.common.enums.EnumHelpers;

/**
 * Enumeration for error codes returned in {@link RipPacket}.
 */
public enum RipErrorCode {
    /**
     * value 0: OK: completed instruction
     */
    OK,
    /**
     * value 1: OK: already at the destination
     */
    ALREADY,
    /**
     * value 2: can't run a replay - dedicated host
     */
    DEDICATED,
    /**
     * value 3: can't start a replay - not in a suitable mode
     */
    WRONG_MODE,
    /**
     * value 4: RName is zero but no replay is currently loaded
     */
    NOT_REPLAY,
    /**
     * value 5: {@link RipPacket} corrupted (e.g. RName does not end with zero)
     */
    CORRUPTED,
    /**
     * value 6: the replay file was not found
     */
    NOT_FOUND,
    /**
     * balue 7: obsolete / future / corrupted
     */
    UNLOADABLE,
    /**
     * value 8: destination is beyond replay length
     */
    DEST_OOB,
    /**
     * value 9: unknown error found starting replay
     */
    UNKNOWN,
    /**
     * value 10: replay search was terminated by user
     */
    USER,
    /**
     * value 11: can't reach destination - SPR is out of sync
     */
    OOS;

    /**
     * Converts ordinal number to enum value.
     * @param ordinal ordinal number
     * @return enum value
     */
    public static RipErrorCode fromOrdinal(int ordinal) {
        return EnumHelpers.get(RipErrorCode.class).fromOrdinal(ordinal);
    }
}
