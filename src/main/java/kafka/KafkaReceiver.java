package kafka;

import java.util.Collections;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class KafkaReceiver {
	public static Consumer<String, String> createConsumer() {
		Consumer<String, String> consumer = new KafkaConsumer<>(KafkaProps.getKafkaConsumerProps());
		consumer.subscribe(Collections.singletonList("unfiltered"));
		return consumer;
	}

	public static void main(String[] args) {
		Consumer<String, String> consumer = createConsumer();
		while (true) {
			ConsumerRecords<String, String> consumerRecords = consumer.poll(1000);
			// 1000 is the time in milliseconds consumer will wait if no record is found at
			// broker.
			if (consumerRecords.count() == 0) {
				continue;
			}
			// print each record.
			consumerRecords.forEach(record -> {
				System.out.println("Record Key " + record.key());
				System.out.println("Record value " + record.value());
				System.out.println("Record partition " + record.partition());
				System.out.println("Record offset " + record.offset());
			});
			// commits the offset of record to broker.
			consumer.commitAsync();
		}
	}
}
