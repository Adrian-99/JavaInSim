package pl.adrian.api.packets.flags;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FlagsTest {
    @Test
    void createFlags_fromFlag() {
        var flags = new Flags<>(IsiFlag.LOCAL, IsiFlag.MSO_COLS, IsiFlag.NLP, IsiFlag.CON, IsiFlag.REQ_JOIN);

        assertEquals(2140, flags.getValue());
        assertTrue(flags.hasNoFlag(IsiFlag.RES_0));
        assertTrue(flags.hasNoFlag(IsiFlag.RES_1));
        assertTrue(flags.hasFlag(IsiFlag.LOCAL));
        assertTrue(flags.hasFlag(IsiFlag.MSO_COLS));
        assertTrue(flags.hasFlag(IsiFlag.NLP));
        assertTrue(flags.hasNoFlag(IsiFlag.MCI));
        assertTrue(flags.hasFlag(IsiFlag.CON));
        assertTrue(flags.hasNoFlag(IsiFlag.OBH));
        assertTrue(flags.hasNoFlag(IsiFlag.HLV));
        assertTrue(flags.hasNoFlag(IsiFlag.AXM_LOAD));
        assertTrue(flags.hasNoFlag(IsiFlag.AXM_EDIT));
        assertTrue(flags.hasFlag(IsiFlag.REQ_JOIN));
    }

    @Test
    void createFlags_fromFlagWithCustomValue() {
        var flags = new Flags<>(LcsFlag.SIGNALS_HAZARD, LcsFlag.FLASH_OFF, LcsFlag.HORN_3, LcsFlag.SIREN_OFF);

        assertEquals(197403, flags.getValue());
        assertTrue(flags.hasNoFlag(LcsFlag.SIGNALS_OFF));
        assertTrue(flags.hasNoFlag(LcsFlag.SIGNALS_LEFT));
        assertTrue(flags.hasNoFlag(LcsFlag.SIGNALS_RIGHT));
        assertTrue(flags.hasFlag(LcsFlag.SIGNALS_HAZARD));
        assertTrue(flags.hasFlag(LcsFlag.FLASH_OFF));
        assertTrue(flags.hasNoFlag(LcsFlag.FLASH_ON));
        assertTrue(flags.hasNoFlag(LcsFlag.HEADLIGHTS_OFF));
        assertTrue(flags.hasNoFlag(LcsFlag.HEADLIGHTS_ON));
        assertTrue(flags.hasNoFlag(LcsFlag.HORN_OFF));
        assertTrue(flags.hasNoFlag(LcsFlag.HORN_1));
        assertTrue(flags.hasNoFlag(LcsFlag.HORN_2));
        assertTrue(flags.hasFlag(LcsFlag.HORN_3));
        assertTrue(flags.hasNoFlag(LcsFlag.HORN_4));
        assertTrue(flags.hasNoFlag(LcsFlag.HORN_5));
        assertTrue(flags.hasFlag(LcsFlag.SIREN_OFF));
        assertTrue(flags.hasNoFlag(LcsFlag.SIREN_FAST));
        assertTrue(flags.hasNoFlag(LcsFlag.SIREN_SLOW));
    }

    @Test
    void createFlags_fromBinary() {
        var flags = new Flags<>(IsiFlag.class, 1220);

        assertEquals(1220, flags.getValue());
        assertTrue(flags.hasNoFlag(IsiFlag.RES_0));
        assertTrue(flags.hasNoFlag(IsiFlag.RES_1));
        assertTrue(flags.hasFlag(IsiFlag.LOCAL));
        assertTrue(flags.hasNoFlag(IsiFlag.MSO_COLS));
        assertTrue(flags.hasNoFlag(IsiFlag.NLP));
        assertTrue(flags.hasNoFlag(IsiFlag.MCI));
        assertTrue(flags.hasFlag(IsiFlag.CON));
        assertTrue(flags.hasFlag(IsiFlag.OBH));
        assertTrue(flags.hasNoFlag(IsiFlag.HLV));
        assertTrue(flags.hasNoFlag(IsiFlag.AXM_LOAD));
        assertTrue(flags.hasFlag(IsiFlag.AXM_EDIT));
        assertTrue(flags.hasNoFlag(IsiFlag.REQ_JOIN));
    }

    @Test
    void createFlags_fromBinaryOfFlagWithCustomValue() {
        var flags = new Flags<>(LcsFlag.class, 264735);

        assertEquals(264735, flags.getValue());
        assertTrue(flags.hasNoFlag(LcsFlag.SIGNALS_OFF));
        assertTrue(flags.hasNoFlag(LcsFlag.SIGNALS_LEFT));
        assertTrue(flags.hasFlag(LcsFlag.SIGNALS_RIGHT));
        assertTrue(flags.hasNoFlag(LcsFlag.SIGNALS_HAZARD));
        assertTrue(flags.hasFlag(LcsFlag.FLASH_OFF));
        assertTrue(flags.hasNoFlag(LcsFlag.FLASH_ON));
        assertTrue(flags.hasNoFlag(LcsFlag.HEADLIGHTS_OFF));
        assertTrue(flags.hasFlag(LcsFlag.HEADLIGHTS_ON));
        assertTrue(flags.hasNoFlag(LcsFlag.HORN_OFF));
        assertTrue(flags.hasNoFlag(LcsFlag.HORN_1));
        assertTrue(flags.hasNoFlag(LcsFlag.HORN_2));
        assertTrue(flags.hasNoFlag(LcsFlag.HORN_3));
        assertTrue(flags.hasFlag(LcsFlag.HORN_4));
        assertTrue(flags.hasNoFlag(LcsFlag.HORN_5));
        assertTrue(flags.hasFlag(LcsFlag.SIREN_OFF));
        assertTrue(flags.hasNoFlag(LcsFlag.SIREN_FAST));
        assertTrue(flags.hasNoFlag(LcsFlag.SIREN_SLOW));
    }
}
