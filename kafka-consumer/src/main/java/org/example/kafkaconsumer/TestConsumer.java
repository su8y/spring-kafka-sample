package org.example.kafkaconsumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TestConsumer {
	@KafkaListener(topics = {"test-events-1"}, groupId = "group_1")
	public void listener(Object data){
		System.out.println("TestConsumer.listener" + data);
	}
}
