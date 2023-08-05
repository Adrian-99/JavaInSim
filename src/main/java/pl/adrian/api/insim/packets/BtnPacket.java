package pl.adrian.api.insim.packets;

import pl.adrian.api.common.flags.Flags;
import pl.adrian.api.insim.packets.enums.PacketType;
import pl.adrian.api.insim.packets.flags.ButtonInstFlag;
import pl.adrian.api.insim.packets.flags.ButtonStyle;
import pl.adrian.internal.insim.packets.annotations.Array;
import pl.adrian.internal.insim.packets.annotations.Byte;
import pl.adrian.internal.insim.packets.annotations.Char;
import pl.adrian.internal.insim.packets.base.AbstractPacket;
import pl.adrian.internal.insim.packets.base.InstructionPacket;
import pl.adrian.internal.insim.packets.enums.ValidationFailureCategory;
import pl.adrian.internal.insim.packets.exceptions.PacketValidationException;
import pl.adrian.internal.insim.packets.util.Constants;
import pl.adrian.internal.insim.packets.util.PacketBuilder;
import pl.adrian.internal.insim.packets.util.PacketUtils;
import pl.adrian.internal.insim.packets.util.PacketValidator;

/**
 * BuTtoN. Sends button to LFS.<br>
 * Host buttons and local buttons are stored separately, so there is no chance of a conflict between
 * a host control system and a local system (although the buttons could overlap on screen).<br>
 * Programmers of local InSim programs may wish to consider using a configurable button range and
 * possibly screen position, in case their users will use more than one local InSim program at once.
 */
public class BtnPacket extends AbstractPacket implements InstructionPacket {
    @Byte
    private final short ucid;
    @Byte(maxValue = Constants.BUTTON_MAX_CLICK_ID)
    private final short clickID;
    @Byte
    private final Flags<ButtonInstFlag> inst;
    @Byte
    private final Flags<ButtonStyle> bStyle;
    @Byte
    private final short typeIn;
    @Byte(maxValue = 200)
    private final short l;
    @Byte(maxValue = 200)
    private final short t;
    @Byte(maxValue = 200)
    private final short w;
    @Byte(maxValue = 200)
    private final short h;
    @Char
    @Array(length = 240, dynamicLength = true)
    private final String text;

    /**
     * Creates button packet for type-in button.
     * @param reqI non-zero (returned in {@link BtcPacket} and {@link BttPacket} packets)
     * @param ucid connection to display the button (0 = local / 255 = all)
     * @param clickID button ID (0 to {@value Constants#BUTTON_MAX_CLICK_ID})
     * @param inst some extra flags
     * @param bStyle button style flags
     * @param typeInCharacters max chars to type in (0 to 95) - if greater than 0, on clicking the button, a text entry
     *                         dialog will be opened, allowing the specified number of characters to be typed in
     * @param initializeWithButtonText set to initialise dialog with the button's text
     * @param l left (0 to 200)
     * @param t top (0 to 200)
     * @param w width (0 to 200)
     * @param h height (0 to 200)
     * @param text button text
     * @param caption dialog caption (optional)
     * @throws PacketValidationException if validation of any field in packet fails
     */
    @SuppressWarnings("java:S107")
    public BtnPacket(int reqI,
                     int ucid,
                     int clickID,
                     Flags<ButtonInstFlag> inst,
                     Flags<ButtonStyle> bStyle,
                     int typeInCharacters,
                     boolean initializeWithButtonText,
                     int l,
                     int t,
                     int w,
                     int h,
                     String text,
                     String caption) throws PacketValidationException {
        super(calculatePacketSize(text, caption), PacketType.BTN, reqI);
        this.ucid = (short) ucid;
        this.clickID = (short) clickID;
        this.inst = inst;
        this.bStyle = bStyle;
        this.typeIn = initializeWithButtonText && typeInCharacters > 0 ?
                (short) (128 + typeInCharacters) :
                (short) typeInCharacters;
        this.l = (short) l;
        this.t = (short) t;
        this.w = (short) w;
        this.h = (short) h;
        this.text = getTextFieldValue(text, caption);
        if (typeInCharacters > 95) {
            throw new PacketValidationException(
                    "typeIn",
                    ValidationFailureCategory.VALUE_OUT_OF_RANGE,
                    "Maximum number of characters to type in exceeded (95)"
            );
        }
        PacketValidator.validate(this);
    }

    /**
     * Creates button packet for non-type-in button.
     * @param reqI non-zero (returned in {@link BtcPacket} and {@link BttPacket} packets)
     * @param ucid connection to display the button (0 = local / 255 = all)
     * @param clickID button ID (0 to {@value Constants#BUTTON_MAX_CLICK_ID})
     * @param inst some extra flags
     * @param bStyle button style flags
     * @param l left (0 to 200)
     * @param t top (0 to 200)
     * @param w width (0 to 200)
     * @param h height (0 to 200)
     * @param text button text
     * @throws PacketValidationException if validation of any field in packet fails
     */
    @SuppressWarnings("java:S107")
    public BtnPacket(int reqI,
                     int ucid,
                     int clickID,
                     Flags<ButtonInstFlag> inst,
                     Flags<ButtonStyle> bStyle,
                     int l,
                     int t,
                     int w,
                     int h,
                     String text) throws PacketValidationException {
        this(reqI, ucid, clickID, inst, bStyle, 0, false, l, t, w, h, text, null);
    }

    /**
     * Creates button packet for existing button text and caption update.
     * @param reqI non-zero (returned in {@link BtcPacket} and {@link BttPacket} packets)
     * @param ucid connection to display the button (0 = local / 255 = all)
     * @param clickID button ID (0 to {@value Constants#BUTTON_MAX_CLICK_ID})
     * @param text button text
     * @param caption dialog caption (optional)
     * @throws PacketValidationException if validation of any field in packet fails
     */
    public BtnPacket(int reqI, int ucid, int clickID, String text, String caption) throws PacketValidationException {
        this(reqI, ucid, clickID, new Flags<>(), new Flags<>(), 0, false, 0, 0, 0, 0, text, caption);
    }

    @Override
    public byte[] getBytes() {
        return new PacketBuilder(size, type, reqI)
                .writeByte(ucid)
                .writeByte(clickID)
                .writeByte(inst.getByteValue())
                .writeByte(bStyle.getByteValue())
                .writeByte(typeIn)
                .writeByte(l)
                .writeByte(t)
                .writeByte(w)
                .writeByte(h)
                .writeCharArray(text, 240, 0)
                .getBytes();
    }

    private static int calculatePacketSize(String text, String caption) {
        return PacketUtils.getPacketSize(12, getTextFieldValue(text, caption), 240);
    }

    private static String getTextFieldValue(String text, String caption) {
        if (caption != null && !caption.isEmpty()) {
            return '\0' + caption + '\0' + text;
        } else {
            return text;
        }
    }
}
