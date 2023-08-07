/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package pl.adrian.api.insim.packets;

import pl.adrian.api.insim.packets.enums.PacketType;
import pl.adrian.api.insim.packets.enums.ViewIdentifier;
import pl.adrian.internal.insim.packets.annotations.Byte;
import pl.adrian.internal.insim.packets.base.InstructionPacket;
import pl.adrian.internal.insim.packets.base.AbstractPacket;
import pl.adrian.internal.insim.packets.util.PacketBuilder;

/**
 * Set Car Camera - Simplified camera packet (not SHIFT+U mode).
 */
public class SccPacket extends AbstractPacket implements InstructionPacket {
    @Byte
    private final short viewPlid;
    @Byte
    private final ViewIdentifier inGameCam;

    /**
     * Creates set car camera packet.
     * @param viewPlid unique ID of player to view - set to 255 to leave unchanged
     * @param inGameCam camera - set to 255 ({@link ViewIdentifier#ANOTHER}) to leave unchanged
     */
    public SccPacket(int viewPlid, ViewIdentifier inGameCam) {
        super(8, PacketType.SCC, 0);
        this.viewPlid = (short) viewPlid;
        this.inGameCam = inGameCam;
    }

    @Override
    public byte[] getBytes() {
        return new PacketBuilder(size, type, reqI)
                .writeZeroByte()
                .writeByte(viewPlid)
                .writeByte(inGameCam.getValue())
                .writeZeroBytes(2)
                .getBytes();
    }
}
