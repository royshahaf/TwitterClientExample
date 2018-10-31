package app;

import mongo.MongoMap;
import mongo.MongoParams;
import server.HttpServerApi;
import server.SecurityParams;

public class App {
	private static final String PREFIX = "twitter.example.";

	public static void main(String[] args) {
		int port = getPort();
		SecurityParams params = getSecurityParams();
		MongoParams mongoParams = getMongoParams();
		HttpServerApi api = new HttpServerApi();
		api.port = port;
		api.start(new MongoMap(mongoParams), params);
	}

	private static MongoParams getMongoParams() {
		MongoParams params = new MongoParams();
		params.connectionString = System.getenv(PREFIX + "connectionString");
		params.databaseName = System.getenv(PREFIX + "databaseName");
		params.collectionName = System.getenv(PREFIX + "collectionName");
		return params;
	}

	private static SecurityParams getSecurityParams() {
		SecurityParams params = new SecurityParams();
		String secureString = System.getenv(PREFIX + "secure");
		params.secure = Boolean.parseBoolean(secureString);
		if (params.secure) {
			params.keystoreFilePath = System.getenv(PREFIX + "keystoreFilePath");
			params.keystorePassword = System.getenv(PREFIX + "keystorePassword");
			params.truststoreFilePath = System.getenv(PREFIX + "truststoreFilePath");
			params.truststorePassword = System.getenv(PREFIX + "truststorePassword");
		}
		return params;
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
