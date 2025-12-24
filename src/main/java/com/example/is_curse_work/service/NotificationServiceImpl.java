package com.example.is_curse_work.service;

import com.example.is_curse_work.repository.function.NotificationFunctionRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final JdbcTemplate jdbc;
    private final NotificationFunctionRepository notifFn;

    public NotificationServiceImpl(JdbcTemplate jdbc, NotificationFunctionRepository notifFn) {
        this.jdbc = jdbc;
        this.notifFn = notifFn;
    }

    @Scheduled(cron = "0 0 2 * * *")
    public void scheduled() {
        runExpiryBatch(1L, 3);
    }

    @Override
    public int runExpiryBatch(Long fridgeId, int daysBefore) {
        List<Long> productIds = jdbc.query(
                "select product_id from fn_get_products_expiring_within(?, ?)",
                (ResultSet rs, int rowNum) -> rs.getLong("product_id"),
                fridgeId, daysBefore
        );

        int created = 0;
        for (Long pid : productIds) {
            Long ownerId = jdbc.queryForObject("select owner_id from products where product_id = ?", Long.class, pid);
            OffsetDateTime expiresAt = jdbc.queryForObject("select expires_at from products where product_id = ?", OffsetDateTime.class, pid);

            String template = pickTemplate(expiresAt);
            notifFn.createNotification(ownerId, pid, "EMAIL", template, "SENT", null);
            created++;
        }
        return created;
    }

    private String pickTemplate(OffsetDateTime expiresAt) {
        if (expiresAt == null) return "OTHER";
        long hours = Duration.between(OffsetDateTime.now(), expiresAt).toHours();
        if (hours <= 0) return "TTL_0";
        if (hours <= 24) return "TTL_T1";
        return "TTL_T3";
    }
}

