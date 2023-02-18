package pl.adrian.packets.exceptions;

import java.lang.reflect.Field;

public class PacketValidationException extends RuntimeException {
    private final transient Field field;
    private final FailureReason failureReason;

    public PacketValidationException(Field field, int minValue, int maxValue, int actualValue, String typeName) {
        this(field, minValue, maxValue, (long) actualValue, typeName);
    }

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

    public PacketValidationException(Field field) {
        super(String.format("Type annotation not found for field %s", field.getName()));
        this.field = field;
        failureReason = FailureReason.MISSING_TYPE_ANNOTATION;
    }

    public Field getField() {
        return field;
    }

    public FailureReason getFailureReason() {
        return failureReason;
    }

    public enum FailureReason {
        VALUE_OUT_OF_RANGE,
        VALUE_TOO_LONG,
        UNSUPPORTED_TYPE,
        MISSING_TYPE_ANNOTATION
    }
}
