package com.alurkerja.core.service.email;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.StringTemplateResolver;

import javax.mail.internet.MimeMessage;
import java.util.Map;

@Component
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    private TemplateEngine templateEngine;
    private String htmlStart = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">" +
            "<html xmlns=\"http://www.w3.org/1999/xhtml\"\n" +
            "      xmlns:th=\"http://www.thymeleaf.org\">" +
            "<head>\n" +
            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
            "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
            "    <meta name=\"color-scheme\" content=\"light\">\n" +
            "    <meta name=\"supported-color-schemes\" content=\"light\">\n" +
            "    <style>\n" +
            "        @media only screen and (max-width: 600px) {\n" +
            "            .inner-body {\n" +
            "                width: 100% !important;\n" +
            "            }\n" +
            "\n" +
            "            .footer {\n" +
            "                width: 100% !important;\n" +
            "            }\n" +
            "        }\n" +
            "\n" +
            "        @media only screen and (max-width: 500px) {\n" +
            "            .button {\n" +
            "                width: 100% !important;\n" +
            "            }\n" +
            "        }\n" +
            "    </style>\n" +
            "\n" +
            "</head>\n" +
            "<body style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; -webkit-text-size-adjust: none; background-color: #ffffff; color: #718096; height: 100%; line-height: 1.4; margin: 0; padding: 0; width: 100% !important;\">\n" +
            "<table class=\"wrapper\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\"\n" +
            "       style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; -premailer-cellpadding: 0; -premailer-cellspacing: 0; -premailer-width: 100%; background-color: #edf2f7; margin: 0; padding: 0; width: 100%;\">\n" +
            "    <tr>\n" +
            "        <td align=\"center\"\n" +
            "            style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative;\">\n" +
            "            <table class=\"content\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\"\n" +
            "                   style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; -premailer-cellpadding: 0; -premailer-cellspacing: 0; -premailer-width: 100%; margin: 0; padding: 0; width: 100%;\">\n" +
            "                <tr>\n" +
            "                    <td class=\"header\"\n" +
            "                        style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; padding: 25px 0; text-align: center;\">\n" +
            "                        <a href=\"#\"\n" +
            "                           style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; color: #3d4852; font-size: 19px; font-weight: bold; text-decoration: none; display: inline-block;\">\n" +
            "                            Notifikasi Rekrutmen\n" +
            "                        </a>\n" +
            "                    </td>\n" +
            "                </tr>\n" +
            "                <tr>\n" +
            "                    <td class=\"body\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"\n" +
            "                        style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; -premailer-cellpadding: 0; -premailer-cellspacing: 0; -premailer-width: 100%; background-color: #edf2f7; border-bottom: 1px solid #edf2f7; border-top: 1px solid #edf2f7; margin: 0; padding: 0; width: 100%;\">\n" +
            "                        <table class=\"inner-body\" align=\"center\" width=\"570\" cellpadding=\"0\" cellspacing=\"0\"\n" +
            "                               role=\"presentation\"\n" +
            "                               style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; -premailer-cellpadding: 0; -premailer-cellspacing: 0; -premailer-width: 570px; background-color: #ffffff; border-color: #e8e5ef; border-radius: 2px; border-width: 1px; box-shadow: 0 2px 0 rgba(0, 0, 150, 0.025), 2px 4px 0 rgba(0, 0, 150, 0.015); margin: 0 auto; padding: 0; width: 570px;\">\n" +
            "                            <tr>\n" +
            "                                <td class=\"content-cell\"\n" +
            "                                    style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; max-width: 100vw; padding: 32px;\">\n";
    private String htmlEnd = "</td>\n" +
            "                            </tr>\n" +
            "                        </table>\n" +
            "                    </td>\n" +
            "                </tr>\n" +
            "                <tr>\n" +
            "                    <td style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative;\">\n" +
            "                        <table class=\"footer\" align=\"center\" width=\"570\" cellpadding=\"0\" cellspacing=\"0\"\n" +
            "                               role=\"presentation\"\n" +
            "                               style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; -premailer-cellpadding: 0; -premailer-cellspacing: 0; -premailer-width: 570px; margin: 0 auto; padding: 0; text-align: center; width: 570px;\">\n" +
            "                            <tr>\n" +
            "                                <td class=\"content-cell\" align=\"center\"\n" +
            "                                    style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; max-width: 100vw; padding: 32px;\">\n" +
            "                                    <p style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; line-height: 1.5em; margin-top: 0; color: #b0adc5; font-size: 12px; text-align: center;\">\n" +
            "                                        &copy; <b th:text=\"2021\"></b>. All rights\n" +
            "                                        reserved.</p></td>\n" +
            "                            </tr>\n" +
            "                        </table>\n" +
            "                    </td>\n" +
            "                </tr>\n" +
            "            </table>\n" +
            "        </td>\n" +
            "    </tr>\n" +
            "</table>\n" +
            "</body>\n" +
            "</html>";

    @SneakyThrows
    private void sendEmail(String from,
                      String to,
                      String subject,
                      String bcc,
                      String cc,
                      String replyTo,
                      String personal,
                      String template,
                      boolean isTemplateString,
                      boolean isSimple,
                      Map<String, Object> variables,
                      byte[] file,
                      String filename,
                      boolean withAttachment) {
        String[] tos = to.split(",");
        if (isSimple) {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(from);
            simpleMailMessage.setTo(tos);
            simpleMailMessage.setSubject(subject);
            simpleMailMessage.setText(template);
            emailSender.send(simpleMailMessage);
        } else {
            Context context = new Context();
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            if (personal == null) {
                helper.setFrom(from);
            } else {
                helper.setFrom(from, personal);
            }
            helper.setTo(tos);
            helper.setSubject(subject);
            if (bcc != null) {
                String[] bccs = bcc.split(",");
                helper.setBcc(bccs);
            }
            if (cc != null) {
                String[] ccs = cc.split(",");
                helper.setCc(ccs);
            }
            if (replyTo != null) {
                helper.setReplyTo(replyTo);
            }
            if (variables != null) {
                context.setVariables(variables);
            }
            if (isTemplateString) {
                TemplateEngine stringTemplateEngine = new TemplateEngine();
                StringTemplateResolver stringTemplateResolver = new StringTemplateResolver();
                stringTemplateResolver.setTemplateMode(TemplateMode.HTML);
                stringTemplateEngine.setTemplateResolver(stringTemplateResolver);
                template = htmlStart
                        + template
                        + htmlEnd;
                helper.setText(stringTemplateEngine.process(template, context), true);
            } else {
                helper.setText(templateEngine.process(template, context), true);
            }
            if (withAttachment) {
                InputStreamSource iss = new ByteArrayResource(file);
                helper.addAttachment(filename, iss);
            }
            emailSender.send(message);
        }
    }

    public void send(String from,
                     String to,
                     String subject,
                     String text) {
        this.sendEmail(from, to, subject, null, null, null, null, text, false, true, null, null, null, false);
    }

    public void send(String from,
                     String to,
                     String subject,
                     String bcc,
                     String text) {
        this.sendEmail(from, to, subject, bcc, null, null, null, text, false, true, null, null, null, false);
    }

    public void send(String from,
                     String to,
                     String subject,
                     String bcc,
                     String cc,
                     String text) {
        this.sendEmail(from, to, subject, bcc, cc, null, null, text, false, true, null, null, null, false);
    }

    public void send(String from,
                     String to,
                     String subject,
                     String bcc,
                     String cc,
                     String replyTo,
                     String text) {
        this.sendEmail(from, to, subject, bcc, cc, replyTo, null, text, false, true, null, null, null, false);
    }

    public void send(String from,
                     String to,
                     String subject,
                     String bcc,
                     String cc,
                     String replyTo,
                     String personal,
                     String text) {
        this.sendEmail(from, to, subject, bcc, cc, replyTo, personal, text, false, true, null, null, null, false);
    }

    public void send(String from,
                     String to,
                     String subject,
                     String template,
                     Map<String, Object> variables) {
        this.sendEmail(from, to, subject, null, null, null, null, template, true, false, variables, null, null, false);
    }

    public void send(String from,
                     String to,
                     String subject,
                     String bcc,
                     String template,
                     Map<String, Object> variables) {
        this.sendEmail(from, to, subject, bcc, null, null, null, template, true, false, variables, null, null, false);
    }

    public void send(String from,
                     String to,
                     String subject,
                     String bcc,
                     String cc,
                     String template,
                     Map<String, Object> variables) {
        this.sendEmail(from, to, subject, bcc, cc, null, null, template, true, false, variables, null, null, false);
    }

    public void send(String from,
                     String to,
                     String subject,
                     String bcc,
                     String cc,
                     String replyTo,
                     String template,
                     Map<String, Object> variables) {
        this.sendEmail(from, to, subject, bcc, cc, replyTo, null, template, true, false, variables, null, null, false);
    }

    public void send(String from,
                     String to,
                     String subject,
                     String bcc,
                     String cc,
                     String replyTo,
                     String personal,
                     String template,
                     Map<String, Object> variables) {
        this.sendEmail(from, to, subject, bcc, cc, replyTo, personal, template, true, false, variables, null, null, false);
    }

    public void sendWithHtml(String from,
                     String to,
                     String subject,
                     String template,
                     Map<String, Object> variables) {
        this.sendEmail(from, to, subject, null, null, null, null, template, false, false, variables, null, null, false);
    }

    public void sendWithHtml(String from,
                     String to,
                     String subject,
                     String bcc,
                     String template,
                     Map<String, Object> variables) {
        this.sendEmail(from, to, subject, bcc, null, null, null, template, false, false, variables, null, null, false);
    }

    public void sendWithHtml(String from,
                     String to,
                     String subject,
                     String bcc,
                     String cc,
                     String template,
                     Map<String, Object> variables) {
        this.sendEmail(from, to, subject, bcc, cc, null, null, template, false, false, variables, null, null, false);
    }

    public void sendWithHtml(String from,
                     String to,
                     String subject,
                     String bcc,
                     String cc,
                     String replyTo,
                     String template,
                     Map<String, Object> variables) {
        this.sendEmail(from, to, subject, bcc, cc, replyTo, null, template, false, false, variables, null, null, false);
    }

    public void sendWithHtml(String from,
                     String to,
                     String subject,
                     String bcc,
                     String cc,
                     String replyTo,
                     String personal,
                     String template,
                     Map<String, Object> variables) {
        this.sendEmail(from, to, subject, bcc, cc, replyTo, personal, template, false, false, variables, null, null, false);
    }

    public void sendWithHtmlAndAttachment(String from,
                             String to,
                             String subject,
                             String template,
                             Map<String, Object> variables,
                             byte[] file,
                             String fileName) {
        this.sendEmail(from, to, subject, null, null, null, null, template, false, false, variables, file, fileName, true);
    }

    public void sendWithHtmlAndAttachment(String from,
                             String to,
                             String subject,
                             String bcc,
                             String template,
                             Map<String, Object> variables,
                             byte[] file,
                             String fileName) {
        this.sendEmail(from, to, subject, bcc, null, null, null, template, false, false, variables, file, fileName, true);
    }

    public void sendWithHtmlAndAttachment(String from,
                             String to,
                             String subject,
                             String bcc,
                             String cc,
                             String template,
                             Map<String, Object> variables,
                             byte[] file,
                             String fileName) {
        this.sendEmail(from, to, subject, bcc, cc, null, null, template, false, false, variables, file, fileName, true);
    }

    public void sendWithHtmlAndAttachment(String from,
                             String to,
                             String subject,
                             String bcc,
                             String cc,
                             String replyTo,
                             String template,
                             Map<String, Object> variables,
                             byte[] file,
                             String fileName) {
        this.sendEmail(from, to, subject, bcc, cc, replyTo, null, template, false, false, variables, file, fileName, true);
    }

    public void sendWithHtmlAndAttachment(String from,
                             String to,
                             String subject,
                             String bcc,
                             String cc,
                             String replyTo,
                             String personal,
                             String template,
                             Map<String, Object> variables,
                             byte[] file,
                             String fileName) {
        this.sendEmail(from, to, subject, bcc, cc, replyTo, personal, template, false, false, variables, file, fileName, true);
    }

}
