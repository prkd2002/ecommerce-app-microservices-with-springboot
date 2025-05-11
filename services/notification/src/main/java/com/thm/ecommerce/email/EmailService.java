package com.thm.ecommerce.email;

import com.thm.ecommerce.kafka.order.Product;
import com.thm.ecommerce.kafka.payment.PaymentConfirmation;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {
    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine springTemplateEngine;
    private final SpringTemplateEngine templateEngine;


    @Async
    public void sendPaymentSuccessEmail(String destinationEmail, String customerName, BigDecimal amount, String orderReference) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_RELATED, StandardCharsets.UTF_8.name());
        mimeMessageHelper.setFrom("contact@rustyTech.com");
        final String templateName = EmailTemplates.PAYMENT_CONFIRMATION.getTemplateName();

        Map<String, Object> variables = Map.of(
                "customerName", customerName,
                "amount", amount,
                "orderReference", orderReference
        );

        Context context = new Context();
        context.setVariables(variables);
        mimeMessageHelper.setSubject(EmailTemplates.PAYMENT_CONFIRMATION.getSubject());

        try{
            String htmlTemplate = templateEngine.process(templateName, context);
            mimeMessageHelper.setText(htmlTemplate, true);

            mimeMessageHelper.setTo(destinationEmail);
            javaMailSender.send(mimeMessage);
            log.info(format("INFO - Email successfully sent to %s with template %s ",destinationEmail,templateName ));
        }catch (MessagingException exception){
            log.warn(format("WARN - Email could not be sent to %s with template %s ",destinationEmail,templateName ));

        }



    }


    @Async
    public void senddOrderConfirmationEmail(String destinationEmail, String customerName, BigDecimal amount, String orderReference, List<Product> productList) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_RELATED, StandardCharsets.UTF_8.name());
        mimeMessageHelper.setFrom("contact@rustyTech.com");
        final String templateName = EmailTemplates.ORDER_CONFIRMATION.getTemplateName();

        Map<String, Object> variables = Map.of(
                "customerName", customerName,
                "totalAmount", amount,
                "orderReference", orderReference,
                "products", productList
        );

        Context context = new Context();
        context.setVariables(variables);
        mimeMessageHelper.setSubject(EmailTemplates.ORDER_CONFIRMATION.getSubject());

        try{
            String htmlTemplate = templateEngine.process(templateName, context);
            mimeMessageHelper.setText(htmlTemplate, true);

            mimeMessageHelper.setTo(destinationEmail);
            javaMailSender.send(mimeMessage);
            log.info(format("INFO - Email successfully sent to %s with template %s ",destinationEmail,templateName ));
        }catch (MessagingException exception){
            log.warn(format("WARN - Email could not be sent to %s with template %s ",destinationEmail,templateName ));

        }



    }

}
