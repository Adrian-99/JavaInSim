package pl.adrian.internal;

import pl.adrian.api.InSimConnection;
import pl.adrian.api.packets.IsiPacket;
import pl.adrian.api.packets.MstPacket;
import pl.adrian.api.packets.flags.Flags;
import pl.adrian.api.packets.flags.IsiFlag;

import java.io.IOException;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Tester class - to be deleted later
 */
public class Tester {

    /**
     * Tester main method - to be deleted later
     * @param args arguments array
     * @throws IOException if I/O error occurs
     * @throws InterruptedException if interrupted exception happens idk
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        var initPacket = new IsiPacket(
                new Flags<>(IsiFlag.LOCAL, IsiFlag.CON, IsiFlag.MCI),
                null,
                200,
                "",
                "Tester"
        );
        try (var connection = new InSimConnection("localhost", 29999, initPacket)) {
            int read;
            while ((read = System.in.read()) != -1) {
                if (read == 113) {
                    break;
                }
                var mst = new MstPacket(0, "Hello world - " + new Date());
                connection.send(mst);
            }
//            var random = new Random();
//            var executor = Executors.newFixedThreadPool(5);
//            for (var i = 0; i < 5; i++) {
//                executor.submit(() -> {
//                    try {
//                        while (true) {
//                            var mst = new MstPacket(0, "Hello world - " + new Date());
//                            connection.send(mst);
//                            Thread.sleep(random.nextInt(20, 50));
//                        }
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                });
//            }
//            executor.awaitTermination(Integer.MAX_VALUE, TimeUnit.MILLISECONDS);
        }
    }
}
