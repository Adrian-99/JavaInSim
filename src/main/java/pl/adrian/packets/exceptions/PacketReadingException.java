package pl.adrian.packets.exceptions;

public class PacketReadingException extends RuntimeException {
    public PacketReadingException(String message) {
        super(message);
    }

    public PacketReadingException(String message, Throwable cause) {
        super(message, cause);
    }
}
