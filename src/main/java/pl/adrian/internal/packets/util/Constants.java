package pl.adrian.internal.packets.util;

/**
 * This class contains constant values used in library
 */
public class Constants {
    /**
     * InSim version supported by library
     */
    public static final short INSIM_VERSION = 9;
    /**
     * Size (in bytes) of header of each packet
     */
    public static final short PACKET_HEADER_SIZE = 3;
    /**
     * Maximum number of mods that can be allowed to be used on host
     */
    public static final short MAL_MAX_MODS = 120;
    /**
     * Maximum number of cars that can be sent in single {@link pl.adrian.api.packets.NlpPacket NlpPacket}
     */
    public static final short NLP_MAX_CARS = 40;
    /**
     * Maximum number of cars that can be sent in single {@link pl.adrian.api.packets.MciPacket MciPacket}
     */
    public static final short MCI_MAX_CARS = 16;

    private Constants() {}
}
