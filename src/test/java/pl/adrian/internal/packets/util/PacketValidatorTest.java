package pl.adrian.internal.packets.util;

import org.junit.jupiter.api.Test;
import pl.adrian.internal.packets.annotations.*;
import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.base.Packet;
import pl.adrian.internal.packets.base.InstructionPacket;
import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.internal.packets.enums.EnumWithCustomValue;
import pl.adrian.internal.packets.exceptions.PacketValidationException;
import pl.adrian.api.packets.flags.Flags;
import pl.adrian.internal.packets.structures.InstructionStructure;

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
                new Flags<>(TestEnum.VALUE2),
                354,
                new Flags<>(TestEnum.VALUE1),
                TestCustomValueEnum.VALUE1,
                "abcdefghij",
                null,
                112946L,
                new Flags<>(TestEnum.VALUE1, TestEnum.VALUE3),
                1834749274,
                new TestStructure[] { new TestStructure(55), null, new TestStructure(15) }
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
                new Flags<>(TestEnum.VALUE2),
                354,
                new Flags<>(TestEnum.VALUE1),
                TestCustomValueEnum.VALUE1,
                "abcdefghij",
                null,
                112946L,
                new Flags<>(TestEnum.VALUE1, TestEnum.VALUE3),
                1834749274,
                new TestStructure[] { new TestStructure(55), null, new TestStructure(15) }
        );
        var exception = assertThrows(PacketValidationException.class, () -> PacketValidator.validate(packet));
        assertEquals(PacketValidationException.FailureReason.VALUE_OUT_OF_RANGE, exception.getFailureReason());
        assertEquals("reqI", exception.getField().getName());
    }

    @Test
    void validate_withTooHighShortByte() {
        var packet = new ValidTestPacket(
                23,
                PacketType.NONE,
                350,
                null,
                true,
                new Flags<>(TestEnum.VALUE2),
                354,
                new Flags<>(TestEnum.VALUE1),
                TestCustomValueEnum.VALUE1,
                "abcdefghij",
                null,
                112946L,
                new Flags<>(TestEnum.VALUE1, TestEnum.VALUE3),
                1834749274,
                new TestStructure[] { new TestStructure(55), null, new TestStructure(15) }
        );
        var exception = assertThrows(PacketValidationException.class, () -> PacketValidator.validate(packet));
        assertEquals(PacketValidationException.FailureReason.VALUE_OUT_OF_RANGE, exception.getFailureReason());
        assertEquals("reqI", exception.getField().getName());
    }

    @Test
    void validate_withTooLowIntWord() {
        var packet = new ValidTestPacket(
                23,
                PacketType.NONE,
                54,
                null,
                true,
                new Flags<>(TestEnum.VALUE2),
                -15,
                new Flags<>(TestEnum.VALUE1),
                TestCustomValueEnum.VALUE1,
                "abcdefghij",
                null,
                112946L,
                new Flags<>(TestEnum.VALUE1, TestEnum.VALUE3),
                1834749274,
                new TestStructure[] { new TestStructure(55), null, new TestStructure(15) }
        );
        var exception = assertThrows(PacketValidationException.class, () -> PacketValidator.validate(packet));
        assertEquals(PacketValidationException.FailureReason.VALUE_OUT_OF_RANGE, exception.getFailureReason());
        assertEquals("intWord", exception.getField().getName());
    }

    @Test
    void validate_withTooHighIntWord() {
        var packet = new ValidTestPacket(
                23,
                PacketType.NONE,
                54,
                'a',
                false,
                new Flags<>(TestEnum.VALUE2),
                74956,
                new Flags<>(TestEnum.VALUE1),
                TestCustomValueEnum.VALUE1,
                "abcdefghij",
                null,
                112946L,
                new Flags<>(TestEnum.VALUE1, TestEnum.VALUE3),
                1834749274,
                new TestStructure[] { new TestStructure(55), null, new TestStructure(15) }
        );
        var exception = assertThrows(PacketValidationException.class, () -> PacketValidator.validate(packet));
        assertEquals(PacketValidationException.FailureReason.VALUE_OUT_OF_RANGE, exception.getFailureReason());
        assertEquals("intWord", exception.getField().getName());
    }

    @Test
    void validate_withTooLongStringCharArray() {
        var packet = new ValidTestPacket(
                23,
                PacketType.NONE,
                54,
                'a',
                false,
                new Flags<>(TestEnum.VALUE2),
                354,
                new Flags<>(TestEnum.VALUE1),
                TestCustomValueEnum.VALUE1,
                "abcdefghij",
                "abcdefghij",
                112946L,
                new Flags<>(TestEnum.VALUE1, TestEnum.VALUE3),
                1834749274,
                new TestStructure[] { new TestStructure(55), null, new TestStructure(15) }
        );
        var exception = assertThrows(PacketValidationException.class, () -> PacketValidator.validate(packet));
        assertEquals(PacketValidationException.FailureReason.INCORRECT_VALUE_LENGTH, exception.getFailureReason());
        assertEquals("stringCharArray", exception.getField().getName());
    }

    @Test
    void validate_withTooLowUnsigned() {
        var packet = new ValidTestPacket(
                23,
                PacketType.NONE,
                54,
                'a',
                false,
                new Flags<>(TestEnum.VALUE2),
                354,
                new Flags<>(TestEnum.VALUE1),
                TestCustomValueEnum.VALUE1,
                "abcdefghij",
                "abc",
                -35L,
                new Flags<>(TestEnum.VALUE1, TestEnum.VALUE3),
                1834749274,
                new TestStructure[] { new TestStructure(55), null, new TestStructure(15) }
        );
        var exception = assertThrows(PacketValidationException.class, () -> PacketValidator.validate(packet));
        assertEquals(PacketValidationException.FailureReason.VALUE_OUT_OF_RANGE, exception.getFailureReason());
        assertEquals("longUnsigned", exception.getField().getName());
    }

    @Test
    void validate_withTooHighUnsigned() {
        var packet = new ValidTestPacket(
                23,
                PacketType.NONE,
                54,
                'a',
                false,
                new Flags<>(TestEnum.VALUE2),
                354,
                new Flags<>(TestEnum.VALUE1),
                TestCustomValueEnum.VALUE1,
                "abcdefghij",
                "abc",
                5732985275L,
                new Flags<>(TestEnum.VALUE1, TestEnum.VALUE3),
                1834749274,
                new TestStructure[] { new TestStructure(55), null, new TestStructure(15) }
        );
        var exception = assertThrows(PacketValidationException.class, () -> PacketValidator.validate(packet));
        assertEquals(PacketValidationException.FailureReason.VALUE_OUT_OF_RANGE, exception.getFailureReason());
        assertEquals("longUnsigned", exception.getField().getName());
    }

    @Test
    void validate_withTooLowInt() {
        var packet = new ValidTestPacket(
                23,
                PacketType.NONE,
                54,
                'a',
                false,
                new Flags<>(TestEnum.VALUE2),
                354,
                new Flags<>(TestEnum.VALUE1),
                TestCustomValueEnum.VALUE1,
                "abcdefghij",
                "abc",
                112946L,
                new Flags<>(TestEnum.VALUE1, TestEnum.VALUE3),
                -2134749274,
                new TestStructure[] { new TestStructure(55), null, new TestStructure(15) }
        );
        var exception = assertThrows(PacketValidationException.class, () -> PacketValidator.validate(packet));
        assertEquals(PacketValidationException.FailureReason.VALUE_OUT_OF_RANGE, exception.getFailureReason());
        assertEquals("intInt", exception.getField().getName());
    }

    @Test
    void validate_withTooHighInt() {
        var packet = new ValidTestPacket(
                23,
                PacketType.NONE,
                54,
                'a',
                false,
                new Flags<>(TestEnum.VALUE2),
                354,
                new Flags<>(TestEnum.VALUE1),
                TestCustomValueEnum.VALUE1,
                "abcdefghij",
                "abc",
                112946L,
                new Flags<>(TestEnum.VALUE1, TestEnum.VALUE3),
                2134749274,
                new TestStructure[] { new TestStructure(55), null, new TestStructure(15) }
        );
        var exception = assertThrows(PacketValidationException.class, () -> PacketValidator.validate(packet));
        assertEquals(PacketValidationException.FailureReason.VALUE_OUT_OF_RANGE, exception.getFailureReason());
        assertEquals("intInt", exception.getField().getName());
    }

    @Test
    void validate_withIncorrectStructureArrayLength() {
        var packet = new ValidTestPacket(
                23,
                PacketType.NONE,
                54,
                null,
                true,
                new Flags<>(TestEnum.VALUE2),
                354,
                new Flags<>(TestEnum.VALUE1),
                TestCustomValueEnum.VALUE1,
                "abcdefghij",
                null,
                112946L,
                new Flags<>(TestEnum.VALUE1, TestEnum.VALUE3),
                1834749274,
                new TestStructure[] { new TestStructure(55), null }
        );
        var exception = assertThrows(PacketValidationException.class, () -> PacketValidator.validate(packet));
        assertEquals(PacketValidationException.FailureReason.INCORRECT_VALUE_LENGTH, exception.getFailureReason());
        assertEquals("structureArray", exception.getField().getName());
    }

    @Test
    void validate_withInvalidValueInStructureArray() {
        var packet = new ValidTestPacket(
                23,
                PacketType.NONE,
                54,
                null,
                true,
                new Flags<>(TestEnum.VALUE2),
                354,
                new Flags<>(TestEnum.VALUE1),
                TestCustomValueEnum.VALUE1,
                "abcdefghij",
                null,
                112946L,
                new Flags<>(TestEnum.VALUE1, TestEnum.VALUE3),
                1834749274,
                new TestStructure[] { new TestStructure(55), null, new TestStructure(-15) }
        );
        var exception = assertThrows(PacketValidationException.class, () -> PacketValidator.validate(packet));
        assertEquals(PacketValidationException.FailureReason.VALUE_OUT_OF_RANGE, exception.getFailureReason());
        assertEquals("structureField", exception.getField().getName());
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
        assertEquals(PacketValidationException.FailureReason.MISSING_TYPE_ANNOTATION, exception.getFailureReason());
        assertEquals("fieldWithNoAnnotation", exception.getField().getName());
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
        assertEquals(PacketValidationException.FailureReason.UNSUPPORTED_TYPE, exception.getFailureReason());
        assertEquals("stringByte", exception.getField().getName());
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
        assertEquals(PacketValidationException.FailureReason.UNSUPPORTED_TYPE, exception.getFailureReason());
        assertEquals("stringWord", exception.getField().getName());
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
        assertEquals(PacketValidationException.FailureReason.UNSUPPORTED_TYPE, exception.getFailureReason());
        assertEquals("intCharArray", exception.getField().getName());
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
        assertEquals(PacketValidationException.FailureReason.UNSUPPORTED_TYPE, exception.getFailureReason());
        assertEquals("stringUnsigned", exception.getField().getName());
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
        assertEquals(PacketValidationException.FailureReason.UNSUPPORTED_TYPE, exception.getFailureReason());
        assertEquals("stringInt", exception.getField().getName());
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
        assertEquals(PacketValidationException.FailureReason.UNSUPPORTED_TYPE, exception.getFailureReason());
        assertEquals("stringStructureArray", exception.getField().getName());
    }

    @SuppressWarnings("all")
    private static class ValidTestPacket extends Packet implements InstructionPacket {
        @Byte
        private final Character characterByte;
        @Byte
        private final boolean booleanByte;
        @Byte
        private final Flags<TestEnum> flagsByte;
        @Word
        private final int intWord;
        @Word
        private final Flags<TestEnum> flagsWord;
        @Word
        private final TestCustomValueEnum customValieEnumWord;
        @CharArray(length = 5)
        private final String stringCharArrayWithoutValidation;
        @CharArray(length = 5, strictLengthValidation = true)
        private final String stringCharArray;
        @Unsigned
        private final long longUnsigned;
        @Unsigned
        private final Flags<TestEnum> flagsUnsigned;
        @Int(minValue = -2000000000, maxValue = 2000000000)
        private final int intInt;
        @StructureArray(length = 3)
        private final TestStructure[] structureArray;

        protected ValidTestPacket(int size,
                                  PacketType type,
                                  int reqI,
                                  Character characterByte,
                                  boolean booleanByte,
                                  Flags<TestEnum> flagsByte,
                                  int intWord,
                                  Flags<TestEnum> flagsWord,
                                  TestCustomValueEnum customValieEnumWord,
                                  String stringCharArrayWithoutValidation,
                                  String stringCharArray,
                                  long longUnsigned,
                                  Flags<TestEnum> flagsUnsigned,
                                  int intInt,
                                  TestStructure[] structureArray) {
            super(size, type, reqI);
            this.characterByte = characterByte;
            this.booleanByte = booleanByte;
            this.flagsByte = flagsByte;
            this.intWord = intWord;
            this.flagsWord = flagsWord;
            this.customValieEnumWord = customValieEnumWord;
            this.stringCharArrayWithoutValidation = stringCharArrayWithoutValidation;
            this.stringCharArray = stringCharArray;
            this.longUnsigned = longUnsigned;
            this.flagsUnsigned = flagsUnsigned;
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

        @CharArray(length = 5)
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

        @StructureArray(length = 2)
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

    private enum TestEnum {
        VALUE1, VALUE2, VALUE3
    }

    private enum TestCustomValueEnum implements EnumWithCustomValue {
        VALUE1, VALUE2, VALUE3;

        @Override
        public int getValue() {
            return 0;
        }
    }

    @SuppressWarnings("all")
    private static class TestStructure implements InstructionStructure {
        @Byte
        private final short structureField;

        private TestStructure(int structureField) {
            this.structureField = (short) structureField;
        }

        @Override
        public void appendBytes(PacketBuilder packetBuilder) {

        }
    }
}
