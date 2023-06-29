package pl.adrian.internal.insim.packets.util;

import pl.adrian.internal.insim.packets.annotations.*;
import pl.adrian.internal.insim.packets.annotations.Byte;
import pl.adrian.internal.insim.packets.annotations.Short;
import pl.adrian.internal.common.enums.EnumWithCustomValue;
import pl.adrian.internal.insim.packets.enums.ValidationFailureCategory;
import pl.adrian.internal.insim.packets.exceptions.PacketValidationException;
import pl.adrian.internal.insim.packets.structures.base.ByteInstructionStructure;
import pl.adrian.internal.insim.packets.structures.base.ComplexInstructionStructure;
import pl.adrian.internal.insim.packets.structures.base.UnsignedInstructionStructure;
import pl.adrian.internal.insim.packets.structures.base.WordInstructionStructure;
import pl.adrian.internal.insim.packets.base.InstructionPacket;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

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
                        tryToValidateShort(field, packetOrStructure) ||
                        tryToValidateChar(field, packetOrStructure) ||
                        tryToValidateUnsigned(field, packetOrStructure) ||
                        tryToValidateInt(field, packetOrStructure) ||
                        tryToValidateStructure(field, packetOrStructure);

                if (!anyAnnotationFound) {
                    throw new PacketValidationException(
                            field.getName(),
                            ValidationFailureCategory.INCORRECT_TYPE_ANNOTATION,
                            "missing LFS type annotation"
                    );
                }
            } catch (IllegalAccessException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    private static boolean tryToValidateByte(Field field, Object packetOrStructure) throws IllegalAccessException {
        var byteAnnotation = field.getAnnotation(Byte.class);
        if (byteAnnotation != null) {
            var arrayAnnotation = field.getAnnotation(Array.class);
            if (arrayAnnotation != null) {
                validateArray(
                        arrayAnnotation,
                        field,
                        field.get(packetOrStructure),
                        element -> validateByte(byteAnnotation, field, element)
                );
            } else {
                validateByte(byteAnnotation, field, field.get(packetOrStructure));
            }
            return true;
        }
        return false;
    }

    private static void validateByte(Byte annotation, Field field, Object value) throws PacketValidationException {
        if (value != null) {
            int intValue;
            if (value instanceof java.lang.Short shortValue) {
                intValue = shortValue;
            } else if (value instanceof Character charValue) {
                intValue = charValue;
            } else if (value instanceof Enum<?> enumValue) {
                intValue = enumValue.ordinal();
            } else if (value instanceof Boolean booleanValue) {
                intValue = Boolean.TRUE.equals(booleanValue) ? 1 : 0;
            } else if (value instanceof ByteInstructionStructure byteStructureValue) {
                intValue = byteStructureValue.getByteValue();
            } else {
                throw getUnsupportedTypeException(field, Byte.class.getSimpleName());
            }

            if (intValue < annotation.minValue() || intValue > annotation.maxValue()) {
                throw getValueOutOfRangeException(field, annotation.minValue(), annotation.maxValue(), intValue);
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
        if (value != null) {
            int intValue;
            if (value instanceof Integer integerValue) {
                intValue = integerValue;
            } else if (value instanceof EnumWithCustomValue enumValue) {
                intValue = enumValue.getValue();
            } else if (value instanceof WordInstructionStructure wordStructureValue) {
                intValue = wordStructureValue.getWordValue();
            } else {
                throw getUnsupportedTypeException(field, Word.class.getSimpleName());
            }

            if (intValue < annotation.minValue() || intValue > annotation.maxValue()) {
                throw getValueOutOfRangeException(field, annotation.minValue(), annotation.maxValue(), intValue);
            }
        }
    }

    private static boolean tryToValidateShort(Field field, Object packetOrStructure) throws IllegalAccessException {
        var annotation = field.getAnnotation(Short.class);
        if (annotation != null) {
            validateShort(annotation, field, field.get(packetOrStructure));
            return true;
        }
        return false;
    }

    private static void validateShort(Short annotation, Field field, Object value) {
        if (value != null) {
            if (value instanceof java.lang.Short shortValue) {
                if (shortValue < annotation.minValue() || shortValue > annotation.maxValue()) {
                    throw getValueOutOfRangeException(field, annotation.minValue(), annotation.maxValue(), shortValue);
                }
            } else {
                throw getUnsupportedTypeException(field, Short.class.getSimpleName());
            }
        }
    }

    private static boolean tryToValidateChar(Field field, Object packetOrStructure) throws IllegalAccessException {
        if (field.getAnnotation(Char.class) != null) {
            if (field.getAnnotation(Array.class) != null) {
                validateCharArray(field, field.get(packetOrStructure));
                return true;
            } else {
                throw getMissingArrayAnnotationException(field);
            }
        }
        return false;
    }

    private static void validateCharArray(Field field, Object value) {
        if (value != null && !(value instanceof String)) {
            throw getUnsupportedTypeException(
                    field,
                    String.format("%s %s", Char.class.getSimpleName(), Array.class.getSimpleName())
            );
        }
    }

    private static boolean tryToValidateUnsigned(Field field, Object packetOrStructure) throws IllegalAccessException {
        var unsignedAnnotation = field.getAnnotation(Unsigned.class);
        if (unsignedAnnotation != null) {
            var arrayAnnotation = field.getAnnotation(Array.class);
            if (arrayAnnotation != null) {
                validateArray(
                        arrayAnnotation,
                        field,
                        field.get(packetOrStructure),
                        element -> validateUnsigned(unsignedAnnotation, field, element)
                );
            } else {
                validateUnsigned(unsignedAnnotation, field, field.get(packetOrStructure));
            }
            return true;
        }
        return false;
    }

    private static void validateUnsigned(Unsigned annotation, Field field, Object value) {
        if (value != null) {
            long longValue;
            if (value instanceof Long lngValue) {
                longValue = lngValue;
            } else if (value instanceof UnsignedInstructionStructure unsignedStructureValue) {
                longValue = unsignedStructureValue.getUnsignedValue();
            } else {
                throw getUnsupportedTypeException(field, Unsigned.class.getSimpleName());
            }

            if (longValue < annotation.minValue() || longValue > annotation.maxValue()) {
                throw getValueOutOfRangeException(field, annotation.minValue(), annotation.maxValue(), longValue);
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
        if (value != null) {
            if (value instanceof Integer intValue) {
                if (intValue < annotation.minValue() || intValue > annotation.maxValue()) {
                    throw getValueOutOfRangeException(field, annotation.minValue(), annotation.maxValue(), intValue);
                }
            } else {
                throw getUnsupportedTypeException(field, Int.class.getSimpleName());
            }
        }
    }

    private static boolean tryToValidateStructure(Field field, Object packetOrStructure) throws IllegalAccessException {
        if (field.getAnnotation(Structure.class) != null) {
            var arrayAnnotation = field.getAnnotation(Array.class);
            if (arrayAnnotation != null) {
                validateArray(
                        arrayAnnotation,
                        field,
                        field.get(packetOrStructure),
                        element -> validateStructure(field, element)
                );
            } else {
                validateStructure(field, field.get(packetOrStructure));
            }
            return true;
        }
        return false;
    }

    private static void validateStructure(Field field, Object value) {
        if (value != null) {
            if (value instanceof ComplexInstructionStructure) {
                validate(getAllStructureFields(value.getClass()), value);
            } else {
                throw getUnsupportedTypeException(field, Structure.class.getSimpleName());
            }
        }
    }

    private static void validateArray(Array annotation, Field field, Object value, Consumer<Object> elementValidator) {
        if (value != null) {
            Object[] valuesArray;
            if (value instanceof Object[] arrayValue) {
                valuesArray = arrayValue;
            } else if (value instanceof List<?> listValue) {
                valuesArray = listValue.toArray();
            } else {
                throw getUnsupportedTypeException(field, Array.class.getSimpleName());
            }

            if ((annotation.dynamicLength() && valuesArray.length <= annotation.length()) ||
                    valuesArray.length == annotation.length()) {
                for (var listElement : valuesArray) {
                    elementValidator.accept(listElement);
                }
            } else {
                throw getWrongArrayLengthException(
                        field,
                        annotation.length(),
                        valuesArray.length,
                        annotation.dynamicLength()
                );
            }
        }
    }

    private static PacketValidationException getUnsupportedTypeException(Field field, String typeName) {
        return new PacketValidationException(
                field.getName(),
                ValidationFailureCategory.UNSUPPORTED_TYPE,
                String.format("type %s not supported as %s", field.getClass().getSimpleName(), typeName)
        );
    }

    private static PacketValidationException getValueOutOfRangeException(Field field,
                                                                         long minValue,
                                                                         long maxValue,
                                                                         long actualValue) {
        throw new PacketValidationException(
                field.getName(),
                ValidationFailureCategory.VALUE_OUT_OF_RANGE,
                String.format("expected value between %d and %d - was %d", minValue, maxValue, actualValue)
        );
    }

    private static PacketValidationException getMissingArrayAnnotationException(Field field) {
        return new PacketValidationException(
                field.getName(),
                ValidationFailureCategory.INCORRECT_TYPE_ANNOTATION,
                "missing required Array annotation"
        );
    }

    private static PacketValidationException getWrongArrayLengthException(Field field,
                                                                          int expectedLength,
                                                                          int actualLength,
                                                                          boolean dynamicLength) {
        return new PacketValidationException(
                field.getName(),
                ValidationFailureCategory.INCORRECT_VALUE_LENGTH,
                String.format(
                        "expected %s%d elements long array - was %d elements long",
                        dynamicLength && actualLength > expectedLength ? "at most " : "",
                        expectedLength,
                        actualLength
                )
        );
    }
}
