package pl.adrian;

import pl.adrian.packets.IS_INI;
import pl.adrian.packets.IS_MST;
import pl.adrian.packets.flags.Flags;
import pl.adrian.packets.flags.ISF;

import java.io.IOException;

public class Tester {
    public static void main(String[] args) throws IOException {
        var initPacket = new IS_INI(
                1,
                0,
                new Flags<>(ISF.LOCAL, ISF.CON, ISF.MCI),
                null,
                200,
                "",
                "Tester"
        );
        try (var connection = new InSimConnection("localhost", 29999, initPacket)) {
            var mst = new IS_MST(0, "Hello world");
            connection.send(mst);
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
