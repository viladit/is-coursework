package com.example.is_curse_work.service;

import com.example.is_curse_work.model.Product;
import com.example.is_curse_work.model.User;
import com.example.is_curse_work.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductExpiryScheduler {
    private final ProductRepository products;
    private final EmailNotificationService mailer;

    @Value("${app.mail.expiring-days:3}")
    private int expiringDays;

    public ProductExpiryScheduler(ProductRepository products, EmailNotificationService mailer) {
        this.products = products;
        this.mailer = mailer;
    }

    @Scheduled(cron = "0 0 9 * * *")
    public void sendDailyExpiryEmails() {
        OffsetDateTime cutoff = OffsetDateTime.now().plusDays(expiringDays);
        List<Product> expiring = products.findExpiringForNotification(cutoff);
        Map<User, List<Product>> grouped = groupByOwner(expiring);
        mailer.sendExpiryDigest(grouped);
    }

    private Map<User, List<Product>> groupByOwner(List<Product> products) {
        Map<User, List<Product>> grouped = new LinkedHashMap<>();
        for (Product product : products) {
            grouped.computeIfAbsent(product.getOwner(), k -> new java.util.ArrayList<>()).add(product);
        }
        return grouped;
    }
}
