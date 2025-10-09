package com.example.rabbit.consumer;

import com.example.rabbit.model.Employee;
import com.example.rabbit.model.Student;
import org.hibernate.sql.ast.tree.expression.SqlTuple;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RabbitMQConsumer {

	@RabbitListener(queues= {"${rabbitmq.direct.queue.name}"})
	public void consumeMessage(String message) {
		log.info(String.format("Message received -> %s", message));
	}

	@RabbitListener(queues= {"${rabbitmq.json.queue.name}"})
	public void consumeJsonMessage(Employee employee) {
		log.info("Employee details received -> {}", employee);
	}

	@RabbitListener(queues = {"${rabbitmq.fanout.queue.name}"})
	public void consumeFanoutMessage(Student student) {
		log.info("Student details received -> {}", student);
	}
}
