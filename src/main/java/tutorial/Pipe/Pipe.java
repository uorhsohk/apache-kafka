package tutorial.Pipe;

import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;

/**
 * first application from Apache Kafka Official Tutorials
 * https://kafka.apache.org/25/documentation/streams/tutorial
 * first Streams application: Pipe
 */
public class Pipe {
    public static void main(String[] args) {

        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "streams-pipe");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        final StreamsBuilder builder = new StreamsBuilder();

        // KStream is continuously generating records from its topic source
        // in this example the topic source is: streams-plaintext-input
        KStream<String, String> source = builder.stream("streams-plaintext-input");
        source.to("streams-pipe-output");

        final Topology topology = builder.build();


        // Construct the Streams client with the two components we have just constructed above
        // the topology from KStream and the properties
        final KafkaStreams streams = new KafkaStreams(topology, props);

        // shutdown hook with a countdown latch
        final CountDownLatch latch = new CountDownLatch(1);

        // attach shutdown handler to catch control-c
        Runtime.getRuntime().addShutdownHook(new Thread("streams-shutdown-hook") {
            @Override
            public void run() {
                streams.close();
                latch.countDown();
            }
        });

        try {
            streams.start();
            latch.await();
        } catch (Throwable e) {
            System.exit(1);
        }
        System.exit(0);
    }
}
