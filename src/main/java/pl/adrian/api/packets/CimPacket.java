package pl.adrian.api.packets;

import pl.adrian.api.packets.enums.*;
import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.base.InfoPacket;
import pl.adrian.internal.packets.base.Packet;

import java.util.Optional;

/**
 * Conn Interface Mode - connection's interface mode.
 */
public class CimPacket extends Packet implements InfoPacket {
    @Byte
    private final short ucid;
    @Byte
    private final InterfaceMode mode;
    @Byte
    private final short subMode;
    @Byte
    private final SelectedObjectType selType;

    /**
     * Creates connection's interface mode packet.
     * @param ucid connection's unique id (0 = local)
     * @param mode interface mode
     * @param subMode submode identifier
     * @param selType selected object type
     */
    public CimPacket(short ucid, InterfaceMode mode, short subMode, SelectedObjectType selType) {
        super(8, PacketType.CIM, 0);
        this.ucid = ucid;
        this.mode = mode;
        this.subMode = subMode;
        this.selType = selType;
    }

    /**
     * @return connection's unique id (0 = local)
     */
    public short getUcid() {
        return ucid;
    }

    /**
     * @return interface mode
     */
    public InterfaceMode getMode() {
        return mode;
    }

    /**
     * @return normal interface submode, or empty if mode is not normal
     */
    public Optional<NormalInterfaceSubmode> getNormalSubmode() {
        if (mode.equals(InterfaceMode.NORMAL)) {
            return Optional.of(NormalInterfaceSubmode.fromOrdinal(subMode));
        } else {
            return Optional.empty();
        }
    }

    /**
     * @return garage interface submode, or empty if mode is not garage
     */
    public Optional<GarageInterfaceSubmode> getGarageSubmode() {
        if (mode.equals(InterfaceMode.GARAGE)) {
            return Optional.of(GarageInterfaceSubmode.fromOrdinal(subMode));
        } else {
            return Optional.empty();
        }
    }

    /**
     * @return shift U interface submode, or empty if mode is not shift U
     */
    public Optional<ShiftUInterfaceSubmode> getShiftUSubmode() {
        if (mode.equals(InterfaceMode.SHIFTU)) {
            return Optional.of(ShiftUInterfaceSubmode.fromOrdinal(subMode));
        } else {
            return Optional.empty();
        }
    }

    /**
     * @return selected object type
     */
    public SelectedObjectType getSelType() {
        return selType;
    }
}
