/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets;

import com.github.adrian99.javainsim.api.common.structures.Car;
import com.github.adrian99.javainsim.api.insim.InSimConnection;
import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;
import com.github.adrian99.javainsim.api.insim.packets.enums.TinySubtype;
import com.github.adrian99.javainsim.internal.common.util.PacketDataBytes;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Array;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Byte;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Char;
import com.github.adrian99.javainsim.internal.insim.packets.requests.builders.BasicTinyPacketRequestBuilder;
import com.github.adrian99.javainsim.internal.insim.packets.base.AbstractPacket;
import com.github.adrian99.javainsim.internal.insim.packets.base.RequestablePacket;

/**
 * SeLected Car. The packet is sent by LFS when a connection selects a car (empty if no car).
 * NOTE: If a new guest joins and does have a car selected then this packet will be sent.
 */
public class SlcPacket extends AbstractPacket implements RequestablePacket {
    @Byte
    private final short ucid;

    @Char
    @Array(length = 4)
    private final Car car;

    /**
     * Creates selected car packet. Constructor used only internally.
     * @param reqI 0 unless this is a reply to a {@link TinySubtype#SLC Tiny SLC} request
     * @param packetDataBytes packet data bytes
     */
    public SlcPacket(short reqI, PacketDataBytes packetDataBytes) {
        super(8, PacketType.SLC, reqI);
        ucid = packetDataBytes.readByte();
        car = new Car(packetDataBytes);
    }

    /**
     * @return connection's unique id (0 = host)
     */
    public short getUcid() {
        return ucid;
    }

    /**
     * @return selected car
     */
    public Car getCar() {
        return car;
    }

    /**
     * Creates builder for packet request for {@link SlcPacket}.
     * @param inSimConnection InSim connection to request packet from
     * @return packet request builder
     */
    public static BasicTinyPacketRequestBuilder<SlcPacket> request(InSimConnection inSimConnection) {
        return new BasicTinyPacketRequestBuilder<>(inSimConnection, TinySubtype.SLC);
    }
}
