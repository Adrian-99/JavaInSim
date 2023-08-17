/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets.flags;

import com.github.adrian99.javainsim.api.insim.packets.AxmPacket;
import com.github.adrian99.javainsim.api.insim.packets.enums.PmoAction;

/**
 * Enumeration for flags used in {@link AxmPacket}.
 */
public enum PmoFlag {
    /**
     * bit 0, value 1: If this flag is set in a {@link PmoAction#LOADING_FILE} packet, LFS has reached the end of
     * a layout file which it is loading. The added objects will then be optimised.<br>
     *
     * Optimised in this case means that static vertex buffers will be created for all
     * objects, to greatly improve the frame rate. The problem with this is that when
     * there are many objects loaded, optimisation causes a significant glitch which can
     * be long enough to cause a driver who is cornering to lose control and crash.<br>
     *
     * This flag can also be set in an {@link AxmPacket} with {@link PmoAction#ADD_OBJECTS}.
     * This causes all objects to be optimised. It is important not to set this flag
     * in every packet you send to add objects, or you will cause severe glitches on the
     * clients computers. It is ok to have some objects on the track which are not
     * optimised. So if you have a few objects that are being removed and added
     * occasionally, the best advice is not to request optimisation at all. Only
     * request optimisation (by setting this flag) if you have added so many objects
     * that it is needed to improve the frame rate.<br>
     *
     * NOTE 1) LFS makes sure that all objects are optimised when the race restarts.<br>
     * NOTE 2) In the 'more' section of SHIFT+U there is info about optimised objects.<br>
     *
     * If you are using InSim to send many packets of objects (for example loading an
     * entire layout through InSim) then you must take care of the bandwidth and buffer
     * overflows. You must not try to send all the objects at once. It's probably good
     * to use LFS's method of doing this: send the first packet of objects then wait for
     * the corresponding {@link AxmPacket} that will be output when the packet is processed. Then
     * you can send the second packet and again wait for the {@link AxmPacket} and so on.
     */
    FILE_END,
    /**
     * bit 1, value 2: When objects are moved or modified in the layout editor, two {@link AxmPacket} packets are
     * sent. A {@link PmoAction#DEL_OBJECTS} followed by a {@link PmoAction#ADD_OBJECTS}. In this case this flag
     * is set in the PMOFlags byte of both packets.
     */
    MOVE_MODIFY,
    /**
     * bit 2, value 4: If you send an {@link AxmPacket} with {@link PmoAction#SELECTION} it is possible for it to be
     * either a selection of real objects (as if the user selected several objects while
     * holding the CTRL key) or a clipboard selection (as if the user pressed CTRL+C after
     * selecting objects). Clipboard is the default selection mode. A real selection can
     * be set by using this flag in the PMOFlags byte.
     */
    SELECTION_REAL,
    /**
     * bit 3, value 8: If you send an {@link AxmPacket} with {@link PmoAction#ADD_OBJECTS} you may wish to set the
     * UCID to one of the guest connections (for example if that user's action caused the
     * objects to be added). In this case some validity checks are done on the guest's
     * computer which may report "invalid position" or "intersecting object" and delete
     * the objects. This can be avoided by setting this flag.
     */
    AVOID_CHECK
}
