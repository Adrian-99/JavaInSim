package pl.adrian.api.insim.packets;

import pl.adrian.api.common.flags.Flags;
import pl.adrian.api.insim.packets.enums.PacketType;
import pl.adrian.api.insim.packets.flags.ButtonInstFlag;
import pl.adrian.api.insim.packets.flags.ClickFlag;
import pl.adrian.internal.common.util.PacketDataBytes;
import pl.adrian.internal.insim.packets.annotations.Byte;
import pl.adrian.internal.insim.packets.base.AbstractPacket;
import pl.adrian.internal.insim.packets.base.InfoPacket;
import pl.adrian.internal.insim.packets.util.Constants;

/**
 * BuTton Click. This packet is sent by LFS when user clicks on a clickable button.
 */
public class BtcPacket extends AbstractPacket implements InfoPacket {
    @Byte
    private final short ucid;
    @Byte(maxValue = Constants.BUTTON_MAX_CLICK_ID)
    private final short clickID;
    @Byte
    private final Flags<ButtonInstFlag> inst;
    @Byte
    private final Flags<ClickFlag> cFlags;

    /**
     * Creates button click packet. Constructor used only internally.
     * @param reqI as received in the {@link BtnPacket}
     * @param packetDataBytes packet data bytes
     */
    public BtcPacket(short reqI, PacketDataBytes packetDataBytes) {
        super(8, PacketType.BTC, reqI);
        ucid = packetDataBytes.readByte();
        clickID = packetDataBytes.readByte();
        inst = new Flags<>(ButtonInstFlag.class, packetDataBytes.readByte());
        cFlags = new Flags<>(ClickFlag.class, packetDataBytes.readByte());
        packetDataBytes.skipZeroByte();
    }

    /**
     * @return connection that clicked the button (zero if local)
     */
    public short getUcid() {
        return ucid;
    }

    /**
     * @return button identifier originally sent in {@link BtnPacket}
     */
    public short getClickID() {
        return clickID;
    }

    /**
     * @return used internally by InSim
     */
    public Flags<ButtonInstFlag> getInst() {
        return inst;
    }

    /**
     * @return button click flags
     */
    public Flags<ClickFlag> getCFlags() {
        return cFlags;
    }
}
