package pl.adrian.testutil;

public class TestUtils {
    private TestUtils() {}

    public static short byteToShort(byte value) {
        if (value < 0) {
            return (short) (value + 256);
        } else {
            return value;
        }
    }
}
