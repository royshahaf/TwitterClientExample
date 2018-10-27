package twitter;

import twitter4j.*;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class SearchTweets {
	private static final Twitter twitter = new TwitterFactory().getInstance();
	private final Logger logger = Logger.getLogger(getClass().getName());

	public List<Status> search(String queryString) {
		try {
			Query query = new Query(queryString);
			QueryResult result;
			do {
				result = twitter.search(query);
				logger.info("search returned " + result.getTweets().size() + " tweets");
				return result.getTweets();
			} while ((query = result.nextQuery()) != null);
		} catch (TwitterException te) {
			logger.warning("search for queryString: " + queryString + " failed with exception: " + te);
			return Collections.emptyList();
		}
	}
}