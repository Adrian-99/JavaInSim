package pl.adrian.internal.packets.util;

import org.junit.jupiter.api.Test;
import pl.adrian.internal.packets.annotations.*;
import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.base.Packet;
import pl.adrian.internal.packets.base.InstructionPacket;
import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.internal.packets.enums.EnumWithCustomValue;
import pl.adrian.internal.packets.enums.ValidationFailureCategory;
import pl.adrian.internal.packets.exceptions.PacketValidationException;
import pl.adrian.internal.packets.structures.base.ByteInstructionStructure;
import pl.adrian.internal.packets.structures.base.ComplexInstructionStructure;
import pl.adrian.internal.packets.structures.base.UnsignedInstructionStructure;
import pl.adrian.internal.packets.structures.base.WordInstructionStructure;

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
                354,
                new TestSimpleStructure(28715),
                TestCustomValueEnum.VALUE1,
                "abcdefghij",
                112946L,
                new TestSimpleStructure(150685),
                new Long[] { 1564357L, 875643L },
                1834749274,
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
                354,
                new TestSimpleStructure(28715),
                TestCustomValueEnum.VALUE1,
                "abcdefghij",
                112946L,
                new TestSimpleStructure(150685),
                new Long[] { 1564357L, 875643L },
                1834749274,
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
                354,
                new TestSimpleStructure(28715),
                TestCustomValueEnum.VALUE1,
                "abcdefghij",
                112946L,
                new TestSimpleStructure(150685),
                new Long[] { 1564357L, 875643L },
                1834749274,
                new TestComplexStructure[] { new TestComplexStructure(55), null, new TestComplexStructure(15) }
        );
        var exception = assertThrows(PacketValidationException.class, () -> PacketValidator.validate(packet));
        assertEquals(ValidationFailureCategory.VALUE_OUT_OF_RANGE, exception.getFailureCategory());
        assertEquals("reqI", exception.getFieldName());
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
                -15,
                new TestSimpleStructure(28715),
                TestCustomValueEnum.VALUE1,
                "abcdefghij",
                112946L,
                new TestSimpleStructure(150685),
                new Long[] { 1564357L, 875643L },
                1834749274,
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
                74956,
                new TestSimpleStructure(28715),
                TestCustomValueEnum.VALUE1,
                "abcdefghij",
                112946L,
                new TestSimpleStructure(150685),
                new Long[] { 1564357L, 875643L },
                1834749274,
                new TestComplexStructure[] { new TestComplexStructure(55), null, new TestComplexStructure(15) }
        );
        var exception = assertThrows(PacketValidationException.class, () -> PacketValidator.validate(packet));
        assertEquals(ValidationFailureCategory.VALUE_OUT_OF_RANGE, exception.getFailureCategory());
        assertEquals("intWord", exception.getFieldName());
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
                354,
                new TestSimpleStructure(28715),
                TestCustomValueEnum.VALUE1,
                "abcdefghij",
                -35L,
                new TestSimpleStructure(150685),
                new Long[] { 1564357L, 875643L },
                1834749274,
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
                354,
                new TestSimpleStructure(28715),
                TestCustomValueEnum.VALUE1,
                "abcdefghij",
                5732985275L,
                new TestSimpleStructure(150685),
                new Long[] { 1564357L, 875643L },
                1834749274,
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
                354,
                new TestSimpleStructure(28715),
                TestCustomValueEnum.VALUE1,
                "abcdefghij",
                112946L,
                new TestSimpleStructure(150685),
                new Long[] { 1564357L, 875643L, 65465L, 5465456L, 1434565L, 5436545L, 5676543L },
                1834749274,
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
                354,
                new TestSimpleStructure(28715),
                TestCustomValueEnum.VALUE1,
                "abcdefghij",
                112946L,
                new TestSimpleStructure(150685),
                new Long[] { 1564357L, -65465L },
                1834749274,
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
                354,
                new TestSimpleStructure(28715),
                TestCustomValueEnum.VALUE1,
                "abcdefghij",
                112946L,
                new TestSimpleStructure(150685),
                new Long[] { 1564357L, 875643L },
                -2134749274,
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
                354,
                new TestSimpleStructure(28715),
                TestCustomValueEnum.VALUE1,
                "abcdefghij",
                112946L,
                new TestSimpleStructure(150685),
                new Long[] { 1564357L, 875643L },
                2134749274,
                new TestComplexStructure[] { new TestComplexStructure(55), null, new TestComplexStructure(15) }
        );
        var exception = assertThrows(PacketValidationException.class, () -> PacketValidator.validate(packet));
        assertEquals(ValidationFailureCategory.VALUE_OUT_OF_RANGE, exception.getFailureCategory());
        assertEquals("intInt", exception.getFieldName());
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
                354,
                new TestSimpleStructure(28715),
                TestCustomValueEnum.VALUE1,
                "abcdefghij",
                112946L,
                new TestSimpleStructure(150685),
                new Long[] { 1564357L, 875643L },
                1834749274,
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
                354,
                new TestSimpleStructure(28715),
                TestCustomValueEnum.VALUE1,
                "abcdefghij",
                112946L,
                new TestSimpleStructure(150685),
                new Long[] { 1564357L, 875643L },
                1834749274,
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
    void validate_packetWithMissingArrayAnnotationForStructure() {
        var packet = new PacketWithMissingArrayAnnotationForStructure(
                8,
                PacketType.NONE,
                54,
                new TestSimpleStructure(5)
        );
        var exception = assertThrows(PacketValidationException.class, () -> PacketValidator.validate(packet));
        assertEquals(ValidationFailureCategory.INCORRECT_TYPE_ANNOTATION, exception.getFailureCategory());
        assertEquals("simpleStructure", exception.getFieldName());
    }

    @Test
    void validate_packetWithInvalidStructureType() {
        var packet = new PacketWithInvalidStructureType(
                8,
                PacketType.NONE,
                54,
                new String[] { "abc", "def" }
        );
        var exception = assertThrows(PacketValidationException.class, () -> PacketValidator.validate(packet));
        assertEquals(ValidationFailureCategory.UNSUPPORTED_TYPE, exception.getFailureCategory());
        assertEquals("stringStructureArray", exception.getFieldName());
    }

    @SuppressWarnings("all")
    private static class ValidTestPacket extends Packet implements InstructionPacket {
        @Byte
        private final Character characterByte;
        @Byte
        private final boolean booleanByte;
        @Byte
        private final TestSimpleStructure simpleStructureByte;
        @Word
        private final int intWord;
        @Word
        private final TestSimpleStructure simpleStructureWord;
        @Word
        private final TestCustomValueEnum customValueEnumWord;
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
        @Structure
        @Array(length = 3)
        private final TestComplexStructure[] structureArray;

        protected ValidTestPacket(int size,
                                  PacketType type,
                                  int reqI,
                                  Character characterByte,
                                  boolean booleanByte,
                                  TestSimpleStructure simpleStructureByte,
                                  int intWord,
                                  TestSimpleStructure simpleStructureWord,
                                  TestCustomValueEnum customValueEnumWord,
                                  String stringCharArray,
                                  long longUnsigned,
                                  TestSimpleStructure simpleStructureUnsigned,
                                  Long[] longArrayUnsignedArrayDynamicLength,
                                  int intInt,
                                  TestComplexStructure[] structureArray) {
            super(size, type, reqI);
            this.characterByte = characterByte;
            this.booleanByte = booleanByte;
            this.simpleStructureByte = simpleStructureByte;
            this.intWord = intWord;
            this.simpleStructureWord = simpleStructureWord;
            this.customValueEnumWord = customValueEnumWord;
            this.stringCharArray = stringCharArray;
            this.longUnsigned = longUnsigned;
            this.simpleStructureUnsigned = simpleStructureUnsigned;
            this.longArrayUnsignedArrayDynamicLength = longArrayUnsignedArrayDynamicLength;
            this.intInt = intInt;
            this.structureArray = structureArray;
        }

        @Override
        public byte[] getBytes() {
            return new byte[0];
        }
    }

    @SuppressWarnings("all")
    private static class PacketWithFieldWithoutAnnotation extends Packet implements InstructionPacket {
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
    private static class PacketWithInvalidByteType extends Packet implements InstructionPacket {

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
    private static class PacketWithInvalidWordType extends Packet implements InstructionPacket {

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
    private static class PacketWithInvalidCharArrayType extends Packet implements InstructionPacket {

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
    private static class PacketWithMissingArrayAnnotationForChar extends Packet implements InstructionPacket {

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
    private static class PacketWithInvalidUnsignedType extends Packet implements InstructionPacket {

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
    private static class PacketWithInvalidIntType extends Packet implements InstructionPacket {

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
    private static class PacketWithInvalidStructureArrayType extends Packet implements InstructionPacket {

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
    private static class PacketWithMissingArrayAnnotationForStructure extends Packet implements InstructionPacket {

        @Structure
        private final TestSimpleStructure simpleStructure;

        protected PacketWithMissingArrayAnnotationForStructure(int size,
                                                               PacketType type,
                                                               int reqI,
                                                               TestSimpleStructure simpleStructure) {
            super(size, type, reqI);
            this.simpleStructure = simpleStructure;
        }

        @Override
        public byte[] getBytes() {
            return new byte[0];
        }
    }

    @SuppressWarnings("all")
    private static class PacketWithInvalidStructureType extends Packet implements InstructionPacket {

        @Structure
        @Array(length = 2)
        private final String[] stringStructureArray;

        protected PacketWithInvalidStructureType(int size,
                                                 PacketType type,
                                                 int reqI,
                                                 String[] stringStructureArray) {
            super(size, type, reqI);
            this.stringStructureArray = stringStructureArray;
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
        public void appendBytes(PacketBuilder packetBuilder) {

        }
    }
}
