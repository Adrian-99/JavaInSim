package pl.adrian.api.common.structures;

import pl.adrian.api.common.enums.DefaultCar;
import pl.adrian.internal.insim.packets.structures.ModSkinId;
import pl.adrian.internal.common.util.PacketDataBytes;

import java.util.Optional;

/**
 * This class holds information about LFS car. It can either be a default car or a mod car.
 */
public class Car {
    private final Optional<DefaultCar> defaultCar;
    private final String skinId;

    /**
     * Creates car information. Constructor used only internally.
     * @param packetDataBytes packet data bytes
     */
    public Car(PacketDataBytes packetDataBytes) {
        var cNameBytes = packetDataBytes.readByteArray(4);
        var bytes = new byte[3];
        for (var i = 0; i < 3; i++) {
            bytes[i] = cNameBytes[i] > 127 ?
                    (byte) (cNameBytes[i] - 256) :
                    (byte) cNameBytes[i];
        }
        defaultCar = DefaultCar.fromString(new String(bytes));
        if (defaultCar.isPresent()) {
            skinId = defaultCar.get().name();
        } else {
            var cNameLong = 0L;
            for (var i = 2; i >= 0; i--) {
                cNameLong <<= 8;
                cNameLong |= cNameBytes[i];
            }
            skinId = new ModSkinId(cNameLong).getStringValue();
        }
    }

    /**
     * @return default car, or empty if it is a mod car
     */
    public Optional<DefaultCar> getDefaultCar() {
        return defaultCar;
    }

    /**
     * @return car skin id (car name for default cars)
     */
    public String getSkinId() {
        return skinId;
    }

    /**
     * @return whether is a mod car
     */
    public boolean isMod() {
        return defaultCar.isEmpty();
    }
}
