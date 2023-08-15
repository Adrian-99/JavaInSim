# Java InSim

Java InSim is a library written in Java, that implements [InSim](https://en.lfsmanual.net/wiki/InSim.txt) protocol of
[Live for Speed](https://www.lfs.net/). Current version of the library is compatible with version 9 of
[InSim](https://en.lfsmanual.net/wiki/InSim.txt) protocol.

## Usage

### Creating InSim connection

To create InSim connection, hostname and port where Live for Speed is running have to be provided.
Furthermore, initialization packet (`IsiPacket`) is also required. Detailed description of fields of this
packet can be found in [InSim protocol specification](https://en.lfsmanual.net/wiki/InSim.txt).

```java
var inSimConnection = new InSimConnection(
        "localhost",
        29999,
        new IsiPacket(
                30000,
                new Flags<>(IsiFlag.LOCAL),
                '!',
                500,
                "",
                "Example app"
        )
);
```

### Sending packet

To send the packet to Live for Speed, the `send` method of `InSimConnection` can be used.

```java
inSimConnection.send(new MsxPacket("Hello world!"));
```

### Receiving packets

To handle every received packet of specified type, `listen` method of `InSimConnection` can be used.

```java
inSimConnection.listen(
        VerPacket.class,
        (connection, packet) -> System.out.println(packet.getProduct())
);
```

Some packets can be requested. To do so, use static `request` method of packet class.
The received packet can then be handled either in callback function or as
[CompletableFuture](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/CompletableFuture.html)
(the latter option available only for requests where single packet response is expected).

```java
// handle requested packet in callback function
VerPacket.request(inSimConnection)
        .listen((connection, packet) -> System.out.println(packet.getProduct()));

// handle requested packet as CompletableFuture
VerPacket.request(inSimConnection)
        .asCompletableFuture()
        .thenAccept(packet -> System.out.println(packet.getProduct()));
```

### Creating OutSim connection

The [OutSim](https://en.lfsmanual.net/wiki/OutSim_/_OutGauge) connection can be manually created
by providing port and option flags.

```java
var outSimConnection = new OutSimConnection(
        30000,
        new Flags<>(OutSimOpts.DRIVE, OutSimOpts.INPUTS)
);
```

Alternatively, OutSim connection can be created using existing InSim connection and
its `initializeOutSim` method, which requires interval and option flags. The port on
which OutSim connection will be created is taken from `IsiPacket` provided when
creating InSim connection.

```java
var outSimConnection = inSimConnection.initializeOutSim(
        500,
        new Flags<>(OutSimOpts.DRIVE, OutSimOpts.INPUTS)
);
```

To receive OutSim packets, `listen` method should be used.

```java
outSimConnection.listen(
        packet -> System.out.println(packet.getOsDrive().get().getGear())
);
```

### Creating OutGauge connection

The [OutGauge](https://en.lfsmanual.net/wiki/OutSim_/_OutGauge) connection can be created
by providing port.

```java
var outGaugeConnection = new OutGaugeConnection(30000);
```

Alternatively, existing InSim connection and its `initializeOutGauge` method can be used,
which requires interval. The port on which OutGauge connection will be created is taken
from `IsiPacket` provided when creating InSim connection.

```java
var outGaugeConnection = inSimConnection.initializeOutGauge(500);
```

To receive OutGauge packets, `listen` method should be used.

```java
outGaugeConnection.listen(packet -> System.out.println(packet.getGear()));
```

## License

The library is licensed under [BSD 3-Clause License](https://opensource.org/license/bsd-3-clause/).