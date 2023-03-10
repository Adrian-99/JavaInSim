package pl.adrian.internal.packets.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.structures.InstructionStructure;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PacketBuilderTest {

    @BeforeEach
    void beforeEach() {
        TestStructure.resetMethodCallsCounter();
    }

    @Test
    void buildEmptyPacket() {
        var packetBuilder = new PacketBuilder((short) 4, PacketType.fromOrdinal(1), (short) 154);
        var bytes = packetBuilder.getBytes();

        assertEquals(4, bytes.length);
        assertEquals(1, bytes[0]);
        assertEquals(1, bytes[1]);
        assertEquals(-102, bytes[2]);
        assertEquals(0, bytes[3]);
    }

    @Test
    void writeByte_fromShort() {
        var packetBuilder = new PacketBuilder((short) 4, PacketType.fromOrdinal(1), (short) 154);
        packetBuilder.writeByte((short) 200);
        var bytes = packetBuilder.getBytes();

        assertEquals(4, bytes.length);
        assertEquals(1, bytes[0]);
        assertEquals(1, bytes[1]);
        assertEquals(-102, bytes[2]);
        assertEquals(-56, bytes[3]);
    }

    @Test
    void writeByte_fromInt() {
        var packetBuilder = new PacketBuilder((short) 4, PacketType.fromOrdinal(1), (short) 154);
        packetBuilder.writeByte(12);
        var bytes = packetBuilder.getBytes();

        assertEquals(4, bytes.length);
        assertEquals(1, bytes[0]);
        assertEquals(1, bytes[1]);
        assertEquals(-102, bytes[2]);
        assertEquals(12, bytes[3]);
    }

    @Test
    void writeByte_fromCharacter() {
        var packetBuilder = new PacketBuilder((short) 4, PacketType.fromOrdinal(1), (short) 154);
        packetBuilder.writeByte(Character.valueOf('a'));
        var bytes = packetBuilder.getBytes();

        assertEquals(4, bytes.length);
        assertEquals(1, bytes[0]);
        assertEquals(1, bytes[1]);
        assertEquals(-102, bytes[2]);
        assertEquals(97, bytes[3]);
    }

    @Test
    void writeByte_fromNullCharacter() {
        var packetBuilder = new PacketBuilder((short) 4, PacketType.fromOrdinal(1), (short) 154);
        packetBuilder.writeByte(null);
        var bytes = packetBuilder.getBytes();

        assertEquals(4, bytes.length);
        assertEquals(1, bytes[0]);
        assertEquals(1, bytes[1]);
        assertEquals(-102, bytes[2]);
        assertEquals(0, bytes[3]);
    }

    @Test
    void writeByte_fromTrueBoolean() {
        var packetBuilder = new PacketBuilder((short) 4, PacketType.fromOrdinal(1), (short) 154);
        packetBuilder.writeByte(true);
        var bytes = packetBuilder.getBytes();

        assertEquals(4, bytes.length);
        assertEquals(1, bytes[0]);
        assertEquals(1, bytes[1]);
        assertEquals(-102, bytes[2]);
        assertEquals(1, bytes[3]);
    }

    @Test
    void writeByte_fromFalseBoolean() {
        var packetBuilder = new PacketBuilder((short) 4, PacketType.fromOrdinal(1), (short) 154);
        packetBuilder.writeByte(false);
        var bytes = packetBuilder.getBytes();

        assertEquals(4, bytes.length);
        assertEquals(1, bytes[0]);
        assertEquals(1, bytes[1]);
        assertEquals(-102, bytes[2]);
        assertEquals(0, bytes[3]);
    }

    @Test
    void writeZeroByte() {
        var packetBuilder = new PacketBuilder((short) 8, PacketType.fromOrdinal(1), (short) 154);
        packetBuilder.writeZeroByte();
        packetBuilder.writeByte(15);
        var bytes = packetBuilder.getBytes();

        assertEquals(8, bytes.length);
        assertEquals(2, bytes[0]);
        assertEquals(1, bytes[1]);
        assertEquals(-102, bytes[2]);
        assertEquals(0, bytes[3]);
        assertEquals(15, bytes[4]);
        assertEquals(0, bytes[5]);
        assertEquals(0, bytes[6]);
        assertEquals(0, bytes[7]);
    }

    @Test
    void writeZeroBytes() {
        var packetBuilder = new PacketBuilder((short) 8, PacketType.fromOrdinal(1), (short) 154);
        packetBuilder.writeZeroBytes(3);
        packetBuilder.writeByte(15);
        var bytes = packetBuilder.getBytes();

        assertEquals(8, bytes.length);
        assertEquals(2, bytes[0]);
        assertEquals(1, bytes[1]);
        assertEquals(-102, bytes[2]);
        assertEquals(0, bytes[3]);
        assertEquals(0, bytes[4]);
        assertEquals(0, bytes[5]);
        assertEquals(15, bytes[6]);
        assertEquals(0, bytes[7]);
    }

    @Test
    void writeWord_fromLowNumber() {
        var packetBuilder = new PacketBuilder((short) 8, PacketType.fromOrdinal(1), (short) 154);
        packetBuilder.writeWord(75);
        var bytes = packetBuilder.getBytes();

        assertEquals(8, bytes.length);
        assertEquals(2, bytes[0]);
        assertEquals(1, bytes[1]);
        assertEquals(-102, bytes[2]);
        assertEquals(75, bytes[3]);
        assertEquals(0, bytes[4]);
        assertEquals(0, bytes[5]);
        assertEquals(0, bytes[6]);
        assertEquals(0, bytes[7]);
    }

    @Test
    void writeWord_fromHighNumber() {
        var packetBuilder = new PacketBuilder((short) 8, PacketType.fromOrdinal(1), (short) 154);
        packetBuilder.writeWord(10688);
        var bytes = packetBuilder.getBytes();

        assertEquals(8, bytes.length);
        assertEquals(2, bytes[0]);
        assertEquals(1, bytes[1]);
        assertEquals(-102, bytes[2]);
        assertEquals(-64, bytes[3]);
        assertEquals(41, bytes[4]);
        assertEquals(0, bytes[5]);
        assertEquals(0, bytes[6]);
        assertEquals(0, bytes[7]);
    }

    @Test
    void writeCharArray_fromShortString() {
        var packetBuilder = new PacketBuilder((short) 12, PacketType.fromOrdinal(1), (short) 154);
        packetBuilder.writeCharArray("Test", 8, false);
        packetBuilder.writeByte(55);
        var bytes = packetBuilder.getBytes();

        assertEquals(12, bytes.length);
        assertEquals(3, bytes[0]);
        assertEquals(1, bytes[1]);
        assertEquals(-102, bytes[2]);
        assertEquals(84, bytes[3]);
        assertEquals(101, bytes[4]);
        assertEquals(115, bytes[5]);
        assertEquals(116, bytes[6]);
        assertEquals(0, bytes[7]);
        assertEquals(0, bytes[8]);
        assertEquals(0, bytes[9]);
        assertEquals(0, bytes[10]);
        assertEquals(55, bytes[11]);
    }

    @Test
    void writeCharArray_fromLongString() {
        var packetBuilder = new PacketBuilder((short) 12, PacketType.fromOrdinal(1), (short) 154);
        packetBuilder.writeCharArray("Test long text", 8, false);
        packetBuilder.writeByte(55);
        var bytes = packetBuilder.getBytes();

        assertEquals(12, bytes.length);
        assertEquals(3, bytes[0]);
        assertEquals(1, bytes[1]);
        assertEquals(-102, bytes[2]);
        assertEquals(84, bytes[3]);
        assertEquals(101, bytes[4]);
        assertEquals(115, bytes[5]);
        assertEquals(116, bytes[6]);
        assertEquals(32, bytes[7]);
        assertEquals(108, bytes[8]);
        assertEquals(111, bytes[9]);
        assertEquals(0, bytes[10]);
        assertEquals(55, bytes[11]);
    }

    @Test
    void writeCharArray_fromNull() {
        var packetBuilder = new PacketBuilder((short) 12, PacketType.fromOrdinal(1), (short) 154);
        packetBuilder.writeCharArray(null, 8, false);
        packetBuilder.writeByte(55);
        var bytes = packetBuilder.getBytes();

        assertEquals(12, bytes.length);
        assertEquals(3, bytes[0]);
        assertEquals(1, bytes[1]);
        assertEquals(-102, bytes[2]);
        assertEquals(0, bytes[3]);
        assertEquals(0, bytes[4]);
        assertEquals(0, bytes[5]);
        assertEquals(0, bytes[6]);
        assertEquals(0, bytes[7]);
        assertEquals(0, bytes[8]);
        assertEquals(0, bytes[9]);
        assertEquals(0, bytes[10]);
        assertEquals(55, bytes[11]);
    }

    @Test
    void writeCharArray_fromShortStringWithVariableLength() {
        var packetBuilder = new PacketBuilder((short) 8, PacketType.fromOrdinal(1), (short) 154);
        packetBuilder.writeCharArray("Tes", 8, true);
        packetBuilder.writeByte(55);
        var bytes = packetBuilder.getBytes();

        assertEquals(8, bytes.length);
        assertEquals(2, bytes[0]);
        assertEquals(1, bytes[1]);
        assertEquals(-102, bytes[2]);
        assertEquals(84, bytes[3]);
        assertEquals(101, bytes[4]);
        assertEquals(115, bytes[5]);
        assertEquals(0, bytes[6]);
        assertEquals(55, bytes[7]);
    }

    @Test
    void writeCharArray_fromLongStringWithVariableLength() {
        var packetBuilder = new PacketBuilder((short) 12, PacketType.fromOrdinal(1), (short) 154);
        packetBuilder.writeCharArray("Test long text", 8, true);
        packetBuilder.writeByte(55);
        var bytes = packetBuilder.getBytes();

        assertEquals(12, bytes.length);
        assertEquals(3, bytes[0]);
        assertEquals(1, bytes[1]);
        assertEquals(-102, bytes[2]);
        assertEquals(84, bytes[3]);
        assertEquals(101, bytes[4]);
        assertEquals(115, bytes[5]);
        assertEquals(116, bytes[6]);
        assertEquals(32, bytes[7]);
        assertEquals(108, bytes[8]);
        assertEquals(111, bytes[9]);
        assertEquals(0, bytes[10]);
        assertEquals(55, bytes[11]);
    }

    @Test
    void writeCharArray_fromNullWithVariableLength() {
        var packetBuilder = new PacketBuilder((short) 8, PacketType.fromOrdinal(1), (short) 154);
        packetBuilder.writeCharArray(null, 8, true);
        packetBuilder.writeByte(55);
        var bytes = packetBuilder.getBytes();

        assertEquals(8, bytes.length);
        assertEquals(2, bytes[0]);
        assertEquals(1, bytes[1]);
        assertEquals(-102, bytes[2]);
        assertEquals(0, bytes[3]);
        assertEquals(0, bytes[4]);
        assertEquals(0, bytes[5]);
        assertEquals(0, bytes[6]);
        assertEquals(55, bytes[7]);
    }

    @Test
    void writeUnsigned_fromLowNumber() {
        var packetBuilder = new PacketBuilder((short) 8, PacketType.fromOrdinal(1), (short) 154);
        packetBuilder.writeUnsigned(15L);
        packetBuilder.writeByte(55);
        var bytes = packetBuilder.getBytes();

        assertEquals(8, bytes.length);
        assertEquals(2, bytes[0]);
        assertEquals(1, bytes[1]);
        assertEquals(-102, bytes[2]);
        assertEquals(15, bytes[3]);
        assertEquals(0, bytes[4]);
        assertEquals(0, bytes[5]);
        assertEquals(0, bytes[6]);
        assertEquals(55, bytes[7]);
    }

    @Test
    void writeUnsigned_fromHighNumber() {
        var packetBuilder = new PacketBuilder((short) 8, PacketType.fromOrdinal(1), (short) 154);
        packetBuilder.writeUnsigned(3845264765L);
        packetBuilder.writeByte(55);
        var bytes = packetBuilder.getBytes();

        assertEquals(8, bytes.length);
        assertEquals(2, bytes[0]);
        assertEquals(1, bytes[1]);
        assertEquals(-102, bytes[2]);
        assertEquals(125, bytes[3]);
        assertEquals(21, bytes[4]);
        assertEquals(50, bytes[5]);
        assertEquals(-27, bytes[6]);
        assertEquals(55, bytes[7]);
    }

    @Test
    void writeInt_fromPositiveNumber() {
        var packetBuilder = new PacketBuilder((short) 8, PacketType.fromOrdinal(1), (short) 154);
        packetBuilder.writeInt(172);
        packetBuilder.writeByte(55);
        var bytes = packetBuilder.getBytes();

        assertEquals(8, bytes.length);
        assertEquals(2, bytes[0]);
        assertEquals(1, bytes[1]);
        assertEquals(-102, bytes[2]);
        assertEquals(-84, bytes[3]);
        assertEquals(0, bytes[4]);
        assertEquals(0, bytes[5]);
        assertEquals(0, bytes[6]);
        assertEquals(55, bytes[7]);
    }

    @Test
    void writeInt_fromNegativeNumber() {
        var packetBuilder = new PacketBuilder((short) 8, PacketType.fromOrdinal(1), (short) 154);
        packetBuilder.writeInt(-1954945182);
        packetBuilder.writeByte(55);
        var bytes = packetBuilder.getBytes();

        assertEquals(8, bytes.length);
        assertEquals(2, bytes[0]);
        assertEquals(1, bytes[1]);
        assertEquals(-102, bytes[2]);
        assertEquals(98, bytes[3]);
        assertEquals(-25, bytes[4]);
        assertEquals(121, bytes[5]);
        assertEquals(-117, bytes[6]);
        assertEquals(55, bytes[7]);
    }

    @Test
    void writeStructureArray_fromRegularArray() {
        var packetBuilder = new PacketBuilder((short) 8, PacketType.fromOrdinal(1), (short) 154);
        packetBuilder.writeStructureArray(
                new TestStructure[] {
                        new TestStructure(5),
                        new TestStructure(6),
                        new TestStructure(7),
                        new TestStructure(8),
                },
                1
        );
        packetBuilder.writeByte(55);
        var bytes = packetBuilder.getBytes();

        assertEquals(8, bytes.length);
        assertEquals(2, bytes[0]);
        assertEquals(1, bytes[1]);
        assertEquals(-102, bytes[2]);
        assertEquals(5, bytes[3]);
        assertEquals(6, bytes[4]);
        assertEquals(7, bytes[5]);
        assertEquals(8, bytes[6]);
        assertEquals(55, bytes[7]);
        assertEquals(4, TestStructure.getAppendBytesMethodCallsCount());
    }

    @Test
    void writeStructureArray_fromArrayWithNulls() {
        var packetBuilder = new PacketBuilder((short) 8, PacketType.fromOrdinal(1), (short) 154);
        packetBuilder.writeStructureArray(
                new TestStructure[] {
                        new TestStructure(5),
                        null,
                        null,
                        new TestStructure(8),
                },
                1
        );
        packetBuilder.writeByte(55);
        var bytes = packetBuilder.getBytes();

        assertEquals(8, bytes.length);
        assertEquals(2, bytes[0]);
        assertEquals(1, bytes[1]);
        assertEquals(-102, bytes[2]);
        assertEquals(5, bytes[3]);
        assertEquals(0, bytes[4]);
        assertEquals(0, bytes[5]);
        assertEquals(8, bytes[6]);
        assertEquals(55, bytes[7]);
        assertEquals(2, TestStructure.getAppendBytesMethodCallsCount());
    }

    private static class TestStructure implements InstructionStructure {
        @Byte
        private final short structureField;

        private static int appendBytesMethodCallsCount = 0;

        private TestStructure(int structureField) {
            this.structureField = (short) structureField;
        }

        @Override
        public void appendBytes(PacketBuilder packetBuilder) {
            appendBytesMethodCallsCount++;
            packetBuilder.writeByte(structureField);
        }

        public static void resetMethodCallsCounter() {
            appendBytesMethodCallsCount = 0;
        }

        public static int getAppendBytesMethodCallsCount() {
            return appendBytesMethodCallsCount;
        }
    }
}
