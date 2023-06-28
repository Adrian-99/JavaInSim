package pl.adrian.api.outsim;

import pl.adrian.api.outsim.flags.OutSimOpts;
import pl.adrian.api.outsim.structures.*;
import pl.adrian.api.packets.flags.Flags;
import pl.adrian.internal.packets.util.PacketDataBytes;

import java.util.Optional;

/**
 * This class represents OutSim packet sent by LFS. Size depends on OutSim Opts.
 */
public class OutSimPacket2 {
    private final Optional<OutSimHeader> osHeader;
    private final Optional<OutSimId> osId;
    private final Optional<OutSimTime> osTime;
    private final Optional<OutSimMain> osMain;
    private final Optional<OutSimInputs> osInputs;
    private final Optional<OutSimDrive> osDrive;
    private final Optional<OutSimDistance> osDistance;
    private final Optional<OutSimWheels> osWheels;
    private final Optional<OutSimExtra1> osExtra1;

    OutSimPacket2(Flags<OutSimOpts> opts, PacketDataBytes packetDataBytes) {
        osHeader = opts.hasFlag(OutSimOpts.HEADER) ?
                Optional.of(new OutSimHeader(packetDataBytes)) :
                Optional.empty();
        osId = opts.hasFlag(OutSimOpts.ID) ?
                Optional.of(new OutSimId(packetDataBytes)) :
                Optional.empty();
        osTime = opts.hasFlag(OutSimOpts.TIME) ?
                Optional.of(new OutSimTime(packetDataBytes)) :
                Optional.empty();
        osMain = opts.hasFlag(OutSimOpts.MAIN) ?
                Optional.of(new OutSimMain(packetDataBytes)) :
                Optional.empty();
        osInputs = opts.hasFlag(OutSimOpts.INPUTS) ?
                Optional.of(new OutSimInputs(packetDataBytes)) :
                Optional.empty();
        osDrive = opts.hasFlag(OutSimOpts.DRIVE) ?
                Optional.of(new OutSimDrive(packetDataBytes)) :
                Optional.empty();
        osDistance = opts.hasFlag(OutSimOpts.DISTANCE) ?
                Optional.of(new OutSimDistance(packetDataBytes)) :
                Optional.empty();
        osWheels = opts.hasFlag(OutSimOpts.WHEELS) ?
                Optional.of(new OutSimWheels(packetDataBytes)) :
                Optional.empty();
        osExtra1 = opts.hasFlag(OutSimOpts.EXTRA_1) ?
                Optional.of(new OutSimExtra1(packetDataBytes)) :
                Optional.empty();
    }

    OutSimPacket2(PacketDataBytes packetDataBytes) {
        osTime = Optional.of(new OutSimTime(packetDataBytes));
        osMain = Optional.of(new OutSimMain(packetDataBytes));
        osId = Optional.of(new OutSimId(packetDataBytes));
        osHeader = Optional.empty();
        osInputs = Optional.empty();
        osDrive = Optional.empty();
        osDistance = Optional.empty();
        osWheels = Optional.empty();
        osExtra1 = Optional.empty();
    }

    /**
     * @return structure for header
     */
    public Optional<OutSimHeader> getOsHeader() {
        return osHeader;
    }

    /**
     * @return structure for ID
     */
    public Optional<OutSimId> getOsId() {
        return osId;
    }

    /**
     * @return structure for time
     */
    public Optional<OutSimTime> getOsTime() {
        return osTime;
    }

    /**
     * @return structure for main data
     */
    public Optional<OutSimMain> getOsMain() {
        return osMain;
    }

    /**
     * @return structure for inputs
     */
    public Optional<OutSimInputs> getOsInputs() {
        return osInputs;
    }

    /**
     * @return structure for drive
     */
    public Optional<OutSimDrive> getOsDrive() {
        return osDrive;
    }

    /**
     * @return structure for distance
     */
    public Optional<OutSimDistance> getOsDistance() {
        return osDistance;
    }

    /**
     * @return structures for wheels
     */
    public Optional<OutSimWheels> getOsWheels() {
        return osWheels;
    }

    /**
     * @return structure for extra 1
     */
    public Optional<OutSimExtra1> getOsExtra1() {
        return osExtra1;
    }
}
