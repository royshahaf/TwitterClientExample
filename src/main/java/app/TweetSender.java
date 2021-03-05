package app;

import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.codahale.metrics.MetricRegistry;
import com.izettle.metrics.influxdb.InfluxDbHttpSender;
import com.izettle.metrics.influxdb.InfluxDbReporter;
import mongo.MongoMap;
import mongo.MongoParams;
import twitter.StreamTweets;
import twitter4j.Status;
import users.Topic;
import websocket.WebSocketSender;

public class TweetSender {
    private static final String PREFIX = "";
    private static Set<String> currentTopics = null;

    public static void main(String[] args) throws Exception {
        int repeats = 0;
        if (args.length > 1) {
            repeats = Integer.parseInt(args[1]);
        }
        MetricRegistry metrics = new MetricRegistry();
        InfluxDbReporter reporter = InfluxDbReporter.forRegistry(metrics).build(new InfluxDbHttpSender("http", "127.0.0.1", 8086, "hello", args[0], TimeUnit.SECONDS));
        MongoParams mongoParams = MongoParams.getMongoParams(PREFIX);
        MongoMap mongoMap = new MongoMap(mongoParams);
        Sender<Status> sender = new WebSocketSender();
        StreamTweets stream = new StreamTweets(sender, metrics.histogram("sentiment"));
        for (int i = 0; repeats == 0 || i < repeats; i++) {
            Set<String> topics = mongoMap.values().stream().map(user -> user.getTopics()).flatMap(set -> set.stream())
                    .collect(Collectors.toSet()).stream().map(Topic::getName).collect(Collectors.toSet());
            if (!topics.equals(currentTopics)) {
                String[] array = new String[topics.size()];
                topics.toArray(array);
                stream.filter(array);
                currentTopics = topics;
            }
            Thread.sleep(10000);
        }
    }
}
