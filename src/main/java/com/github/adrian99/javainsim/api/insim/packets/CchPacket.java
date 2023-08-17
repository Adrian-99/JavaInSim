/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets;

import com.github.adrian99.javainsim.api.insim.packets.flags.PlayerFlag;
import com.github.adrian99.javainsim.internal.common.util.PacketDataBytes;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Byte;
import com.github.adrian99.javainsim.internal.insim.packets.base.InfoPacket;
import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;
import com.github.adrian99.javainsim.api.insim.packets.enums.ViewIdentifier;
import com.github.adrian99.javainsim.internal.insim.packets.base.AbstractPacket;

/**
 * Camera CHange. The packet is sent by LFS when any player changes the camera.<br>
 * To track cameras 3 points need to considered:<br>
 * 1) The default camera: {@link ViewIdentifier#DRIVER}<br>
 * 2) Player flags: {@link PlayerFlag#CUSTOM_VIEW PlayerFlag.CUSTOM_VIEW}
 * means {@link ViewIdentifier#CUSTOM} at start or pit exit<br>
 * 3) {@link CchPacket}: sent when an existing driver changes camera
 */
public class CchPacket extends AbstractPacket implements InfoPacket {
    @Byte
    private final short plid;
    @Byte
    private final ViewIdentifier camera;

    /**
     * Creates camera change packet. Constructor used only internally.
     * @param packetDataBytes packet data bytes
     */
    public CchPacket(PacketDataBytes packetDataBytes) {
        super(8, PacketType.CCH, 0);
        plid = packetDataBytes.readByte();
        camera = ViewIdentifier.fromOrdinal(packetDataBytes.readByte());
        packetDataBytes.skipZeroBytes(3);
    }

    /**
     * @return player's unique id
     */
    public short getPlid() {
        return plid;
    }

    /**
     * @return view identifier
     */
    public ViewIdentifier getCamera() {
        return camera;
    }
}
