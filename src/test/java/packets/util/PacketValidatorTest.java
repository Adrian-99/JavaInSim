package packets.util;

import org.junit.jupiter.api.Test;
import pl.adrian.packets.annotations.Byte;
import pl.adrian.packets.annotations.CharArray;
import pl.adrian.packets.annotations.Word;
import pl.adrian.packets.base.Packet;
import pl.adrian.packets.base.SendablePacket;
import pl.adrian.packets.enums.PacketType;
import pl.adrian.packets.exceptions.PacketValidationException;
import pl.adrian.packets.flags.Flags;
import pl.adrian.packets.util.PacketValidator;

import static org.junit.jupiter.api.Assertions.*;

class PacketValidatorTest {

    @Test
    void validate_withValidValues() {
        var packet = new ValidTestPacket(
                23,
                PacketType.ISP_NONE,
                54,
                'a',
                null,
                354,
                new Flags<>(TestEnum.VALUE1),
                null,
                null,
                "abc"
        );
        assertDoesNotThrow(() -> PacketValidator.validate(packet));
    }

    @Test
    void validate_withTooLowShortByte() {
        var packet = new ValidTestPacket(
                23,
                PacketType.ISP_NONE,
                -5,
                'a',
                null,
                354,
                new Flags<>(TestEnum.VALUE1),
                null,
                null,
                "abc"
        );
        var exception = assertThrows(PacketValidationException.class, () -> PacketValidator.validate(packet));
        assertEquals(PacketValidationException.FailureReason.VALUE_OUT_OF_RANGE, exception.getFailureReason());
        assertEquals("reqI", exception.getField().getName());
    }

    @Test
    void validate_withTooHighShortByte() {
        var packet = new ValidTestPacket(
                23,
                PacketType.ISP_NONE,
                350,
                'a',
                null,
                354,
                new Flags<>(TestEnum.VALUE1),
                null,
                null,
                "abc"
        );
        var exception = assertThrows(PacketValidationException.class, () -> PacketValidator.validate(packet));
        assertEquals(PacketValidationException.FailureReason.VALUE_OUT_OF_RANGE, exception.getFailureReason());
        assertEquals("reqI", exception.getField().getName());
    }

    @Test
    void validate_withNullCharacterByte() {
        var packet = new ValidTestPacket(
                23,
                PacketType.ISP_NONE,
                54,
                null,
                null,
                354,
                new Flags<>(TestEnum.VALUE1),
                null,
                null,
                "abc"
        );
        var exception = assertThrows(PacketValidationException.class, () -> PacketValidator.validate(packet));
        assertEquals(PacketValidationException.FailureReason.NULL_VALUE, exception.getFailureReason());
        assertEquals("characterByte", exception.getField().getName());
    }

    @Test
    void validate_withNullEnumByte() {
        var packet = new ValidTestPacket(
                23,
                null,
                54,
                'a',
                null,
                354,
                new Flags<>(TestEnum.VALUE1),
                null,
                null,
                "abc"
        );
        var exception = assertThrows(PacketValidationException.class, () -> PacketValidator.validate(packet));
        assertEquals(PacketValidationException.FailureReason.NULL_VALUE, exception.getFailureReason());
        assertEquals("type", exception.getField().getName());
    }

    @Test
    void validate_withTooLowIntWord() {
        var packet = new ValidTestPacket(
                23,
                PacketType.ISP_NONE,
                54,
                'a',
                null,
                -15,
                new Flags<>(TestEnum.VALUE1),
                null,
                null,
                "abc"
        );
        var exception = assertThrows(PacketValidationException.class, () -> PacketValidator.validate(packet));
        assertEquals(PacketValidationException.FailureReason.VALUE_OUT_OF_RANGE, exception.getFailureReason());
        assertEquals("intWord", exception.getField().getName());
    }

    @Test
    void validate_withTooHighIntWord() {
        var packet = new ValidTestPacket(
                23,
                PacketType.ISP_NONE,
                54,
                'a',
                null,
                74956,
                new Flags<>(TestEnum.VALUE1),
                null,
                null,
                "abc"
        );
        var exception = assertThrows(PacketValidationException.class, () -> PacketValidator.validate(packet));
        assertEquals(PacketValidationException.FailureReason.VALUE_OUT_OF_RANGE, exception.getFailureReason());
        assertEquals("intWord", exception.getField().getName());
    }

    @Test
    void validate_withNullIntWord() {
        var packet = new ValidTestPacket(
                23,
                PacketType.ISP_NONE,
                54,
                'a',
                null,
                null,
                new Flags<>(TestEnum.VALUE1),
                null,
                null,
                "abc"
        );
        var exception = assertThrows(PacketValidationException.class, () -> PacketValidator.validate(packet));
        assertEquals(PacketValidationException.FailureReason.NULL_VALUE, exception.getFailureReason());
        assertEquals("intWord", exception.getField().getName());
    }

    @Test
    void validate_withNullFlagsWord() {
        var packet = new ValidTestPacket(
                23,
                PacketType.ISP_NONE,
                54,
                'a',
                null,
                354,
                null,
                null,
                null,
                "abc"
        );
        var exception = assertThrows(PacketValidationException.class, () -> PacketValidator.validate(packet));
        assertEquals(PacketValidationException.FailureReason.NULL_VALUE, exception.getFailureReason());
        assertEquals("flagsWord", exception.getField().getName());
    }

    @Test
    void validate_withTooLongStringCharArray() {
        var packet = new ValidTestPacket(
                23,
                PacketType.ISP_NONE,
                54,
                'a',
                null,
                354,
                new Flags<>(TestEnum.VALUE1),
                null,
                null,
                "abcdefghij"
        );
        var exception = assertThrows(PacketValidationException.class, () -> PacketValidator.validate(packet));
        assertEquals(PacketValidationException.FailureReason.VALUE_TOO_LONG, exception.getFailureReason());
        assertEquals("nonNullableCharArray", exception.getField().getName());
    }

    @Test
    void validate_withNullStringCharArray() {
        var packet = new ValidTestPacket(
                23,
                PacketType.ISP_NONE,
                54,
                'a',
                null,
                354,
                new Flags<>(TestEnum.VALUE1),
                null,
                null,
                null
        );
        var exception = assertThrows(PacketValidationException.class, () -> PacketValidator.validate(packet));
        assertEquals(PacketValidationException.FailureReason.NULL_VALUE, exception.getFailureReason());
        assertEquals("nonNullableCharArray", exception.getField().getName());
    }

    @Test
    void validate_packetWithFieldWithoutAnnotation() {
        var packet = new PacketWithFieldWithoutAnnotation(
                8,
                PacketType.ISP_NONE,
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
                PacketType.ISP_NONE,
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
                PacketType.ISP_NONE,
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
                PacketType.ISP_NONE,
                54,
                15
        );
        var exception = assertThrows(PacketValidationException.class, () -> PacketValidator.validate(packet));
        assertEquals(PacketValidationException.FailureReason.UNSUPPORTED_TYPE, exception.getFailureReason());
        assertEquals("intCharArray", exception.getField().getName());
    }

    @SuppressWarnings("all")
    private static class ValidTestPacket extends Packet implements SendablePacket {
        @Byte
        private final Character characterByte;
        @Byte(nullable = true)
        private final Character nullableByte;
        @Word
        private final Integer intWord;
        @Word
        private final Flags<TestEnum> flagsWord;
        @Word(nullable = true)
        private final Integer nullableWord;
        @CharArray(maxLength = 5)
        private final String stringCharArray;
        @CharArray(maxLength = 5, nullable = false)
        private final String nonNullableCharArray;

        protected ValidTestPacket(int size,
                                  PacketType type,
                                  int reqI,
                                  Character characterByte,
                                  Character nullableByte,
                                  Integer intWord,
                                  Flags<TestEnum> flagsWord,
                                  Integer nullableWord,
                                  String stringCharArray,
                                  String nonNullableCharArray) {
            super(size, type, reqI);
            this.characterByte = characterByte;
            this.nullableByte = nullableByte;
            this.intWord = intWord;
            this.flagsWord = flagsWord;
            this.nullableWord = nullableWord;
            this.stringCharArray = stringCharArray;
            this.nonNullableCharArray = nonNullableCharArray;
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

        @CharArray(maxLength = 5)
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

    private enum TestEnum {
        VALUE1, VALUE2, VALUE3
    }
}
