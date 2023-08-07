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
 * which can be requested from LFS by sending appropriate {@link pl.adrian.api.insim.packets.TinyPacket TinyPacket}.
 * This interface extends {@link InfoPacket} interface, therefore it is not necessary to implement
 * both of them while creating packet class.
 */
public interface RequestablePacket extends InfoPacket {
}
