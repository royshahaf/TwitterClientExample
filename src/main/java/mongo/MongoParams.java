package mongo;

public class MongoParams {
	public String connectionString;
	public String databaseName;
	public String collectionName;

	public static MongoParams getMongoParams(String prefix) {
		MongoParams params = new MongoParams();
		params.connectionString = System.getenv(prefix + "connectionString");
		params.databaseName = System.getenv(prefix + "databaseName");
		params.collectionName = System.getenv(prefix + "collectionName");
		return params;
	}
}
