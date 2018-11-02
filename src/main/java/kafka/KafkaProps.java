package kafka;

import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

public class KafkaProps {
	private static final String PREFIX = "twitter.example.";
	public static Properties getKafkaProducerProps() {
		String kafkaBrokers = getKafkaBrokers();
		String clienId = System.getenv(PREFIX + "clienId");
		if (clienId == null) {
			clienId = "client1";
		}
		Properties props = new Properties();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBrokers);
		props.put(ProducerConfig.CLIENT_ID_CONFIG, clienId);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		return props;
	}
	private static String getKafkaBrokers() {
		String kafkaBrokers = System.getenv(PREFIX + "kafkaBrokers");
		if (kafkaBrokers == null) {
			kafkaBrokers = "192.168.99.100:9092";
		}
		return kafkaBrokers;
	}
	public static Properties getKafkaConsumerProps() {
		Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, getKafkaBrokers());
        String groupIdConfig = System.getenv(PREFIX + "groupIdConfig");
		if (groupIdConfig == null) {
			groupIdConfig = "consumerGroup1";
		}
		props.put(ConsumerConfig.GROUP_ID_CONFIG, groupIdConfig);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 1);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        return props;
	}
}
