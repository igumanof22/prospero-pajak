package com.alurkerja.core.service.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;

public abstract class BpmnNotification<E, V, D>{
    @Autowired
    protected JavaMailSender javaMailSender;
    protected String template;
    protected E entity;
    protected V variable;
    protected D dto;

    public BpmnNotification(E entity, V variable, D dto) {
        this.entity = entity;
        this.variable = variable;
        this.dto = dto;
    }

    public void sendNotification(String[] to, String subject){
        this.sendEmailNotification(to, subject);
        this.sendWebNotification();
        this.sendMobileNotification();
    }

    protected abstract void sendEmailNotification(String[] to, String subject);
    protected abstract void sendWebNotification();
    protected abstract void sendMobileNotification();
}
