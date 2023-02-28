package pl.adrian.internal.packets.util;

import org.junit.jupiter.api.Test;
import pl.adrian.internal.packets.annotations.*;
import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.base.Packet;
import pl.adrian.internal.packets.base.SendablePacket;
import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.internal.packets.enums.EnumWithCustomValue;
import pl.adrian.internal.packets.exceptions.PacketValidationException;
import pl.adrian.api.packets.flags.Flags;

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
                354,
                new Flags<>(TestEnum.VALUE1),
                TestCustomValueEnum.VALUE1,
                "abcdefghij",
                null,
                112946L,
                1834749274
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
                354,
                new Flags<>(TestEnum.VALUE1),
                TestCustomValueEnum.VALUE1,
                "abcdefghij",
                null,
                112946L,
                1834749274
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
                354,
                new Flags<>(TestEnum.VALUE1),
                TestCustomValueEnum.VALUE1,
                "abcdefghij",
                null,
                112946L,
                1834749274
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
                -15,
                new Flags<>(TestEnum.VALUE1),
                TestCustomValueEnum.VALUE1,
                "abcdefghij",
                null,
                112946L,
                1834749274
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
                74956,
                new Flags<>(TestEnum.VALUE1),
                TestCustomValueEnum.VALUE1,
                "abcdefghij",
                null,
                112946L,
                1834749274
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
                354,
                new Flags<>(TestEnum.VALUE1),
                TestCustomValueEnum.VALUE1,
                "abcdefghij",
                "abcdefghij",
                112946L,
                1834749274
        );
        var exception = assertThrows(PacketValidationException.class, () -> PacketValidator.validate(packet));
        assertEquals(PacketValidationException.FailureReason.VALUE_TOO_LONG, exception.getFailureReason());
        assertEquals("stringCharArray", exception.getField().getName());
    }

    @Test
    void validate_withTooLowLongUnsigned() {
        var packet = new ValidTestPacket(
                23,
                PacketType.NONE,
                54,
                'a',
                false,
                354,
                new Flags<>(TestEnum.VALUE1),
                TestCustomValueEnum.VALUE1,
                "abcdefghij",
                "abc",
                -35L,
                1834749274
        );
        var exception = assertThrows(PacketValidationException.class, () -> PacketValidator.validate(packet));
        assertEquals(PacketValidationException.FailureReason.VALUE_OUT_OF_RANGE, exception.getFailureReason());
        assertEquals("longUnsigned", exception.getField().getName());
    }

    @Test
    void validate_withTooHighLongUnsigned() {
        var packet = new ValidTestPacket(
                23,
                PacketType.NONE,
                54,
                'a',
                false,
                354,
                new Flags<>(TestEnum.VALUE1),
                TestCustomValueEnum.VALUE1,
                "abcdefghij",
                "abc",
                5732985275L,
                1834749274
        );
        var exception = assertThrows(PacketValidationException.class, () -> PacketValidator.validate(packet));
        assertEquals(PacketValidationException.FailureReason.VALUE_OUT_OF_RANGE, exception.getFailureReason());
        assertEquals("longUnsigned", exception.getField().getName());
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

    @SuppressWarnings("all")
    private static class ValidTestPacket extends Packet implements SendablePacket {
        @Byte
        private final Character characterByte;
        @Byte
        private final boolean booleanByte;
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
        @Int
        private final int intInt;

        protected ValidTestPacket(int size,
                                  PacketType type,
                                  int reqI,
                                  Character characterByte,
                                  boolean booleanByte,
                                  int intWord,
                                  Flags<TestEnum> flagsWord,
                                  TestCustomValueEnum customValieEnumWord,
                                  String stringCharArrayWithoutValidation,
                                  String stringCharArray,
                                  long longUnsigned,
                                  int intInt) {
            super(size, type, reqI);
            this.characterByte = characterByte;
            this.booleanByte = booleanByte;
            this.intWord = intWord;
            this.flagsWord = flagsWord;
            this.customValieEnumWord = customValieEnumWord;
            this.stringCharArrayWithoutValidation = stringCharArrayWithoutValidation;
            this.stringCharArray = stringCharArray;
            this.longUnsigned = longUnsigned;
            this.intInt = intInt;
        }

        @Override
        public byte[] getBytes() {
            return new byte[0];
        }
    }

    @SuppressWarnings("all")
    private static class PacketWithFieldWithoutAnnotation extends Packet implements SendablePacket {
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
    private static class PacketWithInvalidByteType extends Packet implements SendablePacket {

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
    private static class PacketWithInvalidWordType extends Packet implements SendablePacket {

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
    private static class PacketWithInvalidCharArrayType extends Packet implements SendablePacket {

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
    private static class PacketWithInvalidUnsignedType extends Packet implements SendablePacket {

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
    private static class PacketWithInvalidIntType extends Packet implements SendablePacket {

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
}
