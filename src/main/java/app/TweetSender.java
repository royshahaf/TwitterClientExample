package app;

import java.util.Set;
import java.util.stream.Collectors;

import mongo.MongoMap;
import mongo.MongoParams;
import twitter.StreamTweets;
import twitter4j.Status;
import users.Topic;
import websocket.WebSocketSender;

public class TweetSender {
	private static final String PREFIX = "";
	private static Set<String> currentTopics = null;

	public static void main(String[] args) throws InterruptedException {
		int repeats = 0;
		if (args.length > 0) {
			repeats = Integer.parseInt(args[0]);
		}
		MongoParams mongoParams = MongoParams.getMongoParams(PREFIX);
		MongoMap mongoMap = new MongoMap(mongoParams);
		Sender<Status> sender = new WebSocketSender();
		StreamTweets stream = new StreamTweets(sender);
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
