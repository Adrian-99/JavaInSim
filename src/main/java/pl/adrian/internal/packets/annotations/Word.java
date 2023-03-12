package pl.adrian.internal.packets.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation indicating that given field of packet is sent to and from LFS
 * in form of 2-byte unsigned integer
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Word {
    /**
     * @return minimum allowed value, 0 by default
     */
    int minValue() default 0;

    /**
     * @return maximum allowed value, 65535 by default
     */
    int maxValue() default 65535;
}
