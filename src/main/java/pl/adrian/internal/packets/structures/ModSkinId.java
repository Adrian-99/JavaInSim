package pl.adrian.internal.packets.structures;

import pl.adrian.internal.packets.structures.base.UnsignedInstructionStructure;

/**
 * Mod skin id in 4 bytes - there is an array of these in the {@link pl.adrian.api.packets.MalPacket MalPacket}.
 */
public class ModSkinId implements UnsignedInstructionStructure {
    private static final byte[] hexLettersBytes = "0123456789ABCDEF".getBytes();

    private final String skinId;

    /**
     * Creates mod skin id structure out of its unsigned representation.
     * @param unsignedValue unsigned value of skin id
     */
    public ModSkinId(long unsignedValue) {
        var convertedBytes = new byte[6];
        for (int i = 0; i < 6; i++) {
            convertedBytes[5 - i] = hexLettersBytes[(int) ((unsignedValue >> (i * 4)) & 15)];
        }
        skinId = new String(convertedBytes);
    }

    /**
     * Creates mod skin id structure out of its string representation.
     * @param skinId string value of skin id
     */
    public ModSkinId(String skinId) {
        var skinIdBytes = new char[6];
        var inputToUpper = skinId.toUpperCase();
        for (int i = 0; i < 6 && i < skinId.length(); i++) {
            var character = inputToUpper.charAt(i);
            if ((character >= '0' && character <= '9') || (character >= 'A' && character <= 'F')) {
                skinIdBytes[i] = character;
            }
        }
        this.skinId = new String(skinIdBytes);
    }

    @Override
    public long getUnsignedValue() {
        var result = 0L;
        for (var character : skinId.toCharArray()) {
            int bitsValue = 0;
            if (character >= '0' && character <= '9') {
                bitsValue = character - '0';
            } else if (character >= 'A' && character <= 'F') {
                bitsValue = character - 'A' + 10;
            }
            result <<= 4;
            result |= bitsValue & 0xF;
        }
        return result;
    }

    /**
     * @return string value of skin id
     */
    public String getStringValue() {
        return skinId;
    }
}
