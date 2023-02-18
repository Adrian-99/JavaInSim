package pl.adrian.packets.util;

import org.junit.jupiter.api.Test;
import pl.adrian.packets.annotations.Byte;
import pl.adrian.packets.annotations.CharArray;
import pl.adrian.packets.annotations.Unsigned;
import pl.adrian.packets.annotations.Word;
import pl.adrian.packets.base.Packet;
import pl.adrian.packets.base.SendablePacket;
import pl.adrian.packets.enums.PacketType;
import pl.adrian.packets.exceptions.PacketValidationException;
import pl.adrian.packets.flags.Flags;

import static org.junit.jupiter.api.Assertions.*;

class PacketValidatorTest {

    @Test
    void validate_withValidValues() {
        var packet = new ValidTestPacket(
                23,
                PacketType.ISP_NONE,
                54,
                null,
                354,
                new Flags<>(TestEnum.VALUE1),
                null,
                112946L
        );
        assertDoesNotThrow(() -> PacketValidator.validate(packet));
    }

    @Test
    void validate_withTooLowShortByte() {
        var packet = new ValidTestPacket(
                23,
                PacketType.ISP_NONE,
                -5,
                null,
                354,
                new Flags<>(TestEnum.VALUE1),
                null,
                112946L
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
                null,
                354,
                new Flags<>(TestEnum.VALUE1),
                null,
                112946L
        );
        var exception = assertThrows(PacketValidationException.class, () -> PacketValidator.validate(packet));
        assertEquals(PacketValidationException.FailureReason.VALUE_OUT_OF_RANGE, exception.getFailureReason());
        assertEquals("reqI", exception.getField().getName());
    }

    @Test
    void validate_withTooLowIntWord() {
        var packet = new ValidTestPacket(
                23,
                PacketType.ISP_NONE,
                54,
                null,
                -15,
                new Flags<>(TestEnum.VALUE1),
                null,
                112946L
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
                74956,
                new Flags<>(TestEnum.VALUE1),
                null,
                112946L
        );
        var exception = assertThrows(PacketValidationException.class, () -> PacketValidator.validate(packet));
        assertEquals(PacketValidationException.FailureReason.VALUE_OUT_OF_RANGE, exception.getFailureReason());
        assertEquals("intWord", exception.getField().getName());
    }

    @Test
    void validate_withTooLongStringCharArray() {
        var packet = new ValidTestPacket(
                23,
                PacketType.ISP_NONE,
                54,
                'a',
                354,
                new Flags<>(TestEnum.VALUE1),
                "abcdefghij",
                112946L
        );
        var exception = assertThrows(PacketValidationException.class, () -> PacketValidator.validate(packet));
        assertEquals(PacketValidationException.FailureReason.VALUE_TOO_LONG, exception.getFailureReason());
        assertEquals("stringCharArray", exception.getField().getName());
    }

    @Test
    void validate_withTooLowLongUnsigned() {
        var packet = new ValidTestPacket(
                23,
                PacketType.ISP_NONE,
                54,
                'a',
                354,
                new Flags<>(TestEnum.VALUE1),
                "abc",
                -35L
        );
        var exception = assertThrows(PacketValidationException.class, () -> PacketValidator.validate(packet));
        assertEquals(PacketValidationException.FailureReason.VALUE_OUT_OF_RANGE, exception.getFailureReason());
        assertEquals("longUnsigned", exception.getField().getName());
    }

    @Test
    void validate_withTooHighLongUnsigned() {
        var packet = new ValidTestPacket(
                23,
                PacketType.ISP_NONE,
                54,
                'a',
                354,
                new Flags<>(TestEnum.VALUE1),
                "abc",
                5732985275L
        );
        var exception = assertThrows(PacketValidationException.class, () -> PacketValidator.validate(packet));
        assertEquals(PacketValidationException.FailureReason.VALUE_OUT_OF_RANGE, exception.getFailureReason());
        assertEquals("longUnsigned", exception.getField().getName());
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

    @Test
    void validate_packetWithInvalidUnsignedType() {
        var packet = new PacketWithInvalidUnsignedType(
                8,
                PacketType.ISP_NONE,
                54,
                "15"
        );
        var exception = assertThrows(PacketValidationException.class, () -> PacketValidator.validate(packet));
        assertEquals(PacketValidationException.FailureReason.UNSUPPORTED_TYPE, exception.getFailureReason());
        assertEquals("stringUnsigned", exception.getField().getName());
    }

    @SuppressWarnings("all")
    private static class ValidTestPacket extends Packet implements SendablePacket {
        @Byte
        private final Character characterByte;
        @Word
        private final int intWord;
        @Word
        private final Flags<TestEnum> flagsWord;
        @CharArray(maxLength = 5)
        private final String stringCharArray;
        @Unsigned
        private final long longUnsigned;

        protected ValidTestPacket(int size,
                                  PacketType type,
                                  int reqI,
                                  Character characterByte,
                                  int intWord,
                                  Flags<TestEnum> flagsWord,
                                  String stringCharArray,
                                  long longUnsigned) {
            super(size, type, reqI);
            this.characterByte = characterByte;
            this.intWord = intWord;
            this.flagsWord = flagsWord;
            this.stringCharArray = stringCharArray;
            this.longUnsigned = longUnsigned;
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

    private enum TestEnum {
        VALUE1, VALUE2, VALUE3
    }
}
