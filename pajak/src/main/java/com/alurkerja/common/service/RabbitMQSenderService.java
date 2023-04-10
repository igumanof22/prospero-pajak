package com.alurkerja.common.service;

import com.alurkerja.pajak.PajakDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQSenderService {
    private final RabbitTemplate rabbitTemplate;
    @Value("${spring.rabbitmq.exchange}")
    private String exchange;
    @Value("${spring.rabbitmq.routingkey}")
    private String routingkey;

    @Autowired
    public RabbitMQSenderService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(PajakDto dto){
        rabbitTemplate.convertAndSend(exchange,routingkey, dto);
    }
}
