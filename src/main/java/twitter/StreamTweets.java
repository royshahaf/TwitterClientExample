package twitter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import twitter4j.*;

public class StreamTweets {

	private final Logger logger = LoggerFactory.getLogger(getClass().getName());

	private final TwitterStream twitterStream = new TwitterStreamFactory().getInstance()
			.addListener(new StatusListener() {
				@Override
				public void onStatus(Status status) {
					logger.info("@" + status.getUser().getScreenName() + " - " + status.getText());
				}

				@Override
				public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
					logger.warn("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
				}

				@Override
				public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
					logger.warn("Got track limitation notice:" + numberOfLimitedStatuses);
				}

				@Override
				public void onScrubGeo(long userId, long upToStatusId) {
					logger.warn("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
				}

				@Override
				public void onStallWarning(StallWarning warning) {
					logger.warn("Got stall warning:" + warning);
				}

				@Override
				public void onException(Exception ex) {
					logger.error("Exception: " + ex);
				}
			});

	private String[] topics;
	
	public void filter(String... topics) {
		this.topics = topics;
		twitterStream.filter(topics);
	}

}
