package pl.adrian.internal.packets.enums;

/**
 * This interface must be implemented by all enums, whose values sent or received from LFS
 * should not be based off their ordinal number but rather some custom value.
 */
public interface EnumWithCustomValue {
    /**
     * @return custom value of enum
     */
    int getValue();
}
