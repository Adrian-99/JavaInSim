//package pl.adrian.api.packets.structures;
//
//import org.junit.jupiter.api.Test;
//import pl.adrian.api.packets.enums.ControlObjectType;
//import pl.adrian.api.packets.enums.MarshallType;
//import pl.adrian.api.packets.enums.ObjectType;
//import pl.adrian.api.packets.enums.PacketType;
//import pl.adrian.internal.packets.structures.ObjectInfo;
//import pl.adrian.internal.packets.structures.UnknownObjectInfo;
//import pl.adrian.internal.packets.util.PacketBuilder;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class ObjectInfoTest {
//    @Test
//    void readObjectInfo_forRestrictedArea() {
//        var restrictedAreaInfo = new RestrictedAreaInfo((short) 5638, (short) -1561, (short) 2, (short) 169, (short) 53);
//
//        assertEquals(5638, restrictedAreaInfo.getX());
//        assertEquals(-1561, restrictedAreaInfo.getY());
//        assertEquals(2, restrictedAreaInfo.getZByte());
//
//        assertTrue(restrictedAreaInfo.asRestrictedAreaInfo().isPresent());
//        assertEquals(restrictedAreaInfo, restrictedAreaInfo.asRestrictedAreaInfo().get());
//        assertTrue(restrictedAreaInfo.asRouteCheckerInfo().isEmpty());
//        assertTrue(restrictedAreaInfo.asUnknownObjectInfo().isEmpty());
//        assertTrue(restrictedAreaInfo.asControlObjectInfo().isEmpty());
//        assertTrue(restrictedAreaInfo.asAutocrossObjectInfo().isEmpty());
//
//        assertEquals(MarshallType.STANDING, restrictedAreaInfo.getMarshallType());
//        assertEquals(10, restrictedAreaInfo.getRadius());
//        assertEquals(53, restrictedAreaInfo.getHeading());
//    }
//
//    @Test
//    void readObjectInfo_forRouteChecker() {
//        var routeCheckerInfo = new RouteCheckerInfo((short) -15964, (short) -206, (short) -3, (short) 60, (short) 129);
//
//        assertEquals(-15964, routeCheckerInfo.getX());
//        assertEquals(-206, routeCheckerInfo.getY());
//        assertEquals(-3, routeCheckerInfo.getZByte());
//
//        assertTrue(routeCheckerInfo.asRestrictedAreaInfo().isEmpty());
//        assertTrue(routeCheckerInfo.asRouteCheckerInfo().isPresent());
//        assertEquals(routeCheckerInfo, routeCheckerInfo.asRouteCheckerInfo().get());
//        assertTrue(routeCheckerInfo.asUnknownObjectInfo().isEmpty());
//        assertTrue(routeCheckerInfo.asControlObjectInfo().isEmpty());
//        assertTrue(routeCheckerInfo.asAutocrossObjectInfo().isEmpty());
//
//        assertEquals(15, routeCheckerInfo.getRadius());
//        assertEquals(129, routeCheckerInfo.getRouteIndex());
//    }
//
//    @Test
//    void readObjectInfo_forUnknownObject() {
//        var objectInfo = new UnknownObjectInfo((short) 1784, (short) 91, (short) 5, (short) 136, (short) 202, (short) 25);
//
//        assertEquals(1784, objectInfo.getX());
//        assertEquals(91, objectInfo.getY());
//        assertEquals(5, objectInfo.getZByte());
//
//        assertTrue(objectInfo.asRestrictedAreaInfo().isEmpty());
//        assertTrue(objectInfo.asRouteCheckerInfo().isEmpty());
//        assertTrue(objectInfo.asUnknownObjectInfo().isPresent());
//        assertEquals(objectInfo, objectInfo.asUnknownObjectInfo().get());
//        assertTrue(objectInfo.asControlObjectInfo().isEmpty());
//        assertTrue(objectInfo.asAutocrossObjectInfo().isEmpty());
//    }
//
//    @Test
//    void readObjectInfo_forControlObject() {
//        var objectInfo = new ObjectInfo((short) -54, (short) 849, (short) 10, (short) 230, (short) 0, (short) 47);
//
//        assertEquals(-54, objectInfo.getX());
//        assertEquals(849, objectInfo.getY());
//        assertEquals(10, objectInfo.getZByte());
//        assertTrue(objectInfo.asRestrictedAreaInfo().isEmpty());
//        assertTrue(objectInfo.asRouteCheckerInfo().isEmpty());
//        assertFalse(objectInfo.isUnknownObject());
//        assertTrue(objectInfo.asControlObjectInfo().isPresent());
//        assertTrue(objectInfo.asAutocrossObjectInfo().isEmpty());
//
//        var controlObjectInfo = objectInfo.asControlObjectInfo().get();
//        assertEquals(ControlObjectType.CHECKPOINT_2, controlObjectInfo.getType());
//        assertEquals(25, controlObjectInfo.getWidth());
//        assertEquals(ObjectType.UNKNOWN, controlObjectInfo.getIndex());
//        assertEquals(47, controlObjectInfo.getHeading());
//    }
//
//    @Test
//    void readObjectInfo_forControlObjectAsStartPosition() {
//        var objectInfo = new ObjectInfo((short) -54, (short) 849, (short) 10, (short) 128, (short) 184, (short) 47);
//
//        assertEquals(-54, objectInfo.getX());
//        assertEquals(849, objectInfo.getY());
//        assertEquals(10, objectInfo.getZByte());
//        assertTrue(objectInfo.asRestrictedAreaInfo().isEmpty());
//        assertTrue(objectInfo.asRouteCheckerInfo().isEmpty());
//        assertFalse(objectInfo.isUnknownObject());
//        assertTrue(objectInfo.asControlObjectInfo().isPresent());
//        assertTrue(objectInfo.asAutocrossObjectInfo().isEmpty());
//
//        var controlObjectInfo = objectInfo.asControlObjectInfo().get();
//        assertEquals(ControlObjectType.START_POSITION, controlObjectInfo.getType());
//        assertEquals(0, controlObjectInfo.getWidth());
//        assertEquals(ObjectType.START_POSITION, controlObjectInfo.getIndex());
//        assertEquals(47, controlObjectInfo.getHeading());
//    }
//
//    @Test
//    void readObjectInfo_forAutocrossObject() {
//        var objectInfo = new ObjectInfo((short) -378, (short) 15967, (short) 7, (short) 3, (short) 50, (short) 199);
//
//        assertEquals(-378, objectInfo.getX());
//        assertEquals(15967, objectInfo.getY());
//        assertEquals(7, objectInfo.getZByte());
//        assertTrue(objectInfo.asRestrictedAreaInfo().isEmpty());
//        assertTrue(objectInfo.asRouteCheckerInfo().isEmpty());
//        assertFalse(objectInfo.isUnknownObject());
//        assertTrue(objectInfo.asControlObjectInfo().isEmpty());
//        assertTrue(objectInfo.asAutocrossObjectInfo().isPresent());
//
//        var autocrossObjectInfo = objectInfo.asAutocrossObjectInfo().get();
//        assertEquals(3, autocrossObjectInfo.getColour());
//        assertEquals(ObjectType.TYRE_STACK3, autocrossObjectInfo.getIndex());
//        assertEquals(199, autocrossObjectInfo.getHeading());
//    }
//
//    @Test
//    void writeObjectInfo_forRestrictedArea() {
//        var restrictedAreaInfo = new RestrictedAreaInfo(
//                1547,
//                -3063,
//                15,
//                MarshallType.POINTING_RIGHT,
//                3,
//                97
//        );
//        var packetBuilder = new PacketBuilder((short) 12, PacketType.NONE, (short) 0);
//        packetBuilder.writeByte(0)
//                .writeStructure(restrictedAreaInfo, 8);
//
//        var expectedBytes = new byte[] {
//                3, 0, 0, 0, 11, 6, 9, -12, 15, -113, -1, 97
//        };
//
//        assertArrayEquals(expectedBytes, packetBuilder.getBytes());
//    }
//
//    @Test
//    void writeObjectInfo_forRouteChecker() {
//        var routeCheckerInfo = new RouteCheckerInfo(1547, -3063, 15, 8, 67);
//        var packetBuilder = new PacketBuilder((short) 12, PacketType.NONE, (short) 0);
//        packetBuilder.writeByte(0)
//                .writeStructure(routeCheckerInfo, 8);
//
//        var expectedBytes = new byte[] {
//                3, 0, 0, 0, 11, 6, 9, -12, 15, 32, -1, 67
//        };
//
//        assertArrayEquals(expectedBytes, packetBuilder.getBytes());
//    }
//
//    @Test
//    void writeObjectInfo_forUnknownObject() {
//        var objectInfo = new ObjectInfo((short) 1547, (short) -3063, (short) 15, (short) 151, (short) 231, (short) 94);
//        var packetBuilder = new PacketBuilder((short) 12, PacketType.NONE, (short) 0);
//        packetBuilder.writeByte(0);
//        objectInfo.appendBytes(packetBuilder);
//
//        var expectedBytes = new byte[] {
//                3, 0, 0, 0, 11, 6, 9, -12, 15, 0, -64, 0
//        };
//
//        assertArrayEquals(expectedBytes, packetBuilder.getBytes());
//    }
//
//    @Test
//    void writeObjectInfo_forControlObject() {
//        var objectInfo = new ObjectInfo(
//                1547,
//                -3063,
//                15,
//                new ControlObjectInfo(ControlObjectType.FINISH_LINE, 13, ObjectType.UNKNOWN, 37)
//        );
//        var packetBuilder = new PacketBuilder((short) 12, PacketType.NONE, (short) 0);
//        packetBuilder.writeByte(0);
//        objectInfo.appendBytes(packetBuilder);
//
//        var expectedBytes = new byte[] {
//                3, 0, 0, 0, 11, 6, 9, -12, 15, -76, 0, 37
//        };
//
//        assertArrayEquals(expectedBytes, packetBuilder.getBytes());
//    }
//
//    @Test
//    void writeObjectInfo_forControlObjectAsStartPosition() {
//        var objectInfo = new ObjectInfo(
//                1547,
//                -3063,
//                15,
//                new ControlObjectInfo(ControlObjectType.START_POSITION, 1, ObjectType.UNKNOWN, 190)
//        );
//        var packetBuilder = new PacketBuilder((short) 12, PacketType.NONE, (short) 0);
//        packetBuilder.writeByte(0);
//        objectInfo.appendBytes(packetBuilder);
//
//        var expectedBytes = new byte[] {
//                3, 0, 0, 0, 11, 6, 9, -12, 15, -128, 0, -66
//        };
//
//        assertArrayEquals(expectedBytes, packetBuilder.getBytes());
//    }
//
//    @Test
//    void writeObjectInfo_forAutocrossObject() {
//        var objectInfo = new ObjectInfo(
//                1547,
//                -3063,
//                15,
//                new AutocrossObjectInfo(4, ObjectType.TYRE_SINGLE_BIG, 222)
//        );
//        var packetBuilder = new PacketBuilder((short) 12, PacketType.NONE, (short) 0);
//        packetBuilder.writeByte(0);
//        objectInfo.appendBytes(packetBuilder);
//
//        var expectedBytes = new byte[] {
//                3, 0, 0, 0, 11, 6, 9, -12, 15, 4, 52, -34
//        };
//
//        assertArrayEquals(expectedBytes, packetBuilder.getBytes());
//    }
//}
