package uz.spiders.ecommerce.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailSenderService {

    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine;


    public void sendEmail(String receiver, String token) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());

        Context context = new Context();
        Map<String, Object> vars = new HashMap<>();
        vars.put("token", token);
        vars.put("email", receiver);

        context.setVariables(vars);
    
        String html = templateEngine.process("sample-email", context);

        helper.setTo(receiver);
        helper.setText(html, true);
        helper.setSubject("Verify email");
        helper.setFrom("lochinbek433@gmail.com");

        emailSender.send(message);
    }

}