package pl.adrian.internal;

import pl.adrian.api.InSimConnection;
import pl.adrian.api.packets.IsiPacket;
import pl.adrian.api.packets.MstPacket;
import pl.adrian.api.packets.flags.Flags;
import pl.adrian.api.packets.flags.IsiFlag;

import java.io.IOException;
import java.util.Date;

/**
 * Tester class - to be deleted later
 */
public class Tester {

    /**
     * Tester main method - to be deleted later
     * @param args arguments array
     * @throws IOException if I/O error occurs
     */
    public static void main(String[] args) throws IOException {
        var initPacket = new IsiPacket(
                new Flags<>(IsiFlag.LOCAL, IsiFlag.CON, IsiFlag.MCI),
                null,
                200,
                "",
                "Tester"
        );
        try (var connection = new InSimConnection("localhost", 29999, initPacket)) {
            var mst = new MstPacket(0, "Hello world - " + new Date());
            while (System.in.read() != -1) {
                connection.send(mst);
            }
        }
    }
}
