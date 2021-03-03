package mongo;

public class MongoParams {
	private String connectionString;
	private String databaseName;
	private String collectionName;

	public String getConnectionString() {
		return connectionString;
	}

	public void setConnectionString(String connectionString) {
		this.connectionString = connectionString;
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public String getCollectionName() {
		return collectionName;
	}

	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}

	public static MongoParams getMongoParams(String prefix) {
		MongoParams params = new MongoParams();
		params.connectionString = System.getenv(prefix + "connectionString");
		params.databaseName = System.getenv(prefix + "databaseName");
		System.out.println(params.databaseName);
		params.collectionName = System.getenv(prefix + "collectionName");
		return params;
	}
}
