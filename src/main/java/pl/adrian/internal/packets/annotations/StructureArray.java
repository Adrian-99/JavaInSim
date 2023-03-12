package pl.adrian.internal.packets.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation indicating that given field of packet is sent to and from LFS
 * in form of array of structures.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface StructureArray {
    /**
     * @return length of the structures array
     */
    int length();
}
