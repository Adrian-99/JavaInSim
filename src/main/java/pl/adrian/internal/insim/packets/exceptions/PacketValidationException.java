package pl.adrian.internal.insim.packets.exceptions;

import pl.adrian.internal.insim.packets.enums.ValidationFailureCategory;

/**
 * Exception that is thrown when error occurs while validating packet.
 */
public class PacketValidationException extends RuntimeException {
    /**
     * Name of field that caused error during validation
     */
    private final transient String fieldName;
    /**
     * Category of validation failure
     */
    private final ValidationFailureCategory failureCategory;

    /**
     * Creates PacketValidationException.
     * @param fieldName name of the field that caused error during validation
     * @param failureCategory category of validation failure
     * @param reason reason for validation failure
     */
    public PacketValidationException(String fieldName, ValidationFailureCategory failureCategory, String reason) {
        super("Validation failed for field \"" + fieldName + "\": " + reason);
        this.fieldName = fieldName;
        this.failureCategory = failureCategory;
    }

    /**
     * @return name of field that caused error during validation
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * @return category of validation failure
     */
    public ValidationFailureCategory getFailureCategory() {
        return failureCategory;
    }
}
