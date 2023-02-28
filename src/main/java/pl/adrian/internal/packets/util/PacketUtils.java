package pl.adrian.internal.packets.util;

/**
 * This class contains useful methods for dealing with packets.
 */
public class PacketUtils {
    private PacketUtils() {}

    /**
     * Returns number of bytes needed to put specified text into LFS packet. The minimum size is 4,
     * the size will always be multiply of 4.
     * @param text text to measure
     * @param maxSize maximum allowed size of text
     * @return computed number of bytes
     */
    public static int getLfsCharArraySize(String text, int maxSize) {
        if (text != null) {
            if (maxSize % 4 != 0) {
                maxSize -= maxSize % 4;
            }
            var sizeFromText = text.length() + 1;
            if (sizeFromText % 4 != 0) {
                sizeFromText += 4 - (sizeFromText % 4);
            }
            return Math.min(sizeFromText, maxSize);
        }
        return 4;
    }
}
