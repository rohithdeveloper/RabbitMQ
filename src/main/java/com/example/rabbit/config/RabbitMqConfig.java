package com.example.rabbit.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    // ===========================
    // Direct Exchange Configuration
    // ===========================
    @Value("${rabbitmq.direct.queue.name}")
    private String directQueueName;

    @Value("${rabbitmq.direct.exchange.name}")
    private String directExchangeName;

    @Value("${rabbitmq.direct.routing.key}")
    private String directRoutingKey;

    @Bean
    public Queue directQueue() {
        return new Queue(directQueueName);
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(directExchangeName);
    }

    @Bean
    public Binding directBinding() {
        return BindingBuilder.bind(directQueue()).to(directExchange()).with(directRoutingKey);
    }

    // ===========================
    // JSON Queue with Direct Exchange
    // ===========================
    @Value("${rabbitmq.json.queue.name}")
    private String jsonQueueName;

    @Value("${rabbitmq.json.routing.key}")
    private String jsonRoutingKey;

    @Bean
    public Queue jsonQueue() {
        return new Queue(jsonQueueName);
    }

    @Bean
    public Binding jsonBinding() {
        return BindingBuilder.bind(jsonQueue()).to(directExchange()).with(jsonRoutingKey);
    }

    // ===========================
    // Fanout Exchange Configuration
    // ===========================
    @Value("${rabbitmq.fanout.exchange.name}")
    private String fanoutExchangeName;

    @Value("${rabbitmq.fanout.queue.name}")
    private String fanoutQueue1Name;



    @Bean
    public Queue fanoutQueue() {
        return new Queue(fanoutQueue1Name);
    }


    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(fanoutExchangeName);
    }

    @Bean
    public Binding fanoutBinding() {
        return BindingBuilder.bind(fanoutQueue()).to(fanoutExchange());
    }


    // ===========================
    // RabbitMQ Message Converter & Template
    // ===========================
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }
}
