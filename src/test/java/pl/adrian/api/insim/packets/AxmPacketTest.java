/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package pl.adrian.api.insim.packets;

import org.junit.jupiter.api.Test;
import pl.adrian.api.insim.packets.enums.*;
import pl.adrian.api.common.flags.Flags;
import pl.adrian.api.insim.packets.flags.PmoFlag;
import pl.adrian.api.insim.packets.structures.objectinfo.*;
import pl.adrian.internal.insim.packets.util.PacketReader;
import pl.adrian.testutil.MockedInSimConnection;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static pl.adrian.testutil.AssertionUtils.*;

class AxmPacketTest {
    @Test
    void createAxmPacket() {
        var packet = new AxmPacket(
                8,
                PmoAction.ADD_OBJECTS,
                new Flags<>(PmoFlag.FILE_END, PmoFlag.AVOID_CHECK),
                List.of(
                        new AutocrossObjectInfo(15698, -2589, 4, false, ObjectType.CONE_GREEN, 56),
                        new ChalkLineInfo(-10054, 7093, 2, false, ChalkLineColour.WHITE, ObjectType.CHALK_AHEAD, 92),
                        new ConcretePillarInfo(5702, 9950, 10, 0.5f, 0.75f, 2.0f, 157),
                        new ConcreteRampInfo(25, -14068, 4, 8, 16, 0.5f, 129),
                        new ConcreteRampWallInfo(-509, -7490, 7, ConcreteObjectColour.BLUE, 2, 1.0f, 15),
                        new ConcreteShortSlabWallInfo(-12650, -4790, 3, ConcreteObjectColour.RED, 0.75f, 57, 90),
                        new ConcreteSlabInfo(849, -2005, 9, 4, 20, 95, 184),
                        new ConcreteSlabWallInfo(-7095, -6948, 12, ConcreteObjectColour.GREY, -3, -5, 47),
                        new ConcreteWallInfo(21479, -78, 4, ConcreteObjectColour.YELLOW, 8, 3.75f, 75),
                        new ConcreteWedgeInfo(8750, 3070, 10, ConcreteObjectColour.RED, 16, 22.5f, 3),
                        new InSimCheckpointInfo(90, -5974, 2, true, InSimCheckpointType.CHECKPOINT_3, 6, 90),
                        new InSimCircleInfo(10648, -3005, 5, true, 34, 3),
                        new RestrictedAreaInfo(9048, -48, 5, false, MarshallType.POINTING_LEFT, 28, 126),
                        new RouteCheckerInfo(16458, -6987, 25, true, 12, 9),
                        new SpecialControlObjectInfo(8596, -75, 38, true, SpecialControlObjectType.FINISH_LINE, 6, 38),
                        new SpecialControlObjectInfo(-3657, 7856, 12, false, SpecialControlObjectType.START_POSITION, 16, 48),
                        new StartLightsInfo(7543, 9543, 3, false, 3, 21),
                        new StartObjectInfo(-8552, -6952, 9, false, 2, ObjectType.PIT_START_POINT, 53),
                        new TyreObjectInfo(-8452, -7596, 15, true, TyreObjectColour.BLUE, ObjectType.TYRE_SINGLE_BIG, 6)
                )
        );
        var bytes = packet.getBytes();
        var expectedBytes = new byte[] {
                40, 54, 0, 19, 8, 1, 9, 0, 82, 61, -29, -11, 4, 0, 25, 56,
                -70, -40, -75, 27, 2, 0, 6, 92, 70, 22, -34, 38, 10, 121, -81, -99,
                25, 0, 12, -55, 4, 30, -83, -127, 3, -2, -66, -30, 7, 50, -79, 15,
                -106, -50, 74, -19, 3, -103, -78, 90, 81, 3, 43, -8, 9, -3, -84, -72,
                73, -28, -36, -28, 12, 0, -80, 47, -25, 83, -78, -1, 4, -21, -82, 75,
                46, 34, -2, 11, 10, 61, -77, 3, 90, 0, -86, -24, 2, -113, -4, 90,
                -104, 41, 67, -12, 5, -60, -3, 3, 88, 35, -48, -1, 5, 58, -2, 126,
                74, 64, -75, -28, 25, -104, -1, 8, -108, 33, -75, -1, 38, -116, 0, 38,

                -73, -15, -80, 30, 12, 0, 0, 48, 119, 29, 71, 37, 3, 3, -107, 21,
                -104, -34, -40, -28, 9, 1, -71, 53, -4, -34, 84, -30, 15, -125, 52, 6
        };

        assertArrayEquals(expectedBytes, bytes);
    }

    @Test
    void createAxmPacket_withGetZAction() {
        var packet = new AxmPacket(
                List.of(
                        new PositionObjectInfo(8675, -3654, 240),
                        new PositionObjectInfo(-521, 9654, 0)
                ),
                25
        );
        var bytes = packet.getBytes();
        var expectedBytes = new byte[] {
                6, 54, 25, 2, 0, 8, 0, 0, -29, 33, -70, -15, -16, -128, 0, 0,
                -9, -3, -74, 37, 0, -128, 0, 0
        };

        assertArrayEquals(expectedBytes, bytes);
    }

    @Test
    @SuppressWarnings("java:S5961")
    void readAxmPacket() {
        var headerBytes = new byte[] { 42, 54, -112 };
        var dataBytes = new byte[] {
                20, 27, 4, 1, 0, -82, -38, -58, -7, 15, -128, 40, 97,
                74, 14, -68, -34, 2, 3, 5, 10, 32, 29, 17, 3, 6, 105, -81, -71,
                80, -31, -98, -2, 32, 40, -83, 53, 0, 22, 32, 19, 12, -18, -79, 41,
                45, 49, -124, -39, 6, 113, -78, -34, -89, -67, 80, -31, 14, 77, -84, 28,
                -17, -4, -126, 49, 65, -8, -80, 75, -91, -63, 77, 3, 18, -109, -82, 72,
                81, 48, 6, -44, 8, 22, -77, -66, -114, 2, -7, -34, 16, -59, -4, 39,
                -56, -67, 16, -37, 2, 48, -3, 5, 25, 62, 40, -31, 6, 33, -2, -108,
                8, 33, -17, -4, 26, -44, -1, 2, 80, -31, -109, -2, 31, -102, 0, 84,
                70, 14, -4, -58, 2, 0, 0, 36, 15, -35, 44, 84, 10, 15, -107, 46,
                -91, 30, 70, 14, 20, -117, -72, 64, -74, -15, 80, 11, 8, 4, 50, 111,
                61, 29, 35, -25, 15, 95, -54, 68
        };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(165, PacketType.AXM, 144, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof AxmPacket);

        var castedReadPacket = (AxmPacket) readPacket;

        assertPacketHeaderEquals(168, PacketType.AXM, 144, castedReadPacket);
        assertEquals(20, castedReadPacket.getNumO());
        assertEquals(PmoAction.TINY_AXM, castedReadPacket.getPmoAction());
        assertFlagsEqual(PmoFlag.class, Set.of(PmoFlag.FILE_END), castedReadPacket.getPmoFlags());

        assertTrue(castedReadPacket.getInfo().get(0) instanceof AutocrossObjectInfo);
        var object1 = (AutocrossObjectInfo) castedReadPacket.getInfo().get(0);
        assertEquals(-9554, object1.getX());
        assertEquals(-1594, object1.getY());
        assertEquals(15, object1.getZByte());
        assertTrue(object1.isFloating());
        assertEquals(ObjectType.CONE_PTR_RED, object1.getIndex());
        assertEquals(97, object1.getHeading());

        assertTrue(castedReadPacket.getInfo().get(1) instanceof ChalkLineInfo);
        var object2 = (ChalkLineInfo) castedReadPacket.getInfo().get(1);
        assertEquals(3658, object2.getX());
        assertEquals(-8516, object2.getY());
        assertEquals(2, object2.getZByte());
        assertFalse(object2.isFloating());
        assertEquals(ChalkLineColour.YELLOW, object2.getColour());
        assertEquals(ObjectType.CHALK_LINE2, object2.getIndex());
        assertEquals(10, object2.getHeading());

        assertTrue(castedReadPacket.getInfo().get(2) instanceof ConcretePillarInfo);
        var object3 = (ConcretePillarInfo) castedReadPacket.getInfo().get(2);
        assertEquals(7456, object3.getX());
        assertEquals(785, object3.getY());
        assertEquals(6, object3.getZByte());
        assertTrue(object3.isFloating());
        assertEquals(0.5f, object3.getSizeX());
        assertEquals(0.75f, object3.getSizeY());
        assertEquals(1.75f, object3.getHeight());
        assertEquals(ObjectType.CONCRETE_PILLAR, object3.getIndex());
        assertEquals(185, object3.getHeading());

        assertTrue(castedReadPacket.getInfo().get(3) instanceof ConcreteRampInfo);
        var object4 = (ConcreteRampInfo) castedReadPacket.getInfo().get(3);
        assertEquals(-7856, object4.getX());
        assertEquals(-354, object4.getY());
        assertEquals(32, object4.getZByte());
        assertTrue(object4.isFloating());
        assertEquals(2, object4.getWidth());
        assertEquals(8, object4.getLength());
        assertEquals(0.75f, object4.getHeight());
        assertEquals(ObjectType.CONCRETE_RAMP, object4.getIndex());
        assertEquals(53, object4.getHeading());

        assertTrue(castedReadPacket.getInfo().get(4) instanceof ConcreteRampWallInfo);
        var object5 = (ConcreteRampWallInfo) castedReadPacket.getInfo().get(4);
        assertEquals(5632, object5.getX());
        assertEquals(4896, object5.getY());
        assertEquals(12, object5.getZByte());
        assertTrue(object5.isFloating());
        assertEquals(ConcreteObjectColour.BLUE, object5.getColour());
        assertEquals(16, object5.getLength());
        assertEquals(3.75f, object5.getHeight());
        assertEquals(ObjectType.CONCRETE_RAMP_WALL, object5.getIndex());
        assertEquals(41, object5.getHeading());

        assertTrue(castedReadPacket.getInfo().get(5) instanceof ConcreteShortSlabWallInfo);
        var object6 = (ConcreteShortSlabWallInfo) castedReadPacket.getInfo().get(5);
        assertEquals(12589, object6.getX());
        assertEquals(-9852, object6.getY());
        assertEquals(6, object6.getZByte());
        assertTrue(object6.isFloating());
        assertEquals(ConcreteObjectColour.RED, object6.getColour());
        assertEquals(0.25f, object6.getSizeY());
        assertEquals(42, object6.getPitch());
        assertEquals(ObjectType.CONCRETE_SHORT_SLAB_WALL, object6.getIndex());
        assertEquals(222, object6.getHeading());

        assertTrue(castedReadPacket.getInfo().get(6) instanceof ConcreteSlabInfo);
        var object7 = (ConcreteSlabInfo) castedReadPacket.getInfo().get(6);
        assertEquals(-16985, object7.getX());
        assertEquals(-7856, object7.getY());
        assertEquals(14, object7.getZByte());
        assertTrue(object7.isFloating());
        assertEquals(4, object7.getWidth());
        assertEquals(16, object7.getLength());
        assertEquals(24, object7.getPitch());
        assertEquals(ObjectType.CONCRETE_SLAB, object7.getIndex());
        assertEquals(28, object7.getHeading());

        assertTrue(castedReadPacket.getInfo().get(7) instanceof ConcreteSlabWallInfo);
        var object8 = (ConcreteSlabWallInfo) castedReadPacket.getInfo().get(7);
        assertEquals(-785, object8.getX());
        assertEquals(12674, object8.getY());
        assertEquals(65, object8.getZByte());
        assertTrue(object8.isFloating());
        assertEquals(ConcreteObjectColour.GREY, object8.getColour());
        assertEquals(8, object8.getLength());
        assertEquals(90, object8.getPitch());
        assertEquals(ObjectType.CONCRETE_SLAB_WALL, object8.getIndex());
        assertEquals(75, object8.getHeading());

        assertTrue(castedReadPacket.getInfo().get(8) instanceof ConcreteWallInfo);
        var object9 = (ConcreteWallInfo) castedReadPacket.getInfo().get(8);
        assertEquals(-15963, object9.getX());
        assertEquals(845, object9.getY());
        assertEquals(18, object9.getZByte());
        assertTrue(object9.isFloating());
        assertEquals(ConcreteObjectColour.YELLOW, object9.getColour());
        assertEquals(2, object9.getLength());
        assertEquals(2.5f, object9.getHeight());
        assertEquals(ObjectType.CONCRETE_WALL, object9.getIndex());
        assertEquals(72, object9.getHeading());

        assertTrue(castedReadPacket.getInfo().get(9) instanceof ConcreteWedgeInfo);
        var object10 = (ConcreteWedgeInfo) castedReadPacket.getInfo().get(9);
        assertEquals(12369, object10.getX());
        assertEquals(-11258, object10.getY());
        assertEquals(8, object10.getZByte());
        assertTrue(object10.isFloating());
        assertEquals(ConcreteObjectColour.BLUE, object10.getColour());
        assertEquals(4, object10.getLength());
        assertEquals(11.25f, object10.getAngle());
        assertEquals(ObjectType.CONCRETE_WEDGE, object10.getIndex());
        assertEquals(190, object10.getHeading());

        assertTrue(castedReadPacket.getInfo().get(10) instanceof InSimCheckpointInfo);
        var object11 = (InSimCheckpointInfo) castedReadPacket.getInfo().get(10);
        assertEquals(654, object11.getX());
        assertEquals(-8455, object11.getY());
        assertEquals(16, object11.getZByte());
        assertTrue(object11.isFloating());
        assertEquals(InSimCheckpointType.CHECKPOINT_1, object11.getCheckpointIndex());
        assertEquals(34, object11.getWidth());
        assertEquals(ObjectType.MARSH_IS_CP, object11.getIndex());
        assertEquals(39, object11.getHeading());

        assertTrue(castedReadPacket.getInfo().get(11) instanceof InSimCircleInfo);
        var object12 = (InSimCircleInfo) castedReadPacket.getInfo().get(11);
        assertEquals(-16952, object12.getX());
        assertEquals(-9456, object12.getY());
        assertEquals(2, object12.getZByte());
        assertFalse(object12.isFloating());
        assertEquals(24, object12.getDiameter());
        assertEquals(ObjectType.MARSH_IS_AREA, object12.getIndex());
        assertEquals(5, object12.getCircleIndex());

        assertTrue(castedReadPacket.getInfo().get(12) instanceof RestrictedAreaInfo);
        var object13 = (RestrictedAreaInfo) castedReadPacket.getInfo().get(12);
        assertEquals(15897, object13.getX());
        assertEquals(-7896, object13.getY());
        assertEquals(6, object13.getZByte());
        assertFalse(object13.isFloating());
        assertEquals(MarshallType.STANDING, object13.getMarshallType());
        assertEquals(16, object13.getDiameter());
        assertEquals(ObjectType.MARSH_MARSHAL, object13.getIndex());
        assertEquals(148, object13.getHeading());

        assertTrue(castedReadPacket.getInfo().get(13) instanceof RouteCheckerInfo);
        var object14 = (RouteCheckerInfo) castedReadPacket.getInfo().get(13);
        assertEquals(8456, object14.getX());
        assertEquals(-785, object14.getY());
        assertEquals(26, object14.getZByte());
        assertTrue(object14.isFloating());
        assertEquals(42, object14.getDiameter());
        assertEquals(ObjectType.MARSH_ROUTE, object14.getIndex());
        assertEquals(3, object14.getRouteIndex());

        assertTrue(castedReadPacket.getInfo().get(14) instanceof SpecialControlObjectInfo);
        var object15 = (SpecialControlObjectInfo) castedReadPacket.getInfo().get(14);
        assertEquals(-7856, object15.getX());
        assertEquals(-365, object15.getY());
        assertEquals(31, object15.getZByte());
        assertTrue(object15.isFloating());
        assertEquals(SpecialControlObjectType.CHECKPOINT_2, object15.getType());
        assertEquals(12, object15.getWidth());
        assertEquals(ObjectType.NULL, object15.getIndex());
        assertEquals(84, object15.getHeading());

        assertTrue(castedReadPacket.getInfo().get(15) instanceof SpecialControlObjectInfo);
        var object16 = (SpecialControlObjectInfo) castedReadPacket.getInfo().get(15);
        assertEquals(3654, object16.getX());
        assertEquals(-14596, object16.getY());
        assertEquals(2, object16.getZByte());
        assertFalse(object16.isFloating());
        assertEquals(SpecialControlObjectType.START_POSITION, object16.getType());
        assertEquals(0, object16.getWidth());
        assertEquals(ObjectType.NULL, object16.getIndex());
        assertEquals(36, object16.getHeading());

        assertTrue(castedReadPacket.getInfo().get(16) instanceof StartLightsInfo);
        var object17 = (StartLightsInfo) castedReadPacket.getInfo().get(16);
        assertEquals(-8945, object17.getX());
        assertEquals(21548, object17.getY());
        assertEquals(10, object17.getZByte());
        assertFalse(object17.isFloating());
        assertEquals(15, object17.getIdentifier());
        assertEquals(ObjectType.START_LIGHTS, object17.getIndex());
        assertEquals(46, object17.getHeading());

        assertTrue(castedReadPacket.getInfo().get(17) instanceof StartObjectInfo);
        var object18 = (StartObjectInfo) castedReadPacket.getInfo().get(17);
        assertEquals(7845, object18.getX());
        assertEquals(3654, object18.getY());
        assertEquals(20, object18.getZByte());
        assertTrue(object18.isFloating());
        assertEquals(12, object18.getPositionIndex());
        assertEquals(ObjectType.START_POSITION, object18.getIndex());
        assertEquals(64, object18.getHeading());

        assertTrue(castedReadPacket.getInfo().get(18) instanceof TyreObjectInfo);
        var object19 = (TyreObjectInfo) castedReadPacket.getInfo().get(18);
        assertEquals(-3658, object19.getX());
        assertEquals(2896, object19.getY());
        assertEquals(8, object19.getZByte());
        assertFalse(object19.isFloating());
        assertEquals(TyreObjectColour.GREEN, object19.getColour());
        assertEquals(ObjectType.TYRE_STACK3, object19.getIndex());
        assertEquals(111, object19.getHeading());

        assertTrue(castedReadPacket.getInfo().get(19) instanceof UnknownObjectInfo);
        var object20 = (UnknownObjectInfo) castedReadPacket.getInfo().get(19);
        assertEquals(7485, object20.getX());
        assertEquals(-6365, object20.getY());
        assertEquals(15, object20.getZByte());
        assertFalse(object20.isFloating());
        assertEquals(95, object20.getFlags());
        assertEquals(ObjectType.NULL, object20.getIndex());
        assertEquals(68, object20.getHeading());
    }

    @Test
    void readAxmPacket_withAxmPositionAction() {
        var headerBytes = new byte[] { 4, 54, 0 };
        var dataBytes = new byte[] {
                1, 2, 7, 0, 0, 84, -8, 8, -1, 17, 0, 0, -128
        };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(13, PacketType.AXM, 0, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof AxmPacket);

        var castedReadPacket = (AxmPacket) readPacket;

        assertPacketHeaderEquals(16, PacketType.AXM, 0, castedReadPacket);
        assertEquals(1, castedReadPacket.getNumO());
        assertEquals(2, castedReadPacket.getUcid());
        assertEquals(PmoAction.POSITION, castedReadPacket.getPmoAction());
        assertFlagsEqual(PmoFlag.class, Set.of(), castedReadPacket.getPmoFlags());

        assertTrue(castedReadPacket.getInfo().get(0) instanceof UnknownObjectInfo);
        var object1 = (UnknownObjectInfo) castedReadPacket.getInfo().get(0);
        assertEquals(-1964, object1.getX());
        assertEquals(-248, object1.getY());
        assertEquals(17, object1.getZByte());
        assertFalse(object1.isFloating());
        assertEquals(0, object1.getFlags());
        assertEquals(ObjectType.NULL, object1.getIndex());
        assertEquals(128, object1.getHeading());
    }

    @Test
    void requestAxmPacketForAllLayoutObjects() throws IOException {
        var inSimConnectionMock = new MockedInSimConnection();

        AxmPacket.request(inSimConnectionMock)
                .forAllLayoutObjects()
                .listen((connection, packet) -> {});

        var expectedRequestPacketBytes = new byte[] { 1, 3, 0, 25 };
        assertRequestPacketBytesEqual(expectedRequestPacketBytes, inSimConnectionMock.assertAndGetSentPacketBytes());
    }

    @Test
    void requestAxmPacketForConnectionLayoutEditorSelection() throws IOException {
        var inSimConnectionMock = new MockedInSimConnection();

        AxmPacket.request(inSimConnectionMock)
                .forConnectionLayoutEditorSelection(36)
                .listen((connection, packet) -> {});

        var expectedRequestPacketBytes = new byte[] { 2, 61, 0, 1, 36, 0, 0, 0 };
        assertRequestPacketBytesEqual(expectedRequestPacketBytes, inSimConnectionMock.assertAndGetSentPacketBytes());
    }
}
