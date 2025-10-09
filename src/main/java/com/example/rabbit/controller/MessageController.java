package com.example.rabbit.controller;

import com.example.rabbit.model.Employee;
import com.example.rabbit.model.Student;
import com.example.rabbit.repository.EmployeeRepository;
import com.example.rabbit.repository.RmqRepository;
import com.example.rabbit.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.rabbit.model.RabbitMQMessage;
import com.example.rabbit.producer.RabbitMQProducer;

@RestController
@RequestMapping("/api")
public class MessageController {

	@Autowired
	private RabbitMQProducer rmqProducer;

	@Autowired
	private RmqRepository rmqRepo;

	@Autowired
	private EmployeeRepository employeeRepo;

	@Autowired
	private StudentRepository studentRepo;

//	@GetMapping("/publish")
//	public ResponseEntity<String> sendMessage(@RequestParam("message") String message){
//		rmqProducer.sendMessage(message);
//		return ResponseEntity.ok("Message sent to rmq successfully");
//	}

	@PostMapping("/publish")
	public ResponseEntity<String> sendMessage(@RequestBody RabbitMQMessage request) {
		rmqProducer.sendMessage(request.getMessage());
		rmqRepo.save(request);
		return ResponseEntity.ok("Message sent to RMQ successfully");
	}

	@PostMapping("/publish/employee")
	public Employee saveEmployee(@RequestBody Employee employee) {
		employeeRepo.save(employee);                 // Save first - generates ID
		rmqProducer.sendEmployeeDetails(employee);   // Now send with correct ID
		return employee;
	}

	@PostMapping("/publish/student")
	public Student saveStudent(@RequestBody Student student) {
	studentRepo.save(student);
	rmqProducer.sendStudentDetails(student);
	return student;
	}
}
