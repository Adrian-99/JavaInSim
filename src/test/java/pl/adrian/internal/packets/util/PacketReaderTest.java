package pl.adrian.internal.packets.util;

import org.junit.jupiter.api.Test;
import pl.adrian.api.packets.*;
import pl.adrian.api.packets.enums.*;
import pl.adrian.api.packets.enums.DefaultCar;
import pl.adrian.api.packets.flags.*;
import pl.adrian.internal.packets.base.InfoPacket;
import pl.adrian.internal.packets.exceptions.PacketReadingException;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static pl.adrian.testutil.AssertionUtils.assertCarEquals;
import static pl.adrian.testutil.AssertionUtils.assertFlagsEqual;

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
    void readSlcPacket() {
        var headerBytes = new byte[] { 2, 62, -112 };
        var dataBytes = new byte[] { 9, -63, 104, 74, 0 };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(5, PacketType.SLC, 144, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof SlcPacket);

        var castedReadPacket = (SlcPacket) readPacket;

        assertPacketHeaderEquals(8, PacketType.SLC, 144, castedReadPacket);
        assertEquals(9, castedReadPacket.getUcid());
        assertCarEquals("4A68C1", castedReadPacket.getCar());
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

    @Test
    void readCnlPacket() {
        var headerBytes = new byte[] { 2, 19, 0 };
        var dataBytes = new byte[] { 38, 3, 26, 0, 0 };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(5, PacketType.CNL, 0, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof CnlPacket);

        var castedReadPacket = (CnlPacket) readPacket;

        assertPacketHeaderEquals(8, PacketType.CNL, 0, castedReadPacket);
        assertEquals(38, castedReadPacket.getUcid());
        assertEquals(LeaveReason.KICKED, castedReadPacket.getReason());
        assertEquals(26, castedReadPacket.getTotal());
    }

    @Test
    void readCprPacket() {
        var headerBytes = new byte[] { 9, 20, 0 };
        var dataBytes = new byte[] {
                21, 94, 49, 78, 101, 119, 32, 94, 50, 80, 108, 97, 121, 101, 114, 32,
                94, 51, 78, 97, 109, 101, 0, 0, 0, 65, 68, 51, 54, 32, 82, 70,
                89
        };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(33, PacketType.CPR, 0, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof CprPacket);

        var castedReadPacket = (CprPacket) readPacket;

        assertPacketHeaderEquals(36, PacketType.CPR, 0, castedReadPacket);
        assertEquals(21, castedReadPacket.getUcid());
        assertEquals("^1New ^2Player ^3Name", castedReadPacket.getPName());
        assertEquals("AD36 RFY", castedReadPacket.getPlate());
    }

    @Test
    void readNplPacket() {
        var headerBytes = new byte[] { 19, 21, -112 };
        var dataBytes = new byte[] {
                57, 23, 6, 9, 50, 80, 108, 97, 121, 101, 114, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 77, 121, 32,
                80, 108, 97, 116, 101, 85, 70, 49, 0, 100, 101, 102, 97, 117, 108, 116,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, 5, 5, 0, 0, 0,
                100, 0, 0, 0, 0, 7, 3, 1, 54
        };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(73, PacketType.NPL, 144, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof NplPacket);

        var castedReadPacket = (NplPacket) readPacket;

        assertPacketHeaderEquals(76, PacketType.NPL, 144, castedReadPacket);
        assertEquals(57, castedReadPacket.getPlid());
        assertEquals(23, castedReadPacket.getUcid());
        assertFlagsEqual(PlayerTypeFlag.class, Set.of(PlayerTypeFlag.AI, PlayerTypeFlag.REMOTE), castedReadPacket.getPType());
        assertFlagsEqual(
                PlayerFlag.class,
                Set.of(
                        PlayerFlag.LEFTSIDE,
                        PlayerFlag.AUTOGEARS,
                        PlayerFlag.AUTOCLUTCH,
                        PlayerFlag.KB_STABILISED,
                        PlayerFlag.CUSTOM_VIEW
                ),
                castedReadPacket.getFlags()
        );
        assertEquals("Player", castedReadPacket.getPName());
        assertEquals("My Plate", castedReadPacket.getPlate());
        assertCarEquals(DefaultCar.UF1, castedReadPacket.getCar());
        assertEquals("default", castedReadPacket.getSName());
        assertEquals(TyreCompound.ROAD_NORMAL, castedReadPacket.getTyres().getFrontLeft());
        assertEquals(TyreCompound.ROAD_NORMAL, castedReadPacket.getTyres().getFrontRight());
        assertEquals(TyreCompound.ROAD_NORMAL, castedReadPacket.getTyres().getRearLeft());
        assertEquals(TyreCompound.ROAD_NORMAL, castedReadPacket.getTyres().getRearRight());
        assertEquals(0, castedReadPacket.getHMass());
        assertEquals(0, castedReadPacket.getHTRes());
        assertEquals(0, castedReadPacket.getModel());
        assertFlagsEqual(
                PassengerFlag.class,
                Set.of(PassengerFlag.REAR_LEFT_MAN, PassengerFlag.REAR_MIDDLE_WOMAN, PassengerFlag.REAR_RIGHT_MAN),
                castedReadPacket.getPass()
        );
        assertEquals(0, castedReadPacket.getRWAdj());
        assertEquals(0, castedReadPacket.getFWAdj());
        assertFlagsEqual(
                SetupFlag.class,
                Set.of(SetupFlag.SYMM_WHEELS, SetupFlag.TC_ENABLE, SetupFlag.ABS_ENABLE),
                castedReadPacket.getSetF()
        );
        assertEquals(3, castedReadPacket.getNumP());
        assertEquals(1, castedReadPacket.getConfig());
        assertEquals(54, castedReadPacket.getFuel());
    }

    @Test
    void readPlpPacket() {
        var headerBytes = new byte[] { 1, 22, 0 };
        var dataBytes = new byte[] { 31 };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(1, PacketType.PLP, 0, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof PlpPacket);

        var castedReadPacket = (PlpPacket) readPacket;

        assertPacketHeaderEquals(4, PacketType.PLP, 0, castedReadPacket);
        assertEquals(31, castedReadPacket.getPlid());
    }

    @Test
    void readPllPacket() {
        var headerBytes = new byte[] { 1, 23, 0 };
        var dataBytes = new byte[] { 26 };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(1, PacketType.PLL, 0, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof PllPacket);

        var castedReadPacket = (PllPacket) readPacket;

        assertPacketHeaderEquals(4, PacketType.PLL, 0, castedReadPacket);
        assertEquals(26, castedReadPacket.getPlid());
    }

    @Test
    void readCrsPacket() {
        var headerBytes = new byte[] { 1, 41, 0 };
        var dataBytes = new byte[] { 5 };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(1, PacketType.CRS, 0, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof CrsPacket);

        var castedReadPacket = (CrsPacket) readPacket;

        assertPacketHeaderEquals(4, PacketType.CRS, 0, castedReadPacket);
        assertEquals(5, castedReadPacket.getPlid());
    }

    @Test
    void readLapPacket() {
        var headerBytes = new byte[] { 5, 24, 0 };
        var dataBytes = new byte[] {
                15, -12, -35, 0, 0, -15, 125, 3, 0, 4, 0, -111, 0, 0, 5, 1,
                115
        };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(17, PacketType.LAP, 0, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof LapPacket);

        var castedReadPacket = (LapPacket) readPacket;

        assertPacketHeaderEquals(20, PacketType.LAP, 0, castedReadPacket);
        assertEquals(15, castedReadPacket.getPlid());
        assertEquals(56820, castedReadPacket.getLTime());
        assertEquals(228849, castedReadPacket.getETime());
        assertEquals(4, castedReadPacket.getLapsDone());
        assertFlagsEqual(
                PlayerFlag.class,
                Set.of(PlayerFlag.LEFTSIDE, PlayerFlag.SHIFTER, PlayerFlag.AXIS_CLUTCH),
                castedReadPacket.getFlags()
        );
        assertEquals(PenaltyValue.PLUS_30_S, castedReadPacket.getPenalty());
        assertEquals(1, castedReadPacket.getNumStops());
        assertEquals(115, castedReadPacket.getFuel200());
    }

    @Test
    void readSpxPacket() {
        var headerBytes = new byte[] { 4, 25, 0 };
        var dataBytes = new byte[] {
                29, 78, 102, 0, 0, 16, 37, 2, 0, 1, 0, 2, 86
        };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(13, PacketType.SPX, 0, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof SpxPacket);

        var castedReadPacket = (SpxPacket) readPacket;

        assertPacketHeaderEquals(16, PacketType.SPX, 0, castedReadPacket);
        assertEquals(29, castedReadPacket.getPlid());
        assertEquals(26190, castedReadPacket.getSTime());
        assertEquals(140560, castedReadPacket.getETime());
        assertEquals(1, castedReadPacket.getSplit());
        assertEquals(PenaltyValue.NONE, castedReadPacket.getPenalty());
        assertEquals(2, castedReadPacket.getNumStops());
        assertEquals(86, castedReadPacket.getFuel200());
    }

    @Test
    void readPitPacket() {
        var headerBytes = new byte[] { 6, 26, 0 };
        var dataBytes = new byte[] {
                12, 16, 0, 72, 6, 10, 2, 3, 0, 7, -1, -1, 7, -62, -116, 0,
                0, 0, 0, 0, 0
        };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(21, PacketType.PIT, 0, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof PitPacket);

        var castedReadPacket = (PitPacket) readPacket;

        assertPacketHeaderEquals(24, PacketType.PIT, 0, castedReadPacket);
        assertEquals(12, castedReadPacket.getPlid());
        assertEquals(16, castedReadPacket.getLapsDone());
        assertFlagsEqual(
                PlayerFlag.class,
                Set.of(PlayerFlag.AUTOGEARS, PlayerFlag.HELP_B, PlayerFlag.AUTOCLUTCH, PlayerFlag.MOUSE),
                castedReadPacket.getFlags()
        );
        assertEquals(10, castedReadPacket.getFuelAdd());
        assertEquals(PenaltyValue.DT_VALID, castedReadPacket.getPenalty());
        assertEquals(3, castedReadPacket.getNumStops());
        assertEquals(TyreCompound.KNOBBLY, castedReadPacket.getTyres().getFrontRight());
        assertEquals(TyreCompound.NO_CHANGE, castedReadPacket.getTyres().getFrontLeft());
        assertEquals(TyreCompound.NO_CHANGE, castedReadPacket.getTyres().getRearRight());
        assertEquals(TyreCompound.KNOBBLY, castedReadPacket.getTyres().getRearLeft());
        assertFlagsEqual(
                PitWorkFlag.class,
                Set.of(
                        PitWorkFlag.STOP,
                        PitWorkFlag.RI_FR_DAM,
                        PitWorkFlag.RI_FR_WHL,
                        PitWorkFlag.LE_RE_DAM,
                        PitWorkFlag.LE_RE_WHL,
                        PitWorkFlag.BODY_MAJOR
                ),
                castedReadPacket.getWork()
        );
    }

    @Test
    void readPsfPacket() {
        var headerBytes = new byte[] { 3, 27, 0 };
        var dataBytes = new byte[] {
                23, -96, 23, 2, 0, 0, 0, 0, 0
        };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(9, PacketType.PSF, 0, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof PsfPacket);

        var castedReadPacket = (PsfPacket) readPacket;

        assertPacketHeaderEquals(12, PacketType.PSF, 0, castedReadPacket);
        assertEquals(23, castedReadPacket.getPlid());
        assertEquals(137120, castedReadPacket.getSTime());
    }

    @Test
    void readPlaPacket() {
        var headerBytes = new byte[] { 2, 28, 0 };
        var dataBytes = new byte[] { 19, 2, 0, 0, 0 };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(5, PacketType.PLA, 0, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof PlaPacket);

        var castedReadPacket = (PlaPacket) readPacket;

        assertPacketHeaderEquals(8, PacketType.PLA, 0, castedReadPacket);
        assertEquals(19, castedReadPacket.getPlid());
        assertEquals(PitLaneFact.NO_PURPOSE, castedReadPacket.getFact());
    }

    @Test
    void readCchPacket() {
        var headerBytes = new byte[] { 2, 29, 0 };
        var dataBytes = new byte[] { 11, 1, 0, 0, 0 };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(5, PacketType.CCH, 0, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof CchPacket);

        var castedReadPacket = (CchPacket) readPacket;

        assertPacketHeaderEquals(8, PacketType.CCH, 0, castedReadPacket);
        assertEquals(11, castedReadPacket.getPlid());
        assertEquals(ViewIdentifier.HELI, castedReadPacket.getCamera());
    }

    @Test
    void readPenPacket() {
        var headerBytes = new byte[] { 2, 30, 0 };
        var dataBytes = new byte[] { 34, 0, 6, 3, 0 };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(5, PacketType.PEN, 0, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof PenPacket);

        var castedReadPacket = (PenPacket) readPacket;

        assertPacketHeaderEquals(8, PacketType.PEN, 0, castedReadPacket);
        assertEquals(34, castedReadPacket.getPlid());
        assertEquals(PenaltyValue.NONE, castedReadPacket.getOldPen());
        assertEquals(PenaltyValue.PLUS_45_S, castedReadPacket.getNewPen());
        assertEquals(PenaltyReason.FALSE_START, castedReadPacket.getReason());
    }

    @Test
    void readTocPacket() {
        var headerBytes = new byte[] { 2, 31, 0 };
        var dataBytes = new byte[] { 7, 25, 31, 0, 0 };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(5, PacketType.TOC, 0, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof TocPacket);

        var castedReadPacket = (TocPacket) readPacket;

        assertPacketHeaderEquals(8, PacketType.TOC, 0, castedReadPacket);
        assertEquals(7, castedReadPacket.getPlid());
        assertEquals(25, castedReadPacket.getOldUcid());
        assertEquals(31, castedReadPacket.getNewUcid());
    }

    @Test
    void readFlgPacket() {
        var headerBytes = new byte[] { 2, 32, 0 };
        var dataBytes = new byte[] { 19, 1, 2, 15, 0 };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(5, PacketType.FLG, 0, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof FlgPacket);

        var castedReadPacket = (FlgPacket) readPacket;

        assertPacketHeaderEquals(8, PacketType.FLG, 0, castedReadPacket);
        assertEquals(19, castedReadPacket.getPlid());
        assertTrue(castedReadPacket.isOn());
        assertEquals(FlagType.CAUSING_YELLOW, castedReadPacket.getFlag());
        assertEquals(15, castedReadPacket.getCarBehind());
    }

    @Test
    void readPflPacket() {
        var headerBytes = new byte[] { 2, 33, 0 };
        var dataBytes = new byte[] { 22, 9, 11, 0, 0 };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(5, PacketType.PFL, 0, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof PflPacket);

        var castedReadPacket = (PflPacket) readPacket;

        assertPacketHeaderEquals(8, PacketType.PFL, 0, castedReadPacket);
        assertEquals(22, castedReadPacket.getPlid());
        assertFlagsEqual(
                PlayerFlag.class,
                Set.of(
                        PlayerFlag.LEFTSIDE,
                        PlayerFlag.AUTOGEARS,
                        PlayerFlag.INPITS,
                        PlayerFlag.AUTOCLUTCH,
                        PlayerFlag.KB_NO_HELP
                ),
                castedReadPacket.getFlags()
        );
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
