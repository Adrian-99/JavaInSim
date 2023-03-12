package pl.adrian.internal.packets.util;

import pl.adrian.internal.packets.annotations.*;
import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.base.InstructionPacket;
import pl.adrian.internal.packets.enums.EnumWithCustomValue;
import pl.adrian.internal.packets.exceptions.PacketValidationException;
import pl.adrian.api.packets.flags.Flags;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is a helper that is used while validating packets.
 */
public class PacketValidator {
    private PacketValidator() {}

    /**
     * Triggers validation for all fields of packet.
     * @param packet packet to be validated
     * @throws PacketValidationException if validation of any field fails
     */
    public static void validate(InstructionPacket packet) throws PacketValidationException {
        if (packet != null) {
            validate(getAllPacketFields(packet.getClass()), packet);
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

    private static List<Field> getAllStructureFields(Class<?> structureClass) {
        return List.of(structureClass.getDeclaredFields());
    }

    @SuppressWarnings("java:S3011")
    private static void validate(List<Field> fields, Object packetOrStructure) {
        for (var field : fields) {
            try {
                field.setAccessible(true);
                var anyAnnotationFound = tryToValidateByte(field, packetOrStructure) ||
                        tryToValidateWord(field, packetOrStructure) ||
                        tryToValidateCharArray(field, packetOrStructure) ||
                        tryToValidateUnsigned(field, packetOrStructure) ||
                        tryToValidateInt(field, packetOrStructure) ||
                        tryToValidateStructureArray(field, packetOrStructure);

                if (!anyAnnotationFound) {
                    throw new PacketValidationException(field);
                }
            } catch (IllegalAccessException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    private static boolean tryToValidateByte(Field field, Object packetOrStructure) throws IllegalAccessException {
        var annotation = field.getAnnotation(Byte.class);
        if (annotation != null) {
            validateByte(annotation, field, field.get(packetOrStructure));
            return true;
        }
        return false;
    }

    private static void validateByte(Byte annotation, Field field, Object value) throws PacketValidationException {
        var typeName = "Byte";
        if (value != null) {
            int intValue;
            if (value instanceof Short shortValue) {
                intValue = shortValue;
            } else if (value instanceof Character charValue) {
                intValue = charValue;
            } else if (value instanceof Enum<?> enumValue) {
                intValue = enumValue.ordinal();
            } else if (value instanceof Flags<?> flagsValue) {
                intValue = flagsValue.getValue();
            } else if (value instanceof Boolean booleanValue) {
                intValue = Boolean.TRUE.equals(booleanValue) ? 1 : 0;
            } else {
                throw new PacketValidationException(field, typeName);
            }

            if (intValue < annotation.minValue() || intValue > annotation.maxValue()) {
                throw new PacketValidationException(
                        field,
                        annotation.minValue(),
                        annotation.maxValue(),
                        intValue,
                        typeName
                );
            }
        }
    }

    private static boolean tryToValidateWord(Field field, Object packetOrStructure) throws IllegalAccessException {
        var annotation = field.getAnnotation(Word.class);
        if (annotation != null) {
            validateWord(annotation, field, field.get(packetOrStructure));
            return true;
        }
        return false;
    }

    private static void validateWord(Word annotation, Field field, Object value) throws PacketValidationException {
        var typeName = "Word";
        if (value != null) {
            int intValue;
            if (value instanceof Integer integerValue) {
                intValue = integerValue;
            } else if (value instanceof Flags<? extends Enum<?>> flagsValue) {
                intValue = flagsValue.getValue();
            } else if (value instanceof EnumWithCustomValue enumValue) {
                intValue = enumValue.getValue();
            } else {
                throw new PacketValidationException(field, typeName);
            }

            if (intValue < annotation.minValue() || intValue > annotation.maxValue()) {
                throw new PacketValidationException(
                        field,
                        annotation.minValue(),
                        annotation.maxValue(),
                        intValue,
                        typeName
                );
            }
        }
    }

    private static boolean tryToValidateCharArray(Field field, Object packetOrStructure) throws IllegalAccessException {
        var annotation = field.getAnnotation(CharArray.class);
        if (annotation != null) {
            validateCharArray(annotation, field, field.get(packetOrStructure));
            return true;
        }
        return false;
    }

    private static void validateCharArray(CharArray annotation, Field field, Object value) {
        var typeName = "Char Array";
        if (value != null) {
            if (value instanceof String stringValue) {
                if (annotation.strictLengthValidation() && stringValue.length() > annotation.length() - 1) {
                    throw new PacketValidationException(
                            field,
                            stringValue.length(),
                            annotation.length() - 1,
                            false,
                            "bytes",
                            typeName
                    );
                }
            } else {
                throw new PacketValidationException(field, typeName);
            }
        }
    }

    private static boolean tryToValidateUnsigned(Field field, Object packetOrStructure) throws IllegalAccessException {
        var annotation = field.getAnnotation(Unsigned.class);
        if (annotation != null) {
            validateUnsigned(annotation, field, field.get(packetOrStructure));
            return true;
        }
        return false;
    }

    private static void validateUnsigned(Unsigned annotation, Field field, Object value) {
        var typeName = "Unsigned";
        if (value != null) {
            long longValue;
            if (value instanceof Long lngValue) {
                longValue = lngValue;
            } else if (value instanceof Flags<?> flagsValue) {
                longValue = flagsValue.getValue();
            } else {
                throw new PacketValidationException(field, typeName);
            }

            if (longValue < annotation.minValue() || longValue > annotation.maxValue()) {
                throw new PacketValidationException(
                        field,
                        annotation.minValue(),
                        annotation.maxValue(),
                        longValue,
                        typeName
                );
            }
        }
    }

    private static boolean tryToValidateInt(Field field, Object packetOrStructure) throws IllegalAccessException {
        var annotation = field.getAnnotation(Int.class);
        if (annotation != null) {
            validateInt(annotation, field, field.get(packetOrStructure));
            return true;
        }
        return false;
    }

    private static void validateInt(Int annotation, Field field, Object value) {
        var typeName = "Int";
        if (value != null) {
            if (value instanceof Integer intValue) {
                if (intValue < annotation.minValue() || intValue > annotation.maxValue()) {
                    throw new PacketValidationException(
                            field,
                            annotation.minValue(),
                            annotation.maxValue(),
                            intValue,
                            typeName
                    );
                }
            } else {
                throw new PacketValidationException(field, typeName);
            }
        }
    }

    private static boolean tryToValidateStructureArray(Field field, Object packetOrStructure) throws IllegalAccessException {
        var annotation = field.getAnnotation(StructureArray.class);
        if (annotation != null) {
            validateStructureArray(annotation, field, field.get(packetOrStructure));
            return true;
        }
        return false;
    }

    private static void validateStructureArray(StructureArray annotation, Field field, Object value) {
        var typeName = "StructureArray";
        if (value instanceof Object[] arrayValue) {
            if (arrayValue.length == annotation.length()) {
                for (var arrayElement : arrayValue) {
                    if (arrayElement != null) {
                        validate(getAllStructureFields(arrayElement.getClass()), arrayElement);
                    }
                }
            } else {
                throw new PacketValidationException(
                        field,
                        arrayValue.length,
                        annotation.length(),
                        true,
                        "objects",
                        typeName
                );
            }
        } else {
            throw new PacketValidationException(field, typeName);
        }
    }
}
