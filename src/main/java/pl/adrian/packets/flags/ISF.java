package pl.adrian.packets.flags;

public enum ISF {
    RES_0, // 1, bit  0: spare
    RES_1, // 2, bit  1: spare
    LOCAL, // 4, bit  2: guest or single player
    MSO_COLS, // 8, bit  3: keep colours in MSO text
    NLP, // 16, bit  4: receive NLP packets
    MCI, // 32, bit  5: receive MCI packets
    CON, // 64, bit  6: receive CON packets
    OBH, // 128, bit  7: receive OBH packets
    HLV, // 256, bit  8: receive HLV packets
    AXM_LOAD, // 512, bit  9: receive AXM when loading a layout
    AXM_EDIT, // 1024, bit 10: receive AXM when changing objects
    REQ_JOIN // 2048 bit 11: process join requests
}
