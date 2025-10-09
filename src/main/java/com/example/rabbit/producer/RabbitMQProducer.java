package com.example.rabbit.producer;

import com.example.rabbit.model.Employee;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RabbitMQProducer {

	@Value("${rabbitmq.direct.name}")
	private String exchange;

	@Value("${rabbit.routing.Key}")
	private String routingkey;

	@Value(("${rabbit.jsonrouting.Key}"))
	private String jsonroutingkey;

	private RabbitTemplate rabbitTemplate;


	public RabbitMQProducer(RabbitTemplate rabbitTemplate) {
		super();
		this.rabbitTemplate = rabbitTemplate;
	}

	public void sendMessage(String message) {
		log.info(String.format("Message sent -> %s", message));
		rabbitTemplate.convertAndSend(exchange,routingkey,message);
	}

	public void sendEmployeeDetails(Employee employee) {
		log.info("Employee details sent -> {}", employee);
		rabbitTemplate.convertAndSend(exchange, jsonroutingkey, employee);
	}

}