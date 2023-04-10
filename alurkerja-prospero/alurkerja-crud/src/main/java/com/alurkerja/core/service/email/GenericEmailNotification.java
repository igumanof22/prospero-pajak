package com.alurkerja.core.service.email;

import com.google.common.io.Resources;
import lombok.Getter;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class GenericEmailNotification<E, V, D> extends BpmnNotification<E, V, D> {


    @Getter
    private Context context;
    private TemplateEngine templateEngine;
    @Getter
    private MimeMessage message;
    @Getter
    private MimeMessageHelper helper;

    public GenericEmailNotification(String template,
                                    E entity,
                                    V variable,
                                    D dto) {
        super(entity, variable, dto);
        this.template = template;
        this.templateEngine = null;
        this.message = javaMailSender.createMimeMessage();
        try {
            this.helper = new MimeMessageHelper(this.message, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        this.context = new Context();
    }

    public GenericEmailNotification(String template,
                                    TemplateEngine templateEngine,
                                    E entity,
                                    V variable,
                                    D dto) {
        super(entity, variable, dto);
        this.template = template;
        this.templateEngine = templateEngine;
        this.message = javaMailSender.createMimeMessage();
        try {
            this.helper = new MimeMessageHelper(this.message, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        this.context = new Context();
    }

    @Override
    protected void sendEmailNotification(String[] to, String subject) {
        try {
            helper.setTo(to);
            helper.setSubject(subject);
            if (templateEngine != null) {
                context.setVariable("subject", subject);
                for (Field f : this.dto.getClass().getDeclaredFields()) {
                    f.setAccessible(true);
                    if (f.get(this.dto) != null) {
                        context.setVariable(f.getName(), f.get(this.dto));
                    }
                }
                helper.setText(templateEngine.process(this.template, context), true);
            } else {
                URL uri = Resources.getResource(this.template);
                if (uri != null) {
                    helper.setText(Resources.toString(uri, StandardCharsets.UTF_8), true);
                }
            }
            javaMailSender.send(message);
        } catch (IOException | MessagingException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void sendWebNotification() {

    }

    @Override
    protected void sendMobileNotification() {

    }
}
