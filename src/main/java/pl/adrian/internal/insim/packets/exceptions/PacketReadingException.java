package pl.adrian.internal.insim.packets.exceptions;

/**
 * Exception that is thrown when error occurs while reading packet
 */
public class PacketReadingException extends RuntimeException {
    /**
     * Creates PacketReadingException
     * @param message exception message
     */
    public PacketReadingException(String message) {
        super(message);
    }
}
