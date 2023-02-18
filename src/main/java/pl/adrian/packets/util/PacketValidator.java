package pl.adrian.packets.util;

import pl.adrian.packets.annotations.Byte;
import pl.adrian.packets.annotations.CharArray;
import pl.adrian.packets.annotations.Unsigned;
import pl.adrian.packets.annotations.Word;
import pl.adrian.packets.base.SendablePacket;
import pl.adrian.packets.exceptions.PacketValidationException;
import pl.adrian.packets.flags.Flags;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class PacketValidator {
    private static final short BYTE_MIN_VALUE = 0;
    private static final short BYTE_MAX_VALUE = 255;
    private static final int WORD_MIN_VALUE = 0;
    private static final int WORD_MAX_VALUE = 65535;
    private static final long UNSIGNED_MIN_VALUE = 0;
    private static final long UNSIGNED_MAX_VALUE = 4294967295L;

    private PacketValidator() {}

    @SuppressWarnings("java:S3011")
    public static void validate(SendablePacket packet) throws PacketValidationException {
        if (packet != null) {
            for (var field : getAllPacketFields(packet.getClass())) {
                try {
                    field.setAccessible(true);
                    var anyAnnotationFound = tryToValidateByte(field, packet) ||
                            tryToValidateWord(field, packet) ||
                            tryToValidateCharArray(field, packet) ||
                            tryToValidateUnsigned(field, packet);

                    if (!anyAnnotationFound) {
                        throw new PacketValidationException(field);
                    }
                } catch (IllegalAccessException e) {
                    throw new IllegalStateException(e);
                }
            }
        }
    }

    private static List<Field> getAllPacketFields(Class<?> packetClass) {
        var currentClassFields = new ArrayList<>(List.of(packetClass.getDeclaredFields()));
        var superClass = packetClass.getSuperclass();
        if (superClass != null) {
            currentClassFields.addAll(getAllPacketFields(superClass));
        }
        return currentClassFields;
    }

    private static boolean tryToValidateByte(Field field, SendablePacket packet) throws IllegalAccessException {
        if (field.getAnnotation(Byte.class) != null) {
            validateByte(field, field.get(packet));
            return true;
        }
        return false;
    }

    private static void validateByte(Field field, Object value) throws PacketValidationException {
        var typeName = "Byte";
        if (value != null) {
            int intValue;
            if (value instanceof Short shortValue) {
                intValue = shortValue;
            } else if (value instanceof Character charValue) {
                intValue = charValue;
            } else if (value instanceof Enum<?> enumValue) {
                intValue = enumValue.ordinal();
            } else {
                throw new PacketValidationException(field, typeName);
            }

            if (intValue < BYTE_MIN_VALUE || intValue > BYTE_MAX_VALUE) {
                throw new PacketValidationException(field, BYTE_MIN_VALUE, BYTE_MAX_VALUE, intValue, typeName);
            }
        }
    }

    private static boolean tryToValidateWord(Field field, SendablePacket packet) throws IllegalAccessException {
        if (field.getAnnotation(Word.class) != null) {
            validateWord(field, field.get(packet));
            return true;
        }
        return false;
    }

    private static void validateWord(Field field, Object value) throws PacketValidationException {
        var typeName = "Word";
        if (value != null) {
            int intValue;
            if (value instanceof Integer integerValue) {
                intValue = integerValue;
            } else if (value instanceof Flags<? extends Enum<?>> flagsValue) {
                intValue = flagsValue.getValue();
            } else {
                throw new PacketValidationException(field, typeName);
            }

            if (intValue < WORD_MIN_VALUE || intValue > WORD_MAX_VALUE) {
                throw new PacketValidationException(field, WORD_MIN_VALUE, WORD_MAX_VALUE, intValue, typeName);
            }
        }
    }

    private static boolean tryToValidateCharArray(Field field, SendablePacket packet) throws IllegalAccessException {
        var annotation = field.getAnnotation(CharArray.class);
        if (annotation != null) {
            validateCharArray(annotation, field, field.get(packet));
            return true;
        }
        return false;
    }

    private static void validateCharArray(CharArray annotation, Field field, Object value) {
        var typeName = "Char Array";
        if (value != null) {
            if (value instanceof String stringValue) {
                if (stringValue.length() > annotation.maxLength()) {
                    throw new PacketValidationException(
                            field,
                            stringValue.length(),
                            annotation.maxLength(),
                            typeName
                    );
                }
            } else {
                throw new PacketValidationException(field, typeName);
            }
        }
    }

    private static boolean tryToValidateUnsigned(Field field, SendablePacket packet) throws IllegalAccessException {
        if (field.getAnnotation(Unsigned.class) != null) {
            validateUnsigned(field, field.get(packet));
            return true;
        }
        return false;
    }

    private static void validateUnsigned(Field field, Object value) {
        var typeName = "Unsigned";
        if (value != null) {
            if (value instanceof Long longValue) {
                if (longValue < UNSIGNED_MIN_VALUE || longValue > UNSIGNED_MAX_VALUE) {
                    throw new PacketValidationException(field, UNSIGNED_MIN_VALUE, UNSIGNED_MAX_VALUE, longValue, typeName);
                }
            } else {
                throw new PacketValidationException(field, typeName);
            }
        }
    }
}
