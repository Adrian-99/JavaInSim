package pl.adrian.api.packets.structures;

import org.junit.jupiter.api.Test;
import pl.adrian.api.packets.enums.ControlObjectType;
import pl.adrian.api.packets.enums.MarshallType;
import pl.adrian.api.packets.enums.ObjectType;
import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.internal.packets.util.PacketBuilder;

import static org.junit.jupiter.api.Assertions.*;

class ObjectInfoTest {
    @Test
    void readObjectInfo_forRestrictedArea() {
        var objectInfo = new ObjectInfo((short) 5638, (short) -1561, (short) 2, (short) 169, (short) 255, (short) 53);

        assertEquals(5638, objectInfo.getX());
        assertEquals(-1561, objectInfo.getY());
        assertEquals(2, objectInfo.getZByte());
        assertTrue(objectInfo.getRestrictedAreaInfo().isPresent());
        assertTrue(objectInfo.getRouteCheckerInfo().isEmpty());
        assertFalse(objectInfo.isUnknownObject());
        assertTrue(objectInfo.getControlObjectInfo().isEmpty());
        assertTrue(objectInfo.getAutocrossObjectInfo().isEmpty());

        var restrictedAreaInfo = objectInfo.getRestrictedAreaInfo().get();
        assertEquals(MarshallType.STANDING, restrictedAreaInfo.getMarshallType());
        assertEquals(10, restrictedAreaInfo.getRadius());
        assertEquals(53, restrictedAreaInfo.getHeading());
    }

    @Test
    void readObjectInfo_forRouteChecker() {
        var objectInfo = new ObjectInfo((short) -15964, (short) -206, (short) -3, (short) 60, (short) 255, (short) 129);

        assertEquals(-15964, objectInfo.getX());
        assertEquals(-206, objectInfo.getY());
        assertEquals(-3, objectInfo.getZByte());
        assertTrue(objectInfo.getRestrictedAreaInfo().isEmpty());
        assertTrue(objectInfo.getRouteCheckerInfo().isPresent());
        assertFalse(objectInfo.isUnknownObject());
        assertTrue(objectInfo.getControlObjectInfo().isEmpty());
        assertTrue(objectInfo.getAutocrossObjectInfo().isEmpty());

        var routeCheckerInfo = objectInfo.getRouteCheckerInfo().get();
        assertEquals(15, routeCheckerInfo.getRadius());
        assertEquals(129, routeCheckerInfo.getRouteIndex());
    }

    @Test
    void readObjectInfo_forUnknownObject() {
        var objectInfo = new ObjectInfo((short) 1784, (short) 91, (short) 5, (short) 136, (short) 202, (short) 25);

        assertEquals(1784, objectInfo.getX());
        assertEquals(91, objectInfo.getY());
        assertEquals(5, objectInfo.getZByte());
        assertTrue(objectInfo.getRestrictedAreaInfo().isEmpty());
        assertTrue(objectInfo.getRouteCheckerInfo().isEmpty());
        assertTrue(objectInfo.isUnknownObject());
        assertTrue(objectInfo.getControlObjectInfo().isEmpty());
        assertTrue(objectInfo.getAutocrossObjectInfo().isEmpty());
    }

    @Test
    void readObjectInfo_forControlObject() {
        var objectInfo = new ObjectInfo((short) -54, (short) 849, (short) 10, (short) 230, (short) 0, (short) 47);

        assertEquals(-54, objectInfo.getX());
        assertEquals(849, objectInfo.getY());
        assertEquals(10, objectInfo.getZByte());
        assertTrue(objectInfo.getRestrictedAreaInfo().isEmpty());
        assertTrue(objectInfo.getRouteCheckerInfo().isEmpty());
        assertFalse(objectInfo.isUnknownObject());
        assertTrue(objectInfo.getControlObjectInfo().isPresent());
        assertTrue(objectInfo.getAutocrossObjectInfo().isEmpty());

        var controlObjectInfo = objectInfo.getControlObjectInfo().get();
        assertEquals(ControlObjectType.CHECKPOINT_2, controlObjectInfo.getType());
        assertEquals(25, controlObjectInfo.getWidth());
        assertEquals(ObjectType.UNKNOWN, controlObjectInfo.getIndex());
        assertEquals(47, controlObjectInfo.getHeading());
    }

    @Test
    void readObjectInfo_forControlObjectAsStartPosition() {
        var objectInfo = new ObjectInfo((short) -54, (short) 849, (short) 10, (short) 128, (short) 184, (short) 47);

        assertEquals(-54, objectInfo.getX());
        assertEquals(849, objectInfo.getY());
        assertEquals(10, objectInfo.getZByte());
        assertTrue(objectInfo.getRestrictedAreaInfo().isEmpty());
        assertTrue(objectInfo.getRouteCheckerInfo().isEmpty());
        assertFalse(objectInfo.isUnknownObject());
        assertTrue(objectInfo.getControlObjectInfo().isPresent());
        assertTrue(objectInfo.getAutocrossObjectInfo().isEmpty());

        var controlObjectInfo = objectInfo.getControlObjectInfo().get();
        assertEquals(ControlObjectType.START_POSITION, controlObjectInfo.getType());
        assertEquals(0, controlObjectInfo.getWidth());
        assertEquals(ObjectType.START_POSITION, controlObjectInfo.getIndex());
        assertEquals(47, controlObjectInfo.getHeading());
    }

    @Test
    void readObjectInfo_forAutocrossObject() {
        var objectInfo = new ObjectInfo((short) -378, (short) 15967, (short) 7, (short) 3, (short) 50, (short) 199);

        assertEquals(-378, objectInfo.getX());
        assertEquals(15967, objectInfo.getY());
        assertEquals(7, objectInfo.getZByte());
        assertTrue(objectInfo.getRestrictedAreaInfo().isEmpty());
        assertTrue(objectInfo.getRouteCheckerInfo().isEmpty());
        assertFalse(objectInfo.isUnknownObject());
        assertTrue(objectInfo.getControlObjectInfo().isEmpty());
        assertTrue(objectInfo.getAutocrossObjectInfo().isPresent());

        var autocrossObjectInfo = objectInfo.getAutocrossObjectInfo().get();
        assertEquals(3, autocrossObjectInfo.getColour());
        assertEquals(ObjectType.TYRE_STACK3, autocrossObjectInfo.getIndex());
        assertEquals(199, autocrossObjectInfo.getHeading());
    }

    @Test
    void writeObjectInfo_forRestrictedArea() {
        var objectInfo = new ObjectInfo(
                1547,
                -3063,
                15,
                new RestrictedAreaInfo(MarshallType.POINTING_RIGHT, 3, 97)
        );
        var packetBuilder = new PacketBuilder((short) 12, PacketType.NONE, (short) 0);
        packetBuilder.writeByte(0);
        objectInfo.appendBytes(packetBuilder);

        var expectedBytes = new byte[] {
                3, 0, 0, 0, 11, 6, 9, -12, 15, -113, -1, 97
        };

        assertArrayEquals(expectedBytes, packetBuilder.getBytes());
    }

    @Test
    void writeObjectInfo_forRouteChecker() {
        var objectInfo = new ObjectInfo(
                1547,
                -3063,
                15,
                new RouteCheckerInfo(8, 67)
        );
        var packetBuilder = new PacketBuilder((short) 12, PacketType.NONE, (short) 0);
        packetBuilder.writeByte(0);
        objectInfo.appendBytes(packetBuilder);

        var expectedBytes = new byte[] {
                3, 0, 0, 0, 11, 6, 9, -12, 15, 32, -1, 67
        };

        assertArrayEquals(expectedBytes, packetBuilder.getBytes());
    }

    @Test
    void writeObjectInfo_forUnknownObject() {
        var objectInfo = new ObjectInfo((short) 1547, (short) -3063, (short) 15, (short) 151, (short) 231, (short) 94);
        var packetBuilder = new PacketBuilder((short) 12, PacketType.NONE, (short) 0);
        packetBuilder.writeByte(0);
        objectInfo.appendBytes(packetBuilder);

        var expectedBytes = new byte[] {
                3, 0, 0, 0, 11, 6, 9, -12, 15, 0, -64, 0
        };

        assertArrayEquals(expectedBytes, packetBuilder.getBytes());
    }

    @Test
    void writeObjectInfo_forControlObject() {
        var objectInfo = new ObjectInfo(
                1547,
                -3063,
                15,
                new ControlObjectInfo(ControlObjectType.FINISH_LINE, 13, ObjectType.UNKNOWN, 37)
        );
        var packetBuilder = new PacketBuilder((short) 12, PacketType.NONE, (short) 0);
        packetBuilder.writeByte(0);
        objectInfo.appendBytes(packetBuilder);

        var expectedBytes = new byte[] {
                3, 0, 0, 0, 11, 6, 9, -12, 15, -76, 0, 37
        };

        assertArrayEquals(expectedBytes, packetBuilder.getBytes());
    }

    @Test
    void writeObjectInfo_forControlObjectAsStartPosition() {
        var objectInfo = new ObjectInfo(
                1547,
                -3063,
                15,
                new ControlObjectInfo(ControlObjectType.START_POSITION, 1, ObjectType.UNKNOWN, 190)
        );
        var packetBuilder = new PacketBuilder((short) 12, PacketType.NONE, (short) 0);
        packetBuilder.writeByte(0);
        objectInfo.appendBytes(packetBuilder);

        var expectedBytes = new byte[] {
                3, 0, 0, 0, 11, 6, 9, -12, 15, -128, 0, -66
        };

        assertArrayEquals(expectedBytes, packetBuilder.getBytes());
    }

    @Test
    void writeObjectInfo_forAutocrossObject() {
        var objectInfo = new ObjectInfo(
                1547,
                -3063,
                15,
                new AutocrossObjectInfo(4, ObjectType.TYRE_SINGLE_BIG, 222)
        );
        var packetBuilder = new PacketBuilder((short) 12, PacketType.NONE, (short) 0);
        packetBuilder.writeByte(0);
        objectInfo.appendBytes(packetBuilder);

        var expectedBytes = new byte[] {
                3, 0, 0, 0, 11, 6, 9, -12, 15, 4, 52, -34
        };

        assertArrayEquals(expectedBytes, packetBuilder.getBytes());
    }
}
