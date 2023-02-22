package pl.adrian.internal.packets.exceptions;

import java.lang.reflect.Field;

/**
 * Exception that is thrown when error occurs while validating packet
 */
public class PacketValidationException extends RuntimeException {
    /**
     * Field that caused error during validation
     */
    private final transient Field field;
    /**
     * Reason for validation failure
     */
    private final FailureReason failureReason;

    /**
     * Creates PacketValidationException for invalid field value error
     * @param field field with invalid value
     * @param minValue minimum allowed value
     * @param maxValue maximum allowed value
     * @param actualValue actual field value
     * @param typeName name of LFS field type
     */
    public PacketValidationException(Field field, int minValue, int maxValue, int actualValue, String typeName) {
        this(field, minValue, maxValue, (long) actualValue, typeName);
    }

    /**
     * Creates PacketValidationException for invalid field value error
     * @param field field with invalid value
     * @param minValue minimum allowed value
     * @param maxValue maximum allowed value
     * @param actualValue actual field value
     * @param typeName name of LFS field type
     */
    public PacketValidationException(Field field, long minValue, long maxValue, long actualValue, String typeName) {
        super(
                String.format(
                        "Invalid %s field value as %s - must be between %d and %d - was %d",
                        field.getName(),
                        typeName,
                        minValue,
                        maxValue,
                        actualValue
                )
        );
        this.field = field;
        failureReason = FailureReason.VALUE_OUT_OF_RANGE;
    }

    /**
     * Creates PacketValidationException for value too long error
     * @param field field with invalid value
     * @param valueLength actual value length
     * @param maxValueLength maximum allowed value length
     * @param typeName name of LFS field type
     */
    public PacketValidationException(Field field, int valueLength, int maxValueLength, String typeName) {
        super(
                String.format(
                        "Invalid %s field value as %s - must not be longer than %d bytes - was %d bytes long",
                        field.getName(),
                        typeName,
                        maxValueLength,
                        valueLength
                )
        );
        this.field = field;
        failureReason = FailureReason.VALUE_TOO_LONG;
    }

    /**
     * Creates PacketValidationException for unsupported type error
     * @param field field with unsupported type
     * @param typeName name of LFS field type
     */
    public PacketValidationException(Field field, String typeName) {
        super(
                String.format("Type %s of field %s is not supported as %s",
                        field.getClass().getSimpleName(),
                        field.getName(),
                        typeName
                )
        );
        this.field = field;
        failureReason = FailureReason.UNSUPPORTED_TYPE;
    }

    /**
     * Creates PacketValidationException for missing type annotation error
     * @param field field with no annotation
     */
    public PacketValidationException(Field field) {
        super(String.format("Type annotation not found for field %s", field.getName()));
        this.field = field;
        failureReason = FailureReason.MISSING_TYPE_ANNOTATION;
    }

    /**
     * @return field that caused error during validation
     */
    public Field getField() {
        return field;
    }

    /**
     * @return reason for validation failure
     */
    public FailureReason getFailureReason() {
        return failureReason;
    }

    /**
     * Enumeration for validation failure reasons
     */
    public enum FailureReason {
        /**
         * value was lower than minimum allowed or higher than maximum allowed
         */
        VALUE_OUT_OF_RANGE,
        /**
         * value was too long
         */
        VALUE_TOO_LONG,
        /**
         * field had type unsupported by given LFS field type
         */
        UNSUPPORTED_TYPE,
        /**
         * field was missing LFS field type annotation
         */
        MISSING_TYPE_ANNOTATION
    }
}
