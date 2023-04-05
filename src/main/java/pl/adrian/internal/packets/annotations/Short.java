package pl.adrian.internal.packets.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation indicating that given field of packet is sent to and from LFS
 * in form of 2-byte signed integer
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Short {
    /**
     * @return minimum allowed value, {@link java.lang.Short#MIN_VALUE} by default
     */
    short minValue() default java.lang.Short.MIN_VALUE;

    /**
     * @return maximum allowed value, {@link java.lang.Short#MAX_VALUE} by default
     */
    short maxValue() default java.lang.Short.MAX_VALUE;
}
