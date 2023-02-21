package pl.adrian.api.packets.enums;

public enum TtcSubtype {
    NONE,		//  0					: not used
    SEL,		//  1 - info request	: send IS_AXM for a layout editor selection
    SEL_START,	//  2 - info request	: send IS_AXM every time the selection changes
    SEL_STOP	//  3 - instruction		: switch off IS_AXM requested by TTC_SEL_START
}
