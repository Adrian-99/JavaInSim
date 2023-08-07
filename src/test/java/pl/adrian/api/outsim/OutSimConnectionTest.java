/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package pl.adrian.api.outsim;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.adrian.api.outsim.flags.OutSimOpts;
import pl.adrian.api.common.flags.Flags;
import pl.adrian.testutil.LfsUdpMock;

import java.io.IOException;
import java.net.SocketException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.adrian.testutil.AssertionUtils.assertConditionMet;

class OutSimConnectionTest {
    private static final int PORT = 30003;
    private LfsUdpMock lfsUdpMock;

    @BeforeEach
    void beforeEach() throws SocketException {
        lfsUdpMock = new LfsUdpMock(PORT);
    }

    @AfterEach
    void afterEach() {
        lfsUdpMock.close();
    }

    @Test
    @SuppressWarnings("java:S5961")
    void receiveFullOutSimPack2() throws IOException {
        final var outSimPacketBytes = new byte[] {
                76, 70, 83, 84, 65, 0, 0, 0, 105, 92, 13, 0, 27, 47, 93, 61,
                50, 85, -64, -66, -83, -27, 121, 58, -33, 79, -119, 66, -35, 36, -72, -64,
                -77, -46, 57, -64, -115, -105, -10, 63, 92, 56, -64, -66, -127, -107, 3, 62,
                114, 121, 42, 65, 10, -41, 35, 64, -84, -59, 39, 60, -75, -18, 1, 0,
                69, -128, -2, -1, 111, 14, 0, 0, 0, 0, -128, 63, -100, -60, 32, 62,
                68, -117, -52, -65, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0,
                41, 92, 95, 64, -106, -45, -114, 67, -3, 75, -99, 68, 68, -43, -94, 68,
                23, -102, 91, 63, -95, -8, 39, 64, 24, -78, -128, -65, 92, 56, -64, 62,
                17, 63, -108, 62, -13, 57, -121, 63, -81, -88, -108, 59, 27, 115, 1, 0,
                12, -49, 38, 59, 87, 101, 96, 63, -9, 47, 63, 63, -22, 85, -119, -68,
                -70, 66, 61, 62, -91, 40, 27, 59, 88, 96, 62, 63, -42, -1, -59, 63,
                -93, -23, 108, -67, 26, 96, 2, 0, 36, -24, 93, 63, 0, 81, -89, -66,
                87, -85, 85, 63, 102, 102, 102, 63, 20, 8, -53, 62, -84, 26, -60, 61,
                -4, -87, 49, 62, 113, -63, -68, 63, 1, -92, 54, 62, 25, 98, 3, 0,
                -121, 121, -125, 62, 118, 109, 121, -65, 15, 15, 73, 62, 30, -31, 41, -64,
                -125, -52, 36, 63, -22, 84, -76, -64, -112, -66, -15, 62, -120, 44, -32, 64,
                -71, 22, 53, 62, 24, -63, 4, 0, 7, 64, -68, 62, -76, -49, 73, 63,
                -67, -43, -2, 65, 0, 0, 0, 0
        };
        var outSimPacket = new AtomicReference<OutSimPacket2>();

        try (var outSimConnection = new OutSimConnection(PORT, 0x1FF)) {
            assertTrue(outSimConnection.isConnected());

            outSimConnection.listen(outSimPacket::set);

            lfsUdpMock.send(outSimPacketBytes);

            assertConditionMet(() -> outSimPacket.get() != null, 1000, 100);
        }

        assertTrue(outSimPacket.get().getOsHeader().isPresent());
        assertEquals(76, outSimPacket.get().getOsHeader().get().getL());
        assertEquals(70, outSimPacket.get().getOsHeader().get().getF());
        assertEquals(83, outSimPacket.get().getOsHeader().get().getS());
        assertEquals(84, outSimPacket.get().getOsHeader().get().getT());
        assertTrue(outSimPacket.get().getOsId().isPresent());
        assertEquals(65, outSimPacket.get().getOsId().get().getId());
        assertTrue(outSimPacket.get().getOsTime().isPresent());
        assertEquals(875625, outSimPacket.get().getOsTime().get().getTime());
        assertTrue(outSimPacket.get().getOsMain().isPresent());
        assertEquals(0.05400000140070915, outSimPacket.get().getOsMain().get().getAngVel().getX());
        assertEquals(-0.37564998865127563, outSimPacket.get().getOsMain().get().getAngVel().getY());
        assertEquals(0.0009532820549793541, outSimPacket.get().getOsMain().get().getAngVel().getZ());
        assertEquals(68.65599822998047, outSimPacket.get().getOsMain().get().getHeading());
        assertEquals(-5.754499912261963, outSimPacket.get().getOsMain().get().getPitch());
        assertEquals(-2.903485059738159, outSimPacket.get().getOsMain().get().getRoll());
        assertEquals(1.9264999628067017, outSimPacket.get().getOsMain().get().getAccel().getX());
        assertEquals(-0.37542998790740967, outSimPacket.get().getOsMain().get().getAccel().getY());
        assertEquals(0.12849999964237213, outSimPacket.get().getOsMain().get().getAccel().getZ());
        assertEquals(10.65464973449707, outSimPacket.get().getOsMain().get().getVel().getX());
        assertEquals(2.559999942779541, outSimPacket.get().getOsMain().get().getVel().getY());
        assertEquals(0.010239999741315842, outSimPacket.get().getOsMain().get().getVel().getZ());
        assertEquals(126645, outSimPacket.get().getOsMain().get().getPos().getX());
        assertEquals(-98235, outSimPacket.get().getOsMain().get().getPos().getY());
        assertEquals(3695, outSimPacket.get().getOsMain().get().getPos().getZ());
        assertTrue(outSimPacket.get().getOsInputs().isPresent());
        assertEquals(1.0, outSimPacket.get().getOsInputs().get().getThrottle());
        assertEquals(0.157000005245208740234375, outSimPacket.get().getOsInputs().get().getBrake());
        assertEquals(-1.598000049591064453125, outSimPacket.get().getOsInputs().get().getInputSteer());
        assertEquals(0.0, outSimPacket.get().getOsInputs().get().getClutch());
        assertEquals(0.0, outSimPacket.get().getOsInputs().get().getHandbrake());
        assertTrue(outSimPacket.get().getOsDrive().isPresent());
        assertEquals(4, outSimPacket.get().getOsDrive().get().getGear());
        assertEquals(3.4900000095367431640625, outSimPacket.get().getOsDrive().get().getEngineAngVel());
        assertEquals(285.65301513671875, outSimPacket.get().getOsDrive().get().getMaxTorqueAtVel());
        assertTrue(outSimPacket.get().getOsDistance().isPresent());
        assertEquals(1258.3746337890625, outSimPacket.get().getOsDistance().get().getCurrentLapDist());
        assertEquals(1302.66455078125, outSimPacket.get().getOsDistance().get().getIndexedDistance());
        assertTrue(outSimPacket.get().getOsWheels().isPresent());
        assertEquals(0.857819974422454833984375, outSimPacket.get().getOsWheels().get().getLeftRear().getSuspDeflect());
        assertEquals(2.6245501041412353515625, outSimPacket.get().getOsWheels().get().getLeftRear().getSteer());
        assertEquals(-1.00543498992919921875, outSimPacket.get().getOsWheels().get().getLeftRear().getXForce());
        assertEquals(0.37542998790740966796875, outSimPacket.get().getOsWheels().get().getLeftRear().getYForce());
        assertEquals(0.2895436584949493408203125, outSimPacket.get().getOsWheels().get().getLeftRear().getVerticalLoad());
        assertEquals(1.05645596981048583984375, outSimPacket.get().getOsWheels().get().getLeftRear().getAngVel());
        assertEquals(0.0045367102138698101043701171875, outSimPacket.get().getOsWheels().get().getLeftRear().getLeanRelToRoad());
        assertEquals(27, outSimPacket.get().getOsWheels().get().getLeftRear().getAirTemp());
        assertEquals(115, outSimPacket.get().getOsWheels().get().getLeftRear().getSlipFraction());
        assertEquals(1, outSimPacket.get().getOsWheels().get().getLeftRear().getTouching());
        assertEquals(0.002545299939811229705810546875, outSimPacket.get().getOsWheels().get().getLeftRear().getSlipRatio());
        assertEquals(0.876546323299407958984375, outSimPacket.get().getOsWheels().get().getLeftRear().getTanSlipAngle());
        assertEquals(0.746825635433197021484375, outSimPacket.get().getOsWheels().get().getRightRear().getSuspDeflect());
        assertEquals(-0.0167645998299121856689453125, outSimPacket.get().getOsWheels().get().getRightRear().getSteer());
        assertEquals(0.1848248541355133056640625, outSimPacket.get().getOsWheels().get().getRightRear().getXForce());
        assertEquals(0.00236753490753471851348876953125, outSimPacket.get().getOsWheels().get().getRightRear().getYForce());
        assertEquals(0.743657588958740234375, outSimPacket.get().getOsWheels().get().getRightRear().getVerticalLoad());
        assertEquals(1.5468699932098388671875, outSimPacket.get().getOsWheels().get().getRightRear().getAngVel());
        assertEquals(-0.0578400008380413055419921875, outSimPacket.get().getOsWheels().get().getRightRear().getLeanRelToRoad());
        assertEquals(26, outSimPacket.get().getOsWheels().get().getRightRear().getAirTemp());
        assertEquals(96, outSimPacket.get().getOsWheels().get().getRightRear().getSlipFraction());
        assertEquals(2, outSimPacket.get().getOsWheels().get().getRightRear().getTouching());
        assertEquals(0.8668234348297119140625, outSimPacket.get().getOsWheels().get().getRightRear().getSlipRatio());
        assertEquals(-0.32678985595703125, outSimPacket.get().getOsWheels().get().getRightRear().getTanSlipAngle());
        assertEquals(0.834645688533782958984375, outSimPacket.get().getOsWheels().get().getLeftFront().getSuspDeflect());
        assertEquals(0.89999997615814208984375, outSimPacket.get().getOsWheels().get().getLeftFront().getSteer());
        assertEquals(0.39654600620269775390625, outSimPacket.get().getOsWheels().get().getLeftFront().getXForce());
        assertEquals(0.0957539975643157958984375, outSimPacket.get().getOsWheels().get().getLeftFront().getYForce());
        assertEquals(0.173500001430511474609375, outSimPacket.get().getOsWheels().get().getLeftFront().getVerticalLoad());
        assertEquals(1.47465336322784423828125, outSimPacket.get().getOsWheels().get().getLeftFront().getAngVel());
        assertEquals(0.17836000025272369384765625, outSimPacket.get().getOsWheels().get().getLeftFront().getLeanRelToRoad());
        assertEquals(25, outSimPacket.get().getOsWheels().get().getLeftFront().getAirTemp());
        assertEquals(98, outSimPacket.get().getOsWheels().get().getLeftFront().getSlipFraction());
        assertEquals(3, outSimPacket.get().getOsWheels().get().getLeftFront().getTouching());
        assertEquals(0.2567865550518035888671875, outSimPacket.get().getOsWheels().get().getLeftFront().getSlipRatio());
        assertEquals(-0.97432649135589599609375, outSimPacket.get().getOsWheels().get().getLeftFront().getTanSlipAngle());
        assertEquals(0.19634650647640228271484375, outSimPacket.get().getOsWheels().get().getRightFront().getSuspDeflect());
        assertEquals(-2.654365062713623046875, outSimPacket.get().getOsWheels().get().getRightFront().getSteer());
        assertEquals(0.643745601177215576171875, outSimPacket.get().getOsWheels().get().getRightFront().getXForce());
        assertEquals(-5.63536548614501953125, outSimPacket.get().getOsWheels().get().getRightFront().getYForce());
        assertEquals(0.472157001495361328125, outSimPacket.get().getOsWheels().get().getRightFront().getVerticalLoad());
        assertEquals(7.005435943603515625, outSimPacket.get().getOsWheels().get().getRightFront().getAngVel());
        assertEquals(0.17684449255466461181640625, outSimPacket.get().getOsWheels().get().getRightFront().getLeanRelToRoad());
        assertEquals(24, outSimPacket.get().getOsWheels().get().getRightFront().getAirTemp());
        assertEquals(193, outSimPacket.get().getOsWheels().get().getRightFront().getSlipFraction());
        assertEquals(4, outSimPacket.get().getOsWheels().get().getRightFront().getTouching());
        assertEquals(0.3676759898662567138671875, outSimPacket.get().getOsWheels().get().getRightFront().getSlipRatio());
        assertEquals(0.7883255481719970703125, outSimPacket.get().getOsWheels().get().getRightFront().getTanSlipAngle());
        assertTrue(outSimPacket.get().getOsExtra1().isPresent());
        assertEquals(31.8543643951416015625, outSimPacket.get().getOsExtra1().get().getSteerTorque());
    }

    @Test
    void receivedPartialOutSimPack2() throws IOException {
        final var outSimPacketBytes = new byte[] {
            45, -42, 1, 0, -120, 44, -32, 64, 0, 0, 0, 0
        };

        var outSimPacket = new AtomicReference<OutSimPacket2>();

        try (var outSimConnection = new OutSimConnection(PORT, new Flags<>(OutSimOpts.ID, OutSimOpts.EXTRA_1))) {
            assertTrue(outSimConnection.isConnected());

            outSimConnection.listen(outSimPacket::set);

            lfsUdpMock.send(outSimPacketBytes);

            assertConditionMet(() -> outSimPacket.get() != null, 1000, 100);
        }

        assertTrue(outSimPacket.get().getOsId().isPresent());
        assertEquals(120365, outSimPacket.get().getOsId().get().getId());
        assertTrue(outSimPacket.get().getOsExtra1().isPresent());
        assertEquals(7.005435943603515625, outSimPacket.get().getOsExtra1().get().getSteerTorque());
        assertTrue(outSimPacket.get().getOsHeader().isEmpty());
        assertTrue(outSimPacket.get().getOsTime().isEmpty());
        assertTrue(outSimPacket.get().getOsMain().isEmpty());
        assertTrue(outSimPacket.get().getOsInputs().isEmpty());
        assertTrue(outSimPacket.get().getOsDrive().isEmpty());
        assertTrue(outSimPacket.get().getOsDistance().isEmpty());
        assertTrue(outSimPacket.get().getOsWheels().isEmpty());
    }

    @Test
    @SuppressWarnings("java:S5961")
    void receiveOutSimPack1() throws IOException {
        final var outSimPacketBytes = new byte[] {
                -108, 79, 86, 0, 27, 47, 93, 61, 50, 85, -64, -66, -83, -27, 121, 58,
                -33, 79, -119, 66, -35, 36, -72, -64, -77, -46, 57, -64, -115, -105, -10, 63,
                92, 56, -64, -66, -127, -107, 3, 62, 114, 121, 42, 65, 10, -41, 35, 64,
                -84, -59, 39, 60, -75, -18, 1, 0, 69, -128, -2, -1, 111, 14, 0, 0,
                99, 0, 0, 0
        };

        var outSimPacket = new AtomicReference<OutSimPacket2>();

        try (var outSimConnection = new OutSimConnection(PORT, 0)) {
            assertTrue(outSimConnection.isConnected());

            outSimConnection.listen(outSimPacket::set);

            lfsUdpMock.send(outSimPacketBytes);

            assertConditionMet(() -> outSimPacket.get() != null, 1000, 100);
        }

        assertTrue(outSimPacket.get().getOsTime().isPresent());
        assertEquals(5656468, outSimPacket.get().getOsTime().get().getTime());
        assertTrue(outSimPacket.get().getOsMain().isPresent());
        assertEquals(0.05400000140070915, outSimPacket.get().getOsMain().get().getAngVel().getX());
        assertEquals(-0.37564998865127563, outSimPacket.get().getOsMain().get().getAngVel().getY());
        assertEquals(0.0009532820549793541, outSimPacket.get().getOsMain().get().getAngVel().getZ());
        assertEquals(68.65599822998047, outSimPacket.get().getOsMain().get().getHeading());
        assertEquals(-5.754499912261963, outSimPacket.get().getOsMain().get().getPitch());
        assertEquals(-2.903485059738159, outSimPacket.get().getOsMain().get().getRoll());
        assertEquals(1.9264999628067017, outSimPacket.get().getOsMain().get().getAccel().getX());
        assertEquals(-0.37542998790740967, outSimPacket.get().getOsMain().get().getAccel().getY());
        assertEquals(0.12849999964237213, outSimPacket.get().getOsMain().get().getAccel().getZ());
        assertEquals(10.65464973449707, outSimPacket.get().getOsMain().get().getVel().getX());
        assertEquals(2.559999942779541, outSimPacket.get().getOsMain().get().getVel().getY());
        assertEquals(0.010239999741315842, outSimPacket.get().getOsMain().get().getVel().getZ());
        assertEquals(126645, outSimPacket.get().getOsMain().get().getPos().getX());
        assertEquals(-98235, outSimPacket.get().getOsMain().get().getPos().getY());
        assertEquals(3695, outSimPacket.get().getOsMain().get().getPos().getZ());
        assertTrue(outSimPacket.get().getOsId().isPresent());
        assertEquals(99, outSimPacket.get().getOsId().get().getId());
        assertTrue(outSimPacket.get().getOsHeader().isEmpty());
        assertTrue(outSimPacket.get().getOsInputs().isEmpty());
        assertTrue(outSimPacket.get().getOsDrive().isEmpty());
        assertTrue(outSimPacket.get().getOsDistance().isEmpty());
        assertTrue(outSimPacket.get().getOsWheels().isEmpty());
        assertTrue(outSimPacket.get().getOsExtra1().isEmpty());
    }

    @Test
    void listen_withThrowingCallback() throws IOException {
        var firstListenerCalled = new AtomicBoolean();
        var secondListenerCalled = new AtomicBoolean();

        try (var outSimConnection = new OutSimConnection(PORT, new Flags<>(OutSimOpts.TIME))) {
            outSimConnection.listen(packet -> {
                firstListenerCalled.set(true);
                throw new RuntimeException("Exception in listener callback");
            });
            outSimConnection.listen(packet -> secondListenerCalled.set(true));

            lfsUdpMock.send(new byte[] { 65, 93, -82, 0 });

            assertConditionMet(
                    () -> firstListenerCalled.get() && secondListenerCalled.get(),
                    1000,
                    100
            );
        }
    }

    @Test
    void stopListening() throws IOException {
        var firstListenerCalled = new AtomicBoolean();
        var secondListenerCalled = new AtomicBoolean();

        Consumer<OutSimPacket2> packetListener1 = packet -> firstListenerCalled.set(true);

        try (var outSimConnection = new OutSimConnection(PORT, new Flags<>(OutSimOpts.TIME))) {
            outSimConnection.listen(packetListener1);
            outSimConnection.listen(packet -> secondListenerCalled.set(true));

            outSimConnection.stopListening(packetListener1);

            lfsUdpMock.send(new byte[] { 65, 93, -82, 0 });

            assertConditionMet(
                    () -> !firstListenerCalled.get() && secondListenerCalled.get(),
                    1000,
                    100
            );
        }
    }
}
