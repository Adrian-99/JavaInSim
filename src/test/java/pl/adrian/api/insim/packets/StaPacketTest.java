package pl.adrian.api.insim.packets;

import org.junit.jupiter.api.Test;
import pl.adrian.api.insim.packets.enums.*;
import pl.adrian.api.insim.packets.flags.StaFlag;
import pl.adrian.internal.insim.packets.util.PacketReader;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.adrian.testutil.AssertionUtils.assertFlagsEqual;
import static pl.adrian.testutil.AssertionUtils.assertPacketHeaderEquals;

class StaPacketTest {
    @Test
    void readStaPacket_withPracticeRaceLaps() {
        var headerBytes = new byte[] { 7, 5, -112 };
        var dataBytes = new byte[] {
                0, 0, 0, -64, 63, 25, 74, 3, 0, 35, 41, 0, 0, 0, 0, 0,
                1, 65, 83, 49, 88, 0, 0, 1, 1
        };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(25, PacketType.STA, 144, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof StaPacket);

        var castedReadPacket = (StaPacket) readPacket;

        assertPacketHeaderEquals(28, PacketType.STA, 144, castedReadPacket);
        assertEquals(1.5, castedReadPacket.getReplaySpeed());
        assertFlagsEqual(
                StaFlag.class,
                Set.of(StaFlag.GAME, StaFlag.SHIFTU, StaFlag.DIALOG, StaFlag.MULTI, StaFlag.WINDOWED, StaFlag.VISIBLE),
                castedReadPacket.getFlags()
        );
        assertEquals(ViewIdentifier.DRIVER, castedReadPacket.getInGameCam());
        assertEquals(0, castedReadPacket.getViewPlid());
        assertEquals(35, castedReadPacket.getNumP());
        assertEquals(41, castedReadPacket.getNumConns());
        assertEquals(0, castedReadPacket.getNumFinished());
        assertEquals(RaceProgress.NO_RACE, castedReadPacket.getRaceInProg());
        assertEquals(0, castedReadPacket.getQualMins());
        assertEquals(RaceLapsUnit.PRACTICE, castedReadPacket.getRaceLaps().getUnit());
        assertEquals(0, castedReadPacket.getRaceLaps().getValue());
        assertEquals(ServerStatus.SUCCESS, castedReadPacket.getServerStatus());
        assertEquals("AS1X", castedReadPacket.getTrack());
        assertEquals(1, castedReadPacket.getWeather());
        assertEquals(Wind.WEAK, castedReadPacket.getWind());
    }

    @Test
    void readStaPacket_withLapsRaceLaps() {
        var headerBytes = new byte[] { 7, 5, -112 };
        var dataBytes = new byte[] {
                0, 0, 0, -64, 63, 25, 74, 3, 0, 35, 41, 0, 0, 0, 50, 0,
                1, 65, 83, 49, 88, 0, 0, 1, 1
        };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(25, PacketType.STA, 144, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof StaPacket);

        var castedReadPacket = (StaPacket) readPacket;

        assertPacketHeaderEquals(28, PacketType.STA, 144, castedReadPacket);
        assertEquals(1.5, castedReadPacket.getReplaySpeed());
        assertFlagsEqual(
                StaFlag.class,
                Set.of(StaFlag.GAME, StaFlag.SHIFTU, StaFlag.DIALOG, StaFlag.MULTI, StaFlag.WINDOWED, StaFlag.VISIBLE),
                castedReadPacket.getFlags()
        );
        assertEquals(ViewIdentifier.DRIVER, castedReadPacket.getInGameCam());
        assertEquals(0, castedReadPacket.getViewPlid());
        assertEquals(35, castedReadPacket.getNumP());
        assertEquals(41, castedReadPacket.getNumConns());
        assertEquals(0, castedReadPacket.getNumFinished());
        assertEquals(RaceProgress.NO_RACE, castedReadPacket.getRaceInProg());
        assertEquals(0, castedReadPacket.getQualMins());
        assertEquals(RaceLapsUnit.LAPS, castedReadPacket.getRaceLaps().getUnit());
        assertEquals(50, castedReadPacket.getRaceLaps().getValue());
        assertEquals(ServerStatus.SUCCESS, castedReadPacket.getServerStatus());
        assertEquals("AS1X", castedReadPacket.getTrack());
        assertEquals(1, castedReadPacket.getWeather());
        assertEquals(Wind.WEAK, castedReadPacket.getWind());
    }

    @Test
    void readStaPacket_withHundredsOfLapsRaceLaps() {
        var headerBytes = new byte[] { 7, 5, -112 };
        var dataBytes = new byte[] {
                0, 0, 0, -64, 63, 25, 74, 3, 0, 35, 41, 0, 0, 0, -106, 0,
                1, 65, 83, 49, 88, 0, 0, 1, 1
        };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(25, PacketType.STA, 144, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof StaPacket);

        var castedReadPacket = (StaPacket) readPacket;

        assertPacketHeaderEquals(28, PacketType.STA, 144, castedReadPacket);
        assertEquals(1.5, castedReadPacket.getReplaySpeed());
        assertFlagsEqual(
                StaFlag.class,
                Set.of(StaFlag.GAME, StaFlag.SHIFTU, StaFlag.DIALOG, StaFlag.MULTI, StaFlag.WINDOWED, StaFlag.VISIBLE),
                castedReadPacket.getFlags()
        );
        assertEquals(ViewIdentifier.DRIVER, castedReadPacket.getInGameCam());
        assertEquals(0, castedReadPacket.getViewPlid());
        assertEquals(35, castedReadPacket.getNumP());
        assertEquals(41, castedReadPacket.getNumConns());
        assertEquals(0, castedReadPacket.getNumFinished());
        assertEquals(RaceProgress.NO_RACE, castedReadPacket.getRaceInProg());
        assertEquals(0, castedReadPacket.getQualMins());
        assertEquals(RaceLapsUnit.LAPS, castedReadPacket.getRaceLaps().getUnit());
        assertEquals(600, castedReadPacket.getRaceLaps().getValue());
        assertEquals(ServerStatus.SUCCESS, castedReadPacket.getServerStatus());
        assertEquals("AS1X", castedReadPacket.getTrack());
        assertEquals(1, castedReadPacket.getWeather());
        assertEquals(Wind.WEAK, castedReadPacket.getWind());
    }

    @Test
    void readStaPacket_withHoursRaceLaps() {
        var headerBytes = new byte[] { 7, 5, -112 };
        var dataBytes = new byte[] {
                0, 0, 0, -64, 63, 25, 74, 3, 0, 35, 41, 0, 0, 0, -56, 0,
                1, 65, 83, 49, 88, 0, 0, 1, 1
        };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(25, PacketType.STA, 144, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof StaPacket);

        var castedReadPacket = (StaPacket) readPacket;

        assertPacketHeaderEquals(28, PacketType.STA, 144, castedReadPacket);
        assertEquals(1.5, castedReadPacket.getReplaySpeed());
        assertFlagsEqual(
                StaFlag.class,
                Set.of(StaFlag.GAME, StaFlag.SHIFTU, StaFlag.DIALOG, StaFlag.MULTI, StaFlag.WINDOWED, StaFlag.VISIBLE),
                castedReadPacket.getFlags()
        );
        assertEquals(ViewIdentifier.DRIVER, castedReadPacket.getInGameCam());
        assertEquals(0, castedReadPacket.getViewPlid());
        assertEquals(35, castedReadPacket.getNumP());
        assertEquals(41, castedReadPacket.getNumConns());
        assertEquals(0, castedReadPacket.getNumFinished());
        assertEquals(RaceProgress.NO_RACE, castedReadPacket.getRaceInProg());
        assertEquals(0, castedReadPacket.getQualMins());
        assertEquals(RaceLapsUnit.HOURS, castedReadPacket.getRaceLaps().getUnit());
        assertEquals(10, castedReadPacket.getRaceLaps().getValue());
        assertEquals(ServerStatus.SUCCESS, castedReadPacket.getServerStatus());
        assertEquals("AS1X", castedReadPacket.getTrack());
        assertEquals(1, castedReadPacket.getWeather());
        assertEquals(Wind.WEAK, castedReadPacket.getWind());
    }

    @Test
    void readStaPacket_withInvalidRaceLaps() {
        var headerBytes = new byte[] { 7, 5, -112 };
        var dataBytes = new byte[] {
                0, 0, 0, -64, 63, 25, 74, 3, 0, 35, 41, 0, 0, 0, -11, 0,
                1, 65, 83, 49, 88, 0, 0, 1, 1
        };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(25, PacketType.STA, 144, packetReader);

        assertThrows(IllegalStateException.class, () -> packetReader.read(dataBytes));
    }
}
