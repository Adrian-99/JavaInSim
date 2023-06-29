package pl.adrian.internal.insim.packets.enums;

/**
 * Enumeration for validation failure categories.
 */
public enum ValidationFailureCategory {
    /**
     * value was lower than minimum allowed or higher than maximum allowed
     */
    VALUE_OUT_OF_RANGE,
    /**
     * value length was incorrect
     */
    INCORRECT_VALUE_LENGTH,
    /**
     * field had type unsupported by given LFS field type
     */
    UNSUPPORTED_TYPE,
    /**
     * field was missing or had incorrect LFS field type annotation
     */
    INCORRECT_TYPE_ANNOTATION
}
