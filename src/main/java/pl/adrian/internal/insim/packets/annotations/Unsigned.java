package pl.adrian.internal.insim.packets.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation indicating that given field of packet is sent to and from LFS
 * in form of array of 4-byte unsigned integer
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Unsigned {
    /**
     * @return minimum allowed value, 0 by default
     */
    long minValue() default 0;

    /**
     * @return maximum allowed value, 4294967295 by default
     */
    long maxValue() default 4294967295L;
}
