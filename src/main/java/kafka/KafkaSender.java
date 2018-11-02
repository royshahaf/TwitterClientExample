package kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import app.Sender;
import twitter4j.Status;

public class KafkaSender implements Sender<Status> {
	
	private Producer<String, String> producer;
	
	public KafkaSender() {
		producer = createProducer();
	}
	
	public static Producer<String, String> createProducer() {
        return new KafkaProducer<>(KafkaProps.getKafkaProducerProps());
    }
	
	@Override
	public void send(Status message) {
		producer.send(new ProducerRecord<String, String>("unfiltered", message.toString()));
	}

}
