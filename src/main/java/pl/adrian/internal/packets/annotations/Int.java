package pl.adrian.internal.packets.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation indicating that given field of packet is sent to and from LFS
 * in form of 4-byte signed integer
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Int {
    /**
     * @return minimum allowed value, {@link Integer#MIN_VALUE} by default
     */
    int minValue() default Integer.MIN_VALUE;

    /**
     * @return maximum allowed value, {@link Integer#MAX_VALUE} by default
     */
    int maxValue() default Integer.MAX_VALUE;
}
