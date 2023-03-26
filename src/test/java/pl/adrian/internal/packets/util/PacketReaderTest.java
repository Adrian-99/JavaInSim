package pl.adrian.internal.packets.util;

import org.junit.jupiter.api.Test;
import pl.adrian.api.packets.*;
import pl.adrian.api.packets.enums.*;
import pl.adrian.api.packets.enums.DefaultCar;
import pl.adrian.api.packets.flags.NcnFlag;
import pl.adrian.api.packets.flags.RaceFlag;
import pl.adrian.api.packets.flags.StaFlag;
import pl.adrian.internal.packets.base.InfoPacket;
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

        assertPacketHeaderEquals(17, PacketType.VER, 144, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof VerPacket);

        var castedReadPacket = (VerPacket) readPacket;

        assertPacketHeaderEquals(20, PacketType.VER, 144, castedReadPacket);
        assertEquals("0.7D", castedReadPacket.getVersion());
        assertEquals(Product.S3, castedReadPacket.getProduct());
        assertEquals(9, castedReadPacket.getInSimVer());
    }

    @Test
    void readTinyPacket() {
        var headerBytes = new byte[] { 1, 3, -112 };
        var dataBytes = new byte[] { 0 };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(1, PacketType.TINY, 144, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof TinyPacket);

        var castedReadPacket = (TinyPacket) readPacket;

        assertPacketHeaderEquals(4, PacketType.TINY, 144, castedReadPacket);
        assertEquals(TinySubtype.NONE, castedReadPacket.getSubT());
    }

    @Test
    void readSmallPacket() {
        var headerBytes = new byte[] { 2, 4, -112 };
        var dataBytes = new byte[] { 0, -26, -107, 96, -39 };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(5, PacketType.SMALL, 144, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof SmallPacket);

        var castedReadPacket = (SmallPacket) readPacket;

        assertPacketHeaderEquals(8, PacketType.SMALL, 144, castedReadPacket);
        assertEquals(SmallSubtype.NONE, castedReadPacket.getSubT());
        assertEquals(3646985702L, castedReadPacket.getUVal());
        assertTrue(castedReadPacket.getVoteAction().isEmpty());
        assertTrue(castedReadPacket.getCars().isEmpty());
    }

    @Test
    void readSmallPacket_withVtaSubtype() {
        var headerBytes = new byte[] { 2, 4, -112 };
        var dataBytes = new byte[] { 3, 3, 0, 0, 0 };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(5, PacketType.SMALL, 144, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof SmallPacket);

        var castedReadPacket = (SmallPacket) readPacket;

        assertPacketHeaderEquals(8, PacketType.SMALL, 144, castedReadPacket);
        assertEquals(SmallSubtype.VTA, castedReadPacket.getSubT());
        assertEquals(3, castedReadPacket.getUVal());
        assertTrue(castedReadPacket.getVoteAction().isPresent());
        assertEquals(VoteAction.QUALIFY, castedReadPacket.getVoteAction().get());
        assertTrue(castedReadPacket.getCars().isEmpty());
    }

    @Test
    void readSmallPacket_withAlcSubtype() {
        var headerBytes = new byte[] { 2, 4, -112 };
        var dataBytes = new byte[] { 8, 0, 72, 12, 0 };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(5, PacketType.SMALL, 144, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof SmallPacket);

        var castedReadPacket = (SmallPacket) readPacket;

        assertPacketHeaderEquals(8, PacketType.SMALL, 144, castedReadPacket);
        assertEquals(SmallSubtype.ALC, castedReadPacket.getSubT());
        assertEquals(804864, castedReadPacket.getUVal());
        assertTrue(castedReadPacket.getVoteAction().isEmpty());
        assertTrue(castedReadPacket.getCars().isPresent());
        assertFlagsEqual(DefaultCar.class, Set.of(DefaultCar.FOX, DefaultCar.FO8, DefaultCar.BF1, DefaultCar.FBM), castedReadPacket.getCars().get());
    }

    @Test
    void readStaPacket() {
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

        assertPacketHeaderEquals(25, PacketType.MSO, 0, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof MsoPacket);

        var castedReadPacket = (MsoPacket) readPacket;

        assertPacketHeaderEquals(28, PacketType.MSO, 0, castedReadPacket);
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

        assertPacketHeaderEquals(13, PacketType.III, 0, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof IiiPacket);

        var castedReadPacket = (IiiPacket) readPacket;

        assertPacketHeaderEquals(16, PacketType.III, 0, castedReadPacket);
        assertEquals(23, castedReadPacket.getUcid());
        assertEquals(17, castedReadPacket.getPlid());
        assertEquals("test", castedReadPacket.getMsg());
    }

    @Test
    void readAcrPacket() {
        var headerBytes = new byte[] { 5, 55, 0 };
        var dataBytes = new byte[] { 0, 31, 1, 3, 0, 47, 99, 111, 109, 109, 97, 110, 100, 0, 0, 0, 0 };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(17, PacketType.ACR, 0, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof AcrPacket);

        var castedReadPacket = (AcrPacket) readPacket;

        assertPacketHeaderEquals(20, PacketType.ACR, 0, castedReadPacket);
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

        assertPacketHeaderEquals(37, PacketType.ISM, 144, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof IsmPacket);

        var castedReadPacket = (IsmPacket) readPacket;

        assertPacketHeaderEquals(40, PacketType.ISM, 144, castedReadPacket);
        assertTrue(castedReadPacket.isHost());
        assertEquals("Example LFS Server", castedReadPacket.getHName());
    }

    @Test
    void readVtnPacket() {
        var headerBytes = new byte[] { 2, 16, 0 };
        var dataBytes = new byte[] { 0, 34, 2, 0, 0 };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(5, PacketType.VTN, 0, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof VtnPacket);

        var castedReadPacket = (VtnPacket) readPacket;

        assertPacketHeaderEquals(8, PacketType.VTN, 0, castedReadPacket);
        assertEquals(34, castedReadPacket.getUcid());
        assertEquals(VoteAction.RESTART, castedReadPacket.getAction());
    }

    @Test
    void readRstPacket() {
        var headerBytes = new byte[] { 7, 17, -112 };
        var dataBytes = new byte[] {
                0, 15, 0, 20, 67, 70, 69, 49, 82, 0, 0, 1, 2, 33, 1, -24,
                3, 0, 0, -32, 0, 77, 2, -67, 2
        };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(25, PacketType.RST, 144, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof RstPacket);

        var castedReadPacket = (RstPacket) readPacket;

        assertPacketHeaderEquals(28, PacketType.RST, 144, castedReadPacket);
        assertFalse(castedReadPacket.getRaceLaps().isPractice());
        assertTrue(castedReadPacket.getRaceLaps().areLaps());
        assertFalse(castedReadPacket.getRaceLaps().areHours());
        assertEquals(15, castedReadPacket.getRaceLaps().getValue());
        assertEquals(0, castedReadPacket.getQualMins());
        assertEquals(20, castedReadPacket.getNumP());
        assertTrue(castedReadPacket.getTiming().isStandardLapTiming());
        assertFalse(castedReadPacket.getTiming().isCustomLapTiming());
        assertFalse(castedReadPacket.getTiming().isNoLapTiming());
        assertEquals(3, castedReadPacket.getTiming().getNumberOfCheckpoints());
        assertEquals("FE1R", castedReadPacket.getTrack());
        assertEquals(1, castedReadPacket.getWeather());
        assertEquals(Wind.STRONG, castedReadPacket.getWind());
        assertFlagsEqual(
                RaceFlag.class,
                Set.of(RaceFlag.CAN_VOTE, RaceFlag.MID_RACE, RaceFlag.FCV),
                castedReadPacket.getFlags()
        );
        assertEquals(1000, castedReadPacket.getNumNodes());
        assertEquals(0, castedReadPacket.getFinish());
        assertEquals(224, castedReadPacket.getSplit1());
        assertEquals(589, castedReadPacket.getSplit2());
        assertEquals(701, castedReadPacket.getSplit3());
    }

    @Test
    void readNcnPacket() {
        var headerBytes = new byte[] { 14, 18, -112 };
        var dataBytes = new byte[] {
                21, 116, 104, 101, 117, 115, 101, 114, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 78, 101, 119, 32, 85, 115, 101,
                114, 32, 78, 105, 99, 107, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 1, 34, 4, 0
        };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(53, PacketType.NCN, 144, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof NcnPacket);

        var castedReadPacket = (NcnPacket) readPacket;

        assertPacketHeaderEquals(56, PacketType.NCN, 144, castedReadPacket);
        assertEquals(21, castedReadPacket.getUcid());
        assertEquals("theuser", castedReadPacket.getUName());
        assertEquals("New User Nick", castedReadPacket.getPName());
        assertTrue(castedReadPacket.isAdmin());
        assertEquals(34, castedReadPacket.getTotal());
        assertFlagsEqual(NcnFlag.class, Set.of(NcnFlag.REMOTE), castedReadPacket.getFlags());
    }

    @Test
    void readNciPacket() {
        var headerBytes = new byte[] { 4, 57, -112 };
        var dataBytes = new byte[] { 31, 17, 0, 0, 0, 112, -42, 0, 0, 54, 120, -95, 64 };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(13, PacketType.NCI, 144, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof NciPacket);

        var castedReadPacket = (NciPacket) readPacket;

        assertPacketHeaderEquals(16, PacketType.NCI, 144, castedReadPacket);
        assertEquals(31, castedReadPacket.getUcid());
        assertEquals(Language.POLSKI, castedReadPacket.getLanguage());
        assertEquals(54896, castedReadPacket.getUserId());
        assertEquals(1084323894, castedReadPacket.getIpAddress());
    }

    @Test
    void readSlcPacket_withDefaultCar() {
        var headerBytes = new byte[] { 2, 62, -112 };
        var dataBytes = new byte[] { 9, 88, 82, 84, 0 };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(5, PacketType.SLC, 144, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof SlcPacket);

        var castedReadPacket = (SlcPacket) readPacket;

        assertPacketHeaderEquals(8, PacketType.SLC, 144, castedReadPacket);
        assertEquals(9, castedReadPacket.getUcid());
        assertTrue(castedReadPacket.getCar().getDefaultCar().isPresent());
        assertEquals(DefaultCar.XRT, castedReadPacket.getCar().getDefaultCar().get());
        assertEquals("XRT", castedReadPacket.getCar().getSkinId());
        assertFalse(castedReadPacket.getCar().isMod());
    }

    @Test
    void readSlcPacket_withModCar() {
        var headerBytes = new byte[] { 2, 62, -112 };
        var dataBytes = new byte[] { 9, -63, 104, 74, 0 };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(5, PacketType.SLC, 144, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof SlcPacket);

        var castedReadPacket = (SlcPacket) readPacket;

        assertPacketHeaderEquals(8, PacketType.SLC, 144, castedReadPacket);
        assertEquals(9, castedReadPacket.getUcid());
        assertTrue(castedReadPacket.getCar().getDefaultCar().isEmpty());
        assertEquals("4A68C1", castedReadPacket.getCar().getSkinId());
        assertTrue(castedReadPacket.getCar().isMod());
    }

    @Test
    void readMalPacket() {
        var headerBytes = new byte[] { 5, 65, -112 };
        var dataBytes = new byte[] { 3, 28, 0, 0, 0, 46, 71, 61, 0, 54, -84, 87, 0, -72, 65, -97, 0 };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(17, PacketType.MAL, 144, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof MalPacket);

        var castedReadPacket = (MalPacket) readPacket;

        assertPacketHeaderEquals(20, PacketType.MAL, 144, castedReadPacket);
        assertEquals(3, castedReadPacket.getNumM());
        assertEquals(28, castedReadPacket.getUcid());
        assertEquals(3, castedReadPacket.getSkinId().size());
        assertEquals("3D472E", castedReadPacket.getSkinId().get(0));
        assertEquals("57AC36", castedReadPacket.getSkinId().get(1));
        assertEquals("9F41B8", castedReadPacket.getSkinId().get(2));
    }

    @Test
    void readCimPacket_withNormalMode() {
        var headerBytes = new byte[] { 2, 64, 0 };
        var dataBytes = new byte[] { 12, 0, 2, 0, 0 };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(5, PacketType.CIM, 0, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof CimPacket);

        var castedReadPacket = (CimPacket) readPacket;

        assertPacketHeaderEquals(8, PacketType.CIM, 0, castedReadPacket);
        assertEquals(12, castedReadPacket.getUcid());
        assertEquals(InterfaceMode.NORMAL, castedReadPacket.getMode());
        assertTrue(castedReadPacket.getNormalSubmode().isPresent());
        assertEquals(NormalInterfaceSubmode.WHEEL_DAMAGE, castedReadPacket.getNormalSubmode().get());
        assertTrue(castedReadPacket.getGarageSubmode().isEmpty());
        assertTrue(castedReadPacket.getShiftUSubmode().isEmpty());
        assertEquals(SelectedObjectType.NULL, castedReadPacket.getSelType());
    }

    @Test
    void readCimPacket_withGarageMode() {
        var headerBytes = new byte[] { 2, 64, 0 };
        var dataBytes = new byte[] { 12, 3, 5, 0, 0 };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(5, PacketType.CIM, 0, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof CimPacket);

        var castedReadPacket = (CimPacket) readPacket;

        assertPacketHeaderEquals(8, PacketType.CIM, 0, castedReadPacket);
        assertEquals(12, castedReadPacket.getUcid());
        assertEquals(InterfaceMode.GARAGE, castedReadPacket.getMode());
        assertTrue(castedReadPacket.getNormalSubmode().isEmpty());
        assertTrue(castedReadPacket.getGarageSubmode().isPresent());
        assertEquals(GarageInterfaceSubmode.DRIVE, castedReadPacket.getGarageSubmode().get());
        assertTrue(castedReadPacket.getShiftUSubmode().isEmpty());
        assertEquals(SelectedObjectType.NULL, castedReadPacket.getSelType());
    }

    @Test
    void readCimPacket_withShiftUMode() {
        var headerBytes = new byte[] { 2, 64, 0 };
        var dataBytes = new byte[] { 12, 6, 2, -118, 0 };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(5, PacketType.CIM, 0, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof CimPacket);

        var castedReadPacket = (CimPacket) readPacket;

        assertPacketHeaderEquals(8, PacketType.CIM, 0, castedReadPacket);
        assertEquals(12, castedReadPacket.getUcid());
        assertEquals(InterfaceMode.SHIFTU, castedReadPacket.getMode());
        assertTrue(castedReadPacket.getNormalSubmode().isEmpty());
        assertTrue(castedReadPacket.getGarageSubmode().isEmpty());
        assertTrue(castedReadPacket.getShiftUSubmode().isPresent());
        assertEquals(ShiftUInterfaceSubmode.EDIT, castedReadPacket.getShiftUSubmode().get());
        assertEquals(SelectedObjectType.POST_RED, castedReadPacket.getSelType());
    }

    @Test
    void readCimPacket_withOtherMode() {
        var headerBytes = new byte[] { 2, 64, 0 };
        var dataBytes = new byte[] { 12, 4, 0, 1, 0 };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(5, PacketType.CIM, 0, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof CimPacket);

        var castedReadPacket = (CimPacket) readPacket;

        assertPacketHeaderEquals(8, PacketType.CIM, 0, castedReadPacket);
        assertEquals(12, castedReadPacket.getUcid());
        assertEquals(InterfaceMode.CAR_SELECT, castedReadPacket.getMode());
        assertTrue(castedReadPacket.getNormalSubmode().isEmpty());
        assertTrue(castedReadPacket.getGarageSubmode().isEmpty());
        assertTrue(castedReadPacket.getShiftUSubmode().isEmpty());
        assertEquals(SelectedObjectType.NULL, castedReadPacket.getSelType());
    }

    private void assertPacketHeaderEquals(int expectedDataBytesCount,
                                          PacketType expectedPacketType,
                                          int expectedReqI,
                                          PacketReader packetReader) {
        assertEquals(expectedPacketType, packetReader.getPacketType());
        assertEquals(expectedDataBytesCount, packetReader.getDataBytesCount());
        assertEquals(expectedReqI, packetReader.getPacketReqI());
    }

    private void assertPacketHeaderEquals(int expectedSize,
                                          PacketType expectedPacketType,
                                          int expectedReqI,
                                          InfoPacket packet) {
        assertEquals(expectedSize, packet.getSize());
        assertEquals(expectedPacketType, packet.getType());
        assertEquals(expectedReqI, packet.getReqI());
    }
}
