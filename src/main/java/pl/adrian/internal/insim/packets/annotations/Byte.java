package pl.adrian.internal.insim.packets.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation indicating that given field of packet is sent to and from LFS
 * in form of 1-byte unsigned integer
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Byte {
    /**
     * @return minimum allowed value, 0 by default
     */
    short minValue() default 0;

    /**
     * @return maximum allowed value, 255 by default
     */
    short maxValue() default 255;
}
