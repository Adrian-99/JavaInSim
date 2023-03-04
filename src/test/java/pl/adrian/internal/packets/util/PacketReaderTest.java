package pl.adrian.internal.packets.util;

import org.junit.jupiter.api.Test;
import pl.adrian.api.packets.*;
import pl.adrian.api.packets.enums.*;
import pl.adrian.api.packets.flags.StaFlag;
import pl.adrian.internal.packets.exceptions.PacketReadingException;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static pl.adrian.testutil.FlagsTestUtils.assertFlagsEqual;

class PacketReaderTest {

    @Test
    void packetReader_tooShortHeader() {
        var headerBytes = new byte[] { 1, 1 };
        assertThrows(PacketReadingException.class, () -> new PacketReader(headerBytes));
    }

    @Test
    void packetReader_tooLongHeader() {
        var headerBytes = new byte[] { 1, 1, 1, 1 };
        assertThrows(PacketReadingException.class, () -> new PacketReader(headerBytes));
    }

    @Test
    void read_forUnrecognizedReadablePacketType() {
        var headerBytes = new byte[] { 1, 0, 0 };
        var dataBytes = new byte[] { 0 };
        var packetBuilder = new PacketReader(headerBytes);

        assertEquals(PacketType.NONE, packetBuilder.getPacketType());
        assertEquals(1, packetBuilder.getDataBytesCount());
        assertEquals(0, packetBuilder.getPacketReqI());
        assertThrows(PacketReadingException.class, () -> packetBuilder.read(dataBytes));
    }

    @Test
    void readVerPacket() {
        var headerBytes = new byte[] { 5, 2, -112 };
        var dataBytes = new byte[] { 0, 48, 46, 55, 68, 0, 0, 0, 0, 83, 51, 0, 0, 0, 0, 9 };
        var packetReader = new PacketReader(headerBytes);

        assertEquals(PacketType.VER, packetReader.getPacketType());
        assertEquals(17, packetReader.getDataBytesCount());
        assertEquals(144, packetReader.getPacketReqI());

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof VerPacket);

        var castedReadPacket = (VerPacket) readPacket;

        assertEquals(20, castedReadPacket.getSize());
        assertEquals(PacketType.VER, castedReadPacket.getType());
        assertEquals(144, castedReadPacket.getReqI());
        assertEquals("0.7D", castedReadPacket.getVersion());
        assertEquals(Product.S3, castedReadPacket.getProduct());
        assertEquals(9, castedReadPacket.getInSimVer());
    }

    @Test
    void readTinyPacket() {
        var headerBytes = new byte[] { 1, 3, -112 };
        var dataBytes = new byte[] { 0 };
        var packetReader = new PacketReader(headerBytes);

        assertEquals(PacketType.TINY, packetReader.getPacketType());
        assertEquals(1, packetReader.getDataBytesCount());
        assertEquals(144, packetReader.getPacketReqI());

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof TinyPacket);

        var castedReadPacket = (TinyPacket) readPacket;

        assertEquals(4, castedReadPacket.getSize());
        assertEquals(PacketType.TINY, castedReadPacket.getType());
        assertEquals(144, castedReadPacket.getReqI());
        assertEquals(TinySubtype.NONE, castedReadPacket.getSubT());
    }

    @Test
    void readSmallPacket() {
        var headerBytes = new byte[] { 2, 4, -112 };
        var dataBytes = new byte[] { 0, -26, -107, 96, -39 };
        var packetReader = new PacketReader(headerBytes);

        assertEquals(PacketType.SMALL, packetReader.getPacketType());
        assertEquals(5, packetReader.getDataBytesCount());
        assertEquals(144, packetReader.getPacketReqI());

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof SmallPacket);

        var castedReadPacket = (SmallPacket) readPacket;

        assertEquals(8, castedReadPacket.getSize());
        assertEquals(PacketType.SMALL, castedReadPacket.getType());
        assertEquals(144, castedReadPacket.getReqI());
        assertEquals(SmallSubtype.NONE, castedReadPacket.getSubT());
        assertEquals(3646985702L, castedReadPacket.getUVal());
    }

    @Test
    void readStaPacket() {
        var headerBytes = new byte[] { 7, 5, -112 };
        var dataBytes = new byte[] {
                0, 0, 0, -64, 63, 25, 74, 3, 0, 35, 41, 0, 0, 0, 0, 0,
                1, 65, 83, 49, 88, 0, 0, 1, 1
        };
        var packetReader = new PacketReader(headerBytes);

        assertEquals(PacketType.STA, packetReader.getPacketType());
        assertEquals(25, packetReader.getDataBytesCount());
        assertEquals(144, packetReader.getPacketReqI());

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof StaPacket);

        var castedReadPacket = (StaPacket) readPacket;

        assertEquals(28, castedReadPacket.getSize());
        assertEquals(PacketType.STA, castedReadPacket.getType());
        assertEquals(144, castedReadPacket.getReqI());
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
        assertTrue(castedReadPacket.getRaceLaps().isPractice());
        assertFalse(castedReadPacket.getRaceLaps().areLaps());
        assertFalse(castedReadPacket.getRaceLaps().areHours());
        assertEquals(0, castedReadPacket.getRaceLaps().getValue());
        assertEquals(ServerStatus.SUCCESS, castedReadPacket.getServerStatus());
        assertEquals("AS1X", castedReadPacket.getTrack());
        assertEquals(1, castedReadPacket.getWeather());
        assertEquals(Wind.WEAK, castedReadPacket.getWind());
    }

    @Test
    void readMsoPacket() {
        var headerBytes = new byte[] { 7, 11, 0 };
        var dataBytes = new byte[] {
                0, 15, 65, 1, 10, 117, 115, 101, 114, 110, 97, 109, 101, 58, 32, 116,
                101, 115, 116, 32, 116, 101, 120, 116, 0
        };
        var packetReader = new PacketReader(headerBytes);

        assertEquals(PacketType.MSO, packetReader.getPacketType());
        assertEquals(25, packetReader.getDataBytesCount());
        assertEquals(0, packetReader.getPacketReqI());

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof MsoPacket);

        var castedReadPacket = (MsoPacket) readPacket;

        assertEquals(28, castedReadPacket.getSize());
        assertEquals(PacketType.MSO, castedReadPacket.getType());
        assertEquals(0, castedReadPacket.getReqI());
        assertEquals(15, castedReadPacket.getUcid());
        assertEquals(65, castedReadPacket.getPlid());
        assertEquals(MessageType.USER, castedReadPacket.getUserType());
        assertEquals(10, castedReadPacket.getTextStart());
        assertEquals("username: test text", castedReadPacket.getMsg());
    }

    @Test
    void readIiiPacket() {
        var headerBytes = new byte[] { 4, 12, 0 };
        var dataBytes = new byte[] { 0, 23, 17, 0, 0, 116, 101, 115, 116, 0, 0, 0, 0 };
        var packetReader = new PacketReader(headerBytes);

        assertEquals(PacketType.III, packetReader.getPacketType());
        assertEquals(13, packetReader.getDataBytesCount());
        assertEquals(0, packetReader.getPacketReqI());

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof IiiPacket);

        var castedReadPacket = (IiiPacket) readPacket;

        assertEquals(16, castedReadPacket.getSize());
        assertEquals(PacketType.III, castedReadPacket.getType());
        assertEquals(0, castedReadPacket.getReqI());
        assertEquals(23, castedReadPacket.getUcid());
        assertEquals(17, castedReadPacket.getPlid());
        assertEquals("test", castedReadPacket.getMsg());
    }

    @Test
    void readAcrPacket() {
        var headerBytes = new byte[] { 5, 55, 0 };
        var dataBytes = new byte[] { 0, 31, 1, 3, 0, 47, 99, 111, 109, 109, 97, 110, 100, 0, 0, 0, 0 };
        var packetReader = new PacketReader(headerBytes);

        assertEquals(PacketType.ACR, packetReader.getPacketType());
        assertEquals(17, packetReader.getDataBytesCount());
        assertEquals(0, packetReader.getPacketReqI());

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof AcrPacket);

        var castedReadPacket = (AcrPacket) readPacket;

        assertEquals(20, castedReadPacket.getSize());
        assertEquals(PacketType.ACR, castedReadPacket.getType());
        assertEquals(0, castedReadPacket.getReqI());
        assertEquals(31, castedReadPacket.getUcid());
        assertTrue(castedReadPacket.isAdmin());
        assertEquals(AcrResult.UNKNOWN_COMMAND, castedReadPacket.getResult());
        assertEquals("/command", castedReadPacket.getText());
    }

    @Test
    void readIsmPacket() {
        var headerBytes = new byte[] { 10, 10, -112 };
        var dataBytes = new byte[] {
                0, 1, 0, 0, 0, 69, 120, 97, 109, 112, 108, 101, 32, 76, 70, 83,
                32, 83, 101, 114, 118, 101, 114, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0
        };
        var packetReader = new PacketReader(headerBytes);

        assertEquals(PacketType.ISM, packetReader.getPacketType());
        assertEquals(37, packetReader.getDataBytesCount());
        assertEquals(144, packetReader.getPacketReqI());

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof IsmPacket);

        var castedReadPacket = (IsmPacket) readPacket;

        assertEquals(40, castedReadPacket.getSize());
        assertEquals(PacketType.ISM, castedReadPacket.getType());
        assertEquals(144, castedReadPacket.getReqI());
        assertTrue(castedReadPacket.isHost());
        assertEquals("Example LFS Server", castedReadPacket.getHName());
    }
}
