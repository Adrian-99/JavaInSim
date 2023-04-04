package pl.adrian.api.packets.structures;

import pl.adrian.api.packets.enums.DefaultCar;
import pl.adrian.internal.packets.exceptions.PacketReadingException;
import pl.adrian.internal.packets.structures.ModSkinId;

import java.util.Arrays;
import java.util.Optional;

/**
 * This class holds information about LFS car. It can either be a default car or a mod car.
 */
public class Car {
    private final Optional<DefaultCar> defaultCar;
    private final String skinId;

    /**
     * Creates car information.
     * @param cNameBytes car name bytes - should be 4 bytes long and last element should equal to 0
     */
    public Car(short[] cNameBytes) {
        if (cNameBytes.length == 4 && cNameBytes[3] == 0) {
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
        } else {
            throw new PacketReadingException("Incorrect input for creating car structure: " + Arrays.toString(cNameBytes));
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
