package com.alurkerja.common.service;

import com.alurkerja.pajak.Pajak;
import com.alurkerja.pajak.PajakDto;
import com.alurkerja.pajak.PajakRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class RabbitMqService implements RabbitListenerConfigurer {
    @Autowired
    private PajakRepository pajakRepository;

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar rabbitListenerEndpointRegistrar) {
        /*
        Empty
         */
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue}",  containerFactory = "retryContainerFactory")
    public void receivedMessage(PajakDto pajakDto) {
        Pajak pajak = pajakDto.fromDto();
        pajakRepository.save(pajak);
    }
}