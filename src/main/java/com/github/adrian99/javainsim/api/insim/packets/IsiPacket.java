/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets;

import com.github.adrian99.javainsim.internal.insim.packets.base.InstructionPacket;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Array;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Char;
import com.github.adrian99.javainsim.internal.insim.packets.util.Constants;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Byte;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Word;
import com.github.adrian99.javainsim.internal.insim.packets.base.AbstractPacket;
import com.github.adrian99.javainsim.internal.insim.packets.exceptions.PacketValidationException;
import com.github.adrian99.javainsim.api.common.flags.Flags;
import com.github.adrian99.javainsim.api.insim.packets.flags.IsiFlag;
import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;
import com.github.adrian99.javainsim.internal.insim.packets.util.PacketBuilder;
import com.github.adrian99.javainsim.internal.insim.packets.util.PacketValidator;

/**
 * InSim Init - packet to initialise the InSim system.
 */
public class IsiPacket extends AbstractPacket implements InstructionPacket {
    @Word
    private final int udpPort;
    @Word
    private final Flags<IsiFlag> flags;
    @Byte
    private final short inSimVer;
    @Byte
    private final Character prefix;
    @Word
    private final int interval;
    @Char
    @Array(length = 16)
    private final String admin;
    @Char
    @Array(length = 16)
    private final String iName;

    /**
     * Creates InSim Init packet
     * @param udpPort port for UDP replies from LFS (0 to 65535)
     * @param flags bit flags for options
     * @param prefix special host message prefix character
     * @param interval time in ms between NLP or MCI (0 = none)
     * @param admin admin password (if set in LFS)
     * @param iName a short name for your program
     * @throws PacketValidationException if validation of any field in packet fails
     */
    public IsiPacket(int udpPort,
                     Flags<IsiFlag> flags,
                     Character prefix,
                     int interval,
                     String admin,
                     String iName) throws PacketValidationException {
        super(44, PacketType.ISI, 1);
        this.udpPort = udpPort;
        this.flags = flags;
        this.inSimVer = Constants.INSIM_VERSION;
        this.prefix = prefix;
        this.interval = interval;
        this.admin = admin;
        this.iName = iName;
        PacketValidator.validate(this);
    }

    @Override
    public byte[] getBytes() {
        return new PacketBuilder(size, type, reqI)
                .writeZeroByte()
                .writeWord(udpPort)
                .writeWord(flags.getWordValue())
                .writeByte(inSimVer)
                .writeByte(prefix)
                .writeWord(interval)
                .writeCharArray(admin, 16)
                .writeCharArray(iName, 16)
                .getBytes();
    }

    /**
     * @return port for UDP replies from LFS (0 to 65535)
     */
    public int getUdpPort() {
        return udpPort;
    }
}
