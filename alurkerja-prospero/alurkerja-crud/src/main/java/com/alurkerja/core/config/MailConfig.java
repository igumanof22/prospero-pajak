package com.alurkerja.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.mail.MessagingException;
import java.util.Properties;

@Configuration
public class MailConfig {

    @Value("${spring.mail.default-encoding:UTF-8}")
    private String defaultEncoding;
    @Value("${spring.mail.host:mailhog.cloud.javan.co.id}")
    private String host;
    @Value("${spring.mail.username:}")
    private String username;
    @Value("${spring.mail.password:}")
    private String password;
    @Value("${spring.mail.port:#{1025}}")
    private int port;
    @Value("${spring.mail.protocol:smtp}")
    private String protocol;
    @Value("${spring.mail.test-connection:#{false}}")
    private boolean testConnection;
    @Value("${spring.mail.properties.mail.smtp.auth:#{false}}")
    private String auth;
    @Value("${spring.mail.properties.mail.smtp.starttls.enable:#{true}}")
    private String starttls;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setDefaultEncoding(defaultEncoding);
        javaMailSender.setHost(host);
        javaMailSender.setUsername(username);
        javaMailSender.setPassword(password);
        javaMailSender.setPort(port);
        javaMailSender.setProtocol(protocol);
        if (testConnection) {
            try {
                javaMailSender.testConnection();
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
        Properties props = javaMailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", auth);
        props.put("mail.smtp.starttls.enable", starttls);
        return javaMailSender;
    }

}
