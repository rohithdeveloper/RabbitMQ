package com.example.rabbit.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RabbitMQConsumer {

	@RabbitListener(queues= {"${rabbitmq.queue.name}"})
	public void consumeMessage(String message) {
		log.info(String.format("Message received -> %s", message));
	}
}
