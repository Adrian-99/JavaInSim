package pl.adrian.api.packets.enums;

import pl.adrian.internal.packets.enums.EnumHelpers;

/**
 * Enumeration for type of message.
 */
public enum MessageType {
    /**
     * 0 - system message
     */
    SYSTEM,
    /**
     * 1 - normal visible user message
     */
    USER,
    /**
     * 2 - hidden message starting with special prefix (see {@link pl.adrian.api.packets.IsiPacket IsiPacket})
     */
    PREFIX,
    /**
     * 3 - hidden message typed on local pc with /o command
     */
    O;

    /**
     * Converts ordinal number to enum value.
     * @param ordinal ordinal number
     * @return enum value
     */
    public static MessageType fromOrdinal(int ordinal) {
        return EnumHelpers.get(MessageType.class).fromOrdinal(ordinal);
    }
}
