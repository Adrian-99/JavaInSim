package pl.adrian.internal.insim.packets.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.adrian.api.insim.packets.enums.PacketType;
import pl.adrian.internal.insim.packets.annotations.Byte;
import pl.adrian.internal.insim.packets.structures.base.ComplexInstructionStructure;
import pl.adrian.internal.insim.packets.structures.base.UnsignedInstructionStructure;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PacketBuilderTest {

    @BeforeEach
    void beforeEach() {
        TestComplexStructure.resetMethodCallsCounter();
    }

    @Test
    void buildEmptyPacket() {
        var packetBuilder = new PacketBuilder((short) 4, PacketType.fromOrdinal(1), (short) 154);
        var bytes = packetBuilder.getBytes();
        var expectedBytes = new byte[] { 1, 1, -102, 0 };

        assertArrayEquals(expectedBytes, bytes);
    }

    @Test
    void writeByte_fromShort() {
        var packetBuilder = new PacketBuilder((short) 4, PacketType.fromOrdinal(1), (short) 154);
        packetBuilder.writeByte((short) 200);
        var bytes = packetBuilder.getBytes();
        var expectedBytes = new byte[] { 1, 1, -102, -56 };

        assertArrayEquals(expectedBytes, bytes);
    }

    @Test
    void writeByte_fromInt() {
        var packetBuilder = new PacketBuilder((short) 4, PacketType.fromOrdinal(1), (short) 154);
        packetBuilder.writeByte(12);
        var bytes = packetBuilder.getBytes();
        var expectedBytes = new byte[] { 1, 1, -102, 12 };

        assertArrayEquals(expectedBytes, bytes);
    }

    @Test
    void writeByte_fromCharacter() {
        var packetBuilder = new PacketBuilder((short) 4, PacketType.fromOrdinal(1), (short) 154);
        packetBuilder.writeByte(Character.valueOf('a'));
        var bytes = packetBuilder.getBytes();
        var expectedBytes = new byte[] { 1, 1, -102, 97 };

        assertArrayEquals(expectedBytes, bytes);
    }

    @Test
    void writeByte_fromNullCharacter() {
        var packetBuilder = new PacketBuilder((short) 4, PacketType.fromOrdinal(1), (short) 154);
        packetBuilder.writeByte(null);
        var bytes = packetBuilder.getBytes();
        var expectedBytes = new byte[] { 1, 1, -102, 0 };

        assertArrayEquals(expectedBytes, bytes);
    }

    @Test
    void writeByte_fromTrueBoolean() {
        var packetBuilder = new PacketBuilder((short) 4, PacketType.fromOrdinal(1), (short) 154);
        packetBuilder.writeByte(true);
        var bytes = packetBuilder.getBytes();
        var expectedBytes = new byte[] { 1, 1, -102, 1 };

        assertArrayEquals(expectedBytes, bytes);
    }

    @Test
    void writeByte_fromFalseBoolean() {
        var packetBuilder = new PacketBuilder((short) 4, PacketType.fromOrdinal(1), (short) 154);
        packetBuilder.writeByte(false);
        var bytes = packetBuilder.getBytes();
        var expectedBytes = new byte[] { 1, 1, -102, 0 };

        assertArrayEquals(expectedBytes, bytes);
    }

    @Test
    void writeZeroByte() {
        var packetBuilder = new PacketBuilder((short) 8, PacketType.fromOrdinal(1), (short) 154);
        packetBuilder.writeZeroByte();
        packetBuilder.writeByte(15);
        var bytes = packetBuilder.getBytes();
        var expectedBytes = new byte[] { 2, 1, -102, 0, 15, 0, 0, 0 };

        assertArrayEquals(expectedBytes, bytes);
    }

    @Test
    void writeZeroBytes() {
        var packetBuilder = new PacketBuilder((short) 8, PacketType.fromOrdinal(1), (short) 154);
        packetBuilder.writeZeroBytes(3);
        packetBuilder.writeByte(15);
        var bytes = packetBuilder.getBytes();
        var expectedBytes = new byte[] { 2, 1, -102, 0, 0, 0, 15, 0 };

        assertArrayEquals(expectedBytes, bytes);
    }

    @Test
    void writeWord_fromLowNumber() {
        var packetBuilder = new PacketBuilder((short) 8, PacketType.fromOrdinal(1), (short) 154);
        packetBuilder.writeWord(75);
        var bytes = packetBuilder.getBytes();
        var expectedBytes = new byte[] { 2, 1, -102, 75, 0, 0, 0, 0 };

        assertArrayEquals(expectedBytes, bytes);
    }

    @Test
    void writeWord_fromHighNumber() {
        var packetBuilder = new PacketBuilder((short) 8, PacketType.fromOrdinal(1), (short) 154);
        packetBuilder.writeWord(10688);
        var bytes = packetBuilder.getBytes();
        var expectedBytes = new byte[] { 2, 1, -102, -64, 41, 0, 0, 0 };

        assertArrayEquals(expectedBytes, bytes);
    }

    @Test
    void writeShort_fromPositiveNumber() {
        var packetBuilder = new PacketBuilder((short) 8, PacketType.fromOrdinal(1), (short) 154);
        packetBuilder.writeShort((short) 954)
                .writeZeroBytes(3);
        var bytes = packetBuilder.getBytes();
        var expectedBytes = new byte[] { 2, 1, -102, -70, 3, 0, 0, 0 };

        assertArrayEquals(expectedBytes, bytes);
    }

    @Test
    void writeShort_fromNegativeNumber() {
        var packetBuilder = new PacketBuilder((short) 8, PacketType.fromOrdinal(1), (short) 154);
        packetBuilder.writeShort((short) -15832)
                .writeZeroBytes(3);
        var bytes = packetBuilder.getBytes();
        var expectedBytes = new byte[] { 2, 1, -102, 40, -62, 0, 0, 0 };

        assertArrayEquals(expectedBytes, bytes);
    }

    @Test
    void writeCharArray_fromShortString() {
        var packetBuilder = new PacketBuilder((short) 12, PacketType.fromOrdinal(1), (short) 154);
        packetBuilder.writeCharArray("Test", 8, false);
        packetBuilder.writeByte(55);
        var bytes = packetBuilder.getBytes();
        var expectedBytes = new byte[] { 3, 1, -102, 84, 101, 115, 116, 0, 0, 0, 0, 55 };

        assertArrayEquals(expectedBytes, bytes);
    }

    @Test
    void writeCharArray_fromLongString() {
        var packetBuilder = new PacketBuilder((short) 12, PacketType.fromOrdinal(1), (short) 154);
        packetBuilder.writeCharArray("Test long text", 8, false);
        packetBuilder.writeByte(55);
        var bytes = packetBuilder.getBytes();
        var expectedBytes = new byte[] { 3, 1, -102, 84, 101, 115, 116, 32, 108, 111, 0, 55 };

        assertArrayEquals(expectedBytes, bytes);
    }

    @Test
    void writeCharArray_fromNull() {
        var packetBuilder = new PacketBuilder((short) 12, PacketType.fromOrdinal(1), (short) 154);
        packetBuilder.writeCharArray(null, 8, false);
        packetBuilder.writeByte(55);
        var bytes = packetBuilder.getBytes();
        var expectedBytes = new byte[] { 3, 1, -102, 0, 0, 0, 0, 0, 0, 0, 0, 55 };

        assertArrayEquals(expectedBytes, bytes);
    }

    @Test
    void writeCharArray_fromShortStringWithVariableLength() {
        var packetBuilder = new PacketBuilder((short) 8, PacketType.fromOrdinal(1), (short) 154);
        packetBuilder.writeCharArray("Tes", 8, true);
        packetBuilder.writeByte(55);
        var bytes = packetBuilder.getBytes();
        var expectedBytes = new byte[] { 2, 1, -102, 84, 101, 115, 0, 55 };

        assertArrayEquals(expectedBytes, bytes);
    }

    @Test
    void writeCharArray_fromLongStringWithVariableLength() {
        var packetBuilder = new PacketBuilder((short) 12, PacketType.fromOrdinal(1), (short) 154);
        packetBuilder.writeCharArray("Test long text", 8, true);
        packetBuilder.writeByte(55);
        var bytes = packetBuilder.getBytes();
        var expectedBytes = new byte[] { 3, 1, -102, 84, 101, 115, 116, 32, 108, 111, 0, 55 };

        assertArrayEquals(expectedBytes, bytes);
    }

    @Test
    void writeCharArray_fromNullWithVariableLength() {
        var packetBuilder = new PacketBuilder((short) 8, PacketType.fromOrdinal(1), (short) 154);
        packetBuilder.writeCharArray(null, 8, true);
        packetBuilder.writeByte(55);
        var bytes = packetBuilder.getBytes();
        var expectedBytes = new byte[] { 2, 1, -102, 0, 0, 0, 0, 55 };

        assertArrayEquals(expectedBytes, bytes);
    }

    @Test
    void writeUnsigned_fromLowNumber() {
        var packetBuilder = new PacketBuilder((short) 8, PacketType.fromOrdinal(1), (short) 154);
        packetBuilder.writeUnsigned(15L);
        packetBuilder.writeByte(55);
        var bytes = packetBuilder.getBytes();
        var expectedBytes = new byte[] { 2, 1, -102, 15, 0, 0, 0, 55 };

        assertArrayEquals(expectedBytes, bytes);
    }

    @Test
    void writeUnsigned_fromHighNumber() {
        var packetBuilder = new PacketBuilder((short) 8, PacketType.fromOrdinal(1), (short) 154);
        packetBuilder.writeUnsigned(3845264765L);
        packetBuilder.writeByte(55);
        var bytes = packetBuilder.getBytes();
        var expectedBytes = new byte[] { 2, 1, -102, 125, 21, 50, -27, 55 };

        assertArrayEquals(expectedBytes, bytes);
    }

    @Test
    void writeUnsignedArray_withNullValues() {
        final var structuresList = new ArrayList<TestUnsignedStructure>();
        structuresList.add(new TestUnsignedStructure(896523));
        structuresList.add(null);
        structuresList.add(new TestUnsignedStructure(1235675645));
        var packetBuilder = new PacketBuilder((short) 16, PacketType.fromOrdinal(1), (short) 154);
        packetBuilder.writeUnsignedArray(structuresList);
        packetBuilder.writeByte(55);
        var bytes = packetBuilder.getBytes();
        var expectedBytes = new byte[] { 4, 1, -102, 11, -82, 13, 0, 0, 0, 0, 0, -3, -23, -90, 73, 55 };

        assertArrayEquals(expectedBytes, bytes);
    }

    @Test
    void writeInt_fromPositiveNumber() {
        var packetBuilder = new PacketBuilder((short) 8, PacketType.fromOrdinal(1), (short) 154);
        packetBuilder.writeInt(172);
        packetBuilder.writeByte(55);
        var bytes = packetBuilder.getBytes();
        var expectedBytes = new byte[] { 2, 1, -102, -84, 0, 0, 0, 55 };

        assertArrayEquals(expectedBytes, bytes);
    }

    @Test
    void writeInt_fromNegativeNumber() {
        var packetBuilder = new PacketBuilder((short) 8, PacketType.fromOrdinal(1), (short) 154);
        packetBuilder.writeInt(-1954945182);
        packetBuilder.writeByte(55);
        var bytes = packetBuilder.getBytes();
        var expectedBytes = new byte[] { 2, 1, -102, 98, -25, 121, -117, 55 };

        assertArrayEquals(expectedBytes, bytes);
    }

    @Test
    void writeFloat() {
        var packetBuilder = new PacketBuilder((short) 8, PacketType.fromOrdinal(1), (short) 154);
        packetBuilder.writeFloat(-2.73828125f);
        packetBuilder.writeByte(55);
        var bytes = packetBuilder.getBytes();
        var expectedBytes = new byte[] { 2, 1, -102, 0, 64, 47, -64, 55 };

        assertArrayEquals(expectedBytes, bytes);
    }

    @Test
    void writeStructure() {
        var packetBuilder = new PacketBuilder((short) 4, PacketType.fromOrdinal(1), (short) 154);
        packetBuilder.writeStructure(new TestComplexStructure(89), 1);
        var bytes = packetBuilder.getBytes();
        var expectedBytes = new byte[] { 1, 1, -102, 89 };

        assertArrayEquals(expectedBytes, bytes);
        assertEquals(1, TestComplexStructure.getAppendBytesMethodCallsCount());
    }

    @Test
    void writeStructure_fromNull() {
        var packetBuilder = new PacketBuilder((short) 4, PacketType.fromOrdinal(1), (short) 154);
        packetBuilder.writeStructure(null, 1);
        var bytes = packetBuilder.getBytes();
        var expectedBytes = new byte[] { 1, 1, -102, 0 };

        assertArrayEquals(expectedBytes, bytes);
        assertEquals(0, TestComplexStructure.getAppendBytesMethodCallsCount());
    }

    @Test
    void writeStructureArray_fromRegularArray() {
        var packetBuilder = new PacketBuilder((short) 8, PacketType.fromOrdinal(1), (short) 154);
        packetBuilder.writeStructureArray(
                new TestComplexStructure[] {
                        new TestComplexStructure(5),
                        new TestComplexStructure(6),
                        new TestComplexStructure(7),
                        new TestComplexStructure(8),
                },
                1
        );
        packetBuilder.writeByte(55);
        var bytes = packetBuilder.getBytes();
        var expectedBytes = new byte[] { 2, 1, -102, 5, 6, 7, 8, 55 };

        assertArrayEquals(expectedBytes, bytes);
        assertEquals(4, TestComplexStructure.getAppendBytesMethodCallsCount());
    }

    @Test
    void writeStructureArray_fromArrayWithNulls() {
        var packetBuilder = new PacketBuilder((short) 8, PacketType.fromOrdinal(1), (short) 154);
        packetBuilder.writeStructureArray(
                new TestComplexStructure[] {
                        new TestComplexStructure(5),
                        null,
                        null,
                        new TestComplexStructure(8),
                },
                1
        );
        packetBuilder.writeByte(55);
        var bytes = packetBuilder.getBytes();
        var expectedBytes = new byte[] { 2, 1, -102, 5, 0, 0, 8, 55 };

        assertArrayEquals(expectedBytes, bytes);
        assertEquals(2, TestComplexStructure.getAppendBytesMethodCallsCount());
    }

    @Test
    void writeStructureArray_fromList() {
        var packetBuilder = new PacketBuilder((short) 8, PacketType.fromOrdinal(1), (short) 154);
        packetBuilder.writeStructureArray(
                List.of(
                        new TestComplexStructure(5),
                        new TestComplexStructure(6),
                        new TestComplexStructure(7),
                        new TestComplexStructure(8)
                ),
                1
        );
        packetBuilder.writeByte(55);
        var bytes = packetBuilder.getBytes();
        var expectedBytes = new byte[] { 2, 1, -102, 5, 6, 7, 8, 55 };

        assertArrayEquals(expectedBytes, bytes);
        assertEquals(4, TestComplexStructure.getAppendBytesMethodCallsCount());
    }

    private static class TestComplexStructure implements ComplexInstructionStructure {
        @Byte
        private final short structureField;

        private static int appendBytesMethodCallsCount = 0;

        private TestComplexStructure(int structureField) {
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

    private record TestUnsignedStructure(long value) implements UnsignedInstructionStructure {
        @Override
        public long getUnsignedValue() {
            return value;
        }
    }
}
