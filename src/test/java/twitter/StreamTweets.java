package twitter;

import java.util.logging.Logger;

import twitter4j.*;

public class StreamTweets {

	private final Logger logger = Logger.getLogger(getClass().getName());

	private final TwitterStream twitterStream = new TwitterStreamFactory().getInstance().addListener(new StatusListener() {
        @Override
        public void onStatus(Status status) {
        	logger.info("@" + status.getUser().getScreenName() + " - " + status.getText());
        }

        @Override
        public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
        	logger.info("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
        }

        @Override
        public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
            System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
        }

        @Override
        public void onScrubGeo(long userId, long upToStatusId) {
        	logger.warning("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
        }

        @Override
        public void onStallWarning(StallWarning warning) {
        	logger.warning("Got stall warning:" + warning);
        }

        @Override
        public void onException(Exception ex) {
        	logger.severe("Exception: " + ex);
        }
    });
	
	public  void filter(String... topics) {
		twitterStream.filter(topics);
	}

}
