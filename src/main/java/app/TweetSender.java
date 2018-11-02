package app;

import java.util.Set;
import java.util.stream.Collectors;

import mongo.MongoMap;
import mongo.MongoParams;
import twitter.StreamTweets;
import users.Topic;

public class TweetSender {
	private static final String PREFIX = "twitter.example.";
	private static Set<String> currentTopics = null;

	public static void main(String[] args) throws InterruptedException {
		int repeats = 0;
		if (args.length > 0) {
			repeats = Integer.parseInt(args[0]);
		}
		MongoParams mongoParams = MongoParams.getMongoParams(PREFIX);
		MongoMap mongoMap = new MongoMap(mongoParams);
		System.out.println(repeats);
		StreamTweets stream = new StreamTweets();
		for (int i = 0; repeats == 0 || i < repeats; i++) {
			Set<String> topics = mongoMap.values().stream().map(user -> user.getTopics()).flatMap(set -> set.stream())
					.collect(Collectors.toSet()).stream().map(Topic::getName).collect(Collectors.toSet());
			if (!topics.equals(currentTopics)) {
				String[] array = new String[topics.size()];
				topics.toArray(array);
				stream.filter(array);
				currentTopics = topics;
				System.out.println(currentTopics);
			}
			Thread.sleep(10000);
		}
	}
}
