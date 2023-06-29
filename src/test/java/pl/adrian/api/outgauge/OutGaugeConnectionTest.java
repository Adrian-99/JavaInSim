package pl.adrian.api.outgauge;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.adrian.api.common.enums.DefaultCar;
import pl.adrian.api.outgauge.flags.DashLight;
import pl.adrian.api.outgauge.flags.OutGaugeFlag;
import pl.adrian.testutil.LfsUdpMock;

import java.io.IOException;
import java.net.SocketException;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.adrian.testutil.AssertionUtils.*;

class OutGaugeConnectionTest {
    private static final int PORT = 30004;
    private static final byte[] OUT_GAUGE_PACKET_BYTES = new byte[] {
            -39, -4, 11, 0, 88, 82, 84, 0, 2, 32, 3, 29, -15, -45, 122, 65,
            -114, -57, -60, 69, 127, -39, 61, 63, -21, 27, -74, 66, -60, -112, -7, 62,
            122, -43, 81, 64, 82, -86, -83, 66, 102, 6, 0, 0, 70, 0, 0, 0,
            0, 0, -128, 63, -8, 109, -120, 60, 0, 0, 0, 0, 70, 117, 101, 108,
            58, 32, 52, 56, 37, 0, 0, 0, 0, 0, 0, 0, 66, 114, 97, 107,
            101, 32, 66, 97, 108, 58, 32, 54, 53, 37, 0, 0, 96, 0, 0, 0
    };
    private LfsUdpMock lfsUdpMock;
    private OutGaugeConnection outGaugeConnection;

    @BeforeEach
    void beforeEach() throws SocketException {
        lfsUdpMock = new LfsUdpMock(PORT);
        outGaugeConnection = new OutGaugeConnection(PORT);
    }

    @AfterEach
    void afterEach() {
        outGaugeConnection.close();
        lfsUdpMock.close();
    }

    @Test
    void receiveOutGaugePack() throws IOException {
        var outGaugePacket = new AtomicReference<OutGaugePacket>();

        assertTrue(outGaugeConnection.isConnected());

        outGaugeConnection.listen(outGaugePacket::set);

        lfsUdpMock.send(OUT_GAUGE_PACKET_BYTES);

        assertConditionMet(() -> outGaugePacket.get() != null, 1000, 100);

        assertEquals(785625, outGaugePacket.get().getTime());
        assertCarEquals(DefaultCar.XRT, outGaugePacket.get().getCar());
        assertFlagsEqual(
                OutGaugeFlag.class,
                Set.of(OutGaugeFlag.CTRL, OutGaugeFlag.TURBO),
                outGaugePacket.get().getFlags()
        );
        assertEquals(3, outGaugePacket.get().getGear());
        assertEquals(29, outGaugePacket.get().getPlid());
        assertEquals(15.67674350738525390625, outGaugePacket.get().getSpeed());
        assertEquals(6296.9443359375, outGaugePacket.get().getRpm());
        assertEquals(0.741599977016448974609375, outGaugePacket.get().getTurbo());
        assertEquals(91.05452728271484375, outGaugePacket.get().getEngTemp());
        assertEquals(0.48743259906768798828125, outGaugePacket.get().getFuel());
        assertEquals(3.278654575347900390625, outGaugePacket.get().getOilPressure());
        assertEquals(86.8326568603515625, outGaugePacket.get().getOilTemp());
        assertFlagsEqual(
                DashLight.class,
                Set.of(DashLight.FULLBEAM, DashLight.HANDBRAKE, DashLight.SIGNAL_L, DashLight.SIGNAL_R, DashLight.BATTERY, DashLight.ABS),
                outGaugePacket.get().getDashLights()
        );
        assertFlagsEqual(
                DashLight.class,
                Set.of(DashLight.FULLBEAM, DashLight.HANDBRAKE, DashLight.SIGNAL_R),
                outGaugePacket.get().getShowLights()
        );
        assertEquals(1.0, outGaugePacket.get().getThrottle());
        assertEquals(0.01665399968624114990234375, outGaugePacket.get().getBrake());
        assertEquals(0.0, outGaugePacket.get().getClutch());
        assertEquals("Fuel: 48%", outGaugePacket.get().getDisplay1());
        assertEquals("Brake Bal: 65%", outGaugePacket.get().getDisplay2());
        assertEquals(96, outGaugePacket.get().getId());
    }

    @Test
    void listen_withThrowingCallback() throws IOException {
        var firstListenerCalled = new AtomicBoolean();
        var secondListenerCalled = new AtomicBoolean();

        outGaugeConnection.listen(packet -> {
            firstListenerCalled.set(true);
            throw new RuntimeException("Exception in listener callback");
        });
        outGaugeConnection.listen(packet -> secondListenerCalled.set(true));

        lfsUdpMock.send(OUT_GAUGE_PACKET_BYTES);

        assertConditionMet(
                () -> firstListenerCalled.get() && secondListenerCalled.get(),
                1000,
                100
        );
    }

    @Test
    void stopListening() throws IOException {
        var firstListenerCalled = new AtomicBoolean();
        var secondListenerCalled = new AtomicBoolean();

        Consumer<OutGaugePacket> packetListener1 = packet -> firstListenerCalled.set(true);

        outGaugeConnection.listen(packetListener1);
        outGaugeConnection.listen(packet -> secondListenerCalled.set(true));

        outGaugeConnection.stopListening(packetListener1);

        lfsUdpMock.send(OUT_GAUGE_PACKET_BYTES);

        assertConditionMet(
                () -> !firstListenerCalled.get() && secondListenerCalled.get(),
                1000,
                100
        );
    }
}
