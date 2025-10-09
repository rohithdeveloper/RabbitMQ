package com.example.rabbit.consumer;

import com.example.rabbit.model.Employee;
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

	@RabbitListener(queues= {"${rabbitmq.jsonqueue.name}"})
	public void consumeJsonMessage(Employee employee) {
		log.info("Employee details received -> {}", employee);
	}
}
