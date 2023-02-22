package pl.adrian.internal.packets.exceptions;

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

    /**
     * Creates PacketReadingException
     * @param message exception message
     * @param cause exception cause
     */
    public PacketReadingException(String message, Throwable cause) {
        super(message, cause);
    }
}
