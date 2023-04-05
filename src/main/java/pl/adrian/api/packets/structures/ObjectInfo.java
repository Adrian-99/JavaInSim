package pl.adrian.api.packets.structures;

import pl.adrian.api.packets.enums.ObjectType;
import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.annotations.Short;
import pl.adrian.internal.packets.exceptions.PacketReadingException;

import java.util.Arrays;
import java.util.Optional;

/**
 * This class holds information about layout object.
 */
public class ObjectInfo {
    @Short
    private final short x;
    @Short
    private final short y;
    @Byte
    private final short zByte;
    @Byte
    private final short flags;
    @Byte
    private final short index;
    @Byte
    private final short heading;

    /**
     * Creates object information.
     * @param bytes data bytes - should be 6 bytes long
     */
    public ObjectInfo(short[] bytes) {
        if (bytes.length == 6) {
            x = bytes[0];
            y = bytes[1];
            zByte = bytes[2];
            flags = bytes[3];
            index = bytes[4];
            heading = bytes[5];
        } else {
            throw new PacketReadingException("Incorrect input for creating object info structure: " + Arrays.toString(bytes));
        }
    }

    /**
     * @return X position (1 metre = 16)
     */
    public short getX() {
        return x;
    }

    /**
     * @return Y position (1 metre = 16)
     */
    public short getY() {
        return y;
    }

    /**
     * @return height (1m = 4)
     */
    public short getZByte() {
        return zByte;
    }

    /**
     * @return object index
     */
    public ObjectType getIndex() {
        return ObjectType.fromOrdinal(index);
    }

    /**
     * @return marshall circle info, or empty if it's not marshall circle
     */
    public Optional<MarshallCircleInfo> getMarshallCircleInfo() {
        if (index == 255) {
            return Optional.of(new MarshallCircleInfo(flags, heading));
        } else {
            return Optional.empty();
        }
    }

    /**
     * @return whether is an unknown object
     */
    public boolean isUnknownObject() {
        return index >= 192 && index != 255;
    }

    /**
     * @return control object info, or empty is it's not control object
     */
    public Optional<ControlObjectInfo> getControlObjectInfo() {
        if (index < 192 && (flags & 0x80) == 0x80) {
            return Optional.of(new ControlObjectInfo(flags, heading));
        } else {
            return Optional.empty();
        }
    }

    /**
     * @return autocross object info, or empty if it's not autocross object
     */
    public Optional<AutocrossObjectInfo> getAutocrossObjectInfo() {
        if (index < 192 && (flags & 0x80) == 0) {
            return Optional.of(new AutocrossObjectInfo(flags, heading));
        } else {
            return Optional.empty();
        }
    }

    /**
     * This class hold information about marshall circle.
     */
    public static class MarshallCircleInfo {
        private final Optional<RestrictedAreaInfo> restrictedAreaInfo;
        private final Optional<RouteCheckerInfo> routeCheckerInfo;

        private MarshallCircleInfo(short flags, short heading) {
            if ((flags & 0x80) == 0x80) {
                restrictedAreaInfo = Optional.of(new RestrictedAreaInfo(flags, heading));
                routeCheckerInfo = Optional.empty();
            } else {
                restrictedAreaInfo = Optional.empty();
                routeCheckerInfo = Optional.of(new RouteCheckerInfo(flags, heading));
            }
        }

        /**
         * @return restricted area info, or empty if it's not restricted area
         */
        public Optional<RestrictedAreaInfo> getRestrictedAreaInfo() {
            return restrictedAreaInfo;
        }

        /**
         * @return route checker info, or empty if it's not route checker
         */
        public Optional<RouteCheckerInfo> getRouteCheckerInfo() {
            return routeCheckerInfo;
        }

        /**
         * This class hold information about restricted area.
         */
        public static class RestrictedAreaInfo {
            private final short flags;
            private final short heading;

            private RestrictedAreaInfo(short flags, short heading) {
                this.flags = flags;
                this.heading = heading;
            }

            /**
             * @return heading
             */
            public short getHeading() {
                return heading;
            }

            /**
             * @return whether is no marshall
             */
            public boolean isNoMarshall() {
                return (flags & 3) == 0;
            }

            /**
             * @return whether is standing marshall
             */
            public boolean isMarshallStanding() {
                return (flags & 3) == 1;
            }

            /**
             * @return whether is marshall pointing left
             */
            public boolean isMarshallPointingLeft() {
                return (flags & 3) == 2;
            }

            /**
             * @return whether is marshall pointing right
             */
            public boolean isMarshallPointingRight() {
                return (flags & 3) == 3;
            }

            /**
             * @return radius in meters
             */
            public byte getRadius() {
                return (byte) ((flags >> 2) & 31);
            }
        }

        /**
         * This class holds information about route checker.
         */
        public static class RouteCheckerInfo {
            private final short flags;
            private final short heading;

            private RouteCheckerInfo(short flags, short heading) {
                this.flags = flags;
                this.heading = heading;
            }

            /**
             * @return route index
             */
            public short getRouteIndex() {
                return heading;
            }

            /**
             * @return radius in meters
             */
            public byte getRadius() {
                return (byte) ((flags >> 2) & 31);
            }
        }
    }

    /**
     * This class hold information about control object.
     */
    public static class ControlObjectInfo {
        private final short flags;
        private final short heading;

        private ControlObjectInfo(short flags, short heading) {
            this.flags = flags;
            this.heading = heading;
        }

        /**
         * @return heading
         */
        public short getHeading() {
            return heading;
        }

        /**
         * @return whether is start position
         */
        public boolean isStartPosition() {
            return (flags & 3) == 0 && getWidth() == 0;
        }

        /**
         * @return whether is finish line
         */
        public boolean isFinishLine() {
            return (flags & 3) == 0 && getWidth() > 0;
        }

        /**
         * @return whether is checkpoint 1
         */
        public boolean isCheckpoint1() {
            return (flags & 3) == 1;
        }

        /**
         * @return whether is checkpoint 2
         */
        public boolean isCheckpoint2() {
            return (flags & 3) == 2;
        }

        /**
         * @return whether is checkpoint 3
         */
        public boolean isCheckpoint3() {
            return (flags & 3) == 3;
        }

        /**
         * @return half width in metres
         */
        public byte getWidth() {
            return (byte) ((flags >> 2) & 31);
        }
    }

    /**
     * This class hold information about autocross object.
     */
    public static class AutocrossObjectInfo {
        private final short flags;
        private final short heading;

        private AutocrossObjectInfo(short flags, short heading) {
            this.flags = flags;
            this.heading = heading;
        }

        /**
         * @return heading
         */
        public short getHeading() {
            return heading;
        }

        /**
         * @return colour - only used for chalk (0-3) and tyres (0-5)
         */
        public byte getColour() {
            return (byte) (flags & 7);
        }
    }
}
