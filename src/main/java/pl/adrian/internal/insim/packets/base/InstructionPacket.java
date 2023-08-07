/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package pl.adrian.internal.insim.packets.base;

/**
 * This interface must be implemented by all packet classes that represent packets
 * which can be sent to LFS.
 */
public interface InstructionPacket extends Packet {
    /**
     * Converts packet to array of bytes representation.
     * @return Packet in form of array of bytes
     */
    byte[] getBytes();
}
