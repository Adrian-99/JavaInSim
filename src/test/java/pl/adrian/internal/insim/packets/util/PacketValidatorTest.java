/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package pl.adrian.internal.insim.packets.util;

import org.junit.jupiter.api.Test;
import pl.adrian.internal.insim.packets.annotations.*;
import pl.adrian.internal.insim.packets.annotations.Byte;
import pl.adrian.internal.insim.packets.annotations.Float;
import pl.adrian.internal.insim.packets.annotations.Short;
import pl.adrian.internal.insim.packets.base.AbstractPacket;
import pl.adrian.internal.insim.packets.base.InstructionPacket;
import pl.adrian.api.insim.packets.enums.PacketType;
import pl.adrian.internal.common.enums.EnumWithCustomValue;
import pl.adrian.internal.insim.packets.enums.ValidationFailureCategory;
import pl.adrian.internal.insim.packets.exceptions.PacketValidationException;
import pl.adrian.internal.insim.packets.structures.base.ByteInstructionStructure;
import pl.adrian.internal.insim.packets.structures.base.ComplexInstructionStructure;
import pl.adrian.internal.insim.packets.structures.base.UnsignedInstructionStructure;
import pl.adrian.internal.insim.packets.structures.base.WordInstructionStructure;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PacketValidatorTest {

    @Test
    void validate_withValidValues() {
        var packet = new ValidTestPacket(
                23,
                PacketType.NONE,
                54,
                null,
                true,
                new TestSimpleStructure(64),
                List.of((short) 1, (short) 2, (short) 3),
                354,
                new TestSimpleStructure(28715),
                TestCustomValueEnum.VALUE1,
                (short) 24567,
                "abcdefghij",
                112946L,
                new TestSimpleStructure(150685),
                new Long[] { 1564357L, 875643L },
                1834749274,
                3.14f,
                new TestComplexStructure(123),
                new TestComplexStructure[] { new TestComplexStructure(55), null, new TestComplexStructure(15) }
        );
        assertDoesNotThrow(() -> PacketValidator.validate(packet));
    }

    @Test
    void validate_withTooLowShortByte() {
        var packet = new ValidTestPacket(
                23,
                PacketType.NONE,
                -5,
                null,
                true,
                new TestSimpleStructure(64),
                List.of((short) 1, (short) 2, (short) 3),
                354,
                new TestSimpleStructure(28715),
                TestCustomValueEnum.VALUE1,
                (short) 24567,
                "abcdefghij",
                112946L,
                new TestSimpleStructure(150685),
                new Long[] { 1564357L, 875643L },
                1834749274,
                3.14f,
                new TestComplexStructure(123),
                new TestComplexStructure[] { new TestComplexStructure(55), null, new TestComplexStructure(15) }
        );
        var exception = assertThrows(PacketValidationException.class, () -> PacketValidator.validate(packet));
        assertEquals(ValidationFailureCategory.VALUE_OUT_OF_RANGE, exception.getFailureCategory());
        assertEquals("reqI", exception.getFieldName());
    }

    @Test
    void validate_withTooHighShortByte() {
        var packet = new ValidTestPacket(
                23,
                PacketType.NONE,
                350,
                null,
                true,
                new TestSimpleStructure(64),
                List.of((short) 1, (short) 2, (short) 3),
                354,
                new TestSimpleStructure(28715),
                TestCustomValueEnum.VALUE1,
                (short) 24567,
                "abcdefghij",
                112946L,
                new TestSimpleStructure(150685),
                new Long[] { 1564357L, 875643L },
                1834749274,
                3.14f,
                new TestComplexStructure(123),
                new TestComplexStructure[] { new TestComplexStructure(55), null, new TestComplexStructure(15) }
        );
        var exception = assertThrows(PacketValidationException.class, () -> PacketValidator.validate(packet));
        assertEquals(ValidationFailureCategory.VALUE_OUT_OF_RANGE, exception.getFailureCategory());
        assertEquals("reqI", exception.getFieldName());
    }

    @Test
    void validate_withTooLongByteArray() {
        var packet = new ValidTestPacket(
                23,
                PacketType.NONE,
                54,
                null,
                true,
                new TestSimpleStructure(64),
                List.of((short) 1, (short) 2, (short) 3, (short) 4),
                354,
                new TestSimpleStructure(28715),
                TestCustomValueEnum.VALUE1,
                (short) 24567,
                "abcdefghij",
                112946L,
                new TestSimpleStructure(150685),
                new Long[] { 1564357L, 875643L },
                1834749274,
                3.14f,
                new TestComplexStructure(123),
                new TestComplexStructure[] { new TestComplexStructure(55), null, new TestComplexStructure(15) }
        );
        var exception = assertThrows(PacketValidationException.class, () -> PacketValidator.validate(packet));
        assertEquals(ValidationFailureCategory.INCORRECT_VALUE_LENGTH, exception.getFailureCategory());
        assertEquals("shortListByteArray", exception.getFieldName());
    }

    @Test
    void validate_withIncorrectValueInsideByteArray() {
        var packet = new ValidTestPacket(
                23,
                PacketType.NONE,
                54,
                null,
                true,
                new TestSimpleStructure(64),
                List.of((short) 1, (short) -15, (short) 3),
                354,
                new TestSimpleStructure(28715),
                TestCustomValueEnum.VALUE1,
                (short) 24567,
                "abcdefghij",
                112946L,
                new TestSimpleStructure(150685),
                new Long[] { 1564357L, 875643L },
                1834749274,
                3.14f,
                new TestComplexStructure(123),
                new TestComplexStructure[] { new TestComplexStructure(55), null, new TestComplexStructure(15) }
        );
        var exception = assertThrows(PacketValidationException.class, () -> PacketValidator.validate(packet));
        assertEquals(ValidationFailureCategory.VALUE_OUT_OF_RANGE, exception.getFailureCategory());
        assertEquals("shortListByteArray", exception.getFieldName());
    }

    @Test
    void validate_withTooLowIntWord() {
        var packet = new ValidTestPacket(
                23,
                PacketType.NONE,
                54,
                null,
                true,
                new TestSimpleStructure(64),
                List.of((short) 1, (short) 2, (short) 3),
                -15,
                new TestSimpleStructure(28715),
                TestCustomValueEnum.VALUE1,
                (short) 24567,
                "abcdefghij",
                112946L,
                new TestSimpleStructure(150685),
                new Long[] { 1564357L, 875643L },
                1834749274,
                3.14f,
                new TestComplexStructure(123),
                new TestComplexStructure[] { new TestComplexStructure(55), null, new TestComplexStructure(15) }
        );
        var exception = assertThrows(PacketValidationException.class, () -> PacketValidator.validate(packet));
        assertEquals(ValidationFailureCategory.VALUE_OUT_OF_RANGE, exception.getFailureCategory());
        assertEquals("intWord", exception.getFieldName());
    }

    @Test
    void validate_withTooHighIntWord() {
        var packet = new ValidTestPacket(
                23,
                PacketType.NONE,
                54,
                'a',
                false,
                new TestSimpleStructure(64),
                List.of((short) 1, (short) 2, (short) 3),
                74956,
                new TestSimpleStructure(28715),
                TestCustomValueEnum.VALUE1,
                (short) 24567,
                "abcdefghij",
                112946L,
                new TestSimpleStructure(150685),
                new Long[] { 1564357L, 875643L },
                1834749274,
                3.14f,
                new TestComplexStructure(123),
                new TestComplexStructure[] { new TestComplexStructure(55), null, new TestComplexStructure(15) }
        );
        var exception = assertThrows(PacketValidationException.class, () -> PacketValidator.validate(packet));
        assertEquals(ValidationFailureCategory.VALUE_OUT_OF_RANGE, exception.getFailureCategory());
        assertEquals("intWord", exception.getFieldName());
    }

    @Test
    void validate_withTooLowShort() {
        var packet = new ValidTestPacket(
                23,
                PacketType.NONE,
                54,
                null,
                true,
                new TestSimpleStructure(64),
                List.of((short) 1, (short) 2, (short) 3),
                354,
                new TestSimpleStructure(28715),
                TestCustomValueEnum.VALUE1,
                (short) -31780,
                "abcdefghij",
                112946L,
                new TestSimpleStructure(150685),
                new Long[] { 1564357L, 875643L },
                1834749274,
                3.14f,
                new TestComplexStructure(123),
                new TestComplexStructure[] { new TestComplexStructure(55), null, new TestComplexStructure(15) }
        );
        var exception = assertThrows(PacketValidationException.class, () -> PacketValidator.validate(packet));
        assertEquals(ValidationFailureCategory.VALUE_OUT_OF_RANGE, exception.getFailureCategory());
        assertEquals("shortShort", exception.getFieldName());
    }

    @Test
    void validate_withTooHighShort() {
        var packet = new ValidTestPacket(
                23,
                PacketType.NONE,
                54,
                null,
                true,
                new TestSimpleStructure(64),
                List.of((short) 1, (short) 2, (short) 3),
                354,
                new TestSimpleStructure(28715),
                TestCustomValueEnum.VALUE1,
                (short) 30367,
                "abcdefghij",
                112946L,
                new TestSimpleStructure(150685),
                new Long[] { 1564357L, 875643L },
                1834749274,
                3.14f,
                new TestComplexStructure(123),
                new TestComplexStructure[] { new TestComplexStructure(55), null, new TestComplexStructure(15) }
        );
        var exception = assertThrows(PacketValidationException.class, () -> PacketValidator.validate(packet));
        assertEquals(ValidationFailureCategory.VALUE_OUT_OF_RANGE, exception.getFailureCategory());
        assertEquals("shortShort", exception.getFieldName());
    }

    @Test
    void validate_withTooLowUnsigned() {
        var packet = new ValidTestPacket(
                23,
                PacketType.NONE,
                54,
                'a',
                false,
                new TestSimpleStructure(64),
                List.of((short) 1, (short) 2, (short) 3),
                354,
                new TestSimpleStructure(28715),
                TestCustomValueEnum.VALUE1,
                (short) 24567,
                "abcdefghij",
                -35L,
                new TestSimpleStructure(150685),
                new Long[] { 1564357L, 875643L },
                1834749274,
                3.14f,
                new TestComplexStructure(123),
                new TestComplexStructure[] { new TestComplexStructure(55), null, new TestComplexStructure(15) }
        );
        var exception = assertThrows(PacketValidationException.class, () -> PacketValidator.validate(packet));
        assertEquals(ValidationFailureCategory.VALUE_OUT_OF_RANGE, exception.getFailureCategory());
        assertEquals("longUnsigned", exception.getFieldName());
    }

    @Test
    void validate_withTooHighUnsigned() {
        var packet = new ValidTestPacket(
                23,
                PacketType.NONE,
                54,
                'a',
                false,
                new TestSimpleStructure(64),
                List.of((short) 1, (short) 2, (short) 3),
                354,
                new TestSimpleStructure(28715),
                TestCustomValueEnum.VALUE1,
                (short) 24567,
                "abcdefghij",
                5732985275L,
                new TestSimpleStructure(150685),
                new Long[] { 1564357L, 875643L },
                1834749274,
                3.14f,
                new TestComplexStructure(123),
                new TestComplexStructure[] { new TestComplexStructure(55), null, new TestComplexStructure(15) }
        );
        var exception = assertThrows(PacketValidationException.class, () -> PacketValidator.validate(packet));
        assertEquals(ValidationFailureCategory.VALUE_OUT_OF_RANGE, exception.getFailureCategory());
        assertEquals("longUnsigned", exception.getFieldName());
    }

    @Test
    void validate_withTooLongUnsignedArray() {
        var packet = new ValidTestPacket(
                23,
                PacketType.NONE,
                54,
                'a',
                false,
                new TestSimpleStructure(64),
                List.of((short) 1, (short) 2, (short) 3),
                354,
                new TestSimpleStructure(28715),
                TestCustomValueEnum.VALUE1,
                (short) 24567,
                "abcdefghij",
                112946L,
                new TestSimpleStructure(150685),
                new Long[] { 1564357L, 875643L, 65465L, 5465456L, 1434565L, 5436545L, 5676543L },
                1834749274,
                3.14f,
                new TestComplexStructure(123),
                new TestComplexStructure[] { new TestComplexStructure(55), null, new TestComplexStructure(15) }
        );
        var exception = assertThrows(PacketValidationException.class, () -> PacketValidator.validate(packet));
        assertEquals(ValidationFailureCategory.INCORRECT_VALUE_LENGTH, exception.getFailureCategory());
        assertEquals("longArrayUnsignedArrayDynamicLength", exception.getFieldName());
    }

    @Test
    void validate_withIncorrectValueInsideUnsignedArray() {
        var packet = new ValidTestPacket(
                23,
                PacketType.NONE,
                54,
                'a',
                false,
                new TestSimpleStructure(64),
                List.of((short) 1, (short) 2, (short) 3),
                354,
                new TestSimpleStructure(28715),
                TestCustomValueEnum.VALUE1,
                (short) 24567,
                "abcdefghij",
                112946L,
                new TestSimpleStructure(150685),
                new Long[] { 1564357L, -65465L },
                1834749274,
                3.14f,
                new TestComplexStructure(123),
                new TestComplexStructure[] { new TestComplexStructure(55), null, new TestComplexStructure(15) }
        );
        var exception = assertThrows(PacketValidationException.class, () -> PacketValidator.validate(packet));
        assertEquals(ValidationFailureCategory.VALUE_OUT_OF_RANGE, exception.getFailureCategory());
        assertEquals("longArrayUnsignedArrayDynamicLength", exception.getFieldName());
    }

    @Test
    void validate_withTooLowInt() {
        var packet = new ValidTestPacket(
                23,
                PacketType.NONE,
                54,
                'a',
                false,
                new TestSimpleStructure(64),
                List.of((short) 1, (short) 2, (short) 3),
                354,
                new TestSimpleStructure(28715),
                TestCustomValueEnum.VALUE1,
                (short) 24567,
                "abcdefghij",
                112946L,
                new TestSimpleStructure(150685),
                new Long[] { 1564357L, 875643L },
                -2134749274,
                3.14f,
                new TestComplexStructure(123),
                new TestComplexStructure[] { new TestComplexStructure(55), null, new TestComplexStructure(15) }
        );
        var exception = assertThrows(PacketValidationException.class, () -> PacketValidator.validate(packet));
        assertEquals(ValidationFailureCategory.VALUE_OUT_OF_RANGE, exception.getFailureCategory());
        assertEquals("intInt", exception.getFieldName());
    }

    @Test
    void validate_withTooHighInt() {
        var packet = new ValidTestPacket(
                23,
                PacketType.NONE,
                54,
                'a',
                false,
                new TestSimpleStructure(64),
                List.of((short) 1, (short) 2, (short) 3),
                354,
                new TestSimpleStructure(28715),
                TestCustomValueEnum.VALUE1,
                (short) 24567,
                "abcdefghij",
                112946L,
                new TestSimpleStructure(150685),
                new Long[] { 1564357L, 875643L },
                2134749274,
                3.14f,
                new TestComplexStructure(123),
                new TestComplexStructure[] { new TestComplexStructure(55), null, new TestComplexStructure(15) }
        );
        var exception = assertThrows(PacketValidationException.class, () -> PacketValidator.validate(packet));
        assertEquals(ValidationFailureCategory.VALUE_OUT_OF_RANGE, exception.getFailureCategory());
        assertEquals("intInt", exception.getFieldName());
    }

    @Test
    void validate_withTooLowFloat() {
        var packet = new ValidTestPacket(
                23,
                PacketType.NONE,
                54,
                null,
                true,
                new TestSimpleStructure(64),
                List.of((short) 1, (short) 2, (short) 3),
                354,
                new TestSimpleStructure(28715),
                TestCustomValueEnum.VALUE1,
                (short) 24567,
                "abcdefghij",
                112946L,
                new TestSimpleStructure(150685),
                new Long[] { 1564357L, 875643L },
                1834749274,
                -25.86f,
                new TestComplexStructure(123),
                new TestComplexStructure[] { new TestComplexStructure(55), null, new TestComplexStructure(15) }
        );
        var exception = assertThrows(PacketValidationException.class, () -> PacketValidator.validate(packet));
        assertEquals(ValidationFailureCategory.VALUE_OUT_OF_RANGE, exception.getFailureCategory());
        assertEquals("floatFloat", exception.getFieldName());
    }

    @Test
    void validate_withTooHighFloat() {
        var packet = new ValidTestPacket(
                23,
                PacketType.NONE,
                54,
                null,
                true,
                new TestSimpleStructure(64),
                List.of((short) 1, (short) 2, (short) 3),
                354,
                new TestSimpleStructure(28715),
                TestCustomValueEnum.VALUE1,
                (short) 24567,
                "abcdefghij",
                112946L,
                new TestSimpleStructure(150685),
                new Long[] { 1564357L, 875643L },
                1834749274,
                105.2f,
                new TestComplexStructure(123),
                new TestComplexStructure[] { new TestComplexStructure(55), null, new TestComplexStructure(15) }
        );
        var exception = assertThrows(PacketValidationException.class, () -> PacketValidator.validate(packet));
        assertEquals(ValidationFailureCategory.VALUE_OUT_OF_RANGE, exception.getFailureCategory());
        assertEquals("floatFloat", exception.getFieldName());
    }

    @Test
    void validate_withIncorrectStructureArrayLength() {
        var packet = new ValidTestPacket(
                23,
                PacketType.NONE,
                54,
                null,
                true,
                new TestSimpleStructure(64),
                List.of((short) 1, (short) 2, (short) 3),
                354,
                new TestSimpleStructure(28715),
                TestCustomValueEnum.VALUE1,
                (short) 24567,
                "abcdefghij",
                112946L,
                new TestSimpleStructure(150685),
                new Long[] { 1564357L, 875643L },
                1834749274,
                3.14f,
                new TestComplexStructure(123),
                new TestComplexStructure[] { new TestComplexStructure(55), null }
        );
        var exception = assertThrows(PacketValidationException.class, () -> PacketValidator.validate(packet));
        assertEquals(ValidationFailureCategory.INCORRECT_VALUE_LENGTH, exception.getFailureCategory());
        assertEquals("structureArray", exception.getFieldName());
    }

    @Test
    void validate_withInvalidValueInStructureArray() {
        var packet = new ValidTestPacket(
                23,
                PacketType.NONE,
                54,
                null,
                true,
                new TestSimpleStructure(64),
                List.of((short) 1, (short) 2, (short) 3),
                354,
                new TestSimpleStructure(28715),
                TestCustomValueEnum.VALUE1,
                (short) 24567,
                "abcdefghij",
                112946L,
                new TestSimpleStructure(150685),
                new Long[] { 1564357L, 875643L },
                1834749274,
                3.14f,
                new TestComplexStructure(123),
                new TestComplexStructure[] { new TestComplexStructure(55), null, new TestComplexStructure(-15) }
        );
        var exception = assertThrows(PacketValidationException.class, () -> PacketValidator.validate(packet));
        assertEquals(ValidationFailureCategory.VALUE_OUT_OF_RANGE, exception.getFailureCategory());
        assertEquals("structureField", exception.getFieldName());
    }

    @Test
    void validate_packetWithFieldWithoutAnnotation() {
        var packet = new PacketWithFieldWithoutAnnotation(
                8,
                PacketType.NONE,
                54,
                15
        );
        var exception = assertThrows(PacketValidationException.class, () -> PacketValidator.validate(packet));
        assertEquals(ValidationFailureCategory.INCORRECT_TYPE_ANNOTATION, exception.getFailureCategory());
        assertEquals("fieldWithNoAnnotation", exception.getFieldName());
    }

    @Test
    void validate_packetWithInvalidByteType() {
        var packet = new PacketWithInvalidByteType(
                8,
                PacketType.NONE,
                54,
                "15"
        );
        var exception = assertThrows(PacketValidationException.class, () -> PacketValidator.validate(packet));
        assertEquals(ValidationFailureCategory.UNSUPPORTED_TYPE, exception.getFailureCategory());
        assertEquals("stringByte", exception.getFieldName());
    }

    @Test
    void validate_packetWithInvalidWordType() {
        var packet = new PacketWithInvalidWordType(
                8,
                PacketType.NONE,
                54,
                "15"
        );
        var exception = assertThrows(PacketValidationException.class, () -> PacketValidator.validate(packet));
        assertEquals(ValidationFailureCategory.UNSUPPORTED_TYPE, exception.getFailureCategory());
        assertEquals("stringWord", exception.getFieldName());
    }

    @Test
    void validate_packetWithInvalidShortType() {
        var packet = new PacketWithInvalidShortType(
                8,
                PacketType.NONE,
                54,
                "15"
        );
        var exception = assertThrows(PacketValidationException.class, () -> PacketValidator.validate(packet));
        assertEquals(ValidationFailureCategory.UNSUPPORTED_TYPE, exception.getFailureCategory());
        assertEquals("stringShort", exception.getFieldName());
    }

    @Test
    void validate_packetWithInvalidCharArrayType() {
        var packet = new PacketWithInvalidCharArrayType(
                8,
                PacketType.NONE,
                54,
                15
        );
        var exception = assertThrows(PacketValidationException.class, () -> PacketValidator.validate(packet));
        assertEquals(ValidationFailureCategory.UNSUPPORTED_TYPE, exception.getFailureCategory());
        assertEquals("intCharArray", exception.getFieldName());
    }

    @Test
    void validate_packetWithMissingArrayAnnotationForChar() {
        var packet = new PacketWithMissingArrayAnnotationForChar(
                8,
                PacketType.NONE,
                54,
                "15"
        );
        var exception = assertThrows(PacketValidationException.class, () -> PacketValidator.validate(packet));
        assertEquals(ValidationFailureCategory.INCORRECT_TYPE_ANNOTATION, exception.getFailureCategory());
        assertEquals("stringChar", exception.getFieldName());
    }

    @Test
    void validate_packetWithInvalidUnsignedType() {
        var packet = new PacketWithInvalidUnsignedType(
                8,
                PacketType.NONE,
                54,
                "15"
        );
        var exception = assertThrows(PacketValidationException.class, () -> PacketValidator.validate(packet));
        assertEquals(ValidationFailureCategory.UNSUPPORTED_TYPE, exception.getFailureCategory());
        assertEquals("stringUnsigned", exception.getFieldName());
    }

    @Test
    void validate_packetWithInvalidIntType() {
        var packet = new PacketWithInvalidIntType(
                8,
                PacketType.NONE,
                54,
                "15"
        );
        var exception = assertThrows(PacketValidationException.class, () -> PacketValidator.validate(packet));
        assertEquals(ValidationFailureCategory.UNSUPPORTED_TYPE, exception.getFailureCategory());
        assertEquals("stringInt", exception.getFieldName());
    }

    @Test
    void validate_packetWithInvalidFloatType() {
        var packet = new PacketWithInvalidFloatType(
                8,
                PacketType.NONE,
                54,
                "15"
        );
        var exception = assertThrows(PacketValidationException.class, () -> PacketValidator.validate(packet));
        assertEquals(ValidationFailureCategory.UNSUPPORTED_TYPE, exception.getFailureCategory());
        assertEquals("stringFloat", exception.getFieldName());
    }

    @Test
    void validate_packetWithInvalidStructureArrayType() {
        var packet = new PacketWithInvalidStructureArrayType(
                8,
                PacketType.NONE,
                54,
                "15"
        );
        var exception = assertThrows(PacketValidationException.class, () -> PacketValidator.validate(packet));
        assertEquals(ValidationFailureCategory.UNSUPPORTED_TYPE, exception.getFailureCategory());
        assertEquals("stringStructureArray", exception.getFieldName());
    }

    @Test
    void validate_packetWithInvalidStructureType() {
        var packet = new PacketWithInvalidStructureType(
                8,
                PacketType.NONE,
                54,
                "abc"
        );
        var exception = assertThrows(PacketValidationException.class, () -> PacketValidator.validate(packet));
        assertEquals(ValidationFailureCategory.UNSUPPORTED_TYPE, exception.getFailureCategory());
        assertEquals("stringStructure", exception.getFieldName());
    }

    @SuppressWarnings("all")
    private static class ValidTestPacket extends AbstractPacket implements InstructionPacket {
        @Byte
        private final Character characterByte;
        @Byte
        private final boolean booleanByte;
        @Byte
        private final TestSimpleStructure simpleStructureByte;
        @Byte
        @Array(length = 3)
        private final List<java.lang.Short> shortListByteArray;
        @Word
        private final int intWord;
        @Word
        private final TestSimpleStructure simpleStructureWord;
        @Word
        private final TestCustomValueEnum customValueEnumWord;
        @Short(minValue = -30000, maxValue = 30000)
        private final short shortShort;
        @Char
        @Array(length = 5)
        private final String stringCharArray;
        @Unsigned
        private final long longUnsigned;
        @Unsigned
        private final TestSimpleStructure simpleStructureUnsigned;
        @Unsigned
        @Array(length = 5, dynamicLength = true)
        private final Long[] longArrayUnsignedArrayDynamicLength;
        @Int(minValue = -2000000000, maxValue = 2000000000)
        private final int intInt;
        @Float(minValue = -5.0f, maxValue = 15.0f)
        private final float floatFloat;
        @Structure
        private final TestComplexStructure structure;
        @Structure
        @Array(length = 3)
        private final TestComplexStructure[] structureArray;

        protected ValidTestPacket(int size,
                                  PacketType type,
                                  int reqI,
                                  Character characterByte,
                                  boolean booleanByte,
                                  TestSimpleStructure simpleStructureByte,
                                  List<java.lang.Short> shortListByteArray,
                                  int intWord,
                                  TestSimpleStructure simpleStructureWord,
                                  TestCustomValueEnum customValueEnumWord,
                                  short shortShort,
                                  String stringCharArray,
                                  long longUnsigned,
                                  TestSimpleStructure simpleStructureUnsigned,
                                  Long[] longArrayUnsignedArrayDynamicLength,
                                  int intInt,
                                  float floatFloat,
                                  TestComplexStructure structure,
                                  TestComplexStructure[] structureArray) {
            super(size, type, reqI);
            this.characterByte = characterByte;
            this.booleanByte = booleanByte;
            this.simpleStructureByte = simpleStructureByte;
            this.shortListByteArray = shortListByteArray;
            this.intWord = intWord;
            this.simpleStructureWord = simpleStructureWord;
            this.customValueEnumWord = customValueEnumWord;
            this.shortShort = shortShort;
            this.stringCharArray = stringCharArray;
            this.longUnsigned = longUnsigned;
            this.simpleStructureUnsigned = simpleStructureUnsigned;
            this.longArrayUnsignedArrayDynamicLength = longArrayUnsignedArrayDynamicLength;
            this.intInt = intInt;
            this.floatFloat = floatFloat;
            this.structure = structure;
            this.structureArray = structureArray;
        }

        @Override
        public byte[] getBytes() {
            return new byte[0];
        }
    }

    @SuppressWarnings("all")
    private static class PacketWithFieldWithoutAnnotation extends AbstractPacket implements InstructionPacket {
        private final int fieldWithNoAnnotation;

        protected PacketWithFieldWithoutAnnotation(int size,
                                                   PacketType type,
                                                   int reqI,
                                                   int fieldWithNoAnnotation) {
            super(size, type, reqI);
            this.fieldWithNoAnnotation = fieldWithNoAnnotation;
        }

        @Override
        public byte[] getBytes() {
            return new byte[0];
        }
    }

    @SuppressWarnings("all")
    private static class PacketWithInvalidByteType extends AbstractPacket implements InstructionPacket {

        @Byte
        private final String stringByte;

        protected PacketWithInvalidByteType(int size,
                                            PacketType type,
                                            int reqI,
                                            String stringByte) {
            super(size, type, reqI);
            this.stringByte = stringByte;
        }

        @Override
        public byte[] getBytes() {
            return new byte[0];
        }
    }

    @SuppressWarnings("all")
    private static class PacketWithInvalidWordType extends AbstractPacket implements InstructionPacket {

        @Word
        private final String stringWord;

        protected PacketWithInvalidWordType(int size,
                                            PacketType type,
                                            int reqI,
                                            String stringWord) {
            super(size, type, reqI);
            this.stringWord = stringWord;
        }

        @Override
        public byte[] getBytes() {
            return new byte[0];
        }
    }

    @SuppressWarnings("all")
    private static class PacketWithInvalidShortType extends AbstractPacket implements InstructionPacket {

        @Short
        private final String stringShort;

        protected PacketWithInvalidShortType(int size,
                                             PacketType type,
                                             int reqI,
                                             String stringShort) {
            super(size, type, reqI);
            this.stringShort = stringShort;
        }

        @Override
        public byte[] getBytes() {
            return new byte[0];
        }
    }

    @SuppressWarnings("all")
    private static class PacketWithInvalidCharArrayType extends AbstractPacket implements InstructionPacket {

        @Char
        @Array(length = 5)
        private final int intCharArray;

        protected PacketWithInvalidCharArrayType(int size,
                                                 PacketType type,
                                                 int reqI,
                                                 int intCharArray) {
            super(size, type, reqI);
            this.intCharArray = intCharArray;
        }

        @Override
        public byte[] getBytes() {
            return new byte[0];
        }
    }

    @SuppressWarnings("all")
    private static class PacketWithMissingArrayAnnotationForChar extends AbstractPacket implements InstructionPacket {

        @Char
        private final String stringChar;

        protected PacketWithMissingArrayAnnotationForChar(int size,
                                                          PacketType type,
                                                          int reqI,
                                                          String stringChar) {
            super(size, type, reqI);
            this.stringChar = stringChar;
        }

        @Override
        public byte[] getBytes() {
            return new byte[0];
        }
    }

    @SuppressWarnings("all")
    private static class PacketWithInvalidUnsignedType extends AbstractPacket implements InstructionPacket {

        @Unsigned
        private final String stringUnsigned;

        protected PacketWithInvalidUnsignedType(int size,
                                                 PacketType type,
                                                 int reqI,
                                                 String stringUnsigned) {
            super(size, type, reqI);
            this.stringUnsigned = stringUnsigned;
        }

        @Override
        public byte[] getBytes() {
            return new byte[0];
        }
    }

    @SuppressWarnings("all")
    private static class PacketWithInvalidIntType extends AbstractPacket implements InstructionPacket {

        @Int
        private final String stringInt;

        protected PacketWithInvalidIntType(int size,
                                                PacketType type,
                                                int reqI,
                                                String stringInt) {
            super(size, type, reqI);
            this.stringInt = stringInt;
        }

        @Override
        public byte[] getBytes() {
            return new byte[0];
        }
    }

    @SuppressWarnings("all")
    private static class PacketWithInvalidFloatType extends AbstractPacket implements InstructionPacket {

        @Float
        private final String stringFloat;

        protected PacketWithInvalidFloatType(int size,
                                             PacketType type,
                                             int reqI,
                                             String stringFloat) {
            super(size, type, reqI);
            this.stringFloat = stringFloat;
        }

        @Override
        public byte[] getBytes() {
            return new byte[0];
        }
    }

    @SuppressWarnings("all")
    private static class PacketWithInvalidStructureArrayType extends AbstractPacket implements InstructionPacket {

        @Structure
        @Array(length = 2)
        private final String stringStructureArray;

        protected PacketWithInvalidStructureArrayType(int size,
                                                      PacketType type,
                                                      int reqI,
                                                      String stringStructureArray) {
            super(size, type, reqI);
            this.stringStructureArray = stringStructureArray;
        }

        @Override
        public byte[] getBytes() {
            return new byte[0];
        }
    }

    @SuppressWarnings("all")
    private static class PacketWithInvalidStructureType extends AbstractPacket implements InstructionPacket {

        @Structure
        private final String stringStructure;

        protected PacketWithInvalidStructureType(int size,
                                                 PacketType type,
                                                 int reqI,
                                                 String stringStructure) {
            super(size, type, reqI);
            this.stringStructure = stringStructure;
        }

        @Override
        public byte[] getBytes() {
            return new byte[0];
        }
    }

    private enum TestCustomValueEnum implements EnumWithCustomValue {
        VALUE1, VALUE2, VALUE3;

        @Override
        public int getValue() {
            return 0;
        }
    }

    private record TestSimpleStructure(
            long value
    ) implements ByteInstructionStructure, WordInstructionStructure, UnsignedInstructionStructure {

        @Override
            public short getByteValue() {
                return (short) value;
            }

            @Override
            public int getWordValue() {
                return (int) value;
            }

            @Override
            public long getUnsignedValue() {
                return value;
            }
        }

    @SuppressWarnings("all")
    private static class TestComplexStructure implements ComplexInstructionStructure {
        @Byte
        private final short structureField;

        private TestComplexStructure(int structureField) {
            this.structureField = (short) structureField;
        }

        @Override
        public void appendBytes(PacketBuilder packetBuilder) { }
    }
}
