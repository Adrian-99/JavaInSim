package pl.adrian.internal.packets.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation indicating that given field of packet is sent to and from LFS
 * in form of array of 1-byte characters
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CharArray {
    /**
     * @return Length of the character array
     */
    int length();
}
