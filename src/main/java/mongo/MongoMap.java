package mongo;

import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.mongodb.Block;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import users.User;

public class MongoMap implements Map<String, User> {

	private final MongoClient client;
	private final MongoDatabase database;
	private final MongoCollection<User> collection;
	private final CodecRegistry pojoCodecRegistry;

	public MongoMap(String databaseName, String collectionName) {
		pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
				fromProviders(PojoCodecProvider.builder().automatic(true).build()));
		client = MongoClients.create();
		database = client.getDatabase(databaseName);
		collection = database.getCollection(collectionName, User.class).withCodecRegistry(pojoCodecRegistry);
	}

	public MongoMap(String connectionString, String databaseName, String collectionName) {
		pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
				fromProviders(PojoCodecProvider.builder().automatic(true).build()));
		client = MongoClients.create(connectionString);
		database = client.getDatabase(databaseName);
		collection = database.getCollection(collectionName, User.class).withCodecRegistry(pojoCodecRegistry);
	}

	public MongoMap(MongoParams mongoParams) {
		if (mongoParams.connectionString != null) {
			client = MongoClients.create(mongoParams.connectionString);
		} else {
			client = MongoClients.create();
		}
		pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
				fromProviders(PojoCodecProvider.builder().automatic(true).build()));
		database = client.getDatabase(mongoParams.databaseName);
		collection = database.getCollection(mongoParams.collectionName, User.class).withCodecRegistry(pojoCodecRegistry);
	}

	@Override
	public void clear() {
		collection.drop();
	}

	@Override
	public boolean containsKey(Object arg0) {
		return collection.find(eq("name", arg0.toString())).first() != null;
	}

	@Override
	public boolean containsValue(Object arg0) {
		if (arg0.getClass() == User.class) {
			return collection.find(eq("name", ((User) arg0).getName())).first().equals(arg0);
		} else {
			return false;
		}
	}

	@Override
	public Set<Entry<String, User>> entrySet() {
		return new HashSet<>();
	}

	@Override
	public User get(Object arg0) {
		return collection.find(eq("name", arg0.toString())).first();
	}

	@Override
	public boolean isEmpty() {
		return collection.countDocuments() == 0;
	}

	@Override
	public Set<String> keySet() {
		return values().stream().map(User::getName).collect(Collectors.toSet());
	}

	@Override
	public User put(String arg0, User arg1) {
		User previous = get(arg0);
		collection.insertOne(arg1);
		return previous;
	}

	@Override
	public void putAll(Map<? extends String, ? extends User> arg0) {
		collection.insertMany(new ArrayList<>(arg0.values()));
	}

	@Override
	public User remove(Object arg0) {
		User previous = get(arg0);
		collection.deleteOne(eq("name", arg0.toString()));
		return previous;
	}

	@Override
	public int size() {
		return (int) collection.countDocuments();
	}

	@Override
	public Collection<User> values() {
		Set<User> users = new HashSet<>();
		collection.find().forEach(addUser(users));
		return users;
	}

	private Block<User> addUser(Set<User> users) {
		return new Block<User>() {
			@Override
			public void apply(final User user) {
				users.add(user);
			}
		};
	}

	public void close() {
		client.close();
	}
}
