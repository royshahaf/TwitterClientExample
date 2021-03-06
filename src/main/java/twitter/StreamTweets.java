package twitter;

import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import app.Sender;
import twitter4j.*;
import java.util.HashMap;
import java.util.Map;

public class StreamTweets {

	private final Logger logger = LoggerFactory.getLogger(getClass().getName());

	private final SentimentAnalyzer sentimentAnalyzer = new SentimentAnalyzer();

	private final Map<String, Integer> sentimentCounters = new HashMap<>();

	private final TwitterStream twitterStream = new TwitterStreamFactory().getInstance()
			.addListener(new StatusListener() {
				@Override
				public void onStatus(Status status) {
					TweetWithSentiment tweetWithSentiment = sentimentAnalyzer.findSentiment(status.getText());
					String sentiment = tweetWithSentiment.getCssClass();
					sentimentCounters.merge(sentiment, 1, Integer::sum);
					logger.debug("@{} - {}, {} / {}", status.getUser().getScreenName(), sentiment, sentimentCounters.get(sentiment), sentimentCounters.values().stream().reduce(0, Integer::sum));
					meters.get(SentimentAnalyzer.toCss(tweetWithSentiment.getSentiment())).mark();
					logger.debug("@{} - {}", status.getUser().getScreenName(), tweetWithSentiment);
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

	private final Map<String, Meter> meters;
	private final Sender<Status> sender;

	public StreamTweets(Sender<Status> sender, MetricRegistry metrics) {
		this.sender = sender;
		meters = new HashMap<>();
		for (int i = -1; i < 5; i++) {
			meters.put(SentimentAnalyzer.toCss(i), metrics.meter(SentimentAnalyzer.toCss(i)));
		}
	}

	public void filter(String... topics) {
		twitterStream.filter(topics);
	}

}
