package twitter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import app.Sender;
import twitter4j.*;

public class StreamTweets {

	private final Logger logger = LoggerFactory.getLogger(getClass().getName());

	private final TwitterStream twitterStream = new TwitterStreamFactory().getInstance()
			.addListener(new StatusListener() {
				@Override
				public void onStatus(Status status) {
					logger.info("@{} - {}", status.getUser().getScreenName(), status.getText());
					sender.send(status);
				}

				@Override
				public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
					logger.warn("Got a status deletion notice id: {}", statusDeletionNotice.getStatusId());
				}

				@Override
				public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
					logger.warn("Got track limitation notice: {}", numberOfLimitedStatuses);
				}

				@Override
				public void onScrubGeo(long userId, long upToStatusId) {
					logger.warn("Got scrub_geo event userId: {} upToStatusId: {}", userId, upToStatusId);
				}

				@Override
				public void onStallWarning(StallWarning warning) {
					logger.warn("Got stall warning: {}", warning);
				}

				@Override
				public void onException(Exception ex) {
					logger.error("Exception: ", ex);
				}
			});

	private Sender<Status> sender;

	public StreamTweets(Sender<Status> sender) {
		this.sender = sender;
	}

	public void filter(String... topics) {
		twitterStream.filter(topics);
	}

}
