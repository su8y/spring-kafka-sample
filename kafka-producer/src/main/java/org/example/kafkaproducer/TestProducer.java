package org.example.kafkaproducer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class TestProducer {
	private final KafkaTemplate<String, Object> kafkaTemplate;

	public TestProducer(KafkaTemplate<String, Object> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	public void create(String event) {
		kafkaTemplate.send("topic", event);
	}
}
