package pl.adrian;

import pl.adrian.packets.IS_INI;
import pl.adrian.packets.IS_MST;
import pl.adrian.packets.flags.Flags;
import pl.adrian.packets.flags.ISF;

import java.io.IOException;
import java.util.Date;

public class Tester {
    public static void main(String[] args) throws IOException {
        var initPacket = new IS_INI(
                new Flags<>(ISF.LOCAL, ISF.CON, ISF.MCI),
                null,
                200,
                "",
                "Tester"
        );
        try (var connection = new InSimConnection("localhost", 29999, initPacket)) {
            var mst = new IS_MST(0, "Hello world - " + new Date());
            while (System.in.read() != -1) {
                connection.send(mst);
            }
        }
    }
}
