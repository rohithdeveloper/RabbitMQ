package com.example.rabbit.producer;

import com.example.rabbit.model.Employee;
import com.example.rabbit.model.Student;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RabbitMQProducer {

	@Value("${rabbitmq.direct.exchange.name}")
	private String directExchange;

	@Value("${rabbitmq.direct.routing.key}")
	private String directRoutingKey;

	@Value("${rabbitmq.json.routing.key}")
	private String jsonRoutingKey;

	@Value("${rabbitmq.fanout.exchange.name}")
	private String fanoutExchange;

	private RabbitTemplate rabbitTemplate;


	public RabbitMQProducer(RabbitTemplate rabbitTemplate) {
		super();
		this.rabbitTemplate = rabbitTemplate;
	}

	public void sendMessage(String message) {
		log.info(String.format("Message sent -> %s", message));
		rabbitTemplate.convertAndSend(directExchange, directRoutingKey, message);
	}

	public void sendEmployeeDetails(Employee employee) {
		log.info("Employee details sent -> {}", employee);
		rabbitTemplate.convertAndSend(directExchange, jsonRoutingKey, employee);
	}

	public void sendStudentDetails(Student student) {
		log.info("Student details sent -> {}", student);
		rabbitTemplate.convertAndSend(fanoutExchange, "", student);
	}

}