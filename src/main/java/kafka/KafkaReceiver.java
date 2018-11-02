package kafka;

import java.util.Collections;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaReceiver {
	
	private static final Logger logger = LoggerFactory.getLogger(KafkaReceiver.class);
	
	public static Consumer<String, String> createConsumer() {
		Consumer<String, String> consumer = new KafkaConsumer<>(KafkaProps.getKafkaConsumerProps());
		consumer.subscribe(Collections.singletonList("unfiltered"));
		return consumer;
	}

	public static void main(String[] args) {
		int repeats = 0;
		if (args.length > 0) {
			repeats = Integer.parseInt(args[0]);
		}
		Consumer<String, String> consumer = createConsumer();
		for (int i = 0; repeats == 0 || i < repeats; i++) {
			ConsumerRecords<String, String> consumerRecords = consumer.poll(1000);
			// 1000 is the time in milliseconds consumer will wait if no record is found at
			// broker.
			if (consumerRecords.count() == 0) {
				continue;
			}
			// print each record.
			consumerRecords.forEach(record -> {
				logger.info("Record Key {}", record.key());
				logger.info("Record value {}", record.value());
				logger.info("Record partition {}", record.partition());
				logger.info("Record offset {}", record.offset());
			});
			// commits the offset of record to broker.
			consumer.commitAsync();
		}
	}
}
