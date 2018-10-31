package app;

import mongo.MongoMap;
import mongo.MongoParams;
import server.HttpServerApi;
import server.SecurityParams;

public class App {
	private static final String PREFIX = "twitter.example.";
	private static final HttpServerApi api = new HttpServerApi();

	public static void main(String[] args) {
		int port = getPort();
		SecurityParams params = SecurityParams.getSecurityParams(PREFIX);
		MongoParams mongoParams = MongoParams.getMongoParams(PREFIX);
		api.setPort(port);
		api.start(new MongoMap(mongoParams), params);
	}

	public static void stop() {
		api.stopSpark();
	}

	private static int getPort() {
		String portString = System.getenv(PREFIX + "port");
		try {
			return Integer.parseInt(portString);
		} catch (NumberFormatException e) {
			return 8080;
		}
	}
}
