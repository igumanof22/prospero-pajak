package com.alurkerja.config;

import org.aopalliance.aop.Advice;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.interceptor.RetryInterceptorBuilder;
import org.springframework.retry.interceptor.StatefulRetryOperationsInterceptor;

@Configuration
public class RabbitMQConfig {
    @Value("${spring.rabbitmq.queue}")
    private String queue;
    @Value("${spring.rabbitmq.exchange}")
    private String exchange;
    @Value("${spring.rabbitmq.routingkey}")
    private String routingKey;
    @Value("${spring.rabbitmq.username}")
    private String username;
    @Value("${spring.rabbitmq.password}")
    private String password;
    @Value("${spring.rabbitmq.vhost}")
    private String vhost;
    @Value("${spring.rabbitmq.port}")
    private Integer port;
    @Value("${spring.rabbitmq.host}")
    private String host;
    @Bean
    Queue queue() {
        return new Queue(queue, true);
    }
    @Bean
    Exchange myExchange() {
        return ExchangeBuilder.directExchange(exchange).durable(true).build();
    }
    @Bean

    Binding binding() {
        return BindingBuilder
                .bind(queue())
                .to(myExchange())
                .with(routingKey)
                .noargs();
    }
    @Bean
    public StatefulRetryOperationsInterceptor interceptor() {
        return RetryInterceptorBuilder.stateful()
                .maxAttempts(5)
                .backOffOptions(1000,   2.0, 10000) // initialInterval, multiplier, maxInterval
                .build();
    }
    @Bean
    public CachingConnectionFactory cachingConnectionFactory() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(host,  port != null ? port : 5672);
        cachingConnectionFactory.setUsername(username);
        cachingConnectionFactory.setVirtualHost(vhost != null ? vhost : "/");
        cachingConnectionFactory.setPassword(password);
        return cachingConnectionFactory;
    }
    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public SimpleRabbitListenerContainerFactory retryContainerFactory(
            ConnectionFactory connectionFactory, StatefulRetryOperationsInterceptor interceptor) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jackson2JsonMessageConverter());

        Advice[] adviceChain = { interceptor };
        factory.setAdviceChain(adviceChain);

        return factory;
    }

}