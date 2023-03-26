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
    public Car(byte[] cNameBytes) {
        if (cNameBytes.length == 4 && cNameBytes[3] == 0) {
            defaultCar = DefaultCar.fromString(new String(cNameBytes, 0, 3));
            if (defaultCar.isPresent()) {
                skinId = defaultCar.get().name();
            } else {
                var cNameLong = 0L;
                for (var i = 2; i >= 0; i--) {
                    var nonNegativeValue = (short) cNameBytes[i];
                    if (nonNegativeValue < 0) {
                        nonNegativeValue += 256;
                    }
                    cNameLong <<= 8;
                    cNameLong |= nonNegativeValue;
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
