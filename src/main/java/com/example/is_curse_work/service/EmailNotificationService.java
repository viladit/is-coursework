package com.example.is_curse_work.service;

import com.example.is_curse_work.model.Product;
import com.example.is_curse_work.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
public class EmailNotificationService {
    private static final Logger log = LoggerFactory.getLogger(EmailNotificationService.class);
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    private final JavaMailSender mailSender;

    @Value("${app.mail.enabled:false}")
    private boolean enabled;

    @Value("${app.mail.from:no-reply@example.com}")
    private String from;

    public EmailNotificationService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendExpiryDigest(Map<User, List<Product>> grouped) {
        if (!enabled) {
            log.info("Mail disabled, skipping expiry notifications. items={}", grouped.size());
            return;
        }
        for (var entry : grouped.entrySet()) {
            User user = entry.getKey();
            List<Product> products = entry.getValue();
            if (user.getEmail() == null || products.isEmpty()) {
                continue;
            }
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo(user.getEmail());
            msg.setFrom(from);
            msg.setSubject("Срок годности продуктов");
            msg.setText(buildBody(user, products));
            try {
                mailSender.send(msg);
            } catch (Exception ex) {
                log.warn("Failed to send email to {}", user.getEmail(), ex);
            }
        }
    }

    private String buildBody(User user, List<Product> products) {
        StringBuilder sb = new StringBuilder();
        sb.append("Здравствуйте, ").append(user.getName()).append("!\n\n");
        sb.append("Ниже список продуктов, которые скоро испортятся или уже просрочены:\n");
        for (Product p : products) {
            sb.append("- ").append(p.getName());
            if (p.getExpiresAt() != null) {
                sb.append(" (до ").append(p.getExpiresAt().toLocalDateTime().format(DATE_FORMAT)).append(")");
            }
            sb.append("\n");
        }
        sb.append("\nПожалуйста, проверьте холодильник.");
        return sb.toString();
    }
}
